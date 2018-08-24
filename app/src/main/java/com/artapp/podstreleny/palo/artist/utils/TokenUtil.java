package com.artapp.podstreleny.palo.artist.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.artapp.podstreleny.palo.artist.R;

public final class TokenUtil {

    private SharedPreferences mPreferences;
    private Context mContext;

    public TokenUtil(Context context, SharedPreferences sharedPreferences){
        mPreferences = sharedPreferences;
        mContext = context;
    }

    public synchronized void saveToken(@Nullable ArtysToken token){
        if(token != null && token.getToken().length() > 0 && token.getExpireDate().length() > 0) {
            final SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(mContext.getString(R.string.token_entry_value), token.getToken());
            editor.apply();
            editor.putString(mContext.getString(R.string.token_entry_date), token.getExpireDate());
            editor.apply();
        }
    }


    @Nullable
    public String getToken(){
        return mPreferences.getString(mContext.getString(R.string.token_entry_value),null);
    }

    public boolean hasToken(){
        return ((mPreferences.getString(mContext.getString(R.string.token_entry_value),null) != null));
    }


}
