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
SeleniumTestTool STAppFrame;
SeleniumTestToolData STAppData;
ArrayList<SeleniumTestTool> MDIViewClasses;
ArrayList<SeleniumTestToolData> MDIDataClasses;
MainAppFrame mainAppFrame;
STAppController mainAppController;
int calling_MDI_Index;
boolean isFlatten;
boolean RunIt;
boolean fromCloud=false;
boolean hasGUI = true;
public OpenFileThread(STAppController in_mainAppController, File in_file, ArrayList<SeleniumTestToolData> in_MDIDataClasses)
{
   hasGUI = false;
   mainAppController = in_mainAppController;
   MDIDataClasses = in_MDIDataClasses;
   RunIt = true;
   file = in_file;
 
}
    public OpenFileThread(STAppController in_mainAppController, MainAppFrame in_mainAppFrame, File file, ArrayList<SeleniumTestTool> MDIViewClasses, ArrayList<SeleniumTestToolData> MDIDataClasses, int calling_MDI_Index, boolean isFlatten, boolean RunIt)
{
  this.isFlatten = isFlatten;
  this.mainAppFrame = in_mainAppFrame;
  this.mainAppController = in_mainAppController;
  this.file = file;
  this.MDIViewClasses = MDIViewClasses;
  this.MDIDataClasses = MDIDataClasses;
  this.calling_MDI_Index = calling_MDI_Index;
  this.RunIt = RunIt;
}
      public OpenFileThread(STAppController in_mainAppController, MainAppFrame in_mainAppFrame, File file, ArrayList<SeleniumTestTool> MDIViewClasses, ArrayList<SeleniumTestToolData> MDIDataClasses, int calling_MDI_Index, boolean isFlatten, boolean RunIt, boolean fromCloud)
{
    this.fromCloud = fromCloud;
  this.isFlatten = isFlatten;
  this.mainAppFrame = in_mainAppFrame;
   this.mainAppController = in_mainAppController;
  this.file = file;
  this.MDIViewClasses = MDIViewClasses;
  this.MDIDataClasses = MDIDataClasses;
  this.calling_MDI_Index = calling_MDI_Index;
  this.RunIt = RunIt;
}
@Override 
public String doInBackground()
 {
     
   if (hasGUI)
   {
       mainAppFrame.Navigator.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
   }
    try {
        int MDI_CLASS_INDEX;
           MDI_CLASS_INDEX = OpenFile();
         if (MDI_CLASS_INDEX>=0)
     {
         if (hasGUI)
         {
           mainAppController.DisplayWindow(MDI_CLASS_INDEX);
         }
           
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
     if (hasGUI)
     {
  mainAppFrame.Navigator.setCursor(Cursor.getDefaultCursor()); 
  int last_index = mainAppController.MDIViewClasses.size()-1;
  if (last_index>-1)
  {
     MDIViewClasses.get(last_index).UpdateDisplay();
  JScrollBar vertical =  MDIViewClasses.get(last_index).MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
  }
  if (isFlatten)
  {
  MDIViewClasses.get(calling_MDI_Index).setFlattenFileButtonName ("Flatten to New File");
  }
 
     } 
       if (RunIt)
  {
      if (hasGUI)
      {
   int current_MDI_Index = mainAppController.GetCurrentWindow();

    if (current_MDI_Index>=0) {    mainAppController.RunActions(MDIViewClasses.get(current_MDI_Index), MDIDataClasses.get(current_MDI_Index));
    }
      }
    else
    {
        mainAppController.RunActions(MDIDataClasses.get(0));
    } 
  } 
 }
 @Override
 protected void process ( List <Integer> bugindex)
 {
     
 }
    public int OpenFile () throws FileNotFoundException, IOException, ClassNotFoundException
    {
   
  
        
if(!file.exists()) { 
 return -10;
 
}
else
{
    String full_filename;
    if (this.fromCloud)
    {
        if (MDIViewClasses.size()>0)
        {
       full_filename = file.getName() + "-untitled" + MDIViewClasses.size();
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
     if (hasGUI)
     {
     for (SeleniumTestTool thisfile: MDIViewClasses)
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
     }
     if (PromptForSameFileName==false)
    {


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
 
   BuildNewWindow(doc, full_filename);
 
 if (hasGUI)
 {
  STAppFrame.setFilename(full_filename);
 }
  STAppData.setFilenames(full_filename);
  if (this.fromCloud)
  {
      
  }
  else
  {
      if (hasGUI)
      {
   mainAppFrame.Navigator.addRecentFile(full_filename);
      }
  }
  
if (hasGUI)
{
STAppData.AllFieldValues.clear();
STAppData.AllFieldValues.add(STAppData.getOSType());
STAppData.AllFieldValues.add(STAppData.getTargetBrowser());
String stringWaitTime = String.valueOf(STAppData.getWaitTime());
STAppData.AllFieldValues.add(stringWaitTime);
String stringTimeout = String.valueOf(STAppData.getTimeout());
STAppData.AllFieldValues.add(stringTimeout);
String stringSessions = String.valueOf(STAppData.getSessions());
STAppData.AllFieldValues.add(stringSessions);
STAppData.AllFieldValues.add(STAppFrame.getSMTPHostname());
STAppData.AllFieldValues.add(STAppFrame.getEmailFrom());
STAppData.AllFieldValues.add(STAppFrame.getEmailLoginName());
STAppData.AllFieldValues.add(STAppFrame.getEmailPassword());
STAppData.AllFieldValues.add(STAppFrame.getEmailTo());
STAppData.AllFieldValues.add(STAppFrame.getSubject());

String thisbool = "false";
if (STAppData.getEmailReport())
{
    thisbool = "true";
}
STAppData.AllFieldValues.add(thisbool);

thisbool = "false";
if (STAppData.getEmailReportFail())
{
    thisbool = "true";
}

STAppData.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppData.getExitAfter())
{
    thisbool = "true";
}
STAppData.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppData.getPromptToClose())
{
    thisbool = "true";
}
STAppData.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppData.getShowReport())
{
    thisbool = "true";
}
STAppData.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppData.getIncludeScreenshots())
{
    thisbool = "true";
}
STAppData.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppData.getUniqueList())
{
    thisbool = "true";
}

STAppData.AllFieldValues.add(thisbool);

STAppData.AllFieldValues.add(STAppData.getUniqueFileOption());
for (Procedure thisproc: STAppData.BugArray)
{
    STAppData.AllFieldValues.add(thisproc.BugTitle);
    STAppData.AllFieldValues.add(thisproc.DataFile);
      String randboolval = "false";
    if (thisproc.random)
    {
        randboolval = "true";
    }
    
    STAppData.AllFieldValues.add(randboolval);
 
    String limitstring = Integer.toString(thisproc.limit);
    STAppData.AllFieldValues.add(limitstring);
    for (Action thisact: thisproc.ActionsList)
    {
        String checkingboolval1 = "false";
        String checkingboolval2 = "false";
        String checkingboolval3 = "false";
        STAppData.AllFieldValues.add(thisact.Variable1);

        STAppData.AllFieldValues.add(thisact.Variable2);
        if (thisact.BoolVal1)
        {
            checkingboolval1 = "true";
        }
         STAppData.AllFieldValues.add(checkingboolval1);
         if (thisact.BoolVal2)
        {
            checkingboolval2 = "true";
        }
        STAppData.AllFieldValues.add(checkingboolval2);
          if (thisact.Locked)
        {
            checkingboolval3 = "true";
        }
        STAppData.AllFieldValues.add(checkingboolval3);
        
    }
}
}
 if (hasGUI)
 {
 MDIViewClasses.add(STAppFrame);
 }
   MDIDataClasses.add(STAppData);
   MDI_Index =  MDIDataClasses.size()-1;

}

 return MDI_Index;
    }
   else
     {
         if (hasGUI)
         {
         if (mainAppController.MDIViewClasses.get(alreadyopen_index).isIcon())
         {
             try
             {
             mainAppController.MDIViewClasses.get(alreadyopen_index).setMaximum(true);
             }
             catch (Exception ex)
             {
                 System.out.println("Exception maximizing window: " + ex.toString());
             }
         }
         mainAppController.MDIViewClasses.get(alreadyopen_index).moveToFront();
         try
         {
         mainAppController.MDIViewClasses.get(alreadyopen_index).setSelected(true);
         }
         catch(Exception ex)
         {
             System.out.println("Exception selecting window: " + ex.toString());
             
         }
         }
//  mainApp.DisplayWindow(alreadyopen_index);
 //   JOptionPane.showMessageDialog (null, filealreadyopen + " is already open", "File is open", JOptionPane.INFORMATION_MESSAGE);
                               
 
 return -1;
     }
}

    }
  
  public void BuildNewWindow(Document doc, String full_filename)
  {
   ArrayList<Procedure> newBugArray = new ArrayList<>();
  // ArrayList<ProcedureView> newBugViewArray = new ArrayList<>();
   NamedNodeMap NewAttributes = doc.getElementsByTagName("BrowserMatorWindow").item(0).getAttributes(); 
     STAppData = new SeleniumTestToolData(newBugArray);
     
    
   String filename_read = NewAttributes.getNamedItem("Filename").getNodeValue();

   



  STAppData.setFilenames(full_filename);
  if (hasGUI)
  {
  STAppFrame = new SeleniumTestTool(STAppData);
 
    STAppFrame.setResizable(true);
  STAppFrame.setClosable(true);
  STAppFrame.setMaximizable(true);

    STAppFrame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
     @Override 
     public void internalFrameClosing(InternalFrameEvent e) {
    
    
      int closed =  mainAppController.CheckToSaveChanges(STAppFrame, STAppData, false);
           
      if (closed==1)
      {
      STAppFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
          int thisMDIIndex = mainAppController.GetCurrentWindow();
          
       mainAppController.RemoveWindow(thisMDIIndex);
       // mainApp.RemoveWindow(MDIClasses.size()-1); 
       STAppFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      }
     
      }
    });
  }
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
 
   STAppData.setShowReport(ShowReport);
            break;
            
        case "IncludeScreenshots":
           stIncludeScreenshots = thisSettingsNodeValue;
   Boolean IncludeScreenshots = false;  
    if (stIncludeScreenshots.equals("true"))
   {
       IncludeScreenshots = true;
   }
    
       STAppData.setIncludeScreenshots(IncludeScreenshots);
            break;
            
                
        case "EmailReport":
   stEmailReport = thisSettingsNodeValue;
   Boolean EmailReport = false;
    if (stEmailReport.equals("true"))
   {
       EmailReport = true;
   }
   
       STAppData.setEmailReport(EmailReport);
            break;
      
        case "EmailReportFail":
   stEmailReportFail = thisSettingsNodeValue;
   Boolean EmailReportFail = false;
    if (stEmailReportFail.equals("true"))
   {
       EmailReportFail = true;
   }
  
       STAppData.setEmailReportFail(EmailReportFail);
            break;
         
        case "ExitAfter":
   stExitAfter = thisSettingsNodeValue;
   Boolean ExitAfter = false;
    if (stExitAfter.equals("true"))
   {
       ExitAfter = true;
   }
       STAppData.setExitAfter(ExitAfter);
  
            break;        
       
        case "SMTPHostname":
 SMTPHostname = thisSettingsNodeValue;
    
       STAppData.setSMTPHostname(SMTPHostname);
            break;  

        case "EmailLoginName":
 EmailLoginName = thisSettingsNodeValue;
    
      STAppData.setEmailLoginName(EmailLoginName);
            break;  
        
        case "PromptToClose":
 stPromptToClose = thisSettingsNodeValue;
   Boolean PromptToClose = false;
    if (stPromptToClose.equals("true"))
   {
       PromptToClose = true;
   }
    
         STAppData.setPromptToClose(PromptToClose);
            break; 
      case "UniqueList":
 stUniqueList = thisSettingsNodeValue;
   Boolean UniqueList = false;
    if (stUniqueList.equals("true"))
   {
      UniqueList = true;
   }
    
        STAppData.setUniqueList(UniqueList);
     
            break; 
         case "UniqueFileOption":
  
        STAppData.setUniqueFileOption(thisSettingsNodeValue);
     
            break;          
            
          
        case "TargetBrowser":
 TargetBrowser = thisSettingsNodeValue;
    
      STAppData.setTargetBrowser(TargetBrowser);
   
            break;   
            
       case "WaitTime":
 WaitTime = thisSettingsNodeValue;
 int intWaitTime = Integer.parseInt(WaitTime);
    
      STAppData.setWaitTime(intWaitTime);
            break;  
  
            //timeouts not supported by selenium at the moment... or they're borky
 //          case "Timeout":
// Timeout = thisSettingsNodeValue;
// int intTimeout = Integer.parseInt(Timeout);
//     STAppFrame.setjSpinnerTimeout(intTimeout); 
//  STAppData.setTimeout(intTimeout);
//            break;   
            
        case "Sessions":
 Sessions = thisSettingsNodeValue;
 int intSessions = Integer.parseInt(Sessions);
  
      STAppData.setSessions(intSessions);
            break;               
       case "OSType":
 OSType = thisSettingsNodeValue;
     
       STAppData.setOSType(OSType);
    
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
  
      STAppData.setEmailPassword(unepassword);
            break;  
      
       case "EmailTo":
 EmailTo = thisSettingsNodeValue;
    
       STAppData.setEmailTo(EmailTo);
            break;    
      
       case "EmailFrom":
 EmailFrom = thisSettingsNodeValue;
   
      STAppData.setEmailFrom(EmailFrom);
            break;   
       
       case "EmailSubject":
 EmailSubject = thisSettingsNodeValue;
     
       STAppData.setSubject(EmailSubject);
            
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

   boolean hasDataloop = false;
   boolean hasURLList = false;
for (int i = 0; i < ProcedureList.getLength(); ++i)
{
    
    
 
   
    Element Procedure = (Element) ProcedureList.item(i);
    String ProcType = "Procedure";
    if (Procedure.hasAttribute("Type"))
    {
    ProcType = Procedure.getAttribute("Type");
    }
    String DataLoopSource = "urllist";
    if (Procedure.hasAttribute("DataLoopSource"))
    {
     DataLoopSource = Procedure.getAttribute("DataLoopSource");
    }
    if ("Dataloop".equals(ProcType))
    {
        
        hasDataloop = true;
        String DataFile = "";
        if (Procedure.hasAttribute("DataLoopFile"))
                {
                    DataFile = Procedure.getAttribute("DataLoopFile");
            //legacy support...
                    if (DataFile.contains("\\"))
                    {
                        DataLoopSource = "file";
                    }
                }
        if ("file".equals(DataLoopSource))
        {
        File DataFile_file = new File(DataFile);
    if (hasGUI)
    {
   STAppFrame.AddNewDataLoopFileView(DataFile_file);
    }   
   STAppData.AddNewDataLoopFile(DataFile_file);

    int last_added_bug_index = STAppData.BugArray.size()-1;
     Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
    if (hasGUI)
    {
   ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
   newbugview.populateJComboBoxStoredArrayLists(STAppData.VarLists);
     mainAppController.AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
      STAppFrame.UpdateDisplay();
         JScrollBar vertical = STAppFrame.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
    }
  

    
 
     
        }
       if ("urllist".equals(DataLoopSource))
        {
         hasURLList = true;
          STAppData.AddNewDataLoopURLList(DataFile);
           int last_added_bug_index = STAppData.BugArray.size()-1;
           Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
         if (hasGUI)
         {
                STAppFrame.AddNewDataLoopURLListView(DataFile);
                  ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
                    newbugview.populateJComboBoxStoredArrayLists(STAppData.VarLists);
                      mainAppController.AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
                       STAppFrame.UpdateDisplay();
        JScrollBar vertical = STAppFrame.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
         }
  
  
   
 
  
  
   
    
 
        }
      if (Procedure.hasAttribute("Random"))
  {
  String stRand = Procedure.getAttribute("Random");
    Boolean Rand = false;
    if (stRand.equals("true"))
    {
        Rand = true;
    }
   
    STAppData.BugArray.get(i).setRandom(Rand);
    if (hasGUI)
    {
    STAppFrame.BugViewArray.get(i).setRandom(Rand);
    }
  }
    if (Procedure.hasAttribute("Limit"))
  {
    int limit = Integer.parseInt(Procedure.getAttribute("Limit"));
    STAppData.BugArray.get(i).setLimit(limit);
     if (hasGUI)
    {
    STAppFrame.BugViewArray.get(i).setLimit(limit);
    }
  }
    }
    else
    {
     STAppData.AddNewBug(); 
     int last_added_bug_index = STAppData.BugArray.size()-1;
      Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
      if (hasGUI)
    {
    STAppFrame.AddNewBugView();  
     ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
      mainAppController.AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
       STAppFrame.UpdateDisplay();
        JScrollBar vertical = STAppFrame.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
    }
       
      
 
    }
    String BugTitle = "";
    if (Procedure.hasAttribute("Title"))
    {
        BugTitle = Procedure.getAttribute("Title");
    }
    STAppData.BugArray.get(i).setBugTitle(BugTitle);
       if (hasGUI)
    {
    STAppFrame.BugViewArray.get(i).setBugTitle(BugTitle);
    }
    String BugURL = "";
    if (Procedure.hasAttribute("URL"))
    {
        BugURL = Procedure.getAttribute("URL");
    }
    STAppData.BugArray.get(i).setBugURL(BugURL);
    
    // needed?
   // String stPass = Procedure.getAttribute("Pass");
  //  Boolean Pass = false;
  //  if (stPass.equals("true"))
  //  {
  //      Pass = true;
  //  }
  //  STAppData.BugArray.get(i).setPass(Pass);

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
     Procedure NewProcedure = STAppData.BugArray.get(i);
      if (hasGUI)
    {
   ProcedureView NewProcedureView = STAppFrame.BugViewArray.get(i);
    if (thisActionHashMap.containsKey(ActionType))
           {
               Action thisActionToAdd = (Action) thisActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, NewProcedure, NewProcedureView);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, NewProcedure, NewProcedureView);
               STAppFrame.AddActionViewToArray (thisActionViewToAdd, NewProcedureView);
               STAppData.AddActionToArray(thisActionToAdd, NewProcedure);
              
           }      
 
     if (thisPassFailActionHashMap.containsKey(ActionType))
             {
               Action thisActionToAdd = (Action) thisPassFailActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisPassFailActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
             thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, NewProcedure, NewProcedureView);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, NewProcedure, NewProcedureView);
               STAppFrame.AddActionViewToArray (thisActionViewToAdd, NewProcedureView);
               STAppData.AddActionToArray(thisActionToAdd, NewProcedure);
               
             }
    }
      else
      {
           if (thisActionHashMap.containsKey(ActionType))
           {
               Action thisActionToAdd = (Action) thisActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
             //  thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, NewProcedure, NewProcedureView);
            //   thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, NewProcedure, NewProcedureView);
            //   STAppFrame.AddActionViewToArray (thisActionViewToAdd, NewProcedureView);
               STAppData.AddActionToArray(thisActionToAdd, NewProcedure);
              
           }      
 
     if (thisPassFailActionHashMap.containsKey(ActionType))
             {
               Action thisActionToAdd = (Action) thisPassFailActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisPassFailActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
            // thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, NewProcedure, NewProcedureView);
            //   thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, NewProcedure, NewProcedureView);
            //   STAppFrame.AddActionViewToArray (thisActionViewToAdd, NewProcedureView);
               STAppData.AddActionToArray(thisActionToAdd, NewProcedure);
               
             }
      }
   
 
 
  

        }   
 
    }

    if (hasDataloop && !hasURLList)
 {
        if (hasGUI)
     {
    STAppFrame.setJButtonFlattenFileEnabled(true); 
     }
 
 } 
    }
catch (Exception e)
        {
            System.out.println("Exception parsing procedure node" + e.toString());
          
        }
 
     if (hasGUI)
     {
STAppFrame.addTargetBrowserItemListener( new ItemListener() {
    
        public void itemStateChanged (ItemEvent e )
        {
         if ((e.getStateChange() == ItemEvent.SELECTED)) {
            Object ActionType = e.getItem();
            String TargetBrowser = ActionType.toString();
           STAppFrame.setTargetBrowserView(TargetBrowser);
           STAppData.setTargetBrowser(TargetBrowser);
          STAppData.changes = true;
          
         }
        }
        
        });
     
   
  
STAppFrame.addjButtonBrowseForFireFoxExeActionListener(
new ActionListener() {
    public void actionPerformed (ActionEvent evt)
    {
       String TargetBrowser = STAppData.getTargetBrowser();
    FireFoxProperties FFProperties = new FireFoxProperties(TargetBrowser);
    FFProperties.BrowseforFFPath();
 
    }
});

STAppFrame.addjButtonDoStuffActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
 mainAppController.RunActions(STAppFrame, STAppData); 
 
  
        }
      }
    );
   STAppFrame.addjButtonFlattenFileActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
   
                mainAppController.ThreadSaveFile(mainAppFrame, STAppFrame, STAppData, true, true);
                   
 
  
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
   STAppData.loadGlobalEmailSettings();
  STAppFrame.setEmailSettings(STAppData);
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
    
   mainAppController.showGuts(STAppFrame, STAppData);

        }
                                          
      }
    );
    STAppFrame.addjButtonNewBugActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
   STAppFrame.AddNewBugView();  
   STAppData.AddNewBug();
   int last_added_bug_index = STAppFrame.BugViewArray.size()-1;
   ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
   Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
      mainAppController.AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
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
    

   STAppData.AddNewDataLoop(); 
   STAppFrame.AddNewDataLoopView();
     int last_added_bug_index = STAppFrame.BugViewArray.size()-1;
   ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
   Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
      mainAppController.AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
    
  STAppFrame.UpdateDisplay();
        JScrollBar vertical = STAppFrame.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
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


STAppFrame.initializeDisplay();
     }
  }
}
