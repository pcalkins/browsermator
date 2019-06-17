
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class DragAndDropByAction extends BMAction {
    DragAndDropByAction (String ToDragXPATH, String ToTargetCoordinates)
    {
    this.Type = "Drag From XPATH Distance X and Y Pixels";
        
        this.Variable1 = ToDragXPATH;
        this.Variable2 = ToTargetCoordinates;
        
    }
    @Override
    public void SetGuts()
    {
        this.Guts = "\n String[] coords = "+this.Variable2.split(",")+ "\n" +
"   int xtarget = 0;\n" +
"   int ytarget = 0;\n" +
"   \n" +
"   try\n" +
"   {\n" +
"       coords[0] = coords[0].trim();\n" +
"       coords[1] = coords[1].trim();\n" +
" xtarget = Integer.parseInt(coords[0]);\n" +
" ytarget = Integer.parseInt(coords[1]);\n" +
"   }\n" +
"   catch (Exception e)\n" +
"   {\n" +
"       Prompter alert = new Prompter(\"X and Y must be integers separated by a comma: ex: 100,100\");\n" +
"   }\n" +
" try\n" +
" {\n" +
"    WebElement element = driver.findElement(By.xpath("+this.Variable1+"));\n" +
"  Actions actions = new Actions(driver);\n" +
"\n" +
"actions.dragAndDropBy(element, xtarget, ytarget).perform();\n" +
" }\n" +
" catch (Exception e)\n" +
" {\n" +
"  System.out.println(\"Exception: \" + e.toString());\n" +
" }";
    }
    @Override
    public void RunAction(WebDriver driver)
    {
     
   String[] coords = this.Variable2.split(",");
   int xtarget = 0;
   int ytarget = 0;
   
   try
   {
       coords[0] = coords[0].trim();
       coords[1] = coords[1].trim();
 xtarget = Integer.parseInt(coords[0]);
 ytarget = Integer.parseInt(coords[1]);
   }
   catch (Exception e)
   {
       this.Pass = false;
       Prompter alert = new Prompter("Drag Drop Error", "X and Y must be integers separated by a comma: ex: 100,100", false, 0,0);
   }
 try
 {
    WebElement element = driver.findElement(By.xpath(this.Variable1));
 
 Actions actions = new Actions(driver);

//    Action clickAndDrag = actions.clickAndHold(dragElement).moveByOffset(-1,-1).moveToElement(dropElement, dropElement.getLocation().getX() + dropElement.getSize().getWidth()/2, dropElement.getLocation().getY()+dropElement.getSize().getHeight()/2).release(dragElement).build();

actions.dragAndDropBy(element, xtarget, ytarget).build().perform();
        this.Pass = true;
 }
 catch (Exception e)
 {
  this.Pass = false;
  System.out.println("Exception: " + e.toString());
 }
    }     
}
