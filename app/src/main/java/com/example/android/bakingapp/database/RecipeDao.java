package com.example.android.bakingapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.android.bakingapp.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {

    // to call all the recipe from the room database
    @Query("SELECT * FROM recipeTable ")
    List<Recipe> loadAllRecipe();


    // Insert new Recipe in the recipe table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe recipe);


}
