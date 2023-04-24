package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Entity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.UserDAO;
import com.example.project2.databinding.ActivityMainBinding;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    EditText mUsername;
    EditText mPassword;

    Button mSubmit;

    UserDAO mUserDAO;

    List<User> mUserList;

    User admin = new User("admin2", "admin2", true);
    User user = new User("User1", "User2", false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mUsername = binding.Username;
        mPassword = binding.Password;
        mSubmit = binding.Submit;

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();

        addUser(admin);
        addUser(user);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsername.getText().toString();
                String password = mPassword.getText().toString();

                User user = mUserDAO.getUserByUsername(username);

                if(user != null){
                    if(password.equals(user.getPassword())){
                        Intent intent = LandingPage.intentFactory(getApplicationContext(),
                                mUserDAO.getUserByUsername(username).isAdminStatus());
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
    }

    void addUser(User newUser){
        mUserDAO.insert(newUser);
    }

}