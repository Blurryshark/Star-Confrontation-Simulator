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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.UserDAO;
import com.example.project2.R;
import com.example.project2.StarConfData.User;
import com.example.project2.databinding.ActivityAddUserBinding;

import java.util.List;

/*FINISHED ACTIVITY
 * .
 * .
 * .
 * .
 * .
 * .*/
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

                User userExists = mUserDAO.getUserByUsername(username);
                if(userExists == null){
                    if(pass.equals(pass2)){
                        User user = new User(username, pass, false);
                        mUserDAO.insert(user);
                        Intent intent = MainActivity.intentFactory(getApplicationContext());
                        startActivity(intent);
                    } else {
                        mUsername.setText("");
                        mPassword.setText("");
                        mConfirmPassword.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "password must match!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Username taken!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
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
                Intent intent = MainActivity.intentFactory(getApplicationContext());
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public static Intent intentFactory (Context context){
        Intent intent = new Intent (context, AddUserActivity.class);
        return intent;
    }
}