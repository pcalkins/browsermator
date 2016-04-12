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
public class RightArrowKeyAction extends Action {
     RightArrowKeyAction() 
    {
     this.Type = "Right Arrow Key";     
}
     @Override
     public void SetGuts()
     {
         this.Guts = "\nActions actions = new Actions(driver);\n" +
" \n" +
" actions.sendKeys(Keys.ARROW_RIGHT).perform();";
     }
  @Override
    public void RunAction(WebDriver driver)
    {
        Actions actions = new Actions(driver);
 
 actions.sendKeys(Keys.ARROW_RIGHT).perform(); 
 
     
    }   
}
