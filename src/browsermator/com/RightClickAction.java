/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author pcalkins
 */
public class RightClickAction extends BMAction {
       RightClickAction() 
    {
     this.Type = "Right-Click";     
}
       @Override
       public void SetGuts()
       {
           this.Guts = "\n  Actions actions = new Actions(driver);\n" +
" actions.contextClick().build().perform();";
       }
  @Override
    public void RunAction(WebDriver driver)
    {
        WebElement element = driver.switchTo().activeElement();
        Actions actions = new Actions(driver);
      
   //   actions.contextClick(waitedElement);
      Action rightClick = actions.contextClick(element).build();
   rightClick.perform();

 
    } 
}
