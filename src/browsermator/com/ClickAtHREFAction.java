package browsermator.com;

import org.openqa.selenium.WebDriver;


public class ClickAtHREFAction extends Action 
{
    
    ClickAtHREFAction (String HREFToClick, boolean BoolVal1)
    {
        this.Type = "Click at HREF"; 
        this.Variable1 = HREFToClick;
         this.BoolVal1 = BoolVal1;
    }
    
    @Override
     public void RunAction(WebDriver driver)
    {
 String xpather = "//a[@href='" + this.Variable1 + "']";
 if (this.BoolVal1.equals(true))
{
     RightClickCatchAction(driver, xpather);
 }
 else
 {
 ClickCatchAction(driver, xpather);
 }
 
}
}
