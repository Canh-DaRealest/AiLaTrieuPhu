package com.canhmai.ailatrieuphu.view.act;

import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.fragment.app.Fragment;

import com.canhmai.ailatrieuphu.App;
import com.canhmai.ailatrieuphu.model.MediaManager;
import com.canhmai.ailatrieuphu.R;
import com.canhmai.ailatrieuphu.databinding.ActivityMainBinding;
import com.canhmai.ailatrieuphu.dialog.NoticeDialog;
import com.canhmai.ailatrieuphu.view.fragment.M001SettingFragment;
import com.canhmai.ailatrieuphu.view.fragment.M001StartFragment;
import com.canhmai.ailatrieuphu.view.fragment.M002RuleFragment;
import com.canhmai.ailatrieuphu.view.fragment.M003PlayFragment;
import com.canhmai.ailatrieuphu.viewmodel.CommonVM;

import java.util.ArrayList;

public class MainActivity extends BaseActivity<CommonVM, ActivityMainBinding> {
    private int progress;

    private void initUI() {
        App.getInstance().getStorage().listHighScore = new ArrayList<>();


        binding.ivLogo.setAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_left));
        new Handler().postDelayed(this::goToMainScreen, 2000);

    }


    private void goToMainScreen() {
        //truyen list question da lay tu database vao storage
        new Thread() {
            @Override
            public void run() {
                progress = 30;
                while (true) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (progress >= 100) {
                        binding.ivProgressbarLoading.setProgress(100);
                        runOnUiThread(() -> {
                            binding.tvPercent.setText("100%");

                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                            binding.ivLogo.setVisibility(View.GONE);
                            binding.tvPercent.setVisibility(View.GONE);
                            binding.ivProgressbarLoading.setVisibility(View.GONE);


                            App.getInstance().getMediaManager().playBG2(MediaManager.NHAC_MO_DAU, new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {

                                    App.getInstance().getMediaManager().playBG(MediaManager.BG_SOUND);
                                }
                            });

                            showFragment(M001StartFragment.TAG, null, false);

                        });
                        return;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            binding.ivProgressbarLoading.setProgress(progress);
                            binding.tvPercent.setText(progress + "%");
                            progress += 33;
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    protected void initViews() {

        binding.ivProgressbarLoading.setMax(100);
        binding.ivProgressbarLoading.setProgress(0);
        initUI();
        App.getInstance().setAllowbacked(true);
    }


    @Override
    protected Class<CommonVM> getClasViewModel() {
        return CommonVM.class;
    }

    @Override
    protected ActivityMainBinding initViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }


    @Override
    public void onBackPressed() {

        if (App.getInstance().isAllowbacked()) {

            //        //tim fragment  hien tai( dang duoc add vao activity)
            Fragment fragmentManager = getSupportFragmentManager().findFragmentById(R.id.ln_main);
            //kiem tra neu fragment giong voi m003frg
            if (fragmentManager instanceof M003PlayFragment) {

                askForStopGame(fragmentManager);
            } else if (fragmentManager instanceof M001StartFragment) {
                askForExitApp();
                //luu du lieu setting
            } else if (fragmentManager instanceof M001SettingFragment) {
                App.getInstance().getMediaManager().doSaveSetting(App.getInstance().getMediaManager().getBGOn(), App.getInstance().getMediaManager().getGameOn());
                getSupportFragmentManager().popBackStack();

            } else if (fragmentManager instanceof M002RuleFragment) {
                App.getInstance().getMediaManager().stopGameSound();
                App.getInstance().getMediaManager().playBG(MediaManager.BG_SOUND);
                App.getInstance().setOnMusic(false);
                getSupportFragmentManager().popBackStack();

            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }


    private void askForExitApp() {

        NoticeDialog noticeDialog = new NoticeDialog(this);
        noticeDialog.addNotice(true, null, "Bạn muốn thoát trò chơi?", "Hủy", "Đồng ý", v -> {
            if (v.getId() == R.id.bt_ok) {
                App.getInstance().getMediaManager().stopGameSound();
                finish();
            }
            noticeDialog.dismiss();
        });
        noticeDialog.show();

    }

    @Override
    protected void onStop() {
        super.onStop();

        App.getInstance().getMediaManager().pauseSong();
    }

    @Override
    protected void onStart() {
        super.onStart();

        App.getInstance().getMediaManager().resumeSong();
    }


    private void askForStopGame(Fragment fragmentManager) {
        ((M003PlayFragment) fragmentManager).stopCountDown();//dung dem nguoc
        NoticeDialog noticeDialog = new NoticeDialog(this);
        noticeDialog.addNotice(true, null, "Bạn muốn dừng cuộc chơi?", "Hủy", "Đồng ý", v -> {
            if (v.getId() == R.id.bt_ok) {
                App.getInstance().getMediaManager().stopBGSound();
                App.getInstance().setAllowbacked(false);
                noticeDialog.dismiss();
                App.getInstance().getMediaManager().playGameSound(MediaManager.THANKS, mp -> {

                    App.getInstance().getMediaManager().playBG(MediaManager.BG_SOUND);
                    showFragment(M001StartFragment.TAG, null, false);
                    App.getInstance().getMediaManager().stopGameSound();
                    App.getInstance().setOnMusic(false);
                    App.getInstance().setAllowbacked(true);
                });

            } else {
                noticeDialog.dismiss();
                App.getInstance().setAllowbacked(true);
                ((M003PlayFragment) fragmentManager).setCountDown(((M003PlayFragment) fragmentManager).getCurrentTime());
            }
        });
        noticeDialog.show();

    }


}