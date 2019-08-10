package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;
import com.example.myapplication.data.entity.DogListEntity;
import com.example.myapplication.data.model.DogListModel;
import com.example.myapplication.databinding.ItemDogListBinding;
import com.example.myapplication.databinding.ItemEmptyBinding;
import com.example.myapplication.network.DogInfoApi;
import com.example.myapplication.network.dto.DogListDto;
import com.example.myapplication.view.activity.DetailDogActivity;
import com.example.myapplication.view.custom.EmptyViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DogListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_ITEM = 1;

    private Context context;
    private DogListModel model;

    public DogListAdapter(Context context, DogListModel model) {
        this.context = context;
        this.model = model;
    }

    public void adapterNotify() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            ItemEmptyBinding binding = ItemEmptyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new EmptyViewHolder(binding);
        } else {
            ItemDogListBinding binding = ItemDogListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ItemViewHolder(context,binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmptyViewHolder) {

        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bind(model, position);
        }

    }

    @Override
    public int getItemCount() {
        int size = model.getDoglistEntity().getDogs().size();
        if (size == 0) {
            return 1;
        } else {
            return size;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int size = model.getDoglistEntity().getDogs().size();
        if (size == 0) {
            return TYPE_EMPTY;
        } else {
            return TYPE_ITEM;
        }
    }

    // 정적 스태틱 클래스
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemDogListBinding binding;
        private Context context;
        private DogListDto.DetailInfo dogListDto;

        public ItemViewHolder(Context context, ItemDogListBinding binding) {
            super(binding.getRoot());
            this.context = context;
            this.binding = binding;
            setListener();
        }

        public void setListener() {
            binding.dogImage.setOnClickListener(this);
        }

        public void bind(DogListModel model, int pos) {
            dogListDto = model.getDoglistEntity().getDogs().get(pos);
            String url = BuildConfig.ImageBaseURL + dogListDto.getThumbnailImg();
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .error(R.drawable.ic_close)
                    .into(binding.dogImage);

            binding.textName.setText(dogListDto.getName());
            binding.textAge.setText(dogListDto.getAge() + "");
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailDogActivity.class);
            intent.putExtra(DetailDogActivity.TAG_DOG_ENTITY, dogListDto);
            context.startActivity(intent);
        }
    }
}
