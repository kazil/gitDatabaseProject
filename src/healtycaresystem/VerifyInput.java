package healtycaresystem;

import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyInput {

    public VerifyInput(){
    }

    public int verifyCustomer(Person customer){
        // Private customer
        if(customer instanceof PrivateCustomer){
            // sjekk telefon
            if(!checkTlf(customer.getTelephoneNumber())){
                return -2;
            }
            // sjekk mail
            if(!checkEmail(customer.getEmail())){
                return -3;
            }
        }
        // Business customer
        else if(customer instanceof BusinessCustomer){
            BusinessCustomer newCustomer = (BusinessCustomer)customer;
            // sjekk telefon
            if(!checkTlf(newCustomer.getTelephoneNumber())){
                return -2;
            }
            // sjekk mail
            if(!checkEmail(newCustomer.getEmail())){
                return -3;
            }
            // sjekk invoice account
            if(newCustomer.getInvoiceAccount().trim().length() != 11){
                return -4;
            }
        }
        else {
            return -5;
        }
        return 0; // everything ok
    }

    private boolean checkTlf(String tlf){
        if(tlf.trim().length() != 8){
            return false;
        }
        return true;
    }

    private boolean checkEmail(String email){
        if(!email.contains("@")){
            return false;
        }
        else if(!email.contains(".com")){
            if(email.contains(".no")){
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean verifyDate(int year, int month, int date){
        GregorianCalendar cal = new GregorianCalendar();
        String todayYear = Integer.toString(cal.get(GregorianCalendar.YEAR));
        String todayMonth = Integer.toString(cal.get(GregorianCalendar.MONTH)+1);
        String todayDate = Integer.toString(cal.get(GregorianCalendar.DAY_OF_MONTH));
        String newFullDate = ""+year+month+date;
        String todayFullDate = todayYear+todayMonth+todayDate;
        if(Integer.parseInt(newFullDate) >= Integer.parseInt(todayFullDate)){
            return true;
        }
        return false;
    }
}
