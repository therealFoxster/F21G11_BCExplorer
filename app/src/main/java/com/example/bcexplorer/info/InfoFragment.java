package com.example.bcexplorer.info;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bcexplorer.BuildConfig;
import com.example.bcexplorer.R;

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

        String appName = getString(getContext().getApplicationInfo().labelRes);
        String appVersion = BuildConfig.VERSION_NAME;

        ListView listViewInfoItems = view.findViewById(R.id.listViewInfoItems);
        TextView textViewAppVersion = view.findViewById(R.id.textViewAppVersion);
        textViewAppVersion.setText(String.format("%s v%s", appName, appVersion));

        // Adapter
        InfoItemAdapter infoItemAdapter = new InfoItemAdapter();

        // Source code infoItem
        infoItemAdapter.addInfoItem(new InfoItem(R.drawable.ic_github, R.string.source_code), (View view2) -> {
            // Open GitHub page
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.repo_url))));
        });

        // Contact us infoItem
        infoItemAdapter.addInfoItem(new InfoItem(R.drawable.ic_mail, R.string.contact_us), (View view2) -> {
            // Alert stating the feature might not work
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("This feature might not work in an emulator");
            builder.setPositiveButton("Continue", (DialogInterface dialogInterface, int i) -> {
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
            builder.setNegativeButton("Cancel", (DialogInterface dialogInterface, int i) -> {
                dialogInterface.cancel();
            });

            AlertDialog alert = builder.create();
            alert.show();
        });

        // Credits infoItem
        infoItemAdapter.addInfoItem(new InfoItem(R.drawable.ic_info, R.string.credits), (View view2) -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in, R.animator.nav_default_exit_anim, R.animator.nav_default_pop_enter_anim, R.anim.slide_out).
                    replace(((ViewGroup) getView().getParent()).getId(), new CreditsFragment(), "CREDITS_FRAGMENT").addToBackStack("info").commit();
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

        setHasOptionsMenu(true);
    }

    // Hiding save button
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

}