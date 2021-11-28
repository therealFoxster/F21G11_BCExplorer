package com.example.bcexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bcexplorer.databinding.ActivityExploreBinding;
import com.example.bcexplorer.utils.Utils;

public class ExploreActivity extends AppCompatActivity {
    private ActivityExploreBinding b;

    String LIST_TYPE = Constants.VANCOUVER;

    String WHATS_NEW_LIST_TYPE = Constants.WHATS_NEW_VANCOUVER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityExploreBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        LIST_TYPE = getIntent().getStringExtra(Constants.PARAMS);

        if (LIST_TYPE.equals(Constants.VANCOUVER)) {
            b.topText.setText("Vancouver is upping the steaks");
            b.middleText.setText("Black + Blue");
            b.imageview.setImageResource(R.drawable.whats_new_vancouver);

            WHATS_NEW_LIST_TYPE = Constants.WHATS_NEW_VANCOUVER;}

        if (LIST_TYPE.equals(Constants.WHITE_ROCK)) {
            b.topText.setText("The Pond at BFS");
            b.middleText.setText("Blue Frog Studio");
            b.imageview.setImageResource(R.drawable.whats_new_white_rock);

            WHATS_NEW_LIST_TYPE = Constants.WHATS_NEW_WHITE_ROCK;
        }

        if (LIST_TYPE.equals(Constants.WHISTLER)) {
            b.topText.setText("Like Darts But More Canadian");
            b.middleText.setText("Forged Axe Throwing");
            b.imageview.setImageResource(R.drawable.whats_new_whistler);

            WHATS_NEW_LIST_TYPE = Constants.WHATS_NEW_WHISTLER;
        }

        Context context = ExploreActivity.this;

        if (Utils.getBoolean(context, WHATS_NEW_LIST_TYPE, false)) {
            b.saveBtnExplore.setImageResource(R.drawable.ic_baseline_bookmark_selected_24);
            isSaved = true;
        }
        b.saveBtnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSaved) {
                    b.saveBtnExplore.setImageResource(R.drawable.ic_baseline_bookmark_unselected_24);
                    isSaved = false;

                    Utils.store(context, WHATS_NEW_LIST_TYPE, false);
                    Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                } else {
                    //SAVING
                    b.saveBtnExplore.setImageResource(R.drawable.ic_baseline_bookmark_selected_24);
                    isSaved = true;

                    Utils.store(context, WHATS_NEW_LIST_TYPE, true);
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    boolean isSaved = false;

}
