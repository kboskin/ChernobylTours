package com.tourkiev.chernobyltours.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.tourkiev.chernobyltours.R;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tourkiev.chernobyltours.Constants.EXTRAS_TITLE;
import static com.tourkiev.chernobyltours.ModelMarker.convertToBitmap;
import static com.tourkiev.chernobyltours.fragments.MapFragment.hashMap;

/**
 * Created by hp on 001 01.12.2017.
 */

public class DisplayPointActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageContainer;
    Intent intent;
    DocumentView documentView;
    ProgressBar playProgress;
    TextView timeLeftTextView;
    CircleImageView circleImageView;
    MediaPlayer mediaPlayer;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_point);

        // get intents
        intent = getIntent();
        // unique id for all of the markers
        String uniqueTitle = intent.getStringExtra(EXTRAS_TITLE);

        // create instance of mp and set here id of audio
        mediaPlayer = MediaPlayer.create(this, hashMap.get(uniqueTitle).getAudioId());

        // progressBar
        playProgress = findViewById(R.id.progress_bar);
        // time left textview
        timeLeftTextView = findViewById(R.id.text_view_time_left);

        // setting title
        this.setTitle(uniqueTitle);

        // setting text to the textView
        documentView = findViewById(R.id.content_text); // Support plain text
        documentView.setText(hashMap.get(uniqueTitle).getDescription()); // Set to `true` to enable justification

        // setting image into imageView
        imageContainer = findViewById(R.id.content_image);
        imageContainer.setImageBitmap(convertToBitmap(getApplicationContext(), hashMap.get(uniqueTitle).getBitmapId()));

        // circle image with png
        circleImageView = findViewById(R.id.circle_image_view_button);
        circleImageView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();

        } else {
            /*Runnable updateUI = new Runnable() {
                public void run() {
                    try {
                        mediaPlayer.start();
                        //update ur ui here
                        //timeLeft.setText((mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration()) * 100);‌​
                        timeLeft.setText(Integer.toString(mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition()));

                        Log.d("Dur", String.valueOf(mediaPlayer.getDuration()));
                        //timeLeft.setText((mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration())*100);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Handler mHandler = new Handler();
            mHandler.post(updateUI);*/

//Make sure you update Seekbar on UI thread
            mediaPlayer.start();
            DisplayPointActivity.this.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        int wholeTime = mediaPlayer.getDuration();
                        int timeLeft = wholeTime - mediaPlayer.getCurrentPosition(); // time left
                        final int min = timeLeft / 1000 * 60;

                        int mCurrentPosition = (mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition()); // milliseconds


                        timeLeftTextView.setText(String.format("%d:%d",
                                TimeUnit.MILLISECONDS.toMinutes(mCurrentPosition),
                                TimeUnit.MILLISECONDS.toSeconds(mCurrentPosition) -
                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mCurrentPosition))
                        ));
                    }
                    mHandler.postDelayed(this, 1000);
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
