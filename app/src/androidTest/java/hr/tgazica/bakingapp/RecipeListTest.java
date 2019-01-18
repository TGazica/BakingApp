package hr.tgazica.bakingapp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import hr.tgazica.bakingapp.ui.recipeList.RecipeListActivity;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeListTest {

    

    private IdlingResource idlingResource;

    @Before
    public void registerIdlingResource(){
    }

    @Test
    public void clickRecipeListItem_openRecipeStepsListActivity(){
        onData(anything()).inAdapterView(withId(R.id.recipe_list_holder)).atPosition(1).perform(click());

        onData(anything()).inAdapterView(withId(R.id.steps_list)).atPosition(4).onChildView(withId(R.id.step_short_description)).check(matches(withText("Prep the cookie crust.")));
    }

}
