package com.example.project2.RecycleViewStuff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project2.R;
import com.example.project2.StarConfData.Admiral;

import java.util.ArrayList;

public class AdmiralAdapter extends ArrayAdapter<Admiral> {

    public AdmiralAdapter(Context context, ArrayList<Admiral> admirals){
        super(context, 0, admirals);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.admiral_spinner_row, parent, false
            );
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.admiral_portrait);
        TextView textView = convertView.findViewById(R.id.admiral_name);

        Admiral admiral = getItem(position);

        if (admiral != null) {
            imageViewFlag.setImageResource(admiral.getAdmiralImage());
            textView.setText(admiral.getAdmiralName());
        }

        return convertView;
    }
}
