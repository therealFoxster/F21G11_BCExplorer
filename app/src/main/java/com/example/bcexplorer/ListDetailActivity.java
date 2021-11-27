package com.example.bcexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        b.exploreBtn.setOnClickListener(exploreBtnClickListener());
        b.readMoreBtn.setOnClickListener(readMoreBtnClickListener());

    }

    private View.OnClickListener readMoreBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListDetailActivity.this, ReadMoreActivity.class)
                        .putExtra(Constants.PARAMS, LIST_TYPE));

            }
        };
    }

    private View.OnClickListener exploreBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ListDetailActivity.this, ExploreActivity.class)
                        .putExtra(Constants.PARAMS, LIST_TYPE));

            }
        };
    }
}