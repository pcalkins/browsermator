/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Cursor;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    SeleniumTestTool STAppFrame;
    SeleniumTestToolData STAppData;
    WebDriver driver;
 
  public RunASingleTest (SeleniumTestTool in_STAppFrame, SeleniumTestToolData in_STAppData, Procedure in_bugtorun, ProcedureView in_thisbugview, String targetbrowser, String OSType)
          {
              this.STAppFrame = in_STAppFrame;
              this.STAppData = in_STAppData;
              this.bugtorun = in_bugtorun;
              this.thisbugview = in_thisbugview;
              this.targetbrowser = targetbrowser;
              this.OSType = OSType;
            
              STAppData.cancelled = false;
            
          }
    public String doInBackground()
 {
     STAppData.testRunning = true;
      FFprops = new FireFoxProperties(this.targetbrowser);
  this.firefox_path = FFprops.LoadFirefoxPath();
  this.chrome_path = FFprops.LoadChromePath();
  
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
   
  STAppFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

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
// DesiredCapabilities cap = DesiredCapabilities.firefox();
 //       cap.setJavascriptEnabled(true);
 //       cap.setCapability("marionette", true);
        driver = new FirefoxDriver();
    

    //  driver =  new MarionetteDriver();
    }
    catch (Exception ex)
    {
  
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver, will fallback to HTMLUnitDriver", false,0,0);
        FallbackDriver("HTMLUnit");
          
   
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
  // DesiredCapabilities cap = DesiredCapabilities.firefox();
   //     cap.setJavascriptEnabled(true);
   //     cap.setCapability("marionette", true);
        driver = new FirefoxDriver();
    

    //  driver =  new MarionetteDriver();
    }
    catch (Exception ex)
    {
    
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Marionette driver, will fallback to HTMLUnitDriver", false,0,0);
        FallbackDriver("HTMLUnit");
          
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
           Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Internet Explorer driver, will fallback to HTMLUnitDriver", false,0,0);
        FallbackDriver("HTMLUnit");
          
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
   
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Internet Explorer driver, will fallback to HTMLUnitDriver", false,0,0);
        FallbackDriver("HTMLUnit");
          
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
    
         Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the Chromedriver, will fallback to HTMLUnitDriver", false,0,0);
         FallbackDriver("HTMLUnit");
          
   }
     break;
   case "Chrome 49":
           ChromeOptions options = new ChromeOptions();
      if (chrome_path!=null) {
        
options.setBinary(chrome_path);


    }
     System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_win32\\chromedriver.exe");
   
    
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
      case "Edge":
     System.setProperty("webdriver.edge.driver", "lib\\edgedriver\\MicrosoftWebDriver.exe");
     try
     {
   driver = new EdgeDriver(); 
     }  
   catch (Exception ex)
   {
       System.out.println ("Problem launching EdgeDriver: " + ex.toString());
        Prompter fallbackprompt = new Prompter ("Driver Error", "Could not launch the EdgeDriver driver, will fallback to HTMLUnitDriver", false,0, 0);
      FallbackDriver("HTMLUnit");
   }
       break;
       
         default: 
            driver = new ChromeDriver();
                     break;
   }
 
  int WaitTime = STAppData.getWaitTime();
 
//timeouts still buggy... removed
// int Timeout = SiteTest.getTimeout();
//  int Timeout = 5;
  
// driver.manage().timeouts().implicitlyWait(Timeout, TimeUnit.SECONDS);
// driver.manage().timeouts().pageLoadTimeout(Timeout, TimeUnit.SECONDS);
// driver.manage().timeouts().setScriptTimeout(Timeout, TimeUnit.SECONDS);

     int totalpause = WaitTime * 1000;
 
 if (!"Dataloop".equals(thisbugview.Type))
  {
   for( Action ThisAction : bugtorun.ActionsList ) {
        if (STAppData.cancelled)
          {
          
             publish(bugtorun.index);
             
             break;
          }  
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
    for( Action ThisAction : bugtorun.ActionsList ) {
         if (STAppData.cancelled)
          {
          
             publish(bugtorun.index);
             break;
          }  
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
 public void FallbackDriver(String fallbackdriver)
  {
      if ("HTMLUnit".equals(fallbackdriver))
      {
          STAppData.setTargetBrowser("Silent Mode (HTMLUnit)");
          driver = new HtmlUnitDriver();
      }
      else
      {
       STAppData.setTargetBrowser("Chrome 49");
            ChromeOptions options = new ChromeOptions();
options.setBinary(chrome_path);
 System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_win32\\chromedriver.exe");
  driver = new ChromeDriver(options);     
      }
  }
}
