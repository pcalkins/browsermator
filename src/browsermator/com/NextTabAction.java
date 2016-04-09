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
public class NextTabAction extends Action {
       NextTabAction() 
    {
     this.Type = "Next Tab";     
}
       @Override
       public void SetGuts()
       {
           this.Guts = "  Actions actions = new Actions(driver);\n" +
" \n" +
"\n" +
" actions.keyDown(Keys.CONTROL).perform();\n" +
" actions.keyDown(Keys.TAB).perform();\n" +
" actions.keyUp(Keys.TAB).perform();\n" +
" actions.keyUp(Keys.CONTROL).perform();\n" +
"       String tab_handle = driver.getWindowHandle();\n" +
"        driver.switchTo().window(tab_handle); \n" +
"        this.Pass = true;";
       }
  @Override
    public void RunAction(WebDriver driver)
    {
            Actions actions = new Actions(driver);
 

 actions.keyDown(Keys.CONTROL).perform();
 actions.keyDown(Keys.TAB).perform();
 actions.keyUp(Keys.TAB).perform();
 actions.keyUp(Keys.CONTROL).perform();
       String tab_handle = driver.getWindowHandle();
        driver.switchTo().window(tab_handle); 
        this.Pass = true;
    }
}
