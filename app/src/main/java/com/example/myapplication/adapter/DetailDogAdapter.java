package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.BuildConfig;
import com.example.myapplication.data.model.DogDetailModel;
import com.example.myapplication.databinding.ItemDetailDogBinding;

public class DetailDogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private DogDetailModel model;

    public DetailDogAdapter(Context context, DogDetailModel model) {
        this.context = context;
        this.model = model;
    }

    public void adapterNotify() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDetailDogBinding binding = ItemDetailDogBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(context, binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bind(model, position);
        }
    }

    @Override
    public int getItemCount() {
        return model.getInfo().getImgs().size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private ItemDetailDogBinding binding;

        public ItemViewHolder(Context context, ItemDetailDogBinding binding) {
            super(binding.getRoot());
            this.context = context;
            this.binding = binding;
        }

        public void bind(DogDetailModel model, int pos) {
            String path = BuildConfig.ImageBaseURL + model.getInfo().getImgs().get(pos).getImg();
            Glide.with(context)
                    .load(path)
                    .centerCrop()
                    .into(binding.imageDog);
            binding.executePendingBindings();
        }


    }
}
