package com.example.android.miwok;

/**
 * Created by Koushick on 05-08-2016.
 */

public class Word {

    String englishWord;
    String miwokWord;
    int imageResID;
    int wordType;

    public int getWordType() {
        return wordType;
    }

    public void setWordType(int wordType) {
        this.wordType = wordType;
    }

    public Word(String english, String miwok,int wordTyp) {

        englishWord = english;
        miwokWord = miwok;
        wordType=wordTyp;
    }

    public Word(String english,String miwok,int imgid,int wordTyp) {

        englishWord = english;
        miwokWord = miwok;
        imageResID=imgid;
        wordType=wordTyp;
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
