package browsermator.com;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class FindPAGETITLEPassFailAction extends BMAction
{
    
    FindPAGETITLEPassFailAction (String TITLETEXTToFind, Boolean NOTVAR )
    {
        this.Type = "Find Page Title"; 
        if (NOTVAR) { this.Type = "Do NOT Find Page Title";}
        
        
        this.Variable1 = TITLETEXTToFind;
        this.NOT = NOTVAR;
        
         
    }
    @Override
    public void SetGuts()
    {
        this.Guts = "\n  Boolean doespass = false;\n" +
"    if(driver.getTitle().contains(\""+this.Variable1+"\"))\n" +
"    {\n" +
"   doespass = true;\n" +
"    }\n" +
"   \n" +
"    this.Pass = false;\n" +
"       if (doespass == true && this.NOT == false)\n" +
"    {\n" +
"        this.Pass = true;\n" +
"  \n" +
"    }\n" +
"       if (doespass==false && this.NOT==true)\n" +
"    {\n" +
"        this.Pass = true;\n" +
"    }";
    }
    @Override
     public void RunAction(WebDriver driver)
    {

            
        try
    {
        wait = new WebDriverWait(driver, ec_Timeout);
  
    
    Boolean doespass  =  wait.until(ExpectedConditions.titleContains(this.Variable1));
  
    this.Pass = false;
       if (doespass == true && this.NOT == false)
    {
        this.Pass = true;
  
    }
       if (doespass==false && this.NOT==true)
    {
        this.Pass = true;
    }
    }
    catch (Exception ex)
    {
        if (this.NOT==true)
        {
            this.Pass = true;
        }
        else
        {
        this.Pass = false;
        }
        System.out.println ("Exception finding page title: " + ex.toString());
    }
    }
}