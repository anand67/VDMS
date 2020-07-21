package com.example.vdsuser.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vdsuser.ModelClass.MyVehicleListModelClass;
import com.example.vdsuser.R;
import com.example.vdsuser.VehicleDetailsActivity;

import java.util.List;

public class myVehicleListAdapter extends RecyclerView.Adapter<myVehicleListAdapter.MyViewHolder> {

    Context mtx;
    List<MyVehicleListModelClass> requestList;

    public myVehicleListAdapter(Context mtx, List<MyVehicleListModelClass> requestList) {
        this.mtx = mtx;
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public myVehicleListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_vehicle_list, parent, false);
        return new myVehicleListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final myVehicleListAdapter.MyViewHolder holder, final int position) {
        final MyVehicleListModelClass lists = requestList.get(position);

        holder.vehicleNameTV.setText(lists.getVehicle_name());
        holder.vehicleNumberTV.setText(lists.getVehicle_number());

        final Bundle bundle = new Bundle();
        bundle.putString("vehicleid",lists.getId());
        bundle.putString("vehicleModel",lists.getVehicle_name());
        bundle.putString("vehicleNumber",lists.getVehicle_number());
        bundle.putString("vehicleColor",lists.getVehicle_color());

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mtx, VehicleDetailsActivity.class);
                intent.putExtras(bundle);
                mtx.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vehicleNameTV, vehicleNumberTV;
        LinearLayout ll;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicleNameTV = itemView.findViewById(R.id.vehicleNameTextView);
            vehicleNumberTV = itemView.findViewById(R.id.vehicleNumberTextView);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}
