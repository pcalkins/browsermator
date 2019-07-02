
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TypePasswordAtInputNameAction extends BMAction
{
 TypePasswordAtInputNameAction (String TargetXPATH, String ToType, Boolean BoolVal1)
    {
        this.Type = "Type Password at Input Name";
        this.Variable1 = TargetXPATH;
        this.Variable2 = ToType;
     this.BoolVal1 = BoolVal1;
    }
 @Override
   public void SetGuts()
   {
    this.Guts = " try\n" +
" {\n" +
"this.Pass = true;\n" +
"      \n" +
"if (this.Variable2.length()>0)\n" +
" {\n" +
"    wait = new WebDriverWait(driver, ec_Timeout);     \n" +
"       WebElement element  = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(" + this.Variable1+"))); \n" +
"   \n" +
"char[] keys_to_type = this.Variable2.toCharArray();\n" +
"for(int i=0;i<keys_to_type.length;i++){\n" +
"    String sendkey = String.valueOf(keys_to_type[i]);\n" +
"  try\n" +
"  {\n" +
"Thread.sleep((long)(Math.random() * 150));\n" +
"  }\n" +
"  catch (Exception ex)\n" +
"  {\n" +
"      System.out.println (\"Exception when sleeping random: \" + ex.toString());\n" +
"  }\n" +
"    try\n" +
"    {\n" +
"element.sendKeys(sendkey);\n" +
"    }\n" +
"    catch (Exception ex)\n" +
"    {\n" +
"       this.Pass = false; \n" +
"    }\n" +
"}\n" +
"if (this.BoolVal1.equals(true))\n" +
"{  try\n" +
"  {\n" +
"Thread.sleep((long)(Math.random() * 150));\n" +
"  }\n" +
"  catch (Exception ex)\n" +
"  {\n" +
"      System.out.println (\"Exception when sleeping random: \" + ex.toString());\n" +
"  }\n" +
"    try\n" +
"    {\n" +
"element.sendKeys(Keys.RETURN);\n" +
"    }\n" +
"    catch (Exception ex)\n" +
"    {\n" +
"       this.Pass = false; \n" +
"    }\n" +
"}\n" +
" }\n" +
"else\n" +
"{\n" +
"    this.Pass = false;\n" +
"}\n" +
"\n" +
"   \n" +
" }\n" +
" catch (Exception e)\n" +
" {\n" +
"     System.out.println (\"Exception typing password at name: \" + e.toString());\n" +
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
       WebElement element  = wait.until(ExpectedConditions.presenceOfElementLocated(By.name(this.Variable1))); 
   
char[] keys_to_type = this.Variable2.toCharArray();
for(int i=0;i<keys_to_type.length;i++){
    String sendkey = String.valueOf(keys_to_type[i]);
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
element.sendKeys(sendkey);
    }
    catch (Exception ex)
    {
       this.Pass = false; 
    }
}
if (this.BoolVal1.equals(true))
{  try
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
     System.out.println ("Exception typing password at name: " + e.toString());
  this.Pass = false;
  
 }
    }  
    
    
    
    
}
