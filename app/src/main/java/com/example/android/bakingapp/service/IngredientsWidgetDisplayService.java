package com.example.android.bakingapp.service;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;

import com.example.android.bakingapp.RecipeWidgetProvider;

public class IngredientsWidgetDisplayService {

    public static void updateRecipeWidgets(Context context, String ingredientsName) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName name = new ComponentName(context, RecipeWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(name);


        RecipeWidgetProvider.updateRecipeWidgets(context, appWidgetManager
                , appWidgetIds, ingredientsName);
    }
}
