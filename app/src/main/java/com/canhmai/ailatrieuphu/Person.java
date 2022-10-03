package com.canhmai.ailatrieuphu;

public class Person {

 private    String ansText;
 private    int[] ansSound;

    public String getAnsText() {
        return ansText;
    }

    public void setAnsText(String ansText) {
        this.ansText = ansText;
    }

    public int[] getAnsSound() {
        return ansSound;
    }

    public void setAnsSound(int[] ansSound) {
        this.ansSound = ansSound;
    }

    public Person(String ansText, int[] ansSound) {
        this.ansText = ansText;
        this.ansSound = ansSound;
    }
    public Person() {
    }
}
