package com.example.bcexplorer.info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bcexplorer.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreditsItemDetailsActivity extends AppCompatActivity {

    private static String locationName = "";
    private List<Serializable> details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits_item_details);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        details = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            locationName = bundle.getString("NAME");

            Serializable overviewSource = bundle.getSerializable("OVERVIEW");
            if (overviewSource != null)
                details.add(overviewSource);

            Serializable image1Source = bundle.getSerializable("IMAGE1");
            if (image1Source != null)
                details.add(image1Source);

            Serializable image2Source = bundle.getSerializable("IMAGE2");
            if (image2Source != null)
                details.add(image2Source);

            Serializable image3Source = bundle.getSerializable("IMAGE3");
            if (image3Source != null)
                details.add(image3Source);
        }

        ListView listViewLocationSources = findViewById(R.id.listViewLocationSources);
        CreditsItemDetailAdapter adapter = new CreditsItemDetailAdapter();
        for (Serializable detail : details) {
            adapter.addItemDetail((CreditsItemDetail) detail);
        }
        listViewLocationSources.setAdapter(adapter);

        TextView textViewSources = findViewById(R.id.textViewLocationSources);
        textViewSources.setText("Materials used for " + locationName);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                overridePendingTransition(R.anim.enter_rev, R.anim.exit_rev);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}