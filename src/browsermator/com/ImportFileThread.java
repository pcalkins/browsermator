package browsermator.com;

import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JScrollBar;
import javax.swing.SwingWorker;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author pcalkins
 */
public class ImportFileThread extends SwingWorker<String, Integer>{
File[] files;
ArrayList<SeleniumTestTool> MDIViewClasses;
ArrayList<SeleniumTestToolData> MDIDataClasses;
MainAppFrame mainApp;
int calling_MDI_Index;
STAppController mainAppController;
SeleniumTestTool STAppFrame;
SeleniumTestToolData STAppData;
boolean RunIt;

    public ImportFileThread(MainAppFrame in_mainApp, STAppController in_STAppController, File[] files, int calling_MDI_Index)
{
  
  this.mainApp = in_mainApp;
  this.files = files;
  this.calling_MDI_Index = calling_MDI_Index;
  this.mainAppController = in_STAppController;
  this.MDIViewClasses = mainAppController.MDIViewClasses;
  this.MDIDataClasses = mainAppController.MDIDataClasses;
  this.STAppFrame = MDIViewClasses.get(calling_MDI_Index);
  this.STAppData = MDIDataClasses.get(calling_MDI_Index);
}
@Override 
public String doInBackground()
 {
     mainAppController.MDIViewClasses.get(calling_MDI_Index).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    try {
  
          for (int fileindex = 0; fileindex<files.length; fileindex++)
     {
    ImportFile(files[fileindex], calling_MDI_Index);

   }    
       
     
       }
       catch (Exception ex) {
          System.out.println("error opening file: " + ex.toString());
       } 
    
 return "done";
 }
@Override
 protected void done()
 {

   mainAppController.MDIViewClasses.get(calling_MDI_Index).setCursor(Cursor.getDefaultCursor()); 
   mainAppController.MDIViewClasses.get(calling_MDI_Index).UpdateDisplay();
   
     
 }
 @Override
 protected void process ( List <Integer> bugindex)
 {
     
 }
  public void ImportNewWindow (Document doc, int MDI_INDEX)
  {
int current_number_of_procedures = STAppFrame.BugViewArray.size();
 try
{
   NodeList ProcedureList = doc.getElementsByTagName("Procedure");
   
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
        
        
        String DataFile = "";
        if (Procedure.hasAttribute("DataLoopFile"))
                {
                    DataFile = Procedure.getAttribute("DataLoopFile");
            //legacy support...
                    if (DataFile.contains(File.separator))
                    {
                        DataLoopSource = "file";
                    }
                }
        if ("file".equals(DataLoopSource))
        {
        File DataFile_file = new File(DataFile);
       
        
            STAppData.AddNewDataLoopFile(DataFile_file);
                STAppFrame.AddNewDataLoopFileView(DataFile_file);
  
   
        }
       if ("urllist".equals(DataLoopSource))
        {
         
        
               String[] parts = DataFile.split("-");
 String leftpart = parts[0];
  String rightpart = "0";
 if (parts.length>1)
 {
rightpart = parts[1];
 }
 if (!"".equals(leftpart))
 {
 leftpart = Integer.toString(current_number_of_procedures + Integer.parseInt(leftpart));  
 }
 else
 {
     leftpart = "0";
 }
 
    
 String combinedDataFile = leftpart + "-" + rightpart;

         
            STAppData.AddNewDataLoopURLList(combinedDataFile);
               STAppFrame.AddNewDataLoopURLListView(combinedDataFile);
         
            

  
        }
 
    }
    else
    {
     STAppData.AddNewBug();   
    STAppFrame.AddNewBugView(); 
  
    }
    
      int last_added_bug_index = STAppFrame.BugViewArray.size()-1;
   ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
   newbugview.populateJComboBoxStoredArrayLists(STAppData.VarLists);
   Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
      mainAppController.AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
 
      if (Procedure.hasAttribute("Random"))
  {
  String stRand = Procedure.getAttribute("Random");
    Boolean Rand = false;
    if (stRand.equals("true"))
    {
        Rand = true;
    }
   
    newbug.setRandom(Rand);
    newbugview.setRandom(Rand);
  }
    if (Procedure.hasAttribute("Limit"))
  {
    int limit = Integer.parseInt(Procedure.getAttribute("Limit"));
    newbug.setLimit(limit);
    newbugview.setLimit(limit);
  } 
 String BugTitle = "";
    if (Procedure.hasAttribute("Title"))
    {
        BugTitle = Procedure.getAttribute("Title");
    }
    newbug.setBugTitle(BugTitle);
    newbugview.setBugTitle(BugTitle);
    String BugURL = "";
    if (Procedure.hasAttribute("URL"))
    {
        BugURL = Procedure.getAttribute("URL");
    }
    newbug.setBugURL(BugURL);
    
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
   
   HashMap<String, BMAction> thisActionHashMap = NewActionsMaster.ActionHashMap;
   HashMap<String, ActionView> thisActionViewHashMap = NewActionsMaster.ActionViewHashMap;
   HashMap<String, BMAction> thisPassFailActionHashMap = NewActionsMaster.PassFailActionHashMap;
   HashMap<String, ActionView> thisPassFailActionViewHashMap = NewActionsMaster.PassFailActionViewHashMap;
    if (thisActionHashMap.containsKey(ActionType))
           {
               BMAction thisActionToAdd = (BMAction) thisActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.AddListeners(thisActionToAdd, MDIViewClasses.get(MDI_INDEX), MDIDataClasses.get(MDI_INDEX), newbug, newbugview);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, MDIViewClasses.get(MDI_INDEX), MDIDataClasses.get(MDI_INDEX), newbug, newbugview);
            
               MDIViewClasses.get(MDI_INDEX).AddActionViewToArray(thisActionViewToAdd, newbugview);
                  MDIDataClasses.get(MDI_INDEX).AddActionToArray(thisActionToAdd, newbug);
             
               
           }      
 
     if (thisPassFailActionHashMap.containsKey(ActionType))
             {
               BMAction thisActionToAdd = (BMAction) thisPassFailActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisPassFailActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
              thisActionViewToAdd.AddListeners(thisActionToAdd, MDIViewClasses.get(MDI_INDEX), MDIDataClasses.get(MDI_INDEX), newbug, newbugview);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, MDIViewClasses.get(MDI_INDEX), MDIDataClasses.get(MDI_INDEX), newbug, newbugview);
            
               MDIViewClasses.get(MDI_INDEX).AddActionViewToArray(thisActionViewToAdd, newbugview);
                  MDIDataClasses.get(MDI_INDEX).AddActionToArray(thisActionToAdd, newbug);
               
             }
            
// MDIClasses.get(MDI_INDEX).UpdateDisplay();
        }   
  
    }  
    }
catch (Exception e)
        {
            System.out.println("Exception parsing procedure node" + e.toString());
          
        }
  STAppFrame.UpdateDisplay();
        JScrollBar vertical = STAppFrame.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
  }
   public void ImportFile (File file, int MDI_INDEX)
  {
   
        
if(!file.exists()) { 
 
 
}
else
{
  
Document doc=null;
try
{

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();

doc = builder.parse(file.getAbsolutePath());

 
}
catch (ParserConfigurationException | SAXException | IOException e)
{
    System.out.println("DocumentBuilder error:" + e.toString());
   
}
    
finally 
{
 
  ImportNewWindow(doc, MDI_INDEX);
//  MDIClasses.get(MDI_INDEX).UpdateDisplay();
 MDIDataClasses.get(MDI_INDEX).changes = true;
}
    
  }

  }
 
}
