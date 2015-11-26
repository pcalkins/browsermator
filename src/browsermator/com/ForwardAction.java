package browsermator.com;



import org.openqa.selenium.WebDriver;


public class ForwardAction extends Action
{
  ForwardAction()
    {
       this.Type = "Forward Action"; 
    }
  @Override
    public void RunAction(WebDriver driver)
    {
      try
      {
        driver.navigate().forward();
        this.Pass = true;
      }
        catch (Exception e)
 {
  this.Pass = false;
  
 }
      
    }
}