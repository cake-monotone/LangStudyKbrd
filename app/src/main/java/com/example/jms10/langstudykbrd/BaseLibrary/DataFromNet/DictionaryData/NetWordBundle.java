package com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jms10 on 2017-11-24.
 */

public class NetWordBundle {
    private Map<Integer, NetWordData> wordDataMap;
    private String wordString;

    public NetWordBundle(String wordString) {
        this.wordString = wordString.toLowerCase();
        wordDataMap = new HashMap<>();
    }

    public NetWordBundle(NetWordData... words) {
        wordString = words[0].getWordsString().toLowerCase();
        wordDataMap = new HashMap<>();

        for (int i = 0; i < words.length; i ++) {
            wordDataMap.put(words[i].getPartOfSpeech(), words[i]);

            if (!words[i].getWordsString().equals(wordString)) {
                throw new RuntimeException("WordBundle에 잘못된 단어가 입력되었습니다.");
            }
        }
    }

    public void pushWordData(NetWordData word) {
        if (!word.getWordsString().equals(wordString)) {
            throw new RuntimeException("이 번들의 wordString과 WordData의 wordString이 다릅니다.");
        }

        try {
            NetWordData data = wordDataMap.get(word.getPartOfSpeech());
            data.pushMeanings(word.getMeanings());
        } catch (NullPointerException e) {
            wordDataMap.put(word.getPartOfSpeech(), word);
        }
    }

    public NetWordData findWordByParts(int partOfSpeech) {
        try {
            return wordDataMap.get(partOfSpeech);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Collection<NetWordData> getWordDatas() {
        return wordDataMap.values();
    }
    
    public String getWordString() {
        return this.wordString;
    }
}
