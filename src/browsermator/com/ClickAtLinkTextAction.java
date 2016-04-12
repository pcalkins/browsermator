package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ClickAtLinkTextAction extends Action 
{
    
    ClickAtLinkTextAction (String LinkTextToClick, boolean BoolVal1)
    {
        this.Type = "Click at Link Text"; 
        this.Variable1 = LinkTextToClick;
         this.BoolVal1 = BoolVal1;
    }
    @Override
    public void SetGuts()
 {
   if (this.BoolVal1.equals(true))
{

    
        this.Guts = "\n try\n" +
"        {\nActions actions = new Actions(driver);\n"
                + "WebElement element = driver.findElement(By.linkText(\"" + this.Variable1 + "\"));\n"
                + "actions.contextClick(element).perform(); }\n" +
"     catch (NoSuchElementException e)\n" +
" {\n" +
"  this.Pass = false;\n" +
"   \n" +
" }\n" +
" }\n" +
" else\n" +
" {\n" +
"     try\n" +
"        {\n" +
"     WebElement element = driver.findElement(By.linkText(\"" + this.Variable1 + "\"));\n" +
"   \n" +
"     element.click();\n" +
"     this.Pass = true;\n" +
"        }\n" +
"     catch (NoSuchElementException e)\n" +
" {\n" +
"  \n" +
"   \n" +
" }\n" +
" }\n" +
"    }";
        
 }
    else
 {
   this.Guts = "  try\n" +
"        {\n" +
"     WebElement element = driver.findElement(By.linkText(\"" + this.Variable1+ "\"));\n" +
"   \n" +
"     element.click();\n" +
"     this.Pass = true;\n" +
"        }\n" +
"     catch (NoSuchElementException e)\n" +
" {\n" +
"  this.Pass = false;\n" +
"   \n" +
" }\n" +
" }\n" +
"    }";
   
 }
  
 }
    @Override
     public void RunAction(WebDriver driver)
    {
    
 if (this.BoolVal1.equals(true))
{
 try
        {
    
          Actions actions = new Actions(driver);
 WebElement element = driver.findElement(By.linkText(this.Variable1));
 actions.contextClick(element).perform();
     this.Pass = true;
        }
     catch (NoSuchElementException e)
 {
  this.Pass = false;
   
 }
 }
 else
 {
     try
        {
     WebElement element = driver.findElement(By.linkText(this.Variable1));
   
     element.click();
     this.Pass = true;
        }
     catch (NoSuchElementException e)
 {
  this.Pass = false;
   
 }
 }
    }
}