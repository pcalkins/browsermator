package browsermator.com;


import org.openqa.selenium.WebDriver;


public class ClickAtImageSRCAction extends Action
{
    
    ClickAtImageSRCAction (String IDToClick, boolean BoolVal1)
    {
        this.Type = "Click at Image SRC"; 
        this.Variable1 = IDToClick;
         this.BoolVal1 = BoolVal1;
    }
    @Override
     public void RunAction(WebDriver driver)
    {
 String xpather = "//img[@src='" + this.Variable1 + "']";
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