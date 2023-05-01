package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.project2.DB.FleetDAO;
import com.example.project2.StarConfData.Fleet;
import com.example.project2.databinding.ActivityFleetListBinding;

import java.util.ArrayList;

public class FleetListActivity extends AppCompatActivity {

    ActivityFleetListBinding mFleetListActivityBinding;

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
        setContentView(R.layout.activity_fleet_list);

        mFleetListActivityBinding = ActivityFleetListBinding.inflate(getLayoutInflater());
        setContentView(mFleetListActivityBinding.getRoot());

        mFleetList = mFleetDAO.getFleets();

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapater = new FleetViewAdapater(fleets);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapater);

        Boolean adminStatus = getIntent().getBooleanExtra(MESSAGE, true);
        String username = getIntent().getStringExtra(MESSAGE_1);
        /*This is an onClickListener of the adapter of the recycler view. Essentially, when a button
        * in the recycler view is clicked, it will send the user to the fleetViewerActivity, and the
        * intent will pass the admin status, the name of the owner of the fleet associated with the
        * pressed button, and the name of the logged user, and the ID of the selected fleet in that
        * order. The name of the logged user shouldn't be different from the owner of the fleet
        * UNLESS the logged user is an admin accessing the fleet of another user.*/
        mAdapater.setOnItemClickListener(new FleetViewAdapater.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mFleetList.get(position);
                Intent intent = FleetViewActivity.intentFactory(getApplicationContext(),
                        adminStatus,
                        mAdapater.getFleetArrayList().get(position).getOwner().getUsername(),
                        username,
                        mAdapater.getFleetArrayList().get(position).getLogId());

            }
        });
    }
}