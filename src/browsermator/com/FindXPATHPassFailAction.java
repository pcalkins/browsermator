package browsermator.com;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FindXPATHPassFailAction extends BMAction 
{

 

    
  FindXPATHPassFailAction (String ToFind, Boolean NOTVAR)
    {
        this.Variable1 = ToFind;
        this.Type = "Find XPATH";
         if (NOTVAR) { this.Type = "Do NOT Find XPATH";}
              this.NOT = NOTVAR;

    }
  @Override
  public void SetGuts()
  {
      this.Guts = "\n this.Pass = false;\n" +
"   List<WebElement> element = driver.findElements(By.xpath(" + this.Variable1+ "));\n" +
"       \n" +
"   \n" +
"       if (element.size() > 0 && this.NOT == false)\n" +
"    {\n" +
"        this.Pass = true;\n" +
"  \n" +
"    }\n" +
"       if (element.isEmpty() && this.NOT==true)\n" +
"    {\n" +
"        this.Pass = true;\n" +
"    }";
  }
  @Override
    public void RunAction(WebDriver driver)
    {
         this.Pass = false;
         try
         {
          wait = new WebDriverWait(driver, ec_Timeout);
        List<WebElement> element =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(this.Variable1)));   
 
       if (element.size() > 0 && this.NOT == false)
    {
        this.Pass = true;
  
    }
       if (element.isEmpty() && this.NOT==true)
    {
        this.Pass = true;
    }
         }
         catch (Exception ex)
         {
             this.Pass = false;
             System.out.println ("Exception finding XPATH" + ex.toString());
         }
    }
}