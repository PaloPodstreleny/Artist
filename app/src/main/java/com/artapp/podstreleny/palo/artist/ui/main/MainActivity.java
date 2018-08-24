package com.artapp.podstreleny.palo.artist.ui.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.ui.art.ArtFragment;
import com.artapp.podstreleny.palo.artist.ui.settings.SettingsFragment;


public class MainActivity extends AppCompatActivity {

    private static final String ITEM_ID = "item_id";
    private int selectedItem;


    private FragmentManager mFragmentManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_art:
                    if(mFragmentManager == null){
                        mFragmentManager = getSupportFragmentManager();
                    }
                    selectedItem = R.id.navigation_art;
                    mFragmentManager.beginTransaction().replace(R.id.fragment_fl, new ArtFragment()).commit();
                    return true;
                case R.id.navigation_shows:
                    return true;
                case R.id.navigation_settings:
                    if(mFragmentManager == null){
                        mFragmentManager = getSupportFragmentManager();
                    }
                    selectedItem = R.id.navigation_settings;
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

        mFragmentManager = getSupportFragmentManager();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState != null && savedInstanceState.containsKey(ITEM_ID)) {
            selectedItem = savedInstanceState.getInt(ITEM_ID);
        } else {
            selectedItem = R.id.navigation_art;
        }

        navigation.setSelectedItemId(selectedItem);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ITEM_ID, selectedItem);
        super.onSaveInstanceState(outState);
    }
}
