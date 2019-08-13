package com.example.myapplication.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment

import com.example.myapplication.R
import com.example.myapplication.data.model.DetailPhotoModel
import com.example.myapplication.databinding.DialogDetailPhotoBinding
import com.example.myapplication.utils.Const
import com.ppizil.photopicker.view.PhotoPickerActivity

class DetailPhotoDialog : DialogFragment(), View.OnClickListener {

    private var binding: DialogDetailPhotoBinding? = null
    private var bundle: Bundle? = null
    private var detailPhotoModel: DetailPhotoModel? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_detail_photo, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        setImage()
        sendMultipart()
    }

    fun init() {
        bundle = arguments
        detailPhotoModel = DetailPhotoModel()
        detailPhotoModel!!.detailPhotoEntity.filePath = bundle!!.getString(PhotoPickerActivity.TAG_FILEPATH)

        setListener()
    }

    fun setListener() {
        binding!!.cancelBtn.setOnClickListener(this)
        binding!!.confirmBtn.setOnClickListener(this)
    }


    override fun onClick(view: View) {

        val id = view.id
        when (id) {
            R.id.cancel_btn -> dismiss()
            R.id.confirm_btn -> {
            }
        }
    }

    fun setImage() {
        Const.setImageLoadGeneral(binding!!.image, detailPhotoModel!!.detailPhotoEntity.filePath!!)
    }


    fun sendMultipart() {
        Const.setWrapContentDialogSize(this)
    }

    companion object {


        fun newInstance(bundle: Bundle): DetailPhotoDialog {
            val fragment = DetailPhotoDialog()
            fragment.arguments = bundle
            return fragment
        }
    }
}
