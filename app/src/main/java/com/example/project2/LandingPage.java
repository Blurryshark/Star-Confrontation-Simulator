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

    public static final String MESSAGE = "com.example.project2";
    public static Intent intentFactory(Context packageContext, Boolean admin){
        Intent intent = new Intent (packageContext, LandingPage.class);
        intent.putExtra(MESSAGE, admin);

        return intent;
    }

    //Boolean logName = getIntent().getBooleanExtra(MESSAGE, true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        mLandingPageBinding= ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(mLandingPageBinding.getRoot());

        Boolean adminStatus = getIntent().getBooleanExtra(MESSAGE,true);

        if (adminStatus){
            mButton.setVisibility(View.VISIBLE);
        }
        
    }


}