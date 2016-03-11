/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import com.opencsv.CSVReader;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;


/**
 *
 * @author pcalkins
 */
public class DataLoopView extends ProcedureView {
   JTextField JTextFieldDataFile;
   JButton JButtonBrowseForDataFile;
   JScrollPane JTableScrollPane;
   CSVReader CSVFileReader;
JPanel panelForTable;

    DataLoopView()
   {
JTextFieldDataFile = new JTextField();
JTextFieldDataFile.setVisible(true);
 JButtonBrowseForDataFile = new JButton();
 JButtonBrowseForDataFile.setText("Change Data File");
 JButtonBrowseForDataFile.setVisible(true);
panelForTable = new JPanel();
JLabelBugTitle.setText("Data Loop Title: ");
    }
   
      public void addJButtonBrowseForDataFileActionListener(ActionListener listener)
     {
         JButtonBrowseForDataFile.addActionListener(listener);
     }
     public void setJTextFieldDataFile(String dataFile)
     {
         JTextFieldDataFile.setText(dataFile);

     }
     public void setJTableSource (String sourceCSVfile)
     {
         JPanelBug.remove(panelForTable);
     myTable = null;
     myTable = new MyTable(sourceCSVfile);
     myTable.DataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
     myTable.DataTable.getTableHeader().setReorderingAllowed(false);

    myTable.DataTable.setFillsViewportHeight( true );

    panelForTable.removeAll();
   JTableScrollPane = new JScrollPane(myTable.DataTable, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
   
      JTableScrollPane.setSize(new Dimension(1024, 840));
     JTableScrollPane.setVisible(true);
     
  
   panelForTable.setLayout(new BorderLayout());
  //  panelForTable.add(JLabelAddFieldInstructions, BorderLayout.PAGE_START);
    panelForTable.add(JTextFieldDataFile,BorderLayout.PAGE_END );
    panelForTable.add(JButtonBrowseForDataFile, BorderLayout.PAGE_START);
    panelForTable.add(JTableScrollPane);
   // JLabelAddFieldInstructions.setVisible(false);
   AddToGrid(JLabelAddFieldInstructions, 8, 0, 1, 1, 1, 1);
   
    AddToGrid(panelForTable, 9, 0, 9, 1, 1, 1);
     JTextFieldDataFile.setText(sourceCSVfile);
    myTable.DataTable.getTableHeader().addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        int columnindex = myTable.DataTable.columnAtPoint(e.getPoint());
        String selected_name = myTable.DataTable.getColumnName(columnindex);
     
        int field_number = last_selected_jtextfield_variable_number;
        int action_index = last_selected_action_index;
        ActionView actionview_to_update = ActionsViewList.get(action_index);
        actionview_to_update.setActionFieldToDataColumn (field_number, columnindex, selected_name);
     
    
    }
    
});
     }

   @Override
     public void SetIndex(int bugindex)
   {
       this.index = bugindex;
       String stringbugindex = Integer.toString(this.index+1);
       
       this.JLabelBugIndex.setText(stringbugindex);
        
    JLabel ActionScrollPaneTitle = new JLabel ("Data Loop " + stringbugindex + " actions:");
    ActionScrollPane.setColumnHeaderView(ActionScrollPaneTitle);
   
       
   }
}
