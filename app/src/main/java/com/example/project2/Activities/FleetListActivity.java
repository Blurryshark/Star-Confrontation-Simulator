package com.example.project2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.FleetsTableDAO;
import com.example.project2.DB.StarShipDAO;
import com.example.project2.DB.UserDAO;
import com.example.project2.R;
import com.example.project2.RecycleViewStuff.FleetViewAdapater;
import com.example.project2.StarConfData.Fleet;
import com.example.project2.StarConfData.User;
import com.example.project2.databinding.ActivityFleetListBinding;

import java.util.ArrayList;
import java.util.List;

/*FINISHED ACTIVITY
 * .
 * .
 * .
 * .
 * .
 * .*/
public class FleetListActivity extends AppCompatActivity {

    ActivityFleetListBinding mFleetListActivityBinding;

    private RecyclerView mRecyclerView;
    private FleetViewAdapater mAdapater;
    private RecyclerView.LayoutManager mLayoutManager;

    FleetDAO mFleetDAO;
    FleetsTableDAO mFleetsTableDAO;
    UserDAO mUserDAO;
    StarShipDAO mStarShipDAO;

    List<Fleet> mFleetList;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";

    Boolean isAdmin;
    User mLoggedUser;
    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String username){
        Intent intent = new Intent (packageContext, FleetListActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_list);

        mFleetDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.FLEET_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .FleetDAO();
        mFleetsTableDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.FLEETS_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().FleetsTableDAO();
        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.USER_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().UserDAO();
        mStarShipDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.SHIP_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().StarShipDAO();

        isAdmin = getIntent().getBooleanExtra(MESSAGE, true);
        mLoggedUser = mUserDAO.getUserByUsername(getIntent().getStringExtra(MESSAGE_1));

        mFleetList = getFleets(isAdmin, mLoggedUser);
        checkFleets();
        mFleetList = getFleets(isAdmin, mLoggedUser);

        mRecyclerView = findViewById(R.id.fleetRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        ArrayList<Fleet> fleetsViewList = new ArrayList<>();
        for (Fleet fleet : mFleetList){
            fleetsViewList.add(fleet);
        }
        mAdapater = new FleetViewAdapater(fleetsViewList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapater);

        /*This is an onClickListener of the adapter of the recycler view. Essentially, when a button
        * in the recycler view is clicked, it will send the user to the fleetViewerActivity, and the
        * intent will pass the admin status, the name of the owner of the fleet associated with the
        * pressed button, and the name of the logged user, and the ID of the selected fleet in that
        * order. The name of the logged user shouldn't be different from the owner of the fleet
        * UNLESS the logged user is an admin accessing the fleet of another user.*/
        mAdapater.setOnItemClickListener(new FleetViewAdapater.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mFleetList.get(position);
                Intent intent = FleetViewActivity.intentFactory(getApplicationContext(),
                        isAdmin,
                        mAdapater.getFleetArrayList().get(position).getOwnerId(),
                        mLoggedUser.getUsername(),
                        mAdapater.getFleetArrayList().get(position).getLogId());
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
                Intent intent = LandingPageActivity.intentFactory(getApplicationContext(), mLoggedUser.isAdminStatus(),
                        mLoggedUser.getUsername());
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private List<Fleet> getFleets(Boolean isAdmin, User loggedUser){
        if(isAdmin){
            return mFleetDAO.getFleets();
        } else {
            return mFleetDAO.getAllByOwner(loggedUser.getUserLogId());
        }
    }
    private void checkFleets(){
        for (Fleet fleet : mFleetList){
            if(mUserDAO.getUserByLogId(fleet.getOwnerId()) == null){
                mFleetDAO.delete(fleet);
            }
            for (int i = 0; i < fleet.getFleet().size(); i++){
                if(mStarShipDAO.getShipByLogId(fleet.getFleet().get(i)) == null){
                    mFleetDAO.delete(fleet);
                }

            }
        }

    }
}