package com.example.project2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.UserDAO;
import com.example.project2.DialogJunk.FleetDeleteConfirmationDialog;
import com.example.project2.Observer.Observer;
import com.example.project2.Observer.PositionObserver;
import com.example.project2.R;
import com.example.project2.RecycleViewStuff.UserAdapterView;
import com.example.project2.StarConfData.User;
import com.example.project2.DialogJunk.UserDeleteConfirmationDialog;
import com.example.project2.databinding.ActivityUserListBinding;

import java.util.List;

public class UserListActivity extends AppCompatActivity implements FleetDeleteConfirmationDialog
        .FleetDeleteConfirmationDialogListener{

    ActivityUserListBinding mActivityUserListBinding;

    private RecyclerView mRecyclerView;
    private UserAdapterView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    UserDAO mUserDAO;

    PositionObserver mObserver;

    List<User> mUserList;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";

    Boolean isAdmin;
    String username;

    public static Intent intentFactory(Context context, Boolean isAdmin, String username){
        Intent intent = new Intent (context, UserListActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        mActivityUserListBinding = ActivityUserListBinding.inflate(getLayoutInflater());
        setContentView(mActivityUserListBinding.getRoot());

        mUserDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.USER_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .UserDAO();

        mUserList = getUsers();

        mObserver = new PositionObserver();

        mRecyclerView = findViewById(R.id.userRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new UserAdapterView(mUserList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        isAdmin = getIntent().getBooleanExtra(MESSAGE, false);
        username = getIntent().getStringExtra(MESSAGE_1);

        mAdapter.setOnItemClickListener(new UserAdapterView.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                /*OBSERVER PATTERN YEA BOIIIIII*/
                mObserver.setPosition(position);
                openDialog(mObserver.getPosition());
            }
        });
    }

    private List<User> getUsers(){
        return mUserDAO.getUser();
    }

    public void openDialog(int position){
        UserDeleteConfirmationDialog dialog = new UserDeleteConfirmationDialog();
        dialog.show(getSupportFragmentManager(), "DeleteAlertDialog");
    }
    @Override
    public void onYesClicked() {
        mUserDAO.delete(mUserList.get(mObserver.getPosition()));
        Intent intent = UserListActivity.intentFactory(getApplicationContext(), getIntent().getBooleanExtra(MESSAGE, true),
                getIntent().getStringExtra(MESSAGE_1));
    }

    public void addObserver (PositionObserver observer){
        mObserver = observer;
    }
    public int getPostion(){
        return mObserver.getPosition();
    }
    public void setPosition(int position){
        mObserver.setPosition(position);
    }
}