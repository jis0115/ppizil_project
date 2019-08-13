package com.example.myapplication.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager

import com.example.myapplication.R
import com.example.myapplication.adapter.DogListAdapter
import com.example.myapplication.data.model.DogListModel
import com.example.myapplication.databinding.FragmentDogListBinding
import com.example.myapplication.network.RetrofitHelper
import com.example.myapplication.network.dto.DogListDto
import com.example.myapplication.utils.MakeLog
import com.example.myapplication.view.custom.BaseFragment

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.myapplication.adapter.DogListAdapter.Companion.TYPE_EMPTY

class DogListFragment : BaseFragment() {

    private var binding: FragmentDogListBinding? = null
    private var adapter: DogListAdapter? = null
    private var dogListModel: DogListModel? = null

    private val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (adapter!!.getItemViewType(position) == TYPE_EMPTY && adapter!!.itemCount == 1) {
                2
            } else {
                1
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dog_list, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }


    override fun onResume() {
        super.onResume()
        getAllDogListApi()
    }

    override fun init() {
        dogListModel = DogListModel()
        val gridLayoutManager = GridLayoutManager(context, 2)
        binding!!.recyclerview.layoutManager = gridLayoutManager
        gridLayoutManager.spanSizeLookup = spanSizeLookup
        adapter = DogListAdapter(context!!, dogListModel!!)
        binding!!.recyclerview.adapter = adapter
    }

    override fun setListener() {

    }


    fun getAllDogListApi() {

        val call = RetrofitHelper.getinstance().doginfoApi.dogListApi

        val callback = object : Callback<DogListDto> {
            override fun onResponse(call: Call<DogListDto>, response: Response<DogListDto>) {
                if (response.isSuccessful) {
                    val dogListDto = response.body()
                    dogListModel!!.doglistEntity = dogListDto
                    adapter!!.adapterNotify()

                } else {
                    MakeLog.log("getAllDogListApi() fail ", "" + response.message())
                }
            }

            override fun onFailure(call: Call<DogListDto>, t: Throwable) {
                MakeLog.log("getAllDogListApi() exception", t.message!!)
            }
        }

        RetrofitHelper.enqueueWithRetry<DogListDto>(call, callback)
    }

    companion object {

        fun newInstance(): DogListFragment {
            val args = Bundle()
            val fragment = DogListFragment()
            fragment.arguments = args
            return fragment
        }
    }


}
