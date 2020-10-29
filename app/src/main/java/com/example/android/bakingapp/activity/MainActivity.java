package com.example.android.bakingapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.bakingapp.Constant;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapter.RecipeAdapter;
import com.example.android.bakingapp.database.RecipeDatabase;
import com.example.android.bakingapp.database.RecipeExecutor;
import com.example.android.bakingapp.databinding.ActivityMainBinding;
import com.example.android.bakingapp.model.Recipe;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnItemClickListener {

    // String for log message
    public static final String LOG_TAG = MainActivity.class.getName();

    ActivityMainBinding activityMainBinding;


    RecipeAdapter recipeAdapter;

    private RecipeDatabase mDb;

    public List<Recipe> recipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mDb = RecipeDatabase.getInstance(getApplicationContext());


        extractRecipeDataFromServer(Constant.BASE_URL);
        retrieveRecipes();

    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveRecipes();

    }

    private void extractRecipeDataFromServer(String recipeUrl) {

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, recipeUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray recipeArray) {


                        for (int i = 0; i < recipeArray.length(); i++) {

                            try {
                                // data obtain for current movie
                                JSONObject currentRecipe = recipeArray.getJSONObject(i);

                                String recipeId = currentRecipe.getString(Constant.JSON_ID);
                                String recipeName = currentRecipe.getString(Constant.JSON_NAME);
                                String ingredientsString = currentRecipe.getJSONArray(Constant.JSON_INGREDIENTS).toString();
                                String stepsString = currentRecipe.getJSONArray(Constant.JSON_STEPS).toString();

                                final Recipe recipe = new Recipe(recipeId,
                                        recipeName, ingredientsString, stepsString);

                                RecipeExecutor.getInstance().diskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDb.recipeDao().insertRecipe(recipe);


                                    }
                                });


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    } // end of public void onResponse(JSONObject response)
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, R.string.on_response_error + error.getMessage());

                if (error instanceof TimeoutError
                        || error instanceof AuthFailureError || error instanceof ServerError
                        || error instanceof NetworkError || error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), R.string.network_error, Toast.LENGTH_SHORT).show();
                }


            }
        });

        queue.add(jsonArrayRequest);

    }


    private void retrieveRecipes() {
        RecipeExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                recipeList = mDb.recipeDao().loadAllRecipe();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        activityMainBinding.recipeRecyclerView.setLayoutManager(new GridLayoutManager
                                (MainActivity.this, calculateNoOfColumns(MainActivity.this)
                                        , RecyclerView.VERTICAL, false));
                        recipeAdapter = new RecipeAdapter(MainActivity.this, recipeList);
                        activityMainBinding.recipeRecyclerView.setAdapter(recipeAdapter);
                        recipeAdapter.setOnItemClickListener(MainActivity.this);


                    }
                });
            }
        });


    }

    // calculate the number of column according to scree size
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if (noOfColumns < 1)
            noOfColumns = 1;
        return noOfColumns;
    }


    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(this, DetailsActivity.class);

        intent.putExtra(Constant.RECIPE_ID, position);
        intent.putExtra(Constant.RECIPE_TITLE, recipeList.get(position).getRecipeName());


        startActivity(intent);

    }


}