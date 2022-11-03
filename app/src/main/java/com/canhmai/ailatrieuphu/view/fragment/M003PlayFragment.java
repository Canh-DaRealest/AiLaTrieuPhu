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
import com.canhmai.ailatrieuphu.model.MediaManager;
import com.canhmai.ailatrieuphu.R;
import com.canhmai.ailatrieuphu.databinding.M003PlayFragmentBinding;
import com.canhmai.ailatrieuphu.db.entities.Question;
import com.canhmai.ailatrieuphu.dialog.DialogAudience;
import com.canhmai.ailatrieuphu.dialog.DialogCallForHelp;
import com.canhmai.ailatrieuphu.dialog.DialogHighScore;
import com.canhmai.ailatrieuphu.dialog.DialogSearch;
import com.canhmai.ailatrieuphu.dialog.DialogTuVan;
import com.canhmai.ailatrieuphu.dialog.NoticeDialog;
import com.canhmai.ailatrieuphu.view.act.MainActivity;
import com.canhmai.ailatrieuphu.viewmodel.CommonVM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class M003PlayFragment extends BaseFragment<CommonVM, M003PlayFragmentBinding> {
				public static final String TAG = M003PlayFragment.class.getName();
				public CountDownTimer countDownTimer;  // dem nguoc
				Question questionAtThisLv;
				Question questionChange;
				TextView hide1;  // dap an bi an
				List<TextView> hiddenList = new ArrayList<>();  // list chua dap an bi an
				private boolean isGiveUp = true;
				private boolean isShowed = false;
				private boolean isChampion = false;
				private boolean isSearchable = true;
				private boolean isAskable = true;
				private int progress;
				private View viewClicked;   // view da duoc click chua
				private int level = -1;  //  xac dinh level cau hoi
				private boolean isHidden;
				private TextView[] textViewLevel;    //moc tien thuong
				private int trueCase;    //dap an dung
				private long currentTime = 31000;
				// xu ly clickable cho tung button
				private boolean changeQuestionAble = true;
				private boolean askAudienceAble = true;
				private boolean callAble = true;
				private boolean dofifty_fiftyAble = true;

				public long getCurrentTime() {
								return currentTime;
				}

				@Override
				protected void initViews() {

								new Thread() {
												@Override
												public void run() {
																App.getInstance().getStorage().listHighScore = App.getInstance().getDb().getHighScoreDAO().getAll();
																App.getInstance().getStorage().listQ = App.getInstance().getDb().getQuestionDAO().getAll();
																App.getInstance().getStorage().setListQuestion(App.getInstance().getStorage().getListQ());
																Log.e(TAG, "LIST.SIZE = " + App.getInstance().getStorage().listQ.size());
												}
								}.start();
								addTextViewLevel();
								showMoneyLevel();

								doClick();

				}

				private void addTextViewLevel() {
								Log.e(TAG, "1111111111 = ");
								textViewLevel = new TextView[15];
								textViewLevel[0] = binding.include.tvLv1;
								textViewLevel[1] = binding.include.tvLv2;
								textViewLevel[2] = binding.include.tvLv3;
								textViewLevel[3] = binding.include.tvLv4;
								textViewLevel[4] = binding.include.tvLv5;
								textViewLevel[5] = binding.include.tvLv6;
								textViewLevel[6] = binding.include.tvLv7;
								textViewLevel[7] = binding.include.tvLv8;
								textViewLevel[8] = binding.include.tvLv9;
								textViewLevel[9] = binding.include.tvLv10;
								textViewLevel[10] = binding.include.tvLv11;
								textViewLevel[11] = binding.include.tvLv12;
								textViewLevel[12] = binding.include.tvLv13;
								textViewLevel[13] = binding.include.tvLv14;
								textViewLevel[14] = binding.include.tvLv15;
				}

				private void doClick() {

								binding.listQuestion.setOnClickListener(this);
								binding.ivGiveUp.setOnClickListener(this);
								binding.ivTuVan.setOnClickListener(this);
								binding.ivChangeQuestion.setOnClickListener(this);
								binding.iv5050.setOnClickListener(this);
								binding.ivAskAudience.setOnClickListener(this);
								binding.include.btHide.setOnClickListener(this);
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

								isAskable = false;
								setClickableButton(false);

								binding.ivTuVan.setImageResource(R.drawable.circle_tuvan_active);
								DialogTuVan dialogTuVan = new DialogTuVan(mContext);
								dialogTuVan.add4Ans();
								dialogTuVan.getNewTruePer(getIDTrueCase());

								dialogTuVan.setOnDismissListener(new DialogInterface.OnDismissListener() {
												@Override
												public void onDismiss(DialogInterface dialog) {
																binding.ivTuVan.setImageResource(R.drawable.circle_tuvan_off);
																setCountDown(currentTime);
																setClickableButton(true);
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
								if (isShowed) {
												noticeDialog.addNotice(false, "Thông báo", "Tìm kiếm ngay bây giờ?", "Đóng", "Tiếp tục", new View.OnClickListener() {
																@Override
																public void onClick(View v) {
																				if (v.getId() == R.id.bt_no) {
																								noticeDialog.dismiss();
																								binding.ivSearch.setImageResource(R.drawable.circle_search);
																								setCountDown(currentTime);
																				} else {
																								noticeDialog.dismiss();
																								doSearching();
																				}
																}
												});
												noticeDialog.show();
								} else {
												noticeDialog.addNotice(false, "Thông báo", "Tìm kiếm thông tin trên Google,\nKhông giới hạn số lần thực hiện", "Đóng", "Tiếp tục", new View.OnClickListener() {
																@Override
																public void onClick(View v) {
																				if (v.getId() == R.id.bt_no) {
																								noticeDialog.dismiss();
																								binding.ivSearch.setImageResource(R.drawable.circle_search);
																								setCountDown(currentTime);
																				} else {
																								isShowed = true;
																								noticeDialog.dismiss();
																								doSearching();
																				}
																}
												});
												noticeDialog.show();
								}
				}

				private void doSearching() {
								DialogSearch dialogSearch = new DialogSearch(mContext);
								setWindowParams(dialogSearch);
								dialogSearch.show();
								dialogSearch.setOnDismissListener(new DialogInterface.OnDismissListener() {
												@Override
												public void onDismiss(DialogInterface dialog) {
																setCountDown(currentTime);
																binding.ivSearch.setImageResource(R.drawable.circle_search);
												}
								});
				}

				private void setWindowParams(Dialog dialog) {
								Window window = dialog.getWindow();
								window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
				}


				//trả về id cua question hiẹn tai
				private int getCurrentID() {
								Question curQuestion = App.getInstance().getStorage().listQ.get(level);

								return curQuestion.getId();
				}


				//show moc tien hien tai
				private void showMoneyLevel() {

								setClickableButton(false);

								level++;
								//Hien list tien
								openAndCloseDrawer(true, true);

								// thay doi background cua moc tien hien tai
								textViewLevel[level].setBackgroundResource(R.drawable.bg_player_image_money_curent);
								//am thanh
								loadQuestionSound();
								//thay doi background moc tien truoc ve mac dinh neu vuot qua cau hoi
								if (level - 1 >= 0) {
												textViewLevel[level - 1].setBackground(null);
								}
				}

				//kiem tra dap an
				private void checkAns(TextView v, int[] chooseAns, int[] trueAns, int[] neuChoiTiep, int idAns) {

								setClickableButton(false);
								stopCountDown();
								App.getInstance().setAllowbacked(false);
								viewClicked = v;

								v.setBackgroundResource(R.drawable.ic_answer_background_selected);

								if (isGiveUp) {
												App.getInstance().getMediaManager().playGameSound(chooseAns, new MediaPlayer.OnCompletionListener() {
																@Override
																public void onCompletion(MediaPlayer mp) {

																				App.getInstance().getMediaManager().playGameSound(MediaManager.ANS_NOW, mp1 -> {
																								//neu dung
																								if (idAns == trueCase) {
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
																				if (idAns == trueCase) {
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
								viewClicked.setBackgroundResource(R.drawable.ic_answer_background_wrong);
								//dap an dung
								getAnswerTextView(trueCase).setBackgroundResource(R.drawable.ic_answer_background_true);
								startAnimation(getAnswerTextView(trueCase));

								if (!isGiveUp) {
												int[] idSound = new int[1];
												if (trueCase == 1) {
																idSound[0] = R.raw.lose_1;
												} else if (trueCase == 2) {
																idSound[0] = R.raw.lose_2;
												}
												if (trueCase == 3) {
																idSound[0] = R.raw.lose_3;
												}
												if (trueCase == 4) {
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
												App.getInstance().getMediaManager().playGameSound(App.getInstance().getMediaManager().getSoundLoseCase(trueCase), mp -> new Handler().postDelayed(() -> {
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
																				for (TextView textView : textViewLevel) {
																								if (textView == textViewLevel[4] || textView == textViewLevel[9] || textView == textViewLevel[14]) {
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
																				App.getInstance().setAllowbacked(true);
																}
												}
								});
								noticeDialog.show();
				}

				//dap an dung
				private void answerTrue(int[] trueAns) {

								getAnswerTextView(trueCase).setBackgroundResource(R.drawable.ic_answer_background_true);
								startAnimation(getAnswerTextView(trueCase));

								if (level == 14) {
												App.getInstance().getMediaManager().playGameSound(MediaManager.CAU_15_DUNG, new MediaPlayer.OnCompletionListener() {
																@Override
																public void onCompletion(MediaPlayer mp) {
																				isChampion = true;
																				continueGame();

																}
												});
								} else {
												App.getInstance().getMediaManager().playGameSound(trueAns, new MediaPlayer.OnCompletionListener() {
																@Override
																public void onCompletion(MediaPlayer mp) {
																				if (!isGiveUp) {
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
								String money = (textViewLevel[level].getText().toString());
								binding.tvMoneyCount.setText(money);
								//tra loi dung cau 5
								if (level == 4) { // lv =5, lv = 10, lv = 15  .. textviewlevel[level-1] =5

												M003PlayFragment.this.doCongratulate(MediaManager.VUOT_MOC_1);

												//tra loi dung cau 10
								} else if (level == 9) {

												M003PlayFragment.this.doCongratulate(MediaManager.VUOT_MOC_2);

												//tra loi dung cau 15
								} else if (level == 14) {
												M003PlayFragment.this.doCongratulate(MediaManager.VUOT_MOC_3);

								} else {
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
																				M003PlayFragment.this.showMoneyLevel();

																}
												});
								}
				}

				private void handleFireWorkAnimation(boolean state) {
								if (state){
												binding.lottieFirework1.setVisibility(View.VISIBLE);
												binding.lottieFirework2.setVisibility(View.VISIBLE);

												binding.lottieFirework1.playAnimation();
												binding.lottieFirework2.playAnimation();
								}else{
												binding.lottieFirework1.setVisibility(View.GONE);
												binding.lottieFirework2.setVisibility(View.GONE);

												binding.lottieFirework1.cancelAnimation();
												binding.lottieFirework2.cancelAnimation();
								}

				}

				//xu ly am thanh cho cau hoi
				private void loadQuestionSound() {

								App.getInstance().getMediaManager().playGameSound(App.getInstance().getMediaManager().getSoundLevel(level), mp -> {
												if (level == 4 || level == 9 || level == 14) { //vd: lv =5, lv = 10, lv = 15 .... textViewLevel[level - 1]=tv_lv4
																textViewLevel[level].setBackgroundResource(R.drawable.bg_player_image_money_curent);
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

								openAndCloseDrawer(false, false);

								progress = 100;
								playGameSound();
								//cac button co the click
								setClickableButton(true);
								//dat lai thoi gian
								stopCountDown();
								setCountDown(31000);

								//dat lai backgroud mac dinh cho cac cau  tra loi
								setBackgroundAns();
								//an dp an (5050)
								isHidden = false;
								App.getInstance().setAllowbacked(true);

								int lv = level;
								questionAtThisLv = App.getInstance().getStorage().listQ.get(level);

								trueCase = questionAtThisLv.getTrueCase();
								binding.tvQuestionNumber.setText(String.format("Câu %d", lv + 1));

								binding.tvShowQuestion.setText(questionAtThisLv.getQuestion());

								binding.tvA.setText(String.format("A:  %s", questionAtThisLv.getCaseA()));
								binding.tvB.setText(String.format("B:  %s", questionAtThisLv.getCaseB()));
								binding.tvC.setText(String.format("C:  %s", questionAtThisLv.getCaseC()));
								binding.tvD.setText(String.format("D:  %s", questionAtThisLv.getCaseD()));

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
								if (level <= 4) {
												App.getInstance().getMediaManager().playBG(MediaManager.BG_MOC1);
								} else if (level <= 9) {
												App.getInstance().getMediaManager().playBG(MediaManager.BG_MOC2);
								} else if (level <= 14) {
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

																currentTime = millisUntilFinished;
																progress -= 3;
																binding.progressBarLoading.setProgress(progress);
																if (millisUntilFinished / 1000 == 0) {
																				binding.progressBarLoading.setProgress(0);
																}
												}

												@Override
												public void onFinish() {
																setClickableButton(false);
																App.getInstance().getMediaManager().playGameSound(MediaManager.OUT_OF_TIME, mp -> {
																				App.getInstance().getMediaManager().stopBGSound();
																				NoticeDialog noticeDialog = new NoticeDialog(mContext);

																				noticeDialog.addNotice(false, "Thua rồi!!", "Bạn đã hết thời gian", null, "Đóng", v -> {
																								if (v.getId() == R.id.bt_ok) {
																												App.getInstance().setAllowbacked(false);
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
								textViewLevel[level].setBackgroundResource(R.drawable.bg_player_image_money_curent);
								openAndCloseDrawer(true, false);


				}

				//trang thai click
				private void setClickableButton(boolean clickable) {

								binding.tvA.setClickable(clickable);
								binding.tvB.setClickable(clickable);
								binding.tvC.setClickable(clickable);
								binding.tvD.setClickable(clickable);
								binding.ivSearch.setClickable(clickable);
								binding.listQuestion.setClickable(clickable);
								binding.ivGiveUp.setClickable(clickable); //dung choi
								binding.ivChangeQuestion.setClickable(clickable); //doi cau hoi
								binding.iv5050.setClickable(clickable); //5050
								binding.ivAskAudience.setClickable(clickable);//hoi khan gia
								binding.ivCall.setClickable(clickable);//goi dien
								binding.ivTuVan.setClickable(clickable);//tu van tai cho

								if (!isGiveUp) { //dung choi
												binding.ivGiveUp.setClickable(false);
								}

								if (!changeQuestionAble) { //doi cau hoi
												binding.ivChangeQuestion.setClickable(false);
								}

								if (!dofifty_fiftyAble) { //5050
												binding.iv5050.setClickable(false);
								}

								if (!askAudienceAble) { //hoi khan gia
												binding.ivAskAudience.setClickable(false);
								}

								if (!callAble) {
												binding.ivCall.setClickable(false);//goi dien
								}

								if (!isAskable) {
												binding.ivTuVan.setClickable(false); //to tu van
								}
								if (!isSearchable) {
												binding.ivSearch.setClickable(false);//tim kiem
								}

				}

				//tra ve id truecase cho tung cau hoi
				private int getIDTrueCase() {
								return App.getInstance().getStorage().listQ.get(level).getTrueCase();
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
																				changeQuestionAble = false;
																				setClickableButton(true);
																				setBackgroundAns();
																				synchronized (this) {
																								new Thread() {
																												@Override
																												public void run() {
																																questionChange = App.getInstance().getDb().getQuestionDAO().getQuestionAtLevel(getCurrentLevel(), getCurrentID());
																																App.getInstance().getStorage().replaceQuestion(questionChange, level);

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
																				setCountDown(currentTime);
																}
												}
								});
								noticeDialog.show();
				}

				//goi dien thoai cho nguoi than
				private void doCalling() {
								stopCountDown();
								callAble = false;
								binding.ivCall.setImageResource(R.drawable.player_button_image_help_call_active);
								DialogCallForHelp dialogCallForHelp = new DialogCallForHelp(mContext);
								dialogCallForHelp.setCancelable(false);
								dialogCallForHelp.setCanceledOnTouchOutside(false);
								dialogCallForHelp.show();
								Window window = dialogCallForHelp.getWindow();
								window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
								dialogCallForHelp.show();
								dialogCallForHelp.setTrueAnswer(App.getInstance().getStorage().listQ.get(level).getTrueCase());

								App.getInstance().getMediaManager().playGameSound(MediaManager.CALL_WHO, mp -> dialogCallForHelp.setClickable(true));
								dialogCallForHelp.setOnDismissListener(dialog -> {
												setCountDown(currentTime);
												binding.ivCall.setImageResource(R.drawable.ic_help_call_x);
								});
				}

				//hoi khan gia trong truong quay
				private void showPercentage() {
								DialogAudience dialogAudience = new DialogAudience(mContext);

								setClickableButton(false);
								askAudienceAble = false;
								stopCountDown();
								binding.ivAskAudience.setImageResource(R.drawable.player_button_image_help_audience_active);

								//dap an dung
								TextView trueCase;

								trueCase = getAnswerTextView(getIDTrueCase());

								if (isHidden) {
												TextView tv1 = hiddenList.get(0);
												TextView tv2 = hiddenList.get(1);

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
												setClickableButton(true);
								}));
								dialogAudience.setOnDismissListener(dialog -> {
												M003PlayFragment.this.setCountDown(currentTime);
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
								dofifty_fiftyAble = false;
								stopCountDown();
								binding.iv5050.setImageResource(R.drawable.player_button_image_help_5050_active);
								setClickableButton(false);
								App.getInstance().getMediaManager().playGameSound(MediaManager.SOUND_5050, mp -> {
												setClickableButton(true);
												int b = 0;
												int count = 0;
												int trueCaseId;
												trueCaseId = getIDTrueCase();
												while (count <= 1) {
																Random random = new Random();
																int ran = random.nextInt(4) + 1;
																if (ran != trueCaseId && ran != b) {  //id tc =3
																				b = ran;
																				hide1 = getAnswerTextView(b);
																				hiddenList.add(hide1);
																				hide1.setBackgroundResource(R.drawable.ic_answer_background_hide);
																				hide1.setText("");
																				hide1.setClickable(false);
																				count++;
																}

												}
												binding.iv5050.setImageResource(R.drawable.ic_help_5050_off);
												setCountDown(currentTime);
												isHidden = true;
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
																isGiveUp = false;
																setClickableButton(false);
																App.getInstance().getMediaManager().playGameSound(MediaManager.DUNG_TAI_DAY, new MediaPlayer.OnCompletionListener() {
																				@Override
																				public void onCompletion(MediaPlayer mp) {
																								App.getInstance().getMediaManager().playGameSound(MediaManager.NEU_CHOI_TIEP, new MediaPlayer.OnCompletionListener() {
																												@Override
																												public void onCompletion(MediaPlayer mp) {
																																isChampion = false;
																																isAskable = false;
																																askAudienceAble = false;
																																changeQuestionAble = false;
																																dofifty_fiftyAble = false;
																																callAble = false;
																																isSearchable = false;
																																setClickableButton(true);

																												}
																								});

																				}
																});

												} else {
																binding.ivGiveUp.setImageResource(R.drawable.ic_stop_game);
																setCountDown(currentTime);
												}
												noticeDialog.dismiss();
								});
								noticeDialog.show();
				}

				private void setHelpButton(boolean state) {
								binding.ivCall.setClickable(state);
								binding.ivGiveUp.setClickable(state);
								binding.ivChangeQuestion.setClickable(state);
								binding.iv5050.setClickable(state);
								binding.ivAskAudience.setClickable(state);
								binding.ivSearch.setClickable(state);
								binding.listQuestion.setClickable(state);
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
								setCountDown(currentTime);
				}

				public void stopCountDown() {
								if (countDownTimer != null) {
												countDownTimer.cancel();
								}
				}

				//tra ve level hien tai
				private int getCurrentLevel() {
								return level + 1;
				}

				//luu diem
				private void saveScore() {
								stopCountDown();
								DialogHighScore dialogHighScore = new DialogHighScore(mContext);
								if (isChampion) {
												String text = binding.tvMoneyCount.getText().toString();

												doCompareScore(text, dialogHighScore);
								} else if (!isGiveUp) {
												App.getInstance().getMediaManager().playGameSound(MediaManager.RAT_TIEC_CHIA_TAY, new MediaPlayer.OnCompletionListener() {
																@Override
																public void onCompletion(MediaPlayer mp) {

																				if (level > 0) {
																								String text = binding.tvMoneyCount.getText().toString();

																								doCompareScore(text, dialogHighScore);

																				} else {
																								sayGoodBye();
																				}

																}
												});

								} else {

												App.getInstance().getMediaManager().playGameSound(MediaManager.RAT_TIEC_CHIA_TAY, new MediaPlayer.OnCompletionListener() {
																@Override
																public void onCompletion(MediaPlayer mp) {
																				if (level == 14 || level == 4 || level == 9) {
																								level--;
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
												if (isGiveUp) {

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
				protected Class<CommonVM> getClassViewModel() {
								return CommonVM.class;
				}

				@Override
				protected M003PlayFragmentBinding initViewBinding(LayoutInflater inflater) {
								return M003PlayFragmentBinding.inflate(inflater);
				}
}

