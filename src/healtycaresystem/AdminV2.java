package healtycaresystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminV2 extends JFrame{
    private Insets margins = new Insets(5, 5, 5, 5);
    private Dimension dimTable;
    private Dimension dimWindow;
    private Dimension dimTablePanel;
    private Dimension dimActionsPanel;

    private JTable tableData;
    private DefaultTableModel tm;
    private JScrollPane scrollTable;

    public AdminV2(){
        setTitle("Admin Interface");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        GridBagConstraints gb = new GridBagConstraints();

    }

    private class PanelTable extends JPanel{
        public PanelTable(){
            setLayout(new GridBagLayout());
        }
    }
    public void updateTable(){

    }
    public void initTable(){

    }

    private class PanelActions extends JPanel{
        public PanelActions(){
            setLayout(new GridBagLayout());
        }
    }

    private class Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
        }
    }
}
