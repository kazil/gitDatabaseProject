package healtycaresystem;

public class User {

    private int employeeID;
    private String username;
    private String password;
    private String clearance;

    public User(String username, String password, String clearance, int employeeID) {
        this.username = username;
        this.password = password;
        this.clearance = clearance;
        this.employeeID = employeeID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getClearance() {
        return clearance;
    }

    public int getEmployeeID(){
        return employeeID;
    }

    public void setUsername(String newUsername) {
        username = newUsername;
    }

    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public void setClearance(String newClearance) {
        clearance = newClearance;
    }
}