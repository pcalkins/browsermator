package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TypeAtXPATHAction extends Action
{

  
    
  TypeAtXPATHAction (String TargetXPATH, String ToType, Boolean BoolVal1)
    {
        this.Type = "Type at XPATH";
        
        this.Variable1 = TargetXPATH;
        this.Variable2 = ToType;
        this.BoolVal1 = BoolVal1;
        this.Loopable = true;
    }
  @Override
  public void SetGuts()
  {
      this.Guts = "\ntry\n" +
" {\n" +
"        \n" +
"        WebElement element = driver.findElement(By.xpath(\"" + this.Variable1+ "\"));\n" +
"  \n" +
"        element.sendKeys(\"" + this.Variable2+ "\");";
   if (this.BoolVal1.equals(true))
{
    
 this.Guts+="element.sendKeys(Keys.RETURN);";
 
}  
   this.Guts+="this.Pass = true;\n" +
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
        WebElement element = driver.findElement(By.xpath(this.Variable1));
char[] keys_to_type = this.Variable2.toCharArray();
for(int i=0;i<keys_to_type.length;i++){
    String sendkey = String.valueOf(keys_to_type[i]);
  try
  {
Thread.sleep((long)(Math.random() * 200));
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
{
   try
  {
Thread.sleep((long)(Math.random() * 200));
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
 catch (NoSuchElementException e)
 {
  
  this.Pass = false;
  
 }
    }  
  
        
   
    }