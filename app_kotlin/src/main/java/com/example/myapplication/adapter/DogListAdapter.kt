package com.example.myapplication.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.myapplication.BuildConfig
import com.example.myapplication.R
import com.example.myapplication.data.entity.DogListEntity
import com.example.myapplication.data.model.DogListModel
import com.example.myapplication.databinding.ItemDogListBinding
import com.example.myapplication.databinding.ItemEmptyBinding
import com.example.myapplication.network.DogInfoApi
import com.example.myapplication.network.dto.DogListDto
import com.example.myapplication.view.activity.DetailDogActivity
import com.example.myapplication.view.custom.EmptyViewHolder

import java.util.ArrayList

class DogListAdapter(private val context: Context, private val model: DogListModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun adapterNotify() {
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_EMPTY) {
            val binding = ItemEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return EmptyViewHolder(binding)
        } else {
            val binding = ItemDogListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemViewHolder(context, binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is EmptyViewHolder) {

        } else if (holder is ItemViewHolder) {
            holder.bind(model, position)
        }

    }

    override fun getItemCount(): Int {
        val size = model.doglistEntity?.dogs?.size ?: 0
        return if (size == 0) {
            1
        } else {
            size
        }
    }

    override fun getItemViewType(position: Int): Int {
        val size = model.doglistEntity?.dogs?.size ?: 0
        return if (size == 0) {
            TYPE_EMPTY
        } else {
            TYPE_ITEM
        }
    }

    // 정적 스태틱 클래스
    class ItemViewHolder(private val context: Context, private val binding: ItemDogListBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private var dogListDto: DogListDto.DetailInfo? = null

        init {
            setListener()
        }

        fun setListener() {
            binding.dogImage.setOnClickListener(this)
        }

        fun bind(model: DogListModel, pos: Int) {
            dogListDto = model.doglistEntity!!.dogs[pos]
            val url = BuildConfig.ImageBaseURL + dogListDto!!.thumbnailImg
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .error(R.drawable.ic_close)
                    .into(binding.dogImage)

            binding.textName.text = dogListDto!!.name
            binding.textAge.text = dogListDto!!.age.toString() + ""
            binding.executePendingBindings()
        }

        override fun onClick(view: View) {
            val intent = Intent(context, DetailDogActivity::class.java)
            intent.putExtra(DetailDogActivity.TAG_DOG_ENTITY, dogListDto)
            context.startActivity(intent)
        }
    }

    companion object {
        const val TYPE_EMPTY = 0
        const val TYPE_ITEM = 1
    }
}
