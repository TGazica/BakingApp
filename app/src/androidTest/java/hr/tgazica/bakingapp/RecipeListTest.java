package hr.tgazica.bakingapp;


import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import hr.tgazica.bakingapp.ui.recipeList.RecipeListActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class RecipeListTest {

    @Rule
    public ActivityScenarioRule<RecipeListActivity> activityScenarioRule = new ActivityScenarioRule<>(RecipeListActivity.class);

    @Test
    public void clickRecipeItem_opensRecipeActivity(){

        onView(withId(R.id.recipe_list_holder)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.steps_recipe_name)).check(matches(withText("Brownies")));

    }

}
