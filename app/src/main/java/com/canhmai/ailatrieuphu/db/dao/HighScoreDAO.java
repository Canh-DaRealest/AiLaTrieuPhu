package com.canhmai.ailatrieuphu.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.canhmai.ailatrieuphu.db.entities.HighScore;

import java.util.List;

@Dao
public interface HighScoreDAO {


    @Query("SELECT * FROM HIGHSCORE")
    List<HighScore> getAll();

    @Insert
    void insert(HighScore  highScores);


}
