package com.artapp.podstreleny.palo.artist.ui;


import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artapp.podstreleny.palo.artist.GlideApp;
import com.artapp.podstreleny.palo.artist.R;
import com.artapp.podstreleny.palo.artist.db.entity.IListItem;
import com.artapp.podstreleny.palo.artist.db.entity.Show;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;;

import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
//
//public  class GeneralItemListAdapter extends PagedListAdapter<, GeneralItemListAdapter.GeneralViewHolder>{
//
//
//    private final Fragment fragment;
//    private final OnListItemClickListener listener;
//
//    public interface OnListItemClickListener{
//        void onClick(IListItem item, ImageView imageView);
//    }
//
//    public GeneralItemListAdapter(@NonNull DiffUtil.ItemCallback<T> callback, Fragment fragment, OnListItemClickListener listener) {
//        super(callback);
//        this.fragment = fragment;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public GeneralViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artwork_list_item,parent,false);
//        return new GeneralViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull GeneralViewHolder holder, int position) {
//        onBindViewHolder(holder,position);
//        try{
//            final Show show = (Show) getItem(position);
//            holder.bind(show);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    public class GeneralViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//
//        @BindView(R.id.parent_layout)
//        ConstraintLayout mParent;
//
//        @BindView(R.id.artwork_name)
//        TextView mNameTV;
//
//        @BindView(R.id.artwork_image)
//        ImageView mImageView;
//
//        public GeneralViewHolder(View view){
//            super(view);
//            ButterKnife.bind(this,view);
//            mParent.setOnClickListener(this);
//        }
//
//        public void bind(Show show){
//            if (show != null){
//                if(show.hasItemTitle()) {
//                    mNameTV.setText(show.getItemTitle());
//                }
//                GlideApp.with(fragment)
//                        .load(show.getItemThumbnail())
//                        .centerCrop()
//                        .placeholder(R.drawable.placeholder)
//                        .fallback(R.drawable.placeholder)
//                        .transition(DrawableTransitionOptions.withCrossFade())
//                        .into(mImageView);
//            }
//        }
//
//        @Override
//        public void onClick(View v) {
//            listener.onClick(getItem(getAdapterPosition()),mImageView);
//        }
//    }
//
//
//
//}