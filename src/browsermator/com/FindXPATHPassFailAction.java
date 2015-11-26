package browsermator.com;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FindXPATHPassFailAction extends Action 
{

 

    
  FindXPATHPassFailAction (String ToFind, Boolean NOTVAR)
    {
        this.Variable1 = ToFind;
        this.Type = "Find XPATH";
         if (NOTVAR) { this.Type = "Do NOT Find XPATH";}
              this.NOT = NOTVAR;

    }
  @Override
    public void RunAction(WebDriver driver)
    {
   List<WebElement> element = driver.findElements(By.xpath(this.Variable1));
       
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