package browsermator.com;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FindTextPassFailAction extends Action
{
    
    FindTextPassFailAction (String TextToFind, Boolean NOTVAR)
    {
       
        this.Type = "Find Text"; 
         if (NOTVAR) { this.Type = "Do NOT Find Text";}
        this.Variable1 = TextToFind;
        this.NOT = NOTVAR;
    
    }
    @Override
     public void RunAction(WebDriver driver)
    {
 String xpather = "//*[contains(text(), '" + this.Variable1 + "')]";
         
    List<WebElement> element = driver.findElements(By.xpath(xpather));
    
    this.Pass = false;
    if (element.size() > 0 && this.NOT == false)
    {
        this.Pass = true;
  
    }
       if (element.isEmpty() && this.NOT==true)
    {
        this.Pass = true;
    }
     
     
    }
}