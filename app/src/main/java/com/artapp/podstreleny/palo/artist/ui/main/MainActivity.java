package com.artapp.podstreleny.palo.artist.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.services.NotificationJobService;
import com.artapp.podstreleny.palo.artist.ui.art.ArtFragment;
import com.artapp.podstreleny.palo.artist.ui.settings.SettingsFragment;
import com.artapp.podstreleny.palo.artist.ui.shows.ShowFragment;
import com.artapp.podstreleny.palo.artist.utils.SchedularUtil;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    private static final String ITEM_ID = "item_id";
    private static final String ITEM_HEADING = "item_heading";
    private int selectedItem;
    private String selectedHeading;

    private ActionBar actionBar;

    @BindView(R.id.toolbarShadow)
    Toolbar mToolbar;

    private FragmentManager mFragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_art:
                    setFragmentManager();
                    selectedItem = R.id.navigation_art;
                    selectedHeading = getString(R.string.title_art);
                    actionBar.setTitle(selectedHeading);
                    mFragmentManager.beginTransaction().replace(R.id.fragment_fl, new ArtFragment()).commit();
                    return true;
                case R.id.navigation_shows:
                    setFragmentManager();
                    selectedItem = R.id.navigation_shows;
                    selectedHeading = getString(R.string.shows_title);
                    actionBar.setTitle(selectedHeading);
                    mFragmentManager.beginTransaction().replace(R.id.fragment_fl,new ShowFragment()).commit();
                    return true;
                case R.id.navigation_settings:
                    setFragmentManager();
                    selectedItem = R.id.navigation_settings;
                    selectedHeading = getString(R.string.title_settings);
                    actionBar.setTitle(selectedHeading);
                    mFragmentManager.beginTransaction().replace(R.id.fragment_fl,new SettingsFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //Set toolbar
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();

        //Set default values for preferences - settings
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        mFragmentManager = getSupportFragmentManager();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState != null && savedInstanceState.containsKey(ITEM_ID) && savedInstanceState.containsKey(ITEM_HEADING)) {
            selectedItem = savedInstanceState.getInt(ITEM_ID);
            selectedHeading = savedInstanceState.getString(ITEM_HEADING);
            actionBar.setTitle(savedInstanceState.getString(ITEM_HEADING));
        } else {
            selectedItem = R.id.navigation_art;
            navigation.setSelectedItemId(selectedItem);
        }


        SchedularUtil schedularUtil = SchedularUtil.getInstance(this);
        schedularUtil.scheduleJobs();

    }

    private void setFragmentManager(){
        if(mFragmentManager == null){
            mFragmentManager = getSupportFragmentManager();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ITEM_ID, selectedItem);
        outState.putString(ITEM_HEADING, selectedHeading);
        super.onSaveInstanceState(outState);
    }


}
