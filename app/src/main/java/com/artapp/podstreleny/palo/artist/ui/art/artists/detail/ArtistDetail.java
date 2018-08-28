package com.artapp.podstreleny.palo.artist.ui.art.artists.detail;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

    @BindView(R.id.birthday_label)
    TextView labelBirthday;

    @BindView(R.id.death_day_label)
    TextView labelDeathDay;

    @BindView(R.id.gender_label)
    TextView labelGender;

    @BindView(R.id.location_label)
    TextView labelLocation;

    @BindView(R.id.nationality_label)
    TextView labelNationality;

    @BindView(R.id.hometown_label)
    TextView labelHometown;


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
                mImageView.setVisibility(View.VISIBLE);
                GlideApp.with(this)
                        .load(artist.getThumbnail())
                        .fallback(R.drawable.placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(mImageView);

            }else {
                mImageView.setVisibility(View.GONE);
            }

            if(artist.hasName()){
                mArtistName.setText(artist.getName());
                mArtistName.setVisibility(View.VISIBLE);
            }

            if(artist.hasGender()){
                mArtistGender.setText(artist.getGender());
                mArtistGender.setVisibility(View.VISIBLE);
                labelGender.setVisibility(View.VISIBLE);
            }

            if(artist.hasBiography()){
                mArtistBiography.setText(artist.getBiography());
                mArtistBiography.setVisibility(View.VISIBLE);
            }

            if(artist.hasBirthday()){
                mArtistBirthday.setText(artist.getBirthday());
                mArtistBirthday.setVisibility(View.VISIBLE);
                labelBirthday.setVisibility(View.VISIBLE);
            }

            if(artist.hasDeathDay()){
                mArtistDeathDay.setText(artist.getDeathday());
                mArtistDeathDay.setVisibility(View.VISIBLE);
                labelDeathDay.setVisibility(View.VISIBLE);
            }

            if(artist.hasHometown()){
                mArtistHometown.setText(artist.getHometown());
                mArtistHometown.setVisibility(View.VISIBLE);
                labelHometown.setVisibility(View.VISIBLE);
            }

            if(artist.hasLocation()){
                mArtistLocation.setText(artist.getLocation());
                mArtistLocation.setVisibility(View.VISIBLE);
                labelLocation.setVisibility(View.VISIBLE);
            }

            if(artist.hasNationality()){
                mArtistNationality.setText(artist.getNationality());
                mArtistNationality.setVisibility(View.VISIBLE);
                labelNationality.setVisibility(View.VISIBLE);
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
