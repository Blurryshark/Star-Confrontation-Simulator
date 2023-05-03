package com.example.project2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.project2.R;
import com.example.project2.databinding.ActivityAdministratorPageBinding;

public class AdministratorPageActivity extends AppCompatActivity {

    ActivityAdministratorPageBinding mAdminPageBinding;

    Button mUsersViewButton;
    Button mShipViewButton;

    public static final String MESSAGE = "message1";
    public static final String MESSAGE_1 = "message2";

//    TableLayout mTableLayout;
//    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator_page);

        mAdminPageBinding = ActivityAdministratorPageBinding.inflate(getLayoutInflater());
        setContentView(mAdminPageBinding.getRoot());

        mUsersViewButton = mAdminPageBinding.viewUsersButton;
        mShipViewButton = mAdminPageBinding.shipViewButton;

        mUsersViewButton.setOnClickListener(new View.OnClickListener() {
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