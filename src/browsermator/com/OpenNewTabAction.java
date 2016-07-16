/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;


import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;


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
          this.Guts = "     try\n" +
"      {\n" +
"\n" +
"driver.findElement(By.cssSelector(\"body\")).sendKeys(Keys.chord(Keys.CONTROL, \"t\"));\n" +
"     \n" +
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

driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "t"));
     
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
