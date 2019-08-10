package com.example.myapplication.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.view.custom.BaseActivity;
import com.example.myapplication.view.dialog.DetailPhotoDialog;
import com.example.myapplication.view.fragment.DogListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ppizil.photopicker.view.PhotoPickerActivity;

public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String TAB_DOG_LIST = "dogList";
    public static final String TAB_DOG_ADD = "dogAdd";
    public static final String TAB_USER_SETTING = "userSetting";

    public static final int REQUEST_PHOTOPICK = 1001;

    private Fragment currentFragment;
    private DogListFragment dogListFragment = DogListFragment.newInstance(); //그냥 클래스 로드시에 만들어 줌 인스턴스 계속 유지할 거라서.
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        init();
    }


    @Override
    public void init() {
        setListener();
        setFragment(dogListFragment, TAB_DOG_LIST);
        setListener();
    }

    @Override
    public void setListener() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }


    public void setFragment(Fragment fragment, String tag) {
        if (currentFragment != null) {
            if (currentFragment != fragment) {
                currentFragment = fragment;
                getSupportFragmentManager().beginTransaction().replace(R.id.container, currentFragment, tag).commit();
            } else {
                Toast.makeText(this, "같은 탭임 ㅎㅎ", Toast.LENGTH_SHORT).show();
            }
        } else {
            currentFragment = dogListFragment;
            getSupportFragmentManager().beginTransaction().replace(R.id.container, currentFragment, tag).commit();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.list:
                setFragment(dogListFragment, TAB_DOG_LIST);
                break;
            case R.id.add:
                Intent intent = new Intent(MainActivity.this, PhotoPickerActivity.class);
                startNextActivityForResult(intent, REQUEST_PHOTOPICK);
                break;
            case R.id.setting:
                break;
        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (resultCode) {
                case PhotoPickerActivity.RESULT_SUCCESS:
                    Bundle bundle = new Bundle();
                    //
                    bundle.putString(PhotoPickerActivity.TAG_FILEPATH, data.getStringExtra(PhotoPickerActivity.TAG_FILEPATH));
                    DetailPhotoDialog dialog = DetailPhotoDialog.newInstance(bundle);
                    dialog.show(getSupportFragmentManager(), "detail");
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}
