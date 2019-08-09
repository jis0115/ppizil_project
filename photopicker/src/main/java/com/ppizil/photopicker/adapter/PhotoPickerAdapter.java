package com.ppizil.photopicker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ppizil.photopicker.databinding.ItemPhotoBinding;
import com.ppizil.photopicker.viewmodel.PhotoPickerViewModel;

public class PhotoPickerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private PhotoPickerViewModel viewModel;

    public PhotoPickerAdapter(PhotoPickerViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void adapterNotify() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPhotoBinding binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bind(viewModel, position);
        }
    }

    @Override
    public int getItemCount() {
        return viewModel.getPhotoListModel().getPhotoList().size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemPhotoBinding binding;

        public ItemViewHolder(ItemPhotoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PhotoPickerViewModel viewModel, int pos) {
            binding.setViewmodel(viewModel);
            binding.setPos(pos);
            binding.executePendingBindings();
        }
    }
}
