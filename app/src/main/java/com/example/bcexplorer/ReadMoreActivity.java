package com.example.bcexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bcexplorer.databinding.ActivityReadMoreBinding;
import com.example.bcexplorer.utils.Utils;

import java.util.Objects;

public class ReadMoreActivity extends AppCompatActivity {
    private ActivityReadMoreBinding b;

    String LIST_TYPE = Constants.VANCOUVER;
    String GETTING_AROUND_LIST_TYPE = Constants.GETTING_AROUND_VANCOUVER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityReadMoreBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LIST_TYPE = getIntent().getStringExtra(Constants.PARAMS);

        if (LIST_TYPE.equals(Constants.WHITE_ROCK)) {
            Objects.requireNonNull(getSupportActionBar())
                    .setTitle("Getting Around White Rock");
            b.mapImage.setImageResource(R.drawable.vancouver);
            b.airportTxt.setText("WR AIRPORT TEXT HERE");
            b.taxisTxt.setText("WR TAXIS TEXT HERE");
            b.bikesTxt.setText("WR BIKES TEXT HERE");
            b.walkTxt.setText("WR WALK TEXT HERE");

            GETTING_AROUND_LIST_TYPE = Constants.GETTING_AROUND_WHITE_ROCK;
        }

        if (LIST_TYPE.equals(Constants.VANCOUVER)) {
            Objects.requireNonNull(getSupportActionBar())
                    .setTitle("Getting Around DT Vancouver");

            b.mapImage.setImageResource(R.drawable.vancouver);
            b.airportTxt.setText("VAN AIRPORT TEXT HERE");
            b.taxisTxt.setText("VAN TAXIS TEXT HERE");
            b.bikesTxt.setText("VAN BIKES TEXT HERE");
            b.walkTxt.setText("VAN WALK TEXT HERE");

            GETTING_AROUND_LIST_TYPE = Constants.GETTING_AROUND_VANCOUVER;
        }

        if (LIST_TYPE.equals(Constants.WHISTLER)) {
            Objects.requireNonNull(getSupportActionBar())
                    .setTitle("Getting Around Whistler");
            b.mapImage.setImageResource(R.drawable.vancouver);
            b.airportTxt.setText("WH AIRPORT TEXT HERE");
            b.taxisTxt.setText("WH TAXIS TEXT HERE");
            b.bikesTxt.setText("WH BIKES TEXT HERE");
            b.walkTxt.setText("WH WALK TEXT HERE");


            GETTING_AROUND_LIST_TYPE = Constants.GETTING_AROUND_WHISTLER;
        }


    }

    boolean isSaved = false;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location_view, menu);
        MenuItem item = menu.findItem(R.id.location_save);

        if(Utils.getBoolean(this, GETTING_AROUND_LIST_TYPE))
        {
            item.setIcon(R.drawable.ic_location_unsave);
        }
        else
        {
            item.setIcon(R.drawable.ic_location_save);
        }

        item.setVisible(true);
        return true;
    }

    // Method that handles actionBar items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) { // Back button
            finish();
            overridePendingTransition(R.anim.enter_rev, R.anim.exit_rev);
        }
        else if(id == R.id.location_save)
        {
            // If save icon is visible (location is not saved)
            if (item.getIcon().getConstantState() == getResources().getDrawable(R.drawable.ic_location_save).getConstantState()) {
                item.setIcon(R.drawable.ic_location_unsave); // Change icon to unsave

                // Set saved status in database
                Utils.store(this, GETTING_AROUND_LIST_TYPE, true);
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

            }
            // If unsave icon is visible (location is already saved)
            else if (item.getIcon().getConstantState() == getResources().getDrawable(R.drawable.ic_location_unsave).getConstantState()) {
                item.setIcon(R.drawable.ic_location_save); // Change icon to save

                // Set unsaved status in database

                Utils.store(this, GETTING_AROUND_LIST_TYPE, false);
                Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
            }
        }



        return super.onOptionsItemSelected(item);
    }
}

