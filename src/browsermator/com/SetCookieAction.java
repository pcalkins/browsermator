
package browsermator.com;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;


public class SetCookieAction extends BMAction 
{
 SetCookieAction (String Key, String Value)
    {
        this.Type = "Set Cookie";
        
        this.Variable1 = Key;
        this.Variable2 = Value;
        
    }
 @Override
 public void SetGuts()
 {
     this.Guts = "\nCookie cookie = new Cookie(\""+this.Variable1+"\", \"" +this.Variable2+"\");\n" +
"driver.manage().addCookie(cookie);";
 }
    @Override
    public void RunAction(WebDriver driver)
    {
      

Cookie cookie = new Cookie(this.Variable1, this.Variable2);
driver.manage().addCookie(cookie);

    }  
    
    
}
