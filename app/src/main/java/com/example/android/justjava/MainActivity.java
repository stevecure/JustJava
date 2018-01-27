/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

import static android.R.id.message;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.android.justjava.R.string.price;
import static com.example.android.justjava.R.string.quantity;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);

    }
    public void decrement(View view) {
        quantity = quantity - 1;
        displayQuantity(quantity);

    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
         String name = nameField.getText().toString();

        //figure out if user want whipped cream
        CheckBox whippedCreamCheckbox= (CheckBox) findViewById(R.id.whipped_cream_checkbox);
          boolean hasWhippedCream = whippedCreamCheckbox.isChecked();


        //figure out if the user want chocolate
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
         boolean hasChocolate = chocolateCheckbox.isChecked();

         int price = calculatePrice(hasWhippedCream,hasChocolate);
        String priceMessage =creatOrderSummary(name,price, hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }



    }


    /**
     * Calculates the price of the order.
     *@return total price
     *
     */
    private int calculatePrice(boolean addWhippedCream , boolean addChocolate ){
      //  price of i cup of coffee

        int basePrice = 5;

        //add 1$ if user want whipped cream
        if (addWhippedCream) {
            basePrice=basePrice + 1;
        }

        //add 2$ if user want chocolate
        if (addChocolate){
            basePrice=basePrice +2;
        }
        //calculate the total order price by multiplying by quantity

        return quantity * basePrice;
    }

    private String creatOrderSummary(String name ,int price , boolean addWhippedCream, boolean addChocolate){

        String priceMessage = "Name " + name;
        priceMessage+="\nAnd Whipped Cream?" + addWhippedCream;
        priceMessage+="\nAnd Chocolate?" + addChocolate;
        priceMessage=priceMessage+ "\nQuantity "+ quantity ;
        priceMessage=priceMessage + "\nTotal: $" + price;
        priceMessage = priceMessage +"\n" + getString(R.string.thank_you );

        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }


}