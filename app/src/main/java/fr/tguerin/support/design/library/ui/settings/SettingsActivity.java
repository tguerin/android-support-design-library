package fr.tguerin.support.design.library.ui.settings;

import android.content.Context;
import android.content.Intent;

import fr.tguerin.support.design.library.R;
import fr.tguerin.support.design.library.ui.util.DrawerActivity;

public class SettingsActivity extends DrawerActivity {
    public SettingsActivity() {
        super(R.layout.settings_activity);
    }

    @Override
    public int getSelfNavDrawerItem() {
        return R.id.settings;
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, SettingsActivity.class));
    }
}
