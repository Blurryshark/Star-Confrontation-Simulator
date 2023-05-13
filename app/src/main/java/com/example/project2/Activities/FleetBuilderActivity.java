package com.example.project2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project2.DB.AdmiralDAO;
import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.FleetsTableDAO;
import com.example.project2.DB.StarShipDAO;
import com.example.project2.DB.UserDAO;
import com.example.project2.R;
import com.example.project2.RecycleViewStuff.AdmiralAdapter;
import com.example.project2.RecycleViewStuff.ShipAdapter;
import com.example.project2.StarConfData.Admiral;
import com.example.project2.StarConfData.Fleet;
import com.example.project2.StarConfData.Ship;
import com.example.project2.StarConfData.User;
import com.example.project2.databinding.ActivityFleetBuilderBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*FINISHED ACTIVITY
 * .
 * .
 * .
 * .
 * .
 * .*/
public class FleetBuilderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityFleetBuilderBinding mFleetBuilderBinding;

    private ArrayList<Admiral> mAdmiralList;
    private ArrayList<Ship> mShipList;
    private AdmiralAdapter mAdmiralAdapter;
    private ShipAdapter mShipOneAdapter;
    private ShipAdapter mShipTwoAdapter;
    private ShipAdapter mShipThreeAdapter;
    private HashMap<Integer, Ship> newFleetShips;
    private Admiral newAdmiral;

    User mLoggedUser;

    private StarShipDAO mStarShipDAO;
    private FleetDAO mFleetDAO;
    private UserDAO mUserDAO;
    private FleetsTableDAO mFleetsTableDAO;
    private AdmiralDAO mAdmiralDAO;

    Spinner mAdmiralSpinner;
    Spinner mShipOneSpinner;
    Spinner mShipTwoSpinner;
    Spinner mShipThreeSpinner;
    Button mCreateFleetButton;
    EditText mFleetName;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_builder);

        mFleetBuilderBinding = ActivityFleetBuilderBinding.inflate(getLayoutInflater());
        setContentView(mFleetBuilderBinding.getRoot());

        mStarShipDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.SHIP_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .StarShipDAO();
        mAdmiralDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.ADMIRAL_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .AdmiralDAO();
        mFleetDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.FLEET_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .FleetDAO();
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


        newFleetShips = new HashMap<>();

        mLoggedUser = mUserDAO.getUserByUsername(getIntent().getStringExtra(MESSAGE_1));

        initList();

        mAdmiralSpinner = mFleetBuilderBinding.admiralSpinner;
        mShipOneSpinner = mFleetBuilderBinding.shipOneSpinner;
        mShipTwoSpinner = mFleetBuilderBinding.shipTwoSpinner;
        mShipThreeSpinner = mFleetBuilderBinding.shipThreeSpinner;
        mCreateFleetButton = mFleetBuilderBinding.createFleetButton;
        mFleetName = mFleetBuilderBinding.fleetNameText;


        mAdmiralAdapter = new AdmiralAdapter(this, mAdmiralList);
        mShipOneAdapter = new ShipAdapter(this, mShipList);
        mShipTwoAdapter = new ShipAdapter(this, mShipList);
        mShipThreeAdapter = new ShipAdapter(this, mShipList);

        mAdmiralSpinner.setAdapter(mAdmiralAdapter);
        mShipOneSpinner.setAdapter(mShipOneAdapter);
        mShipTwoSpinner.setAdapter(mShipTwoAdapter);
        mShipThreeSpinner.setAdapter(mShipThreeAdapter);




        mAdmiralSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Admiral clickedAdmiral = (Admiral) adapterView.getItemAtPosition(position);
                newAdmiral = clickedAdmiral;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mShipOneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private final Integer SHIP_KEY = 1;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Ship clickedShip = (Ship) adapterView.getItemAtPosition(position);
                newFleetShips.put(SHIP_KEY, clickedShip);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mShipTwoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private final Integer SHIP_KEY = 2;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Ship clickedShip = (Ship) adapterView.getItemAtPosition(position);
                newFleetShips.put(SHIP_KEY, clickedShip);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mShipThreeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private final Integer SHIP_KEY = 3;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Ship clickedShip = (Ship) adapterView.getItemAtPosition(position);
                newFleetShips.put(SHIP_KEY, clickedShip);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mCreateFleetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> newFleetArrayList= new ArrayList<>();
                for (Integer i : newFleetShips.keySet()){
                    newFleetArrayList.add(newFleetShips.get(i).getShipLogId());
                }
                String newFleetName = mFleetName.getText().toString().trim();

                if (newFleetName.length() > 0 && !newFleetName.contains("\n")){
                    Fleet newFleet = new Fleet(newFleetArrayList, newAdmiral.getAdmiralId(), newFleetName,
                            mLoggedUser.mUserLogId, getApplicationContext());

                    Intent intent = LandingPageActivity.intentFactory(getApplicationContext(), getIntent().getBooleanExtra(MESSAGE,
                            false), mLoggedUser.getUsername());
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(),"invalid fleet name", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void initList() {
        List<Admiral> tempAdmiralList = new ArrayList<>();
        mAdmiralList = new ArrayList<>();
        tempAdmiralList = mAdmiralDAO.getAdmirals();
        for (Admiral admiral : tempAdmiralList){
            mAdmiralList.add(admiral);
        }
        List<Ship> tempList = new ArrayList<>();
        mShipList = new ArrayList<>();
        tempList = mStarShipDAO.getAllShips();
        for (Ship ships : tempList){
            mShipList.add(ships);
        }

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
                        getIntent().getStringExtra(MESSAGE_1));
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String username){
        Intent intent = new Intent (packageContext, FleetBuilderActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, username);
        return intent;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}