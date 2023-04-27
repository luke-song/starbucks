package com.example.springcashier;

import java.util.HashMap;
import java.util.Random;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PostMapping;

public class OrderGenerator {

    // Array for mapping purposes
    private static String[] DRINK_OPTIONS = { "Caffe Latte", "Caffe Americano", "Caffe Mocha", "Espresso",
            "Cappuccino" };
    private static String[] MILK_OPTIONS = { "Whole Milk", "2% Milk", "Nonfat Milk", "Almond Milk", "Soy Milk" };
    private static String[] SIZE_OPTIONS = { "Tall", "Grande", "Venti", "Your Own Cup" };
    private static String[] ESPRESSO_SIZE = { "Short", "Tall" };

    // Hashmap prices based on sizes
    private static HashMap<String, String> TALL = new HashMap<String, String>();
    private static HashMap<String, String> GRANDE = new HashMap<String, String>();
    private static HashMap<String, String> VENTI = new HashMap<String, String>();
    private static HashMap<String, String> E_SIZE = new HashMap<String, String>();

    // Adding prices based on sizes
    static {
        TALL.put("Caffe Latte", "$2.95");
        TALL.put("Caffe Americano", "$2.25");
        TALL.put("Caffe Mocha", "$3.45");
        TALL.put("Cappuccino", "$1.95");

        GRANDE.put("Caffe Latte", "$3.65");
        GRANDE.put("Caffe Americano", "$2.65");
        GRANDE.put("Caffe Mocha", "$4.15");
        GRANDE.put("Cappuccino", "$3.65");

        VENTI.put("Caffe Latte", "$3.95");
        VENTI.put("Caffe Americano", "$2.95");
        VENTI.put("Caffe Mocha", "$4.45");
        VENTI.put("Cappuccino", "$3.95");

        E_SIZE.put("Short", "$1.75");
        E_SIZE.put("Tall", "$1.95");
    }

    @PostMapping("/newOrder")
    public static void prepareOrder(Order order) {
        Random random = new Random();
        int drinkIndex = random.nextInt(DRINK_OPTIONS.length);
        int milkIndex = random.nextInt(MILK_OPTIONS.length);

        String drink = DRINK_OPTIONS[drinkIndex];
        String milk = MILK_OPTIONS[milkIndex];

        order.setDrink(drink);
        order.setMilk(milk);

        if (drink.equals("Espresso")) {
            int sizeIndex = random.nextInt(ESPRESSO_SIZE.length);
            String size = ESPRESSO_SIZE[sizeIndex];
            order.setSize(size);
            order.setTotal(E_SIZE.get(size));
        } else {
            int sizeIndex = random.nextInt(SIZE_OPTIONS.length);
            String size = SIZE_OPTIONS[sizeIndex];
            order.setSize(size);
            if (size.equals("Tall")) {
                order.setTotal(TALL.get(drink));
            } else if (size.equals("Grande")) {
                order.setTotal(GRANDE.get(drink));
            } else {
                order.setTotal(VENTI.get(drink));
            }
        }

        order.setStatus("Payment Pending");
    }
}
