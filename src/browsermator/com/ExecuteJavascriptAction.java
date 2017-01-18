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
       public void SetGuts()
       {
           this.Guts="\n      File newJSFile = new File("+this.Variable1+");\n" +
"        if (newJSFile.exists())\n" +
"        {\n" +
"            try\n" +
"            {\n" +
"                  Scanner sc = new Scanner(new FileInputStream(newJSFile));\n" +
"        String js_TxtFile = \"\"; \n" +
"            while (sc.hasNext()) {          \n" +
"                String[] s = sc.next().split(\"\\r\\n\");   \n" +
"                for (int i = 0; i < s.length; i++) {\n" +
"                    js_TxtFile += s[i];\n" +
"                    js_TxtFile += \" \";\n" +
"                }    \n" +
"                ((JavascriptExecutor)driver).executeScript(js_TxtFile);\n" +
"            }\n" +
"            \n" +
"            }\n" +
"            catch (Exception ex)\n" +
"            {\n" +
"            Prompter errorPrompt = new Prompter(\"Error occured executing script\");  \n" +
"            System.out.println(ex.toString());\n" +
"            }\n" +
"        }\n" +
"        ((JavascriptExecutor)driver).executeScript("+this.Variable1+");";
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
                try
                {
                ((JavascriptExecutor)driver).executeScript(js_TxtFile);
                this.Pass = true;
                }
                catch (Exception ex)
                {
                     System.out.println ("Exception when running Javascript: " + ex.toString());
            this.Pass = false;    
                }
               
            }
            
            }
            catch (Exception ex)
            {
            Prompter errorPrompt = new Prompter("Error occured executing script", false, 0,0);  
            System.out.println(ex.toString());
            }
        }
        try
        {
        ((JavascriptExecutor)driver).executeScript(this.Variable1);
        this.Pass = true;
        }
        catch (Exception ex)
        {
            System.out.println ("Exception when running Javascript: " + ex.toString());
            this.Pass = false;
        }
        

 
    }
    
}
