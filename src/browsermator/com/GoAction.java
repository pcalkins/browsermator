package browsermator.com;

import org.openqa.selenium.WebDriver;


public class GoAction extends BMAction 
{


    GoAction(String URLTOGO) 
    {
      
       this.Variable1 = URLTOGO;
       this.Type = "Go to URL";
    }
    @Override
    public void SetGuts()
    {
      this.Guts = "\n  try\n" +
"     {\n" +
"     driver.get(\""+this.Variable1+ "\");\n" +
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
     Boolean checker3 = variable.contains("[dataloop-field-start]");
     Boolean checker4 = variable.contains("[stored_varname-start]");
     
      if (checker == true || checker2 == true || checker3 == true || checker4 ==true) 
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