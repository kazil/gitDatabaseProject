package healtycaresystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class SelectPackageDialog extends JDialog {
    /** Editor Fold **/
    //<editor-fold defaultstate="collapsed" desc="Object variables">
    private DatabaseOperations db = new DatabaseOperations();
    private ArrayList<Package> packages;
    private JTable tablePackages;
    private DefaultTableModel tm;
    private JScrollPane scrollTablePackages;
    private final String[] columnNames = {"Package ID", "Name"};
    private final int tableHeight = 200;
    private final int tableWidth = 450;
    private Dimension dimTable = new Dimension(tableWidth, tableHeight);
    private Insets margins = new Insets(5, 5, 5, 5);
    private Package selectedPackage;
    private String deliveryDate = "";
    private JButton buttonSelect, buttonCancel;
    private String[] yearSelections;
    private final String[] monthSelections = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private String[] daySelections;
    private String[] februaryDaySelections;

    private JCheckBox checkBoxOverTime = new JCheckBox();
    private JComboBox<String> comboFromYear;
    private JComboBox<String> comboFromMonth;
    private JComboBox<String> comboFromDay;

    private JComboBox<String> comboToYear;
    private JComboBox<String> comboToMonth;
    private JComboBox<String> comboToDay;
    private VerifyInput vf = new VerifyInput();
    //</editor-fold>

    public SelectPackageDialog(JFrame parent){
        super(parent,  true);
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        gb.gridx = 0;
        gb.gridy = 0;
        gb.gridwidth = 2;
        gb.insets = margins;
        initTable();
        initButtons();
        updateTable();
        add(scrollTablePackages, gb);

        gb.gridwidth = 1;
        gb.gridx = 0;
        gb.gridy = 1;
        add(new PanelButtons(), gb);

        pack();
    }

    /** Editor Fold **/
    //<editor-fold defaultstate="collapsed" desc="Initiation methods">
    private void initTable(){
        tm = new DefaultTableModel(columnNames, 0){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tablePackages = new JTable(tm);
        tablePackages.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollTablePackages = new JScrollPane(tablePackages);
        scrollTablePackages.setPreferredSize(dimTable);

        updateTable();
    }

    private void initButtons(){
        buttonSelect = new JButton("OK");
        buttonSelect.setActionCommand("ok");
        buttonSelect.addActionListener(new Listener());

        buttonCancel = new JButton("Cancel");
        buttonCancel.setActionCommand("cancel");
        buttonCancel.addActionListener(new Listener());
    }

    private void initSelections(){
        int count = 1;
        daySelections = new String[31];
        for(int i = 0; i < 31; i++){
            daySelections[i] = Integer.toString(count);
            count++;
        }
        februaryDaySelections = new String[28];
        count = 1;
        for(int i = 0; i < 28; i++){
            februaryDaySelections[i] = Integer.toString(count);
            count++;
        }

        yearSelections = new String[3];
        GregorianCalendar cal = new GregorianCalendar();
        int year = cal.get(GregorianCalendar.YEAR);
        yearSelections[0] = Integer.toString(year);
        yearSelections[1] = Integer.toString(year+1);
        yearSelections[2] = Integer.toString(year+2);
    }

    private void initCombos(){
        initSelections();
        comboFromYear = new JComboBox<>(yearSelections);
        comboFromMonth = new JComboBox<>(monthSelections);
        comboFromDay = new JComboBox<>(daySelections);

        comboToYear = new JComboBox<>(yearSelections);
        comboToMonth = new JComboBox<>(monthSelections);
        comboToDay = new JComboBox<>(daySelections);
        /** Sets to-date uneditable **/
        comboToYear.setEnabled(false);
        comboToMonth.setEnabled(false);
        comboToDay.setEnabled(false);

        GregorianCalendar cal = new GregorianCalendar();
        comboFromMonth.setSelectedIndex(cal.get(GregorianCalendar.MONTH));
        comboFromDay.setSelectedIndex(cal.get(GregorianCalendar.DATE) - 1);

        comboToMonth.setSelectedIndex(cal.get(GregorianCalendar.MONTH));
        comboToDay.setSelectedIndex(cal.get(GregorianCalendar.DATE) - 1);
    }
    //</editor-fold>

    public void updateTable(){
        // Clear table.
        for(int i = tm.getRowCount() - 1; i >=0; i--){
            tm.removeRow(i);
        }

        packages = db.getPackages();
        for(Package p : packages){
            String id = Integer.toString(p.getPackageID());
            String name = p.getPackageName();

            String[] newRow = {id, name};
            tm.addRow(newRow);
        }
    }

    public Package getSelectedPackage(){
        return selectedPackage;
    }

    public String getDeliveryDate(){
        return deliveryDate;
    }

    private class PanelButtons extends JPanel{
        public PanelButtons(){
            setLayout(new GridBagLayout());
            GridBagConstraints gb = new GridBagConstraints();

            initCombos();

            gb.insets = margins;

            gb.gridx = 0;
            gb.gridy = 0;
            gb.gridwidth = 2;
            add(new JLabel("Order over time?"), gb);

            gb.gridx += 2;
            gb.gridwidth = 1;
            checkBoxOverTime.setActionCommand("radioButton");
            checkBoxOverTime.addActionListener(new Listener());
            add(checkBoxOverTime, gb);

            gb.gridx = 0;
            gb.gridy++;
            gb.gridwidth = 2;
            add(new JLabel("Select Start Date:"), gb);

            gb.gridwidth = 1;
            gb.gridx += 2;
            add(comboFromYear, gb);

            gb.gridx++;
            add(comboFromMonth, gb);

            gb.gridx++;
            add(comboFromDay, gb);

            gb.gridx = 0;
            gb.gridy++;
            gb.gridwidth = 2;
            add(new JLabel("Select End Date:"), gb);

            gb.gridwidth = 1;
            gb.gridx += 2;
            add(comboToYear, gb);

            gb.gridx++;
            add(comboToMonth, gb);

            gb.gridx++;
            add(comboToDay, gb);

            gb.gridwidth = 1;
            gb.gridx = 0;
            gb.gridy++;
            add(buttonSelect, gb);
            gb.gridx++;
            add(buttonCancel, gb);

            pack();
        }
    }

    private class Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String actionCommand = e.getActionCommand();
            switch(actionCommand){
                case "ok":
                    if(verifyDeliveryDate()){
                        if(verifySelectedPackage()){
                            dispose();
                        }else{
                            javax.swing.JOptionPane.showMessageDialog(null, "Select a package.");
                        }
                    }else{
                        javax.swing.JOptionPane.showMessageDialog(null, "You have selected a date backwards in time.");
                    }
                    break;
                case "cancel":
                    dispose();
                    break;
                case "radioButton":
                    if(checkBoxOverTime.isSelected()){
                        comboToYear.setEnabled(true);
                        comboToMonth.setEnabled(true);
                        comboToDay.setEnabled(true);
                    }else{
                        comboToYear.setEnabled(false);
                        comboToMonth.setEnabled(false);
                        comboToDay.setEnabled(false);
                    }
                    break;
                default:
                    break;
            }
        }

        private boolean verifyDeliveryDate(){
            int year = Integer.parseInt((String) comboFromYear.getSelectedItem());
            int month = comboFromMonth.getSelectedIndex()+1;
            int day = comboFromDay.getSelectedIndex()+1;
            if(vf.verifyDate(year,month,day)){
                deliveryDate = year+"-"+month+"-"+day;
                return true;
            }
            return false;
        }

        private boolean verifySelectedPackage(){
            int column = 0;
            int row = tablePackages.getSelectedRow();
            if(row >= 0){
                int id = Integer.parseInt((String)tablePackages.getValueAt(row, column));
                for(Package p : packages){
                    if(p.getPackageID() == id){
                        selectedPackage = p;
                        return true;
                    }
                }
                return false;
            } else {
                return false;
            }
        }
    }
}
