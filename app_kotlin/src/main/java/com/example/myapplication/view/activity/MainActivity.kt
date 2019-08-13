package com.example.myapplication.view.activity

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.view.custom.BaseActivity
import com.example.myapplication.view.dialog.DetailPhotoDialog
import com.example.myapplication.view.fragment.DogListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ppizil.photopicker.view.PhotoPickerActivity

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var currentFragment: Fragment? = null
    private val dogListFragment = DogListFragment.newInstance() //그냥 클래스 로드시에 만들어 줌 인스턴스 계속 유지할 거라서.
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }


    override fun init() {
        setListener()
        setFragment(dogListFragment, TAB_DOG_LIST)
        setListener()
    }

    override fun setListener() {
        binding!!.bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }


    fun setFragment(fragment: Fragment, tag: String) {
        if (currentFragment != null) {
            if (currentFragment !== fragment) {
                currentFragment = fragment
                supportFragmentManager.beginTransaction().replace(R.id.container, currentFragment!!, tag).commit()
            } else {
                Toast.makeText(this, "같은 탭임 ㅎㅎ", Toast.LENGTH_SHORT).show()
            }
        } else {
            currentFragment = dogListFragment
            supportFragmentManager.beginTransaction().replace(R.id.container, currentFragment!!, tag).commit()
        }
    }


    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val id = menuItem.itemId
        when (id) {
            R.id.list -> setFragment(dogListFragment, TAB_DOG_LIST)
            R.id.add -> {
                val intent = Intent(this@MainActivity, PhotoPickerActivity::class.java)
                startNextActivityForResult(intent, REQUEST_PHOTOPICK)
            }
            R.id.setting -> {
            }
        }
        return false
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            when (resultCode) {
                PhotoPickerActivity.RESULT_SUCCESS -> {
                    val bundle = Bundle()
                    //
                    bundle.putString(PhotoPickerActivity.TAG_FILEPATH, data.getStringExtra(PhotoPickerActivity.TAG_FILEPATH))
                    val dialog = DetailPhotoDialog.newInstance(bundle)
                    dialog.show(supportFragmentManager, "detail")
                }
            }
        }
    }

    override fun onClick(view: View) {

    }

    companion object {

        val TAB_DOG_LIST = "dogList"
        val TAB_DOG_ADD = "dogAdd"
        val TAB_USER_SETTING = "userSetting"

        val REQUEST_PHOTOPICK = 1001
    }
}
