package com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData;

import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

/**
 * Created by jms10 on 2017-11-23.
 */

public class NetWordDictionary {
    public static final int GET_OK = 0;
    public static final int GET_FAIL = 1;
//    public static final String DICT_SRC_URI =
//           "https://dictionary.cambridge.org/ko/%EC%82%AC%EC%A0%84/%EC%98%81%EC%96%B4-%ED%95%9C%EA%B5%AD%EC%96%B4/";
    public static final String DICT_SRC_URI = // 사전 단어 쿼리 주소
            "https://dictionary.cambridge.org/ko/%EA%B2%80%EC%83%89/english-korean/direct/?q=";
    private String targetWord;
    private String targetUri;
    private NetWordBundle result;

    //  파싱되어 얻은 뜻을, 이용하기 편리하게 정규화 합니다.
    public String makeRegularString(String str) {
        String res = str.trim();
        // (~~~) 삭제 ||| -에서 -에 ... 삭제
        res = res.replaceAll("\\([^\\(\\)]*\\)|.*\\-[^ ]*", "").trim();
        return res;
    }

    // TargetWord Setter
    public void setTargetWord(String targetWord) {
        setTargetWord(targetWord, DICT_SRC_URI);
    }

    public void setTargetWord(String targetWord, String targetUri) {
        this.targetWord = targetWord.toLowerCase();
        this.targetUri = targetUri;
    }


    /// 단어 관련 메소드들, Thread 이용
    public void startFindWordOnNet() {
        result = new NetWordBundle(targetWord);
        getWordThread.start();
    }

    @Nullable
    public NetWordBundle getResult() {
        try {
            getWordThread.join();
        } catch (InterruptedException e) {
            Log.e("DEBUGINGDD", e.getMessage());
        }

        return result;
    }

    private Thread getWordThread = new Thread() {
        public void run() {
            try {
                HashSet<String> meaningSet = new HashSet<>();
                Document document = Jsoup.connect(targetUri + targetWord)
                        .timeout(3000)
                        .method(Connection.Method.GET)
                        .userAgent("Pixi")
                        .get();
                Elements elements = document.select(".entry-body__el"); // 큰 단어 묶기

                if (elements.isEmpty()) {
                    return;
                }

                NetWordData tmpData;
                for (Element element : elements) {
                    Elements posEles = element.select(".pos-block");
                    for (Element posBaseEle : posEles) {
                        Elements posTextEle = posBaseEle.select(".pos");
                        tmpData = new NetWordData(targetWord,
                                NetWordData.POS_STRMAP.get(posTextEle.text()));

                        // 관용구가 아닌 뜻만 가져온다.
                        Elements meaningsEles = posBaseEle.select(".sense-body > .def-block > span > .trans");

                        for (Element meaningEle : meaningsEles) {
                            String tmpStr = makeRegularString(meaningEle.text());
                            if (!meaningSet.contains(tmpStr)) {
                                tmpData.pushMeanings(tmpStr);
                                meaningSet.add(tmpStr);
                            }
                        }

                        result.pushWordData(tmpData);
                    }
                }

            } catch (IOException e) {
                Log.e("DEBUGINGDD", e.getMessage());
            }
        }
    };

    /// Singletone 구조 관련 구현
    private static NetWordDictionary instance;

    public static NetWordDictionary getInstance() {
        if (instance == null)
            instance = new NetWordDictionary();
        return instance;
    }

    private NetWordDictionary() { }
}
