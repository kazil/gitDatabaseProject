package healtycaresystem;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: KazilLaptop
 * Date: 2013-03-21
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
public class ChefRegistry {
    private ArrayList<Order> orders;
    private ArrayList<Recipe> recipes;
    private DatabaseOperations db = new DatabaseOperations();
    private int employeeID;
    public ChefRegistry(int employeeID){
        this.employeeID = employeeID;
        this.orders = db.getAllOrders();
    }

    public ArrayList<Order> updateCustomerTable(){
        return orders;
    }
}
