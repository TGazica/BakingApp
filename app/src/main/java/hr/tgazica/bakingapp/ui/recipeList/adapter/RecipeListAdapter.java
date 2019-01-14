package hr.tgazica.bakingapp.ui.recipeList.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import hr.tgazica.bakingapp.R;
import hr.tgazica.bakingapp.model.Recipe;
import hr.tgazica.bakingapp.ui.recipeList.listener.OnRecipeListClickListener;
import hr.tgazica.bakingapp.ui.recipeList.viewHolder.RecipeListViewHolder;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListViewHolder> {

    private List<Recipe> recipes = new ArrayList<>();
    private OnRecipeListClickListener clickListener;

    public RecipeListAdapter(OnRecipeListClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipeListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.setClickListener(clickListener);
        holder.setRecipeItem(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
