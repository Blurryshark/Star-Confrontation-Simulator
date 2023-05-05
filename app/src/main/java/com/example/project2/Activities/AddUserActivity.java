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
import com.example.project2.StarConfData.User;
import com.example.project2.databinding.ActivityAddUserBinding;

public class AddUserActivity extends AppCompatActivity {

    ActivityAddUserBinding mAddUserBinding;

    EditText mUsername;
    EditText mPassword;
    EditText mConfirmPassword;
    Button mButton;

    UserDAO mUserDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mAddUserBinding = ActivityAddUserBinding.inflate(getLayoutInflater());
        setContentView(mAddUserBinding.getRoot());

        mUsername = mAddUserBinding.Username;
        mPassword = mAddUserBinding.Password;
        mConfirmPassword = mAddUserBinding.PasswordConfirm;
        mButton = mAddUserBinding.button2;

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.USER_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .UserDAO();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = mPassword.getText().toString();
                String pass2 = mConfirmPassword.getText().toString();
                String username = mUsername.getText().toString();

                if(pass == pass2 && mUserDAO.getUserByUsername(username) == null){
                    User user = new User(username, pass, false);
                    mUserDAO.insert(user);
                    Intent intent = MainActivity.intentFactory(getApplicationContext());
                    startActivity(intent);
                } else {
                    mUsername.setText("");
                    mPassword.setText("");
                    mConfirmPassword.setText("");
                    /*implement a toast here later*/
                }
            }
        });
    }

    public static Intent intentFactory (Context context){
        Intent intent = new Intent (context, AddUserActivity.class);
        return intent;
    }
}