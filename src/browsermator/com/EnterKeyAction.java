/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author pcalkins
 */
public class EnterKeyAction extends Action {
      EnterKeyAction() 
    {
     this.Type = "Enter Key";     
}
      @Override
 public void SetGuts()
 {
    this.Guts = "try\n" +
"{\n" +
"WebElement element = driver.switchTo().activeElement();\n" +
"  element.sendKeys(Keys.RETURN);\n" +
"\n" +
"    this.Pass = true;\n" +
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
WebElement element = driver.switchTo().activeElement();
  element.sendKeys(Keys.RETURN);

    this.Pass = true;
}
catch (Exception ex)
{
    this.Pass = false;
}
 
     
    }
}
