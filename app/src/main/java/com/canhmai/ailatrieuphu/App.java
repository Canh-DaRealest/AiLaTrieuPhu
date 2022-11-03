package com.canhmai.ailatrieuphu;

import android.app.Application;

import androidx.room.Room;

import com.canhmai.ailatrieuphu.db.AppDB;
import com.canhmai.ailatrieuphu.model.MediaManager;

public class App extends Application {
    private static App instance;
    private Storage storage;
    private AppDB db;
    private MediaManager mediaManager;
    private boolean allowbacked;
    private boolean isOnMusic =true;

    public boolean isOnMusic() {
        return isOnMusic;
    }

    public void setOnMusic(boolean onMusic) {
        isOnMusic = onMusic;
    }

    public static App getInstance() {
        return instance;
    }

    public MediaManager getMediaManager() {
        return mediaManager;
    }

    public AppDB getDb() {
        return db;
    }

    public Storage getStorage() {
        return storage;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        storage = new Storage();
        mediaManager = new MediaManager(this);
        db = Room.databaseBuilder(this, AppDB.class, "Questiondb")
                .createFromAsset("db/Question.sqlite")
                .build();
        allowbacked = true;
    }

    public boolean isAllowbacked() {
        return allowbacked;
    }

    public void setAllowbacked(boolean allowbacked) {
        this.allowbacked = allowbacked;
    }
}
