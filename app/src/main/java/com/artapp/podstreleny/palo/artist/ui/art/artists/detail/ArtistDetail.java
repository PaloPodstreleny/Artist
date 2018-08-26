package com.artapp.podstreleny.palo.artist.ui.art.artists.detail;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.artapp.podstreleny.palo.artist.GlideApp;
import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.db.entity.Artist;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistDetail extends AppCompatActivity {

    public static final String ARTIST_DETAIL = "ARTIST_DETAIL";


    @BindView(R.id.artist_name_tv)
    TextView mArtistName;

    @BindView(R.id.artist_gender_tv)
    TextView mArtistGender;

    @BindView(R.id.artist_biography_tv)
    TextView mArtistBiography;

    @BindView(R.id.artist_birhday_tv)
    TextView mArtistBirthday;

    @BindView(R.id.artist_deathday_tv)
    TextView mArtistDeathDay;

    @BindView(R.id.artist_hometown_tv)
    TextView mArtistHometown;

    @BindView(R.id.artist_location_tv)
    TextView mArtistLocation;

    @BindView(R.id.artist_nationality_tv)
    TextView mArtistNationality;

    @BindView(R.id.artist_image_thumbnail)
    ImageView mImageView;

    @BindView(R.id.toolbarShadow)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(ARTIST_DETAIL)){
            final Artist artist = intent.getParcelableExtra(ARTIST_DETAIL);

            if(artist.hasThumbnail()){
                GlideApp.with(this)
                        .load(artist.getThumbnail())
                        .fallback(R.drawable.placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(mImageView);
            }

            if(artist.hasName()){
                mArtistName.setText(artist.getName());
            }

            if(artist.hasGender()){
                mArtistGender.setText(artist.getGender());
            }

            if(artist.hasBiography()){
                mArtistBiography.setText(artist.getBiography());
            }

            if(artist.hasBirthday()){
                mArtistBirthday.setText(artist.getBirthday());
            }

            if(artist.hasDeathDay()){
                mArtistDeathDay.setText(artist.getDeathday());
            }

            if(artist.hasHometown()){
                mArtistHometown.setText(artist.getHometown());
            }

            if(artist.hasLocation()){
                mArtistLocation.setText(artist.getLocation());
            }

            if(artist.hasNationality()){
                mArtistNationality.setText(artist.getNationality());
            }



        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
