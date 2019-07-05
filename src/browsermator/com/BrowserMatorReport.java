/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.apache.commons.io.FileUtils;

/**
 *
 * @author pcalkins
 */
public class BrowserMatorReport {
JFrame ReportFrame;
JEditorPane HTMLPane;
JButton jButtonSaveFile;
String HTMLReport;
String HTMLReportForSave;
String TextReport;
BorderLayout thisLayout;
JPanel mainPanel;
SeleniumTestToolData STAppData;
String LineBreak;
String Header;
String Footer;
final JFXPanel fxPanel = new JFXPanel();
 WebView browser;
WebEngine webEngine;
Stage webStage;
Boolean ReportDisplayed;
File TEMP_HTML_FILE;


    public BrowserMatorReport (SeleniumTestToolData SiteTest)
 {
   this.STAppData = SiteTest;
   this.ReportDisplayed = false;
   this.TEMP_HTML_FILE = null;
   this.HTMLReport = "";
   this.TextReport = "";
 }
    public String GetTextReport()
    {
        TextReport = OutPutReport(false);
        HTMLReportForSave = TextReport;
        return TextReport;
       
    }
    public String GetHTMLReport()
    {
    
        return HTMLReport;
    }
     public void EmailReport()
   {
            Properties applicationProps = new Properties();


   // String smtp_hostname = this.SiteTest.getSMTPHostname();
   String login_name = STAppData.getEmailLoginName();
   
   String password = STAppData.getEmailPassword();
   String to = STAppData.getEmailTo();
   String from = STAppData.getEmailFrom();
   String subject = STAppData.getEmailSubject();
   if (STAppData.AllTestsPassed.equals(true))
   {
       subject = subject + " - All Procedures PASSED";
   }
   else
   {
       subject = subject + " - Some Procedures FAILED";
   }
  
   // set for TLS, port 587... seems pretty standard these days
   applicationProps.put("mail.smtp.port", "587");
    applicationProps.put("mail.smtp.auth", "true");
      applicationProps.put("mail.smtp.starttls.enable", "true"); //TLS
// gmail requires Oauth id stuff...  I should still implement port setting
   applicationProps.put("mail.smtp.host", STAppData.getSMTPHostname());
    applicationProps.put("email_login_name", STAppData.getEmailLoginName());
     applicationProps.put("email_login_password", STAppData.getEmailPassword());
      applicationProps.put("email_to", STAppData.getEmailTo());
       applicationProps.put("email_from", STAppData.getEmailFrom());
       applicationProps.put("email_subject", STAppData.getEmailSubject());
       
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
     Prompter thisContinuePrompt = new Prompter("Email Failure", "Sending Email has failed. Check settings: " + mex.toString(), false,0,0);    
     
    }
   
   }
   public void ShowTextReport()
   {
        String HTML_TO_SEND = GetTextReport();
  //      GetTextReport()
        jButtonSaveFile = new JButton("Save and Open HTML Report");
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
 fxPanel.setSize(900,920);

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
   ReportFrame.setSize(910, 920);
 //  ReportFrame.pack();
  ReportFrame.setVisible(true);   
     
     
       this.ReportDisplayed = true;    
   }
    public void ShowHTMLReport()
    {
  
        this.HTMLReport = "<HTML><HEAD></HEAD><BODY>Due to a memory problem in JavaFX, you'll need to save the report to view it.  Click the button above to save and open the file.</BODY></HTML>";
    
        jButtonSaveFile = new JButton("Save and Open HTML Report");
   Platform.runLater(new Runnable() {
            @Override
            public void run() {
 if (HTMLReport!=null)
{
                browser = new WebView();
webEngine = browser.getEngine();

webEngine.loadContent(HTMLReport);

 browser.setVisible(true);
Scene scene = new Scene(browser);
        fxPanel.setScene(scene);
        
}
            }
   });   

            fxPanel.setVisible(true);
 fxPanel.setSize(900,920);

mainPanel = new JPanel(new BorderLayout());
  mainPanel.add(jButtonSaveFile, BorderLayout.NORTH);
    mainPanel.add(fxPanel, BorderLayout.CENTER);
    mainPanel.setVisible(true);

   ReportFrame = new JFrame("Browsermator Report");

    jButtonSaveFile.addActionListener(new ActionListener() {
        
       @Override
        public void actionPerformed (ActionEvent e )
        {
         SaveAsHTMLFile(OutPutHTMLReportForSave());   
        }
        });

  
    ReportFrame.add(mainPanel);
    ReportFrame.setSize(910, 920);
 // ReportFrame.pack();
    ReportFrame.setVisible(true);   
     
     
       this.ReportDisplayed = true;       
    
    }
     public void OutPutReports()
     {
     //  this.HTMLReport = OutPutReport(true);
     //  this.HTMLReportForSave = OutPutHTMLReportForSave();
     //  this.TextReport = OutPutReport(false);
     }
     public String FileToBase64(String ThisScreenshotPath)
     {
         String Base64String = "";
        
         
      
        Path screen_path = Paths.get(ThisScreenshotPath);
          Base64Encoder enc_resized = new Base64Encoder();
          byte[] screenshot_in_bytes = null;
          try
          {
 screenshot_in_bytes = Files.readAllBytes(screen_path);
          }
          catch (Exception ex)
                  {
                     System.out.println("Exception reading temporary screenshot file: " + ex.toString()); 
                  }
 Base64String = enc_resized.encode(screenshot_in_bytes);
         
         return Base64String;
     }
     public File OutPutHTMLReportForSave()
     {
         String ReportText = "<HTML></HTML>";
          LineBreak = "<BR>"; 
         Header = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><HTML>\n<HEAD>\n"
                 + "<STYLE>#Report { height: 800; overflow-y: auto;}\n #Controls { }\n</STYLE>"
                 + "\n<SCRIPT>"
                 + "\nfunction ShowHideThisScreen(button_id)\n {"
                 + "var buttsize = button_id.length;\n" +
" var id_string = button_id.substring(14, buttsize);\n" +
" var img_id = \"Screenshot\" + id_string;\n" +
"\n" +
" var thisimg = document.getElementById(img_id);\n" +
" var button = document.getElementById(button_id);\n" +
"\n" +
" if (thisimg.style.display == \"inline\")\n" +
"     {\n" +
"         thisimg.style.display = \"none\";\n" +
"         button.innerHTML = \"Show Screenshot \" + id_string;\n" +
"         \n" +
"     }\n" +
"     else{\n" +
"         thisimg.style.display = \"inline\";\n" +
"         button.innerHTML = \"Hide Screenshot \" + id_string;\n" +
"     }"
                 + " }\nfunction ShowAllScreens()\n{"
                 + "   var allscreens = document.getElementsByClassName(\"report_screenshots\");\n" +
" for (x=0; x<allscreens.length; x++)\n" +
"   {\n" +
"      allscreens.item(x).style.display = \"inline\";\n" +
"      var thisid = allscreens.item(x).id;\n" +
       "      var idsize = allscreens.item(x).id.length;\n" +          
        "     var id_string = allscreens.item(x).id.substring(10, idsize); \n" +  
              
         " var button = document.getElementById(\"ShowHideButton\" + id_string);\n" + 
             "button.innerHTML = \"Hide Screenshot \" + id_string;" +
                 
                 "  }"
                 + "}\n function HideAllScreens()\n{"
                 + "  var allscreens = document.getElementsByClassName(\"report_screenshots\");\n" +
" for (x=0; x<allscreens.length; x++)\n" +
"   {\n" +

                       "      var idsize = allscreens.item(x).id.length;\n" +          
        "     var id_string = allscreens.item(x).id.substring(10, idsize);  \n" +  
              
         " var button = document.getElementById(\"ShowHideButton\" + id_string);\n" + 
             "button.innerHTML = \"Show Screenshot \" + id_string;" +
                 "      allscreens.item(x).style.display = \"none\";\n" +
         
"   }"
                 + "}\n</SCRIPT>\n</HEAD>\n<BODY>\n<DIV ID = \"Controls\">\n<BUTTON NAME = \"SHOWALLSCREENS\" ONCLICK=\"ShowAllScreens()\">Show All Screenshots</BUTTON> <BUTTON NAME = \"HIDEALLSCREENS\" ONCLICK=\"HideAllScreens()\">Hide All Screenshots</BUTTON>\n</DIV>\n<DIV ID=\"Report\">\n";
         Footer = "</DIV>\n</BODY>\n</HTML>"; 
            ReportText= "Procedure report: " + STAppData.filename + STAppData.TimeOfRun.toString() + LineBreak;
    int action_INT=0;
    int bug_INT=0;
    String bug_ID="";
    String action_ID="";
  
   
     BufferedWriter writer =null;
     File temp_report_file = null;
    String tempFileName = "report_results";
    try {
      temp_report_file = File.createTempFile(tempFileName, ".htm");
   temp_report_file.deleteOnExit();
  writer = new BufferedWriter(new FileWriter(temp_report_file));
       
     
    } 
    catch (Exception ex)
    {
        System.out.println ("Exception writing temp file for report: " + ex.toString());
        
 
    }
    if (writer!=null)
    {
        try
        {
               writer.append(Header);
               writer.append(LineBreak);
        writer.append(ReportText);
        }
        catch (Exception ex)
                {
                    System.out.println ("Execpetion appending temp file: " + ex.toString());
                }
    }
     String to_write = "</html>";
        for(int BugIndex=0; BugIndex<STAppData.BugArray.size(); BugIndex++)
     {
       bug_INT = BugIndex + 1;
       bug_ID = Integer.toString(bug_INT);
       
        to_write = "Procedure " + STAppData.BugArray.get(BugIndex).getBugTitle() + " " + STAppData.BugArray.get(BugIndex).getPassText() + LineBreak;
         if (writer!=null)
    {
         try
        {
         
        writer.append(to_write);
        to_write = "";
        }
        catch (Exception ex)
                {
                          System.out.println ("Execpetion appending temp file: " + ex.toString());
                           to_write = "";
                }
    }
        
         int number_of_actions = STAppData.BugArray.get(BugIndex).ActionsList.size();
        int passvalueslength = 0;
        if (STAppData.BugArray.get(BugIndex).ActionsList.get(0).loop_pass_values!=null)
        {
              passvalueslength = STAppData.BugArray.get(BugIndex).ActionsList.get(0).loop_pass_values.size();
        }
              if (passvalueslength>0)
            {
                for (int passindex = 0; passindex<passvalueslength; passindex++)
                {
        for (int ActionViewIndex = 0; ActionViewIndex<number_of_actions; ActionViewIndex++)
        {
        action_INT = ActionViewIndex + 1;
        action_ID = Integer.toString(action_INT) + "-" + passindex;
           if (!STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Locked)  
       {
                Boolean ThisPassValue = false;
            LocalDateTime ThisTimeValue = LocalDateTime.now();
            String ThisType = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Type;
            String ThisValue1 = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable1;
            String ThisValue2 = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable2;
            String ThisScreenshot = "";
            String pass_string = " has failed at ";
                   DataLoopVarParser var1Parser = new DataLoopVarParser(STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable2);
    ThisPassValue = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).loop_pass_values.get(passindex);
        ThisTimeValue = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).loop_time_of_test.get(passindex);
        ThisScreenshot = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).loop_ScreenshotsBase64.get(passindex);
               int index_of_fileslashes = ThisScreenshot.indexOf("file:///")+8;
        int index_of_id = ThisScreenshot.indexOf("id")-2;
          if (index_of_fileslashes > 0 && index_of_id>index_of_fileslashes)
        {
        ThisScreenshot = ThisScreenshot.substring(index_of_fileslashes, index_of_id);

        
        ThisScreenshot = FileToBase64(ThisScreenshot);
        }
        else
        {
            ThisScreenshot = "";
        }
        
    if (var1Parser.hasDataLoopVar==false && var2Parser.hasDataLoopVar==false)
    {
        
        if (ThisPassValue)
        {
            pass_string = " has passed at ";
        }
         if (STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Type.contains("assword"))
              {
                   to_write = bug_ID + "-" + action_ID + " Action: " + 
                ThisType + " " + ThisValue1
                + " ########" + 
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
                                 if ("null".equals(ThisScreenshot) || "Screenshot Failed".equals(ThisScreenshot))
             {
                 to_write += LineBreak;
             }
             else
             {
                to_write +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Hide Screenshot " + bug_ID + "-" + action_ID + "</BUTTON>" +LineBreak + "<img src=\"data:image/png;base64," + ThisScreenshot + "\" style=\"display: inline;\" class = \"report_screenshots\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\"></img>" + LineBreak;
             }
             
             
              }
         else
         {
              to_write = bug_ID + "-" + action_ID + " Action: " + 
                ThisType + " " + ThisValue1
                + " " + ThisValue2 +
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
        
                  if ("null".equals(ThisScreenshot) || "Screenshot Failed".equals(ThisScreenshot))
             {
                 to_write += LineBreak;
             }
             else
             {
             
      to_write +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Hide Screenshot " + bug_ID + "-" + action_ID + "</BUTTON>" +LineBreak + "<img src=\"data:image/png;base64," + ThisScreenshot + "\" style=\"display: inline;\" class = \"report_screenshots\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\"></img>" + LineBreak;
                  }
             }
                        if (writer!=null)
    {
         try
        {
           
        writer.append(to_write);
         to_write = "";
        }
        catch (Exception ex)
                {
                          System.out.println ("Execpetion appending temp file: " + ex.toString());
                           to_write = "";
                }
    }
           
            
         
    
            
      
    }
    else
    {
  
         if (ThisPassValue)
        {
            pass_string = " has passed at ";
        }
   
            String concat_variable="";
            String concat_variable2="";
                    if ("urllist".equals(STAppData.BugArray.get(BugIndex).DataLoopSource))
            {
 concat_variable = var1Parser.GetFullValueFromURLList(passindex, STAppData.BugArray.get(BugIndex).URLListData);
            }
            if ("file".equals(STAppData.BugArray.get(BugIndex).DataLoopSource))
            {
   concat_variable = var1Parser.GetFullValueFromFile(passindex, STAppData.BugArray.get(BugIndex).RunTimeFileSet);             
            }

 if (!"".equals(concat_variable))
 {
     ThisValue1 = concat_variable;
 }
                    if ("urllist".equals(STAppData.BugArray.get(BugIndex).DataLoopSource))
            {
 concat_variable2 = var2Parser.GetFullValueFromURLList(passindex, STAppData.BugArray.get(BugIndex).URLListData);
            }
            if ("file".equals(STAppData.BugArray.get(BugIndex).DataLoopSource))
            {
   concat_variable2 = var2Parser.GetFullValueFromFile(passindex, STAppData.BugArray.get(BugIndex).RunTimeFileSet);             
            }
  
     if (!"".equals(concat_variable2))
     {
    ThisValue2 = concat_variable2;  
     }
      
 if (STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Type.contains("assword"))
              {
             to_write = bug_ID + "-" + action_ID + " Action: " + 
                ThisType + " " + ThisValue1
                + " ########" + 
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
           
           
                  if ("null".equals(ThisScreenshot) || "Screenshot Failed".equals(ThisScreenshot))
             {
                 to_write += LineBreak;
             }
             else
             {
             
     to_write +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Hide Screenshot " + bug_ID + "-" + action_ID + "</BUTTON>" +LineBreak + "<img src=\"data:image/png;base64," + ThisScreenshot + "\" style=\"display: inline;\" class = \"report_screenshots\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\"></img>" + LineBreak;
         }
                  
             }
              
         else
         {
              to_write = bug_ID + "-" + action_ID + " Action: " + 
                ThisType + " " + ThisValue1
                + " " + ThisValue2 +
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
          
          
                   if ("null".equals(ThisScreenshot) || "Screenshot Failed".equals(ThisScreenshot))
             {
                 to_write += LineBreak;
             }
             else
             {
              
        to_write +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Hide Screenshot " + bug_ID + "-" + action_ID + "</BUTTON>" +LineBreak + "<img src=\"data:image/png;base64," + ThisScreenshot + "\" style=\"display: inline;\" class = \"report_screenshots\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\"></img>" +  LineBreak;
         }
             } 
                             if (writer!=null)
    {
         try
        {
        writer.append(to_write);
         to_write = "";
        }
        catch (Exception ex)
                {
                          System.out.println ("Execpetion appending temp file: " + ex.toString());
                           to_write = "";
                }
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
            String ThisType = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Type;
            String ThisValue1 = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable1;
            String ThisValue2 = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable2;
            String ThisScreenshot = "null";
            String pass_string = " has failed at ";
                   DataLoopVarParser var1Parser = new DataLoopVarParser(STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable2);
    ThisPassValue = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Pass;
        ThisTimeValue = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).TimeOfTest;
        ThisScreenshot = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).ScreenshotBase64;
               int index_of_fileslashes = ThisScreenshot.indexOf("file:///")+8;
        int index_of_id = ThisScreenshot.indexOf("id")-2;
        if (index_of_fileslashes > 0 && index_of_id>index_of_fileslashes)
        {
        ThisScreenshot = ThisScreenshot.substring(index_of_fileslashes, index_of_id);

        
        ThisScreenshot = FileToBase64(ThisScreenshot);
        }
        else
        {
            ThisScreenshot = "";
        }
        if (ThisPassValue)
        {
            pass_string = " has passed at ";
        }
         if (STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Type.contains("assword"))
              {
            to_write = bug_ID + "-" + action_ID + " Action: " + 
                ThisType + " " + ThisValue1
                + " ########" + 
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
            
    
                  if ("null".equals(ThisScreenshot) || "Screenshot Failed".equals(ThisScreenshot))
             {
                 to_write += LineBreak;
             }
             else
             {
            
      to_write +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Hide Screenshot " + bug_ID + "-" + action_ID + "</BUTTON>" +LineBreak + "<img src=\"data:image/png;base64," + ThisScreenshot + "\" style=\"display: inline;\" class = \"report_screenshots\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\"></img>" +  LineBreak;
         } 
                                       if (writer!=null)
    {
         try
        {
        writer.append(to_write);
         to_write = "";
        }
        catch (Exception ex)
                {
                          System.out.println ("Execpetion appending temp file: " + ex.toString());
                           to_write = "";
                }
    }
             }
            
         else
         {
              to_write = bug_ID + "-" + action_ID + " Action: " + 
                ThisType + " " + ThisValue1
                + " " + ThisValue2 +
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
       
                  if ("null".equals(ThisScreenshot) || "Screenshot Failed".equals(ThisScreenshot))
             {
                 to_write += LineBreak;
             }
             else
             {
              
       to_write +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Hide Screenshot " + bug_ID + "-" + action_ID + "</BUTTON>" +LineBreak + "<img src=\"data:image/png;base64," + ThisScreenshot + "\" style=\"display: inline;\" class = \"report_screenshots\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\"></img>" +  LineBreak;
         }
                                       if (writer!=null)
    {
         try
        {
       
            writer.append(to_write);
            writer.flush();
        
         to_write = "";
        }
        catch (Exception ex)
                {
                          System.out.println ("Execpetion appending temp file: " + ex.toString());
                           to_write = "";
                }
    }
             }
          
             
      
   
            }
    
        }
  
     }
                                        if (writer!=null)
    {
         try
        {
       
            writer.append(Footer);
            
        
         to_write = "";
        }
        catch (Exception ex)
                {
                          System.out.println ("Execpetion appending temp file: " + ex.toString());
                           to_write = "";
                }
    }
 if (writer!=null)
 {
     try
     {
     writer.close();
     }
     catch (Exception ex)
     {
         System.out.println("Exception closing writer: " + ex.toString());
     }
     }
         this.TEMP_HTML_FILE = temp_report_file;
         return temp_report_file;   
         
      
         
     }
     public String OutPutReport(boolean includescreens)
  {
     if (includescreens)
     {
         LineBreak = "<BR>"; 
         Header = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"><HTML>\n<HEAD>\n"
                 + "<STYLE>#Report { height: 800; overflow-y: auto;}\n #Controls { }\n</STYLE>"
                 + "\n<SCRIPT>"
                 + "\nfunction ShowHideThisScreen(button_id)\n {"
                 + "var buttsize = button_id.length;\n" +
" var id_string = button_id.substring(14, buttsize);\n" +
" var img_id = \"Screenshot\" + id_string;\n" +
"\n" +
" var thisimg = document.getElementById(img_id);\n" +
" var button = document.getElementById(button_id);\n" +
"\n" +
" if (thisimg.style.display == \"inline\")\n" +
"     {\n" +
"         thisimg.style.display = \"none\";\n" +
"         button.innerHTML = \"Show Screenshot \" + id_string;\n" +
"         \n" +
"     }\n" +
"     else{\n" +
"         thisimg.style.display = \"inline\";\n" +
"         button.innerHTML = \"Hide Screenshot \" + id_string;\n" +
"     }"
                 + " }\nfunction ShowAllScreens()\n{"
                 + "   var allscreens = document.getElementsByClassName(\"report_screenshots\");\n" +
" for (x=0; x<allscreens.length; x++)\n" +
"   {\n" +
"      allscreens.item(x).style.display = \"inline\";\n" +
"      var thisid = allscreens.item(x).id;\n" +
       "      var idsize = allscreens.item(x).id.length;\n" +          
        "     var id_string = allscreens.item(x).id.substring(10, idsize); \n" +  
              
         " var button = document.getElementById(\"ShowHideButton\" + id_string);\n" + 
             "button.innerHTML = \"Hide Screenshot \" + id_string;" +
                 
                 "  }"
                 + "}\n function HideAllScreens()\n{"
                 + "  var allscreens = document.getElementsByClassName(\"report_screenshots\");\n" +
" for (x=0; x<allscreens.length; x++)\n" +
"   {\n" +

                       "      var idsize = allscreens.item(x).id.length;\n" +          
        "     var id_string = allscreens.item(x).id.substring(10, idsize);  \n" +  
              
         " var button = document.getElementById(\"ShowHideButton\" + id_string);\n" + 
             "button.innerHTML = \"Show Screenshot \" + id_string;" +
                 "      allscreens.item(x).style.display = \"none\";\n" +
         
"   }"
                 + "}\n</SCRIPT>\n</HEAD>\n<BODY>\n<DIV ID = \"Controls\">\n<BUTTON NAME = \"SHOWALLSCREENS\" ONCLICK=\"ShowAllScreens()\">Show All Screenshots</BUTTON> <BUTTON NAME = \"HIDEALLSCREENS\" ONCLICK=\"HideAllScreens()\">Hide All Screenshots</BUTTON>\n</DIV>\n<DIV ID=\"Report\">\n";
         Footer = "</DIV>\n</BODY>\n</HTML>";
     }
     else
     {
         Header = "<HTML><BODY>";
         Footer = "</BODY></HTML>";
     if (STAppData.getEmailReport())
     {
       LineBreak = "\n";
       Header = "";
       Footer = "";
     }
     else
     {
         LineBreak = "<br>";
     }
     }
      String ReportText= "Procedure report: " + STAppData.filename + STAppData.TimeOfRun.toString() + LineBreak;
    int action_INT=0;
    int bug_INT=0;
    String bug_ID="";
    String action_ID="";
        for(int BugIndex=0; BugIndex<STAppData.BugArray.size(); BugIndex++)
     {
       bug_INT = BugIndex + 1;
       bug_ID = Integer.toString(bug_INT);
         ReportText = ReportText + "Procedure " + STAppData.BugArray.get(BugIndex).getBugTitle() + " " + STAppData.BugArray.get(BugIndex).getPassText() + LineBreak;
        int number_of_actions = STAppData.BugArray.get(BugIndex).ActionsList.size();
        int passvalueslength = 0;
        if (number_of_actions>0)
        {
        if (STAppData.BugArray.get(BugIndex).ActionsList.get(0).loop_pass_values!=null)
        {
              passvalueslength = STAppData.BugArray.get(BugIndex).ActionsList.get(0).loop_pass_values.size();
        }
        }
              if (passvalueslength>0)
            {
                for (int passindex = 0; passindex<passvalueslength; passindex++)
                {
        for (int ActionViewIndex = 0; ActionViewIndex<number_of_actions; ActionViewIndex++)
        {
        action_INT = ActionViewIndex + 1;
        action_ID = Integer.toString(action_INT) + "-" + passindex;
           if (!STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Locked)  
       {
                Boolean ThisPassValue = false;
            LocalDateTime ThisTimeValue = LocalDateTime.now();
            String ThisType = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Type;
            String ThisValue1 = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable1;
            String ThisValue2 = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable2;
            String ThisScreenshot = "";
            String pass_string = " has failed at ";
                   DataLoopVarParser var1Parser = new DataLoopVarParser(STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable2);
    ThisPassValue = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).loop_pass_values.get(passindex);
        ThisTimeValue = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).loop_time_of_test.get(passindex);
        ThisScreenshot = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).loop_ScreenshotsBase64.get(passindex);
    if (var1Parser.hasDataLoopVar==false && var2Parser.hasDataLoopVar==false)
    {
        
        if (ThisPassValue)
        {
            pass_string = " has passed at ";
        }
         if (STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Type.contains("assword"))
              {
             ReportText = ReportText + bug_ID + "-" + action_ID + " Action: " + 
                ThisType + " " + ThisValue1
                + " ########" + 
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
            
             if (includescreens)
             {
                  if ("null".equals(ThisScreenshot) || "Screenshot Failed".equals(ThisScreenshot))
             {
                 ReportText += LineBreak;
             }
             else
             {
                 ReportText +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Show Screenshot " + bug_ID + "-" + action_ID + "</BUTTON>" +LineBreak + ThisScreenshot + LineBreak;
             }
             
              }
              }
         else
         {
              ReportText = ReportText + bug_ID + "-" + action_ID + " Action: " + 
                ThisType + " " + ThisValue1
                + " " + ThisValue2 +
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
               if (includescreens)
             {
                  if ("null".equals(ThisScreenshot) || "Screenshot Failed".equals(ThisScreenshot))
             {
                 ReportText += LineBreak;
             }
             else
             {
        ReportText +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton" 
                + bug_ID + "-" + action_ID + "\">Show Screenshot " + bug_ID + "-" + action_ID + "</BUTTON>" +LineBreak + ThisScreenshot + LineBreak;
             }
             }
              }
      
    }
    else
    {
  
         if (ThisPassValue)
        {
            pass_string = " has passed at ";
        }
   
            String concat_variable="";
            String concat_variable2="";
                         if ("urllist".equals(STAppData.BugArray.get(BugIndex).DataLoopSource))
            {
 concat_variable = var1Parser.GetFullValueFromURLList(passindex, STAppData.BugArray.get(BugIndex).URLListData);
            }
            if ("file".equals(STAppData.BugArray.get(BugIndex).DataLoopSource))
            {
   concat_variable = var1Parser.GetFullValueFromFile(passindex, STAppData.BugArray.get(BugIndex).RunTimeFileSet);             
            }      
 
 if (!"".equals(concat_variable))
 {
     ThisValue1 = concat_variable;
 }
                    if ("urllist".equals(STAppData.BugArray.get(BugIndex).DataLoopSource))
            {
 concat_variable2 = var2Parser.GetFullValueFromURLList(passindex, STAppData.BugArray.get(BugIndex).URLListData);
            }
            if ("file".equals(STAppData.BugArray.get(BugIndex).DataLoopSource))
            {
   concat_variable2 = var2Parser.GetFullValueFromFile(passindex, STAppData.BugArray.get(BugIndex).RunTimeFileSet);             
            }
   
     if (!"".equals(concat_variable2))
     {
    ThisValue2 = concat_variable2;  
     }
      
 if (STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Type.contains("assword"))
              {
             ReportText = ReportText + bug_ID + "-" + action_ID + " Action: " + 
                ThisType + " " + ThisValue1
                + " ########" + 
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
           
             if (includescreens)
             {
                  if ("null".equals(ThisScreenshot) || "Screenshot Failed".equals(ThisScreenshot))
             {
                 ReportText += LineBreak;
             }
             else
             {
             ReportText +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Show Screenshot " + bug_ID + "-" + action_ID + "</BUTTON>" +LineBreak + ThisScreenshot + LineBreak;
             }
                  
             }
              }
         else
         {
              ReportText = ReportText + bug_ID + "-" + action_ID + " Action: " + 
                ThisType + " " + ThisValue1
                + " " + ThisValue2 +
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
          
              if (includescreens)
             {
                   if ("null".equals(ThisScreenshot) || "Screenshot Failed".equals(ThisScreenshot))
             {
                 ReportText += LineBreak;
             }
             else
             {
                ReportText +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Show Screenshot " + bug_ID + "-" + action_ID + "</BUTTON>" +LineBreak + ThisScreenshot + LineBreak;
             }
             } 
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
            String ThisType = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Type;
            String ThisValue1 = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable1;
            String ThisValue2 = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable2;
            String ThisScreenshot = "null";
            String pass_string = " has failed at ";
                   DataLoopVarParser var1Parser = new DataLoopVarParser(STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Variable2);
    ThisPassValue = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Pass;
        ThisTimeValue = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).TimeOfTest;
        ThisScreenshot = STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).ScreenshotBase64;
        
        if (ThisPassValue)
        {
            pass_string = " has passed at ";
        }
         if (STAppData.BugArray.get(BugIndex).ActionsList.get(ActionViewIndex).Type.contains("assword"))
              {
             ReportText = ReportText + bug_ID + "-" + action_ID + " Action: " + 
                ThisType + " " + ThisValue1
                + " ########" + 
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
            
             if (includescreens)
             {
                  if ("null".equals(ThisScreenshot) || "Screenshot Failed".equals(ThisScreenshot))
             {
                 ReportText += LineBreak;
             }
             else
             {
                ReportText +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Show Screenshot " + bug_ID + "-" + action_ID + "</BUTTON>" +LineBreak + ThisScreenshot + LineBreak;
             }   
             }
              }
         else
         {
              ReportText = ReportText + bug_ID + "-" + action_ID + " Action: " + 
                ThisType + " " + ThisValue1
                + " " + ThisValue2 +
                     
               pass_string + ThisTimeValue.toString() + LineBreak;
               if (includescreens)
             {
                  if ("null".equals(ThisScreenshot) || "Screenshot Failed".equals(ThisScreenshot))
             {
                 ReportText += LineBreak;
             }
             else
             {
                ReportText +=  "\n<BUTTON NAME =\"ShowHideButton\" onclick = \"ShowHideThisScreen(this.id)\" id = \"ShowHideButton"  + bug_ID + "-" + action_ID + "\">Show Screenshot " + bug_ID + "-" + action_ID + "</BUTTON>" +LineBreak + ThisScreenshot + LineBreak;
             }
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
        BrowserMatorConfig theseProps = new BrowserMatorConfig();
      String lastReportSaveDir = theseProps.getKeyValue("lastused_dir_save_report");
     
     FileNameExtensionFilter filefilter = new FileNameExtensionFilter("HTML file (*.html)","html");

    fc.setFileFilter(filefilter);
 fc.setPreferredSize(new Dimension(800,600));
        
        fc.setSelectedFile(file);
        if (lastReportSaveDir!=null)
        {
        fc.setCurrentDirectory(new File(lastReportSaveDir));
        }
    fc.setPreferredSize(new Dimension(800,600));
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
    writer.write(HTMLReportForSave);
    writer.newLine();
     writer.close();
} catch (Exception ex) {
  System.out.println ("Exception saving HTML file: " + ex.toString());
}
            
   if (Desktop.isDesktopSupported()) {
    try {
       
      Desktop.getDesktop().open(file);
     
    } catch (Exception ex) {
        System.out.println("Exception opening HTML file: " + ex.toString());
    }
}
            }
            else
            {
               
            }
            File chosenDir = fc.getCurrentDirectory();
 theseProps.setKeyValue ("lastused_dir_save_report", chosenDir.getAbsolutePath());
 
    }
    public void SaveAsHTMLFile(File sourceFile)
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
      BrowserMatorConfig theseProps = new BrowserMatorConfig();

      String lastReportSaveDir = theseProps.getKeyValue("lastused_dir_save_report");
   
     FileNameExtensionFilter filefilter = new FileNameExtensionFilter("HTML file (*.html)","html");

    fc.setFileFilter(filefilter);
    if (lastReportSaveDir!=null)
        {
        fc.setCurrentDirectory(new File(lastReportSaveDir));
        } 
        
        fc.setSelectedFile(file);
       
fc.setPreferredSize(new Dimension(800,600));

int returnVal = fc.showSaveDialog(this.ReportFrame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                String filestring = file.toString();
                
                String[] left_side_of_dot = filestring.split("\\.");
                
                 file = new File(left_side_of_dot[0] + ".html");
         
          
if (sourceFile!=null)
{
try {
    FileUtils.copyFile(sourceFile, file);
} catch (Exception ex) {
  System.out.println ("Exception saving HTML file: " + ex.toString());
}
            
}
if (Desktop.isDesktopSupported()) {
    try {
       
      Desktop.getDesktop().open(file);
     
    } catch (Exception ex) {
        System.out.println("Exception opening HTML file: " + ex.toString());
    }
}
            }
            else
            {
              
            }
                  File chosenDir = fc.getCurrentDirectory();
 theseProps.setKeyValue ("lastused_dir_save_report", chosenDir.getAbsolutePath());
    }
}
