package browsermator.com;

import java.time.LocalDateTime;
import javax.swing.JTable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public abstract class Action implements Initializable {
  
  
    

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
String Password;
Boolean Loopable; 
   Action ()
   {
  this.Pass = true;
  this.TimeOfTest = LocalDateTime.now();
  this.NOT = false;
  this.Type = "";
  this.Variable1 = "";
  this.Variable2 = "";
  this.Password = "";
  this.BoolVal1 = false;
  this.Locked = false;
  this.BugURL = "http://www.browsermator.com";
  this.Loopable = false;
   }
   public void RunAction(WebDriver driver)
   {
  
    
   }
   public void RunDataLoopAction (WebDriver driver, JTable dataLoopTable)
   {
       
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
  public void RightClickCatchAction (WebDriver driver, String xpather)
  {
        try { 
            Actions actions = new Actions(driver);
 WebElement element = driver.findElement(By.xpath(xpather));
 actions.contextClick(element).perform();
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
@Override
 public void SetVars (String Variable1, String Variable2, String Password, Boolean BoolVal1)
 {
     this.Variable1 = Variable1;
     this.Variable2 = Variable2;
     this.Password = Password;
     this.BoolVal1 = BoolVal1;
 }
  

    
       
}
