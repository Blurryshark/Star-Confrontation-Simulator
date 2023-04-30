package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.project2.DB.FleetDAO;
import com.example.project2.StarConfData.Fleet;

import java.util.List;

public class FleetVIewerActivity extends AppCompatActivity {

    private FleetVIewerActivity binding;

    TextView mTextView;
    Button mEditButton;
    Button mDeleteButton;

    FleetDAO mFleetDAO;

    List<Fleet> mFleetList;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";

    List<Fleet> fleets;
    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String username){
        Intent intent = new Intent (packageContext, LandingPageActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, username);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_viewer);


    }



    private void refreshDisplay() {
        mFleetList = mFleetDAO.getFleets();
        if (!mFleetList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Fleet log : mFleetList) {
                sb.append(log.toString());
            }
            mTextView.setText(sb.toString());
        } else {
            mTextView.setText(" ");
        }
    }
}