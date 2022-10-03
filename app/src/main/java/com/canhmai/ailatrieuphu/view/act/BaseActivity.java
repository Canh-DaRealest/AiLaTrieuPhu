package com.canhmai.ailatrieuphu.view.act;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.canhmai.ailatrieuphu.R;
import com.canhmai.ailatrieuphu.view.fragment.BaseFragment;

public abstract class BaseActivity<V extends ViewModel, T extends ViewBinding> extends AppCompatActivity implements View.OnClickListener, OnMainCallBack {

    protected T binding;
    protected V viewmodel;

    @Override
    public final void onClick(View v) {
        v.setAnimation(AnimationUtils.loadAnimation(this, androidx.appcompat.R.anim.abc_fade_in));
        clickView();
    }

    private void clickView() {

    }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = initViewBinding();
        setContentView(binding.getRoot());
        viewmodel = new ViewModelProvider(this).get(getClasViewModel());
        initViews();

    }

    protected abstract void initViews();

    protected abstract Class<V> getClasViewModel();

    protected abstract T initViewBinding();


    @Override
    public void showFragment(String frgName, Object data, boolean isBacked) {
        try {
            Class<?> classInstance = Class.forName(frgName); //tu tag ta lay duoc class
            BaseFragment<?, ?> fragmentname = (BaseFragment<?, ?>) classInstance.newInstance(); //tu ham constuctor ta tao ra mot the hien moi cua fragment tuong ung voi class

            //set data va callback
            fragmentname.setmData(data);
            fragmentname.setOnMainCallBack(this);

            FragmentManager fmanager = getSupportFragmentManager();
            FragmentTransaction ftransaction = fmanager.beginTransaction();
            ftransaction.replace(R.id.ln_main, fragmentname);
            ftransaction.setCustomAnimations(androidx.fragment.R.animator.fragment_fade_enter, androidx.fragment.R.animator.fragment_fade_exit);
            //ham kiem tra fragment co cho back lai hay ko -> neu true thi add fragment to backstack
            if (isBacked) {
                ftransaction.addToBackStack(null);
            }

            ftransaction.commit();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callBack(Object data, String key)  {
    }

}
