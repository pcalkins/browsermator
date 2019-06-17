package browsermator.com;

import org.openqa.selenium.WebDriver;


public class ClickAtButtonTextAction extends BMAction 
{
    
    ClickAtButtonTextAction (String LinkTextToClick, boolean BoolVal1, boolean BoolVal2)
    {
        this.Type = "Click at Button Text"; 
        this.Variable1 = LinkTextToClick;
         this.BoolVal1 = BoolVal1;
         this.BoolVal2 = BoolVal2;
    }
    @Override
    public void SetGuts()
 {
   if (this.BoolVal1.equals(true))
{

    
        this.Guts = " String xpather = \"//button[text()='\" +"+ this.Variable1 +  " + \"']\"; RightClickCatchAction(driver, xpather);";
        
 }
    else
 {
   this.Guts = " String xpather = \"//button[text()='\" + " + this.Variable1 + "+ \"']\";ClickCatchAction(driver, xpather);";
   
 }
  
 }
    @Override
     public void RunAction(WebDriver driver)
    {
      
  
   String xpather = "//button[text()='" + this.Variable1 + "']";
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