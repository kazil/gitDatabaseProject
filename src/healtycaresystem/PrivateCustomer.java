package healtycaresystem;

public class PrivateCustomer extends Person {
    public PrivateCustomer(int id, String firstName, String surname, String address, ZipCode zipCode, String telephoneNumber, String email){
        super(id, firstName, surname, address, zipCode, telephoneNumber, email);
    }
    public PrivateCustomer(String firstName, String surname, String address, ZipCode zipCode, String telephoneNumber, String email){
        super(firstName, surname, address, zipCode, telephoneNumber, email);
    }
}
