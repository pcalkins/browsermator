
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


public class DragAndDropByAction extends Action {
    DragAndDropByAction (String ToDragXPATH, String ToTargetCoordinates)
    {
    this.Type = "Drag From XPATH Distance X and Y Pixels";
        
        this.Variable1 = ToDragXPATH;
        this.Variable2 = ToTargetCoordinates;
        
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
       Prompter alert = new Prompter("X and Y must be integers separated by a comma: ex: 100,100");
   }
 try
 {
    WebElement element = driver.findElement(By.xpath(this.Variable1));
  Actions actions = new Actions(driver);

actions.dragAndDropBy(element, xtarget, ytarget).perform();
        this.Pass = true;
 }
 catch (Exception e)
 {
  this.Pass = false;
  System.out.println("Exception: " + e.toString());
 }
    }     
}
