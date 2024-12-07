package com.example.project_1;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    private CoffeeAdapter adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Utils utils = Utils.getInstance(this);
        User user = utils.getUserById(utils.getCurentUserId());
        FloatingActionButton directToCartButton = findViewById(R.id.directToCartButton);
        FloatingActionButton logoutButton = findViewById(R.id.logoutButton);
        SearchView searchBar = findViewById(R.id.searchBar);
        TextView usernameTv = findViewById(R.id.username);
        usernameTv.setText(user.getUsername());

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        directToCartButton.setOnClickListener((v) ->{
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener((v) -> logoutHandler());


        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        RecyclerView recyclerView = findViewById(R.id.coffeeRecycleView);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CoffeeAdapter( this, Utils.getInstance(this).getAllCoffees());
        recyclerView.setAdapter(adapter);

    }

    void logoutHandler(){
        AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(this);
        confirmationDialog.setTitle("Logout")
                .setMessage("Do you want to Logout?")
                .setCancelable(true)
                .setPositiveButton("Yes", (dialog, which) -> {
                    // set Current User ID to -1 there is no user that logged in
                    Utils.getInstance(this).setCurentUserId(-1);
                   Intent intent = new Intent(this, LoginActivity.class);
                   startActivity(intent);
                })
                .setNegativeButton("No", (dialog, which) -> dialog.cancel()).show();

    }
}
