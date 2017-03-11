
package browsermator.com;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.ParseException;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;



public class SeleniumTestTool extends JInternalFrame {

String URL;
String filename;
ArrayList<Procedure> BugArray = new ArrayList<Procedure>();
ArrayList<ProcedureView> BugViewArray = new ArrayList<ProcedureView>();
    ArrayList<String> AllFieldValues;
  JPanel BugPanel;
  Thread ActionThread;
  Boolean AllTestsPassed;
  Boolean ShowReport;
  Boolean EmailReport;
  Boolean EmailReportFail;
  Boolean ExitAfter;
  Boolean PromptToClose;
  Boolean changes;
  String TargetBrowser;
  String OSType;
  int WaitTime;
  int Sessions;
  boolean hasStoredVar;
  boolean hasStoredArray;
  boolean IncludeScreenshots;
  HashMap<String, String> VarHashMap = new HashMap();
  HashMap<String, ArrayList> VarLists = new HashMap();
  boolean cancelled;
  boolean testRunning;
  String short_filename;
  public SeleniumTestTool(String filename)
  {
      this.cancelled = false;
    this.hasStoredVar = false;
    this.hasStoredArray = false;
   this.VarHashMap = new HashMap();
   // super("Selenium Test Tool");
  this.TargetBrowser = "Chrome";
  this.OSType = "Windows32";
  this.Sessions = 1;
  this.changes = false;
  this.PromptToClose = false;
  this.ShowReport = false;
  this.filename = "";
  this.short_filename=filename;
  this.URL = "";
  this.BugPanel = new JPanel();
  this.IncludeScreenshots = false;
  this.testRunning = false;
  this.AllFieldValues = new ArrayList<>();
 this.setIconifiable(true);
      initComponents();
jButtonPlaceStoredVariable.setFocusable(false);
jComboBoxStoredVariables.setFocusable(false);
       jCheckBoxEmailReport.addActionListener(new java.awt.event.ActionListener() {
          @Override
           public void actionPerformed(java.awt.event.ActionEvent evt) {
              jCheckBoxEmailReportActionPerformed(evt);
            }
        });
       jCheckBoxExitAfter.addActionListener(new java.awt.event.ActionListener() {
          @Override
           public void actionPerformed(java.awt.event.ActionEvent evt) {
              jCheckBoxExitAfterActionPerformed(evt);
            }
        });
        jCheckBoxEmailReportFail.addActionListener(new java.awt.event.ActionListener() {
           @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              jCheckBoxEmailReportFailActionPerformed(evt);
            }
        });
         jCheckBoxPromptToClose.addActionListener(new java.awt.event.ActionListener() {
          @Override
             public void actionPerformed(java.awt.event.ActionEvent evt) {
              jCheckBoxPromptToCloseActionPerformed(evt);
            }
        });
                  jCheckBoxShowReport.addActionListener(new java.awt.event.ActionListener() {
           @Override
                      public void actionPerformed(java.awt.event.ActionEvent evt) {
              jCheckBoxShowReportActionPerformed(evt);
            }
        });
         jCheckBoxOSTypeWindows32.addActionListener(new java.awt.event.ActionListener() {
           @Override
             public void actionPerformed(java.awt.event.ActionEvent evt) {
              jCheckBoxOSTypeWindows32ActionPerformed(evt);
            }
        });
             jCheckBoxOSTypeWindows64.addActionListener(new java.awt.event.ActionListener() {
           @Override
             public void actionPerformed(java.awt.event.ActionEvent evt) {
              jCheckBoxOSTypeWindows64ActionPerformed(evt);
            }
        });
          jCheckBoxOSTypeMac.addActionListener(new java.awt.event.ActionListener() {
           @Override
              public void actionPerformed(java.awt.event.ActionEvent evt) {
              jCheckBoxOSTypeMacActionPerformed(evt);
            }
        });
           jCheckBoxOSTypeLinux32.addActionListener(new java.awt.event.ActionListener() {
           @Override
               public void actionPerformed(java.awt.event.ActionEvent evt) {
              jCheckBoxOSTypeLinux32ActionPerformed(evt);
            }
        });
            jCheckBoxOSTypeLinux64.addActionListener(new java.awt.event.ActionListener() {
           @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
              jCheckBoxOSTypeLinux64ActionPerformed(evt);
            }
        });
      
  
 // this.setVisible(true);
  this.EmailReport = false;
  this.EmailReportFail = false;
  this.ExitAfter = false;
  this.AllTestsPassed = false;
  try{
      LoadGlobalEmailSettings();
  }
      catch (Exception e) {
	System.out.println("Exception on loading email settings: " + e);
    }

}
public void setCurrentlySelectedFieldToStoredVariable(String fieldindex, int fieldnum)
{
    String[] indexes = fieldindex.split("-");
    String bugindex = indexes[0];
    String actionindex = indexes[1];
    int bugindexnum = Integer.parseInt(bugindex)-1;
    int actionindexnum = Integer.parseInt(actionindex)-1;
    BugViewArray.get(bugindexnum).ActionsViewList.get(actionindexnum).setFieldToStoredVariable(jComboBoxStoredVariables.getSelectedItem().toString(), fieldnum);

}
public void setHasStoredVar(boolean hasit)
{
    this.hasStoredVar=hasit;
}
public void setHasStoredArray(boolean hasit)
{
    this.hasStoredArray=hasit;
}

public void initVarHashMap()
{
    this.VarHashMap = new HashMap();
}

public void initVarLists()
{
    
   this.VarLists = new HashMap();

   
}
  public String getSelectedVariableName()
      {
          String ret_val = "";
          ret_val = (String)jComboBoxStoredVariables.getSelectedItem();
          if (ret_val=="Select a stored variable") { ret_val = ""; }
          return ret_val;  
      }
  public void UpdateDataLoopTable(String ListName, ArrayList<String> storedURLlist, Procedure thisproc, ProcedureView thisprocview)
  {

    thisprocview.myTable.setSourceToURLList(storedURLlist);   
    thisproc.DataSet.setSourceToURLList(storedURLlist);
   
  }
         
     
  
  
   public void addSelectedArrayName(String varname)
      {
          int itemcount = 0;
       for (ProcedureView BV : BugViewArray)
      {
         
          if ("Dataloop".equals(BV.Type))
          {
                   if(((DefaultComboBoxModel)BV.JComboBoxStoredArrayLists.getModel()).getIndexOf(varname) == -1) {
 String[] parts = varname.split("-");
 if (parts.length>1)
 {
 String leftpart = parts[0];

 int bugindex = Integer.parseInt(leftpart);
 if (bugindex<BV.index+1)
 {
                       BV.JComboBoxStoredArrayLists.addItem(varname);

 
  //      BV.JComboBoxStoredArrayLists.setSelectedIndex(newitemindex); 
 }
 
          }
  itemcount = BV.JComboBoxStoredArrayLists.getItemCount();
 int newitemindex = itemcount-1;
                   if (itemcount>1)
                   {
                       BV.EnableArrayListsPulldown(true);
                     
                   }
                   }
      }   
 
}
      
      }
    public void updatePlacedVariables(String to_updatename, String update_toname)
    {
    int bugindex = 0;
    if (!"".equals(to_updatename))
    {
    for (ProcedureView BV : BugViewArray)
      {
            for (ActionView AV : BV.ActionsViewList )
        {
            String testing = AV.JTextFieldVariable1.getText();
            
            if (AV.JTextFieldVariable1.getText().contains("[stored_varname"))
            {
               AV.JTextFieldVariable1.setText("[stored_varname-start]" + update_toname + "[stored_varname-end]");
           //    UpdateScrollPane(BV);
               
            }
              if (AV.JTextFieldVariable2.getText().contains("[stored_varname"))
            {
               AV.JTextFieldVariable2.setText("[stored_varname-start]" + update_toname + "[stored_varname-end]");
           //    UpdateScrollPane(BV);
            }
            
        }
      } 
    }
    }
    
      public void addSelectedVariableName(String varname)
      {
         
       if(((DefaultComboBoxModel)jComboBoxStoredVariables.getModel()).getIndexOf(varname) == -1) {
 jComboBoxStoredVariables.addItem(varname);
 int newitemindex = jComboBoxStoredVariables.getItemCount()-1;
        jComboBoxStoredVariables.setSelectedIndex(newitemindex);
}
      }
 

    public void updateSelectedArrayName(String oldname, String newname)
    {

          
        if (VarLists.containsKey(newname))
        {       
      
            
       int bugindex = 0;  
            for (Procedure PROC: BugArray)
      {
          if (oldname.equals(PROC.DataFile))
          {
      PROC.setDataFile(newname);
      BugViewArray.get(bugindex).setJTableSource(newname);
        BugViewArray.get(bugindex).JComboBoxStoredArrayLists.setEnabled(false);

      BugViewArray.get(bugindex).JComboBoxStoredArrayLists.addItem(newname);
  BugViewArray.get(bugindex).JComboBoxStoredArrayLists.setSelectedItem(newname); 
         BugViewArray.get(bugindex).JComboBoxStoredArrayLists.setEnabled(true);
   //   UpdateDisplay();
          }
          bugindex++;
      }
        }
        else
        {
            VarLists.remove(oldname);
            VarLists.put(newname, new ArrayList());
  //          updatePlacedListVariables();
         
             int bugindex = 0;  
            for (Procedure PROC: BugArray)
      {
          if (oldname.equals(PROC.DataFile))
          {
     
      
      PROC.setDataFile(newname);
     
      BugViewArray.get(bugindex).setJTableSource(newname);
        BugViewArray.get(bugindex).JComboBoxStoredArrayLists.setEnabled(false);
      BugViewArray.get(bugindex).JComboBoxStoredArrayLists.removeItem(oldname);
      BugViewArray.get(bugindex).JComboBoxStoredArrayLists.addItem(newname);
          BugViewArray.get(bugindex).JComboBoxStoredArrayLists.setSelectedItem(newname); 
        BugViewArray.get(bugindex).JComboBoxStoredArrayLists.setEnabled(true);
  
   // UpdateStoredListsPulldown(newname);
          }
          bugindex++;
      }
        }
      
     
    
    }
    public void updateSelectedVariableName(String oldname, String newname)
      {
      
          
        if (VarHashMap.containsKey(newname))
        {       
            updatePlacedVariables(newname, oldname);
            updatePlacedVariables(oldname, newname);
            VarHashMap.put(newname, "");
            VarHashMap.put(oldname, "");
        }
        else
        {
            VarHashMap.remove(oldname);
            VarHashMap.put(newname, "");
           updatePlacedVariables(oldname, newname);
        }
      UpdateStoredVarPulldown();
       
      }
  public void UpdateStoredVarPulldown()
  {
        jComboBoxStoredVariables.removeAllItems();
        jComboBoxStoredVariables.addItem("Select a stored variable");
        for (String keyname: VarHashMap.keySet())
        {
            jComboBoxStoredVariables.addItem(keyname);
        }
  }
    public void UpdateStoredListsPulldown(String selecteditem)
  {
      for (ProcedureView BV : BugViewArray)
      {
         
          if ("Dataloop".equals(BV.Type))
          {
              BV.JComboBoxStoredArrayLists.setEnabled(false); 
                 BV.JComboBoxStoredArrayLists.removeAllItems();
               
        BV.JComboBoxStoredArrayLists.addItem("Select a stored URL list");
        for (String keyname: VarLists.keySet())
        {
     String[] parts = keyname.split("-");
 String leftpart = parts[0];
 if (!"".equals(leftpart))
 {
 int bugindex = Integer.parseInt(leftpart);
 if (bugindex<BV.index+1)
 {       
           BV.JComboBoxStoredArrayLists.addItem(keyname);
 }
        }
          }
       
   //    BV.JComboBoxStoredArrayLists.setSelectedItem(selecteditem);
        BV.JComboBoxStoredArrayLists.setEnabled(true);
      }
          
      }
 
    
  }
  
public String GetStoredVariableValue(String fieldname)
{
    String ret_val = "";
if (VarHashMap.containsKey(fieldname))
{
    ret_val = VarHashMap.get(fieldname);
}

return ret_val;
}

public void SetStoredVariableName(String varname)
{
    VarHashMap.put(varname, "");  
    addSelectedVariableName(varname);
    
}
public void SetStoredVariableValue (String varname, String varval)
{

    if (VarHashMap.containsKey(varname))
    {
        VarHashMap.put(varname, varval);
    }

}
public void ShowStoredVarListControls(Boolean showhideval)
{
 for (ProcedureView DLV: BugViewArray)
 {
     if ("Dataloop".equals(DLV.Type))
     {
    DLV.EnableArrayListsPulldown(showhideval);
     }
 }
}
public void ShowStoredVarControls(Boolean showhideval)
{
    if (showhideval)
    {
      jButtonPlaceStoredVariable.setVisible(true);
  jButtonPlaceStoredVariable.setEnabled(false);
    jLabelStoredVariables.setVisible(true);
    jComboBoxStoredVariables.setVisible(true);   
    
    }
    else
    {
    jButtonPlaceStoredVariable.setVisible(false);
    jComboBoxStoredVariables.setVisible(false);
    jLabelStoredVariables.setVisible(false);
    }
}

public void ShowPlaceStoredVariableButton(Boolean showhideval, int bugindex, int actionindex, int fieldnum)
{
    if (showhideval)
    {
     jButtonPlaceStoredVariable.setVisible(true);   
     jButtonPlaceStoredVariable.setEnabled(true); 
       
            String stringactionindex = Integer.toString(actionindex+1);
        String stringbugindex = Integer.toString(bugindex);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
     jButtonPlaceStoredVariable.setActionCommand(bugdashactionindex);
     addjButtonPlaceStoredVariableActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
   String ACommand = evt.getActionCommand();

       setCurrentlySelectedFieldToStoredVariable(ACommand, fieldnum);
       
 
  
        }
      }
    );
     
    }
    else
    {
        
    jButtonPlaceStoredVariable.setEnabled(false);      
    }
}

public void setAllFieldValues(ArrayList<String> allfieldvalues)
{
    this.AllFieldValues = allfieldvalues;
}
      public final void LoadGlobalEmailSettings() throws IOException 
 {
     Properties applicationProps = new Properties();
    String userdir = System.getProperty("user.home");
try
{
         try (FileInputStream input = new FileInputStream(userdir + File.separator + "browsermator_config.properties")) {
             applicationProps.load(input);
         }
         catch (Exception e)
         {
             System.out.println(e);
         }
}
catch (Exception e) {
			System.out.println("Exception loading email70: " + e);
		} 

   String smtp_hostname = applicationProps.getProperty("smtp_hostname");
   String login_name = applicationProps.getProperty("email_login_name");
   String password = applicationProps.getProperty("email_login_password");
   String to = applicationProps.getProperty("email_to");
   String from = applicationProps.getProperty("email_from");
   String subject = applicationProps.getProperty("email_subject");
 
   setSMTPHostname(smtp_hostname);
   setEmailLoginName(login_name);
   try
   {
   password = Protector.decrypt(password);
   }
   catch (Exception ex)
   {
       System.out.println("Exception getting email password: " + ex.toString());
   }
   setEmailPassword(password);
   setEmailTo(to);
   setEmailFrom(from);
   setSubject(subject);
   
        
	}
    
      
      
      public boolean getExitAfter()
      {
          boolean checked = this.jCheckBoxExitAfter.isSelected();
          this.ExitAfter = checked;
          return checked;
      }
      
      public boolean getEmailReport()
    {
    boolean checked = this.jCheckBoxEmailReport.isSelected();
    this.EmailReport = checked;
    return checked;
    }
         public boolean getIncludeScreenshots()
    {
         boolean checked = this.jCheckBoxIncludeScreenshots.isSelected();
         if (getShowReport()==false)
         {
             checked = false;
         }
    this.IncludeScreenshots = checked;
    return checked;
       
    }
      public boolean getEmailReportFail()
    {
    boolean checked = this.jCheckBoxEmailReportFail.isSelected();
    this.EmailReportFail = checked;
    return checked;
    }
    public void setPromptToClose(Boolean prompttoclose)
    {
        this.PromptToClose = prompttoclose;
        jCheckBoxPromptToClose.setSelected(prompttoclose);
     
    }
    public boolean getPromptToClose()
    {
       boolean checked = this.jCheckBoxPromptToClose.isSelected();
    this.PromptToClose = checked;
    return checked;  
    }
 
    public boolean getShowReport()
    {
    boolean checked = this.jCheckBoxShowReport.isSelected();
    this.ShowReport = checked;
    return checked;
    }
public int GetWaitTime()
{
    int fallbackValue = 1;


    
    try {
       jSpinnerWaitTime.commitEdit();
   }
   catch (ParseException pe) {
       // Edited value is invalid, spinner.getValue() will return
       // the last valid value, you could revert the spinner to show that:
       JComponent editor =jSpinnerWaitTime.getEditor();
       
       if (editor instanceof DefaultEditor) {
           ((DefaultEditor)editor).getTextField().setValue(jSpinnerWaitTime.getValue());
       }
       // reset the value to some known value:
       jSpinnerWaitTime.setValue(fallbackValue);
       // or treat the last valid value as the current, in which
       // case you don't need to do anything.
      
   }
   int wait = (Integer)this.jSpinnerWaitTime.getValue();
    return wait;
    
}
   public void setWaitTime (int wait_time)
    {   
    
        this.WaitTime = wait_time;

        this.jSpinnerWaitTime.setValue(wait_time);
    }
   public void setSessions (int number_of_sessions)
   {
       this.Sessions = number_of_sessions;
       this.jSpinnerSessions.setValue(number_of_sessions);
   }
   public int getSessions()
{
    int fallbackValue = 1;


    
    try {
       jSpinnerSessions.commitEdit();
   }
   catch (ParseException pe) {
       // Edited value is invalid, spinner.getValue() will return
       // the last valid value, you could revert the spinner to show that:
       JComponent editor =jSpinnerSessions.getEditor();
       
       if (editor instanceof DefaultEditor) {
           ((DefaultEditor)editor).getTextField().setValue(jSpinnerSessions.getValue());
       }
       // reset the value to some known value:
       jSpinnerSessions.setValue(fallbackValue);
       // or treat the last valid value as the current, in which
       // case you don't need to do anything.
      
   }
   int wait = (Integer)this.jSpinnerSessions.getValue();
    return wait;
    
}
public void setProperties (String filename)
    {
    Path thisP = Paths.get(filename);
    
    
String shortname = thisP.getFileName().toString();
if (shortname.length()>1)
{
  int end_of_name = shortname.indexOf(".browsermation");
  
if (end_of_name<1)
{
    end_of_name=shortname.length();

}

   this.short_filename = shortname.substring(0, end_of_name );
}
else
{
    this.short_filename = shortname;
}


    this.setTitle(this.short_filename); 
    this.filename = filename;
    
    }
   
    @SuppressWarnings("unchecked")

 public void disableAdds()
 {
   jButtonNewBug.setEnabled(false);
   jButtonNewDataLoop.setEnabled(false);
   for (ProcedureView PV: BugViewArray)
   {
       PV.JComboBoxDoActions.setEnabled(false);
       PV.JComboBoxPassFailActions.setEnabled(false);
       PV.JButtonGoAction.setEnabled(false);
       PV.JButtonClickAtXPATH.setEnabled(false);
       PV.JButtonTypeAtXPATH.setEnabled(false);
       PV.JButtonFindXPATHPassFail.setEnabled(false);
       PV.JButtonDoNotFindXPATHPassFail.setEnabled(false); 
       PV.JButtonYesNoPromptPassFail.setEnabled(false);
   }
   
 }
 public void enableAdds()
 {
  jButtonNewBug.setEnabled(true);
   jButtonNewDataLoop.setEnabled(true);
   for (ProcedureView PV: BugViewArray)
   {
       PV.JComboBoxDoActions.setEnabled(true);
       PV.JComboBoxPassFailActions.setEnabled(true);
       PV.JButtonGoAction.setEnabled(true);
       PV.JButtonClickAtXPATH.setEnabled(true);
       PV.JButtonTypeAtXPATH.setEnabled(true);
       PV.JButtonFindXPATHPassFail.setEnabled(true);
       PV.JButtonDoNotFindXPATHPassFail.setEnabled(true); 
       PV.JButtonYesNoPromptPassFail.setEnabled(true);
   }    
 }
 public void clearPassFailColors()
 {
   for (ProcedureView PV: BugViewArray)
   {
       PV.JLabelPass.setText("");
       for (ActionView TAV: PV.ActionsViewList)
       {
           
           TAV.JButtonDragIt.setBackground(new JButton().getBackground());
       }
   }     
 }
 public void setRunActionsButtonName(String newtext)
 {
     jButtonDoStuff.setText(newtext);
 }
 public String getRunActionsButtonName()
 {
     return jButtonDoStuff.getText();
 }
 public void setFlattenFileButtonName (String newtext)
 {
     jButtonFlattenFile.setText(newtext);
 }
 public void ScrollActionPaneDown(ProcedureView bugview)
 {
  
        JScrollBar action_scroll_pane_vertical = bugview.ActionScrollPane.getVerticalScrollBar();
 action_scroll_pane_vertical.setValue( action_scroll_pane_vertical.getMaximum() );         
 }
 
 public void UpdateDisplay()
 {
 this.MainScrollPane.setVisible(false);
 
 
 ModifiedFlowLayout layout = new ModifiedFlowLayout();
 
//     layout.setVgap(1);
 //  layout.setHgap(5);

this.BugPanel.removeAll();

this.BugPanel.setLayout(layout);


   setHasStoredArray(false);
int bugindex = 0;
    for (ProcedureView BV : BugViewArray)
      {

          BV.SetIndex(bugindex);
          
          this.BugPanel.add(BV.JPanelBug);
     JPanel ActionAdderPanel = new JPanel();

     
          this.BugPanel.add(ActionAdderPanel);
  
   GridBagLayout ActionLayout = new GridBagLayout();
   GridBagConstraints ActionConstraints = new GridBagConstraints();
            JPanel ActionPanel = new JPanel();

            
      ActionPanel.setLayout(ActionLayout); 
      
     ActionConstraints.fill = GridBagConstraints.NONE;
     ActionConstraints.anchor = GridBagConstraints.WEST;
 
      int actionindex = 0;
      
      for (ActionView AV : BV.ActionsViewList )
        {

       
        AV.SetIndexes(bugindex, actionindex);
    if ("StoreLinksAsURLListByXPATH".equals(AV.ActionType))
        {
            
            setHasStoredArray(true);
                             String stringactionindex = Integer.toString(actionindex+1);
        String stringbugindex = Integer.toString(BV.index+1);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
        String oldname = AV.JTextFieldVariableVARINDEX.getText();
         String newname = bugdashactionindex;
         if (oldname.equals(newname))
         {
          // addSelectedVariableName(AV.JTextFieldVariableVARINDEX.getText());
         
         addSelectedArrayName(AV.JTextFieldVariableVARINDEX.getText());
          }
          else
         {
              if ("".equals(oldname))
              {
              addSelectedArrayName(AV.JTextFieldVariableVARINDEX.getText());    
              }
              else
              {
                 
         updateSelectedArrayName(oldname, newname);
              }
        }
          AV.JTextFieldVariableVARINDEX.setText(newname);
    
        }
       if ("StoreLinkAsVarByXPATH".equals(AV.ActionType))
        {
              setHasStoredVar(true);
                                      String stringactionindex = Integer.toString(actionindex+1);
        String stringbugindex = Integer.toString(BV.index+1);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
        String oldname = AV.JTextFieldVariableVARINDEX.getText();
         String newname = bugdashactionindex;
      
              if ("".equals(oldname))
          {
           addSelectedVariableName(bugdashactionindex);
           AV.JTextFieldVariableVARINDEX.setText(bugdashactionindex);
         
          }
          else
          {
         updateSelectedVariableName(oldname, newname);
     //    updateInsertedVariableNames(oldname, newname);
          AV.JTextFieldVariableVARINDEX.setText(newname);
          }
 
        }
    
  //      AV.AddDraggers(this, this.BugArray.get(bugindex), BV, AV);
         ActionConstraints.gridx = 1;
         ActionConstraints.gridy = actionindex;
         ActionConstraints.gridwidth = 1;
         ActionConstraints.gridheight = 1;
         ActionLayout.setConstraints(AV.JPanelAction, ActionConstraints);
         
         ActionPanel.add(AV.JPanelAction);
    
         actionindex++;

        }
      if (actionindex < 9)
      {
     BV.ActionScrollPane.setPreferredSize(new Dimension(1024, 36*actionindex+40));
          }
      
BV.ActionScrollPane.setVisible(true);
       BV.ActionScrollPane.setViewportView(ActionPanel);

   
bugindex++;
     }
if (hasStoredVar)
{
    ShowStoredVarControls(true);
}
else
{
    ShowStoredVarControls(false);
}
 if (hasStoredArray)
{
    ShowStoredVarListControls(true);
}
else
{
    ShowStoredVarListControls(false);
}

  this.setProperties(this.filename);
     this.MainScrollPane.setViewportView(this.BugPanel);

     this.MainScrollPane.setVisible(true);
  this.revalidate();

 }
     public void UpdateScrollPane(ProcedureView newbugview)
     {
              GridBagConstraints ActionConstraints = new GridBagConstraints();
        
          
            JPanel ActionPanel = (JPanel)newbugview.ActionScrollPane.getViewport().getView();
            ActionPanel.removeAll();
              GridBagLayout ActionLayout = new GridBagLayout();
      ActionPanel.setLayout(ActionLayout); 
      
     ActionConstraints.fill = GridBagConstraints.NONE;
     ActionConstraints.anchor = GridBagConstraints.WEST;            
         int actionindex = 0;

      for (ActionView AV : newbugview.ActionsViewList )
        {
       
          AV.SetIndexes(newbugview.index, actionindex);
          if ("StoreLinksAsURLListByXPATH".equals(AV.ActionType))
        {
            
            setHasStoredArray(true);
                      String stringactionindex = Integer.toString(actionindex+1);
        String stringbugindex = Integer.toString(newbugview.index+1);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
        String oldname = AV.JTextFieldVariableVARINDEX.getText();
         String newname = bugdashactionindex;
         if (oldname.equals(newname))
        {
          // addSelectedVariableName(AV.JTextFieldVariableVARINDEX.getText());
         
       addSelectedArrayName(AV.JTextFieldVariableVARINDEX.getText());
         }
       else
        {
              if ("".equals(oldname))
              {
              addSelectedArrayName(AV.JTextFieldVariableVARINDEX.getText());    
              }
              else
              {
                       
         updateSelectedArrayName(oldname, newname);
              }
          AV.JTextFieldVariableVARINDEX.setText(newname);
        }
         }

        if ("StoreLinkAsVarByXPATH".equals(AV.ActionType))
        {
                              String stringactionindex = Integer.toString(actionindex+1);
        String stringbugindex = Integer.toString(newbugview.index+1);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
        String oldname = AV.JTextFieldVariableVARINDEX.getText();
         String newname = bugdashactionindex;
         
         if ("".equals(oldname))
          {
           addSelectedVariableName(bugdashactionindex);
         
         
          }
          else
          {
         updateSelectedVariableName(oldname, newname);
     //    updateInsertedVariableNames(oldname, newname);
          AV.JTextFieldVariableVARINDEX.setText(newname);
          }
        }
     
     
      
         
         ActionConstraints.gridx = 1;
         ActionConstraints.gridy = actionindex;
         ActionConstraints.gridwidth = 1;
         ActionConstraints.gridheight = 1;
         
         ActionLayout.setConstraints(AV.JPanelAction, ActionConstraints);
         
         ActionPanel.add(AV.JPanelAction);
    
         actionindex++;

        }
      if (hasStoredVar)
      {
        ShowStoredVarControls(true);
        
      }
      else
      {
          ShowStoredVarControls(false);
      }
      if (actionindex < 9)
      {
          
     newbugview.ActionScrollPane.setSize(new Dimension(1024, 36*actionindex+40));
   
          }
      
      newbugview.ActionScrollPane.setVisible(true);

       newbugview.ActionScrollPane.setViewportView(ActionPanel);

     }
 public void RunActions()
 {
     if (this.getRunActionsButtonName()=="Run All Procedures")
     {
          int sessions = getSessions();
          String tbrowser = this.getTargetBrowser();
      if (tbrowser=="Firefox/IE/Chrome")
      {
 for (int x=0; x<sessions; x++)
 {
    
      this.setTargetBrowser("Firefox");
       RunAllTests REFSYNCH = new RunAllTests(this);
    REFSYNCH.execute();   
    this.setTargetBrowser("Chrome");
       RunAllTests REFSYNCH2 = new RunAllTests(this);
    REFSYNCH2.execute();  
    this.setTargetBrowser("Internet Explorer-32");
      RunAllTests REFSYNCH3 = new RunAllTests(this);
    REFSYNCH3.execute();  
    this.setTargetBrowser("Firefox/IE/Chrome");
 }
      }
      else
      {
     for (int x=0; x<sessions; x++)
 {
    RunAllTests REFSYNCH = new RunAllTests(this);
    REFSYNCH.execute();      
 }     
      }
     }
     else
     {
         this.cancelled = true;
     }
 
 }

 public void RunSingleTest(Procedure bugtorun, ProcedureView thisbugview)
 {
      RunASingleTest REFSYNCH = new RunASingleTest(this, bugtorun, thisbugview, this.TargetBrowser, this.OSType);
    REFSYNCH.execute();
 }

 

        public void AddNewBug()
        {
         Procedure newbug = new Procedure();
         ProcedureView newbugview = new ProcedureView();
         newbug.setType("Procedure");
         newbugview.setType("Procedure");
         BugArray.add(newbug);
         BugViewArray.add(newbugview);
         newbug.index = BugArray.size();
         newbugview.index = BugViewArray.size();
        AddNewHandlers(this, newbugview, newbug);
        UpdateDisplay();
 JScrollBar vertical = this.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );

        }
           public void AddNewDataLoop(File CSVFile)
        {
        Procedure newdataloop = new Procedure();
      
         newdataloop.setType("Dataloop");
         
        ProcedureView newdataloopview = new ProcedureView();
        newdataloopview.setType("Dataloop");
        Boolean SetVarListPulldown = false;
if (CSVFile.isAbsolute()) {
        if (CSVFile.exists())
         {
         newdataloopview.setJTableSource(CSVFile.getAbsolutePath());
         newdataloop.setDataFile(CSVFile.getAbsolutePath());
         
         }
         else
         {
          newdataloopview.setJTableSource("");
         newdataloop.setDataFile("");  
         newdataloop.DataSet = new MyTable("");
         
         }
}
else
{
 newdataloop.setDataFile(CSVFile.toString());   
  newdataloopview.setJTableSource(CSVFile.toString());
 
         newdataloop.setDataFile(CSVFile.toString());  
        
         newdataloop.DataSet = new MyTable(CSVFile.toString());
         SetVarListPulldown = true;
       newdataloopview.SetJComboBoxStoredArraylists(CSVFile.toString());  
}

         BugArray.add(newdataloop);
         BugViewArray.add(newdataloopview);
         newdataloop.index = BugArray.size();
         newdataloopview.index = BugViewArray.size();
        AddNewHandlers(this, newdataloopview, newdataloop);
        AddLoopHandlers(this, newdataloopview, newdataloop);
       UpdateDisplay();
        if (SetVarListPulldown)
        {
            newdataloopview.SetJComboBoxStoredArraylists(CSVFile.toString());
        }
        else
        {
            newdataloopview.SetJComboBoxStoredArraylists("Select a stored URL List");
        }
        
 JScrollBar vertical = this.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
   jButtonFlattenFile.setEnabled(true);
   
        }
  
      public void AddLoopHandlers (SeleniumTestTool Window, ProcedureView newbugview, Procedure newbug) 
      {
           newbugview.addJComboBoxStoredArrayListsItemListener((ItemEvent e) -> {
        if ((e.getStateChange() == ItemEvent.SELECTED)) {
         if (newbugview.JComboBoxStoredArrayLists.getSelectedIndex()>0)
               {
                   
          String selectedarraylist = newbugview.JComboBoxStoredArrayLists.getSelectedItem().toString(); 
      newbugview.setJTableSource(selectedarraylist);
      newbug.setDataFile(selectedarraylist);
   //   UpdateDisplay();

               }
            
        }
         
            
         
        });
       
            newbugview.addJButtonBrowseForDataFileActionListener((ActionEvent evt) -> {
             File chosenCSVFile = ChangeCSVFile();
   if (chosenCSVFile!=null)
   {
   newbugview.SetJComboBoxStoredArraylists("Select a stored URL List");
   newbugview.setJTableSource(chosenCSVFile.getAbsolutePath());
   newbug.setDataFile(chosenCSVFile.getAbsolutePath());
   UpdateDisplay();
   }
          });
      
      }
      public File ChangeCSVFile()
      {
   
  final JFileChooser CSVFileChooser = new JFileChooser();
BrowserMatorConfig theseProps = new BrowserMatorConfig();

      String lastused_datafile_dir = theseProps.getKeyValue("lastused_datafile_dir");
      if (lastused_datafile_dir!=null)
      {
      CSVFileChooser.setCurrentDirectory(new File(lastused_datafile_dir));
      }
   CSVFileChooser.addChoosableFileFilter(new ExtensionFileFilter(
                    new String[] { ".CSV", ".XLSX", ".XLSXM", ".XLS", ".XLSM" },
                    "Data File (*.CSV|XLSX|XLSXM|XLS|XLSM)"));

    CSVFileChooser.addChoosableFileFilter(new ExtensionFileFilter(
                    new String[] { ".CSV" },
                    "Comma Delimited File (*.CSV)"));
    CSVFileChooser.addChoosableFileFilter(new ExtensionFileFilter(
                    new String[] { ".XLSX", ".XLSXM", ".XLS", ".XLSM" },
                    "Excel File (*.XLSX|XLSXM|XLS|XLSM)"));


    // Turn off 'All Files' capability of file chooser,
    // so only our custom filter is used.
    CSVFileChooser.setAcceptAllFileFilterUsed(false);
CSVFileChooser.setPreferredSize(new Dimension(800,600));

int returnVal = CSVFileChooser.showOpenDialog(this);
      File chosenDir = CSVFileChooser.getCurrentDirectory();
 theseProps.setKeyValue ("lastused_datafile_dir", chosenDir.getAbsolutePath());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = CSVFileChooser.getSelectedFile();   


 return file;
            }
            else
            {
           return null;
            }

      }
  
      public void AddNewHandlers (SeleniumTestTool Window, ProcedureView newbugview, Procedure newbug)
      {
         
         newbugview.addJButtonMoveProcedureUpActionListener((ActionEvent evt) -> {
               MoveProcedure(newbugview.index, -1);
           });
         newbugview.addJButtonMoveProcedureDownActionListener((ActionEvent evt) -> {
               MoveProcedure(newbugview.index, 1);
           });  
           newbugview.addJButtonRunTestActionListener((ActionEvent evt) -> {
               
               RunSingleTest(newbug, newbugview);
           });
           
           newbugview.addJTextFieldBugTitleDocListener(

           new DocumentListener()
           {
       @Override
       public void changedUpdate(DocumentEvent documentEvent) {
       newbug.setProcedureTitle(newbugview.JTextFieldBugTitle.getText());
 //      Window.changes = true;
      }
       @Override
      public void insertUpdate(DocumentEvent documentEvent) {
      newbug.setProcedureTitle(newbugview.JTextFieldBugTitle.getText());
//    Window.changes = true;
      }
      @Override
      public void removeUpdate(DocumentEvent documentEvent) {
      newbug.setProcedureTitle(newbugview.JTextFieldBugTitle.getText());
//      Window.changes = true;
      }
      }
           );
           
 newbugview.addJButtonDeleteBugActionListener((ActionEvent evt) -> {
               DeleteBug(newbugview.index);
           
               UpdateDisplay();

           });

 
 
           newbugview.addJButtonGoActionActionListener((ActionEvent evt) -> {
              GoAction thisActionToAdd = new GoAction("");
               GoActionView thisActionViewToAdd = new GoActionView();
               thisActionViewToAdd.AddListeners(thisActionToAdd, Window, newbug, newbugview);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, Window, newbug, newbugview);
               AddActionToArray(thisActionToAdd, thisActionViewToAdd, newbug, newbugview);         
            UpdateDisplay();
            ScrollActionPaneDown(newbugview);
            this.changes=true;  
           });
            newbugview.addJButtonClickAtXPATHActionListener((ActionEvent evt) -> {
              ClickXPATHAction thisActionToAdd = new ClickXPATHAction("", false, false);
              ClickXPATHActionView thisActionViewToAdd = new ClickXPATHActionView();
               thisActionViewToAdd.AddListeners(thisActionToAdd, Window, newbug, newbugview);
             
                     thisActionViewToAdd.AddLoopListeners(thisActionToAdd, Window, newbug, newbugview);
               AddActionToArray(thisActionToAdd, thisActionViewToAdd, newbug, newbugview);         
            UpdateDisplay();
            ScrollActionPaneDown(newbugview);
            this.changes=true;  
           });
           newbugview.addJButtonTypeAtXPATHActionListener((ActionEvent evt) -> {
              TypeAtXPATHAction thisActionToAdd = new TypeAtXPATHAction("","", false);
              TypeAtXPATHActionView thisActionViewToAdd = new TypeAtXPATHActionView();
               thisActionViewToAdd.AddListeners(thisActionToAdd, Window, newbug, newbugview);
                  thisActionViewToAdd.AddLoopListeners(thisActionToAdd, Window, newbug, newbugview);
               AddActionToArray(thisActionToAdd, thisActionViewToAdd, newbug, newbugview);         
            UpdateDisplay();
            ScrollActionPaneDown(newbugview);
            this.changes=true;  
           });
           newbugview.addJButtonFindXPATHPassFailListener((ActionEvent evt) -> {
             FindXPATHPassFailAction thisActionToAdd = new FindXPATHPassFailAction("", false);
             FindXPATHPassFailActionView thisActionViewToAdd = new FindXPATHPassFailActionView();
              thisActionViewToAdd.AddListeners(thisActionToAdd, Window, newbug, newbugview);
              thisActionViewToAdd.AddLoopListeners(thisActionToAdd, Window, newbug, newbugview);
               AddActionToArray(thisActionToAdd, thisActionViewToAdd, newbug, newbugview);         
            UpdateDisplay();
            ScrollActionPaneDown(newbugview);
            this.changes=true;  
           });
           newbugview.addJButtonDoNotFindXPATHPassFailListener((ActionEvent evt) -> {
             FindXPATHPassFailAction thisActionToAdd = new FindXPATHPassFailAction("", true);
             NOTFindXPATHPassFailActionView thisActionViewToAdd = new NOTFindXPATHPassFailActionView();
              thisActionViewToAdd.AddListeners(thisActionToAdd, Window, newbug, newbugview);
              thisActionViewToAdd.AddLoopListeners(thisActionToAdd, Window, newbug, newbugview);
               AddActionToArray(thisActionToAdd, thisActionViewToAdd, newbug, newbugview);         
            UpdateDisplay();
            ScrollActionPaneDown(newbugview);
            this.changes=true;  
           });
               newbugview.addJButtonYesNoPromptPassFailListener((ActionEvent evt) -> {
             YesNoPromptPassFailAction thisActionToAdd = new YesNoPromptPassFailAction("");
             YesNoPromptPassFailActionView thisActionViewToAdd = new YesNoPromptPassFailActionView();
              thisActionViewToAdd.AddListeners(thisActionToAdd, Window, newbug, newbugview);
              thisActionViewToAdd.AddLoopListeners(thisActionToAdd, Window, newbug, newbugview);
               AddActionToArray(thisActionToAdd, thisActionViewToAdd, newbug, newbugview);         
            UpdateDisplay();
            ScrollActionPaneDown(newbugview);
            this.changes=true;  
           });
           
    newbugview.addDoActionItemListener((ItemEvent e) -> {
        if ((e.getStateChange() == ItemEvent.SELECTED)) {
            Object ActionType = e.getItem();
            String ActionToAdd = ActionType.toString();
            ActionsMaster newActionsMaster = new ActionsMaster();
            HashMap<String, Action> ActionHashMap = newActionsMaster.ActionHashMap;
            HashMap<String, ActionView> ActionViewHashMap = newActionsMaster.ActionViewHashMap;
            
            newbugview.JComboBoxDoActions.setSelectedIndex(0);
           if (ActionHashMap.containsKey(ActionToAdd))
           {
               Action thisActionToAdd = ActionHashMap.get(ActionToAdd);
               ActionView thisActionViewToAdd = ActionViewHashMap.get(ActionToAdd);
               thisActionViewToAdd.AddListeners(thisActionToAdd, Window, newbug, newbugview);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, Window, newbug, newbugview);
               AddActionToArray(thisActionToAdd, thisActionViewToAdd, newbug, newbugview);
               
           }      
         
            
            UpdateDisplay();
            ScrollActionPaneDown(newbugview);
            this.changes=true;
        }
           });
     newbugview.addPassFailActionsItemListener((ItemEvent e) -> {
         if ((e.getStateChange() == ItemEvent.SELECTED)) {
             Object PassFailActionType = e.getItem();
             String PassFailActionToAdd = PassFailActionType.toString();
             ActionsMaster newActionsMaster = new ActionsMaster();
             HashMap<String, Action> PassFailActionHashMap = newActionsMaster.PassFailActionHashMap;
             HashMap<String, ActionView> PassFailActionViewHashMap = newActionsMaster.PassFailActionViewHashMap;
             
             newbugview.JComboBoxPassFailActions.setSelectedIndex(0);
             if (PassFailActionHashMap.containsKey(PassFailActionToAdd))
             {
                 Action thisActionToAdd = PassFailActionHashMap.get(PassFailActionToAdd);
               ActionView thisActionViewToAdd = PassFailActionViewHashMap.get(PassFailActionToAdd);
               thisActionViewToAdd.AddListeners(thisActionToAdd, Window, newbug, newbugview);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, Window, newbug, newbugview);
               AddActionToArray(thisActionToAdd, thisActionViewToAdd, newbug, newbugview); 
             }
          
             
             UpdateDisplay();
             ScrollActionPaneDown(newbugview);
             this.changes=true;
         }
           });
     
      }
              
      public void AddActionToArray (Action action, ActionView actionview, Procedure newbug, ProcedureView newbugview)
{
            newbugview.ActionsViewList.add(actionview);
            newbug.ActionsList.add(action);
            actionview.index = newbugview.ActionsViewList.size()-1;
            action.index = newbug.ActionsList.size()-1;
  //          actionview.AddDraggers(this, newbug, newbugview, actionview);
          
}

   public File BrowseForJSFileAction ()
   {
           final JFileChooser CSVFileChooser = new JFileChooser();
   BrowserMatorConfig theseProps = new BrowserMatorConfig();

      String lastJSOpenDir = theseProps.getKeyValue("lastused_js_open_dir");
       if (lastJSOpenDir!=null)
        {
        CSVFileChooser.setCurrentDirectory(new File(lastJSOpenDir));
        } 
 FileNameExtensionFilter filefilter = new FileNameExtensionFilter("Javascript file (*.js)","js");

    CSVFileChooser.setFileFilter(filefilter);
CSVFileChooser.setPreferredSize(new Dimension(800,600));
int returnVal = CSVFileChooser.showOpenDialog(this);
        File chosenDir = CSVFileChooser.getCurrentDirectory();
 theseProps.setKeyValue ("lastused_js_open_dir", chosenDir.getAbsolutePath());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = CSVFileChooser.getSelectedFile();   

    if (file.getAbsoluteFile().getName().contains(".js"))
{
 
}
else
{
   String path = file.getAbsolutePath();
    
File newfile = new File(path + ".js");
 file = newfile;
 
}  
    return file;
            }
            else
            {
            return null;
            }   
   }
   public void MoveProcedure (int toMoveIndex, int Direction)
   {
     int SwapIndex = toMoveIndex + Direction;
    if (Direction == 1)
       {
      if (SwapIndex<BugArray.size())
      {
    Collections.swap(BugArray, toMoveIndex, SwapIndex);
  Collections.swap(BugViewArray, toMoveIndex, SwapIndex);
  BugArray.get(toMoveIndex).index = toMoveIndex;
  BugViewArray.get(SwapIndex).index = SwapIndex;
    
      }
       }  
    if (Direction == -1)
    {
        if (SwapIndex >= 0)
        {
    Collections.swap(BugArray, toMoveIndex, SwapIndex);
  Collections.swap(BugViewArray, toMoveIndex, SwapIndex);
  BugArray.get(toMoveIndex).index = toMoveIndex;
  BugViewArray.get(SwapIndex).index = SwapIndex;
 
        }
    }
      UpdateDisplay();
   }
   public void MoveAction (Procedure thisBug, ProcedureView thisBugView, int toMoveIndex, int Direction)
   {

    int SwapIndex = toMoveIndex + Direction;
    if (Direction == 1)
       {
                if (toMoveIndex<thisBug.ActionsList.size()-1)
     {


  Collections.swap(thisBug.ActionsList, toMoveIndex, SwapIndex);
  Collections.swap(thisBugView.ActionsViewList, toMoveIndex, SwapIndex);
  thisBugView.ActionsViewList.get(toMoveIndex).SetIndexes(thisBugView.index, toMoveIndex);
  thisBugView.ActionsViewList.get(SwapIndex).SetIndexes(thisBugView.index, SwapIndex);
  thisBug.ActionsList.get(toMoveIndex).setActionIndex(toMoveIndex);
  thisBug.ActionsList.get(SwapIndex).setActionIndex(SwapIndex);
    


      
  
    }

       }
       if (Direction == -1)
       {
     if (toMoveIndex!=0)
    {
    Collections.swap(thisBug.ActionsList, toMoveIndex, SwapIndex);
  Collections.swap(thisBugView.ActionsViewList, toMoveIndex, SwapIndex);
thisBugView.ActionsViewList.get(toMoveIndex).SetIndexes(thisBugView.index, toMoveIndex);
  thisBugView.ActionsViewList.get(SwapIndex).SetIndexes(thisBugView.index, SwapIndex);
  thisBug.ActionsList.get(toMoveIndex).setActionIndex(toMoveIndex);
  thisBug.ActionsList.get(SwapIndex).setActionIndex(SwapIndex);


       }
   }
  
       this.changes=true;
   }
  
   public void DeleteAction (Procedure thisBug, ProcedureView thisBugView, int atIndex)
   {
     String stringactionindex = Integer.toString(atIndex+1);
        String stringbugindex = Integer.toString(thisBugView.index+1);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
        if (VarHashMap.containsKey(bugdashactionindex))
        {
            VarHashMap.remove(bugdashactionindex);
            UpdateStoredVarPulldown();
        }
        if (VarLists.containsKey(bugdashactionindex))
        {
            VarLists.remove(bugdashactionindex);
            UpdateStoredListsPulldown("Select a stored URL List");
        }
    thisBug.ActionsList.remove(atIndex);
    thisBugView.ActionsViewList.remove(atIndex);

  for(int BugIndex=0; BugIndex<thisBug.ActionsList.size(); BugIndex++)
     {
     if (BugIndex>=atIndex)
     {
     thisBug.ActionsList.get(BugIndex).index--;
     thisBugView.ActionsViewList.get(BugIndex).index--;
     }
     }
 
 
  this.changes=true;
   }
   public void DeleteBug (int BugIndex)
   {
   for (Action A: this.BugArray.get(BugIndex).ActionsList)
   {
       if ("Store Link as Variable by XPATH".equals(A.Type))
       {
           VarHashMap.remove(A.Variable2);
       }
       if ("Store Links as URL List by XPATH".equals(A.Type))
       {
           VarLists.remove(A.Variable2);
       }
   }
   UpdateStoredVarPulldown();
   this.BugArray.remove(BugIndex);
   this.BugViewArray.remove(BugIndex);
this.changes=true;
   }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonDoStuff = new javax.swing.JButton();
        jButtonNewBug = new javax.swing.JButton();
        MainScrollPane = new javax.swing.JScrollPane();
        jLabelTHISSITEURL = new javax.swing.JLabel();
        jSpinnerWaitTime = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jCheckBoxEmailReport = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldSMTPHostName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldEmailLoginName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldEmailTo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldEmailFrom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldSubject = new javax.swing.JTextField();
        jCheckBoxShowReport = new javax.swing.JCheckBox();
        jCheckBoxExitAfter = new javax.swing.JCheckBox();
        jTextFieldEmailPassword = new javax.swing.JPasswordField();
        jCheckBoxEmailReportFail = new javax.swing.JCheckBox();
        jCheckBoxPromptToClose = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxTargetBrowser = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jCheckBoxOSTypeWindows32 = new javax.swing.JCheckBox();
        jCheckBoxOSTypeMac = new javax.swing.JCheckBox();
        jCheckBoxOSTypeLinux32 = new javax.swing.JCheckBox();
        jCheckBoxOSTypeLinux64 = new javax.swing.JCheckBox();
        jButtonClearEmailSettings = new javax.swing.JButton();
        jButtonNewDataLoop = new javax.swing.JButton();
        jButtonBrowseForFireFoxExe = new javax.swing.JButton();
        jButtonFlattenFile = new javax.swing.JButton();
        jButtonLoadEmailSettings = new javax.swing.JButton();
        jButtonGutsView = new javax.swing.JButton();
        jComboBoxStoredVariables = new javax.swing.JComboBox<>();
        jButtonPlaceStoredVariable = new javax.swing.JButton();
        jLabelStoredVariables = new javax.swing.JLabel();
        jCheckBoxOSTypeWindows64 = new javax.swing.JCheckBox();
        jCheckBoxIncludeScreenshots = new javax.swing.JCheckBox();
        jSpinnerSessions = new javax.swing.JSpinner();
        jLabelSessions = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 834));

        jButtonDoStuff.setText("Run All Procedures");
        jButtonDoStuff.setActionCommand("AddAction");

        jButtonNewBug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/browsermator/com/Resources/newFile.png"))); // NOI18N
        jButtonNewBug.setText("Add Procedure ");
        jButtonNewBug.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewBugActionPerformed(evt);
            }
        });

        MainScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        MainScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        MainScrollPane.setName(""); // NOI18N

        jSpinnerWaitTime.setModel(new javax.swing.SpinnerNumberModel(0, 0, 99, 1));
        jSpinnerWaitTime.setMinimumSize(new java.awt.Dimension(41, 21));
        jSpinnerWaitTime.setPreferredSize(new java.awt.Dimension(41, 21));

        jLabel1.setText("Pause time (seconds)");

        jCheckBoxEmailReport.setText("Email Report");

        jLabel10.setText("Email Configuration:");

        jLabel11.setText("SMTPHostname:");

        jTextFieldSMTPHostName.setText("smtp.gmail.com");

        jLabel4.setText("Email Login Name:");

        jLabel5.setText("Email Login Password:");

        jLabel6.setText("E-mail To:");

        jLabel7.setText("E-mail From:");

        jLabel3.setText("Title/Subject:");

        jCheckBoxShowReport.setText("Show Report");

        jCheckBoxExitAfter.setText("Exit After Running");

        jTextFieldEmailPassword.setText("jPasswordField1");

        jCheckBoxEmailReportFail.setText("Email Fail Report Only");
        jCheckBoxEmailReportFail.setActionCommand("EmailReportIfFail");

        jCheckBoxPromptToClose.setText("Prompt to Close WebDriver/Browser");

        jLabel8.setText("Target Browser:");

        jComboBoxTargetBrowser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Chrome", "Firefox", "Internet Explorer-32", "Internet Explorer-64", "Chrome 49", "Silent Mode (HTMLUnit)", "Firefox/IE/Chrome" }));

        jLabel9.setText("<HTML>*Additional configuration is needed for IE (this program does not adjust the registry or browser security zones settings).<br/>**HTMLUnit's Javascript engine is a bit quirky.</HTML> ");

        jCheckBoxOSTypeWindows32.setText("Windows - 32");
        jCheckBoxOSTypeWindows32.setEnabled(false);

        jCheckBoxOSTypeMac.setText("Mac");
        jCheckBoxOSTypeMac.setEnabled(false);

        jCheckBoxOSTypeLinux32.setText("Linux - 32");
        jCheckBoxOSTypeLinux32.setEnabled(false);

        jCheckBoxOSTypeLinux64.setText("Linux - 64");
        jCheckBoxOSTypeLinux64.setEnabled(false);

        jButtonClearEmailSettings.setText("Clear Email Settings");

        jButtonNewDataLoop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/browsermator/com/Resources/newFile.png"))); // NOI18N
        jButtonNewDataLoop.setText("Add Data Loop Procedure ");
        jButtonNewDataLoop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewDataLoopActionPerformed(evt);
            }
        });

        jButtonBrowseForFireFoxExe.setText("...");
        jButtonBrowseForFireFoxExe.setEnabled(false);

        jButtonFlattenFile.setText("Flatten to New File");
        jButtonFlattenFile.setEnabled(false);

        jButtonLoadEmailSettings.setText("Load Default Settings");

        jButtonGutsView.setText("View Guts");

        jComboBoxStoredVariables.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select a stored variable" }));

        jButtonPlaceStoredVariable.setText("Place Variable");

        jLabelStoredVariables.setText("Stored Variables");

        jCheckBoxOSTypeWindows64.setText("Windows - 64");
        jCheckBoxOSTypeWindows64.setEnabled(false);

        jCheckBoxIncludeScreenshots.setText("Include Screenshots");
        jCheckBoxIncludeScreenshots.setEnabled(false);

        jSpinnerSessions.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        jLabelSessions.setText("Number of Sessions:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabelTHISSITEURL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MainScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1244, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jSpinnerWaitTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(jButtonDoStuff, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jCheckBoxShowReport)
                                                    .addComponent(jCheckBoxEmailReport))
                                                .addGap(59, 59, 59))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jCheckBoxIncludeScreenshots)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jCheckBoxPromptToClose)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButtonFlattenFile))
                                            .addComponent(jCheckBoxExitAfter)
                                            .addComponent(jCheckBoxEmailReportFail, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBoxTargetBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButtonBrowseForFireFoxExe)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabelSessions)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jSpinnerSessions, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCheckBoxOSTypeWindows32)
                                        .addGap(13, 13, 13)
                                        .addComponent(jCheckBoxOSTypeWindows64)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckBoxOSTypeLinux32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckBoxOSTypeLinux64)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckBoxOSTypeMac))
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(145, 145, 145)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(58, 58, 58)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jTextFieldSMTPHostName)
                                            .addComponent(jTextFieldEmailLoginName)
                                            .addComponent(jTextFieldEmailTo)
                                            .addComponent(jTextFieldEmailFrom)
                                            .addComponent(jTextFieldSubject, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                                            .addComponent(jTextFieldEmailPassword)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jButtonClearEmailSettings)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jButtonLoadEmailSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                .addGap(0, 34, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonNewBug, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonNewDataLoop)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelStoredVariables)
                                .addGap(10, 10, 10)
                                .addComponent(jComboBoxStoredVariables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonPlaceStoredVariable)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonGutsView)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonNewDataLoop)
                        .addComponent(jButtonGutsView)
                        .addComponent(jComboBoxStoredVariables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButtonPlaceStoredVariable)
                        .addComponent(jLabelStoredVariables))
                    .addComponent(jButtonNewBug, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabelTHISSITEURL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MainScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCheckBoxShowReport)
                                    .addComponent(jCheckBoxPromptToClose)
                                    .addComponent(jButtonFlattenFile))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCheckBoxIncludeScreenshots)
                                    .addComponent(jCheckBoxExitAfter)))
                            .addComponent(jButtonDoStuff, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jSpinnerWaitTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)
                                .addComponent(jCheckBoxEmailReport))
                            .addComponent(jCheckBoxEmailReportFail))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jComboBoxTargetBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonBrowseForFireFoxExe))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelSessions)
                                .addComponent(jSpinnerSessions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxOSTypeWindows32)
                            .addComponent(jCheckBoxOSTypeMac)
                            .addComponent(jCheckBoxOSTypeLinux32)
                            .addComponent(jCheckBoxOSTypeLinux64)
                            .addComponent(jCheckBoxOSTypeWindows64))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTextFieldSMTPHostName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextFieldEmailLoginName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldEmailPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldEmailTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldEmailFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonClearEmailSettings)
                            .addComponent(jButtonLoadEmailSettings))))
                .addContainerGap())
        );

        jLabel9.getAccessibleContext().setAccessibleName("<HTML>*Additional configuration is needed for IE (this program does not adjust the registry or browser security zones settings).<br/>**HTMLUnit's Javascript engine is a bit quirky.<br/>*** If a report fails to show the number of screenshots may have overrun Java's heap space.</HTML> ");
        jButtonBrowseForFireFoxExe.getAccessibleContext().setAccessibleName("jButtonBrowseForFireFoxExe");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNewBugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewBugActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonNewBugActionPerformed

    private void jButtonNewDataLoopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewDataLoopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonNewDataLoopActionPerformed
  private void jCheckBoxPromptToCloseActionPerformed(ActionEvent evt)
  {
  this.PromptToClose = jCheckBoxPromptToClose.isSelected();
  // this.changes=true;
  }
  private void jCheckBoxShowReportActionPerformed(ActionEvent evt)
  {
  this.ShowReport = jCheckBoxShowReport.isSelected();
  if (this.ShowReport==false)
  {
      jCheckBoxIncludeScreenshots.setEnabled(false);
    
  }
  else
  {
      jCheckBoxEmailReportFail.setSelected(false);
      this.EmailReportFail = false;
      jCheckBoxEmailReport.setSelected(false);
        this.EmailReport = false;
    jCheckBoxIncludeScreenshots.setEnabled(true);  
      jCheckBoxExitAfter.setSelected(false);
        this.ExitAfter = false;
  }
  }
  
   private void jCheckBoxExitAfterActionPerformed(ActionEvent evt)
  {
  this.ExitAfter = jCheckBoxExitAfter.isSelected();
  if (this.ExitAfter)
  {
      jCheckBoxShowReport.setSelected(false);
      this.ShowReport = false;
      
      
  }
 
  }
  private void jCheckBoxEmailReportActionPerformed(ActionEvent evt)
  {
   this.EmailReport = jCheckBoxEmailReport.isSelected();
  if (this.EmailReport==true)
  {
       jCheckBoxIncludeScreenshots.setEnabled(false);
      this.IncludeScreenshots=false;   
      jCheckBoxShowReport.setSelected(false);
      this.ShowReport = false;
      jCheckBoxEmailReportFail.setSelected(false);

      this.EmailReportFail = false;
  }
 
  // this.changes=true;
  }
   private void jCheckBoxIncludeScreenshotsActionPerformed(ActionEvent evt)
  {
     this.IncludeScreenshots = jCheckBoxIncludeScreenshots.isSelected();
  }
  private void jCheckBoxEmailReportFailActionPerformed(ActionEvent evt)
  {
     this.EmailReportFail = jCheckBoxEmailReportFail.isSelected();
  if (this.EmailReportFail==true)
  {
       jCheckBoxIncludeScreenshots.setEnabled(false);
      this.IncludeScreenshots=false;   
  jCheckBoxShowReport.setSelected(false);
            this.ShowReport = false;
     jCheckBoxEmailReport.setSelected(false);
     this.EmailReport = false;
  }


  }
 private void jCheckBoxOSTypeWindows32ActionPerformed(ActionEvent evt)
 {
     
     if (jCheckBoxOSTypeWindows32.isSelected())
     {
         this.OSType = "Windows32";
         jCheckBoxOSTypeWindows64.setSelected(false);
     jCheckBoxOSTypeMac.setSelected(false);
     jCheckBoxOSTypeLinux32.setSelected(false);
     jCheckBoxOSTypeLinux64.setSelected(false);
     }
   
 }
  private void jCheckBoxOSTypeWindows64ActionPerformed(ActionEvent evt)
 {
     
     if (jCheckBoxOSTypeWindows64.isSelected())
     {
         this.OSType = "Windows64";
         jCheckBoxOSTypeWindows32.setSelected(false);
     jCheckBoxOSTypeMac.setSelected(false);
     jCheckBoxOSTypeLinux32.setSelected(false);
     jCheckBoxOSTypeLinux64.setSelected(false);
     }
   
 }


   private void jCheckBoxOSTypeMacActionPerformed(ActionEvent evt)
 {
     
     if (jCheckBoxOSTypeMac.isSelected())
     {
         this.OSType = "Mac";
     jCheckBoxOSTypeWindows32.setSelected(false);
     jCheckBoxOSTypeLinux32.setSelected(false);
     jCheckBoxOSTypeLinux64.setSelected(false);
     jCheckBoxOSTypeWindows64.setSelected(false);
     }
 }
    private void jCheckBoxOSTypeLinux32ActionPerformed(ActionEvent evt)
 {
     
     if (jCheckBoxOSTypeLinux32.isSelected())
     {
         this.OSType = "Linux32";
     jCheckBoxOSTypeMac.setSelected(false);
     jCheckBoxOSTypeWindows32.setSelected(false);
     jCheckBoxOSTypeLinux64.setSelected(false);
     jCheckBoxOSTypeWindows64.setSelected(false);
     }
 }
     private void jCheckBoxOSTypeLinux64ActionPerformed(ActionEvent evt)
 {
     
     if (jCheckBoxOSTypeLinux64.isSelected())
     {
         this.OSType = "Linux64";
     jCheckBoxOSTypeMac.setSelected(false);
     jCheckBoxOSTypeLinux32.setSelected(false);
     jCheckBoxOSTypeWindows32.setSelected(false);
     jCheckBoxOSTypeWindows64.setSelected(false);
     }

 }
public void setOSTypeActive (Boolean Active)
{
    if (Active)
    {
    jCheckBoxOSTypeWindows32.setEnabled(true);
    jCheckBoxOSTypeWindows64.setEnabled(true);
    jCheckBoxOSTypeMac.setEnabled(true);
    jCheckBoxOSTypeLinux32.setEnabled(true);
    jCheckBoxOSTypeLinux64.setEnabled(true);
    }
    else
    {
    jCheckBoxOSTypeWindows32.setEnabled(false);
    jCheckBoxOSTypeWindows64.setEnabled(false);
    jCheckBoxOSTypeMac.setEnabled(false);
    jCheckBoxOSTypeLinux32.setEnabled(false);
    jCheckBoxOSTypeLinux64.setEnabled(false);    
    }
    }

public void addTargetBrowserItemListener (ItemListener listener)
{
  jComboBoxTargetBrowser.addItemListener(listener);
}
public void addjButtonBrowseForFireFoxExeActionListener(ActionListener listener)
{
    jButtonBrowseForFireFoxExe.addActionListener(listener);
}

public void addjButtonPlaceStoredVariableActionListener(ActionListener listener)
{
    jButtonPlaceStoredVariable.addActionListener(listener);
}

public void addjButtonNewBugActionListener(ActionListener listener) {
     jButtonNewBug.addActionListener(listener);
  }
public void addjButtonNewDataLoopActionListener(ActionListener listener) {
    jButtonNewDataLoop.addActionListener(listener);  
  }
   public void addjButtonDoStuffActionListener(ActionListener listener) {
       jButtonDoStuff.addActionListener(listener);
   }
      public void addjButtonFlattenFileActionListener(ActionListener listener) {
       jButtonFlattenFile.addActionListener(listener);
   }
  public void addjButtonClearEmailSettingsListener(ActionListener listener) {
       jButtonClearEmailSettings.addActionListener(listener);
   }
   public void addjButtonLoadEmailSettingsListener(ActionListener listener) {
       jButtonLoadEmailSettings.addActionListener(listener);
   }
   public void addjButtonGutsViewActionListener(ActionListener listener)
   {
       jButtonGutsView.addActionListener(listener);
   }
 public void ClearEmailSettings ()
 {
     setSMTPHostname("");
     setEmailLoginName("");
     setEmailPassword("");
     setEmailTo("");
     setEmailFrom("");
     setSubject("");
     
 }
   public void ShowGuts()
   {
     
        ViewGuts GUTSREF = new ViewGuts(this);
  GUTSREF.execute();
     
       
       
   }
    public void setGutsViewButtonName(String button_name)
    {
        jButtonGutsView.setText(button_name);
    }
    public String getSMTPHostname ()
    {
        String SMTPHostname = jTextFieldSMTPHostName.getText();
        return SMTPHostname;
       
    }
    public String getEmailLoginName ()
    {
        String EmailLoginName = jTextFieldEmailLoginName.getText();
        return EmailLoginName;
    }
    public String getEmailPassword ()
    {
        String Password="";
     char[] temp;
     temp = jTextFieldEmailPassword.getPassword();
       for (int x = 0; x<temp.length; x++)
     {
         Password = Password + temp[x];
     } 
       
        return Password;
    }
    public String getEmailTo ()
    {
        String EmailTo = jTextFieldEmailTo.getText();
        return EmailTo;
    }
    public String getEmailFrom()
    {
        String EmailFrom = jTextFieldEmailFrom.getText();
        return EmailFrom;        
    }
    public String getSubject()
    {
        String Subject = jTextFieldSubject.getText();
        return Subject;
    }
   public void setSMTPHostname (String SMTPHostName)
    {
        jTextFieldSMTPHostName.setText(SMTPHostName);
    }
    public void setEmailLoginName (String EmailLoginName)
    {   
      jTextFieldEmailLoginName.setText(EmailLoginName);
    }
    public void setEmailPassword (String EmailPassword)
    {
    
      jTextFieldEmailPassword.setText(EmailPassword); 
    }
    public void setEmailTo (String EmailPassword)
    {
     jTextFieldEmailTo.setText(EmailPassword);
    
    }
    public void setEmailFrom(String EmailFrom)
    {
      jTextFieldEmailFrom.setText(EmailFrom);      
    }
   
    public void setSubject(String Subject)
    {
      jTextFieldSubject.setText(Subject);
    } 
    public void setExitAfter (Boolean exitafter)
    {
        this.ExitAfter = exitafter;
        jCheckBoxExitAfter.setSelected(exitafter);
        if (this.ExitAfter)
        {
         jCheckBoxShowReport.setSelected(false);
      this.ShowReport = false;
        }
    }
    public void setShowReport (Boolean showreport)
    {
        this.ShowReport = showreport;
        jCheckBoxShowReport.setSelected(showreport);
        if (this.ShowReport==false)
        {
    
      jCheckBoxIncludeScreenshots.setEnabled(false);
      this.IncludeScreenshots=false;   
        }
        else
        {
        jCheckBoxEmailReportFail.setSelected(false);
        this.EmailReportFail = false;
        
        jCheckBoxEmailReport.setSelected(false);
        this.EmailReport = false;
      
        jCheckBoxIncludeScreenshots.setEnabled(true);
        jCheckBoxExitAfter.setSelected(false);
        this.ExitAfter = false;
        }
    }
    public void setIncludeScreenshots(Boolean includescreenshots)
    {
        this.IncludeScreenshots = includescreenshots;
        jCheckBoxIncludeScreenshots.setSelected(includescreenshots);
    }
 
    public void setEmailReport (Boolean emailreport)
    {
        jCheckBoxEmailReport.setSelected(emailreport);
        this.EmailReport = emailreport;
        if (emailreport==true)
        {
             jCheckBoxIncludeScreenshots.setEnabled(false);
      this.IncludeScreenshots=false;   
            jCheckBoxShowReport.setSelected(false);
            this.ShowReport = false;
                    jCheckBoxIncludeScreenshots.setSelected(false);
      jCheckBoxIncludeScreenshots.setEnabled(false);
      this.IncludeScreenshots=false;   
            jCheckBoxEmailReportFail.setSelected(false);
        }
     
    }
    
        public void setEmailReportFail (Boolean emailreportfail)
    {
        this.EmailReportFail = emailreportfail;
        jCheckBoxEmailReportFail.setSelected(emailreportfail);
         if (emailreportfail==true)
        {
             jCheckBoxIncludeScreenshots.setEnabled(false);
      this.IncludeScreenshots=false;   
              jCheckBoxShowReport.setSelected(false);
            this.ShowReport = false;
            jCheckBoxEmailReport.setSelected(false);
            this.EmailReport = false;
        }
         else
         {
      
         this.EmailReportFail = false;
         
         }
    }
        public void setTargetBrowser (String targetbrowser)
        {   
            //legacy stuff
            if ("Firefox-Marionette".equals(targetbrowser))
            {
                targetbrowser = "Firefox";
            }
            
            jComboBoxTargetBrowser.setSelectedItem(targetbrowser);   
            this.TargetBrowser = jComboBoxTargetBrowser.getSelectedItem().toString();
            
            switch (targetbrowser)
            {
                case "Firefox":
                    jButtonBrowseForFireFoxExe.setEnabled(true);
                     setOSTypeActive(true);
                    break;
         
                case "Internet Explorer-32":
                    jButtonBrowseForFireFoxExe.setEnabled(false);
                    break;
                case "Internet Explorer-64":
                    jButtonBrowseForFireFoxExe.setEnabled(false);
                    break;  
                case "Chrome":
                    jButtonBrowseForFireFoxExe.setEnabled(false);
                     setOSTypeActive(true);
                    break;
                
                case "Chrome 49":
                     jButtonBrowseForFireFoxExe.setEnabled(true);
                     setOSTypeActive(true);
                    break;
                    
                case "Silent Mode (HTMLUnit)":
                    jButtonBrowseForFireFoxExe.setEnabled(false);
                    break;
                default:
                      jButtonBrowseForFireFoxExe.setEnabled(false);
                     setOSTypeActive(true);
                    break;
                    
            }
        }
        public String getTargetBrowser ()
        {
            return jComboBoxTargetBrowser.getSelectedItem().toString();
        }
        public String getOSType()
        {
            if (jCheckBoxOSTypeWindows32.isSelected())
            {
                return "Windows32";
            }
             if (jCheckBoxOSTypeWindows64.isSelected())
            {
                return "Windows64";
            }
            if (jCheckBoxOSTypeMac.isSelected())
            {
                return "Mac";
            }
            if (jCheckBoxOSTypeLinux32.isSelected())
            {
                return "Linux-32";   
            }
            if (jCheckBoxOSTypeLinux64.isSelected())
            {
                return "Linux-64";
            }
           return "Windows32";
        }
        public void setOSType(String OSType)
        {
            if ("Windows".equals(OSType))
            {
                OSType = "Windows32";
            }
            this.OSType = OSType;
            switch (OSType)
            {
                case "Windows":
                   jCheckBoxOSTypeWindows32.setSelected(true); 
                    break;
                    
                case "Windows32":
                jCheckBoxOSTypeWindows32.setSelected(true);
                break;
                case "Windows64":
                jCheckBoxOSTypeWindows64.setSelected(true);
                break;
                case "Mac":
                jCheckBoxOSTypeMac.setSelected(true);
                break;
                case "Linux-32":
                jCheckBoxOSTypeLinux32.setSelected(true);
                break;
                case "Linux-64":
                jCheckBoxOSTypeLinux64.setSelected(true);
                break;
                
            }
                
        }
 
 public int FillTables(Procedure thisproc, ProcedureView thisprocview)
  {
      int number_of_rows = 0;
     for (Action ThisAction: thisproc.ActionsList)
     {
      String concat_variable;
 
              DataLoopVarParser var1Parser = new DataLoopVarParser(ThisAction.Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(ThisAction.Variable2);   
    if (var1Parser.hasDataLoopVar)
    {
 concat_variable = ThisAction.Variable1;
            String middle_part = concat_variable.substring(21, concat_variable.length()-20 );
            String[] parts = middle_part.split(",");
            if (parts[2].contains(":"))
            {   
            String[] parts2 = parts[2].split(":");
            String URLListName = parts2[1];
               if (this.VarLists.containsKey(URLListName))
            {
            this.UpdateDataLoopTable(URLListName, this.VarLists.get(URLListName), thisproc, thisprocview);
            number_of_rows = this.VarLists.get(URLListName).size();
            }
            }
        } 
    if (var2Parser.hasDataLoopVar)
    {
 concat_variable = ThisAction.Variable2;
            String middle_part = concat_variable.substring(21, concat_variable.length()-20 );
            String[] parts = middle_part.split(",");
             if (parts[2].contains(":"))
            {  
            String[] parts2 = parts[2].split(":");
            String URLListName = parts2[1];
            if (this.VarLists.containsKey(URLListName))
            {
            this.UpdateDataLoopTable(URLListName, this.VarLists.get(URLListName), thisproc, thisprocview);
            number_of_rows = this.VarLists.get(URLListName).size();
            }
            }
        } 
    
    }
     return number_of_rows;
     }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JScrollPane MainScrollPane;
    private javax.swing.JButton jButtonBrowseForFireFoxExe;
    private javax.swing.JButton jButtonClearEmailSettings;
    private javax.swing.JButton jButtonDoStuff;
    private javax.swing.JButton jButtonFlattenFile;
    private javax.swing.JButton jButtonGutsView;
    private javax.swing.JButton jButtonLoadEmailSettings;
    private javax.swing.JButton jButtonNewBug;
    private javax.swing.JButton jButtonNewDataLoop;
    private javax.swing.JButton jButtonPlaceStoredVariable;
    private javax.swing.JCheckBox jCheckBoxEmailReport;
    private javax.swing.JCheckBox jCheckBoxEmailReportFail;
    private javax.swing.JCheckBox jCheckBoxExitAfter;
    private javax.swing.JCheckBox jCheckBoxIncludeScreenshots;
    private javax.swing.JCheckBox jCheckBoxOSTypeLinux32;
    private javax.swing.JCheckBox jCheckBoxOSTypeLinux64;
    private javax.swing.JCheckBox jCheckBoxOSTypeMac;
    private javax.swing.JCheckBox jCheckBoxOSTypeWindows32;
    private javax.swing.JCheckBox jCheckBoxOSTypeWindows64;
    private javax.swing.JCheckBox jCheckBoxPromptToClose;
    private javax.swing.JCheckBox jCheckBoxShowReport;
    private javax.swing.JComboBox<String> jComboBoxStoredVariables;
    private javax.swing.JComboBox jComboBoxTargetBrowser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelSessions;
    private javax.swing.JLabel jLabelStoredVariables;
    private javax.swing.JLabel jLabelTHISSITEURL;
    private javax.swing.JSpinner jSpinnerSessions;
    private javax.swing.JSpinner jSpinnerWaitTime;
    private javax.swing.JTextField jTextFieldEmailFrom;
    private javax.swing.JTextField jTextFieldEmailLoginName;
    private javax.swing.JPasswordField jTextFieldEmailPassword;
    private javax.swing.JTextField jTextFieldEmailTo;
    private javax.swing.JTextField jTextFieldSMTPHostName;
    private javax.swing.JTextField jTextFieldSubject;
    // End of variables declaration//GEN-END:variables
}
