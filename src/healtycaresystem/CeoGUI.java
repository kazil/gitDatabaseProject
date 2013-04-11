package healtycaresystem;

import com.sun.org.glassfish.external.statistics.Statistic;
import sun.misc.JavaxSecurityAuthKerberosAccess;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CeoGUI extends JFrame {
    //<editor-fold defaultstate="collapsed" desc="Variables">
    private JTabbedPane tp;
    private Dimension dimPanels = new Dimension(620, 570);
    private Dimension dimIncomePanel = new Dimension(270, 160);
    private Dimension dimExpensesPanel = new Dimension(270, 300);
    private Dimension dimLine = new Dimension(50, 10);
    private Insets margins = new Insets(5, 5, 5, 5);

    private EconomicOverview panelEconomic;
    private InsuranceElRentOverview panelInsuranceElRent;
    private StatisticsOverview panelStatistics;

    private JComboBox<String> comboYear;
    private JComboBox<String> comboMonth;
    private String[] listYear = {"2013", "2012", "2011", "2010"};
    private String[] listMonth = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    private int employeeID;

    private JFrame parent = this;

    private JLabel

            /*Economic overview*/

            /*Income*/
            labelPrivateSales,
            labelOrgSales,
            labelTotalSales,
            labelIncomeOther,
            labelTotalIncome,

            /*Expenses*/
            labelSalary,
            labelIngredients,
            labelGas,
            labelElectricity,
            labelEquipment,
            labelMaintenance,
            labelInsurance,
            labelRent,
            labelOtherExpenses,
            labelTotalExpences,

            /*Revenue*/
            labelBeforeTaxes,
            labelVAT,
            labelTaxes,
            labelAfterTaxes,

            /* Insurance, el, rent*/

            /*Insurance*/
            labelCar,
            labelHousehold,
            labelWorkersComp,
            labelTotalInsurance,

            /*Electricity*/

            /*Rent*/
            labelHouse;
    
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
        panelInsuranceElRent = new InsuranceElRentOverview();
        panelStatistics = new StatisticsOverview();
        tp.addTab("Economic overview", panelEconomic);
        tp.addTab("Insurance, electricity and rent overview", panelInsuranceElRent);
        tp.addTab("Statistics", panelStatistics);

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
    }

    /** Is inside the Economic overview. **/
    private class ExpensesPanel extends JPanel {
        private JLabel prompt;

        public ExpensesPanel(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimExpensesPanel);
            GridBagConstraints gb = new GridBagConstraints();
            gb.anchor = GridBagConstraints.FIRST_LINE_START;

            updateNumbers();

            gb.gridx = 0;
            gb.gridy = 0;
            gb.insets = margins;

            prompt = new JLabel("Salary:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelSalary, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Ingredients:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelIngredients, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Gas:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelGas, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Electricity:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelElectricity, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Equipment:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelEquipment, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Maintenance:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelMaintenance, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Insurance:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelInsurance, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Rent:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelRent, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Other:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelOtherExpenses, gb);

            gb.gridy++;
            gb.gridx--;
            gb.gridwidth = 2;
            gb.anchor = GridBagConstraints.LINE_START;
            gb.fill = GridBagConstraints.BOTH;
            JSeparator line = new JSeparator(JSeparator.HORIZONTAL);
            line.setBackground(Color.BLACK);
            line.setForeground(Color.BLACK);
            line.setPreferredSize(dimLine);
            add(line, gb);

            gb.weighty = 1.0;
            gb.weightx = 1.0;
            gb.gridy++;
            gb.gridwidth = 1;
            gb.fill = GridBagConstraints.NONE;
            prompt = new JLabel("Total:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelTotalExpences, gb);
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

            updateNumbers();

            gb.gridx = 0;
            gb.gridy = 0;
            gb.insets = margins;

            prompt = new JLabel("Revenue before taxes:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelBeforeTaxes, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("VAT:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelVAT, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Taxes:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelTaxes, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            add(new JLabel(" "), gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(new JLabel(" "), gb);

            /** Seperator Line **/
            gb.gridy++;
            gb.gridx--;
            gb.gridwidth = 2;
            gb.anchor = GridBagConstraints.LINE_START;
            gb.fill = GridBagConstraints.BOTH;
            JSeparator line = new JSeparator(JSeparator.HORIZONTAL);
            line.setBackground(Color.BLACK);
            line.setForeground(Color.BLACK);
            line.setPreferredSize(dimLine);
            add(line, gb);

            gb.gridwidth = 1;
            gb.gridy++;
            gb.fill = GridBagConstraints.NONE;
            prompt = new JLabel("Revenue after taxes:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelAfterTaxes, gb);

            gb.weighty = 1.0;
            gb.weightx = 1.0;
            gb.gridy++;
            gb.gridy--;
            gb.anchor = GridBagConstraints.LINE_START;
            add(new JLabel(""), gb);
        }


    }

    /**
     * Method for updating numbers in the overview.
     */
    public void updateNumbers(){
        /*Income*/
        int privateSales = 101450;
        int orgSales = 253700;
        int totalSales = privateSales + orgSales;
        int other = 45000;
        int totalIncome = totalSales + other;

        /*Expenses*/
        int salary = 54600;
        int ingredients = 45000;
        int gas = 13000;
        int electricity = 17000;
        int equipment = 30000;
        int maintenance = 20000;
        int insurance = 40000;
        int rent = 45000;
        int otherExpenses = 13000;
        int totalExpenses = salary + ingredients + gas + electricity + equipment + maintenance + insurance + rent + otherExpenses;

        /*Revenue*/
        int beforeTaxes = totalIncome - totalExpenses;
        int VAT = (int)(totalIncome * 0.25);
        int taxes = (int)((totalIncome - VAT) * 0.28);
        int afterTaxes = beforeTaxes - VAT - taxes;

        /*Income*/
        String strPrivateSales = String.format("%,d", privateSales);
        String strOrgSales = String.format("%,d", orgSales);
        String strTotalSales = String.format("%,d", totalSales);
        String strOther = String.format("%,d", other);
        String strTotalIncome = String.format("%,d", totalIncome);

        /*Expenses*/
        String strSalary = String.format("%,d", salary);
        String strIngredients = String.format("%,d", ingredients);
        String strGas = String.format("%,d", gas);
        String strElectricity = String.format("%,d", electricity);
        String strEquipment = String.format("%,d", equipment);
        String strMaintenance = String.format("%,d", maintenance);
        String strInsurance = String.format("%,d", insurance);
        String strRent = String.format("%,d", rent);
        String strOtherExpenses = String.format("%,d", otherExpenses);
        String strTotalExpenses = String.format("%,d", totalExpenses);

        /*Revenue*/
        String strBeforeTaxes = String.format("%,d", beforeTaxes);
        String strVAT = String.format("%,d", VAT);
        String strTaxes = String.format("%,d", taxes);
        String strAfterTaxes = String.format("%,d", afterTaxes);

        /*Income*/
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

        /*Expenses*/
        labelSalary = new JLabel("-" + strSalary + ",-");
        labelSalary.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelIngredients = new JLabel("-" + strIngredients + ",-");
        labelIngredients.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelGas = new JLabel("-" + strGas + ",-");
        labelGas.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelElectricity = new JLabel("-" + strElectricity + ",-");
        labelElectricity.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelEquipment = new JLabel("-" + strEquipment + ",-");
        labelEquipment.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelMaintenance = new JLabel("-" + strMaintenance + ",-");
        labelMaintenance.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelInsurance = new JLabel("-" + strInsurance + ",-");
        labelInsurance.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelRent = new JLabel("-" + strRent + ",-");
        labelRent.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelOtherExpenses = new JLabel("-" + strOtherExpenses + ",-");
        labelOtherExpenses.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelTotalExpences = new JLabel("-" + strTotalExpenses + ",-");
        labelTotalExpences.setForeground(new Color(1.0f, 0.0f, 0.0f));

        /*Revenue*/
        labelBeforeTaxes = new JLabel(strBeforeTaxes + ",-");
        if(beforeTaxes > 0){
            labelBeforeTaxes.setForeground(new Color(0.0f, 0.7f, 0.3f));
        }else{
            labelBeforeTaxes.setForeground(new Color(1.0f, 0.0f, 0.0f));
        }

        labelVAT = new JLabel("-" + strVAT + ",-");
        labelVAT.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelTaxes = new JLabel("-" + strTaxes + ",-");
        labelTaxes.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelAfterTaxes = new JLabel(strAfterTaxes + ",-");
        if(afterTaxes > 0){
            labelAfterTaxes.setForeground(new Color(0.0f, 0.7f, 0.3f));
        }else{
            labelAfterTaxes.setForeground(new Color(1.0f, 0.0f, 0.0f));
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
    private class InsuranceElRentOverview extends JPanel{
        private InsurancePanel panelInsurance;
        private ElectricityPanel panelElectricity;
        private RentPanel panelRent;

        private JButton button = new JButton();
        //skal den stå her?

        public InsuranceElRentOverview(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimPanels);
            GridBagConstraints gb = new GridBagConstraints();

            gb.insets = margins;
            //gb.fill = GridBagConstraints.BOTH;
            gb.anchor = GridBagConstraints.FIRST_LINE_START;

            initPanelsTabTwo();

            gb.gridx = 0;
            gb.gridy = 0;
            JLabel title = new JLabel("Insurance, electricity, rent");
            title.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 18));
            add(title, gb);

            //gb.weightx = 1;
            //gb.weighty = 1;
            gb.gridy++;
            add(panelInsurance, gb);

            //gb.weightx = 1;
            //gb.weighty = 1;
            gb.gridy++;
            add(panelElectricity, gb);

            //gb.insets = new Insets(5, 25, 5, 5);
            gb.gridy = 1;
            gb.gridx = 1;
            //gb.weightx = 1.0;
            //gb.weighty = 1.0;
            add(panelRent, gb);

           // gb.weightx = 1.0;
            //gb.weighty = 1.0;
            gb.gridy++;
            add(button, gb);
            button.setText("Edit values");
            button.addActionListener(new ButtonListener());

        }

        private class ButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                CeoEdit edit = new CeoEdit(parent);
                edit.setVisible(true);
            }

        }

        public void initPanelsTabTwo(){
            panelInsurance = new InsurancePanel();
            Border grayLine = BorderFactory.createLineBorder(Color.GRAY);
            TitledBorder borderTitle = BorderFactory.createTitledBorder(grayLine, "Insurance");
            panelInsurance.setBorder(borderTitle);

            panelElectricity = new ElectricityPanel();
            grayLine = BorderFactory.createLineBorder(Color.GRAY);
            borderTitle = BorderFactory.createTitledBorder(grayLine, "Electricity");
            panelElectricity.setBorder(borderTitle);

            panelRent = new RentPanel();
            grayLine = BorderFactory.createLineBorder(Color.GRAY);
            borderTitle = BorderFactory.createTitledBorder(grayLine, "Rent");
            panelRent.setBorder(borderTitle);
        }

    }

    private class InsurancePanel extends JPanel {
        private JLabel prompt;
        private JButton edit;

        public InsurancePanel(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimIncomePanel);
            GridBagConstraints gb = new GridBagConstraints();

            gb.anchor = GridBagConstraints.LINE_START;

            updateNumbersTabTwo();

            gb.gridx = 0;
            gb.gridy = 0;
            gb.insets = margins;

            prompt = new JLabel("Car insurance:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelCar, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Household insurance:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelHousehold, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Workers' compensation insurance:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelWorkersComp, gb);

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

            gb.gridy++;
            prompt = new JLabel("Total:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelTotalInsurance, gb);
        }


    }

    private class ElectricityPanel extends JPanel {
        private JLabel prompt;

        public ElectricityPanel(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimPanels);
            GridBagConstraints gb = new GridBagConstraints();

            gb.insets = margins;
            gb.anchor = GridBagConstraints.FIRST_LINE_START;

            gb.gridx = 0;
            gb.gridy = 0;
            JLabel title = new JLabel("Electricity");
            //title.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 18));
            add(title, gb);
        }
    }

    private class RentPanel extends JPanel {
        private JLabel prompt;

        public RentPanel(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimIncomePanel);
            GridBagConstraints gb = new GridBagConstraints();

            gb.anchor = GridBagConstraints.LINE_START;

            updateNumbersTabTwo();

            gb.gridx = 0;
            gb.gridy = 0;
            gb.insets = margins;

            prompt = new JLabel("House rent:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(labelHouse, gb);

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
        }
    }

    public void updateNumbersTabTwo(){
        /*Insurance*/
        int car = 101450;
        int household = 24500;
        int workersComp = 13600;
        int totalInsurance = car + household + workersComp;

        /*Rent*/
        int house = 20000;

        /*Insurance*/
        String strCar = String.format("%,d", car);
        String strHousehold = String.format("%,d", household);
        String strWorkersComp = String.format("%,d", workersComp);
        String strTotalInsurance = String.format("%,d", totalInsurance);

        /*Rent*/
        String strHouse = String.format("%,d", house);

        /*Insurance*/
        labelCar = new JLabel("-" + strCar + ",-");
        labelCar.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelHousehold = new JLabel("-" + strHousehold + ",-");
        labelHousehold.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelWorkersComp = new JLabel("-" + strWorkersComp + ",-");
        labelWorkersComp.setForeground(new Color(1.0f, 0.0f, 0.0f));

        labelTotalInsurance = new JLabel("-" + strTotalInsurance + ",-");
        labelTotalInsurance.setForeground(new Color(1.0f, 0.0f, 0.0f));

        /*Rent*/
        labelHouse = new JLabel("-" + strHouse + ",-");
        labelHouse.setForeground(new Color(1.0f, 0.0f, 0.0f));
    }


    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Third Tab">
    /** Third tab. **/
    private class StatisticsOverview extends JPanel {
        public StatisticsOverview(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimPanels);
            GridBagConstraints gb = new GridBagConstraints();

            gb.insets = margins;
            gb.anchor = GridBagConstraints.FIRST_LINE_START;

            gb.gridx = 0;
            gb.gridy = 0;
            JLabel title = new JLabel("Statistics");
            title.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 18));
            add(title, gb);
        }
    }
    //</editor-fold>

}
