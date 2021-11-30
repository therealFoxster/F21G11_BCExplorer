package com.example.bcexplorer.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.bcexplorer.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    ArrayList<ListItemModel> arrayList;
    Context context;

    public CustomAdapter(Context context, ArrayList<ListItemModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItemModel listItemModel = arrayList.get(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row //, viewGroup, false);
//            convertView = layoutInflater.inflate(R.layout.view_saved_item // Setup required for view_saved_item
                    , null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "Clicked at position: " + position, Toast.LENGTH_SHORT).show();
                }
            });
            TextView tittle = convertView.findViewById(R.id.title);
            TextView desc = convertView.findViewById(R.id.desc);
            ImageView imag = convertView.findViewById(R.id.list_image);

            // Setups required for view_saved_item
//            TextView tittle = convertView.findViewById(R.id.textViewSavedItemTitle);
//            TextView desc = convertView.findViewById(R.id.textViewSavedItemSubTitle);
//            ImageView imag = convertView.findViewById(R.id.imageViewSavedItem);

            tittle.setText(listItemModel.itemTitle);
            desc.setText(listItemModel.itemDesc);
            imag.setImageResource(listItemModel.image);
        }

        // Setups required for view_saved_item
//        float height = 90 * context.getResources().getDisplayMetrics().density;
//        convertView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) height));
//        float horizontal = 12 * context.getResources().getDisplayMetrics().density;
//        float vertical = 6 * context.getResources().getDisplayMetrics().density;
//        convertView.setPadding((int) horizontal, (int) vertical, (int) horizontal, (int) vertical);
//
//        CardView cardViewContainer = convertView.findViewById(R.id.cardViewSavedItem);
//        float cornerRadius = 12 * context.getResources().getDisplayMetrics().density;
//        cardViewContainer.setRadius((int) cornerRadius);

        return convertView;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

}
