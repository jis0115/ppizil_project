package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.data.entity.DogListEntity;
import com.example.myapplication.databinding.ItemDogListBinding;
import com.example.myapplication.databinding.ItemEmptyBinding;
import com.example.myapplication.view.custom.EmptyViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DogListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_ITEM = 1;

    private Context context;
    private List<DogListEntity> items;

    public DogListAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    public void addItems(List<DogListEntity> items) {
        items.addAll(items);
    }

    public void replaceItems(List<DogListEntity> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            ItemEmptyBinding binding = ItemEmptyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new EmptyViewHolder(binding);
        } else {
            ItemDogListBinding binding = ItemDogListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        int size = items.size();
        size=20;
        if (size == 0) {
            return 1;
        } else {
            return size;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int size = items.size();
        size=20;
        if (size == 0) {
            return TYPE_EMPTY;
        } else {
            return TYPE_ITEM;
        }
    }

    // 정적 스태틱 클래스
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ItemDogListBinding binding;

        public ItemViewHolder(ItemDogListBinding binding) {
            super(binding.getRoot());
        }
    }
}
