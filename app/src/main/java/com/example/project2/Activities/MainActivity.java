package com.example.project2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project2.DB.AdmiralDAO;
import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.FleetDAO;
import com.example.project2.DB.StarShipDAO;
import com.example.project2.DB.UserDAO;
import com.example.project2.R;
import com.example.project2.StarConfData.Admiral;
import com.example.project2.StarConfData.Ship;
import com.example.project2.StarConfData.User;
import com.example.project2.databinding.ActivityMainBinding;

import java.util.List;

/*FINISHED ACTIVITY
 * .
 * .
 * .
 * .
 * .
 * .*/
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    EditText mUsername;
    EditText mPassword;

    Button mSubmit;
    Button mNewUser;

    UserDAO mUserDAO;
    FleetDAO mFleetDAO;
    StarShipDAO mStarShipDAO;
    AdmiralDAO mAdmiralDAO;

    String defaultUsername = "user1";
    String defaultAdminName = "admin1";

    /*Default users*/
    User admin;
    User user;

    Ship galaxy;
    Ship bird;
    Ship constitution;
    Ship warbird;

    Admiral sisko;
    Admiral kirk;
    Admiral chang;
    Admiral dukat;
    Admiral picard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.USER_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .UserDAO();
        mFleetDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.FLEET_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().FleetDAO();
        mStarShipDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.SHIP_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().StarShipDAO();
        mAdmiralDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.ADMIRAL_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().AdmiralDAO();

        List<Ship> allShips = mStarShipDAO.getAllShips();
        List<Admiral> allAdmirals = mAdmiralDAO.getAdmirals();
        /*Default Starship options, may implement in a List later as the group of default ship entries grows.*/
        if(allShips.size() < 1) {
            galaxy = new Ship("galaxy_class", 6, 15, 12, 7, 15, getApplicationContext());
            bird = new Ship("bird_of_prey", 12, 10, 18, 6, 10, getApplicationContext());
            constitution = new Ship("constitution_class", 9, 12, 13, 12, 8, getApplicationContext());
            warbird = new Ship("war_bird", 4, 18, 12, 10, 10, getApplicationContext());
        }
        if (mAdmiralDAO.getAdmirals().size() < 1) {
            sisko = new Admiral("Sisko", R.drawable.starfleetbadge, getApplicationContext());
            kirk = new Admiral("Kirk", R.drawable.starfleetbadge, getApplicationContext());
            chang = new Admiral("Chang", R.drawable.starfleetbadge, getApplicationContext());
            dukat = new Admiral("Dukat", R.drawable.starfleetbadge, getApplicationContext());
            picard = new Admiral("Picard", R.drawable.starfleetbadge, getApplicationContext());
        }
        if (mUserDAO.getUser().size() < 1) {
            user = new User(defaultUsername, "User2", false);
            admin = new User(defaultAdminName, "admin2", true);
            mUserDAO.insert(user,admin);
        }
        mUsername = binding.Username;
        mPassword = binding.Password;
        mSubmit = binding.Submit;
        mNewUser = binding.addUserButton;

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                User user = mUserDAO.getUserByUsername(username);

                if(user != null){
                    if(password.equals(user.getPassword())){
                        Intent intent = LandingPageActivity.intentFactory(getApplicationContext(),
                                mUserDAO.getUserByUsername(username).isAdminStatus(), username);
                        startActivity(intent);
                    }
                } else {
                    System.out.println("suck my balls"); //not final
                }
            }
        });
        mNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddUserActivity.intentFactory(getApplicationContext());
                startActivity(intent);

            }
        });


    }
    public static Intent intentFactory (Context context){
        Intent intent = new Intent (context, AddUserActivity.class);
        return intent;
    }

    void addUser(User newUser){
        if (mUserDAO.getUserByUsername(newUser.getUsername()) == null) {
            mUserDAO.insert(newUser);
        }
    }
}