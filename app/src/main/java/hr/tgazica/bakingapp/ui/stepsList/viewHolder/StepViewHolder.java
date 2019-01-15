package hr.tgazica.bakingapp.ui.stepsList.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.tgazica.bakingapp.R;
import hr.tgazica.bakingapp.model.Recipe;
import hr.tgazica.bakingapp.model.Step;
import hr.tgazica.bakingapp.ui.recipe.OnRecipeClickListener;

public class StepViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.step_short_description)
    TextView stepShortDescription;

    private OnRecipeClickListener clickListener;

    public StepViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setClickListener(OnRecipeClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setStep(Step step) {
        itemView.setTag(step);
        stepShortDescription.setText(step.getShortDescription());
    }

    public void setIngredientsList(Recipe recipe) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(itemView.getContext().getString(R.string.ingredients)).append("\n");
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            if (i != recipe.getIngredients().size() - 1) {
                stringBuilder.append("  -").append(recipe.getIngredients().get(i).getIngredient()).append(", qty: ").append(recipe.getIngredients().get(i).getQuantity()).append(" ").append(recipe.getIngredients().get(i).getMeasure()).append("\n");
            }
        }
        stepShortDescription.setText(stringBuilder.toString());
    }

    @OnClick
    public void onRecipeStepClicked() {
        if (getAdapterPosition() != 0) {
            Step step = (Step) itemView.getTag();
            clickListener.onRecipeStepClicked(step);
        }
    }
}
