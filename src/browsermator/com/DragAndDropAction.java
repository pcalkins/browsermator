/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 *
 * @author pcalkins
 */
public class DragAndDropAction extends BMAction {
  DragAndDropAction (String ToDragXPATH, String ToTargetXPATH)
    {
        this.Type = "Drag From ID to ID";
        
        this.Variable1 = ToDragXPATH;
        this.Variable2 = ToTargetXPATH;
        
    }
  @Override
    public void SetGuts()
    {
        this.Guts = "\ntry\n" +
" {\n" +
"\n" +
" WebElement dragElement = driver.findElement(By.xpath(" + this.Variable1+ "));\n" +
" WebElement dropElement = driver.findElement(By.xpath(" + this.Variable2+ "));\n" +
" \n" +
"  \n" +
"    Actions actions = new Actions(driver);\n" +
"  actions.dragAndDrop(dragElement, dropElement).perform();\n" +
"\n" +
"     this.Pass = true;\n" +
" }\n" +
" catch (Exception e)\n" +
" {\n" +
"  this.Pass = false;\n" +
"  System.out.println(\"Exception: \" + e);\n" +
"  \n" +
" }";
    }
    @Override
    public void RunAction(WebDriver driver)
    {
      wait = new WebDriverWait(driver, ec_Timeout);
 try
 {
  
 WebElement dragElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(this.Variable1)));
 WebElement dropElement = wait.until(ExpectedConditions.elementToBeClickable(By.id(this.Variable2)));
// WebElement dragElement = driver.findElement(By.xpath(this.Variable1));
// WebElement dropElement = driver.findElement(By.xpath(this.Variable2));
 
 String simulateFunction = "function simulateDragDrop(sourceNode, destinationNode) {\n" +
"    var EVENT_TYPES = {\n" +
"        DRAG_END: 'dragend',\n" +
"        DRAG_START: 'dragstart',\n" +
"        DROP: 'drop'\n" +
"    }\n" +
"\n" +
"    function createCustomEvent(type) {\n" +
"        var event = new CustomEvent(\"CustomEvent\")\n" +
"        event.initCustomEvent(type, true, true, null)\n" +
"        event.dataTransfer = {\n" +
"            data: {\n" +
"            },\n" +
"            setData: function(type, val) {\n" +
"                this.data[type] = val\n" +
"            },\n" +
"            getData: function(type) {\n" +
"                return this.data[type]\n" +
"            }\n" +
"        }\n" +
"        return event\n" +
"    }\n" +
"\n" +
"    function dispatchEvent(node, type, event) {\n" +
"        if (node.dispatchEvent) {\n" +
"            return node.dispatchEvent(event)\n" +
"        }\n" +
"        if (node.fireEvent) {\n" +
"            return node.fireEvent(\"on\" + type, event)\n" +
"        }\n" +
"    }\n" +
"\n" +
"    var event = createCustomEvent(EVENT_TYPES.DRAG_START)\n" +
"    dispatchEvent(sourceNode, EVENT_TYPES.DRAG_START, event)\n" +
"\n" +
"    var dropEvent = createCustomEvent(EVENT_TYPES.DROP)\n" +
"    dropEvent.dataTransfer = event.dataTransfer\n" +
"    dispatchEvent(destinationNode, EVENT_TYPES.DROP, dropEvent)\n" +
"\n" +
"    var dragEndEvent = createCustomEvent(EVENT_TYPES.DRAG_END)\n" +
"    dragEndEvent.dataTransfer = event.dataTransfer\n" +
"    dispatchEvent(sourceNode, EVENT_TYPES.DRAG_END, dragEndEvent)\n" +
"} var toDrag =document.getElementById('" + this.Variable1 + "'); var toDrop = document.getElementById('" + this.Variable2 + "');";

  ((JavascriptExecutor)driver).executeScript(simulateFunction + "simulateDragDrop(toDrag, toDrop);");
   


     this.Pass = true;
 }
 catch (Exception e)
 {
  this.Pass = false;
  System.out.println("Exception: " + e);
  
 }
    }  
       
}
