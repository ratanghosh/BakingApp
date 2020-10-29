package com.example.android.bakingapp.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import android.os.Bundle;


import com.example.android.bakingapp.Constant;
import com.example.android.bakingapp.DataBinderMapperImpl;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.ActivityDetailsBinding;
import com.example.android.bakingapp.ui.DetailsFragment;
import com.example.android.bakingapp.ui.StepsFragment;



public class DetailsActivity extends AppCompatActivity implements DetailsFragment.OnStepClickListener {

    ActivityDetailsBinding activityDetailsBinding;

    // Track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
       // activityDetailsBinding = DataBindingUtil.setContentView(this,R.layout.activity_details);

        getSupportActionBar().setTitle(getIntent().getStringExtra(Constant.RECIPE_TITLE));

        // Determine if you're creating a two-pane or single-pane display
        if (findViewById(R.id.details_fragment_view)!= null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            if (savedInstanceState == null) {


                DetailsFragment detailsFragment = new DetailsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.details_fragment_view, detailsFragment)
                        .commit();


                StepsFragment stepsFragment = new StepsFragment();
                FragmentManager stepsFragmentManager = getSupportFragmentManager();

                stepsFragmentManager.beginTransaction()
                        .add(R.id.step_fragment_view, stepsFragment)
                        .commit();
            }


        } else {


            if (savedInstanceState == null) {
                DetailsFragment detailsFragment = new DetailsFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.details_container, detailsFragment)
                        .commit();


            }
        }

    }

    @Override
    public void onStepSelected(int position) {


        if (mTwoPane) {


            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setStepIndex(position);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_fragment_view, stepsFragment)
                    .commit();


        }


    }
}





