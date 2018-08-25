package com.artapp.podstreleny.palo.artist.ui.art.artworks;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.network.Resource;
import com.artapp.podstreleny.palo.artist.utils.ArtysToken;
import com.artapp.podstreleny.palo.artist.utils.TokenUtil;

import java.util.List;


public class ArtworksFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = ArtworksFragment.class.getSimpleName();
    private TokenUtil mTokenUtil;
    private SharedPreferences mSharedPreferences;
    private ArtworksViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.artworks_fragment,container,false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Context context = getContext();
        if(context != null){
            mSharedPreferences = context.getSharedPreferences(getString(R.string.token_file_key),Context.MODE_PRIVATE);
            mTokenUtil = new TokenUtil(context,mSharedPreferences);
        }


        mViewModel = ViewModelProviders.of(this).get(ArtworksViewModel.class);
        mViewModel.getArtworks().observe(this, new Observer<Resource<List<Artwork>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Artwork>> listResource) {
                if (listResource != null){
                    Log.v(TAG,"Weeeeee");
                }
            }
        });

        mViewModel.getToken().observe(this, new Observer<Resource<ArtysToken>>() {
            @Override
            public void onChanged(@Nullable Resource<ArtysToken> tokenResource) {
                if(tokenResource != null){
                    switch (tokenResource.getStatus()){
                        case LOADING:

                            Log.v(TAG,"Token is loading");
                            break;
                        case ERROR:
                            Log.v(TAG,"Token error");
                            break;
                        case SUCCESS:
                            if(mTokenUtil != null && tokenResource.getData() != null){
                                mTokenUtil.saveToken(tokenResource.getData());
                            }
                            break;
                    }
                }
            }
        });

        if (mTokenUtil != null && !mTokenUtil.hasToken()){
            mViewModel.fetchToken();
        }else if(mTokenUtil != null && mTokenUtil.getToken() != null){
            mViewModel.setToken(mTokenUtil.getToken());
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        final Context context = getContext();
        if(context != null){
            context.getSharedPreferences(getString(R.string.token_file_key),Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this);
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        final Context context = getContext();
        if(context != null){
            context.getSharedPreferences(getString(R.string.token_file_key),Context.MODE_PRIVATE).unregisterOnSharedPreferenceChangeListener(this);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        final String newToken = sharedPreferences.getString(key,null);
        if(newToken != null){
            mViewModel.setToken(newToken);
        }
    }
}
