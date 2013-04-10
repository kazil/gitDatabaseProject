package healtycaresystem;

import java.util.ArrayList;

public class Recipe {
    private int recipeID;
    private String name;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    public Recipe(int recipeID, String name){
        this.recipeID = recipeID;
        this.name = name;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public String getName() {
        return name;
    }

    public void addIngredient(Ingredient ingr){
        ingredients.add(ingr);
    }

    public ArrayList<Ingredient> getIngredients(){
        ArrayList<Ingredient> res = new ArrayList<>();
        for(Ingredient i : ingredients){
            res.add(new Ingredient(i.getIngredientID(), i.getName(), i.getInStock(), i.getOrderMin(), i.getPrice(), i.getAmount()));
        }
        return res;
    }

    public String toString(){
        String out = "";
        for(Ingredient i : ingredients){
            out += i.toString() + "\n";
        }
        return "recipieID = " + recipeID + ", name = " + name + "\nIngredients:\n" + out;
    }
}
