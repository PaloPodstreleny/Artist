package com.artapp.podstreleny.palo.artist.ui.art.artworks;


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
import com.artapp.podstreleny.palo.artist.db.entity.Artwork;
import com.artapp.podstreleny.palo.artist.ui.art.artworks.detail.ArtworkDetail;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtworkListAdapter extends PagedListAdapter<Artwork,ArtworkListAdapter.ArtworkViewHolder> {

    private final FragmentActivity context;
    private final Fragment fragment;

    public ArtworkListAdapter(Fragment fragment, FragmentActivity context) {
        super(new DiffUtil.ItemCallback<Artwork>() {
            @Override
            public boolean areItemsTheSame(Artwork oldItem, Artwork newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(Artwork oldItem, Artwork newItem) {
                return oldItem.getTitle().equals(newItem.getTitle());
            }
        });

        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ArtworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artwork_list_item,parent,false);
        return new ArtworkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtworkViewHolder holder, int position) {
        holder.bind(position);
    }

    public class ArtworkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.parent_layout)
        ConstraintLayout mParent;

        @BindView(R.id.artwork_name)
        TextView mNameTV;

        @BindView(R.id.artwork_image)
        ImageView mImageView;

        public ArtworkViewHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
            mParent.setOnClickListener(this);
        }

        public void bind(int position){
            //Can be nullable
            final Artwork artwork = getItem(position);
            if(artwork != null) {
                if(artwork.getTitle() != null && artwork.hasTitle()) {
                    mNameTV.setText(artwork.getTitle());
                }
                GlideApp.with(fragment)
                        .load(artwork.getThumbnail())
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .fallback(R.drawable.placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(mImageView);
            }
        }

        @Override
        public void onClick(View v) {
            final Intent intent = new Intent(context, ArtworkDetail.class);
            final Artwork artwork = getItem(getAdapterPosition());
            if(artwork != null) {
                intent.putExtra(ArtworkDetail.ARTWORK_DETAIL, artwork.getId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    final Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(
                            context,
                            mImageView,
                            mImageView.getTransitionName()).toBundle();
                    context.startActivity(intent, bundle);
                } else {

                    context.startActivity(intent);
                }
            }
        }
    }
}
