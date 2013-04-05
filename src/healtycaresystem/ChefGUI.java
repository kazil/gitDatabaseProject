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

public class ChefGUI extends JFrame{
    private ChefRegistry orderReg;
    //<editor-fold defaultstate="collapsed" desc="This fold contains object variables">
    private String[] tableColNames = {"Order ID", "Recipes", "Status"};
    private String[] tableRecipes = {"Recipe type", "Number #"};
    private Listener listener = new Listener();
    private Dimension dimTable = new Dimension(1024, 200);
    private Dimension dimBigWindow = new Dimension(1100, 550);
    private Dimension dimSmallWindow = new Dimension(1100, 350);
    private int employeeID;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="This fold contains components for the GUI">

    private JLabel prompt;

    /** Customer table and default table model.**/
    private JTable tableOrders;
    private JScrollPane scrollTableOrders;
    private DefaultTableModel tm;


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
    public ChefGUI(int employeeID){
        this.employeeID = employeeID;
        this.orderReg = new ChefRegistry(employeeID);
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
    public void updateOrderTable(String date){
        // Clear table.
        for(int i = tm.getRowCount() - 1; i >=0; i--){
            tm.removeRow(i);
        }

        ArrayList<Order> newOrder = orderReg.updateCustomerTable();
        for(Order o : newOrder){
            if (date.equals(o.getDeliveryDate())){
                String id = Integer.toString(o.getOrderID());
                String pId = Integer.toString(o.getPackageID());
                String status = Integer.toString(o.getOrderStatus());
                String[] newRow = new String[]{id, pId, status};
                tm.addRow(newRow);
            }



        }

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
            add(scrollTableOrders, gbc);
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
            tableOrders = new JTable(tm);
            tableOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollTableOrders = new JScrollPane(tableOrders);
            scrollTableOrders.setPreferredSize(dimTable);

            updateOrderTable("test");
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
            initTable();
            gbc.insets = margins;
            gbc.anchor = GridBagConstraints.FIRST_LINE_START;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
        }

        public void initTable(){

        }


        public void initButtons(){
            Dimension dimButton = new Dimension(90, 25);
            Dimension dimSelectionBox = new Dimension(150, 25);

        }
    }

    /**
     * Button Listener class
     *
     * Used with SalesmanGUI for handling actions.
     */
    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e){
            String actionCommand = e.getActionCommand();
            switch (actionCommand){
                 case "openReg":
                    panelRegCustomer.setVisible(true);
                    setWinSize(dimBigWindow);
                    break;
                case "update":
                    updateOrderTable("Test");
                    fieldSearch.setText("");
                    break;
                case "orders":
                    JOptionPane.showMessageDialog(null, "Not implemented");
                    break;
                case "exit":
                    dispose();
                    break;
                default:
                    break;
            }
        }


        private int findCustomerID(){
            int column = 0;
            int row = tableOrders.getSelectedRow();
            if(row >= 0){
                int id = Integer.parseInt((String) tableOrders.getValueAt(row, column));
                return id;
            } else {
                return -1;
            }
        }
    }
}
