package com.example.myapplication.view.custom

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract fun init()
    abstract fun setListener()

}
