package com.example.bcexplorer.saved;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcexplorer.R;
import com.example.bcexplorer.utils.ListItemModel;

import java.util.ArrayList;
import java.util.List;

public class SavedItemRecyclerViewAdapter2 extends RecyclerView.Adapter<SavedItemRecyclerViewAdapter2.SavedItemViewHolder> {
    List<ListItemModel> itemModelList;
    Context context;

    public SavedItemRecyclerViewAdapter2(Context context) {
        itemModelList = new ArrayList<>();
        this.context = context;
    }

    public void addItemModel(ListItemModel itemModel) {
        itemModelList.add(itemModel);
    }

    public SavedItemRecyclerViewAdapter2(List<ListItemModel> itemModelList, Context context) {
        this.itemModelList = itemModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public SavedItemRecyclerViewAdapter2.SavedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_saved_item, parent, false);

        return new SavedItemRecyclerViewAdapter2.SavedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedItemRecyclerViewAdapter2.SavedItemViewHolder holder, int position) {
        TextView textViewSavedItemTitle, textViewSavedItemSubTitle;
        ImageView imageViewSavedItem, imageViewSaveIcon;

        textViewSavedItemTitle = holder.savedItemView.findViewById(R.id.textViewSavedItemTitle);
        textViewSavedItemSubTitle = holder.savedItemView.findViewById(R.id.textViewSavedItemSubTitle);
        imageViewSavedItem = holder.savedItemView.findViewById(R.id.imageViewSavedItem);
        imageViewSaveIcon = holder.savedItemView.findViewById(R.id.imageViewSaveIcon);

        textViewSavedItemTitle.setText(itemModelList.get(position).getItemTitle());
        textViewSavedItemSubTitle.setText(itemModelList.get(position).getItemDesc());
        imageViewSavedItem.setImageResource(itemModelList.get(position).getImage());

        CardView cardViewContainer = holder.savedItemView.findViewById(R.id.cardViewSavedItem);

        // Card's click listener
        cardViewContainer.setOnClickListener((View view1) -> {

        });

        // Save button's click listener
        imageViewSaveIcon.setOnClickListener((View view1) -> {
            Toast.makeText(holder.savedItemView.getContext(), "Save clicked", Toast.LENGTH_SHORT).show();
        });


    }

    @Override
    public int getItemCount() {
        if (itemModelList != null)
            return itemModelList.size();
        else return 0;
    }

    public class SavedItemViewHolder extends RecyclerView.ViewHolder {
        View savedItemView;
        public SavedItemViewHolder(View view) {
            super(view);
            savedItemView = view;
        }
    }

}
