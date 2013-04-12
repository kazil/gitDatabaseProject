package healtycaresystem;

public class Ingredient {
    private int ingredientID;
    private String name;
    private int inStock;
    private int orderMin;
    private double price;
    private int amount;

    public Ingredient(int ingredientID, String name, int inStock, int orderMin, double price, int amount){
        this.ingredientID = ingredientID;
        this.name = name;
        this.inStock = inStock;
        this.orderMin = orderMin;
        this.price = price;
        this.amount = amount;
    }

    public Ingredient(int ingredientID, String name, int inStock, int orderMin, double price){
        this.ingredientID = ingredientID;
        this.name = name;
        this.inStock = inStock;
        this.orderMin = orderMin;
        this.price = price;
        amount = -1;
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

    public double getPrice(){
        return price;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }

    public void changeOrderMin(int count){
        orderMin += count;
    }

    public void changeInStock(int count){
        inStock += count;
    }

    @Override
    public String toString(){
        return "IngredientID = " + ingredientID + ". Name = " + name + ". inStock = " + inStock +
                ". OrderMin = " + orderMin + ". Price = " + price + ". Amount = " + amount;
    }
}
