package com.sserdiuk.bitsandpizzas;

/**
 * DataBase for Pasta Image + names
 *
 * Created by sserdiuk on 3/12/18.
 */

public class Pasta {
    private String name;
    private int imageResourceID;

    public static final Pasta[] pastas = {
            new Pasta("Spaghetti Bolognese", R.drawable.upload_empty),
            new Pasta("Lasagne", R.drawable.upload_empty)
    };

    private Pasta(String name, int imageResourceID) {
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
