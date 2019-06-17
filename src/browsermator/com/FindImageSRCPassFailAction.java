package browsermator.com;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FindImageSRCPassFailAction extends BMAction
{
    
    FindImageSRCPassFailAction (String ImageSRCToFind, Boolean NOTVAR)
    {
        this.Type = "Find Image SRC"; 
        if (NOTVAR) { this.Type = "Do NOT Find Image SRC";}
        
        
        this.Variable1 = ImageSRCToFind;
        this.NOT = NOTVAR;
     
      
    }
    @Override
    public void SetGuts()
    {
         String xpather = "//img[@src='" + this.Variable1 + "']";
         this.Guts = "\nList<WebElement> element = driver.findElements(By.xpath("+xpather+"));\n" +
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
"    }";
    }
    @Override
     public void RunAction(WebDriver driver)
    {

         String xpather = "//img[@src='" + this.Variable1 + "']";
         
          try
               {
                   wait = new WebDriverWait(driver, ec_Timeout);
              
    List<WebElement> element =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpather))); 
                 
       
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
                   System.out.println ("Exception Finding Image SRC: " + ex.toString());
               }
     
    }
}