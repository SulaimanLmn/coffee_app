package com.example.project_1;

public class Coffee {
    private int id;
    private String name;
    private int price;

    private String productDescription;
    private int productImage;

    private int productDetailImage;

    public Coffee(int id, String name, int price, String productDescription, int productImage, int productDetailImage){
        this.id = id;
        this.name = name;
        this.price = price;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productDetailImage = productDetailImage;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getPrice(){
        return price;
    }

    public String getPriceString(){
        return String.valueOf(price);
    }

    public void setPrice(int price){
        this.price = price;
    }

    public String getProductDescription(){
        return productDescription;
    }

    public void setProductDescription(String productDescription){
        this.productDescription = productDescription;
    }

    public int getProductImage(){
        return productImage;
    }

    public void setProductImage(int productImage){
        this.productImage = productImage;
    }

    public int getProductDetailImage(){
        return productDetailImage;
    }

    public void setProductDetailImage(int productDetailImage){
        this.productDetailImage = productDetailImage;
    }







}
