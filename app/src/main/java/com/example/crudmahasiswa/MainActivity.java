package com.example.crudmahasiswa;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudmahasiswa.adapter.AdapterMahasiswa;
import com.example.crudmahasiswa.model.DatanyaItem;
import com.example.crudmahasiswa.model.ResponseInsert;
import com.example.crudmahasiswa.model.ResponseMahasiswa;
import com.example.crudmahasiswa.network.ConfigRetrofit;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    EditText edtNim,edtNama,edtJurusan;
    String nim,nama,jurusan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflateDiologInput();
            }
        });

        fetchDataMahasiswa();
    }

    private void inflateDiologInput() {
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.input_mahasiswa, null);

        edtNim = v.findViewById(R.id.edt_nim_inputan);
        edtNama = v.findViewById(R.id.edt_nama_inputan);
        edtJurusan = v.findViewById(R.id.edt_jurusan_inputan);

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setView(v);
        dialog.setCancelable(false);
        dialog.setTitle("Masukan Data");

        dialog.setPositiveButton("Insert", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkInputan();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void checkInputan() {
        nim = edtNim.getText().toString().trim();
        nama = edtNama.getText().toString().trim();
      jurusan = edtJurusan.getText().toString().trim();

        if (TextUtils.isEmpty(nim)|| TextUtils.isEmpty(nama)||TextUtils.isEmpty(jurusan)){
            Toast.makeText(this, "Tidak Boleh ada yang Kosong", Toast.LENGTH_SHORT).show();
        }else {
            fetchInsertMahasiswa();
        }
    }

    private void fetchInsertMahasiswa() {
        ConfigRetrofit.getInstance().create(nim,nama,jurusan).enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {

                if (response.isSuccessful()){
                    String message = response.body().getPesan();
                    int status = response.body().getStatus();

                    if (status==1){
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        recreate();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {

            }
        });
    }

    private void fetchDataMahasiswa() {
        ConfigRetrofit.getInstance().getDataMahasiswa().enqueue(new Callback<ResponseMahasiswa>() {
            @Override
            public void onResponse(Call<ResponseMahasiswa> call, Response<ResponseMahasiswa> response) {
                if (response.isSuccessful()) {
                    String massege = response.body().getPesan();
                    int status = response.body().getStatus();

                    if (status == 1) {
                        List<DatanyaItem> datanyaItems = response.body().getDatanya();
                        recyclerview.setHasFixedSize(true);
                        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        AdapterMahasiswa adapter = new AdapterMahasiswa(MainActivity.this, datanyaItems);
                        recyclerview.setAdapter(adapter);
                    } else {
                        Toast.makeText(MainActivity.this, massege, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseMahasiswa> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
