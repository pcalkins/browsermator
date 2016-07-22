/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author pcalkins
 */
public class StoreLinksAsArrayByXPATHAction extends Action {
  StoreLinksAsArrayByXPATHAction(String TargetXPATH, String StoreVarName)
{
    this.Type = "Store Links as URL List by XPATH";
    this.Variable1 = TargetXPATH;
    this.Variable2 = StoreVarName;

}
      @Override
   public void SetGuts()
   {
      this.Guts = "   try\n" +
" {\n" +
"\n" +
"        ArrayList<String> link_list = new ArrayList();\n" +
"       List<WebElement> link_elements = driver.findElements(By.xpath(this.Variable1));\n" +
"        for (Iterator<WebElement> it = link_elements.iterator(); it.hasNext();) {\n" +
"            WebElement e = it.next();\n" +
"            String thishref = e.getAttribute(\"href\");\n" +
"            if (link_list.contains(thishref))\n" +
"            {\n" +
"                \n" +
"            }\n" +
"            else\n" +
"            {\n" +
"                link_list.add(thishref);\n" +
"            } }\n" +
"  \n" +
"     \n" +
" SetStoredLinkArray(link_list);\n" +
"    \n" +
"        this.Pass = true;\n" +
" }\n" +
" catch (NoSuchElementException e)\n" +
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

        ArrayList<String> link_list = new ArrayList();
       List<WebElement> link_elements = driver.findElements(By.xpath(this.Variable1));
        for (Iterator<WebElement> it = link_elements.iterator(); it.hasNext();) {
            WebElement e = it.next();
            String thishref = e.getAttribute("href");
            if (link_list.contains(thishref))
            {
                
            }
            else
            {
                link_list.add(thishref);
            } }
  
     
 SetStoredLinkArray(link_list);
    
        this.Pass = true;
 }
 catch (NoSuchElementException e)
 {
  this.Pass = false;
  
 }
    
    }

    public void SetStoredLinkArray (ArrayList<String> to_storelist)
    {
       this.tostore_varlist = to_storelist;
       
    }  
}
