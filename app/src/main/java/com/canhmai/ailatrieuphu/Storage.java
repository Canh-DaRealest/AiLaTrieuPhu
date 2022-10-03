package com.canhmai.ailatrieuphu;

import com.canhmai.ailatrieuphu.db.entities.HighScore;
import com.canhmai.ailatrieuphu.db.entities.Question;
import com.canhmai.ailatrieuphu.model.Person;

import java.util.List;

public class Storage {
    public List<Question> listQ;
    public List<HighScore> listHighScore;
    public List<Person> personList;
    public Person personTrue;

    public List<Question> getListQ() {
        return listQ;
    }

    public void setListQuestion(List<Question> listQuestion) {
        listQ = listQuestion;
    }

    //doi cau hoi
    public void replaceQuestion(Question qs, int level) {
        listQ.set(listQ.indexOf(listQ.get(level)), qs);
    }


}
