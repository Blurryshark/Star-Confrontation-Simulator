package com.example.project2.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.project2.R;
import com.example.project2.RecycleViewStuff.AdmiralAdapter;
import com.example.project2.StarConfData.Admiral;
import com.example.project2.databinding.ActivityFleetBuilderBinding;

import java.util.ArrayList;

public class FleetBuilderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityFleetBuilderBinding mFleetBuilderBinding;

    private ArrayList<Admiral> mAdmiralList;
    private AdmiralAdapter mAdmiralAdapter;

    Spinner mSpinner;

    private static final String MESSAGE = "message1";
    private static final String MESSAGE_1 = "message2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fleet_builder);

        mFleetBuilderBinding = ActivityFleetBuilderBinding.inflate(getLayoutInflater());
        setContentView(mFleetBuilderBinding.getRoot());

        initList();

        mSpinner = mFleetBuilderBinding.admiralSpinner;

        mAdmiralAdapter = new AdmiralAdapter(this, mAdmiralList);
        mSpinner.setAdapter(mAdmiralAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Admiral clickedAdmiral = (Admiral) adapterView.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initList() {
        mAdmiralList = new ArrayList<>();
        mAdmiralList.add(new Admiral("Sisko", R.drawable.starfleetbadge));
        mAdmiralList.add(new Admiral("Dukat", R.drawable.starfleetbadge));
        mAdmiralList.add(new Admiral("Picard", R.drawable.starfleetbadge));
        mAdmiralList.add(new Admiral("Kirk", R.drawable.starfleetbadge));
        mAdmiralList.add(new Admiral("Chang", R.drawable.starfleetbadge));
    }

    public static Intent intentFactory(Context packageContext, Boolean isAdmin, String username){
        Intent intent = new Intent (packageContext, LandingPageActivity.class);
        intent.putExtra(MESSAGE, isAdmin);
        intent.putExtra(MESSAGE_1, username);
        return intent;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}