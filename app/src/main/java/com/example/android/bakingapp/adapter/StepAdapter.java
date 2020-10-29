package com.example.android.bakingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.model.RecipeStep;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {


    private LayoutInflater inflater;

    private List<RecipeStep> stepsList;

    private StepAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position );
    }

    public void setOnItemClickListener(StepAdapter.OnItemClickListener listener) {
        mListener = listener;
    }


    public StepAdapter(Context context, List<RecipeStep> steps) {

        this.inflater = LayoutInflater.from(context);
        this.stepsList = steps;

    }


    @NonNull
    @Override
    public StepAdapter.StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.step_list_item, viewGroup, false);
        return new StepAdapter.StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepAdapter.StepViewHolder holder, int position) {

        // bind the data to create thumbnails in the main page

        holder.stepShortDescriptionTv.setText(stepsList.get(position).getShortDescription());


    }

    @Override
    public int getItemCount() {


        return stepsList==null?0:stepsList.size();


    }

    public class StepViewHolder extends RecyclerView.ViewHolder {

        TextView stepShortDescriptionTv;


        public StepViewHolder(@NonNull View itemView) {
            super(itemView);

            stepShortDescriptionTv = itemView.findViewById(R.id.stepShortDescription);

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

