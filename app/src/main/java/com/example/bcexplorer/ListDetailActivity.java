package com.example.bcexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bcexplorer.databinding.ActivityListDetailBinding;

public class ListDetailActivity extends AppCompatActivity {

    private ActivityListDetailBinding b;

    String LIST_TYPE = Constants.VANCOUVER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityListDetailBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        LIST_TYPE = getIntent().getStringExtra(Constants.PARAMS);

        if (LIST_TYPE.equals(Constants.VANCOUVER)) {
            b.topText.setText("Downtown Vancouver");
            b.imageview.setImageResource(R.drawable.vancouver);
            b.gettingAroundText.setText("Getting Around\nDT Vancouver");
            b.whatsNewText.setText("What's New in\nDT Vancouver");

        }

        if (LIST_TYPE.equals(Constants.WHITE_ROCK)) {
            b.topText.setText("White Rock");
            b.imageview.setImageResource(R.drawable.white_rock);
            b.gettingAroundText.setText("Getting Around\nWhite Rock");
            b.whatsNewText.setText("What's New in\nWhite Rock");
        }

        if (LIST_TYPE.equals(Constants.WHISTLER)) {
            b.topText.setText("Whistler");
            b.imageview.setImageResource(R.drawable.whistler);
            b.gettingAroundText.setText("Getting Around\nWhistler");
            b.whatsNewText.setText("What's New in\nWhistler");
        }
    }
}