package com.example.absensipegawai;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.absensipegawai.pojo.DataListPegawai;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<DataListPegawai> data;

    CustomAdapter(Context context){
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<DataListPegawai> data){
        this.data = data;
        Log.d("SetListPegawai", String.valueOf(data.size()));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.cardview_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        String datetime = data.get(i).getJam();
        holder.datetimeuser.setText(datetime);
        String nama = data.get(i).getNama();
        holder.namauser.setText(nama);
        String nidn = data.get(i).getNidn();
        holder.nidnuser.setText(nidn);
        String keterangan = data.get(i).getKeterangan();
        holder.keteranganuser.setText(keterangan);
        String lokasi = data.get(i).getLokasi();
        holder.lokasiuser.setText(lokasi);

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView datetimeuser, namauser, nidnuser, keteranganuser, lokasiuser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            datetimeuser = itemView.findViewById(R.id.datetimeuser);
            namauser = itemView.findViewById(R.id.namauser);
            nidnuser = itemView.findViewById(R.id.nidnuser);
            keteranganuser = itemView.findViewById(R.id.keteranganuser);
            lokasiuser = itemView.findViewById(R.id.lokasiuser);
        }
    }
}
