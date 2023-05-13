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
import androidx.room.Room;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.FleetsTableDAO;
import com.example.project2.DB.StarShipDAO;
import com.example.project2.Observer.ContextObserver;
import com.example.project2.R;
import com.example.project2.StarConfData.Fleet;

import java.util.ArrayList;

public class FleetSelectAdapter extends ArrayAdapter<Fleet> {

    StarShipDAO mStarShipDAO;
    ContextObserver mObserver = new ContextObserver();
    public FleetSelectAdapter(Context context, ArrayList<Fleet> fleets){
        super(context, 0, fleets);
        mObserver.setContext(context);
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
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.fleet_spinner_row, parent, false
            );
        }

        mStarShipDAO = Room.databaseBuilder(mObserver.getContext(), AppDataBase.class, AppDataBase.SHIP_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .StarShipDAO();

        ImageView imageViewFlag = convertView.findViewById(R.id.fleet_pic);
        TextView fleetNameView = convertView.findViewById(R.id.fleet_name);
       /* TextView shipOneView = convertView.findViewById(R.id.shipOneText);
        TextView shipTwoView = convertView.findViewById(R.id.shipTwoText);
        TextView shipThreeView = convertView.findViewById(R.id.shipThreeText);*/

        Fleet fleet = getItem(position);

        if(fleet != null){
            imageViewFlag.setImageResource(fleet.getFleetImage());
            fleetNameView.setText(fleet.getFleetName());
            /*shipOneView.setText(mStarShipDAO.getShipByLogId(fleet.getFleet().get(0)).getShipType());
            shipTwoView.setText(mStarShipDAO.getShipByLogId(fleet.getFleet().get(1)).getShipType());
            shipThreeView.setText(mStarShipDAO.getShipByLogId(fleet.getFleet().get(2)).getShipType());*/
        }

        return convertView;
    }
}
