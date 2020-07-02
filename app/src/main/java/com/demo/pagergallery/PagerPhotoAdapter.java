package com.demo.pagergallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class PagerPhotoAdapter extends ListAdapter<PhotoItem, PagerPhotoAdapter.PagerPhotoViewHolder> {

    static class PagerPhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView pagerPhoto;

        public PagerPhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            pagerPhoto = itemView.findViewById(R.id.pagerPhoto);
        }
    }

    protected PagerPhotoAdapter() {
        super(new DiffUtil.ItemCallback<PhotoItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull PhotoItem oldItem, @NonNull PhotoItem newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull PhotoItem oldItem, @NonNull PhotoItem newItem) {
                return oldItem.getId() == oldItem.getId();
            }
        });
    }

    @NonNull
    @Override
    public PagerPhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.pager_photo_view, parent, false);
        PagerPhotoViewHolder holder = new PagerPhotoViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PagerPhotoViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(getItem(position).getLargeImageURL())
                .placeholder(R.drawable.ic_baseline_image_24)
                .thumbnail(0.1f)
                .into(holder.pagerPhoto);
    }
}
