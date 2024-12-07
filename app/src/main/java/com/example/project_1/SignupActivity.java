package com.example.project_1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        TextView directToLoginBtn = findViewById(R.id.directToLoginButton);
        EditText emailTv = findViewById(R.id.editTextEmail);
        EditText usernameTv = findViewById(R.id.editTextUsername);
        EditText passwordTv = findViewById(R.id.editTextPassword);
        MaterialButton signupBtn = findViewById(R.id.signupButton);


        signupBtn.setOnClickListener((v) -> {
            String email = emailTv.getText().toString().toLowerCase().trim();
            String username = usernameTv.getText().toString().toLowerCase().trim();
            String password = passwordTv.getText().toString().toLowerCase().trim();
            SignupHandler(email, username, password);
        });
        directToLoginBtn.setOnClickListener((v) ->{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void SignupHandler(String email, String username, String password){
        boolean isEmailValid = !email.isEmpty() && emailValidator(email);
        boolean isUsernameValid = !username.isEmpty() && username.length() >= 3;
        boolean isPasswordValid = !password.isEmpty() && password.length() >= 8;

        if(isEmailValid && isUsernameValid && isPasswordValid){
            if(!Utils.getInstance(this).addUser(email, username, password)){
                Toast.makeText(this, "This email address is already registered. Please try another one.", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        } else{
            Toast.makeText(this, "Please fill in all required fields to continue", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean emailValidator(String email) {
        String emailRegex = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
