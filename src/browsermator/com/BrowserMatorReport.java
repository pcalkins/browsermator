/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.SAVE_DIALOG;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author pcalkins
 */
public class BrowserMatorReport {
JFrame ReportFrame;
JEditorPane HTMLPane;
JButton jButtonSaveFile;
String HTMLReport;
String TextReport;
BorderLayout thisLayout;
JPanel mainPanel;
SeleniumTestTool SiteTest;
String LineBreak;
String Header;
String Footer;
final JFXPanel fxPanel = new JFXPanel();
 WebView browser;
WebEngine webEngine;
Stage webStage;



    public BrowserMatorReport (SeleniumTestTool SiteTest)
 {
   this.SiteTest = SiteTest;
   
 }
    public String GetTextReport()
    {
        return this.TextReport;
    }
    public String GetHTMLReport()
    {
        return this.HTMLReport;
    }
     public void EmailReport ()
   {
            Properties applicationProps = new Properties();


   // String smtp_hostname = this.SiteTest.getSMTPHostname();
   String login_name = this.SiteTest.getEmailLoginName();
   
   String password = this.SiteTest.getEmailPassword();
   String to = this.SiteTest.getEmailTo();
   String from = this.SiteTest.getEmailFrom();
   String subject = this.SiteTest.getSubject();
   if (this.SiteTest.AllTestsPassed.equals(true))
   {
       subject = subject + " - All Procedures PASSED";
   }
   else
   {
       subject = subject + " - Some Procedures FAILED";
   }
  OutPutReports();
   
       
   applicationProps.put("mail.smtp.host", this.SiteTest.getSMTPHostname());
    applicationProps.put("email_login_name", this.SiteTest.getEmailLoginName());
     applicationProps.put("email_login_password", this.SiteTest.getEmailPassword());
      applicationProps.put("email_to", this.SiteTest.getEmailTo());
       applicationProps.put("email_from", this.SiteTest.getEmailFrom());
       applicationProps.put("email_subject", this.SiteTest.getSubject());
       
    Session session = Session.getInstance(applicationProps, null);

    try {
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(from);
        msg.setRecipients(Message.RecipientType.TO,
                          to);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(GetTextReport());
        Transport.send(msg, login_name, password);
    } catch (MessagingException mex) {
    //    System.out.println("send failed, exception: " + mex);
     Prompter thisContinuePrompt = new Prompter("Sending Email has failed. Check settings.");    
    }
   
   }
   
    public void ShowHTMLReport()
    {
        String HTML_TO_SEND = GetHTMLReport();
        jButtonSaveFile = new JButton("Save HTML Report");
   Platform.runLater(new Runnable() {
            @Override
            public void run() {
                    browser = new WebView();
webEngine = browser.getEngine();
webEngine.loadContent(HTML_TO_SEND);

 browser.setVisible(true);
Scene scene = new Scene(browser);
        fxPanel.setScene(scene);
        

            }
   });   

            fxPanel.setVisible(true);
fxPanel.setSize(800,800);

mainPanel = new JPanel(new BorderLayout());
  mainPanel.add(jButtonSaveFile, BorderLayout.NORTH);
    mainPanel.add(fxPanel, BorderLayout.CENTER);
    mainPanel.setVisible(true);

   ReportFrame = new JFrame("Browsermator Report");

    jButtonSaveFile.addActionListener(new ActionListener() {
        
       @Override
        public void actionPerformed (ActionEvent e )
        {
         SaveAsHTMLFile();   
        }
        });

  
    ReportFrame.add(mainPanel);
    ReportFrame.setSize(800, 800);
    ReportFrame.setVisible(true);   
     
     
             
    
    }
     public void OutPutReports()
     {
       this.HTMLReport = OutPutReport(true);
       this.TextReport = OutPutReport(false);
     }
     public String OutPutReport(boolean includescreens)
  {
     if (includescreens)
     {
         LineBreak = "<BR>"; 
         Header = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><HTML>\n<HEAD>\n<STYLE>#Report { height: 800; overflow-y: auto;}\n #Controls { }\n</STYLE>\n<SCRIPT>\nfunction ShowHideThisScreen(button_id)\n {alert(button_id); }\nfunction ShowAllScreens()\n{ alert('show all screens'); }\n function HideAllScreens()\n{alert('hide all screens');}\n</SCRIPT>\n</HEAD>\n<BODY>\n<DIV ID = \"Controls\">\n<BUTTON NAME = \"SHOWALLSCREENS\" ONCLICK=\"ShowAllScreens()\">Show All Screenshots</BUTTON> <BUTTON NAME = \"HIDEALLSCREENS\" ONCLICK=\"HideAllScreens()\">Hide All Screenshots</BUTTON>\n</DIV>\n<DIV ID=\"Report\">\n";
         Footer = "</DIV>\n</BODY>\n</HTML>";
     }
     else
     {
       LineBreak = "\n";
       Header = "";
       Footer = "";
     }
     
      String ReportText= "Procedure report: " + SiteTest.filename + LineBreak;
    int action_INT=0;
    int bug_INT=0;
    String bug_ID="";
    String action_ID="";
        for(int BugViewIndex=0; BugViewIndex<SiteTest.BugViewArray.size(); BugViewIndex++)
     {
       bug_INT = BugViewIndex + 1;
       bug_ID = Integer.toString(bug_INT);
         ReportText = ReportText + "Procedure Title: " + SiteTest.BugViewArray.get(BugViewIndex).JTextFieldBugTitle.getText() + " " + SiteTest.BugViewArray.get(BugViewIndex).JLabelPass.getText() + LineBreak;
        int number_of_actions = SiteTest.BugViewArray.get(BugViewIndex).ActionsViewList.size();
        int passvalueslength = 0;
        if (SiteTest.BugArray.get(BugViewIndex).ActionsList.get(0).loop_pass_values!=null)
        {
              passvalueslength = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(0).loop_pass_values.length;
        }
              if (passvalueslength>0)
            {
                for (int passindex = 0; passindex<passvalueslength; passindex++)
                {
        for (int ActionViewIndex = 0; ActionViewIndex<number_of_actions; ActionViewIndex++)
        {
        action_INT = ActionViewIndex + 1;
        action_ID = Integer.toString(action_INT) + "-" + passindex;
       
                Boolean ThisPassValue = false;
            LocalDateTime ThisTimeValue = LocalDateTime.now();
            String ThisType = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Type;
            String ThisValue1 = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable1;
            String ThisValue2 = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable2;
            String ThisScreenshot = "";
            String pass_string = " has failed at ";
                   DataLoopVarParser var1Parser = new DataLoopVarParser(SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable2);
    ThisPassValue = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).loop_pass_values[passindex];
        ThisTimeValue = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).loop_time_of_test[passindex];
        ThisScreenshot = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).loop_ScreenshotsBase64[passindex];
    if (var1Parser.hasDataLoopVar==false && var2Parser.hasDataLoopVar==false)
    {
        
        if (ThisPassValue)
        {
            pass_string = " has passed at ";
        }
         if (SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Type.contains("assword"))
              {
             ReportText = ReportText + "Action Performed: " + 
                ThisType + " " + ThisValue1
                + " ########" + 
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
             if (includescreens)
             {
                 ReportText +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Hide</BUTTON>" +LineBreak + ThisScreenshot + LineBreak;
             }
             
              }
         else
         {
              ReportText = ReportText + "Action Performed: " + 
                ThisType + " " + ThisValue1
                + " " + ThisValue2 +
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
               if (includescreens)
             {
        ReportText +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Hide</BUTTON>" +LineBreak + ThisScreenshot + LineBreak;
             }
              }
      
    }
    else
    {
  
         if (ThisPassValue)
        {
            pass_string = " has passed at ";
        }
            String concat_variable;
            String concat_variable2;
 concat_variable = var1Parser.GetFullValue(passindex, SiteTest.BugViewArray.get(BugViewIndex).myTable);
 if (!"".equals(concat_variable))
 {
     ThisValue1 = concat_variable;
 }
      concat_variable2 = var2Parser.GetFullValue(passindex, SiteTest.BugViewArray.get(BugViewIndex).myTable);
     if (!"".equals(concat_variable2))
     {
    ThisValue2 = concat_variable2;  
     }
 if (SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Type.contains("assword"))
              {
             ReportText = ReportText + "Action Performed: " + 
                ThisType + " " + ThisValue1
                + " ########" + 
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
              if (includescreens)
             {
             ReportText +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Hide</BUTTON>" +LineBreak + ThisScreenshot + LineBreak;
             }
              }
         else
         {
              ReportText = ReportText + "Action Performed: " + 
                ThisType + " " + ThisValue1
                + " " + ThisValue2 +
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
               if (includescreens)
             {
                ReportText +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Hide</BUTTON>" +LineBreak + ThisScreenshot + LineBreak;
             }
               
              }
    
          
                }
            }
            }
            }
            else
            {
                   for (int ActionViewIndex = 0; ActionViewIndex<number_of_actions; ActionViewIndex++)
        {
        
           action_INT = ActionViewIndex + 1;
        action_ID = Integer.toString(action_INT);
        
                Boolean ThisPassValue = false;
            LocalDateTime ThisTimeValue = LocalDateTime.now();
            String ThisType = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Type;
            String ThisValue1 = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable1;
            String ThisValue2 = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable2;
            String ThisScreenshot = "";
            String pass_string = " has failed at ";
                   DataLoopVarParser var1Parser = new DataLoopVarParser(SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable2);
    ThisPassValue = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Pass;
        ThisTimeValue = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).TimeOfTest;
        ThisScreenshot = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).ScreenshotBase64;
        
        if (ThisPassValue)
        {
            pass_string = " has passed at ";
        }
         if (SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Type.contains("assword"))
              {
             ReportText = ReportText + "Action Performed: " + 
                ThisType + " " + ThisValue1
                + " ########" + 
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
              if (includescreens)
             {
                ReportText +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Hide</BUTTON>" +LineBreak + ThisScreenshot + LineBreak;
             }   
              }
         else
         {
              ReportText = ReportText + "Action Performed: " + 
                ThisType + " " + ThisValue1
                + " " + ThisValue2 +
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
               if (includescreens)
             {
                ReportText +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Hide</BUTTON>" +LineBreak + ThisScreenshot + LineBreak;
             }
          
              }
      
   
            }
    
        }
  
     }
         ReportText = Header + LineBreak + ReportText + Footer;

         return ReportText;  
  }  

    public void SaveAsHTMLFile()
    {
         final JFileChooser fc = new JFileChooser(){
    @Override
    public void approveSelection(){
        
        File f = getSelectedFile();
                String filestring = f.toString();
                
                String[] left_side_of_dot = filestring.split("\\.");
                
                 f = new File(left_side_of_dot[0] + ".html");
       
       
        if(f.exists() && getDialogType() == SAVE_DIALOG){
            int result = JOptionPane.showConfirmDialog(ReportFrame,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
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
    
    }
};
File file=null;
   
   
     FileNameExtensionFilter filefilter = new FileNameExtensionFilter("HTML file (*.html)","html");

    fc.setFileFilter(filefilter);
   
        
        fc.setSelectedFile(file);
        
    
int returnVal = fc.showSaveDialog(this.ReportFrame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                String filestring = file.toString();
                
                String[] left_side_of_dot = filestring.split("\\.");
                
                 file = new File(left_side_of_dot[0] + ".html");
           FileWriter fWriter = null;
BufferedWriter writer = null;
try {
    fWriter = new FileWriter(file);
    writer = new BufferedWriter(fWriter);
    writer.write(HTMLReport);
    writer.newLine();
     writer.close();
} catch (Exception ex) {
  System.out.println ("Exception saving HTML file: " + ex.toString());
}
            
   
            }
            else
            {
                return;
            }
    }
}
