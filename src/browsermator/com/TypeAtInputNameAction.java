
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class TypeAtInputNameAction extends Action 
{
   
     TypeAtInputNameAction (String TargetFieldID, String ToType)
    {
        this.Type = "Type at Input Name";
        
        this.Variable1 = TargetFieldID;
        this.Variable2 = ToType;
        
    }
    @Override
    public void RunAction(WebDriver driver)
    {
Boolean SendEnter = false;
try
{
WebElement element = driver.findElement(By.name(this.Variable1));   
// if (this.Variable2.contains("\n")) { this.Variable2 = this.Variable2.replace("\n", ""); SendEnter = true; }
    
element.sendKeys(this.Variable2);
if (SendEnter)
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
