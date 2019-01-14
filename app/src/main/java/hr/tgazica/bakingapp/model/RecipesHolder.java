package hr.tgazica.bakingapp.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import hr.tgazica.bakingapp.util.RecipeParser;

public class RecipesHolder {

    private List<Recipe> recipes;
    private static RecipesHolder sInstance;

    private RecipesHolder(Context context){
        recipes = new ArrayList<>();
        recipes.addAll(RecipeParser.parseRecipes(context));
    }

    public static RecipesHolder getInstance(Context context){
        if (sInstance ==  null){
            sInstance = new RecipesHolder(context);
        }
        return sInstance;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
