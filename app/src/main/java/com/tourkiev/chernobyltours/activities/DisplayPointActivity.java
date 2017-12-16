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

public class DisplayPointActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnCompletionListener {

    ImageView imageContainer;
    Intent intent;
    DocumentView documentView;
    ProgressBar playProgress;
    TextView timeLeftTextView;
    CircleImageView circleImageView;
    MediaPlayer mediaPlayer;
    private Handler mHandler = new Handler();
    private Runnable updateRunnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                int mCurrentPosition = (mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition()); // milliseconds

                timeLeftTextView.setText(String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(mCurrentPosition),
                        TimeUnit.MILLISECONDS.toSeconds(mCurrentPosition) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mCurrentPosition))
                ));

                // set progress values
                // (formula to get the value of one second in progressbar)
                // 100 (max pr) - 1sec * 100%/all time,
                // to calculate weight of one second
                playProgress.setProgress((int) (
                                100 // max progress
                                -
                                (TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getCurrentPosition()) // calculation of weight
                                        * 100
                                        / TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration()))));
            }
            mHandler.postDelayed(this, 1000);
        }
    };


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
        mediaPlayer.setOnCompletionListener(this);

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
            //Make sure you update Seekbar on UI thread
            mediaPlayer.start();
            mHandler.postDelayed(updateRunnable, 1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mHandler.removeCallbacks(updateRunnable);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        mediaPlayer.release();
        mHandler.removeCallbacks(updateRunnable);

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mHandler.removeCallbacks(updateRunnable);
        playProgress.setProgress(100);
    }

}
