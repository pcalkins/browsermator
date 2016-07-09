package browsermator.com;


import com.opencsv.CSVReader;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
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
import javax.swing.JTable;

import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
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
   JLabel JLabelBugTitle = new JLabel ("Procedure");
   JLabel JLabelBugTitle2 = new JLabel ("Title:");
     JTextField JTextFieldBugTitle = new JTextField("", 35);
  
       JLabel JLabelAddFieldInstructions = new JLabel (" ");
 JComboBox JComboBoxStoredArrayLists;
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
      JButton JButtonMoveProcedureUp = new JButton("/\\");
      JButton JButtonMoveProcedureDown = new JButton ("\\/");
      JPanel MoveButtonsPanel = new JPanel();
        JTextField JTextFieldDataFile;
   JButton JButtonBrowseForDataFile;
   JScrollPane JTableScrollPane;
   CSVReader CSVFileReader;
JPanel panelForTable;
JButton JButtonUseList;
JLabel JLabelOR;
String Type;
    ProcedureView()
     {
             JLabelOR = new JLabel("OR:");
       JButtonUseList = new JButton("Use Stored URL List");
JTextFieldDataFile = new JTextField();
JTextFieldDataFile.setVisible(true);
 JButtonBrowseForDataFile = new JButton();

 JButtonBrowseForDataFile.setText("Browse for Data File");

 JButtonBrowseForDataFile.setVisible(true);
panelForTable = new JPanel();

 JComboBoxStoredArrayLists = new JComboBox();
 JComboBoxStoredArrayLists.addItem("Select a stored URL List");
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
     JPanelBug.setSize(600, 252);
     BugConstraints.fill = GridBagConstraints.BOTH;
     BugConstraints.anchor = GridBagConstraints.WEST;
     BugConstraints.insets = new Insets(2,2,4,10); //top, left, bottom, right
     double global_weightx = 1/6;
     double global_weighty = 1/4;
     JPanel ProcedurePlusIndex = new JPanel();
     ProcedurePlusIndex.add(JLabelBugTitle);
     ProcedurePlusIndex.add(JLabelBugIndex);
     ProcedurePlusIndex.add(JLabelBugTitle2);
     
     
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
     MoveButtonsPanel.add(JButtonMoveProcedureUp);
     MoveButtonsPanel.add(JButtonMoveProcedureDown);
     MoveButtonsPanel.add(JLabelPass);
 
     AddToGrid(ProcedurePlusIndex, 1, 1, 1, 1, global_weightx, global_weighty);
  
     AddToGrid(JTextFieldBugTitle, 1, 2, 1, 1, global_weightx, global_weighty );
   

  AddToGrid(JButtonDeleteBug, 1, 3, 1, 1, global_weightx, global_weighty);
 
  AddToGrid(JButtonRunTest, 1, 4, 1, 1, global_weightx, global_weighty);
  
  AddToGrid(MoveButtonsPanel, 1, 5, 1, 1, global_weightx, global_weighty);
     JLabelPass.setVisible(false);

 //   AddToGrid(JLabelPass, 1, 5, 1, 1, global_weightx, global_weighty);  
 
   
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
     public void setType (String type)
     {
         this.Type = type;
     }
     public void setTitle(String title)
     {
         JLabelBugTitle.setText(title);
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
         public void addJButtonMoveProcedureUpActionListener(ActionListener listener)
       {
       JButtonMoveProcedureUp.addActionListener(listener);
           
       }
         public void addJButtonMoveProcedureDownActionListener(ActionListener listener)
         {
         JButtonMoveProcedureDown.addActionListener(listener);    
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
        if (this.Type=="Procedure")
        {
    JLabel ActionScrollPaneTitle = new JLabel ("Procedure " + stringbugindex + " actions:");
    ActionScrollPane.setColumnHeaderView(ActionScrollPaneTitle);
        }
        else
        {
              
    JLabel ActionScrollPaneTitle = new JLabel ("Data Loop " + stringbugindex + " actions:");
    ActionScrollPane.setColumnHeaderView(ActionScrollPaneTitle);
        }
   }
 
   public void EnableArrayListsPulldown(boolean enableit)
   {
    this.JComboBoxStoredArrayLists.setEnabled(enableit);
   }
   public void EnableJComboBoxStoredArrayLists(Boolean enableit)
   {
       JComboBoxStoredArrayLists.setEnabled(enableit);
       
   }
   public void SetJComboBoxStoredArraylists(String itemname)
   {
       JComboBoxStoredArrayLists.setSelectedItem(itemname);
   }
         public void addJButtonUseListActionListener(ActionListener listener)
      {
          JButtonUseList.addActionListener(listener);
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
    JPanel panelForBrowseAndPulldown = new JPanel();
//    EnableArrayListsPulldown(false);
//    JComboBoxStoredArrayLists.setEnabled(false);
    panelForBrowseAndPulldown.add(JButtonBrowseForDataFile);
    panelForBrowseAndPulldown.add(JLabelOR);
    panelForBrowseAndPulldown.add(JButtonUseList);
    panelForBrowseAndPulldown.add(JComboBoxStoredArrayLists);
    
    panelForTable.add(panelForBrowseAndPulldown, BorderLayout.PAGE_START);
      
    panelForTable.add(JTableScrollPane);
   // JLabelAddFieldInstructions.setVisible(false);
   AddToGrid(JLabelAddFieldInstructions, 8, 0, 1, 1, 1, 1);
   
    AddToGrid(panelForTable, 9, 0, 9, 1, 1, 1);
     JTextFieldDataFile.setText(sourceCSVfile);

    
    File file = new File(sourceCSVfile);
if (file.isAbsolute()) {
 
}
else
{
    if ("".equals(sourceCSVfile))
    {
        JTextFieldDataFile.setText ("No data file or URL list set.");
    }
    else
    {
   
    JTextFieldDataFile.setText("Will use stored URL List " + sourceCSVfile);
    }
}
     
    myTable.DataTable.getTableHeader().addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        int columnindex = myTable.DataTable.columnAtPoint(e.getPoint());
        String selected_name = myTable.DataTable.getColumnName(columnindex);
     
        int field_number = last_selected_jtextfield_variable_number;
        int action_index = last_selected_action_index;
        if (ActionsViewList.size()>action_index)
            {
        ActionView actionview_to_update = ActionsViewList.get(action_index);
        actionview_to_update.setActionFieldToDataColumn (field_number, columnindex, selected_name);
            }
    
    }
    
});
     }

  
}