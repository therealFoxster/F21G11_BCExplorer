package com.example.bcexplorer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bcexplorer.databinding.ActivityListDetailBinding;
import com.example.bcexplorer.utils.Utils;

import java.util.Objects;

public class ListDetailActivity extends AppCompatActivity {


    private ActivityListDetailBinding b;

    String LIST_TYPE = Constants.VANCOUVER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityListDetailBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());



        LIST_TYPE = getIntent().getStringExtra(Constants.PARAMS);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (LIST_TYPE.equals(Constants.VANCOUVER)) {
            Objects.requireNonNull(getSupportActionBar())
                    .setTitle("City Guide DT Vancouver");
            b.imageview.setImageResource(R.drawable.vancouver);
            b.titleTxt.setText("Downtown Vancouver");
            b.gettingAroundText.setText("Getting Around\nDT Vancouver");
            b.whatsNewText.setText("What's New in\nDT Vancouver");
            b.descTxt.setText("Downtown is known for a buzzing nightlife scene on neon-lit Granville Street, with raucous bars, clubs, and live bands at the art deco Commodore Ballroom. By day, it’s a busy shopping hub of chain and luxury boutiques, plus high-end department stores in the CF Pacific Centre mall.\n" +
                    "\n" +
                    "Food trucks and casual lunch spots dot the area, and cruise liners depart from Canada Place, a terminal designed to look like a ship.");

        }

        if (LIST_TYPE.equals(Constants.WHITE_ROCK)) {
            Objects.requireNonNull(getSupportActionBar())
                    .setTitle("City Guide White Rock");
            b.imageview.setImageResource(R.drawable.white_rock);
            b.titleTxt.setText("White Rock");
            b.gettingAroundText.setText("Getting Around\nWhite Rock");
            b.whatsNewText.setText("What's New in\nWhite Rock");
            b.descTxt.setText("White Rock is a city in British Columbia, Canada, and a member municipality of the Metro Vancouver Regional District.\n" +
                    "\n" +
                    "It borders Semiahmoo Bay to the south and is surrounded on three sides by Surrey. To the southeast across a footbridge lies the Semiahmoo First Nation, which is within the borders of Surrey.");
        }

        if (LIST_TYPE.equals(Constants.WHISTLER)) {
            Objects.requireNonNull(getSupportActionBar())
                    .setTitle("City Guide Whistler");
            b.imageview.setImageResource(R.drawable.whistler);
            b.titleTxt.setText("Whistler");
            b.gettingAroundText.setText("Getting Around\nWhistler");
            b.whatsNewText.setText("What's New in\nWhistler");
            b.descTxt.setText("Whistler is a town north of Vancouver, British Columbia, that's home to Whistler Blackcomb, one of the largest ski resorts in North America. Besides skiing and snowboarding, the area offers snowshoeing, tobogganing and ski jumping at the Olympic Park, a venue for the 2010 Vancouver Winter Olympics.\n" +
                    "\n" +
                    "The hub of Whistler is a compact, chalet-style pedestrian village at the base of Whistler and Blackcomb mountains");
        }



        b.exploreBtn.setOnClickListener(exploreBtnClickListener());
        b.readMoreBtn.setOnClickListener(readMoreBtnClickListener());



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_location_view, menu);
        MenuItem item = menu.findItem(R.id.location_save);

        if(Utils.getBoolean(this, LIST_TYPE))
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
                Utils.store(this, LIST_TYPE, true);
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

            }
            // If unsave icon is visible (location is already saved)
            else if (item.getIcon().getConstantState() == getResources().getDrawable(R.drawable.ic_location_unsave).getConstantState()) {
                item.setIcon(R.drawable.ic_location_save); // Change icon to save

                // Set unsaved status in database

                Utils.store(this, LIST_TYPE, false);
                Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();
            }
        }



        return super.onOptionsItemSelected(item);
    }

    boolean isSaved = false;

    private View.OnClickListener readMoreBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListDetailActivity.this, ExploreActivity.class)
                        .putExtra(Constants.PARAMS, LIST_TYPE));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        };
    }

    private View.OnClickListener exploreBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ListDetailActivity.this, ReadMoreActivity.class)
                        .putExtra(Constants.PARAMS, LIST_TYPE));
                overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        };
    }

}