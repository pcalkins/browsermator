
package browsermator.com;




import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import org.openqa.selenium.WebDriver;


public class ViewGuts extends SwingWorker<String, Integer>
{
SeleniumTestTool SiteTest;
String targetbrowser;
String OSType;
WebDriver driver;
String GutsHeader;
String GutsFooter;
String Guts;

 ViewGuts (SeleniumTestTool in_SiteTest)
 {
  this.GutsHeader = "";
  this.GutsFooter = "";
  this.Guts = "";
   this.SiteTest = in_SiteTest;
  this.targetbrowser = in_SiteTest.TargetBrowser;
  this.OSType = in_SiteTest.OSType;
 }
 
@Override 
public String doInBackground()
 {
    SiteTest.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    SiteTest.setGutsViewButtonName("Running...");
    DisplayAllActions(SiteTest, targetbrowser, OSType);
    String donetext = "View Guts";
     return donetext;
     
 }
@Override
 protected void done()
 {
    String donetext = "View Guts"; 
try
{
       donetext = get();
}
catch (Exception ex)
{
    
}
        SiteTest.setCursor(Cursor.getDefaultCursor());   
     SiteTest.setGutsViewButtonName(donetext);
   


    JPanel GutsPanel = new JPanel();
       JTextField GutsTextField = new JTextField();
  GutsTextField.setText(Guts);
      GutsTextField.setSize(1000,800);
      GutsTextField.setVisible(true);
       GutsPanel.add(GutsTextField);
        GutsPanel.setSize(1000,800);
       GutsPanel.setVisible(true);
       JFrame GutsFrame = new JFrame("Guts View");
      
       
       GutsFrame.add(GutsPanel);
       
       GutsFrame.setSize(1000,800);
       GutsFrame.setVisible(true);
     
 }
 
  
@Override
 protected void process ( List <Integer> bugindex)
 {
  //  int updatebugindex = bugindex.size()-1;
    
  //  SiteTest.BugViewArray.get(updatebugindex).JButtonRunTest.setText("Done");
    
 }
  public void DisplayAllActions(SeleniumTestTool SiteTest, String TargetBrowser, String OSType)
 {

 this.GutsHeader = "import java.awt.Cursor;\n" +
"import java.time.LocalDateTime;\n" +
"import java.util.ArrayList;\n" +
"import java.util.List;\n" +
"import javax.swing.JOptionPane;\n" +
"import org.openqa.selenium.OutputType;\n" +
"import org.openqa.selenium.TakesScreenshot;\n" +
"import org.openqa.selenium.WebDriver;\n" +
"import org.openqa.selenium.chrome.ChromeDriver;\n" +
"import org.openqa.selenium.firefox.FirefoxDriver;\n" +
"import org.openqa.selenium.htmlunit.HtmlUnitDriver;\n" +
"import org.openqa.selenium.ie.InternetExplorerDriver;\n";
  switch (TargetBrowser)
   {
     case "Firefox":

       this.Guts+=  "driver = new FirefoxDriver();";
      
     break;
     
     case "Silent Mode (HTMLUnit)":
      this.Guts+=  "driver = new HtmlUnitDriver();"; 
     break;
     
     case "Internet Explorer-32":
     this.Guts+=  "System.setProperty(\"webdriver.ie.driver\", \"IEDriverServer_Win32_2.48.0\\IEDriverServer.exe\");";
     this.Guts+= "driver = new InternetExplorerDriver();";
     break;
     case "Internet Explorer-64":
     this.Guts+=  "System.setProperty(\"webdriver.ie.driver\", \"IEDriverServer_x64_2.48.0\\IEDriverServer.exe\");";
     this.Guts+=  "driver = new InternetExplorerDriver();";
     break;
     case "Chrome":
     if ("Windows".equals(OSType))
     {
     this.Guts+=  "System.setProperty(\"webdriver.chrome.driver\", \"chromedriver_win32\\chromedriver.exe\");";
     }
     if ("Mac".equals(OSType))
     {
         this.Guts+=  "System.setProperty(\"webdriver.chrome.driver\", \"chromedriver_mac32\\\\chromedriver-mac32\");";
     
     }
     if ("Linux-32".equals(OSType))
     {
         this.Guts+="System.setProperty(\"webdriver.chrome.driver\", \"chromedriver_linux32\\\\chromedriver-linux32\");";
     
     }
     if ("Linux-64".equals(OSType))
     {
         this.Guts+="System.setProperty(\"webdriver.chrome.driver\", \"chromedriver_linux64\\\\chromedriver-linux64\");";
     
     }
     this.Guts+="driver = new ChromeDriver();";
    
     break;
         
         default: 
           this.Guts+="driver = new FirefoxDriver();"; 
                     break;
   }
  
this.Guts+=" int WaitTime = SiteTest.GetWaitTime();\n" +
"     int totalpause = WaitTime * 1000;";
 
  
  int thisbugindex = 0;
  int bug_INT = thisbugindex+1;
  String bug_ID = Integer.toString(bug_INT);
  
     for (Procedure thisbug : SiteTest.BugArray)
      {
          SiteTest.BugViewArray.get(thisbugindex).JButtonRunTest.setText("Running...");
        ArrayList<Action> Actions = thisbug.ActionsList;


int action_INT = 0;
String action_ID = "";
ProcedureView thisbugview = SiteTest.BugViewArray.get(thisbugindex);

if (thisbugview.myTable==null)
{
    action_INT=0;
   for( Action ThisAction : thisbug.ActionsList ) {
           String original_value = ThisAction.Variable2;
 action_INT++;
 action_ID = Integer.toString(action_INT);
           if (!ThisAction.Locked)
   {

  
       ThisAction.SetGuts();
       this.Guts+=ThisAction.GetGuts();
  
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
    
          
          ThisAction.SetGuts();
    this.Guts+=ThisAction.GetGuts();
      
      
      
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
 

     
      ThisAction.SetGuts();
 this.Guts+=ThisAction.GetGuts();
      ThisAction.Variable1 = original_value1;
   ThisAction.Variable2 = original_value2;
   ThisAction.loop_pass_values[x] = ThisAction.Pass;
        ThisAction.loop_time_of_test[x] = ThisAction.TimeOfTest;
     
   
      }
     
     }
    
    
    }
   
  
     
 }
}
      }
 }
}
