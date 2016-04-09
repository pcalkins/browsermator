package browsermator.com;


import org.openqa.selenium.WebDriver;


public class ClickAtIDAction extends Action
{
    
    ClickAtIDAction (String IDToClick, boolean BoolVal1)
    {
        this.Type = "Click at ID"; 
        this.Variable1 = IDToClick;
        this.BoolVal1 = BoolVal1;
    }
    @Override
        public void SetGuts()
 {
  String xpather = "//*[@id='" + this.Variable1 + "']";
 
   if (this.BoolVal1.equals(true))
{
   this.Guts = "RightClickCatchAction(driver, " + xpather + ");";
   
 }
 else
 {
 this.Guts = "ClickCatchAction(driver, "+xpather + ");";
 }
 }
    @Override
     public void RunAction(WebDriver driver)
    {
 String xpather = "//*[@id='" + this.Variable1 + "']";
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