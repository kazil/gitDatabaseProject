package healtycaresystem;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CeoEdit extends JDialog {

    //<editor-fold defaultstate="collapsed" desc="Variables">
    private Dimension dimPanels = new Dimension(310, 240);
    private Dimension dimInsurancePanel = new Dimension(290, 120);
    private Dimension dimRentPanel = new Dimension(290, 55);
    private Insets margins = new Insets(5, 5, 5, 5);
    private Panel panel = new Panel();

    private JTextField
            carInsurance,
            householdInsurance,
            workersInsurance,
            houseRent;
    //</editor-fold>

    public CeoEdit(JFrame parent){
        super(parent, true);
        setTitle("Edit values");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        //setLayout(new GridBagLayout());

        add(panel);

        pack();
    }

    private class Panel extends JPanel{
        private InsurancePanel panelInsurance;
        private RentPanel panelRent;

        public Panel(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimPanels);
            GridBagConstraints gb = new GridBagConstraints();

            gb.insets = margins;
            gb.anchor = GridBagConstraints.LINE_START;

            initPanels();

            gb.gridx = 0;
            gb.gridy = 0;
            JLabel title = new JLabel("Edit values");
            title.setFont(new Font(Font.SANS_SERIF,Font.BOLD, 18));
            add(title, gb);

            gb.gridy++;
            add(panelInsurance, gb);

            gb.gridy++;
            add(panelRent, gb);
        }

        public void initPanels(){
            panelInsurance = new InsurancePanel();
            Border grayLine = BorderFactory.createLineBorder(Color.GRAY);
            TitledBorder borderTitle = BorderFactory.createTitledBorder(grayLine, "Insurance");
            panelInsurance.setBorder(borderTitle);

            panelRent = new RentPanel();
            grayLine = BorderFactory.createLineBorder(Color.GRAY);
            borderTitle = BorderFactory.createTitledBorder(grayLine, "Rent");
            panelRent.setBorder(borderTitle);
        }


    }

    private class InsurancePanel extends JPanel{
        private JLabel prompt;

        public InsurancePanel(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimInsurancePanel);
            GridBagConstraints gb = new GridBagConstraints();

            gb.anchor = GridBagConstraints.LINE_START;

            gb.gridx = 0;
            gb.gridy = 0;
            gb.insets = margins;

            initTextfields();

            prompt = new JLabel("Car insurance:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(carInsurance, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Household insurance:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(householdInsurance, gb);

            gb.gridy++;
            gb.gridx--;
            gb.anchor = GridBagConstraints.LINE_START;
            prompt = new JLabel("Workers' compensation insurance:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(workersInsurance, gb);
        }
    }

    private class RentPanel extends JPanel{
        private JLabel prompt;

        public RentPanel(){
            setLayout(new GridBagLayout());
            setPreferredSize(dimRentPanel);
            GridBagConstraints gb = new GridBagConstraints();

            gb.anchor = GridBagConstraints.LINE_START;

            gb.gridx = 0;
            gb.gridy = 0;
            gb.insets = margins;

            initTextfields();

            gb.weightx = 1;
            prompt = new JLabel("House rent:");
            add(prompt, gb);
            gb.gridx++;
            gb.anchor = GridBagConstraints.LINE_END;
            add(houseRent, gb);
        }
    }

    private void initTextfields(){
        carInsurance = new JTextField(5);
        carInsurance.setActionCommand("car");
        carInsurance.addActionListener(new Listener());

        householdInsurance = new JTextField(5);
        householdInsurance.setActionCommand("household");
        householdInsurance.addActionListener(new Listener());

        workersInsurance = new JTextField(5);
        workersInsurance.setActionCommand("worker");
        workersInsurance.addActionListener(new Listener());

        houseRent = new JTextField(5);
        houseRent.setActionCommand("house");
        houseRent.addActionListener(new Listener());
    }

    private class Listener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String actionCommand = e.getActionCommand();
            //TODO fikse det så du kun kan skrive tall
            switch(actionCommand){
                case "car":
                    JOptionPane.showMessageDialog(null, carInsurance.getText());
                    /*try {
                        int cars = Integer.parseInt(carInsurance.getText());
                        JOptionPane.showMessageDialog(null, cars);
                    } catch (NumberFormatException s) {
                        JOptionPane.showMessageDialog(null, "Not a number");
                    }*/
                    break;
                case "household":
                    JOptionPane.showMessageDialog(null, householdInsurance.getText());
                    break;
                case "worker":
                    JOptionPane.showMessageDialog(null, workersInsurance.getText());
                    break;
                case "house":
                    JOptionPane.showMessageDialog(null, houseRent.getText());
                    break;
                default:
                    break;
            }
        }
    }
}