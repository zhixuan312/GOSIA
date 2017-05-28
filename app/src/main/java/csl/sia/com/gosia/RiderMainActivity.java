package csl.sia.com.gosia;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csl.sia.com.gosia.Helper.DummyData;

import static csl.sia.com.gosia.Helper.Global.retrieveImageId;
import static csl.sia.com.gosia.R.id.driver_float_button1;
import static csl.sia.com.gosia.R.id.driver_float_button2;
import static csl.sia.com.gosia.R.id.driver_float_button3;
import static csl.sia.com.gosia.R.id.driver_place_autocomplete_fragment;

public class RiderMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener {

    private Toolbar toolbar;
    private LocationManager mLocationManager;
    private GoogleMap mMap;
    //    private Button driver_button_search;
    private ImageView profile_image_view;
    private PlaceAutocompleteFragment rider_place_autocomplete_fragment;
    private FloatingActionButton rider_float_button1;
    private FloatingActionButton rider_float_button2;
    private FloatingActionButton rider_float_button3;
    private ArrayList<HashMap<String, String>> favouriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("GO SIA");
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColor));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.driver_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.driver_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        profile_image_view = (ImageView) headerLayout.findViewById(R.id.profile_image_view);
        int exampleProfileImageId = R.drawable.xuan;
        Glide
                .with(RiderMainActivity.this)
                .load(exampleProfileImageId)
                .asBitmap()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new BitmapImageViewTarget(profile_image_view) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        profile_image_view.setImageDrawable(circularBitmapDrawable);
                    }
                });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.driver_mapView);
        mapFragment.getMapAsync(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(RiderMainActivity.this, "Not Enough Permission", Toast.LENGTH_SHORT).show();
        }
        boolean gps_enabled;
        boolean network_enabled;
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        gps_enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        network_enabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (network_enabled) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        CameraPosition newCamPos = new CameraPosition.Builder()
                                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                .zoom(15)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), 4000, null);
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });

        }
        if (gps_enabled) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new android.location.LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        CameraPosition newCamPos = new CameraPosition.Builder()
                                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                .zoom(15)
                                .build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(newCamPos), 4000, null);
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }

        rider_place_autocomplete_fragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(driver_place_autocomplete_fragment);
        rider_place_autocomplete_fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Intent intent = new Intent(RiderMainActivity.this, RiderNewTripActivity.class);
                intent.putExtra("address", place.getAddress().toString());
                intent.putExtra("lat", place.getLatLng().latitude);
                intent.putExtra("lng", place.getLatLng().longitude);
                startActivity(intent);
            }

            @Override
            public void onError(Status status) {
            }
        });

        rider_float_button1 = (FloatingActionButton) findViewById(driver_float_button1);
        rider_float_button2 = (FloatingActionButton) findViewById(driver_float_button2);
        rider_float_button3 = (FloatingActionButton) findViewById(driver_float_button3);
        favouriteList = DummyData.loadDriverMainFavouriteIconDummyData(RiderMainActivity.this);
        if (favouriteList.isEmpty()) {
            rider_float_button1.setLabelText("Add");
            rider_float_button1.setImageResource(R.drawable.ic_add_white_24dp);
            rider_float_button1.setColorNormal(R.color.colorPrimary);
            rider_float_button1.setColorPressed(R.color.colorPrimaryDark);
            rider_float_button2.setVisibility(View.GONE);
            rider_float_button3.setVisibility(View.GONE);
        } else if (favouriteList.size() == 1) {
            String title = favouriteList.get(0).get("title");
            rider_float_button1.setLabelText(title);
            rider_float_button1.setImageResource(retrieveImageId(title.charAt(0)));
            rider_float_button2.setVisibility(View.GONE);
            rider_float_button3.setVisibility(View.GONE);
        } else if (favouriteList.size() == 2) {
            String title1 = favouriteList.get(0).get("title");
            rider_float_button1.setLabelText(title1);
            rider_float_button1.setImageResource(retrieveImageId(title1.charAt(0)));
            String title2 = favouriteList.get(1).get("title");
            rider_float_button2.setLabelText(title2);
            rider_float_button2.setImageResource(retrieveImageId(title2.charAt(0)));
            rider_float_button3.setVisibility(View.GONE);
        } else {
            String title1 = favouriteList.get(0).get("title");
            rider_float_button1.setLabelText(title1);
            rider_float_button1.setImageResource(retrieveImageId(title1.charAt(0)));
            String title2 = favouriteList.get(1).get("title");
            rider_float_button2.setLabelText(title2);
            rider_float_button2.setImageResource(retrieveImageId(title2.charAt(0)));
            String title3 = favouriteList.get(2).get("title");
            rider_float_button3.setLabelText(title3);
            rider_float_button3.setImageResource(retrieveImageId(title3.charAt(0)));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.driver_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle trips_navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_trips) {
            Intent intent = new Intent(RiderMainActivity.this, TripsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_favourite) {
            Intent intent = new Intent(RiderMainActivity.this, FavouriteActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(RiderMainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.driver_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location = getLastKnownLocation(locationManager);
        if (location != null) {
            double lat = location.getLatitude();
            double longi = location.getLongitude();
            LatLng latLng = new LatLng(lat, longi);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    private Location getLastKnownLocation(LocationManager mLocationManager) {
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }


}
