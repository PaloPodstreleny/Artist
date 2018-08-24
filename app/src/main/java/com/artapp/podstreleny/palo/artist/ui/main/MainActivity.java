package com.artapp.podstreleny.palo.artist.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.utils.ArtysToken;
import com.artapp.podstreleny.palo.artist.utils.TokenUtil;


public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView mTextMessage;
    private SharedPreferences mPreferences;
    private TokenUtil mTokenUtil;
    private String mToken;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getSharedPreferences(getString(R.string.token_file_key), Context.MODE_PRIVATE);
        mTokenUtil = new TokenUtil(this,mPreferences);

        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getToken.observe(this, new Observer<Resource<ArtysToken>>() {
            @Override
            public void onChanged(@Nullable Resource<ArtysToken> token) {
                if(token != null){
                    switch (token.getStatus()){
                        case ERROR:
                            //Network problem
                            Log.v(MainActivity.this.getLocalClassName(),"Error");
                            break;
                        case LOADING:
                            //Loading
                            Log.v(MainActivity.this.getLocalClassName(),"Loading");
                            break;
                        case SUCCESS:
                            if(token.getData() != null && token.getData().getToken().length() > 0){
                                mTokenUtil.saveToken(token.getData());
                                Log.v(MainActivity.this.getLocalClassName(),"Saved token");
                            }
                            break;
                        case UNAUTHORIZED:
                            //Unauthorized
                            Log.v(MainActivity.this.getLocalClassName(),"Unauthorized");
                            break;

                    }
                }
            }
        });

        if (!mTokenUtil.hasToken()){
            viewModel.fetchToken();
        }else {
            mToken = mTokenUtil.getToken();
            Toast.makeText(this,"Token: "+mToken,Toast.LENGTH_LONG).show();
        }

        mTextMessage = (TextView) findViewById(R.id.message);
        Button mButton = findViewById(R.id.button);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    @Override
    protected void onStart() {
        super.onStart();
        getSharedPreferences(getString(R.string.token_file_key),Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getSharedPreferences(getString(R.string.token_file_key),Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        final String token = sharedPreferences.getString(key,null);
        if(token != null){
            mToken = token;
        }
    }
}
