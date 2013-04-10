package healtycaresystem;

import com.sun.xml.internal.ws.developer.EPRRecipe;

import java.util.ArrayList;

public class Package {
    private int packageID;
    private String packageName;
    private ArrayList<Recipe> recipes = new ArrayList<>();

    public Package(int packageID, String packageName){
        this.packageID = packageID;
        this.packageName = packageName;
    }

    public int getPackageID() {
        return packageID;
    }

    public String getPackageName() {
        return packageName;
    }

    public void addRecipie(Recipe resipie){
        recipes.add(resipie);
    }

    public ArrayList<Recipe> getRecipes() {
        ArrayList<Recipe> res = new ArrayList<>();
        for(Recipe r : recipes){
            res.add(new Recipe(r.getRecipeID(), r.getName()));
        }
        return res;
    }

    @Override
    public String toString(){
        String out = "";
        for(Recipe r : recipes){
            out += r.toString();
        }
        return "Package ID: " + packageID + ". Package Name: " + packageName + "\nRecipes:\n" + out;
    }
}
