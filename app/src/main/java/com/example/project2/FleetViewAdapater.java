package com.example.project2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.StarConfData.Fleet;

import java.util.ArrayList;

public class FleetViewAdapater extends RecyclerView.Adapter<FleetViewAdapater.FleetViewHolder> {

    private ArrayList<Fleet> mFleetArrayList;

    public static class FleetViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public FleetViewHolder (View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
        }
    }

    public FleetViewAdapater(ArrayList<Fleet> fleetArrayList) {
        mFleetArrayList = fleetArrayList;
    }

    @NonNull
    @Override
    public FleetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        FleetViewHolder fvh = new FleetViewHolder(v);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FleetViewHolder holder, int position) {
        Fleet currentFleet = mFleetArrayList.get(position);

        holder.mImageView.setImageResource(R.drawable.starfleetbadge);
        holder.mTextView1.setText(currentFleet.getFleetName());
        holder.mTextView2.setText(currentFleet.getOwner().getUsername());
    }

    @Override
    public int getItemCount() {
        return mFleetArrayList.size();
    }
}
