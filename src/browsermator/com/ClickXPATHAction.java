package browsermator.com;

import org.openqa.selenium.WebDriver;


public class ClickXPATHAction extends Action 
{

    
  ClickXPATHAction (String ToClick, boolean BoolVal1)
    {
        this.Variable1 = ToClick;
        this.Type = "Click at XPATH";
        this.BoolVal1 = BoolVal1;
        this.Loopable = true;
    }
  @Override
    public void RunAction(WebDriver driver)
    {
        
 if (this.BoolVal1.equals(true))
{
     RightClickCatchAction(driver, this.Variable1);
 }
 else
 {
 ClickCatchAction(driver, this.Variable1);
 }
    }
   

}