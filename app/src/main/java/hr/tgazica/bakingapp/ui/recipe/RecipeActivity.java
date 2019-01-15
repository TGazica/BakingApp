package hr.tgazica.bakingapp.ui.recipe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import hr.tgazica.bakingapp.R;
import hr.tgazica.bakingapp.model.Recipe;
import hr.tgazica.bakingapp.model.Step;
import hr.tgazica.bakingapp.ui.details.RecipeDetailsFragment;
import hr.tgazica.bakingapp.ui.stepsList.RecipeStepsFragment;

public class RecipeActivity extends AppCompatActivity implements OnRecipeClickListener {

    public static final String RECIPE_EXTRA = "recipe_extra";

    @Nullable
    @BindView(R.id.recipe_navigation_holder)
    FrameLayout recipeNavigationHolder;
    @Nullable
    @BindView(R.id.recipe_details_holder)
    FrameLayout recipeDetailsHolder;
    @Nullable
    @BindView(R.id.recipe_fragment_holder)
    FrameLayout recipeFragmentHolder;

    private Recipe recipe;
    private Step step;
    private boolean isPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent.getSerializableExtra(RECIPE_EXTRA) != null) {
            recipe = (Recipe) intent.getSerializableExtra(RECIPE_EXTRA);
        }

        isPhone = recipeFragmentHolder != null;

        if (isPhone) {
            initPhoneLayout();
        } else {
            initTabletLayout();
        }
    }

    private void initPhoneLayout() {
        RecipeStepsFragment recipeStepsFragment = new RecipeStepsFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(RECIPE_EXTRA, recipe);

        recipeStepsFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.recipe_fragment_holder, recipeStepsFragment)
                .addToBackStack(null)
                .commit();
    }

    private void initTabletLayout() {

    }

    @Override
    public void onRecipeStepClicked(Step step) {
        this.step = step;

        Bundle bundle = new Bundle();
        bundle.putSerializable(RECIPE_EXTRA, step);

        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        recipeDetailsFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.recipe_fragment_holder, recipeDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onPreviousStepClicked() {

    }

    @Override
    public void onNextStepClicked() {

    }

    @Override
    public void onStepsButtonClicked() {

    }
}
