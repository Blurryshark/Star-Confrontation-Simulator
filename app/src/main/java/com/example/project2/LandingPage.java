package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.project2.DB.UserDAO;

public class LandingPage extends AppCompatActivity {

    public static final String MESSAGE = "com.example.project2";
    public static Intent intentFactory(Context packageContext, Boolean admin){
        Intent intent = new Intent (packageContext, LandingPage.class);
        intent.putExtra(MESSAGE, admin);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        Boolean adminStatus = getIntent().getBooleanExtra(MESSAGE,true);
        
    }

    String logName = getIntent().getStringExtra(MESSAGE);

}