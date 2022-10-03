package com.canhmai.ailatrieuphu.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.canhmai.ailatrieuphu.db.dao.HighScoreDAO;
import com.canhmai.ailatrieuphu.db.dao.QuestionDAO;
import com.canhmai.ailatrieuphu.db.entities.HighScore;
import com.canhmai.ailatrieuphu.db.entities.Question;

@Database(entities = {Question.class, HighScore.class}, version = 2)
public abstract class AppDB extends RoomDatabase {
    public abstract QuestionDAO getQuestionDAO();

    public abstract HighScoreDAO getHighScoreDAO();
}
