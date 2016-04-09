/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author pcalkins
 */
public class OpenNewTabAction extends Action {
      OpenNewTabAction() 
    {
     this.Type = "Open New Tab";     
}
      @Override
      public void SetGuts()
      {
          this.Guts = "Actions actions = new Actions(driver);\n" +
" actions.keyDown(Keys.CONTROL).perform();\n" +
" actions.sendKeys(\"t\").perform();\n" +
" actions.keyUp(Keys.CONTROL).perform();\n" +
"\n" +
"           \n" +
"       String tab_handle = driver.getWindowHandle();\n" +
"        driver.switchTo().window(tab_handle); \n" +
"        this.Pass = true;";
      }
  @Override
    public void RunAction(WebDriver driver)
    {
         Actions actions = new Actions(driver);
 actions.keyDown(Keys.CONTROL).perform();
 actions.sendKeys("t").perform();
 actions.keyUp(Keys.CONTROL).perform();

           
       String tab_handle = driver.getWindowHandle();
        driver.switchTo().window(tab_handle); 
        this.Pass = true;
    }
}
