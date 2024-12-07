package com.example.project_1;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends AppCompatActivity {
    private RadioButton bcaRb;
    private RadioButton gopayRb;
    private RadioButton bniRb;

    private Utils utils = Utils.getInstance(this);
    private int userId = utils.getCurentUserId();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.my_toolbar3);
        TextView totalPrice = findViewById(R.id.totalPriceValue);
        ImageButton directToHomeBtn = findViewById(R.id.directToImageButton);
        TextView orderBtn = findViewById(R.id.orderButton);

        bcaRb = findViewById(R.id.bcaRb);
        gopayRb = findViewById(R.id.gopayRb);
        bniRb = findViewById(R.id.bniRb);


        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        orderBtn.setOnClickListener((v) -> validateOrderBtn());

        directToHomeBtn.setOnClickListener((v) ->{
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.cartRecView);
        CartAdapter cartAdapter = new CartAdapter(this, utils.getUserCart(userId), totalPrice);
        utils.setCartAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);
        totalPrice.setText("Rp ." +  utils.getTotalCartPriceString(userId));


    }

    private void validateOrderBtn(){
        Utils utils = Utils.getInstance(this);
        Intent intent = new Intent(this, PaymentActivity.class);

        if(utils.getTotalCartPriceString(userId).equals("0")){
            Toast.makeText(this, "Please add items to your cart before checking out.", Toast.LENGTH_SHORT).show();
            return;
        }

        // set payment based on radio button that is clicked
        // 1 for BCA, 2 for GOPAY, and 3 for BNI
        if(bcaRb.isChecked()){
            utils.setPaymentMethod(1);
            startActivity(intent);
        } else if(gopayRb.isChecked()){
            utils.setPaymentMethod(2);
            startActivity(intent);
        } else if(bniRb.isChecked()){
            utils.setPaymentMethod(3);
            startActivity(intent);
        } else{
            Toast.makeText(this, "Please select a payment method before order.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return super.onSupportNavigateUp();
    }


}
