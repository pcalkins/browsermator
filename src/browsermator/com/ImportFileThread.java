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
STAppController STAppController;

boolean RunIt;

    public ImportFileThread(MainAppFrame in_mainApp, STAppController in_STAppController, File[] files, int calling_MDI_Index)
{
  
  this.mainApp = in_mainApp;
  this.files = files;
  this.calling_MDI_Index = calling_MDI_Index;
  this.STAppController = in_STAppController;
  this.MDIViewClasses = STAppController.MDIViewClasses;
  this.MDIDataClasses = STAppController.MDIDataClasses;
}
@Override 
public String doInBackground()
 {
     STAppController.MDIViewClasses.get(calling_MDI_Index).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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

   STAppController.MDIViewClasses.get(calling_MDI_Index).setCursor(Cursor.getDefaultCursor()); 
   STAppController.MDIViewClasses.get(calling_MDI_Index).UpdateDisplay();
   
     
 }
 @Override
 protected void process ( List <Integer> bugindex)
 {
     
 }
  public void ImportNewWindow (Document doc, int MDI_INDEX)
  {

      NodeList ProcedureList = doc.getElementsByTagName("Procedure");
   
for (int i = 0; i < ProcedureList.getLength(); ++i)
{
    int newbug_index = MDIDataClasses.get(MDI_INDEX).BugArray.size();
   Element Procedure = (Element) ProcedureList.item(i);
     String ProcType = Procedure.getAttribute("Type");
     String DataLoopSource = "urllist";
    if ("Dataloop".equals(ProcType))
    {
      
         String DataFile = "";
        if (Procedure.hasAttribute("DataLoopFile"))
                {
                    DataFile = Procedure.getAttribute("DataLoopFile");
                }
         if (Procedure.hasAttribute("DataLoopSource"))
         {
             DataLoopSource = Procedure.getAttribute("DataLoopSource");
         }
       
    if ("file".equals(DataLoopSource))
    {
         File DataFile_file = new File(DataFile);
       MDIDataClasses.get(MDI_INDEX).AddNewDataLoopFile(DataFile_file);   
        MDIViewClasses.get(MDI_INDEX).AddNewDataLoopFileView(DataFile_file); 
    }
    else
    {
       MDIDataClasses.get(MDI_INDEX).AddNewDataLoopURLList(DataFile);
        MDIViewClasses.get(MDI_INDEX).AddNewDataLoopURLListView(DataFile);
    }
                
            
      if (Procedure.hasAttribute("Random"))
  {
  String stRand = Procedure.getAttribute("Random");
    Boolean Rand = false;
    if (stRand.equals("true"))
    {
        Rand = true;
    }

   MDIDataClasses.get(MDI_INDEX).BugArray.get(newbug_index).setRandom(Rand);
   MDIViewClasses.get(MDI_INDEX).BugViewArray.get(newbug_index).setRandom(Rand);
  }
    if (Procedure.hasAttribute("Limit"))
  {
    int limit = Integer.parseInt(Procedure.getAttribute("Limit"));
  MDIDataClasses.get(MDI_INDEX).BugArray.get(newbug_index).setLimit(limit);
   MDIViewClasses.get(MDI_INDEX).BugViewArray.get(newbug_index).setLimit(limit);
  
  }
    }
    else
    {
    MDIDataClasses.get(MDI_INDEX).AddNewBug(); 
    MDIViewClasses.get(MDI_INDEX).AddNewBugView();
    }
    
    
   
     MDIDataClasses.get(MDI_INDEX).BugArray.get(newbug_index).setBugTitle(Procedure.getAttribute("Title"));
     MDIViewClasses.get(MDI_INDEX).BugViewArray.get(newbug_index).setBugTitle(Procedure.getAttribute("Title"));
   
     MDIDataClasses.get(MDI_INDEX).BugArray.get(newbug_index).setBugURL(Procedure.getAttribute("URL"));
     
    // not needed?
  //  String stPass = Procedure.getAttribute("Pass");
  //  Boolean Pass = false;
 //   if (stPass.equals("true"))
 //   {
 //       Pass = true;
 //   }
 //    MDIDataClasses.get(MDI_INDEX).BugArray.get(newbug_index).setPass(Pass);

    NodeList ActionsList = Procedure.getElementsByTagName("Action");
  
    for (int j = 0; j <ActionsList.getLength(); j++)
    {
        
   Element Action = (Element) ActionsList.item(j);
   NodeList ActionNodes = Action.getChildNodes();
   String thisActionNodeName = "none";
   String thisActionNodeValue = "none";
   
   String Variable1 = "";
   String Variable2 = "";
   String Password = "";
   String LOCKED = "false";
   String BoolVal1 = "false";
   String BoolVal2 = "false";
    String TimeOfTest;
    String ActionType = "none";
    String ActionIndex;
    String ActionPass;
    
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
    
   Procedure NewProcedure =  MDIDataClasses.get(MDI_INDEX).BugArray.get(newbug_index);
   ProcedureView NewProcedureView =  MDIViewClasses.get(MDI_INDEX).BugViewArray.get(newbug_index);
  
   
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
               thisActionViewToAdd.AddListeners(thisActionToAdd, MDIViewClasses.get(MDI_INDEX), MDIDataClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, MDIViewClasses.get(MDI_INDEX), MDIDataClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
               MDIDataClasses.get(MDI_INDEX).AddActionToArray(thisActionToAdd, NewProcedure);
               MDIViewClasses.get(MDI_INDEX).AddActionViewToArray(thisActionViewToAdd, NewProcedureView);
               
           }      
 
     if (thisPassFailActionHashMap.containsKey(ActionType))
             {
               Action thisActionToAdd = (Action) thisPassFailActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisPassFailActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, RealBoolVal2, boolLOCKED);
              thisActionViewToAdd.AddListeners(thisActionToAdd, MDIViewClasses.get(MDI_INDEX), MDIDataClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, MDIViewClasses.get(MDI_INDEX), MDIDataClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
               MDIDataClasses.get(MDI_INDEX).AddActionToArray(thisActionToAdd, NewProcedure);
               MDIViewClasses.get(MDI_INDEX).AddActionViewToArray(thisActionViewToAdd, NewProcedureView);
             }
            
// MDIClasses.get(MDI_INDEX).UpdateDisplay();
        }   
  
    }     
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
