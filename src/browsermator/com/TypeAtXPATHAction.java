package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TypeAtXPATHAction extends Action
{

  
    
  TypeAtXPATHAction (String TargetXPATH, String ToType, Boolean BoolVal1)
    {
        this.Type = "Type at XPATH";
        
        this.Variable1 = TargetXPATH;
        this.Variable2 = ToType;
        this.BoolVal1 = BoolVal1;
        this.Loopable = true;
    }
    @Override
    public void RunAction(WebDriver driver)
    {
      
 try
 {
        
        WebElement element = driver.findElement(By.xpath(this.Variable1));
  
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