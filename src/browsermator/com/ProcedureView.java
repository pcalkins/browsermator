package browsermator.com;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
     JTextField JTextFieldBugTitle = new JTextField("", 35);
  
       JLabel JLabelAddFieldInstructions = new JLabel (" ");
 
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
       JLabel JLabelDoActions = new JLabel("Add Actions: ");
     JLabel JLabelPassFailActions = new JLabel("Add Pass/Fail Actions: ");
    JButton JButtonGoAction =              new JButton("Go To URL");
    JButton JButtonClickAtXPATH =          new JButton("Click at XPATH");
    JButton JButtonTypeAtXPATH =           new JButton("Type at XPATH");
 //   JButton JButtonTypePasswordAtXPATH =   new JButton ("Type Password at XPATH");
    JButton JButtonFindXPATHPassFail =     new JButton ("Find XPATH");
    JButton JButtonDoNotFindXPATHPassFail = new JButton ("Do Not Find XPATH");
   // JButton JButtonFindPageTitlePassFail = new JButton ("Find Page Title");
   // JButton JButtonDoNotFindPageTitlePassFail = new JButton ("Do Not Find Page Title");
    JButton JButtonYesNoPromptPassFail = new JButton ("Yes/No Question (Yes Passes Test)");
    JLabel JLabelQuickActions = new JLabel ("Quick Select Action Buttons:");
    JLabel JLabelQuickPassFailActions = new JLabel("Quick Select Pass/Fail Action Buttons:");
  //  JLabel JLabelQuickActionsPassFail = new JLabel ("Quick Select Pass/Fail Action Buttons:");
    MyTable myTable = null;
    int last_selected_procedure_index = 0;
    int last_selected_action_index = 0;
    int last_selected_jtextfield_variable_number = 0;
     ProcedureView()
     {

       JComboBoxBugSeverity.addItem("Trivial");
     JComboBoxBugSeverity.addItem("Low");
     JComboBoxBugSeverity.addItem("Medium");
     JComboBoxBugSeverity.addItem("High");
  JComboBoxDoActions.addItem("Choose an Action");
  ActionsMaster ActionNames = new ActionsMaster();
  HashMap<String, Action> ActionHashMap = ActionNames.ActionHashMap;
SortedSet<String> action_keys = new TreeSet<>(ActionHashMap.keySet());
for (String action_name : action_keys) 
{
 JComboBoxDoActions.addItem(action_name);
 
}
 
 HashMap<String, Action> PassFailActionHashMap = ActionNames.PassFailActionHashMap;
 

     JComboBoxPassFailActions.addItem("Choose a Pass/Fail Condition");

SortedSet<String> passfailaction_keys = new TreeSet<>(PassFailActionHashMap.keySet());
for (String passfailaction_name : passfailaction_keys) 
{
 JComboBoxPassFailActions.addItem(passfailaction_name);
 
}
  

     JPanelBug.setLayout(BugLayout);
     BugConstraints.fill = GridBagConstraints.BOTH;
     BugConstraints.anchor = GridBagConstraints.WEST;
     BugConstraints.insets = new Insets(2,2,4,10); //top, left, bottom, right
     double global_weightx = 1/6;
     double global_weighty = 1/4;
     JPanel ProcedurePlusIndex = new JPanel();
     ProcedurePlusIndex.add(JLabelBugIndex);
     ProcedurePlusIndex.add(JLabelBugTitle);
     
     JPanel LeftSideButtonsPanel = new JPanel();
     BoxLayout LeftSideButtonsLayout = new BoxLayout(LeftSideButtonsPanel, BoxLayout.PAGE_AXIS);
     setStandardButtonSize(JButtonGoAction);
     setStandardButtonSize(JButtonClickAtXPATH);
     setStandardButtonSize(JButtonTypeAtXPATH);
  //   setStandardButtonSize(JButtonTypePasswordAtXPATH);
     setStandardButtonSize(JButtonFindXPATHPassFail);
     setStandardButtonSize(JButtonDoNotFindXPATHPassFail);
  //   setStandardButtonSize(JButtonFindPageTitlePassFail);
  //   setStandardButtonSize(JButtonDoNotFindPageTitlePassFail);
     setStandardButtonSize(JButtonYesNoPromptPassFail);
     
     LeftSideButtonsPanel.add(JLabelQuickActions);
     LeftSideButtonsPanel.add(Box.createRigidArea(new Dimension (0, 5)));
     LeftSideButtonsPanel.add(JButtonGoAction);
     LeftSideButtonsPanel.add(Box.createRigidArea(new Dimension (0, 5)));
     LeftSideButtonsPanel.add(JButtonClickAtXPATH);
     LeftSideButtonsPanel.add(Box.createRigidArea(new Dimension (0, 5)));
     LeftSideButtonsPanel.add(JButtonTypeAtXPATH);
     LeftSideButtonsPanel.add(Box.createRigidArea(new Dimension (0, 5)));
     LeftSideButtonsPanel.add(JLabelQuickPassFailActions);
     LeftSideButtonsPanel.add(Box.createRigidArea(new Dimension (0, 5)));
     LeftSideButtonsPanel.add(JButtonFindXPATHPassFail);
     LeftSideButtonsPanel.add(Box.createRigidArea(new Dimension (0, 5)));
     LeftSideButtonsPanel.add(JButtonDoNotFindXPATHPassFail);
     LeftSideButtonsPanel.add(Box.createRigidArea(new Dimension (0, 5)));
 
     LeftSideButtonsPanel.add(JButtonYesNoPromptPassFail);
     LeftSideButtonsPanel.add(Box.createRigidArea(new Dimension (0, 5)));
     
  
     LeftSideButtonsPanel.setLayout(LeftSideButtonsLayout);
     
     
 
     AddToGrid(ProcedurePlusIndex, 1, 1, 1, 1, global_weightx, global_weighty);
  
     AddToGrid(JTextFieldBugTitle, 1, 2, 1, 1, global_weightx, global_weighty );
   

  AddToGrid(JButtonDeleteBug, 1, 3, 1, 1, global_weightx, global_weighty);
 
  AddToGrid(JButtonRunTest, 1, 4, 1, 1, global_weightx, global_weighty);

     JLabelPass.setVisible(false);

    AddToGrid(JLabelPass, 1, 5, 1, 1, global_weightx, global_weighty);  
 
   
    BugConstraints.insets = new Insets(2,2,4,2);
    JPanel DoActionComboPanel = new JPanel();
    DoActionComboPanel.add(JLabelDoActions);
    DoActionComboPanel.add(JComboBoxDoActions);
    
    AddToGrid (DoActionComboPanel, 2, 1, 2, 1, global_weightx, global_weighty);
    AddToGrid (JLabelPassFailActions, 2, 3, 1, 1, global_weightx, global_weighty);
    AddToGrid (JComboBoxPassFailActions, 2, 4, 1, 1, global_weightx, global_weighty); 

    BugConstraints.insets = new Insets(10,10,10,10);
   AddToGrid(LeftSideButtonsPanel, 1, 0, 1, 3, global_weightx, global_weighty); 
  ActionScrollPane.setVisible(false);
  ActionScrollPane.setSize(new Dimension(1024, 840));

    AddToGrid (ActionScrollPanel, 3, 1, 6, 5, global_weightx, global_weighty);
    ActionScrollPanel.add(ActionScrollPane);
  
    
 JButtonSubmitBug.setActionCommand("Update");
    
     }
     
     public void clearLastSelectedValues ()
     {
         last_selected_procedure_index = 0;
         last_selected_action_index = 0;
         last_selected_jtextfield_variable_number = 0;
     }
     public void setLastSelectedField (int variable_number, int procedure_index, int action_index)
     {
         last_selected_procedure_index = procedure_index;
         last_selected_action_index = action_index;
         last_selected_jtextfield_variable_number = variable_number;
     }
      public final void setStandardButtonSize(JButton thisbutton)
     {
     //    thisbutton.setSize(new Dimension(5000, 5000));
         thisbutton.setMaximumSize(new Dimension(500, 500));
     }
     public void ShowFieldInstructions(boolean showit, int textfieldindex, int bugindex, int actionindex)
     {
         bugindex++;
         actionindex++;
         String stringactionindex = Integer.toString(actionindex);
        String stringbugindex = Integer.toString(bugindex);
        String textfieldindex_char = Integer.toString(textfieldindex);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
         if (showit)
         {
             this.JLabelAddFieldInstructions.setText("Click ColumnName to set Field" + textfieldindex_char + " of " + bugdashactionindex);
         }
         else
         {
             this.JLabelAddFieldInstructions.setText(" ");
         }
     }
     public final void AddToGrid( Component component, int row, int column, int width, int height, double weightx, double weighty)
     {
         BugConstraints.gridx = column;
         BugConstraints.gridy = row;
         BugConstraints.gridwidth = width;
         BugConstraints.gridheight = height;
         BugConstraints.weightx = weightx;
         BugConstraints.weighty = weighty;
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
     public void addJButtonGoActionActionListener(ActionListener listener) {
      JButtonGoAction.addActionListener(listener);
   } 
     public void addJButtonClickAtXPATHActionListener(ActionListener listener)
     {
         JButtonClickAtXPATH.addActionListener(listener);
     }
     public void addJButtonTypeAtXPATHActionListener(ActionListener listener)
     {
         JButtonTypeAtXPATH.addActionListener(listener);
     }
      public void addJButtonFindXPATHPassFailListener(ActionListener listener)
     {
         JButtonFindXPATHPassFail.addActionListener(listener);
     }
      public void addJButtonDoNotFindXPATHPassFailListener(ActionListener listener)
     {
         JButtonDoNotFindXPATHPassFail.addActionListener(listener);
     }
         public void addJButtonYesNoPromptPassFailListener(ActionListener listener)
     {
         JButtonYesNoPromptPassFail.addActionListener(listener);
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