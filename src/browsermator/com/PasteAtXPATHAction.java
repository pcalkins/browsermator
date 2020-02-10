package browsermator.com;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PasteAtXPATHAction extends BMAction
{

  
    
  PasteAtXPATHAction (String TargetXPATH, String ToType, Boolean BoolVal1)
    {
        this.Type = "Paste at XPATH";
        
 
        this.Variable1 = TargetXPATH;
        this.Variable2 = ToType;
        this.BoolVal1 = BoolVal1;
        this.Loopable = true;
   
    }
  @Override
  public void SetGuts()
  {
      this.Guts = " try\n" +
" {\n" +
"       \n" +
"       \n" +
"this.Pass = true;     \n" +
"if (this.Variable2.length()>0)\n" +
" {\n" +
"       wait = new WebDriverWait(driver, ec_Timeout);  \n" +
"       WebElement element  = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(" + this.Variable1+")));  \n" +
"\n" +
"    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();\n" +
"    Transferable transferable = new StringSelection("+ this.Variable2+ ");\n" +
"    clipboard.setContents(transferable, null);  \n" +
"   \n" +
"    String vKey = \"v\";\n" +
"\n" +
"            try\n" +
"            {\n" +
"        element.sendKeys(Keys.CONTROL , vKey);    \n" +
"            }\n" +
"            catch (Exception ex)\n" +
"            {\n" +
"            this.Pass = false; \n" +
"            }     \n" +
" }\n" +
"else\n" +
"{\n" +
"    this.Pass = false;\n" +
"}\n" +
"\n" +
"    \n" +
" }\n" +
" catch (Exception e)\n" +
" {\n" +
"  System.out.println (e.toString());\n" +
"  this.Pass = false;\n" +
"  \n" +
" }";
  }
    @Override
    public void RunAction(WebDriver driver)
    {
      
 try
 {
       
       
this.Pass = true;     
if (this.Variable2.length()>0)
 {
       wait = new WebDriverWait(driver, ec_Timeout);  
       WebElement element  = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(this.Variable1)));  

    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    Transferable transferable = new StringSelection(this.Variable2);
    Transferable emptytransferable = new StringSelection("");
    clipboard.setContents(transferable, null);  
   
    String vKey = "v";

            try
            {
        element.sendKeys(Keys.CONTROL , vKey);  
        clipboard.setContents(emptytransferable, null);
            }
            catch (Exception ex)
            {
            this.Pass = false; 
            clipboard.setContents(emptytransferable, null);
            }     
            if (this.BoolVal1.equals(true))
{
   try
  {
Thread.sleep((long)(Math.random() * 150));
  }
  catch (Exception ex)
  {
      System.out.println ("Exception when sleeping random: " + ex.toString());
  }
    try
    {
element.sendKeys(Keys.RETURN);
    }
    catch (Exception ex)
    {
       this.Pass = false; 
    }
 
}
 }
else
{
    this.Pass = false;
}

    
 }
 catch (Exception e)
 {
  System.out.println (e.toString());
  this.Pass = false;
  
 }
    }  
  
        
   
    }