package com.example.bcexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bcexplorer.databinding.ActivityReadMoreBinding;

public class ReadMoreActivity extends AppCompatActivity {
    private ActivityReadMoreBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityReadMoreBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

    }
}
