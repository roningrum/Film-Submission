package co.id.roni.filmsubmission.fragment;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import co.id.roni.filmsubmission.R;
import co.id.roni.filmsubmission.notification.DailyNotification;
import co.id.roni.filmsubmission.notification.ReleaseTodayReminder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    public static CharSequence REMINDER_NAME = "NOTIFICATION";
    private DailyNotification dailyNotification = new DailyNotification();
    private ReleaseTodayReminder releaseTodayReminder = new ReleaseTodayReminder();


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences_setting);
        SwitchPreference switchDailyNotif = findPreference(getString(R.string.DAILYKEY));
        SwitchPreference switchReminderNotif = findPreference(getString(R.string.RELEASEKEY));

        switchDailyNotif.setOnPreferenceChangeListener(this);
        switchReminderNotif.setOnPreferenceChangeListener(this);
        findPreference(getString(R.string.LANGUAGEKEY)).setOnPreferenceClickListener(this);


    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        boolean isSet = (boolean) newValue;
        if (key.equals(getString(R.string.DAILYKEY))) {
            if (isSet) {
                dailyNotification.setRepeatingReminder(getActivity());
            } else {
                dailyNotification.cancelNotification(getActivity());
            }
        }
        if (key.equals(getString(R.string.RELEASEKEY))) {
            if (isSet) {
                releaseTodayReminder.setRepeatingReminder(getActivity());
            } else {
                releaseTodayReminder.cancelNotification(getActivity());
            }
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals(getString(R.string.LANGUAGEKEY))) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return false;
    }
}