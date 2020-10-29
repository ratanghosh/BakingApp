package com.example.android.bakingapp.database;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.android.bakingapp.Constant;
import com.example.android.bakingapp.model.Recipe;


@Database(entities = {Recipe.class}, version = 1, exportSchema = false)


public abstract class RecipeDatabase extends RoomDatabase {
    private static final String LOG_TAG = RecipeDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = Constant.RECIPE_LIST;
    private static RecipeDatabase sInstance;

    public static RecipeDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {

                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        RecipeDatabase.class, RecipeDatabase.DATABASE_NAME)
                        .build();
            }
        }

        return sInstance;
    }

    public abstract RecipeDao recipeDao();

}