package browsermator.com;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
String pause_message;
Boolean[] loop_pass_values;
LocalDateTime[] loop_time_of_test;
 String ScreenshotBase64;
 String[] loop_ScreenshotsBase64;
 String Guts;
 String tostore_varname;
 String tostore_varvalue;

 ArrayList<String> tostore_varlist;
   Action ()
   {
  this.tostore_varname = "";
  this.tostore_varvalue = "";
  this.tostore_varlist = new ArrayList();
  this.Pass = false;
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
  this.pause_message = "";
  this.ScreenshotBase64 = "null";
  
   }

   public void InitializeLoopTestVars(int number_of_rows)
   {
       this.loop_pass_values = new Boolean[number_of_rows];
       this.loop_time_of_test = new LocalDateTime[number_of_rows];
       this.loop_ScreenshotsBase64 = new String[number_of_rows];
       for (int x = 0; x<number_of_rows; x++)
       {
        this.loop_pass_values[x] = false;
        this.loop_time_of_test[x] = LocalDateTime.now();
        this.loop_ScreenshotsBase64[x] = "<img src = \"\"></img>";
       }
   }
   public void RunAction(WebDriver driver)
   {
  
    
   }
   public void RunAction (WebDriver driver, String message)
   {
       
   }
   public void SetGuts()
   {
       
   }
  public String GetGuts()
  {
     return Guts; 
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
