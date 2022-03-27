package com;

public class IngredientsForOrderIncorrectHash {

    private String ingredients;


    public IngredientsForOrderIncorrectHash(String ingredients) {
        this.ingredients = ingredients;
    }

    public static IngredientsForOrderIncorrectHash IngredientsForOrderIncorrectHash() {
        String ingredients = "1234567890";

        return new IngredientsForOrderIncorrectHash(ingredients);
    }
}
