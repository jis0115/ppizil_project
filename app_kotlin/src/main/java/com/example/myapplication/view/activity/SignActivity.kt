package com.example.myapplication.view.activity

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil

import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.utils.Const
import com.example.myapplication.utils.MakeLog
import com.example.myapplication.data.entity.SignupTitles
import com.example.myapplication.data.model.SignupModel
import com.example.myapplication.databinding.ActivitySignupBinding
import com.example.myapplication.network.RetrofitHelper
import com.example.myapplication.view.custom.BaseActivity
import com.example.myapplication.view.dialog.DetailPhotoDialog
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.ppizil.photopicker.view.PhotoPickerActivity

import java.util.ArrayList
import java.util.HashMap

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.example.myapplication.view.activity.MainActivity.Companion.REQUEST_PHOTOPICK
import com.ppizil.photopicker.view.PhotoPickerActivity.RESULT_SUCCESS

class SignActivity : BaseActivity(), View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private var binding: ActivitySignupBinding? = null
    private var signupTitles: SignupTitles? = null
    private var titlesList: MutableList<TextView>? = null

    private var signupModel: SignupModel? = null

    //권한
    var permissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            RendingAlbum()
        }

        override fun onPermissionDenied(deniedPermissions: List<String>) {
            Const.showToastMsg(this@SignActivity, "Check permission!!")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        init()
        setListener()
    }

    override fun init() {

        titlesList = ArrayList()
        signupTitles = SignupTitles(resources.getStringArray(R.array.signup_title))
        signupModel = SignupModel()
        addTitleList()
        setTitlesSet()
    }


    //타이블뷰를 리스트로 관리
    fun addTitleList() {
        binding!!.includeAge.inputValue.inputType = InputType.TYPE_CLASS_NUMBER
        titlesList!!.add(binding!!.includeNickname.textTitle)
        titlesList!!.add(binding!!.includeAge.textTitle)
        titlesList!!.add(binding!!.includeAddr.textTitle)
        titlesList!!.add(binding!!.includePwd.textTitle)
        titlesList!!.add(binding!!.includeRePwd.textTitle)
    }

    //Arrays.xml data ->  set
    fun setTitlesSet() { // 입력필드 타이틀 수정
        for (i in titlesList!!.indices) {
            titlesList!![i].text = signupTitles!!.titles[i]
        }
    }

    override fun setListener() {
        binding!!.confirmBtn.setOnClickListener(this)
        binding!!.selectProfileBtn.setOnClickListener(this)
        binding!!.radioGroup.setOnCheckedChangeListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.confirm_btn -> {
                sumValues()
                if (signupModel!!.checkValid()) {
                    if (signupModel!!.checkPwdDuplicate()) {
                        MakeLog.log("객체 시리얼라이즈", Const.toJsonString(signupModel!!.signupEntity))
                        Const.showToastMsg(this@SignActivity, "Success!")
                        // startNextActivity(MainActivity.class);
                        requestRegistApi()
                    } else {
                        Const.showToastMsg(this@SignActivity, "Not dulplicated Pwd!")
                    }
                } else {
                    Const.showToastMsg(this@SignActivity, "Check your info! ")
                }
            }
            R.id.select_profile_btn -> TedPermission.with(this@SignActivity)
                    .setPermissionListener(permissionListener)
                    .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .check()
        }
    }


    //


    fun RendingAlbum() {
        val intent = Intent(this, PhotoPickerActivity::class.java)
        startNextActivityForResult(intent, Const.TAG_ALBUM)

        /*  Intent intent = new Intent(Intent.ACTION_PICK);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(TAG_IMAGE_TYPE);
        startActivityForResult(intent, Const.TAG_ALBUM);*/
    }


    fun sumValues() {
        signupModel!!.signupEntity.userName = binding!!.includeNickname.inputValue.text.toString()

        val age = binding!!.includeAge.inputValue.text.toString()
        if (Const.isInteger(age)) {
            signupModel!!.signupEntity.age = Integer.parseInt(age)
        } else {
            Const.showToastMsg(this@SignActivity, "Check your Age")
        }
        signupModel!!.signupEntity.addr = binding!!.includeAddr.inputValue.text.toString()
        signupModel!!.signupEntity.password = binding!!.includePwd.inputValue.text.toString()
        signupModel!!.signupEntity.rePassword = binding!!.includeRePwd.inputValue.text.toString()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //request => startActivity할때 넣어준 값
        // result => 타겟 Activity에서 finish 전 setResult(변수)값
        if (data != null) {
            when (resultCode) {
                PhotoPickerActivity.RESULT_SUCCESS -> {
                    val path = data.getStringExtra(PhotoPickerActivity.TAG_FILEPATH)
                    signupModel!!.filePath = path
                    Const.setImageLoadGeneral(binding!!.profileImage, path!!)
                }
            }
        }
    }

    fun requestRegistApi() {

        val call = RetrofitHelper.getinstance().getSignupApi().requestSignupApi(signupModel!!.multipartObj, signupModel!!.makeParams())
        val callback = object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Const.showToastMsg(this@SignActivity, "Success Regis!")
                    this@SignActivity.startNextActivity(LoginActivity::class.java)
                } else {
                    val errorMsg = RetrofitHelper.getErrorMsg(response)
                    Const.showToastMsg(this@SignActivity, errorMsg!!)
                    MakeLog.log("requestRegistApi() Fail", errorMsg)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                MakeLog.log("에러", t.message!!)
            }
        }
        RetrofitHelper.enqueueWithRetry<ResponseBody>(call, callback)
    }

    override fun onCheckedChanged(radioGroup: RadioGroup, i: Int) {
        val id = radioGroup.checkedRadioButtonId
        when (id) {
            R.id.male -> signupModel!!.signupEntity.gender = "M" //남성
            R.id.female -> signupModel!!.signupEntity.gender = "F"// 여성
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startNextActivity(StartActivity::class.java)
    }

    companion object {

        private val TAG_IMAGE_TYPE = "image/*"
    }
}
