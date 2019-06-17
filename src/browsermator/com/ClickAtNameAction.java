package browsermator.com;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ClickAtNameAction extends BMAction 
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
      wait = new WebDriverWait(driver, ec_Timeout);  
     
        try { 
  
  
       List<WebElement>elements = driver.findElements(By.name(this.Variable1));
 
 if (this.BoolVal2)
 {

  if (elements.size()>0)
 {
  for (WebElement thiselement: elements)
  {
 WebElement waitedElement =  wait.until(ExpectedConditions.elementToBeClickable(thiselement));
  
      waitedElement.click();
  }
 
  
  this.Pass = true;
 }
 }
else
  {
 if (elements.size()>0)
 {
WebElement element = elements.get(0);
 WebElement waitedElement =  wait.until(ExpectedConditions.elementToBeClickable(element));
  
      waitedElement.click();

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