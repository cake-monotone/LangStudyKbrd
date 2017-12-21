package com.example.jms10.langstudykbrd;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData.NetWordBundle;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData.NetWordData;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.DictionaryData.NetWordDictionary;
import com.example.jms10.langstudykbrd.BaseLibrary.DataFromNet.ImageData.NetImageGetter;
import com.example.jms10.langstudykbrd.CustomListView.WordSelectListView.WordSelectAdapter;
import com.example.jms10.langstudykbrd.CustomListView.WordSelectListView.WordSelectItem;

public class WordSelectActivity extends AppCompatActivity {
    public static final String EXTRA_WORD_STRING_KEY = "WORD";
    public static final int GET_STRING_REULST_REQUEST = 1;

    private ProgressBar progressBar;
    private ListView listView;

    private String wordString;

    private WordSelectAdapter adapter;
    private NetWordDictionary dictionary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_select);

        // 단어 정보가 담긴 Intent 가져오기
        Intent intent = getIntent();
        wordString = intent.getExtras().getString(EXTRA_WORD_STRING_KEY);

        dictionary = NetWordDictionary.getInstance();
        dictionary.setTargetWord(wordString);
        dictionary.startFindWordOnNet();

        progressBar = (ProgressBar) findViewById(R.id.word_select_progressBar);
        listView = (ListView) findViewById(R.id. word_select_listView);

        adapter = new WordSelectAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l_pos) {
                WordSelectItem item = (WordSelectItem) adapterView.getAdapter().getItem(pos);

                if (item.getWordPOS() == -1) {
                    Intent intent = new Intent(getApplicationContext(), DirectInputActivity.class);
                    startActivityForResult(intent, GET_STRING_REULST_REQUEST);
                }
                else if (item.getWordPOS() == -2) { }
                else{
                    Intent intent = new Intent(getApplicationContext(), SelectTransformActivity.class);
                    intent.putExtra("WORD", item.getWordString());
                    intent.putExtra("POS", item.getWordPOS());
                    startActivityForResult(intent, GET_STRING_REULST_REQUEST);
                }
            }
        });

        genListTask task = new genListTask();
        task.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_STRING_REULST_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, data.getExtras().getString("RESULT"), Toast.LENGTH_SHORT).show();
                //setResult(RESULT_OK, data);
                String word = data.getStringExtra("RESULT");
                SharedPreferenceUtil sp = new SharedPreferenceUtil(this);
                sp.setConvertedWord(word);
                finish();
            }
        }
    }

    private class genListTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            NetWordBundle netWordBundle = dictionary.getResult();

            for (NetWordData data : netWordBundle.getWordDatas()) {
                for (String meaning : data.getMeanings()) {
                    WordSelectItem item = new WordSelectItem(meaning, null, data.getPartOfSpeech());
                    GetBitmapThread thread = new GetBitmapThread(item);
                    thread.run();
                    adapter.addItem(item);
                }
            }

            if (netWordBundle.getWordDatas().size() == 0) {
                adapter.addItem(new WordSelectItem("단어를 찾지 못했습니다.",null, -2));
            }

            adapter.addItem(new WordSelectItem("직접 입력", null, -1));
            publishProgress();

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            progressBar.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            adapter.notifyDataSetChanged();
            super.onPostExecute(aVoid);
        }

        private class GetBitmapThread extends Thread {
            private WordSelectItem item;
            public GetBitmapThread(WordSelectItem item) {
                this.item = item;
            }
            @Override
            public void run() {
                NetImageGetter netImageGetter = new NetImageGetter(item.getWordString());
                netImageGetter.startGetting();
                item.setWordBitmap(netImageGetter.getResult());
            }
        }
    }

}
