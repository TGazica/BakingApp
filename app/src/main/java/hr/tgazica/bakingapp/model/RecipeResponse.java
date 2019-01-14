package hr.tgazica.bakingapp.model;

public class RecipeResponse {

    private Recipe[] recipes;

    public RecipeResponse() {
    }

    public RecipeResponse(Recipe[] recipes) {
        this.recipes = recipes;
    }

    public Recipe[] getRecipes() {
        return recipes;
    }

    public void setRecipes(Recipe[] recipes) {
        this.recipes = recipes;
    }
}
