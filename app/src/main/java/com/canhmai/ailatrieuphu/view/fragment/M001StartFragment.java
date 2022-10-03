package com.canhmai.ailatrieuphu.view.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.canhmai.ailatrieuphu.App;
import com.canhmai.ailatrieuphu.MediaManager;
import com.canhmai.ailatrieuphu.R;
import com.canhmai.ailatrieuphu.databinding.M001StartFragmentBinding;
import com.canhmai.ailatrieuphu.dialog.DialogRatingMe;
import com.canhmai.ailatrieuphu.view.act.MainActivity;
import com.canhmai.ailatrieuphu.viewmodel.CommonVM;

public class M001StartFragment extends BaseFragment<CommonVM, M001StartFragmentBinding> {
    public static final String TAG = M001StartFragment.class.getName();

    @Override
    protected void initViews() {



        //doi mau statusbar
        MainActivity mainActivity = (MainActivity) mContext;
        mainActivity.getWindow().setStatusBarColor(getResources().getColor(R.color.colorbg));
        binding.lnRuleMain.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.abc_fade_in_custom));
        doClick();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void doClick() {
        binding.ivM001About.setOnClickListener(this);
        binding.ivM001Cup.setOnClickListener(this);
        binding.ivM001Setting.setOnClickListener(this);
        binding.ivM001Play.setOnClickListener(this);
        binding.ivM001Rating.setOnClickListener(this);

    }

    @Override
    protected Class<CommonVM> getClassViewModel() {
        return CommonVM.class;
    }

    @Override
    protected M001StartFragmentBinding initViewBinding(LayoutInflater inflater) {
        return M001StartFragmentBinding.inflate(inflater);
    }

    @Override
    protected void clickView(View v) {
        super.clickView(v);
        switch (v.getId()) {
            case R.id.iv_m001_about:
                showAboutDialog();
                break;
            case R.id.tv_link:
                showLinkFB();
                break;

            case R.id.iv_m001_cup:
                showRankFragment();
                break;

            case R.id.iv_m001_setting:
                showSettingFragment();
                break;

            case R.id.iv_m001_play:
                showRuleFragment();
                break;
            case R.id.iv_m001_rating:
                showDialogRatting();
                break;
        }
    }

    private void showDialogRatting() {
        DialogRatingMe dialogRatingMe = new DialogRatingMe(mContext);
        Window window = dialogRatingMe.getWindow();
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        dialogRatingMe.show();
    }

    private void showRuleFragment() {
        App.getInstance().getMediaManager().stopBGSound();
        binding.ivM001Play.setImageLevel(1);
        new Handler().postDelayed(() -> {
            binding.lnRuleMain.clearAnimation();
            onMainCallBack.showFragment(M002RuleFragment.TAG, null, true);
        }, 100);
    }

    private void showSettingFragment() {
        onMainCallBack.showFragment(M001SettingFragment.TAG, null, true);
    }

    private void showRankFragment() {
        onMainCallBack.showFragment(M001AchivementFragment.TAG, null, true);
    }

    private void showLinkFB() {
        String url = "https://www.facebook.com/Yeu.23.06";
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void showAboutDialog() {
        Dialog dialog = new Dialog(mContext);
        //anh xa dialog xml vao view
        View view = LayoutInflater.from(mContext).inflate(R.layout.m001_dialog_about_me, null);
        dialog.setContentView(view);
        TextView linktext = view.findViewById(R.id.tv_link);
        linktext.setOnClickListener(this);
        //chinh kich thuoc cua custom dialog
        Window window = dialog.getWindow();
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        dialog.show();
    }
}
