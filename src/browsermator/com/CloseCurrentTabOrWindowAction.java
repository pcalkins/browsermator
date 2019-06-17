/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author pcalkins
 */
public class CloseCurrentTabOrWindowAction extends BMAction 
{
  CloseCurrentTabOrWindowAction() 
    {
     this.Type = "Close Current Tab or Window";     
}
  @Override
  public void SetGuts()
  {
      this.Guts = "try\n" +
"{\n" +
"\n" +
"\n" +
"((JavascriptExecutor)driver).executeScript(\"close();\");\n" +
"    for (String winHandle : driver.getWindowHandles()) {\n" +
"  driver.switchTo().window(winHandle); \n" +
"}\n" +
"     this.Pass = true;\n" +
"}\n" +
"catch (Exception ex)\n" +
"{\n" +
"    this.Pass = false;\n" +
"}";
  }
  @Override
    public void RunAction(WebDriver driver)
    {
try
{


 // driver.findElement(By.cssSelector("html")).sendKeys(Keys.chord(Keys.CONTROL, "w"));
// Actions actions = new Actions(driver); 
// actions.keyDown(Keys.CONTROL).sendKeys("w").keyUp(Keys.CONTROL).build().perform();
((JavascriptExecutor)driver).executeScript("close();");
    for (String winHandle : driver.getWindowHandles()) {
  driver.switchTo().window(winHandle); 
}
     this.Pass = true;
}
catch (Exception ex)
{
    this.Pass = false;
}
  
   
    }
    
    
}
