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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.BorderFactory;
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
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;
// import net.miginfocom.swing.MigLayout;

public class ProcedureView {
      int index;
 
      JLabel JLabelBugIndex = new JLabel ("#");
 
   JLabel JLabelBugTitle = new JLabel ("Procedure");
   JLabel JLabelBugTitle2 = new JLabel ("Title:");
     JTextField JTextFieldBugTitle = new JTextField("", 25);
  
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
    JLabel JLabelQuickActions = new JLabel ("Quick Select Actions:");
    JLabel JLabelQuickPassFailActions = new JLabel("Quick Select Pass/Fail Actions:");
  //  JLabel JLabelQuickActionsPassFail = new JLabel ("Quick Select Pass/Fail Action Buttons:");
    MyTable myTable;
    int last_selected_procedure_index = 0;
    int last_selected_action_index = 0;
    int last_selected_jtextfield_variable_number = 0;
      JButton JButtonMoveProcedureUp = new JButton("/\\");
      JButton JButtonMoveProcedureDown = new JButton ("\\/");
      JPanel MoveButtonsPanel = new JPanel();
      JPanel RemoveRunButtonsPanel = new JPanel();
        JTextField JTextFieldDataFile;
   JButton JButtonBrowseForDataFile;
   JScrollPane JTableScrollPane;
   CSVReader CSVFileReader;
JPanel panelForTable;
JLabel JLabelUseList;
JLabel JLabelOR;
String Type;
TitledBorder BugPanelBorder;
Boolean Locked;
JButton JButtonOK = new JButton("Disable");
    ProcedureView()
     {
JButtonOK.setActionCommand("Update");
JLabelPass.setOpaque(true);
         myTable=new MyTable("");
             JLabelOR = new JLabel("OR:");
       JLabelUseList = new JLabel("Use Stored URL List");
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
   JPanelBug.setSize(920, 252);
 //    BugConstraints.fill = GridBagConstraints.NONE;
//     BugConstraints.anchor = GridBagConstraints.WEST;
//     BugConstraints.insets = new Insets(2,2,2,2); //top, left, bottom, right
     double global_weightx = 1/6;
     double global_weighty = 1/4;
     JPanel ProcedurePlusIndex = new JPanel();
     ProcedurePlusIndex.add(JLabelBugTitle);
     ProcedurePlusIndex.add(JLabelBugIndex);
     ProcedurePlusIndex.add(JLabelBugTitle2);
     
     
     JPanel LeftSideButtonsPanel = new JPanel();
     BoxLayout LeftSideButtonsLayout = new BoxLayout(LeftSideButtonsPanel, BoxLayout.Y_AXIS);
     setStandardButtonSize(JButtonGoAction);
     setStandardButtonSize(JButtonClickAtXPATH);
     setStandardButtonSize(JButtonTypeAtXPATH);
     setStandardButtonSize(JButtonFindXPATHPassFail);
     setStandardButtonSize(JButtonDoNotFindXPATHPassFail);
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
    AddToGrid(LeftSideButtonsPanel, 1, 0, 1, 3, 0.1, global_weighty, GridBagConstraints.WEST); 
    
     AddToGrid(ProcedurePlusIndex, 1, 1, 1, 1, global_weightx, global_weighty, GridBagConstraints.WEST);
     AddToGrid(JTextFieldBugTitle, 1, 2, 1, 1, global_weightx, global_weighty, GridBagConstraints.WEST );
     RemoveRunButtonsPanel.add(JButtonOK);
     RemoveRunButtonsPanel.add(JButtonDeleteBug);
     RemoveRunButtonsPanel.add(JButtonRunTest);


   AddToGrid(RemoveRunButtonsPanel, 1, 3, 1, 1, global_weightx, global_weighty, GridBagConstraints.WEST);
   AddToGrid(MoveButtonsPanel, 1, 4, 1, 1, global_weightx, global_weighty, GridBagConstraints.EAST);
    JLabelPass.setVisible(false);
   
 //   BugConstraints.insets = new Insets(2,2,4,2);
 //   JPanel DoActionComboPanel = new JPanel();
 //   DoActionComboPanel.add(JLabelDoActions);
 //   DoActionComboPanel.add(JComboBoxDoActions);
    AddToGrid(JLabelDoActions, 2, 1, 1, 1, global_weightx, global_weighty, GridBagConstraints.EAST);
    AddToGrid(JComboBoxDoActions, 2, 2, 1, 1, global_weightx, global_weighty, GridBagConstraints.WEST);
    
  //  AddToGrid (DoActionComboPanel, 2, 1, 1, 1, global_weightx, global_weighty);
     
    AddToGrid (JLabelPassFailActions, 2, 3, 1, 1, global_weightx, global_weighty, GridBagConstraints.EAST);
    AddToGrid (JComboBoxPassFailActions, 2, 4, 1, 1, global_weightx, global_weighty, GridBagConstraints.WEST); 

 //   BugConstraints.insets = new Insets(1,1,1,1);

  ActionScrollPane.setVisible(false);
// ActionScrollPane.setSize(new Dimension(920, 840));
   ActionScrollPanel.add(ActionScrollPane);
  // ActionScrollPanel.setSize(920,480);
    AddToGrid (ActionScrollPanel, 3, 1, 6, 4, global_weightx, global_weighty, GridBagConstraints.WEST);
 
  
    
 JButtonSubmitBug.setActionCommand("Update");
    
     }
     public void setLocked(Boolean lockedstate)
   {
     Locked = lockedstate;
     if (Locked)
     {
     JButtonOK.setText("Enable");
     JButtonOK.setActionCommand("Edit");
     }
   }
   public Boolean getLocked()
   {
       return Locked;
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
         thisbutton.setMaximumSize(new Dimension(200, 100));
         thisbutton.setMinimumSize(new Dimension(180, 100));
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
     public final void AddToGrid( Component component, int row, int column, int width, int height, double weightx, double weighty, int anchor_value)
     {
         BugConstraints.gridx = column;
         BugConstraints.gridy = row;
         BugConstraints.gridwidth = width;
         BugConstraints.gridheight = height;
         BugConstraints.weightx = weightx;
         BugConstraints.weighty = weighty;
         BugConstraints.anchor = anchor_value;
         BugLayout.setConstraints(component, BugConstraints);
         JPanelBug.add(component);
     }
         public void Disable()
       {
  
   this.JButtonOK.setText("Enable");
   this.JButtonOK.setActionCommand("Edit");
    for (ActionView AV: ActionsViewList)
    {
        
        AV.UpdateActionView();
       
    }
    
       }
       public void Enable()
       {
  
   this.JButtonOK.setText("Disable");
   this.JButtonOK.setActionCommand("Update");
    for (ActionView AV: ActionsViewList)
    {
        AV.EditActionView();
      
    }
       }
       public void addJButtonOKActionListener(ActionListener listener)
       {
       JButtonOK.addActionListener(listener);
           
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
  
  public void addJComboBoxStoredArrayListsItemListener(ItemListener listener)
  {
      JComboBoxStoredArrayLists.addItemListener(listener);
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
        if ("Procedure".equals(this.Type))
        {
    JLabel ActionScrollPaneTitle = new JLabel ("Procedure " + stringbugindex + " actions:");
    ActionScrollPane.setColumnHeaderView(ActionScrollPaneTitle);
 BugPanelBorder = BorderFactory.createTitledBorder("Procedure " + stringbugindex);
         JPanelBug.setBorder(BugPanelBorder);
         setTitle("Procedure ");
         
        }
        else
        {
              
    JLabel ActionScrollPaneTitle = new JLabel ("Data Loop " + stringbugindex + " actions:");
    ActionScrollPane.setColumnHeaderView(ActionScrollPaneTitle);
     BugPanelBorder = BorderFactory.createTitledBorder("Procedure (Data Loop) " + stringbugindex);
         JPanelBug.setBorder(BugPanelBorder);
          setTitle("Procedure (Data Loop) ");
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
   
      public void addJButtonBrowseForDataFileActionListener(ActionListener listener)
     {
         JButtonBrowseForDataFile.addActionListener(listener);
     }
     public void setJTextFieldDataFile(String dataFile)
     {
         JTextFieldDataFile.setText(dataFile);

     }
     public void UpdatePlacedLoopVars(String newsource)
     {
         Boolean isStoredList = true;
             File checkfile = new File(newsource);
             if (checkfile.isAbsolute())
             {
                isStoredList = false; 
             }
         for (ActionView AV: this.ActionsViewList)
         {
            
             if (isStoredList)
             {
             if (AV.JTextFieldVariable1.getText().contains("Stored URL List:"))
             {
           //   String oldval = AV.JTextFieldVariable1.getText();
           //   String meat = oldval.substring(21, oldval.length()-20);
              String newval = "[dataloop-field-start]1,0,Stored URL List:" + newsource + "[dataloop-field-end]";
              AV.JTextFieldVariable1.setText(newval);
             }
           
               if (AV.JTextFieldVariable2.getText().contains("Stored URL List:"))
             {
            //  String oldval =  AV.JTextFieldVariable2.getText();
           //   String meat = oldval.substring(21, oldval.length()-20);
              String newval = "[dataloop-field-start]2,0,Stored URL List:" + newsource + "[dataloop-field-end]";
              AV.JTextFieldVariable2.setText(newval);
             }
             }
             else
             {
              if (AV.JTextFieldVariable1.getText().contains("[dataloop-field-start]"))
             {
            
              AV.JTextFieldVariable1.setText("");
             }
           
               if (AV.JTextFieldVariable2.getText().contains("[dataloop-field-start]"))
             {
            
              AV.JTextFieldVariable2.setText("");
             }  
             //    JComboBoxStoredArrayLists.setSelectedIndex(0);
             }
         }
    
     }
  
     public void setJTableSource (String sourceCSVfile)
     {
     UpdatePlacedLoopVars(sourceCSVfile);
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
   
      JTableScrollPane.setSize(new Dimension(600, 840));
     JTableScrollPane.setVisible(true);
     
  
   panelForTable.setLayout(new BorderLayout());
  //  panelForTable.add(JLabelAddFieldInstructions, BorderLayout.PAGE_START);
    panelForTable.add(JTextFieldDataFile,BorderLayout.PAGE_END );
    JPanel panelForBrowseAndPulldown = new JPanel();
//    EnableArrayListsPulldown(false);
//    JComboBoxStoredArrayLists.setEnabled(false);
    panelForBrowseAndPulldown.add(JButtonBrowseForDataFile);
    panelForBrowseAndPulldown.add(JLabelOR);
    panelForBrowseAndPulldown.add(JLabelUseList);
    panelForBrowseAndPulldown.add(JComboBoxStoredArrayLists);
    
    panelForTable.add(panelForBrowseAndPulldown, BorderLayout.PAGE_START);
      
    panelForTable.add(JTableScrollPane, BorderLayout.PAGE_END);
   // JLabelAddFieldInstructions.setVisible(false);
   AddToGrid(JLabelAddFieldInstructions, 9, 0, 2, 1, 1, 1, GridBagConstraints.WEST);
   
    AddToGrid(panelForTable, 10, 1, 3, 8, 1, 1, GridBagConstraints.WEST);
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
        if (columnindex<0){columnindex=0;}
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