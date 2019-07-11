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
public class SwitchToTabOrWindowAction extends BMAction
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
        this.Guts = "\nint_index =  Integer.parseInt(\""+this.Variable1+"\");";
        }
        this.Guts+="  try\n" +
"        {\n" +
"       ArrayList<String> tabs_windows = new ArrayList<String> (driver.getWindowHandles());\n" +
"    driver.switchTo().window(tabs_windows.get(int_index));\n" +
"    this.Pass = true;\n" +
"        }\n" +
"        catch (Exception ex)\n" +
"        {\n" +
"            this.Pass = false;\n" +
"        }";
     }
    @Override
    public void RunAction(WebDriver driver)
    {
        int int_index = 0;
       
        if (!this.Variable1.equals(""))
        {
        int_index =  Integer.parseInt(this.Variable1);
        }
        try
        {
       ArrayList<String> tabs_windows = new ArrayList<> (driver.getWindowHandles());
    driver.switchTo().window(tabs_windows.get(int_index));
    this.Pass = true;
        }
        catch (Exception ex)
        {
            this.Pass = false;
        }
    }

}
