package com.artapp.podstreleny.palo.artist.ui.art;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.ui.art.artists.ArtistsFragment;
import com.artapp.podstreleny.palo.artist.ui.art.artworks.ArtworksFragment;
import com.artapp.podstreleny.palo.artist.ui.art.genes.GenesFragment;

public class ArtPager extends FragmentPagerAdapter {

    private static final int NUMBER_OF_ITEMS = 3;
    private Context context;

    public ArtPager(FragmentManager manager, Context context) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ArtworksFragment();
            case 1:
                return new ArtistsFragment();
            case 2:
                return new GenesFragment();
            default:
                throw new IllegalArgumentException(context.getString(R.string.error_not_defined_position));
        }
    }

    @Override
    public int getCount() {
        return NUMBER_OF_ITEMS;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.art_page_artworks_title);
            case 1:
                return context.getString(R.string.art_page_artists_title);
            case 2:
                return context.getString(R.string.art_page_genes_title);
            default:
                throw new IllegalArgumentException(context.getString(R.string.error_not_defined_position));
        }
    }
}
