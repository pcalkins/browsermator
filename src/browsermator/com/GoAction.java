package browsermator.com;

import org.openqa.selenium.WebDriver;


public class GoAction extends Action 
{


    GoAction(String URLTOGO) 
    {
      
       this.Variable1 = URLTOGO;
       this.Type = "Go to URL";
    }
    @Override
    public void SetGuts()
    {
      this.Guts = "  try\n" +
"     {\n" +
"     driver.get("+this.Variable1+ ");\n" +
"     this.Pass = true;\n" +
"     }\n" +
"       catch (Exception e)\n" +
" {\n" +
"\n" +
"  this.Pass = false;\n" +
"  \n" +
" }";  
    }
@Override  
public void setVariable1(String variable)
    {
      variable = variable.trim();
      
       Boolean checker = variable.contains("http://");
     Boolean checker2 = variable.contains("https://");
      if (checker == true || checker2 == true) 
      {
      
        this.Variable1 = variable;
       }
      
       else
       {
       
       this.Variable1 = "http://" + variable;
     
       }
    }
    
    @Override
    public void RunAction(WebDriver driver)
    {     
     try
     {
     driver.get(this.Variable1);
     this.Pass = true;
     }
       catch (Exception e)
 {

  this.Pass = false;
  
 }
     }
 
}