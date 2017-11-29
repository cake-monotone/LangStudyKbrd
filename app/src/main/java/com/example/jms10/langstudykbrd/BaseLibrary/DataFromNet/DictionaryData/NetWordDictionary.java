package com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.jms10.langstudykbrd.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jms10 on 2017-11-23.
 */

public class NetWordDictionary {
    public static final int GET_OK = 0;
    public static final int GET_FAIL = 1;
//    public static final String DICT_SRC_URI =
//           "https://dictionary.cambridge.org/ko/%EC%82%AC%EC%A0%84/%EC%98%81%EC%96%B4-%ED%95%9C%EA%B5%AD%EC%96%B4/";
    public static final String DICT_SRC_URI =
            "https://dictionary.cambridge.org/ko/%EA%B2%80%EC%83%89/english-korean/direct/?q=";
    private String targetWord;
    private String targetUri;
    private NetWordBundle result;
    private Thread thread = new Thread() {
        public void run() {
            try {
                HashSet<String> meaningSet = new HashSet<>();
                Document document = Jsoup.connect(targetUri + targetWord).get();
                        //.userAgent("Mozilla").cookie("auth","token").post();
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
                return;
            }
        }
    };

    public String makeRegularString(String str) {
        String res = str.trim();
        res = res.replaceAll("\\(.*\\)|.*\\-[^ ]*", "").trim();
        return res;
    }

    public void setTargetWord(String targetWord) {
        setTargetWord(targetWord, DICT_SRC_URI);
    }

    public void setTargetWord(String targetWord, String targetUri) {
        this.targetWord = targetWord.toLowerCase();
        this.targetUri = targetUri;
    }

    public void startFindWordOnNet() {
        result = new NetWordBundle(targetWord);
        thread.start();
    }


    public NetWordBundle getResult() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("DEBUGINGDD", e.getMessage());
        }

        return result;
    }
}
