package healtycaresystem;

import java.util.ArrayList;
import java.sql.*;

/**
 * This class contains methods for fetching objects from the database,
 * and to save new information.
 * When writing new methods, remember to open connection with db.openConnection()
 * Do operations
 * And close the connection when done. This is done with db.closeConnection().
 * closeConnection() closes ResultSet, Statement and Connection objects in the DatabaseManagement object.
 *
 * Business customers have customer_type = 0.
 * Private customer have customer_type = 1.
 * @author Team One
 */
public class DatabaseOperations {

    private final String dbDriver = "org.apache.derby.jdbc.ClientDriver";
    private final String dbName = "jdbc:derby://db.stud.aitel.hist.no/healthycaresystems;user=teamone;password=1ForTheWin";
    private DatabaseManagement db;
    private final String selectAllCustomers = "select * from customers";
    private final String sqlSelectZip = "select * from zip_code order by zip_code";
    private final String selectAllUsers = "select * from users";
    private final String selectAllPackages = "SELECT * FROM package";
    private final String insertBusinessCustomer = "INSERT INTO customers(first_name, surname, adress, zip_code, telephone_number, email, organization_name, invoice_account, customer_type) VALUES(";
    private final String insertPrivateCustomer = "INSERT INTO customers(first_name, surname, adress, zip_code, telephone_number, email, customer_type) VALUES(";
    private final String insertOrder = "INSERT INTO orders(delivery_date, order_status, package_id, customer_id, employee_id) VALUES(";

    /**
     * Construktor.
     * Set's up a new DatabaseManagement object.
     * This object is used for database operations
     */
    public DatabaseOperations() {
        db = new DatabaseManagement(dbDriver, dbName);
    }

    public ArrayList<Person> getAllCustomers(){
        db.openConnection();
        ArrayList<Person> customers = new ArrayList<>();
        ResultSet res = db.getSelection(selectAllCustomers);
        try {
            while(res.next()){
                int id = res.getInt("customer_id");
                String fName = res.getString("first_name");
                String sName = res.getString("surname");
                String adr = res.getString("adress");
                String zipCode = res.getString("zip_code");
                String city = getCityFromZipCode(zipCode);
                String tlf = res.getString("telephone_number");
                String email = res.getString("email");
                String orgName = res.getString("organization_name");
                String account = res.getString("invoice_account");
                int type = res.getInt("customer_type");
                if(type == 0){
                    // Business
                    customers.add(new BusinessCustomer(id,fName, sName, adr, new ZipCode(zipCode, city), tlf, email, account, orgName));
                }else {
                    // Private
                    customers.add(new PrivateCustomer(id,fName, sName, adr, new ZipCode(zipCode, city), tlf, email));
                }
            }
        } catch (Exception e) {
            // Exception handling
            System.out.println("Error in DatabaseOperations.getAllCustomers()");
        } finally {
            db.closeConnection();
            return customers;
        }
    }

    public boolean regBusinessCustomer(BusinessCustomer newCustomer){
        // String firstName, String surname, String address, ZipCode zipCode, String telephoneNumber, String email, String invoiceAccount, String orgName
        String fName = newCustomer.getFirstName();
        String sName = newCustomer.getSurname();
        String adr = newCustomer.getAddress();
        String zipCode = newCustomer.getZipCode().getZipCode();
        String tlf = newCustomer.getTelephoneNumber();
        String email = newCustomer.getEmail();
        String account = newCustomer.getInvoiceAccount();
        String orgName = newCustomer.getOrgName();
        String sql = insertBusinessCustomer + "'"+ fName +"','" + sName + "','" + adr + "','" + zipCode + "','" + tlf + "','" + email + "','" + orgName + "','" + account + "', 0)";
        db.openConnection();
        if(db.doUpdate(sql)){
            db.closeConnection();
            return true;
        } else {
            db.closeConnection();
            return false;
        }
    }

    public boolean regPrivateCustomer(PrivateCustomer newCustomer){
        // String firstName, String surname, String address, ZipCode zipCode, String telephoneNumber, String email
        String fName = newCustomer.getFirstName();
        String sName = newCustomer.getSurname();
        String adr = newCustomer.getAddress();
        String zipCode = newCustomer.getZipCode().getZipCode();
        String tlf = newCustomer.getTelephoneNumber();
        String email = newCustomer.getEmail();
        String sql = insertPrivateCustomer + "'"+ fName +"','" + sName + "','" + adr + "','" + zipCode + "','" + tlf + "','" + email + "', 1)";
        db.openConnection();
        if(db.doUpdate(sql)){
            db.closeConnection();
            return true;
        } else {
            db.closeConnection();
            return false;
        }
    }

    public boolean regOrder(Order newOrder){
        String deliveryDate = newOrder.getDeliveryDate();
        int orderStatus = newOrder.getOrderStatus();
        int packageID = newOrder.getPackageID();
        int customerID = newOrder.getCustomerID();
        int employeeID = newOrder.getEmployeeID();
        String sql = insertOrder+"DATE('"+deliveryDate+"'),"+orderStatus+","+packageID+","+customerID+","+employeeID+")";
        db.openConnection();
        if(db.doUpdate(sql)){
            db.closeConnection();
            return true;
        }else{
            db.closeConnection();
            return false;
        }
    }

    public ArrayList<Package> getPackages() {
        db.openConnection();
        ArrayList<Package> packages = new ArrayList<>();
        ResultSet res = db.getSelection(selectAllPackages);
        try {
            while(res.next()){
                int id = res.getInt("package_id");
                String name = res.getString("package_name");
                packages.add(new Package(id, name));
            }
        } catch (Exception e) {
            System.out.println("Error in getPackages()");
        } finally{
            db.closeConnection();
            return packages;
        }
    }

    /**
     * Finds all the zip codes in database.
     *
     * @return ArrayList<ZipCode> containing customers.
     */
    public ArrayList<ZipCode> getZipCodes() {
        db.openConnection();
        ArrayList<ZipCode> zips = new ArrayList<ZipCode>();
        ResultSet res = db.getSelection(sqlSelectZip);
        try {
            while (res.next()) {
                String zip = res.getString("zip_code");
                String city = res.getString("city");
                zips.add(new ZipCode(zip, city));
            }
        } catch (SQLException e) {
            System.out.println("Feil oppstod i getZipCodes()");
        } finally {
            db.closeConnection();
            return zips;
        }
    }


    public String getCityFromZipCode(String zip){
        String sqlSelectedZip = "select city as city from zip_code where zip_code ='" + zip.trim() +"'";
        db.openConnection();
        ResultSet res = db.getSelection(sqlSelectedZip);
        String city = null;
        try {
            res.next();
            city = res.getString("city");
        } catch (SQLException e) {
            System.out.println("Can't get city from database.\n" + e);
        }
        db.closeConnection();
        return city;
    }

    /**
     * Method for fetching all users from database.
     *
     * @return - ArrayList<User> with all users.
     */
    public ArrayList<User> getUsers() {
        db.openConnection();
        ResultSet res = db.getSelection(selectAllUsers);
        ArrayList<User> users = new ArrayList<User>();
        try {
            while (res.next()) {
                String userName = res.getString("user_name");
                String password = res.getString("password");
                String clearance = res.getString("clearance");
                int employeeID = res.getInt("employee_id");
                users.add(new User(userName, password, clearance, employeeID));
            }
        } catch (SQLException e) {
            System.out.println("SQL error in getUsers()");
        } finally {
            db.closeConnection();
            return users;
        }

    }

    public ArrayList<Order> getOrders(){
        String sql = "SELECT * FROM orders";
        db.openConnection();
        ResultSet res = db.getSelection(sql);
        ArrayList<Order> orders = new ArrayList<>();
        try {
            while(res.next()){
                int orderID = res.getInt("order_id");
                String date = res.getDate("delivery_date").toString();
                int orderStatus = res.getInt("order_status");
                int packageID = res.getInt("package_id");
                int customerID = res.getInt("customer_id");
                int employeeID = res.getInt("employee_id");

                Order order = new Order(orderID, date, orderStatus, packageID, customerID, employeeID);
                orders.add(order);
            }
            for(Order o : orders){
                o.setPackage(getPackage(o.getPackageID()));
            }
        } catch (Exception e) {
            System.out.println("Feil i getOrders()");
        } finally {
            db.closeConnection();
            return orders;
        }
    }

    public Package getPackage(int packageID){
        String sql = "SELECT * FROM package WHERE package_id = " + packageID;
        db.openConnection();
        ResultSet res = db.getSelection(sql);
        Package packageRes = null;
        try {
            while(res.next()){
                String name = res.getString("package_name");
                int standard = res.getInt("standard");
                packageRes = new Package(packageID, name);
            }
            ArrayList<Recipe> allRecipes = getAllRecipes();
            db.closeConnection();
            db.openConnection();
            res = db.getSelection("SELECT * FROM package_recipes WHERE package_id = " + packageID);
            while(res.next()){
                int recipieID = res.getInt("recipie_id");
                for(Recipe r : allRecipes){
                    if(r.getRecipeID() == recipieID){
                        packageRes.addRecipie(r);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Feil i DatabaseOperations.getPackage()");
        } finally {
            db.closeConnection();
            return packageRes;
        }
    }

    public ArrayList<Recipe> getAllRecipes(){
        String sql = "SELECT * FROM recipes";
        String sql2 = "SELECT * FROM ingredients_recipes WHERE recipie_id = ";
        db.openConnection();
        ResultSet res = db.getSelection(sql);
        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            // Henter alle oppskrifter.
            while(res.next()){
                int recipieID = res.getInt("recipie_id");
                String name = res.getString("name");
                Recipe recipie = new Recipe(recipieID, name);
                recipes.add(recipie);
            }
            ArrayList<Ingredient> allIngredients = getAllIngredients();
            for(Recipe r : recipes){
                int recipieID = r.getRecipeID();
                db.closeConnection();
                db.openConnection();
                res = db.getSelection(sql2 + recipieID);
                while(res.next()){
                    int ingredienceID = res.getInt("ingredience_id");
                    int amount = res.getInt("amount");
                    for(Ingredient i : allIngredients){
                        if(i.getIngredientID() == ingredienceID){
                            i.setAmount(amount);
                            r.addIngredient(new Ingredient(i.getIngredientID(), i.getName(), i.getInStock(), i.getOrderMin(), i.getPrice(), i.getAmount()));
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Feil i DatabaseOperations.getAllRecipes()");
        } finally {
            db.closeConnection();
            return recipes;
        }
    }

    public ArrayList<Ingredient> getAllIngredients(){
        String sql = "SELECT * FROM ingredients";
        db.openConnection();
        ResultSet res = db.getSelection(sql);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try {
            while(res.next()){
                int ingredienceID = res.getInt("ingredience_id");
                String name = res.getString("name");
                int inStock = res.getInt("in_stock");
                int orderMin = res.getInt("order_min");
                double price = (double)(res.getFloat("price"));
                //int ingredientID, String name, int inStock, int orderMin, double price
                Ingredient ingr = new Ingredient(ingredienceID, name, inStock, orderMin, price);
                ingredients.add(ingr);
            }
        } catch (Exception e) {
            System.out.println("Feil i DatabaseOperations.getAllIngredients()");
        } finally {
            db.closeConnection();
            return ingredients;
        }
    }

    // Metode for å hente alle ansatte
    // Metode for å legge inn ny ansatt
    // Metode for å hente pakker
    // Metode for å legge inn ny pakke
    // Metode for å hente alle oppskrifter
    // Metode for å legge til ny oppskrift
    // Metode for å hente alle ingredienser
    // Metode for å legge til ny ingrediens
    // Metode for å hente alle brukere.
    // Metode for å legge til ny bruker.
}
