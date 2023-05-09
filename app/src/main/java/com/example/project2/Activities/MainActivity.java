package com.example.project2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
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

    List<User> mUserList;

    String defaultUsername = "user1";
    String defaultAdminName = "admin1";

    /*Default users*/
    User admin = new User(defaultAdminName, "admin2", true);
    User user = new User(defaultUsername, "User2", false);

    /*Default Starship options, may implement in a List later as the group of default ship entries grows.*/
    Ship galaxy = new Ship("galaxy_class", 6, 15, 12, 7, 15, this);
    Ship bird = new Ship("bird_of_prey", 12, 10, 18, 6, 10, this);
    Ship constitution = new Ship("constitution_class", 9, 12, 13, 12, 8, this);
    Ship warbird = new Ship("war_bird", 4, 18, 12, 10, 10, this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mUsername = binding.Username;
        mPassword = binding.Password;
        mSubmit = binding.Submit;
        mNewUser = binding.addUserButton;

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.USER_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
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