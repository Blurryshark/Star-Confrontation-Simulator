package com.example.project2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project2.R;
import com.example.project2.databinding.ActivityLandingPageBinding;

/*FINISHED ACTIVITY
 * .
 * .
 * .
 * .
 * .
 * .*/
public class LandingPageActivity extends AppCompatActivity {

    ActivityLandingPageBinding mLandingPageBinding;

    Button mButton;
    Button mViewFleetButton;
    Button mBuildFleetButton;
    Button mBattleButton;
    TextView mWelcomeText;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";
    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String username){
        Intent intent = new Intent (packageContext, LandingPageActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, username);
        return intent;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        mLandingPageBinding= ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(mLandingPageBinding.getRoot());

        Boolean adminStatus = getIntent().getBooleanExtra(MESSAGE,true);
        String username = getIntent().getStringExtra(MESSAGE_1);

        /*Wiring up ma buttons*/
        mButton = mLandingPageBinding.button;
        mViewFleetButton = mLandingPageBinding.viewFleetButton;
        mBuildFleetButton = mLandingPageBinding.buildFleetButton;
        mBattleButton = mLandingPageBinding.battleButton;
        mWelcomeText = mLandingPageBinding.WelcomeText;

        /*Setting welcome text to be consistent with username and determining if admin options are
        * available to the given user
        */
        if (adminStatus){
            mButton.setVisibility(View.VISIBLE);
        }
        mWelcomeText.setText("Welcome " + username + "!");

        /*Click listeners!*/
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adminStatus){
                    Intent intent = AdministratorPageActivity.intentFactory(getApplicationContext(),
                            adminStatus, username);
                    startActivity(intent);
                }
            }
        });
        mViewFleetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = FleetListActivity.intentFactory(getApplicationContext(),
                        adminStatus, username);
                startActivity(intent);
            }
        });
        mBuildFleetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = FleetBuilderActivity.intentFactory(getApplicationContext(),
                        adminStatus, username);
                startActivity(intent);
            }
        });
        mBattleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = FleetSelectActivity.intentFactory(getApplicationContext(),
                        adminStatus, username);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("addShip");
        item.setIcon(R.drawable.starfleetbadge);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = MainActivity.intentFactory(getApplicationContext());
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}