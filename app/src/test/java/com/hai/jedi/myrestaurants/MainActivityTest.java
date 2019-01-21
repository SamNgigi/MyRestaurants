package com.hai.jedi.myrestaurants;

import android.content.Intent;

import android.widget.TextView;

import com.hai.jedi.myrestaurants.UI.MainActivity;
import com.hai.jedi.myrestaurants.UI.RestaurantsListActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

// It would seem robolectric 4.1 does not need the @Config annotation
@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {
    private MainActivity activity;

    @Before
    public void setUp() throws Exception{
        activity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void validateTextViewContent(){
        TextView appNameTextView = activity.findViewById(R.id.sample_text);
        assertEquals("This is awesome! Pretty Dope!", appNameTextView.getText().toString());
    }

    @Test
    public void restaurantActivityStartedTest() {
        // Click the find restaurant button on the main activity.
        activity.findViewById(R.id.findRestaurantsButton).performClick();

        // Creating the activity that we expect.
        Intent expected_intent = new Intent(activity, RestaurantsListActivity.class);

        /**
         * Shadows are classes that modify or extend the behaviour of a class in
         * the Android SDK.
         *
         * Robolectric looks for Shadow classes that correspond with any Android
         * classes that are run as part
         * */
        ShadowActivity shadow_activity = org.robolectric.Shadows.shadowOf(activity);

        // Assigning the actual result of our test to actual intent
        Intent actual_intent = shadow_activity.getNextStartedActivity();

        // Checking if our expected_intent matches actual_intent
        assertTrue(actual_intent.filterEquals(expected_intent));
    }
}
