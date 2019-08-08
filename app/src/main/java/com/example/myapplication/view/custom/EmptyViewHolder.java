package com.example.myapplication.view.custom;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemEmptyBinding;

public class EmptyViewHolder extends RecyclerView.ViewHolder {
    private ItemEmptyBinding binding;

    public EmptyViewHolder(ItemEmptyBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    public void bind(){

    }
}
