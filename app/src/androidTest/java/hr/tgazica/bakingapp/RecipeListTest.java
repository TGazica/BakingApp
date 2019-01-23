package hr.tgazica.bakingapp;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import hr.tgazica.bakingapp.ui.recipeList.RecipeListActivity;

import static androidx.test.espresso.Espresso.onIdle;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class RecipeListTest {

    @Rule
    public ActivityScenarioRule<RecipeListActivity> activityScenarioRule = new ActivityScenarioRule<>(RecipeListActivity.class);

    @Before
    public void registerIdlingResources(){
    }

    @Test
    public void clickRecipeItem_opensRecipeActivity() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipe_list_holder)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.steps_recipe_name)).check(matches(withText("Brownies")));

    }

}
