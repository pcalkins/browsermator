/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Cursor;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingWorker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;



public class RunASingleTest extends SwingWorker <String, Integer> {
    Procedure bugtorun;
    ProcedureView thisbugview;
    String targetbrowser;
    String OSType;
    FireFoxProperties FFprops;
    String firefox_path;
    String chrome_path;
    String chrome_main_path;
    
    SeleniumTestTool STAppFrame;
    SeleniumTestToolData STAppData;
    WebDriver driver;
 //  String BMPATH;
    String BrowsermatorAppFolder;
    String WEBDRIVERSDIR;
     Map<String, Object> prefs;
  public RunASingleTest (SeleniumTestTool in_STAppFrame, SeleniumTestToolData in_STAppData, Procedure in_bugtorun, ProcedureView in_thisbugview, String targetbrowser, String OSType)
          {
              prefs = new HashMap<String, Object>();
              this.STAppFrame = in_STAppFrame;
              this.STAppData = in_STAppData;
              this.bugtorun = in_bugtorun;
              this.thisbugview = in_thisbugview;
              this.targetbrowser = targetbrowser;
              this.OSType = OSType;
            
              STAppData.cancelled = false;
    //             BMPATH = "";
   //  try
   //  {
   
  //     BMPATH = new File(RunAllTests.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
   
  
  //   }
  //   catch (Exception ex)
  //   {
  //     Prompter fallbackprompt2 = new Prompter ("Driver Error", "Could not locate jar folder: " + BMPATH + " Error: " + ex.toString(), false,0,0);     
  //   }
     BrowsermatorAppFolder =   System.getProperty("user.home")+File.separator+"BrowsermatorAppFolder"+File.separator;
   WEBDRIVERSDIR = BrowsermatorAppFolder + "Webdrivers" + File.separator;        
          }
    public String doInBackground()
 {
     STAppData.testRunning = true;
      FFprops = new FireFoxProperties(this.targetbrowser);
  this.firefox_path = FFprops.LoadFirefoxPath();
  this.chrome_path = FFprops.LoadChromePath();
    this.chrome_main_path = FFprops.LoadChromeMainPath();
   thisbugview.JButtonRunTest.setText("Running...");
  
    RunSingleTest(bugtorun, thisbugview, targetbrowser, OSType);
    String donetext = "Run";
     return donetext;
 }
 protected void done()
 {
  STAppData.testRunning = false;
    try
    {
        String donetext = get();
      thisbugview.JButtonRunTest.setText(donetext); 
      
    }
   catch (Exception ex)
    {
      
        if (ex.toString().contains("Cannot find firefox"))
        {
       FFprops.BrowseforFFPath();
  
            }
            
            else
            {
            
            }
   
        thisbugview.JButtonRunTest.setText("Run"); 
      
    }
   STAppFrame.setCursor(Cursor.getDefaultCursor()); 
       if (STAppData.getPromptToClose())
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
            try {
                driver.quit();
            }
            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
   driver.quit();
     }

          ArrayList<ActionView> ActionView = thisbugview.ActionsViewList;

 int ActionIndex = 0;

   for( ActionView TheseActionViews : ActionView ) {


    LocalDateTime stringtime = LocalDateTime.now();
    
            bugtorun.ActionsList.get(ActionIndex).TimeOfTest = stringtime;
       boolean TestState = bugtorun.ActionsList.get(ActionIndex).Pass;
       if (TestState==true)
       {
           thisbugview.ActionsViewList.get(ActionIndex).JLabelPassFail.setText("Passed at " + stringtime);

       }
       else
       {
           thisbugview.ActionsViewList.get(ActionIndex).JLabelPassFail.setText("Fail at " + stringtime);
           
       }

       ActionIndex++;

}  
 }
 
 public void RunSingleTest(Procedure bugtorun, ProcedureView thisbugview, String TargetBrowser, String OSType)
 {
     prefs = new HashMap<String, Object>();
  STAppFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
     
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver, will fallback to HTMLUnitDriver", false,0,0);
            FallbackDriver("HTMLUnit");
         
          
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
     
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver, will fallback to HTMLUnitDriver", false,0,0);
         FallbackDriver("HTMLUnit");
      
    }
      
     break;
     
     case "Silent Mode (HTMLUnit)":
  
     driver = new HtmlUnitDriver();  
   
     break;
     
     case "Internet Explorer-32":
            thisDriver =  new File(WEBDRIVERSDIR+"iedriverserver_win32"+File.separator+"IEDriverServer.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.ie.driver", thisDriver.getAbsolutePath());  
    // System.setProperty("webdriver.ie.driver", BMPATH+File.separator+"lib"+File.separator+"iedriverserver_win32"+File.separator+"IEDriverServer.exe");
     try
     {
     driver = new InternetExplorerDriver();
     }
     catch (Exception ex)
     {
         System.out.println ("Exception launching Internet Explorer driver: " + ex.toString());
     
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the IEdriver, will fallback to HTMLUnitDriver", false,0,0);
          FallbackDriver("HTMLUnit");
          
     }
     break;
     case "Internet Explorer-64":
            thisDriver =  new File(WEBDRIVERSDIR+"iedriverserver_win64"+File.separator+"IEDriverServer.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.ie.driver", thisDriver.getAbsolutePath());  
      
   //  System.setProperty("webdriver.ie.driver",BMPATH+File.separator+ "lib"+File.separator+"iedriverserver_win64"+File.separator+"IEDriverServer.exe");
     try
     {
     driver = new InternetExplorerDriver();
     }
     catch (Exception ex)
             {
             System.out.println ("Exception launching Internet Explorer-64 driver: " + ex.toString());
    
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the IEdriver, will fallback to HTMLUnitDriver", false,0,0);
          FallbackDriver("HTMLUnit");
          
             }
     break;
     case "Chrome":
         //legacy support
          ChromeOptions options = new ChromeOptions();  
             prefs.put("profile.default_content_setting_values.notifications", 2);
                 options.setExperimentalOption("prefs", prefs);
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
     
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver, will fallback to HTMLUnitDriver: " + ex.toString(), false,0,0);
         FallbackDriver("HTMLUnit");
          
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
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chrome 49 driver, will fallback to HTMLUnitDriver: " + ex.toString(), false,0, 0);
      FallbackDriver("HTMLUnit");
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
         try {
         
            driver = new EdgeDriver();
   }
     catch (Exception ex)
   {
       System.out.println ("Problem launching EdgeDriver: " + ex.toString());
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Edge Driver. Location: "+edgeDriverPath+" Go to Settings > Update and Security > For Developer and then select Developer mode." + ex.toString(), false,0, 0);
    
   }
        
       break;
       
         
         default: 
           //legacy support
    
         if ("Windows".equals(OSType))
     {
                      thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
 
    //    System.setProperty("webdriver.chrome.driver",BMPATH+File.separator+ "lib"+File.separator+"chromedriver_win32"+File.separator+"chromedriver.exe");
     }
     if ("Windows32".equals(OSType))
     {
                   thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
    
   //  System.setProperty("webdriver.chrome.driver", WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
     }
       if ("Windows64".equals(OSType))
     {
                      thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
 
  //   System.setProperty("webdriver.chrome.driver",BMPATH+File.separator+ "lib"+File.separator+"chromedriver_win32"+File.separator+"chromedriver.exe");
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
 
   //  System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_linux32"+File.separator+"chromedriver");
     }
     if ("Linux-64".equals(OSType))
     {
                      thisDriver =  new File(WEBDRIVERSDIR+"chromedriver_linux64"+File.separator+"chromedriver");
          setPermissions(thisDriver);
        System.setProperty("webdriver.chrome.driver", thisDriver.getAbsolutePath());  
 
  //   System.setProperty("webdriver.chrome.driver", BMPATH+File.separator+"lib"+File.separator+"chromedriver_linux64"+File.separator+"chromedriver");
     }
     try
     {
        driver = new ChromeDriver();     
     }
   catch (Exception ex)
   {
       System.out.println ("Problem launching Chromedriver: " + ex.toString());
    
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver, will fallback to HTMLUnit Driver: " + ex.toString(), false,0,0);
          FallbackDriver("HTMLUnit");
     

    
   }
    }
   int WaitTime = 0;
   int EcTimeout = 10;
  WaitTime = STAppData.getWaitTime();
  EcTimeout = STAppData.getEcTimeout();
  //timeouts still buggy.. removed
 // int Timeout = SiteTest.getTimeout();
//  int Timeout = 5;
  
// driver.manage().timeouts().implicitlyWait(Timeout, TimeUnit.SECONDS);
// driver.manage().timeouts().pageLoadTimeout(Timeout, TimeUnit.SECONDS);
// driver.manage().timeouts().setScriptTimeout(Timeout, TimeUnit.SECONDS);

     int totalpause = WaitTime * 1000;
 
 if (!"Dataloop".equals(thisbugview.Type))
  {
   for( BMAction ThisAction : bugtorun.ActionsList ) {
        if (STAppData.cancelled)
          {
          
             publish(bugtorun.index);
             
             break;
          }  
           if (!ThisAction.Locked)
   {
       ThisAction.setEcTimeout(EcTimeout);
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
       if (ThisAction.Variable2.contains("[stored_varname-start]"))
       {
         varfieldname = ThisAction.Variable2;
            int indexof_end_tag = varfieldname.indexOf("[stored_varname-end]");
      // assuming name of "[stored_varname-start]" and "[stored_varname-end]"
         String fieldname = varfieldname.substring(22, indexof_end_tag);
         ThisAction.Variable2 = STAppData.GetStoredVariableValue(fieldname);
          ThisAction.RunAction(driver);
          ThisAction.Variable2 = "[stored_varname-start]"+fieldname+"[stored_varname-end]";
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
    if (ThisAction.tostore_varlist.size()>0)
       {

           STAppData.VarLists.put(ThisAction.Variable2, ThisAction.tostore_varlist);
           

       }
      
     
   
   }
   catch (Exception ex)
   {
  
        break;
     
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
 int number_of_rows = thisbugview.DataTable.getRowCount();
 
     for (int x = 0; x<number_of_rows; x++)
    {
         int changex = -1;
    for( BMAction ThisAction : bugtorun.ActionsList ) {
         if (STAppData.cancelled)
          {
          
             publish(bugtorun.index);
             break;
          }  
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
  
        }
         else
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
            publish(bugtorun.index);
          break;
  }
      
         }
                   
   
                       String varfieldname="";
       if (ThisAction.Variable2.contains("[stored_varname-start]"))
       {
         varfieldname = ThisAction.Variable2;
            int indexof_end_tag = varfieldname.indexOf("[stored_varname-end]");
      // assuming name of "[stored_varname-start]" and "[stored_varname-end]"
         String fieldname = varfieldname.substring(22, indexof_end_tag);
         ThisAction.Variable2 = STAppData.GetStoredVariableValue(fieldname);
          ThisAction.RunAction(driver);
          ThisAction.Variable2 = "[stored_varname-start]"+fieldname+"[stored_varname-end]";
       }
       else
       {
         ThisAction.RunAction(driver);    
       }
       
      
       if (!"".equals(ThisAction.tostore_varvalue))
       {
        
           STAppData.VarHashMap.put(ThisAction.tostore_varname, ThisAction.tostore_varvalue);
        
       }
      
    
      
       }
        catch (Exception ex)
     {
 
        ThisAction.Variable1 = original_value1;
        ThisAction.Variable2 = original_value2;
      
          break;
       
     }
       
       }
    else
    {
  
       
            String concat_variable="";
            String concat_variable2="";
                 if ("urllist".equals(bugtorun.DataLoopSource))
            {
 concat_variable = var1Parser.GetFullValueFromURLList(x, bugtorun.URLListData);
            }
            if ("file".equals(bugtorun.DataLoopSource))
            {
   concat_variable = var1Parser.GetFullValueFromFile(x, bugtorun.RunTimeFileSet);             
            }

 if (var1Parser.hasDataLoopVar)
 {
     ThisAction.Variable1 = concat_variable;
        if ("".equals(ThisAction.Variable1))
           {
               ThisAction.Variable1 = " ";
           }
 }
  if ("urllist".equals(bugtorun.DataLoopSource))
            {
 concat_variable2 = var2Parser.GetFullValueFromURLList(x, bugtorun.URLListData);
            }
            if ("file".equals(bugtorun.DataLoopSource))
            {
   concat_variable2 = var2Parser.GetFullValueFromFile(x, bugtorun.RunTimeFileSet);             
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

     }
     catch (Exception ex)
     {
   
       ThisAction.Variable1 = original_value1;
       ThisAction.Variable2 = original_value2;
    
          break;
       
     }
     
  }
   
      }
        else
      {
          ThisAction.Pass = true;
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
  
   
        try
        {
        driver.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
           try {
                driver.quit();
            }
            catch (Exception ex)
            {
                System.out.println(ex.toString());
            }
        }
    driver.quit();
     } 
    
 } 
     public void setPermissions(File thisDriver)
         {
             
          
           if (!thisDriver.canExecute())
           {
            thisDriver.setExecutable (true, true);
           }
           
         }
 public void FallbackDriver(String fallbackdriver)
  {
      if ("HTMLUnit".equals(fallbackdriver))
      {
          STAppData.setTargetBrowser("Silent Mode (HTMLUnit)");
      
              STAppFrame.setTargetBrowserView("Silent Mode (HTMLUnit)");
        
          driver = new HtmlUnitDriver();
      }
      else
      {
       STAppData.setTargetBrowser("Chrome 49");
      
       STAppFrame.setTargetBrowserView("Chrome 49");     
     
            ChromeOptions optionsfallback49 = new ChromeOptions();
  
                 prefs.put("profile.default_content_setting_values.notifications", 2);
                 optionsfallback49.setExperimentalOption("prefs", prefs);
optionsfallback49.setBinary(chrome_path);

 System.setProperty("webdriver.chrome.driver", WEBDRIVERSDIR+"chromedriver_win32"+File.separator+"chromedriver.exe");
  driver = new ChromeDriver(optionsfallback49);    
  
      }
  }
}
