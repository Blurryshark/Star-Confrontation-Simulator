package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class FleetViewActivity extends AppCompatActivity {

    private FleetViewActivity mFleetViewActivityBinding;

    private Button mDeleteButton;
    private Button mEditButton;
    private TextView mFleetInfo;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";
    private static final String MESSAGE_2 = "message3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_view);


    }

    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String fleetOwner,
                                       String loggedUser){
        Intent intent = new Intent (packageContext, FleetViewActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, fleetOwner);
        intent.putExtra(MESSAGE_2, loggedUser);
        return intent;
    }
}