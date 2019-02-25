package com.example.crudmahasiswa.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crudmahasiswa.DetailActivity;
import com.example.crudmahasiswa.MainActivity;
import com.example.crudmahasiswa.R;
import com.example.crudmahasiswa.model.DatanyaItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterMahasiswa extends RecyclerView.Adapter<AdapterMahasiswa.ViewHolder> {

    Context context;
    List<DatanyaItem> datanyaItems;

    public AdapterMahasiswa(Context context, List<DatanyaItem> datanyaItems) {
        this.context = context;
        this.datanyaItems = datanyaItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_mahasiswa, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {

        //get data item untuk ditampilkan di list

        viewHolder.txtNim.setText(datanyaItems.get(i).getMahasiswaNim());
        viewHolder.txtNama.setText(datanyaItems.get(i).getMahasiswaNama());
        viewHolder.txtJurusan.setText(datanyaItems.get(i).getMahasiswaJurusan());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set data yang mau dikirim
                DatanyaItem data = new DatanyaItem();
                data.setMahasiswaId(datanyaItems.get(i).getMahasiswaId());
                data.setMahasiswaNim(datanyaItems.get(i).getMahasiswaNim());
                data.setMahasiswaNama(datanyaItems.get(i).getMahasiswaNama());
                data.setMahasiswaJurusan(datanyaItems.get(i).getMahasiswaJurusan());

                //kirim datanya dengan parameter pertama sebagai key, parameter kedua value yang dikirim
                Intent kirim = new Intent(context, DetailActivity.class);
                kirim.putExtra("key",data);
                context.startActivity(kirim);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(datanyaItems==null)return 0;
        return datanyaItems.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_nama)
        TextView txtNama;
        @BindView(R.id.txt_nim)
        TextView txtNim;
        @BindView(R.id.txt_jurusan)
        TextView txtJurusan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
