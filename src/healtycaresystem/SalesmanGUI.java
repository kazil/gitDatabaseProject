package healtycaresystem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SalesmanGUI extends JFrame{

    //<editor-fold defaultstate="collapsed" desc="This fold contains object variables">
    private ArrayList<Person> customers;
    private ArrayList<ZipCode> zipCodes;
    private DatabaseOperations db = new DatabaseOperations();
    private String[] tableColNames = {"Customer ID", "Organization Name", "First Name", "Surname", "Address", "Telephone Number", "E-mail"};
    private String[] selections = {"Customer type", "Private", "Business"};
    private Listener listener = new Listener();
    private Dimension dimTable = new Dimension(1024, 200);
    private Dimension dimBigWindow = new Dimension(1100, 550);
    private Dimension dimSmallWindow = new Dimension(1100, 350);
    private VerifyInput vf = new VerifyInput();
    private int employeeID;
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
    private JComboBox<String> selectType;

    /** Components for PanelActions **/
    private JTextField fieldSearch;
    private JButton
            buttonSearch,
            buttonResetSearch,
            buttonRegCustomer,
            buttonPackage,
            buttonUpdateCustomerTable,
            buttonExit,
            buttonOrders;

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="This fold contains all panels">
    private PanelTable panelTable;
    private PanelActions panelActions;
    private PanelRegCustomer panelRegCustomer;
    private JFrame parent = this;
    //</editor-fold>

    /**
     * Constructor for SalesmanGUI.
     */
    public SalesmanGUI(int employeeID){
        this.employeeID = employeeID;
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

        customers = db.getAllCustomers();
        for(Person p : customers){
            String orgName = "Private Customer";
            String account = "Private Customer";
            String id = Integer.toString(p.getCustomerID());
            String fName = p.getFirstName();
            String sName = p.getSurname();
            String adr = p.getAddress();
            String tlf = p.getTelephoneNumber();
            String email = p.getEmail();
            if(p instanceof BusinessCustomer){
                orgName = ((BusinessCustomer) p).getOrgName();
                account = ((BusinessCustomer) p).getInvoiceAccount();
            }
            String[] newRow = {id, orgName, fName, sName, adr, tlf, email};
            tm.addRow(newRow);
        }
    }

    public void setEditableAll(boolean editable){
        fieldOrgName.setEditable(editable);
        fieldFname.setEditable(editable);
        fieldSname.setEditable(editable);
        fieldAdr.setEditable(editable);
        fieldTlf.setEditable(editable);
        fieldEmail.setEditable(editable);
        fieldZipCode.setEditable(editable);
        fieldCity.setEditable(false);
        fieldAccount.setEditable(editable);
    }

    public void editablePrivate(){
        fieldOrgName.setEditable(false);
        fieldFname.setEditable(true);
        fieldSname.setEditable(true);
        fieldAdr.setEditable(true);
        fieldTlf.setEditable(true);
        fieldEmail.setEditable(true);
        fieldZipCode.setEditable(true);
        fieldCity.setEditable(false);
        fieldAccount.setEditable(false);
    }

    /**
     * Populates the table with the given array list
     *
     * @param newList - ArrayList<Customer>
     */
    public void tableSearch(ArrayList<Person> newList){
        // Clear table.
        for(int i = tm.getRowCount() - 1; i >=0; i--){
            tm.removeRow(i);
        }
        // Populate table.
        for(Person p : newList){
            String orgName = "Private Customer";
            String account = "Private Customer";
            String id = Integer.toString(p.getCustomerID());
            String fName = p.getFirstName();
            String sName = p.getSurname();
            String adr = p.getAddress();
            String tlf = p.getTelephoneNumber();
            String email = p.getEmail();
            if(p instanceof BusinessCustomer){
                orgName = ((BusinessCustomer) p).getOrgName();
                account = ((BusinessCustomer) p).getInvoiceAccount();
            }
            String[] newRow = {id, orgName, fName, sName, adr, tlf, email};
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
        selectType.setSelectedIndex(0);
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
        private Insets extMargins = new Insets(5, 150, 5, 5);

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
            /** Look at orders button **/
            gbc.gridx++;
            add(buttonOrders, gbc);
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

            buttonPackage = new JButton("Place Order");
            buttonPackage.addActionListener(listener);
            buttonPackage.setActionCommand("package");

            buttonUpdateCustomerTable = new JButton("Update");
            buttonUpdateCustomerTable.addActionListener(listener);
            buttonUpdateCustomerTable.setActionCommand("update");

            buttonOrders = new JButton("Orders");
            buttonOrders.addActionListener(listener);
            buttonOrders.setActionCommand("orders");

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
            /** Selection box **/
            prompt = new JLabel("*Chose type:");
            add(prompt, gbc);
            gbc.gridx += 2;
            add(selectType, gbc);
            /** Organization name **/
            gbc.gridx -= 2;
            gbc.gridy++;
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
            /** Address **/
            gbc.gridx -= 2;
            gbc.gridy++;
            prompt = new JLabel("*Address:");
            add(prompt, gbc);
            gbc.gridx += 2;
            add(fieldAdr, gbc);


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
            fieldOrgName.setEditable(false);

            fieldFname = new JTextField(15);
            fieldFname.setEditable(false);

            fieldSname = new JTextField(15);
            fieldSname.setEditable(false);

            fieldAdr = new JTextField(15);
            fieldAdr.setEditable(false);

            fieldTlf = new JTextField(15);
            fieldTlf.setEditable(false);

            fieldEmail = new JTextField(15);
            fieldEmail.setEditable(false);

            fieldZipCode = new JTextField(4);
            fieldZipCode.setEditable(false);
            fieldZipCode.addCaretListener(new Listener());

            fieldCity = new JTextField(9);
            fieldCity.setEditable(false);

            fieldAccount = new JTextField(15);
            fieldAccount.setEditable(false);
        }



        public void initButtons(){
            Dimension dimButton = new Dimension(90, 25);
            Dimension dimSelectionBox = new Dimension(150, 25);
            /** Selction box **/
            selectType = new JComboBox<>(selections);
            selectType.setActionCommand("selectionBox");
            selectType.addActionListener(listener);
            selectType.setPreferredSize(dimSelectionBox);

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
                case "selectionBox":
                    String selected = (String)selectType.getSelectedItem();
                    if(selected.equals("Private")){
                        editablePrivate();
                    }else if(selected.equals("Business")){
                        setEditableAll(true);
                    }else {
                        setEditableAll(false);
                    }
                    break;
                case "search":
                    ArrayList<Person> searchList = search.searchCustomers(customers, fieldSearch.getText());
                    tableSearch(searchList);
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
                    SelectPackageDialog pd = new SelectPackageDialog(parent);
                    int customerID = findCustomerID();
                    if(customerID != -1){
                        pd.setVisible(true);
                        Package p = pd.getSelectedPackage();
                        if(p != null){
                            String deliveryDate = pd.getDeliveryDate();
                            Order newOrder = new Order(deliveryDate, 0, p.getPackageID(), customerID, employeeID);
                            if(db.regOrder(newOrder)){
                                JOptionPane.showMessageDialog(null, "Order saved");
                            }else{
                                JOptionPane.showMessageDialog(null, "Could not reach database. Order deleted");
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "You have to select a customer in the table before placing an order.");
                    }
                    break;
                case "update":
                    updateCustomerTable();
                    fieldSearch.setText("");
                    break;
                case "orders":
                    JOptionPane.showMessageDialog(null, "Not implemented");
                    break;
                case "exit":
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
                    setEditableAll(false);
                    resetNewCustomerFields();
                    break;
                default:
                    break;
            }
        }

        private void regCustomer(){

            if(fieldCity.getText().equals("")){
                javax.swing.JOptionPane.showMessageDialog(panelRegCustomer, "Could not find city with given zip code.");
            } else {
                if(selectType.getSelectedIndex() == 1){
                    // Private Customer
                    String fName = fieldFname.getText();
                    String sName = fieldSname.getText();
                    String adr = fieldAdr.getText();
                    String zipCode = fieldZipCode.getText();
                    String city = fieldCity.getText();
                    String tlf = fieldTlf.getText();
                    String email = fieldEmail.getText();
                    PrivateCustomer newCustomer = new PrivateCustomer(fName, sName, adr, new ZipCode(zipCode, city), tlf, email);
                    int check = vf.verifyCustomer(newCustomer);
                    if(check == 0){
                        // OK
                        db.regPrivateCustomer(newCustomer);
                        resetNewCustomerFields();
                        panelRegCustomer.setVisible(false);
                        setWinSize(dimSmallWindow);
                        javax.swing.JOptionPane.showMessageDialog(panelRegCustomer, "Customer saved.");
                    } else {
                        javax.swing.JOptionPane.showMessageDialog(panelRegCustomer, "Check all input boxes.");
                    }
                }
                else if(selectType.getSelectedIndex() == 2){
                    // Business Customer
                    String fName = fieldFname.getText();
                    String sName = fieldSname.getText();
                    String adr = fieldAdr.getText();
                    String zipCode = fieldZipCode.getText();
                    String city = fieldCity.getText();
                    String tlf = fieldTlf.getText();
                    String email = fieldEmail.getText();
                    String account = fieldAccount.getText();
                    String orgName = fieldOrgName.getText();
                    BusinessCustomer newCustomer = new BusinessCustomer(fName, sName, adr, new ZipCode(zipCode, city), tlf, email, account, orgName);
                    int check = vf.verifyCustomer(newCustomer);
                    if(check == 0){
                        // OK
                        db.regBusinessCustomer(newCustomer);
                        resetNewCustomerFields();
                        panelRegCustomer.setVisible(false);
                        setWinSize(dimSmallWindow);
                        javax.swing.JOptionPane.showMessageDialog(panelRegCustomer, "Customer saved.");
                    } else {
                        switch(check){
                            case -2:
                                javax.swing.JOptionPane.showMessageDialog(panelRegCustomer, "Wrong length on telephone number.");
                                break;
                            case -3:
                                javax.swing.JOptionPane.showMessageDialog(panelRegCustomer, "Non-valid email address");
                                break;
                            case -4:
                                javax.swing.JOptionPane.showMessageDialog(panelRegCustomer, "Wrong length on invoice account");
                                break;
                            default:
                                javax.swing.JOptionPane.showMessageDialog(panelRegCustomer, "Something is wrong.");
                                break;
                        }
                    }
                }
                else {
                    javax.swing.JOptionPane.showMessageDialog(panelRegCustomer, "Choose private or business customer.");
                }
            }
        }

        private int findCustomerID(){
            int column = 0;
            int row = tableCustomers.getSelectedRow();
            if(row >= 0){
                int id = Integer.parseInt((String)tableCustomers.getValueAt(row, column));
                return id;
            } else {
                return -1;
            }
        }
    }
}