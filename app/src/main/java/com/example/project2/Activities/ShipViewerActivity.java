package com.example.project2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.project2.R;
import com.example.project2.RecycleViewStuff.ShipRecyclerAdapter;
import com.example.project2.databinding.ActivityShipViewerBinding;

public class ShipViewerActivity extends AppCompatActivity {

    ActivityShipViewerBinding mActivityShipViewerBinding;

    private RecyclerView mRecyclerView;
    private ShipRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String MESSAGE = "message1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship_viewer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.add("addShip");
        item.setIcon(R.drawable.starfleetbadge);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public static Intent intentFactory(Context context, String loggedUser){
        Intent intent = new Intent(context, ShipViewerActivity.class);
        intent.putExtra(MESSAGE, loggedUser);
        return intent;
    }
}