package browsermator.com;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public abstract class Action implements Serializable {
  
  
    

int index;
Boolean Pass;
LocalDateTime TimeOfTest;
Boolean NOT;
String Type;
String Variable1;
String Variable2;
Boolean BoolVal1;
String BugURL;
Boolean Locked;

    
   
   Action ()
   {
  this.Pass = true;
  this.TimeOfTest = LocalDateTime.now();
  this.NOT = false;
  this.Type = "";
  this.Variable1 = "";
  this.Variable2 = "";
  this.BoolVal1 = false;
  this.Locked = false;
  this.BugURL = "http://www.browsermator.com";
   }
   public void RunAction(WebDriver driver)
   {
    driver.get("http://news.google.com");
    this.TimeOfTest = LocalDateTime.now();
    
   }
  public void ClickCatchAction(WebDriver driver, String xpather)
  {
       try { 
 WebElement element = driver.findElement(By.xpath(xpather));
 element.click();
 this.Pass = true;
 
 }
 catch (NoSuchElementException e)
 {
  this.Pass = false;
  
 }
  }
  public void setBoolVal1 (Boolean BoolVal1)
{
    this.BoolVal1 = BoolVal1;
    
}
public void setActionIndex (int newindex)
{
    this.index = newindex;
}
   public void setVariable1(String variable)
    {
        this.Variable1 = variable;
        
    }
   public void setVariable2(String variable)
   {
       this.Variable2 = variable;
   
   }

   
  

    
       
}
