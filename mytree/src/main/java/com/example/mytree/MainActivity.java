package com.example.mytree;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText editText;
    private CustomTreeView customTreeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        imageView = findViewById(R.id.seach_image);
        editText = findViewById(R.id.seach_ed);
        customTreeView = findViewById(R.id.customtree);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(MainActivity.this);
                textView.setTextColor(Color.RED);
                textView.setText(editText.getText().toString().trim());
                textView.setBackgroundResource(R.drawable.text_shape);
                customTreeView.addView(textView);
                customTreeView.invalidate();
            }
        });
    }
}
