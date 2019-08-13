package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.myapplication.BuildConfig
import com.example.myapplication.data.model.DogDetailModel
import com.example.myapplication.databinding.ItemDetailDogBinding

class DetailDogAdapter(private val context: Context, private val model: DogDetailModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun adapterNotify() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemDetailDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.bind(model, position)
        }
    }

    override fun getItemCount(): Int {
        return model.info?.imgs?.size ?: 0
    }


    class ItemViewHolder(private val context: Context, private val binding: ItemDetailDogBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: DogDetailModel, pos: Int) {
            val path = BuildConfig.ImageBaseURL + model.info!!.imgs[pos].img
            Glide.with(context)
                    .load(path)
                    .centerCrop()
                    .into(binding.imageDog)
            binding.executePendingBindings()
        }


    }
}
