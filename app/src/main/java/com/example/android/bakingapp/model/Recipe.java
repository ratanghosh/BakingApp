package com.example.android.bakingapp.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.android.bakingapp.Constant;


@Entity(tableName = Constant.RECIPE_TABLE, indices = @Index(value = {Constant.RECIPEID}, unique = true))
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String recipeId;
    private String recipeName;
    private String ingredientsString;
    private String stepsString;


    public Recipe(int id, String recipeId, String recipeName, String ingredientsString, String stepsString) {
        this.id = id;
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.ingredientsString = ingredientsString;
        this.stepsString = stepsString;
    }

    @Ignore
    public Recipe(String recipeId, String recipeName, String ingredientsString, String stepsString) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.ingredientsString = ingredientsString;
        this.stepsString = stepsString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIngredientsString() {
        return ingredientsString;
    }

    public void setIngredientsString(String ingredientsString) {
        this.ingredientsString = ingredientsString;
    }

    public String getStepsString() {
        return stepsString;
    }

    public void setStepsString(String stepsString) {
        this.stepsString = stepsString;
    }
}