package com.example.androidlivedata;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class ArchActivity extends AppCompatActivity {
    private ArchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch_avtivity);
        viewModel = new ViewModelProvider(this).get(ArchViewModel.class);
        Button increase = findViewById(R.id.increase_number);
        final TextView showNumber = findViewById(R.id.show_number);

        Observer<Integer> textObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                showNumber.setText(integer.toString());
            }
        };

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.increase();
            }
        });


        viewModel.getCurrentNumber().observe(this, textObserver);
    }
}
