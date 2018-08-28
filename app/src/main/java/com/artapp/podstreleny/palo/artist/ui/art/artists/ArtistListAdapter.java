package com.artapp.podstreleny.palo.artist.ui.art.artists;


import android.app.ActivityOptions;
import android.arch.paging.PagedListAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artapp.podstreleny.palo.artist.GlideApp;
import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.db.entity.Artist;
import com.artapp.podstreleny.palo.artist.ui.art.artists.detail.ArtistDetail;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistListAdapter extends PagedListAdapter<Artist,ArtistListAdapter.ArtistViewHolder>{


    private final FragmentActivity context;
    private final Fragment fragment;

    public ArtistListAdapter(Fragment fragment, FragmentActivity context) {
        super(new DiffUtil.ItemCallback<Artist>() {
            @Override
            public boolean areItemsTheSame(Artist oldItem, Artist newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(Artist oldItem, Artist newItem) {
                return oldItem.getId().equals(newItem.getId());
            }
        });

        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artwork_list_item,parent,false);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder holder, int position) {
        holder.bind(position);
    }

    public class ArtistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.parent_layout)
        ConstraintLayout mParent;

        @BindView(R.id.artwork_name)
        TextView mNameTV;

        @BindView(R.id.artwork_image)
        ImageView mImageView;

        public ArtistViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
            mParent.setOnClickListener(this);
        }

        public void bind(int position){
            final Artist artist = getItem(position);
            if (artist != null){
                if(artist.getName() != null && artist.hasName()) {
                    mNameTV.setText(artist.getName());
                }
                GlideApp.with(fragment)
                        .load(artist.getThumbnail())
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .fallback(R.drawable.placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(mImageView);
            }
        }

        @Override
        public void onClick(View v) {
            final Intent intent = new Intent(context, ArtistDetail.class);
            intent.putExtra(ArtistDetail.ARTIST_DETAIL,getItem(getAdapterPosition()));
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                final Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(
                        context,
                        mImageView,
                        mImageView.getTransitionName()).toBundle();
                context.startActivity(intent,bundle);
            }else {

                context.startActivity(intent);
            }
        }
    }



}