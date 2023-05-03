package com.example.project2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.UserDAO;
import com.example.project2.R;
import com.example.project2.databinding.ActivityFleetSelectBinding;

public class FleetSelectActivity extends AppCompatActivity {

    ActivityFleetSelectBinding mActivityFleetSelectBinding;

    Button mBattleButton;
    Spinner mFleetOneSpinner;
    Spinner mFleetTwoSpinner;

    UserDAO mUserDAO;

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

        mBattleButton = mActivityFleetSelectBinding.battleButton;
        mFleetOneSpinner = mActivityFleetSelectBinding.fleetOneSpinner;
        mFleetTwoSpinner = mActivityFleetSelectBinding.fleetTwoSpinner;
    }
}