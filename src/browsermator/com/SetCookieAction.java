
package browsermator.com;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;


public class SetCookieAction extends Action 
{
 SetCookieAction (String Key, String Value)
    {
        this.Type = "Set Cookie";
        
        this.Variable1 = Key;
        this.Variable2 = Value;
        
    }
    @Override
    public void RunAction(WebDriver driver)
    {
      
 
// Now set the cookie. This one's valid for the entire domain
Cookie cookie = new Cookie(this.Variable1, this.Variable2);
driver.manage().addCookie(cookie);

    }  
    
    
}
