package browsermator.com;


import javax.swing.JOptionPane;
import org.openqa.selenium.WebDriver;




public class YesNoPromptPassFailAction extends Action 
{
    
    YesNoPromptPassFailAction (String PromptText)
    {
        this.Type = "Yes/No Prompt"; 
        this.Variable1 = PromptText;
              
    }
    @Override
     public void RunAction(WebDriver driver)
    {
 int PromptResult = JOptionPane.showConfirmDialog(null, this.Variable1, "Pass/Fail Prompt", 
                                JOptionPane.YES_NO_OPTION);
    if (PromptResult == 0)
    {
        this.Pass = true;
    }
    if (PromptResult == 1)
    {
     this.Pass = false;
    }
    }
     
}