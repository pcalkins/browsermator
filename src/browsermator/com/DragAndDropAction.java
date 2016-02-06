/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import org.openqa.selenium.By;
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
    
 try
 {

 WebElement dragElement = driver.findElement(By.xpath(this.Variable1));
 WebElement dropElement = driver.findElement(By.xpath(this.Variable2));
 
  
    Actions actions = new Actions(driver);
  actions.dragAndDrop(dragElement, dropElement).perform();

     this.Pass = true;
 }
 catch (Exception e)
 {
  this.Pass = false;
  System.out.println("Exception: " + e);
  
 }
    }  
       
}
