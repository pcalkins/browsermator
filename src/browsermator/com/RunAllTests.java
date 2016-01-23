
package browsermator.com;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;



public class RunAllTests extends SwingWorker<String, Integer>
{
SeleniumTestTool SiteTest;
String report;
String targetbrowser;
String OSType;
WebDriver driver;
 
 public RunAllTests (SeleniumTestTool in_SiteTest)
 {
   this.SiteTest = in_SiteTest;
  this.targetbrowser = in_SiteTest.TargetBrowser;
  this.OSType = in_SiteTest.OSType;
 }
 
@Override 
public String doInBackground()
 {
     
    SiteTest.setRunActionsButtonName("Running...");
    RunAllActions(SiteTest, targetbrowser, OSType);
    String donetext = "Run All Procedures";
     return donetext;
     
 }
@Override
 protected void done()
 {
    try
    {
        String donetext = get();
     SiteTest.setRunActionsButtonName(donetext);
    if (SiteTest.getShowReport())
    {
     JFrame ReportJFrame = new JFrame();
      JTextArea ReportArea = new JTextArea();
      ReportJFrame.add(ReportArea);
     ReportArea.setText(this.report);
        ReportJFrame.setSize(400, 800);
     
        
        ReportJFrame.setVisible(true);
    }
    if (SiteTest.getEmailReportFail())
    {
        if (SiteTest.AllTestsPassed)
        {
            
        }
        else
        {
            EmailReport();
        }
    }
    if (SiteTest.getEmailReport())
    {
 
        EmailReport();
  
    }
    if (SiteTest.getExitAfter())
    {
    System.exit(0);
    }
    }
    catch (InterruptedException | ExecutionException ex)
    {
        SiteTest.setRunActionsButtonName("Run All Procedures");
       System.out.println(ex.getLocalizedMessage());
    }
     
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
    this.report = OutPutReport();
    
       
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
        msg.setText(this.report);
        Transport.send(msg, login_name, password);
    } catch (MessagingException mex) {
        System.out.println("send failed, exception: " + mex);
    }
   
   }
  
@Override
 protected void process ( List <Integer> bugindex)
 {
    int updatebugindex = bugindex.size()-1;
    
    SiteTest.BugViewArray.get(updatebugindex).JButtonRunTest.setText("Done");
    
 }
  public void RunAllActions(SeleniumTestTool SiteTest, String TargetBrowser, String OSType)
 {

int NumberOfTestsPassed = 0;
//  WebDriver driver = null;

  switch (TargetBrowser)
   {
     case "Firefox":
      driver = new FirefoxDriver();
     break;
     
     case "Silent Mode (HTMLUnit)":
     driver = new HtmlUnitDriver();  
     break;
     
     case "Internet Explorer-32":
     System.setProperty("webdriver.ie.driver", "IEDriverServer_Win32_2.48.0\\IEDriverServer.exe");
     driver = new InternetExplorerDriver();
     break;
     case "Internet Explorer-64":
     System.setProperty("webdriver.ie.driver", "IEDriverServer_x64_2.48.0\\IEDriverServer.exe");
     driver = new InternetExplorerDriver();
     break;
     case "Chrome":
     if ("Windows".equals(OSType))
     {
     System.setProperty("webdriver.chrome.driver", "chromedriver_win32\\chromedriver.exe");
     }
     if ("Mac".equals(OSType))
     {
     System.setProperty("webdriver.chrome.driver", "chromedriver_mac32\\chromedriver-mac32");
     }
     if ("Linux-32".equals(OSType))
     {
     System.setProperty("webdriver.chrome.driver", "chromedriver_linux32\\chromedriver-linux32");
     }
     if ("Linux-64".equals(OSType))
     {
     System.setProperty("webdriver.chrome.driver", "chromedriver_linux64\\chromedriver-linux64");
     }
     
     driver = new ChromeDriver();
     break;
         
         default: 
            driver = new FirefoxDriver(); 
                     break;
   }
  
  int WaitTime = SiteTest.GetWaitTime();
// driver.manage().timeouts().implicitlyWait(WaitTime, TimeUnit.SECONDS);
     int totalpause = WaitTime * 1000;
        
  
  int thisbugindex = 0;
     for (Procedure thisbug : SiteTest.BugArray)
      {
          SiteTest.BugViewArray.get(thisbugindex).JButtonRunTest.setText("Running...");
        ArrayList<Action> Actions = thisbug.ActionsList;


int actionsrun = 0;
   for( Action TheseActions : Actions ) {
try
{
    
      TheseActions.RunAction(driver);
            try
        {
Thread.sleep(totalpause);
        }
        catch (InterruptedException e)
                {
                    System.out.println("pause exception: " + e.toString());
                }
}
catch (Exception ex)
   {

        System.out.println(ex);
  
        }


}
  
   publish(thisbugindex);
    thisbugindex++;
      }
     if (SiteTest.getPromptToClose())
     {
   Object[] options = {"OK"};
    int n = JOptionPane.showOptionDialog(null,
                   "Close webdriver/browser?","Prompt to close browser",
                   JOptionPane.PLAIN_MESSAGE,
                   JOptionPane.QUESTION_MESSAGE,
                   null,
                   options,
                   options[0]);
    if (n==0)
    {
        driver.close();
    }
     }
     else
     {
    driver.close();
     }
      int BugIndex = 0;
  
    Boolean BugPass = false;
     for (ProcedureView thisbugview : SiteTest.BugViewArray)
      {
        ArrayList<ActionView> ActionView = thisbugview.ActionsViewList;

 int ActionIndex = 0;
 int NumberOfActionsPassed = 0;
   for( ActionView TheseActionViews : ActionView ) {


    LocalDateTime stringtime = SiteTest.BugArray.get(BugIndex).ActionsList.get(ActionIndex).TimeOfTest;
       boolean TestState = SiteTest.BugArray.get(BugIndex).ActionsList.get(ActionIndex).Pass;
       if (TestState==true)
       {
           thisbugview.ActionsViewList.get(ActionIndex).JLabelPassFail.setText("Passed at " + stringtime);
           NumberOfActionsPassed++;
       }
       else
       {
           thisbugview.ActionsViewList.get(ActionIndex).JLabelPassFail.setText("Fail at " + stringtime);
           
       }

       ActionIndex++;

}
    if (NumberOfActionsPassed == thisbugview.ActionsViewList.size())
    {
        BugPass = true;
    }
    int LastActionIndex = SiteTest.BugArray.get(BugIndex).ActionsList.size()-1;
   if (BugPass.equals(true))
   {
       NumberOfTestsPassed++;
        LocalDateTime stringtime = SiteTest.BugArray.get(BugIndex).ActionsList.get(LastActionIndex).TimeOfTest;
        thisbugview.JLabelPass.setVisible(true);
       thisbugview.JLabelPass.setText("Passed at " + stringtime);
       SiteTest.BugArray.get(BugIndex).Pass = true;
       thisbugview.JLabelPass.setVisible(true);
   }
   else
   {
       LocalDateTime stringtime = SiteTest.BugArray.get(BugIndex).ActionsList.get(LastActionIndex).TimeOfTest;
       thisbugview.JLabelPass.setText("Failed at " + stringtime);
       SiteTest.BugArray.get(BugIndex).Pass = false;
       thisbugview.JLabelPass.setVisible(true);
   }
   SiteTest.BugViewArray.get(BugIndex).JButtonRunTest.setText("Run");
   BugIndex++;
      }
 // STAppFrame.jButtonDoStuff.setText("Run Tests");
     if (NumberOfTestsPassed==SiteTest.BugArray.size())
     {
     SiteTest.AllTestsPassed = true;
     }
     else
     {
         SiteTest.AllTestsPassed = false;
     }
  //   if (SiteTest.GetLoop())
  //   {
  //     if (SiteTest.AllTestsPassed == false) 
  //     {
  //     RunAllActions(SiteTest);    
  //     }
       if (SiteTest.getShowReport())
       {
    
   this.report = OutPutReport();
    
     
       }

     
  
     
 }

  public String OutPutReport()
  {
     
      String ReportText=SiteTest.URL + "Procedure report: " + SiteTest.filename + "\n";
      
        for(int BugViewIndex=0; BugViewIndex<SiteTest.BugViewArray.size(); BugViewIndex++)
     {
        ReportText = ReportText + "Procedure Title: " + SiteTest.BugViewArray.get(BugViewIndex).JTextFieldBugTitle.getText() + " " + SiteTest.BugViewArray.get(BugViewIndex).JLabelPass.getText() + "\n";
        
        for (int ActionViewIndex = 0; ActionViewIndex<SiteTest.BugViewArray.get(BugViewIndex).ActionsViewList.size(); ActionViewIndex++)
        {
       
              if (SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Type.contains("assword"))
              {
                   ReportText = ReportText + "Action Performed: " + 
                SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Type + " " + SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable1
                + " " +  "*******" +
                     
                SiteTest.BugViewArray.get(BugViewIndex).ActionsViewList.get(ActionViewIndex).JLabelPassFail.getText() + "\n";    
              }
              else
              {
                   ReportText = ReportText + "Action Performed: " + 
                SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Type + " " + SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable1
                + " " +  SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable2 + " " +
                     
                SiteTest.BugViewArray.get(BugViewIndex).ActionsViewList.get(ActionViewIndex).JLabelPassFail.getText() + "\n";
        }}
     }
        
   return ReportText;    
  }  
}
