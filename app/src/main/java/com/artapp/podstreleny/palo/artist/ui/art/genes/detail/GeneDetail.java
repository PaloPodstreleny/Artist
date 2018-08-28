package com.artapp.podstreleny.palo.artist.ui.art.genes.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.artapp.podstreleny.palo.artist.GlideApp;
import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.db.entity.Gene;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeneDetail extends AppCompatActivity {

    public static final String GENE_DETAIL = "gene_detail";

    @BindView(R.id.gene_name_tv)
    TextView mGeneName;

    @BindView(R.id.gene_description_tv)
    TextView mGeneDescription;

    @BindView(R.id.gene_thumbnail_iv)
    ImageView mImageView;

    @BindView(R.id.toolbarShadow)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gene_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        final ActionBar bar = getSupportActionBar();

        if (bar != null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }


        final Intent intent = getIntent();
        if (intent != null && intent.hasExtra(GENE_DETAIL)) {
            final Gene gene = intent.getParcelableExtra(GENE_DETAIL);

            if(gene.hasThumbnail()){
                GlideApp.with(this)
                        .load(gene.getThumbnail())
                        .fallback(R.drawable.placeholder)
                        .placeholder(R.drawable.placeholder)
                        .into(mImageView);
            }else {
                mImageView.setVisibility(View.GONE);
            }


            if(gene.hasName()){
                mGeneName.setText(gene.getName());
                toolbar.setTitle(gene.getName());
            }

            if(gene.hasDescription()){
                mGeneDescription.setText(gene.getDescription());
            }

        }

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



