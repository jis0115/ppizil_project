package com.ppizil.photopicker.adapter;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ppizil.photopicker.R;

public class CustomBinding {

    @BindingAdapter("bind:setGridAdpater_4row")
    public static void setGridAdpater_4row(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("bind:photoLoad")
    public static void photoLoad(ImageView imageView, Object object) {
        if (object instanceof String) {
            Glide.with(imageView.getContext())
                    .load(object)
                    .centerCrop()
                    .into(imageView);

        } else if (object instanceof Integer) {
            Drawable drawable = imageView.getResources().getDrawable(R.drawable.ic_close);
            Glide.with(imageView.getContext())
                    .load(drawable)
                    .centerCrop()
                    .into(imageView);

        }
    }
}
