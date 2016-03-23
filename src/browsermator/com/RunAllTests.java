
package browsermator.com;




import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
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
import org.openqa.selenium.remote.UnreachableBrowserException;



public class RunAllTests extends SwingWorker<String, Integer>
{
SeleniumTestTool SiteTest;
String report;
String targetbrowser;
String OSType;
WebDriver driver;
String firefox_path;
FireFoxProperties FFprops;
 public RunAllTests (SeleniumTestTool in_SiteTest)
 {
  FFprops = new FireFoxProperties();
  this.firefox_path = FFprops.LoadFirefoxPath();
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
     ReportJFrame.setSize(800, 800);
     
        
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
    catch (Exception ex)
    {
        if (ex.toString().contains("Cannot find firefox"))
        {
        FFprops.BrowseforFFPath();
        
 
        }
        SiteTest.setRunActionsButtonName("Run All Procedures");
        this.report = OutPutReport();
        if (SiteTest.getShowReport())
    {
     JFrame ReportJFrame = new JFrame();
     JTextArea ReportArea = new JTextArea();
     ReportJFrame.add(ReportArea);
     ReportArea.setText(this.report);
     ReportJFrame.setSize(800, 800);
     
        
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
    //    System.out.println("send failed, exception: " + mex);
     Prompter thisContinuePrompt = new Prompter("Sending Email has failed. Check settings.");    
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


  switch (TargetBrowser)
   {
     case "Firefox":
      if (this.firefox_path!=null)
      {
           System.setProperty("webdriver.firefox.bin", this.firefox_path);
      }
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
 driver.manage().timeouts().implicitlyWait(WaitTime, TimeUnit.SECONDS);
     int totalpause = WaitTime * 1000;
        
  
  int thisbugindex = 0;
     for (Procedure thisbug : SiteTest.BugArray)
      {
          SiteTest.BugViewArray.get(thisbugindex).JButtonRunTest.setText("Running...");
        ArrayList<Action> Actions = thisbug.ActionsList;


int actionsrun = 0;
ProcedureView thisbugview = SiteTest.BugViewArray.get(thisbugindex);

if (thisbugview.myTable==null)
{
   for( Action ThisAction : thisbug.ActionsList ) {
           String original_value = ThisAction.Variable2;
 
           if (!ThisAction.Locked)
   {
   try
   {
       ThisAction.RunAction(driver);
       
   }
  catch (UnreachableBrowserException ex)
     {
  
      ThisAction.Pass = false;
       
  FillReport();
              driver.close();
              publish(thisbugindex);
          break;
      
       
     }
   }   
   }  

}
else
{
 int number_of_rows = thisbugview.myTable.DataTable.getRowCount();
  for( Action ThisAction : thisbug.ActionsList ) { 
 ThisAction.InitializeLoopTestVars(number_of_rows);
  } 
 for (int x = 0; x<number_of_rows; x++)
    {
      
    for( Action ThisAction : thisbug.ActionsList ) {
      
        
        String original_value1 = ThisAction.Variable1;
           String original_value2 = ThisAction.Variable2;
      if (!ThisAction.Locked)
   {
   
   
               DataLoopVarParser var1Parser = new DataLoopVarParser(ThisAction.Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(ThisAction.Variable2);
    if (var1Parser.hasDataLoopVar==false && var2Parser.hasDataLoopVar==false)
    {
        if ("Pause with Continue Button".equals(ThisAction.Type))
        {
           String pause_message = "Paused at record " + (x+1) + " of " + number_of_rows;
          ThisAction.RunAction(driver, pause_message);
        ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
        }
       else
        {
            try
            {
        ThisAction.RunAction(driver);
        ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
            }
             catch (UnreachableBrowserException ex)
     {
   
          ThisAction.loop_pass_values[x] = false;
          ThisAction.loop_time_of_test[x] = LocalDateTime.now();
        
  FillReport();
               driver.close();
               publish(thisbugindex);
          break;
       
     }
        }
    }
    else
    {
  
       
            String concat_variable;
            String concat_variable2;
 concat_variable = var1Parser.GetFullValue(x, thisbugview.myTable);
 if (!"".equals(concat_variable))
 {
      ThisAction.Variable1 = concat_variable;
 }
      concat_variable2 = var2Parser.GetFullValue(x, thisbugview.myTable);
     if (!"".equals(concat_variable2))
     {
      ThisAction.Variable2 = concat_variable2;  
     }
     try
             {
      ThisAction.RunAction(driver);

      ThisAction.Variable1 = original_value1;
   ThisAction.Variable2 = original_value2;
   ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
             }
      catch (UnreachableBrowserException ex)
     {
   
       ThisAction.Variable1 = original_value1;
       ThisAction.Variable2 = original_value2;
       ThisAction.loop_pass_values[x] = false;
        ThisAction.loop_time_of_test[x] = LocalDateTime.now();
        
  FillReport();
               driver.close();
               publish(thisbugindex);
          break;
       
     }
             }
   
      }
     
     }
    
    
    }
     //check if all actions passed
    for( Action ThisAction : thisbug.ActionsList )
    {   
        ThisAction.Pass = false;
        Boolean all_actions_passed = false;
        int actions_passed = 0;
        for (Boolean passvalue: ThisAction.loop_pass_values)
        {
            if (passvalue)
            {
                actions_passed++;
            }
        }
        if (actions_passed == ThisAction.loop_pass_values.length)
        {
            ThisAction.Pass = true;
        }
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
 
     FillReport();
  
     
 }

  public void FillReport()
  {
      int NumberOfTestsPassed = 0;   
      int BugIndex = 0;
  
    Boolean BugPass = false;
     for (ProcedureView thisbugview : SiteTest.BugViewArray)
      {
        ArrayList<ActionView> ActionView = thisbugview.ActionsViewList;


 int NumberOfActionsPassed = 0;
  if (thisbugview.myTable==null)
  {
       int ActionIndex = 0;
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
  }
  else
  {
      int number_of_rows = thisbugview.myTable.DataTable.getRowCount();
    for (int x = 0; x<number_of_rows; x++)
    {
        
 int ActionIndex = 0;
    for( ActionView TheseActionViews : ActionView ) {


    LocalDateTime stringtime = SiteTest.BugArray.get(BugIndex).ActionsList.get(ActionIndex).TimeOfTest;
       boolean TestState = SiteTest.BugArray.get(BugIndex).ActionsList.get(ActionIndex).loop_pass_values[x];
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
    }
     if (NumberOfActionsPassed == thisbugview.ActionsViewList.size()*number_of_rows)
    {
        BugPass = true;
    }
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

     if (NumberOfTestsPassed==SiteTest.BugArray.size())
     {
     SiteTest.AllTestsPassed = true;
     }
     else
     {
         SiteTest.AllTestsPassed = false;
     }

       if (SiteTest.getShowReport())
       {
    
   this.report = OutPutReport();
 
     
       }

  }
  public String OutPutReport()
  {
     
      String ReportText= "Procedure report: " + SiteTest.filename + "\n";
      
        for(int BugViewIndex=0; BugViewIndex<SiteTest.BugViewArray.size(); BugViewIndex++)
     {
        ReportText = ReportText + "Procedure Title: " + SiteTest.BugViewArray.get(BugViewIndex).JTextFieldBugTitle.getText() + " " + SiteTest.BugViewArray.get(BugViewIndex).JLabelPass.getText() + "\n";
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
      
                Boolean ThisPassValue = false;
            LocalDateTime ThisTimeValue = LocalDateTime.now();
            String ThisType = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Type;
            String ThisValue1 = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable1;
            String ThisValue2 = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable2;
            String pass_string = " has failed at ";
                   DataLoopVarParser var1Parser = new DataLoopVarParser(SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable2);
    ThisPassValue = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).loop_pass_values[passindex];
        ThisTimeValue = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).loop_time_of_test[passindex];
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
                     
               pass_string + ThisTimeValue.toString() + "\n";    
              }
         else
         {
              ReportText = ReportText + "Action Performed: " + 
                ThisType + " " + ThisValue1
                + " " + ThisValue2 +
                     
               pass_string + ThisTimeValue.toString() + "\n";    
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
                     
               pass_string + ThisTimeValue.toString() + "\n";    
              }
         else
         {
              ReportText = ReportText + "Action Performed: " + 
                ThisType + " " + ThisValue1
                + " " + ThisValue2 +
                     
               pass_string + ThisTimeValue.toString() + "\n";    
              }
    
          
                }
            }
            }
            }
            else
            {
                   for (int ActionViewIndex = 0; ActionViewIndex<number_of_actions; ActionViewIndex++)
        {
      
                Boolean ThisPassValue = false;
            LocalDateTime ThisTimeValue = LocalDateTime.now();
            String ThisType = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Type;
            String ThisValue1 = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable1;
            String ThisValue2 = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable2;
            String pass_string = " has failed at ";
                   DataLoopVarParser var1Parser = new DataLoopVarParser(SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Variable2);
    ThisPassValue = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Pass;
        ThisTimeValue = SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).TimeOfTest;
  
        
        if (ThisPassValue)
        {
            pass_string = " has passed at ";
        }
         if (SiteTest.BugArray.get(BugViewIndex).ActionsList.get(ActionViewIndex).Type.contains("assword"))
              {
             ReportText = ReportText + "Action Performed: " + 
                ThisType + " " + ThisValue1
                + " ########" + 
                     
               pass_string + ThisTimeValue.toString() + "\n";    
              }
         else
         {
              ReportText = ReportText + "Action Performed: " + 
                ThisType + " " + ThisValue1
                + " " + ThisValue2 +
                     
               pass_string + ThisTimeValue.toString() + "\n";    
              }
      
   
            }
    
        }
  
     }
         return ReportText;    
  }  

}
