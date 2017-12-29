package com.tourkiev.chernobyltours.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tourkiev.chernobyltours.R;

import java.util.Locale;

public class LocalizationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localization);

        findViewById(R.id.en_lang_image_view).setOnClickListener(this);
        findViewById(R.id.es_lang_image_view).setOnClickListener(this);
        findViewById(R.id.fr_lang_image_view).setOnClickListener(this);
        findViewById(R.id.ja_lang_image_view).setOnClickListener(this);
        findViewById(R.id.zh_lang_image_view).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.en_lang_image_view:
                setLocalization("en");
                startMainActivity();
                break;
            case R.id.es_lang_image_view:
                setLocalization("es");
                startMainActivity();
                break;
            case R.id.fr_lang_image_view:
                setLocalization("fr");
                startMainActivity();
                break;
            case R.id.ja_lang_image_view:
                setLocalization("ja");
                startMainActivity();
                break;
            case R.id.zh_lang_image_view:
                setLocalization("zh");
                startMainActivity();
                break;
        }
    }

    private void setLocalization(String langId) {
        Locale locale = new Locale(langId);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
    private void startMainActivity()
    {
        startActivity(new Intent(LocalizationActivity.this, MainActivity.class));
        finish();
    }
}
