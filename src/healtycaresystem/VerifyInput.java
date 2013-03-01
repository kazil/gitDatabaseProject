package healtycaresystem;

public class VerifyInput {

    public VerifyInput(){
    }

    public int verifyCustomer(Customer newCustomer){
        if(newCustomer.getInvoiceAccount().trim().length() != 11 && !newCustomer.getInvoiceAccount().trim().equals("")){
            return -1;
        } else if(newCustomer.getTelephoneNumber().trim().length() != 8){
            return -2;
        } else if(newCustomer.getZip() == null){
            return -3;
        } else if(newCustomer.getFirstName().equals("") || newCustomer.getSurname().equals("") || newCustomer.getAddress().equals("")){
            return -4;
        }

        return 0;
    }
}
