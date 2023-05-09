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
import com.example.project2.DB.FleetsTableDAO;
import com.example.project2.DB.UserDAO;
import com.example.project2.DialogJunk.FleetDeleteConfirmationDialog;
import com.example.project2.R;
import com.example.project2.StarConfData.Fleet;
import com.example.project2.StarConfData.Ship;
import com.example.project2.StarConfData.User;
import com.example.project2.databinding.ActivityFleetViewBinding;

import java.util.List;

/*FINISHED ACTIVITY
 * .
 * .
 * .
 * .
 * .
 * .*/
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
    private FleetsTableDAO mFleetsTableDAO;

    Boolean mIsAdmin;
    int mFleetId;
    User mFleetOwner;
    User mLoggedUser;

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
        mFleet = mFleetDAO.getFleetById(mFleetId);
        mFleetOwner = mUserDAO.getUserByLogId(mFleet.getOwnerId());
        mLoggedUser = mUserDAO.getUserByUsername(getIntent().getStringExtra(MESSAGE_2));

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
        mFleetsTableDAO = Room.databaseBuilder(this,AppDataBase.class,AppDataBase.FLEET_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().FleetsTableDAO();

        mFleetShipView.setMovementMethod(new ScrollingMovementMethod());

        mFleetNameView.setText(mFleet.getFleetName());
        mFleetOwnerView.setText(mFleetOwner.getUsername());
        mFleetAdmiralView.setText(mFleetsTableDAO.getAdmiralFromFleet(mFleetId).getAdmiralName());
        StringBuilder shipViewText = new StringBuilder();
        List<Ship> ships = mFleetsTableDAO.getShipsByFleetId(mFleetId);
        for (Ship ship : ships){
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
        mFleetDAO.delete(mFleetDAO.getFleetById(mFleetId));
        Intent intent = FleetListActivity.intentFactory(getApplicationContext(),
                mIsAdmin,
                mLoggedUser.getUsername());
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