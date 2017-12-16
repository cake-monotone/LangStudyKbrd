package com.example.jms10.langstudykbrd;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by Junsu on 2017-12-16.
 */

public class SettingdictionaryActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingdictionaryActivity.MyPreferenceFragment()).commit();

    }


    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settingdictionary);

            Preference txtpref0 = findPreference("go_to_font");
            txtpref0.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(), Setting_Font.class);
                    startActivity(intent);
                    return false;
                }
            });
        }
    }
}
