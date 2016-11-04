
package browsermator.com;




import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.xml.bind.DatatypeConverter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.internal.Base64Encoder;
import org.openqa.selenium.remote.DesiredCapabilities;

public class RunAllTests extends SwingWorker<String, Integer>
{
SeleniumTestTool SiteTest;
String targetbrowser;
String OSType;
WebDriver driver;
String firefox_path;
String chrome_path;
FireFoxProperties FFprops;
BrowserMatorReport BrowserMatorReport;

 public RunAllTests (SeleniumTestTool in_SiteTest)
 {
   this.BrowserMatorReport = new BrowserMatorReport(in_SiteTest);
  FFprops = new FireFoxProperties(targetbrowser);
  this.firefox_path = FFprops.LoadFirefoxPath();
  this.chrome_path = FFprops.LoadChromePath();
   this.SiteTest = in_SiteTest;
  this.targetbrowser = in_SiteTest.TargetBrowser;
  this.OSType = in_SiteTest.OSType;
  this.driver = new HtmlUnitDriver();
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
    if (BrowserMatorReport.ReportDisplayed)
    {
        
    }
    else
    {
   BrowserMatorReport.OutPutReports();
   BrowserMatorReport.ShowHTMLReport();
    }
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
 
   SiteTest.setCursor(Cursor.getDefaultCursor()); 
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
        // legacy file support
     case "Firefox-Marionette":
     // legacy file support
         if ("Windows".equals(OSType))
     {
       System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-0.11.1-win32\\geckodriver.exe");
     }   
     if ("Windows32".equals(OSType))
     {
       System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-0.11.1-win32\\geckodriver.exe");
     }
     if ("Windows64".equals(OSType))
     {
       System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-0.11.1-win64\\geckodriver.exe");
     }
     if ("Mac".equals(OSType))
     {
      System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-0.11.1-osx\\geckodriver");
     }
     if ("Linux-32".equals(OSType))
     {
      System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-0.11.1-linux32\\geckodriver");
     }
     if ("Linux-64".equals(OSType))
     {
      System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-0.11.1-linux64\\geckodriver");
     }
   
    if (firefox_path!=null) {
        System.setProperty("webdriver.firefox.bin", firefox_path);
    }

    try
    {
 DesiredCapabilities cap = DesiredCapabilities.firefox();
        cap.setJavascriptEnabled(true);
        cap.setCapability("marionette", true);
        driver = new FirefoxDriver(cap);
    

    //  driver =  new MarionetteDriver();
    }
    catch (Exception ex)
    {
        System.out.println ("Exception launching Marionette driver... possibly XP or missing msvcr110.dll: " + ex.toString());
        Prompter fallbackprompt = new Prompter ("We could not launch the Marionette driver, will fallback to HTMLUnitDriver");
       
        SiteTest.setTargetBrowser("Silent Mode (HTMLUnit)");
    }
      
     break;
            
    case "Firefox":
     if ("Windows32".equals(OSType))
     {
       System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-0.11.1-win32\\geckodriver.exe");
     }
     if ("Windows64".equals(OSType))
     {
       System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-0.11.1-win64\\geckodriver.exe");
     }
     if ("Mac".equals(OSType))
     {
      System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-0.11.1-osx\\geckodriver");
     }
     if ("Linux-32".equals(OSType))
     {
      System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-0.11.1-linux32\\geckodriver");
     }
     if ("Linux-64".equals(OSType))
     {
      System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-0.11.1-linux64\\geckodriver");
     }
   
    if (firefox_path!=null) {
        System.setProperty("webdriver.firefox.bin", firefox_path);
    }

    try
    {
 DesiredCapabilities cap = DesiredCapabilities.firefox();
        cap.setJavascriptEnabled(true);
        cap.setCapability("marionette", true);
        driver = new FirefoxDriver(cap);
    

    //  driver =  new MarionetteDriver();
    }
    catch (Exception ex)
    {
        System.out.println ("Exception launching Marionette driver... possibly XP or missing msvcr110.dll: " + ex.toString());
        Prompter fallbackprompt = new Prompter ("We could not launch the Marionette driver, will fallback to HTMLUnitDriver");
       
        SiteTest.setTargetBrowser("Silent Mode (HTMLUnit)");
    }
      
     break;
     
     case "Silent Mode (HTMLUnit)":
     driver = new HtmlUnitDriver();  
     break;
     
     case "Internet Explorer-32":
     System.setProperty("webdriver.ie.driver", "lib\\iedriverserver_win32\\IEDriverServer.exe");
     try
     {
     driver = new InternetExplorerDriver();
     }
     catch (Exception ex)
     {
         System.out.println ("Exception launching Internet Explorer driver: " + ex.toString());
         Prompter fallbackprompt = new Prompter ("We could not launch the Internet Explorer driver, will fallback to HTMLUnitDriver");
         SiteTest.setTargetBrowser("Silent Mode (HTMLUnit)");
     }
     break;
     case "Internet Explorer-64":
     System.setProperty("webdriver.ie.driver", "lib\\iedriverserver_win64\\IEDriverServer.exe");
     try
     {
     driver = new InternetExplorerDriver();
     }
     catch (Exception ex)
             {
             System.out.println ("Exception launching Internet Explorer-64 driver: " + ex.toString());
              Prompter fallbackprompt = new Prompter ("We could not launch the Internet Explorer 64 driver, will fallback to HTMLUnitDriver");
         SiteTest.setTargetBrowser("Silent Mode (HTMLUnit)");    
             }
     break;
     case "Chrome":
     if ("Windows32".equals(OSType))
     {
     System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_win32\\chromedriver.exe");
     }
       if ("Windows64".equals(OSType))
     {
     System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_win32\\chromedriver.exe");
     }
     if ("Mac".equals(OSType))
     {
     System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_mac32\\chromedriver-mac32");
     }
     if ("Linux-32".equals(OSType))
     {
     System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_linux32\\chromedriver-linux32");
     }
     if ("Linux-64".equals(OSType))
     {
     System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_linux64\\chromedriver-linux64");
     }
     try
     {
        driver = new ChromeDriver();     
     }
   catch (Exception ex)
   {
       System.out.println ("Problem launching Chromedriver: " + ex.toString());
        Prompter fallbackprompt = new Prompter ("We could not launch the Chrome driver, will fallback to HTMLUnitDriver");
       SiteTest.setTargetBrowser("Silent Mode (HTMLUnit)");
   }
     break;
   case "Chrome (WinXP)":
         ChromeOptions options = new ChromeOptions();
      if (chrome_path!=null) {
        
options.setBinary(chrome_path);


    }
     System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_win32\\chromedriver-winxp.exe");
   
    
     try
     {
        driver = new ChromeDriver(options);     
     }
   catch (Exception ex)
   {
       System.out.println ("Problem launching Chromedriver for XP: " + ex.toString());
        Prompter fallbackprompt = new Prompter ("We could not launch the Chrome WinXP driver, will fallback to HTMLUnitDriver");
       SiteTest.setTargetBrowser("Silent Mode (HTMLUnit)");
   }
     break;
         
         default: 
            driver = new ChromeDriver();
                     break;
   }
  
  int WaitTime = SiteTest.GetWaitTime();
 driver.manage().timeouts().implicitlyWait(WaitTime, TimeUnit.SECONDS);
     int totalpause = WaitTime * 1000;
        
  
  int thisbugindex = 0;
  
  
     for (Procedure thisbug : SiteTest.BugArray)
      {
          SiteTest.BugViewArray.get(thisbugindex).JButtonRunTest.setText("Running...");
   int bug_INT = thisbugindex+1;
  String bug_ID = Integer.toString(bug_INT);


int action_INT = 0;
String action_ID = "";
ProcedureView thisbugview = SiteTest.BugViewArray.get(thisbugindex);

if (!"Dataloop".equals(thisbugview.Type))
{
    action_INT=0;
   for( Action ThisAction : thisbug.ActionsList ) {
           String original_value = ThisAction.Variable2;
 action_INT++;
 action_ID = Integer.toString(action_INT);
           if (!ThisAction.Locked)
   {
   try
   {
      try
  {
   Thread.sleep(totalpause);  
  }
  catch (Exception ex)
  {
      System.out.println ("Exception when sleeping: " + ex.toString());
  }
                     String varfieldname="";
       if (ThisAction.Variable2.contains("[stored_varname-start]"))
       {
         varfieldname = ThisAction.Variable2;
            int indexof_end_tag = varfieldname.indexOf("[stored_varname-end]");
      // assuming name of "[stored_varname-start]" and "[stored_varname-end]"
         String fieldname = varfieldname.substring(22, indexof_end_tag);
         ThisAction.Variable2 = SiteTest.GetStoredVariableValue(fieldname);
          ThisAction.RunAction(driver);
          ThisAction.Variable2 = "[stored_varname-start]"+fieldname+"[stored_varname-end]";
       }
       else
       {
         ThisAction.RunAction(driver);    
       }
       
      
       if (!"".equals(ThisAction.tostore_varvalue))
       {
        
           SiteTest.VarHashMap.put(ThisAction.tostore_varname, ThisAction.tostore_varvalue);
       }
    if (ThisAction.tostore_varlist.size()>0)
       {
           SiteTest.VarLists.put(ThisAction.Variable2, ThisAction.tostore_varlist);
         
           
       }
    
       
      
    
   }
  catch (Exception ex)
     {
  System.out.println(ex.toString());
      ThisAction.Pass = false;
       
  FillReport();
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
      driver.quit();
              SiteTest.setCursor(Cursor.getDefaultCursor()); 
    System.exit(0);
    }  
      driver.quit();
              SiteTest.setCursor(Cursor.getDefaultCursor()); 
        //      publish(thisbugindex);
          break;
      
       
     }
      try
       {
     String full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
     String scrn = OneQuarterBase64(full_scrn);
     
    ThisAction.ScreenshotBase64 = "<img style = \"display: inline\" src=\"data:image/png;base64,"+scrn+"\" id = \"screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\"></img>";
 //  ThisAction.ScreenshotBase64 = "<img src=\"local.png\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\"></img>";
       }
       catch (Exception ex)
       {
            ThisAction.ScreenshotBase64 = "Screenshot Failed";
           System.out.println("Exception creating screenshot: " + ex.toString());     
    }
   
   }   
           else
           {
             ThisAction.Pass = true; 
           }
   }  

}
else
{
    
 int number_of_rows = thisbugview.myTable.DataTable.getRowCount();
 if (number_of_rows==0)
 {
  number_of_rows = FillTables(thisbug, thisbugview);
 }
  for( Action ThisAction : thisbug.ActionsList ) { 
 ThisAction.InitializeLoopTestVars(number_of_rows);
  } 
 
 for (int x = 0; x<number_of_rows; x++)
    {
  action_INT = 0;
    for( Action ThisAction : thisbug.ActionsList ) {
      
       action_INT++;
 action_ID = Integer.toString(action_INT) + "-" + Integer.toString(x);   
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
     
       ThisAction.loop_ScreenshotsBase64[x] = "<img style = \"display: inline\" id = \"screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\" style = \"visibility: visible\" src=\"\"></img>";
// ThisAction.loop_ScreenshotsBase64[x] = "<img id = \"screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\" style = \"visibility: visible\" src=\"local.png\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\"></img>";
      
        }
       else
        {
            try
            {
                  try
  {
  Thread.sleep(totalpause);  
  }
  catch (Exception ex)
  {
      System.out.println ("Exception when sleeping: " + ex.toString());
  }
                     String varfieldname="";
       if (ThisAction.Variable2.contains("[stored_varname-start]"))
       {
         varfieldname = ThisAction.Variable2;
            int indexof_end_tag = varfieldname.indexOf("[stored_varname-end]");
      // assuming name of "[stored_varname-start]" and "[stored_varname-end]"
         String fieldname = varfieldname.substring(22, indexof_end_tag);
      
         ThisAction.Variable2 = SiteTest.GetStoredVariableValue(fieldname);
          ThisAction.RunAction(driver);
          ThisAction.Variable2 = "[stored_varname-start]"+fieldname+"[stored_varname_end]";
       }
       else
       {
         ThisAction.RunAction(driver);    
       }
       
      
   
       
        ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
            }
             catch (Exception ex)
     {
   
          ThisAction.loop_pass_values[x] = false;
          ThisAction.loop_time_of_test[x] = LocalDateTime.now();
        
  FillReport();
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
       driver.quit();
               SiteTest.setCursor(Cursor.getDefaultCursor()); 
    System.exit(0);
    }
       driver.quit();
               SiteTest.setCursor(Cursor.getDefaultCursor()); 
               publish(thisbugindex);
          break;
       
     }
              try
        {
                  String full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
     String scrn = OneQuarterBase64(full_scrn);
      ThisAction.loop_ScreenshotsBase64[x] = "<img style = \"display: inline\" id = \"screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\" style = \"visibility: visible\" src=\"data:image/png;base64,"+scrn+"\"></img>";
// ThisAction.loop_ScreenshotsBase64[x] = "<img id = \"screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\" style = \"visibility: visible\" src=\"local.png\"></img>";
        }
                 catch (Exception ex)
       {
          ThisAction.loop_ScreenshotsBase64[x] = "Screenshot Failed";
           System.out.println("Exception creating screenshot: " + ex.toString());     
    }
        
            
        }
    }
    else
    {
  
       
            String concat_variable;
            String concat_variable2;
 concat_variable = var1Parser.GetFullValue(x, thisbugview.myTable);
 if (var1Parser.hasDataLoopVar)
 {
     ThisAction.Variable1 = concat_variable;
        if ("".equals(ThisAction.Variable1))
           {
               ThisAction.Variable1 = " ";
           }
      
 }

           concat_variable2 = var2Parser.GetFullValue(x, thisbugview.myTable);
   if (var2Parser.hasDataLoopVar)
 {
     ThisAction.Variable2 = concat_variable2;
     if ("".equals(ThisAction.Variable2))
           {
               ThisAction.Variable2 = " ";
           } 
 }  
 
     try
             {
                                 try
  {
   Thread.sleep(totalpause);  
  }
  catch (Exception ex)
  {
      System.out.println ("Exception when sleeping: " + ex.toString());
  }
      ThisAction.RunAction(driver);

      ThisAction.Variable1 = original_value1;
   ThisAction.Variable2 = original_value2;
   ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
        try
        {
                      String full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
     String scrn = OneQuarterBase64(full_scrn);
          ThisAction.loop_ScreenshotsBase64[x] = "<img style = \"display: inline\" id = \"screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\" style = \"visibility: visible\" src=\"data:image/png;base64,"+scrn+"\"></img>";
        }
                  catch (Exception ex)
       {
          ThisAction.loop_ScreenshotsBase64[x] = "Screenshot Failed";
           System.out.println("Exception creating screenshot: " + ex.toString());     
    }
        
        
             }
      catch (Exception ex)
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
      driver.quit();
          
    System.exit(0);
    }
      driver.quit();
               SiteTest.setCursor(Cursor.getDefaultCursor()); 
               publish(thisbugindex);
          break;
       
     }
             }
   
      }
      else
      {
          ThisAction.Pass = true;
      }
     
     }
    
    
    }
     //check if all actions passed
    for( Action ThisAction : thisbug.ActionsList )
    {   
        ThisAction.Pass = false;
      
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
  int actions_passed = 0;
 for( Action ThisAction : thisbug.ActionsList )
    {   
     
      
      
        Boolean passvalue = ThisAction.Pass;
        
            if (passvalue)
            {
                actions_passed++;
            }
        
     
    }
    if (actions_passed == thisbug.ActionsList.size())
        {
            thisbug.Pass = true;
        }
    else
    {
        thisbug.Pass = false;
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
    driver.quit();
    }
     }
     else
     {
 
   driver.quit();
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
  if (!"Dataloop".equals(thisbugview.Type))
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
     else
     {
         BugPass = false;
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
     else
     {
         BugPass = false;
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
  public String OneQuarterBase64(String in_image)
  {
  
    String imageData = in_image;
 
    //convert the image data String to a byte[]
    byte[] dta = DatatypeConverter.parseBase64Binary(imageData);
    try (InputStream in = new ByteArrayInputStream(dta);) {
        BufferedImage fullSize = ImageIO.read(in);

        // Create a new image .7 the size of the original image
      double newheight_db = fullSize.getHeight() * .7;
      double newwidth_db = fullSize.getWidth() * .7;
   
        int newheight = (int)newheight_db;
        int newwidth = (int)newwidth_db;

        BufferedImage resized = new BufferedImage(newwidth, newheight, BufferedImage.SCALE_REPLICATE);

        Graphics2D g2 = (Graphics2D) resized.getGraphics();
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
      g2.drawImage(fullSize, 0, 0, newwidth, newheight, null);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write( resized, "png", baos );
            baos.flush();
           byte[] resizedInByte = baos.toByteArray();
      Base64Encoder enc_resized = new Base64Encoder();
 String out_image = enc_resized.encode(resizedInByte);

        return out_image;
        }


    } catch (IOException e) {
       System.out.println("error resizing screenshot" + e.toString());
        return "";
    }
  }
  public int FillTables(Procedure thisproc, ProcedureView thisprocview)
  {
      int number_of_rows = 0;
     for (Action ThisAction: thisproc.ActionsList)
     {
      String concat_variable;
 
              DataLoopVarParser var1Parser = new DataLoopVarParser(ThisAction.Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(ThisAction.Variable2);   
    if (var1Parser.hasDataLoopVar)
    {
 concat_variable = ThisAction.Variable1;
            String middle_part = concat_variable.substring(21, concat_variable.length()-20 );
            String[] parts = middle_part.split(",");
             if (parts[2].contains(":"))
            {  
            String[] parts2 = parts[2].split(":");
            String URLListName = parts2[1];
               if (SiteTest.VarLists.containsKey(URLListName))
            {
            SiteTest.UpdateDataLoopTable(SiteTest.VarLists.get(URLListName), thisproc, thisprocview);
            number_of_rows = SiteTest.VarLists.get(URLListName).size();
            }
            }
        } 
    if (var2Parser.hasDataLoopVar)
    {
 concat_variable = ThisAction.Variable2;
            String middle_part = concat_variable.substring(21, concat_variable.length()-20 );
            String[] parts = middle_part.split(",");
             if (parts[2].contains(":"))
            {  
            String[] parts2 = parts[2].split(":");
            String URLListName = parts2[1];
                  if (SiteTest.VarLists.containsKey(URLListName))
            {
            SiteTest.UpdateDataLoopTable(SiteTest.VarLists.get(URLListName), thisproc, thisprocview);
            number_of_rows = SiteTest.VarLists.get(URLListName).size();
            }
            }
        } 
    
    }
     return number_of_rows;
     }
 
}
