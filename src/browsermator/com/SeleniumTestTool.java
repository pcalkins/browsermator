
package browsermator.com;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner.DefaultEditor;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SeleniumTestTool extends JInternalFrame {
   JLabel jLabelInsertionPoint;
      JComboBox jComboBoxAtIndex;
String filename;
String short_filename;
  JPanel BugPanel;
SeleniumTestToolData STAppData;
ArrayList<ProcedureView> BugViewArray;
String undoTempFile;
    JButton jButtonPause;
    JButton jButtonContinue;
    JButton jButtonCancel;
    JButton jButtonClearUniqueList;
    JPanel SouthButtonPanel;
public final String USERDIR;
public final String UNIQUE_LOG_DIR;
   
  public SeleniumTestTool(SeleniumTestToolData in_STAppData)
  {
        USERDIR = System.getProperty("user.home") + File.separator + "BrowsermatorAppFolder";
UNIQUE_LOG_DIR = USERDIR + File.separator + "BrowsermatorUniqueLogFolder" + File.separator;
 
  this.undoTempFile = "";
  this.STAppData = in_STAppData;
  this.BugPanel = new JPanel();
  this.filename = STAppData.filename;
 
 this.short_filename = STAppData.short_filename;
  this.BugViewArray = new ArrayList<>();
 this.setIconifiable(true);
//  initComponents();
   initializeComponents();
      JTextFieldProgress.setVisible(false);
   jLabelTasks.setVisible(false);
  SouthButtonPanel.setVisible(false);
jButtonPlaceStoredVariable.setFocusable(false);
jComboBoxStoredVariables.setFocusable(false);

  
}
  public void addJButtonCancelActionListener(ActionListener listener)
  {
      jButtonCancel.addActionListener(listener);
  }
   public void addJButtonPauseActionListener(ActionListener listener)
  {
      jButtonPause.addActionListener(listener);
  }
    public void addJButtonContinueActionListener(ActionListener listener)
  {
      jButtonContinue.addActionListener(listener);
  }
  
  public void Pause()
  {
   jButtonPause.setEnabled(false);
       jButtonContinue.setEnabled(true);
       jButtonCancel.setEnabled(false);
  }
  public void Continue()
  {
       jButtonPause.setEnabled(true);
       jButtonContinue.setEnabled(false);
       jButtonCancel.setEnabled(true);
  }
 public void refreshjComboBoxAtIndex()
 {
    int bugindex = 0;
     jComboBoxAtIndex.removeAllItems();
     for (ProcedureView PV: BugViewArray)
     {
          bugindex++;
      jComboBoxAtIndex.addItem(bugindex);
    
     }
     jComboBoxAtIndex.addItem(bugindex+1);
     jComboBoxAtIndex.setSelectedItem(bugindex+1);
     
 }
 public void refreshjComboBoxMoveToIndex()
 {
      int bugindex = 0;
      int bugviewarraysize = BugViewArray.size();
      if (bugviewarraysize>0)
      {
     int[] indexes = new int[bugviewarraysize];
     for (int x=0; x<bugviewarraysize; x++)
     {
         indexes[x] = 0;
     }
     for (ProcedureView PV: BugViewArray)
     {
         indexes[bugindex] = PV.index;
         bugindex++;
     }
     for (ProcedureView PV: BugViewArray)
     {
     PV.jComboBoxMoveToIndex.removeAllItems();
     
     PV.jComboBoxMoveToIndex.addItem("Move To Index:");
     for (int thisindex: indexes)
     {
         if (PV.index!=thisindex && thisindex!=0)
         {
         PV.jComboBoxMoveToIndex.addItem(thisindex);
         }
     }
     
     }
      }
 }
 public int getInsertionPoint()
 {
     int ret_val = 1;
     if (jComboBoxAtIndex.getItemCount()>0)
     {
   ret_val = (Integer)jComboBoxAtIndex.getSelectedItem();
     }
     return ret_val;
 }
  public void initializeDisplay()
  {
      setjSpinnerEcTimeout(STAppData.getEcTimeout());
      setOSType(STAppData.getOSType());
      setTargetBrowserView(STAppData.getTargetBrowser());
      setFilename(STAppData.getFilename());
      setShortFilename(STAppData.getShortFilename());
      setPromptToClose(STAppData.getPromptToClose());
      setUniqueFileOptionView(STAppData.getUniqueFileOption());
      setShowReportView(STAppData.getShowReport());
      setIncludeScreenShotView(STAppData.getIncludeScreenshots());
      setSMTPHostname (STAppData.getSMTPHostname());
setEmailLoginName (STAppData.getEmailLoginName());
 setEmailPassword (STAppData.getEmailPassword());
setEmailTo (STAppData.getEmailTo());
setEmailFrom(STAppData.getEmailFrom());
 setSubject(STAppData.getEmailSubject());
setExitAfterView (STAppData.getExitAfter());
setjSpinnerWaitTime(STAppData.getWaitTime());
   setjSpinnerSessions(STAppData.getSessions());
   setUniqueListView(STAppData.getUniqueList());
   setEmailReportView(STAppData.getEmailReport());
    setEmailReportFailView(STAppData.getEmailReportFail());
      jButtonCancel.setEnabled(false);

if (STAppData.getTargetBrowser().equals("Firefox") || STAppData.getTargetBrowser().equals("Chrome"))
      {
      setOSTypeActive(true);
    
      }  

 populateSelectURLListPulldowns();
  }

   public void refreshStoredArrayPulldown(ProcedureView PV)
   {
       PV.JComboBoxStoredArrayLists.removeAllItems();
       PV.JComboBoxStoredArrayLists.addItem("Select a stored URL List");
       for (String keyname: STAppData.VarLists.keySet())
       {
           PV.JComboBoxStoredArrayLists.addItem(keyname);
       }
   }
   public void updateSelectedVariableName (String oldname, String newname)
    {
    
        if (STAppData.VarHashMap.containsKey(newname))
        {       
      
            
     
     
        }
        else
        {
  
            STAppData.VarHashMap.put(newname, "");
           
     
        }
             if (STAppData.VarHashMap.containsKey(oldname))
      {
       RemoveOldNameIfNotUsedVarHashMap(oldname);
        
      }  
     
    
    }   
    public void RemoveOldNameIfNotUsedVarHashMap(String _oldname)
    {
       boolean oldNameStillThere = false;
               for (Procedure PROC: STAppData.BugArray)
      {
          for (BMAction thisAct: PROC.ActionsList)
          {
              if (thisAct.Variable2.equals(_oldname))
              {
                oldNameStillThere = true;  
              }
          }
      }
               if (!oldNameStillThere)
               {
                 STAppData.VarHashMap.remove(_oldname);
               }  
    }
    public void RemoveOldNameIfNotUsedVarlist(String _oldname)
    {
        boolean oldNameStillThere = false;
               for (Procedure PROC: STAppData.BugArray)
      {
          for (BMAction thisAct: PROC.ActionsList)
          {
              if (thisAct.Variable2.equals(_oldname))
              {
                oldNameStillThere = true;  
              }
          }
      }
               if (!oldNameStillThere)
               {
                 STAppData.VarLists.remove(_oldname);
               }
    }
    public void updateSelectedArrayName(String oldname, String newname)
    {
     
        if (STAppData.VarLists.containsKey(newname))
        {       
      
            
       int bugindex = 0;  
            for (Procedure PROC: STAppData.BugArray)
      {
          if ("Dataloop".equals(PROC.Type))
          {
          if (PROC.URLListName.equals(oldname))
          {   
              if ("urllist".equals(STAppData.BugArray.get(bugindex).DataLoopSource))
      {
      PROC.setURLListName(newname);
      refreshStoredArrayPulldown(BugViewArray.get(bugindex));
  
         
      STAppData.BugArray.get(bugindex).setURLListName(newname);
      BugViewArray.get(bugindex).setURLListName(newname);
      }
      
          }
          
      
      }
              bugindex++;
      }
        }
        else
        {
  
            STAppData.VarLists.put(newname, new ArrayList<>());
           
             int bugindex = 0;  
            for (Procedure PROC: STAppData.BugArray)
      {
        if ("Dataloop".equals(PROC.Type))
          {
          if (PROC.URLListName.equals(oldname))
          {   
              if ("urllist".equals(STAppData.BugArray.get(bugindex).DataLoopSource))
      {
      
     PROC.setURLListName(newname);
      refreshStoredArrayPulldown(BugViewArray.get(bugindex));
      STAppData.BugArray.get(bugindex).setURLListName(newname);
      BugViewArray.get(bugindex).setURLListName(newname);
 BugViewArray.get(bugindex).JComboBoxStoredArrayLists.removeItem(oldname);
      }
          }
          }
          bugindex++;
      }
        }
      
           if (STAppData.VarLists.containsKey(oldname))
      {
          RemoveOldNameIfNotUsedVarlist(oldname);
      }  
    
    }
  
          public void updateStoredURLListIndexes(ProcedureView thisBugView)
      {
          int actionindex = 0;
        for (ActionView AV: thisBugView.ActionsViewList)
        {
         if ("StoreLinksAsURLListByXPATH".equals(AV.ActionType))
        {
            
            STAppData.setHasStoredArray(true);
                             String stringactionindex = Integer.toString(actionindex+1);
        String stringbugindex = Integer.toString(thisBugView.index);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
        String oldname = AV.JTextFieldVariable2.getText();
         String newname = bugdashactionindex;
         if (oldname.equals(newname))
         {
          // addSelectedVariableName(AV.JTextFieldVariableVARINDEX.getText());
      STAppData.VarLists.put(newname, new ArrayList<>());
       
       //  addSelectedArrayName(bugdashactionindex);
          }
          else
         {
              if ("".equals(oldname))
              {
           
           STAppData.addSelectedArrayName(bugdashactionindex); 
              }
              else
              {
                 
         updateSelectedArrayName(oldname, newname);
         
              }
        }
        AV.JTextFieldVariable2.setText(newname);
   //     AV.setJTextFieldVariableVARINDEX(newname);
   //  updateStoredListsPulldownView(oldname, newname, STAppData.VarLists);
     
   //  STAppFrame.updatePlacedLoopVariables(oldname, newname);
        }
       if ("StoreLinkAsVarByXPATH".equals(AV.ActionType))
        {
              STAppData.setHasStoredVar(true);
                            String stringactionindex = Integer.toString(actionindex+1);
        String stringbugindex = Integer.toString(thisBugView.index);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
        String oldname = AV.JTextFieldVariable2.getText();
         String newname = bugdashactionindex;
        if (oldname.equals(newname))
         {
          // addSelectedVariableName(AV.JTextFieldVariableVARINDEX.getText());
      STAppData.VarHashMap.put(newname, "");
       
       //  addSelectedArrayName(bugdashactionindex);
          }
        else
        {
              if ("".equals(oldname))
          {
           addSelectedVariableName(bugdashactionindex);
        //   AV.JTextFieldVariableVARINDEX.setText(bugdashactionindex);
         
          }
          else
          {
              updateSelectedVariableName(oldname, newname);
     //    STAppData.updateSelectedVariableName(oldname, newname);
     //    updateInsertedVariableNames(oldname, newname);
     //     AV.JTextFieldVariableVARINDEX.setText(newname);
          }
        }
   AV.JTextFieldVariable2.setText(newname);
        }  
       actionindex++;
        }
        
      } 

 public void populateSelectURLListPulldowns()
  {
    for (ProcedureView BV : BugViewArray)
      {
         
          if ("Dataloop".equals(BV.Type))
          {
            
                   String selecteditem = BV.JComboBoxStoredArrayLists.getSelectedItem().toString();
         
              BV.JComboBoxStoredArrayLists.setEnabled(false); 
                 BV.JComboBoxStoredArrayLists.removeAllItems();
              
        BV.JComboBoxStoredArrayLists.addItem("Select a stored URL List");
        BV.JComboBoxStoredArrayLists.addItem("MASTER");
        for (String keyname: STAppData.VarLists.keySet())
        {
            if (keyname.contains("-"))
            {
     String[] parts = keyname.split("-");
 String leftpart = parts[0];
 if (!"".equals(leftpart))
 {
 int bugindex = Integer.parseInt(leftpart);
 if (bugindex<BV.index)
 {       
           BV.JComboBoxStoredArrayLists.addItem(keyname);
 }
        }
            }
          }
     if ("".equals(selecteditem))
     {
         selecteditem = "Select a stored URL List";
     }
    BV.JComboBoxStoredArrayLists.setSelectedItem(selecteditem);
    BV.JComboBoxStoredArrayLists.setEnabled(true); 
            
          }
      }
  }

 public void setFilenames()
 {
     setShortFilename(STAppData.short_filename);
     setFilename(STAppData.filename);
 }
       public void setShortFilename(String in_short_filename)
       {
           this.short_filename = in_short_filename;
       

   setTitle(in_short_filename);
   repaint();
       }
        public void setOSType(String in_OSType)
        {
    
          
            switch (in_OSType)
            {
                case "Windows":
                   jCheckBoxOSTypeWindows32.setSelected(true); 
                   jCheckBoxOSTypeWindows64.setSelected(false);
                   jCheckBoxOSTypeMac.setSelected(false);
                   jCheckBoxOSTypeLinux32.setSelected(false);
                   jCheckBoxOSTypeLinux64.setSelected(false);
                    break;
                    
                case "Windows32":
               jCheckBoxOSTypeWindows32.setSelected(true); 
                   jCheckBoxOSTypeWindows64.setSelected(false);
                   jCheckBoxOSTypeMac.setSelected(false);
                   jCheckBoxOSTypeLinux32.setSelected(false);
                   jCheckBoxOSTypeLinux64.setSelected(false);
                break;
                case "Windows64":
                jCheckBoxOSTypeWindows64.setSelected(true);
                 jCheckBoxOSTypeWindows32.setSelected(false); 
               
                   jCheckBoxOSTypeMac.setSelected(false);
                   jCheckBoxOSTypeLinux32.setSelected(false);
                   jCheckBoxOSTypeLinux64.setSelected(false);
                break;
                case "Mac":
                jCheckBoxOSTypeMac.setSelected(true);
                 jCheckBoxOSTypeWindows32.setSelected(false); 
                   jCheckBoxOSTypeWindows64.setSelected(false);
               
                   jCheckBoxOSTypeLinux32.setSelected(false);
                   jCheckBoxOSTypeLinux64.setSelected(false);
                break;
                case "Linux-32":
                jCheckBoxOSTypeLinux32.setSelected(true);
                 jCheckBoxOSTypeWindows32.setSelected(false); 
                   jCheckBoxOSTypeWindows64.setSelected(false);
                   jCheckBoxOSTypeMac.setSelected(false);
                
                   jCheckBoxOSTypeLinux64.setSelected(false);
                break;
                case "Linux-64":
                jCheckBoxOSTypeLinux64.setSelected(true);
                 jCheckBoxOSTypeWindows32.setSelected(false); 
                   jCheckBoxOSTypeWindows64.setSelected(false);
                   jCheckBoxOSTypeMac.setSelected(false);
                   jCheckBoxOSTypeLinux32.setSelected(false);
               
                break;
                
            }
                
        }
        

  public String getFilename()
  {

      return this.filename;
      
  }
  public void setFilename(String in_filename)
  {
      this.filename = in_filename;
      
  
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
public void hideTaskGUI()
{
 jLabelTasks.setVisible(false);
 JTextFieldProgress.setVisible(false);
 SouthButtonPanel.setVisible(false);
}
public void showTaskGUI()
{
  jLabelTasks.setVisible(true);
 JTextFieldProgress.setVisible(true); 
 SouthButtonPanel.setVisible(true);
}
public void setCurrentlySelectedFieldToStoredVariable(String fieldindex, int fieldnum)
{
    String[] indexes = fieldindex.split("-");
    String bugindex = indexes[0];
    String actionindex = indexes[1];
    int bugindexnum = Integer.parseInt(bugindex)-1;
    int actionindexnum = Integer.parseInt(actionindex)-1;
    if (actionindexnum>=0)
    {
   BugViewArray.get(bugindexnum).ActionsViewList.get(actionindexnum).setFieldToStoredVariable(jComboBoxStoredVariables.getSelectedItem().toString(), fieldnum);
    }
}

  public String getSelectedVariableName()
      {
          String ret_val = "";
          ret_val = (String)jComboBoxStoredVariables.getSelectedItem();
          if (ret_val=="Select a stored variable") { ret_val = ""; }
          return ret_val;  
      }
  


public void addSelectedVariableNameView(String varname)
{
    if(((DefaultComboBoxModel)jComboBoxStoredVariables.getModel()).getIndexOf(varname) == -1) {
 jComboBoxStoredVariables.addItem(varname);
 int newitemindex = jComboBoxStoredVariables.getItemCount()-1;
        jComboBoxStoredVariables.setSelectedIndex(newitemindex);
    }
}
      public void MoveActionView (ProcedureView thisBugView, int toMoveIndex, int Direction)
   {
   saveState();
    int SwapIndex = toMoveIndex + Direction;
    if (Direction == 1)
       {
                if (toMoveIndex<thisBugView.ActionsViewList.size()-1)
     {


  Collections.swap(thisBugView.ActionsViewList, toMoveIndex, SwapIndex);
  thisBugView.ActionsViewList.get(toMoveIndex).SetIndexes(thisBugView.index, toMoveIndex+1);
  thisBugView.ActionsViewList.get(SwapIndex).SetIndexes(thisBugView.index, SwapIndex+1);

    


      
  
    }

       }
       if (Direction == -1)
       {
     if (toMoveIndex!=0)
    {
  
  Collections.swap(thisBugView.ActionsViewList, toMoveIndex, SwapIndex);
thisBugView.ActionsViewList.get(toMoveIndex).SetIndexes(thisBugView.index, toMoveIndex+1);
  thisBugView.ActionsViewList.get(SwapIndex).SetIndexes(thisBugView.index, SwapIndex+1);
 

       }
   }

 // UpdateDisplay();

   }
 
      public void addSelectedVariableName(String varname)
      {
         
       if(((DefaultComboBoxModel)jComboBoxStoredVariables.getModel()).getIndexOf(varname) == -1) {
 jComboBoxStoredVariables.addItem(varname);
 int newitemindex = jComboBoxStoredVariables.getItemCount()-1;
        jComboBoxStoredVariables.setSelectedIndex(newitemindex);
       
   
}
      }
 

 
  public void updateStoredVarPulldownView()
  {
        jComboBoxStoredVariables.removeAllItems();
        jComboBoxStoredVariables.addItem("Select a stored variable");
        for (String keyname: STAppData.VarHashMap.keySet())
        {
            jComboBoxStoredVariables.addItem(keyname);
        }
  }
    public void updateStoredListsPulldownView(String olditem, String selecteditem,  HashMap<String, String[]> VarLists)
  {
      for (ProcedureView BV : BugViewArray)
      {
         
          if ("Dataloop".equals(BV.Type))
          {
              if ("urllist".equals(BV.DataLoopSource))
              {
              BV.JComboBoxStoredArrayLists.setEnabled(false); 
                 BV.JComboBoxStoredArrayLists.removeAllItems();
               
        BV.JComboBoxStoredArrayLists.addItem("Select a stored URL List");
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
     if ("".equals(selecteditem))
     {
         selecteditem = "Select a stored URL List";
     }
    BV.JComboBoxStoredArrayLists.setSelectedItem(selecteditem);
    BV.updatePlacedLoopVars(selecteditem);
    List<String> blanklist = new ArrayList<>();
    BV.setJTableSourceToURLList(blanklist, selecteditem);
        BV.JComboBoxStoredArrayLists.setEnabled(true);
              }
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
       actionindex++;
            String stringactionindex = Integer.toString(actionindex);
        String stringbugindex = Integer.toString(bugindex);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
     jButtonPlaceStoredVariable.setActionCommand(bugdashactionindex);
     removejButtonPlaceStoredVariableActionListener();
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


    
      
      
      public boolean getExitAfter()
      {
          boolean checked = this.jCheckBoxExitAfter.isSelected();
        
          return checked;
      }
      
      public boolean getEmailReport()
    {
    boolean checked = this.jCheckBoxEmailReport.isSelected();

    return checked;
    }
         public boolean getIncludeScreenshots()
    {
         boolean checked = this.jCheckBoxIncludeScreenshots.isSelected();
         if (getShowReport()==false)
         {
             checked = false;
         }
 
    return checked;
       
    }
      public boolean getEmailReportFail()
    {
    boolean checked = this.jCheckBoxEmailReportFail.isSelected();
  
    return checked;
    }
    public void setPromptToClose(Boolean prompttoclose)
    {
  
        jCheckBoxPromptToClose.setSelected(prompttoclose);
     
    }
    public boolean getPromptToClose()
    {
       boolean checked = this.jCheckBoxPromptToClose.isSelected();
  
    return checked;  
    }
 
    public boolean getShowReport()
    {
    boolean checked = this.jCheckBoxShowReport.isSelected();
  
    return checked;
    }
public int GetWaitTime()
{
    int fallbackValue = 1;


    
    try {
       jSpinnerWaitTime.commitEdit();
   }
   catch (ParseException pe) {
     
       JComponent editor =jSpinnerWaitTime.getEditor();
       
       if (editor instanceof DefaultEditor) {
           ((DefaultEditor)editor).getTextField().setValue(jSpinnerWaitTime.getValue());
       }
     
       jSpinnerWaitTime.setValue(fallbackValue);
     
      
   }
   int wait = (Integer)this.jSpinnerWaitTime.getValue();
    return wait;
    
}
 public int getEcTimeout()
{
    int fallbackValue = 1;


    
    try {
       jSpinnerEcTimeout.commitEdit();
   }
   catch (ParseException pe) {
     
       JComponent editor =jSpinnerEcTimeout.getEditor();
       
       if (editor instanceof DefaultEditor) {
           ((DefaultEditor)editor).getTextField().setValue(jSpinnerEcTimeout.getValue());
       }
     
       jSpinnerEcTimeout.setValue(fallbackValue);
     
      
   }
   int wait = (Integer)this.jSpinnerEcTimeout.getValue();
    return wait;
    
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
    public void DeleteActionView (ProcedureView thisBugView, int atIndex)
   {
 String stringactionindex = Integer.toString(atIndex+1);
        String stringbugindex = Integer.toString(thisBugView.index);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
        if (STAppData.VarHashMap.containsKey(bugdashactionindex))
        {
            STAppData.VarHashMap.remove(bugdashactionindex);
          
        }
        if (STAppData.VarLists.containsKey(bugdashactionindex))
        {
           STAppData.VarLists.remove(bugdashactionindex);
           
        }
    thisBugView.ActionsViewList.remove(atIndex);
    
  for(int BugViewIndex=0; BugViewIndex<thisBugView.ActionsViewList.size(); BugViewIndex++)
     {
     if (BugViewIndex>=atIndex)
     {
     thisBugView.ActionsViewList.get(BugViewIndex).index--;
     }
     }
   
//   ChangeURLListPulldowns();
 updateStoredURLListIndexes(thisBugView);
 UpdateScrollPane(thisBugView);
   thisBugView.refreshjComboBoxAddAtPosition();
   }
     public void DeleteBugView (int BugIndex)
   {
 

   BugViewArray.remove(BugIndex-1);
   ResetBugIndexes();
 
   ChangeURLListPulldowns();
   RefreshStoredLists();
 
   }
     public void RefreshStoredLists()
     {
   //    Map<String, List<String>> map = STAppData.VarLists;
    //   int checker = 0;
         List<String> ExistingLists = new ArrayList<>();
         List<String> ExistingVars = new ArrayList<>();
               for (ProcedureView PV: BugViewArray)
       {
   
           for (ActionView AV: PV.ActionsViewList)
           {
           if ("StoreLinksAsURLListByXPATH".equals(AV.ActionType))
        {
            ExistingLists.add(AV.JTextFieldVariable2.getText());
        }
                if ("StoreLinkAsVarByXPATH".equals(AV.ActionType))
        {
            ExistingVars.add(AV.JTextFieldVariable2.getText());
        }
          
           }
       }
               List<String> removeFromVarlistsKeys = new ArrayList<>();
               for (String keyname: STAppData.VarLists.keySet())
               {
                   if (!ExistingLists.contains(keyname))
                   {
                       removeFromVarlistsKeys.add(keyname);
                   }
               }
              
               for (String keynameToRemove: removeFromVarlistsKeys)
               {
                   STAppData.VarLists.remove(keynameToRemove);
               }
                List<String> removeFromVars = new ArrayList<>();
                 for (String keyname: STAppData.VarHashMap.keySet())
               {
                   if (!ExistingVars.contains(keyname))
                   {
                       removeFromVars.add(keyname);
                   }
               }
                 for (String keynameToRemove: removeFromVars)
               {
                   STAppData.VarHashMap.remove(keynameToRemove);
               }
                
     }
       public void ResetBugIndexes()
   {
       int newindex = 1;
       for (ProcedureView procview: BugViewArray)
       {
           procview.SetIndex(newindex);
           newindex++;
       }
       newindex = 1;
       for (Procedure PROC: STAppData.BugArray)
       {
           PROC.setIndex(newindex);
           newindex++;
       }
       refreshjComboBoxAtIndex();
       refreshjComboBoxMoveToIndex();
   }
   
     public void ChangeURLListPulldowns()
     {
            for (ProcedureView PV: BugViewArray)
   {
   
           for (ActionView AV: PV.ActionsViewList)
           {
           if ("StoreLinksAsURLListByXPATH".equals(AV.ActionType))
        {
                 STAppData.setHasStoredArray(true);
                             String stringactionindex = Integer.toString(AV.index);
        String stringbugindex = Integer.toString(PV.index);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
        String oldname = AV.JTextFieldVariable2.getText();
         String newname = bugdashactionindex; 
         if (!oldname.equals(newname))
         {
             updateSelectedArrayName(oldname, newname); 
         }
        }
           }
   
    updateStoredURLListIndexes(PV);
       
    
   }
          RemoveUpdateStoredPulldowns();
     }
     public void RemoveUpdateStoredPulldowns()
     {
    
            UpdateURLListPulldowns();  
           updateStoredVarPulldownView();
           UpdateDisplay();
   
     }
     
     public void UpdateURLListPulldowns()
     {
        int procedureindex = 0;
        for (ProcedureView PV: BugViewArray)
        {
            if ("Dataloop".equals(PV.Type))
            {
                if ("urllist".equals(PV.DataLoopSource))
                {
                    
              String selecteditem = PV.JComboBoxStoredArrayLists.getSelectedItem().toString();
       
           PV.JComboBoxStoredArrayLists.removeAllItems();
           PV.JComboBoxStoredArrayLists.addItem("Select a stored URL List");
           boolean hasSelecteditem = false;
            for (String keyname: STAppData.VarLists.keySet())
        {
            if (keyname.equals(selecteditem))
            {
                hasSelecteditem = true;
            }
            PV.JComboBoxStoredArrayLists.addItem(keyname);
        }
             List<String> blanklist = new ArrayList<>();
            if (hasSelecteditem)
            {
               PV.setURLListName(selecteditem);
               STAppData.BugArray.get(procedureindex).setURLListName(selecteditem);
               
            }
            else
            {
           PV.JComboBoxStoredArrayLists.setSelectedIndex(0); 
         PV.setURLListName("");
           PV.setJTableSourceToURLList(blanklist, "");
           STAppData.BugArray.get(procedureindex).setURLListName("");
            }
     
           
            }
        }
            procedureindex++;
     }
   
     }
 public void disableRemoves()
 {
    for (ProcedureView PV: BugViewArray)
   {
      PV.JButtonDeleteBug.setEnabled(false);
      for (ActionView AV: PV.ActionsViewList)
      {
          AV.JButtonDelete.setEnabled(false);
      }
   }       
 }
 public void enableRemoves()
 {  
     for (ProcedureView PV: BugViewArray)
   {
      PV.JButtonDeleteBug.setEnabled(true);
      for (ActionView AV: PV.ActionsViewList)
      {
          AV.JButtonDelete.setEnabled(true);
      }
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
 public void resetRunButtons()
 {
   for (ProcedureView PV: BugViewArray)
   {
       PV.JButtonRunTest.setText("Run");
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
 public void clearProcedurePassFailColors(ProcedureView PV)
 {
          PV.JLabelPass.setText("");
       for (ActionView TAV: PV.ActionsViewList)
       {
           
           TAV.JButtonDragIt.setBackground(new JButton().getBackground());
       }
 }
 public void setRunButtonEnabled(Boolean enabled_value)
 {
    this.jButtonDoStuff.setEnabled(enabled_value);
 }

 public void setFlattenFileButtonName (String newtext)
 {
     jButtonFlattenFile.setText(newtext);
 }

 public void RefreshViewData()
 {
     for (ProcedureView procview: this.BugViewArray)
     {
         if ("Dataloop".equals(procview.Type))
         {
        
      
           procview.refreshURLListRunTimeEntries();  
         
             }
       
     }
     for (Procedure PROC: this.STAppData.BugArray)
     {
         if ("file".equals(PROC.Type))
         {
             PROC.RunTimeFileSet.clear();
         }
     }
  
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
 
               public void setJButtonFlattenFileEnabled(boolean enable)
               {
     
   jButtonFlattenFile.setEnabled(enable);
               }

 public void saveState() 
{

 //   System.out.println("SaveState");
//   try
//    {
//        String tempDir = System.getProperty("java.io.tmpdir");
//         File temp = new File(tempDir + short_filename+"-undo.tmp");
  
//         temp.createNewFile();
//      FileOutputStream fos = new FileOutputStream(temp);
//      ObjectOutputStream oos = new ObjectOutputStream(fos);  
//       oos.writeObject(STAppData.BugArray);
    
//       undoTempFile = temp.getAbsolutePath();
//       temp.deleteOnExit();
//       oos.close();
//       fos.close();
//   }
//   catch (Exception ex)
//   {
//       System.out.println("Exception when writing undo file" + ex.toString());
//        ex.printStackTrace();
        
 // }
   
     
     
  
    
}
public void Undo()
{ 
  
   try
    {
        
        File lastUndoFile = new File(undoTempFile);
      if(lastUndoFile.exists())
      {
     FileInputStream fis = new FileInputStream(undoTempFile);
      ObjectInputStream ois = new ObjectInputStream(fis);
      ArrayList<Procedure> loadedBugArray = (ArrayList<Procedure>)ois.readObject();
      ois.close();
      fis.close();
      saveState();
    
     
    STAppData.BugArray.clear();
    for (Procedure PROC: loadedBugArray)
    {
    STAppData.BugArray.add(PROC);
    }
   
      }
    }
    catch (Exception ex)
    {
        System.out.println("Exception loading undo file: " + ex.toString());
    }
   
}
 
 public void UpdateDisplay()
 {
 this.MainScrollPane.setVisible(false);
 
 this.BugPanel.setVisible(false);
 GridBagLayout buglayout = new GridBagLayout();
 GridBagConstraints bugConstraints = new GridBagConstraints();
 bugConstraints.anchor = GridBagConstraints.NORTH;
 bugConstraints.fill = GridBagConstraints.HORIZONTAL;
 bugConstraints.gridx = 0;
 bugConstraints.weightx = 1.0;
 bugConstraints.weighty = 1.0;

this.BugPanel.removeAll();



 populateSelectURLListPulldowns();
 this.BugPanel.setLayout(buglayout);
int bugindex = 0;
    for (ProcedureView BV : BugViewArray)
      {
      bugConstraints.gridy = bugindex;
          BV.SetIndex(bugindex+1);
       
          this.BugPanel.add(BV.JPanelBug, bugConstraints);


    JPanel ActionPanel = new JPanel();

       BV.ActionScrollPane.setViewportView(ActionPanel);
      UpdateScrollPane(BV); 

bugindex++;
       

     }

  this.BugPanel.setVisible(true);
     this.MainScrollPane.setViewportView(this.BugPanel);

     this.MainScrollPane.setVisible(true);
    // this.setResizable(false);
 // this.pack();
    this.validate();
 // this.revalidate();
      updateStoredVarPulldownView();
 }

     public void setJTextFieldProgress(String value)
{
       jButtonCancel.setEnabled(true);
    JTextFieldProgress.setText(value);
}
     public void UpdateScrollPane(ProcedureView newbugview)
     {
              
            JPanel ActionPanel = (JPanel)newbugview.ActionScrollPane.getViewport().getView();
          
            ActionPanel.removeAll();
           
            GridBagLayout ActionLayout = new GridBagLayout();
      
          
      
  
              
         int actionindex = 0;
    GridBagConstraints ActionConstraints = new GridBagConstraints();
   
         ActionConstraints.anchor = GridBagConstraints.WEST;
              ActionConstraints.gridx = 0;
      ActionConstraints.fill = GridBagConstraints.HORIZONTAL;
      ActionConstraints.weightx = 1.0;
    ActionPanel.setLayout(ActionLayout); 
      for (ActionView AV : newbugview.ActionsViewList )
        {
        ActionConstraints.gridy = actionindex;
   
   
          AV.SetIndexes(newbugview.index, actionindex+1);
         
          for (ActionSettings theseSettings: AV.theseActionSettings)
          {
                GridBagConstraints ActionPanelConstraints = new GridBagConstraints();
        
      ActionPanelConstraints.insets = new java.awt.Insets(2,2,2,2);
       ActionPanelConstraints.fill = GridBagConstraints.HORIZONTAL;
       ActionPanelConstraints.anchor = theseSettings.GridBagAnchor;
      ActionPanelConstraints.gridx = theseSettings.gridx;
      ActionPanelConstraints.gridy = actionindex;
      ActionPanelConstraints.gridwidth = theseSettings.width;
      ActionPanelConstraints.gridheight = 1;
      ActionPanelConstraints.weightx = theseSettings.weightX;
        AV.JPanelAction.add(theseSettings.comp,ActionPanelConstraints);
        ActionPanel.add(AV.JPanelAction, ActionConstraints);
          }
         actionindex++;
        
        }
   
      if (actionindex < 8 )
      {
   
    newbugview.ActionScrollPane.setPreferredSize(new Dimension(950, (36 * actionindex)+20));  
   
          }
      else
      {
     newbugview.ActionScrollPane.setPreferredSize(new Dimension(950, 236));   
      }
      
      newbugview.ActionScrollPane.setVisible(true);
      newbugview.ActionScrollPane.setViewportView(ActionPanel);


     }

 

    

    
 
             public void AddNewBugView()
        {
       
        ProcedureView newbugview = new ProcedureView();
       
         newbugview.setType("Procedure");
      
         BugViewArray.add(newbugview);
         int thissize = BugViewArray.size();
          BugViewArray.get(thissize-1).SetIndex(thissize);
       
    refreshjComboBoxAtIndex();
    refreshjComboBoxMoveToIndex();
        }
         public void AddNewBugView(int atindex)
         {
             atindex--;
           ProcedureView newbugview = new ProcedureView();
       
         newbugview.setType("Procedure");
      
         BugViewArray.add(atindex, newbugview);
         ResetBugIndexes();
     
       ChangeURLListPulldowns();
  
         }
                  public void AddNewDataLoopView()
        {
        ProcedureView newdataloopview = new ProcedureView();
      
        newdataloopview.setType("Dataloop");
        newdataloopview.setDataLoopSource("none");
     
       AddDataLoopProcView(newdataloopview);
    
        }
            public void AddNewDataLoopView(int atindex)
            {
            ProcedureView newdataloopview = new ProcedureView();
      
        newdataloopview.setType("Dataloop");
        newdataloopview.setDataLoopSource("none");
     
       AddDataLoopProcView(newdataloopview, atindex);     
            }
         public void AddDataLoopProcView(ProcedureView newdataloopview)
        {
           
           
     
      
          BugViewArray.add(newdataloopview);
          int thissize = BugViewArray.size();
         BugViewArray.get(thissize-1).SetIndex(thissize);
       
       ResetBugIndexes();
     
        }
               public void AddDataLoopProcView(ProcedureView newdataloopview, int atindex)
        {
           atindex--;
        
      
          BugViewArray.add(atindex, newdataloopview);
       ResetBugIndexes();
      
       ChangeURLListPulldowns();
        }
        public void AddNewDataLoopURLListView(String in_listname)
        {
          
        ProcedureView newdataloopview = new ProcedureView();
        newdataloopview.setType("Dataloop");
        newdataloopview.setDataLoopSource("urllist");
       
    
        newdataloopview.setURLListName(in_listname);
        List<String> blanklist = new ArrayList<>();
  newdataloopview.setJTableSourceToURLList(blanklist, in_listname);
  
     
     
     
   AddDataLoopProcView(newdataloopview);
        }
          public void AddNewDataLoopURLListView(String in_listname, int at_index)
        {
          
        ProcedureView newdataloopview = new ProcedureView();
        newdataloopview.setType("Dataloop");
        newdataloopview.setDataLoopSource("urllist");
       
    
        newdataloopview.setURLListName(in_listname);
        List<String> blanklist = new ArrayList<>();
  newdataloopview.setJTableSourceToURLList(blanklist, in_listname);
  
     
     
     
   AddDataLoopProcView(newdataloopview, at_index);
        }
           public void AddNewDataLoopFileView(File CSVFile)
        {
        String canon_path = "";
         ProcedureView newdataloopview = new ProcedureView();
        newdataloopview.setType("Dataloop");
        newdataloopview.setDataLoopSource("file");
         List<String[]> dataset_to_send = new ArrayList<>();
        if (CSVFile.exists())
        {
        try {
           canon_path = CSVFile.getCanonicalPath();
        }
        catch (IOException ex)
        {
            System.out.println("Exception getting data file path: " + ex.toString());
        }
      
                         newdataloopview.setDataFile(canon_path);
                dataset_to_send = STAppData.getDataSetByFileName(canon_path);
        }
       //  newbug.setDataSet(dataset_to_send);
           newdataloopview.setJComboBoxStoredArraylists("Select a stored URL List");
   newdataloopview.setJTableSourceToDataSet(dataset_to_send, canon_path);
       
        
  
   AddDataLoopProcView(newdataloopview);
        }
                      public void AddNewDataLoopFileView(File CSVFile, int at_index)
        {
     
      
         String canon_path = "";
         ProcedureView newdataloopview = new ProcedureView();
        newdataloopview.setType("Dataloop");
        newdataloopview.setDataLoopSource("file");
        if (CSVFile.exists())
        {
        try {
           canon_path = CSVFile.getCanonicalPath();
        }
        catch (IOException ex)
        {
            System.out.println("Exception getting data file path: " + ex.toString());
        }
      
                         newdataloopview.setDataFile(canon_path);
                  List<String[]> dataset_to_send = STAppData.getDataSetByFileName(canon_path);
       //  newbug.setDataSet(dataset_to_send);
           newdataloopview.setJComboBoxStoredArraylists("Select a stored URL List");
   newdataloopview.setJTableSourceToDataSet(dataset_to_send, canon_path);
       
        }
  
   AddDataLoopProcView(newdataloopview, at_index);
        }
  

 
      public void DisableProcedure(ProcedureView thisbugview, Procedure thisbug)
      {
      thisbugview.Disable();
      thisbug.Disable();
 
      }
      public void EnableProcedure(ProcedureView thisbugview, Procedure thisbug)
      {
      thisbugview.Enable(); 
      thisbug.Enable();
  
      }
     
  
       public void ScrollActionPaneDown(ProcedureView bugview)
 {
     
           JComponent component = (JComponent)bugview.ActionScrollPane.getViewport().getView();
           int last_added_action_index = bugview.getJComboBoxAddAtPosition();
           if (last_added_action_index>0) { last_added_action_index--;}
    Rectangle bounds =  bugview.ActionsViewList.get(last_added_action_index).JPanelAction.getBounds();
   
      component.scrollRectToVisible(bounds); 
     
  //      JScrollBar action_scroll_pane_vertical = bugview.ActionScrollPane.getVerticalScrollBar();
 // action_scroll_pane_vertical.setValue( action_scroll_pane_vertical.getMaximum() );         
 } 
    public void RefreshActionViewIndexes (ArrayList<ActionView> actionviewlist)
                {
                  for (int x=0; x<actionviewlist.size(); x++)
                  {
                      actionviewlist.get(x).index = x;
                  }
                }
      public void AddActionViewToArray (ActionView actionview, ProcedureView newbugview)
{
    int at_index = newbugview.getJComboBoxAddAtPosition();
    if (at_index>0) {at_index--;
            newbugview.ActionsViewList.add(at_index, actionview);
             actionview.index = at_index;
             RefreshActionViewIndexes(newbugview.ActionsViewList);
                }
    else
    {
        newbugview.ActionsViewList.add(actionview);
         actionview.index = newbugview.ActionsViewList.size();
    }
         
        actionview.SetIndexes(newbugview.index, actionview.index);
        
               if ("StoreLinksAsURLListByXPATH".equals(actionview.ActionType))
           {
       
              
              UpdateURLListPulldowns();
           }
                    if ("StoreLinkAsVarByXPATH".equals(actionview.ActionType))
           {
       
              
             updateStoredVarPulldownView();
           }

 UpdateScrollPane(newbugview);


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
  

 private void initializeComponents()
 {
    
     JPanel jPanelNorth = new JPanel();
     JPanel jPanelSouth = new JPanel();
     JPanel jPanelEast = new JPanel();
  //   JPanel jPanelWest = new JPanel();
        jButtonDoStuff = new javax.swing.JButton();
        jButtonNewBug = new javax.swing.JButton();
   
      
        jLabelInsertionPoint = new JLabel("Add at position: ");
  jComboBoxAtIndex = new javax.swing.JComboBox();
        MainScrollPane = new javax.swing.JScrollPane();
        jLabelTHISSITEURL = new javax.swing.JLabel();
        jSpinnerWaitTime = new javax.swing.JSpinner();
        jSpinnerEcTimeout = new javax.swing.JSpinner();
        JLabel jLabelEcTimeout = new javax.swing.JLabel();
        JLabel jLabelPauseTime = new javax.swing.JLabel();
        jCheckBoxEmailReport = new javax.swing.JCheckBox();
        JLabel jLabelSMTPHostname = new javax.swing.JLabel();
        jTextFieldSMTPHostName = new javax.swing.JTextField();
        JLabel jLabelEmailLoginName = new javax.swing.JLabel();
        jTextFieldEmailLoginName = new javax.swing.JTextField();
        JLabel jLabelEmailLoginPassword = new javax.swing.JLabel();
        JLabel jLabelEmailTo = new javax.swing.JLabel();
        jTextFieldEmailTo = new javax.swing.JTextField();
        JLabel jLabelEmailFrom = new javax.swing.JLabel();
        jTextFieldEmailFrom = new javax.swing.JTextField();
        JLabel jLabelTitleSubject = new javax.swing.JLabel();
        jTextFieldSubject = new javax.swing.JTextField();
        jCheckBoxShowReport = new javax.swing.JCheckBox();
        jCheckBoxExitAfter = new javax.swing.JCheckBox();
        jTextFieldEmailPassword = new javax.swing.JPasswordField();
        jCheckBoxEmailReportFail = new javax.swing.JCheckBox();
        jCheckBoxPromptToClose = new javax.swing.JCheckBox();
        JLabel jLabelTargetBrowser = new javax.swing.JLabel();
        jComboBoxTargetBrowser = new javax.swing.JComboBox();
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
        jCheckBoxEnableMultiSession = new javax.swing.JCheckBox();
        JLabel jLabelIENote = new javax.swing.JLabel();
        jCheckBoxUniqueURLs = new javax.swing.JCheckBox();
        jRadioButtonUniquePerFile = new javax.swing.JRadioButton();
        jRadioButtonUniqueGlobal = new javax.swing.JRadioButton();
        JTextFieldProgress = new javax.swing.JTextField();
        jLabelTasks = new javax.swing.JLabel();
        BorderLayout mainLayout = new BorderLayout();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 800));

        jButtonDoStuff.setText("Run All Procedures");
        jButtonDoStuff.setActionCommand("AddAction");

        jButtonNewBug.setIcon(new javax.swing.ImageIcon(getClass().getResource("/browsermator/com/Resources/newFile.png"))); // NOI18N
        jButtonNewBug.setText("Add Procedure ");
      

        MainScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        MainScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        MainScrollPane.setName(""); // NOI18N
        jLabelEcTimeout.setText("EC Timeout:");
        jSpinnerEcTimeout.setModel(new javax.swing.SpinnerNumberModel(0, 0, 99, 1));
        jSpinnerEcTimeout.setMinimumSize(new java.awt.Dimension(41, 21));
        jSpinnerEcTimeout.setPreferredSize(new java.awt.Dimension(41, 21));
        
        jSpinnerWaitTime.setModel(new javax.swing.SpinnerNumberModel(0, 0, 99, 1));
        jSpinnerWaitTime.setMinimumSize(new java.awt.Dimension(41, 21));
        jSpinnerWaitTime.setPreferredSize(new java.awt.Dimension(41, 21));

        jLabelPauseTime.setText("Pause time (seconds)");

        jCheckBoxEmailReport.setText("Email Report");

        jLabelSMTPHostname.setText("SMTPHostname:");

    

        jLabelEmailLoginName.setText("Email Login Name:");

        jLabelEmailLoginPassword.setText("Email Login Password:");

        jLabelEmailTo.setText("E-mail To:");

        jLabelEmailFrom.setText("E-mail From:");

        jLabelTitleSubject.setText("Title/Subject:");

        jCheckBoxShowReport.setText("Show Report");

        jCheckBoxExitAfter.setText("Exit After Running");

      

        jCheckBoxEmailReportFail.setText("Email Fail Report Only");
        jCheckBoxEmailReportFail.setActionCommand("EmailReportIfFail");

        jCheckBoxPromptToClose.setText("Prompt to Close WebDriver/Browser");

        jLabelTargetBrowser.setText("Target Browser:");
        String[] BugIndexes = new String[0];
        
      

        jComboBoxTargetBrowser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Chrome", "Firefox", "Internet Explorer-32", "Internet Explorer-64", "Chrome 49", "Edge", "Silent Mode (HTMLUnit)", "Firefox/IE/Chrome" }));

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
        jSpinnerSessions.setEnabled(false);

        jLabelSessions.setText("Number of Sessions:");

        jCheckBoxEnableMultiSession.setText("Enable Multi-Session");

        jLabelIENote.setText("*To use Internet Explorer all security zones must have Enable Protected Mode checked.");

        jCheckBoxUniqueURLs.setText("Unique Random URLs Only");
        jCheckBoxUniqueURLs.setToolTipText("");

        jRadioButtonUniquePerFile.setText("Per File");
        jRadioButtonUniquePerFile.setEnabled(false);

        jRadioButtonUniqueGlobal.setText("Global");
        jRadioButtonUniqueGlobal.setEnabled(false);
        JLabel jLabelOSType = new JLabel();
        jLabelOSType.setText("Set OS:");
        jLabelTasks.setText("Running Tasks:");
     //   setLayout(mainLayout);
       
        jPanelNorth.add(jButtonNewBug);
        jPanelNorth.add(jButtonNewDataLoop);
        jPanelNorth.add(jLabelInsertionPoint);
        jPanelNorth.add(jComboBoxAtIndex);
        jPanelNorth.add(jLabelStoredVariables);
       
        jPanelNorth.add(jComboBoxStoredVariables);
        jPanelNorth.add(jButtonPlaceStoredVariable);
        jPanelNorth.add(jButtonGutsView);
      
        jPanelSouth.add(jButtonDoStuff);
   
        jPanelSouth.add(jLabelPauseTime);
        
        jPanelSouth.add(jSpinnerWaitTime);
        jPanelSouth.add(jLabelEcTimeout);
        jPanelSouth.add(jSpinnerEcTimeout);
        jPanelSouth.add(jLabelTargetBrowser);
        jPanelSouth.add(jComboBoxTargetBrowser);
        jPanelSouth.add(jButtonBrowseForFireFoxExe);
        jPanelSouth.add(jCheckBoxPromptToClose);
        JPanel jPanelSouth2 = new JPanel();
        
        jPanelSouth2.add(jLabelTasks);
   
  jButtonPause = new JButton("Pause");
        jButtonContinue = new JButton("Continue");
        jButtonContinue.setEnabled(false);
        jButtonCancel = new JButton("Cancel");
     jButtonClearUniqueList = new JButton("Clear Unique List");
  
     SouthButtonPanel = new JPanel();
        SouthButtonPanel.add(jButtonContinue);
        SouthButtonPanel.add(jButtonPause);
        SouthButtonPanel.add(jButtonCancel);
        JTextFieldProgress.setPreferredSize(new Dimension(600, 20));
        jPanelSouth2.add(JTextFieldProgress);
        JPanel jPanelAllSouth = new JPanel();
        jPanelAllSouth.setLayout(new BoxLayout( jPanelAllSouth , BoxLayout.Y_AXIS));
         jPanelAllSouth.add(jPanelSouth);
         jPanelAllSouth.add(jPanelSouth2);
         jPanelAllSouth.add(SouthButtonPanel);
       
    //    jPanelSouth.setPreferredSize(new Dimension(800, 240));
     
      //   add(jPanelNorth, BorderLayout.NORTH);
         add(MainScrollPane, BorderLayout.CENTER);
         add(jPanelAllSouth, BorderLayout.SOUTH);
      
        // jPanelEast.setLayout(new FlowLayout());
        jPanelEast.setLayout(new BoxLayout(jPanelEast, BoxLayout.Y_AXIS));
        jPanelEast.add(jCheckBoxShowReport);
        jPanelEast.add(jCheckBoxIncludeScreenshots);
        jPanelEast.add(jCheckBoxExitAfter);

   
        
                JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
separator2.setMaximumSize( new Dimension(Integer.MAX_VALUE, 10) );
           jPanelEast.add(separator2);
        jPanelEast.add(jLabelOSType);
        jPanelEast.add(jCheckBoxOSTypeWindows32);
        jPanelEast.add(jCheckBoxOSTypeWindows64);
        jPanelEast.add(jCheckBoxOSTypeLinux32);
        jPanelEast.add(jCheckBoxOSTypeLinux64);
        jPanelEast.add(jCheckBoxOSTypeMac);
                JSeparator separator3 = new JSeparator(SwingConstants.HORIZONTAL);
separator3.setMaximumSize( new Dimension(Integer.MAX_VALUE, 10) );
           jPanelEast.add(separator3);
   
        jPanelEast.add(jCheckBoxUniqueURLs);
        jPanelEast.add(jRadioButtonUniquePerFile);
        jPanelEast.add(jRadioButtonUniqueGlobal);
        jPanelEast.add(jButtonClearUniqueList);
   
        
             JSeparator separator30 = new JSeparator(SwingConstants.HORIZONTAL);
separator30.setMaximumSize( new Dimension(Integer.MAX_VALUE, 10) );
           jPanelEast.add(separator30);
  //    jPanelWest.setLayout(new BoxLayout(jPanelWest, BoxLayout.Y_AXIS));
        jPanelEast.add(jCheckBoxEmailReport);
        jPanelEast.add(jCheckBoxEmailReportFail);
       jPanelEast.add(jLabelSMTPHostname);
      jTextFieldSMTPHostName.setMaximumSize( new Dimension(Integer.MAX_VALUE, 20) );
       jTextFieldEmailLoginName.setMaximumSize( new Dimension(Integer.MAX_VALUE, 20) );
        jTextFieldEmailPassword.setMaximumSize( new Dimension(Integer.MAX_VALUE, 20) );
          jTextFieldEmailTo.setMaximumSize( new Dimension(Integer.MAX_VALUE, 20) );
           jTextFieldEmailFrom.setMaximumSize( new Dimension(Integer.MAX_VALUE, 20) );
           jTextFieldSubject.setMaximumSize( new Dimension(Integer.MAX_VALUE, 20) );
               
        jPanelEast.add(jTextFieldSMTPHostName);
        jPanelEast.add(jLabelEmailLoginName);
        jPanelEast.add(jTextFieldEmailLoginName);
        jPanelEast.add(jLabelEmailLoginPassword);
        jPanelEast.add(jTextFieldEmailPassword);
        jPanelEast.add(jLabelEmailTo);
        jPanelEast.add(jTextFieldEmailTo);
        jPanelEast.add(jLabelEmailFrom);
        jPanelEast.add(jTextFieldEmailFrom);
        jPanelEast.add(jLabelTitleSubject);
        jPanelEast.add(jTextFieldSubject);
        jPanelEast.add(jButtonLoadEmailSettings);
        jPanelEast.add(jButtonClearEmailSettings);

 
             JSeparator separator4 = new JSeparator(SwingConstants.HORIZONTAL);
separator4.setMaximumSize( new Dimension(Integer.MAX_VALUE, 10) );
        jPanelEast.add(separator4);
     
        jPanelEast.add(jButtonFlattenFile);
     add(jPanelEast, BorderLayout.WEST);
         JSeparator separator5 = new JSeparator(SwingConstants.HORIZONTAL);
separator5.setMaximumSize( new Dimension(Integer.MAX_VALUE, 10) );
     
       add(jPanelNorth, BorderLayout.NORTH);
         pack();
    
     
        String visited_list_file_path = UNIQUE_LOG_DIR + "browsermator_" + STAppData.short_filename + "_visited_url_log.xml";
       File fileCheck = new File(visited_list_file_path);
       if (!fileCheck.exists())
       {
      jButtonClearUniqueList.setEnabled(false);
       }
      
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
        jCheckBoxEnableMultiSession = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jCheckBoxUniqueURLs = new javax.swing.JCheckBox();
        jRadioButtonUniquePerFile = new javax.swing.JRadioButton();
        jRadioButtonUniqueGlobal = new javax.swing.JRadioButton();
        JTextFieldProgress = new javax.swing.JTextField();
        jLabelTasks = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSpinnerEcTimeout = new javax.swing.JSpinner();

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

        jLabel11.setText("SMTPHostname:");

        jLabel4.setText("Email Login Name:");

        jLabel5.setText("Email Login Password:");

        jLabel6.setText("E-mail To:");

        jLabel7.setText("E-mail From:");

        jLabel3.setText("Title/Subject:");

        jCheckBoxShowReport.setText("Show Report");

        jCheckBoxExitAfter.setText("Exit After Running");

        jCheckBoxEmailReportFail.setText("Email Fail Report Only");
        jCheckBoxEmailReportFail.setActionCommand("EmailReportIfFail");

        jCheckBoxPromptToClose.setText("Prompt to Close WebDriver/Browser");

        jLabel8.setText("Target Browser:");

        jComboBoxTargetBrowser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Chrome", "Firefox", "Internet Explorer-32", "Internet Explorer-64", "Chrome 49", "Silent Mode (HTMLUnit)", "Firefox/IE/Chrome" }));

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
        jSpinnerSessions.setEnabled(false);

        jLabelSessions.setText("Number of Sessions:");

        jCheckBoxEnableMultiSession.setText("Enable Multi-Session");

        jLabel2.setText("*To use Internet Explorer all security zones must have Enable Protected Mode checked.");

        jCheckBoxUniqueURLs.setText("Unique Random URLs Only");
        jCheckBoxUniqueURLs.setToolTipText("");

        jRadioButtonUniquePerFile.setText("Per File");
        jRadioButtonUniquePerFile.setEnabled(false);

        jRadioButtonUniqueGlobal.setText("Global");
        jRadioButtonUniqueGlobal.setEnabled(false);

        jLabelTasks.setText("Running Tasks:");

        jLabel9.setText("Timeout (seconds)");

        jSpinnerEcTimeout.setModel(new javax.swing.SpinnerNumberModel(0, 0, 99, 1));
        jSpinnerEcTimeout.setMinimumSize(new java.awt.Dimension(41, 21));
        jSpinnerEcTimeout.setPreferredSize(new java.awt.Dimension(41, 21));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabelTHISSITEURL)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelTasks)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(JTextFieldProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jCheckBoxOSTypeWindows32)
                                                .addGap(13, 13, 13)
                                                .addComponent(jCheckBoxOSTypeWindows64)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jCheckBoxOSTypeLinux32)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jCheckBoxOSTypeLinux64)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jCheckBoxOSTypeMac)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCheckBoxUniqueURLs)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jRadioButtonUniquePerFile)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jRadioButtonUniqueGlobal)))
                                        .addGap(1, 1, 1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jButtonDoStuff, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(6, 6, 6)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel9)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jSpinnerEcTimeout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel1)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jSpinnerWaitTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                                                    .addComponent(jCheckBoxEmailReportFail, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jCheckBoxPromptToClose)
                                                            .addComponent(jCheckBoxExitAfter))
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jButtonFlattenFile))))
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
                                                .addComponent(jSpinnerSessions, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jCheckBoxEnableMultiSession)))
                                        .addGap(14, 14, 14)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(60, 60, 60)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(39, 39, 39)
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
                                .addComponent(jButtonLoadEmailSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 109, Short.MAX_VALUE))
                    .addComponent(MainScrollPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonNewBug, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonNewDataLoop, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelStoredVariables)
                        .addGap(10, 10, 10)
                        .addComponent(jComboBoxStoredVariables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonPlaceStoredVariable)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonGutsView)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxStoredVariables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonPlaceStoredVariable)
                            .addComponent(jLabelStoredVariables)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonGutsView))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonNewBug, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonNewDataLoop, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelTHISSITEURL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MainScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 655, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonDoStuff, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBoxShowReport)
                                .addComponent(jCheckBoxPromptToClose)
                                .addComponent(jButtonFlattenFile)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jSpinnerEcTimeout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCheckBoxIncludeScreenshots)
                                .addComponent(jCheckBoxExitAfter)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jSpinnerWaitTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)
                                .addComponent(jCheckBoxEmailReport))
                            .addComponent(jCheckBoxEmailReportFail)))
                    .addGroup(layout.createSequentialGroup()
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
                            .addComponent(jTextFieldEmailPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldEmailTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldEmailFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBoxUniqueURLs))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextFieldSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jRadioButtonUniqueGlobal)
                            .addComponent(jRadioButtonUniquePerFile))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonClearEmailSettings)
                            .addComponent(jButtonLoadEmailSettings)
                            .addComponent(JTextFieldProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelTasks)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jComboBoxTargetBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonBrowseForFireFoxExe))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabelSessions)
                                .addComponent(jSpinnerSessions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jCheckBoxEnableMultiSession)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxOSTypeWindows32)
                            .addComponent(jCheckBoxOSTypeMac)
                            .addComponent(jCheckBoxOSTypeLinux32)
                            .addComponent(jCheckBoxOSTypeLinux64)
                            .addComponent(jCheckBoxOSTypeWindows64))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)))
                .addGap(0, 0, 0))
        );

        jButtonBrowseForFireFoxExe.getAccessibleContext().setAccessibleName("jButtonBrowseForFireFoxExe");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNewDataLoopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewDataLoopActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonNewDataLoopActionPerformed

    private void jButtonNewBugActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewBugActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonNewBugActionPerformed
  
  public void setUniqueFileOptionView(String option)
  {
      if (option.equals("file"))
      {
         jRadioButtonUniquePerFile.setSelected(true);
         jRadioButtonUniqueGlobal.setSelected(false);
   
         
      }
      if (option.equals("global"))
      {
          jRadioButtonUniquePerFile.setSelected(false);
         jRadioButtonUniqueGlobal.setSelected(true);
      
         
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
public void setjRadioButtonUniqueGlobalSelected(boolean selected)
{
    jRadioButtonUniqueGlobal.setSelected(selected);
}
public boolean getjRadioButtonUniquePerFile()
{
    return jRadioButtonUniquePerFile.isSelected();
}
public boolean getjCheckBoxEmailReportSelected()
{
    return jCheckBoxEmailReport.isSelected();
}
public void addjCheckBoxIncludeScreenshotsActionListener(ActionListener listener)
{
    jCheckBoxIncludeScreenshots.addActionListener(listener);
}
public void addjCheckBoxEmailReportActionListener(ActionListener listener)
{
    jCheckBoxEmailReport.addActionListener(listener);
}
public void setjCheckBoxEmailReportSelected(boolean selected)
{
    jCheckBoxEmailReport.setSelected(selected);
}
public boolean getjCheckBoxOSTypeLinux64Selected()
{
    return jCheckBoxOSTypeLinux64.isSelected();
}
public void addjCheckBoxOSTypeLinux64ActionListener(ActionListener listener)
{
    jCheckBoxOSTypeLinux64.addActionListener(listener);
}

public boolean getjCheckBoxOSTypeLinux32Selected()
{
    return jCheckBoxOSTypeLinux32.isSelected();
}
public void addjCheckBoxOSTypeLinux32ActionListener(ActionListener listener)
{
    jCheckBoxOSTypeLinux32.addActionListener(listener);
}
public boolean getjCheckBoxOSTypeMacSelected()
{
    return jCheckBoxOSTypeMac.isSelected();
}
public void addjCheckBoxOSTypeMacActionListener(ActionListener listener)
{
    jCheckBoxOSTypeMac.addActionListener(listener);
}
public void setjCheckBoxOSTypeWindows32Selected(boolean selected)
{
    jCheckBoxOSTypeWindows32.setSelected(selected);
}
public boolean getjCheckBoxOSTypeWindows64Selected()
{
    return jCheckBoxOSTypeWindows64.isSelected();
}
public boolean getjCheckBoxOSTypeWindows32Selected()
{
    return jCheckBoxOSTypeWindows32.isSelected();
}
 public void addjCheckBoxOSTypeWindows64ActionListener(ActionListener listener)
 {
     jCheckBoxOSTypeWindows64.addActionListener(listener);
     
 }
   public void setjCheckBoxOSTypeLinux64Selected (boolean selected)
{
    jCheckBoxOSTypeLinux64.setSelected(selected);
}   
 public void setjCheckBoxOSTypeLinux32Selected (boolean selected)
{
    jCheckBoxOSTypeLinux32.setSelected(selected);
}   
 public void setjCheckBoxOSTypeMacSelected (boolean selected)
{
    jCheckBoxOSTypeMac.setSelected(selected);
}    
public void setjCheckBoxOSTypeWindows64Selected (boolean selected)
{
    jCheckBoxOSTypeWindows64.setSelected(selected);
}
public void setjCheckBoxShowReportSelected(boolean selected)
{
    jCheckBoxShowReport.setSelected(selected);
}
public void addjCheckBoxOSTypeWindows32ActionListener(ActionListener listener)
{
    jCheckBoxOSTypeWindows32.addActionListener(listener);
    
}
public boolean getjCheckBoxEmailReportFailSelected()
{
    return jCheckBoxEmailReportFail.isSelected();
    
}
public void addjCheckBoxEmailReportFailActionListener(ActionListener listener)
{
    jCheckBoxEmailReportFail.addActionListener(listener);
}
public void setjSpinnerSessionsEnabled(boolean enabled)
{
    jSpinnerSessions.setEnabled(enabled);
}
public boolean getjCheckBoxEnableMultiSessionSelected()
{
    return jCheckBoxEnableMultiSession.isSelected();
}
public boolean getjRadioButtonUniquePerFileSelected()
{
    return jRadioButtonUniquePerFile.isSelected();
}
public void addjCheckBoxEnableMultiSessionActionListener(ActionListener listener)
{
    jCheckBoxEnableMultiSession.addActionListener(listener);
}
public void setjCheckBoxEmailReportFailSelected(boolean selected)
{
    jCheckBoxEmailReportFail.setSelected(selected);
}

 
 public void setjRadioButtonUniquePerFileEnabled(boolean enabled)
{
    jRadioButtonUniquePerFile.setEnabled(enabled);
}   
public void setjRadioButtonUniqueGlobalEnabled(boolean enabled)
{
    jRadioButtonUniqueGlobal.setEnabled(enabled);
}
public boolean getjCheckBoxUniqueURLsSelected()
{
    return jCheckBoxUniqueURLs.isSelected();
}
public void addjCheckBoxUniqueURLsActionListener(ActionListener listener)
{
    jCheckBoxUniqueURLs.addActionListener(listener);
}
public boolean getjRadioButtonUniqueGlobalSelected()
{
    return jRadioButtonUniqueGlobal.isSelected();
}
public void setjRadioButtonUniquePerFileSelected(boolean selected)
{
    jRadioButtonUniquePerFile.setSelected(selected);
}
public void addjRadioButtonUniqueGlobalActionListener(ActionListener listener)
{
    jRadioButtonUniqueGlobal.addActionListener(listener);
}
public void addjRadioButtonUniquePerFileActionListener(ActionListener listener)
{
    jRadioButtonUniquePerFile.addActionListener(listener);
}
public boolean getjCheckBoxExitAfter()
{
    return jCheckBoxExitAfter.isSelected();
    
}
public void addjCheckBoxExitAfterActionListener(ActionListener listener)
{
    jCheckBoxExitAfter.addActionListener(listener);
}
public boolean getjCheckBoxPromptToClose()
{
    return jCheckBoxPromptToClose.isSelected();
}
public void setjCheckBoxExitAfterEnabled(boolean enabled)
{
    jCheckBoxExitAfter.setEnabled(enabled);
}
public void setjCheckBoxEmailReportEnabled(boolean enabled)    
{
    jCheckBoxEmailReport.setEnabled(enabled);
}

public void setjCheckBoxIncludeScreenshotsEnabled(boolean enabled)
{
    jCheckBoxIncludeScreenshots.setEnabled(enabled);
}

 public Boolean getjCheckBoxShowReport()
 {
     return jCheckBoxShowReport.isSelected();
     
 }
            public void addjCheckBoxShowReportActionListener(ActionListener listener)
    {
        jCheckBoxShowReport.addActionListener(listener);
    }
    public void addjCheckBoxPromptToCloseActionListener(ActionListener listener)
    {
        jCheckBoxPromptToClose.addActionListener(listener);
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
public void removejButtonPlaceStoredVariableActionListener()
{
  ActionListener[] theseListeners = jButtonPlaceStoredVariable.getActionListeners();
  if (theseListeners.length>0)
  {
       jButtonPlaceStoredVariable.removeActionListener(theseListeners[0]); 
  }
 
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
   public void enablejButtonDoStuff(boolean enable_it)
   {
       jButtonDoStuff.setEnabled(enable_it);
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
        public void addjSpinnerEcTimeoutTimeChangeListener(ChangeListener listener)
   {
       jSpinnerEcTimeout.addChangeListener(listener);
   }
     public void addjSpinnerWaitTimeChangeListener(ChangeListener listener)
   {
       jSpinnerWaitTime.addChangeListener(listener);
   }
         public void addjSpinnerSessionsChangeListener(ChangeListener listener)
   {
       jSpinnerSessions.addChangeListener(listener);
   }
 public void addjButtonClearUniqueListActionListener(ActionListener listener)
{
    jButtonClearUniqueList.addActionListener(listener);
}
  
 public void clearEmailSettings()
 {
     setSMTPHostname("");
     setEmailLoginName("");
     setEmailPassword("");
     setEmailTo("");
     setEmailFrom("");
     setSubject("");
     
 }
    public void setEmailSettings(SeleniumTestToolData in_appdata)
    {
          String smtp_hostname = in_appdata.SMTPHostName;
   String login_name = in_appdata.EmailLoginName;
   String password = in_appdata.EmailPassword;
   String to = in_appdata.EmailTo;
   String from = in_appdata.EmailFrom;
   String subject = in_appdata.EmailSubject;
 
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
    public void setExitAfterView (Boolean exitafter)
    {
      
        jCheckBoxExitAfter.setSelected(exitafter);
        if (exitafter)
        {
         jCheckBoxShowReport.setSelected(false);
    
        }
    }
    public void setShowReportView (Boolean showreport)
    {
      
        jCheckBoxShowReport.setSelected(showreport);
        if (showreport==false)
        {
    
      jCheckBoxIncludeScreenshots.setEnabled(false);
   
        }
        else
        {
        jCheckBoxEmailReportFail.setSelected(false);
      
        jCheckBoxEmailReport.setSelected(false);
       
      
        jCheckBoxIncludeScreenshots.setEnabled(true);
        jCheckBoxExitAfter.setSelected(false);
      
        }
    }
        public void setjSpinnerEcTimeout(int WaitTime)
    {
          this.jSpinnerEcTimeout.setValue(WaitTime);
    }
    public void setjSpinnerWaitTime(int WaitTime)
    {
          this.jSpinnerWaitTime.setValue(WaitTime);
    }
    public void setjSpinnerSessions(int Sessions)
    {
         jSpinnerSessions.setValue(Sessions);
    }
    public void setIncludeScreenshotsView(Boolean includescreenshots)
    {
    
        jCheckBoxIncludeScreenshots.setSelected(includescreenshots);
    }
 
    public void setEmailReportView(Boolean emailreport)
    {
        jCheckBoxEmailReport.setSelected(emailreport);
     
        if (emailreport==true)
        {
             jCheckBoxIncludeScreenshots.setEnabled(false);
 
            jCheckBoxShowReport.setSelected(false);
        
                    jCheckBoxIncludeScreenshots.setSelected(false);
      jCheckBoxIncludeScreenshots.setEnabled(false);
   
            jCheckBoxEmailReportFail.setSelected(false);
        }
     
    }
    public void setIncludeScreenShotView (Boolean include_screenshots)
    {
      jCheckBoxIncludeScreenshots.setSelected(include_screenshots);
    }
            
        public void setEmailReportFailView (Boolean emailreportfail)
    {
      
        jCheckBoxEmailReportFail.setSelected(emailreportfail);
         if (emailreportfail==true)
        {
             jCheckBoxIncludeScreenshots.setEnabled(false);
   
              jCheckBoxShowReport.setSelected(false);
        
            jCheckBoxEmailReport.setSelected(false);
       
        }
         else
         {
      
      
         
         }
    }
        public void setTargetBrowserView (String targetbrowser)
        {   
            //legacy stuff
            if ("Firefox-Marionette".equals(targetbrowser))
            {
                targetbrowser = "Firefox";
            }
            
            jComboBoxTargetBrowser.setSelectedItem(targetbrowser);   
       
            
            switch (targetbrowser)
            {
                case "Firefox":
                    jButtonBrowseForFireFoxExe.setEnabled(true);
                     setOSTypeActive(true);
                    break;
//                case "Edge":
//                    jButtonBrowseForFireFoxExe.setEnabled(false);
//                    setOSTypeActive(false);
//                    break;
                case "Internet Explorer-32":
                    jButtonBrowseForFireFoxExe.setEnabled(false);
                    break;
                case "Internet Explorer-64":
                    jButtonBrowseForFireFoxExe.setEnabled(false);
                    break;  
                case "Chrome":
                    jButtonBrowseForFireFoxExe.setEnabled(true);
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
        public void setOSTypeView(String OSType)
        {
            if ("Windows".equals(OSType))
            {
                OSType = "Windows32";
            }
          
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

 public void setUniqueListView(boolean unique)
 {
  
    jCheckBoxUniqueURLs.setSelected(unique);
    jRadioButtonUniquePerFile.setEnabled(unique);
    jRadioButtonUniqueGlobal.setEnabled(unique);
 
 }
 
 public boolean getUniqueList()
 {
    
         boolean checked = this.jCheckBoxUniqueURLs.isSelected();
   
    return checked;  
 }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JTextFieldProgress;
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
    private javax.swing.JCheckBox jCheckBoxEnableMultiSession;
    private javax.swing.JCheckBox jCheckBoxExitAfter;
    private javax.swing.JCheckBox jCheckBoxIncludeScreenshots;
    private javax.swing.JCheckBox jCheckBoxOSTypeLinux32;
    private javax.swing.JCheckBox jCheckBoxOSTypeLinux64;
    private javax.swing.JCheckBox jCheckBoxOSTypeMac;
    private javax.swing.JCheckBox jCheckBoxOSTypeWindows32;
    private javax.swing.JCheckBox jCheckBoxOSTypeWindows64;
    private javax.swing.JCheckBox jCheckBoxPromptToClose;
    private javax.swing.JCheckBox jCheckBoxShowReport;
    private javax.swing.JCheckBox jCheckBoxUniqueURLs;
    private javax.swing.JComboBox<String> jComboBoxStoredVariables;
    private javax.swing.JComboBox jComboBoxTargetBrowser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabelTasks;
    private javax.swing.JRadioButton jRadioButtonUniqueGlobal;
    private javax.swing.JRadioButton jRadioButtonUniquePerFile;
    private javax.swing.JSpinner jSpinnerEcTimeout;
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
