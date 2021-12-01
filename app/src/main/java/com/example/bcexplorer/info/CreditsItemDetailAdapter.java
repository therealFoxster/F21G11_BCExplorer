package com.example.bcexplorer.info;

import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bcexplorer.R;

import java.util.ArrayList;
import java.util.List;

public class CreditsItemDetailAdapter extends BaseAdapter {
    List<CreditsItemDetail> itemDetails;

    public CreditsItemDetailAdapter() {
        itemDetails = new ArrayList<>();
    }

    public void addItemDetail(CreditsItemDetail itemDetail) {
        itemDetails.add(itemDetail);
    }

    public CreditsItemDetailAdapter(List<CreditsItemDetail> itemDetails) {
        this.itemDetails = itemDetails;
    }


    @Override
    public int getCount() {
        return itemDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return itemDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_credit_item_detail, parent, false);

        TextView header = convertView.findViewById(R.id.textViewItemHeader);
        TextView content = convertView.findViewById(R.id.textViewItemContent);

        header.setText(itemDetails.get(position).getTitle());
        content.setText(itemDetails.get(position).getContent());
        Linkify.addLinks(content, Linkify.WEB_URLS);

        return convertView;
    }
}
