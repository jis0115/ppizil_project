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
import com.example.myapplication.data.model.DogListModel;
import com.example.myapplication.databinding.FragmentDogListBinding;
import com.example.myapplication.network.RetrofitHelper;
import com.example.myapplication.network.dto.DogListDto;
import com.example.myapplication.utils.MakeLog;
import com.example.myapplication.view.custom.BaseFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private DogListModel dogListModel;

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
    public void onResume() {
        super.onResume();
        getAllDogListApi();
    }

    @Override
    public void init() {
        dogListModel = new DogListModel();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerview.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        adapter = new DogListAdapter(getContext(), dogListModel);
        binding.recyclerview.setAdapter(adapter);
    }

    @Override
    public void setListener() {

    }


    public void getAllDogListApi() {

        Call<DogListDto> call = RetrofitHelper.getinstance().getDoginfoApi().getDogListApi();

        Callback<DogListDto> callback = new Callback<DogListDto>() {
            @Override
            public void onResponse(Call<DogListDto> call, Response<DogListDto> response) {
                if (response.isSuccessful()) {
                    DogListDto dogListDto = response.body();
                    dogListModel.setDoglistEntity(dogListDto);
                    adapter.adapterNotify();

                } else {
                    MakeLog.log("getAllDogListApi() fail ", "" + response.message());
                }
            }

            @Override
            public void onFailure(Call<DogListDto> call, Throwable t) {
                MakeLog.log("getAllDogListApi() exception", t.getMessage());
            }
        };

        RetrofitHelper.getinstance().enqueueWithRetry(call, callback);
    }


}
