package browsermator.com;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FindHREFPassFailAction extends Action 
{
    
    FindHREFPassFailAction (String HREFToFind, Boolean NOTVAR)
    {
        this.Type = "Find HREF"; 
        if (NOTVAR) { this.Type = "Do NOT Find HREF";}
        
        
        this.Variable1 = HREFToFind;
        this.NOT = NOTVAR;
      
    }
    @Override
    public void SetGuts()
    {
             String xpather = "//a[contains(@href='" + this.Variable1 + "')]"; 
             this.Guts = " List<WebElement> element = driver.findElements(By.xpath("+xpather+"));\n" +
"    \n" +
"    this.Pass = false;\n" +
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

         String xpather = "//a[contains(@href='" + this.Variable1 + "')]";
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