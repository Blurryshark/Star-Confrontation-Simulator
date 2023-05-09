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
import com.example.project2.DB.StarShipDAO;
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
                int mNewAgi = Integer.getInteger(mAgiText.getText().toString());
                int mNewDef = Integer.getInteger(mDefText.getText().toString());
                int mNewStr = Integer.getInteger(mStrText.getText().toString());
                int mNewShields = Integer.getInteger(mShieldsText.getText().toString());
                int mNewHull = Integer.getInteger(mHullText.getText().toString());

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

    public static Intent intentFactory(Context context, String loggedUser){
        Intent intent = new Intent(context, ShipViewerActivity.class);
        intent.putExtra(MESSAGE, loggedUser);
        return intent;
    }
}