
package browsermator.com;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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
 
  FFprops = new FireFoxProperties(targetbrowser);
  this.firefox_path = FFprops.LoadFirefoxPath();
  this.chrome_path = FFprops.LoadChromePath();
   this.SiteTest = in_SiteTest;
    this.SiteTest.cancelled = false;
  this.targetbrowser = in_SiteTest.TargetBrowser;
  this.OSType = in_SiteTest.OSType;

  SiteTest.VarHashMap.clear();
  SiteTest.VarLists.clear();
 
    
 }
 
@Override 
public String doInBackground()
 {
     SiteTest.disableAdds();
     SiteTest.testRunning = true;
     
    SiteTest.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    SiteTest.setRunActionsButtonName("Running...(Click to Cancel)");
        for (ProcedureView thisbugview : SiteTest.BugViewArray)
      {
          thisbugview.JLabelPass.setVisible(false);
      }
    RunAllActions(SiteTest, targetbrowser, OSType);
    
    String donetext = "Run All Procedures";
     return donetext;
     
 }
@Override
 protected void done()
 {
     SiteTest.enableAdds();
    SiteTest.testRunning = false; 
    try
    {
        String donetext = get();
        SiteTest.setCursor(Cursor.getDefaultCursor());   
     SiteTest.setRunActionsButtonName(donetext);

  
    }
    catch (Exception ex)
    {
        if (ex.toString().contains("Cannot find firefox"))
        {
        FFprops.BrowseforFFPath();
        
 
        }
        SiteTest.setRunActionsButtonName("Run All Procedures");
 try
 {
     driver.close();
 }
 catch (Exception e)
 {
     System.out.println(e.toString());
     driver.quit();
 }
   driver.quit();
  
   SiteTest.setCursor(Cursor.getDefaultCursor()); 
    if (SiteTest.getExitAfter())
    {
    System.exit(0);
    }
       System.out.println(ex.getLocalizedMessage());
           
    }
        if (SiteTest.getPromptToClose())
     {
  
   
     }
     else
     {
 try
        {
        driver.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            driver.quit();
        }
   driver.quit();
     }
             FillReport();
    SiteTest.UpdateDisplay(); 
     BrowserMatorReport = new BrowserMatorReport(SiteTest);
      if (SiteTest.getShowReport())
       {
     

        if (SiteTest.getIncludeScreenshots())
       {
       BrowserMatorReport.ShowHTMLReport();
       }
       else
       {
           BrowserMatorReport.ShowTextReport();
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
 
  
@Override
 protected void process ( List <Integer> bugindex)
 {
 //   int updatebugindex = bugindex.size()-1;
    
    SiteTest.BugViewArray.get(bugindex.get(0)).JButtonRunTest.setText("Run");
      if (SiteTest.BugArray.get(bugindex.get(0)).Pass)
    {
      
     
      SiteTest.BugViewArray.get(bugindex.get(0)).JLabelPass.setBackground(Color.GREEN);
        SiteTest.BugViewArray.get(bugindex.get(0)).JLabelPass.setText("Passed");
      
       SiteTest.BugArray.get(bugindex.get(0)).Pass = true;
       SiteTest.BugViewArray.get(bugindex.get(0)).JLabelPass.setVisible(true);
    }
     else
     {
     
     
      
       SiteTest.BugViewArray.get(bugindex.get(0)).JLabelPass.setBackground(Color.RED);
       SiteTest.BugViewArray.get(bugindex.get(0)).JLabelPass.setText("Failed");
      
       
       SiteTest.BugViewArray.get(bugindex.get(0)).JLabelPass.setVisible(true);
       SiteTest.BugArray.get(bugindex.get(0)).Pass = true;
     }
    
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
       System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-win32\\geckodriver.exe");
     }   
     if ("Windows32".equals(OSType))
     {
       System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-win32\\geckodriver.exe");
     }
     if ("Windows64".equals(OSType))
     {
       System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-win64\\geckodriver.exe");
     }
     if ("Mac".equals(OSType))
     {
      System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-osx\\geckodriver");
     }
     if ("Linux-32".equals(OSType))
     {
      System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-linux32\\geckodriver");
     }
     if ("Linux-64".equals(OSType))
     {
      System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-linux64\\geckodriver");
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
        if (chrome_path!=null) {
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver, will fallback to Chrome (WinXP)", false,0,0);
              FallbackDriver("Chrome49");
         
    }
          else
          {
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver, will fallback to HTMLUnitDriver", false,0,0);
            FallbackDriver("HTMLUnit");
         
          }
    }
      
     break;
            
    case "Firefox":
    //legacy support
     if ("Windows".equals(OSType))
     {
        System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-win32\\geckodriver.exe");  
     }
     if ("Windows32".equals(OSType))
     {
       System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-win32\\geckodriver.exe");
     }
     if ("Windows64".equals(OSType))
     {
       System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-win64\\geckodriver.exe");
     }
     if ("Mac".equals(OSType))
     {
      System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-osx\\geckodriver");
     }
     if ("Linux-32".equals(OSType))
     {
      System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-linux32\\geckodriver");
     }
     if ("Linux-64".equals(OSType))
     {
      System.setProperty("webdriver.gecko.driver", "lib\\geckodriver-linux64\\geckodriver");
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
       if (chrome_path!=null) {
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver, will fallback to Chrome (WinXP)", false,0,0);
            FallbackDriver("Chrome49");
      
    }
          else
          {
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver, will fallback to HTMLUnitDriver", false,0,0);
         FallbackDriver("HTMLUnit");
          }
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
         if (chrome_path!=null) {
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the IEdriver, will fallback to Chrome (WinXP)", false,0,0);
             FallbackDriver("Chrome49");
     
    }
          else
          {
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the IEdriver, will fallback to HTMLUnitDriver", false,0,0);
          FallbackDriver("HTMLUnit");
          }
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
          if (chrome_path!=null) {
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the IEdriver, will fallback to Chrome (WinXP)", false,0,0);
           FallbackDriver("Chrome49");
        
    }
          else
          {
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the IEdriver, will fallback to HTMLUnitDriver", false,0,0);
          FallbackDriver("HTMLUnit");
          }
             }
     break;
     case "Chrome":
         //legacy support
         if ("Windows".equals(OSType))
     {
        System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_win32\\chromedriver.exe");
     }
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
     System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_mac64\\chromedriver");
     }
     if ("Linux-32".equals(OSType))
     {
     System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_linux32\\chromedriver");
     }
     if ("Linux-64".equals(OSType))
     {
     System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_linux64\\chromedriver");
     }
     try
     {
        driver = new ChromeDriver();     
     }
   catch (Exception ex)
   {
       System.out.println ("Problem launching Chromedriver: " + ex.toString());
        if (chrome_path!=null) {
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver, will fallback to Chrome (WinXP)", false,0,0);
         FallbackDriver("Chrome49");
     

    }

          else
          {
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver, will fallback to HTMLUnitDriver", false,0,0);
         FallbackDriver("HTMLUnit");
          }
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
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chrome WinXP driver, will fallback to HTMLUnitDriver", false,0, 0);
      FallbackDriver("HTMLUnit");
   }
     break;
         
         default: 
            driver = new ChromeDriver();
                     break;
   }
  
  int WaitTime = SiteTest.GetWaitTime();
 
//   driver.manage().timeouts().implicitlyWait(WaitTime, TimeUnit.SECONDS);
// driver.manage().timeouts().pageLoadTimeout(WaitTime, TimeUnit.SECONDS);
// driver.manage().timeouts().setScriptTimeout(WaitTime, TimeUnit.SECONDS);

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
         if (SiteTest.cancelled)
          {
          
             publish(thisbugindex);
             
             break;
          }  
       String original_value = ThisAction.Variable2;
 action_INT++;
 action_ID = Integer.toString(action_INT);
           if (!ThisAction.Locked)
   {
   try
   {
       if (totalpause>0)
       {
      try
  {
   Thread.sleep(totalpause);  
  }
  catch (Exception ex)
  {
      System.out.println ("Exception when sleeping: " + ex.toString());
       ThisAction.Pass = false;
            publish(thisbugindex);
          break;
        
  }
       }
                     String varfieldname="";
          if (ThisAction.Variable2.contains("[stored_varname-start]") || ThisAction.Variable1.contains("[stored_varname-start]"))
       {
           if (ThisAction.Variable2.contains("[stored_varname-start]"))
                   {
          varfieldname = ThisAction.Variable1;
         //   indexof_end_tag = varfieldname.indexOf("[stored_varname_end]")-1;
      // assuming name of "[stored_varname-start]" and "[stored_varname-end]"
               String[] split_testfield_end = varfieldname.split("\\[stored_varname\\-end\\]");

       
         String fieldname = split_testfield_end[0].substring(22);
      
         ThisAction.Variable2 = SiteTest.GetStoredVariableValue(fieldname);

         ThisAction.RunAction(driver);
          ThisAction.Variable2 = "[stored_varname-start]"+fieldname+"[stored_varname-end]";
                   }
           else
           {
              if (ThisAction.Variable1.contains("[stored_varname-start]"))
                   {
         varfieldname = ThisAction.Variable1;
         //   indexof_end_tag = varfieldname.indexOf("[stored_varname_end]")-1;
      // assuming name of "[stored_varname-start]" and "[stored_varname-end]"
               String[] split_testfield_end = varfieldname.split("\\[stored_varname\\-end\\]");

       
         String fieldname = split_testfield_end[0].substring(22);
      
         ThisAction.Variable1 = SiteTest.GetStoredVariableValue(fieldname);
          ThisAction.RunAction(driver);
          ThisAction.Variable1 = "[stored_varname-start]"+fieldname+"[stored_varname-end]";
                   }
           }
       
       }
       else
       {
                 if ("Pause with Continue Button".equals(ThisAction.Type))
        {
         
        int nothing =  ThisAction.RunAction(driver, "Actions Paused...", this.SiteTest, 0, 0);
        }
                 else
                 {
         ThisAction.RunAction(driver);    
                 }
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
       
 
              publish(thisbugindex);
          break;
      
       
     }
         if (SiteTest.getIncludeScreenshots())
    { 
      try
       {
     File full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
     full_scrn.deleteOnExit();
   ThisAction.ScreenshotBase64 = "<img src=\"file:///" + full_scrn.getAbsolutePath() + "\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\"></img>";
       }
       catch (Exception ex)
       {
            ThisAction.ScreenshotBase64 = "Screenshot Failed";
      //     System.out.println("Exception creating screenshot: " + ex.toString());     
    }
    }
         else
         {
              ThisAction.ScreenshotBase64 = ""; 
         }
   
   }   
           else
           {
              ThisAction.ScreenshotBase64 = ""; 
             ThisAction.Pass = true; 
           
           }
   }  

}
else
{
    String selectedarraylist = thisbugview.JComboBoxStoredArrayLists.getSelectedItem().toString(); 
    if (!"Select a stored URL List".equals(selectedarraylist))
    {
      thisbugview.setJTableSource(selectedarraylist);
      thisbug.setDataFile(selectedarraylist);
    }
   
     
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
   int changex = -1;
  action_INT = 0;
    for( Action ThisAction : thisbug.ActionsList ) {
       if (SiteTest.cancelled)
          {
       
             publish(thisbugindex);
             break;
          }   
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
        changex =  ThisAction.RunAction(driver, pause_message, this.SiteTest, x, number_of_rows);
        
        ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
           if (SiteTest.getIncludeScreenshots())
    { 

       ThisAction.loop_ScreenshotsBase64[x] = "<img id = \"Screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\" style = \"display: inline; visibility: visible;\" src=\"\"></img>";
    } 
           else
           {
             ThisAction.loop_ScreenshotsBase64[x] = "";
           }
        }
       else
        {
            try
            {
                   if (totalpause>0)
       {
                  try
  {
  Thread.sleep(totalpause);  
  }
  catch (Exception ex)
  {
  
         System.out.println ("Exception when sleeping: " + ex.toString());
       ThisAction.Pass = false;
            publish(thisbugindex);
          break;
  }
       }
      
     
       
                  String varfieldname="";
       if (ThisAction.Variable2.contains("[stored_varname-start]") || ThisAction.Variable1.contains("[stored_varname-start]"))
       {
           if (ThisAction.Variable2.contains("[stored_varname-start]"))
                   {
          varfieldname = ThisAction.Variable1;
         //   indexof_end_tag = varfieldname.indexOf("[stored_varname_end]")-1;
      // assuming name of "[stored_varname-start]" and "[stored_varname-end]"
               String[] split_testfield_end = varfieldname.split("\\[stored_varname\\-end\\]");

       
         String fieldname = split_testfield_end[0].substring(22);
      
         ThisAction.Variable2 = SiteTest.GetStoredVariableValue(fieldname);
          ThisAction.RunAction(driver);
          ThisAction.Variable2 = "[stored_varname-start]"+fieldname+"[stored_varname-end]";
                   }
           else
           {
              if (ThisAction.Variable1.contains("[stored_varname-start]"))
                   {
         varfieldname = ThisAction.Variable1;
         //   indexof_end_tag = varfieldname.indexOf("[stored_varname_end]")-1;
      // assuming name of "[stored_varname-start]" and "[stored_varname-end]"
               String[] split_testfield_end = varfieldname.split("\\[stored_varname\\-end\\]");

       
         String fieldname = split_testfield_end[0].substring(22);
      
         ThisAction.Variable1 = SiteTest.GetStoredVariableValue(fieldname);
          ThisAction.RunAction(driver);
          ThisAction.Variable1 = "[stored_varname-start]"+fieldname+"[stored_varname-end]";
                   }
           }
           
       }
       else
       {
         ThisAction.RunAction(driver);    
       }   
          if (!"".equals(ThisAction.tostore_varvalue))
       {
        
           SiteTest.VarHashMap.put(ThisAction.tostore_varname, ThisAction.tostore_varvalue);
       }
       
        ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
            }
             catch (Exception ex)
     {
   
          ThisAction.loop_pass_values[x] = false;
          ThisAction.loop_time_of_test[x] = LocalDateTime.now();
        
 
               publish(thisbugindex);
          break;
       
     }
                  if (SiteTest.getIncludeScreenshots())
    { 
              try
        {
    File full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
     full_scrn.deleteOnExit();
   ThisAction.loop_ScreenshotsBase64[x] = "<img src=\"file:///" + full_scrn.getAbsolutePath() + "\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\"></img>";
  
        }
                 catch (Exception ex)
       {
          ThisAction.loop_ScreenshotsBase64[x] = "Screenshot Failed";
     //      System.out.println("Exception creating screenshot: " + ex.toString());     
    }
    }
                  else
                  {
                   ThisAction.loop_ScreenshotsBase64[x] = "";    
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
                 if (totalpause>0)
                 {
                                 try
  {
   Thread.sleep(totalpause);  
  }
  catch (Exception ex)
  {
    
         System.out.println ("Exception when sleeping: " + ex.toString());
       ThisAction.Pass = false;
            publish(thisbugindex);
          break;
  }
                 }
      ThisAction.RunAction(driver);

      ThisAction.Variable1 = original_value1;
   ThisAction.Variable2 = original_value2;
   ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
             if (SiteTest.getIncludeScreenshots())
    { 
        try
        {
     File full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
     full_scrn.deleteOnExit();
   ThisAction.loop_ScreenshotsBase64[x] = "<img src=\"file:///" + full_scrn.getAbsolutePath() + "\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\"></img>";
       }
                  catch (Exception ex)
       {
          ThisAction.loop_ScreenshotsBase64[x] = "Screenshot Failed";
     //      System.out.println("Exception creating screenshot: " + ex.toString());     
    }
        
    } 
             else
             {
             ThisAction.loop_ScreenshotsBase64[x] = "";     
             }
             }
      catch (Exception ex)
     {
   
       ThisAction.Variable1 = original_value1;
       ThisAction.Variable2 = original_value2;
       ThisAction.loop_pass_values[x] = false;
        ThisAction.loop_time_of_test[x] = LocalDateTime.now();
        
  
               publish(thisbugindex);
          break;
       
     }
             }
   
      }
      else
      {
          ThisAction.Pass = true;
          ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
        
      }
     
     }
 
             if (changex!=x)
    {
        if (changex==-1)
        {
      
        }
        else
        {
        
        x=changex-1;
          
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
          Prompter thisContinuePrompt = new Prompter(SiteTest.short_filename, "Clicking continue will close webdriver/browser.", false,0, 0);
  
    


    
while(thisContinuePrompt.isVisible() == true){
       try
       {
 Thread.sleep(200);

       }
       catch (InterruptedException e)
               {
                  System.out.println("pause exception: " + e.toString());
                
              }
  
}
  
   
        try
        {
        driver.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            driver.quit();
        }
    driver.quit();
     } 
 }

  public void FillReport()
  {
      int NumberOfTestsPassed = 0;   
      int BugIndex = 0;
  
    Boolean BugPass = false;
     for (Procedure thisbug : SiteTest.BugArray)
      {
        ArrayList<Action> ActionsToLoop = thisbug.ActionsList;
        ArrayList<ActionView> ActionViewsToLoop = SiteTest.BugViewArray.get(BugIndex).ActionsViewList;

 int NumberOfActionsPassed = 0;
  if (!"Dataloop".equals(thisbug.Type))
  {
       int ActionIndex = 0;
   for( Action TheseActions : ActionsToLoop ) {


    LocalDateTime stringtime = TheseActions.TimeOfTest;
       boolean TestState = TheseActions.Pass;
       if (TestState==true)
       {
           ActionViewsToLoop.get(ActionIndex).JLabelPassFail.setText("Passed at " + stringtime);
           NumberOfActionsPassed++;
       }
       else
       {
          ActionViewsToLoop.get(ActionIndex).JLabelPassFail.setText("Fail at " + stringtime);
           
       }

       ActionIndex++;

}
     if (NumberOfActionsPassed == SiteTest.BugViewArray.get(BugIndex).ActionsViewList.size())
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
      int number_of_rows = SiteTest.BugViewArray.get(BugIndex).myTable.DataTable.getRowCount();

    for (int x = 0; x<number_of_rows; x++)
    {
        
 int ActionIndex = 0;
    for( Action TheseActions : ActionsToLoop ) {


    LocalDateTime stringtime = TheseActions.TimeOfTest;
   
       boolean TestState = TheseActions.loop_pass_values[x];
       if (TestState==true)
       {
           ActionViewsToLoop.get(ActionIndex).JLabelPassFail.setText("Passed at " + stringtime);
           NumberOfActionsPassed++;
       }
       else
       {
           ActionViewsToLoop.get(ActionIndex).JLabelPassFail.setText("Fail at " + stringtime);
           
       }

       ActionIndex++;   
  }
    }
     if (NumberOfActionsPassed == SiteTest.BugViewArray.get(BugIndex).ActionsViewList.size()*number_of_rows)
    {
        BugPass = true;
     
    
    
    }
     else
     {
         BugPass = false;
         
   
     }
  }
  
   if (BugPass.equals(true))
   {
       NumberOfTestsPassed++;
     
      
     
   }
   else
   {
       
   
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
 
  public int FillTables(Procedure thisproc, ProcedureView thisprocview)
  {
      int number_of_rows = 0;
     for (Action ThisAction: thisproc.ActionsList)
     {
         if (!ThisAction.Locked)
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
      
            SiteTest.UpdateDataLoopTable(URLListName, SiteTest.VarLists.get(URLListName), thisproc, thisprocview);
            
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
     
            SiteTest.UpdateDataLoopTable(URLListName, SiteTest.VarLists.get(URLListName), thisproc, thisprocview);
            number_of_rows = SiteTest.VarLists.get(URLListName).size();
            }
            }
        } 
         }
    }
   
     return number_of_rows;
     
     }
  public void FallbackDriver(String fallbackdriver)
  {
      if ("HTMLUnit".equals(fallbackdriver))
      {
          SiteTest.setTargetBrowser("Silent Mode (HTMLUnit)");
          driver = new HtmlUnitDriver();
      }
      else
      {
       SiteTest.setTargetBrowser("Chrome 49");
            ChromeOptions options = new ChromeOptions();
options.setBinary(chrome_path);
 System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_win32\\chromedriver-winxp.exe");
  driver = new ChromeDriver(options);     
      }
  }
 
}
