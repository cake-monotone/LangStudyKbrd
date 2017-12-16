package com.example.jms10.langstudykbrd;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

/**
 * Created by Junsu on 2017-12-16.
 */

public class SettingpushActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingpushActivity.MyPreferenceFragment()).commit();
    }


    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settingpush);

            Preference onoffPush = findPreference("saonoff");
            onoffPush.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object o) {
                    SharedPreferenceUtil sp = new SharedPreferenceUtil(getActivity());
                    boolean state = ((SwitchPreference)preference).isChecked();
                    sp.setMemwordreview(state);
                    return true;
                }
            });
        }
    }
}
