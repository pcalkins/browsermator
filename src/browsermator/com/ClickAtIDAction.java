package browsermator.com;


import org.openqa.selenium.WebDriver;


public class ClickAtIDAction extends BMAction
{
    
    ClickAtIDAction (String IDToClick, boolean BoolVal1, boolean BoolVal2)
    {
        this.Type = "Click at ID"; 
        this.Variable1 = IDToClick;
        this.BoolVal1 = BoolVal1;
        this.BoolVal2 = BoolVal2;
    }
    @Override
        public void SetGuts()
 {
  String xpather = "//*[@id='" + this.Variable1 + "']";
 
   if (this.BoolVal1.equals(true))
{
   this.Guts = "\nRightClickCatchAction(driver, \"" + xpather + "\");";
   
 }
 else
 {
 this.Guts = "\nClickCatchAction(driver, \""+xpather + "\");";
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