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
import javax.swing.JScrollBar;

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
  int last_index = MDIClasses.size()-1;
     MDIClasses.get(last_index).UpdateDisplay();
  JScrollBar vertical =  MDIClasses.get(last_index).MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
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
    
     int alreadyopen_index = -1;
     int thisfile_index = 0;
     for (SeleniumTestTool thisfile: MDIClasses)
     {
         
        String twoslashes = "\\" + "\\";
        
         String thisfilename = thisfile.filename.replace(twoslashes, "\\");
        String browsedfile = file.getAbsolutePath();
        
                
         if (browsedfile.equals(thisfilename))
         {   
           alreadyopen_index = thisfile_index;
             PromptForSameFileName = true;
          
         }
         thisfile_index++;
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
 
    STAppFrame = BuildNewWindow(doc, full_filename);
  
 //  STAppFrame.setVisible(true);
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
String stringTimeout = String.valueOf(STAppFrame.getTimeout());
STAppFrame.AllFieldValues.add(stringTimeout);
String stringSessions = String.valueOf(STAppFrame.getSessions());
STAppFrame.AllFieldValues.add(stringSessions);
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
thisbool = "false";
if (STAppFrame.getIncludeScreenshots())
{
    thisbool = "true";
}
STAppFrame.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppFrame.getUniqueList())
{
    thisbool = "true";
}

STAppFrame.AllFieldValues.add(thisbool);

STAppFrame.AllFieldValues.add(STAppFrame.getUniqueFileOption());
for (Procedure thisproc: STAppFrame.BugArray)
{
    STAppFrame.AllFieldValues.add(thisproc.BugTitle);
    STAppFrame.AllFieldValues.add(thisproc.DataFile);
      String randboolval = "false";
    if (thisproc.random)
    {
        randboolval = "true";
    }
    
    STAppFrame.AllFieldValues.add(randboolval);
 
    String limitstring = Integer.toString(thisproc.limit);
    STAppFrame.AllFieldValues.add(limitstring);
    for (Action thisact: thisproc.ActionsList)
    {
        String checkingboolval1 = "false";
        String checkingboolval2 = "false";
        String checkingboolval3 = "false";
        STAppFrame.AllFieldValues.add(thisact.Variable1);

        STAppFrame.AllFieldValues.add(thisact.Variable2);
        if (thisact.BoolVal1)
        {
            checkingboolval1 = "true";
        }
         STAppFrame.AllFieldValues.add(checkingboolval1);
         if (thisact.BoolVal2)
        {
            checkingboolval2 = "true";
        }
        STAppFrame.AllFieldValues.add(checkingboolval2);
          if (thisact.Locked)
        {
            checkingboolval3 = "true";
        }
        STAppFrame.AllFieldValues.add(checkingboolval3);
        
    }
}


}

 return MDI_Index;
    }
   else
     {
         if (mainApp.MDIClasses.get(alreadyopen_index).isIcon())
         {
             try
             {
             mainApp.MDIClasses.get(alreadyopen_index).setMaximum(true);
             }
             catch (Exception ex)
             {
                 System.out.println("Exception maximizing window: " + ex.toString());
             }
         }
         mainApp.MDIClasses.get(alreadyopen_index).moveToFront();
         try
         {
         mainApp.MDIClasses.get(alreadyopen_index).setSelected(true);
         }
         catch(Exception ex)
         {
             System.out.println("Exception selecting window: " + ex.toString());
             
         }
//  mainApp.DisplayWindow(alreadyopen_index);
 //   JOptionPane.showMessageDialog (null, filealreadyopen + " is already open", "File is open", JOptionPane.INFORMATION_MESSAGE);
                               
 
 return -1;
     }
}

    }
  
  public SeleniumTestTool BuildNewWindow(Document doc, String full_filename)
  {
   
  
   NamedNodeMap NewAttributes = doc.getElementsByTagName("BrowserMatorWindow").item(0).getAttributes(); 
   
   String filename_read = NewAttributes.getNamedItem("Filename").getNodeValue();
   SeleniumTestTool STAppFrame = new SeleniumTestTool(filename_read);
   



  STAppFrame.setProperties(full_filename);
  STAppFrame.setResizable(true);

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
          int thisMDIIndex = mainApp.GetCurrentWindow();
          
       mainApp.RemoveWindow(thisMDIIndex);
       // mainApp.RemoveWindow(MDIClasses.size()-1); 
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
  String stIncludeScreenshots = "false";
  String stExitAfter = "false";
  String SMTPHostname = "";
  String EmailLoginName = "";
  String stPromptToClose = "false";
  String TargetBrowser = "Firefox";
  String WaitTime = "3";
  String Sessions = "1";
  String OSType = "Windows32";
  String EmailPassword = "";
  String unepassword = "";
  String EmailTo = "";
  String EmailFrom = "";
  String EmailSubject = "";
  String Timeout = "";
  String stUniqueList = "false";
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
            
        case "IncludeScreenshots":
           stIncludeScreenshots = thisSettingsNodeValue;
   Boolean IncludeScreenshots = false;  
    if (stIncludeScreenshots.equals("true"))
   {
       IncludeScreenshots = true;
   }
       STAppFrame.setIncludeScreenshots(IncludeScreenshots);
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
       STAppFrame.setEmailReportFail(EmailReportFail);
            break;
         
        case "ExitAfter":
   stExitAfter = thisSettingsNodeValue;
   Boolean ExitAfter = false;
    if (stExitAfter.equals("true"))
   {
       ExitAfter = true;
   }
       STAppFrame.setExitAfter(ExitAfter);
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
      case "UniqueList":
 stUniqueList = thisSettingsNodeValue;
   Boolean UniqueList = false;
    if (stUniqueList.equals("true"))
   {
      UniqueList = true;
   }
       STAppFrame.setUniqueList(UniqueList);
     
            break; 
         case "UniqueFileOption":
       STAppFrame.setUniqueFileOption(thisSettingsNodeValue);
     
            break;          
            
          
        case "TargetBrowser":
 TargetBrowser = thisSettingsNodeValue;
      STAppFrame.setTargetBrowser(TargetBrowser);
        if (TargetBrowser.equals("Firefox") || TargetBrowser.equals("Chrome"))
      {
      STAppFrame.setOSTypeActive(true);
      }
            break;   
            
       case "WaitTime":
 WaitTime = thisSettingsNodeValue;
 int intWaitTime = Integer.parseInt(WaitTime);
      STAppFrame.setWaitTime(intWaitTime);
            break;  
            
           case "Timeout":
Timeout = thisSettingsNodeValue;
 int intTimeout = Integer.parseInt(Timeout);
      STAppFrame.setTimeout(intTimeout);
            break;   
            
        case "Sessions":
 Sessions = thisSettingsNodeValue;
 int intSessions = Integer.parseInt(Sessions);
      STAppFrame.setSessions(intSessions);
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
   
    String ProcType = Procedure.getAttribute("Type");
    String DataLoopSource = "urllist";
    if (Procedure.hasAttribute("DataLoopSource"))
    {
     DataLoopSource = Procedure.getAttribute("DataLoopSource");
    }
    if ("Dataloop".equals(ProcType))
    {
        
        
        String DataFile = "";
        if (Procedure.hasAttribute("DataLoopFile"))
                {
                    DataFile = Procedure.getAttribute("DataLoopFile");
                   
                }
        if ("file".equals(DataLoopSource))
        {
        File DataFile_file = new File(DataFile);
       
            STAppFrame.AddNewDataLoopFile(DataFile_file);
        }
       if ("urllist".equals(DataLoopSource))
        {
            STAppFrame.AddNewDataLoopURLList(DataFile);
        }
      if (Procedure.hasAttribute("Random"))
  {
  String stRand = Procedure.getAttribute("Random");
    Boolean Rand = false;
    if (stRand.equals("true"))
    {
        Rand = true;
    }
   
    STAppFrame.BugArray.get(i).random = Rand;
    STAppFrame.BugViewArray.get(i).setRandom(Rand);
  }
    if (Procedure.hasAttribute("Limit"))
  {
    int limit = Integer.parseInt(Procedure.getAttribute("Limit"));
    STAppFrame.BugArray.get(i).limit = limit;
    STAppFrame.BugViewArray.get(i).setLimit(limit);
  }
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
   String BoolVal2 = "false";
    String TimeOfTest;
    String ActionType = "none";
    String ActionIndex;
    String ActionPass;
    String Password = "";
    
    
   Boolean RealBoolVal1 = false;
   Boolean RealBoolVal2 = false;
   Boolean boolLOCKED = false;
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
            case "BoolVal2":
            BoolVal2 = thisActionNodeValue;
            if (BoolVal2.equals("true"))
                    {
                    RealBoolVal2 = true;
                    }
           break;
        case "LOCKED":
            LOCKED = thisActionNodeValue;
             if (LOCKED.equals("true"))
                    {
                    boolLOCKED = true;
                    }
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
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, NewProcedure, NewProcedureView);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, NewProcedure, NewProcedureView);
               STAppFrame.AddActionToArray (thisActionToAdd, thisActionViewToAdd, NewProcedure, NewProcedureView);
              
           }      
 
     if (thisPassFailActionHashMap.containsKey(ActionType))
             {
               Action thisActionToAdd = (Action) thisPassFailActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisPassFailActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, NewProcedure, NewProcedureView);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, NewProcedure, NewProcedureView);
              STAppFrame.AddActionToArray (thisActionToAdd, thisActionViewToAdd, NewProcedure, NewProcedureView);
             }
 
 
  

        }   
     
    }

    }
catch (Exception e)
        {
            System.out.println("Exception parsing procedure node" + e.toString());
          
        }
 
 
STAppFrame.addTargetBrowserItemListener( new ItemListener() {
    
        public void itemStateChanged (ItemEvent e )
        {
         if ((e.getStateChange() == ItemEvent.SELECTED)) {
            Object ActionType = e.getItem();
            String TargetBrowser = ActionType.toString();
           STAppFrame.setTargetBrowser(TargetBrowser);
           
          STAppFrame.changes = true;
          
         }
        }
        
        });
 
  
STAppFrame.addjButtonBrowseForFireFoxExeActionListener(
new ActionListener() {
    public void actionPerformed (ActionEvent evt)
    {
       String TargetBrowser = STAppFrame.getTargetBrowser();
    FireFoxProperties FFProperties = new FireFoxProperties(TargetBrowser);
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
   
                mainApp.ThreadSaveFile(STAppFrame, true, true);
                   
 
  
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
    STAppFrame.UpdateDisplay();
  JScrollBar vertical = STAppFrame.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
 
  }
                                          
      }
    );
     STAppFrame.addjButtonNewDataLoopActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    

   STAppFrame.AddNewDataLoop(); 
  
  }
                                          
      }
    );
  for (ProcedureView PV: STAppFrame.BugViewArray)
{
    int avlockcount = 0;
    for (ActionView AV: PV.ActionsViewList)
    {
       if (AV.Locked)
       {
           avlockcount++;
       }
    }
    if (avlockcount==PV.ActionsViewList.size())
    {
        PV.setLocked(true);
    }
}

 return STAppFrame;

  }
}
