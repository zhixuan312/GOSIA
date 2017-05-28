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

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView favourite_recycler;
    private FavouriteListAdapter favouriteListAdapter;
    private ArrayList<HashMap<String, String>> favouriteList;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button favourite_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        favourite_back = (Button)findViewById(R.id.favourite_back);
        favourite_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        favouriteList = DummyData.loadFavouriteDummyData(FavouriteActivity.this);
        favourite_recycler = (RecyclerView) findViewById(R.id.favourite_content_recycler);
        favourite_recycler.setHasFixedSize(true);
        favouriteListAdapter = new FavouriteListAdapter(favouriteList, FavouriteActivity.this);
        mLayoutManager = new LinearLayoutManager(FavouriteActivity.this);
        favourite_recycler.setLayoutManager(mLayoutManager);
        favourite_recycler.setItemAnimator(new DefaultItemAnimator());
        favourite_recycler.setAdapter(favouriteListAdapter);
    }
}
