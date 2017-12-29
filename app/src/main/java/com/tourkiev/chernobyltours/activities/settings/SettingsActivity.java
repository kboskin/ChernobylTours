package com.tourkiev.chernobyltours.activities.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.tourkiev.chernobyltours.R;

import static com.tourkiev.chernobyltours.Constants.PREFS_TRACK_GEO;


public class SettingsActivity extends AppCompatPreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        // set back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            final CheckBoxPreference checkboxPref = (CheckBoxPreference) getPreferenceManager().findPreference("geoUpdates");

            checkboxPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if(newValue instanceof Boolean){
                        Boolean boolVal = (Boolean)newValue;
                        setInPrefs(boolVal);
                    }
                    return true;
                }
            });
        }
        private void setInPrefs(Boolean b)
        {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(PREFS_TRACK_GEO, b);
            editor.apply();
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //click listener for navigation home
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
