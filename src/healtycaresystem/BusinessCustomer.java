package healtycaresystem;

public class BusinessCustomer extends Person {
    private String invoiceAccount;
    private String orgName;

    public BusinessCustomer(int id, String firstName, String surname, String address, ZipCode zipCode, String telephoneNumber, String email, String invoiceAccount, String orgName){
        super(id, firstName, surname, address, zipCode, telephoneNumber, email);
        this.invoiceAccount = invoiceAccount;
        this.orgName = orgName;
    }

    public BusinessCustomer(String firstName, String surname, String address, ZipCode zipCode, String telephoneNumber, String email, String invoiceAccount, String orgName){
        super(firstName, surname, address, zipCode, telephoneNumber, email);
        this.invoiceAccount = invoiceAccount;
        this.orgName = orgName;
    }

    public String getInvoiceAccount(){
        return invoiceAccount;
    }

    public String getOrgName(){
        return orgName;
    }
}
