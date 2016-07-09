/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Cursor;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.SAVE_DIALOG;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author pcalkins
 */
public class SaveFileThread extends SwingWorker<String, Integer>{
 STAppController mainApp;
 SeleniumTestTool STAppFrame;
 boolean isSaveAs;
 boolean isFlatten;
 int calling_MDI_Index;
    public SaveFileThread(STAppController mainApp, SeleniumTestTool STAppFrame, boolean isSaveAs, boolean isFlatten, int calling_MDI_Index)
 {
   this.mainApp = mainApp;  
   this.STAppFrame = STAppFrame;
   this.isSaveAs = isSaveAs;
   this.isFlatten = isFlatten;
   this.calling_MDI_Index = calling_MDI_Index;
 }
    @Override 
public String doInBackground()
 {
   if (calling_MDI_Index == -1)
   {
       mainApp.Navigator.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
   }
   else
   {
     STAppFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
   }
     if (isFlatten)
     {
         STAppFrame.setFlattenFileButtonName("Flattenning...");
     }
     try
                      {
                     SaveFile(STAppFrame, isSaveAs, isFlatten);
            
                      }
                      catch (Exception e)
                      {
                        System.out.println("Save Changes exception:" + e.toString());  
                      
                      } 
     return "done";
 }
@Override
 protected void done()
 {
     if (calling_MDI_Index == -1)
   { 
  mainApp.Navigator.setCursor(Cursor.getDefaultCursor());   
   }
      else
   {
     STAppFrame.setCursor(Cursor.getDefaultCursor());
   }
     if (isFlatten)
     {
         STAppFrame.setFlattenFileButtonName("Flattenning...");
         
     }
     
 }
 @Override
 protected void process ( List <Integer> bugindex)
 {
     
 }
  public void SaveFile(SeleniumTestTool STAppFrame, boolean isSaveAs, boolean isFlatten) throws IOException, XMLStreamException
    {

      final JFileChooser fc = new JFileChooser(){
    @Override
    public void approveSelection(){
        
        File f = getSelectedFile();
                String filestring = f.toString();
                
                String[] left_side_of_dot = filestring.split("\\.");
                
                 f = new File(left_side_of_dot[0] + ".browsermation");
       
       
        if(f.exists() && getDialogType() == SAVE_DIALOG){
            int result = JOptionPane.showConfirmDialog(STAppFrame,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
            switch(result){
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                 
                    return;
                case JOptionPane.NO_OPTION:
                    return;
                case JOptionPane.CLOSED_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }
        super.approveSelection();
    STAppFrame.changes = false;
    }
};
File file=null;
   
    if (isSaveAs==true || STAppFrame.filename.contains("untitled") == true)
    {
     FileNameExtensionFilter filefilter = new FileNameExtensionFilter("Browsermator file (*.browsermation)","browsermation");

    fc.setFileFilter(filefilter);
    if (STAppFrame.filename.contains("untitled") == false  && isSaveAs==true)
    {
          String[] left_side_of_dot = STAppFrame.filename.split("\\.");
                if (isFlatten)
                {
                 file = new File(left_side_of_dot[0] + "-flat.browsermation");   
                }
                else
                {
                 file = new File(left_side_of_dot[0] + ".browsermation");
                }
        fc.setSelectedFile(file);
        
    }
int returnVal = fc.showSaveDialog(STAppFrame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                String filestring = file.toString();
                
                String[] left_side_of_dot = filestring.split("\\.");
                
                 file = new File(left_side_of_dot[0] + ".browsermation");
            
            }
            else
            {
            
            }
    }
    else
    {
    
         String filestring = STAppFrame.filename;
                
                String[] left_side_of_dot = filestring.split("\\.");
                
                 file = new File(left_side_of_dot[0] + ".browsermation");
    }
  

               XMLStreamWriter xmlfile = XMLOutputFactory.newInstance().createXMLStreamWriter( new BufferedOutputStream(
                              new FileOutputStream(file)));
     
             try {
xmlfile.writeStartElement("BrowserMatorWindow");
xmlfile.writeAttribute("Filename",file.getName());
xmlfile.writeAttribute("ProgramVersion", mainApp.ProgramVersion);
String ShowReport = Boolean.toString(STAppFrame.getShowReport());

xmlfile.writeStartElement("FileSettings");

xmlfile.writeStartElement("ShowReport");
    xmlfile.writeCharacters(ShowReport);
    xmlfile.writeEndElement();
// xmlfile.writeAttribute("ShowReport", ShowReport);
String EmailReport = Boolean.toString(STAppFrame.getEmailReport());
  xmlfile.writeStartElement("EmailReport");
    xmlfile.writeCharacters(EmailReport);
    xmlfile.writeEndElement();
// xmlfile.writeAttribute("EmailReport", EmailReport);
String EmailReportFail = Boolean.toString(STAppFrame.getEmailReportFail());
xmlfile.writeStartElement("EmailReportFail");
    xmlfile.writeCharacters(EmailReportFail);
    xmlfile.writeEndElement();
    
// xmlfile.writeAttribute("EmailReportFail", EmailReportFail);
String ExitAfter = Boolean.toString(STAppFrame.getExitAfter());
xmlfile.writeStartElement("ExitAfter");
    xmlfile.writeCharacters(ExitAfter);
    xmlfile.writeEndElement();    
// xmlfile.writeAttribute("ExitAfter", ExitAfter);
   String SMTPHostname = STAppFrame.getSMTPHostname();
xmlfile.writeStartElement("SMTPHostname");
    xmlfile.writeCharacters(SMTPHostname);
    xmlfile.writeEndElement(); 
// xmlfile.writeAttribute("SMTPHostname", STAppFrame.getSMTPHostname());
    
  String EmailLoginName = STAppFrame.getEmailLoginName();
xmlfile.writeStartElement("EmailLoginName");
    xmlfile.writeCharacters(EmailLoginName);
    xmlfile.writeEndElement();     
// xmlfile.writeAttribute("EmailLoginName", STAppFrame.getEmailLoginName());
String PromptToClose = Boolean.toString(STAppFrame.getPromptToClose());
xmlfile.writeStartElement("PromptToClose");
    xmlfile.writeCharacters(PromptToClose);
    xmlfile.writeEndElement();     
// String PromptToClose = Boolean.toString(STAppFrame.getPromptToClose());
//    xmlfile.writeAttribute("PromptToClose", PromptToClose);
String TargetBrowser = STAppFrame.TargetBrowser;
xmlfile.writeStartElement("TargetBrowser");
    xmlfile.writeCharacters(TargetBrowser);
    xmlfile.writeEndElement();   
// xmlfile.writeAttribute("TargetBrowser", TargetBrowser);
    
Integer WaitTime = STAppFrame.GetWaitTime();
String WaitTimeString = WaitTime.toString();
xmlfile.writeStartElement("WaitTime");
    xmlfile.writeCharacters(WaitTimeString);
    xmlfile.writeEndElement();   
// xmlfile.writeAttribute("WaitTime", WaitTimeString);

String OSType = STAppFrame.OSType;
xmlfile.writeStartElement("OSType");
    xmlfile.writeCharacters(OSType);
    xmlfile.writeEndElement();  
// xmlfile.writeAttribute("OSType", OSType);
String EmailPassword = "";
EmailPassword = STAppFrame.getEmailPassword();

String une = "";
      try
      {
     une = Protector.encrypt(EmailPassword);
      }
      catch (Exception e)
      {
      System.out.println("Error encrypting emailpassword: " + e.toString());
      }
      EmailPassword = une;
xmlfile.writeStartElement("EmailPassword");
    xmlfile.writeCharacters(EmailPassword);
    xmlfile.writeEndElement();    
// xmlfile.writeAttribute("EmailPassword", EmailPassword);
String EmailTo = STAppFrame.getEmailTo();
xmlfile.writeStartElement("EmailTo");
    xmlfile.writeCharacters(EmailTo);
    xmlfile.writeEndElement();  
// xmlfile.writeAttribute("EmailTo", STAppFrame.getEmailTo());

String EmailFrom = STAppFrame.getEmailFrom();
xmlfile.writeStartElement("EmailFrom");
    xmlfile.writeCharacters(EmailFrom);
    xmlfile.writeEndElement(); 
// xmlfile.writeAttribute("EmailFrom", STAppFrame.getEmailFrom());
String EmailSubject = STAppFrame.getSubject();
xmlfile.writeStartElement("EmailSubject");
    xmlfile.writeCharacters(EmailSubject);
    xmlfile.writeEndElement();     
// xmlfile.writeAttribute("EmailSubject", STAppFrame.getSubject());
xmlfile.writeEndElement();

for (Procedure thisbug: STAppFrame.BugArray)
{

xmlfile.writeStartElement("Procedure");
xmlfile.writeAttribute("Title", thisbug.BugTitle);
xmlfile.writeAttribute("URL", thisbug.BugURL);
xmlfile.writeAttribute("Pass", Boolean.toString(thisbug.Pass));
String index = String.valueOf(thisbug.index);
xmlfile.writeAttribute("index", index);
xmlfile.writeAttribute("Type", thisbug.Type);   

if ("Dataloop".equals(thisbug.Type))
{

xmlfile.writeAttribute("DataLoopFile", thisbug.DataFile);

}
 
  if (isFlatten==false)
  {
    for (Action thisaction: thisbug.ActionsList)
    {
    xmlfile.writeStartElement("Action");

    String LOCKED = Boolean.toString(thisaction.Locked);
   
    xmlfile.writeStartElement("LOCKED");
    xmlfile.writeCharacters(LOCKED);
    xmlfile.writeEndElement();
    
   
        
    String Pass = Boolean.toString(thisaction.Pass);
    xmlfile.writeStartElement("Pass");
    xmlfile.writeCharacters(Pass);
    xmlfile.writeEndElement();
    
    xmlfile.writeStartElement("TimeOfTest");
    xmlfile.writeCharacters(thisaction.TimeOfTest.format(DateTimeFormatter.ISO_DATE_TIME));
    xmlfile.writeEndElement();
    
    xmlfile.writeStartElement("Type");
    xmlfile.writeCharacters(thisaction.Type);
    xmlfile.writeEndElement();
    
    xmlfile.writeStartElement("Variable1");
    
    xmlfile.writeCharacters(thisaction.Variable1);
    xmlfile.writeEndElement();
    if (thisaction.Type.contains("Password"))
    {
    xmlfile.writeStartElement("Variable2");
    try
    {
    thisaction.Variable2 = Protector.encrypt(thisaction.Variable2);
    }
    catch (Exception e)
            {
            System.out.println("encrypt error.. passvar2: " + e.toString());
            }
    xmlfile.writeCharacters(thisaction.Variable2);
    xmlfile.writeEndElement(); 
    }
    else
    {
    xmlfile.writeStartElement("Variable2");
    xmlfile.writeCharacters(thisaction.Variable2);
    xmlfile.writeEndElement();
    }
    
    xmlfile.writeStartElement("BoolVal1");
    String tempstringbool = "false";
    if (thisaction.BoolVal1)
    {
        tempstringbool = "true";
    }
    xmlfile.writeCharacters(tempstringbool);
    xmlfile.writeEndElement();
    String ActionIndex = Integer.toString(thisaction.index);   
    xmlfile.writeStartElement("ActionIndex");
    xmlfile.writeCharacters(ActionIndex);
    xmlfile.writeEndElement();
    
    xmlfile.writeEndElement();  
    }
  }
  else
  {
       int number_of_rows = thisbug.DataSet.DataTable.getRowCount();
  for( Action ThisAction : thisbug.ActionsList ) { 
 ThisAction.InitializeLoopTestVars(number_of_rows);
  } 
int action_index_for_flatten = 0;
 for (int x = 0; x<number_of_rows; x++)
    {
     for (Action thisaction: thisbug.ActionsList)
    {
         String original_value1 = thisaction.Variable1;
           String original_value2 = thisaction.Variable2;
               DataLoopVarParser var1Parser = new DataLoopVarParser(thisaction.Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(thisaction.Variable2);
    if (var1Parser.hasDataLoopVar==false && var2Parser.hasDataLoopVar==false)
    {
        xmlfile.writeStartElement("Action");

    String LOCKED = Boolean.toString(thisaction.Locked);
   
    xmlfile.writeStartElement("LOCKED");
    xmlfile.writeCharacters(LOCKED);
    xmlfile.writeEndElement();
    
   
        
    String Pass = Boolean.toString(thisaction.Pass);
    xmlfile.writeStartElement("Pass");
    xmlfile.writeCharacters(Pass);
    xmlfile.writeEndElement();
    
    xmlfile.writeStartElement("TimeOfTest");
    xmlfile.writeCharacters(thisaction.TimeOfTest.format(DateTimeFormatter.ISO_DATE_TIME));
    xmlfile.writeEndElement();
    
    xmlfile.writeStartElement("Type");
    xmlfile.writeCharacters(thisaction.Type);
    xmlfile.writeEndElement();
    
    xmlfile.writeStartElement("Variable1");
    
    xmlfile.writeCharacters(thisaction.Variable1);
    xmlfile.writeEndElement();
    if (thisaction.Type.contains("Password"))
    {
    xmlfile.writeStartElement("Variable2");
    try
    {
    thisaction.Variable2 = Protector.encrypt(thisaction.Variable2);
    }
    catch (Exception e)
            {
            System.out.println("encrypt error.. passvar2: " + e.toString());
            }
    xmlfile.writeCharacters(thisaction.Variable2);
    xmlfile.writeEndElement(); 
    }
    else
    {
    xmlfile.writeStartElement("Variable2");
    xmlfile.writeCharacters(thisaction.Variable2);
    xmlfile.writeEndElement();
    }
    
    xmlfile.writeStartElement("BoolVal1");
    String tempstringbool = "false";
    if (thisaction.BoolVal1)
    {
        tempstringbool = "true";
    }
    xmlfile.writeCharacters(tempstringbool);
    xmlfile.writeEndElement();
    String ActionIndex = Integer.toString(action_index_for_flatten);   
    
    xmlfile.writeStartElement("ActionIndex");
    xmlfile.writeCharacters(ActionIndex);
    action_index_for_flatten++;
    xmlfile.writeEndElement();
    
    xmlfile.writeEndElement();  
    }
    else
    {
               String concat_variable;
            String concat_variable2;
 concat_variable = var1Parser.GetFullValue(x, thisbug.DataSet);
 if (!"".equals(concat_variable))
 {
      thisaction.Variable1 = concat_variable;
 }
      concat_variable2 = var2Parser.GetFullValue(x, thisbug.DataSet);
     if (!"".equals(concat_variable2))
     {
      thisaction.Variable2 = concat_variable2;  
     }   
           xmlfile.writeStartElement("Action");

    String LOCKED = Boolean.toString(thisaction.Locked);
   
    xmlfile.writeStartElement("LOCKED");
    xmlfile.writeCharacters(LOCKED);
    xmlfile.writeEndElement();
    
   
        
    String Pass = Boolean.toString(thisaction.Pass);
    xmlfile.writeStartElement("Pass");
    xmlfile.writeCharacters(Pass);
    xmlfile.writeEndElement();
    
    xmlfile.writeStartElement("TimeOfTest");
    xmlfile.writeCharacters(thisaction.TimeOfTest.format(DateTimeFormatter.ISO_DATE_TIME));
    xmlfile.writeEndElement();
    
    xmlfile.writeStartElement("Type");
    xmlfile.writeCharacters(thisaction.Type);
    xmlfile.writeEndElement();
    
    xmlfile.writeStartElement("Variable1");
    
    xmlfile.writeCharacters(thisaction.Variable1);
    xmlfile.writeEndElement();
    if (thisaction.Type.contains("Password"))
    {
    xmlfile.writeStartElement("Variable2");
    try
    {
    thisaction.Variable2 = Protector.encrypt(thisaction.Variable2);
    }
    catch (Exception e)
            {
            System.out.println("encrypt error.. passvar2: " + e.toString());
            }
    xmlfile.writeCharacters(thisaction.Variable2);
    xmlfile.writeEndElement(); 
    }
    else
    {
    xmlfile.writeStartElement("Variable2");
    xmlfile.writeCharacters(thisaction.Variable2);
    xmlfile.writeEndElement();
    }
    
    xmlfile.writeStartElement("BoolVal1");
    String tempstringbool = "false";
    if (thisaction.BoolVal1)
    {
        tempstringbool = "true";
    }
    xmlfile.writeCharacters(tempstringbool);
    xmlfile.writeEndElement();
    String ActionIndex = Integer.toString(action_index_for_flatten);   
    xmlfile.writeStartElement("ActionIndex");
    xmlfile.writeCharacters(ActionIndex);
    action_index_for_flatten++;
    xmlfile.writeEndElement();
    
    xmlfile.writeEndElement();    
        thisaction.Variable1 = original_value1;
   thisaction.Variable2 = original_value2;
    }
  
    }
    }
  }
    xmlfile.writeEndElement();
  
}
xmlfile.writeEndElement();
// STAppFrame.AllFieldValues.clear();


// System.out.println("Successfully Created XML...");
  
 
        } catch (Exception e) {
           System.out.println("Write error:" + e.toString());
 
        } finally {
            xmlfile.flush();
            xmlfile.close();
            if (isFlatten)
            {

     OpenFileThread OPENREF = new OpenFileThread(mainApp, file, mainApp.MDIClasses, calling_MDI_Index, true, false);
  OPENREF.execute();
            }
            else
            {
 
    STAppFrame.setProperties(file.getAbsolutePath());
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
STAppFrame.changes = false;
            }
        }
if (isFlatten==false)
{
mainApp.filename = file.getAbsolutePath();
STAppFrame.setProperties(mainApp.filename);
mainApp.Navigator.addRecentFile(file.getAbsolutePath());

}
else
{
   
}
     
        }
}
