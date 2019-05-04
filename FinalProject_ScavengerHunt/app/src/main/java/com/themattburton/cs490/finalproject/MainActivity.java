package com.themattburton.cs490.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO:  this home screen activity is just a stub, and really needs dressed up
    }

    public void startScavengerHunt (View view) {
        Intent intent = new Intent(this,ScavengerHuntBeginActivity.class);
        this.startActivity(intent);
    }

}
