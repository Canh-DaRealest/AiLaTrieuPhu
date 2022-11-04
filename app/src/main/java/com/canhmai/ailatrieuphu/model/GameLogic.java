package com.canhmai.ailatrieuphu.model;

import android.view.View;
import android.widget.TextView;

import com.canhmai.ailatrieuphu.App;
import com.canhmai.ailatrieuphu.db.entities.Question;

import java.util.ArrayList;
import java.util.List;

public class GameLogic {


				Question questionAtThisLv;
				Question questionChange;
				TextView hide1;  // dap an bi an
				List<TextView> hiddenList = new ArrayList<>();  // list chua dap an bi an
				private boolean isGiveUp = true;
				private boolean isShowed = false;
				private boolean isChampion = false;
				private boolean isSearchable = true;
				private boolean isAdviceable = true;
				private int progress;
				private View viewClicked;   // view da duoc click chua
				private int level = -1;  //  xac dinh level cau hoi
				private boolean isHidden;

				private int trueCase;    //dap an dung
				private long currentTime = 31000;
				// xu ly clickable cho tung button
				private boolean changeQuestionAble = true;
				private boolean askAudienceAble = true;
				private boolean callAble = true;
				private boolean dofifty_fiftyAble = true;
				private List<Question> listQuestion = new ArrayList<>();
				private boolean buttonState ;


				public long getCurrentTime() {
								return currentTime;
				}


				public GameLogic() {
				}

				public List<Question> getListQuestion() {
								return listQuestion;
				}

				public Question getQuestionAtThisLv() {
								return questionAtThisLv;
				}

				public void setQuestionAtThisLv(Question questionAtThisLv) {
								this.questionAtThisLv = questionAtThisLv;
				}

				public Question getQuestionChange() {
								return questionChange;
				}

				public void setQuestionChange(Question questionChange) {
								this.questionChange = questionChange;
				}

				public TextView getHide1() {
								return hide1;
				}

				public void setHide1(TextView hide1) {
								this.hide1 = hide1;
				}

				public List<TextView> getHiddenList() {
								return hiddenList;
				}

				public void setHiddenList(List<TextView> hiddenList) {
								this.hiddenList = hiddenList;
				}

				public boolean isGiveUp() {
								return isGiveUp;
				}

				public void setGiveUp(boolean giveUp) {
								isGiveUp = giveUp;
				}

				public boolean isShowed() {
								return isShowed;
				}

				public void setShowed(boolean showed) {
								isShowed = showed;
				}

				public boolean isChampion() {
								return isChampion;
				}

				public void setChampion(boolean champion) {
								isChampion = champion;
				}

				public boolean isSearchable() {
								return isSearchable;
				}

				public void setSearchable(boolean searchable) {
								isSearchable = searchable;
				}

				public boolean isAdviceable() {
								return isAdviceable;
				}

				public void setAdviceable(boolean adviceable) {
								isAdviceable = adviceable;
				}

				public int getProgress() {
								return progress;
				}

				public void setProgress(int progress) {
								this.progress = progress;
				}

				public View getViewClicked() {
								return viewClicked;
				}

				public void setViewClicked(View viewClicked) {
								this.viewClicked = viewClicked;
				}

				public int getLevel() {
								return level;
				}

				public void setLevel(int level) {
								this.level = level;
				}

				public boolean isHidden() {
								return isHidden;
				}

				public void setHidden(boolean hidden) {
								isHidden = hidden;
				}

				public int getTrueCase() {
								return trueCase;
				}

				public void setTrueCase(int trueCase) {
								this.trueCase = trueCase;
				}

				public void setCurrentTime(long currentTime) {
								this.currentTime = currentTime;
				}

				public boolean isChangeQuestionAble() {
								return changeQuestionAble;
				}

				public void setChangeQuestionAble(boolean changeQuestionAble) {
								this.changeQuestionAble = changeQuestionAble;
				}

				public boolean isAskAudienceAble() {
								return askAudienceAble;
				}

				public void setAskAudienceAble(boolean askAudienceAble) {
								this.askAudienceAble = askAudienceAble;
				}

				public boolean isCallAble() {
								return callAble;
				}

				public void setCallAble(boolean callAble) {
								this.callAble = callAble;
				}

				public boolean isDofifty_fiftyAble() {
								return dofifty_fiftyAble;
				}

				public void setDofifty_fiftyAble(boolean dofifty_fiftyAble) {
								this.dofifty_fiftyAble = dofifty_fiftyAble;
				}


				//trả về id cua question hiẹn tai
				public int getCurrentID() {
								Question curQuestion = listQuestion.get(level);

								return curQuestion.getId();
				}


				//kiem tra dap an
				public boolean checkAnswer( int idTrueCas) {
								return idTrueCas == getIDTrueCase();

				}



				//tra ve id truecase cho tung cau hoi
				private int getIDTrueCase() {
								return listQuestion.get(level).getTrueCase();
				}



				//tra ve level hien tai
				private int getCurrentLevel() {
								return level + 1;
				}




				public void setListQuestion(List<Question> listQuestion) {
								this.listQuestion = listQuestion;
				}

				public boolean getStateButton() {

								return buttonState;
				}

				public void setStateButton(boolean state) {
								this.buttonState = state;
				}

				public void updateNewQuestion(Question qs, int level) {
								//doi cau hoi

								listQuestion.set(listQuestion.indexOf(listQuestion.get(level)), qs);

				}
}



