package com.example.crudmahasiswa;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crudmahasiswa.model.DatanyaItem;
import com.example.crudmahasiswa.model.ResponseInsert;
import com.example.crudmahasiswa.network.ConfigRetrofit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.edt_nim_detail)
    EditText edtNimDetail;
    @BindView(R.id.edt_nama_detail)
    EditText edtNamaDetail;
    @BindView(R.id.edt_jurusan_detail)
    EditText edtJurusanDetail;
    @BindView(R.id.btn_update_detail)
    Button btnUpdateDetail;

    String id,nim, nama, jurusan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //terima data
        DatanyaItem data = getIntent().getParcelableExtra("key");
        id = data.getMahasiswaId();
        nim = data.getMahasiswaNim();
        nama = data.getMahasiswaNama();
        jurusan = data.getMahasiswaJurusan();


        //tampilkan kelayout
        edtNimDetail.setText(nim);
        edtNamaDetail.setText(nama);
        edtJurusanDetail.setText(jurusan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.btn_update_detail)
    public void onViewClicked() {
        infalteDialogUpdate();
    }

    private void infalteDialogUpdate() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Confirmation");
        dialog.setMessage("Are you sure for update this data?");

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cekInputanUpdate(dialog);
            }
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.show();
    }

    private void cekInputanUpdate(DialogInterface dialog) {

        nim = edtNimDetail.getText().toString().trim();
        nama = edtNamaDetail.getText().toString().trim();
        jurusan = edtJurusanDetail.getText().toString().trim();

        if (TextUtils.isEmpty(nim) || TextUtils.isEmpty(nama) || TextUtils.isEmpty(jurusan)) {
            Toast.makeText(this, "Tidak Boleh Ada yang Kosong", Toast.LENGTH_SHORT).show();
        }else {
            fetchUpdateData(dialog);
        }
    }

    private void fetchUpdateData(final DialogInterface dialog) {
        ConfigRetrofit.getInstance().update(id,nim,nama,jurusan).enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {

                if (response.isSuccessful()){
                    String msg = response.body().getPesan();
                    int status = response.body().getStatus();

                    if (status==1){
                        dialog.dismiss();
                        Toast.makeText(DetailActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(DetailActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_delete, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int idMenu = item.getItemId();

        if (idMenu == R.id.btn_delete) {

            AlertDialog dialog = new AlertDialog
                    .Builder(DetailActivity.this)
                    .create();

            dialog.setTitle("Confirmation");
            dialog.setMessage("Are you sure want delete this data ?");
            dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ConfigRetrofit.getInstance().delete(id)
                                    .enqueue(new Callback<ResponseInsert>() {
                                        @Override
                                        public void onResponse(Call<ResponseInsert> call,
                                                               Response<ResponseInsert> response) {

                                            String msg = response.body().getPesan();
                                            int status = response.body().getStatus();

                                            if (status == 1) {
                                                Toast.makeText(DetailActivity.this, msg,
                                                        Toast.LENGTH_SHORT).show();

                                                startActivity(new Intent(DetailActivity.this,
                                                        MainActivity.class));

                                                finish();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseInsert> call, Throwable t) {

                                        }
                                    });

                            dialogInterface.dismiss();

                        }
                    });

            dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

            dialog.show();

        }

        return super.onOptionsItemSelected(item);

    }
}

