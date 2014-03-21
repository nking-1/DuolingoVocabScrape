package edu.vanderbilt.cs292.nbk;
public class WordData {

    private final String mWord, mLang, mPos;

    private final double mStr;

    public WordData(String word, String language, String partOfSpeech, double strength) {
        mWord = word;
        mLang = language;
        mPos = partOfSpeech;
        mStr = strength;
    }

    public String getWord() {
        return mWord;
    }

    public String getLanguage() {
        return mLang;
    }

    public String getPartOfSpeech() {
        return mPos;
    }

    public double getStrength() {
        return mStr;
    }

    @Override
    public String toString() {
        return "{word: " + mWord + ", lang: " + mLang + ", pos: " + mPos + ", strength: " + mStr
                + "}";
    }
}
