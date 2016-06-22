/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.util.ArrayList;
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
      this.Guts = "try\n" +
"{\n" +
" String currenthandle = driver.getWindowHandle();\n" +
"  driver.switchTo().window(currenthandle);\n" +
"\n" +
"driver.close();\n" +
" for (String winHandle : driver.getWindowHandles()) {\n" +
"  driver.switchTo().window(winHandle); \n" +
"}\n" +
"     this.Pass = true;\n" +
"}\n" +
"catch (Exception ex)\n" +
"{\n" +
"    this.Pass = false;\n" +
"}";
  }
  @Override
    public void RunAction(WebDriver driver)
    {
try
{
 String currenthandle = driver.getWindowHandle();
  driver.switchTo().window(currenthandle);

driver.close();
 for (String winHandle : driver.getWindowHandles()) {
  driver.switchTo().window(winHandle); 
}
     this.Pass = true;
}
catch (Exception ex)
{
    this.Pass = false;
}
  
   
    }
    
    
}
