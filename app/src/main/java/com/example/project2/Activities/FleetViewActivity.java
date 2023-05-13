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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project2.DB.AdmiralDAO;
import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.FleetsTableDAO;
import com.example.project2.DB.StarShipDAO;
import com.example.project2.DB.UserDAO;
import com.example.project2.DialogJunk.FleetDeleteConfirmationDialog;
import com.example.project2.R;
import com.example.project2.StarConfData.Admiral;
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
    private AdmiralDAO mAdmiralDAO;
    private StarShipDAO mStarShipDAO;

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

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.USER_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .UserDAO();
        mStarShipDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.SHIP_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .StarShipDAO();
        mFleetDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.FLEET_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .FleetDAO();
        mFleetsTableDAO = Room.databaseBuilder(this,AppDataBase.class,AppDataBase.FLEET_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().FleetsTableDAO();
        mAdmiralDAO = Room.databaseBuilder(this,AppDataBase.class,AppDataBase.ADMIRAL_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().AdmiralDAO();

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


        mFleetShipView.setMovementMethod(new ScrollingMovementMethod());

        mFleetNameView.setText(mFleet.getFleetName());
        mFleetOwnerView.setText("Owner: " + mFleetOwner.getUsername());
        mFleetAdmiralView.setText("Admiral: " + mAdmiralDAO.getAdmiralById(mFleet.getAdmiralId()).getAdmiralName());
        StringBuilder shipViewText = new StringBuilder();
        for (Integer num : mFleet.getFleet()){
            try {
                shipViewText.append(mStarShipDAO.getShipByLogId(num).toString());
                shipViewText.append("\n");
            } catch (NullPointerException e){
                Toast toast = Toast.makeText(getApplicationContext(), "Fleet does not exist!", Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                mFleetDAO.delete(mFleet);
                Intent intent = LandingPageActivity.intentFactory(getApplicationContext(), mIsAdmin,
                        mLoggedUser.getUsername());
                startActivity(intent);
            }
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
        mFleetDAO.delete(mFleet);
        Intent intent = FleetListActivity.intentFactory(getApplicationContext(),
                mIsAdmin,
                mLoggedUser.getUsername());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("addShip");
        item.setIcon(R.drawable.starfleetbadge);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = FleetListActivity.intentFactory(getApplicationContext(), mLoggedUser.isAdminStatus(),
                        getIntent().getStringExtra(MESSAGE_2));
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public static Intent intentFactory(Context packageContext, Boolean isAdmin, int fleetOwnerId,
                                       String loggedUser, int fleetId){
        Intent intent = new Intent (packageContext, FleetViewActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, fleetOwnerId);
        intent.putExtra(MESSAGE_2, loggedUser);
        intent.putExtra(MESSAGE_3, fleetId);
        return intent;
    }
}