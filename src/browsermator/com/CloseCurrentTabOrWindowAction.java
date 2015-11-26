/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.util.ArrayList;
import org.openqa.selenium.WebDriver;

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
    public void RunAction(WebDriver driver)
    {
        driver.close();
         ArrayList<String> tabs_windows = new ArrayList<String> (driver.getWindowHandles());
         if (tabs_windows.size()>0)
         {
         int FocusIndex = tabs_windows.size()-1;
         driver.switchTo().window(tabs_windows.get(FocusIndex));
         }
    }
    
    
}
