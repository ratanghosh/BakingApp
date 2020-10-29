package com.example.android.bakingapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;



import android.os.Bundle;


import com.example.android.bakingapp.R;
import com.example.android.bakingapp.ui.StepsFragment;



public class StepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        getSupportActionBar().setTitle(getIntent().getStringExtra("recipe_title"));

        if (savedInstanceState == null) {
            StepsFragment stepsFragment = new StepsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.step_container, stepsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}