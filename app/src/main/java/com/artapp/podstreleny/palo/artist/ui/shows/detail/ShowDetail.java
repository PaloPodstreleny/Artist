package com.artapp.podstreleny.palo.artist.ui.shows.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @BindView(R.id.show_start_at_tv)
    TextView mShowStartDate;

    @BindView(R.id.show_end_at_tv)
    TextView mShowEndDate;

    @BindView(R.id.show_press_release_tv)
    TextView mShowPressRelease;

    @BindView(R.id.show_solo_tv)
    TextView mShowSolo;

    @BindView(R.id.show_group_tv)
    TextView mShowGroup;

    @BindView(R.id.toolbarShadow)
    android.support.v7.widget.Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        ButterKnife.bind(this);

        mToolbar.setTitle(R.string.detail_show_title);

        final Intent intent = getIntent();
        if(intent != null && intent.hasExtra(SHOW_DETAIL)){
            final Show show = intent.getParcelableExtra(SHOW_DETAIL);

            GlideApp.with(this)
                    .load(show.getThumbnail())
                    .placeholder(R.drawable.placeholder)
                    .fallback(R.drawable.placeholder)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(mShowImageView);

            if(show.hasItemTitle()){
                mShowName.setText(show.getName());
                mToolbar.setTitle(show.getName());

            }

            if(show.hasDescription()){
                mShowDescription.setText(show.getDescription());

            }

            if(show.hasStatus()){
                mShowStatus.setText(show.getStatus());
            }

            if(show.hasStartDate()){
                mShowStartDate.setText(show.getStartAt());
            }

            if(show.hasEndDate()){
                mShowEndDate.setText(show.getEndAt());
            }

            if(show.hasPressRelease()){
                mShowPressRelease.setText(show.getPressRelease());
            }


            mShowSolo.setText(String.valueOf(show.isSoloShow()));
            mShowGroup.setText(String.valueOf(show.isGroupShow()));


        }



    }
}
