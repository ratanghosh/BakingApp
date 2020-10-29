package com.example.android.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.android.bakingapp.Constant;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.activity.DetailsActivity;
import com.example.android.bakingapp.activity.StepActivity;
import com.example.android.bakingapp.adapter.IngredientAdapter;
import com.example.android.bakingapp.adapter.StepAdapter;
import com.example.android.bakingapp.database.RecipeDatabase;
import com.example.android.bakingapp.database.RecipeExecutor;
import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeStep;
import com.example.android.bakingapp.service.IngredientsWidgetDisplayService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.activity.DetailsActivity.mTwoPane;


public class DetailsFragment extends Fragment implements StepAdapter.OnItemClickListener {

    // Define a new interface OnImageClickListener that triggers a callback in the host activity
    OnStepClickListener mCallback;

    // OnImageClickListener interface, calls a method in the host activity named onImageSelected
    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + getString(R.string.onClickimplement));
        }
    }


    List<Ingredient> ingredientList;
    List<RecipeStep> stepsList;

    // for steps description
    ArrayList<String> descriptionArrayList = new ArrayList<String>();

    // for steps videoUrl
    ArrayList<String> videoUrlArrayList = new ArrayList<String>();

    IngredientAdapter ingredientAdapter;
    StepAdapter stepAdapter;


    private int recipeId;

    private String ingredientsString;
    private String stepsString;

    private RecipeDatabase mDb;

    private RecyclerView ingredientsRecyclerView;
    private RecyclerView stepsRecyclerView;

    private String recipeTitle;


    private DetailsActivity detailsActivity;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.ingredientsRecyclerView);
        stepsRecyclerView = (RecyclerView) rootView.findViewById(R.id.stepsRecyclerView);


        mDb = RecipeDatabase.getInstance(getActivity().getApplicationContext());

        recipeTitle = getActivity().getIntent().getStringExtra(Constant.RECIPE_TITLE);
        ingredientList = new ArrayList<>();
        stepsList = new ArrayList<>();

        recipeId = getActivity().getIntent().getIntExtra(Constant.RECIPE_ID, 0);


        setIngredientsToUI(recipeId);

        setStepsToUI(recipeId);


        return rootView;

    }


    private void setIngredientsToUI(final int recipeId) {
        RecipeExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Recipe> recipeList = mDb.recipeDao().loadAllRecipe();

                ingredientsString = recipeList.get(recipeId).getIngredientsString();


                Gson gson = new Gson();
                Type ingredientType = new TypeToken<ArrayList<Ingredient>>() {
                }.getType();
                ingredientList = gson.fromJson(ingredientsString, ingredientType);

                StringBuilder ingredientForWidget = new StringBuilder();
                ;

                for (int i = 0; i < ingredientList.size(); i++) {

                    String currentIngredient = getString(R.string.hash) + ingredientList.get(i).getIngredientsName()
                            + getString(R.string.mark)
                            + ingredientList.get(i).getIngredientsQuantity()
                            + ingredientList.get(i).getIngredientsMeasure()
                            + "\n";

                    ingredientForWidget.append(currentIngredient);

                }
                // get the recipe name and ingredients and update the recipe widget
                IngredientsWidgetDisplayService.updateRecipeWidgets(getContext(), ingredientForWidget.toString());


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        ingredientsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1,
                                RecyclerView.VERTICAL, false));
                        ingredientAdapter = new IngredientAdapter(getContext(), ingredientList);
                        ingredientsRecyclerView.setAdapter(ingredientAdapter);


                    }
                });
            }
        });


    }

    private void setStepsToUI(final int recipeId) {
        RecipeExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<Recipe> recipeList = mDb.recipeDao().loadAllRecipe();

                stepsString = recipeList.get(recipeId).getStepsString();

                Gson gson = new Gson();
                Type stepType = new TypeToken<ArrayList<RecipeStep>>() {
                }.getType();
                stepsList = gson.fromJson(stepsString, stepType);

                for (int i = 0; i < stepsList.size(); i++) {

                    String currentDescription = stepsList.get(i).getDescription();
                    descriptionArrayList.add(currentDescription);

                    String currentVideoUrl = stepsList.get(i).getVideoUrl();
                    videoUrlArrayList.add(currentVideoUrl);

                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        stepsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1,
                                RecyclerView.VERTICAL, false));
                        stepAdapter = new StepAdapter(getContext(), stepsList);
                        stepsRecyclerView.setAdapter(stepAdapter);
                        stepAdapter.setOnItemClickListener(DetailsFragment.this);


                    }
                });
            }
        });


    }


    @Override
    public void onItemClick(int position) {

        if (mTwoPane) {
            //Toast.makeText(getContext(), " Tab Position clicked = " + position, Toast.LENGTH_SHORT).show();
            mCallback.onStepSelected(position );
        } else {
            //Toast.makeText(getContext(), " Phone Position clicked = " + position, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getContext(), StepActivity.class);
            intent.putExtra(Constant.STEP_NUMBER, position);
            intent.putExtra(Constant.RECIPE_ID, recipeId);
            intent.putExtra(Constant.RECIPE_TITLE, recipeTitle);
            intent.putExtra("video_url", videoUrlArrayList.get(position));
            startActivity(intent);
        }
    }


}