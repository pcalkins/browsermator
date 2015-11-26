package browsermator.com;

import org.openqa.selenium.WebDriver;


public class ClickXPATHAction extends Action 
{

 

    
  ClickXPATHAction (String ToClick)
    {
        this.Variable1 = ToClick;
        this.Type = "Click at XPATH";
    }
  @Override
    public void RunAction(WebDriver driver)
    {
    
        ClickCatchAction (driver, this.Variable1);
        
    }
}