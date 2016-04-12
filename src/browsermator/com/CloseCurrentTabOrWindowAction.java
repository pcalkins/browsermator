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
public class CloseCurrentTabOrWindowAction extends Action 
{
  CloseCurrentTabOrWindowAction() 
    {
     this.Type = "Close Current Tab or Window";     
}
  @Override
  public void SetGuts()
  {
      this.Guts = "\n Actions actions = new Actions(driver);\n" +
" actions.keyDown(Keys.CONTROL).perform();\n" +
" actions.sendKeys(\"w\").perform();\n" +
" actions.keyUp(Keys.CONTROL).perform();\n" +
"        this.Pass = true;";
  }
  @Override
    public void RunAction(WebDriver driver)
    {
       Actions actions = new Actions(driver);
 actions.keyDown(Keys.CONTROL).perform();
 actions.sendKeys("w").perform();
 actions.keyUp(Keys.CONTROL).perform();
        this.Pass = true;
    }
    
    
}
