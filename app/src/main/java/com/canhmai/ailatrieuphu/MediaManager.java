package com.canhmai.ailatrieuphu;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Random;

public class MediaManager {
    //key luu data
    public static final String SAVE_SETTING = "SAVE_SETTING_SOUND";
    public static final String SAVE_STATE_MUSIC = "STATE_MUSIC";
    //them id sound vao cac mang
    public static final int[] GAME_RULE = {R.raw.luatchoi_b, R.raw.luatchoi_c};
    public static final int[] VUOT_MOC_3 = {R.raw.best_player, R.raw.best_player2};
    public static final int[] BGCHAMPION = {R.raw.champion};
    public static final int[] BG_MOC1 = {R.raw.moc1};
    public static final int[] BG_MOC2 = {R.raw.moc2};
    public static final int[] BG_MOC3 = {R.raw.moc3};
    public static final int[] IMPORTANT = {R.raw.important};
    public static final int[] VUOT_MOC_2 = {R.raw.chuc_mung_vuot_moc_02_0};
    public static final int[] BG_SOUND = {R.raw.bg_sound2};

    public static final int[] NHAC_MO_DAU = {R.raw.batdauvaogame};

    public static final int[] READY = {R.raw.ready, R.raw.ready_b, R.raw.ready_c};
    public static final int[] GO_FIND = {R.raw.gofind, R.raw.gofind_b};
    public static final int[] SOUND_5050 = {R.raw.sound5050, R.raw.sound5050_2};
    public static final int[] KHAN_GIA = {R.raw.khan_gia};
    public static final int[] TO_TU_VAN = {R.raw.sound_trogiup_tuvan};

    public static final int[] TRO_GIUP_1 = {R.raw.trogiup_1_1, R.raw.trogiup_2_1, R.raw.trogiup_3_1, R.raw.trogiup_4_1};
    public static final int[] TRO_GIUP_2 = {R.raw.trogiup_1_2, R.raw.trogiup_2_2, R.raw.trogiup_3_2, R.raw.trogiup_4_2};
    public static final int[] TRO_GIUP_3 = {R.raw.trogiup_1_3, R.raw.trogiup_2_3, R.raw.trogiup_3_3, R.raw.trogiup_4_3};
    public static final int[] TRO_GIUP_4 = {R.raw.trogiup_1_4, R.raw.trogiup_2_4, R.raw.trogiup_3_4, R.raw.trogiup_4_4};

    public static final int[] RAT_TIEC_CHIA_TAY = {R.raw.sound_chiatay};
    public static final int[] PING = {R.raw.ping};
    public static final int[] DUNG_TAI_DAY = {R.raw.stop_now};
    public static final int[] NEU_CHOI_TIEP = {R.raw.if_continues};
    public static final int[] HOI_DUNG_CHOI = {R.raw.tro_giup_dung_choi_new};
    public static final int[] CHUYEN_GIA = {R.raw.hoi_y_kien_chuyen_gia_01b, R.raw.khan_gia_2};
    public static final int[] KET_NOI_DIEN_THOAI = {R.raw.conect_call};
    public static final int[] CALL_WHO = {R.raw.help_call};
    public static final int[] OUT_OF_TIME = {R.raw.out_of_time};
    public static final int[] ANS_NOW = {R.raw.ans_now1, R.raw.ans_now2, R.raw.ans_now3, R.raw.ans_now4, R.raw.ans_now5};
    public static final int[] VUOT_MOC_1 = {R.raw.vuot_moc_1_p3};
    public static final int[] CHOOSE_A = {R.raw.ans_a, R.raw.ans_a2};
    public static final int[] CHOOSE_B = {R.raw.ans_b, R.raw.ans_b2};
    public static final int[] CHOOSE_C = {R.raw.ans_c, R.raw.ans_c2};
    public static final int[] CHOOSE_D = {R.raw.ans_d, R.raw.ans_d2};
    public static final int[] CAU_15_DUNG = {R.raw.pass_15};
    public static final int[] TRUE_A = {R.raw.true_a, R.raw.true_a2, R.raw.eff_dung};
    public static final int[] TRUE_A_NEU_CHOI_TIEP = {R.raw.is_give_true_1_2};
    public static final int[] TRUE_B = {R.raw.true_b, R.raw.true_b2, R.raw.true_b3, R.raw.eff_dung};
    public static final int[] TRUE_B_NEU_CHOI_TIEP = {R.raw.is_give_true_2_2};
    public static final int[] TRUE_C = {R.raw.true_c, R.raw.true_c2, R.raw.true_c3, R.raw.eff_dung};
    public static final int[] TRUE_C_NEU_CHOI_TIEP = {R.raw.is_give__true_3_2};
    public static final int[] TRUE_D = {R.raw.true_d2, R.raw.true_d3, R.raw.eff_dung};
    public static final int[] TRUE_D_NEU_CHOI_TIEP = {R.raw.is_gv_true_4_2};
    public static final int[] THANKS = {R.raw.lose, R.raw.lose2};
    public static final int[] LOSE_A = {R.raw.lose_a, R.raw.lose_a2};
    public static final int[] LOSE_B = {R.raw.lose_b, R.raw.lose_b2};
    public static final int[] LOSE_C = {R.raw.lose_c, R.raw.lose_c2};
    public static final int[] LOSE_D = {R.raw.lose_d, R.raw.lose_d2, R.raw.lose_d3};
    public static final int[] QUEST_1 = {R.raw.ques1, R.raw.ques1_b};
    public static final int[] QUEST_2 = {R.raw.ques2, R.raw.ques2_b};
    public static final int[] QUEST_3 = {R.raw.ques3, R.raw.ques3_b};
    public static final int[] QUEST_4 = {R.raw.ques4, R.raw.ques4_b};
    public static final int[] QUEST_5 = {R.raw.ques5, R.raw.ques5_b};
    public static final int[] QUEST_6 = {R.raw.ques6};
    public static final int[] QUEST_7 = {R.raw.ques7, R.raw.ques7_b};
    public static final int[] QUEST_8 = {R.raw.ques8, R.raw.ques8_b};
    public static final int[] QUEST_9 = {R.raw.ques9, R.raw.ques9_b};
    public static final int[] QUEST_10 = {R.raw.ques10};
    public static final int[] QUEST_11 = {R.raw.ques11};
    public static final int[] QUEST_12 = {R.raw.ques12};
    public static final int[] QUEST_13 = {R.raw.ques13};
    public static final int[] QUEST_14 = {R.raw.ques14};
    public static final int[] QUEST_15 = {R.raw.ques15, R.raw.start_cau15};
    private static final String SAVE_STATE_SOUND = "STATE_SOUND";
    private static MediaManager instance;
    private MediaPlayer playerBG;
    private MediaPlayer playerGame;
    private boolean isPauseGame;
    private boolean isPauseBG;
    //trang thai cua music va sound ( true: bat, false: tat)
    private SharedPreferences sharedPreferences;
    private ArrayList<int[]> soundLevelList;
    private boolean isBGOn = true;
    private boolean isGameOn = true;

    public MediaManager(Context context) {
        getSettingData();
        addSoundLevel();
    }

    //them cac sound cau hoi vao 1 list
    private void addSoundLevel() {

        soundLevelList = new ArrayList<>();

        soundLevelList.add(QUEST_1);
        soundLevelList.add(QUEST_2);
        soundLevelList.add(QUEST_3);
        soundLevelList.add(QUEST_4);
        soundLevelList.add(QUEST_5);
        soundLevelList.add(QUEST_6);
        soundLevelList.add(QUEST_7);
        soundLevelList.add(QUEST_8);
        soundLevelList.add(QUEST_9);
        soundLevelList.add(QUEST_10);
        soundLevelList.add(QUEST_11);
        soundLevelList.add(QUEST_12);
        soundLevelList.add(QUEST_13);
        soundLevelList.add(QUEST_14);
        soundLevelList.add(QUEST_15);
    }

    public boolean getBGOn() {
        return isBGOn;
    }

    public void setBGOn(boolean BGOn) {
        isBGOn = BGOn;
    }

    public boolean getGameOn() {
        return isGameOn;
    }

    public void setGameOn(boolean gameOn) {
        isGameOn = gameOn;
    }

    public int[] getSoundLevel(int lv) {
        return soundLevelList.get(lv);
    }


    public void playBG(int[] idSong) {
        int len = idSong.length;
        Random random = new Random();

        if (playerBG != null) {
            playerBG.reset();
        }
        playerBG = MediaPlayer.create(App.getInstance(), idSong[random.nextInt(len)]);

        playerBG.setLooping(true);
        if (!isBGOn) {
            playerBG.setVolume(0, 0);
        } else {
            playerBG.setVolume(1, 1);
        }
        playerBG.start();
    }

    public void playBG2(int[] idSong, MediaPlayer.OnCompletionListener event) {
        int len = idSong.length;
        Random random = new Random();

        if (playerBG != null) {
            playerBG.reset();
        }
        playerBG = MediaPlayer.create(App.getInstance(), idSong[random.nextInt(len)]);
        playerBG.setOnCompletionListener(event);

        if (!isBGOn) {
            playerBG.setVolume(0, 0);
        } else {
            playerBG.setVolume(1, 1);
        }
        playerBG.start();
    }

    public void playGameSound(int[] idSound, MediaPlayer.OnCompletionListener event) {
        int len = idSound.length;
        if (playerGame != null) {
            playerGame.reset();
        }
        Random random = new Random();

        playerGame = MediaPlayer.create(App.getInstance(), idSound[random.nextInt(len)]);

        playerGame.setOnCompletionListener(event);

        if (!isGameOn) {
            playerGame.setVolume(0, 0);
        } else {
            playerGame.setVolume(1, 1);
        }
        playerGame.start();
    }

    public void pauseSong() {
        if (playerBG != null && playerBG.isPlaying()) {
            playerBG.pause();
            isPauseBG = true;
        }
        if (playerGame != null && playerGame.isPlaying()) {
            playerGame.pause();
            isPauseGame = true;
        }
    }

    //setting

    //lay du lieu setting  trong app/data
    public void getSettingData() {
        //dung sharepreference de lay du lieu trong file data
        sharedPreferences = App.getInstance().getSharedPreferences(SAVE_SETTING, Context.MODE_PRIVATE);
        //lay tung du lieu trong SAVE_SETTING
        boolean musicSett = sharedPreferences.getBoolean(SAVE_STATE_MUSIC, true);
        boolean soundSett = sharedPreferences.getBoolean(SAVE_STATE_SOUND, true);

        //trang thai am luong cua game
        setGameOn(soundSett);
        setBGOn(musicSett);
    }

    //luu du lieu setting  vao  app/data
    //thuc hien phương thuc tai onbackpress
    public void doSaveSetting(boolean musicState, boolean soundState) {
        isBGOn = musicState;
        isGameOn = soundState;
        sharedPreferences = App.getInstance().getSharedPreferences(SAVE_SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putBoolean(SAVE_STATE_SOUND, soundState);
        editor.putBoolean(SAVE_STATE_MUSIC, musicState);
        editor.apply();

    }

    public void resumeSong() {
        if (playerBG != null && isPauseBG) {
            isPauseBG = false;
            playerBG.start();
        }

        if (playerGame != null && isPauseGame) {
            isPauseGame = false;
            playerGame.start();

        }
    }

    public void stopBGSound() {

        if (playerBG != null) {
            playerBG.reset();
        }
    }

    public void stopGameSound() {
        if (playerGame != null) {
            playerGame.reset();
        }
    }

    public int[] getSoundLoseCase(int trueCase) {
        int[] kq = null;
        if (trueCase == 1) {
            kq = LOSE_A;
        } else if (trueCase == 2) {
            kq = LOSE_B;
        }
        if (trueCase == 3) {
            kq = LOSE_C;
        }
        if (trueCase == 4) {
            kq = LOSE_D;
        }
        return kq;
    }


}

