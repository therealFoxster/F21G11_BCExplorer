package com.example.bcexplorer.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.bcexplorer.global.MainActivity;
import com.example.bcexplorer.R;
import com.example.bcexplorer.database.Location;
import com.example.bcexplorer.global.LocationActivity;
import com.example.bcexplorer.global.LocationFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocationListItemRecyclerViewAdapter extends  RecyclerView.Adapter<LocationListItemRecyclerViewAdapter.LocationListItemViewHolder> {
    List<Location> locationList;
    ViewGroup parent;
    Context context;

    public LocationListItemRecyclerViewAdapter(ViewGroup viewGroup, Context context) {
        locationList = new ArrayList<>();
        parent = viewGroup;
        this.context = context;
    }

    public void addLocation(Location location) {
        locationList.add(location);
    }

    public LocationListItemRecyclerViewAdapter(List<Location> locationList, ViewGroup parent, Context context) {
        this.locationList = locationList;
        this.parent = parent;
        this.context = context;
    }

    @NonNull
    @Override
    public LocationListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_saved_item, parent, false);
        return new LocationListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationListItemViewHolder holder, int position) {
        Location location = locationList.get(position);
        TextView textViewItemTitle, textViewItemSubTitle;
        ImageView imageViewItem, saveIcon;
        CardView containerView;

        textViewItemTitle =  holder.locationListItemView.findViewById(R.id.textViewSavedItemTitle);
        textViewItemSubTitle = holder.locationListItemView.findViewById(R.id.textViewSavedItemSubTitle);
        imageViewItem = holder.locationListItemView.findViewById(R.id.imageViewSavedItem);
        saveIcon = holder.locationListItemView.findViewById(R.id.imageViewSaveIcon);
        containerView = holder.locationListItemView.findViewById(R.id.cardViewSavedItem);

        textViewItemTitle.setText(location.getLocationName());
        textViewItemSubTitle.setText(location.getOverviewHeader());
        imageViewItem.setImageResource(holder.locationListItemView.getResources().getIdentifier(location.getImage1Name(), "drawable", holder.locationListItemView.getContext().getPackageName()));

        if (location.isSaved()) {
            saveIcon.setImageResource(R.drawable.ic_baseline_bookmark_selected_24);
        } else {
            saveIcon.setImageResource(R.drawable.ic_baseline_bookmark_unselected_24);
        }

        containerView.setOnClickListener((View v) -> {
            FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
            LocationFragment locationFragment = new LocationFragment();

            Bundle bundle = new Bundle();
            bundle.putString("LOCATION_ID", location.getLocationID());
            locationFragment.setArguments(bundle);

            Intent locationActivity = new Intent(context, LocationActivity.class);
            locationActivity.putExtras(bundle);

            ((AppCompatActivity) context).startActivity(locationActivity);
            ((AppCompatActivity) context).overridePendingTransition(R.anim.enter, R.anim.none);

//            fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.animator.nav_default_exit_anim, R.animator.nav_default_pop_enter_anim, R.anim.slide_out).
//                    replace(parent.getId(), locationFragment, "LOCATION_LIST_LOCATION_FRAGMENT").addToBackStack("location_list").commit();
        });

        saveIcon.setOnClickListener((View v) -> {
            if (location.isSaved()) {
                // Change icon to unselected
                saveIcon.setImageResource(R.drawable.ic_baseline_bookmark_unselected_24);

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    // Unsave location
                    MainActivity.database.locationDAO().unsaveLocation(location.getLocationID());
                });

                Toast.makeText(holder.locationListItemView.getContext(), String.format("Removed %s from saved list", location.getLocationName()), Toast.LENGTH_LONG).show();
            } else { // Location is not saved
                // Change icon to selected
                saveIcon.setImageResource(R.drawable.ic_baseline_bookmark_selected_24);

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(() -> {
                    // Save location
                    MainActivity.database.locationDAO().saveLocation(location.getLocationID());
                    MainActivity.database.locationDAO().setSavedTime(location.getLocationID(), (int) (System.currentTimeMillis()/1000)); // Saved time
                });

                Toast.makeText(holder.locationListItemView.getContext(), String.format("Added %s to saved list", location.getLocationName()), Toast.LENGTH_LONG).show();
            }

            MainActivity.refreshLocationListPage();
        });

    }

    @Override
    public int getItemCount() {
        if (locationList != null)
            return locationList.size();
        else return 0;
    }

    public class LocationListItemViewHolder extends RecyclerView.ViewHolder {
        View locationListItemView;
        public LocationListItemViewHolder(View view) {
            super(view);
            locationListItemView = view;
        }
    }

}
