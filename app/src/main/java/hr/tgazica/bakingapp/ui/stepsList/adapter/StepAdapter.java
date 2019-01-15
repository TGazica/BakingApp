package hr.tgazica.bakingapp.ui.stepsList.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hr.tgazica.bakingapp.R;
import hr.tgazica.bakingapp.model.Recipe;
import hr.tgazica.bakingapp.model.Step;
import hr.tgazica.bakingapp.ui.recipe.OnRecipeClickListener;
import hr.tgazica.bakingapp.ui.stepsList.viewHolder.StepViewHolder;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {

    List<Object> steps = new ArrayList<>();
    private OnRecipeClickListener clickListener;

    public StepAdapter(OnRecipeClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setSteps(Recipe recipe) {
        this.steps.clear();
        this.steps.add(recipe);
        this.steps.addAll(recipe.getSteps());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StepViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        if (position == 0){
            Recipe recipe = (Recipe) steps.get(position);
            holder.setClickListener(clickListener);
            holder.setIngredientsList(recipe);
        }else {
            Step step = (Step) steps.get(position);
            holder.setClickListener(clickListener);
            holder.setStep(step);
        }
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }


}
