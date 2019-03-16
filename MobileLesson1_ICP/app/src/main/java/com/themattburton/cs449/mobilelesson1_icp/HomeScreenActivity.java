package com.themattburton.cs449.mobilelesson1_icp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }


    public void redirectToHomePage(View v) {
        Intent redirect = new Intent(HomeScreenActivity.this, MainActivity.class);
        startActivity(redirect);
    }
}
