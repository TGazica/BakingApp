package hr.tgazica.bakingapp.ui.recipeList;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import hr.tgazica.bakingapp.R;
import hr.tgazica.bakingapp.model.Recipe;
import hr.tgazica.bakingapp.model.RecipesHolder;
import hr.tgazica.bakingapp.ui.recipe.RecipeActivity;
import hr.tgazica.bakingapp.ui.recipeList.adapter.RecipeListAdapter;
import hr.tgazica.bakingapp.ui.recipeList.listener.OnRecipeListClickListener;

public class RecipeListActivity extends AppCompatActivity implements OnRecipeListClickListener {

    @Nullable
    @BindView(R.id.recipe_list_holder)
    RecyclerView recipeListHolder;

    @Nullable
    @BindView(R.id.recipe_list_holder_tablet)
    RecyclerView recipeListHolderTablet;

    private RecipeListAdapter recipeListAdapter;
    private RecipesHolder recipesHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recipesHolder = RecipesHolder.getInstance(this);

        recipeListAdapter = new RecipeListAdapter(this);

        if (recipeListHolder != null) {
            recipeListHolder.setLayoutManager(new LinearLayoutManager(this));
            recipeListHolder.setAdapter(recipeListAdapter);
        } else {
            if (recipeListHolderTablet != null) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                recipeListHolderTablet.setLayoutManager(new GridLayoutManager(this, 3));
                recipeListHolderTablet.setAdapter(recipeListAdapter);
            }
        }

        recipeListAdapter.setRecipes(recipesHolder.getRecipes());
    }

    @Override
    public void onRecipeItemClicked(Recipe recipe) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(RecipeActivity.RECIPE_EXTRA, recipe);
        startActivity(intent);
    }
}
