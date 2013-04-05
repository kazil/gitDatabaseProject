package healtycaresystem;

import java.util.ArrayList;

public class ZipCodeRegister {
    private DatabaseOperations db = new DatabaseOperations();
    private ArrayList<ZipCode> zipCodes;
    private int zipCodeCount;

    public ZipCodeRegister(){
        zipCodes = db.getZipCodes();
        zipCodeCount = zipCodes.size();
    }

    public void regZipCode(String zipCode, String city){
        zipCodes.add(new ZipCode(zipCode, city));
        zipCodeCount = zipCodes.size();
    }

    public int getZipCodeCount(){
        return zipCodeCount;
    }

    public ArrayList<ZipCode> getZipCodes(){
        ArrayList<ZipCode> result = new ArrayList<>();
        //TODO
        return null;
    }
}
