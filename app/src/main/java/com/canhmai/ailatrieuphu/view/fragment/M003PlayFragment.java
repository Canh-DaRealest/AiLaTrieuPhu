package com.canhmai.ailatrieuphu.view.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.canhmai.ailatrieuphu.App;
import com.canhmai.ailatrieuphu.db.entities.Question;
import com.canhmai.ailatrieuphu.model.MediaManager;
import com.canhmai.ailatrieuphu.R;
import com.canhmai.ailatrieuphu.databinding.M003PlayFragmentBinding;
import com.canhmai.ailatrieuphu.dialog.DialogAudience;
import com.canhmai.ailatrieuphu.dialog.DialogCallForHelp;
import com.canhmai.ailatrieuphu.dialog.DialogHighScore;
import com.canhmai.ailatrieuphu.dialog.DialogSearch;
import com.canhmai.ailatrieuphu.dialog.DialogTuVan;
import com.canhmai.ailatrieuphu.dialog.NoticeDialog;
import com.canhmai.ailatrieuphu.view.act.MainActivity;
import com.canhmai.ailatrieuphu.viewmodel.GamePlayVM;

import java.util.Arrays;
import java.util.Random;

public class M003PlayFragment extends BaseFragment<GamePlayVM, M003PlayFragmentBinding> {
    public static final String TAG = M003PlayFragment.class.getName();
    public CountDownTimer countDownTimer;  // dem nguoc
    private final TextView[] textViewMileStone = new TextView[15];    //moc tien thuong

    public long getCurrentTime() {
        return viewModel.getCurrentTime();
    }

    @Override
    protected void initViews() {
        viewModel.setupGameLogic();
        viewModel.updateListQuestions();
        addTextViewMileStone();
        showMoneyLevel();

        doClick();

    }

    private void addTextViewMileStone() {


        textViewMileStone[0] = binding.includeMilestone.tvLv1;
        textViewMileStone[1] = binding.includeMilestone.tvLv2;
        textViewMileStone[2] = binding.includeMilestone.tvLv3;
        textViewMileStone[3] = binding.includeMilestone.tvLv4;
        textViewMileStone[4] = binding.includeMilestone.tvLv5;
        textViewMileStone[5] = binding.includeMilestone.tvLv6;
        textViewMileStone[6] = binding.includeMilestone.tvLv7;
        textViewMileStone[7] = binding.includeMilestone.tvLv8;
        textViewMileStone[8] = binding.includeMilestone.tvLv9;
        textViewMileStone[9] = binding.includeMilestone.tvLv10;
        textViewMileStone[10] = binding.includeMilestone.tvLv11;
        textViewMileStone[11] = binding.includeMilestone.tvLv12;
        textViewMileStone[12] = binding.includeMilestone.tvLv13;
        textViewMileStone[13] = binding.includeMilestone.tvLv14;
        textViewMileStone[14] = binding.includeMilestone.tvLv15;


    }

    private void doClick() {

        binding.listQuestion.setOnClickListener(this);
        binding.ivGiveUp.setOnClickListener(this);
        binding.ivTuVan.setOnClickListener(this);
        binding.ivChangeQuestion.setOnClickListener(this);
        binding.iv5050.setOnClickListener(this);
        binding.ivAskAudience.setOnClickListener(this);
        binding.includeMilestone.btHide.setOnClickListener(this);
        binding.ivSearch.setOnClickListener(this);
        binding.ivCall.setOnClickListener(this);
        binding.tvA.setOnClickListener(this);
        binding.tvB.setOnClickListener(this);
        binding.tvC.setOnClickListener(this);
        binding.tvD.setOnClickListener(this);
    }

    protected void clickView(View v) {
        if (v.getId() == R.id.list_question) {
            showListMoney();
        } else if (v.getId() == R.id.iv_give_up) {
            askForExitGame();
        } else if (v.getId() == R.id.iv_5050) {
            hideTwoAns();
        } else if (v.getId() == R.id.iv_ask_audience) {
            showPercentage();
        } else if (v.getId() == R.id.iv_call) {
            doCalling();
        } else if (v.getId() == R.id.iv_change_question) {
            changeQuestion();
        } else if (v.getId() == R.id.iv_search) {
            showSearchingDialog();
        } else if (v.getId() == R.id.tv_A) {
            checkAns(binding.tvA, MediaManager.CHOOSE_A, MediaManager.TRUE_A, MediaManager.TRUE_A_NEU_CHOI_TIEP, 1);
        } else if (v.getId() == R.id.tv_B) {
            checkAns(binding.tvB, MediaManager.CHOOSE_B, MediaManager.TRUE_B, MediaManager.TRUE_B_NEU_CHOI_TIEP, 2);
        } else if (v.getId() == R.id.tv_C) {
            checkAns(binding.tvC, MediaManager.CHOOSE_C, MediaManager.TRUE_C, MediaManager.TRUE_C_NEU_CHOI_TIEP, 3);
        } else if (v.getId() == R.id.tv_D) {
            checkAns(binding.tvD, MediaManager.CHOOSE_D, MediaManager.TRUE_D, MediaManager.TRUE_D_NEU_CHOI_TIEP, 4);
        } else if (v.getId() == R.id.iv_tu_van) {
            ask3Audience();
        }
    }

    private void ask3Audience() {
        stopCountDown();

        viewModel.setAskAbleState(false);
        viewModel.setButtonState(false);
        setClickableButton();

        binding.ivTuVan.setImageResource(R.drawable.circle_tuvan_active);
        DialogTuVan dialogTuVan = new DialogTuVan(mContext);
        dialogTuVan.add4Ans();
        dialogTuVan.getNewTruePer(viewModel.getIDTrueCase());

        dialogTuVan.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                binding.ivTuVan.setImageResource(R.drawable.circle_tuvan_off);
                setCountDown(viewModel.getCurrentTime());
                setClickableButton();
            }
        });

        App.getInstance().getMediaManager().playGameSound(MediaManager.TO_TU_VAN, new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                dialogTuVan.show();
                dialogTuVan.anouceTrueAns();
            }
        });
    }

    private void showSearchingDialog() {
        stopCountDown();
        NoticeDialog noticeDialog = new NoticeDialog(mContext);
        binding.ivSearch.setImageResource(R.drawable.circle_search_active);

        noticeDialog.addNotice(false, "Thông báo", "Tìm kiếm ngay bây giờ?", "Đóng", "Tiếp tục", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.bt_no) {
                    noticeDialog.dismiss();
                    binding.ivSearch.setImageResource(R.drawable.circle_search);
                    setCountDown(viewModel.getCurrentTime());
                } else {
                    noticeDialog.dismiss();
                    doSearching();
                }
            }
        });
        noticeDialog.show();


        noticeDialog.show();
    }


    private void doSearching() {
        DialogSearch dialogSearch = new DialogSearch(mContext);
        setWindowParams(dialogSearch);
        dialogSearch.show();
        dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                setCountDown(viewModel.getCurrentTime());
                binding.ivSearch.setImageResource(R.drawable.circle_search);
            }
        });
    }

    private void setWindowParams(Dialog dialog) {
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }


    //show moc tien hien tai
    private void showMoneyLevel() {

        setStateButton(false);
        openAndCloseDrawer(true, true);
        int level = viewModel.getCurrentLevel();
        if (level > 0) {
            textViewMileStone[level - 1].setBackground(null);
        }
        textViewMileStone[level].setBackgroundResource(R.drawable.bg_player_image_money_curent);
//						viewModel.setCurrentLevel(level);

        loadQuestionSound();

    }

    private void setStateButton(boolean stateButton) {
        viewModel.setButtonState(stateButton);
        setClickableButton();
    }

    //kiem tra dap an
    private void checkAns(TextView v, int[] chooseAns, int[] trueAns, int[] neuChoiTiep, int idAns) {

        setStateButton(false);
        stopCountDown();
        App.getInstance().setAllowbacked(false);
        viewModel.setViewClicked(v);

        v.setBackgroundResource(R.drawable.ic_answer_background_selected);

        if (viewModel.getBooleanGiveUpState()) {
            App.getInstance().getMediaManager().playGameSound(chooseAns, new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    App.getInstance().getMediaManager().playGameSound(MediaManager.ANS_NOW, mp1 -> {
                        //neu dung
                        if (viewModel.checkAnswer(idAns)) {
                            M003PlayFragment.this.answerTrue(trueAns);
                            //neu sai
                        } else {
                            M003PlayFragment.this.answerFalse();
                        }
                    });
                }
            });
        } else {
            App.getInstance().getMediaManager().playGameSound(MediaManager.ANS_NOW, new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (viewModel.checkAnswer(idAns)) {
                        M003PlayFragment.this.answerTrue(neuChoiTiep);
                        //neu sai
                    } else {
                        M003PlayFragment.this.answerFalse();
                    }
                }
            });
        }

    }

    // dap an sai
    private void answerFalse() {
        viewModel.getViewClicked().setBackgroundResource(R.drawable.ic_answer_background_wrong);
        //dap an dung
        getAnswerTextView(viewModel.getIDTrueCase()).setBackgroundResource(R.drawable.ic_answer_background_true);

        startAnimation(getAnswerTextView(viewModel.getIDTrueCase()));

        if (!viewModel.getBooleanGiveUpState()) {
            int[] idSound = new int[1];
            if (viewModel.getIDTrueCase() == 1) {
                idSound[0] = R.raw.lose_1;
            } else if (viewModel.getIDTrueCase() == 2) {
                idSound[0] = R.raw.lose_2;
            }
            if (viewModel.getIDTrueCase() == 3) {
                idSound[0] = R.raw.lose_3;
            }
            if (viewModel.getIDTrueCase() == 4) {
                idSound[0] = R.raw.lose_4;
            }
            App.getInstance().getMediaManager().playGameSound(idSound, new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    App.getInstance().getMediaManager().playGameSound(MediaManager.RAT_TIEC_CHIA_TAY, null);
                    saveScore();
                }
            });

        } else {
            App.getInstance().getMediaManager().playGameSound(App.getInstance().getMediaManager().getSoundLoseCase(viewModel.getIDTrueCase()), mp -> new Handler().postDelayed(() -> {
                saveScore();

            }, 1000));
        }

    }

    private void playAgain() {

        NoticeDialog noticeDialog = new NoticeDialog(mContext);
        noticeDialog.addNotice(false, null, "Chơi lại từ đầu?", "Không", "Ok", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.bt_ok) {
                    noticeDialog.dismiss();
                    for (TextView textView : textViewMileStone) {
                        if (textView == textViewMileStone[4] || textView == textViewMileStone[9] || textView == textViewMileStone[14]) {
                            textView.setBackgroundResource(R.drawable.bg_player_image_money_milestone);
                        } else {
                            textView.setBackground(null);
                        }
                    }
                    openAndCloseDrawer(true, true);
                    App.getInstance().getMediaManager().playGameSound(MediaManager.GO_FIND, new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            onMainCallBack.showFragment(M003PlayFragment.TAG, null, false);
                        }
                    });

                } else {
                    noticeDialog.dismiss();
                    App.getInstance().getMediaManager().playBG(MediaManager.BG_SOUND);
                    onMainCallBack.showFragment(M001StartFragment.TAG, false, false);
                    viewModel.setAllowBacked(true);
                }
            }
        });
        noticeDialog.show();
    }

    //dap an dung
    private void answerTrue(int[] trueAns) {

        getAnswerTextView(viewModel.getIDTrueCase()).setBackgroundResource(R.drawable.ic_answer_background_true);
        startAnimation(getAnswerTextView(viewModel.getIDTrueCase()));

        if (viewModel.getCurrentLevel() == 14) {
            App.getInstance().getMediaManager().playGameSound(MediaManager.CAU_15_DUNG, new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    viewModel.setBooleanChampionState(true);
                    continueGame();

                }
            });
        } else {

            App.getInstance().getMediaManager().playGameSound(trueAns, new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (!viewModel.getBooleanGiveUpState()) {
                        App.getInstance().getMediaManager().playGameSound(MediaManager.RAT_TIEC_CHIA_TAY, null);
                        saveScore();
                    } else {
                        continueGame();
                    }

                }
            });
        }
    }


    private void continueGame() {

        String money = (textViewMileStone[viewModel.getCurrentLevel()].getText().toString());

        binding.tvMoneyCount.setText(money);
        //tra loi dung cau 5
        if (viewModel.getCurrentLevel() == 4) { // lv =5, lv = 10, lv = 15  .. textviewlevel[level-1] =5

            M003PlayFragment.this.doCongratulate(MediaManager.VUOT_MOC_1);

            //tra loi dung cau 10
        } else if (viewModel.getCurrentLevel() == 9) {

            M003PlayFragment.this.doCongratulate(MediaManager.VUOT_MOC_2);

            //tra loi dung cau 15
        } else if (viewModel.getCurrentLevel() == 14) {
            M003PlayFragment.this.doCongratulate(MediaManager.VUOT_MOC_3);

        } else {
            viewModel.setCurrentLevel(viewModel.getCurrentLevel() + 1);
            new Handler().postDelayed(M003PlayFragment.this::showMoneyLevel, 1000);
        }
    }

    //thong bao vuot moc
    private void doCongratulate(int[] vuotMocSound) {
        //neu nguoi choi vuot qua moc 3
        if (Arrays.equals(vuotMocSound, MediaManager.VUOT_MOC_3)) {
            stopCountDown();
            handleFireWorkAnimation(true);

            NoticeDialog noticeDialog = new NoticeDialog(mContext);
            App.getInstance().getMediaManager().playGameSound(MediaManager.BGCHAMPION, mp -> App.getInstance().getMediaManager().playGameSound(vuotMocSound, mp1 -> {
                noticeDialog.addNotice(false, "Xin Chúc Mừng", "Bạn đã trở thành nhà vô địch", null, "OK", v -> {
                    if (v.getId() == R.id.bt_ok) {
                        handleFireWorkAnimation(false);
                        saveScore();
                        noticeDialog.dismiss();
                    }
                });
                noticeDialog.show();
            }));

        } else if (Arrays.equals(vuotMocSound, MediaManager.VUOT_MOC_1)) {

            openAndCloseDrawer(true, true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    openAndCloseDrawer(false, false);
                }
            }, 5000);

            App.getInstance().getMediaManager().playGameSound(vuotMocSound, new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    viewModel.setCurrentLevel(viewModel.getCurrentLevel() + 1);
                    M003PlayFragment.this.showMoneyLevel();

                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    binding.ivTuVan.setVisibility(View.VISIBLE);
                    startAnimation(binding.ivTuVan);
                }
            }, 3000);


        } else {
            //   nguoi choi vuot qua moc 1 va 2
            openAndCloseDrawer(true, true);
            App.getInstance().getMediaManager().playGameSound(vuotMocSound, new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    openAndCloseDrawer(false, false);
                    viewModel.setCurrentLevel(viewModel.getCurrentLevel() + 1);
                    M003PlayFragment.this.showMoneyLevel();

                }
            });
        }
    }

    private void handleFireWorkAnimation(boolean state) {
        if (state) {
            binding.lottieFirework1.setVisibility(View.VISIBLE);
            binding.lottieFirework2.setVisibility(View.VISIBLE);

            binding.lottieFirework1.playAnimation();
            binding.lottieFirework2.playAnimation();
        } else {
            binding.lottieFirework1.setVisibility(View.GONE);
            binding.lottieFirework2.setVisibility(View.GONE);

            binding.lottieFirework1.cancelAnimation();
            binding.lottieFirework2.cancelAnimation();
        }

    }

    //xu ly am thanh cho cau hoi
    private void loadQuestionSound() {

        App.getInstance().getMediaManager().playGameSound(App.getInstance().getMediaManager().getSoundLevel(viewModel.getCurrentLevel()), mp -> {
            if (viewModel.getCurrentLevel() == 4 || viewModel.getCurrentLevel() == 9 || viewModel.getCurrentLevel() == 14) { //vd: lv =5, lv = 10, lv = 15 .... textViewLevel[level - 1]=tv_lv4
                textViewMileStone[viewModel.getCurrentLevel()].setBackgroundResource(R.drawable.bg_player_image_money_curent);
                App.getInstance().getMediaManager().playGameSound(MediaManager.IMPORTANT, mp1 -> {
                    startGame();
                });

            } else {
                //     binding.icludeList.frMain.setVisibility(View.GONE);
                startGame();
            }

        });
    }

    //bat dau tro choi
    private void startGame() {
        viewModel.setCurrentTime(31000);
        openAndCloseDrawer(false, false);

        viewModel.setProgress(100);
        playGameSound();
        //cac button co the click

        setStateButton(true);
        //dat lai thoi gian
        stopCountDown();
        setCountDown(viewModel.getCurrentTime());

        //dat lai backgroud mac dinh cho cac cau  tra loi
        setBackgroundAns();
        //an dp an (5050)
        viewModel.setBooleanHiden(false);

        viewModel.setAllowBacked(true);
        int lv = viewModel.getCurrentLevel();

        viewModel.setQuestionAtCurrentLevel(viewModel.getListQuestion().get(lv));
        Question question = viewModel.getQuestionAtCurrentLevel();

        viewModel.setTrueCase(question.getTrueCase());
        Log.e(TAG, "startGame: truecase" + question.getTrueCase()   );
        Log.e(TAG, "startGame: current level" +lv   );
        binding.tvQuestionNumber.setText(String.format("Câu %d", lv + 1));

        binding.tvShowQuestion.setText(question.getQuestion());

        binding.tvA.setText(String.format("A:  %s", question.getCaseA()));
        binding.tvB.setText(String.format("B:  %s", question.getCaseB()));
        binding.tvC.setText(String.format("C:  %s", question.getCaseC()));
        binding.tvD.setText(String.format("D:  %s", question.getCaseD()));

    }

    private void openAndCloseDrawer(boolean isOpen, boolean isLocked) {
        if (isOpen) {
            binding.dlMyDrawerlayout.openDrawer(GravityCompat.START);
        } else {
            binding.dlMyDrawerlayout.close();
        }

        if (isLocked) {
            binding.dlMyDrawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);

        } else {
            binding.dlMyDrawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
        }
    }

    private void playGameSound() {
        if (viewModel.getCurrentLevel() < 4) {
            App.getInstance().getMediaManager().playBG(MediaManager.BG_MOC1);
        } else if (viewModel.getCurrentLevel() < 9) {
            App.getInstance().getMediaManager().playBG(MediaManager.BG_MOC2);
        } else if (viewModel.getCurrentLevel() < 14) {
            App.getInstance().getMediaManager().playBG(MediaManager.BG_MOC3);
        }
    }

    //background mac dinh cho 4 dap an
    private void setBackgroundAns() {
        binding.tvA.setBackgroundResource(R.drawable.ic_player_answer);
        binding.tvB.setBackgroundResource(R.drawable.ic_player_answer);
        binding.tvC.setBackgroundResource(R.drawable.ic_player_answer);
        binding.tvD.setBackgroundResource(R.drawable.ic_player_answer);
    }

    //dem nguoc
    public void setCountDown(long time) {
        countDownTimer = new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                binding.tvTimeCountdown.setText(String.format("%d", millisUntilFinished / 1000)); //30000/1000==30

                viewModel.setCurrentTime(millisUntilFinished);
                viewModel.setProgress(viewModel.getProgress() - 3);
                binding.progressBarLoading.setProgress(viewModel.getProgress());
                if (millisUntilFinished / 1000 == 0) {
                    binding.progressBarLoading.setProgress(0);
                }
            }

            @Override
            public void onFinish() {

                setStateButton(false);

                App.getInstance().getMediaManager().playGameSound(MediaManager.OUT_OF_TIME, mp -> {
                    App.getInstance().getMediaManager().stopBGSound();
                    NoticeDialog noticeDialog = new NoticeDialog(mContext);

                    noticeDialog.addNotice(false, "Thua rồi!!", "Bạn đã hết thời gian", null, "Đóng", v -> {
                        if (v.getId() == R.id.bt_ok) {
                            viewModel.setAllowBacked(false);
                            noticeDialog.dismiss();
                            saveScore();
                        }
                    });
                    noticeDialog.show();
                });
            }
        }.start();
    }


    //hien list tien
    private void showListMoney() {
        textViewMileStone[viewModel.getCurrentLevel()].setBackgroundResource(R.drawable.bg_player_image_money_curent);
        openAndCloseDrawer(true, false);


    }

    //trang thai click
    private void setClickableButton() {
        boolean state = viewModel.getButtonState();
        binding.tvA.setClickable(state);
        binding.tvB.setClickable(state);
        binding.tvC.setClickable(state);
        binding.tvD.setClickable(state);
        binding.ivSearch.setClickable(state);
        binding.listQuestion.setClickable(state);
        binding.ivGiveUp.setClickable(state); //dung choi
        binding.ivChangeQuestion.setClickable(state); //doi cau hoi
        binding.iv5050.setClickable(state); //5050
        binding.ivAskAudience.setClickable(state);//hoi khan gia
        binding.ivCall.setClickable(state);//goi dien
        binding.ivTuVan.setClickable(state);//tu van tai cho


        if (!viewModel.getBooleanGiveUpState()) { //dung choi
            binding.ivGiveUp.setClickable(false);
        }

        if (!viewModel.getBooleanChangeQuestionButton()) { //doi cau hoi
            binding.ivChangeQuestion.setClickable(false);
        }

        if (!viewModel.getBoolean50_50Button()) { //5050
            binding.iv5050.setClickable(false);
        }

        if (!viewModel.getBooleanAudienceButton()) { //hoi khan gia
            binding.ivAskAudience.setClickable(false);
        }

        if (!viewModel.getBooleanCallButton()) {
            binding.ivCall.setClickable(false);//goi dien
        }

        if (!viewModel.getBooleanAdviceButton()) {
            binding.ivTuVan.setClickable(false); //to tu van
        }
        if (!viewModel.getBooleanSearchButton()) {
            binding.ivSearch.setClickable(false);//tim kiem
        }

    }


    //tra ve dap an dung cho tung cau hoi
    private TextView getAnswerTextView(int id) {
        //tra ve view ung voi truecase cua tung cau hoi

        TextView kq = null;
        if (id == 1) {
            kq = binding.tvA;
        } else if (id == 2) {
            kq = binding.tvB;
        }
        if (id == 3) {
            kq = binding.tvC;
        }
        if (id == 4) {
            kq = binding.tvD;
        }
        return kq;

    }


    //thay doi cau hoi
    private void changeQuestion() {
        stopCountDown();
        binding.ivChangeQuestion.setImageResource(R.drawable.player_button_image_help_change_question_active);
        NoticeDialog noticeDialog = new NoticeDialog(mContext);
        noticeDialog.addNotice(false, null, "Bạn muốn đổi câu hỏi?", "Không", "Có", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.bt_ok) {
                    viewModel.setBooleanChangeQuestion(false);
                    setStateButton(true);
                    setBackgroundAns();
                    synchronized (this) {
                        new Thread() {
                            @Override
                            public void run() {
                                int level = viewModel.getCurrentLevel() + 1;

                                viewModel.setQuestionChange(App.getInstance().getDb().getQuestionDAO().getQuestionAtLevel(level, viewModel.getCurrentId()));
                                viewModel.replaceCurrentQuestion(viewModel.getQuestionChange(), viewModel.getCurrentLevel());
                                Log.e(TAG, "run: " + viewModel.getQuestionChange().toString());
                                Log.e(TAG, "run: " + " /// " + viewModel.getListQuestion().get(viewModel.getCurrentLevel()).toString());
                                MainActivity mainActivity = (MainActivity) mContext;
                                mainActivity.runOnUiThread(() -> {
                                    binding.ivChangeQuestion.setImageResource(R.drawable.ic__change_question_x);
                                    startGame();
                                    noticeDialog.dismiss();
                                });
                            }

                        }.start();
                    }

                } else {
                    binding.ivChangeQuestion.setImageResource(R.drawable.ic_change_question);
                    noticeDialog.dismiss();
                    setCountDown(viewModel.getCurrentTime());
                }
            }
        });
        noticeDialog.show();
    }

    //goi dien thoai cho nguoi than
    private void doCalling() {
        stopCountDown();

        viewModel.setBooleanCallState(false);
        binding.ivCall.setImageResource(R.drawable.player_button_image_help_call_active);
        DialogCallForHelp dialogCallForHelp = new DialogCallForHelp(mContext);
        dialogCallForHelp.setCancelable(false);
        dialogCallForHelp.setCanceledOnTouchOutside(false);
        dialogCallForHelp.show();
        Window window = dialogCallForHelp.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialogCallForHelp.show();
        dialogCallForHelp.setTrueAnswer(viewModel.getListQuestion().get(viewModel.getCurrentLevel()).getTrueCase());

        App.getInstance().getMediaManager().playGameSound(MediaManager.CALL_WHO, mp -> dialogCallForHelp.setClickable(true));
        dialogCallForHelp.setOnDismissListener(dialog -> {
            setCountDown(viewModel.getCurrentTime());
            binding.ivCall.setImageResource(R.drawable.ic_help_call_x);
        });
    }

    //hoi khan gia trong truong quay
    private void showPercentage() {
        DialogAudience dialogAudience = new DialogAudience(mContext);

        setStateButton(false);
        viewModel.setBooleanAudienceState(false);
        stopCountDown();
        binding.ivAskAudience.setImageResource(R.drawable.player_button_image_help_audience_active);

        //dap an dung
        TextView trueCase;

        trueCase = getAnswerTextView(viewModel.getIDTrueCase());

        if (viewModel.isHidden()) {
            TextView tv1 = viewModel.getHiddenList().get(0);
            TextView tv2 = viewModel.getHiddenList().get(1);

            //an cot % cua dap an neu da su dung 5050
            dialogAudience.hideView(checkWhatViewisBeingHidden(tv1), checkWhatViewisBeingHidden(tv2));
        }
        if (trueCase.equals(binding.tvA)) {
            dialogAudience.setTrueCase("A");
        } else if (trueCase.equals(binding.tvB)) {
            dialogAudience.setTrueCase("B");
        } else if (trueCase.equals(binding.tvC)) {
            dialogAudience.setTrueCase("C");
        } else if (trueCase.equals(binding.tvD)) {
            dialogAudience.setTrueCase("D");
        }

        dialogAudience.prepareToVote();
        Window window = dialogAudience.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialogAudience.show();
        App.getInstance().getMediaManager().playGameSound(MediaManager.KHAN_GIA, mp -> App.getInstance().getMediaManager().playGameSound(MediaManager.CHUYEN_GIA, mp1 -> {
            dialogAudience.votting();
            setStateButton(true);
        }));
        dialogAudience.setOnDismissListener(dialog -> {
            M003PlayFragment.this.setCountDown(viewModel.getCurrentTime());
            binding.ivAskAudience.setImageResource(R.drawable.ic_help_audience_x);
        });
    }

    //kiem tra dap an nao dang bi an
    private int checkWhatViewisBeingHidden(TextView tv) {
        int kq = 0;

        if (tv.equals(binding.tvA)) {
            kq = 1;
        } else if (tv.equals(binding.tvB)) {
            kq = 2;

        } else if (tv.equals(binding.tvC)) {
            kq = 3;

        } else if (tv.equals(binding.tvD)) {
            kq = 4;
        }
        return kq;
    }

    // tro giup 5050
    private void hideTwoAns() {
        viewModel.setBoolean50_50State(false);
        stopCountDown();
        binding.iv5050.setImageResource(R.drawable.player_button_image_help_5050_active);
        setStateButton(false);
        App.getInstance().getMediaManager().playGameSound(MediaManager.SOUND_5050, mp -> {
            setStateButton(true);
            int b = 0;
            int count = 0;
            int trueCaseId;
            trueCaseId = viewModel.getIDTrueCase();
            while (count <= 1) {
                Random random = new Random();
                int ran = random.nextInt(4) + 1;
                if (ran != trueCaseId && ran != b) {  //id tc =3
                    b = ran;
                    TextView hide1 = getAnswerTextView(b);

                    viewModel.updateHidenList(hide1);
                    hide1.setBackgroundResource(R.drawable.ic_answer_background_hide);
                    hide1.setText("");
                    hide1.setClickable(false);
                    count++;
                }

            }
            binding.iv5050.setImageResource(R.drawable.ic_help_5050_off);
            setCountDown(viewModel.getCurrentTime());
            viewModel.setBooleanHiden(true);
        });


    }

    //thoat game
    private void askForExitGame() {
        stopCountDown();
        binding.ivGiveUp.setImageResource(R.drawable.player_button_image_help_stop_active);
        NoticeDialog noticeDialog = new NoticeDialog(mContext);
        App.getInstance().getMediaManager().playGameSound(MediaManager.HOI_DUNG_CHOI, null);
        noticeDialog.addNotice(false, "Thông báo", "Bạn muốn dừng cuộc chơi?", "Hủy", "Có", v -> {
            if (v.getId() == R.id.bt_ok) {
                App.getInstance().setAllowbacked(false);
                App.getInstance().getMediaManager().stopBGSound();
                viewModel.setBooleanGiveUp(false);
                setStateButton(false);
                App.getInstance().getMediaManager().playGameSound(MediaManager.DUNG_TAI_DAY,
                        mp -> App.getInstance().getMediaManager().playGameSound(MediaManager.NEU_CHOI_TIEP, mp1 -> {
                            viewModel.setBooleanChampionState(false);
                            viewModel.setBooleanAdvice(false);
                            viewModel.setBooleanAudienceState(false);
                            viewModel.setBooleanChangeQuestion(false);
                            viewModel.setBoolean50_50State(false);
                            viewModel.setBooleanCallState(false);
                            viewModel.setBooleanSearchState(false);


                            setStateButton(true);

                        }));

            } else {
                binding.ivGiveUp.setImageResource(R.drawable.ic_stop_game);
                setCountDown(viewModel.getCurrentTime());
            }
            noticeDialog.dismiss();
        });
        noticeDialog.show();
    }


    // hoat anh dap an
    private void startAnimation(View v) {
        Animation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setDuration(100);
        animation.setRepeatCount(21);
        v.startAnimation(animation);

    }

    @Override
    public void onStop() {
        super.onStop();
        stopCountDown();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopCountDown();
    }

    @Override
    public void onStart() {
        super.onStart();
        setCountDown(viewModel.getCurrentTime());
    }

    public void stopCountDown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }


    //luu diem
    private void saveScore() {
        stopCountDown();
        DialogHighScore dialogHighScore = new DialogHighScore(mContext);
        if (viewModel.getBooleanChampionState()) {
            String text = binding.tvMoneyCount.getText().toString();

            doCompareScore(text, dialogHighScore);
        } else if (!viewModel.getBooleanGiveUpState()) {
            App.getInstance().getMediaManager().playGameSound(MediaManager.RAT_TIEC_CHIA_TAY, mp -> {

                if ((viewModel.getCurrentLevel() - 1) > 0) {
                    String text = binding.tvMoneyCount.getText().toString();

                    doCompareScore(text, dialogHighScore);

                } else {
                    sayGoodBye();
                }

            });

        } else {

            App.getInstance().getMediaManager().playGameSound(MediaManager.RAT_TIEC_CHIA_TAY, mp -> {
                int level = viewModel.getCurrentLevel() - 1;
                if (level == 14 || level == 4 || level == 9) {
                    level--;
                    viewModel.setCurrentLevel(level);
                }

                String newScore = "";
                if (level > 0) {
                    if (level < 4) {
                        newScore = "200,000";
                    } else if (level < 9) {
                        newScore = "2,000,000";
                    } else if (level < 14) {
                        newScore = "22,000,000";
                    } else {
                        newScore = "150,000,000";
                    }
                    doCompareScore(newScore, dialogHighScore);
                } else {
                    sayGoodBye();
                }
            });
        }
    }

    private void doCompareScore(String text, DialogHighScore dialogHighScore) {

        dialogHighScore.setScore(text);
        dialogHighScore.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                sayGoodBye();
            }
        });
        dialogHighScore.show();
    }

    private void sayGoodBye() {
        //cam on ban.....
        App.getInstance().getMediaManager().playGameSound(MediaManager.THANKS, mp -> {
            if (viewModel.getBooleanGiveUpState()) {

                playAgain();

            } else {
                App.getInstance().getMediaManager().playBG(MediaManager.BG_SOUND);
                onMainCallBack.showFragment(M001StartFragment.TAG, false, false);
                App.getInstance().setAllowbacked(true);
                App.getInstance().setOnMusic(false);

            }
        });
    }

    @Override
    protected Class<GamePlayVM> getClassViewModel() {
        return GamePlayVM.class;
    }

    @Override
    protected M003PlayFragmentBinding initViewBinding(LayoutInflater inflater) {
        return M003PlayFragmentBinding.inflate(inflater);
    }
}

