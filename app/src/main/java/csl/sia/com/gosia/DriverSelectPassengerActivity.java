package csl.sia.com.gosia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

import csl.sia.com.gosia.Helper.DummyData;

public class DriverSelectPassengerActivity extends AppCompatActivity {

    private RecyclerView driver_select_passenger_recycler;
    private RideRequestListAdapter rideRequestListAdapter;
    private Button driver_select_passenger_back;
    private ArrayList<HashMap<String, String>> requestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_select_passenger);

        int seatAvailableNum = getIntent().getIntExtra("seatAvailableNum",0);

        //load with dummy data
        requestList = DummyData.loadRequestDummyData(this);

        driver_select_passenger_recycler = (RecyclerView) findViewById(R.id.driver_select_passenger_recycler);
        driver_select_passenger_recycler.setHasFixedSize(true);
        rideRequestListAdapter = new RideRequestListAdapter(requestList,seatAvailableNum, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        driver_select_passenger_recycler.setLayoutManager(mLayoutManager);
        driver_select_passenger_recycler.setItemAnimator(new DefaultItemAnimator());
        driver_select_passenger_recycler.setAdapter(rideRequestListAdapter);

        driver_select_passenger_back = (Button) findViewById(R.id.driver_select_passenger_back);
        driver_select_passenger_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
