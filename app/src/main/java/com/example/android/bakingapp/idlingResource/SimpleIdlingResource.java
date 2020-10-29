package com.example.android.bakingapp.idlingResource;


import androidx.test.espresso.idling.CountingIdlingResource;

import com.example.android.bakingapp.Constant;


public class SimpleIdlingResource {

    private final static String Resource = Constant.GLOBAL;

    public static CountingIdlingResource resource = new CountingIdlingResource(Resource);


}
