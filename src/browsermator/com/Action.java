package browsermator.com;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


public abstract class Action implements Initializable, Serializable {
  
  
    

int index;
Boolean Pass;
LocalDateTime TimeOfTest;
Boolean NOT;
String Type;
String Variable1;
String Variable2;
Boolean BoolVal1;
Boolean BoolVal2;
String BugURL;
Boolean Locked;
String Password;
Boolean Loopable;
String pause_message;
List<Boolean> loop_pass_values;
List<LocalDateTime> loop_time_of_test;
 String ScreenshotBase64;
 List<String> loop_ScreenshotsBase64;
 String Guts;
 String tostore_varname;
 String tostore_varvalue;
 List<String> tostore_varlist;
   Action ()
   {
  this.tostore_varname = "";
  this.tostore_varvalue = "";
  this.tostore_varlist = new ArrayList<>();
  this.Pass = false;
  this.TimeOfTest = LocalDateTime.now();
  this.NOT = false;
  this.Type = "";
  this.Variable1 = "";
  this.Variable2 = "";
  this.Password = "";
  this.BoolVal1 = false;
  this.BoolVal2 = false;
  this.Locked = false;
  this.BugURL = "https://www.browsermator.com";
  this.Loopable = false;
  this.pause_message = "";
  this.ScreenshotBase64 = "null";
    loop_pass_values = new ArrayList<>();
       loop_time_of_test = new ArrayList<>();
       loop_ScreenshotsBase64 = new ArrayList<>();
   }

   public void InitializeLoopTestVars(int number_of_rows)
   {
       loop_pass_values = new ArrayList<>();
       loop_time_of_test = new ArrayList<>();
       loop_ScreenshotsBase64 = new ArrayList<>();
       for (int x = 0; x<number_of_rows; x++)
       {
       loop_pass_values.add(false);
       loop_time_of_test.add (LocalDateTime.now());
       loop_ScreenshotsBase64.add("<img src = \"\"></img>");
       }
   }
   public void RunAction(WebDriver driver)
   {
  
    
   }
   
   public void RunAction (WebDriver driver, String message)
   {
       
   }

     public int RunAction (WebDriver driver, String message, SeleniumTestToolData in_sitetest, int currentrecord, int number_of_records)
    {
        return number_of_records;
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
     if (driver!=null)
     {
       try { 
  
  
          List<WebElement> elements = driver.findElements(By.xpath(xpather));
 
 if (this.BoolVal2)
 {

  if (elements.size()>1)
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
     else
     {
         this.Pass=false;
     }
  }
  public void RightClickCatchAction (WebDriver driver, String xpather)
  {
        try { 
        // actions don't seem to work with geckodriver
            Actions actions = new Actions(driver);
             List<WebElement> elements = driver.findElements(By.xpath(xpather));
 if (elements.size()>1)
 {
  for (WebElement thiselement: elements)
  {
      actions.contextClick(thiselement);
   
  }
  actions.build();
  actions.perform();
   this.Pass = true;
 }
 else
 {
     if (elements.size()>0)
     {
WebElement element = elements.get(0);
actions.contextClick(element).perform(); 
     }
 this.Pass = true;
 }
if (elements.isEmpty())
{
    this.Pass = false;
}

 
 }
 catch (Exception e)
 {
  this.Pass = false;
  
 }    
  }
  public void setBoolVal1 (Boolean BoolVal1)
{
    this.BoolVal1 = BoolVal1;
    
}
    public void setBoolVal2 (Boolean BoolVal2)
{
    this.BoolVal2 = BoolVal2;
    
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
 public void SetVars (String Variable1, String Variable2, String Password, Boolean BoolVal1, Boolean BoolVal2, Boolean LOCKED)
 {
     this.Variable1 = Variable1;
     this.Variable2 = Variable2;
     this.Password = Password;
     this.BoolVal1 = BoolVal1;
     this.BoolVal2 = BoolVal2;
     this.Locked = LOCKED;
 }
 

    
       
}
