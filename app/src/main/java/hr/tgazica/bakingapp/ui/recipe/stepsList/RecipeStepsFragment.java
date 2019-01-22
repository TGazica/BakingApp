package hr.tgazica.bakingapp.ui.recipe.stepsList;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import hr.tgazica.bakingapp.R;
import hr.tgazica.bakingapp.model.Recipe;
import hr.tgazica.bakingapp.ui.recipe.OnRecipeListener;
import hr.tgazica.bakingapp.ui.recipe.RecipeActivity;
import hr.tgazica.bakingapp.ui.recipe.stepsList.adapter.StepAdapter;

public class RecipeStepsFragment extends Fragment {

    @BindView(R.id.steps_list)
    RecyclerView stepsList;
    @BindView(R.id.steps_recipe_name)
    TextView stepsRecipeName;

    private OnRecipeListener recipeClickListener;
    private Recipe recipe;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        recipeClickListener = (OnRecipeListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_steps_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable(RecipeActivity.RECIPE_EXTRA);
            if (recipe != null && recipe.getName() != null){
                stepsRecipeName.setText(recipe.getName());

            }
        }

        StepAdapter stepAdapter = new StepAdapter(recipeClickListener);

        stepsList.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsList.setAdapter(stepAdapter);

        stepAdapter.setSteps(recipe);

    }

    @Override
    public void onResume() {
        super.onResume();
        recipeClickListener.onListFragmentResumed();
    }
}
