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
public class UpArrowKeyAction extends Action {
    UpArrowKeyAction() 
    {
     this.Type = "Up Arrow Key";     
}
  @Override
    public void RunAction(WebDriver driver)
    {
        Actions actions = new Actions(driver);
 
 actions.sendKeys(Keys.ARROW_UP).perform(); 
 
     
    }    
}
