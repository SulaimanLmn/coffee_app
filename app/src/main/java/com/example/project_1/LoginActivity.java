package com.example.project_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils utils = Utils.getInstance(this);
        setContentView(R.layout.activity_login);
        TextView emailTv = findViewById(R.id.editTextEmail);
        TextView passwordTv = findViewById(R.id.editTextPassword);
        MaterialButton loginBtn = findViewById(R.id.loginButton);

        loginBtn.setOnClickListener((v) -> {
            String email = emailTv.getText().toString().trim().toLowerCase();
            String password = passwordTv.getText().toString().trim().toLowerCase();
            loginHandler(utils, email, password);
        });
        TextView directToSignupBtn = findViewById(R.id.directToSignupButton);
        directToSignupBtn.setOnClickListener((v) ->{
            Intent intent = new Intent(this, SignupActivity.class);
            startActivity(intent);
        });



    }
    private void loginHandler(Utils utils, String email, String password){
        User user = utils.findUserByEmailAndPassword(email, password);
        if(user != null){
            utils.setCurentUserId(user.getId());
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(this, "Incorrect email or password.", Toast.LENGTH_SHORT).show();
        }

    }
}
