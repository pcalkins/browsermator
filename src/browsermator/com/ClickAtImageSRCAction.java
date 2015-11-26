package browsermator.com;


import org.openqa.selenium.WebDriver;


public class ClickAtImageSRCAction extends Action
{
    
    ClickAtImageSRCAction (String IDToClick)
    {
        this.Type = "Click at Image SRC"; 
        this.Variable1 = IDToClick;
        
    }
    @Override
     public void RunAction(WebDriver driver)
    {
 String xpather = "//img[@src='" + this.Variable1 + "']";
         
 ClickCatchAction(driver, xpather);
    }
}