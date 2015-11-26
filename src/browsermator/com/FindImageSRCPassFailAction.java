package browsermator.com;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FindImageSRCPassFailAction extends Action
{
    
    FindImageSRCPassFailAction (String ImageSRCToFind, Boolean NOTVAR)
    {
        this.Type = "Find Image SRC"; 
        if (NOTVAR) { this.Type = "Do NOT Find Image SRC";}
        
        
        this.Variable1 = ImageSRCToFind;
        this.NOT = NOTVAR;
     
      
    }
    @Override
     public void RunAction(WebDriver driver)
    {

         String xpather = "//img[@src='" + this.Variable1 + "']";
         // String xpather = "//iframe[@src='" + this.Variable1 + "']";
                 
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