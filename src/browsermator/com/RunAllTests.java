
package browsermator.com;


import java.awt.Color;
import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.SwingWorker;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;



public class RunAllTests extends SwingWorker<String, Integer>
{
  
SeleniumTestToolData STAppData;
SeleniumTestTool STAppFrame;
String targetbrowser;
String OSType;
WebDriver driver;
String firefox_path;
String chrome_path;
String chrome_main_path;

BrowserMatorReport BrowserMatorReport;
Boolean RUNWITHGUI;
Boolean paused = false;
Boolean RunAgain = false;
// String BMPATH;
String WEBDRIVERSDIR;
String BrowsermatorAppFolder;
Map<String, Object> prefs;
ProgressFrame popOutFrame;
String waitForLoad;
String promptBehavior;
int EcTimeout;
 PageLoadStrategy PageLoadConstant= PageLoadStrategy.NORMAL;
 UnexpectedAlertBehaviour promptBehaviorConstant = UnexpectedAlertBehaviour.DISMISS;
 
 String stringPageLoadConstant = "normal";
 String downloadDir = "";
 BrowserMatorConfig appConfig = new BrowserMatorConfig();;

 
 public RunAllTests (SeleniumTestTool in_STAppFrame, SeleniumTestToolData in_STAppData)
 {
 prefs = new HashMap<String, Object>();
 BrowsermatorAppFolder =   System.getProperty("user.home")+File.separator+"BrowsermatorAppFolder"+File.separator;
   WEBDRIVERSDIR = BrowsermatorAppFolder + "Webdrivers" + File.separator;
          STAppData = in_STAppData;
          STAppFrame = in_STAppFrame;
     STAppFrame.RefreshViewData();
    
  STAppData.RefreshData();
  STAppFrame.UpdateDisplay();
  RUNWITHGUI = true;

  this.firefox_path = appConfig.getKeyValue("Firefox");
  this.chrome_path = appConfig.getKeyValue("Chrome 49");
  this.chrome_main_path = appConfig.getKeyValue("Chrome");
  this.downloadDir = appConfig.getKeyValue("downloaddir");
    this.STAppData.cancelled = false;
  this.targetbrowser = STAppData.TargetBrowser;
  this.waitForLoad = STAppData.waitForLoad;
  this.promptBehavior = STAppData.promptBehavior;
  this.OSType = STAppData.OSType;
  this.EcTimeout = 10;
  STAppFrame.showTaskGUI();
   switch (promptBehavior)
   {
       case "Dismiss":
           promptBehaviorConstant = UnexpectedAlertBehaviour.DISMISS;
          
           break;
       case "Accept":
         promptBehaviorConstant = UnexpectedAlertBehaviour.ACCEPT;
         
           break;
       case "Fail":
             promptBehaviorConstant = UnexpectedAlertBehaviour.IGNORE;
           break;
   }
   switch (waitForLoad)
   {
       case "Yes":
           PageLoadConstant = PageLoadStrategy.NORMAL;
           stringPageLoadConstant = "normal";
           break;
       case "No":
           PageLoadConstant = PageLoadStrategy.NONE;
           stringPageLoadConstant = "none";
           break;
       case "Local DOM Only":
           PageLoadConstant = PageLoadStrategy.EAGER;
           stringPageLoadConstant = "eager";
           break;
   }
 setProgressListeners();
  
 
    
 }
 
public RunAllTests (SeleniumTestToolData in_SiteTest)
 {
     
  this.firefox_path = appConfig.getKeyValue("Firefox");
  this.chrome_path = appConfig.getKeyValue("Chrome 49");
  this.chrome_main_path = appConfig.getKeyValue("Chrome");
  this.downloadDir = appConfig.getKeyValue("downloaddir");
     prefs = new HashMap<String, Object>();
      BrowsermatorAppFolder =   System.getProperty("user.home")+File.separator+"BrowsermatorAppFolder"+File.separator;
   WEBDRIVERSDIR = BrowsermatorAppFolder + "Webdrivers" + File.separator;
  //   STAppData.RefreshData();
 //we're in no GUI Mode
     STAppData = in_SiteTest;
       STAppData.RefreshData();
     RUNWITHGUI = false;
    
  

  
    this.STAppData.cancelled = false;
  this.targetbrowser = in_SiteTest.TargetBrowser;
  this.waitForLoad = in_SiteTest.waitForLoad;
  this.OSType = in_SiteTest.OSType;
   this.EcTimeout = 10;
   switch (waitForLoad)
   {
       case "Yes":
           PageLoadConstant = PageLoadStrategy.NORMAL;
           stringPageLoadConstant = "normal";
           break;
       case "No":
           PageLoadConstant = PageLoadStrategy.NONE;
           stringPageLoadConstant = "none";
           break;
       case "Local DOM Only":
           PageLoadConstant = PageLoadStrategy.EAGER;
           stringPageLoadConstant = "eager";
           break;
   }
popOutFrame = new ProgressFrame(in_SiteTest.short_filename);
 setProgressListeners(popOutFrame);
      
 }
 public synchronized void Pause() {
     if (RUNWITHGUI) {  STAppFrame.Pause(); } else {popOutFrame.Pause();}
        this.paused = true;
    }
  public void Continue() {
    if (RUNWITHGUI) {  STAppFrame.Continue();} else {popOutFrame.Continue();}
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
public void setProgressListeners(ProgressFrame popFrame)
 {
     popFrame.initFrame();
   popFrame.addJButtonCancelActionListener(new ActionListener() {
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
            popOutFrame.jButtonCancel.setText("Cancelling...");
             
         
           }
       }).execute();
       
   }    
 });
  popFrame.addJButtonContinueActionListener(new ActionListener() {
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
     popFrame.addJButtonPauseActionListener(new ActionListener() {
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
        if (STAppData.hasSentStoredVars)
     {
       STAppData.PromptForUserVarValues();
     }
     STAppData.testRunning = true;
     
     if (RUNWITHGUI)
     {
        for (ProcedureView thisbugview : STAppFrame.BugViewArray)
      {
          thisbugview.JLabelPass.setVisible(false);
      }
  
     }
            RunAllActions(STAppFrame, STAppData, targetbrowser, OSType);
  
     
    ret_val = "Run All Procedures";
    
    
      return ret_val;
 }
@Override
 protected void done()
 {

      if (RUNWITHGUI)
     {
      STAppData.setSMTPHostname(STAppFrame.getSMTPHostname());
      STAppData.setEmailLoginName(STAppFrame.getEmailLoginName());
      STAppData.setEmailPassword(STAppFrame.getEmailPassword());
      STAppData.setEmailTo(STAppFrame.getEmailTo());
      STAppData.setEmailFrom(STAppFrame.getEmailFrom());
      STAppData.setSubject(STAppFrame.getSubject());
      
     if (STAppFrame.getjCheckBoxUniqueURLsSelected())
     {
      STAppFrame.jButtonClearUniqueList.setEnabled(true);
     }
    STAppFrame.enablejButtonDoStuff(true);
    STAppFrame.enableAdds();
    STAppFrame.enableRemoves();
    STAppFrame.hideTaskGUI();
    STAppFrame.resetRunButtons();
    STAppFrame.setJTextFieldProgress("");
  STAppFrame.jButtonCancel.setText("Cancel");
     }
    else
      {
          popOutFrame.mainFrame.dispose();
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
// complicated close then try/catch stuff doesn't seem to be necessary anymore... 
 //   boolean closecaught = false;
   
// try
// {
//   if (driver!=null) {  driver.close(); }
// }
// catch (Exception e)
// {
   //  closecaught = true;
   //  System.out.println(e.toString());
   //  try {
   //             driver.quit();
   //         }
   //         catch (Exception exce)
   //         {
               
   //             System.out.println("Exception quitting" + exce.toString());
  //          }
 //}
// if (closecaught)
// {
 
// }
// else
// {
     try
 {
   if (driver != null) { driver.quit();}
 }
 catch (Exception ex)
 {
     // don't worry it should close
 }
  
// }

    FillReport();  
     
    if (STAppData.getExitAfter())
    {
    System.exit(0);
    }
  
      
    
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
 STAppData.initVarLists();
  File thisDriver =  new File( WEBDRIVERSDIR+"geckodriver-win32"+File.separator+"geckodriver.exe");
    switch (TargetBrowser)
   {
     
     case "Firefox-Marionette":
     // legacy file support
         
         if ("Windows".equals(OSType))
     {
           thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-win32"+File.separator+"geckodriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
    //   System.setProperty("webdriver.gecko.driver", "lib"+File.separator+"geckodriver-win32"+File.separator+"geckodriver.exe");
     }   
     if ("Windows32".equals(OSType))
     {
         thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-win32"+File.separator+"geckodriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());   
      // System.setProperty("webdriver.gecko.driver", "lib"+File.separator+"geckodriver-win32"+File.separator+"geckodriver.exe");
     }
     if ("Windows64".equals(OSType))
     {
         thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-win64"+File.separator+"geckodriver.exe");
         setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
    //   System.setProperty("webdriver.gecko.driver", "lib"+File.separator+"geckodriver-win64"+File.separator+"geckodriver.exe");
     }
     if ("Mac".equals(OSType))
     {
            thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-osx"+File.separator+"geckodriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());     
    //  System.setProperty("webdriver.gecko.driver", "lib"+File.separator+"geckodriver-osx"+File.separator+"geckodriver");
     }
     if ("Linux-32".equals(OSType))
     {
              thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-linux32"+File.separator+"geckodriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
    //  System.setProperty("webdriver.gecko.driver", "lib"+File.separator+"geckodriver-linux32"+File.separator+"geckodriver");
     }
     if ("Linux-64".equals(OSType))
     {
                thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-linux64"+File.separator+"geckodriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
   //   System.setProperty("webdriver.gecko.driver", "lib"+File.separator+"geckodriver-linux64"+File.separator+"geckodriver");
     }
   
    if (firefox_path!=null) {
        System.setProperty("webdriver.firefox.bin", firefox_path);
    }

    try
    {


        FirefoxOptions options = new FirefoxOptions();
    
        if (!"".equals(downloadDir))
        {
                FirefoxProfile profile = new FirefoxProfile();
profile.setPreference("browser.download.dir", downloadDir);
profile.setPreference("browser.download.folderList", 2);
profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "...");
    options.setProfile(profile);
        }        
options.addPreference("dom.webnotifications.enabled", false);
        options.setUnhandledPromptBehaviour(promptBehaviorConstant);
       options.setPageLoadStrategy(PageLoadConstant);
   
        driver = new FirefoxDriver(options);

    }
    catch (Exception ex)
    {
        System.out.println ("Exception launching Marionette driver... possibly XP or missing msvcr110.dll: " + ex.toString());
     
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch Marionette driver:" + ex.toString(), false,0,0);
     
         
          
    }
      
     break;
            
    case "Firefox":
   
     if ("Windows".equals(OSType))
     {
        
        thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-win32"+File.separator+"geckodriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
     }
     if ("Windows32".equals(OSType))
     {
         thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-win32"+File.separator+"geckodriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
     }
     if ("Windows64".equals(OSType))
     {
           thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-win64"+File.separator+"geckodriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
   //    System.setProperty("webdriver.gecko.driver", BMPATH+File.separator+"lib"+File.separator+"geckodriver-win64"+File.separator+"geckodriver.exe");
     }
     if ("Mac".equals(OSType))
     {
            thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-osx"+File.separator+"geckodriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
  //    System.setProperty("webdriver.gecko.driver", BMPATH+File.separator+"lib"+File.separator+"geckodriver-osx"+File.separator+"geckodriver");
     }
     if ("Linux-32".equals(OSType))
     {
            thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-linux32"+File.separator+"geckodriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
  //    System.setProperty("webdriver.gecko.driver",BMPATH+File.separator+ "lib"+File.separator+"geckodriver-linux32"+File.separator+"geckodriver");
     }
     if ("Linux-64".equals(OSType))
     {
           thisDriver =  new File(WEBDRIVERSDIR+"geckodriver-linux64"+File.separator+"geckodriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.gecko.driver", thisDriver.getAbsolutePath());  
    //  System.setProperty("webdriver.gecko.driver", BMPATH+File.separator+"lib"+File.separator+"geckodriver-linux64"+File.separator+"geckodriver");
     }
   
    if (firefox_path!=null) {
        System.setProperty("webdriver.firefox.bin", firefox_path);
    }

    try
    {

         FirefoxOptions options = new FirefoxOptions();
         
            if (!"".equals(downloadDir))
            {
                   FirefoxProfile profile = new FirefoxProfile();
profile.setPreference("browser.download.dir", downloadDir);
profile.setPreference("browser.download.folderList", 2);
profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "...");
    options.setProfile(profile);
            }
         options.addPreference("dom.webnotifications.enabled", false);
         options.setUnhandledPromptBehaviour(promptBehaviorConstant);
        options.setPageLoadStrategy(PageLoadConstant);
      
        driver = new FirefoxDriver(options);
       

    }
    catch (Exception ex)
    {
        System.out.println ("Exception launching Marionette driver... possibly XP or missing msvcr110.dll: " + ex.toString());
     
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver: " + ex.toString(), false,0,0);
      
      
    }
      
     break;
     
     case "Silent Mode (HTMLUnit)":
  //not implemented yet
//   DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
//  capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, promptBehaviorConstant);
//   capabilities.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, PageLoadConstant);
     driver = new HtmlUnitDriver();  
   if (driver==null){System.out.println("driver null");}
     break;
     
     case "Internet Explorer-32":
            thisDriver =  new File(WEBDRIVERSDIR+"iedriverserver_win32"+File.separator+"IEDriverServer.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.ie.driver", thisDriver.getAbsolutePath()); 
         InternetExplorerOptions IEOptions = new InternetExplorerOptions();
         IEOptions.setUnhandledPromptBehaviour(promptBehaviorConstant);
        IEOptions.setPageLoadStrategy(PageLoadConstant);
 
     try
     {
     driver = new InternetExplorerDriver(IEOptions);
     }
     catch (Exception ex)
     {
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch IEdriver:" + ex.toString(), false,0,0);        
     }
     break;
     case "Internet Explorer-64":
            thisDriver =  new File(WEBDRIVERSDIR+"iedriverserver_win64"+File.separator+"IEDriverServer.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.ie.driver", thisDriver.getAbsolutePath());  
        InternetExplorerOptions IEOptions64 = new InternetExplorerOptions();
        IEOptions64.setUnhandledPromptBehaviour(promptBehaviorConstant);
        IEOptions64.setPageLoadStrategy(PageLoadConstant);
     try
     {
     driver = new InternetExplorerDriver(IEOptions64);
     }
     catch (Exception ex)
             {
     
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch IEdriver: " +ex.toString(), false,0,0);  
          
             }
     break;
     case "Chrome":
       
          ChromeOptions options = new ChromeOptions();  
                 options.setUnhandledPromptBehaviour(promptBehaviorConstant);             
                // options.setPageLoadStrategy(PageLoadConstant);  not supported quite yet
                 prefs.put("profile.default_content_setting_values.notifications", 2);
                // prefs.put("--dns-prefetch-disable", );
                 if (!"".equals(downloadDir))
                 {
                 prefs.put("download.default_directory", downloadDir);
                 }
                 options.setExperimentalOption("prefs", prefs);  
                 options.addArguments("--dns-prefetch-disable");
         //      options.addArguments("--auto-open-devtools-for-tabs");
     
             if (chrome_main_path!=null) {
            
   
options.setBinary(chrome_main_path);


    }
         if ("Windows".equals(OSType))
     {
            thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
     //   System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_win32"+File.separator+"chromedriver.exe");
     }
     if ("Windows32".equals(OSType))
     {
                 thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
 // System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_win32"+File.separator+"chromedriver.exe");
 
  
     }
       if ("Windows64".equals(OSType))
     {
                 thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
   //  System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_win32"+File.separator+"chromedriver.exe");
     }
     if ("Mac".equals(OSType))
     {
                 thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_mac64"+File.separator+"chromedriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
   //  System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_mac64"+File.separator+"chromedriver");
     }
     if ("Linux-32".equals(OSType))
     {
                  thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_linux32"+File.separator+"chromedriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());    
   //  System.setProperty("webdriver.chrome.driver",BMPATH+File.separator+ "lib"+File.separator+"chromedriver_linux32"+File.separator+"chromedriver");
     }
     if ("Linux-64".equals(OSType))
     {
                    thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_linux64"+File.separator+"chromedriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
   //  System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_linux64"+File.separator+"chromedriver");
     }
     try
     {
        driver = new ChromeDriver(options);     
     }
   catch (Exception ex)
   {
       System.out.println ("Problem launching Chromedriver: " + ex.toString());
     
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver." + ex.toString(), false,0,0);
  
   }
  
      break;
     
     
   case "Chrome 49":
         ChromeOptions options49 = new ChromeOptions();
                 prefs.put("profile.default_content_setting_values.notifications", 2);
                 options49.setExperimentalOption("prefs", prefs);     
      if (chrome_path!=null) {
   
          
options49.setBinary(chrome_path);


    }
      // since new SElenium does not work with Chrome49, this was changed
       //          thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver-winxp.exe");
                 thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
      
                 setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
    // System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_win32"+File.separator+"chromedriver-winxp.exe");
   
    
     try
     {
        driver = new ChromeDriver(options49);     
     }
   catch (Exception ex)
   {
       System.out.println ("Problem launching Chromedriver 49: " + ex.toString());
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chrome 49 driver." + ex.toString(), false,0, 0);
          
   }
 
   
     break;
  case "Edge":
     
       String windir = System.getenv("windir");
                boolean is64bit = false;
    
    is64bit = (System.getenv("ProgramFiles(x86)") != null);
  String edgeDriverPath = windir + "\\SysWOW64\\MicrosoftWebDriver.exe";
        if (!is64bit)
        {
     edgeDriverPath = windir + "\\System32\\MicrosoftWebDriver.exe";
        }
       
  System.setProperty("webdriver.edge.driver", edgeDriverPath); 
     EdgeOptions edgeOptions = new EdgeOptions();
   
      edgeOptions.setPageLoadStrategy(stringPageLoadConstant);
      // edgeOptions.setUnhandledPromptBehaviour... to be implemented?
         try {
         
            driver = new EdgeDriver(edgeOptions);
   }
     catch (Exception ex)
   {
       System.out.println ("Problem launching EdgeDriver: " + ex.toString());
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Edge Driver. Location: "+edgeDriverPath+" Go to Settings > Update and Security > For Developer and then select Developer mode." + ex.toString(), false,0, 0);
    
   }
             
       break;
    
    }
    int WaitTime = 0;
       GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
       GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
       Rectangle graphicsConfigurationBounds = ge.getMaximumWindowBounds();
       int desiredWidth =  graphicsConfigurationBounds.width - 400;
       int desiredHeight =  graphicsConfigurationBounds.height;
       driver.manage().window().setPosition(new Point(0,0));
       driver.manage().window().setSize(new Dimension(desiredWidth,desiredHeight));

  WaitTime = STAppData.getWaitTime();
   EcTimeout = STAppData.getEcTimeout();


     int totalpause = WaitTime * 1000;
     
  
  int thisbugindex = 0;
  

  
     for (Procedure thisbug : STAppData.BugArray)
      {
          String bugtitle = STAppData.BugArray.get(thisbugindex).getBugTitle();
   
       if (RUNWITHGUI)
       {
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

                   STAppFrame.BugViewArray.get(thisbugindex).JButtonRunTest.setText("Running...");           
      
       }
       else
       {       LoudCall<Void, String> procMethod = new LoudCall<Void, String>() {
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
             popOutFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
            
             }
        }).execute();
       }
   int bug_INT = thisbugindex+1;
  String bug_ID = Integer.toString(bug_INT);


int action_INT = 0;
String action_ID = "";

if (!"Dataloop".equals(thisbug.Type))
{
    action_INT=0;
   for( BMAction ThisAction : thisbug.ActionsList ) {
         if (STAppData.cancelled)
          {
          
             publish(thisbugindex);
             
             break;
          }
         waitWhenPaused();
      
      
  
       
 action_INT++;
 action_ID = Integer.toString(action_INT);

           if (!ThisAction.Locked)
   {
         ThisAction.setEcTimeout(EcTimeout);
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
                if (chunks.size()>0)
                {
             STAppFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
                }
          
            }
        }).execute();
       }
       else
       {   String action_title = ThisAction.Type + ": " + ThisAction.Variable1 + " " + ThisAction.Variable2;
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
                if (chunks.size()>0)
                {
            popOutFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
                }
          
            }
        }).execute();
       }
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
          varfieldname = ThisAction.Variable2;
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
                 else if ("Switch Driver".equals(ThisAction.Type))
                 {
        
         ThisAction.RunAction(this);    
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
    if (ThisAction.tostore_varlist.size()>0)
       {
  
        STAppData.VarLists.put(ThisAction.Variable2, ThisAction.tostore_varlist);
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
   int actionspassed = 0;
 for (BMAction thisaction: thisbug.ActionsList)
 {
     Boolean passvalue = thisaction.Pass;
     if (passvalue)
     {
         actionspassed++;
     }
 }
  int sizeof = thisbug.ActionsList.size();
    if (actionspassed==sizeof)
    {
        thisbug.Pass = true;
    }
    else
    {
        thisbug.Pass = false;
    }

}
else
{
     int number_of_rows = 0;
    if ("urllist".equals(thisbug.DataLoopSource))
    {
      // randomList = new ArrayList<>(); 
    
    if (thisbug.getLimit()>0 || thisbug.getRandom())
      {
          if (thisbug.URLListName.equals("MASTER"))
          {
              ArrayList<String> masterList = new ArrayList<>();
              for (List<String> thisList: STAppData.VarLists.values())
              {
                 for(String thisString: thisList)
                 {
                     masterList.add(thisString);
                 }
              }
              STAppData.VarLists.put("MASTER", masterList);
          }
      thisbug.setURListRunTimeData(STAppData.RandomizeAndLimitURLList(thisbug.URLListName,thisbug.getLimit(), thisbug.getRandom()), thisbug.URLListName);
      }
      else
      {
           if (thisbug.URLListName.equals("MASTER"))
          {
              ArrayList<String> masterList = new ArrayList<>();
              for (List<String> thisList: STAppData.VarLists.values())
              {
                 for(String thisString: thisList)
                 {
                     masterList.add(thisString);
                 }
              }
              STAppData.VarLists.put("MASTER", masterList);
          }
      thisbug.setURListRunTimeData(STAppData.VarLists.get(thisbug.URLListName), thisbug.URLListName);
      }
      if (thisbug.URLListRunTimeEntries!=null)
      {
      number_of_rows = thisbug.URLListRunTimeEntries.size();
      }
     
      if (RUNWITHGUI)
      {
          if (number_of_rows>0)
          {
            ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
      thisbugview.setJTableSourceToURLList(thisbug.URLListRunTimeEntries, thisbug.URLListName);
          }
      }
    
    }
    else
    {
   if ("file".equals(thisbug.DataLoopSource))
    { 
       
        if (thisbug.getLimit()>0 || thisbug.getRandom())
        {
            if (STAppData.getDataSetByFileName(thisbug.DataFile).size()>0)
            {
        List<String[]> randomList = STAppData.RandomizeAndLimitFileList(STAppData.getDataSetByFileName(thisbug.DataFile), thisbug.getLimit(), thisbug.getRandom());
         thisbug.setRunTimeFileSet(randomList); 
   
        }
        }
        else
        {
            thisbug.setRunTimeFileSet(STAppData.getDataSetByFileName(thisbug.DataFile));
         
        }
        
         number_of_rows = thisbug.RunTimeFileSet.size();
        
    }     
    }
 
// if (number_of_rows==0)
// {
//  number_of_rows = FillTables(thisbug, thisbugview);
// }

  for( BMAction ThisAction : thisbug.ActionsList ) { 
 ThisAction.InitializeLoopTestVars(number_of_rows);
  } 

 for (int x = 0; x<number_of_rows; x++)
    {
 
   int changex = -1;
  action_INT = 0;
    for( BMAction ThisAction : thisbug.ActionsList ) {
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
    ThisAction.setEcTimeout(EcTimeout);
 
               DataLoopVarParser var1Parser = new DataLoopVarParser(ThisAction.Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(ThisAction.Variable2);
    if (var1Parser.hasDataLoopVar==false && var2Parser.hasDataLoopVar==false)
    {
        if ("Pause with Continue Button".equals(ThisAction.Type))
        {
           
           String pause_message = "Paused at record " + (x+1) + " of " + number_of_rows;
     
           changex =  ThisAction.RunAction(driver, pause_message, STAppData, x, number_of_rows);
        
        ThisAction.loop_pass_values.set(x, ThisAction.Pass);
        ThisAction.loop_time_of_test.set(x, ThisAction.TimeOfTest);
           if (STAppData.getIncludeScreenshots())
    { 

       ThisAction.loop_ScreenshotsBase64.set(x, "<img id = \"Screenshot" + bug_ID + "-" + action_ID + "\" class = \"report_screenshots\" style = \"display: none;\" src=\"\"></img>");
    } 
           else
           {
             ThisAction.loop_ScreenshotsBase64.set(x,"");
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
                if (chunks.size()>0)
                {
             STAppFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
                }
            }
        }).execute();
        }
        else
        {   LoudCall<Void, String> actMethod = new LoudCall<Void, String>() {
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
                if (chunks.size()>0)
                {
            popOutFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
                }
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
                if (chunks.size()>0)
                {
            STAppFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
                }
            }
        }).execute();
         }
         else {       String action_title2 = ThisAction.Type + ": " + ThisAction.Variable1 + " " + ThisAction.Variable2;
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
                if (chunks.size()>0)
                {
            popOutFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
                }
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
                if (chunks.size()>0)
                {
             STAppFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
                }
            }
        }).execute();   
           }
           else {    String action_title = ThisAction.Type + ": " + ThisAction.Variable1 + " " + ThisAction.Variable2;
            
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
                if (chunks.size()>0)
                {
            popOutFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
                }
            }
        }).execute();   
           }
      
         ThisAction.RunAction(driver);    
       }   
         
          if (!"".equals(ThisAction.tostore_varvalue))
       {
        
           STAppData.VarHashMap.put(ThisAction.tostore_varname, ThisAction.tostore_varvalue);
       }
       
        ThisAction.loop_pass_values.set(x,ThisAction.Pass);
        ThisAction.loop_time_of_test.set(x, ThisAction.TimeOfTest);
            }
             catch (Exception ex)
     {
   
          ThisAction.loop_pass_values.set(x,false);
          ThisAction.loop_time_of_test.set(x, LocalDateTime.now());
        
             if (RUNWITHGUI)
             {
               publish(thisbugindex);
                 ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
                thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.loop_pass_values.get(x));
             }
          break;
       
     }
                  if (STAppData.getIncludeScreenshots())
    { 
              try
        {
    File full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
     full_scrn.deleteOnExit();
   ThisAction.loop_ScreenshotsBase64.set(x, "<img src=\"file:///" + full_scrn.getAbsolutePath() + "\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" style = \"display: none;\" class = \"report_screenshots\"></img>");
  
        }
                 catch (Exception ex)
       {
          ThisAction.loop_ScreenshotsBase64.set(x,"Screenshot Failed");
     //      System.out.println("Exception creating screenshot: " + ex.toString());     
    }
    }
                  else
                  {
                   ThisAction.loop_ScreenshotsBase64.set(x, "");    
                  }
            
        }
    }
    else
    {
  
       
            String concat_variable="";
            String concat_variable2="";
            if ("urllist".equals(thisbug.DataLoopSource))
            {
 concat_variable = var1Parser.GetFullValueFromURLList(x, thisbug.URLListRunTimeEntries);
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
 concat_variable2 = var2Parser.GetFullValueFromURLList(x, thisbug.URLListRunTimeEntries);
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
             thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.loop_pass_values.get(x));
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
                if (chunks.size()>0)
                {
             STAppFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
                }
            }
        }).execute();   
                 }
                 else
                 {           String action_title = ThisAction.Type + ": " + ThisAction.Variable1 + " " + ThisAction.Variable2;
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
                if (chunks.size()>0)
                {
             popOutFrame.setJTextFieldProgress(chunks.get(chunks.size() - 1));
                }
            }
        }).execute();   
                 }
       
      ThisAction.RunAction(driver);

      ThisAction.Variable1 = original_value1;
   ThisAction.Variable2 = original_value2;
   ThisAction.loop_pass_values.set(x ,ThisAction.Pass);
        ThisAction.loop_time_of_test.set(x ,ThisAction.TimeOfTest);
             if (STAppData.getIncludeScreenshots())
    { 
        try
        {
     File full_scrn = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
     full_scrn.deleteOnExit();
   ThisAction.loop_ScreenshotsBase64.set(x ,"<img src=\"file:///" + full_scrn.getAbsolutePath() + "\" id = \"Screenshot" + bug_ID + "-" + action_ID + "\" style = \"display: none;\" class = \"report_screenshots\"></img>");
       }
                  catch (Exception ex)
       {
          ThisAction.loop_ScreenshotsBase64.set(x,"Screenshot Failed");
     //      System.out.println("Exception creating screenshot: " + ex.toString());     
    }
        
    } 
             else
             {
             ThisAction.loop_ScreenshotsBase64.set(x,"");     
             }
             }
      catch (Exception ex)
     {
   
       ThisAction.Variable1 = original_value1;
       ThisAction.Variable2 = original_value2;
       ThisAction.loop_pass_values.set(x, false);
        ThisAction.loop_time_of_test.set(x, LocalDateTime.now());
        if (RUNWITHGUI)
        {
              ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
         thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.loop_pass_values.get(x));
  
               publish(thisbugindex);
        }
          break;
       
     }
             }
   
      }
      else
      {
          ThisAction.Pass = true;
          ThisAction.loop_pass_values.set(x,ThisAction.Pass);
        ThisAction.loop_time_of_test.set(x,ThisAction.TimeOfTest);
            
      }
       if (RUNWITHGUI)
        {
              ProcedureView thisbugview = STAppFrame.BugViewArray.get(thisbugindex);
      thisbugview.ActionsViewList.get(ThisAction.index).setPassState(ThisAction.loop_pass_values.get(x));
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
     int actions_passed = 0;
    for( BMAction ThisAction : thisbug.ActionsList )
    {   
        ThisAction.Pass = false;
      
        int loop_actions_passed = 0;
        if (ThisAction.loop_pass_values.size()>0)
        {
        for (Boolean passvalue: ThisAction.loop_pass_values)
        {
            if (passvalue)
            {
                loop_actions_passed++;
            }
        }
        }
        if (loop_actions_passed == ThisAction.loop_pass_values.size())
        {
            ThisAction.Pass = true;
        }
        if (ThisAction.Pass)
        {
            actions_passed++;
        }
    }
  int sizeof = thisbug.ActionsList.size();
    if (actions_passed==sizeof)
    {
        thisbug.Pass = true;
    }
    else
    {
        thisbug.Pass = false;
    }
    
     
   }

 
  
 publish(thisbugindex);
    thisbugindex++;
      }
 
         if (STAppData.getPromptToClose() && driver!=null)
     {
          PromptToClose thisContinuePrompt = new PromptToClose(STAppData.short_filename + " - Prompt to close webdriver", "Close webdriver/browser?");
  
   thisContinuePrompt.addjButtonRunAgainActionListener(new ActionListener()
  {
   @Override
     public void actionPerformed(ActionEvent event)
     {
        RunAgain = true;
   thisContinuePrompt.dispose();
       boolean closecaught = false;
   
 try
 {
   if (driver!=null) {  driver.close(); }  
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
     if (STAppData.getUniqueList())
     {
         String file_option = STAppData.getUniqueFileOption();
 STAppData.AddURLListToUniqueFile(file_option);
 STAppData.ClearVisittedURLList();
     } 
 }
     }   
  });
    thisContinuePrompt.addjButtonCloseActionListener(new ActionListener()
  {
   @Override
     public void actionPerformed(ActionEvent event)
     {
        RunAgain = false;
        thisContinuePrompt.dispose();
     }   
  });
  
    


    
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
  
     if (RunAgain)
    {
      RunAgain=false;  
      if (RUNWITHGUI)
    {
     STAppFrame.clearPassFailColors();
     STAppFrame.disableAdds();
     STAppFrame.disableRemoves();
     STAppFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
     STAppFrame.setRunButtonEnabled(false);
    
    }
        if (STAppData.hasSentStoredVars)
     {
       STAppData.PromptForUserVarValues();
     }
     STAppData.testRunning = true;
     
     if (RUNWITHGUI)
     {
     prefs = new HashMap<String, Object>();
 BrowsermatorAppFolder =   System.getProperty("user.home")+File.separator+"BrowsermatorAppFolder"+File.separator;
   WEBDRIVERSDIR = BrowsermatorAppFolder + "Webdrivers" + File.separator;
        
     STAppFrame.RefreshViewData();
    
  STAppData.RefreshData();
  STAppFrame.UpdateDisplay();
  RUNWITHGUI = true;
  this.firefox_path = appConfig.getKeyValue("Firefox");
  this.chrome_path = appConfig.getKeyValue("Chrome 49");
  this.chrome_main_path = appConfig.getKeyValue("Chrome");
  this.downloadDir = appConfig.getKeyValue("downloaddir");
  
    this.STAppData.cancelled = false;
  this.targetbrowser = STAppData.TargetBrowser;
  this.OSType = STAppData.OSType;
  STAppFrame.jButtonCancel.setText("Cancel");
  STAppFrame.showTaskGUI();

 setProgressListeners();
     }
     else
     {
             prefs = new HashMap<String, Object>();
      BrowsermatorAppFolder =   System.getProperty("user.home")+File.separator+"BrowsermatorAppFolder"+File.separator;
   WEBDRIVERSDIR = BrowsermatorAppFolder + "Webdrivers" + File.separator;
  //   STAppData.RefreshData();
 //we're in no GUI Mode

       STAppData.RefreshData();
     RUNWITHGUI = false;
    
  this.firefox_path = appConfig.getKeyValue("Firefox");
  this.chrome_path = appConfig.getKeyValue("Chrome 49");
  this.chrome_main_path = appConfig.getKeyValue("Chrome");
  this.downloadDir = appConfig.getKeyValue("downloaddir");
  
    this.STAppData.cancelled = false;
  this.targetbrowser = STAppData.TargetBrowser;
  this.OSType = STAppData.OSType;
   popOutFrame.mainFrame.dispose();
   popOutFrame = new ProgressFrame(STAppData.short_filename);
 setProgressListeners(popOutFrame);
     }
     try
     {
       Thread.sleep(5000);  
     }
     catch (Exception ex)
     {
       System.out.println ("Exception while sleeping before runagain:" + ex.toString());  
     }
      
        RunAllActions(STAppFrame, STAppData, targetbrowser, OSType);
       
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
        ArrayList<BMAction> ActionsToLoop = thisbug.ActionsList;
  
 int NumberOfActionsPassed = 0;
  if (!"Dataloop".equals(thisbug.Type))
  {
       int ActionIndex = 0;
   for( BMAction TheseActions : ActionsToLoop ) {


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
       if (STAppData.BugArray.get(BugIndex).URLListRunTimeEntries!=null)
       {
      number_of_rows = STAppData.BugArray.get(BugIndex).URLListRunTimeEntries.size();
       }
   
      }
      if ("file".equals(thisbug.DataLoopSource))
      {
          number_of_rows = STAppData.BugArray.get(BugIndex).RunTimeFileSet.size();
      }
     
if (number_of_rows==0)
{
   int ActionIndex = 0;
    for( BMAction TheseActions : ActionsToLoop ) {
       
          TheseActions.Pass = false;
        
         ActionIndex++;
    }  
}
    for (int x = 0; x<number_of_rows; x++)
    {
        
    for( BMAction TheseActions : ActionsToLoop ) {

         
    LocalDateTime stringtime =  LocalDateTime.now();
   TheseActions.TimeOfTest = stringtime;
       boolean TestState = TheseActions.loop_pass_values.get(x);
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
 concat_variable = var1Parser.GetFullValueFromURLList(x, thisbug.URLListRunTimeEntries);
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
          public void setPermissions(File thisDriver)
         {
             
        
           if (!thisDriver.canExecute())
           {
       Prompter cantexecuteprompt = new Prompter ("Permissions Error", "The current user does not have permission to run the webdriver.  The Browsermator will attempt to set permissions now.  If this fails, you'll need to manually set permission to execute the following file: " + thisDriver.getAbsolutePath(), false,0,0);     
  
            thisDriver.setExecutable (true, true);
           }
           
         }

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
            ChromeOptions optionsfallback49 = new ChromeOptions();
          prefs.put("profile.default_content_setting_values.notifications", 2);
                 optionsfallback49.setExperimentalOption("prefs", prefs);
           
optionsfallback49.setBinary(chrome_path);
 System.setProperty("webdriver.chrome.driver", WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
  driver = new ChromeDriver(optionsfallback49);    
  
      }
  }

}

