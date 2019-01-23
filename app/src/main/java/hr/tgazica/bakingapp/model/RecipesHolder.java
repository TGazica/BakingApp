package hr.tgazica.bakingapp.model;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import hr.tgazica.bakingapp.networking.ApiInteractor;
import hr.tgazica.bakingapp.util.RecipeParser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesHolder {

    private Context context;
    private List<Recipe> recipes;
    private static RecipesHolder sInstance;
    private OnDataReadyListener onDataReadyListener;

    private RecipesHolder(Context context, ApiInteractor apiInteractor){
        this.context = context;
        apiInteractor.getBakingData(getBakingDataCallback());
    }

    private Callback<JsonArray> getBakingDataCallback() {
        return new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.body() != null) {
                    recipes = new ArrayList<>();
                    recipes = RecipeParser.parseRecipes(context, response.body().toString());
                    onDataReadyListener.OnDataReady(recipes);
                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                onDataReadyListener.onDataFailed();
            }
        };
    }

    public static RecipesHolder getInstance(Context context, ApiInteractor apiInteractor){
        if (sInstance ==  null){
            sInstance = new RecipesHolder(context, apiInteractor);
        }
        return sInstance;
    }

    public void setListener(OnDataReadyListener onDataReadyListener){
        this.onDataReadyListener = onDataReadyListener;
        if (recipes != null){
            onDataReadyListener.OnDataReady(recipes);
        }
    }

    public void removeListener(){
        onDataReadyListener = null;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public interface OnDataReadyListener{
        void OnDataReady(List<Recipe> recipes);

        void onDataFailed();
    }
}
