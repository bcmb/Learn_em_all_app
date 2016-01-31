package cf.bcmbx.learnemall.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cf.bcmbx.learnemall.R;
import cf.bcmbx.learnemall.fragments.AboutFragment;
import cf.bcmbx.learnemall.fragments.AddSingleWordFragment;
import cf.bcmbx.learnemall.fragments.AllWordsFragment;
import cf.bcmbx.learnemall.fragments.LearnFragment;

public class MainActivity extends ActionBarActivity {

    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mDrawerArray;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        prefs = getSharedPreferences("cf.bcmbx.learnemall", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            loadInitialFragment(true);
            prefs.edit().putBoolean("firstrun", false).commit();
            PreferenceManager.setDefaultValues(this, R.xml.prefs, false);
        } else {
            loadInitialFragment(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void addDrawerItems() {
        mDrawerArray  = getResources().getStringArray(R.array.navDrawer);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerArray);
        mDrawerList.setAdapter(mAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectDrawerItem(position);
                mDrawerList.setItemChecked(position, true);
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.app_name);
                View view = MainActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                invalidateOptionsMenu();
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void selectDrawerItem(int menuItem) {
        Fragment fragment = null;

        Class fragmentClass = null;
        switch(menuItem) {
            case 0:
                fragmentClass = LearnFragment.class;
                getSupportActionBar().setTitle(mDrawerArray[0]);
                //mDrawerList.mDrawerLayout.getItem(0).setChecked(true);
                break;
            case 1:
                fragmentClass = AllWordsFragment.class;
                getSupportActionBar().setTitle(mDrawerArray[1]);
                break;
            case 2:
                fragmentClass = AddSingleWordFragment.class;
                getSupportActionBar().setTitle(mDrawerArray[2]);
                break;
            case 3:
                fragmentClass = AboutFragment.class;
                getSupportActionBar().setTitle(mDrawerArray[3]);
                break;
            default:
        }

        try {
                fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fragment).commit();
        mDrawerLayout.closeDrawers();
    }

    private void loadInitialFragment(boolean ifFirstLaunch) {
        Fragment myFragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (ifFirstLaunch) {
            myFragment = new AboutFragment();
            getSupportActionBar().setTitle(mDrawerArray[3]);
        } else {
            myFragment = new LearnFragment();
        }
        fragmentTransaction.add(R.id.main_frame, myFragment);
        fragmentTransaction.commit();
    }
}
