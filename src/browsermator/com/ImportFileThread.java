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
public class ImportFileThread extends SwingWorker<String, Integer>{
File[] files;
ArrayList<SeleniumTestTool> MDIClasses;
STAppController mainApp;
int calling_MDI_Index;

boolean RunIt;

    public ImportFileThread(STAppController mainApp, File[] files, int calling_MDI_Index)
{
  
  this.mainApp = mainApp;
  this.files = files;
  this.calling_MDI_Index = calling_MDI_Index;
  this.MDIClasses = mainApp.MDIClasses;
}
@Override 
public String doInBackground()
 {
     mainApp.MDIClasses.get(calling_MDI_Index).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
  //    MDIClasses.get(calling_MDI_Index).UpdateDisplay();  
   mainApp.MDIClasses.get(calling_MDI_Index).setCursor(Cursor.getDefaultCursor()); 
 
     
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
    int newbug_index = MDIClasses.get(MDI_INDEX).BugArray.size();
   Element Procedure = (Element) ProcedureList.item(i);
     String ProcType = Procedure.getAttribute("Type");
 
    if ("Dataloop".equals(ProcType))
    {
      
         String DataFile = Procedure.getAttribute("DataLoopFile");
        File DataFile_file = new File(DataFile);
        if (DataFile_file.exists())
        {
            MDIClasses.get(MDI_INDEX).AddNewDataLoop(DataFile_file);
         
        }
        else
        {
           MDIClasses.get(MDI_INDEX).AddNewBug();  
        }
    }
    else
    {
    MDIClasses.get(MDI_INDEX).AddNewBug(); 
    }
    
    
   
     MDIClasses.get(MDI_INDEX).BugArray.get(newbug_index).BugTitle = Procedure.getAttribute("Title");
     MDIClasses.get(MDI_INDEX).BugViewArray.get(newbug_index).JTextFieldBugTitle.setText(Procedure.getAttribute("Title"));
     MDIClasses.get(MDI_INDEX).BugArray.get(newbug_index).BugURL = Procedure.getAttribute("URL");
    
    String stPass = Procedure.getAttribute("Pass");
    Boolean Pass = false;
    if (stPass.equals("true"))
    {
        Pass = true;
    }
     MDIClasses.get(MDI_INDEX).BugArray.get(newbug_index).Pass = Pass;

    NodeList ActionsList = Procedure.getElementsByTagName("Action");
   int thislength = ActionsList.getLength();

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
    String TimeOfTest;
    String ActionType = "none";
    String ActionIndex;
    String ActionPass;
    
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
    
   Procedure NewProcedure =  MDIClasses.get(MDI_INDEX).BugArray.get(newbug_index);
   ProcedureView NewProcedureView =  MDIClasses.get(MDI_INDEX).BugViewArray.get(newbug_index);
  
   
   if (ActionType.contains("Password"))
   {
       try
       {
       Password = Protector.decrypt(Variable2);
     
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
               thisActionViewToAdd.AddListeners(thisActionToAdd, MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
               MDIClasses.get(MDI_INDEX).AddActionToArray(thisActionToAdd, thisActionViewToAdd,NewProcedure, NewProcedureView);
               
           }      
 
     if (thisPassFailActionHashMap.containsKey(ActionType))
             {
               Action thisActionToAdd = (Action) thisPassFailActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisPassFailActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1);
               thisActionViewToAdd.AddListeners(thisActionToAdd, MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
               MDIClasses.get(MDI_INDEX).AddActionToArray(thisActionToAdd, thisActionViewToAdd,NewProcedure, NewProcedureView);
             }
            

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
 MDIClasses.get(MDI_INDEX).changes = true;
}
    
  }

  }
 
}
