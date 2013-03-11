package healtycaresystem;

import java.util.ArrayList;

public class Recipe {
    private int recipeID;
    private String name;
    private int price;
    private ArrayList<Ingredient> ingredients;

    public Recipe(int recipeID, String name, int price){
        this.recipeID = recipeID;
        this.name = name;
        this.price = price;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    public ArrayList<Ingredient> getIngredients(){
        ArrayList<Ingredient> res = new ArrayList<>();
        for(Ingredient i : ingredients){
            res.add(new Ingredient(i.getIngredientID(), i.getName(), i.getInStock(), i.getOrderMin()));
        }
        return res;
    }
}
