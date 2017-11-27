package com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData;

import java.util.Dictionary;
import java.util.Enumeration;

/**
 * Created by jms10 on 2017-11-24.
 */

public class WordBundle {
    private Dictionary<Integer, WordData> wordDataDictionary;
    private String wordString;

    public WordBundle(WordData... words) {
        wordString = words[0].getWordsString();

        for (int i = 0; i < words.length; i ++) {
            wordDataDictionary.put(words[i].getPartOfSpeech(), words[i]);

            if (!words[i].getWordsString().equals(wordString)) {
                throw new RuntimeException("WordBundle에 잘못된 단어가 입력되었습니다.");
            }
        }
    }

    public WordData findWordByParts(int partOfSpeech) {
        try {
            return wordDataDictionary.get(partOfSpeech);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Enumeration<WordData> getWordDatas() {
        return wordDataDictionary.elements();
    }
    
    public String getWordString() {
        return this.wordString;
    }
}
