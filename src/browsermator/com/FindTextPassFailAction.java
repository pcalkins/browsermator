package browsermator.com;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FindTextPassFailAction extends BMAction
{
    
    FindTextPassFailAction (String TextToFind, Boolean NOTVAR)
    {
       
        this.Type = "Find Text"; 
         if (NOTVAR) { this.Type = "Do NOT Find Text";}
        this.Variable1 = TextToFind;
        this.NOT = NOTVAR;
    
    }
    @Override
    public void SetGuts()
    {
       String xpather = "//*[contains(text(), '" + this.Variable1 + "')]";
       this.Guts = "\n List<WebElement> element = driver.findElements(By.xpath(" + xpather+ "));\n" +
"    \n" +
"    this.Pass = false;\n" +
"    if (element.size() > 0 && this.NOT == false)\n" +
"    {\n" +
"        this.Pass = true;\n" +
"  \n" +
"    }\n" +
"       if (element.isEmpty() && this.NOT==true)\n" +
"    {\n" +
"        this.Pass = true;\n" +
"    }\n" +
"     \n" +
"     ";
    }
    @Override
     public void RunAction(WebDriver driver)
    {
 String xpather = "//*[contains(text(), '" + this.Variable1 + "')]";
    try
        {
    wait = new WebDriverWait(driver, ec_Timeout);
        List<WebElement> element =  wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpather)));   
     
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
        catch (Exception ex)
        {
            this.Pass = false;
            System.out.println ("Exception finding text: " + ex.toString());
        }
     
    }
}