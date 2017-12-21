package com.example.jms10.langstudykbrd;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;

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

            Preference piconoff = findPreference("piconoff");
            piconoff.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    SharedPreferenceUtil sp = new SharedPreferenceUtil(getActivity());
                    boolean state = ((SwitchPreference)preference).isChecked();
                    Log.d("hihi", "------");
                    Log.d("hihi", String.valueOf(!state));
                    sp.setPicturePresent(!state);
                    return true;
                }
            });

            Preference CoolTime = findPreference("CoolTime");
            CoolTime.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    SharedPreferenceUtil sp = new SharedPreferenceUtil(getActivity());
                    String textVal = o.toString();

                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(textVal);

                    CharSequence[] entries = listPreference.getEntries();

                    if(index >= 0) {
                        int number = entries[index].charAt(0) - '0';
                        sp.setVibrartionLength(number); Log.d("hihi", "vib: "+String.valueOf(number));
                    }
                    return true;
                }
            });
        }
    }
}
