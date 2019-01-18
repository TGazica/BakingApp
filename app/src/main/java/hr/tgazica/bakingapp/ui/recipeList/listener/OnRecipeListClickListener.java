package hr.tgazica.bakingapp.ui.recipeList.listener;

import hr.tgazica.bakingapp.model.Recipe;

public interface OnRecipeListClickListener {

    void onRecipeItemClicked(Recipe recipe);

    void onSetWidget(Recipe recipe);

}
