package csl.sia.com.gosia;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import csl.sia.com.gosia.Helper.Constant;
import csl.sia.com.gosia.Helper.DataParser;

public class DriverNewTripActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private Spinner driver_new_trip_body_seat;
    private Button driver_new_trip_button_back;
    private Button driver_new_trip_button_offer;
    private RelativeLayout driver_new_trip_rl_schedule;
    private TextView driver_new_trip_body_destination;
    private TextView driver_new_trip_body_current;
    private int seatAvailableNum;
    private LocationManager mLocationManager;
    private GoogleMap mMap;
    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private List<Marker> markers = new ArrayList<>();
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;
    private LatLngBounds bounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_new_trip);

        startLat = getIntent().getDoubleExtra("lat", 1.3521);
        startLng = getIntent().getDoubleExtra("lng", 103.8198);
        endLat = getIntent().getDoubleExtra("lat", 1.3521);
        endLng = getIntent().getDoubleExtra("lng", 103.8198);

        driver_new_trip_body_current = (TextView) findViewById(R.id.driver_new_trip_body_current);
        driver_new_trip_body_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(DriverNewTripActivity.this);
                    startActivityForResult(intent, Constant.DRIVER_SCHEDULE_PLACE_AUTOCOMPLETE_FROM);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO:Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO:Handle the error.
                }
            }
        });

        driver_new_trip_body_destination = (TextView) findViewById(R.id.driver_new_trip_body_destination);
        driver_new_trip_body_destination.setText(getIntent().getStringExtra("address"));
        driver_new_trip_body_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(DriverNewTripActivity.this);
                    startActivityForResult(intent, Constant.DRIVER_SCHEDULE_PLACE_AUTOCOMPLETE_TO);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO:Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO:Handle the error.
                }
            }
        });

        driver_new_trip_button_back = (Button) findViewById(R.id.driver_new_trip_button_back);
        driver_new_trip_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        driver_new_trip_button_offer = (Button) findViewById(R.id.driver_new_trip_button_offer);
        driver_new_trip_button_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverNewTripActivity.this, DriverSelectPassengerActivity.class);
                intent.putExtra("seatAvailableNum", seatAvailableNum);
                startActivity(intent);
            }
        });

        List<String> seatNumber = new ArrayList<String>();
        seatNumber.add("0");
        seatNumber.add("1");
        seatNumber.add("2");
        seatNumber.add("3");
        seatNumber.add("4");
        seatNumber.add("4+");

        driver_new_trip_body_seat = (Spinner) findViewById(R.id.driver_new_trip_body_seat);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, seatNumber);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driver_new_trip_body_seat.setAdapter(dataAdapter);
        driver_new_trip_body_seat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seatAvailableNum = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                seatAvailableNum = 0;
            }
        });

        driver_new_trip_rl_schedule = (RelativeLayout) findViewById(R.id.driver_new_trip_rl_schedule);
        driver_new_trip_rl_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverNewTripActivity.this, DriverScheduleActivity.class);
                intent.putExtra("fromAddress", driver_new_trip_body_current.getText());
                intent.putExtra("fromLat", startLat);
                intent.putExtra("fromLng", startLng);
                intent.putExtra("toAddress", driver_new_trip_body_destination.getText());
                intent.putExtra("toLat", endLat);
                intent.putExtra("toLng", endLng);
                intent.putExtra("seatNumber", seatAvailableNum);
                startActivity(intent);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.driver_new_trip_map);
        mapFragment.getMapAsync(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(DriverNewTripActivity.this, "Not Enough Permission", Toast.LENGTH_SHORT).show();
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
                        updateMap(location.getLatitude(), location.getLongitude(), endLat, endLng);
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
                        updateMap(location.getLatitude(), location.getLongitude(), endLat, endLng);
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
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location location = getLastKnownLocation(locationManager);
        startLat = location.getLatitude();
        startLng = location.getLongitude();
        updateMap(startLat, startLng, endLat, endLng);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    private Bitmap getBitmap(Drawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
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

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    private class DownloadTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(R.color.colorPrimary);

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private void updateMap(double sLat, double sLng, double eLat, double eLng) {
        mMap.clear();
        latlngs = new ArrayList<>();
        markers = new ArrayList<>();
        LatLng latLng = new LatLng(sLat, sLng);
        LatLng latLng2 = new LatLng(eLat, eLng);
        latlngs.add(latLng);
        latlngs.add(latLng2);
        for (int i = 0; i < latlngs.size(); i++) {
            if (i == 0) {
                Bitmap bitmap = getBitmap(getDrawable(R.drawable.ic_place_text_color_light_24dp));
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latlngs.get(i))
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                markers.add(marker);
            } else {
                Bitmap bitmap = getBitmap(getDrawable(R.drawable.ic_pin_drop_yellow_24dp));
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latlngs.get(i))
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));
                markers.add(marker);
            }
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        bounds = builder.build();
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
            }
        });

        String url = getDirectionsUrl(latLng, latLng2);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.DRIVER_SCHEDULE_PLACE_AUTOCOMPLETE_FROM) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                driver_new_trip_body_current.setText(place.getAddress());
                startLat = place.getLatLng().latitude;
                startLng = place.getLatLng().longitude;
                updateMap(startLat, startLng, endLat, endLng);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {

            } else if (resultCode == RESULT_CANCELED) {

            }
        } else if (requestCode == Constant.DRIVER_SCHEDULE_PLACE_AUTOCOMPLETE_TO) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                driver_new_trip_body_destination.setText(place.getAddress());
                endLat = place.getLatLng().latitude;
                endLng = place.getLatLng().longitude;
                updateMap(startLat, startLng, endLat, endLng);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}


