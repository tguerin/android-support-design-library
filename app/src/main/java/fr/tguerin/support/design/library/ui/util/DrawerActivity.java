package fr.tguerin.support.design.library.ui.util;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewStub;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.tguerin.support.design.library.R;
import fr.tguerin.support.design.library.ui.HomeActivity;
import fr.tguerin.support.design.library.ui.NavigationDrawerFragment;
import fr.tguerin.support.design.library.ui.settings.SettingsActivity;

import static fr.tguerin.support.design.library.ui.NavigationDrawerFragment.POSITION_MY_TODO;

public abstract class DrawerActivity extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment navigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence title;

    private final int contentLayout;

    private final int menuLayout;

    private final Handler handler = new Handler();

    ViewStub containerStub;

    @Nullable @Bind(R.id.toolbar) Toolbar toolbar;

    protected View containerView;

    public DrawerActivity(int contentLayout) {
        this(contentLayout, -1);
    }

    public DrawerActivity(int contentLayout, int menuLayout) {
        this.contentLayout = contentLayout;
        this.menuLayout = menuLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_activity);

        containerStub = (ViewStub) findViewById(R.id.container);
        containerStub.setLayoutResource(contentLayout);
        containerView = containerStub.inflate();

        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        navigationDrawerFragment.setSelection(getSelfNavDrawerItem());
        title = getTitle();

        // Set up the drawer.
        navigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (self(position)) {
            return;
        }
        switch (position) {
            case POSITION_MY_TODO:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HomeActivity.start(DrawerActivity.this);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }, 400);
                break;
            case NavigationDrawerFragment.POSITION_SETTINGS:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SettingsActivity.start(DrawerActivity.this);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }, 400);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationDrawerFragment.isDrawerOpen() && menuLayout != -1) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(menuLayout, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }

    public abstract int getSelfNavDrawerItem();

    public boolean self(int position) {
        return getSelfNavDrawerItem() == position;
    }
}
