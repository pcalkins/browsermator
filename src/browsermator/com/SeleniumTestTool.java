
package browsermator.com;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;



public class SeleniumTestTool extends JInternalFrame {

String URL;
String filename;
ArrayList<Procedure> BugArray = new ArrayList();
ArrayList<ProcedureView> BugViewArray = new ArrayList();
    ArrayList<String> AllFieldValues;
  JPanel BugPanel;
  Thread ActionThread;
  Boolean AllTestsPassed;
  String report;
  Boolean ShowReport;
  Boolean EmailReport;
  Boolean EmailReportFail;
  Boolean ExitAfter;
  Boolean PromptToClose;
  Boolean changes;
  String TargetBrowser;
  String OSType;
  int WaitTime;
 
  
  public SeleniumTestTool(String filename)
  {
   // super("Selenium Test Tool");
  this.TargetBrowser = "Firefox";
  this.OSType = "Windows";

  this.changes = false;
  this.PromptToClose = false;
  this.ShowReport = false;
  this.filename = filename;
  this.URL = "";
  this.BugPanel = new JPanel();
 
  this.AllFieldValues = new ArrayList<>();

  
      initComponents();

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
         jCheckBoxOSTypeWindows.addActionListener(new java.awt.event.ActionListener() {
           @Override
             public void actionPerformed(java.awt.event.ActionEvent evt) {
              jCheckBoxOSTypeWindowsActionPerformed(evt);
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
    int fallbackValue = 3;


    
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
public void setProperties (String filename)
    {
   
    this.setTitle("Browsermator - " + filename); 
    
    }
   
    @SuppressWarnings("unchecked")


 public void setRunActionsButtonName(String newtext)
 {
     jButtonDoStuff.setText(newtext);
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


// this.BugPanel.setBorder(BorderFactory.createLineBorder(Color.black));

int bugindex = 0;
    for (ProcedureView BV : BugViewArray)
      {
   
    //    BV.JPanelBug.setBorder(BorderFactory.createLineBorder(Color.black));
          BV.SetIndex(bugindex);
          
          this.BugPanel.add(BV.JPanelBug);
     JPanel ActionAdderPanel = new JPanel();
  //   ActionAdderPanel.add(BV.JPanelActionAdders);
     
          this.BugPanel.add(ActionAdderPanel);
  
   GridBagLayout ActionLayout = new GridBagLayout();
   GridBagConstraints ActionConstraints = new GridBagConstraints();
            JPanel ActionPanel = new JPanel();
    //  FlowLayout ActionLayout = new ModifiedFlowLayout(1);
            
      ActionPanel.setLayout(ActionLayout); 
      
     ActionConstraints.fill = GridBagConstraints.NONE;
     ActionConstraints.anchor = GridBagConstraints.WEST;
    
    // AddToGrid(JLabelBugIndex, 1, 2, 1, 1);
      int actionindex = 0;
      for (ActionView AV : BV.ActionsViewList )
        {

       
        AV.SetIndexes(bugindex, actionindex);
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



  this.setProperties(this.filename);
     this.MainScrollPane.setViewportView(this.BugPanel);

     this.MainScrollPane.setVisible(true);
  this.revalidate();
  
// this.changes = true;
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
          
     newbugview.ActionScrollPane.setSize(new Dimension(1024, 36*actionindex+40));
   
          }
      
      newbugview.ActionScrollPane.setVisible(true);

       newbugview.ActionScrollPane.setViewportView(ActionPanel);

     }
 public void RunActions()
 {
    RunAllTests REFSYNCH = new RunAllTests(this);
    REFSYNCH.execute();
    
    this.report = REFSYNCH.report;
 }
 public void RunSingleTest(Procedure bugtorun, ProcedureView thisbugview)
 {
      RunASingleTest REFSYNCH = new RunASingleTest(bugtorun, thisbugview, this.TargetBrowser, this.OSType);
    REFSYNCH.execute();
 }

 

        public void AddNewBug()
        {
         Procedure newbug = new Procedure();
         ProcedureView newbugview = new ProcedureView();
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
         DataLoop newdataloop = new DataLoop();
         DataLoopView newdataloopview = new DataLoopView();
         newdataloopview.setJTableSource(CSVFile.getAbsolutePath());
         newdataloop.setDataFile(CSVFile.getAbsolutePath());
         BugArray.add(newdataloop);
         BugViewArray.add(newdataloopview);
         newdataloop.index = BugArray.size();
         newdataloopview.index = BugViewArray.size();
        AddNewHandlers(this, newdataloopview, newdataloop);
        AddLoopHandlers(this, newdataloopview, newdataloop);
        UpdateDisplay();
 JScrollBar vertical = this.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );

        }
      public void AddLoopHandlers (SeleniumTestTool Window, DataLoopView newbugview, DataLoop newbug) 
      {
            
            newbugview.addJButtonBrowseForDataFileActionListener((ActionEvent evt) -> {
             File chosenCSVFile = ChangeCSVFile();
   if (chosenCSVFile!=null)
   {
  
   newbugview.setJTableSource(chosenCSVFile.getAbsolutePath());
   newbug.setDataFile(chosenCSVFile.getAbsolutePath());
   UpdateDisplay();
   }
          });
      
      }
      public File ChangeCSVFile()
      {
   
  final JFileChooser CSVFileChooser = new JFileChooser();

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


int returnVal = CSVFileChooser.showOpenDialog(this);

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
              ClickXPATHAction thisActionToAdd = new ClickXPATHAction("", false);
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
         
}

   public File BrowseForJSFileAction ()
   {
           final JFileChooser CSVFileChooser = new JFileChooser();

 FileNameExtensionFilter filefilter = new FileNameExtensionFilter("Javascript file (*.js)","js");

    CSVFileChooser.setFileFilter(filefilter);

int returnVal = CSVFileChooser.showOpenDialog(this);

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
 
   public void MoveAction (SeleniumTestTool Window, Procedure thisBug, ProcedureView thisBugView, int toMoveIndex, int Direction)
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
   //  GridBagConstraints ActionConstraints = new GridBagConstraints();
         
      
   //  ActionConstraints.fill = GridBagConstraints.NONE;
   //  ActionConstraints.anchor = GridBagConstraints.WEST;            

   //     AV.SetIndexes(newbugview.index, actionindex);
   //     ActionConstraints.gridx = 1;
   //      ActionConstraints.gridy = SwapIndex;
   //      ActionConstraints.gridwidth = 1;
   //      ActionConstraints.gridheight = 1;
   //     JPanel theActionPanel = thisBugView.ActionScrollPanel;
    
 //      Component theddseaction = thisBugView.ActionScrollPane.getViewport().getView();
 //    thisBugView.ActionScrollPane.getViewport().remove(theddseaction.getComponentAt(e.getPoint()));
 //    thisBugView.ActionScrollPane.getViewport().revalidate();
 //    thisBugView.ActionScrollPane.getViewport().repaint();
 //    thisBugView.ActionScrollPane.revalidate();
 //    thisBugView.ActionScrollPane.repaint();
 // int test=0;
      


      
  
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
  if (atIndex != 0)
  {
  Point visible_location = thisBugView.ActionsViewList.get(atIndex-1).JPanelAction.getLocation();
  Dimension visible_size = thisBugView.ActionsViewList.get(atIndex-1).JPanelAction.getSize();
  Rectangle visible = new Rectangle (visible_location.x, visible_location.y, visible_size.width, visible_size.height);
 
  thisBugView.ActionsViewList.get(atIndex-1).JPanelAction.scrollRectToVisible(visible);     
  }
 
  this.changes=true;
   }
   public void DeleteBug (int BugIndex)
   {
  
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
        jLabel2 = new javax.swing.JLabel();
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
        jCheckBoxOSTypeWindows = new javax.swing.JCheckBox();
        jCheckBoxOSTypeMac = new javax.swing.JCheckBox();
        jCheckBoxOSTypeLinux32 = new javax.swing.JCheckBox();
        jCheckBoxOSTypeLinux64 = new javax.swing.JCheckBox();
        jButtonClearEmailSettings = new javax.swing.JButton();
        jButtonNewDataLoop = new javax.swing.JButton();
        jButtonBrowseForFireFoxExe = new javax.swing.JButton();

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

        jSpinnerWaitTime.setModel(new javax.swing.SpinnerNumberModel(3, 0, 99, 1));
        jSpinnerWaitTime.setMinimumSize(new java.awt.Dimension(41, 21));
        jSpinnerWaitTime.setPreferredSize(new java.awt.Dimension(41, 21));

        jLabel1.setText("Timeout* (seconds)");

        jLabel2.setText("*if set to zero all tests will fail. Need to wait for page to load.");

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

        jComboBoxTargetBrowser.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Firefox", "Internet Explorer-32", "Internet Explorer-64", "Chrome", "Silent Mode (HTMLUnit)" }));

        jLabel9.setText("<HTML>*Additional configuration is needed for IE (this program does not adjust the registry or browser security zones settings).<br/>**HTMLUnit's Javascript engine is a bit quirky.<br/>See http://www.seleniumhq.org/docs/03_webdriver.jsp for details</HTML> ");

        jCheckBoxOSTypeWindows.setText("Windows");
        jCheckBoxOSTypeWindows.setEnabled(false);

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
                    .addComponent(MainScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonNewBug, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonNewDataLoop))
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
                                            .addComponent(jCheckBoxShowReport)
                                            .addComponent(jCheckBoxEmailReport))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCheckBoxExitAfter)
                                            .addComponent(jCheckBoxEmailReportFail, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jCheckBoxPromptToClose)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel8)
                                                .addGap(18, 18, 18)
                                                .addComponent(jComboBoxTargetBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jButtonBrowseForFireFoxExe))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCheckBoxOSTypeWindows)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCheckBoxOSTypeMac)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckBoxOSTypeLinux32)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckBoxOSTypeLinux64))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(8, 8, 8)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(22, 22, 22)
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
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButtonClearEmailSettings)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jTextFieldSMTPHostName)
                                                .addComponent(jTextFieldEmailLoginName)
                                                .addComponent(jTextFieldEmailTo)
                                                .addComponent(jTextFieldEmailFrom)
                                                .addComponent(jTextFieldSubject, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                                                .addComponent(jTextFieldEmailPassword)))))))
                        .addGap(156, 156, 156))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonNewDataLoop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonNewBug, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelTHISSITEURL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MainScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCheckBoxShowReport)
                                    .addComponent(jCheckBoxPromptToClose))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jCheckBoxEmailReport)
                                    .addComponent(jCheckBoxEmailReportFail)))
                            .addComponent(jButtonDoStuff, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jSpinnerWaitTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1))
                            .addComponent(jCheckBoxExitAfter))
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(jComboBoxTargetBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonBrowseForFireFoxExe, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCheckBoxOSTypeWindows)
                            .addComponent(jCheckBoxOSTypeMac)
                            .addComponent(jCheckBoxOSTypeLinux32)
                            .addComponent(jCheckBoxOSTypeLinux64))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonClearEmailSettings))))
        );

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
  // this.changes=true;
  }
  
   private void jCheckBoxExitAfterActionPerformed(ActionEvent evt)
  {
  this.ExitAfter = jCheckBoxExitAfter.isSelected();
  // this.changes=true;
  }
  private void jCheckBoxEmailReportActionPerformed(ActionEvent evt)
  {
   this.EmailReport = jCheckBoxEmailReport.isSelected();
  if (this.EmailReport==true)
  {
      jCheckBoxEmailReportFail.setSelected(false);
      this.EmailReportFail = false;
  }
  // this.changes=true;
  }
  private void jCheckBoxEmailReportFailActionPerformed(ActionEvent evt)
  {
     this.EmailReportFail = jCheckBoxEmailReportFail.isSelected();
  if (this.EmailReportFail==true)
  {
     jCheckBoxEmailReport.setSelected(false);
     this.EmailReport = false;
  }
  // this.changes=true;
  }
 private void jCheckBoxOSTypeWindowsActionPerformed(ActionEvent evt)
 {
     
     if (jCheckBoxOSTypeWindows.isSelected())
     {
         this.OSType = "Windows";
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
     jCheckBoxOSTypeWindows.setSelected(false);
     jCheckBoxOSTypeLinux32.setSelected(false);
     jCheckBoxOSTypeLinux64.setSelected(false);
     }
 }
    private void jCheckBoxOSTypeLinux32ActionPerformed(ActionEvent evt)
 {
     
     if (jCheckBoxOSTypeLinux32.isSelected())
     {
         this.OSType = "Linux32";
     jCheckBoxOSTypeMac.setSelected(false);
     jCheckBoxOSTypeWindows.setSelected(false);
     jCheckBoxOSTypeLinux64.setSelected(false);
     }
 }
     private void jCheckBoxOSTypeLinux64ActionPerformed(ActionEvent evt)
 {
     
     if (jCheckBoxOSTypeLinux64.isSelected())
     {
         this.OSType = "Linux64";
     jCheckBoxOSTypeMac.setSelected(false);
     jCheckBoxOSTypeLinux32.setSelected(false);
     jCheckBoxOSTypeWindows.setSelected(false);
     }

 }
public void setOSTypeActive (Boolean Active)
{
    if (Active)
    {
    jCheckBoxOSTypeWindows.setEnabled(true);
    jCheckBoxOSTypeMac.setEnabled(true);
    jCheckBoxOSTypeLinux32.setEnabled(true);
    jCheckBoxOSTypeLinux64.setEnabled(true);
    }
    else
    {
    jCheckBoxOSTypeWindows.setEnabled(false);
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
public void addjButtonNewBugActionListener(ActionListener listener) {
     jButtonNewBug.addActionListener(listener);
  }
public void addjButtonNewDataLoopActionListener(ActionListener listener) {
    jButtonNewDataLoop.addActionListener(listener);  
  }
   public void addjButtonDoStuffActionListener(ActionListener listener) {
       jButtonDoStuff.addActionListener(listener);
   }
  public void addjButtonClearEmailSettingsListener(ActionListener listener) {
       jButtonClearEmailSettings.addActionListener(listener);
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
    }
    public void setShowReport (Boolean showreport)
    {
        this.ShowReport = showreport;
        jCheckBoxShowReport.setSelected(showreport);
    }
    public void setEmailReport (Boolean emailreport)
    {
        jCheckBoxEmailReport.setSelected(emailreport);
        this.EmailReport = emailreport;
        if (emailreport==true)
        {
            jCheckBoxEmailReportFail.setSelected(false);
        }
    }
        public void setEmailReportFail (Boolean emailreportfail)
    {
        this.EmailReportFail = emailreportfail;
        jCheckBoxEmailReportFail.setSelected(emailreportfail);
         if (emailreportfail==true)
        {
            jCheckBoxEmailReport.setSelected(false);
        }
    }
        public void setTargetBrowser (String targetbrowser)
        {   
            
            jComboBoxTargetBrowser.setSelectedItem(targetbrowser);   
            this.TargetBrowser = targetbrowser;
        }
        public String getTargetBrowser ()
        {
            return jComboBoxTargetBrowser.getSelectedItem().toString();
        }
        public String getOSType()
        {
            if (jCheckBoxOSTypeWindows.isSelected())
            {
                return "Windows";
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
           return "Windows";
        }
        public void setOSType(String OSType)
        {
            this.OSType = OSType;
            switch (OSType)
            {
                case "Windows":
                jCheckBoxOSTypeWindows.setSelected(true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JScrollPane MainScrollPane;
    private javax.swing.JButton jButtonBrowseForFireFoxExe;
    private javax.swing.JButton jButtonClearEmailSettings;
    private javax.swing.JButton jButtonDoStuff;
    private javax.swing.JButton jButtonNewBug;
    private javax.swing.JButton jButtonNewDataLoop;
    private javax.swing.JCheckBox jCheckBoxEmailReport;
    private javax.swing.JCheckBox jCheckBoxEmailReportFail;
    private javax.swing.JCheckBox jCheckBoxExitAfter;
    private javax.swing.JCheckBox jCheckBoxOSTypeLinux32;
    private javax.swing.JCheckBox jCheckBoxOSTypeLinux64;
    private javax.swing.JCheckBox jCheckBoxOSTypeMac;
    private javax.swing.JCheckBox jCheckBoxOSTypeWindows;
    private javax.swing.JCheckBox jCheckBoxPromptToClose;
    private javax.swing.JCheckBox jCheckBoxShowReport;
    private javax.swing.JComboBox jComboBoxTargetBrowser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelTHISSITEURL;
    private javax.swing.JSpinner jSpinnerWaitTime;
    private javax.swing.JTextField jTextFieldEmailFrom;
    private javax.swing.JTextField jTextFieldEmailLoginName;
    private javax.swing.JPasswordField jTextFieldEmailPassword;
    private javax.swing.JTextField jTextFieldEmailTo;
    private javax.swing.JTextField jTextFieldSMTPHostName;
    private javax.swing.JTextField jTextFieldSubject;
    // End of variables declaration//GEN-END:variables
}
