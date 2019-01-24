package hr.tgazica.bakingapp.ui.recipe;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import hr.tgazica.bakingapp.R;
import hr.tgazica.bakingapp.model.Recipe;
import hr.tgazica.bakingapp.model.Step;
import hr.tgazica.bakingapp.ui.recipe.details.RecipeDetailsFragment;
import hr.tgazica.bakingapp.ui.recipe.stepsList.RecipeStepsFragment;

public class RecipeActivity extends AppCompatActivity implements OnRecipeListener {

    public static final String RECIPE_EXTRA = "recipe_extra";
    public static final String STEP_EXTRA = "step_extra";
    public static final String IS_TABLET = "is_tablet";

    public static final String LIST_FRAGMENT = "list_fragment";

    public static final String RECIPE_INSTANCE = "recipe_instance";
    public static final String STEP_INSTANCE = "step_instance";

    @Nullable
    @BindView(R.id.recipe_navigation_holder)
    FrameLayout recipeNavigationHolder;
    @Nullable
    @BindView(R.id.recipe_details_holder)
    FrameLayout recipeDetailsHolder;
    @Nullable
    @BindView(R.id.recipe_fragment_holder)
    FrameLayout recipeFragmentHolder;
    @Nullable
    @BindView(R.id.button_steps)
    Button buttonSteps;
    @Nullable
    @BindView(R.id.button_next)
    Button buttonNext;
    @Nullable
    @BindView(R.id.button_previous)
    Button buttonPrevious;

    private Recipe recipe;
    private Step step;
    private boolean isPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        isPhone = recipeFragmentHolder != null;
        initToolbar();

        if (savedInstanceState == null) {

            Intent intent = getIntent();

            if (intent.getSerializableExtra(RECIPE_EXTRA) != null) {
                recipe = (Recipe) intent.getSerializableExtra(RECIPE_EXTRA);
            }

            if (isPhone) {
                initPhoneLayout();
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                initTabletLayout();
            }
        }else {
            recipe = (Recipe) savedInstanceState.getSerializable(RECIPE_INSTANCE);
            step = (Step) savedInstanceState.getSerializable(STEP_INSTANCE);
        }
    }

    private void initToolbar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initPhoneLayout() {
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(RECIPE_EXTRA, recipe);

        recipeStepsFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.recipe_fragment_holder, recipeStepsFragment, LIST_FRAGMENT)
                .commit();
    }

    private void initTabletLayout() {
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(RECIPE_EXTRA, recipe);

        recipeStepsFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.recipe_navigation_holder, recipeStepsFragment)
                .commit();
    }

    @Override
    public void onRecipeStepClicked(Step step) {
        if (this.step == null || this.step.getId() != step.getId()) {
            this.step = step;

            Bundle bundle = new Bundle();
            bundle.putSerializable(RECIPE_EXTRA, recipe);
            bundle.putSerializable(STEP_EXTRA, step);
            bundle.putBoolean(IS_TABLET, !isPhone);

            RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
            recipeDetailsFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(recipeFragmentHolder != null ? R.id.recipe_fragment_holder : R.id.recipe_details_holder, recipeDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onListFragmentResumed() {
        if (recipeFragmentHolder != null) {
            if (buttonSteps != null && buttonNext != null && buttonPrevious != null) {
                buttonNext.setVisibility(View.GONE);
                buttonPrevious.setVisibility(View.GONE);
                buttonSteps.setVisibility(View.GONE);
            }
            step = null;
        }
    }

    @Override
    public void onDetailsFragmentResumed(boolean shouldButtonsShow) {
        if (shouldButtonsShow) {
            if (recipe.getSteps().get(0).getId() == step.getId()) {
                if (buttonSteps != null && buttonNext != null && buttonPrevious != null) {
                    buttonPrevious.setVisibility(View.GONE);
                    buttonSteps.setVisibility(View.VISIBLE);
                    buttonNext.setVisibility(View.VISIBLE);
                }
            } else if (recipe.getSteps().get(recipe.getSteps().size() - 1).getId() == step.getId()) {
                if (buttonSteps != null && buttonNext != null && buttonPrevious != null) {
                    buttonPrevious.setVisibility(View.VISIBLE);
                    buttonSteps.setVisibility(View.VISIBLE);
                    buttonNext.setVisibility(View.GONE);
                }
            } else {
                if (buttonSteps != null && buttonNext != null && buttonPrevious != null) {
                    buttonPrevious.setVisibility(View.VISIBLE);
                    buttonSteps.setVisibility(View.VISIBLE);
                    buttonNext.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (buttonSteps != null && buttonNext != null && buttonPrevious != null) {
                buttonPrevious.setVisibility(View.GONE);
                buttonSteps.setVisibility(View.GONE);
                buttonNext.setVisibility(View.GONE);
            }
        }
    }

    @Optional
    @OnClick(R.id.button_previous)
    public void onPreviousStepClicked() {
        int i = recipe.getSteps().indexOf(step);
        onRecipeStepClicked(recipe.getSteps().get(i - 1));
    }

    @Optional
    @OnClick(R.id.button_next)
    public void onNextStepClicked() {
        int i = recipe.getSteps().indexOf(step);
        onRecipeStepClicked(recipe.getSteps().get(i + 1));
    }

    @Optional
    @OnClick(R.id.button_steps)
    public void onStepsButtonClicked() {
        initPhoneLayout();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(RECIPE_INSTANCE, recipe);
        outState.putSerializable(STEP_INSTANCE, step);
    }
}
