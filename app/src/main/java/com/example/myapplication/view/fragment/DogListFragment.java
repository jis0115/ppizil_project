package com.example.myapplication.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DogListAdapter;
import com.example.myapplication.databinding.FragmentDogListBinding;
import com.example.myapplication.view.custom.BaseFragment;

import static com.example.myapplication.adapter.DogListAdapter.TYPE_EMPTY;

public class DogListFragment extends BaseFragment {

    public static DogListFragment newInstance() {
        Bundle args = new Bundle();
        DogListFragment fragment = new DogListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentDogListBinding binding;
    private DogListAdapter adapter;

    private GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
            if (adapter.getItemViewType(position) == TYPE_EMPTY && adapter.getItemCount() == 1) {
                return 2;
            } else {
                return 1;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dog_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void init() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerview.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        adapter = new DogListAdapter(getContext());
        binding.recyclerview.setAdapter(adapter);
    }

    @Override
    public void setListener() {

    }
}
