package com.example.project2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.StarShipDAO;
import com.example.project2.DB.UserDAO;
import com.example.project2.R;
import com.example.project2.RecycleViewStuff.AdmiralAdapter;
import com.example.project2.RecycleViewStuff.ShipAdapter;
import com.example.project2.StarConfData.Admiral;
import com.example.project2.StarConfData.Fleet;
import com.example.project2.StarConfData.Ship;
import com.example.project2.databinding.ActivityFleetBuilderBinding;

import java.util.ArrayList;
import java.util.HashMap;

public class FleetBuilderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityFleetBuilderBinding mFleetBuilderBinding;

    private ArrayList<Admiral> mAdmiralList;
    private ArrayList<Ship> mShipList;
    private AdmiralAdapter mAdmiralAdapter;
    private ShipAdapter mShipOneAdapter;
    private ShipAdapter mShipTwoAdapter;
    private ShipAdapter mShipThreeAdapter;
    private HashMap<Integer, Ship> newFleetShips;
    private Admiral newAdmiral;


    private StarShipDAO mStarShipDAO;
    private FleetDAO mFleetDAO;
    private UserDAO mUserDAO;

    Spinner mAdmiralSpinner;
    Spinner mShipOneSpinner;
    Spinner mShipTwoSpinner;
    Spinner mShipThreeSpinner;
    Button mCreateFleetButton;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_builder);

        mFleetBuilderBinding = ActivityFleetBuilderBinding.inflate(getLayoutInflater());
        setContentView(mFleetBuilderBinding.getRoot());

        initList();

        mAdmiralSpinner = mFleetBuilderBinding.admiralSpinner;
        mShipOneSpinner = mFleetBuilderBinding.shipOneSpinner;
        mShipTwoSpinner = mFleetBuilderBinding.shipTwoSpinner;
        mShipThreeSpinner = mFleetBuilderBinding.shipThreeSpinner;

        mAdmiralAdapter = new AdmiralAdapter(this, mAdmiralList);
        mShipOneAdapter = new ShipAdapter(this, mShipList);
        mShipTwoAdapter = new ShipAdapter(this, mShipList);
        mShipThreeAdapter = new ShipAdapter(this, mShipList);

        mAdmiralSpinner.setAdapter(mAdmiralAdapter);
        mShipOneSpinner.setAdapter(mShipOneAdapter);
        mShipTwoSpinner.setAdapter(mShipTwoAdapter);
        mShipThreeSpinner.setAdapter(mShipThreeAdapter);

        mStarShipDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.SHIP_DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build()
                        .StarShipDAO();
        mFleetDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.FLEET_DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build()
                        .FleetDAO();
        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.USER_DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build()
                        .UserDAO();



        mAdmiralSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Admiral clickedAdmiral = (Admiral) adapterView.getItemAtPosition(position);
                newAdmiral = clickedAdmiral;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mShipOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private final Integer SHIP_KEY = 1;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Ship clickedShip = (Ship) adapterView.getItemAtPosition(position);
                newFleetShips.put(SHIP_KEY, clickedShip);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mShipTwoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private final Integer SHIP_KEY = 2;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Ship clickedShip = (Ship) adapterView.getItemAtPosition(position);
                newFleetShips.put(SHIP_KEY, clickedShip);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mShipThreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private final Integer SHIP_KEY = 3;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Ship clickedShip = (Ship) adapterView.getItemAtPosition(position);
                newFleetShips.put(SHIP_KEY, clickedShip);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mCreateFleetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Ship> newFleetArrayList= new ArrayList<>();
                for (Integer i : newFleetShips.keySet()){
                    newFleetArrayList.add(newFleetShips.get(i));
                }
                Fleet newFleet = new Fleet(newFleetArrayList, newAdmiral);

                //newFleet.setOwner(getIntent().getStringExtra(MESSAGE_1));

            }
        });
    }

    private void initList() {
        mAdmiralList = new ArrayList<>();
        mShipList = mStarShipDAO.getAllShips();
        mAdmiralList.add(new Admiral("Sisko", R.drawable.starfleetbadge));
        mAdmiralList.add(new Admiral("Dukat", R.drawable.starfleetbadge));
        mAdmiralList.add(new Admiral("Picard", R.drawable.starfleetbadge));
        mAdmiralList.add(new Admiral("Kirk", R.drawable.starfleetbadge));
        mAdmiralList.add(new Admiral("Chang", R.drawable.starfleetbadge));
    }

    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String username){
        Intent intent = new Intent (packageContext, LandingPageActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, username);
        return intent;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}