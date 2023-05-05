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

import java.util.List;

public class ShipRecyclerAdapter extends RecyclerView.Adapter<ShipRecyclerAdapter.ShipRecyclerHolder> {
    private List<Ship> mShipList;
    private UserAdapterView.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(UserAdapterView.OnItemClickListener listener){
        mListener = listener;
    }

    public static class ShipRecyclerHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public ShipRecyclerHolder (View itemView, UserAdapterView.OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    public ShipRecyclerAdapter(List<Ship> shipList) {
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

        holder.mImageView.setImageResource(R.drawable.starfleetbadge);
        holder.mTextView.setText(currentShip.getShipType());
    }

    @Override public int getItemCount(){
        return mShipList.size();
    }
    public List<Ship> getShipList(){
        return mShipList;
    }
}
