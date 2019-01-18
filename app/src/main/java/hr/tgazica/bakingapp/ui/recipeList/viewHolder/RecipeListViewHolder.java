package hr.tgazica.bakingapp.ui.recipeList.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.tgazica.bakingapp.R;
import hr.tgazica.bakingapp.model.Recipe;
import hr.tgazica.bakingapp.ui.recipeList.listener.OnRecipeListClickListener;

public class RecipeListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.recipe_image)
    ImageView recipeImage;
    @BindView(R.id.recipe_name)
    TextView recipeName;
    @BindView(R.id.servings_number)
    TextView servingsNumber;

    private OnRecipeListClickListener clickListener;

    public RecipeListViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setClickListener(OnRecipeListClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setRecipeItem(Recipe recipe) {
        itemView.setTag(recipe);

        if (!recipe.getImage().equals("")){
            Glide.with(recipeImage).load(recipe.getImage()).into(recipeImage);
        }
        recipeName.setText(recipe.getName());
        servingsNumber.setText(String.valueOf(recipe.getServings()));
    }

    @OnClick
    public void onRecipeItemClicked(){
        Recipe recipe = (Recipe) itemView.getTag();
        clickListener.onRecipeItemClicked(recipe);
    }

    @OnClick(R.id.widget_button)
    public void onwidgetButtonClicked(){
        Recipe recipe = (Recipe) itemView.getTag();
        clickListener.onSetWidget(recipe);
    }

}
