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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bcexplorer.MainActivity;
import com.example.bcexplorer.R;
import com.example.bcexplorer.database.Location;
import com.example.bcexplorer.global.LocationFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SavedItemRecyclerViewAdapter extends RecyclerView.Adapter<SavedItemRecyclerViewAdapter.SavedItemViewHolder> {
    List<Location> locationList;
    Context context;
    ViewGroup parent;

    public SavedItemRecyclerViewAdapter(Context context) {
        locationList = new ArrayList<>();
        this.context = context;
    }

    public SavedItemRecyclerViewAdapter(Context context, ViewGroup viewGroup) {
        locationList = new ArrayList<>();
        this.context = context;
        parent = viewGroup;
    }

    // Overloaded constructor
    public SavedItemRecyclerViewAdapter(List<Location> locationList, Context context, ViewGroup viewGroup) {
        this.locationList = locationList;
        this.context = context;
        parent = viewGroup;
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

        CardView cardViewContainer = holder.savedItemView.findViewById(R.id.cardViewSavedItem);

        // Card's click listener
        cardViewContainer.setOnClickListener((View view1) -> {
            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            LocationFragment locationFragment = new LocationFragment();

            Bundle bundle = new Bundle();
            bundle.putString("LOCATION_ID", locationList.get(position).getLocationID());
            locationFragment.setArguments(bundle);

            fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.animator.nav_default_exit_anim, R.animator.nav_default_pop_enter_anim, R.anim.slide_out).
                    replace(parent.getId(), locationFragment, "SAVED_LOCATION_FRAGMENT").addToBackStack("saved").commit();
        });

        // Save button's click listener
        imageViewSaveIcon.setOnClickListener((View view1) -> {
            Toast.makeText(holder.savedItemView.getContext(), "Save clicked", Toast.LENGTH_SHORT).show();

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                // Unsave location
                MainActivity.database.locationDAO().unsaveLocation(locationList.get(position).getLocationID());
            });

            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MainActivity.refreshSavedPage();
                }
            });

        });
    }

    @Override
    public int getItemCount() {
        if (locationList != null)
            return locationList.size();
        else return 0;
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
