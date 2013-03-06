package healtycaresystem;

public class Order {
    private int orderID;
    private String deliveryDate;
    private int orderStatus;
    private int packageID;
    private int customerID;
    private int employeeID;

    public Order(int orderID, String deliveryDate, int orderStatus, int packageID, int customerID, int employeeID){
        this.orderID = orderID;
        this.deliveryDate = deliveryDate;
        this.orderStatus = orderStatus;
        this.packageID = packageID;
        this.customerID = customerID;
        this.employeeID = employeeID;
    }

    public Order(String deliveryDate, int orderStatus, int packageID, int customerID, int employeeID){
        this.deliveryDate = deliveryDate;
        this.orderStatus = orderStatus;
        this.packageID = packageID;
        this.customerID = customerID;
        this.employeeID = employeeID;
    }

    public int getOrderID() {
        return orderID;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public int getPackageID() {
        return packageID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getEmployeeID() {
        return employeeID;
    }
}
