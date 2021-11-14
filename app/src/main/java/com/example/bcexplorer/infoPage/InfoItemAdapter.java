package com.example.bcexplorer.infoPage;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bcexplorer.R;

import java.util.ArrayList;
import java.util.List;

public class InfoItemAdapter extends BaseAdapter {
    List<InfoItem> infoItemList;
    List<View.OnClickListener> onClickListenerList;

    public InfoItemAdapter() {
        this.infoItemList = new ArrayList<>();
        this.onClickListenerList = new ArrayList<>();
    }

    public void addInfoItem(InfoItem infoItem, View.OnClickListener onClickListener) {
        infoItemList.add(infoItem);
        onClickListenerList.add(onClickListener);
    }

    public void addInfoItem(InfoItem infoItem) {
        addInfoItem(infoItem, null);
    }

    @Override
    public int getCount() {
        return infoItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return infoItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.info_item, viewGroup, false);

        ImageView imageViewInfoItem = view.findViewById(R.id.imageViewInfoItem);
        TextView textViewInfoItem = view.findViewById(R.id.textViewInfoItem);

        imageViewInfoItem.setColorFilter(Color.rgb(28,31,35));
        imageViewInfoItem.setImageResource(infoItemList.get(i).getIconImageResource());
        textViewInfoItem.setText(infoItemList.get(i).getTextResource());

        if (onClickListenerList.get(i) != null)
            view.setOnClickListener(onClickListenerList.get(i));

        return view;
    }
}
