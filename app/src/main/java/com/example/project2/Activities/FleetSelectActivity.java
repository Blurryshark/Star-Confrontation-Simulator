package com.example.project2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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


    Button mBattleButton;
    Spinner mFleetOneSpinner;
    Spinner mFleetTwoSpinner;

    UserDAO mUserDAO;
    FleetsTableDAO mFleetsTableDAO;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";

    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String username) {
        Intent intent = new Intent(packageContext, LandingPageActivity.class);
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

        initList();

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.USER_TABLE)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .UserDAO();

        mFleetsTableDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.FLEETS_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .FleetsTableDAO();

        mCurrentUser = mUserDAO.getUserByUsername(getIntent().getStringExtra(MESSAGE_1));

        mBattleButton = mActivityFleetSelectBinding.battleButton;
        mFleetOneSpinner = mActivityFleetSelectBinding.fleetOneSpinner;
        mFleetTwoSpinner = mActivityFleetSelectBinding.fleetTwoSpinner;

        mFleetOneAdapter = new FleetSelectAdapter(this, mFleetArrayList);
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
                Intent intent = BattleActivity.intentFactory(getApplicationContext(), mCurrentUser.getUsername(),
                        mSelectedFleetOne.getFleetId(), mSelectedFleetTwo.getFleetId());
                startActivity(intent);
            }
        });
        
    }

    private void initList() {
        List<Fleet> tempList = mFleetsTableDAO.getFleetFromUser(mCurrentUser.mUserLogId);
        mFleetArrayList = new ArrayList<>();
        for (Fleet fleet : tempList){
            mFleetArrayList.add(fleet);
        }
    }
}