
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TypePasswordAtIDAction extends Action
{
 TypePasswordAtIDAction (String TargetXPATH, String ToType, Boolean BoolVal1)
    {
        this.Type = "Type Password at ID";
        this.Variable1 = TargetXPATH;
        this.Variable2 = ToType;
     this.BoolVal1 = BoolVal1;
    }
 @Override
   public void SetGuts()
   {
    this.Guts = "\nWebElement element = driver.findElement(By.id(\""+this.Variable1+"\"));  \n" +
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

        WebElement element = driver.findElement(By.id(this.Variable1));  
char[] keys_to_type = this.Variable2.toCharArray();
for(int i=0;i<keys_to_type.length;i++){
    String sendkey = String.valueOf(keys_to_type[i]);
  try
  {
Thread.sleep((long)(Math.random() * 100));
  }
  catch (Exception ex)
  {
      System.out.println ("Exception when sleeping random: " + ex.toString());
  }
          
element.sendKeys(sendkey);
}
if (this.BoolVal1.equals(true))
{
    element.sendKeys(Keys.RETURN);
}
        this.Pass = true;
 }
 catch (NoSuchElementException e)
 {
  this.Pass = false;
  
 }
    }  
    
    
    
}
