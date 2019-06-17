package browsermator.com;


import org.openqa.selenium.WebDriver;


public class BackAction extends BMAction 
{
    
   BackAction() 
    {
     this.Type = "Back Action";   
    
}
   @Override
   public void SetGuts()
   {
      this.Guts = "\ndriver.navigate().back();";
   }
  @Override
    public void RunAction(WebDriver driver)
    {
        driver.navigate().back();
        this.Pass = true;
    }
    
}