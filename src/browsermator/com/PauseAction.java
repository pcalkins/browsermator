
package browsermator.com;


import org.openqa.selenium.WebDriver;



public class PauseAction extends BMAction {
 
    PauseAction (String minutes, String seconds)
    {
        this.Type = "Pause";
        
        this.Variable1 = minutes;
        this.Variable2 = seconds;
        
    }
    @Override
    public void SetGuts()
    {
      this.Guts = "\n int intminutes = 0;\n" +
"        int intseconds = 0;";
        if (this.Variable1!="")
        {
       this.Guts+= "intminutes =  Integer.parseInt(\""+this.Variable1+"\");\n";
        }
        if (this.Variable2!="")
        {
        this.Guts+= "intseconds = Integer.parseInt(\""+this.Variable2+"\");\n";
        }  
        this.Guts+=" int totalpause = ((intminutes * 60) + intseconds) * 1000;\n" +
"        \n" +
"        try\n" +
"        {\n" +
"Thread.sleep(totalpause);\n" +
"        }\n" +
"        catch (InterruptedException e)\n" +
"                {\n" +
"                    System.out.println(\"pause exception: \" + e.toString());\n" +
"                }";
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
this.Pass = true;
        }
        catch (InterruptedException e)
                {
                    System.out.println("pause exception: " + e.toString());
                    this.Pass = false;
                }
    }
  

}
