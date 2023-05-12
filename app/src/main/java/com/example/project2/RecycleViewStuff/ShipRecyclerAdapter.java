package com.example.project2.RecycleViewStuff;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.R;
import com.example.project2.StarConfData.Ship;
import com.example.project2.StarConfData.User;

import java.util.ArrayList;
import java.util.List;

public class ShipRecyclerAdapter extends RecyclerView.Adapter<ShipRecyclerAdapter.ShipRecyclerHolder> {
    private List<Ship> mShipList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ShipRecyclerHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public ShipRecyclerHolder (View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.ship_pic);
            mTextView = itemView.findViewById(R.id.ship_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.OnItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ShipRecyclerAdapter(ArrayList<Ship> shipList) {
        mShipList = shipList;
    }

    @NonNull
    @Override
    public ShipRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ship_recycler_cardview_layout,
                parent, false);
        ShipRecyclerHolder srh = new ShipRecyclerHolder(v, mListener);
        return srh;
    }

    @Override
    public void onBindViewHolder(@NonNull ShipRecyclerHolder holder, int position) {
        Ship currentShip = mShipList.get(position);

        holder.mImageView.setImageResource(currentShip.getShipImage());
        holder.mTextView.setText(currentShip.getShipType());
    }

    @Override public int getItemCount(){
        return mShipList.size();
    }
    public List<Ship> getShipList(){
        return mShipList;
    }
}
