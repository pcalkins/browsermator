/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.embed.swing.JFXPanel;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 *
 * @author pcalkins
 */
public class UploadFileThread extends SwingWorker<String, Integer>{
 STAppController mainAppController;
 MainAppFrame mainAppFrame;
 SeleniumTestTool STAppFrame;
 SeleniumTestToolData STAppData;
 int calling_MDI_Index;
String Header;
String Footer;
final JFXPanel fxPanel = new JFXPanel();
WebView browser2;
WebEngine webEngine;
Stage webStage;
JPanel mainPanel;
JFrame ReportFrame;


 public UploadFileThread(STAppController in_mainAppController, MainAppFrame in_mainAppFrame, SeleniumTestTool in_STAppFrame, SeleniumTestToolData in_STAppData, int calling_MDI_Index)
 {
   this.mainAppFrame = in_mainAppFrame;
   this.mainAppController = in_mainAppController;
   this.STAppFrame = in_STAppFrame;
   this.STAppData = in_STAppData;
   this.calling_MDI_Index = calling_MDI_Index;

 }
    @Override 
public String doInBackground()
 {
   if (calling_MDI_Index == -1)
   {
      mainAppController.Navigator.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
   }
   else
   {
     STAppFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
   }
   mainAppController.LoadNameAndPassword();
 
  mainAppController.LookUpUser(mainAppController.loginName, mainAppController.loginPassword);
   if (mainAppController.user_id >0 )
   {
       
   
     try
                      {
                          
                     UploadFile(STAppFrame);
                     
                      }
                      catch (Exception e)
                      {
                        System.out.println("Upload file exception:" + e.toString());  
                      
                      } 
     return "done";
   }
   else
   {
     Login_Register_Dialog loginDialog = new Login_Register_Dialog();
     loginDialog.setLoginName(mainAppController.loginName);
  loginDialog.setPassword(mainAppController.loginPassword);
     loginDialog.addjTextFieldConfirmPasswordDocListener(
             new DocumentListener()
           {
@Override
       public void changedUpdate(DocumentEvent documentEvent) {
       loginDialog.ComparePasswordFields();
       loginDialog.AllRequiredCheck();
      }
@Override
      public void insertUpdate(DocumentEvent documentEvent) {
      loginDialog.ComparePasswordFields();
      loginDialog.AllRequiredCheck();
      }
@Override
      public void removeUpdate(DocumentEvent documentEvent) {
     loginDialog.ComparePasswordFields();
     loginDialog.AllRequiredCheck();
      }
      }
                 );
 loginDialog.addjTextFieldPasswordDocListener(
             new DocumentListener()
           {
@Override
       public void changedUpdate(DocumentEvent documentEvent) {
       loginDialog.ComparePasswordFields();
       loginDialog.AllRequiredCheck();

      }
@Override
      public void insertUpdate(DocumentEvent documentEvent) {
      loginDialog.ComparePasswordFields();
      loginDialog.AllRequiredCheck();
      }
@Override
      public void removeUpdate(DocumentEvent documentEvent) {
     loginDialog.ComparePasswordFields();
     loginDialog.AllRequiredCheck();
      }
      }
                 );
    
     loginDialog.addjTextFieldLoginNameDocListener(
             new DocumentListener()
           {
@Override
       public void changedUpdate(DocumentEvent documentEvent) {
      
       loginDialog.AllRequiredCheck();

      }
@Override
      public void insertUpdate(DocumentEvent documentEvent) {
    
      loginDialog.AllRequiredCheck();
      }
@Override
      public void removeUpdate(DocumentEvent documentEvent) {
    
     loginDialog.AllRequiredCheck();
      }
      }
                 );
     loginDialog.addjTextFieldEmailDocListener(
             new DocumentListener()
           {
@Override
       public void changedUpdate(DocumentEvent documentEvent) {
      
       loginDialog.AllRequiredCheck();
       loginDialog.ValidateEmailAddress();
      }
@Override
      public void insertUpdate(DocumentEvent documentEvent) {
    
      loginDialog.AllRequiredCheck();
      loginDialog.ValidateEmailAddress();
      }
@Override
      public void removeUpdate(DocumentEvent documentEvent) {
    
     loginDialog.AllRequiredCheck();
     loginDialog.ValidateEmailAddress();
      }
      }
                 );
      loginDialog.addjButtonLoginModeActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
        loginDialog.LoginMode();
 
  
        }
      }
    );
   
       loginDialog.addjButtonRegisterModeActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
        loginDialog.RegisterMode();
 
  
        }
      }
    );
        loginDialog.addjButtonRecoverModeActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
        loginDialog.RecoverMode();
 
  
        }
      }
    );
     loginDialog.addjButtonLoginActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 loginDialog.setStatus("");
       mainAppController.LookUpUser(loginDialog.getLoginName(), loginDialog.getPassword());
  if (mainAppController.user_id >0 )
   {
       
   
     try
                      {
                           mainAppController.SaveNameAndPassword(loginDialog.getLoginName(), loginDialog.getPassword());
                     UploadFile(STAppFrame);
                     loginDialog.dispose();
                      }
                      catch (Exception e)
                      {
                        System.out.println("Upload file exception:" + e.toString());  
                      
                      } 
    loginDialog.setStatus("");
   }
  else
  {
      if (mainAppController.user_id==0)
      {
      loginDialog.setStatus("Login has failed.");
      }
      else
      {
      loginDialog.setStatus("Unable to connecto to Browsermator.com.");
      }
      }
  
        }
      }
    );
        loginDialog.addjTextFieldPasswordActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
         
       if ("login".equals(loginDialog.mode) && loginDialog.isActive )
       {
      loginDialog.setStatus("");
      mainAppController.LookUpUser(loginDialog.getLoginName(), loginDialog.getPassword());
       if (mainAppController.user_id >0 )
   {
       
   
     try
                      {
                          mainAppController.SaveNameAndPassword(loginDialog.getLoginName(), loginDialog.getPassword());
                     UploadFile(STAppFrame);
                     loginDialog.dispose();
                      }
                      catch (Exception e)
                      {
                        System.out.println("Upload file exception:" + e.toString());  
                     
                      } 
    loginDialog.setStatus("");
   }
        else
  {
      loginDialog.setStatus("Login has failed.");
  }
       }
  
        }
      }
    );
        loginDialog.addjTextFieldEmailActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
       if ("recover".equals(loginDialog.mode)&& loginDialog.isActive)
       {
           loginDialog.setStatus("");
     String statustext = mainAppController.RecoverPassword(loginDialog.getEmail());
     loginDialog.setStatus (statustext);
     
       }
       if ("register".equals(loginDialog.mode)&& loginDialog.isActive)
       {
           loginDialog.setStatus("");
      String statustext = mainAppController.RegisterUser(loginDialog, loginDialog.getLoginName(), loginDialog.getEmail(), loginDialog.getPassword());
      if ("Success".equals(statustext))
      {
             try
     {
      UploadFile(STAppFrame);
        mainAppController.SaveNameAndPassword(mainAppController.loginName, mainAppController.loginPassword);
        loginDialog.dispose();
     }
      catch (Exception ex)
     {
         System.out.println("Exception registering user: " + ex.toString());
       
     }        
      }
      loginDialog.setStatus(statustext);
       }
  
        }
      }
    );
        
     loginDialog.addjButtonRegisterActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 loginDialog.setStatus("");
       String statustext = mainAppController.RegisterUser(loginDialog, loginDialog.getLoginName(), loginDialog.getEmail(), loginDialog.getPassword());
        loginDialog.setStatus(statustext);
  
        }
      }
    );
     loginDialog.addjButtonRecoverPasswordActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
       String status =  mainAppController.RecoverPassword(loginDialog.getEmail());
         loginDialog.setStatus(status);
  
        }
      }
    );
     
       return "done";
       
   }
 }
@Override
 protected void done()
 {
     if (calling_MDI_Index == -1)
   { 
  mainAppController.Navigator.setCursor(Cursor.getDefaultCursor());   
   }
      else
   {
     STAppFrame.setCursor(Cursor.getDefaultCursor());
   }
    
    
     
 }
 @Override
 protected void process ( List <Integer> bugindex)
 {
     
 }

 
  public void UploadFile(SeleniumTestTool STAppFrame) throws IOException, XMLStreamException
    {

   File thisfile = new File(STAppFrame.filename);
String just_filename = thisfile.getName();
   String[] left_side_of_dot = just_filename.split("\\.");
  String noext_filename = left_side_of_dot[0];
   
   
   
File file= File.createTempFile(noext_filename, ".browsermation"); 
  
  

               XMLStreamWriter xmlfile = XMLOutputFactory.newInstance().createXMLStreamWriter( new BufferedOutputStream(
                              new FileOutputStream(file)));
     
             try {
xmlfile.writeStartElement("BrowserMatorWindow");
xmlfile.writeAttribute("Filename",noext_filename);
xmlfile.writeAttribute("ProgramVersion", mainAppController.ProgramVersion);
String ShowReport = Boolean.toString(STAppData.getShowReport());

xmlfile.writeStartElement("FileSettings");

xmlfile.writeStartElement("ShowReport");
    xmlfile.writeCharacters(ShowReport);
    xmlfile.writeEndElement();
// xmlfile.writeAttribute("ShowReport", ShowReport);
String EmailReport = Boolean.toString(STAppData.getEmailReport());
  xmlfile.writeStartElement("EmailReport");
    xmlfile.writeCharacters(EmailReport);
    xmlfile.writeEndElement();
    
    String IncludeScreenshots = Boolean.toString(STAppData.getIncludeScreenshots());
xmlfile.writeStartElement("IncludeScreenshots");
    xmlfile.writeCharacters(IncludeScreenshots);
    xmlfile.writeEndElement();
// xmlfile.writeAttribute("EmailReport", EmailReport);
String EmailReportFail = Boolean.toString(STAppData.getEmailReportFail());
xmlfile.writeStartElement("EmailReportFail");
    xmlfile.writeCharacters(EmailReportFail);
    xmlfile.writeEndElement();
    
// xmlfile.writeAttribute("EmailReportFail", EmailReportFail);
String ExitAfter = Boolean.toString(STAppData.getExitAfter());
xmlfile.writeStartElement("ExitAfter");
    xmlfile.writeCharacters(ExitAfter);
    xmlfile.writeEndElement();    
// xmlfile.writeAttribute("ExitAfter", ExitAfter);
   String SMTPHostname = STAppData.getSMTPHostname();
xmlfile.writeStartElement("SMTPHostname");
    xmlfile.writeCharacters("");
    xmlfile.writeEndElement(); 
// xmlfile.writeAttribute("SMTPHostname", STAppFrame.getSMTPHostname());
    
  String EmailLoginName = STAppData.getEmailLoginName();
xmlfile.writeStartElement("EmailLoginName");
    xmlfile.writeCharacters("");
    xmlfile.writeEndElement();     
// xmlfile.writeAttribute("EmailLoginName", STAppFrame.getEmailLoginName());
String PromptToClose = Boolean.toString(STAppData.getPromptToClose());
xmlfile.writeStartElement("PromptToClose");
    xmlfile.writeCharacters(PromptToClose);
    xmlfile.writeEndElement();     
// String PromptToClose = Boolean.toString(STAppFrame.getPromptToClose());
//    xmlfile.writeAttribute("PromptToClose", PromptToClose);
String TargetBrowser = STAppData.getTargetBrowser();
xmlfile.writeStartElement("TargetBrowser");
    xmlfile.writeCharacters(TargetBrowser);
    xmlfile.writeEndElement();   
// xmlfile.writeAttribute("TargetBrowser", TargetBrowser);
    
Integer WaitTime = STAppData.getWaitTime();
String WaitTimeString = WaitTime.toString();
xmlfile.writeStartElement("WaitTime");
    xmlfile.writeCharacters(WaitTimeString);
    xmlfile.writeEndElement();   
 
    Integer Timeout = STAppData.getEcTimeout();
String TimeoutString = Timeout.toString();
xmlfile.writeStartElement("EcTimeout");
    xmlfile.writeCharacters(TimeoutString);
    xmlfile.writeEndElement();  
    
    
Integer Sessions = STAppData.getSessions();
String SessionsString = Sessions.toString();
xmlfile.writeStartElement("Sessions");
    xmlfile.writeCharacters(SessionsString);
    xmlfile.writeEndElement();  


String OSType = STAppData.getOSType();
xmlfile.writeStartElement("OSType");
    xmlfile.writeCharacters(OSType);
    xmlfile.writeEndElement();  

xmlfile.writeStartElement("EmailPassword");
    xmlfile.writeCharacters("");
    xmlfile.writeEndElement();    
// xmlfile.writeAttribute("EmailPassword", EmailPassword);
String EmailTo = STAppData.getEmailTo();
xmlfile.writeStartElement("EmailTo");
    xmlfile.writeCharacters("");
    xmlfile.writeEndElement();  
// xmlfile.writeAttribute("EmailTo", STAppFrame.getEmailTo());

String EmailFrom = STAppData.getEmailFrom();
xmlfile.writeStartElement("EmailFrom");
    xmlfile.writeCharacters("");
    xmlfile.writeEndElement(); 
// xmlfile.writeAttribute("EmailFrom", STAppFrame.getEmailFrom());
String EmailSubject = STAppData.getEmailSubject();
xmlfile.writeStartElement("EmailSubject");
    xmlfile.writeCharacters("");
    xmlfile.writeEndElement();     
// xmlfile.writeAttribute("EmailSubject", STAppFrame.getSubject());
xmlfile.writeEndElement();

for (Procedure thisbug: STAppData.BugArray)
{

xmlfile.writeStartElement("Procedure");
xmlfile.writeAttribute("Title", thisbug.getBugTitle());
xmlfile.writeAttribute("URL", thisbug.BugURL);
xmlfile.writeAttribute("Pass", Boolean.toString(thisbug.Pass));
String index = String.valueOf(thisbug.index);
xmlfile.writeAttribute("index", index);
xmlfile.writeAttribute("Type", thisbug.Type);
String LockedString = Boolean.toString(thisbug.getLocked());
xmlfile.writeAttribute("Locked", LockedString);

if ("Dataloop".equals(thisbug.Type))
{
String string_limit = Integer.toString(thisbug.getLimit());

xmlfile.writeAttribute("DataLoopFile", thisbug.DataFile);
xmlfile.writeAttribute("DataLoopSource", thisbug.DataLoopSource);
xmlfile.writeAttribute("Limit", string_limit);

String string_randval = "false";
if (thisbug.getRandom())
{
    string_randval = "true";
}
xmlfile.writeAttribute("Random", string_randval);



}


    for (BMAction thisaction: thisbug.ActionsList)
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
    xmlfile.writeCharacters("");
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


    xmlfile.writeEndElement();
    }

xmlfile.writeEndElement();

           
  xmlfile.flush();
            xmlfile.close();
        } catch (Exception e) {
           System.out.println("Write error:" + e.toString());
 
        } finally {
     Upload_File_Dialog thisUpDiag = new Upload_File_Dialog(noext_filename);
      thisUpDiag.addjButtonUploadFileActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
       thisUpDiag.hideWarningMessage();
      SendFileThread sendREF = new SendFileThread(mainAppController, thisUpDiag,file, mainAppController.loginName, mainAppController.loginPassword);
       sendREF.execute();
  
        }
      }
    );
      thisUpDiag.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
}
 
 
 

    
}
