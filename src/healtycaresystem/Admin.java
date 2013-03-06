package healtycaresystem;

/**
 * Created with IntelliJ IDEA.
 * User: Excludos
 * Date: 06.03.13
 * Time: 14:06
 * To change this template use File | Settings | File Templates.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static javax.swing.JOptionPane.*;

/*
 Klassen Hovedvindu styrer et vindu med tre knapper, som ved klikk åpner tre eksempler på layouthåndterere.
 */
public class Admin extends javax.swing.JFrame {

    private JButton users = new JButton("Users");
    private JButton employers = new JButton("Employers");
    private JButton exit = new JButton("Exit");
    private Insets margins = new Insets(5, 5, 5, 5);
    private Dimension dimension = new Dimension(1920, 1080);
    int employeeID = 0;

    public Admin(int employeeID) {
        this.employeeID = employeeID;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setPreferredSize(dimension);
        users.setPreferredSize(new Dimension(100, 50));
        employers.setPreferredSize(new Dimension(100, 50));
        exit.setPreferredSize(new Dimension(100, 50));
        setTitle("Admin Vindu");

// setter layout, legger til tekst og knapper
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridwidth = 1;
        gb.gridheight = 1;
        gb.gridx = 0;
        gb.gridy = 0;
        gb.anchor = GridBagConstraints.FIRST_LINE_START;
        gb.insets = margins;
        add(users, gb);
        gb.gridy++;
        add(employers, gb);
        gb.gridy++;
        add(exit, gb);
        gb.gridy = 0;
        gb.gridx = 1;
        gb.gridheight = 4;

// legger inn fillersetter knappene for at de skal holde seg til høyre hjørne
        gb.gridy = 2;
        gb.fill = GridBagConstraints.BOTH;
        gb.anchor = GridBagConstraints.LINE_END;
        gb.weighty = 3.0;
        gb.weightx = 3.0;
        gb.gridheight = GridBagConstraints.REMAINDER;
        add(Box.createGlue(), gb);

// legger til en knappelytter og kobler denne til knappene
        Knappelytter knappelytteren = new Knappelytter();
        users.addActionListener(knappelytteren);
        exit.addActionListener(knappelytteren);

        pack();
    }

    private class Knappelytter implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent hendelse) {
            // Finner hvilken knapp som ble trykket på
            JButton valgtKnapp = (JButton) hendelse.getSource();

            // Åpner riktig vindu
            if (valgtKnapp.equals(users)) {
            } else if (valgtKnapp.equals(exit)) {
                dispose();
            } else if (valgtKnapp.equals(employers)) {
            }
        }
    }
}