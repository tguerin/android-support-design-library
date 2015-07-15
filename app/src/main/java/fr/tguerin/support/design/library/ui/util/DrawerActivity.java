package fr.tguerin.support.design.library.ui.util;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.tguerin.support.design.library.R;
import fr.tguerin.support.design.library.ui.HomeActivity;
import fr.tguerin.support.design.library.ui.settings.SettingsActivity;

public abstract class DrawerActivity extends AppCompatActivity {

    private final int contentLayout;

    private final int menuLayout;

    protected final Handler handler = new Handler();

    ViewStub containerStub;
    @Nullable @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
    @Bind(R.id.navigation_view) NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

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

        toolbar = (Toolbar) containerView.findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(
                this,                    /* host Activity */
                drawerLayout,                    /* DrawerLayout object */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        // Defer code dependent on restoration of previous instance state.
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });

        drawerLayout.setDrawerListener(drawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                onNavigationDrawerItemSelected(menuItem);
                return true;
            }
        });
        navigationView.getMenu().findItem(getSelfNavDrawerItem()).setChecked(true);
    }


    public void onNavigationDrawerItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (self(itemId)) {
            return;
        }
        switch (itemId) {
            case R.id.my_todos:
                drawerLayout.closeDrawer(navigationView);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        HomeActivity.start(DrawerActivity.this);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }, 400);
                break;
            case R.id.settings:
                drawerLayout.closeDrawer(navigationView);
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
        if (!drawerLayout.isDrawerOpen(navigationView) && menuLayout != -1) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(menuLayout, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    public abstract int getSelfNavDrawerItem();

    public boolean self(int position) {
        return getSelfNavDrawerItem() == position;
    }
}
