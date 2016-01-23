/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

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
       driver.findElement(By.tagName("html")).sendKeys(Keys.CONTROL, Keys.TAB); 
       String tab_handle = driver.getWindowHandle();
        driver.switchTo().window(tab_handle); 
        this.Pass = true;
    }
}
