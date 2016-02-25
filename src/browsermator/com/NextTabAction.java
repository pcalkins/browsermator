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
    public void RunAction(WebDriver driver)
    {
            Actions actions = new Actions(driver);
 

 actions.keyDown(Keys.CONTROL).perform();
 actions.keyDown(Keys.TAB).perform();
 actions.keyUp(Keys.TAB).perform();
 actions.keyUp(Keys.CONTROL).perform();
   //    driver.findElement(By.tagName("html")).sendKeys(Keys.CONTROL, Keys.TAB); 
       String tab_handle = driver.getWindowHandle();
        driver.switchTo().window(tab_handle); 
        this.Pass = true;
    }
}
