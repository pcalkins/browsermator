package browsermator.com;


import org.openqa.selenium.WebDriver;


public class ClickAtIDAction extends Action
{
    
    ClickAtIDAction (String IDToClick)
    {
        this.Type = "Click at ID"; 
        this.Variable1 = IDToClick;
        
    }
    @Override
     public void RunAction(WebDriver driver)
    {
 String xpather = "//a[@id='" + this.Variable1 + "']";
 ClickCatchAction(driver, xpather);
 
    }
}