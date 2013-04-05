package healtycaresystem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CeoGUI extends JFrame {
    //<editor-fold defaultstate="collapsed" desc="Variables">
    private JTabbedPane tp;
    private Dimension dimPanels = new Dimension(620, 570);
    private Dimension dimIncomePanel = new Dimension(270, 160);
    private Dimension dimExpensesPanel = new Dimension(270, 300);
    private Dimension dimLine = new Dimension(50, 10);
    private Insets margins = new Insets(5, 5, 5, 5);

    private EconomicOverview panelEconomic;
    private InsuranceOverview panelInsurance;
    private ElectricityAndRentOverview panelElAndRent;

    private JComboBox<String> comboYear;
    private JComboBox<String> comboMonth;
    private String[] listYear = {"2013", "2012", "2011", "2010"};
    private String[] listMonth = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private int employeeID;

    private JLabel
            labelPrivateSales,
            labelOrgSales,
            labelTotalSales,
            labelIncomeOther,
            labelTotalIncome;

    
    //</editor-fold>

    public CeoGUI(int employeeID){
        this.employeeID = employeeID;
        setTitle("CEO Interface");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());

        //TODO Build window.
        initTabbedPane();
        add(tp);

        pack();
    }

    private void initTabbedPane(){
        tp = new JTabbedPane();
        panelEconomic = new EconomicOverview();
        panelInsurance = new InsuranceOverview();
        panelElAndRent = new ElectricityAndRentOverview();
        tp.addTab("Economic overview", panelEconomic);
        tp.addTab("Insurance overview", panelInsurance);
        tp.addTab("Electricity and Rent", panelElAndRent);

    }

    //<editor-fold defaultstate="collapsed" desc="First Tab">
    /** First tab. **/
    private class EconomicOverview extends JPanel {
        private IncomePanel panelIncome;
        private ExpensesPanel panelExpenses;
        private RevenuePanel panelRevenue;

        public EconomicOverview(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimPanels);
            GridBagConstraints gb = new GridBagConstraints();

            gb.insets = margins;
            gb.anchor = GridBagConstraints.FIRST_LINE_START;

            initPanels();

            gb.gridx = 0;
            gb.gridy = 0;
            JLabel title = new JLabel("Economic overview");
            title.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 18));
            add(title, gb);

            gb.gridy++;
            add(new ComboPanel(), gb);


            gb.gridy++;
            add(panelIncome, gb);

            gb.gridy++;

            add(panelExpenses, gb);

            gb.insets = new Insets(5, 25, 5, 5);
            gb.gridy = 2;
            gb.gridx = 1;
            gb.weightx = 1.0;
            gb.weighty = 1.0;
            add(panelRevenue, gb);

        }

        public void initPanels(){
            panelIncome = new IncomePanel();
            Border grayLine = BorderFactory.createLineBorder(Color.GRAY);
            TitledBorder borderTitle = BorderFactory.createTitledBorder(grayLine, "Income");
            panelIncome.setBorder(borderTitle);

            panelExpenses = new ExpensesPanel();
            grayLine = BorderFactory.createLineBorder(Color.GRAY);
            borderTitle = BorderFactory.createTitledBorder(grayLine, "Expenses");
            panelExpenses.setBorder(borderTitle);

            panelRevenue = new RevenuePanel();
            grayLine = BorderFactory.createLineBorder(Color.GRAY);
            borderTitle = BorderFactory.createTitledBorder(grayLine, "Revenue");
            panelRevenue.setBorder(borderTitle);
        }
    }

    /** Is inside the Economic overview. **/
    private class IncomePanel extends JPanel {
        private JLabel prompt;

        public IncomePanel(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimIncomePanel);
            GridBagConstraints gb = new GridBagConstraints();

            gb.anchor = GridBagConstraints.LINE_START;

            updateNumbers();


            gb.gridx = 0;
            gb.gridy = 0;
            gb.insets = margins;

            prompt = new JLabel("Private sales:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelPrivateSales, gb);


            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Organization sales:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelOrgSales, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Total sales:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelTotalSales, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Other:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelIncomeOther, gb);

            /** Seperator line **/
            gb.fill = GridBagConstraints.BOTH;
            gb.anchor = GridBagConstraints.LINE_START;
            gb.gridy++;
            gb.gridx--;
            gb.gridwidth = 2;
            JSeparator line = new JSeparator(JSeparator.HORIZONTAL);
            line.setBackground(Color.BLACK);
            line.setForeground(Color.BLACK);
            line.setPreferredSize(dimLine);
            add(line, gb);

            gb.gridwidth = 1;
            gb.gridy++;
            gb.fill = GridBagConstraints.NONE;
            prompt = new JLabel("Total:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelTotalIncome, gb);

            gb.weighty = 1.0;
            gb.weightx = 1.0;
            gb.gridy++;
            gb.gridy--;
            gb.anchor = GridBagConstraints.LINE_START;
            add(new JLabel(""), gb);
        }

        private void updateNumbers(){
            int privateSales = 14500;
            int orgSales = 37000;
            int totalSales = privateSales + orgSales;
            int other = 1000;
            int totalIncome = totalSales + other;

            String strPrivateSales = String.format("%,d", privateSales);
            String strOrgSales = String.format("%,d", orgSales);
            String strTotalSales = String.format("%,d", totalSales);
            String strOther = String.format("%,d", other);
            String strTotalIncome = String.format("%,d", totalIncome);

            labelPrivateSales = new JLabel(strPrivateSales + ",-");
            labelPrivateSales.setForeground(new Color(0.0f, 0.7f, 0.3f));

            labelOrgSales = new JLabel(strOrgSales + ",-");
            labelOrgSales.setForeground(new Color(0.0f, 0.7f, 0.3f));

            labelTotalSales = new JLabel(strTotalSales + ",-");
            labelTotalSales.setForeground(new Color(0.0f, 0.7f, 0.3f));

            labelIncomeOther = new JLabel(strOther + ",-");
            labelIncomeOther.setForeground(new Color(0.0f, 0.7f, 0.3f));

            labelTotalIncome = new JLabel(strTotalIncome + ",-");
            labelTotalIncome.setForeground(new Color(0.0f, 0.7f, 0.3f));
        }
    }

    /** Is inside the Economic overview. **/
    private class ExpensesPanel extends JPanel {
        private JLabel prompt;

        public ExpensesPanel(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimExpensesPanel);
            GridBagConstraints gb = new GridBagConstraints();
            gb.anchor = GridBagConstraints.FIRST_LINE_START;

            gb.gridx = 0;
            gb.gridy = 0;
            gb.insets = margins;
            prompt = new JLabel("Salary:");
            add(prompt, gb);

            gb.gridy++;
            prompt = new JLabel("Ingredients:");
            add(prompt, gb);

            gb.gridy++;
            prompt = new JLabel("Gas:");
            add(prompt, gb);

            gb.gridy++;
            prompt = new JLabel("Electricity:");
            add(prompt, gb);

            gb.gridy++;
            prompt = new JLabel("Equipment:");
            add(prompt, gb);

            gb.gridy++;
            prompt = new JLabel("Maintenance:");
            add(prompt, gb);

            gb.gridy++;
            prompt = new JLabel("Insurance:");
            add(prompt, gb);

            gb.gridy++;
            prompt = new JLabel("Rent:");
            add(prompt, gb);

            gb.gridy++;
            prompt = new JLabel("Other:");
            add(prompt, gb);

            gb.gridy++;
            gb.fill = GridBagConstraints.BOTH;
            JSeparator line = new JSeparator(JSeparator.HORIZONTAL);
            line.setBackground(Color.BLACK);
            line.setForeground(Color.BLACK);
            line.setPreferredSize(dimLine);
            add(line, gb);

            gb.weighty = 1.0;
            gb.weightx = 1.0;
            gb.gridy++;
            prompt = new JLabel("Total:");
            add(prompt, gb);
        }
    }

    /** Is inside the Economic overview. **/
    private class RevenuePanel extends JPanel{
        private JLabel prompt;

        public RevenuePanel(){
            setLayout(new GridBagLayout());
            GridBagConstraints gb = new GridBagConstraints();
            setPreferredSize(dimIncomePanel);

            gb.anchor = GridBagConstraints.FIRST_LINE_START;

            gb.gridx = 0;
            gb.gridy = 0;
            gb.insets = margins;

            prompt = new JLabel("Revenue before taxes:");
            add(prompt, gb);

            gb.gridy++;
            prompt = new JLabel("VAT:");
            add(prompt, gb);

            gb.gridy++;
            prompt = new JLabel("Taxes:");
            add(prompt, gb);

            gb.gridy++;
            add(new JLabel(" "), gb);

            /** Seperator Line **/
            gb.gridy++;
            gb.fill = GridBagConstraints.BOTH;
            JSeparator line = new JSeparator(JSeparator.HORIZONTAL);
            line.setBackground(Color.BLACK);
            line.setForeground(Color.BLACK);
            line.setPreferredSize(dimLine);
            add(line, gb);

            gb.gridy++;
            prompt = new JLabel("Revenue after taxes:");
            add(prompt, gb);

            gb.weighty = 1.0;
            gb.weightx = 1.0;
            gb.gridy++;
            add(new JLabel(""), gb);
        }
    }

    /** Is inside the Economic overview **/
    private class ComboPanel extends JPanel {
        private JLabel prompt;

        public ComboPanel(){
            setLayout(new FlowLayout());
            initCombos();
            prompt = new JLabel("Year:");
            add(prompt);
            add(comboYear);
            prompt = new JLabel("Month:");
            add(prompt);
            add(comboMonth);
        }
        public void initCombos(){
            comboYear = new JComboBox<>(listYear);
            comboMonth = new JComboBox<>(listMonth);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Second Tab">
    /** Second tab. **/
    private class InsuranceOverview extends JPanel {
        public InsuranceOverview(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimPanels);

            add(new JLabel("Insurance Overview:"));
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Third Tab">
    /** Third tab. **/
    private class ElectricityAndRentOverview extends JPanel {
        public ElectricityAndRentOverview(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimPanels);

            add(new JLabel("Electricity and Rent"));
        }
    }
    //</editor-fold>

}
