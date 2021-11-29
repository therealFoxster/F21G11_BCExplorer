package com.example.bcexplorer;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bcexplorer.databinding.FragmentSavedBinding;
import com.example.bcexplorer.utils.CustomAdapter;
import com.example.bcexplorer.utils.ListItemModel;
import com.example.bcexplorer.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SavedFragment extends Fragment {

    public SavedFragment() {
        // Required empty public constructor
    }

    private FragmentSavedBinding b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentSavedBinding.inflate(inflater, container, false);

        initAdapter();

        return b.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        initAdapter();
    }

    private void initAdapter() {
        ArrayList<ListItemModel> arrayList = new ArrayList<ListItemModel>();

        Context context = requireContext();

        // WHISTLER
        if (Utils.getBoolean(context, Constants.WHISTLER))
            arrayList.add(new ListItemModel("Whistler",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    R.drawable.whistler));

        // VANCOUVER
        if (Utils.getBoolean(context, Constants.VANCOUVER))
            arrayList.add(new ListItemModel("Downtown Vancouver",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    R.drawable.vancouver));

        // WHITE ROCK
        if (Utils.getBoolean(context, Constants.WHITE_ROCK))
            arrayList.add(new ListItemModel("White Rock",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    R.drawable.white_rock));

        // GETTING_AROUND_WHISTLER
        if (Utils.getBoolean(context, Constants.GETTING_AROUND_WHISTLER))
            arrayList.add(new ListItemModel("Getting Around Whistler",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    R.drawable.map));

        // GETTING_AROUND_VANCOUVER
        if (Utils.getBoolean(context, Constants.GETTING_AROUND_VANCOUVER))
            arrayList.add(new ListItemModel("Getting Around DT Vancouver",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    R.drawable.map));

        // GETTING_AROUND_WHITE ROCK
        if (Utils.getBoolean(context, Constants.GETTING_AROUND_WHITE_ROCK))
            arrayList.add(new ListItemModel("Getting Around White Rock",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    R.drawable.map));

        // WHATS_NEW_WHISTLER
        if (Utils.getBoolean(context, Constants.WHATS_NEW_WHISTLER))
            arrayList.add(new ListItemModel("Like Darts But More Canadian",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    R.drawable.whats_new_whistler));

        // WHATS_NEW_VANCOUVER
        if (Utils.getBoolean(context, Constants.WHATS_NEW_VANCOUVER))
            arrayList.add(new ListItemModel("Vancouver is upping the steaks",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    R.drawable.whats_new_vancouver));

        // WHATS_NEW_WHITE ROCK
        if (Utils.getBoolean(context, Constants.WHATS_NEW_WHITE_ROCK))
            arrayList.add(new ListItemModel("The Pond at BFS",
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                    R.drawable.whats_new_white_rock));

        Collections.sort(arrayList, new Comparator<ListItemModel>() {
            @Override
            public int compare(ListItemModel listItemModel, ListItemModel t1) {
                return listItemModel.getItemTitle().compareTo(t1.getItemTitle());
            }
        });

        CustomAdapter customAdapter = new CustomAdapter(requireContext(), arrayList);
        b.list.setAdapter(customAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    // Hiding save button
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }
}
