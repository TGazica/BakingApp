package hr.tgazica.bakingapp.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

import hr.tgazica.bakingapp.model.Recipe;

public class RecipeParser {

    public static List<Recipe> parseRecipes(Context context, String string) {

        Gson gson = new Gson();

        Type listType = new TypeToken<List<Recipe>>(){}.getType();
        List<Recipe> recipes = gson.fromJson(string, listType);

        return recipes;
    }

}
