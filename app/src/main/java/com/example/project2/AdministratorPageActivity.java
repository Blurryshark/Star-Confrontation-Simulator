package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.project2.databinding.ActivityAdministratorPageBinding;

public class AdministratorPageActivity extends AppCompatActivity {

    ActivityAdministratorPageBinding mAdminPageBinding;

    Button mFleetViewButton;
    Button mShipViewButton;

    public static final String MESSAGE = "message1";
    public static final String MESSAGE_1 = "message2";

    TableLayout mTableLayout;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_page);

        mAdminPageBinding = ActivityAdministratorPageBinding.inflate(getLayoutInflater());
        setContentView(mAdminPageBinding.getRoot());

        mFleetViewButton = mAdminPageBinding.fleetViewButton;
        mShipViewButton = mAdminPageBinding.shipViewButton;

        mFleetViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mShipViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String username){
        Intent intent = new Intent (packageContext, AdministratorPageActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, username);
        return intent;
    }

}