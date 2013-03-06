package healtycaresystem;

public class Employee extends Person {
    private String account;
    private int salary;

    public Employee(int id, String firstName, String surname, String address, ZipCode zipCode, String telephoneNumber, String email, String account, int salary){
        super(id, firstName, surname, address, zipCode, telephoneNumber, email);
        this.account = account;
        this.salary = salary;
    }

    public Employee(String firstName, String surname, String address, ZipCode zipCode, String telephoneNumber, String email, String account, int salary){
        super(firstName, surname, address, zipCode, telephoneNumber, email);
        this.account = account;
        this.salary = salary;
    }

}
