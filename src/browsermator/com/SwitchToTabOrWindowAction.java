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
public class SwitchToTabOrWindowAction extends Action
{
     SwitchToTabOrWindowAction (String index)
    {
        this.Type = "Switch To Tab or Window";
        
        this.Variable1 = index;
       
        
    }
     @Override
     public void SetGuts()
     {
         if (this.Variable1!="")
        {
        this.Guts = "int_index =  Integer.parseInt("+this.Variable1+");";
        }
        this.Guts+="    ArrayList<String> tabs_windows = new ArrayList<String> (driver.getWindowHandles());\n" +
"    driver.switchTo().window(tabs_windows.get(int_index));";
     }
    @Override
    public void RunAction(WebDriver driver)
    {
        int int_index = 0;
       
        if (this.Variable1!="")
        {
        int_index =  Integer.parseInt(this.Variable1);
        }
        
       ArrayList<String> tabs_windows = new ArrayList<String> (driver.getWindowHandles());
    driver.switchTo().window(tabs_windows.get(int_index));
    }

}
