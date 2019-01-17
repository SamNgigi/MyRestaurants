package com.hai.jedi.myrestaurants;

import androidx.test.rule.ActivityTestRule;

import android.view.View;

import com.hai.jedi.myrestaurants.UI.RestaurantsActivity;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.core.IsNot.not;

public class RestaurantsActivityInstrumentationTest {

    // Configuring the right activity before we test
    @Rule
    public ActivityTestRule<RestaurantsActivity> restaurants_activity_test_rule =
            new ActivityTestRule<>(RestaurantsActivity.class);

    // Test to make sure a click on a ListView item returns the correct restaurant name as toast
    @Test
    public void listItemClickDisplaysToastWithCorrectRestaurantTest() {

        // Setting up the restaurant name we will use to test the toast.
        String restaurant_name = "Kibandanski";


        View activity_decor_view = restaurants_activity_test_rule
                                   .getActivity()
                                   .getWindow()
                                   .getDecorView();

        /**
         * The gist here is that we are instructing Espresso to check that clicking on the first
         * item atPosition(0) in our ListView results in a Toast that displays "Kibandanski.
         *
         * To interact with the data in the adapter we MUST use the onData() method rather than the
         * onView()
         * */
        onData(anything()).inAdapterView(withId(R.id.listView))
                          .atPosition(0)
                          .perform(click());

        /**
         * After we grab data after the click from the adapter we then use the onView method
         *
         * Here is where we check, that after the click, the toast itself is displaying the correct
         * restaurant and not anything else.
         * */
        onView(withText(restaurant_name))
                          .inRoot(withDecorView(not(activity_decor_view)))
                          .check(matches(withText(restaurant_name)));
    }
}
