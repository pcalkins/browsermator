
package browsermator.com;


import org.openqa.selenium.WebDriver;



public class PauseAction extends Action {
 
    PauseAction (String minutes, String seconds)
    {
        this.Type = "Pause";
        
        this.Variable1 = minutes;
        this.Variable2 = seconds;
        
    }
    @Override
    public void RunAction(WebDriver driver)
    {
        int intminutes = 0;
        int intseconds = 0;
        if (this.Variable1!="")
        {
        intminutes =  Integer.parseInt(this.Variable1);
        }
        if (this.Variable2!="")
        {
        intseconds = Integer.parseInt(this.Variable2);
        }
        int totalpause = ((intminutes * 60) + intseconds) * 1000;
        
        try
        {
Thread.sleep(totalpause);
        }
        catch (InterruptedException e)
                {
                    System.out.println("pause exception: " + e.toString());
                }
    }

}
