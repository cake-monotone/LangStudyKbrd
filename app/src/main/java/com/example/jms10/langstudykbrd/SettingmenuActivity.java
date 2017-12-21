package com.example.jms10.langstudykbrd;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by Junsu on 2017-12-16.
 */



public class SettingmenuActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingmenuActivity.MyPreferenceFragment()).commit();

    }


    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settingmenu);

            Preference txtpref0 = findPreference("Dic");
            txtpref0.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(), SettingdictionaryActivity.class);
                    startActivity(intent);
                    return false;
                }
            });
            Preference txtpref1 = findPreference("Kbr");
            txtpref1.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(), SettingkbrActivity.class);
                    startActivity(intent);
                    return false;
                }
            });
            /*Preference txtpref2 = findPreference("SearchWord");
            txtpref2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(), Search_Word.class);
                    startActivity(intent);
                    return false;
                }
            });*/
            Preference txtpref3 = findPreference("Push");
            txtpref3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(), SettingpushActivity.class);
                    startActivity(intent);
                    return false;
                }
            });



        }
    }
}
