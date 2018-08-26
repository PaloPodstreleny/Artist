package com.artapp.podstreleny.palo.artist.ui.art.artworks.detail;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artapp.podstreleny.palo.artist.GlideApp;
import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ArtworkDetail extends AppCompatActivity {

    public static final String ARTWORK_DETAIL = "artwork_detail";

    @BindView(R.id.artwork_image_thumbnail)
    ImageView mThumbnail;

    @BindView(R.id.artwork_title)
    TextView mTitle;

    @BindView(R.id.artwork_category)
    TextView mCategory;

    @BindView(R.id.artwork_date)
    TextView mDate;

    @BindView(R.id.artwork_published)
    TextView mPublished;

    @BindView(R.id.artwork_website)
    TextView mWebsite;

    @BindView(R.id.artwork_rights)
    TextView mRights;


    @BindView(R.id.toolbarShadow)
    Toolbar toolbar;

    @BindView(R.id.fab_share)
    FloatingActionButton mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(ARTWORK_DETAIL)){
            final Artwork artwork = intent.getParcelableExtra(ARTWORK_DETAIL);
            GlideApp.with(this)
                    .load(artwork.getThumbnail())
                    .fallback(R.drawable.placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mThumbnail);

            if(artwork.hasTitle()){
                getSupportActionBar().setTitle(artwork.getTitle());
                mTitle.setText(artwork.getTitle());
            }

            if(artwork.hasCategory()){
                mCategory.setText(artwork.getCategory());
            }

            if(artwork.hasDate()){
                mDate.setText(artwork.getDate());
            }

            if(artwork.isPublished()){
                mPublished.setText(""+artwork.isPublished());
            }

            if(artwork.hasWebsite()){
                mWebsite.setText(artwork.getWebsite());
            }

            if(artwork.hasRights()){
                mRights.setText(artwork.getImageRights());
            }

            if(artwork.isShare()){
                mButton.setVisibility(View.VISIBLE);
            }



        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ArtworkDetail.this,"Nooo",Toast.LENGTH_LONG).show();
            }
        });

    }
}
