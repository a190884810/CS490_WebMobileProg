package com.example.vijaya.myorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends AppCompatActivity {
    String summaryText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        Intent intent = getIntent();
        summaryText = intent.getExtras().getString("summary");
        TextView summaryTextView = findViewById(R.id.summaryText);
        summaryTextView.setText(summaryText);

    }

    public void goToOrder(View view) {


        Intent intent1 = new Intent(SummaryActivity.this, MainActivity.class);
        startActivity(intent1);
    }
}
