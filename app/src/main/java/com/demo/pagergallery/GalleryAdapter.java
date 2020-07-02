package com.demo.pagergallery;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import io.supercharge.shimmerlayout.ShimmerLayout;

class GalleryAdapter extends ListAdapter<PhotoItem, GalleryAdapter.GalleryViewHolder> {
    private static final String TAG = "GalleryAdapter";

    protected GalleryAdapter() {
        super(new DiffUtil.ItemCallback<PhotoItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull PhotoItem oldItem, @NonNull PhotoItem newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull PhotoItem oldItem, @NonNull PhotoItem newItem) {
                return oldItem.getPreviewURL().equals(newItem.getPreviewURL());
            }
        });
    }

    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        ShimmerLayout shimmerLayout;
        ImageView imageView;

        GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            shimmerLayout = itemView.findViewById(R.id.shimmerLayout);
            imageView = itemView.findViewById(R.id.imageView2);
        }
    }


    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        final GalleryViewHolder galleryViewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(R.layout.item_photo, parent, false);

        galleryViewHolder = new GalleryViewHolder(itemView);
        galleryViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 这里得到了当前显示内容的PhotoItem对象
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("photo_list", new ArrayList<>(getCurrentList()));
                // Log.d(TAG, "position: " + galleryViewHolder.getAdapterPosition());
                bundle.putInt("photo_position", galleryViewHolder.getAdapterPosition());
                NavController navController = Navigation.findNavController(itemView);
                navController.navigate(R.id.action_galleryFragment_to_pagerPhotoFragment, bundle);
            }
        });
        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {
        if (getItemCount() - 1 == position) {
            return;
        }
        holder.shimmerLayout.setShimmerColor(0x55ffffff);
        holder.shimmerLayout.setShimmerAngle(0);
        holder.shimmerLayout.startShimmerAnimation();
        Glide.with(holder.itemView)
                .load(getItem(position).getLargeImageURL())
                .placeholder(R.drawable.ic_baseline_image_24)
                .thumbnail(0.1f)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (holder.shimmerLayout != null) {
                            holder.shimmerLayout.stopShimmerAnimation();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if (holder.shimmerLayout != null) {
                            holder.shimmerLayout.stopShimmerAnimation();
                        }
                        return false;
                    }
                })
                .into(holder.imageView);

    }
}


