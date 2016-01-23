package browsermator.com;


import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.JTextField;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import javax.swing.event.DocumentListener;
// import net.miginfocom.swing.MigLayout;

public class ProcedureView {
      int index;
  //  static final long serialVersionUID = -1406026899421845272L;
   //   JLabel JLabelAddActions = new JLabel("Add Actions:");
      JLabel JLabelBugIndex = new JLabel ("#");
  //    JLabel JLabelBugNumber = new JLabel("Bug Number: ");
   JLabel JLabelBugTitle = new JLabel ("Procedure Title: ");
     JTextField JTextFieldBugTitle = new JTextField("", 55);
    
    
 
     JLabel JLabelBugURL = new JLabel("Procedure URL (if available):");
     JTextField JTextFieldBugURL = new JTextField("", 15);
     JLabel JLabelBugSeverity = new JLabel ("Importance:");
     JComboBox JComboBoxBugSeverity = new JComboBox();
        JButton JButtonSubmitBug = new JButton("OK");
  
      
     JComboBox JComboBoxDoActions = new JComboBox();
     JComboBox JComboBoxPassFailActions = new JComboBox();
     JButton JButtonDeleteBug = new JButton("Remove");
     JButton JButtonRunTest = new JButton("Run");
    JLabel JLabelPass = new JLabel ("Procedure not run yet.");
    GridBagLayout BugLayout = new GridBagLayout();
    GridBagConstraints BugConstraints = new GridBagConstraints();
    JPanel JPanelBug = new JPanel();
   ArrayList<ActionView> ActionsViewList = new ArrayList();   
     JScrollPane ActionScrollPane = new JScrollPane(VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
     
     JPanel ActionScrollPanel = new JPanel();
       JLabel JLabelDoActions = new JLabel("Add Do Actions: ");
     JLabel JLabelPassFailActions = new JLabel("Add Pass/Fail Actions: ");
     ProcedureView()
     {
     
     JComboBoxBugSeverity.addItem("Trivial");
     JComboBoxBugSeverity.addItem("Low");
     JComboBoxBugSeverity.addItem("Medium");
     JComboBoxBugSeverity.addItem("High");
     JComboBoxDoActions.addItem("Choose an Action");
     JComboBoxDoActions.addItem("Back Action");
     JComboBoxDoActions.addItem("Click at HREF");
     JComboBoxDoActions.addItem("Click at Link Text");
     JComboBoxDoActions.addItem("Click at ID");
     JComboBoxDoActions.addItem("Click at XPATH");
     JComboBoxDoActions.addItem("Click at Image SRC");
     JComboBoxDoActions.addItem("Close Current Tab or Window");
     
     JComboBoxDoActions.addItem("Drag From XPATH to XPATH");
     JComboBoxDoActions.addItem("Enter Key");
     JComboBoxDoActions.addItem("Execute Javascript");
     JComboBoxDoActions.addItem("Forward Action");
     JComboBoxDoActions.addItem("Go to URL");
     JComboBoxDoActions.addItem("Open New Tab");
     JComboBoxDoActions.addItem("Next Tab");
     JComboBoxDoActions.addItem("Pause");
     JComboBoxDoActions.addItem("Set Cookie");
     
     JComboBoxDoActions.addItem("Switch To Frame");
     JComboBoxDoActions.addItem("Switch To Tab or Window");
     JComboBoxDoActions.addItem("Type at ID");
     JComboBoxDoActions.addItem("Type at Input Name");
     
     JComboBoxDoActions.addItem("Type at XPATH");
     JComboBoxDoActions.addItem("Type Password at Input Name");
     JComboBoxDoActions.addItem("Type Password at XPATH");
     
     JComboBoxPassFailActions.addItem("Choose a Pass/Fail Condition");
     JComboBoxPassFailActions.addItem("Yes/No Prompt");
     JComboBoxPassFailActions.addItem("Find Text");
     JComboBoxPassFailActions.addItem("Find HREF");
     JComboBoxPassFailActions.addItem("Find IFrame SRC");
     JComboBoxPassFailActions.addItem("Find Image SRC");
     JComboBoxPassFailActions.addItem("Find Page Title");
     JComboBoxPassFailActions.addItem("Find XPATH");
     JComboBoxPassFailActions.addItem("Do Not Find Text");
     JComboBoxPassFailActions.addItem("Do Not Find HREF");
     JComboBoxPassFailActions.addItem("Do Not Find IFrame SRC");
     JComboBoxPassFailActions.addItem("Do Not Find Image SRC");
     JComboBoxPassFailActions.addItem("Do Not Find Page Title");
     JComboBoxPassFailActions.addItem("Do Not Find XPATH");
     
  
    
  
 //    JPanelActionAdders.add(JLabelDoActions);
 
//     JPanelActionAdders.add(JComboBoxDoActions);
// JPanelActionAdders.add(JLabelPassFailActions);
//     JPanelActionAdders.add(JComboBoxPassFailActions);
 
//  JPanelActionAdders.setVisible(true);
  
     
   // LAYOUT GRIDBAGSTYLE
     JPanelBug.setLayout(BugLayout);
     BugConstraints.fill = GridBagConstraints.BOTH;
     BugConstraints.anchor = GridBagConstraints.WEST;
     BugConstraints.insets = new Insets(2,2,4,2); //top, left, bottom, right
     AddToGrid(JLabelBugIndex, 1, 0, 1, 1);
     
  //JPanelBug.add(JLabelBugIndex);
  //   JPanelBug.add(JTextFieldBugNumber);
     
  //   JPanelBug.add(JLabelBugTitle);
     AddToGrid(JLabelBugTitle, 1, 1, 1, 1);
  
//   JPanelBug.add(JTextFieldBugTitle);
     AddToGrid(JTextFieldBugTitle, 1, 2, 2, 1 );
   

  AddToGrid(JButtonDeleteBug, 1, 4, 1, 1);
 
  //JPanelBug.add(JButtonRunTest);
  AddToGrid(JButtonRunTest, 1, 5, 1, 1);

     JLabelPass.setVisible(false);
  //   JPanelBug.add(JLabelPass);
    AddToGrid(JLabelPass, 1, 6, 1, 1);  
 
    //   JPanelBug.add(JComboBoxDoActions);
    BugConstraints.insets = new Insets(5,5,0,0);
    AddToGrid (JLabelDoActions, 2, 1, 1, 1);
    AddToGrid (JComboBoxDoActions, 2, 2, 1, 1);
    
//   JPanelBug.add(JComboBoxPassFailActions);
    AddToGrid (JLabelPassFailActions, 2, 4, 1, 1);
    AddToGrid (JComboBoxPassFailActions, 2, 5, 1, 1); 
  ActionScrollPane.setVisible(false);
  //  ActionScrollPane.setPreferredSize(new Dimension(900, 80));

    AddToGrid (ActionScrollPanel, 3, 1, 6, 1);
   
      
    
     
       ActionScrollPanel.add(ActionScrollPane);
  
    
 JButtonSubmitBug.setActionCommand("Update");
    
     }
     
     public final void AddToGrid( Component component, int row, int column, int width, int height)
     {
         BugConstraints.gridx = column;
         BugConstraints.gridy = row;
         BugConstraints.gridwidth = width;
         BugConstraints.gridheight = height;
         BugLayout.setConstraints(component, BugConstraints);
         JPanelBug.add(component);
     }
     
     public void addJButtonRunTestActionListener(ActionListener listener)
     {
         JButtonRunTest.addActionListener(listener);
     }
     public void addJTextFieldBugTitleDocListener(DocumentListener doclistener)
     {
         JTextFieldBugTitle.getDocument().addDocumentListener(doclistener);
     }
       public void addJButtonSubmitBugActionListener(ActionListener listener) {
       JButtonSubmitBug.addActionListener(listener);
   
   } 
              public void addJButtonDeleteBugActionListener(ActionListener listener) {
       JButtonDeleteBug.addActionListener(listener);
   
   } 

  public void addDoActionItemListener(ItemListener listener) {
       JComboBoxDoActions.addItemListener(listener);
     
   } 
    public void addPassFailActionsItemListener(ItemListener listener) {
       JComboBoxPassFailActions.addItemListener(listener);
 
   } 

   public void SetIndex(int bugindex)
   {
       this.index = bugindex;
       String stringbugindex = Integer.toString(this.index+1);
       
       this.JLabelBugIndex.setText(stringbugindex);
        
    JLabel ActionScrollPaneTitle = new JLabel ("Procedure " + stringbugindex + " actions:");
    ActionScrollPane.setColumnHeaderView(ActionScrollPaneTitle);
       
   }
     
}