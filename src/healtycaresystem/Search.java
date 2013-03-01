package healtycaresystem;

import java.util.ArrayList;

public class Search {
    public Search(){

    }

    public ArrayList<Customer> searchCustomer(ArrayList<Customer> list, String criteria){
        int critInt = -1;
        ArrayList<Customer> resultList = new ArrayList<Customer>();

        for(Customer c : list){
            int id = c.getCustomerID();
            String orgName = c.getOrganizationName().toLowerCase();
            String fName = c.getFirstName().toLowerCase();
            String sName = c.getSurname().toLowerCase();
            String adr = c.getAddress().toLowerCase();
            String tlf = c.getTelephoneNumber().toLowerCase();
            String mail = c.getEmail().toLowerCase();
            String account = c.getInvoiceAccount().toLowerCase();
            //String zip = c.getZip().getZipCode();

            try {
                critInt = Integer.parseInt(criteria);

            } catch (Exception e) {
                // Exception handling
            }

            if(id == critInt){
                // ID match
                resultList.add(c);
            } else if(orgName.contains(criteria.toLowerCase().trim())){
                // orgName match
                resultList.add(c);
            } else if(fName.contains(criteria.toLowerCase().trim())){
                // fName match
                resultList.add(c);
            } else if(sName.contains(criteria.toLowerCase().trim())){
                // sName match
                resultList.add(c);
            } else if(adr.contains(criteria.toLowerCase().trim())){
                // adr match
                resultList.add(c);
            } else if(tlf.contains(criteria.toLowerCase().trim())){
                // tlf match
                resultList.add(c);
            } else if(account.contains(criteria.toLowerCase().trim())){
                // account match
                resultList.add(c);
            } else if(mail.contains(criteria.toLowerCase().trim())){
                resultList.add(c);
            }
        }
        return resultList;
    }

    public ZipCode searchZip(ArrayList<ZipCode> list, String criteria){

        for(ZipCode z : list){
            if(z.getZipCode().equals(criteria.trim())){
                return z;
            }
        }

        return null;
    }

    public String searchForCity(ArrayList<ZipCode> list, String zip){
        for(ZipCode z : list){
            if(z.getZipCode().equalsIgnoreCase(zip)){
                return z.getCity();
            }
        }
        return "";
    }

}
