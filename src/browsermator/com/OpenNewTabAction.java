/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;


import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;


/**
 *
 * @author pcalkins
 */
public class OpenNewTabAction extends Action {
      OpenNewTabAction() 
    {
     this.Type = "Open New Tab";     
}
      @Override
      public void SetGuts()
      {
          this.Guts = "    try\n" +
"      {\n" +
"        \n" +
"((JavascriptExecutor)driver).executeScript(\"window.open('about:blank', '_blank');\");\n" +
"\n" +
"        Set<String> tab_handles = driver.getWindowHandles();\n" +
"        int number_of_tabs = tab_handles.size();\n" +
"        int new_tab_index = number_of_tabs-1;\n" +
"        driver.switchTo().window(tab_handles.toArray()[new_tab_index].toString()); \n" +
"        this.Pass = true;\n" +
"      }\n" +
"      catch (Exception ex)\n" +
"      {\n" +
"          this.Pass = false;\n" +
"      }";
      }
  @Override
    public void RunAction(WebDriver driver)
    {
      try
      {
        
 ((JavascriptExecutor)driver).executeScript("window.open('about:blank', '_blank');");
// ((JavascriptExecutor)driver).executeScript("var d=document,a=d.createElement('a');a.target='_blank';a.id = 'newtabber'; a.href='';a.innerHTML='.';d.body.appendChild(a);");
// driver.findElement(By.id("newtabber")).click();
   

    
// driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "t"));
// Actions actions = new Actions(driver); 
// actions.keyDown(Keys.CONTROL).sendKeys("t").keyUp(Keys.CONTROL).build().perform();  
        Set<String> tab_handles = driver.getWindowHandles();
        int number_of_tabs = tab_handles.size();
        int new_tab_index = number_of_tabs-1;
        driver.switchTo().window(tab_handles.toArray()[new_tab_index].toString()); 
        this.Pass = true;
      }
      catch (Exception ex)
      {
          this.Pass = false;
      }
    }
}
