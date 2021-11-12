package com.example.bcexplorer.info;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bcexplorer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_info, container, false);

        ListView listViewInfoItems = view.findViewById(R.id.listViewInfoItems);

        // Adapter
        InfoItemAdapter infoItemAdapter = new InfoItemAdapter();
        infoItemAdapter.addInfoItem(new InfoItem(R.drawable.github_icon, R.string.source_code), (View view2) -> {
            // Open GitHub page
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.repo_url))));
        });
        infoItemAdapter.addInfoItem(new InfoItem(R.drawable.mail_icon, R.string.contact_us), (View view2) -> {
            // Send an email; doesn't work in an emulator (tested on Nexus 6 API 30)
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");

            // Extras
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Recipient"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Content");

            startActivity(emailIntent);
        });
        infoItemAdapter.addInfoItem(new InfoItem(R.drawable.info_icon, R.string.credits), (View view2) -> {
            Toast.makeText(view2.getContext(), "Credits view to be implemented", Toast.LENGTH_SHORT).show();

            // TODO: Implement credits activity/fragment
        });
        listViewInfoItems.setAdapter(infoItemAdapter);

        return view;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

}