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
public class NextTabAction extends Action {
       NextTabAction() 
    {
     this.Type = "Next Tab";     
}
       @Override
       public void SetGuts()
       {
           this.Guts = "     Actions actions = new Actions(driver);\n" +
"   String current_tab_handle = driver.getWindowHandle();\n" +
"     \n" +
"   \n" +
" actions.keyDown(Keys.CONTROL).sendKeys(Keys.TAB).keyUp(Keys.CONTROL).build().perform();\n" +
" \n" +
"    Set<String> tab_handles = driver.getWindowHandles();\n" +
"   int current_tab_index = 0;\n" +
"  \n" +
"   int number_of_tabs = tab_handles.size();\n" +
"   int tabs_counted = 1;\n" +
"    for(String handle  : tab_handles)\n" +
"       {\n" +
"       if(handle.equals(current_tab_handle))\n" +
"          {\n" +
"         \n" +
"          if (tabs_counted==number_of_tabs)\n" +
"          {\n" +
"              // last tab\n" +
"              current_tab_index = 0;\n" +
"          }\n" +
"          else\n" +
"          {\n" +
"              current_tab_index = tabs_counted;\n" +
"          }\n" +
"          }\n" +
"       \n" +
"       tabs_counted++;\n" +
"       }  \n" +
"   \n" +
"       \n" +
"        driver.switchTo().window(tab_handles.toArray()[current_tab_index].toString()); \n" +
"        \n" +
"      \n" +
"        this.Pass = true;\n" +
"   }\n" +
"   catch (Exception ex)\n" +
"           {\n" +
"               this.Pass = false;\n" +
"           }";
       }
  @Override
    public void RunAction(WebDriver driver)
    {
   try
   {
 //       Actions actions = new Actions(driver);
   String current_tab_handle = driver.getWindowHandle();
     
   driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, Keys.TAB));
 // actions.keyDown(Keys.CONTROL).sendKeys(Keys.TAB).keyUp(Keys.CONTROL).build().perform();
 
    Set<String> tab_handles = driver.getWindowHandles();
   int current_tab_index = 0;
  
   int number_of_tabs = tab_handles.size();
   int tabs_counted = 1;
    for(String handle  : tab_handles)
       {
       if(handle.equals(current_tab_handle))
          {
         
          if (tabs_counted==number_of_tabs)
          {
              // last tab
              current_tab_index = 0;
          }
          else
          {
              current_tab_index = tabs_counted;
          }
          }
       
       tabs_counted++;
       }  
   
       
        driver.switchTo().window(tab_handles.toArray()[current_tab_index].toString()); 
        
      
        this.Pass = true;
   }
   catch (Exception ex)
           {
               this.Pass = false;
           }
    }
}
