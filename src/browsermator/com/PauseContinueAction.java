/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author pcalkins
 */
class PauseContinueAction extends Action {
String pause_message;
int changex = 0;

    public PauseContinueAction() 
     {
        this.Type = "Pause with Continue Button";
        this.pause_message = "Actions Paused...";
     
       }
    @Override
    public void SetGuts()
    {
        this.Guts = "\nPrompter thisContinuePrompt = new Prompter(\""+this.pause_message+"\");\n" +
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
    Prompter thisContinuePrompt = new Prompter("Actions Paused", this.pause_message, false, 0,0);
    this.Pass = true;
while(thisContinuePrompt.isVisible() == true){
        try
        {
Thread.sleep(200);

        }
        catch (InterruptedException e)
                {
                    System.out.println("pause exception: " + e.toString());
                    this.Pass = false;
                }
    }
    }
    @Override
    public void RunAction (WebDriver driver, String message)
    {
       Prompter thisContinuePrompt = new Prompter("Actions Paused", message, false,0, 0);
    this.Pass = true;
    


    
while(thisContinuePrompt.isVisible() == true){
       try
       {
 Thread.sleep(200);

       }
       catch (InterruptedException e)
               {
                  System.out.println("pause exception: " + e.toString());
                  this.Pass = false;
              }
   }   
    }
  
    @Override
    public int RunAction (WebDriver driver, String message, SeleniumTestToolData in_sitetest, int currentrecord, int number_of_records)
    {
     
       Prompter thisContinuePrompt = new Prompter(in_sitetest.short_filename, message, true, currentrecord, number_of_records);
       this.Pass = true;
       if (number_of_records>0)
       {
        thisContinuePrompt.addJumpToItemListener((ItemEvent e) -> {
        if ((e.getStateChange() == ItemEvent.SELECTED)) {
            Object ChosenIndex = e.getItem();
            int jumpindex = Integer.parseInt(ChosenIndex.toString());
            thisContinuePrompt.JumpToRecord = jumpindex;
            thisContinuePrompt.ClickedContinue();
        }
           });
       }
      thisContinuePrompt.addCancelButtonActionListener(new ActionListener() {
           public void actionPerformed (ActionEvent evt) {

       in_sitetest.cancelled = true;
       thisContinuePrompt.setVisible(false);
       thisContinuePrompt.dispose();
       
   }    
 });
    
while(thisContinuePrompt.isVisible() == true){
        try
        {
Thread.sleep(200);
        }
        catch (InterruptedException e)
                {
                    System.out.println("pause exception: " + e.toString());
                    this.Pass = false;
                }
    }
changex = thisContinuePrompt.JumpToRecord;
return changex;
    }

 
 
  
}
    
