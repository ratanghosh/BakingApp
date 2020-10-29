package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {


    private LayoutInflater inflater;
    private List<Recipe> recipeList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public RecipeAdapter(Context context, List<Recipe> recipes) {
        this.inflater = LayoutInflater.from(context);
        this.recipeList = recipes;

    }

    public void setTasks(List<Recipe> recipeListCurrent) {
        recipeList = recipeListCurrent;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        Recipe recipe = recipeList.get(position);
        // bind the data to create thumbnails in the main page

        holder.recipeNameTV.setText(recipeList.get(position).getRecipeName());


    }

    @Override
    public int getItemCount() {

        return recipeList==null?0:recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipeNameTV;
        //RecyclerView ingredientRecyclerView;


        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            recipeNameTV = itemView.findViewById(R.id.recipeName);
            //ingredientRecyclerView = itemView.findViewById(R.id.ingredientsRecyclerView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }

                }
            });


        }


    }


}
