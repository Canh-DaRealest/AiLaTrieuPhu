package com.canhmai.ailatrieuphu.viewmodel;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModel;

import com.canhmai.ailatrieuphu.App;
import com.canhmai.ailatrieuphu.R;
import com.canhmai.ailatrieuphu.db.entities.Question;
import com.canhmai.ailatrieuphu.dialog.DialogAudience;
import com.canhmai.ailatrieuphu.dialog.DialogCallForHelp;
import com.canhmai.ailatrieuphu.dialog.DialogHighScore;
import com.canhmai.ailatrieuphu.dialog.DialogSearch;
import com.canhmai.ailatrieuphu.dialog.NoticeDialog;
import com.canhmai.ailatrieuphu.model.GameLogic;
import com.canhmai.ailatrieuphu.model.MediaManager;
import com.canhmai.ailatrieuphu.view.act.MainActivity;
import com.canhmai.ailatrieuphu.view.fragment.M001StartFragment;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GamePlayVM extends ViewModel {

				private GameLogic gameLogic ;

				public long getCurrentTime() {
								return gameLogic.getCurrentTime();
				}

				public void updateListQuestions() {
								new Thread() {
												@Override
												public void run() {
																App.getInstance().getStorage().listHighScore = App.getInstance().getDb().getHighScoreDAO().getAll();
																gameLogic.setListQuestion(App.getInstance().getDb().getQuestionDAO().getAll());

												}
								}.start();
				}




				public void setAskAbleState(boolean state) {
								gameLogic.setAdviceable(state);
				}







				//tra ve id truecase cho tung cau hoi
				public int getIDTrueCase() {

								return gameLogic.getListQuestion().get(gameLogic.getLevel()).getTrueCase();
				}






				public int getCurrentLevel() {
								return gameLogic.getLevel();
				}






				public boolean getButtonState() {
								return gameLogic.getbuttonClickState();
				}

				public boolean getBooleanGiveUpState() {
								return gameLogic.isGiveUp();
				}

				public boolean getBooleanChangeQuestionButton() {
								return gameLogic.isChangeQuestionAble();
				}

				public boolean getBoolean50_50Button() {
								return gameLogic.isDofifty_fiftyAble();
				}

				public boolean getBooleanAudienceButton() {
								return gameLogic.isAskAudienceAble();
				}

				public boolean getBooleanCallButton() {
								return gameLogic.isCallAble();
				}

				public boolean getBooleanAdviceButton() {
								return gameLogic.isAdviceable();
				}

				public boolean getBooleanSearchButton() {
								return gameLogic.isSearchable();
				}


				public Question getQuestionAtCurrentLevel() {
								return gameLogic.getQuestionAtThisLv();
				}

				public void setTrueCase(int trueCase) {
								gameLogic.setTrueCase(trueCase);
				}

				public int getProgress() {
								return gameLogic.getProgress();
				}

				public Question getQuestionChange() {
								return gameLogic.getQuestionChange();
				}

				public int getCurrentId() {
								return gameLogic.getCurrentID();
				}

				public void replaceCurrentQuestion(Question questionChange, int level) {
								gameLogic.updateNewQuestion(questionChange, level);

				}
				public List<TextView> getHiddenList() {
								return gameLogic.getHiddenList();
				}


				public void setBooleanCallState(boolean b) {
								gameLogic.setCallAble(b);
				}

				public List<Question> getListQuestion() {
								return gameLogic.getListQuestion();
				}

				public void setBooleanAudienceState(boolean b) {
								gameLogic.setAskAudienceAble(b);
				}

				public boolean isHidden() {
								return gameLogic.isHidden();
				}


				public void setBoolean50_50State(boolean b) {
								gameLogic.setDofifty_fiftyAble(b);
				}

				public void updateHidenList(TextView hide1) {
								gameLogic.getHiddenList().add(hide1);
				}

				public void setBooleanGiveUp(boolean b) {
								gameLogic.setGiveUp(b);
				}
				public void setProgress(int varialble) {
								gameLogic.setProgress(varialble);
				}

				public void setCurrentTime(long variable) {
								gameLogic.setCurrentTime(variable);
				}

				public void setBooleanHiden(boolean state) {
								gameLogic.setHidden(state);
				}

				public void setAllowBacked(boolean state) {
								App.getInstance().setAllowbacked(state);
				}

				public void setQuestionAtCurrentLevel(Question question) {
								gameLogic.setQuestionAtThisLv(question);
				}

				public void setButtonState(boolean state) {
								gameLogic.setStateButton(state);
				}

				public void setBooleanChangeQuestion(boolean state) {
								gameLogic.setChangeQuestionAble(state);
				}

				public void setQuestionChange(Question question) {
								gameLogic.setQuestionChange(question);
				}

				public void setBooleanChampionState(boolean b) {
								gameLogic.setChampion(b);
				}

				public void setBooleanAdvice(boolean b) {
								gameLogic.setAdviceable(b);
				}

				public void setBooleanSearchState(boolean b) {
								gameLogic.setSearchable(b);
				}

				public boolean getBooleanChampionState() {
								return gameLogic.isChampion();
				}

				public void setCurrentLevel(int level) {
								gameLogic.setLevel(level);
				}

				public void setViewClicked(TextView v) {
								gameLogic.setViewClicked(v);
				}

				public View getViewClicked() {
						return 		gameLogic.getViewClicked();
				}

				public boolean checkAnswer(int idAns) {
							return 	gameLogic.checkAnswer(idAns);
				}

				public void setupGameLogic() {

								gameLogic = new GameLogic();
								setCurrentLevel(0);
				}
}




