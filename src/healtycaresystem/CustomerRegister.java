package healtycaresystem;

import java.util.ArrayList;

public class CustomerRegister {
    private DatabaseOperations db = new DatabaseOperations();
    private ArrayList<Person> listCustomers;
    private int customerCount;

    public CustomerRegister(){
        listCustomers = db.getAllCustomers();
        customerCount = listCustomers.size();
    }

    public int getCustomerCount(){
        return customerCount;
    }

    public ArrayList<Person> getListCustomers(){
        //TODO send back copy ?
        return listCustomers;
    }

    public int regPrivateCustomer(PrivateCustomer newCustomer){
        //TODO check input
        VerifyInput vf = new VerifyInput();
        int check = vf.verifyCustomer(newCustomer);
        if(check == 0){
            if(db.regPrivateCustomer(newCustomer)){
                updateListCustomers();
                return 0;
            }else{
                return -1;
            }
        }
        return check;
    }

    public int regBusinessCustomer(BusinessCustomer newCustomer){
        // TODO check input
        VerifyInput vf = new VerifyInput();
        int check = vf.verifyCustomer(newCustomer);
        if(check == 0){
            if(db.regBusinessCustomer(newCustomer)){
                updateListCustomers();
                return 0;
            }else{
                return -1;
            }
        }
        return check;
    }

    public ArrayList<Person> updateListCustomers(){
        //TODO send back copy
        listCustomers = db.getAllCustomers();
        return listCustomers;
    }
}
