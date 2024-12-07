package com.example.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class FinishedTransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_transaction);
        Utils utils = Utils.getInstance(this);
        int userId = utils.getCurentUserId();
        TextView transactionIdTv = findViewById(R.id.transactionIdText);
        TextView amountPaidTv = findViewById(R.id.amountPaidText);
        TextView paymentTypeTv = findViewById(R.id.paymentTypeText);
        MaterialButton directToHomeBtn = findViewById(R.id.directToHomeButton);


        String transactionId = generateTransactionId();
        String amountPaid = "Amount Paid : " + "Rp " + utils.getTotalCartPriceString(userId);
        String paymentType = "Payment Method : " + utils.getPaymentMethod();

        paymentTypeTv.setText(paymentType);
        transactionIdTv.setText("Transaction ID : " + transactionId);
        amountPaidTv.setText(amountPaid);
        directToHomeBtn.setOnClickListener((v) ->{
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            Utils.getInstance(this).clearAllCartItems(userId);
        });
    }

    private String generateTransactionId(){
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        return timestamp + "-" + randomNumber;
    }

}
