package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ClickAtLinkTextAction extends Action 
{
    
    ClickAtLinkTextAction (String LinkTextToClick)
    {
        this.Type = "Click at Link Text"; 
        this.Variable1 = LinkTextToClick;
        
    }
    @Override
     public void RunAction(WebDriver driver)
    {
        try
        {
     WebElement element = driver.findElement(By.linkText(this.Variable1));
   
     element.click();
     this.Pass = true;
        }
     catch (NoSuchElementException e)
 {
  this.Pass = false;
   
 }
    }
}