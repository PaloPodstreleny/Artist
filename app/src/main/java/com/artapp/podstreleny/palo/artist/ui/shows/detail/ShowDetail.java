package com.artapp.podstreleny.palo.artist.ui.shows.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artapp.podstreleny.palo.artist.GlideApp;
import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.db.entity.Show;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowDetail extends AppCompatActivity {

    public static final String SHOW_DETAIL = "SHOW_DETAIl";

    @BindView(R.id.show_name_tv)
    TextView mShowName;

    @BindView(R.id.show_description_tv)
    TextView mShowDescription;

    @BindView(R.id.show_status_tv)
    TextView mShowStatus;

    @BindView(R.id.show_thumbnail_iv)
    ImageView mShowImageView;

    @BindView(R.id.show_press_release_tv)
    TextView mShowPressRelease;

    @BindView(R.id.toolbarShadow)
    android.support.v7.widget.Toolbar mToolbar;

    @BindView(R.id.status_iv)
    ImageView mStatusIV;

    @BindView(R.id.start_day_iv)
    ImageView mStarEndImageView;

    @BindView(R.id.show_start_end_tv)
    TextView mStartEndDayTv;

    @BindView(R.id.solo_group_iv)
    ImageView mSoloGroupIV;

    @BindView(R.id.solog_group_tv)
    TextView mSoloGroupTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.detail_show_title);
        }


        final Intent intent = getIntent();
        if(intent != null && intent.hasExtra(SHOW_DETAIL)){
            final Show show = intent.getParcelableExtra(SHOW_DETAIL);

            if(show.hasThumbnail()){
                GlideApp.with(this)
                        .load(show.getThumbnail())
                        .placeholder(R.drawable.placeholder)
                        .fallback(R.drawable.placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(mShowImageView);
            }else {
                mShowImageView.setVisibility(View.GONE);
            }


            if(show.hasItemTitle()){
                mShowName.setText(show.getName());
                mToolbar.setTitle(show.getName());

            }

            if(show.hasStatus()){
                mShowStatus.setText(show.getStatus());
                mStatusIV.setVisibility(View.VISIBLE);
            }

            if(show.hasStartDate() && show.hasEndDate()){
                mStartEndDayTv.setText(show.getStartEndDate());
                mStarEndImageView.setVisibility(View.VISIBLE);
            }

            if(show.isSoloShow()){
                mSoloGroupIV.setVisibility(View.VISIBLE);
                mSoloGroupTV.append(getString(R.string.show_detail_solo_person));
            }

            if(show.isGroupShow()){
                mSoloGroupIV.setVisibility(View.VISIBLE);
                mSoloGroupTV.append(getString(R.string.show_detail_group_people));
            }

            if(show.hasDescription()){
                mShowDescription.setText(show.getDescription());

            }

            if(show.hasPressRelease()){
                mShowPressRelease.setText(show.getPressRelease());
            }


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }
}
