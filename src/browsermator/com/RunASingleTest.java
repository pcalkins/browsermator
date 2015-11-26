/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
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
  public RunASingleTest (Procedure in_bugtorun, ProcedureView in_thisbugview, String targetbrowser, String OSType)
          {
              this.bugtorun = in_bugtorun;
              this.thisbugview = in_thisbugview;
              this.targetbrowser = targetbrowser;
              this.OSType = OSType;
          }
    public String doInBackground()
 {
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
   catch (InterruptedException ex)
    {
         thisbugview.JButtonRunTest.setText("Run"); 
        System.out.println(ex);
    }
    catch (ExecutionException ex)
    {
         thisbugview.JButtonRunTest.setText("Run"); 
        System.out.println(ex);
    }
     
 }
 
 public void RunSingleTest(Procedure bugtorun, ProcedureView thisbugview, String TargetBrowser, String OSType)
 {
   
 
int actionsrun = 0;
  WebDriver driver = null; 
 //  WebDriver driver = new FirefoxDriver();
   switch (TargetBrowser)
   {
     case "Firefox":
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
     if (OSType == "Windows")
     {
     System.setProperty("webdriver.chrome.driver", "chromedriver_win32\\chromedriver.exe");
     }
     if (OSType == "Mac")
     {
     System.setProperty("webdriver.chrome.driver", "chromedriver_mac32\\chromedriver-mac32");
     }
     if (OSType == "Linux-32")
     {
     System.setProperty("webdriver.chrome.driver", "chromedriver_linux32\\chromedriver-linux32");
     }
     if (OSType == "Linux-64")
     {
     System.setProperty("webdriver.chrome.driver", "chromedriver_linux64\\chromedriver-linux64");
     }
     
     driver = new ChromeDriver();
     break;
   }
 
  int WaitTime = 5;
driver.manage().timeouts().implicitlyWait(WaitTime, TimeUnit.SECONDS);


    for( Action TheseActions : bugtorun.ActionsList ) {

   try
   {
       TheseActions.RunAction(driver);
       
   }
   catch (Exception ex)
   {
     System.out.println(ex);
        }
   actionsrun++;


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
