/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.event.InternalFrameEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author pcalkins
 */
public class OpenFileThread extends SwingWorker<String, Integer>{
File file;
ArrayList<SeleniumTestTool> MDIClasses;
STAppController mainApp;
int calling_MDI_Index;
boolean isFlatten;
boolean RunIt;
boolean fromCloud=false;

    public OpenFileThread(STAppController mainApp, File file, ArrayList<SeleniumTestTool> MDIClasses, int calling_MDI_Index, boolean isFlatten, boolean RunIt)
{
  this.isFlatten = isFlatten;
  this.mainApp = mainApp;
  this.file = file;
  this.MDIClasses = MDIClasses;
  this.calling_MDI_Index = calling_MDI_Index;
  this.RunIt = RunIt;
}
      public OpenFileThread(STAppController mainApp, File file, ArrayList<SeleniumTestTool> MDIClasses, int calling_MDI_Index, boolean isFlatten, boolean RunIt, boolean fromCloud)
{
    this.fromCloud = fromCloud;
  this.isFlatten = isFlatten;
  this.mainApp = mainApp;
  this.file = file;
  this.MDIClasses = MDIClasses;
  this.calling_MDI_Index = calling_MDI_Index;
  this.RunIt = RunIt;
}
@Override 
public String doInBackground()
 {
     mainApp.Navigator.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try {
        int MDI_CLASS_INDEX;
           MDI_CLASS_INDEX = OpenFile(file, MDIClasses);
         if (MDI_CLASS_INDEX>=0)
     {
           mainApp.DisplayWindow(MDI_CLASS_INDEX);
     }
       }
       catch (IOException | ClassNotFoundException ex) {
          System.out.println("error opening file: " + ex.toString());
       } 
    
 return "done";
 }
@Override
 protected void done()
 {
  mainApp.Navigator.setCursor(Cursor.getDefaultCursor()); 
  if (isFlatten)
  {
  MDIClasses.get(calling_MDI_Index).setFlattenFileButtonName ("Flatten to New File");
  }
   if (RunIt)
  {
   int current_MDI_Index = mainApp.GetCurrentWindow();
    if (current_MDI_Index>=0) {    MDIClasses.get(current_MDI_Index).RunActions(); }   
  } 
     
 }
 @Override
 protected void process ( List <Integer> bugindex)
 {
     
 }
    public int OpenFile (File file, ArrayList<SeleniumTestTool> MDIClasses) throws FileNotFoundException, IOException, ClassNotFoundException
    {
   
  
        
if(!file.exists()) { 
 return -10;
 
}
else
{
    String full_filename;
    if (this.fromCloud)
    {
        if (MDIClasses.size()>0)
        {
       full_filename = file.getName() + "-untitled" + MDIClasses.size();
        }
        else
        {
          full_filename = file.getName() + "-untitled";   
        }
        }
    else
    {
 full_filename = file.getAbsoluteFile().toString();
    }

int MDI_Index = -1;


              Boolean PromptForSameFileName = false;
     String filealreadyopen ="";
     for (SeleniumTestTool thisfile: MDIClasses)
     {
        String twoslashes = "\\" + "\\";
        
         String thisfilename = thisfile.filename.replace(twoslashes, "\\");
        String browsedfile = file.getAbsolutePath();
        
                
         if (browsedfile.equals(thisfilename))
         {   
          filealreadyopen = browsedfile;
             PromptForSameFileName = true; }
     }
     if (PromptForSameFileName==false)
    {

SeleniumTestTool STAppFrame;
Document doc=null;
try
{

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
String file_path = file.getAbsolutePath();

doc = builder.parse(file_path);

 
}
catch (ParserConfigurationException | SAXException | IOException e)
{
    System.out.println("DocumentBuilder error:" + e.toString());
   
}
    
finally 
{
 
    STAppFrame = BuildNewWindow(doc);
    STAppFrame.UpdateDisplay();
   STAppFrame.setVisible(true);
  STAppFrame.setProperties(full_filename);
  if (this.fromCloud)
  {
      
  }
  else
  {
   mainApp.Navigator.addRecentFile(full_filename);
  }
   MDIClasses.add(STAppFrame);
MDI_Index =  MDIClasses.size()-1;
STAppFrame.AllFieldValues.clear();
STAppFrame.AllFieldValues.add(STAppFrame.OSType);
STAppFrame.AllFieldValues.add(STAppFrame.TargetBrowser);
String stringWaitTime = String.valueOf(STAppFrame.GetWaitTime());
STAppFrame.AllFieldValues.add(stringWaitTime);
STAppFrame.AllFieldValues.add(STAppFrame.getSMTPHostname());
STAppFrame.AllFieldValues.add(STAppFrame.getEmailFrom());
STAppFrame.AllFieldValues.add(STAppFrame.getEmailLoginName());
STAppFrame.AllFieldValues.add(STAppFrame.getEmailPassword());
STAppFrame.AllFieldValues.add(STAppFrame.getEmailTo());
STAppFrame.AllFieldValues.add(STAppFrame.getSubject());
String thisbool = "false";
if (STAppFrame.getEmailReport())
{
    thisbool = "true";
}
STAppFrame.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppFrame.getEmailReportFail())
{
    thisbool = "true";
}
STAppFrame.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppFrame.getExitAfter())
{
    thisbool = "true";
}
STAppFrame.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppFrame.getPromptToClose())
{
    thisbool = "true";
}
STAppFrame.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppFrame.getShowReport())
{
    thisbool = "true";
}
STAppFrame.AllFieldValues.add(thisbool);
for (Procedure thisproc: STAppFrame.BugArray)
{
    STAppFrame.AllFieldValues.add(thisproc.BugTitle);

    for (Action thisact: thisproc.ActionsList)
    {
        String checkingboolval1 = "false";
        STAppFrame.AllFieldValues.add(thisact.Variable1);

        STAppFrame.AllFieldValues.add(thisact.Variable2);
        if (thisact.BoolVal1)
        {
            checkingboolval1 = "true";
        }
        STAppFrame.AllFieldValues.add(checkingboolval1);
        
    }
}


}

 return MDI_Index;
    }
   else
     {
  
    JOptionPane.showMessageDialog (null, filealreadyopen + " is already open", "File is open", JOptionPane.INFORMATION_MESSAGE);
                               
 
 return -1;
     }
}

    }
  
  public SeleniumTestTool BuildNewWindow(Document doc)
  {
   
    
   NamedNodeMap NewAttributes = doc.getElementsByTagName("BrowserMatorWindow").item(0).getAttributes(); 
   
   String filename_read = NewAttributes.getNamedItem("Filename").getNodeValue();
   SeleniumTestTool STAppFrame = new SeleniumTestTool(filename_read);
   
   STAppFrame.filename = filename_read;
      STAppFrame.setClosable(true);
  STAppFrame.setMaximizable(true);
  STAppFrame.setTitle("Browsermator - " + STAppFrame.filename);
  STAppFrame.setResizable(true);
  STAppFrame.setSize(1024, 800);
   STAppFrame.setClosable(true);
  STAppFrame.setMaximizable(true);

    STAppFrame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
     @Override 
     public void internalFrameClosing(InternalFrameEvent e) {
    
    
      int closed =  mainApp.CheckToSaveChanges(STAppFrame, false);
           
      if (closed==1)
      {
      STAppFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
       MDIClasses.remove(MDIClasses.size()-1); 
       STAppFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      }
     
      }
    });
  NodeList FileSettingsNode = doc.getElementsByTagName("FileSettings");
 
  String thisSettingsNodeName;
  String thisSettingsNodeValue;
  String stShowReport="false";
  String stEmailReport = "false";
  String stEmailReportFail = "false";
  String stExitAfter = "false";
  String SMTPHostname = "";
  String EmailLoginName = "";
  String stPromptToClose = "false";
  String TargetBrowser = "Firefox";
  String WaitTime = "3";
  String OSType = "Windows";
  String EmailPassword = "";
  String unepassword = "";
  String EmailTo = "";
  String EmailFrom = "";
  String EmailSubject = "";
 if (FileSettingsNode!=null)
 {
try
{
    NodeList SettingsNodes = FileSettingsNode.item(0).getChildNodes();


    for (int k = 0; k<SettingsNodes.getLength(); k++)
    {
   thisSettingsNodeName = SettingsNodes.item(k).getNodeName();
   thisSettingsNodeValue = SettingsNodes.item(k).getTextContent();
    switch(thisSettingsNodeName)
    {
        case "ShowReport":
            stShowReport = thisSettingsNodeValue;
           Boolean ShowReport = false;
   if (stShowReport.equals("true"))
   {
       ShowReport = true;
   }
   STAppFrame.setShowReport(ShowReport);
            break;
        
        case "EmailReport":
   stEmailReport = thisSettingsNodeValue;
   Boolean EmailReport = false;
    if (stEmailReport.equals("true"))
   {
       EmailReport = true;
   }
       STAppFrame.setEmailReport(EmailReport);
            break;
      
        case "EmailReportFail":
   stEmailReportFail = thisSettingsNodeValue;
   Boolean EmailReportFail = false;
    if (stEmailReportFail.equals("true"))
   {
       EmailReportFail = true;
   }
       STAppFrame.setEmailReport(EmailReportFail);
            break;
         
        case "ExitAfter":
   stExitAfter = thisSettingsNodeValue;
   Boolean ExitAfter = false;
    if (stExitAfter.equals("true"))
   {
       ExitAfter = true;
   }
       STAppFrame.setEmailReport(ExitAfter);
            break;        
       
        case "SMTPHostname":
 SMTPHostname = thisSettingsNodeValue;
      STAppFrame.setSMTPHostname(SMTPHostname);
            break;  

        case "EmailLoginName":
 EmailLoginName = thisSettingsNodeValue;
      STAppFrame.setEmailLoginName(EmailLoginName);
            break;  
        
        case "PromptToClose":
 stPromptToClose = thisSettingsNodeValue;
   Boolean PromptToClose = false;
    if (stPromptToClose.equals("true"))
   {
       PromptToClose = true;
   }
       STAppFrame.setPromptToClose(PromptToClose);
            break; 
          
        case "TargetBrowser":
 TargetBrowser = thisSettingsNodeValue;
      STAppFrame.setTargetBrowser(TargetBrowser);
            break;   
            
       case "WaitTime":
 WaitTime = thisSettingsNodeValue;
 int intWaitTime = Integer.parseInt(WaitTime);
      STAppFrame.setWaitTime(intWaitTime);
            break;   
              
       case "OSType":
 OSType = thisSettingsNodeValue;
      STAppFrame.setOSType(OSType);
            break;   
     
       case "EmailPassword":
 EmailPassword = thisSettingsNodeValue;
  try
   {
   unepassword = Protector.decrypt(EmailPassword);
   }
   catch (GeneralSecurityException | IOException e)
           {
   //            System.out.println("decrypt error" + e.toString());
           }
      STAppFrame.setEmailPassword(unepassword);
            break;  
      
       case "EmailTo":
 EmailTo = thisSettingsNodeValue;
      STAppFrame.setEmailTo(EmailTo);
            break;    
      
       case "EmailFrom":
 EmailFrom = thisSettingsNodeValue;
      STAppFrame.setEmailFrom(EmailFrom);
            break;   
       
       case "EmailSubject":
 EmailSubject = thisSettingsNodeValue;
      STAppFrame.setSubject(EmailSubject);
            break; 
    }

    }

    }
catch (Exception e)
        {
           System.out.println ("exception reading file settings: " + e.toString());
          
        }
 }

try
{
   NodeList ProcedureList = doc.getElementsByTagName("Procedure");
   
for (int i = 0; i < ProcedureList.getLength(); ++i)
{
    
    
    
   
    Element Procedure = (Element) ProcedureList.item(i);
   
    String DataFile = Procedure.getAttribute("DataLoopFile");
    if (!"".equals(DataFile))
    {
        
        File DataFile_file = new File(DataFile);
       
            STAppFrame.AddNewDataLoop(DataFile_file);
    
    }
    else
    {
     STAppFrame.AddNewBug();    
    }
    
    STAppFrame.BugArray.get(i).BugTitle = Procedure.getAttribute("Title");
    STAppFrame.BugViewArray.get(i).JTextFieldBugTitle.setText(Procedure.getAttribute("Title"));
    STAppFrame.BugArray.get(i).BugURL = Procedure.getAttribute("URL");
    
    String stPass = Procedure.getAttribute("Pass");
    Boolean Pass = false;
    if (stPass.equals("true"))
    {
        Pass = true;
    }
    STAppFrame.BugArray.get(i).Pass = Pass;

    
    
 
    
    NodeList ActionsList = Procedure.getElementsByTagName("Action");
  
    for (int j = 0; j <ActionsList.getLength(); j++)
    {
   Element Action = (Element) ActionsList.item(j);
   NodeList ActionNodes = Action.getChildNodes();
   String thisActionNodeName = "none";
   String thisActionNodeValue = "none";
   
   String Variable1 = "";
   String Variable2 = "";
   String LOCKED = "false";
   String BoolVal1 = "false";
    String TimeOfTest;
    String ActionType = "none";
    String ActionIndex;
    String ActionPass;
    String Password = "";
    
    
   Boolean RealBoolVal1 = false;
    for (int k = 0; k<ActionNodes.getLength(); k++)
    {
   thisActionNodeName = ActionNodes.item(k).getNodeName();
   thisActionNodeValue = ActionNodes.item(k).getTextContent();
   
    switch(thisActionNodeName)
    {
        case "Pass":
            ActionPass = thisActionNodeValue;
            break;
        case "ActionIndex":
            ActionIndex = thisActionNodeValue;
            break;
        case "Type":
            ActionType = thisActionNodeValue;
            break;
        case "Variable1":
            Variable1 = thisActionNodeValue;
            break;
        case "Variable2":
            Variable2 = thisActionNodeValue;
            break;
        case "BoolVal1":
            BoolVal1 = thisActionNodeValue;
            if (BoolVal1.equals("true"))
                    {
                    RealBoolVal1 = true;
                    }
           break;
        case "LOCKED":
            LOCKED = thisActionNodeValue;
            break;
       

        case "TimeOfTest":
            TimeOfTest = thisActionNodeValue;
            break;
    }  
                
    } 
    
   Procedure NewProcedure = STAppFrame.BugArray.get(i);
   ProcedureView NewProcedureView = STAppFrame.BugViewArray.get(i);
   if (ActionType.contains("Password"))
   {
       try
       {
       Password = Protector.decrypt(Variable2);
       Variable2 = Password;
       }
       catch (Exception e)
       {
     //   System.out.println("Load/decrypt error: " + e.toString());
       }
   }
  ActionsMaster NewActionsMaster = new ActionsMaster();
   
   HashMap<String, Action> thisActionHashMap = NewActionsMaster.ActionHashMap;
   HashMap<String, ActionView> thisActionViewHashMap = NewActionsMaster.ActionViewHashMap;
   HashMap<String, Action> thisPassFailActionHashMap = NewActionsMaster.PassFailActionHashMap;
   HashMap<String, ActionView> thisPassFailActionViewHashMap = NewActionsMaster.PassFailActionViewHashMap;
    if (thisActionHashMap.containsKey(ActionType))
           {
               Action thisActionToAdd = (Action) thisActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1);
               thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, NewProcedure, NewProcedureView);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, NewProcedure, NewProcedureView);
               STAppFrame.AddActionToArray (thisActionToAdd, thisActionViewToAdd, NewProcedure, NewProcedureView);
               
           }      
 
     if (thisPassFailActionHashMap.containsKey(ActionType))
             {
               Action thisActionToAdd = (Action) thisPassFailActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisPassFailActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1);
               thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, NewProcedure, NewProcedureView);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, NewProcedure, NewProcedureView);
              STAppFrame.AddActionToArray (thisActionToAdd, thisActionViewToAdd, NewProcedure, NewProcedureView);
             }
 
 
  
STAppFrame.UpdateDisplay();
        }   
     
    }
    }
catch (Exception e)
        {
            System.out.println(e.toString());
          
        }
 
 
STAppFrame.addTargetBrowserItemListener( new ItemListener() {
    
        public void itemStateChanged (ItemEvent e )
        {
         if ((e.getStateChange() == ItemEvent.SELECTED)) {
            Object ActionType = e.getItem();
            String TargetBrowser = ActionType.toString();
           STAppFrame.TargetBrowser = TargetBrowser;
          STAppFrame.changes = true;
           if ("Chrome".equals(STAppFrame.TargetBrowser) || "Firefox-Marionette".equals(STAppFrame.TargetBrowser))
           {
              STAppFrame.setOSTypeActive(true);
           }
           else
           {
            STAppFrame.setOSTypeActive(false);   
           }
         }
        }
        
        });
 
  
STAppFrame.addjButtonBrowseForFireFoxExeActionListener(
new ActionListener() {
    public void actionPerformed (ActionEvent evt)
    {
    FireFoxProperties FFProperties = new FireFoxProperties();
    FFProperties.BrowseforFFPath();
 
    }
});

STAppFrame.addjButtonDoStuffActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
 STAppFrame.RunActions(); 
 
  
        }
      }
    );
   STAppFrame.addjButtonFlattenFileActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
   
                mainApp.SaveFile(STAppFrame, true, true);
                   
 
  
        }
      }
    );
      STAppFrame.addjButtonClearEmailSettingsListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
 STAppFrame.ClearEmailSettings(); 
 
  
        }
      }
    );
    STAppFrame.addjButtonLoadEmailSettingsListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 try
 {
 STAppFrame.LoadGlobalEmailSettings();
 }
 catch (Exception ex)
 {
     System.out.println ("Exception loading global email settings: " + ex.toString());
 }
  
        }
      }
    );  
        STAppFrame.addjButtonGutsViewActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
   STAppFrame.ShowGuts();

        }
                                          
      }
    );
    STAppFrame.addjButtonNewBugActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
   STAppFrame.AddNewBug();  
 
 
  }
                                          
      }
    );
     STAppFrame.addjButtonNewDataLoopActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
   File chosenCSVFile = mainApp.BrowseForCSVFile();
   if (chosenCSVFile!=null)
   {
   STAppFrame.AddNewDataLoop(chosenCSVFile);  
   }
 
  }
                                          
      }
    );
  
// STAppFrame.ShowStoredVarControls(false);
 return STAppFrame;

  }
}
