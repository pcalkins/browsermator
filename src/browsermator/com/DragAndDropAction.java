/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author pcalkins
 */
public class DragAndDropAction extends Action {
  DragAndDropAction (String ToDragXPATH, String ToTargetXPATH)
    {
        this.Type = "Drag From XPATH to XPATH";
        
        this.Variable1 = ToDragXPATH;
        this.Variable2 = ToTargetXPATH;
        
    }
    @Override
    public void RunAction(WebDriver driver)
    {
        Boolean SendEnter = false;
 try
 {
    WebElement element = driver.findElement(By.xpath(this.Variable1));
WebElement target = driver.findElement(By.xpath(this.Variable2));

(new Actions(driver)).dragAndDrop(element, target).perform();
        this.Pass = true;
 }
 catch (NoSuchElementException e)
 {
  this.Pass = false;
  
 }
    }  
       
}
