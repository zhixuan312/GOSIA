package csl.sia.com.gosia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import csl.sia.com.gosia.Helper.LoopViewPager;
import me.relex.circleindicator.CircleIndicator;

public class LandingActivity extends Activity {

    private MediaPlayer mediaPlayer;
    private SurfaceView surfaceView;
    private Uri video = Uri.parse("android.resource://" + "csl.sia.com.gosia" + "/" + R.raw.lineup);
    private LoopViewPager viewpager;
    private int currentPage;
    private Handler handler;
    private Runnable update;
    private Button login_join_button;
    private Button login_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        login_join_button = (Button) findViewById(R.id.login_join_button);
        login_login_button = (Button) findViewById(R.id.login_login_button);
        login_join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });
        login_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        mediaPlayer = new MediaPlayer();
        surfaceView = (SurfaceView) findViewById(R.id.login_background_surface);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (video != null) {
                    mediaPlayer = MediaPlayer.create(getApplicationContext(),
                            video, holder);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

        viewpager = (LoopViewPager) findViewById(R.id.login_view_pager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.login_view_pager_indicators);

        viewpager.setAdapter(new SamplePagerAdapter(getApplicationContext()));
        indicator.setViewPager(viewpager);
        handler = new Handler();

        update = new Runnable() {
            public void run() {
                if (currentPage == 5) {
                    currentPage = 0;
                }
                viewpager.setCurrentItem(currentPage++, true);
            }
        };
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 5000);
    }

    public class SamplePagerAdapter extends PagerAdapter {
        private Context mContext;
        private LayoutInflater mLayoutInflater;
        private int mSize;

        public SamplePagerAdapter(Context mContext) {
            this.mContext = mContext;
            mSize = 5;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mSize;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            view.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.login_slider, container, false);
            TextView login_text_text = (TextView) itemView.findViewById(R.id.login_text_text);
            TextView login_text_title = (TextView) itemView.findViewById(R.id.login_text_title);
            if (position == 0) {
                login_text_text.setText("Sign up for your free car pull assistant on your phone.");
                login_text_title.setText("Welcome");
            } else if (position == 1) {
                login_text_text.setText("Make a request in real time or schedule a trip to your destination .");
                login_text_title.setText("Request");
            } else if (position == 2) {
                login_text_text.setText("Ready to go somewhere? Easily pick up other riders along the way.");
                login_text_title.setText("Match");
            } else if (position == 3) {
                login_text_text.setText("Get start your trip and meet up with each other.");
                login_text_title.setText("Settle");
            } else if (position == 4) {
                login_text_text.setText("Time to explore the world and finish your journey.");
                login_text_title.setText("Let's Start");
            }
            container.addView(itemView);
            return itemView;
        }

        public void addItem() {
            mSize++;
            notifyDataSetChanged();
        }

        public void removeItem() {
            mSize--;
            mSize = mSize < 0 ? 0 : mSize;

            notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        mediaPlayer.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mediaPlayer.start();
        super.onResume();
    }
}

