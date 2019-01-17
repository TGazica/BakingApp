package hr.tgazica.bakingapp.ui.recipe;

import hr.tgazica.bakingapp.model.Step;

public interface OnRecipeListener {

    void onRecipeStepClicked(Step step);

    void onListFragmentResumed();

    void onDetailsFragmentResumed(boolean shouldButtonsShow);

}
