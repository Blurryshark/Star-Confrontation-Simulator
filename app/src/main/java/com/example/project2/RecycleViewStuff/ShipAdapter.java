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
import com.example.project2.StarConfData.Ship;

import java.util.ArrayList;

public class ShipAdapter extends ArrayAdapter<Ship> {
        public ShipAdapter(Context context, ArrayList<Ship> ships){
                super (context, 0, ships);
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

        private View initView (int position, View convertView, ViewGroup parent){
                if(convertView == null){
                        convertView = LayoutInflater.from(getContext()).inflate(
                                R.layout.ship_spinner_row, parent, false
                        );
                }

                ImageView imageViewFlag = convertView.findViewById(R.id.ship_pic);
                TextView textView = convertView.findViewById(R.id.ship_name);

                Ship ship = getItem(position);

                if(ship != null){
                        imageViewFlag.setImageResource(ship.getShipImage());
                        textView.setText(ship.getShipType());
                }

                return convertView;
        }
}
