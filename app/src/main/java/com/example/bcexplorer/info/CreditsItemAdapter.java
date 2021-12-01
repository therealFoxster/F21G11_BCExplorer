package com.example.bcexplorer.info;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bcexplorer.R;

import java.util.ArrayList;
import java.util.List;

public class CreditsItemAdapter extends BaseAdapter {
    List<String> textList;
    List<View.OnClickListener> onClickListenerList;

    public CreditsItemAdapter() {
        textList = new ArrayList<>();
        onClickListenerList = new ArrayList<>();
    }

    public void addCreditsItem(String itemName) {
        textList.add(itemName);
        onClickListenerList.add(null);
    }

    public void addCreditsItem(String itemName, View.OnClickListener onClickListener) {
        textList.add(itemName);
        onClickListenerList.add(onClickListener);
    }

    public CreditsItemAdapter(List<String> textList) {
        this.textList = textList;
        onClickListenerList = null;
    }

    @Override
    public int getCount() {
        return textList.size();
    }

    @Override
    public Object getItem(int position) {
        return textList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_credits_item, parent, false);

        TextView textView = convertView.findViewById(R.id.textViewCreditsItem);
        textView.setText(textList.get(position));

        if (onClickListenerList != null && onClickListenerList.get(position) != null)
            convertView.setOnClickListener(onClickListenerList.get(position));

        return convertView;
    }
}
