package com.example.jms10.langstudykbrd.CustomListView.WordSelectListView;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jms10.langstudykbrd.R;

import java.util.List;

/**
 * Created by jms10 on 2017-12-06.
 */

public class WordSelectAdapter extends BaseAdapter{
    private List<WordSelectItem> mItemList;
    private LayoutInflater mInflater;

    public WordSelectAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.layout_word_select_item, parent, false);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_word_img);
        TextView textView = (TextView) convertView.findViewById(R.id.list_item_word_text);

        WordSelectItem item = mItemList.get(pos);

        imageView.setImageBitmap(item.getWordBitmap());
        textView.setText(item.getWordString());

        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return mItemList.get(i);
    }

}
