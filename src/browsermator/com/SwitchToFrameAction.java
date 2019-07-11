
package browsermator.com;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


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
// driver.switchTo().frame(this.Variable1);
  wait = new WebDriverWait(driver, ec_Timeout);
  String xpather = "//iframe[@name='" + this.Variable1 + "']";
  wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(xpather)));
this.Pass = true;
}
catch (Exception ex)
{
    System.out.println(ex.toString());
    this.Pass = false;
}
}

    
}
