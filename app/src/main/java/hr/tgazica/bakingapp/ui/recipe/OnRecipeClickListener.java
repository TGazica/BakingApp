package hr.tgazica.bakingapp.ui.recipe;

import hr.tgazica.bakingapp.model.Step;

public interface OnRecipeClickListener {

    void onRecipeStepClicked(Step step);

    void onPreviousStepClicked();

    void onNextStepClicked();

    void onStepsButtonClicked();

}
