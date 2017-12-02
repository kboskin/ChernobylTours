package com.tourkiev.chernobyltours.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bluejamesbond.text.DocumentView;
import com.tourkiev.chernobyltours.R;

import static com.tourkiev.chernobyltours.Constants.EXTRAS_DESCRIPTION;
import static com.tourkiev.chernobyltours.Constants.EXTRAS_TITLE;
import static com.tourkiev.chernobyltours.fragments.MapFragment.hashMap;

/**
 * Created by hp on 001 01.12.2017.
 */

public class DisplayPointActivity extends AppCompatActivity {

    ImageView imageContainer;
    Intent intent;
    DocumentView documentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_point);

        intent = getIntent();// get intents

        // setting title
        this.setTitle(intent.getStringExtra(EXTRAS_TITLE));

        // setting text to the textView
        documentView = findViewById(R.id.content_text); // Support plain text
        documentView.setText(intent.getStringExtra(EXTRAS_DESCRIPTION)); // Set to `true` to enable justification

        // setting image into imageView
        imageContainer = findViewById(R.id.content_image);

        imageContainer.setImageBitmap(hashMap.get(intent.getStringExtra(EXTRAS_TITLE)).getBitmap());

    }
}
