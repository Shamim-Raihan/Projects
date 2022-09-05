package com.rahat.pharmsafebd.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rahat.pharmsafebd.R;
import com.rahat.pharmsafebd.services.model.MedicineModelClass;
import com.rahat.pharmsafebd.view.ui.MedicineDetailsActivity;
import com.rahat.pharmsafebd.view.ui.MedicineNameListActivity;

import java.util.ArrayList;
import java.util.Collection;

public class MedicineGroupListAdapter extends RecyclerView.Adapter<MedicineGroupListAdapter.MyViewHoder> implements Filterable {

    private ArrayList<MedicineModelClass> medicineList;
    private Context context;
    private String category;
    private ArrayList<MedicineModelClass> medicineListAll;

    public MedicineGroupListAdapter(ArrayList<MedicineModelClass> medicineList, Context context, String category) {
        this.medicineList = medicineList;
        this.context = context;
        this.category = category;
        this.medicineListAll = new ArrayList<>(medicineList);
    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.adapter_medicine_group_list_design, parent, false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
        MedicineModelClass medicineModelClass = medicineList.get(position);
        if (category.equals("name"))
        {
            if(medicineModelClass.getAvailability().equals("Stock Available")){
                holder.medicineGroupNameEd.setText(medicineModelClass.getMedicineName());
            }
            else {
                holder.layout.setVisibility(View.GONE);
            }

        }
        else if(category.equals("group")){
            holder.medicineGroupNameEd.setText(medicineModelClass.getGroup());
        }

    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<MedicineModelClass> filteredList = new ArrayList<>();
            if (charSequence.toString().isEmpty()){
                filteredList.addAll(medicineListAll);
            }
            else {
                for (MedicineModelClass list : medicineListAll){
                    if (list.getGroup().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(list);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            medicineList.clear();
            medicineList.addAll((Collection<? extends MedicineModelClass>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHoder extends RecyclerView.ViewHolder {

        TextView medicineGroupNameEd;
        LinearLayout layout;

        public MyViewHoder(@NonNull View itemView) {
            super(itemView);

            medicineGroupNameEd = itemView.findViewById(R.id.medicine_id);
            layout = itemView.findViewById(R.id.layout_id);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    MedicineModelClass medicineModelClass = medicineList.get(pos);
                    if(category.equals("group")){
                        Intent intent = new Intent(context, MedicineNameListActivity.class);
                        intent.putExtra("singleMedicineDetails", medicineModelClass);
                        context.startActivity(intent);
                    }

                    else if(category.equals("name")){
                        Intent intent = new Intent(context, MedicineDetailsActivity.class);
                        intent.putExtra("singleMedicineDetails", medicineModelClass);
                        intent.putExtra("from", "adapter");
                        intent.putExtra("searchString", "s");
                        context.startActivity(intent);
                    }

                }
            });
        }
    }
}
