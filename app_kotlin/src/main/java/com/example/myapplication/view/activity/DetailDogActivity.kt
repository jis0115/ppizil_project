package com.example.myapplication.view.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.myapplication.R
import com.example.myapplication.adapter.DetailDogAdapter
import com.example.myapplication.data.model.DogDetailModel
import com.example.myapplication.databinding.ActivityDetaildogBinding
import com.example.myapplication.view.custom.BaseActivity

class DetailDogActivity : BaseActivity() {


    private var binding: ActivityDetaildogBinding? = null
    private var adapter: DetailDogAdapter? = null
    private var model: DogDetailModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detaildog)
        init()
    }

    fun getIntentData() {
        val intent = intent
        model!!.info = intent.getParcelableExtra(TAG_DOG_ENTITY)
    }

    override fun init() {
        model = DogDetailModel()
        getIntentData()

        adapter = DetailDogAdapter(this, model!!)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding!!.recyclerview.layoutManager = linearLayoutManager
        binding!!.recyclerview.adapter = adapter
        setListener()
    }

    override fun setListener() {

    }

    override fun onClick(view: View) {

    }

    companion object {
        const val TAG_DOG_ENTITY = "dogEntity"
    }
}
