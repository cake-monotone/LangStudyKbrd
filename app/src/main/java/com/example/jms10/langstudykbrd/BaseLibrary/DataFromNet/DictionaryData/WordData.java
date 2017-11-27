package com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData;

/**
 * Created by jms10 on 2017-11-23.
 */

public class WordData {
    public final static int NOUN = 1;
    public final static int VERB = 2;
    public final static int A = 3;
    public final static int ADV = 4;
    public final static int CONJ = 5;

    private String wordString;
    private String[] meanings;
    private int partOfSpeech;

    public WordData(String wordString, int partOfSpeech, String... meanings) {
        this.wordString = wordString;
        this.meanings = meanings;
        this.partOfSpeech = partOfSpeech;
    }

    public String getWordsString() {
        return this.wordString;
    }

    public String[] getMeanings() { return this.meanings; }

    public int getPartOfSpeech() {
        return this.partOfSpeech;
    }

    @Override
    public String toString() {
        return this.wordString;
    }
}
