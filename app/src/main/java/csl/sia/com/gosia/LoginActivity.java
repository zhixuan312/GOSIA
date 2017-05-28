package csl.sia.com.gosia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import csl.sia.com.gosia.Helper.DummyData;

public class LoginActivity extends AppCompatActivity {

    private ImageView login_profile_image;
    private View login_cancel;
    private Button login_login_button;
    private LoginWithEmailTask loginWithEmailTask = null;
    private boolean cancel = false;
    private View focusView = null;
    private EditText login_edit_email;
    private EditText login_edit_password;
    private String encodedEmail;
    private String encodedPassword;
    private boolean signUpBoolean;
    private ImageView mProgressView;
    private RadioGroup login_radio_group;
    private String role = "rider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_profile_image = (ImageView) findViewById(R.id.login_profile_image);
        int exampleProfileImageId = R.drawable.z4;
        Glide
                .with(LoginActivity.this)
                .load(exampleProfileImageId)
                .asBitmap()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new BitmapImageViewTarget(login_profile_image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        login_profile_image.setImageDrawable(circularBitmapDrawable);
                    }
                });

        login_cancel = findViewById(R.id.login_cancel);
        login_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        login_radio_group = (RadioGroup) findViewById(R.id.login_radio_group);
        login_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.login_radio_driver:
                        role = "driver";
                        break;
                    case R.id.login_radio_rider:
                        role = "rider";
                        break;
                }
            }
        });

        login_edit_email = (EditText) findViewById(R.id.login_edit_email);
        login_edit_password = (EditText) findViewById(R.id.login_edit_password);

        login_login_button = (Button) findViewById(R.id.login_login_button);
        login_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                attemptLogin();
                Intent intent;
                if (role.equals("rider")) {
                    intent = new Intent(LoginActivity.this, RiderMainActivity.class);
                } else {
                    intent = new Intent(LoginActivity.this, DriverMainActivity.class);
                }

                startActivity(intent);
                finishAffinity();
            }
        });
    }

    private void attemptLogin() {
        if (loginWithEmailTask != null) {
            loginWithEmailTask = null;

        }
        cancel = false;
        // Reset errors.
        login_edit_email.setError(null);
        login_edit_password.setError(null);

        // Store values at the time of the login attempt.
        String email = login_edit_email.getText().toString();
        String password = login_edit_password.getText().toString();


        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            login_edit_password.setError(getString(R.string.error_invalid_password));
            focusView = login_edit_password;
            cancel = true;
        }


        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            login_edit_email.setError(getString(R.string.error_field_required));
            focusView = login_edit_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            login_edit_email.setError(getString(R.string.error_invalid_email));
            focusView = login_edit_email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            loginWithEmailTask = new LoginWithEmailTask(role, email, password);
            loginWithEmailTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.contains("[a-zA-Z]+") == false && password.length() > 7;
    }

    private boolean isEmailValid(String email) {
        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public class LoginWithEmailTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private String mRole;

        LoginWithEmailTask(String role, String email, String password) {
            mEmail = email;
            mPassword = password;
            mRole = role;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            showProgress(true);
            encodedEmail = "";
            encodedPassword = "";
            try {
                encodedEmail = URLEncoder.encode(mEmail, "UTF-8");
                encodedPassword = URLEncoder.encode(mPassword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(3000);
                signUpBoolean = DummyData.checkDummyProfile(mRole, mEmail, mPassword);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return signUpBoolean;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            loginWithEmailTask = null;
            cancel = false;
            if (success) {
                Intent intent;
                if (mRole.equals("rider")) {
                    intent = new Intent(LoginActivity.this, RiderMainActivity.class);
                } else {
                    intent = new Intent(LoginActivity.this, DriverMainActivity.class);
                }
                startActivity(intent);
                finishAffinity();
            } else {
                showProgress(false);
                Toast.makeText(LoginActivity.this, "Credential does not match", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {

        }
    }

    private void showProgress(final boolean show) {
        if (show) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressView = (ImageView) findViewById(R.id.login_spin_globe);
                    login_login_button.setBackgroundColor(getResources().getColor(R.color.textColorLight));
                    login_login_button.setText("processing...");
                    mProgressView.setBackgroundResource(R.drawable.spinning_globe_white);
                    AnimationDrawable frameAnimation = (AnimationDrawable) mProgressView.getBackground();
                    frameAnimation.start();
                    mProgressView.setVisibility(View.VISIBLE);
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    login_login_button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    login_login_button.setText("login");
                    mProgressView.setVisibility(View.GONE);
                }
            });
        }
    }
}
