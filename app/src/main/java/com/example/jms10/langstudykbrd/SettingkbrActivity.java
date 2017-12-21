package com.example.jms10.langstudykbrd;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;

import com.example.jms10.langstudykbrd.Setting.Setting_Keyboardheihgt;

/**
 * Created by Junsu on 2017-12-10.
 */

public class SettingkbrActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }


    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settingkbr);

            Preference onoffsound = findPreference("soundonoff");
            onoffsound.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    SharedPreferenceUtil sp = new SharedPreferenceUtil(getActivity());
                    boolean state = ((SwitchPreference)preference).isChecked();
                    Log.d("hihi", String.valueOf(state));
                    sp.setKeySound(!state);
                    return true;
                }
            });

            Preference onoffvib = findPreference("vibnoff");
            onoffvib.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    SharedPreferenceUtil sp = new SharedPreferenceUtil(getActivity());
                    boolean state = ((SwitchPreference)preference).isChecked();
                    sp.setKeyVibration(!state);
                    return true;
                }
            });

            Preference keyset = findPreference("kbr_setting");
            keyset.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {

                    return true;
                }
            });

            Preference kbrHeight = findPreference("kbrHeight");
            kbrHeight.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Intent intent = new Intent(getActivity(), Setting_Keyboardheihgt.class);
                    startActivity(intent);
                    return false;
                }
            });

            Preference keyviblen = findPreference("Kbrvib");
            keyviblen.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener(){
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    SharedPreferenceUtil sp = new SharedPreferenceUtil(getActivity());
                    String textVal = o.toString();

                    ListPreference listPreference = (ListPreference) preference;
                    int index = listPreference.findIndexOfValue(textVal);
                    CharSequence[] entries = listPreference.getEntries();

                    if(index >= 0) {
                        int number = entries[index].charAt(0) - '0';
                        sp.setDicWaitingTime(number);Log.d("hihi", String.valueOf(number));
                    }
                    return true;
                }
            });
        }
    }
}
