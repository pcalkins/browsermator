
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TypePasswordAtInputNameAction extends Action
{
 TypePasswordAtInputNameAction (String TargetXPATH, String ToType, Boolean BoolVal1)
    {
        this.Type = "Type Password at Input Name";
        this.Variable1 = TargetXPATH;
        this.Variable2 = ToType;
     this.BoolVal1 = BoolVal1;
    }
 @Override
   public void SetGuts()
   {
    this.Guts = "\nWebElement element = driver.findElement(By.name(\""+this.Variable1+"\"));  \n" +
"        element.sendKeys(\"" +this.Variable2+"\");";   
    if (this.BoolVal1.equals(true))
{
   this.Guts+= "element.sendKeys(Keys.RETURN);";
}
   this.Guts+="      this.Pass = true;\n" +
" }\n" +
" catch (NoSuchElementException e)\n" +
" {\n" +
"  this.Pass = false;\n" +
"  \n" +
" }"; 
   }
     @Override
    public void RunAction(WebDriver driver)
    {

 try
 {
this.Pass = true;
      
if (this.Variable2.length()>0)
 {
       WebElement element = driver.findElement(By.name(this.Variable1)); 
char[] keys_to_type = this.Variable2.toCharArray();
for(int i=0;i<keys_to_type.length;i++){
    String sendkey = String.valueOf(keys_to_type[i]);
  try
  {
Thread.sleep((long)(Math.random() * 150));
  }
  catch (Exception ex)
  {
      System.out.println ("Exception when sleeping random: " + ex.toString());
  }
    try
    {
element.sendKeys(sendkey);
    }
    catch (Exception ex)
    {
       this.Pass = false; 
    }
}
if (this.BoolVal1.equals(true))
{  try
  {
Thread.sleep((long)(Math.random() * 150));
  }
  catch (Exception ex)
  {
      System.out.println ("Exception when sleeping random: " + ex.toString());
  }
    try
    {
element.sendKeys(Keys.RETURN);
    }
    catch (Exception ex)
    {
       this.Pass = false; 
    }
}
 }
else
{
    this.Pass = false;
}

   
 }
 catch (Exception e)
 {
  this.Pass = false;
  
 }
    }  
    
    
    
    
}
