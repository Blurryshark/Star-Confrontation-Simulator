package com.example.project2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.FleetsTableDAO;
import com.example.project2.DB.UserDAO;
import com.example.project2.R;
import com.example.project2.StarConfData.Battle;
import com.example.project2.StarConfData.Fleet;
import com.example.project2.StarConfData.User;
import com.example.project2.databinding.ActivityBattleBinding;

public class BattleActivity extends AppCompatActivity {

    ActivityBattleBinding mActivityBattleBinding;
    TextView mBattleText;

    UserDAO mUserDAO;
    FleetDAO mFleetDAO;
    FleetsTableDAO mFleetsTableDAO;

    User mLoggedUser;
    Fleet mBattleFleetOne;
    Fleet mBattleFleetTwo;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";
    private static final String MESSAGE_2 = "message3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        mActivityBattleBinding = ActivityBattleBinding.inflate(getLayoutInflater());
        setContentView(mActivityBattleBinding.getRoot());

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.USER_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .UserDAO();

        mFleetsTableDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.FLEETS_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .FleetsTableDAO();

        mFleetDAO = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, AppDataBase.FLEET_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .FleetDAO();

        mBattleText = mActivityBattleBinding.battleDisplay;
        mBattleText.setMovementMethod(new ScrollingMovementMethod());

        int fleetIdOne = getIntent().getIntExtra(MESSAGE_1,50);
        int fleetIdTwo = getIntent().getIntExtra(MESSAGE_2,50);
        mLoggedUser = mUserDAO.getUserByUsername(getIntent().getStringExtra(MESSAGE));
        mBattleFleetOne = mFleetDAO.getFleetById(fleetIdOne);
        mBattleFleetTwo = mFleetDAO.getFleetById(fleetIdTwo);

        Battle battle = new Battle(mLoggedUser, mBattleFleetOne, mBattleFleetTwo, getApplicationContext());
        mBattleText.setText(Battle.Fight(battle, battle.getBattleFleetOne(), battle.getBattleFleetTwo()).toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("addShip");
        item.setIcon(R.drawable.starfleetbadge);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = FleetSelectActivity.intentFactory(getApplicationContext(), mLoggedUser.isAdminStatus(),
                        mLoggedUser.getUsername());
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public static Intent intentFactory(Context packageContext, String username, int fleetOneId,
                                       int fleetTwoId){
        Intent intent = new Intent (packageContext, BattleActivity.class);
        intent.putExtra(MESSAGE, username);
        intent.putExtra(MESSAGE_1, fleetOneId);
        intent.putExtra(MESSAGE_2, fleetTwoId);
        return intent;
    }
}