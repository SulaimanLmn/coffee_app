package com.example.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button directButton = findViewById(R.id.directButton);
        directButton.setOnClickListener((v) ->{
            Utils utils = Utils.getInstance(this);
            int currentUserId = utils.getCurentUserId();
            User user = utils.getUserById(currentUserId);
            if(user != null){
                Intent i = new Intent(this, HomeActivity.class);
                startActivity(i);
            } else{
                Intent i = new Intent(this, SignupActivity.class);
                startActivity(i);
            }


        });






    }
}