/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author pcalkins
 */
public class ExecuteJavascriptAction extends Action {
       ExecuteJavascriptAction (String JavaScriptToExecute)
    {
        this.Type = "Execute Javascript"; 
        this.Variable1 = JavaScriptToExecute;
        
    }
    @Override
     public void RunAction(WebDriver driver)
    {
        ((JavascriptExecutor)driver).executeScript(this.Variable1);

 
    }
    
}
