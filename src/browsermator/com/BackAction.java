package browsermator.com;


import org.openqa.selenium.WebDriver;


public class BackAction extends Action 
{
    
   BackAction() 
    {
     this.Type = "Back Action";   
    
}
   @Override
   public void SetGuts()
   {
      this.Guts = "driver.navigate().back();";
   }
  @Override
    public void RunAction(WebDriver driver)
    {
        driver.navigate().back();
        this.Pass = true;
    }
    
}