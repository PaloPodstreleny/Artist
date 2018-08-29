package com.artapp.podstreleny.palo.artist.ui.shows;

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
import com.artapp.podstreleny.palo.artist.db.entity.Show;
import com.artapp.podstreleny.palo.artist.ui.shows.detail.ShowDetail;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShowListAdapter extends PagedListAdapter<Show, ShowListAdapter.ShowViewHolder> {

    private final FragmentActivity context;
    private final Fragment fragment;

    public ShowListAdapter(Fragment fragment, FragmentActivity context) {
        super(new DiffUtil.ItemCallback<Show>() {
            @Override
            public boolean areItemsTheSame(Show oldItem, Show newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(Show oldItem, Show newItem) {
                return oldItem.getName().equals(newItem.getName());
            }
        });

        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artwork_list_item, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowViewHolder holder, int position) {
        holder.bind(position);
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.parent_layout)
        ConstraintLayout mParent;

        @BindView(R.id.artwork_name)
        TextView mNameTV;

        @BindView(R.id.artwork_image)
        ImageView mImageView;

        public ShowViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mParent.setOnClickListener(this);
        }

        public void bind(int position) {
            //Can be nullable
            final Show show = getItem(position);
            if (show != null) {
                if (show.hasItemTitle()) {
                    mNameTV.setText(show.getItemTitle());
                }
                GlideApp.with(fragment)
                        .load(show.getItemThumbnail())
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .fallback(R.drawable.placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(mImageView);
            }
        }

        @Override
        public void onClick(View v) {
            final Intent intent = new Intent(context, ShowDetail.class);
            intent.putExtra(ShowDetail.SHOW_DETAIL,getItem(getAdapterPosition()));
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
