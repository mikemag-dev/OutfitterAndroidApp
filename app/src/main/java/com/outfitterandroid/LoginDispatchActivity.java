package com.outfitterandroid;

import com.parse.ui.ParseLoginDispatchActivity;

/**
 * Created by MaguireM on 2/17/15.
 * intermediary class to handle login details from Parse
 */
public class LoginDispatchActivity extends ParseLoginDispatchActivity {
    @Override
    protected Class<?> getTargetClass() {
        return ProfileActivity.class;
    }

}
