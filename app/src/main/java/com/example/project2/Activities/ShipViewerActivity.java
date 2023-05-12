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
import com.example.project2.DialogJunk.ShipDeleteConfirmationDialog;
import com.example.project2.Observer.PositionObserver;
import com.example.project2.R;
import com.example.project2.RecycleViewStuff.ShipRecyclerAdapter;
import com.example.project2.StarConfData.Fleet;
import com.example.project2.StarConfData.Ship;
import com.example.project2.databinding.ActivityShipViewerBinding;

import java.util.ArrayList;
import java.util.List;

/*FINISHED ACTIVITY
 * .
 * .
 * .
 * .
 * .
 * .*/
public class ShipViewerActivity extends AppCompatActivity implements
        ShipDeleteConfirmationDialog.ShipDeleteConfirmationDialogListener{

    ActivityShipViewerBinding mActivityShipViewerBinding;

    private RecyclerView mRecyclerView;
    private ShipRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private StarShipDAO mStarShipDAO;
    private FleetDAO mFleetDAO;
    private FleetsTableDAO mFleetsTableDAO;

    private List<Ship> mShipList;

    PositionObserver observer = new PositionObserver();

    private static final String MESSAGE = "message1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_viewer);

        mActivityShipViewerBinding = ActivityShipViewerBinding.inflate(getLayoutInflater());
        setContentView(mActivityShipViewerBinding.getRoot());

        mStarShipDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.SHIP_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .StarShipDAO();
        mFleetsTableDAO = Room.databaseBuilder(this,AppDataBase.class,AppDataBase.FLEETS_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().FleetsTableDAO();
        mFleetDAO = Room.databaseBuilder(this,AppDataBase.class,AppDataBase.FLEETS_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().FleetDAO();

        mShipList = mStarShipDAO.getAllShips();



        refreshRecyclerView();

        mAdapter.setOnItemClickListener(new ShipRecyclerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                observer.setPosition(position);
                openDialog();
            }
        });
    }

    public void openDialog() {
        ShipDeleteConfirmationDialog dialog = new ShipDeleteConfirmationDialog();
        dialog.show(getSupportFragmentManager(), "ShipDeleteAlertDialog");
    }

    @Override
    public void onYesClicked(){
        List<Fleet> fleetsToDelete = mFleetDAO.getFleets();
        for(Fleet fleet : fleetsToDelete){
            if(fleet.getFleet().contains(mShipList.get(observer.getPosition()).getShipLogId())){
                mFleetDAO.delete(fleet);
            }
        }
        mShipList.remove(observer.getPosition());
        mAdapter.notifyItemRemoved(observer.getPosition());
        mStarShipDAO.delete(mShipList.get(observer.getPosition()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("addShip");
        item.setIcon(R.drawable.starfleetbadge);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                Intent intent = AdministratorPageActivity.intentFactory(getApplicationContext(), true, getIntent().getStringExtra(MESSAGE));
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public static Intent intentFactory(Context context, String loggedUser){
        Intent intent = new Intent(context, ShipViewerActivity.class);
        intent.putExtra(MESSAGE, loggedUser);
        return intent;
    }

    public void refreshRecyclerView(){
        mRecyclerView = findViewById(R.id.fleetRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        ArrayList<Ship> tempList = new ArrayList<>();
        for(Ship ship : mShipList){
            tempList.add(ship);
        }
        mAdapter = new ShipRecyclerAdapter(tempList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}