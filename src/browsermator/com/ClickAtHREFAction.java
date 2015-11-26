package browsermator.com;

import org.openqa.selenium.WebDriver;


public class ClickAtHREFAction extends Action 
{
    
    ClickAtHREFAction (String HREFToClick)
    {
        this.Type = "Click at HREF"; 
        this.Variable1 = HREFToClick;
        
    }
    
    @Override
     public void RunAction(WebDriver driver)
    {
 String xpather = "//a[@href='" + this.Variable1 + "']";

 
 ClickCatchAction(driver, xpather);
}
}
