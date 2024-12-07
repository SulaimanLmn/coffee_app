package com.example.project_1;

public class Cart {
    private Coffee coffee;
    private int amount;

    public Cart(Coffee coffee, int amount){
        this.coffee = coffee;
        this.amount = amount;
    }

    public Coffee getCoffee(){
        return coffee;
    }

    public void setCoffee(Coffee coffee) {
        this.coffee = coffee;
    }

    public int getAmount() {
        return amount;
    }

    public String getAmountString(){
        return String.valueOf(amount);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
