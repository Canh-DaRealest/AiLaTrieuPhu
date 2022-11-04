package com.canhmai.ailatrieuphu.viewmodel;

import androidx.lifecycle.ViewModel;

import com.canhmai.ailatrieuphu.App;
import com.canhmai.ailatrieuphu.db.entities.HighScore;

import java.util.List;

public class AchivementVM extends ViewModel {

				public void getListHighScore() {
								App.getInstance().getStorage().listHighScore = App.getInstance().getDb().getHighScoreDAO().getAll();
				}
}
