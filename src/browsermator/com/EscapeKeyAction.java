/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 *
 * @author pcalkins
 */
public class EscapeKeyAction extends BMAction {
      EscapeKeyAction() 
    {
     this.Type = "Escape Key";     
}
      @Override
 public void SetGuts()
 {
    this.Guts = "try\n" +
"{\n" +
"WebElement element = driver.switchTo().activeElement();\n" +
"  element.sendKeys(Keys.ESCAPE);\n" +
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
  element.sendKeys(Keys.ESCAPE);

    this.Pass = true;
}
catch (Exception ex)
{
    this.Pass = false;
}
 
     
    }
}