
package browsermator.com;


import org.openqa.selenium.WebDriver;


public class SwitchToFrameAction extends BMAction 
{
  SwitchToFrameAction (String index)
    {
        this.Type = "Switch To Frame";
        
        this.Variable1 = index;
       
        
    }
  @Override
    public void SetGuts()
    {
        this.Guts = "\ndriver.switchTo().frame("+this.Variable1+");";
    }
    @Override
    public void RunAction(WebDriver driver)
    {    
try
{
 driver.switchTo().frame(this.Variable1);

this.Pass = true;
}
catch (Exception ex)
{
    this.Pass = false;
}
}

    
}
