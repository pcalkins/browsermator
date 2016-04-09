/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import org.openqa.selenium.WebDriver;

/**
 *
 * @author pcalkins
 */
class PauseContinueAction extends Action {
String pause_message;

    public PauseContinueAction() 
     {
        this.Type = "Pause with Continue Button";
        this.pause_message = "Actions Paused...";
       }
    @Override
    public void SetGuts()
    {
        this.Guts = "Prompter thisContinuePrompt = new Prompter("+this.pause_message+");\n" +
"    \n" +
"while(thisContinuePrompt.isVisible() == true){\n" +
"        try\n" +
"        {\n" +
"Thread.sleep(200);\n" +
"this.Pass = true;\n" +
"        }\n" +
"        catch (InterruptedException e)\n" +
"                {\n" +
"                    System.out.println(\"pause exception: \" + e.toString());\n" +
"                }\n" +
"    }";
    }
    @Override
    public void RunAction(WebDriver driver)
    {
    Prompter thisContinuePrompt = new Prompter(this.pause_message);
    
while(thisContinuePrompt.isVisible() == true){
        try
        {
Thread.sleep(200);
this.Pass = true;
        }
        catch (InterruptedException e)
                {
                    System.out.println("pause exception: " + e.toString());
                }
    }
    }
    @Override
    public void RunAction (WebDriver driver, String message)
    {
       Prompter thisContinuePrompt = new Prompter(message);
    
while(thisContinuePrompt.isVisible() == true){
        try
        {
Thread.sleep(200);
        }
        catch (InterruptedException e)
                {
                    System.out.println("pause exception: " + e.toString());
                }
    }   
    }
    
 
  
}
    
