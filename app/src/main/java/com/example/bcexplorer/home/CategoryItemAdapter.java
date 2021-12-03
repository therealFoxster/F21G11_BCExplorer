package com.example.bcexplorer.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bcexplorer.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemAdapter extends BaseAdapter {
    List<String> categoryList;

    public CategoryItemAdapter() {
        categoryList = new ArrayList<>();
    }

    public CategoryItemAdapter(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_home_category_item, parent, false);

        TextView textViewCategoryName = convertView.findViewById(R.id.textViewHomeCategory);
        textViewCategoryName.setText(categoryList.get(position));

        ImageView imageViewCategoryIcon = convertView.findViewById(R.id.imageViewCategoryIcon);
        String iconName = String.format("ic_baseline_%s_24", categoryList.get(position).toLowerCase().replaceAll(" ", "_"));
        imageViewCategoryIcon.setImageResource(parent.getContext().getResources().getIdentifier(iconName, "drawable",parent.getContext().getPackageName()));;

        return convertView;
    }
}
