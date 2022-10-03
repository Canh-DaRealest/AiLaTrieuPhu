package com.canhmai.ailatrieuphu.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "HighScore")
public class HighScore {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    public int id;

    @ColumnInfo(name = "Name")
    @NonNull
    public String name;

    @ColumnInfo(name = "Score")
    public int scoreUser;

    public HighScore(@NonNull String name, int scoreUser) {
        this.name = name;
        this.scoreUser = scoreUser;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getScoreUser() {
        return scoreUser;
    }

}

