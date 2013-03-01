package healtycaresystem;

public class User {

    private String username;
    private String password;
    private String clearance;

    public User(String username, String password, String clearance) {
        this.username = username;
        this.password = password;
        this.clearance = clearance;
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