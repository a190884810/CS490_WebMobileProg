package com.themattburton.cs449.mobilelesson1_icp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText usernameCtrl, passwordCtrl;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameCtrl = findViewById(R.id.editTextUserName);
        passwordCtrl = findViewById(R.id.editTextPassword);

        username = "";
        password = "";

    }

    public void validateLogin(View v) {
        Boolean goodUsername = false;
        Boolean goodPassword = false;
        username = usernameCtrl.getText().toString();
        password = passwordCtrl.getText().toString();

        v.clearFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

        if (!username.isEmpty() && !password.isEmpty()) {
            if (username.equals("Admin")) {
                goodUsername = true;
                if (password.equals("Admin")) {
                    goodPassword = true;
                }
            }
        }

        if (goodUsername && goodPassword) {
            Toast.makeText(getApplicationContext(), "Welcome!" , Toast.LENGTH_SHORT).show();
            redirectToHomePage();
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Login!" , Toast.LENGTH_LONG).show();
            if (!goodUsername) {
                usernameCtrl.setError("Invalid Username");
                usernameCtrl.requestFocus();
            } else {
                passwordCtrl.setError("Invalid Password");
                passwordCtrl.setText("");
            }
        }
    }

    public void redirectToHomePage() {
        Intent redirect = new Intent(MainActivity.this, HomeScreenActivity.class);
        startActivity(redirect);
    }
}
