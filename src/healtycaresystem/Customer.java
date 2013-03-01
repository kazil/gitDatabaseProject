package healtycaresystem;

public class Customer {
    private int customerID = 0; // før registrering i database
    private String organizationName;
    private String firstName;
    private String surname;
    private String address;
    private ZipCode zip;
    private String telephoneNumber;
    private String email;
    private String invoiceAccount;

    public Customer(String address, String telephoneNumber, String email, String organizationName, String firstName, String surname, ZipCode zip, String invoiceAccount) {
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.organizationName = organizationName;
        this.firstName = firstName;
        this.surname = surname;
        this.zip = zip;
        this.invoiceAccount = invoiceAccount;
    }

    public Customer(int customerID, String address, String telephoneNumber, String email, String organizationName, String firstName, String surname, ZipCode zip, String invoiceAccount) {
        this.customerID = customerID;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.organizationName = organizationName;
        this.firstName = firstName;
        this.surname = surname;
        this.zip = zip;
        this.invoiceAccount = invoiceAccount;
    }

    public void testGit(){
        System.out.println("hej hej hej");
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setZip(ZipCode zip) {
        this.zip = zip;
    }

    public void setInvoiceAccount(String invoiceAccount) {
        this.invoiceAccount = invoiceAccount;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getEmail() {
        if(email != null)
            return email;
        else
            return "";
    }

    public String getOrganizationName() {
        if(organizationName != null)
            return organizationName;
        else
            return "";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public ZipCode getZip() {
        return zip;
    }

    public String getInvoiceAccount() {
        if(invoiceAccount != null)
            return invoiceAccount;
        else
            return "";
    }

    @Override
    public String toString() {
        return "ID: " + customerID + "\nOrgName: " + organizationName + "\nFirst name: " + firstName + "\nSurname: " + surname + "\nAddress: " + address
                + "\nTlf: " + telephoneNumber + "\nEmail: " + email + "\nAccount: " + invoiceAccount;
    }
}
