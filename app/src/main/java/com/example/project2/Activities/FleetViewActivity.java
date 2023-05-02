package com.example.project2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.UserDAO;
import com.example.project2.FleetDeleteConfirmationDialog;
import com.example.project2.R;
import com.example.project2.StarConfData.Fleet;
import com.example.project2.StarConfData.Ship;
import com.example.project2.databinding.ActivityFleetViewBinding;

public class FleetViewActivity extends AppCompatActivity implements
        FleetDeleteConfirmationDialog.FleetDeleteConfirmationDialogListener {

    ActivityFleetViewBinding mFleetViewActivityBinding;

    private Button mDeleteButton;
    private TextView mFleetNameView;
    private TextView mFleetOwnerView;
    private TextView mFleetAdmiralView;
    private TextView mFleetShipView;

    private UserDAO mUserDAO;
    private FleetDAO mFleetDAO;

    Boolean mIsAdmin;
    int mFleetId;
    String mFleetOwner;
    String mLoggedUser;

    private Fleet mFleet;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";
    private static final String MESSAGE_2 = "message3";
    private static final String MESSAGE_3 = "message4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_view);

        mFleetViewActivityBinding = ActivityFleetViewBinding.inflate(getLayoutInflater());
        setContentView(mFleetViewActivityBinding.getRoot());

        mIsAdmin = getIntent().getBooleanExtra(MESSAGE, true);
        mFleetId = getIntent().getIntExtra(MESSAGE_3, 0);
        mFleetOwner = getIntent().getStringExtra(MESSAGE_1);
        mLoggedUser = getIntent().getStringExtra(MESSAGE_2);

        mDeleteButton = mFleetViewActivityBinding.DeleteButton;
        mFleetNameView = mFleetViewActivityBinding.FleetNameView;
        mFleetOwnerView = mFleetViewActivityBinding.FleetOwnerView;
        mFleetAdmiralView = mFleetViewActivityBinding.fleetAdmiralView;
        mFleetShipView = mFleetViewActivityBinding.FleetShipView;

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.USER_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .UserDAO();
        mFleetDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.FLEET_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .FleetDAO();

        mFleet = mFleetDAO.getFleetById(mFleetId);
        mFleetShipView.setMovementMethod(new ScrollingMovementMethod());

        mFleetNameView.setText(mFleet.getFleetName());
        mFleetOwnerView.setText(mFleet.getOwner().getUsername());
        mFleetAdmiralView.setText(mFleet.getAdmiral().getAdmiralId());
        StringBuilder shipViewText = new StringBuilder();
        for (Ship ship : mFleet.getFleet()){
            shipViewText.append(ship.toString());
        }
        mFleetShipView.setText(shipViewText.toString());

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        FleetDeleteConfirmationDialog dialog = new FleetDeleteConfirmationDialog();
        dialog.show(getSupportFragmentManager(), "DeleteAlertDialog");
    }

    @Override
    public void onYesClicked(){
        Intent intent = FleetListActivity.intentFactory(getApplicationContext(),
                mIsAdmin,
                mLoggedUser);
    }

    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String fleetOwner,
                                       String loggedUser, int fleetId){
        Intent intent = new Intent (packageContext, FleetViewActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, fleetOwner);
        intent.putExtra(MESSAGE_2, loggedUser);
        intent.putExtra(MESSAGE_3, fleetId);
        return intent;
    }
}