package com.example.android.bakingapp;

import com.google.gson.annotations.SerializedName;

public class Constant {


    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";



    /* Constant values for the names of each respective parameter */
    public static final String RECIPE_TITLE = "recipe_title";
    public static final String STEP_NUMBER = "step_number";
    public static final String RECIPE_ID = "recipe_id";
    public static final String SAVED_STEP_NUMBER= "saved_step_number";
    public static final String RECIPE_LIST= "recipeList";
    public static final String RECIPEID= "recipeId";
    public static final String RECIPE_TABLE= "recipeTable";

    public static final String SHORT_DESCRIPTION = "shortDescription";
    public static final String DESCRIPTION = "description";
    public static final String VIDEO_URL = "videoURL";
    public static final String THUMBNAIL_URL= "thumbnailURL";


    public static final String INGREDIENT= "ingredient";
    public static final String QUANTITY= "quantity";
    public static final String MEASURE= "measure";

    public static final String GLOBAL= "Global";




    // Key received from jSON
    public static final String JSON_INGREDIENTS = "ingredients";
    public static final String JSON_STEPS = "steps";
    public static final String JSON_NAME = "name";
    public static final String JSON_ID = "id";


}
