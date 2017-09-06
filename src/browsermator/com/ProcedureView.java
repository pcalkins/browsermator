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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.ParseException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatter;
// import net.miginfocom.swing.MigLayout;

public class ProcedureView {
      int index;
      int limit;
      Boolean random;
      JLabel JLabelBugIndex = new JLabel ("#");
   JPanel LeftSideButtonsPanel;
   JLabel JLabelBugTitle = new JLabel ("Procedure");
   JLabel JLabelBugTitle2 = new JLabel ("Title:");
     JTextField JTextFieldBugTitle = new JTextField();
       JLabel JLabelAddFieldInstructions = new JLabel (" ");
      JLabel  JLabelAddFieldInstructions2 = new JLabel (" ");
      JPanel jPanelFieldInstructions;
     
    
 JComboBox JComboBoxStoredArrayLists;
 JComboBox jComboBoxSetDataLoopType;
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
    JPanel JPanelBug = new JPanel(BugLayout);
   ArrayList<ActionView> ActionsViewList = new ArrayList();   
  JScrollPane ActionScrollPane = new JScrollPane(VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
     GridBagLayout ActionLayout = new GridBagLayout();
   GridBagConstraints ActionConstraints = new GridBagConstraints(); 
       JLabel JLabelDoActions = new JLabel("Add Actions:");
     JLabel JLabelPassFailActions = new JLabel("Add Pass/Fail Actions:");
    JButton JButtonGoAction =              new JButton("Go To URL");
    JButton JButtonClickAtXPATH =          new JButton("Click at XPATH");
    JButton JButtonTypeAtXPATH =           new JButton("Type at XPATH");
 //   JButton JButtonTypePasswordAtXPATH =   new JButton ("Type Password at XPATH");
    JButton JButtonFindXPATHPassFail =     new JButton ("Find XPATH");
    JButton JButtonDoNotFindXPATHPassFail = new JButton ("Do Not Find XPATH");
   // JButton JButtonFindPageTitlePassFail = new JButton ("Find Page Title");
   // JButton JButtonDoNotFindPageTitlePassFail = new JButton ("Do Not Find Page Title");
    JButton JButtonYesNoPromptPassFail = new JButton ("Yes/No Question");
    JLabel JLabelQuickActions = new JLabel ("Quick Select Actions:");
    JLabel JLabelQuickPassFailActions = new JLabel("Pass/Fail Actions:");

    MyTable myTable;
    int last_selected_procedure_index = 0;
    int last_selected_action_index = 0;
    int last_selected_jtextfield_variable_number = 0;
  //    JButton JButtonMoveProcedureUp = new JButton("/\\");
 //     JButton JButtonMoveProcedureDown = new JButton ("\\/");
      JPanel MoveButtonsPanel = new JPanel();
      JPanel RemoveRunButtonsPanel = new JPanel();
        JTextField JTextFieldDataFile;
   JButton JButtonBrowseForDataFile;
   JScrollPane JTableScrollPane;
   CSVReader CSVFileReader;
JPanel panelForTable;
JLabel JLabelUseList;
JLabel JLabelDataLoopType;
String Type;
TitledBorder BugPanelBorder;
String DataFile;
Boolean Locked;
JButton JButtonOK = new JButton("Disable");
JCheckBox JCheckBoxRandom = new JCheckBox("Randomize");
JLabel JLabelSpinnerLimit = new JLabel("Limit:");
JSpinner JSpinnerLimit = new JSpinner( new SpinnerNumberModel(0, //initial value
                               0, //min
                              100000, //max
                               1));                //step);
String DataLoopSource;
String URLListName; // values for DataLoopSource are 'urllist' or 'file'
SortedComboBoxModel <String> sortmodel;
String fieldBugTitleOnFocus;
JComboBox jComboBoxMoveToIndex;
JLabel jLabelAddAtPosition;
JComboBox jComboBoxAddAtPosition;

   static final Comparator<String> URLLIST_ORDER = 
                                        new Comparator<String>() {
            public int compare(String list_item1, String list_item2) {
              
              if ("Select a stored URL List".equals(list_item1) || "".equals(list_item1)) { list_item1 = "0-0"; }
              if ("Select a stored URL List".equals(list_item2)|| "".equals(list_item2)) { list_item2 = "0-0"; }
              String[] left_and_right_item1 = list_item1.split("-");
              String[] left_and_right_item2 = list_item2.split("-");
              Integer leftitem1 = Integer.parseInt(left_and_right_item1[0]);
              Integer leftitem2 = Integer.parseInt(left_and_right_item2[0]);
              Integer rightitem1 = Integer.parseInt(left_and_right_item1[1]);
              Integer rightitem2 = Integer.parseInt(left_and_right_item2[1]);
              
            if (leftitem1.compareTo(leftitem2)==0)
             {
                 return rightitem1.compareTo(rightitem2);
                 
             }
            else
             {
                return leftitem1.compareTo(leftitem2);
             }
            }
    };
   
    ProcedureView()
     {
         jComboBoxMoveToIndex = new JComboBox();
        
         fieldBugTitleOnFocus = "";
         DataFile = "";
URLListName = "";
 DataLoopSource = "none";
         limit = 0;
JButtonOK.setActionCommand("Update");
JLabelPass.setOpaque(true);
         myTable=new MyTable(""); 
             JLabelDataLoopType = new JLabel("Set Dataloop Type:");
       JLabelUseList = new JLabel("Use Stored URL List");
JTextFieldDataFile = new JTextField();
JTextFieldDataFile.setVisible(true);
 JButtonBrowseForDataFile = new JButton();

 JButtonBrowseForDataFile.setText("Change Data File");

 JButtonBrowseForDataFile.setVisible(true);
panelForTable = new JPanel();

sortmodel = new SortedComboBoxModel<>(URLLIST_ORDER);

 JComboBoxStoredArrayLists = new JComboBox<String>(sortmodel);
 JComboBoxStoredArrayLists.addItem("Select a stored URL List");
  jComboBoxSetDataLoopType = new JComboBox<>();
 jComboBoxSetDataLoopType.addItem("File");
 jComboBoxSetDataLoopType.addItem("Stored URL List");
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
  

  
 //  JPanelBug.setSize(920, 252);
 //    BugConstraints.fill = GridBagConstraints.NONE;
//     BugConstraints.anchor = GridBagConstraints.WEST;
    BugConstraints.insets = new Insets(2,2,2,2); //top, left, bottom, right
     double global_weightx = 0.0;
     double global_weighty = 0.0;
     JPanel ProcedurePlusIndex = new JPanel();
     ProcedurePlusIndex.add(JLabelBugTitle);
     ProcedurePlusIndex.add(JLabelBugIndex);
     ProcedurePlusIndex.add(JLabelBugTitle2);
     
     
      LeftSideButtonsPanel = new JPanel();
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
 //    MoveButtonsPanel.add(JButtonMoveProcedureUp);
     MoveButtonsPanel.add(jComboBoxMoveToIndex);
 //    MoveButtonsPanel.add(JButtonMoveProcedureDown);
     MoveButtonsPanel.add(JLabelPass);
  //   BugConstraints.insets = new Insets(10,10,10,10);
     BugConstraints.fill = GridBagConstraints.HORIZONTAL;
    AddToGrid(LeftSideButtonsPanel, 0, 0, 1, 3, 0.0, 0.0, GridBagConstraints.WEST); 
     BugConstraints.insets = new Insets(2,2,2,2);
     AddToGrid(ProcedurePlusIndex, 0, 1, 1, 1, global_weightx, global_weighty, GridBagConstraints.WEST);
     AddToGrid(JTextFieldBugTitle, 0, 2, 1, 1, 1.0, global_weighty, GridBagConstraints.WEST );
     RemoveRunButtonsPanel.add(JButtonOK);
     RemoveRunButtonsPanel.add(JButtonDeleteBug);
     RemoveRunButtonsPanel.add(JButtonRunTest);


   AddToGrid(RemoveRunButtonsPanel, 0, 3, 1, 1, global_weightx, global_weighty, GridBagConstraints.WEST);
   AddToGrid(MoveButtonsPanel, 0, 4, 1, 1, global_weightx, global_weighty, GridBagConstraints.EAST);
    JLabelPass.setVisible(false);
   
 //   BugConstraints.insets = new Insets(2,2,4,2);
 //   JPanel DoActionComboPanel = new JPanel();
 //   DoActionComboPanel.add(JLabelDoActions);
 //   DoActionComboPanel.add(JComboBoxDoActions);
jLabelAddAtPosition = new JLabel ("Add at Position:");
jComboBoxAddAtPosition = new JComboBox();
jComboBoxAddAtPosition.addItem(1);
 JPanel AddActionsPanel = new JPanel();
     AddActionsPanel.add(JLabelDoActions);
     AddActionsPanel.add(JComboBoxDoActions);
     AddActionsPanel.add(JLabelPassFailActions);
     AddActionsPanel.add(JComboBoxPassFailActions);
     AddActionsPanel.add(jLabelAddAtPosition);
     AddActionsPanel.add(jComboBoxAddAtPosition);
     
     AddToGrid (AddActionsPanel, 1, 1, 4, 1, global_weightx, global_weighty, GridBagConstraints.WEST);
  //  AddToGrid(JLabelDoActions, 1, 1, 1, 1, global_weightx, global_weighty, GridBagConstraints.EAST);
  //  AddToGrid(JComboBoxDoActions, 1, 2, 1, 1, global_weightx, global_weighty, GridBagConstraints.WEST);
    
  //  AddToGrid (DoActionComboPanel, 2, 1, 1, 1, global_weightx, global_weighty);
     
 //   AddToGrid (JLabelPassFailActions, 1, 3, 1, 1, global_weightx, global_weighty, GridBagConstraints.EAST);
 //   AddToGrid (JComboBoxPassFailActions, 1, 4, 1, 1, global_weightx, global_weighty, GridBagConstraints.WEST); 

 

ActionScrollPane.setVisible(true);
    AddToGrid (ActionScrollPane, 2, 1, 6, 4, global_weightx, global_weighty, GridBagConstraints.WEST);
 
  
    
 JButtonSubmitBug.setActionCommand("Update");
  
    JPanelBug.validate();
     }
    public void refreshjComboBoxAddAtPosition()
    {
        jComboBoxAddAtPosition.removeAllItems();
        int act_index = 1;
        for (ActionView AV: ActionsViewList)
        {
            jComboBoxAddAtPosition.addItem(AV.index);
            act_index++;
        }
        jComboBoxAddAtPosition.addItem(act_index);     
       jComboBoxAddAtPosition.setSelectedItem(act_index);
       
        
    }
     public int getJComboBoxAddAtPosition()
  {
     int ret_val = 1;
     if (jComboBoxAddAtPosition.getItemCount()>0)
     {
     ret_val = (Integer)jComboBoxAddAtPosition.getSelectedItem();
     }
   
     return ret_val;  
  }
     public void addJTextBugTitleFocusListener(FocusListener focuslistener)
     {
        JTextFieldBugTitle.addFocusListener(focuslistener);
     }
    public void addJCheckBoxRandomActionListener(ActionListener listener)
    {
        JCheckBoxRandom.addActionListener(listener);
    }
    public Boolean getRandom()
    {
       return JCheckBoxRandom.isSelected();
    }
    public void setRandom(Boolean randval)
    {
            JCheckBoxRandom.setSelected(randval);
    }
    public int getLimit()
    {
           int fallbackValue = 0;

    
    try {
        this.JSpinnerLimit.commitEdit();
   }
   catch (ParseException pe) {
     
       JComponent editor = this.JSpinnerLimit.getEditor();
       
       if (editor instanceof JSpinner.DefaultEditor) {
           ((JSpinner.DefaultEditor)editor).getTextField().setValue( this.JSpinnerLimit.getValue());
       }
     
        this.JSpinnerLimit.setValue(fallbackValue);
     
      
   }
   limit = (Integer) this.JSpinnerLimit.getValue();
    return limit;
    }
    public void setLimit(int Limit)
    {
           this.limit = Limit;
 this.JSpinnerLimit.setValue(Limit);
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
   public void setURLListName (String in_listname)
   {
       this.URLListName = in_listname;
      
       String[] blanklist = new String[0];
       if ("".equals(in_listname)|| "Select a stored URL List".equals(in_listname))
       {
        
                 JComboBoxStoredArrayLists.setSelectedItem("Select a stored URL List");
         this.setJTableSourceToURLList(blanklist, in_listname);     
          
                
       }
       else
       {
           
        String[] splitter = in_listname.split("-");
         
                String leftpart = splitter[0];
                
                if (this.index+1 > Integer.parseInt(leftpart))
                {
                     JComboBoxStoredArrayLists.setSelectedItem(in_listname);
                    
                   setJTableSourceToURLList(blanklist, in_listname);
                }
                else
                {
                     JComboBoxStoredArrayLists.setSelectedItem("Select a stored URL List");
                    this.setJTableSourceToURLList(blanklist, "");  
                }
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
     public void setBugTitle(String title)
     {
        JTextFieldBugTitle.setText(title);
    
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
        // bugindex++;
         actionindex++;
         String stringactionindex = Integer.toString(actionindex);
        String stringbugindex = Integer.toString(bugindex);
        String textfieldindex_char = Integer.toString(textfieldindex);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
         if (showit)
         {
             this.JLabelAddFieldInstructions.setText("Click Column Header");
             this.JLabelAddFieldInstructions2.setText("to insert field.");
         }
         else
         {
             this.JLabelAddFieldInstructions.setText(" ");
             this.JLabelAddFieldInstructions2.setText(" ");
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
       //  BugLayout.setConstraints(component, BugConstraints);
         JPanelBug.add(component, BugConstraints);
     //    JPanelBug.validate();
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
             class PopUpDemo extends JPopupMenu {
    JMenuItem anItem;
    Action thisAct;
    Procedure this_bug;
    ProcedureView this_bugview;
    
    public PopUpDemo(STAppController mainAppController, Procedure this_bug, ProcedureView this_bugview, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData){
        anItem = new JMenuItem("Clone Procedure");
        add(anItem);
     
        this.this_bug = this_bug;
        this.this_bugview = this_bugview;
      
        anItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ev) {
          STAppFrame.saveState();
          cloneProcedure(mainAppController, this_bug, this_bugview, STAppFrame, STAppData);
      }
    });      
    }
             }
              public void ShowContextMenu(STAppController mainAppController, Procedure this_bug, ProcedureView this_bugview, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, MouseEvent evt)
 {
   ProcedureView.PopUpDemo menu = new ProcedureView.PopUpDemo(mainAppController, this_bug, this_bugview, STAppFrame, STAppData);
        menu.show(evt.getComponent(), evt.getX(), evt.getY());
  
        
 }

          public void cloneProcedure(STAppController mainAppController, Procedure this_bug_in, ProcedureView this_bugview_in, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData)
      {
           int insertionPoint = STAppFrame.getInsertionPoint(); 
           int last_added_bug_index = insertionPoint-1;
          if ("Dataloop".equals(this_bug_in.Type))
          {

      
        String DataFile = "";
        
        if ("file".equals(DataLoopSource))
        {
            DataFile = this_bug_in.DataFile;
        File DataFile_file = new File(DataFile);
       
  
        
   STAppData.AddNewDataLoopFile(DataFile_file, insertionPoint);
 STAppFrame.AddNewDataLoopFileView(DataFile_file, insertionPoint);
  
   ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
   Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
   newbugview.populateJComboBoxStoredArrayLists(STAppData.VarLists);
      mainAppController.AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
  STAppFrame.UpdateDisplay();
 
        }
      else
        {
       DataFile = this_bug_in.URLListName;
           
   STAppData.AddNewDataLoopURLList(DataFile);
        STAppFrame.AddNewDataLoopURLListView(DataFile);
  
  
   ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
   Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
  
     newbugview.populateJComboBoxStoredArrayLists(STAppData.VarLists);
      mainAppController.AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
  STAppFrame.UpdateDisplay();
  
        }
  
   
    

         
          }
          else
          {
           
        
          STAppData.AddNewBug(insertionPoint);
           STAppFrame.AddNewBugView(insertionPoint);
          
   ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
   Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
      mainAppController.AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
  STAppFrame.UpdateDisplay();
  
          
          }
        
           
          
          STAppFrame.BugViewArray.get(last_added_bug_index).setBugTitle(this_bug_in.getBugTitle() + "-CLONE" );
        STAppData.BugArray.get(last_added_bug_index).setBugTitle(this_bug_in.getBugTitle() + "-CLONE" );
          STAppFrame.BugViewArray.get(last_added_bug_index).setLimit(this_bugview_in.getLimit());
            STAppData.BugArray.get(last_added_bug_index).setLimit(this_bug_in.getLimit());
          STAppFrame.BugViewArray.get(last_added_bug_index).setRandom(this_bugview_in.getRandom());
          STAppData.BugArray.get(last_added_bug_index).setRandom(this_bug_in.getRandom());
          Procedure this_bug = STAppData.BugArray.get(last_added_bug_index);
          
          ProcedureView this_bugview = STAppFrame.BugViewArray.get(last_added_bug_index);
          for (Action ACT: this_bug_in.ActionsList)
          {
      String ActionType = ACT.Type;

  
  ActionsMaster NewActionsMaster = new ActionsMaster();
   
   HashMap<String, Action> thisActionHashMap = NewActionsMaster.ActionHashMap;
   HashMap<String, ActionView> thisActionViewHashMap = NewActionsMaster.ActionViewHashMap;
   HashMap<String, Action> thisPassFailActionHashMap = NewActionsMaster.PassFailActionHashMap;
   HashMap<String, ActionView> thisPassFailActionViewHashMap = NewActionsMaster.PassFailActionViewHashMap;
    if (thisActionHashMap.containsKey(ActionType))
           {
               Action thisActionToAdd = (Action) thisActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(ACT.Variable1, ACT.Variable2, ACT.Password, ACT.BoolVal1, ACT.BoolVal2, ACT.Locked);
               thisActionViewToAdd.SetVars(ACT.Variable1, ACT.Variable2, ACT.Password, ACT.BoolVal1, ACT.BoolVal2, ACT.Locked);
               thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, this_bug, this_bugview);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, this_bug, this_bugview);
              
               STAppFrame.AddActionViewToArray(thisActionViewToAdd, this_bugview);
                STAppData.AddActionToArray (thisActionToAdd, this_bug, this_bugview);
                this_bugview.refreshjComboBoxAddAtPosition();
          //    window.UpdateDisplay();
           }      
 
     if (thisPassFailActionHashMap.containsKey(ActionType))
             {
               Action thisActionToAdd = (Action) thisPassFailActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisPassFailActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(ACT.Variable1, ACT.Variable2, ACT.Password, ACT.BoolVal1, ACT.BoolVal2, ACT.Locked);
               thisActionViewToAdd.SetVars(ACT.Variable1, ACT.Variable2, ACT.Password, ACT.BoolVal1, ACT.BoolVal2, ACT.Locked);
               thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, this_bug, this_bugview);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, this_bug, this_bugview);
           
               STAppFrame.AddActionViewToArray(thisActionViewToAdd, this_bugview);
            STAppData.AddActionToArray (thisActionToAdd, this_bug, this_bugview);
          //    window.UpdateDisplay();
          this_bugview.refreshjComboBoxAddAtPosition();
             }
      }
                STAppFrame.UpdateDisplay();
        JComponent component = (JComponent) STAppFrame.MainScrollPane.getViewport().getView();
           
    Rectangle bounds =  STAppFrame.BugViewArray.get(last_added_bug_index).JPanelBug.getBounds();
   
      component.scrollRectToVisible(bounds);
      }

       public void addJButtonOKActionListener(ActionListener listener)
       {
       JButtonOK.addActionListener(listener);
           
       }
       public void addRightClickPanelListener(STAppController mainAppController, Procedure newbug, ProcedureView newbugview, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData)
       {
             JPanelBug.addMouseListener(new MouseAdapter(){
    @Override
       public void mouseClicked(java.awt.event.MouseEvent evt) {
                 
                    if (evt.getButton()==3)
                    {
                        ShowContextMenu(mainAppController, newbug, newbugview, STAppFrame, STAppData, evt);
                    }
                }
             });
                     }
 //        public void addJButtonMoveProcedureUpActionListener(ActionListener listener)
 //      {
 //      JButtonMoveProcedureUp.addActionListener(listener);
           
 //      }
//         public void addJButtonMoveProcedureDownActionListener(ActionListener listener)
//         {
//         JButtonMoveProcedureDown.addActionListener(listener);    
//         }
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
  public void addJSpinnerLimitListener(ChangeListener listener)
  {
    
    JComponent comp = JSpinnerLimit.getEditor();
    JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
    DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
    formatter.setCommitsOnValidEdit(true);
    JSpinnerLimit.addChangeListener(listener);
    
  }
  public String getStoredArrayListName()
  {
      return URLListName;
  }
  public void addJComboBoxMoveToIndex(ItemListener listener)
  {
      jComboBoxMoveToIndex.addItemListener(listener);
  }
  public void addJComboBoxStoredArrayListsItemListener(ItemListener listener)
  {
      JComboBoxStoredArrayLists.addItemListener(listener);
  }
   public void addjComboBoxSetDataLoopTypeItemListener(ItemListener listener)
  {
     jComboBoxSetDataLoopType.addItemListener(listener);
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
       String stringbugindex = Integer.toString(this.index);
       
       this.JLabelBugIndex.setText(stringbugindex);
        if ("Procedure".equals(this.Type))
        {
  JLabel ActionScrollPaneTitle = new JLabel ("Procedure " + stringbugindex + " actions:");
  ActionScrollPane.setColumnHeaderView(ActionScrollPaneTitle);
 BugPanelBorder = BorderFactory.createTitledBorder("Procedure " + stringbugindex);
         JPanelBug.setBorder(BugPanelBorder);
         setBugLabelType("Procedure");
         
        }
        else
        {
              
   JLabel ActionScrollPaneTitle = new JLabel ("Data Loop " + stringbugindex + " actions:");
    ActionScrollPane.setColumnHeaderView(ActionScrollPaneTitle);
     BugPanelBorder = BorderFactory.createTitledBorder("Data Loop " + stringbugindex);
         JPanelBug.setBorder(BugPanelBorder);
          setBugLabelType("Data Loop");
        }
   }
   public void setBugLabelType(String labeltype)
   {
       JLabelBugTitle.setText(labeltype);
   }
   public void EnableArrayListsPulldown()
   {
    this.JComboBoxStoredArrayLists.setEnabled(true);
   }
   public void populateJComboBoxStoredArrayLists( HashMap<String, String[]> VarLists)
   {
              for (String keyname: VarLists.keySet())
        {
     String[] parts = keyname.split("-");
 String leftpart = parts[0];
 if (!"".equals(leftpart))
 {
 int bugindex = Integer.parseInt(leftpart);
 if (bugindex<index+1)
 {       
           JComboBoxStoredArrayLists.addItem(keyname);
 }
        }
          }
              JComboBoxStoredArrayLists.setSelectedItem(URLListName);
   }
   public void setJComboBoxStoredArraylists(String itemname)
   {
       
       JComboBoxStoredArrayLists.setSelectedItem(itemname);
   }
    public void setjComboBoxSetDataLoopType(String itemname)
   {
       if ("file".equals(itemname))
       {
       jComboBoxSetDataLoopType.setSelectedItem("File");
       }
       else
       {
           jComboBoxSetDataLoopType.setSelectedItem("Stored URL List");
       }
     
   }
   
      public void addJButtonBrowseForDataFileActionListener(ActionListener listener)
     {
         JButtonBrowseForDataFile.addActionListener(listener);
     }
     public void setJTextFieldDataFile(String dataFile)
     {
         JTextFieldDataFile.setText(dataFile);

     }
     public void updatePlacedLoopVars(String newsource)
     {
         Boolean isStoredList = true;
          
             if ("file".equals(DataLoopSource))
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
          
             }
         }
    
     }
    public void AddTableToGrid()
    {
  //       myTable.DataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
     myTable.DataTable.getTableHeader().setReorderingAllowed(false);

  myTable.DataTable.setFillsViewportHeight( true );

    panelForTable.removeAll();
  
   JTableScrollPane = new JScrollPane(myTable.DataTable, 
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    
   //   JTableScrollPane.setSize(new Dimension(700, 120));
     JTableScrollPane.setVisible(true);
     
  
   panelForTable.setLayout(new BorderLayout());
  //  panelForTable.add(JLabelAddFieldInstructions, BorderLayout.PAGE_START);
    panelForTable.add(JTextFieldDataFile,BorderLayout.PAGE_END );
    JPanel panelForBrowseAndPulldown = new JPanel();
//    EnableArrayListsPulldown(false);
//    JComboBoxStoredArrayLists.setEnabled(false);
    panelForBrowseAndPulldown.add(JLabelDataLoopType);
    panelForBrowseAndPulldown.add(jComboBoxSetDataLoopType);
     panelForBrowseAndPulldown.add(JButtonBrowseForDataFile);
      panelForBrowseAndPulldown.add(JLabelUseList);
       panelForBrowseAndPulldown.add(JComboBoxStoredArrayLists);
    if ("file".equals(DataLoopSource))
    {
       jComboBoxSetDataLoopType.setSelectedItem("File");
       JLabelUseList.setVisible(false);
       JComboBoxStoredArrayLists.setVisible(false);
       JButtonBrowseForDataFile.setVisible(true);
    }
    else
    {
   
    jComboBoxSetDataLoopType.setSelectedItem("Stored URL List");
    JButtonBrowseForDataFile.setVisible(false);
     JLabelUseList.setVisible(true);
       JComboBoxStoredArrayLists.setVisible(true);
   
    }
    panelForBrowseAndPulldown.add(JLabelSpinnerLimit);
    panelForBrowseAndPulldown.add(JSpinnerLimit);
    panelForBrowseAndPulldown.add(JCheckBoxRandom);
 //   myTable.setColumnWidth(panelForTable.getWidth()-4);
    panelForTable.add(panelForBrowseAndPulldown, BorderLayout.PAGE_START);
    if (myTable.number_of_records<2)
    {
    panelForTable.setPreferredSize(new Dimension(700, 120));
    }
    else
    {
     panelForTable.setPreferredSize(new Dimension(700, 220));    
    }
  panelForTable.add(JTableScrollPane, BorderLayout.CENTER);
   jPanelFieldInstructions = new JPanel();
   jPanelFieldInstructions.setLayout(new BoxLayout(jPanelFieldInstructions, BoxLayout.Y_AXIS));
  jPanelFieldInstructions.add(JLabelAddFieldInstructions);
  jPanelFieldInstructions.add(JLabelAddFieldInstructions2);
  
   
   AddToGrid(jPanelFieldInstructions, 9, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST);
  
    AddToGrid(panelForTable, 9, 1, 4, 1, 1, 1, GridBagConstraints.WEST);
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
    JPanelBug.validate();         
    }
     public void setDataFile (String data_file)
     { 
         DataFile = data_file;
     }
     public void setJTableSourceToFile (String sourceCSVfile)
     {
     updatePlacedLoopVars(sourceCSVfile);
         JPanelBug.remove(panelForTable);
     myTable = null;
     myTable = new MyTable(sourceCSVfile);
    
         JTextFieldDataFile.setText(sourceCSVfile);
     AddTableToGrid();
     
     
         
     }
     public void setDataLoopSource(String in_looptype)
     {
         if (in_looptype.equals(this.DataLoopSource))
         {
             
         }
         else
         {
         this.DataLoopSource = in_looptype;
         
         setjComboBoxSetDataLoopType(in_looptype);
         }
     }
  
     public void setJTableSourceToURLList(String[] in_list, String list_name)
     {
      updatePlacedLoopVars(list_name);
         JPanelBug.remove(panelForTable);
  //   myTable = null;
     myTable = new MyTable(in_list, list_name);
     if (in_list.length>0)
     {
     myTable.populateTableWithURLListRunTimeEntries();
    
     }
    
         if ("".equals(list_name) || "Select a stored URL List".equals(list_name))
    {
        JTextFieldDataFile.setText ("No data file or URL list set.");
    }
    else
    {
   
    URLListName = list_name;
    JTextFieldDataFile.setText("Will use stored URL List " + list_name);
 // this should not be done? 
//   JComboBoxStoredArrayLists.addItem(list_name);
 //   SetJComboBoxStoredArraylists(list_name);
    }
          AddTableToGrid();   
     }
     

  
}