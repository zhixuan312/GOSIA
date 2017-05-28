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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {

    private ImageView join_profile_image;
    private View join_cancel;
    private Button join_join_button;
    private SignUpWithEmailTask signUpWithEmailTask = null;
    private boolean cancel = false;
    private View focusView = null;
    private EditText join_first_name;
    private EditText join_edit_email;
    private EditText join_edit_password;
    private EditText join_edit_confirm_password;
    private String encodedEmail;
    private String encodedPassword;
    private String encodedConfirmPassword;
    private String encodedFirstName;
    private boolean signUpBoolean;
    private ImageView mProgressView;
    private String role = "rider";
    private RadioGroup join_radio_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        join_profile_image = (ImageView) findViewById(R.id.join_profile_image);
        int exampleProfileImageId = R.drawable.user;
        Glide
                .with(JoinActivity.this)
                .load(exampleProfileImageId)
                .asBitmap()
                .centerCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new BitmapImageViewTarget(join_profile_image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        join_profile_image.setImageDrawable(circularBitmapDrawable);
                    }
                });

        join_cancel = findViewById(R.id.join_cancel);
        join_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        join_radio_group = (RadioGroup) findViewById(R.id.join_radio_group);
        join_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.join_radio_driver:
                        role = "driver";
                        break;
                    case R.id.join_radio_rider:
                        role = "rider";
                        break;
                    case R.id.join_radio_both:
                        role = "both";
                        break;
                }
            }
        });

        join_first_name = (EditText) findViewById(R.id.join_first_name);
        join_edit_email = (EditText) findViewById(R.id.join_edit_email);
        join_edit_password = (EditText) findViewById(R.id.join_edit_password);
        join_edit_confirm_password = (EditText) findViewById(R.id.join_edit_confirm_password);

        join_join_button = (Button) findViewById(R.id.join_join_button);
        join_join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }

    private void attemptLogin() {
        if (signUpWithEmailTask != null) {
            signUpWithEmailTask = null;

        }
        cancel = false;
        // Reset errors.
        join_edit_email.setError(null);
        join_edit_password.setError(null);
        join_edit_confirm_password.setError(null);
        join_first_name.setError(null);

        // Store values at the time of the login attempt.
        String email = join_edit_email.getText().toString();
        String password = join_edit_password.getText().toString();
        String confirmPassowrd = join_edit_confirm_password.getText().toString();
        String firstName = join_first_name.getText().toString();

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            join_edit_password.setError(getString(R.string.error_invalid_password));
            focusView = join_edit_password;
            cancel = true;
        }

        if (!TextUtils.isEmpty(firstName) && !isFirstNameValid(firstName)) {
            join_first_name.setError(getString(R.string.error_invalid_name));
            focusView = join_first_name;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            join_edit_email.setError(getString(R.string.error_field_required));
            focusView = join_edit_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            join_edit_email.setError(getString(R.string.error_invalid_email));
            focusView = join_edit_email;
            cancel = true;
        }

        if (!TextUtils.isEmpty(confirmPassowrd) && !isPasswordValid(confirmPassowrd)) {
            join_edit_confirm_password.setError(getString(R.string.error_invalid_password));
            focusView = join_edit_confirm_password;
            cancel = true;
        } else if (!isConfirmPasswordValid(password, confirmPassowrd)) {
            join_edit_confirm_password.setError("Password not match");
            focusView = join_edit_confirm_password;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            signUpWithEmailTask = new SignUpWithEmailTask(role, email, password, confirmPassowrd, firstName);
            signUpWithEmailTask.execute((Void) null);
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

    private boolean isFirstNameValid(String firstName) {
        return firstName.length() > 0;
    }

    private boolean isConfirmPasswordValid(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public class SignUpWithEmailTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mConfirmPassword;
        private final String mFirstName;
        private String mRole;

        SignUpWithEmailTask(String role, String email, String password, String confirmPassword, String firstName) {
            mEmail = email;
            mPassword = password;
            mConfirmPassword = confirmPassword;
            mFirstName = firstName;
            mRole = role;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            showProgress(true);
            encodedEmail = "";
            encodedPassword = "";
            encodedConfirmPassword = "";
            encodedFirstName = "";
            try {
                encodedEmail = URLEncoder.encode(mEmail, "UTF-8");
                encodedPassword = URLEncoder.encode(mPassword, "UTF-8");
                encodedConfirmPassword = URLEncoder.encode(mConfirmPassword, "UTF-8");
                encodedFirstName = URLEncoder.encode(mFirstName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(3000);
                signUpBoolean = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return signUpBoolean;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            signUpWithEmailTask = null;
            cancel = false;
            if (success) {
                Intent intent;
                if (mRole.equals("rider")) {
                    intent = new Intent(JoinActivity.this, RiderMainActivity.class);
                } else {
                    intent = new Intent(JoinActivity.this, DriverMainActivity.class);
                }
                startActivity(intent);
                finishAffinity();
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
                    mProgressView = (ImageView) findViewById(R.id.join_spin_globe);
                    join_join_button.setBackgroundColor(getResources().getColor(R.color.textColorLight));
                    join_join_button.setText("Joining...");
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
                    join_join_button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    join_join_button.setText("Join");
                    mProgressView.setVisibility(View.GONE);
                }
            });
        }
    }
}
