package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.model.Ingredient;
import com.example.android.bakingapp.R;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {




    private LayoutInflater inflater;

    private List<Ingredient> ingredientList;


    public IngredientAdapter(Context context, List<Ingredient> ingredients) {

        this.inflater = LayoutInflater.from(context);
        this.ingredientList = ingredients;

    }


    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingredient_list_item, viewGroup, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientViewHolder holder, int position) {

        // bind the data to create thumbnails in the main page

        holder.ingredientNameTv.setText(ingredientList.get(position).getIngredientsName()
                + ":"

        );
        holder.ingredientMeasureTv.setText(ingredientList.get(position).getIngredientsQuantity()
                + ingredientList.get(position).getIngredientsMeasure());

    }

    @Override
    public int getItemCount() {


        return ingredientList==null?0:ingredientList.size();


    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientNameTv;
        TextView ingredientMeasureTv;


        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientNameTv = itemView.findViewById(R.id.ingredientNameTv);
            ingredientMeasureTv = itemView.findViewById(R.id.ingredientMeasureTv);




        }


    }


}
