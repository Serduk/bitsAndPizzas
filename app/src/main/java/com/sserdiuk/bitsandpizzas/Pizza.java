package com.sserdiuk.bitsandpizzas;

/**
 * This class is prepare resource adapter
 * Here will be take image and name for Pizza
 * will be used in RecyclerView
 *
 * Created by sserdiuk on 3/1/18.
 */

public class Pizza {
    private String name;
    private int imageResourceID;

    public static final Pizza[] pizzas = {
            new Pizza("Diavolo", R.drawable.diavolo),
            new Pizza("Funghi", R.drawable.funghi),
    };

    private Pizza(String name, int imageResourceID) {
        this.name = name;
        this.imageResourceID = imageResourceID;
    }

    public String getName() {
        return name;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }
}
