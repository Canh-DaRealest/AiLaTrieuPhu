package com.canhmai.ailatrieuphu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.canhmai.ailatrieuphu.databinding.DialogNoticeBinding;

public class NoticeDialog extends Dialog implements View.OnClickListener {
    public static final String EXIT_APP = "EXIT_APP";
    private final DialogNoticeBinding binding;
    private final Context context;

    public NoticeDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        binding = DialogNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        initViews();
    }

    private void initViews() {
        doClick();
    }

    private void doClick() {
        binding.btNo.setOnClickListener(this);
        binding.btOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        clickView(v);
    }

    private void clickView(View v) {
        v.setAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_popup_enter));
        dismiss();
    }

    public void addNotice(boolean isOutSidTouch, String title, String detail, String btNoName, String btOkName, View.OnClickListener onClickListener) {
        binding.tvDetail.setText(detail);

        if (btOkName == null) {
            binding.btOk.setVisibility(View.GONE);
        } else {
            binding.btOk.setText(btOkName);
        }

        if (btNoName == null) {
            binding.btNo.setVisibility(View.GONE);
        } else {
            binding.btNo.setText(btNoName);
        }
        if (onClickListener != null) {
            binding.btOk.setOnClickListener(onClickListener);
            binding.btNo.setOnClickListener(onClickListener);

        } else {
            binding.btNo.setOnClickListener(this);
        }
        if (!isOutSidTouch) {
            setCanceledOnTouchOutside(false);
            setCancelable(false);
        }
        if (title == null) {
            binding.tvTitle.setText("Thông báo");
        } else {
            binding.tvTitle.setText(title);
        }
    }

}
