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
public class EnterKeyAction extends Action {
      EnterKeyAction() 
    {
     this.Type = "Enter Key";     
}
      @Override
 public void SetGuts()
 {
    this.Guts = "    Actions actions = new Actions(driver);\n" +
" \n" +
" actions.keyDown(Keys.RETURN).perform(); \n" +
" actions.keyUp(Keys.RETURN).perform();"; 
 }
  @Override
    public void RunAction(WebDriver driver)
    {
        Actions actions = new Actions(driver);
 
 actions.keyDown(Keys.RETURN).perform(); 
 actions.keyUp(Keys.RETURN).perform();
 
     
    }
}
