package com.canhmai.ailatrieuphu.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.canhmai.ailatrieuphu.db.entities.Question;

import java.util.List;

@Dao
public interface QuestionDAO {


    @Query("SELECT* FROM (SELECT * FROM Question WHERE level =1 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =2 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =3 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =4 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =5 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =6 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =7 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =8 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =9 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =10 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =11 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =12 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =13 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =14 ORDER by random() LIMIT 1)\n" +
            "UNION SELECT * FROM (SELECT * FROM Question WHERE level =15 ORDER by random() LIMIT 1)\n" +
            "ORDER by level ASC")

    List<Question> getAll();

//lay cau hoi theo level
    @Query("SELECT * FROM Question WHERE level = :lv AND _id != :id ORDER by random() LIMIT 1")
    Question getQuestionAtLevel(int lv, int id);

}
