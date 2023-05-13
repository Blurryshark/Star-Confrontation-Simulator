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

import com.example.project2.DB.AppDataBase;
import com.example.project2.DB.StarShipDAO;
import com.example.project2.DB.UserDAO;
import com.example.project2.R;
import com.example.project2.StarConfData.Ship;
import com.example.project2.databinding.ActivityAddShipBinding;
import com.example.project2.databinding.ActivityUserListBinding;

/*FINISHED ACTIVITY
 * .
 * .
 * .
 * .
 * .
 * .*/
public class AddShipActivity extends AppCompatActivity {

    ActivityAddShipBinding mActivityAddShipBinding;

    Button mSubmitButton;
    Button mCancelButton;
    EditText mShipClassText;
    EditText mAgiText;
    EditText mDefText;
    EditText mStrText;
    EditText mShieldsText;
    EditText mHullText;

    StarShipDAO mStarShipDAO;
    UserDAO mUserDAO;

    private static final String MESSAGE = "message1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ship);

        mActivityAddShipBinding = ActivityAddShipBinding.inflate(getLayoutInflater());
        setContentView(mActivityAddShipBinding.getRoot());

        mStarShipDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.SHIP_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
                .StarShipDAO();
        mUserDAO =Room.databaseBuilder(this, AppDataBase.class, AppDataBase.USER_DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build().UserDAO();

        mSubmitButton = mActivityAddShipBinding.confirmButton;
        mCancelButton = mActivityAddShipBinding.cancelButton;

        mShipClassText = mActivityAddShipBinding.classView;
        mAgiText = mActivityAddShipBinding.agiView;
        mDefText = mActivityAddShipBinding.defView;
        mStrText = mActivityAddShipBinding.strView;
        mShieldsText = mActivityAddShipBinding.shieldsText;
        mHullText = mActivityAddShipBinding.hullText;

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mShipClass = mShipClassText.getText().toString();
                int mNewAgi = Integer.parseInt(mAgiText.getText().toString());
                int mNewDef = Integer.parseInt(mDefText.getText().toString());
                int mNewStr = Integer.parseInt(mStrText.getText().toString());
                int mNewShields = Integer.parseInt(mShieldsText.getText().toString());
                int mNewHull = Integer.parseInt(mHullText.getText().toString());

                if (mNewAgi > 0 && mNewDef > 0 && mNewStr > 0 && mNewShields > 0 && mNewHull > 0){
                    Ship newShip = new Ship(mShipClass, mNewAgi, mNewDef, mNewStr, mNewShields, mNewHull,
                            getApplicationContext());
                }
                Intent intent = ShipViewerActivity.intentFactory(getApplicationContext(), getIntent()
                        .getStringExtra(MESSAGE));
                startActivity(intent);
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ShipViewerActivity.intentFactory(getApplicationContext(), getIntent()
                        .getStringExtra(MESSAGE));
                startActivity(intent);
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
                        mUserDAO.getUserByUsername(getIntent().getStringExtra(MESSAGE)).isAdminStatus(),
                        getIntent().getStringExtra(MESSAGE));
                startActivity(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public static Intent intentFactory(Context context, String loggedUser){
        Intent intent = new Intent(context, AddShipActivity.class);
        intent.putExtra(MESSAGE, loggedUser);
        return intent;
    }
}