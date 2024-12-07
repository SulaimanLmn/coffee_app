package com.example.project_1;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.ViewHolder> implements Filterable {
    private ArrayList<Coffee> coffees;
    private ArrayList<Coffee> filteredCoffeesData;
    private Context context;
    public CoffeeAdapter(Context context, ArrayList<Coffee> coffees){
        this.context = context;
        this.coffees = coffees;
        filteredCoffeesData = new ArrayList<>(coffees);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.coffee_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Coffee coffee = coffees.get(position);

        holder.nameTv.setText(coffee.getName());
        holder.priceTv.setText("Rp " + coffee.getPriceString());
        holder.imageIv.setImageResource(coffee.getProductImage());

        holder.directToDetailButtonMb.setOnClickListener((v) ->{
            Intent intent = new Intent(context, CoffeeDetailActivity.class);
            intent.putExtra("coffeeId", coffee.getId());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return coffees.size();
    }

    private Filter filterNotification  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Coffee> filteredCoffees = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0){
                filteredCoffees.addAll(filteredCoffeesData);
            } else{
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (var coffee : filteredCoffeesData) {
                    if(coffee.getName().toLowerCase().contains(filterPattern)){
                        filteredCoffees.add(coffee);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredCoffees;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            coffees.clear();
            coffees.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return filterNotification;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        private TextView nameTv;
        private TextView priceTv;
        private ImageView imageIv;

        MaterialButton directToDetailButtonMb;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.name);
            priceTv = itemView.findViewById(R.id.price);
            imageIv = itemView.findViewById(R.id.coffeeImage);
            directToDetailButtonMb = itemView.findViewById(R.id.directToDetailButton);
        }
    }

}
