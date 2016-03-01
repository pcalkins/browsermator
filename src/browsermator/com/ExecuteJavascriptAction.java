/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
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
       
  
        File newJSFile = new File(this.Variable1);
        if (newJSFile.exists())
        {
            try
            {
                  Scanner sc = new Scanner(new FileInputStream(newJSFile));
        String js_TxtFile = ""; 
            while (sc.hasNext()) {          
                String[] s = sc.next().split("\r\n");   
                for (int i = 0; i < s.length; i++) {
                    js_TxtFile += s[i];
                    js_TxtFile += " ";
                }    
                ((JavascriptExecutor)driver).executeScript(js_TxtFile);
            }
            
            }
            catch (Exception ex)
            {
            Prompter errorPrompt = new Prompter("Error occured executing script");  
            System.out.println(ex.toString());
            }
        }
        ((JavascriptExecutor)driver).executeScript(this.Variable1);

 
    }
    
}
