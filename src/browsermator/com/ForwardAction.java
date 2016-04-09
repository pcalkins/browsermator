package browsermator.com;



import org.openqa.selenium.WebDriver;


public class ForwardAction extends Action
{
  ForwardAction()
    {
       this.Type = "Forward Action"; 
    }
  @Override
  public void SetGuts()
  {
   this.Guts = "  try\n" +
"      {\n" +
"        driver.navigate().forward();\n" +
"        this.Pass = true;\n" +
"      }\n" +
"        catch (Exception e)\n" +
" {\n" +
"  this.Pass = false;\n" +
"  \n" +
" }";   
  }
  @Override
    public void RunAction(WebDriver driver)
    {
      try
      {
        driver.navigate().forward();
        this.Pass = true;
      }
        catch (Exception e)
 {
  this.Pass = false;
  
 }
      
    }
}