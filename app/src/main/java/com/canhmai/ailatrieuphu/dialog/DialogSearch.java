package com.canhmai.ailatrieuphu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.canhmai.ailatrieuphu.R;
import com.canhmai.ailatrieuphu.databinding.DialogclicktosearchBinding;
import com.canhmai.ailatrieuphu.view.act.OnMainCallBack;

public class DialogSearch extends Dialog implements View.OnClickListener {
    private final Context context;
    private final DialogclicktosearchBinding binding;
    private OnMainCallBack onMainCallBack;
    private Object dlAboutMeData;

    public DialogSearch(@NonNull Context context) {
        super(context);
        this.context = context;
        this.onMainCallBack = onMainCallBack;
        binding = DialogclicktosearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViews();
    }



    private void initViews() {
        binding.trBack.setOnClickListener(this);
        binding.wvWebview.getSettings().setJavaScriptEnabled(true);
        binding.wvWebview.getSettings().setAllowContentAccess(true);
        binding.wvWebview.getSettings().setBuiltInZoomControls(true);
        binding.wvWebview.getSettings().setAllowFileAccess(true);
        binding.wvWebview.getSettings().setDomStorageEnabled(true);
        binding.wvWebview.getSettings().setSupportZoom(true);
        binding.wvWebview.setWebChromeClient(new WebChromeClient());
        binding.wvWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        binding.wvWebview.loadUrl("https://www.google.com/");
    }


    @Override
    public void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        if (v.getId() == R.id.tr_back) {
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        if (!binding.wvWebview.canGoBack()) {
            super.dismiss();
            return;
        }
        binding.wvWebview.goBack();

    }
}
