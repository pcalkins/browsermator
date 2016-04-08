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
    SeleniumTestTool SiteTest;
  public RunASingleTest (SeleniumTestTool in_SiteTest, Procedure in_bugtorun, ProcedureView in_thisbugview, String targetbrowser, String OSType)
          {
              this.SiteTest = in_SiteTest;
              this.bugtorun = in_bugtorun;
              this.thisbugview = in_thisbugview;
              this.targetbrowser = targetbrowser;
              this.OSType = OSType;
          }
    public String doInBackground()
 {
      FFprops = new FireFoxProperties();
  this.firefox_path = FFprops.LoadFirefoxPath();
   thisbugview.JButtonRunTest.setText("Running...");
    RunSingleTest(bugtorun, thisbugview, targetbrowser, OSType);
    String donetext = "Run";
     return donetext;
 }
 protected void done()
 {
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
     
 }
 
 public void RunSingleTest(Procedure bugtorun, ProcedureView thisbugview, String TargetBrowser, String OSType)
 {
   
  SiteTest.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  WebDriver driver = null; 
 //  WebDriver driver = new FirefoxDriver();
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
   }
 
  int WaitTime = SiteTest.GetWaitTime();
// driver.manage().timeouts().implicitlyWait(WaitTime, TimeUnit.SECONDS);
     int totalpause = WaitTime * 1000;
 
if (thisbugview.myTable==null)
{
   for( Action ThisAction : bugtorun.ActionsList ) {
       
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
       ThisAction.RunAction(driver);
       
   }
   catch (Exception ex)
   {
   SiteTest.setCursor(Cursor.getDefaultCursor()); 
     driver.close();
        break;
     
        }
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
         try
  {
   Thread.sleep(totalpause);  
  }
  catch (Exception ex)
  {
      System.out.println ("Exception when sleeping: " + ex.toString());
  }
       try
       {
        ThisAction.RunAction(driver);
       }
        catch (Exception ex)
     {
   
        driver.close();
        ThisAction.Variable1 = original_value1;
        ThisAction.Variable2 = original_value2;
        SiteTest.setCursor(Cursor.getDefaultCursor()); 
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

     }
     catch (Exception ex)
     {
   
       ThisAction.Variable1 = original_value1;
       ThisAction.Variable2 = original_value2;
          driver.close();
       SiteTest.setCursor(Cursor.getDefaultCursor()); 
          break;
       
     }
     
  }
   
      }
     }
    }
   }
     
    driver.close();  
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

}
