package healtycaresystem;

public class Ingredient {
    private int ingredientID;
    private String name;
    private int inStock;
    private int orderMin;

    public Ingredient(int ingredientID, String name, int inStock, int orderMin){
        this.ingredientID = ingredientID;
        this.name = name;
        this.inStock = inStock;
        this.orderMin = orderMin;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public String getName() {
        return name;
    }

    public int getInStock() {
        return inStock;
    }

    public int getOrderMin() {
        return orderMin;
    }

    public void changeOrderMin(int count){
        orderMin += count;
    }

    public void changeInStock(int count){
        inStock += count;
    }
}
