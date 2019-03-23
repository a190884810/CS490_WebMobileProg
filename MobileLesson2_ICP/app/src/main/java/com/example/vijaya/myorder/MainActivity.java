package com.example.vijaya.myorder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String MAIN_ACTIVITY_TAG = "MainActivity";
    final int COFFEE_PRICE = 5;
    final int WHIPPED_CREAM_PRICE = 1;
    final int CHOCOLATE_PRICE = 2;
    final int PIZZA_PRICE = 10;
    final int MEAT_PRICE = 3;
    final int VEG_PRICE = 1;
    int quantity = 3;
    String orderSummaryMessage = "";
    String userInputName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void createSummary() {
        // get user input
        EditText userInputNameView = (EditText) findViewById(R.id.user_input);
        userInputName = userInputNameView.getText().toString();

        // check if pepperoni is selected
        CheckBox pepperoni = (CheckBox) findViewById(R.id.pepperoni_checked);
        boolean hasPepperoni = pepperoni.isChecked();

        // check if anchovies is selected
        CheckBox anchovies = (CheckBox) findViewById(R.id.anchovies_checked);
        boolean hasAnchovies = anchovies.isChecked();

        // check if pineapple is selected
        CheckBox pineapple = (CheckBox) findViewById(R.id.pineapple_checked);
        boolean hasPineapple = pineapple.isChecked();

        // check if peppers is selected
        CheckBox peppers = (CheckBox) findViewById(R.id.peppers_checked);
        boolean hasPeppers = peppers.isChecked();

        // calculate and store the total price
        float totalPrice = calculatePrice(hasPepperoni, hasAnchovies, hasPineapple, hasPeppers);

        // create and store the order summary
        orderSummaryMessage = createOrderSummary(userInputName, hasPepperoni, hasAnchovies, hasPineapple, hasPeppers, totalPrice);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {

        createSummary();

        // Write the relevant code for making the buttons work(i.e implement the implicit and explicit intents
        sendEmail(userInputName, orderSummaryMessage);

    }

    public void sendEmail(String name, String output) {
        // Write the relevant code for triggering email

        // Hint to accomplish the task

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_SUBJECT, name + "'s Order");
        intent.putExtra(Intent.EXTRA_TEXT, output);
        startActivity(intent);
        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }
    }

    public void gotoSummary (View view) {
        createSummary();
        Intent intent1 = new Intent(MainActivity.this, SummaryActivity.class);
        intent1.putExtra("summary", orderSummaryMessage);
        startActivity(intent1);
    }

    private String boolToString(boolean bool) {
        return bool ? (getString(R.string.yes)) : (getString(R.string.no));
    }

    private String createOrderSummary(String userInputName, boolean hasPepperoni, boolean hasAnchovies, boolean hasPineapple, boolean hasPeppers,  float price) {
        String orderSummaryMessage = getString(R.string.order_summary_name, userInputName) + "\n" +
                getString(R.string.order_summary_pepperoni, boolToString(hasPepperoni)) + "\n" +
                getString(R.string.order_summary_anchovies, boolToString(hasAnchovies)) + "\n" +
                getString(R.string.order_summary_pineapple, boolToString(hasPineapple)) + "\n" +
                getString(R.string.order_summary_peppers, boolToString(hasPeppers)) + "\n" +
                getString(R.string.order_summary_quantity, quantity) + "\n" +
                getString(R.string.order_summary_total_price, price) + "\n" +
                getString(R.string.thank_you);
        return orderSummaryMessage;
    }

    /**
     * Method to calculate the total price
     *
     * @return total Price
     */
    private float calculatePrice(boolean hasPepperoni, boolean hasAnchovies, boolean hasPineapple, boolean hasPeppers) {
        int basePrice = PIZZA_PRICE;
        if (hasPepperoni) {
            basePrice += MEAT_PRICE;
        }
        if (hasPeppers) {
            basePrice += VEG_PRICE;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method increments the quantity of pizzas by one
     *
     * @param view on passes the view that we are working with to the method
     */

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select less than one hundred pizzas");
            Context context = getApplicationContext();
            String lowerLimitToast = getString(R.string.too_much_pizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    /**
     * This method decrements the quantity of pizzas by one
     *
     * @param view passes on the view that we are working with to the method
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("MainActivity", "Please select at east one pizza");
            Context context = getApplicationContext();
            String upperLimitToast = getString(R.string.too_little_pizza);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }
}