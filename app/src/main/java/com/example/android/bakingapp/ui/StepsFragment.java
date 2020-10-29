package com.example.android.bakingapp.ui;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.android.bakingapp.Constant;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.database.RecipeDatabase;
import com.example.android.bakingapp.database.RecipeExecutor;
import com.example.android.bakingapp.model.Recipe;
import com.example.android.bakingapp.model.RecipeStep;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.bakingapp.activity.DetailsActivity.mTwoPane;


public class StepsFragment extends Fragment {

    // String for log message
    public static final String TAG = StepsFragment.class.getName();


    private String stepsString;

    // for steps description
    ArrayList<String> descriptionArrayList = new ArrayList<>();
    // for steps videoUrl
    ArrayList<String> videoUrlArrayList = new ArrayList<String>();

    List<RecipeStep> stepsList;

    Button nextButton;
    Button previousButton;
    TextView stepNumberTextView;


    private RecipeDatabase mDb;

    private TextView descriptionTextView;

    //private VideoView stepVideoView;
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private String videoUrl;






    private int stepNumber;

    private int stepNumberSaved;

    public StepsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);


        descriptionTextView = (TextView) rootView.findViewById(R.id.stepDescription);


        // Initialize the player view.
        playerView =  rootView.findViewById(R.id.stepVideoPlayerView);



        mDb = RecipeDatabase.getInstance(getActivity().getApplicationContext());

        nextButton = (Button) rootView.findViewById(R.id.nextButton);
        previousButton = (Button) rootView.findViewById(R.id.previousButton);
        stepNumberTextView = (TextView) rootView.findViewById(R.id.step_number_textView);






        // UI for Phone start

        if (!mTwoPane) {

            // check step if number saved in the InstanceState

            if (savedInstanceState != null) {
                stepNumber = savedInstanceState.getInt(Constant.SAVED_STEP_NUMBER);
                videoUrl = savedInstanceState.getString("video_url");

            } else {

                stepNumber = getActivity().getIntent().getIntExtra(Constant.STEP_NUMBER, 0);
            }

            if (savedInstanceState == null){
                videoUrl = getActivity().getIntent().getStringExtra("video_url");
            }

            int recipeId = getActivity().getIntent().getIntExtra(Constant.RECIPE_ID, 0);

            setStepDetailView(recipeId);

            buttonAction();




        }// UI for Phone finish


        // UI for TAB start
        else {

            // retrieve the recipe Id

            int recipeId = getActivity().getIntent().getIntExtra(Constant.RECIPE_ID, 0);


            // check step if number saved in the InstanceState

            if (savedInstanceState != null) {
                stepNumber = savedInstanceState.getInt(Constant.SAVED_STEP_NUMBER);
                videoUrl = savedInstanceState.getString("video_url");
            } else  {
                videoUrl = getActivity().getIntent().getStringExtra("video_url");
            }

            setStepDetailView(recipeId);



            buttonAction();


        } // UI for TAB finish


        return rootView;
    }




    public void buttonAction() {
        final int[] i = {stepNumber};

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i[0] < (descriptionArrayList.size() - 1)) {
                    i[0]++;

                    descriptionTextView.setText(descriptionArrayList.get(i[0]));
                    stepNumberTextView.setText(getString(R.string.step)+" " + i[0] +" " + getString(R.string.of) +" "+ (stepsList.size() - 1));

                    if (videoUrlArrayList.get(i[0]).isEmpty()) {

                        Toast.makeText(getContext(), R.string.videoNotAvailable, Toast.LENGTH_SHORT).show();

                    }
                        releasePlayer();
                        playbackPosition = 0;
                        videoUrl = videoUrlArrayList.get(i[0]);
                        initializePlayer();


                    stepNumberSaved = i[0];

                }

            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i[0] > 0) {
                    i[0]--;


                    descriptionTextView.setText(descriptionArrayList.get(i[0]));
                    stepNumberTextView.setText(getString(R.string.step)+" " + i[0]+" "  + getString(R.string.of) +" "+ (stepsList.size() - 1));

                    if (videoUrlArrayList.get(i[0]).isEmpty()) {

                        Toast.makeText(getContext(), R.string.videoNotAvailable, Toast.LENGTH_SHORT).show();

                    }

                    releasePlayer();
                    playbackPosition = 0;
                    videoUrl = videoUrlArrayList.get(i[0]);
                    initializePlayer();

                    stepNumberSaved = i[0];

                }

            }
        });

        stepNumberSaved = i[0];

    }






    public void setStepIndex(int index) {
        stepNumber = index;

    }



    public void setStepDetailView(final int recipeId) {
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

                        descriptionTextView.setText(descriptionArrayList.get(stepNumber));
                        stepNumberTextView.setText(getString(R.string.step)+" " + stepNumber+" "  + getString(R.string.of)+" " + (stepsList.size() - 1));

                        if (videoUrlArrayList.get(stepNumber).isEmpty() ){

                            Toast.makeText(getContext(), R.string.videoNotAvailable, Toast.LENGTH_SHORT).show();

                        }

                        if(mTwoPane){
                            videoUrl = videoUrlArrayList.get(stepNumber);
                            initializePlayer();
                        }


                        stepNumberSaved = stepNumber;


                    }
                });
            }
        });

        stepNumberSaved = stepNumber;



    }


    // Media-player set-up with exo-player


    private void initializePlayer() {

        player = new SimpleExoPlayer.Builder(getContext()).build();
        playerView.setPlayer(player);


        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(videoUrl));
        player.setMediaItem(mediaItem);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23 ) {

            if((videoUrl !=null) ) {
                initializePlayer();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)  ) {

            if((videoUrl !=null) ) {
                initializePlayer();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    /**
     * Release ExoPlayer.
     */


    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }



    /* Save the current step of the recipe */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putInt(Constant.SAVED_STEP_NUMBER, stepNumberSaved);
        outState.putString("video_url", videoUrl);
    }



}





