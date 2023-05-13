package com.example.project2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.FleetsTableDAO;
import com.example.project2.DB.UserDAO;
import com.example.project2.R;
import com.example.project2.RecycleViewStuff.FleetSelectAdapter;
import com.example.project2.StarConfData.Fleet;
import com.example.project2.StarConfData.User;
import com.example.project2.databinding.ActivityFleetSelectBinding;

import java.util.ArrayList;
import java.util.List;


public class FleetSelectActivity extends AppCompatActivity {

    ActivityFleetSelectBinding mActivityFleetSelectBinding;

    private User mCurrentUser;
    private Fleet mSelectedFleetOne;
    private Fleet mSelectedFleetTwo;
    private ArrayList<Fleet> mFleetArrayList;
    private FleetSelectAdapter mFleetOneAdapter;
    private FleetSelectAdapter mFleetTwoAdapter;


    private Button mBattleButton;
    private Spinner mFleetOneSpinner;
    private Spinner mFleetTwoSpinner;

    private UserDAO mUserDAO;
    private FleetDAO mFleetDAO;
    private FleetsTableDAO mFleetsTableDAO;

    private User mLoggedUser;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";

    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String username) {
        Intent intent = new Intent(packageContext, FleetSelectActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_select);

        mActivityFleetSelectBinding = ActivityFleetSelectBinding.inflate(getLayoutInflater());
        setContentView(mActivityFleetSelectBinding.getRoot());


        mUserDAO = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, AppDataBase.USER_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .UserDAO();
        mFleetDAO = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, AppDataBase.FLEET_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .FleetDAO();
        mFleetsTableDAO = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, AppDataBase.FLEETS_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .FleetsTableDAO();

        String username = getIntent().getStringExtra(MESSAGE_1);
        mLoggedUser = mUserDAO.getUserByUsername(username);

        if(mLoggedUser == null){
            System.out.println("FUCK");
            Intent intent = LandingPageActivity.intentFactory(getApplicationContext(), getIntent().getBooleanExtra(MESSAGE, false),
                    getIntent().getStringExtra(MESSAGE_1));
            startActivity(intent);
        }

        initList();

        mBattleButton = mActivityFleetSelectBinding.battleButton;
        mFleetOneSpinner = mActivityFleetSelectBinding.fleetOneSpinner;
        mFleetTwoSpinner = mActivityFleetSelectBinding.fleetTwoSpinner;

        mFleetOneAdapter = new FleetSelectAdapter(this, mFleetArrayList);
        mFleetTwoAdapter = new FleetSelectAdapter(this, mFleetArrayList);
        mFleetOneSpinner.setAdapter(mFleetOneAdapter);
        mFleetTwoSpinner.setAdapter(mFleetTwoAdapter);

        mFleetOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Fleet clickedFleet = (Fleet) adapterView.getItemAtPosition(position);
                mSelectedFleetOne = clickedFleet;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mFleetTwoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Fleet clickedFleet = (Fleet) adapterView.getItemAtPosition(position);
                mSelectedFleetTwo = clickedFleet;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = BattleActivity.intentFactory(getApplicationContext(), mLoggedUser.getUsername(),
                        mSelectedFleetOne.getFleetId(), mSelectedFleetTwo.getFleetId());
                startActivity(intent);
            }
        });
        
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("addShip");
        item.setIcon(R.drawable.starfleetbadge);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = LandingPageActivity.intentFactory(getApplicationContext(), mLoggedUser.isAdminStatus(),
                        mLoggedUser.getUsername());
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void initList() {
        List<Fleet> tempList;
        if(mLoggedUser.isAdminStatus()){
            tempList = mFleetDAO.getFleets();
        } else {
            tempList = mFleetDAO.getAllByOwner(mLoggedUser.getUserLogId());
        }
        mFleetArrayList = new ArrayList<>();
        for (Fleet fleet : tempList){
            mFleetArrayList.add(fleet);
        }
    }
}