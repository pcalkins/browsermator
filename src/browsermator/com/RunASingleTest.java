/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Cursor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import javax.swing.SwingWorker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;




public class RunASingleTest extends SwingWorker <String, Integer> {
    Procedure bugtorun;
    ProcedureView thisbugview;
    String targetbrowser;
    String OSType;
    FireFoxProperties FFprops;
    String firefox_path;
    String chrome_path;
    SeleniumTestTool SiteTest;
    WebDriver driver;
 
  public RunASingleTest (SeleniumTestTool in_SiteTest, Procedure in_bugtorun, ProcedureView in_thisbugview, String targetbrowser, String OSType)
          {
              this.SiteTest = in_SiteTest;
              this.bugtorun = in_bugtorun;
              this.thisbugview = in_thisbugview;
              this.targetbrowser = targetbrowser;
              this.OSType = OSType;
              this.driver = new HtmlUnitDriver();
             
            
          }
    public String doInBackground()
 {
     SiteTest.testRunning = true;
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
  SiteTest.testRunning = false;
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
   SiteTest.setCursor(Cursor.getDefaultCursor()); 
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

          ArrayList<ActionView> ActionView = thisbugview.ActionsViewList;

 int ActionIndex = 0;

   for( ActionView TheseActionViews : ActionView ) {


    LocalDateTime stringtime = bugtorun.ActionsList.get(ActionIndex).TimeOfTest;
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
   
  SiteTest.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

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
        Prompter fallbackprompt = new Prompter ("We could not launch the Marionette driver, will fallback to HTMLUnitDriver", false);
       
        SiteTest.setTargetBrowser("Silent Mode (HTMLUnit)");
    }
      
     break;
            
    case "Firefox":
        //legacy support
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
        Prompter fallbackprompt = new Prompter ("We could not launch the Marionette driver, will fallback to HTMLUnitDriver", false);
       
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
         Prompter fallbackprompt = new Prompter ("We could not launch the Internet Explorer driver, will fallback to HTMLUnitDriver", false);
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
              Prompter fallbackprompt = new Prompter ("We could not launch the Internet Explorer 64 driver, will fallback to HTMLUnitDriver", false);
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
     System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_mac64\\chromedriver");
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
        Prompter fallbackprompt = new Prompter ("We could not launch the Chrome driver, will fallback to HTMLUnitDriver", false);
       SiteTest.setTargetBrowser("Silent Mode (HTMLUnit)");
   }
     break;
   case "Chrome (WinXP)":
     if (chrome_path!=null) {
        System.setProperty("webdriver.chrome.bin", chrome_path);
    }
     System.setProperty("webdriver.chrome.driver", "lib\\chromedriver_win32\\chromedriver-winxp.exe");
    
     try
     {
        driver = new ChromeDriver();     
     }
   catch (Exception ex)
   {
       System.out.println ("Problem launching Chromedriver for XP: " + ex.toString());
        Prompter fallbackprompt = new Prompter ("We could not launch the Chrome WinXP driver, will fallback to HTMLUnitDriver", false);
       SiteTest.setTargetBrowser("Silent Mode (HTMLUnit)");
   }
     break;
         
         default: 
            driver = new ChromeDriver();
                     break;
   }
 
  int WaitTime = SiteTest.GetWaitTime();
// driver.manage().timeouts().implicitlyWait(WaitTime, TimeUnit.SECONDS);
     int totalpause = WaitTime * 1000;
 
 if (!"Dataloop".equals(thisbugview.Type))
  {
   for( Action ThisAction : bugtorun.ActionsList ) {
       
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
         ThisAction.Variable2 = SiteTest.GetStoredVariableValue(fieldname);
          ThisAction.RunAction(driver);
          ThisAction.Variable2 = "[stored_varname-start]"+fieldname+"[stored_varname-end]";
       }
       else
       {
             if ("Pause with Continue Button".equals(ThisAction.Type))
        {
         
          ThisAction.RunAction(driver, "Actions Paused...", this.SiteTest);
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
 int number_of_rows = thisbugview.myTable.DataTable.getRowCount();
 
     for (int x = 0; x<number_of_rows; x++)
    {
    for( Action ThisAction : bugtorun.ActionsList ) {
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
          ThisAction.RunAction(driver, pause_message, this.SiteTest);
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
          
          break;
  }
         }
       try
       {
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
    }
   }

    
 } 

}
