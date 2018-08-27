package com.artapp.podstreleny.palo.artist.ui.art.genes;

import android.arch.paging.PagedListAdapter;
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
import com.artapp.podstreleny.palo.artist.db.entity.Gene;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import butterknife.BindView;
import butterknife.ButterKnife;


public class GeneListAdapter extends PagedListAdapter<Gene, GeneListAdapter.GeneViewHolder> {

    private final FragmentActivity context;
    private final Fragment fragment;

    public GeneListAdapter(Fragment fragment, FragmentActivity context) {
        super(new DiffUtil.ItemCallback<Gene>() {
            @Override
            public boolean areItemsTheSame(Gene oldItem, Gene newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(Gene oldItem, Gene newItem) {
                return oldItem.getName().equals(newItem.getName());
            }
        });

        this.context = context;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public GeneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artwork_list_item, parent, false);
        return new GeneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneViewHolder holder, int position) {
        holder.bind(position);
    }

    public class GeneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.parent_layout)
        ConstraintLayout mParent;

        @BindView(R.id.artwork_name)
        TextView mNameTV;

        @BindView(R.id.artwork_image)
        ImageView mImageView;

        public GeneViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mParent.setOnClickListener(this);
        }

        public void bind(int position) {
            //Can be nullable
            final Gene gene = getItem(position);
            if (gene != null) {
                if (gene.getName() != null && gene.hasName()) {
                    mNameTV.setText(gene.getName());
                }
                GlideApp.with(fragment)
                        .load(gene.getThumbnail())
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .fallback(R.drawable.placeholder)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(mImageView);
            }
        }

        @Override
        public void onClick(View v) {
//            final Intent intent = new Intent(context, GeneDetail.class);
//            intent.putExtra(ArtworkDetail.ARTWORK_DETAIL,getItem(getAdapterPosition()));
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                final Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(
//                        context,
//                        mImageView,
//                        mImageView.getTransitionName()).toBundle();
//                context.startActivity(intent,bundle);
//            }else {
//
//                context.startActivity(intent);
//            }
        }
    }
}
