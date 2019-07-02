package browsermator.com;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FindHREFPassFailAction extends BMAction 
{
     int sanitycount = 0;
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
             
             this.Guts = "       String xpather = \"//a[contains(@href,'\"" + this.Variable1 + "\"')]\";\n" +
"          try\n" +
"         {\n" +
"           wait = new WebDriverWait(driver, ec_Timeout);\n" +
"        \n" +
"      \n" +
"         List<WebElement> element =  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpather)));\n" +
"   \n" +
"   \n" +
"\n" +
"    this.Pass = false;\n" +
"       if (element.size() > 0 && this.NOT == false)\n" +
"    {\n" +
"        this.Pass = true;\n" +
"  \n" +
"    }\n" +
"       if (element.isEmpty() && this.NOT==true)\n" +
"    {\n" +
"        this.Pass = true;\n" +
"    }\n" +
"         }\n" +
"         catch (Exception ex)\n" +
"         {\n" +
"    if (ex.getClass().getCanonicalName().equals(\"org.openqa.selenium.StaleElementReferenceException\"))\n" +
"   {\n" +
"   //need to do it again, not finished loading\n" +
"    System.out.println(\"*****************Stale caught-redoing\");\n" +
"    sanitycount++;\n" +
"    if (sanitycount<50000)\n" +
"    {\n" +
"    RunAction(driver);\n" +
"    }\n" +
"      System.out.println (ex.toString());\n" +
"  this.Pass = false;\n" +
"    }\n" +
"   else\n" +
"   {\n" +
"             System.out.println (\"Exception finding Href: \" + ex.toString());\n" +
"             this.Pass = false;\n" +
"   }\n" +
"         }";
    }
    @Override
     public void RunAction(WebDriver driver)
    {
         String xpather = "//a[contains(@href,'" + this.Variable1 + "')]";
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
    if (ex.getClass().getCanonicalName().equals("org.openqa.selenium.StaleElementReferenceException"))
   {
   //need to do it again, not finished loading
    System.out.println("*****************Stale caught-redoing");
    sanitycount++;
    if (sanitycount<50000)
    {
    RunAction(driver);
    }
      System.out.println (ex.toString());
  this.Pass = false;
    }
   else
   {
             System.out.println ("Exception finding Href: " + ex.toString());
             this.Pass = false;
   }
         }
     
    }
}