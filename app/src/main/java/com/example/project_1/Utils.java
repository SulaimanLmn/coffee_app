package com.example.project_1;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;


public class Utils {

    private final String ALL_COFFEES_KEY = "all_coffees_key";

    private final String ALL_USERS_KEY = "all_users_key";
    private final String ALL_USER_CARTS_KEY = "all_users_cart_key";

    private final String CURRENT_USER_ID_KEY = "curent_user_id_key";
    private static Utils instance;
    private SharedPreferences sharedPreferences;

    private CartAdapter cartAdapter;
    private String paymentMethod;

    private Utils(Context context) {
        sharedPreferences = context.getSharedPreferences("coffeeShop", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (getAllUsers() == null) {
            editor.putString(ALL_USERS_KEY, gson.toJson(new ArrayList<User>()));
            editor.commit();
        }
        if (getAllCoffees() == null) {
            initData();
        }
        if (getAllUserCarts() == null) {
            editor.putString(ALL_USER_CARTS_KEY, gson.toJson(new HashMap<>()));
            editor.commit();
        }
    }

    private void initData() {
        ArrayList<Coffee> coffees = new ArrayList<>();
        coffees.add(new Coffee(1, "Espresso", 15000, "A bold and intense coffee experience, perfect for the true coffee connoisseur. Our Espresso is expertly brewed to deliver a rich, robust flavor in every sip. The concentrated shot of espresso awakens your senses and provides a powerful caffeine boost.", R.drawable.expresso_product_img, R.drawable.expresso_product_detail_img));
        coffees.add(new Coffee(2, "Cappuccino", 20000, "A classic Italian coffee drink, our Cappuccino combines rich espresso with velvety steamed milk and a delicate layer of frothy foam. The perfect balance of flavors and textures, each sip is a delightful journey. The creamy milk softens the intensity of the espresso, creating a harmonious blend that satisfies both coffee lovers and those who prefer a milder taste.", R.drawable.cappuccino_product_img, R.drawable.cappuccino_product_detail_img));
        coffees.add(new Coffee(3, "Latte", 20000, "A smooth and creamy coffee beverage, our Latte blends rich espresso with steamed milk for a comforting and indulgent experience. Perfect for any time of day, the Latte's velvety texture and subtle sweetness make it a popular choice. The steamed milk mellows the strong flavor of the espresso, creating a gentle and comforting drink.", R.drawable.latte_product_img, R.drawable.latte_product_detail_img));
        coffees.add(new Coffee(4, "Americano", 15000, "A simple yet elegant coffee, our Americano is a perfect choice for those who prefer a milder coffee experience. Espresso is diluted with hot water to create a clean, refreshing taste. The Americano offers a more gentle caffeine kick, making it a great option for those who want to avoid the intensity of a straight shot of espresso.", R.drawable.americano_product_img, R.drawable.americano_product_detail_img));
        coffees.add(new Coffee(5, "Mocha", 25000, "A decadent treat for the coffee lover with a sweet tooth. Our Caffè Mocha combines rich espresso with steamed milk and velvety chocolate. Every sip is a delightful experience, offering the perfect balance of coffee, creaminess, and chocolatey goodness. The rich chocolate flavor complements the bold taste of espresso, creating a truly indulgent beverage.", R.drawable.mocha_product_img, R.drawable.mocha_product_detail_img));
        coffees.add(new Coffee(6, "Caramel Macchiato", 25000, "A delightful blend of espresso, steamed milk, vanilla syrup, and caramel drizzle. Our Caramel Macchiato offers a sweet and indulgent coffee experience. The layers of flavor and texture in every sip make it a truly satisfying drink.", R.drawable.caramel_machiato_product_img, R.drawable.caramel_machiato_product_detail_img));
        coffees.add(new Coffee(7, "Cold Brew", 25000, "A smooth and refreshing coffee, our Cold Brew is brewed slowly over ice to create a bold, full-bodied flavor without the bitterness. Perfect for a hot summer day, the Cold Brew offers a refreshing and invigorating caffeine boost.", R.drawable.cold_brew_product_img, R.drawable.cold_brew_product_detail_img));
        coffees.add(new Coffee(8, "Iced Latte", 20000, "A refreshing take on the classic latte, our Iced Latte combines rich espresso with cold milk and ice. Perfect for a hot summer day, the Iced Latte offers a cool and refreshing way to enjoy your favorite coffee.", R.drawable.iced_latte_product_img, R.drawable.iced_latte_product_detail_img));
        coffees.add(new Coffee(9, "Vanilla Latte", 20000, "A comforting and indulgent coffee drink, our Vanilla Latte combines rich espresso with steamed milk and a touch of vanilla syrup. A perfect blend of flavors, the Vanilla Latte offers a warm and comforting experience.", R.drawable.vanila_latte_product_img, R.drawable.vanila_latte_product_detail_img));
        coffees.add(new Coffee(10, "Flat White", 20000, "A smooth and creamy coffee, our Flat White combines rich espresso with microfoam milk. A perfect choice for those who appreciate a subtle, yet flavorful coffee. The microfoam creates a velvety texture and a delicate sweetness that complements the bold flavor of the espresso.", R.drawable.flat_white_product_img, R.drawable.flat_white_product_detail_img));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_COFFEES_KEY,gson.toJson(coffees));
        editor.apply();

    }

    public static Utils getInstance(Context context) {
        if (instance == null) {
            instance = new Utils(context);
        }
        return instance;
    }

    public ArrayList<User> getAllUsers() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<User>>(){}.getType();

        return gson.fromJson(sharedPreferences.getString(ALL_USERS_KEY, null), type);
    }

  public User getUserById(int userId){
        ArrayList<User> users = getAllUsers();
      for (var user : users) {
          if(user.getId() == userId){
              return user;
          }
      }
      return null;
  }

  public void setCurentUserId(int userId){
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putInt(CURRENT_USER_ID_KEY, userId);
      editor.apply();
  }

  public int getCurentUserId(){
      return sharedPreferences.getInt(CURRENT_USER_ID_KEY, -1);
  }


    public User findUserByEmailAndPassword(String email, String password) {
        ArrayList<User> users = getAllUsers();
        for (var user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean addUser(String email, String username, String password) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();


        ArrayList<User> users = getAllUsers();
        for (var user : users) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }
        users.add(new User(users.size() + 1, email, username, password));
        editor.putString(ALL_USERS_KEY, gson.toJson(users));
        editor.apply();
        return true;
    }

    public ArrayList<Coffee> getAllCoffees() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Coffee>>(){}.getType();

        return gson.fromJson(sharedPreferences.getString(ALL_COFFEES_KEY, null),type);
    }

    public Coffee getCoffeeById(int coffeeId) {
        ArrayList<Coffee> coffees = getAllCoffees();
        for (var coffee : coffees) {
            if (coffee.getId() == coffeeId) {
                return coffee;
            }
        }
        return null;
    }

    // Manage User Carts
    public ArrayList<Cart> getUserCart(int userId) {
        HashMap<Integer, ArrayList<Cart>> userCarts = getAllUserCarts();
        return userCarts.getOrDefault(userId, new ArrayList<>());
    }

    public void addToCart(int userId, Coffee coffee) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        HashMap<Integer, ArrayList<Cart>> userCarts = getAllUserCarts();
        ArrayList<Cart> cart = userCarts.getOrDefault(userId, new ArrayList<>());

        // Check if coffee already exists in the cart
        boolean coffeeExists = false;
        for (Cart cartItem : cart) {
            if (cartItem.getCoffee().getId() == coffee.getId()) {
                cartItem.setAmount(cartItem.getAmount() + 1);
                coffeeExists = true;
                break;
            }
        }

        // If coffee doesn't exist, add it as a new item
        if (!coffeeExists) {
            cart.add(new Cart(coffee, 1));
        }

        // Update the user's cart in the map
        userCarts.put(userId, cart);

        editor.putString(ALL_USER_CARTS_KEY, gson.toJson(userCarts));
        editor.apply();
    }

    public void incrementCoffeeItemAmount(int userId, int position){
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        HashMap<Integer, ArrayList<Cart>> userCarts = getAllUserCarts();
        ArrayList<Cart> carts = userCarts.get(userId);
        if (carts == null || carts.isEmpty()) {
            return;
        }
        Cart cartItem = carts.get(position);
        cartItem.setAmount(cartItem.getAmount() + 1);

        editor.putString(ALL_USER_CARTS_KEY, gson.toJson(userCarts));
        editor.apply();

    }

    public void decrementCoffeeItemAmount(int userId, int position){
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        HashMap<Integer, ArrayList<Cart>> userCarts = getAllUserCarts();
        ArrayList<Cart> carts = userCarts.get(userId);
        if (carts == null || carts.isEmpty()) {
            return;
        }
        Cart cartItem = carts.get(position);
        cartItem.setAmount(cartItem.getAmount() - 1);

        editor.putString(ALL_USER_CARTS_KEY, gson.toJson(userCarts));
        editor.apply();

    }


    public void deleteFromCart(int userId, Cart cart) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        HashMap<Integer, ArrayList<Cart>> userCarts = getAllUserCarts();
        ArrayList<Cart> carts = userCarts.get(userId);
        if (carts == null) {
            return;
        }
        carts.removeIf(cartItem -> cartItem.getCoffee().getId() == cart.getCoffee().getId());
        getCartAdapter().setCarts(carts);

        userCarts.put(userId, carts);
        editor.putString(ALL_USER_CARTS_KEY, gson.toJson(userCarts));
        editor.apply();
    }

    public HashMap<Integer, ArrayList<Cart>> getAllUserCarts(){
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<Integer, ArrayList<Cart>>>(){}.getType();

        return gson.fromJson(sharedPreferences.getString(ALL_USER_CARTS_KEY, null), type);
    }

    public void clearAllCartItems(int userId) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        HashMap<Integer, ArrayList<Cart>> userCarts = getAllUserCarts();

        userCarts.put(userId, new ArrayList<>());
        editor.putString(ALL_USER_CARTS_KEY, gson.toJson(userCarts));
        editor.apply();

    }

    public String getTotalCartPriceString(int userId) {
        HashMap<Integer, ArrayList<Cart>> userCarts = getAllUserCarts();
        ArrayList<Cart> cart = userCarts.getOrDefault(userId, new ArrayList<>());
        int totalPrice = 0;

        if(cart == null){
            return "0";
        }
        for (var cartItem : cart) {
            totalPrice += cartItem.getCoffee().getPrice() * cartItem.getAmount();
        }

        return String.valueOf(totalPrice);
    }

    public String getCoffeeItemTotalPriceString(int userId, int position) {
        HashMap<Integer, ArrayList<Cart>> userCarts = getAllUserCarts();
        ArrayList<Cart> carts = userCarts.get(userId);

        if (carts == null || carts.isEmpty()) {
            return "0";
        }

        Cart cart = carts.get(position);
        return String.valueOf(cart.getAmount() * cart.getCoffee().getPrice());
    }

    public String getCoffeeItemTotalAmountString(int userId, int position) {
        HashMap<Integer, ArrayList<Cart>> userCarts = getAllUserCarts();
        ArrayList<Cart> carts = userCarts.get(userId);

        // Check if the cart is empty
        if (carts == null || carts.isEmpty()) {
            return "0"; // Or any other appropriate message
        }

        Cart cart = carts.get(position);
        return String.valueOf(cart.getAmount());
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        switch (paymentMethod) {
            case 1:
                this.paymentMethod = "BCA";
                break;
            case 2:
                this.paymentMethod = "GOPAY";
                break;
            case 3:
                this.paymentMethod = "BNI";
                break;
            default:
                this.paymentMethod = "Not Defined";
        }
    }

    public void setCartAdapter(CartAdapter cartAdapter) {
        this.cartAdapter = cartAdapter;
    }

    public CartAdapter getCartAdapter() {
        return cartAdapter;
    }
}
