package com.example.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

public class CoffeeDetailActivity extends AppCompatActivity {
    private  ImageView coffeeImageIv;
    private TextView coffeeNameTv;
    private TextView coffeeDescriptionTv;
    private TextView coffeePriceTv;
    private MaterialButton addToCartButtonMb;
    private Coffee filteredCoffee;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils utils = Utils.getInstance(this);
        int userId = utils.getCurentUserId();

        setContentView(R.layout.activity_coffee_detail);
        coffeeImageIv = findViewById(R.id.coffeeImage);
        coffeeNameTv = findViewById(R.id.coffeeName);
        coffeeDescriptionTv  = findViewById(R.id.coffeeDescription);
        coffeePriceTv = findViewById(R.id.coffeePrice);
        addToCartButtonMb = findViewById(R.id.addCartButton);
        Toolbar toolbar = findViewById(R.id.my_toolbar);

        Intent intentData = getIntent();
        if(intentData != null){
            int coffeeId = intentData.getIntExtra("coffeeId", -1);

            // Check if coffeeId is not -1 which is default value if intent doesn't find coffeeId
            if(coffeeId != -1){
                filteredCoffee = utils.getCoffeeById(coffeeId);
            }
            setData();
        }

        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        addToCartButtonMb.setOnClickListener((v) ->{
            Intent intent = new Intent(this, CartActivity.class);
            utils.addToCart(userId, filteredCoffee);
                startActivity(intent);
        });

    }

    private void setData(){
        if(filteredCoffee != null){
            coffeeImageIv.setImageResource(filteredCoffee.getProductDetailImage());
            coffeeNameTv.setText(filteredCoffee.getName());
            coffeeDescriptionTv.setText(filteredCoffee.getProductDescription());
            coffeePriceTv.setText("Rp" + filteredCoffee.getPriceString());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }
}
