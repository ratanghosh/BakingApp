package com.example.android.bakingapp.model;


import com.example.android.bakingapp.Constant;
import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName(Constant.INGREDIENT)
    private String ingredientsName;
    @SerializedName(Constant.QUANTITY)
    private String ingredientsQuantity;
    @SerializedName(Constant.MEASURE)
    private String ingredientsMeasure;

    public Ingredient() {

    }

    public Ingredient(String ingredientsName) {
        this.ingredientsName = ingredientsName;
    }

    public Ingredient(String ingredientsName, String ingredientsQuantity, String ingredientsMeasure) {
        this.ingredientsName = ingredientsName;
        this.ingredientsQuantity = ingredientsQuantity;
        this.ingredientsMeasure = ingredientsMeasure;
    }

    public String getIngredientsName() {
        return ingredientsName;
    }

    public void setIngredientsName(String ingredientsName) {
        this.ingredientsName = ingredientsName;
    }

    public String getIngredientsQuantity() {
        return ingredientsQuantity;
    }

    public void setIngredientsQuantity(String ingredientsQuantity) {
        this.ingredientsQuantity = ingredientsQuantity;
    }

    public String getIngredientsMeasure() {
        return ingredientsMeasure;
    }

    public void setIngredientsMeasure(String ingredientsMeasure) {
        this.ingredientsMeasure = ingredientsMeasure;
    }
}
