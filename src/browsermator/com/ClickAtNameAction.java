package browsermator.com;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class ClickAtNameAction extends Action 
{
    
    ClickAtNameAction (String LinkTextToClick, boolean BoolVal1, boolean BoolVal2)
    {
        this.Type = "Click at Button Name"; 
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
      
     
        try { 
  
  
       List<WebElement>elements = driver.findElements(By.name(this.Variable1));
 
 if (this.BoolVal2)
 {

  if (elements.size()>0)
 {
  for (WebElement thiselement: elements)
  {

   //    actions.click(thiselement).perform();
       thiselement.click();
  }
 
  
  this.Pass = true;
 }
 }
else
  {
 if (elements.size()>0)
 {
WebElement element = elements.get(0);
element.click();
  this.Pass = true;
 }
 else
 
{
    elements.clear();
    this.Pass = false;
}
  }
     
 }
  catch (Exception e)
 {
     e.printStackTrace();
     System.out.println ("Exception while running clickcatch: " + e.toString());
  this.Pass = false;
  
 }   
  
    
    }
}