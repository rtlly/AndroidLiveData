package com.example.androidlivedata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button archActivity = findViewById(R.id.arch_activity);
        archActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startArchActivity();
            }
        });
    }

    private void startArchActivity() {
        Intent intent = new Intent(this, ArchActivity.class);
        startActivity(intent);
    }
}
