
package browsermator.com;


import org.openqa.selenium.WebDriver;


public class SwitchToFrameAction extends Action 
{
  SwitchToFrameAction (String index)
    {
        this.Type = "Switch To Frame";
        
        this.Variable1 = index;
       
        
    }
    @Override
    public void RunAction(WebDriver driver)
    {    
    driver.switchTo().frame(this.Variable1);
    }

    
}
