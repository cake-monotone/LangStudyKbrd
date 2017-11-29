package com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData;

import android.provider.Settings;
import android.support.v4.content.res.TypedArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jms10 on 2017-11-23.
 */

public class NetWordData {
    public final static int NOUN = 1;
    public final static int VERB = 2;
    public final static int A = 3;
    public final static int ADV = 4;
    public final static int CONJ = 5;
    public final static HashMap<String, Integer> POS_STRMAP = new HashMap<String, Integer>() {{
        put("noun", NOUN);
        put("pronoun", NOUN);
        put("adjective", A);
        put("adverb", ADV);
        put("verb", VERB);
        put("modal verb", VERB);
        put("conjunction", CONJ);
    }};

    private String wordString;
    private ArrayList<String> meanings;
    private int partOfSpeech;

    // 생성자
    public NetWordData(String wordString, int partOfSpeech, String... meanings) {
        this.wordString = wordString;
        this.partOfSpeech = partOfSpeech;
        this.meanings = new ArrayList<>(Arrays.asList(meanings));
    }

    // 의미 추가
    public void pushMeanings(String... plusMeanings) {
        for (int i = 0; i < plusMeanings.length; i ++) {
            this.meanings.add(plusMeanings[i]);
        }
    }
    public void pushMeanings(ArrayList<String> plusMeanings) {
        meanings.addAll(plusMeanings);
    }

    // 의미를 맞는 타입으로 정리

    // Getter
    public String getWordsString() {
        return this.wordString;
    }

    public ArrayList<String> getMeanings() { return this.meanings; }

    public int getPartOfSpeech() {
        return this.partOfSpeech;
    }

    @Override
    public String toString() {
        return this.wordString;
    }
}
