package browsermator.com;

import org.openqa.selenium.WebDriver;


public class FindPAGETITLEPassFailAction extends Action
{
    
    FindPAGETITLEPassFailAction (String TITLETEXTToFind, Boolean NOTVAR )
    {
        this.Type = "Find Page Title"; 
        if (NOTVAR) { this.Type = "Do NOT Find Page Title";}
        
        
        this.Variable1 = TITLETEXTToFind;
        this.NOT = NOTVAR;
        
         
    }
    @Override
    public void SetGuts()
    {
        this.Guts = "  Boolean doespass = false;\n" +
"    if(driver.getTitle().contains("+this.Variable1+"))\n" +
"    {\n" +
"   doespass = true;\n" +
"    }\n" +
"   \n" +
"    this.Pass = false;\n" +
"       if (doespass == true && this.NOT == false)\n" +
"    {\n" +
"        this.Pass = true;\n" +
"  \n" +
"    }\n" +
"       if (doespass==false && this.NOT==true)\n" +
"    {\n" +
"        this.Pass = true;\n" +
"    }";
    }
    @Override
     public void RunAction(WebDriver driver)
    {

     
    Boolean doespass = false;
    if(driver.getTitle().contains(this.Variable1))
    {
   doespass = true;
    }
   
    this.Pass = false;
       if (doespass == true && this.NOT == false)
    {
        this.Pass = true;
  
    }
       if (doespass==false && this.NOT==true)
    {
        this.Pass = true;
    }
     
    }
}