package com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData;

/**
 * Created by jms10 on 2017-11-23.
 */

public class WordData {
    public final static int NOUN = 1;
    public final static int VERB = 2;
    public final static int ADVJ = 3;

    private String wordString;
    private String[] meanings;
    private int partOfSpeech;

    WordData(String wordString, int partOfSpeech, String... meanings) {
        this.wordString = wordString;
        this.partOfSpeech = partOfSpeech;
        this.meanings = meanings;
    }

    String getWordsString() {
        return this.wordString;
    }

    int getPartOfSpeech() {
        return this.partOfSpeech;
    }

    @Override
    public String toString() {
        return this.wordString;
    }
}
