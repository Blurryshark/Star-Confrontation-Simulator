package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class FleetVIewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_viewer);
    }

    public static final String MESSAGE = "message1";
    public static final String MESSAGE_1 = "message2";
    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String username){
        Intent intent = new Intent (packageContext, LandingPage.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, username);
        return intent;
    }
}