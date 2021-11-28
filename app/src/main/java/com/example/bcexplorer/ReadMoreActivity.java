package com.example.bcexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bcexplorer.databinding.ActivityReadMoreBinding;
import com.example.bcexplorer.utils.Utils;

public class ReadMoreActivity extends AppCompatActivity {
    private ActivityReadMoreBinding b;

    String LIST_TYPE = Constants.VANCOUVER;
    String GETTING_AROUND_LIST_TYPE = Constants.GETTING_AROUND_VANCOUVER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityReadMoreBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        LIST_TYPE = getIntent().getStringExtra(Constants.PARAMS);

        if (LIST_TYPE.equals(Constants.VANCOUVER)) {
            b.topText.setText("Getting Around DT Vancouver");

            GETTING_AROUND_LIST_TYPE = Constants.GETTING_AROUND_VANCOUVER;
        }

        if (LIST_TYPE.equals(Constants.WHITE_ROCK)) {
            b.topText.setText("Getting Around White Rock");

            GETTING_AROUND_LIST_TYPE = Constants.GETTING_AROUND_WHITE_ROCK;
        }

        if (LIST_TYPE.equals(Constants.WHISTLER)) {
            b.topText.setText("Getting Around Whistler");

            GETTING_AROUND_LIST_TYPE = Constants.GETTING_AROUND_WHISTLER;
        }

        Context context = ReadMoreActivity.this;

        if (Utils.getBoolean(context, GETTING_AROUND_LIST_TYPE, false)) {
            b.saveBtnExplore.setImageResource(R.drawable.ic_baseline_bookmark_selected_24);
            isSaved = true;
        }
        b.saveBtnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSaved) {
                    b.saveBtnExplore.setImageResource(R.drawable.ic_baseline_bookmark_unselected_24);
                    isSaved = false;

                    Utils.store(context, GETTING_AROUND_LIST_TYPE, false);
                    Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();

                } else {
                    b.saveBtnExplore.setImageResource(R.drawable.ic_baseline_bookmark_selected_24);
                    isSaved = true;

                    Utils.store(context, GETTING_AROUND_LIST_TYPE, true);
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    boolean isSaved = false;
}

