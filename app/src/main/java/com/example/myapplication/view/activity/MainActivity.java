package com.example.myapplication.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.view.custom.BaseActivity;
import com.example.myapplication.view.fragment.DogListFragment;

public class MainActivity extends BaseActivity implements MenuItem.OnMenuItemClickListener {

    public static final String TAB_DOG_LIST = "dogList";
    public static final String TAB_DOG_ADD = "dogAdd";
    public static final String TAB_USER_SETTING = "userSetting";

    private Fragment currentFragment;
    private DogListFragment dogListFragment = DogListFragment.newInstance(); //그냥 클래스 로드시에 만들어 줌 인스턴스 계속 유지할 거라서.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    @Override
    public void init() {

        setListener();
        setFragment(dogListFragment, TAB_DOG_LIST);
    }

    @Override
    public void setListener() {

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.list:
                setFragment(dogListFragment, TAB_DOG_LIST);
                break;
            case R.id.add:
                break;
            case R.id.setting:
                break;
        }
        return false;
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


}
