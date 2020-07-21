package com.example.vdsuser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vdsuser.ModelClass.MyHistoryListModelClass;
import com.example.vdsuser.ModelClass.MyHistoryListModelClass;
import com.example.vdsuser.R;
import com.example.vdsuser.VehicleDetailsActivity;

import java.util.List;

public class myHistoryListAdapter extends RecyclerView.Adapter<myHistoryListAdapter.MyViewHolder> {

    Context mtx;
    List<MyHistoryListModelClass> historyList;

    public myHistoryListAdapter(Context mtx, List<MyHistoryListModelClass> historyList) {
        this.mtx = mtx;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public myHistoryListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_history_list, parent, false);
        return new myHistoryListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final myHistoryListAdapter.MyViewHolder holder, final int position) {
        final MyHistoryListModelClass lists = historyList.get(position);


        holder.organisationTextView.setText(lists.getOrganization());
        holder.vehicleTextView.setText(lists.getVehicle());
        holder.dateTimeTextView.setText(lists.getDate_time());
        holder.statusTextView.setText(lists.getStatus());

    }

    @Override

    public int getItemCount() {
        return historyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView organisationTextView, vehicleTextView, dateTimeTextView, statusTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            organisationTextView = itemView.findViewById(R.id.organisationTextView);
            vehicleTextView = itemView.findViewById(R.id.vehicleTextView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}
