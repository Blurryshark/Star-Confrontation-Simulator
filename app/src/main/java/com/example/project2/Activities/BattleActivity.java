package com.example.project2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.FleetsTableDAO;
import com.example.project2.DB.UserDAO;
import com.example.project2.R;
import com.example.project2.databinding.ActivityBattleBinding;

public class BattleActivity extends AppCompatActivity {

    ActivityBattleBinding mActivityBattleBinding;

    UserDAO mUserDAO;
    FleetDAO mFleetDAO;
    FleetsTableDAO mFleetsTableDAO;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";
    private static final String MESSAGE_2 = "message3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        mActivityBattleBinding = ActivityBattleBinding.inflate(getLayoutInflater());
        setContentView(mActivityBattleBinding.getRoot());

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

        mFleetDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.FLEET_TABLE)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .FleetDAO();

    }

    public static Intent intentFactory(Context packageContext, String username, int fleetOneId,
                                       int fleetTwoId){
        Intent intent = new Intent (packageContext, LandingPageActivity.class);
        intent.putExtra(MESSAGE, username);
        intent.putExtra(MESSAGE_1, fleetOneId);
        intent.putExtra(MESSAGE_2, fleetTwoId);
        return intent;
    }
}