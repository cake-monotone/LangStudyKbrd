package com.example.jms10.langstudykbrd.BaseLibrary.DataFromSQL.PushNotiData;

import java.util.Date;

/**
 * Created by jms10 on 2017-12-09.
 */

/**
 * Push 알림 데이터가 담길 클래스입니다.
 * SQLPushNotiHelper 가 반환합니다. 받아쓰세요
 */
public class PushNotiData {
    String word;
    String meaning;
    Date date;

    public PushNotiData(String word, String meaning, Date date) {
        this.word = word;
        this.meaning = meaning;
        this.date = date;
    }

    // Gatter

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

    public Date getDate() {
        return date;
    }

    // Setter

    public void setWord(String word) {
        this.word = word;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

