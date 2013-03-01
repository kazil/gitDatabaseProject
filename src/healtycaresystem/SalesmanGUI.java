package healtycaresystem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SalesmanGUI extends JFrame{

    //<editor-fold defaultstate="collapsed" desc="This fold contains object variables">
    private ArrayList<Customer> customers;
    private ArrayList<ZipCode> zipCodes;
    private DatabaseOperations db = new DatabaseOperations();
    private String[] tableColNames = {"Customer ID", "Organization Name", "First Name", "Surname", "Address", "Telephone Number", "E-mail"};
    private Listener listener = new Listener();
    private Dimension dimTable = new Dimension(1024, 200);
    private Dimension dimBigWindow = new Dimension(1100, 550);
    private Dimension dimSmallWindow = new Dimension(1100, 350);
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="This fold contains components for the GUI">

    private JLabel prompt;

    /** Customer table and default table model.**/
    private JTable tableCustomers;
    private JScrollPane scrollTableCustomers;
    private DefaultTableModel tm;

    /** Components for PanelRegCustomer **/
    private JTextField
            fieldID, fieldOrgName, fieldFname, fieldSname,
            fieldAdr, fieldTlf, fieldEmail, fieldZipCode, fieldAccount;
    private JButton
            buttonSaveCustomer,
            buttonCancelReg;
    private JTextField fieldCity;

    /** Components for PanelActions **/
    private JTextField fieldSearch;
    private JButton
            buttonSearch,
            buttonResetSearch,
            buttonRegCustomer,
            buttonPackage,
            buttonUpdateCustomerTable,
            buttonExit;

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="This fold contains all panels">
    private PanelTable panelTable;
    private PanelActions panelActions;
    private PanelRegCustomer panelRegCustomer;
    //</editor-fold>

    /**
     * Constructor for SalesmanGUI.
     */
    public SalesmanGUI(){
        zipCodes = db.getZipCodes();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Salesman Interface");
        setLayout(new GridBagLayout());
        GridBagConstraints fgb = new GridBagConstraints();

        initPanels();

        fgb.insets = new Insets(5, 5, 5, 5);

        setPreferredSize(dimSmallWindow);
        fgb.gridx = 0;
        fgb.gridy = 0;

        add(panelTable, fgb);

        fgb.gridy++;
        fgb.anchor = GridBagConstraints.LINE_START;
        add(panelActions, fgb);
        fgb.gridy++;

        Border grayLine = BorderFactory.createLineBorder(Color.GRAY);
        TitledBorder borderTitle = BorderFactory.createTitledBorder(grayLine, "Fill inn data");
        panelRegCustomer.setBorder(borderTitle);
        add(panelRegCustomer, fgb);

        panelRegCustomer.setVisible(false);
        pack();
    }

    /**
     * Initializes the panels to be used in salesman GUI.
     * Should only be run once on startup.
     */
    private void initPanels(){
        panelTable = new PanelTable();
        panelActions = new PanelActions();
        panelRegCustomer = new PanelRegCustomer();
    }

    /**
     * Resize the window
     *
     * @param size - Dimension
     */
    private void setWinSize(Dimension size){
        setSize(size);
    }

    /**
     * Method for updating customer table.
     * This method fetches all customers from the database
     * and populates the table.
     */
    public void updateCustomerTable(){
        // Clear table.
        for(int i = tm.getRowCount() - 1; i >=0; i--){
            tm.removeRow(i);
        }
        // Populate table.
        customers = db.getCustomers();
        for(Customer c : customers){
            int id = c.getCustomerID();
            String orgName = c.getOrganizationName();
            String fName = c.getFirstName();
            String sName = c.getSurname();
            String adr = c.getAddress();
            String tlf = c.getTelephoneNumber();
            String email = c.getEmail();
            String newRow[] = {Integer.toString(id), orgName, fName, sName, adr, tlf, email};
            tm.addRow(newRow);
        }
    }

    /**
     * Populates the table with the given array list
     *
     * @param newList - ArrayList<Customer>
     */
    public void tableSearch(ArrayList<Customer> newList){
        // Clear table.
        for(int i = tm.getRowCount() - 1; i >=0; i--){
            tm.removeRow(i);
        }
        // Populate table.
        for(Customer c : newList){
            int id = c.getCustomerID();
            String orgName = c.getOrganizationName();
            String fName = c.getFirstName();
            String sName = c.getSurname();
            String adr = c.getAddress();
            String tlf = c.getTelephoneNumber();
            String email = c.getEmail();
            String newRow[] = {Integer.toString(id), orgName, fName, sName, adr, tlf, email};
            tm.addRow(newRow);
        }
    }

    /**
     * Method to reset the fields in PanelRegCustomer.
     */
    public void resetNewCustomerFields(){
        fieldOrgName.setText("");
        fieldFname.setText("");
        fieldSname.setText("");
        fieldAdr.setText("");
        fieldTlf.setText("");
        fieldEmail.setText("");
        fieldAccount.setText("");
        fieldZipCode.setText("");
    }

    /**
     * PanelTable class.
     *
     * Panel for SalesmanGUI.
     * Contains a table with customers.
     */
    private class PanelTable extends JPanel {
        /** GridBagConstraint for PanelTable **/
        private GridBagConstraints gbc = new GridBagConstraints();
        private Insets margins = new Insets(5, 5, 5, 5);

        public PanelTable(){
            // Constructor for PanelTable
            setLayout(new GridBagLayout());
            initTable();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = margins;
            add(scrollTableCustomers, gbc);
        }

        /**
         * Method for initiating the table.
         * Only to be used in the constructor
         * before adding the table to the window.
         */
        private void initTable(){
            // Overrides the method isCellEditable. This sets the cell to not editable.
            tm = new DefaultTableModel(tableColNames, 0){
                @Override
                public boolean isCellEditable(int row, int column){
                    return false;
                }
            };
            tableCustomers = new JTable(tm);
            tableCustomers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            int width = tableCustomers.getWidth();
            int height = tableCustomers.getHeight();
            scrollTableCustomers = new JScrollPane(tableCustomers);
            scrollTableCustomers.setPreferredSize(dimTable);

            updateCustomerTable();
        }



    }

    /**
     * PanelActions class.
     *
     * Panel for SalesmanGUI.
     * Contains search fields and buttons for the standard GUI.
     */
    private class PanelActions extends JPanel {
        private GridBagConstraints gbc = new GridBagConstraints();
        private Insets normMargins = new Insets(5, 5, 5, 5);
        private Insets extMargins = new Insets(5, 210, 5, 5);

        public PanelActions(){
            // Constructor for PanelActions
            setLayout(new GridBagLayout());
            initComponents();
            gbc.insets = normMargins;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.LINE_START;
            /**
             * Search function
             */
            /** Search field **/
            add(prompt, gbc);
            gbc.gridx++;
            add(fieldSearch, gbc);
            /** Search button **/
            gbc.gridx++;
            add(buttonSearch, gbc);
            /** Reset button **/
            gbc.gridx++;
            add(buttonResetSearch, gbc);

            /**
             * Program buttons
             */
            gbc.anchor = GridBagConstraints.LINE_END;
            gbc.insets = extMargins;
            /** Register new customer button **/
            gbc.gridx++;
            add(buttonRegCustomer, gbc);
            gbc.insets = normMargins;
            /** Select package button **/
            gbc.gridx++;
            add(buttonPackage, gbc);
            /** Update table button **/
            gbc.gridx++;
            add(buttonUpdateCustomerTable, gbc);
            /** Exit button **/
            gbc.gridx++;
            add(buttonExit, gbc);
        }

        private void initComponents(){
            fieldSearch = new JTextField(15);
            fieldSearch.addActionListener(listener);
            fieldSearch.setActionCommand("search");

            buttonSearch = new JButton("Search");
            buttonSearch.addActionListener(listener);
            buttonSearch.setActionCommand("search");

            buttonResetSearch = new JButton("Reset");
            buttonResetSearch.addActionListener(listener);
            buttonResetSearch.setActionCommand("resetSearch");

            buttonRegCustomer = new JButton("Register Customer");
            buttonRegCustomer.addActionListener(listener);
            buttonRegCustomer.setActionCommand("openReg");

            buttonPackage = new JButton("Select Package");
            buttonPackage.addActionListener(listener);
            buttonPackage.setActionCommand("package");

            buttonUpdateCustomerTable = new JButton("Update");
            buttonUpdateCustomerTable.addActionListener(listener);
            buttonUpdateCustomerTable.setActionCommand("update");

            buttonExit = new JButton("Exit");
            buttonExit.addActionListener(listener);
            buttonExit.setActionCommand("exit");

            prompt = new JLabel("Search:");
        }
    }

    /**
     * PanelRegCustomer class.
     *
     * Panel for SalesmanGUI.
     * Contains text fields and buttons for registering a new customer.
     */
    private class PanelRegCustomer extends JPanel {
        /** GridBagConstraint for PanelRegCustomer **/
        private GridBagConstraints gbc = new GridBagConstraints();
        private Insets margins = new Insets(5, 5, 5, 5);



        public PanelRegCustomer(){
            // Constructor for PanelRegCustomer
            setLayout(new GridBagLayout());
            setPreferredSize(new Dimension(1024, 200));
            initButtons();
            initRegFields();
            gbc.insets = margins;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;

            /**************************
             * First Column with fields
             **************************/
            /** Organization name **/
            prompt = new JLabel("Organization Name:");
            add(prompt, gbc);
            gbc.gridx += 2;
            add(fieldOrgName, gbc);
            /** Invoice account name **/
            gbc.gridx -= 2;
            gbc.gridy++;
            prompt = new JLabel("Invoice Account:");
            add(prompt, gbc);
            gbc.gridx += 2;
            add(fieldAccount, gbc);
            /** Address **/
            gbc.gridx -= 2;
            gbc.gridy++;
            prompt = new JLabel("*Address:");
            add(prompt, gbc);
            gbc.gridx += 2;
            add(fieldAdr, gbc);

            /***************************
             * Second Column with fields
             ***************************/
            /** First Name **/
            gbc.gridx = 4; // start for second column
            gbc.gridy = 0;
            prompt = new JLabel("*First Name:");
            add(prompt, gbc);
            gbc.gridx += 2;
            add(fieldFname, gbc);
            /** Surname **/
            gbc.gridx -= 2;
            gbc.gridy++;
            prompt = new JLabel("*Surname:");
            add(prompt, gbc);
            gbc.gridx += 2;
            add(fieldSname, gbc);
            /** Zip Code **/
            gbc.gridx -= 2;
            gbc.gridy++;
            prompt = new JLabel("*Zip Code:");
            add(prompt, gbc);
            gbc.gridx += 2;
            gbc.gridwidth = 1;
            add(fieldZipCode, gbc);
            gbc.gridx++;
            gbc.insets = new Insets(5, 11, 5, 5);
            add(fieldCity, gbc);
            gbc.insets = margins;
            gbc.gridwidth = 2;

            /***************************
             * Third Column with fields
             ***************************/
            gbc.gridx = 8;
            gbc.gridy = 0;
            /** Telephone **/
            prompt = new JLabel("*Telephone:");
            add(prompt, gbc);
            gbc.gridx += 2;
            add(fieldTlf, gbc);
            /** E-Mail **/
            gbc.gridx -= 2;
            gbc.gridy++;
            prompt = new JLabel("E-Mail:");
            add(prompt, gbc);
            gbc.gridx += 2;
            add(fieldEmail, gbc);

            /**************************
             * Right buttons
             *************************/
            gbc.gridx = 12;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 50, 5, 5);
            gbc.gridwidth = 1;
            add(buttonSaveCustomer, gbc);
            gbc.gridy++;
            add(buttonCancelReg, gbc);
        }

        /**
         * Method for initializing buttons.
         * Should only be run in the constructor,
         * and only once before adding the buttons
         * to the panel.
         */
        private void initRegFields(){
            fieldID = new JTextField(15);
            fieldOrgName = new JTextField(15);
            fieldFname = new JTextField(15);
            fieldSname = new JTextField(15);
            fieldAdr = new JTextField(15);
            fieldTlf = new JTextField(15);
            fieldEmail = new JTextField(15);
            fieldZipCode = new JTextField(4);
            fieldZipCode.addCaretListener(new Listener());
            fieldCity = new JTextField(9);
            fieldCity.setEditable(false);
            fieldAccount = new JTextField(15);
        }

        public void initButtons(){
            Dimension dimButton = new Dimension(90, 25);
            /** Register button **/
            buttonSaveCustomer = new JButton("Register");
            buttonSaveCustomer.setPreferredSize(dimButton);
            buttonSaveCustomer.addActionListener(listener);
            buttonSaveCustomer.setActionCommand("saveCustomer");

            /** Cancel button **/
            buttonCancelReg = new JButton("Cancel");
            buttonCancelReg.setPreferredSize(dimButton);
            buttonCancelReg.addActionListener(listener);
            buttonCancelReg.setActionCommand("cancelCustomer");
        }
    }

    /**
     * Button Listener class
     *
     * Used with SalesmanGUI for handling actions.
     */
    private class Listener implements ActionListener, CaretListener {
        private Search search = new Search();
        //<editor-fold defaultstate="collapsed" desc="This fold contains variables for making new customer">
        private String orgName;
        private String fName;
        private String sName;
        private String adr;
        private String tlf;
        private String email;
        private String zip;
        private String account;
        private ZipCode zipCode;
        //</editor-fold>

        @Override
        public void caretUpdate(CaretEvent e){
            String city = search.searchForCity(zipCodes, fieldZipCode.getText());
            fieldCity.setText(city);
        }

        @Override
        public void actionPerformed(ActionEvent e){
            String actionCommand = e.getActionCommand();
            switch (actionCommand){
                case "search":
                    ArrayList<Customer> newList = search.searchCustomer(customers, fieldSearch.getText());
                    tableSearch(newList);
                    break;
                case "resetSearch":
                    fieldSearch.setText("");
                    tableSearch(customers);
                    break;
                case "openReg":
                    panelRegCustomer.setVisible(true);
                    setWinSize(dimBigWindow);
                    resetNewCustomerFields();
                    break;
                case "package":
                    System.out.println("select package");
                    findCustomerID();
                    break;
                case "update":
                    System.out.println("Update table");
                    updateCustomerTable();
                    break;
                case "exit":
                    System.out.println("exit");
                    dispose();

                    break;
                case "saveCustomer":
                    // Hide the panel
                    regCustomer();
                    updateCustomerTable();
                    break;
                case "cancelCustomer":
                    // Hide the panel
                    panelRegCustomer.setVisible(false);
                    setWinSize(dimSmallWindow);
                    resetNewCustomerFields();
                    break;
                default:
                    break;
            }
        }

        private void regCustomer(){
            VerifyInput vf = new VerifyInput();

            orgName = (!fieldOrgName.equals(""))? fieldOrgName.getText(): null;
            fName = fieldFname.getText();
            sName = fieldSname.getText();
            adr = fieldAdr.getText();
            tlf = fieldTlf.getText();
            email = (!fieldEmail.equals(""))? fieldEmail.getText(): null;
            account = (!fieldAccount.equals(""))? fieldAccount.getText(): null;
            zip = fieldZipCode.getText();
            zipCode = search.searchZip(zipCodes, zip);
            Customer newCustomer = new Customer(adr, tlf, email, orgName, fName, sName, zipCode, account);
            int check = vf.verifyCustomer(newCustomer);

            switch(check){
                case 0:
                    JOptionPane.showMessageDialog(null, "Everything OK!");
                    db.regCustomer(newCustomer);
                    panelRegCustomer.setVisible(false);
                    setWinSize(dimSmallWindow);
                    resetNewCustomerFields();
                    break;
                case -1:
                    JOptionPane.showMessageDialog(null, "Invoice Account field has the wrong length");
                    break;
                case -2:
                    JOptionPane.showMessageDialog(null, "Telephone number field has the wrong length");
                    break;
                case -3:
                    JOptionPane.showMessageDialog(null, "The zip code doesn't exist");
                    break;
                case -4:
                    JOptionPane.showMessageDialog(null, "First name, surname and address must be filled out");
                    break;
                default:
                    break;
            }

        }

        private void findCustomerID(){
            int column = 0;
            int row = tableCustomers.getSelectedRow();
            if(row >= 0){
                int id = Integer.parseInt((String)tableCustomers.getValueAt(row, column));
                System.out.println("Customer ID: " + id);
            } else {
                JOptionPane.showMessageDialog(null, "You have to select a customer in the table before selecting package");
            }
        }
    }
}