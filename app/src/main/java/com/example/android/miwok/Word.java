package com.example.android.miwok;

/**
 * Created by Koushick on 05-08-2016.
 */

public class Word {

    String englishWord;
    String miwokWord;
    int imageResID;
    int wordType;

    public int getSong() {
        return song;
    }

    public void setSong(int song) {
        this.song = song;
    }

    int song;

    public int getWordType() {
        return wordType;
    }

    public void setWordType(int wordType) {
        this.wordType = wordType;
    }

    public Word(String english, String miwok,int wordTyp,int songName) {

        englishWord = english;
        miwokWord = miwok;
        wordType=wordTyp;
        song=songName;
    }

    public Word(String english,String miwok,int imgid,int wordTyp,int songName) {

        englishWord = english;
        miwokWord = miwok;
        imageResID=imgid;
        wordType=wordTyp;
        song=songName;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getMiwokWord() {
        return miwokWord;
    }

    public void setMiwokWord(String miwokWord) {
        this.miwokWord = miwokWord;
    }

    public int getImageResourceId()
    {
        return imageResID;
    }
}
