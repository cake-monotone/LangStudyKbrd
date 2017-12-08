package com.example.jms10.langstudykbrd;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ScrollingTabContainerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jms10.langstudykbrd.BaseLibrary.DataFromSQL.DictionaryData.SQLWordDictionary;

import java.util.ArrayList;
import java.util.List;

public class DictionaryActivity extends AppCompatActivity {
    private EditText searchEditText;
    private ListView resWordListView;
    private TextView resMeaningTextView;
    private ArrayAdapter<String> adapter;

    // 오버헤드를 줄이기 위한 사전 변수
    private SQLWordDictionary dictionary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        resWordListView = (ListView) findViewById(R.id.dict_resWordListView);
        resMeaningTextView = (TextView) findViewById(R.id.dict_resMeaningTextView);
        searchEditText = (EditText) findViewById(R.id.dict_searchEditText);

        // 사전의 세팅, 액티비티가 만들어져 있는 동안은 미리 사전을 열어둡니다.
        dictionary = SQLWordDictionary.getInstance(getApplicationContext());
        dictionary.open();

        // TextView를 스크롤이 가능하게 합니다.
        resMeaningTextView.setMovementMethod(ScrollingMovementMethod.getInstance());

        // ListView 어댑터 세팅
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        resWordListView.setAdapter(adapter);

        // ListView 아이템들에 ClickListener
        resWordListView.setOnItemClickListener(new resWordClickedListener());

        // serchEditText에 리스너 SearchTextWatcher 등록
        SearchTextWatcher watcher = new SearchTextWatcher();
        searchEditText.addTextChangedListener(watcher);

        // 액티비티 시작시에도 ListView가 비어있지 않게 해줍니다.
        watcher.afterTextChanged(searchEditText.getText());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dictionary.close();
    }

    /**
     * EditText의 텍스트가 바뀔 때마다, SQL 쿼리를 날려 단어 리스트를 업데이트 하는
     * TextWatcher를 만듭니다.
     */
    class SearchTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

        @Override
        public void afterTextChanged(Editable editable) {
            adapter.clear();

            // SQL 사전에서 EditText를 포함하는 단어를 찾아와 리스트로 만들어줍니다.
            List<String> list = dictionary.getWordList(editable.toString());

            adapter.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    // resMeaningTextView 에 클릭한 단어의 뜻을 표시해주는 ClickListener 입니다.
    class resWordClickedListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            String meaning = dictionary.getWordMeaning(
                    ((AppCompatTextView)view).getText().toString()
            );

            if (meaning == null)
                return;

            // html 태그로 구성되어 있는 뜻 String을 TextView에 적절히 넣어 줍니다.
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
                resMeaningTextView.setText(Html.fromHtml(meaning));
            else
                resMeaningTextView.setText(Html.fromHtml(meaning, Html.FROM_HTML_MODE_COMPACT).toString());
        }
    }
}
