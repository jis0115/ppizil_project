package com.ppizil.photopicker.viewmodel;

import androidx.lifecycle.ViewModel;

public abstract class BaseViewModel extends ViewModel {


    public abstract void onCreate();
    public abstract void onStart();
    public abstract void onResume();
    public abstract void onStop();
    public abstract void onDestroy();


}
