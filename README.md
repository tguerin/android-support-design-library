# android-support-design-library

Add dependency :

```groovy
compile 'com.android.support:design:22.2.0'
```


Use Snackbar :

```java
Snackbar.make(containerView,"content to show", LENGTH_SHORT).show()
```


Use FloatingActionButton :

```xml
<android.support.design.widget.FloatingActionButton
        android:id="@+id/floating_action"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="16dp"
        android:src="@drawable/ic_add_white_36dp"
        app:borderWidth="0dp"
        app:elevation="6dp"
        app:pressedTranslationZ="12dp"/>
```


Use CoordinatorLayout :

Just make TodoListView extends CoordinatorLayout

Use AppBarLayout :

```xml
<android.support.design.widget.AppBarLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:elevation="4dp">

        <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:background="@color/colorPrimary"
            android:minHeight="?attr/actionBarSize"
            app:layout_collapseMode="pin"
            app:layout_scrollFlags="scroll|enterAlways"
            app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"/>

</android.support.design.widget.AppBarLayout>
```

```xml
app:layout_behavior="@string/appbar_scrolling_view_behavior"
```


* scroll: this flag should be set for all views that want to scroll off the screen - for views that do not use this flag, they’ll remain pinned to the top of the screen
* enterAlways: this flag ensures that any downward scroll will cause this view to become visible, enabling the ‘quick return’ pattern
* enterAlwaysCollapsed: When your view has declared a minHeight and you use this flag, your View will only enter at its minimum height (i.e., ‘collapsed’), only re-expanding to its full height when the scrolling view has reached it’s top.
* exitUntilCollapsed: this flag causes the view to scroll off until it is ‘collapsed’ (its minHeight) before exiting


Use CollapsibleToolbarLayout :

```xml
<android.support.design.widget.AppBarLayout
    android:layout_width="match_parent"
    android:layout_height="192dp"
    android:elevation="4dp">

    <android.support.design.widget.CollapsingToolbarLayout
        android:id="@+id/collapsing_toolbar_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:collapsedTitleTextAppearance="@style/CollapsedTitleTextAppearance"
        app:contentScrim="?attr/colorPrimary"
        app:expandedTitleTextAppearance="@style/ExpandedTitleTextAppearance"
        app:layout_scrollFlags="scroll|enterAlways|enterAlwaysCollapsed">

        <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:background="@android:color/transparent"
            android:minHeight="?attr/actionBarSize"
            app:layout_collapseMode="pin"
            app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"/>

    </android.support.design.widget.CollapsingToolbarLayout>

</android.support.design.widget.AppBarLayout>
```

```xml
app:collapsedTitleTextAppearance="@style/CollapsedTitleTextAppearance"
app:expandedTitleTextAppearance="@style/ExpandedTitleTextAppearance"

<style name="CollapsedTitleTextAppearance" parent="@style/TextAppearance.AppCompat.Widget.ActionBar.Title">
    <item name="android:textColor">@android:color/white</item>
</style>

<style name="ExpandedTitleTextAppearance" parent="@style/TextAppearance.AppCompat.Headline">
    <item name="android:textColor">@android:color/white</item>
</style>
```

```xml
app:expandedTitleMarginStart="64dp"
```

Use parallax :

```xml
<ImageView xmlns:android="http://schemas.android.com/apk/res/android"
                       android:layout_width="match_parent"
                       android:layout_height="match_parent"
                       android:scaleType="centerCrop"
                       android:src="@drawable/landscape"
                       app:layout_collapseMode="parallax"
                       app:layout_collapseParallaxMultiplier="0.7"/>
```


Fit system window :

```xml
android:fitsSystemWindows="true"
```


Use NavigationView :


```java
@Bind(R.id.drawer_layout) DrawerLayout drawerLayout;
@Bind(R.id.navigation_view) NavigationView navigationView;
private ActionBarDrawerToggle drawerToggle;


private void setUpNavigationDrawer() {
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
```

```xml
<android.support.design.widget.NavigationView
    android:id="@+id/navigation_view"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    android:layout_gravity="start"
    app:menu="@menu/drawer"/>
```


```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <group android:checkableBehavior="single">
        <item
            android:id="@+id/my_todos"
            android:title="@string/title_my_todos"/>
        <item
            android:id="@+id/settings"
            android:title="@string/title_settings"/>
    </group>
</menu>
```



Add header :

```xml
app:headerLayout="@layout/drawer_header"
```
