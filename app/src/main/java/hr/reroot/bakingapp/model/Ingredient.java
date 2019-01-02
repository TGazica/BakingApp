package hr.reroot.bakingapp.model;

public class Ingredient {

    private double quantity;
    private String measeure;
    private String ingredient;

    public Ingredient() {
    }

    public Ingredient(double quantity, String measeure, String ingredient) {
        this.quantity = quantity;
        this.measeure = measeure;
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeaseure() {
        return measeure;
    }

    public void setMeaseure(String measeure) {
        this.measeure = measeure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
