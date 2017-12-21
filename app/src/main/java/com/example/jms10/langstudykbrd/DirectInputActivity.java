package com.example.jms10.langstudykbrd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class DirectInputActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_input);

        editText = (EditText) findViewById(R.id.direct_input_editText);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent intent = new Intent();
                    intent.putExtra("RESULT", editText.getText().toString());
                    SharedPreferenceUtil util = new SharedPreferenceUtil(getApplicationContext());
                    util.setConvertedWord(editText.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();

                    return true;
                }
                return false;
            }
        });

    }

}
