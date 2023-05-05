package com.example.project2.RecycleViewStuff;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.DB.UserDAO;
import com.example.project2.R;
import com.example.project2.StarConfData.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapterView extends RecyclerView.Adapter<UserAdapterView.UserViewHolder> {
    private List<User> mUserArrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView;

        public UserViewHolder (View itemView, OnItemClickListener listener) {
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

    public UserAdapterView(List<User> userArrayList){mUserArrayList = userArrayList;}

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycler_view, parent, false);
        UserViewHolder uvh = new UserViewHolder(v, mListener);
        return uvh;
    }
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User currentUser = mUserArrayList.get(position);

        holder.mImageView.setImageResource(R.drawable.starfleetbadge);
        holder.mTextView.setText(currentUser.getUsername());
    }

    @Override
    public int getItemCount() {
        return mUserArrayList.size();
    }
}
