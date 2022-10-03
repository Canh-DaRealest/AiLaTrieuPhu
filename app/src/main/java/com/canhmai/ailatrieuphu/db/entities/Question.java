package com.canhmai.ailatrieuphu.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Question {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    public int id;
    public Question(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCaseA() {
        return caseA;
    }

    public String getCaseB() {
        return caseB;
    }

    public String getCaseC() {
        return caseC;
    }

    public String getCaseD() {
        return caseD;
    }

    public void setCaseD(String caseD) {
        this.caseD = caseD;
    }

    public int getTrueCase() {
        return trueCase;
    }

    public void setTrueCase(int trueCase) {
        this.trueCase = trueCase;
    }

    public Question(int id, int level, String question, String caseA, String caseB, String caseC, String caseD, int trueCase) {
        this.id = id;
        this.level = level;
        this.question = question;
        this.caseA = caseA;
        this.caseB = caseB;
        this.caseC = caseC;
        this.caseD = caseD;
        this.trueCase = trueCase;
    }

    @ColumnInfo(name = "level")
    public int level;

    @ColumnInfo(name = "question")
    public String question;

    @ColumnInfo(name = "casea")
    public String caseA;

    @ColumnInfo(name = "caseb")
    public String caseB;

    @ColumnInfo(name = "casec")
    public String caseC;

    @ColumnInfo(name = "cased")
    public String caseD;

    @ColumnInfo(name = "truecase")
    public int trueCase;

    @NonNull
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", level=" + level +
                ", question='" + question + '\'' +
                ", caseA='" + caseA + '\'' +
                ", caseB='" + caseB + '\'' +
                ", caseC='" + caseC + '\'' +
                ", caseD='" + caseD + '\'' +
                ", trueCase=" + trueCase +
                '}';
    }
}

