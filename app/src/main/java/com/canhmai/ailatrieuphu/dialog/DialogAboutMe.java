package com.canhmai.ailatrieuphu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.room.Insert;

import com.canhmai.ailatrieuphu.databinding.M001DialogAboutMeBinding;
import com.canhmai.ailatrieuphu.databinding.M001DialogAboutMeBinding;
import com.canhmai.ailatrieuphu.view.act.OnMainCallBack;

public class DialogAboutMe extends Dialog {
    public static final String OPEN_FB_APP = "OPEN_FB_APP";
    private final Context context;
    private OnMainCallBack onMainCallBack;
    private final M001DialogAboutMeBinding binding;
    private Object dlAboutMeData;

    public void setDlAboutMeData(Object dlAboutMeData) {
        this.dlAboutMeData = dlAboutMeData;
    }

    public DialogAboutMe(@NonNull Context context) {
        super(context);
        this.context = context;
        this.onMainCallBack = onMainCallBack;
        binding = M001DialogAboutMeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
    }

    private void initViews() {
        binding.tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doClickLink();
            }
        });
    }

    private void doClickLink() {
        onMainCallBack.callBack(null, OPEN_FB_APP);
    }
}
