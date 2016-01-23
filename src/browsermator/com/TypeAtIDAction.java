package browsermator.com;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TypeAtIDAction extends Action
{

  
    
  TypeAtIDAction (String TargetFieldID, String ToType, Boolean BoolVal1)
    {
        this.Type = "Type at ID";
        
        this.Variable1 = TargetFieldID;
        this.Variable2 = ToType;
        this.BoolVal1 = BoolVal1;
    }
    @Override
    public void RunAction(WebDriver driver)
    {
Boolean SendEnter = false;
try
{
WebElement element = driver.findElement(By.id(this.Variable1));   
// if (this.Variable2.contains("\\n")) { this.Variable2 = this.Variable2.replace("\\n", ""); SendEnter = true; }
    
        
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
