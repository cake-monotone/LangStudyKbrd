package com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet;

import android.net.Uri;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by jms10 on 2017-11-23.
 */

public class Parser {
    Parser(Uri r) {
        try {
            Document document = Jsoup.connect(r.toString()).get();

        } catch (IOException e) {
        }
    }
}
