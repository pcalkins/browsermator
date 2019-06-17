package browsermator.com;

import org.openqa.selenium.WebDriver;


public class ClickAtHREFAction extends BMAction 
{
    
    ClickAtHREFAction (String HREFToClick, boolean BoolVal1, boolean BoolVal2)
    {
        this.Type = "Click at HREF"; 
        this.Variable1 = HREFToClick;
         this.BoolVal1 = BoolVal1;
         this.BoolVal2 = BoolVal2;
    }
    
    @Override
     public void RunAction(WebDriver driver)
    {
 String xpather = "//a[@href='" + this.Variable1 + "']";
 if (this.BoolVal1)
{
     RightClickCatchAction(driver, xpather);
 }
 else
 {
 ClickCatchAction(driver, xpather);
 }
 

}
     @Override
      public void SetGuts()
 {
   String xpather = "//a[@href='" + this.Variable1 + "']";
  
   if (this.BoolVal1.equals(true))
{
   this.Guts = "\nRightClickCatchAction(driver, \"" + xpather + "\");";
   
 }
 else
 {
 this.Guts = "\nClickCatchAction(driver, \""+xpather + "\");";
 }
 }
}
