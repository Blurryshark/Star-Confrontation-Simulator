package com.example.project2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.UserDAO;
import com.example.project2.R;
import com.example.project2.StarConfData.Ship;
import com.example.project2.StarConfData.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    EditText mUsername;
    EditText mPassword;

    Button mSubmit;

    UserDAO mUserDAO;

    List<User> mUserList;

    String defaultUsername = "user1";
    String defaultAdminName = "admin1";

    /*Default users*/
    User admin = new User(defaultAdminName, "admin2", true);
    User user = new User(defaultUsername, "User2", false);

    /*Default Starship options, may implement in a List later as the group of default ship entries grows.*/
    Ship galaxy = new Ship("Galaxy-Class", 6, 15, 12, 7, 15, this);
    Ship bird = new Ship("BirdOfPrey", 12, 10, 18, 6, 10, this);
    Ship constitution = new Ship("Constitution-Class", 9, 12, 13, 12, 8, this);
    Ship warbird = new Ship("WarBird", 4, 18, 12, 10, 10, this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mUsername = binding.Username;
        mPassword = binding.Password;
        mSubmit = binding.Submit;

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.USER_DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();

        if(mUserDAO.getUserByUsername(defaultUsername) != null){
            addUser(user);
        }
        if(mUserDAO.getUserByUsername(defaultAdminName) != null){
            addUser(admin);
        }

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


    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mUserDAO.delete(admin);
        mUserDAO.delete(user);
        Ship.deleteShips(this, galaxy, bird, constitution, warbird);
    }

    void addUser(User newUser){
        if (mUserDAO.getUserByUsername(newUser.getUsername()) != null) {
            mUserDAO.insert(newUser);
        }
    }

}