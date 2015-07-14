package fr.tguerin.support.design.library.ui.settings;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import fr.tguerin.support.design.library.R;
import fr.tguerin.support.design.library.TodoApplication;

public class SettingsFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        String versionName = getString(R.string.settings_about_version_title, getVersionName());
        findPreference(getString(R.string.settings_about_version_key)).setTitle(versionName);
    }

    public static String getVersionName() {
        String versionName = null;
        try {
            TodoApplication context = TodoApplication.get();
            versionName = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException ignore) {
        }
        return versionName;
    }
}
