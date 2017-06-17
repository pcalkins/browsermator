
package browsermator.com;

import java.awt.Cursor;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import javax.swing.SwingWorker;
import org.openqa.selenium.WebDriver;


public class ViewGuts extends SwingWorker<String, Integer>
{
SeleniumTestToolData SiteTestData;
SeleniumTestTool SiteTestView;
String targetbrowser;
String OSType;
WebDriver driver;
String GutsHeader;
String GutsFooter;
String Guts;

 ViewGuts (SeleniumTestTool in_SiteTestView, SeleniumTestToolData in_SiteTest)
 {

  this.GutsHeader = "";
  this.GutsFooter = "";
  this.Guts = "";
   this.SiteTestView = in_SiteTestView;
   this.SiteTestData = in_SiteTest;
  this.targetbrowser = in_SiteTest.TargetBrowser;
  this.OSType = in_SiteTest.OSType;
 }
 
@Override 
public String doInBackground()
 {
    SiteTestView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    SiteTestView.setGutsViewButtonName("Viewing...");
    DisplayAllActions(SiteTestView, targetbrowser, OSType);
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
this.Guts+="\ndriver.close();\n}" +
" public void ClickCatchAction(WebDriver driver, String xpather)\n" +
"  {\n" +
"     \n" +
"       try { \n" +
" WebElement element = driver.findElement(By.xpath(xpather));\n" +
" element.click();\n" +
" this.Pass = true;\n" +
" \n" +
" }\n" +
" catch (NoSuchElementException e)\n" +
" {\n" +
"  this.Pass = false;\n" +
"  \n" +
" }\n" +
"  }\n" +
"  public void RightClickCatchAction (WebDriver driver, String xpather)\n" +
"  {\n" +
"        try { \n" +
"            Actions actions = new Actions(driver);\n" +
" WebElement element = driver.findElement(By.xpath(xpather));\n" +
" actions.contextClick(element).perform();\n" +
" this.Pass = true;\n" +
" \n" +
" }\n" +
" catch (NoSuchElementException e)\n" +
" {\n" +
"  this.Pass = false;\n" +
"  \n" +
" }    \n" +
"  }\n" +
"public class Prompter extends JFrame implements ActionListener \n" +
"{\n" +
"    JButton ContinueButton;\n" +
"\n" +
"    \n" +
"     Prompter (String messagetodisplay)\n" +
"            {\n" +
"              \n" +
"                \n" +
"                ContinueButton = new JButton(\"Continue\");\n" +
"                \n" +
"             \n" +
"                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);\n" +
"                FlowLayout flo = new FlowLayout();\n" +
"                setLayout(flo);\n" +
"                JLabel messageText = new JLabel(messagetodisplay);\n" +
"                JPanel flowpanel = new JPanel();\n" +
"                \n" +
"                flowpanel.add(messageText);\n" +
"                JPanel boxpanel = new JPanel();\n" +
"                boxpanel.setLayout(new BoxLayout(boxpanel, BoxLayout.Y_AXIS));\n" +
"                boxpanel.add(flowpanel);\n" +
"                boxpanel.add(ContinueButton);\n" +
"                setSize(400, 200);\n" +
"                this.setTitle(messagetodisplay);\n" +
"                add(boxpanel);\n" +
"             \n" +
"                \n" +
"                setVisible(true);\n" +
"                ContinueButton.addActionListener(this);\n" +
"              \n" +
"               pack();\n" +
"            }\n" +
" @Override\n" +
"     public void actionPerformed(ActionEvent event)\n" +
"     {\n" +
"         Object source = event.getSource();\n" +
"         if (source == ContinueButton)\n" +
"         {\n" +
"          this.setVisible(false);\n" +
"          this.dispose();\n" +
"     \n" +
"     }\n" +
"        \n" +
"}\n" +
"   }\n  } public static void main(String[] args) { \n" +
"  try\n" +
"  {\n" +
"   \n" +
"      SeleniumTest app = new SeleniumTest(args); \n" +
" \n" +
"  }\n" +
"  catch (PropertyVetoException e)\n" +
"          {\n" +
"           System.out.println(\"Exception: \" + e);\n" +
"          }\n" +
"  \n" +
"  }\n}";

        SiteTestView.setCursor(Cursor.getDefaultCursor());   
     SiteTestView.setGutsViewButtonName(donetext);
   

    JPanel GutsPanel = new JPanel();
   
       JTextArea GutsTextField = new JTextArea();
   //    GutsTextField.setSize(900,800);
      GutsTextField.setVisible(true);
  GutsTextField.setText(Guts);
     
      
     JScrollPane GutsScrollPane = new JScrollPane(GutsTextField, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED); 
     GutsPanel.add(GutsTextField);
  //      GutsPanel.setSize(1000,800);
       GutsPanel.setVisible(true);
  // GutsScrollPane.setPreferredSize(new Dimension(1000, 800));
    GutsScrollPane.setViewportView(GutsPanel);   

       JFrame GutsFrame = new JFrame("Guts View - approximation of underlying code, edits will not save or change behavior");
  
      GutsFrame.add(GutsScrollPane);
       
       GutsFrame.setSize(800,800);
       GutsFrame.setVisible(true);
    //   GutsFrame.pack();
 }
 
  
@Override
 protected void process ( List <Integer> bugindex)
 {
  //  int updatebugindex = bugindex.size()-1;
    
  //  SiteTest.BugViewArray.get(updatebugindex).JButtonRunTest.setText("Done");
    
 }
  public void DisplayAllActions(SeleniumTestTool SiteTest, String TargetBrowser, String OSType)
 {

 this.Guts = 
         "\nimport java.beans.PropertyVetoException;\nimport java.awt.*;\n" +
"import javax.swing.*;\n" +
"import java.awt.event.*;\n" +
         "import javax.swing.JOptionPane;\n" +
"import org.openqa.selenium.OutputType;\n" +
"import org.openqa.selenium.TakesScreenshot;\n" +
"import org.openqa.selenium.WebDriver;\n" +
"import org.openqa.selenium.chrome.ChromeDriver;\n" +
"import org.openqa.selenium.firefox.FirefoxDriver;\n" +
"import org.openqa.selenium.htmlunit.HtmlUnitDriver;\n" +
"import org.openqa.selenium.ie.InternetExplorerDriver;\n" +
         "\n public final class SeleniumTest extends JFrame { \n boolean Pass;\n public SeleniumTest(String[] args) throws PropertyVetoException {\n this.Pass = false;\n\n";
         
  switch (TargetBrowser)
   {
     case "Firefox":

      
       if ("Windows32".equals(OSType))
     {
     this.Guts+=  "System.setProperty(\"webdriver.gecko.driver\", \"lib\\\\geckodriver-0.11.1-win32\\\\geckodriver.exe\");";
     }
      if ("Windows64".equals(OSType))
     {
     this.Guts+=  "System.setProperty(\"webdriver.gecko.driver\", \"lib\\\\geckodriver-0.11.1-win64\\\\geckodriver.exe\");";
     }
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
     if ("Windows32".equals(OSType))
     {
     this.Guts+=  "System.setProperty(\"webdriver.chrome.driver\", \"chromedriver_win32\\chromedriver.exe\");";
     }
      if ("Windows64".equals(OSType))
     {
     this.Guts+=  "System.setProperty(\"webdriver.chrome.driver\", \"chromedriver_win32\\chromedriver.exe\");";
     }
     if ("Mac".equals(OSType))
     {
         this.Guts+=  "System.setProperty(\"webdriver.chrome.driver\", \"chromedriver_mac64\\\\chromedriver\");";
     
     }
     if ("Linux-32".equals(OSType))
     {
         this.Guts+="System.setProperty(\"webdriver.chrome.driver\", \"chromedriver_linux32\\\\chromedriver\");";
     
     }
     if ("Linux-64".equals(OSType))
     {
         this.Guts+="System.setProperty(\"webdriver.chrome.driver\", \"chromedriver_linux64\\\\chromedriver\");";
     
     }
     this.Guts+="driver = new ChromeDriver();";
    
     break;
         
         default: 
           this.Guts+="driver = new FirefoxDriver();"; 
                     break;
   }
 int WaitTime = SiteTest.GetWaitTime();
int totalpause = WaitTime * 1000;

 
  
  int thisbugindex = 0;
  int bug_INT = thisbugindex+1;
  String bug_ID = Integer.toString(bug_INT);
  
     for (Procedure thisbug : SiteTestData.BugArray)
      {
          SiteTest.BugViewArray.get(thisbugindex).JButtonRunTest.setText("Running...");
        ArrayList<Action> Actions = thisbug.ActionsList;


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

  
       ThisAction.SetGuts();
       this.Guts+= "try\n" +
"  {\n" +
"   Thread.sleep(" + totalpause+ ");  \n" +
"  }\n" +
"  catch (Exception ex)\n" +
"  {\n" +
"      System.out.println (\"Exception when sleeping: \" + ex.toString());\n" +
"  }";
       this.Guts+=ThisAction.GetGuts();
  
   }   
   }  

}
else
{
     int number_of_rows = 0;
 if ("urllist".equals(thisbug.DataLoopSource))
    {
        
      if (thisbugview.getLimit()>0 || thisbugview.getRandom())
      {
      SiteTestData.RandomizeAndLimitURLList(thisbug.URLListName,thisbugview.getLimit(), thisbugview.getRandom());
      }
      thisbug.setURLListData(SiteTestData.VarLists.get(thisbug.URLListName), thisbug.URLListName);
      thisbugview.setJTableSourceToURLList(thisbug.URLListData, thisbug.URLListName);
      number_of_rows = SiteTestData.VarLists.get(thisbug.URLListName).length;
    }
    else
    {
   if ("file".equals(thisbug.DataLoopSource))
    {
        if (thisbugview.getLimit()>0 || thisbugview.getRandom())
        {
         thisbug.setRunTimeFileSet(SiteTestData.RandomizeAndLimitFileList(thisbug.DataSet, thisbugview.getLimit(), thisbugview.getRandom())); 
        }
         number_of_rows = thisbug.RunTimeFileSet.size();
    }     
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
    
          
          ThisAction.SetGuts();
          this.Guts+= "try\n" +
"  {\n" +
"   Thread.sleep(" + totalpause+ ");  \n" +
"  }\n" +
"  catch (Exception ex)\n" +
"  {\n" +
"      System.out.println (\"Exception when sleeping: \" + ex.toString());\n" +
"  }";
    this.Guts+=ThisAction.GetGuts();
      
      
      
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
 

     
      ThisAction.SetGuts();
      this.Guts+= "try\n" +
"  {\n" +
"   Thread.sleep(" + totalpause+ ");  \n" +
"  }\n" +
"  catch (Exception ex)\n" +
"  {\n" +
"      System.out.println (\"Exception when sleeping: \" + ex.toString());\n" +
"  }";
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
thisbugindex++;
      }
 }
}
