package csl.sia.com.gosia;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

import csl.sia.com.gosia.Helper.DummyData;

public class TripsActivity extends AppCompatActivity {

    private RecyclerView trips_recycler;
    private TripsListAdapter tripListAdapter;
    private ArrayList<HashMap<String, String>> tripList;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button trips_back;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_past:
                    //load with dummy data
                    tripList = DummyData.loadPastTripsDummyData(TripsActivity.this);
                    tripListAdapter = new TripsListAdapter(tripList, TripsActivity.this);
                    trips_recycler.setAdapter(tripListAdapter);
                    trips_recycler.invalidate();
                    return true;
                case R.id.navigation_upcoming:
                    tripList = DummyData.loadUpcomingTripsDummyData(TripsActivity.this);
                    tripListAdapter = new TripsListAdapter(tripList, TripsActivity.this);
                    trips_recycler.setAdapter(tripListAdapter);
                    trips_recycler.invalidate();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        tripList = DummyData.loadPastTripsDummyData(TripsActivity.this);
        trips_recycler = (RecyclerView) findViewById(R.id.trips_recycler);
        trips_recycler.setHasFixedSize(true);
        tripListAdapter = new TripsListAdapter(tripList, TripsActivity.this);
        mLayoutManager = new LinearLayoutManager(TripsActivity.this);
        trips_recycler.setLayoutManager(mLayoutManager);
        trips_recycler.setItemAnimator(new DefaultItemAnimator());
        trips_recycler.setAdapter(tripListAdapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        trips_back = (Button) findViewById(R.id.trips_back);
        trips_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
