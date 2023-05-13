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
import com.example.project2.DB.UserDAO;
import com.example.project2.DialogJunk.FleetDeleteConfirmationDialog;
import com.example.project2.Observer.Observer;
import com.example.project2.Observer.PositionObserver;
import com.example.project2.R;
import com.example.project2.RecycleViewStuff.ShipRecyclerAdapter;
import com.example.project2.RecycleViewStuff.UserAdapterView;
import com.example.project2.StarConfData.Fleet;
import com.example.project2.StarConfData.User;
import com.example.project2.DialogJunk.UserDeleteConfirmationDialog;
import com.example.project2.databinding.ActivityUserListBinding;

import java.util.List;

/*FINISHED ACTIVITY
* .
* .
* .
* .
* .
* .*/
public class UserListActivity extends AppCompatActivity implements UserDeleteConfirmationDialog.UserDeleteConfirmationDialogListener{

    ActivityUserListBinding mActivityUserListBinding;

    private RecyclerView mRecyclerView;
    private UserAdapterView mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    UserDAO mUserDAO;
    FleetDAO mFleetDAO;

    PositionObserver mObserver;

    List<User> mUserList;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";

    Boolean isAdmin;
    String username;

    PositionObserver observer = new PositionObserver();

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
        mFleetDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.FLEET_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .FleetDAO();

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
                openDialog();
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
                Intent intent = AdministratorPageActivity.intentFactory(getApplicationContext(),
                        mUserDAO.getUserByUsername(getIntent().getStringExtra(MESSAGE_1)).isAdminStatus() ,
                        getIntent().getStringExtra(MESSAGE_1));
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    private List<User> getUsers(){
        return mUserDAO.getUser();
    }

    public void openDialog(){
        UserDeleteConfirmationDialog dialog = new UserDeleteConfirmationDialog();
        dialog.show(getSupportFragmentManager(), "DeleteAlertDialog");
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

    @Override
    public void onYesCLicked() {
        List<Integer> fleetsToDelete = mUserList.get(mObserver.getPosition()).getFleetIdList();
        for (Integer num : fleetsToDelete){
            mFleetDAO.delete(mFleetDAO.getFleetById(num));
        }
        mUserDAO.delete(mUserList.get(mObserver.getPosition()));
        Intent intent = AdministratorPageActivity.intentFactory(getApplicationContext(), getIntent().getBooleanExtra(MESSAGE, true),
                getIntent().getStringExtra(MESSAGE_1));
        startActivity(intent);
    }
}