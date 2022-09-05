package com.rahat.pharmsafebd.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.AmbulanceModelClass;

import java.util.ArrayList;

public class AmbulanceListAdapter extends RecyclerView.Adapter<AmbulanceListAdapter.MyViewHolder> {

    private ArrayList<AmbulanceModelClass> ambulanceList;
    private Context context;

    public AmbulanceListAdapter(ArrayList<AmbulanceModelClass> ambulanceList, Context context) {
        this.ambulanceList = ambulanceList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.adaper_ambulance_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AmbulanceModelClass ambulanceModelClass = ambulanceList.get(position);
        holder.hospitalNameTv.setText(ambulanceModelClass.getHospitalName());
        holder.hotlineTv.setText("Hotline : " + ambulanceModelClass.getHotline());
    }

    @Override
    public int getItemCount() {
        return ambulanceList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView hospitalNameTv, hotlineTv;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hospitalNameTv = itemView.findViewById(R.id.hospital_name_id);
            hotlineTv = itemView.findViewById(R.id.hotline_id);
        }
    }
}
