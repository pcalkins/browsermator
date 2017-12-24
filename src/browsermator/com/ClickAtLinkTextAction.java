package browsermator.com;

import org.openqa.selenium.WebDriver;


public class ClickAtLinkTextAction extends Action 
{
    
    ClickAtLinkTextAction (String LinkTextToClick, boolean BoolVal1, boolean BoolVal2)
    {
        this.Type = "Click at Link Text"; 
        this.Variable1 = LinkTextToClick;
         this.BoolVal1 = BoolVal1;
         this.BoolVal2 = BoolVal2;
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
      
  
   String xpather = "//a[text()='" + this.Variable1 + "']";
  if (this.BoolVal1.equals(true))
{
     RightClickCatchAction(driver, xpather);
 }
 else
 {
 ClickCatchAction(driver, xpather);
 }        
    }
}