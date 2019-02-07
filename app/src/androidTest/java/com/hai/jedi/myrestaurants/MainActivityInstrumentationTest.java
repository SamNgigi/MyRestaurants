package com.hai.jedi.myrestaurants;

//import android.content.Intent;
import com.hai.jedi.myrestaurants.UI.MainActivity;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(JUnit4.class)
public class MainActivityInstrumentationTest {
    // String that we will use to test our EditText view and the next activity
    public String test_string = "Testing testing 123";

    // The code with the @Rule annotation tells our device which activity to launch
    // before the next test.
    @Rule
    public ActivityTestRule<MainActivity> activity_test_rule
            = new ActivityTestRule<>(MainActivity.class, false, true);

    @Test
    public void validateEditText(){
        /**
         * In other words
         *
         * On the activity we are testing, go to the view with the id of "locationInfo"
         * which happens to be our EditTextView.
         *
         * Type the text Testing testing 123. Then check if the EditText has matching text.
         * */
        onView(withId(R.id.editLocationText)).perform(clearText(),typeText(test_string))
                                         .check(matches(withText("Testing testing 123")));
    }

    @Test
    public void locationSentToRestaurantsActivityTest(){
        // Writing test_string on our EditText text input
        onView(withId(R.id.editLocationText)).perform(typeText(test_string));

        // Submit text by clicking the button
        onView(withId(R.id.findRestaurantsButton)).perform(click());

        // Check if the restaurant_activity has this exact text
//        onView(withId(R.id.locationInfo)).check(matches(
//                withText("Here are all the restaurants near: " + test_string)
//        ));
    }

}
