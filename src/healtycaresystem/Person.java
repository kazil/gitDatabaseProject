package healtycaresystem;

public class Person {
    private int id;
    private String firstName;
    private String surname;
    private String address;
    private ZipCode zipCode;
    private String telephoneNumber;
    private String email;

    public Person(String firstName, String surname, String address, ZipCode zipCode, String telephoneNumber, String email){
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.zipCode = zipCode;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
    }

    public Person(int id, String firstName, String surname, String address, ZipCode zipCode, String telephoneNumber, String email){
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.zipCode = zipCode;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
    }

    public int getCustomerID(){
        return id;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getSurname(){
        return surname;
    }

    public String getAddress(){
        return address;
    }

    public String getTelephoneNumber(){
        return telephoneNumber;
    }

    public String getEmail(){
        return email;
    }

    public ZipCode getZipCode(){
        return zipCode;
    }
}
