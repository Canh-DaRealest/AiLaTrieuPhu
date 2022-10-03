package com.canhmai.ailatrieuphu.view.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.canhmai.ailatrieuphu.App;
import com.canhmai.ailatrieuphu.MediaManager;
import com.canhmai.ailatrieuphu.R;
import com.canhmai.ailatrieuphu.databinding.M002RuleFragmentBinding;
import com.canhmai.ailatrieuphu.dialog.NoticeDialog;
import com.canhmai.ailatrieuphu.view.act.MainActivity;
import com.canhmai.ailatrieuphu.viewmodel.CommonVM;

public class M002RuleFragment extends BaseFragment<CommonVM, M002RuleFragmentBinding> {
    public static final String TAG = M002RuleFragment.class.getName();

    @Override
    protected void initViews() {
       binding.ivBack.setVisibility(View.VISIBLE);
        binding.btHide.setVisibility(View.VISIBLE);
        binding.btHide.setClickable(true);
        binding.frMain.setVisibility(View.VISIBLE);
        binding.frMain.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.slide_left));
        App.getInstance().getMediaManager().playGameSound(MediaManager.GAME_RULE,
                mp -> App.getInstance().getMediaManager().playGameSound(MediaManager.READY, mp1 -> showReadyDialog()));
        setAnimateUI();
//
        binding.btHide.setOnClickListener(this);
        binding.ivBack.setOnClickListener(this);
    }

    @Override
    protected void clickView(View v) {
        super.clickView(v);
        switch (v.getId()) {
            case R.id.bt_hide:
                doPlayGame();
                break;

            case R.id.iv_back:
                MainActivity mainActivity = (MainActivity) mContext;
                mainActivity.onBackPressed();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    private void doPlayGame() {
        binding.btHide.setAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_popup_enter));
        binding.btHide.setClickable(false);
        binding.ivBack.setClickable(false);

        App.getInstance().getMediaManager().playGameSound(MediaManager.GO_FIND, mp -> {
            onMainCallBack.showFragment(M003PlayFragment.TAG, null, false);
            binding.btHide.setVisibility(View.GONE);
        });
    }

    private void showReadyDialog() {
        NoticeDialog noticeDialog = new NoticeDialog(mContext);
        noticeDialog.addNotice(false, null, "Bạn đã sẵn sàng chơi chưa", "Quay lại", "Sẵn sàng", v -> {
            if (v.getId() == R.id.bt_ok) {
                doReady();
                noticeDialog.dismiss();
            } else {
                noticeDialog.dismiss();
            }

        });
        noticeDialog.show();
    }

    private void doReady() {
        binding.btHide.setClickable(false);
        App.getInstance().getMediaManager().stopGameSound();
        App.getInstance().getMediaManager().playGameSound(MediaManager.GO_FIND, mp -> {

            onMainCallBack.showFragment(M003PlayFragment.TAG, null, false);
            Log.e(TAG, "onCompletion: M002RULE");
        });
    }

    private void doBack() {

    }

    private void setAnimateUI() {
        binding.lnRuleMain.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.abc_fade_in_custom));
    }

    @Override
    protected Class<CommonVM> getClassViewModel() {
        return CommonVM.class;
    }

    @Override
    protected M002RuleFragmentBinding initViewBinding(LayoutInflater inflater) {
        return M002RuleFragmentBinding.inflate(inflater);
    }

}
