package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project2.DB.UserDAO;
import com.example.project2.databinding.ActivityLandingPageBinding;
import com.example.project2.databinding.ActivityMainBinding;

public class LandingPage extends AppCompatActivity {

    ActivityLandingPageBinding mLandingPageBinding;

    Button mButton;
    Button mViewFleetButton;
    Button mBuildFleetButton;
    Button mBattleButton;

    public static final String MESSAGE = "message1";
    public static final String MESSAGE_1 = "message2";
    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String username){
        Intent intent = new Intent (packageContext, LandingPage.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        mLandingPageBinding= ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(mLandingPageBinding.getRoot());

        Boolean adminStatus = getIntent().getBooleanExtra(MESSAGE,true);
        String username = getIntent().getStringExtra(MESSAGE_1);

        mButton = mLandingPageBinding.button;
        mViewFleetButton = mLandingPageBinding.viewFleetButton;
        mBuildFleetButton = mLandingPageBinding.buildFleetButton;
        mBattleButton = mLandingPageBinding.battleButton;

        if (adminStatus){
            mButton.setVisibility(View.VISIBLE);
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mViewFleetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = FleetVIewer.intentFactory(getApplicationContext(),
                        adminStatus, username);
            }
        });
        mBuildFleetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


}