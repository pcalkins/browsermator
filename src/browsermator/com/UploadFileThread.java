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
 STAppController mainApp;
 SeleniumTestTool STAppFrame;
 int calling_MDI_Index;
String Header;
String Footer;
final JFXPanel fxPanel = new JFXPanel();
WebView browser2;
WebEngine webEngine;
Stage webStage;
JPanel mainPanel;
JFrame ReportFrame;


 public UploadFileThread(STAppController mainApp, SeleniumTestTool STAppFrame, int calling_MDI_Index)
 {
   this.mainApp = mainApp;  
   this.STAppFrame = STAppFrame;
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
   mainApp.LoadNameAndPassword();
 
  mainApp.LookUpUser(mainApp.loginName, mainApp.loginPassword);
   if (mainApp.user_id >0 )
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
     loginDialog.setLoginName(mainApp.loginName);
  loginDialog.setPassword(mainApp.loginPassword);
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
       mainApp.LookUpUser(loginDialog.getLoginName(), loginDialog.getPassword());
  if (mainApp.user_id >0 )
   {
       
   
     try
                      {
                           mainApp.SaveNameAndPassword(loginDialog.getLoginName(), loginDialog.getPassword());
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
      if (mainApp.user_id==0)
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
      mainApp.LookUpUser(loginDialog.getLoginName(), loginDialog.getPassword());
       if (mainApp.user_id >0 )
   {
       
   
     try
                      {
                          mainApp.SaveNameAndPassword(loginDialog.getLoginName(), loginDialog.getPassword());
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
     String statustext = mainApp.RecoverPassword(loginDialog.getEmail());
     loginDialog.setStatus (statustext);
     
       }
       if ("register".equals(loginDialog.mode)&& loginDialog.isActive)
       {
           loginDialog.setStatus("");
      String statustext = mainApp.RegisterUser(loginDialog, loginDialog.getLoginName(), loginDialog.getEmail(), loginDialog.getPassword());
      if ("Success".equals(statustext))
      {
             try
     {
      UploadFile(STAppFrame);
        mainApp.SaveNameAndPassword(mainApp.loginName, mainApp.loginPassword);
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
       String statustext = mainApp.RegisterUser(loginDialog, loginDialog.getLoginName(), loginDialog.getEmail(), loginDialog.getPassword());
        loginDialog.setStatus(statustext);
  
        }
      }
    );
     loginDialog.addjButtonRecoverPasswordActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
       String status =  mainApp.RecoverPassword(loginDialog.getEmail());
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
  mainApp.Navigator.setCursor(Cursor.getDefaultCursor());   
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

   
   String[] left_side_of_dot = STAppFrame.filename.split("\\.");
   String[] noslashes_name = left_side_of_dot[0].split("\\\\");
         int lastbit_index = noslashes_name.length-1;
         
   String noext_filename = noslashes_name[lastbit_index];
   
   
File file= File.createTempFile(noext_filename, ".browsermation"); 
  
  

               XMLStreamWriter xmlfile = XMLOutputFactory.newInstance().createXMLStreamWriter( new BufferedOutputStream(
                              new FileOutputStream(file)));
     
             try {
xmlfile.writeStartElement("BrowserMatorWindow");
xmlfile.writeAttribute("Filename",noext_filename);
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
    
    String IncludeScreenshots = Boolean.toString(STAppFrame.getIncludeScreenshots());
xmlfile.writeStartElement("IncludeScreenshots");
    xmlfile.writeCharacters(IncludeScreenshots);
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
    xmlfile.writeCharacters("");
    xmlfile.writeEndElement(); 
// xmlfile.writeAttribute("SMTPHostname", STAppFrame.getSMTPHostname());
    
  String EmailLoginName = STAppFrame.getEmailLoginName();
xmlfile.writeStartElement("EmailLoginName");
    xmlfile.writeCharacters("");
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
 
    Integer Timeout = STAppFrame.getTimeout();
String TimeoutString = Timeout.toString();
xmlfile.writeStartElement("Timeout");
    xmlfile.writeCharacters(TimeoutString);
    xmlfile.writeEndElement();  
    
    
Integer Sessions = STAppFrame.getSessions();
String SessionsString = Sessions.toString();
xmlfile.writeStartElement("Sessions");
    xmlfile.writeCharacters(SessionsString);
    xmlfile.writeEndElement();  


String OSType = STAppFrame.OSType;
xmlfile.writeStartElement("OSType");
    xmlfile.writeCharacters(OSType);
    xmlfile.writeEndElement();  

xmlfile.writeStartElement("EmailPassword");
    xmlfile.writeCharacters("");
    xmlfile.writeEndElement();    
// xmlfile.writeAttribute("EmailPassword", EmailPassword);
String EmailTo = STAppFrame.getEmailTo();
xmlfile.writeStartElement("EmailTo");
    xmlfile.writeCharacters("");
    xmlfile.writeEndElement();  
// xmlfile.writeAttribute("EmailTo", STAppFrame.getEmailTo());

String EmailFrom = STAppFrame.getEmailFrom();
xmlfile.writeStartElement("EmailFrom");
    xmlfile.writeCharacters("");
    xmlfile.writeEndElement(); 
// xmlfile.writeAttribute("EmailFrom", STAppFrame.getEmailFrom());
String EmailSubject = STAppFrame.getSubject();
xmlfile.writeStartElement("EmailSubject");
    xmlfile.writeCharacters("");
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
String LockedString = Boolean.toString(thisbug.getLocked());
xmlfile.writeAttribute("Locked", LockedString);

if ("Dataloop".equals(thisbug.Type))
{
String string_limit = Integer.toString(thisbug.limit);

xmlfile.writeAttribute("DataLoopFile", thisbug.DataSet.DataFile);
xmlfile.writeAttribute("Limit", string_limit);

String string_randval = "false";
if (thisbug.random)
{
    string_randval = "true";
}
xmlfile.writeAttribute("Random", string_randval);



}


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
      SendFileThread sendREF = new SendFileThread(mainApp, thisUpDiag,file, mainApp.loginName, mainApp.loginPassword);
       sendREF.execute();
  
        }
      }
    );
      thisUpDiag.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
}
 
 
 

    
}
