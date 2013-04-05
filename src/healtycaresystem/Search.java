package healtycaresystem;

import java.util.ArrayList;

public class Search {

    public ArrayList<Person> searchCustomers(ArrayList<Person> list, String criteria){
        ArrayList<Person> resultList = new ArrayList<Person>();
        int critInt;
        try {
            critInt = Integer.parseInt(criteria);
        } catch (Exception e) {
            critInt = -1;
        }

        for (Person p : list) {
            if(p.getCustomerID() == critInt){
                resultList.add(p);
            }else if(p.getFirstName().toLowerCase().contains(criteria.trim().toLowerCase())){
                resultList.add(p);
            }else if(p.getSurname().toLowerCase().contains(criteria.trim().toLowerCase())){
                resultList.add(p);
            }else if(p.getAddress().toLowerCase().contains(criteria.trim().toLowerCase())){
                resultList.add(p);
            }else if(p.getTelephoneNumber().toLowerCase().contains(criteria.trim().toLowerCase())){
                resultList.add(p);
            }else if(p.getEmail().toLowerCase().contains(criteria.trim().toLowerCase())){
                resultList.add(p);
            }else if(p instanceof BusinessCustomer){
                BusinessCustomer bc = (BusinessCustomer)p;
                if(bc.getOrgName().toLowerCase().contains(criteria.toLowerCase())){
                    resultList.add(p);
                }
            }
        }
        return resultList;
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
