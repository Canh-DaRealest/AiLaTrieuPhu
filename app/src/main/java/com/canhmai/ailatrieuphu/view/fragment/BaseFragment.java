package com.canhmai.ailatrieuphu.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.canhmai.ailatrieuphu.view.act.OnMainCallBack;

public abstract class BaseFragment<V extends ViewModel, T extends ViewBinding> extends Fragment implements View.OnClickListener {
    protected Object mData;
    protected Context mContext;
    protected V viewModel;
    protected T binding;

    protected OnMainCallBack onMainCallBack;

    public void setmData(Object mData) {
        this.mData = mData;
    }

    public void setOnMainCallBack(OnMainCallBack onMainCallBack) {
        this.onMainCallBack = onMainCallBack;
    }

    @Override
    public final void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = initViewBinding(inflater);
        viewModel = new ViewModelProvider(this).get(getClassViewModel());
        return binding.getRoot();
    }

    @Override
    public final void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    protected abstract void initViews();

    protected abstract Class<V> getClassViewModel();

    protected abstract T initViewBinding(LayoutInflater inflater);


    @Override
    public final void onClick(View v) {
        v.setAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_fade_in));
        clickView(v);
    }

    protected void clickView(View v) {
    }


}
