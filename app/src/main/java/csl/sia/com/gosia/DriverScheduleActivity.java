package csl.sia.com.gosia;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import csl.sia.com.gosia.Helper.Constant;

public class DriverScheduleActivity extends AppCompatActivity {

    private Button driver_schedule_back;
    private RelativeLayout driver_schedule_r2;
    private RelativeLayout driver_schedule_r3;
    private RelativeLayout driver_schedule_r5;
    private RelativeLayout driver_schedule_r8;
    private TextView driver_schedule_from_body;
    private TextView driver_schedule_to_body;
    private TextView driver_schedule_date_body;
    private TextView driver_schedule_time_body;
    private Spinner driver_schedule_seat_body;
    private DatePickerDialog dpd;
    private TimePickerDialog tpd;
    private Date datePicked;
    private View rootView;
    private int year, month, day, hour, minute, seatAvailableNum;
    private String minuteString, hourString, additionalString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_schedule);

        String fromAddress = getIntent().getStringExtra("fromAddress");
        String toAddress = getIntent().getStringExtra("toAddress");
        int seatNum = getIntent().getIntExtra("seatNumber",0);

        driver_schedule_back = (Button) findViewById(R.id.driver_schedule_back);
        driver_schedule_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        driver_schedule_from_body = (TextView) findViewById(R.id.driver_schedule_from_body);
        driver_schedule_to_body = (TextView) findViewById(R.id.driver_schedule_to_body);
        driver_schedule_from_body.setText(fromAddress);
        driver_schedule_to_body.setText(toAddress);
        driver_schedule_date_body = (TextView) findViewById(R.id.driver_schedule_date_body);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Date current = new Date();
        driver_schedule_date_body.setText(simpleDateFormat.format(current).toString());
        driver_schedule_time_body = (TextView) findViewById(R.id.driver_schedule_time_body);
        hour = current.getHours();
        minute = current.getMinutes();
        if (hour < 12) {
            hourString = "" + hour;
            additionalString = " AM";
        } else {
            hourString = hour - 12 + "";
            additionalString = " PM";
        }
        if (minute < 10) {
            minuteString = "0" + minute;
        } else {
            minuteString = "" + minute;
        }
        driver_schedule_time_body.setText(hourString + ":" + minuteString + additionalString);

        driver_schedule_r2 = (RelativeLayout) findViewById(R.id.driver_schedule_r2);
        driver_schedule_r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(DriverScheduleActivity.this);
                    startActivityForResult(intent, Constant.DRIVER_SCHEDULE_PLACE_AUTOCOMPLETE_FROM);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO:Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO:Handle the error.
                }
            }
        });

        driver_schedule_r3 = (RelativeLayout) findViewById(R.id.driver_schedule_r3);
        driver_schedule_r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(DriverScheduleActivity.this);
                    startActivityForResult(intent, Constant.DRIVER_SCHEDULE_PLACE_AUTOCOMPLETE_TO);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO:Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO:Handle the error.
                }
            }
        });

        driver_schedule_r5 = (RelativeLayout) findViewById(R.id.driver_schedule_r5);
        driver_schedule_r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd = new DatePickerDialog(DriverScheduleActivity.this, R.style.DatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        datePicked = calendar.getTime();
                        if (!datePicked.before(new Date())) {
                            driver_schedule_date_body.setText(simpleDateFormat.format(datePicked).toString());
                            dpd.hide();
                        } else {
                            Toast.makeText(DriverScheduleActivity.this, "Please select a future date", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, year, month, day);
                dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dpd.show();
            }
        });

        driver_schedule_r8 = (RelativeLayout) findViewById(R.id.driver_schedule_r8);
        driver_schedule_r8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpd = new TimePickerDialog(DriverScheduleActivity.this, R.style.DatePickerDialogTheme, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay < 12) {
                            hourString = "" + hourOfDay;
                            additionalString = " AM";
                        } else {
                            hourString = hourOfDay - 12 + "";
                            additionalString = " PM";
                        }
                        if (minute < 10) {
                            minuteString = "0" + minute;
                        } else {
                            minuteString = "" + minute;
                        }
                        driver_schedule_time_body.setText(hourString + ":" + minuteString + additionalString);
                    }
                }, hour, minute, false);
                tpd.show();
            }
        });

        List<String> seatNumber = new ArrayList<String>();
        seatNumber.add("0");
        seatNumber.add("1");
        seatNumber.add("2");
        seatNumber.add("3");
        seatNumber.add("4");
        seatNumber.add("4+");

        driver_schedule_seat_body = (Spinner) findViewById(R.id.driver_schedule_seat_body);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, seatNumber);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driver_schedule_seat_body.setAdapter(dataAdapter);
        driver_schedule_seat_body.setSelection(seatNum);
        driver_schedule_seat_body.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seatAvailableNum = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                seatAvailableNum = 0;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.DRIVER_SCHEDULE_PLACE_AUTOCOMPLETE_FROM) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                driver_schedule_from_body.setText(place.getAddress());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {

            } else if (resultCode == RESULT_CANCELED) {

            }
        } else if (requestCode == Constant.DRIVER_SCHEDULE_PLACE_AUTOCOMPLETE_TO) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                driver_schedule_to_body.setText(place.getAddress());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}