
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TypePasswordAtInputNameAction extends Action
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
    this.Guts = "WebElement element = driver.findElement(By.name(this.Variable1));  \n" +
"        element.sendKeys(this.Variable2);";   
    if (this.BoolVal1.equals(true))
{
   this.Guts+= "element.sendKeys(Keys.RETURN);";
}
   this.Guts+="      this.Pass = true;\n" +
" }\n" +
" catch (NoSuchElementException e)\n" +
" {\n" +
"  this.Pass = false;\n" +
"  \n" +
" }"; 
   }
    @Override
    public void RunAction(WebDriver driver)
    {

 try
 {
        
        WebElement element = driver.findElement(By.name(this.Variable1));  
        element.sendKeys(this.Variable2);
if (this.BoolVal1.equals(true))
{
    element.sendKeys(Keys.RETURN);
}
        this.Pass = true;
 }
 catch (NoSuchElementException e)
 {
  this.Pass = false;
  
 }
    }  
    
    
    
}
