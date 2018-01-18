package com.tourkiev.chernobyltours.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.tourkiev.chernobyltours.R;
import com.tourkiev.chernobyltours.activities.settings.SettingsActivity;
import com.tourkiev.chernobyltours.fragments.AboutUsFragment;
import com.tourkiev.chernobyltours.fragments.BookATourFragment;
import com.tourkiev.chernobyltours.fragments.MapFragment;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.tourkiev.chernobyltours.Constants.CHECK_IF_IS_AUTH_PASSED;
import static com.tourkiev.chernobyltours.Constants.PREFS_PROFILE_FIRST_NAME;
import static com.tourkiev.chernobyltours.Constants.PREFS_PROFILE_IMAGE_URL;
import static com.tourkiev.chernobyltours.Constants.TOURKIEV_URL_BOOK_TOUR;
import static com.tourkiev.chernobyltours.Constants.TOURKIEV_URL_MAIN;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    MapFragment mapFragment;
    AboutUsFragment aboutUsFragment;
    BookATourFragment bookATourFragment;
    CircleImageView profileImageView;
    TextView profileTextName;
    Handler handler;
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // fixed orientation in portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // prefs initialization
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = prefs.edit();

        // get prefs for loading
        String profileImageUrl = prefs.getString(PREFS_PROFILE_IMAGE_URL, "");
        Log.d("URL", profileImageUrl);
        String profileName = prefs.getString(PREFS_PROFILE_FIRST_NAME, "");
        Log.d("Name", profileName);


        View headerLayout = navigationView.getHeaderView(0);// я ебу нахуй оно, если я мог просто его импортировать же....просто работает и х с ним

        // finding our elements
        profileImageView = headerLayout.findViewById(R.id.profile_image);
        profileTextName = headerLayout.findViewById(R.id.profile_name);

        // load image into circleImageView
        Picasso.with(getApplicationContext())
                .load(profileImageUrl)
                .into(profileImageView);
        profileTextName.setText(getString(R.string.greetings) + ", " + profileName + " " + "!");

        if (savedInstanceState == null) {
            MapFragment mapFragment = new MapFragment();
            replaceWithFragment(mapFragment, handler);
            navigationView.setCheckedItem(R.id.nav_map);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int fragments = getSupportFragmentManager().getBackStackEntryCount();
            if (fragments == 1) {
                finish();
            } else {
                if (getFragmentManager().getBackStackEntryCount() > 1) {
                    getFragmentManager().popBackStack();
                } else {
                    super.onBackPressed();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // empty fragment, just for a while
        drawer = findViewById(R.id.drawer_layout);

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawer.closeDrawer(Gravity.LEFT, true);
                    }
                }, 100);
                return true;
            }
        });


        initializeDrawerItemList(id);
        return true;
    }

    private void initializeDrawerItemList(int id) {
        switch (id) {
            case R.id.nav_book:
                if (bookATourFragment == null) {
                    bookATourFragment = new BookATourFragment();
                    bookATourFragment.init(TOURKIEV_URL_BOOK_TOUR);
                }
                replaceWithFragment(bookATourFragment, handler);
                break;
            case R.id.nav_map:
                if (mapFragment == null) {
                    mapFragment = new MapFragment();
                }
                replaceWithFragment(mapFragment, handler);
                break;
            case R.id.nav_about:
                if (aboutUsFragment == null) {
                    aboutUsFragment = new AboutUsFragment();
                    // initializing of url
                    aboutUsFragment.init(TOURKIEV_URL_MAIN);
                }
                replaceWithFragment(aboutUsFragment, handler);
                break;
            case R.id.nav_log_out:
                rewriteLogInValueAndBackToLogIn(editor);
                System.gc();
                break;
            case R.id.nav_language:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this, LocalizationActivity.class));
                    }
                }).start();
                break;
        }
    }

    private void rewriteLogInValueAndBackToLogIn(SharedPreferences.Editor editor) {
        // change value of our variable
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();

        // trigger switching
        editor.putBoolean(CHECK_IF_IS_AUTH_PASSED, false);
        editor.apply();

        // back to LoginActivity
        Intent backToLoginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(backToLoginIntent);
        finish();
    }

    private void replaceWithFragment(final Fragment fragment, Handler handler) {
        // frgmcont has strong reference because we always replace it exactly
        if (fragment.isAdded()) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            for (Fragment addedFragment : getSupportFragmentManager().getFragments()) {

                if (addedFragment != fragment) {
                    ft.hide(addedFragment);
                }
            }
            ft.show(fragment).commit();
        } else {
            // for faster UI and better optimization
            new Thread(new Runnable() {
                @Override
                public void run() {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.frgm_cont, fragment)
                            .addToBackStack(String.valueOf(fragment.getId()))
                            .show(fragment)
                            .commit();
                }
            }).start();

        }

        if (handler != null)
            handler.sendEmptyMessage(1);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
