package com.example.bcexplorer.saved;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcexplorer.R;
import com.example.bcexplorer.database.Location;
import com.example.bcexplorer.global.LocationFragment;

import java.util.ArrayList;
import java.util.List;

public class SavedItemRecyclerViewAdapter extends RecyclerView.Adapter<SavedItemRecyclerViewAdapter.SavedItemViewHolder> {
    List<Location> locationList;
    Context context;

    public SavedItemRecyclerViewAdapter(Context context) {
        locationList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public SavedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_saved_item, parent, false);

        return new SavedItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedItemViewHolder holder, int position) {
        TextView textViewSavedItemTitle, textViewSavedItemSubTitle;
        ImageView imageViewSavedItem, imageViewSaveIcon;

        textViewSavedItemTitle = holder.savedItemView.findViewById(R.id.textViewSavedItemTitle);
        textViewSavedItemSubTitle = holder.savedItemView.findViewById(R.id.textViewSavedItemSubTitle);
        imageViewSavedItem = holder.savedItemView.findViewById(R.id.imageViewSavedItem);
        imageViewSaveIcon = holder.savedItemView.findViewById(R.id.imageViewSaveIcon);

        textViewSavedItemTitle.setText(locationList.get(position).getLocationName());
        textViewSavedItemSubTitle.setText(locationList.get(position).getOverviewHeader());
        imageViewSavedItem.setImageResource(holder.savedItemView.getResources().getIdentifier(locationList.get(position).getImage1Name(), "drawable", holder.savedItemView.getContext().getPackageName()));

        imageViewSaveIcon.setOnClickListener((View view1) -> {
            Toast.makeText(holder.savedItemView.getContext(), "Save clicked", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    // Overloaded constructor
    public SavedItemRecyclerViewAdapter(List<Location> locationList, Context context) {
        this.locationList = locationList;
        this.context = context;
    }

    public void addLocation(Location location) {
        locationList.add(location);
    }

    public class SavedItemViewHolder extends RecyclerView.ViewHolder {
        View savedItemView;
        public SavedItemViewHolder(View view) {
            super(view);
            savedItemView = view;
        }
    }

}
