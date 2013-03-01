package healtycaresystem;

import java.util.ArrayList;
import java.sql.*;

/**
 * This class contains methods for fetching objects from the database,
 * and to save new information.
 * When writing new methods, remember to open connection with db.openConnection()
 * Do operations
 * And close the connection when done. This is done with db.closeConnection().
 * closeConnection() closes ResultSet, Statement and Connection objects in the DatabaseManagement object.
 * @author Team One
 */
public class DatabaseOperations {

    private String dbDriver = "org.apache.derby.jdbc.ClientDriver";
    private String dbName = "jdbc:derby://db.stud.aitel.hist.no/healthycaresystems;user=teamone;password=1ForTheWin";
    private DatabaseManagement db;
    private final String selectAllCustomers = "select * from customers order by first_name";
    private final String sqlSelectZip = "select * from zip_code order by zip_code";

    /**
     * Construktor.
     * Set's up a new DatabaseManagement object.
     * This object is used for database operations
     */
    public DatabaseOperations() {
        db = new DatabaseManagement(dbDriver, dbName);
    }

    /**
     * Method for fetching all customers from database.
     *
     * @return - ArrayList<Customer> with all customers.
     */
    public ArrayList<Customer> getCustomers() {
        db.openConnection();
        ResultSet res = db.getSelection(selectAllCustomers);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        try {
            while (res.next()) {
                int id = res.getInt("customer_id");
                String address = res.getString("address");
                String tlf = res.getString("telephone_number");
                String email = res.getString("email");
                String zip = res.getString("zip_code");
                String orgName = res.getString("organization_name");
                String fname = res.getString("first_name");
                String lname = res.getString("surname");
                String account = res.getString("invoice_account");
                customers.add(new Customer(id, address, tlf, email, orgName, fname, lname, null, account));
            }
        } catch (SQLException e) {
            System.out.println("Feil oppstod i getCustomers()");
        } finally {
            db.closeConnection();
            return customers;
        }
    }

    /**
     * Finds all the zip codes in database.
     *
     * @return ArrayList<ZipCode> containing customers.
     */
    public ArrayList<ZipCode> getZipCodes() {
        db.openConnection();
        ArrayList<ZipCode> zips = new ArrayList<ZipCode>();
        ResultSet res = db.getSelection(sqlSelectZip);
        try {
            while (res.next()) {
                String zip = res.getString("zip_code");
                String city = res.getString("city");
                zips.add(new ZipCode(zip, city));
            }
        } catch (SQLException e) {
            System.out.println("Feil oppstod i getZipCodes()");
        } finally {
            db.closeConnection();
            return zips;
        }
    }


    public String getCityFromZipCode(String zip){
        String sqlSelectedZip = "select city as city from zip_code where zip_code ='" + zip.trim() +"'";
        db.openConnection();
        ResultSet res = db.getSelection(sqlSelectedZip);
        String city = null;
        try {
            res.next();
            city = res.getString("city");
        } catch (SQLException e) {
            System.out.println("Can't get city from database.\n" + e);
        }
        db.closeConnection();
        return city;
    }

    /**
     * Method for registering a new customer in the database. Look closer on
     * this method before launch!
     *
     * @param newCustomer - the customers to be saved.
     */
    public void regCustomer(Customer newCustomer) {
        // address
        String adr = "'" + newCustomer.getAddress() + "',";
        // telephone number
        String tlf = "'" + newCustomer.getTelephoneNumber() + "',";
        // sjekker om mail skal være null
        String mailLest = newCustomer.getEmail();
        String mail = null;
        if (mailLest == null || mailLest.trim().equals("")) {
            mail = "NULL,";
        } else {
            mail = "'" + newCustomer.getEmail() + "',";
        }
        // sjekker om orgName skal være null
        String orgNameLest = newCustomer.getOrganizationName();
        String orgName = null;
        if (orgNameLest == null || orgNameLest.trim().equals("")) {
            orgName = "NULL,";
        } else {
            orgName = "'" + newCustomer.getOrganizationName() + "',";
        }
        // first name
        String fname = "'" + newCustomer.getFirstName() + "',";
        // surname
        String sname = "'" + newCustomer.getSurname() + "',";
        // sjekker om account skal være null.
        String accountLest = newCustomer.getInvoiceAccount();
        String account = null;
        if (accountLest == null || accountLest.trim().equals("")) {
            account = "NULL";
        } else {
            account = "'" + newCustomer.getInvoiceAccount() + "'";
        }
        // zip code
        String zip = "'" + newCustomer.getZip().getZipCode() + "',";

        String sqlUpdate = "insert into customers(address, telephone_number, email, zip_code, organization_name, first_name, surname, invoice_account)"
                + " values(" + adr + tlf + mail + zip + orgName + fname + sname + account + ")";
        System.out.println(sqlUpdate);
        db.openConnection();
        boolean sjekk = db.doUpdate(sqlUpdate);
        if (sjekk) {
            System.out.println("reg ok");
        } else {
            System.out.println("reg ikke ok");
        }
        db.closeConnection();
    }

    public void regZip(String zip, String city){
        db.openConnection();
        String sqlInsertZip = "INSERT INTO zip_code(zip_code, city) VALUES('"+zip+"','"+city+"')";
        db.doUpdate(sqlInsertZip);
        db.closeConnection();
    }
    // Metode for å hente alle ansatte
    // Metode for å legge inn ny ansatt
    // Metode for å hente pakker
    // Metode for å legge inn ny pakke
    // Metode for å hente alle oppskrifter
    // Metode for å legge til ny oppskrift
    // Metode for å hente alle ingredienser
    // Metode for å legge til ny ingrediens
    // Metode for å hente alle brukere.
    // Metode for å legge til ny bruker.
}
