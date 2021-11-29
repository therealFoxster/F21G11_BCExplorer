package com.example.bcexplorer.global;

import android.content.res.Resources;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.bcexplorer.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerLocationImagesAdapter extends PagerAdapter {
    List<String> imageNames;

    public ViewPagerLocationImagesAdapter() {
        imageNames = new ArrayList<>();
    }

    public ViewPagerLocationImagesAdapter(List<String> imageNames) {
        this.imageNames = imageNames;
    }

    public void addImageName(String imageName) {
        imageNames.add(imageName);
    }

    @Override
    public int getCount() {
        return imageNames.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
        imageNames.set(position, null);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Inflate layout
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.view_location_image, container, false);
        container.addView(view);

        ImageView imageViewLocation = view.findViewById(R.id.imageViewLocation);
        Resources resources = container.getResources();

        if (imageNames.get(position) != null)
            imageViewLocation.setImageResource(resources.getIdentifier(imageNames.get(position), "drawable", container.getContext().getPackageName()));

        return view;
    }
}
