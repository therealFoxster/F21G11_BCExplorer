package com.example.bcexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bcexplorer.databinding.ActivityExploreBinding;

public class ExploreActivity extends AppCompatActivity {
    private ActivityExploreBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityExploreBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

    }
}