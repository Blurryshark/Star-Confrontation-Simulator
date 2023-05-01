package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.project2.DB.FleetDAO;
import com.example.project2.StarConfData.Fleet;

import java.util.ArrayList;
import java.util.List;

public class FleetViewerActivity extends AppCompatActivity {

    private FleetViewerActivity mFleetViewerActivityBinding;

    private RecyclerView mRecyclerView;
    private FleetViewAdapater mAdapater;
    private RecyclerView.LayoutManager mLayoutManager;

    FleetDAO mFleetDAO;

    ArrayList<Fleet> mFleetList;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";

    ArrayList<Fleet> fleets;
    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String username){
        Intent intent = new Intent (packageContext, LandingPageActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_viewer);

        mFleetList = mFleetDAO.getFleets();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapater = new FleetViewAdapater(fleets);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapater);

        mAdapater.setOnItemClickListener(new FleetViewAdapater.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mFleetList.get(position);
            }
        });
    }
}