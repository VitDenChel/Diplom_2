package com;

public class IngredientsForOrder {

    private String ingredients;


    public IngredientsForOrder(String ingredients) {
        this.ingredients = ingredients;
    }

    public static IngredientsForOrder IngredientsForOrder() {
        String ingredients = "61c0c5a71d1f82001bdaaa6d";

        return new IngredientsForOrder(ingredients);
    }
}


