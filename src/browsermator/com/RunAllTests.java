
package browsermator.com;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class RunAllTests extends SwingWorker<String, Integer>
{
SeleniumTestToolData STAppData;
SeleniumTestTool STAppFrame;
String targetbrowser;
String OSType;
WebDriver driver;
String firefox_path;
String chrome_path;
FireFoxProperties FFprops;
BrowserMatorReport BrowserMatorReport;
Boolean RUNWITHGUI;

Boolean paused = false;
  
 public RunAllTests (SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData)
 {
         STAppFrame.RefreshViewData();
  STAppData.RefreshData();
  STAppFrame.UpdateDisplay();
  RUNWITHGUI = true;
  FFprops = new FireFoxProperties(targetbrowser);
  this.firefox_path = FFprops.LoadFirefoxPath();
  this.chrome_path = FFprops.LoadChromePath();
   this.STAppFrame = STAppFrame;
   this.STAppData = STAppData;
    this.STAppData.cancelled = false;
  this.targetbrowser = STAppData.TargetBrowser;
  this.OSType = STAppData.OSType;
  STAppFrame.showTaskGUI();

 setProgressListeners();
  
 
    
 }
 
 public RunAllTests (SeleniumTestToolData in_SiteTest)
 {
  //   STAppData.RefreshData();
 //we're in no GUI Mode
     RUNWITHGUI = false;
  FFprops = new FireFoxProperties(targetbrowser);
  this.firefox_path = FFprops.LoadFirefoxPath();
  this.chrome_path = FFprops.LoadChromePath();
   this.STAppData = in_SiteTest;
    this.STAppData.cancelled = false;
  this.targetbrowser = in_SiteTest.TargetBrowser;
  this.OSType = in_SiteTest.OSType;


      
 }
 public synchronized void Pause() {
       STAppFrame.Pause();
        this.paused = true;
    }
  public synchronized void Continue() {
      STAppFrame.Continue();
        this.paused = false;
         synchronized(this) {
            this.notifyAll();
        }
    }
 protected synchronized void waitWhenPaused() {
        while(this.paused) {
            try
            {
            this.wait();
            }
            catch (Exception ex)
            {
                
            }
        }
 }
 

public void setProgressListeners()
 {
   STAppFrame.addJButtonCancelActionListener(new ActionListener() {
    public void actionPerformed (ActionEvent evt) {

     LoudCall<Void, String> procMethod = new LoudCall<Void, String>() {
            @Override
            public Void call() throws Exception {
            shoutOut("cancel");
                    Thread.sleep(100);
                    return null;
                      }
        };
      
       (new ListenerTask<Void, String>(procMethod) {
            @Override
            protected void process(List<String> chunks) {
             STAppData.cancelled = true;
             STAppFrame.jButtonCancel.setText("Cancelling...");
             
            STAppFrame.enablejButtonDoStuff(false);
           }
       }).execute();
       
   }    
 });
   STAppFrame.addJButtonContinueActionListener(new ActionListener() {
    public void actionPerformed (ActionEvent evt) {


     LoudCall<Void, String> procMethod = new LoudCall<Void, String>() {
            @Override
            public Void call() throws Exception {
            shoutOut("continue");
                    Thread.sleep(100);
                    return null;
                      }
        };
      
       (new ListenerTask<Void, String>(procMethod) {
            @Override
            protected void process(List<String> chunks) {
     Continue();
    
        
           }
       }).execute(); 
   }    
 });
     STAppFrame.addJButtonPauseActionListener(new ActionListener() {
    public void actionPerformed (ActionEvent evt) {

    LoudCall<Void, String> procMethod = new LoudCall<Void, String>() {
            @Override
            public Void call() throws Exception {
            shoutOut("pause");
                    Thread.sleep(100);
                    return null;
                      }
        };
      
       (new ListenerTask<Void, String>(procMethod) {
            @Override
            protected void process(List<String> chunks) {
 Pause();
           
           }
       }).execute();
  
    }
     });
        
 } 
@Override 
public String doInBackground()
 {
     String ret_val = "";
    if (RUNWITHGUI)
    {
     STAppFrame.clearPassFailColors();
     STAppFrame.disableAdds();
     STAppFrame.disableRemoves();
     STAppFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
     STAppFrame.setRunButtonEnabled(false);
    
    }
     STAppData.testRunning = true;
     
     if (RUNWITHGUI)
     {
        for (ProcedureView thisbugview : STAppFrame.BugViewArray)
      {
          thisbugview.JLabelPass.setVisible(false);
      }
          RunAllActions(STAppFrame, STAppData, targetbrowser, OSType);
     }
     else
     {
           RunAllActions(STAppData, targetbrowser, OSType);
     }
  
     
    ret_val = "Run All Procedures";
    
    
      return ret_val;
 }
@Override
 protected void done()
 {
    
      if (RUNWITHGUI)
     {
          STAppFrame.enablejButtonDoStuff(true);
    STAppFrame.enableAdds();
    STAppFrame.enableRemoves();
    STAppFrame.hideTaskGUI();
    STAppFrame.resetRunButtons();
    STAppFrame.setJTextFieldProgress("");
  STAppFrame.jButtonCancel.setText("Cancel");
     }
 
   STAppData.testRunning = false; 
   
    if (RUNWITHGUI)
     {
   try
    {
     
       STAppFrame.setCursor(Cursor.getDefaultCursor());   
    STAppFrame.setRunButtonEnabled(true);

  
    }
    catch (Exception ex)
    {
      System.out.println("exception setting cursor: " + ex.toString());
       STAppFrame.setRunButtonEnabled(true);
    }
     }

    boolean closecaught = false;
   
 try
 {
     driver.close();
 }
 catch (Exception e)
 {
     closecaught = true;
     System.out.println(e.toString());
     try {
                driver.quit();
            }
            catch (Exception exce)
            {
               
                System.out.println("Exception quitting" + exce.toString());
            }
 }
 if (closecaught)
 {
 
 }
 else
 {
     try
 {
   driver.quit();
 }
 catch (Exception ex)
 {
     // don't worry it should close
 }
  
 }
 
  
    
    if (STAppData.getExitAfter())
    {
    System.exit(0);
    }
 
    
             FillReport();
    
     BrowserMatorReport = new BrowserMatorReport(STAppData);
      if (STAppData.getShowReport())
       {
     

        if (STAppData.getIncludeScreenshots())
       {
       BrowserMatorReport.ShowHTMLReport();
       }
       else
       {
           BrowserMatorReport.ShowTextReport();
       }
      
     
       }
          
                if (STAppData.getEmailReportFail())
    {
        if (STAppData.AllTestsPassed)
        {
            
        }
        else
        {
            BrowserMatorReport.EmailReport();
        }
    }
    if (STAppData.getEmailReport())
    {
 
        BrowserMatorReport.EmailReport();
  
    }
         if (STAppFrame==null)
     {
    
     System.exit(0);
      }
    if (STAppData.getExitAfter())
    {
    
          
    System.exit(0);
    }
  
 }
 
  
@Override
 protected void process ( List <Integer> bugindex)
 {

    if (RUNWITHGUI)
    {
    STAppFrame.BugViewArray.get(bugindex.get(0)).JButtonRunTest.setText("Run");
      if (STAppData.BugArray.get(bugindex.get(0)).Pass)
    {
      
     
      STAppFrame.BugViewArray.get(bugindex.get(0)).JLabelPass.setBackground(Color.GREEN);
      STAppFrame.BugViewArray.get(bugindex.get(0)).JLabelPass.setText("Passed");
      
   
       STAppFrame.BugViewArray.get(bugindex.get(0)).JLabelPass.setVisible(true);
    
    }
     else
     {
     
     
      
       STAppFrame.BugViewArray.get(bugindex.get(0)).JLabelPass.setBackground(Color.RED);
       STAppFrame.BugViewArray.get(bugindex.get(0)).JLabelPass.setText("Failed");
      
       
       STAppFrame.BugViewArray.get(bugindex.get(0)).JLabelPass.setVisible(true);
    
     }
    }
 }
  public void RunAllActions(SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, String TargetBrowser, String OSType)
 {
 STAppData.TimeOfRun = LocalDateTime.now();
    LocalDate today = LocalDate.now();
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

// FirefoxProfile profile = new FirefoxProfile();

 //DesiredCapabilities cap = DesiredCapabilities.firefox();
   //     cap.setJavascriptEnabled(true);
   //     cap.setCapability("marionette", false);
        
   //     profile.setPreference("dom.max_script_run_time", 1);
        driver = new FirefoxDriver();
    

    //  driver =  new MarionetteDriver();
    }
    catch (Exception ex)
    {
        System.out.println ("Exception launching Marionette driver... possibly XP or missing msvcr110.dll: " + ex.toString());
        if (chrome_path!=null) {
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver, will fallback to Chrome 49", false,0,0);
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
// DesiredCapabilities cap = DesiredCapabilities.firefox();
  //      cap.setJavascriptEnabled(false);

  //     FirefoxProfile profile = new FirefoxProfile();

 // DesiredCapabilities cap = DesiredCapabilities.firefox();
  //    cap.setJavascriptEnabled(true);
  //     cap.setCapability("marionette", true);
        
 //      profile.setPreference("dom.max_script_run_time", 30);
        driver = new FirefoxDriver();
       

    //  driver =  new MarionetteDriver();
    }
    catch (Exception ex)
    {
        System.out.println ("Exception launching Marionette driver... possibly XP or missing msvcr110.dll: " + ex.toString());
       if (chrome_path!=null) {
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver, will fallback to Chrome 49", false,0,0);
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
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the IEdriver, will fallback to Chrome 49", false,0,0);
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
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the IEdriver, will fallback to Chrome 49", false,0,0);
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
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver, will fallback to Chrome 49", false,0,0);
         FallbackDriver("Chrome49");
     

    }

          else
          {
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver, will fallback to HTMLUnitDriver", false,0,0);
         FallbackDriver("HTMLUnit");
          }
   }
     break;

     
     
   case "Chrome 49":
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
       System.out.println ("Problem launching Chromedriver 49: " + ex.toString());
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chrome 49 driver, will fallback to HTMLUnitDriver", false,0, 0);
      FallbackDriver("HTMLUnit");
   }
     break;
         
         default: 
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
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver, will fallback to Chrome 49", false,0,0);
         FallbackDriver("Chrome49");
     

    }
   }
    }
    int WaitTime = 0;

  WaitTime = STAppData.getWaitTime();

  //timeouts still buggy.. removed
 // int Timeout = SiteTest.getTimeout();
//  int Timeout = 5;
  
// driver.manage().timeouts().implicitlyWait(Timeout, TimeUnit.SECONDS);
// driver.manage().timeouts().pageLoadTimeout(Timeout, TimeUnit.SECONDS);
// driver.manage().timeouts().setScriptTimeout(Timeout, TimeUnit.SECONDS);

     int totalpause = WaitTime * 1000;
        
  
  int thisbugindex = 0;
  

  
     for (Procedure thisbug : STAppData.BugArray)
      {
          String bugtitle = STAppData.BugArray.get(thisbugindex).getBugTitle();
      LoudCall<Void, String> procMethod = new LoudCall<Void, String>() {
            @Override
            public Void call() throws Exception {
            shoutOut(bugtitle);
                    Thread.sleep(100);
                    return null;
                      }
        };
       if (RUNWITHGUI)
       {
          (new ListenerTask<Void, String>(procMethod) {
            @Override
            protected void process(List<String> chunks) {
             STAppFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
            
             }
        }).execute();

                   STAppFrame.BugViewArray.get(thisbugindex).JButtonRunTest.setText("Running...");           
      
       }
   int bug_INT = thisbugindex+1;
  String bug_ID = Integer.toString(bug_INT);


int action_INT = 0;
String action_ID = "";

if (!"Dataloop".equals(thisbug.Type))
{
    action_INT=0;
   for( Action ThisAction : thisbug.ActionsList ) {
         if (STAppData.cancelled)
          {
          
             publish(thisbugindex);
             
             break;
          }
         waitWhenPaused();
      
      
       String original_value = ThisAction.Variable2;
       
 action_INT++;
 action_ID = Integer.toString(action_INT);

           if (!ThisAction.Locked)
   {
       if (RUNWITHGUI)
       {
          String action_title = ThisAction.Type + ": " + ThisAction.Variable1 + " " + ThisAction.Variable2;
               LoudCall<Void, String> actMethod = new LoudCall<Void, String>() {
            @Override
            public Void call() throws Exception {
            shoutOut(action_title);
                    Thread.sleep(100);
                    return null;
                      }
        };
          (new ListenerTask<Void, String>(actMethod) {
            @Override
            protected void process(List<String> chunks) {
             STAppFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
          
            }
        }).execute();
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
       if (RUNWITHGUI)
       {
           ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
        thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.Pass);
            publish(thisbugindex);
       }
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
      
         ThisAction.Variable2 = STAppData.GetStoredVariableValue(fieldname);

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
      
         ThisAction.Variable1 = STAppData.GetStoredVariableValue(fieldname);
          ThisAction.RunAction(driver);
          ThisAction.Variable1 = "[stored_varname-start]"+fieldname+"[stored_varname-end]";
                   }
           }
       
       }
       else
       {
                 if ("Pause with Continue Button".equals(ThisAction.Type))
        {
      
        int nothing =  ThisAction.RunAction(driver, "Actions Paused...", STAppData, 0, 0);
        }
                 else
                 {
         ThisAction.RunAction(driver);    
                 }
                 if (RUNWITHGUI)
                 {
                     ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
                  thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.Pass);
                 }
              
       }
       
  
       if (!"".equals(ThisAction.tostore_varvalue))
       {
        
           STAppData.VarHashMap.put(ThisAction.tostore_varname, ThisAction.tostore_varvalue);
       }
    if (ThisAction.tostore_varlist.length>0)
       {
      
           STAppData.VarLists.put(ThisAction.Variable2, ThisAction.tostore_varlist);

       }
    
       
      
    
   }
  catch (Exception ex)
     {
  System.out.println(ex.toString());
      ThisAction.Pass = false;
      if (RUNWITHGUI)
      {
            ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
        thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.Pass);
 
              publish(thisbugindex);
      }
          break;
      
       
     }
         if (STAppData.getIncludeScreenshots())
    { 
      try
       {
     File full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
     full_scrn.deleteOnExit();
   ThisAction.ScreenshotBase64 = "<img src=\"file:///" + full_scrn.getAbsolutePath() + "\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" style = \"display: none;\" class = \"report_screenshots\"></img>";
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
           if (RUNWITHGUI)
           {
                 ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
            thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.Pass);
           }
   }  

}
else
{
     int number_of_rows = 0;
    if ("urllist".equals(thisbug.DataLoopSource))
    {
       String[] randomList = new String[0]; 
       randomList = STAppData.VarLists.get(thisbug.URLListName);
      if (thisbug.getLimit()>0 || thisbug.getRandom())
      {
      randomList = STAppData.RandomizeAndLimitURLList(thisbug.URLListName,thisbug.getLimit(), thisbug.getRandom());
      }
      
      thisbug.setURLListData(randomList, thisbug.URLListName);
      if (RUNWITHGUI)
      {
            ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
      thisbugview.setJTableSourceToURLList(thisbug.URLListData, thisbug.URLListName);
    
      }
      number_of_rows = randomList.length;
    }
    else
    {
   if ("file".equals(thisbug.DataLoopSource))
    {
        if (thisbug.getLimit()>0 || thisbug.getRandom())
        {
            
         thisbug.setRunTimeFileSet(STAppData.RandomizeAndLimitFileList(thisbug.DataSet, thisbug.getLimit(), thisbug.getRandom())); 
     number_of_rows = thisbug.RunTimeFileSet.size();
        }
        else
        {
            thisbug.setRunTimeFileSet(thisbug.DataSet);
            number_of_rows = thisbug.RunTimeFileSet.size();
        }
        
    }     
    }
 
// if (number_of_rows==0)
// {
//  number_of_rows = FillTables(thisbug, thisbugview);
// }
  for( Action ThisAction : thisbug.ActionsList ) { 
 ThisAction.InitializeLoopTestVars(number_of_rows);
  } 

 for (int x = 0; x<number_of_rows; x++)
    {
        if (RUNWITHGUI)
        {
       ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);       
   STAppFrame.clearProcedurePassFailColors(thisbugview);
        }
   int changex = -1;
  action_INT = 0;
    for( Action ThisAction : thisbug.ActionsList ) {
       if (STAppData.cancelled)
          {
       
             publish(thisbugindex);
             break;
          }  
        waitWhenPaused();
         
          
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
        changex =  ThisAction.RunAction(driver, pause_message, STAppData, x, number_of_rows);
        
        ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
           if (STAppData.getIncludeScreenshots())
    { 

       ThisAction.loop_ScreenshotsBase64[x] = "<img id = \"Screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\" style = \"display: none;\" src=\"\"></img>";
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
            if (RUNWITHGUI)
            {
                  ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
             thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.Pass);
            }
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
      
         ThisAction.Variable2 = STAppData.GetStoredVariableValue(fieldname);
        String action_title3 = ThisAction.Type + ": " + ThisAction.Variable1 + " " + ThisAction.Variable2; 
                  
        if (RUNWITHGUI)
        {
        LoudCall<Void, String> actMethod = new LoudCall<Void, String>() {
            @Override
            public Void call() throws Exception {
            shoutOut(action_title3);
                    Thread.sleep(100);
                    return null;
                      }
        };
          (new ListenerTask<Void, String>(actMethod) {
            @Override
            protected void process(List<String> chunks) {
             STAppFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
        
            }
        }).execute();
        }
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
      
         ThisAction.Variable1 = STAppData.GetStoredVariableValue(fieldname);
       
         if (RUNWITHGUI)
         {
              String action_title2 = ThisAction.Type + ": " + ThisAction.Variable1 + " " + ThisAction.Variable2;
        LoudCall<Void, String> actMethod = new LoudCall<Void, String>() {
            @Override
            public Void call() throws Exception {
            shoutOut(action_title2);
                    Thread.sleep(100);
                    return null;
                      }
        };
          (new ListenerTask<Void, String>(actMethod) {
            @Override
            protected void process(List<String> chunks) {
            STAppFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
        
            }
        }).execute();
         }
          ThisAction.RunAction(driver);
          ThisAction.Variable1 = "[stored_varname-start]"+fieldname+"[stored_varname-end]";
                   }
           }
           
       }
       
       else
       {
           if (RUNWITHGUI)
           {
              String action_title = ThisAction.Type + ": " + ThisAction.Variable1 + " " + ThisAction.Variable2;
            
              LoudCall<Void, String> actMethod = new LoudCall<Void, String>() {
            @Override
            public Void call() throws Exception {
            shoutOut(action_title);
                    Thread.sleep(100);
                    return null;
                      }
        };
          (new ListenerTask<Void, String>(actMethod) {
            @Override
            protected void process(List<String> chunks) {
             STAppFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
          
            }
        }).execute();   
           }
         ThisAction.RunAction(driver);    
       }   
         
          if (!"".equals(ThisAction.tostore_varvalue))
       {
        
           STAppData.VarHashMap.put(ThisAction.tostore_varname, ThisAction.tostore_varvalue);
       }
       
        ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
            }
             catch (Exception ex)
     {
   
          ThisAction.loop_pass_values[x] = false;
          ThisAction.loop_time_of_test[x] = LocalDateTime.now();
        
             if (RUNWITHGUI)
             {
               publish(thisbugindex);
                 ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
                thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.loop_pass_values[x]);
             }
          break;
       
     }
                  if (STAppData.getIncludeScreenshots())
    { 
              try
        {
    File full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
     full_scrn.deleteOnExit();
   ThisAction.loop_ScreenshotsBase64[x] = "<img src=\"file:///" + full_scrn.getAbsolutePath() + "\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" style = \"display: none;\" class = \"report_screenshots\"></img>";
  
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
  
       
            String concat_variable="";
            String concat_variable2="";
            if ("urllist".equals(thisbug.DataLoopSource))
            {
 concat_variable = var1Parser.GetFullValueFromURLList(x, thisbug.URLListData);
            }
            if ("file".equals(thisbug.DataLoopSource))
            {
   concat_variable = var1Parser.GetFullValueFromFile(x, thisbug.RunTimeFileSet);             
            }
 if (var1Parser.hasDataLoopVar)
 {
     ThisAction.Variable1 = concat_variable;
        if ("".equals(ThisAction.Variable1))
           {
               ThisAction.Variable1 = " ";
           }
      
 }
    if ("urllist".equals(thisbug.DataLoopSource))
            {
 concat_variable2 = var2Parser.GetFullValueFromURLList(x, thisbug.URLListData);
            }
            if ("file".equals(thisbug.DataLoopSource))
            {
   concat_variable2 = var2Parser.GetFullValueFromFile(x, thisbug.RunTimeFileSet);             
            }
        
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
       if (RUNWITHGUI)
       {
            publish(thisbugindex);
              ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
             thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.loop_pass_values[x]);
       }
          break;
  }
                 }
                 if (RUNWITHGUI)
                 {
                      String action_title = ThisAction.Type + ": " + ThisAction.Variable1 + " " + ThisAction.Variable2;
             LoudCall<Void, String> actMethod = new LoudCall<Void, String>() {
            @Override
            public Void call() throws Exception {
            shoutOut(action_title);
                    Thread.sleep(100);
                    return null;
                      }
        };
          (new ListenerTask<Void, String>(actMethod) {
            @Override
            protected void process(List<String> chunks) {
             STAppFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
          
            }
        }).execute();   
                 }
      ThisAction.RunAction(driver);

      ThisAction.Variable1 = original_value1;
   ThisAction.Variable2 = original_value2;
   ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
             if (STAppData.getIncludeScreenshots())
    { 
        try
        {
     File full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
     full_scrn.deleteOnExit();
   ThisAction.loop_ScreenshotsBase64[x] = "<img src=\"file:///" + full_scrn.getAbsolutePath() + "\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" style = \"display: none;\" class = \"report_screenshots\"></img>";
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
        if (RUNWITHGUI)
        {
              ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
         thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.loop_pass_values[x]);
  
               publish(thisbugindex);
        }
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
       if (RUNWITHGUI)
        {
              ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
      thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.loop_pass_values[x]);
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
 
         if (STAppData.getPromptToClose())
     {
          Prompter thisContinuePrompt = new Prompter(STAppData.short_filename + " - Prompt to close webdriver", "Close webdriver/browser?", false,0, 0);
  
    


    
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
  
   
     
 }
 }

  public void FillReport()
  {
      int NumberOfTestsPassed = 0;   
      int BugIndex = 0;
  
    Boolean BugPass = false;
     for (Procedure thisbug : STAppData.BugArray)
      {
        ArrayList<Action> ActionsToLoop = thisbug.ActionsList;
  
 int NumberOfActionsPassed = 0;
  if (!"Dataloop".equals(thisbug.Type))
  {
       int ActionIndex = 0;
   for( Action TheseActions : ActionsToLoop ) {


    LocalDateTime stringtime =  LocalDateTime.now();
    TheseActions.TimeOfTest = stringtime;
       boolean TestState = TheseActions.Pass;
       if (TestState==true)
       {
        
           NumberOfActionsPassed++;
       }
       else
       {
      
           
       }

       ActionIndex++;

}
     if (NumberOfActionsPassed == STAppData.BugArray.get(BugIndex).ActionsList.size())
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
      int number_of_rows = 0;
 
      if ("urllist".equals(thisbug.DataLoopSource))
      {
 
      number_of_rows = STAppData.BugArray.get(BugIndex).URLListData.length;
      }
      if ("file".equals(thisbug.DataLoopSource))
      {
          number_of_rows = STAppData.BugArray.get(BugIndex).RunTimeFileSet.size();
      }
     
if (number_of_rows==0)
{
   int ActionIndex = 0;
    for( Action TheseActions : ActionsToLoop ) {
          LocalDateTime stringtime =  LocalDateTime.now();
          TheseActions.Pass = false;
        
         ActionIndex++;
    }  
}
    for (int x = 0; x<number_of_rows; x++)
    {
        
 int ActionIndex = 0;
    for( Action TheseActions : ActionsToLoop ) {

         
    LocalDateTime stringtime =  LocalDateTime.now();
   TheseActions.TimeOfTest = stringtime;
       boolean TestState = TheseActions.loop_pass_values[x];
       if (TestState==true)
       {
       
          String URL_TO_WRITE = "";
           if ("Go to URL".equals(TheseActions.Type))
           {
                DataLoopVarParser var1Parser = new DataLoopVarParser(TheseActions.Variable1);
            if (var1Parser.hasDataLoopVar)
            {
                 String concat_variable="";
         if ("urllist".equals(thisbug.DataLoopSource))
            {
 concat_variable = var1Parser.GetFullValueFromURLList(x, thisbug.URLListData);
            }
            if ("file".equals(thisbug.DataLoopSource))
            {
   concat_variable = var1Parser.GetFullValueFromFile(x, thisbug.RunTimeFileSet);             
            }

     URL_TO_WRITE = concat_variable;
       
             STAppData.AddURLToUniqueFileList(URL_TO_WRITE);
            }
           }
           NumberOfActionsPassed++;
       }
       else
       {
       
           
       }

       ActionIndex++;   
  }
    }
     if (NumberOfActionsPassed == STAppData.BugArray.get(BugIndex).ActionsList.size()*number_of_rows)
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
   if (RUNWITHGUI)
   {
       // needed??
  //  STAppFrame.BugViewArray.get(BugIndex).JButtonRunTest.setText("Run");
   }
   BugIndex++;
      }
     if (STAppData.getUniqueList())
     {
         String file_option = STAppData.getUniqueFileOption();
 STAppData.AddURLListToUniqueFile(file_option);
 STAppData.ClearVisittedURLList();
     }
     if (NumberOfTestsPassed==STAppData.BugArray.size())
     {
     STAppData.AllTestsPassed = true;
     }
     else
     {
        STAppData.AllTestsPassed = false;
     }

 
  }
 
//  public int FillTables(Procedure thisproc, ProcedureView thisprocview)
//  {
//      int number_of_rows = 0;
//     for (Action ThisAction: thisproc.ActionsList)
//     {
//         if (!ThisAction.Locked)
//         {
//      String concat_variable;
 
//              DataLoopVarParser var1Parser = new DataLoopVarParser(ThisAction.Variable1);
//    DataLoopVarParser var2Parser = new DataLoopVarParser(ThisAction.Variable2);   
//    if (var1Parser.hasDataLoopVar)
//    {
// concat_variable = ThisAction.Variable1;
 //           String middle_part = concat_variable.substring(21, concat_variable.length()-20 );
 //           String[] parts = middle_part.split(",");
 //            if (parts[2].contains(":"))
 //           {  
  //          String[] parts2 = parts[2].split(":");
  //          String URLListName = parts2[1];
   //            if ("urllist".equals(thisproc.DataLoopSource))
   //         {
      // testing random/limit
        
    //        SiteTest.RandomizeAndLimitURLList(thisproc.URLListName, thisprocview.getLimit(), thisprocview.getRandom());
           
    //        SiteTest.UpdateDataLoopURLListTable(URLListName, SiteTest.VarLists.get(URLListName), thisproc, thisprocview);   
      
          
   //         number_of_rows = SiteTest.VarLists.get(URLListName).length;

  //          }
  //          }
      
 //       } 
 //   if (var2Parser.hasDataLoopVar)
 //   {
// concat_variable = ThisAction.Variable2;
//            String middle_part = concat_variable.substring(21, concat_variable.length()-20 );
 //           String[] parts = middle_part.split(",");
//             if (parts[2].contains(":"))
 //           {  
 //           String[] parts2 = parts[2].split(":");
 //           String URLListName = parts2[1];
 //               if ("urllist".equals(thisproc.DataLoopSource))
 //           {
 //       SiteTest.RandomizeAndLimitURLList(thisprocview.getStoredArrayListName(), thisprocview.getLimit(), thisprocview.getRandom());
           
 //           SiteTest.UpdateDataLoopURLListTable(URLListName, SiteTest.VarLists.get(URLListName), thisproc, thisprocview);
      
 //           number_of_rows = SiteTest.VarLists.get(URLListName).length;
 //           }
 //           }
 //       } 
 //        }
 //   }
   
 //    return number_of_rows;
     
 //    }
  public void FallbackDriver(String fallbackdriver)
  {
      if ("HTMLUnit".equals(fallbackdriver))
      {
          STAppData.setTargetBrowser("Silent Mode (HTMLUnit)");
          if (RUNWITHGUI)
          {
              STAppFrame.setTargetBrowserView("Silent Mode (HTMLUnit)");
          }
          driver = new HtmlUnitDriver();
      }
      else
      {
       STAppData.setTargetBrowser("Chrome 49");
       if (RUNWITHGUI)
       {
       STAppFrame.setTargetBrowserView("Chrome 49");     
       }
            ChromeOptions options = new ChromeOptions();
options.setBinary(chrome_path);
 System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_win32\\chromedriver-winxp.exe");
  driver = new ChromeDriver(options);     
      }
  }
   public void RunAllActions(SeleniumTestToolData STAppData, String TargetBrowser, String OSType)
 {
 STAppData.TimeOfRun = LocalDateTime.now();
    LocalDate today = LocalDate.now();
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

// FirefoxProfile profile = new FirefoxProfile();

 //DesiredCapabilities cap = DesiredCapabilities.firefox();
   //     cap.setJavascriptEnabled(true);
   //     cap.setCapability("marionette", false);
        
   //     profile.setPreference("dom.max_script_run_time", 1);
        driver = new FirefoxDriver();
    

    //  driver =  new MarionetteDriver();
    }
    catch (Exception ex)
    {
        System.out.println ("Exception launching Marionette driver... possibly XP or missing msvcr110.dll: " + ex.toString());
        if (chrome_path!=null) {
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver, will fallback to Chrome 49", false,0,0);
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
// DesiredCapabilities cap = DesiredCapabilities.firefox();
  //      cap.setJavascriptEnabled(false);

  //     FirefoxProfile profile = new FirefoxProfile();

 // DesiredCapabilities cap = DesiredCapabilities.firefox();
  //    cap.setJavascriptEnabled(true);
  //     cap.setCapability("marionette", true);
        
 //      profile.setPreference("dom.max_script_run_time", 30);
        driver = new FirefoxDriver();
       

    //  driver =  new MarionetteDriver();
    }
    catch (Exception ex)
    {
        System.out.println ("Exception launching Marionette driver... possibly XP or missing msvcr110.dll: " + ex.toString());
       if (chrome_path!=null) {
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver, will fallback to Chrome 49", false,0,0);
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
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the IEdriver, will fallback to Chrome 49", false,0,0);
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
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the IEdriver, will fallback to Chrome 49", false,0,0);
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
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver, will fallback to Chrome 49", false,0,0);
         FallbackDriver("Chrome49");
     

    }

          else
          {
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver, will fallback to HTMLUnitDriver", false,0,0);
         FallbackDriver("HTMLUnit");
          }
   }
     break;

     
     
   case "Chrome 49":
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
       System.out.println ("Problem launching Chromedriver 49: " + ex.toString());
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chrome 49 driver, will fallback to HTMLUnitDriver", false,0, 0);
      FallbackDriver("HTMLUnit");
   }
     break;
         
         default: 
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
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver, will fallback to Chrome 49", false,0,0);
         FallbackDriver("Chrome49");
     

    }
   }
    }
  
  int WaitTime = STAppData.getWaitTime();
  //timeouts still buggy.. removed
 // int Timeout = SiteTest.getTimeout();
 // int Timeout = 20;
  
 // driver.manage().timeouts().implicitlyWait(Timeout, TimeUnit.SECONDS);
 //driver.manage().timeouts().pageLoadTimeout(Timeout, TimeUnit.SECONDS);
 //driver.manage().timeouts().setScriptTimeout(Timeout, TimeUnit.SECONDS);

     int totalpause = WaitTime * 1000;
        
  
  int thisbugindex = 0;
  

  
     for (Procedure thisbug : STAppData.BugArray)
      {
          String bugtitle = STAppData.BugArray.get(thisbugindex).getBugTitle();
      LoudCall<Void, String> procMethod = new LoudCall<Void, String>() {
            @Override
            public Void call() throws Exception {
            shoutOut(bugtitle);
                    Thread.sleep(100);
                    return null;
                      }
        };
        (new ListenerTask<Void, String>(procMethod) {
            @Override
            protected void process(List<String> chunks) {
             STAppFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
          
            }
        }).execute();
     
   int bug_INT = thisbugindex+1;
  String bug_ID = Integer.toString(bug_INT);


int action_INT = 0;
String action_ID = "";

if (!"Dataloop".equals(thisbug.Type))
{
    action_INT=0;
   for( Action ThisAction : thisbug.ActionsList ) {
         if (STAppData.cancelled)
          {
          
             publish(thisbugindex);
             
             break;
          } 
      waitWhenPaused();
      
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
      
         ThisAction.Variable2 = STAppData.GetStoredVariableValue(fieldname);

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
      
         ThisAction.Variable1 = STAppData.GetStoredVariableValue(fieldname);
          ThisAction.RunAction(driver);
          ThisAction.Variable1 = "[stored_varname-start]"+fieldname+"[stored_varname-end]";
                   }
           }
       
       }
       else
       {
                 if ("Pause with Continue Button".equals(ThisAction.Type))
        {
         
        int nothing =  ThisAction.RunAction(driver, "Actions Paused...", STAppData, 0, 0);
        }
                 else
                 {
         ThisAction.RunAction(driver);    
                 }
              
       }
       
  
       if (!"".equals(ThisAction.tostore_varvalue))
       {
        
           STAppData.VarHashMap.put(ThisAction.tostore_varname, ThisAction.tostore_varvalue);
       }
    if (ThisAction.tostore_varlist.length>0)
       {
      
           STAppData.VarLists.put(ThisAction.Variable2, ThisAction.tostore_varlist);

       }
    
       
      
    
   }
  catch (Exception ex)
     {
  System.out.println(ex.toString());
      ThisAction.Pass = false;
     
          break;
      
       
     }
         if (STAppData.getIncludeScreenshots())
    { 
      try
       {
     File full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
     full_scrn.deleteOnExit();
   ThisAction.ScreenshotBase64 = "<img src=\"file:///" + full_scrn.getAbsolutePath() + "\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" style = \"display: none;\" class = \"report_screenshots\"></img>";
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
     int number_of_rows = 0;
    if ("urllist".equals(thisbug.DataLoopSource))
    {
      String[] randomList = new String[0];  
      randomList = STAppData.VarLists.get(thisbug.URLListName);
      if (thisbug.getLimit()>0 || thisbug.getRandom())
      {
      randomList = STAppData.RandomizeAndLimitURLList(thisbug.URLListName,thisbug.getLimit(), thisbug.getRandom());
      }
      thisbug.setURLListData(randomList, thisbug.URLListName);
     
      number_of_rows = randomList.length;
    }
    else
    {
   if ("file".equals(thisbug.DataLoopSource))
    {
        if (thisbug.getLimit()>0 || thisbug.getRandom())
        {
            
         thisbug.setRunTimeFileSet(STAppData.RandomizeAndLimitFileList(thisbug.DataSet, thisbug.getLimit(), thisbug.getRandom())); 
     number_of_rows = thisbug.RunTimeFileSet.size();
        }
        else
        {
            thisbug.setRunTimeFileSet(thisbug.DataSet);
            number_of_rows = thisbug.RunTimeFileSet.size();
        }
        
    }     
    }
 
// if (number_of_rows==0)
// {
//  number_of_rows = FillTables(thisbug, thisbugview);
// }
  for( Action ThisAction : thisbug.ActionsList ) { 
 ThisAction.InitializeLoopTestVars(number_of_rows);
  } 

 for (int x = 0; x<number_of_rows; x++)
    {
  
   int changex = -1;
  action_INT = 0;
    for( Action ThisAction : thisbug.ActionsList ) {
       if (STAppData.cancelled)
          {
       
             publish(thisbugindex);
             break;
          } 
     
     waitWhenPaused();
         
          
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
        changex =  ThisAction.RunAction(driver, pause_message, STAppData, x, number_of_rows);
        
        ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
           if (STAppData.getIncludeScreenshots())
    { 

       ThisAction.loop_ScreenshotsBase64[x] = "<img id = \"Screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\" style = \"display: none;\" src=\"\"></img>";
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
      
         ThisAction.Variable2 = STAppData.GetStoredVariableValue(fieldname);
        String action_title3 = ThisAction.Type + ": " + ThisAction.Variable1 + " " + ThisAction.Variable2; 
                  
       
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
      
         ThisAction.Variable1 = STAppData.GetStoredVariableValue(fieldname);
       
      
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
        
           STAppData.VarHashMap.put(ThisAction.tostore_varname, ThisAction.tostore_varvalue);
       }
       
        ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
            }
             catch (Exception ex)
     {
   
          ThisAction.loop_pass_values[x] = false;
          ThisAction.loop_time_of_test[x] = LocalDateTime.now();
        
          
          break;
       
     }
                  if (STAppData.getIncludeScreenshots())
    { 
              try
        {
    File full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
     full_scrn.deleteOnExit();
   ThisAction.loop_ScreenshotsBase64[x] = "<img src=\"file:///" + full_scrn.getAbsolutePath() + "\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" style = \"display: none;\" class = \"report_screenshots\"></img>";
  
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
  
       
            String concat_variable="";
            String concat_variable2="";
            if ("urllist".equals(thisbug.DataLoopSource))
            {
 concat_variable = var1Parser.GetFullValueFromURLList(x, thisbug.URLListData);
            }
            if ("file".equals(thisbug.DataLoopSource))
            {
   concat_variable = var1Parser.GetFullValueFromFile(x, thisbug.RunTimeFileSet);             
            }
 if (var1Parser.hasDataLoopVar)
 {
     ThisAction.Variable1 = concat_variable;
        if ("".equals(ThisAction.Variable1))
           {
               ThisAction.Variable1 = " ";
           }
      
 }
    if ("urllist".equals(thisbug.DataLoopSource))
            {
 concat_variable2 = var2Parser.GetFullValueFromURLList(x, thisbug.URLListData);
            }
            if ("file".equals(thisbug.DataLoopSource))
            {
   concat_variable2 = var2Parser.GetFullValueFromFile(x, thisbug.RunTimeFileSet);             
            }
        
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
     
          break;
  }
                 }
             
      ThisAction.RunAction(driver);

      ThisAction.Variable1 = original_value1;
   ThisAction.Variable2 = original_value2;
   ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
             if (STAppData.getIncludeScreenshots())
    { 
        try
        {
     File full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
     full_scrn.deleteOnExit();
   ThisAction.loop_ScreenshotsBase64[x] = "<img src=\"file:///" + full_scrn.getAbsolutePath() + "\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" style = \"display: none;\" class = \"report_screenshots\"></img>";
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
 
         if (STAppData.getPromptToClose())
     {
          Prompter thisContinuePrompt = new Prompter(STAppData.short_filename + " - Prompt to close webdriver", "Close webdriver/browser?", false,0, 0);
  
    


    
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
  
   
     
 }
 }
}
