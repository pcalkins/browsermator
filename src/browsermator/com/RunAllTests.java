
package browsermator.com;




import java.awt.Cursor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;


public class RunAllTests extends SwingWorker<String, Integer>
{
SeleniumTestTool SiteTest;
String targetbrowser;
String OSType;
WebDriver driver;
String firefox_path;
FireFoxProperties FFprops;
BrowserMatorReport BrowserMatorReport;

 public RunAllTests (SeleniumTestTool in_SiteTest)
 {
   this.BrowserMatorReport = new BrowserMatorReport(in_SiteTest);
  FFprops = new FireFoxProperties();
  this.firefox_path = FFprops.LoadFirefoxPath();
   this.SiteTest = in_SiteTest;
  this.targetbrowser = in_SiteTest.TargetBrowser;
  this.OSType = in_SiteTest.OSType;
 }
 
@Override 
public String doInBackground()
 {
    SiteTest.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
        SiteTest.setCursor(Cursor.getDefaultCursor());   
     SiteTest.setRunActionsButtonName(donetext);
    if (SiteTest.getShowReport())
    {
   BrowserMatorReport.OutPutReports();
   BrowserMatorReport.ShowHTMLReport();
    }
    if (SiteTest.getEmailReportFail())
    {
        if (SiteTest.AllTestsPassed)
        {
            
        }
        else
        {
            BrowserMatorReport.EmailReport();
        }
    }
    if (SiteTest.getEmailReport())
    {
 
        BrowserMatorReport.EmailReport();
  
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
        BrowserMatorReport.OutPutReports();
        if (SiteTest.getShowReport())
    {
    BrowserMatorReport.ShowHTMLReport();
 
    }
    if (SiteTest.getEmailReportFail())
    {
        if (SiteTest.AllTestsPassed)
        {
            
        }
        else
        {
            BrowserMatorReport.EmailReport();
        }
    }
    if (SiteTest.getEmailReport())
    {
 
        BrowserMatorReport.EmailReport();
  
    }
    if (SiteTest.getExitAfter())
    {
    System.exit(0);
    }
       System.out.println(ex.getLocalizedMessage());
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
       try
       {
    ThisAction.ScreenshotBase64 = "<img src=\"data:image/png;base64,"+((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64)+"\" id = \"screenshot-" + thisbugindex+1 + "-" + ThisAction.index+1 + "\" class = \"report_screenshots\"></img>";
       }
       catch (Exception ex)
       {
           System.out.println("Exception creating screenshot: " + ex.toString());     
    }
   }
  catch (UnreachableBrowserException ex)
     {
  
      ThisAction.Pass = false;
       
  FillReport();
         if (SiteTest.getShowReport())
       {
    
       BrowserMatorReport.OutPutReports();
       BrowserMatorReport.ShowHTMLReport();
     
       }
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
       if (SiteTest.getShowReport())
       {
    
       BrowserMatorReport.OutPutReports();
       BrowserMatorReport.ShowHTMLReport();
     
       }
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
       if (SiteTest.getShowReport())
       {
    
       BrowserMatorReport.OutPutReports();
       BrowserMatorReport.ShowHTMLReport();
     
       }
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

  

  }
 
}
