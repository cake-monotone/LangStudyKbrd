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

/**
 * Created by jms10 on 2017-11-23.
 */

public class NetWordDictionary {
    public static final int GET_OK = 0;
    public static final int GET_FAIL = 1;
    public static final String DICT_SRC_URI =
            "https://dictionary.cambridge.org/ko/%EC%82%AC%EC%A0%84/%EC%98%81%EC%96%B4-%ED%95%9C%EA%B5%AD%EC%96%B4/";
    private String targetWord;
    private String targetUri;
    private NetWordBundle result;
    private Thread thread = new Thread() {
        public void run() {
            try {
                Log.d("DEBUGINGDD", "제이수프 시작");
                Document document = Jsoup.connect(targetUri + targetWord).get();
                        //.userAgent("Mozilla").cookie("auth","token").post();
                Log.d("DEBUGINGDD", "다큐멘트 얻어오기 완료");
                Elements elements = document.select(".entry-body__el"); // 큰 단어 묶기
                Log.d("DEBUGINGDD", "entry 까지 얻어오기 성공");

                if (elements.isEmpty()) {
                    return;
                }

                Log.d("DEBUGINGDD", "파싱 시작");
                NetWordData tmpData;
                for (Element element : elements) {
                    Elements posEles = element.select(".pos-block");
                    Log.d("DEBUGINGDD", "품사 단위 블락 셀랙트");
                    for (Element posBaseEle : posEles) {
                        Elements posTextEle = posBaseEle.select(".pos");
                        Elements meaningsEles = posBaseEle.select(".trans");
                        tmpData = new NetWordData(targetWord,
                                NetWordData.POS_STRMAP.get(posTextEle.text()));

                        for (Element meaningEle : meaningsEles) {
                            tmpData.pushMeanings(meaningEle.text().trim());
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

    public void setTargetWord(String targetWord) {
        setTargetWord(targetWord, DICT_SRC_URI);
    }

    public void setTargetWord(String targetWord, String targetUri) {
        this.targetWord = targetWord.toLowerCase();
        this.targetUri = targetUri;
    }

    public void startFindWordOnNet() {
        Log.d("DEBUGINGDD", "찾기 시작");
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
