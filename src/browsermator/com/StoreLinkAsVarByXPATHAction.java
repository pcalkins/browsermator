/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;


/**
 *
 * @author pcalkins
 */
public class StoreLinkAsVarByXPATHAction extends Action 
{
StoreLinkAsVarByXPATHAction(String TargetXPATH, String StoreVarName)
{
    this.Type = "Store Link as Variable by XPATH";
    this.Variable1 = TargetXPATH;
    this.Variable2 = StoreVarName;

}
      @Override
   public void SetGuts()
   {
      this.Guts = "   try\n" +
" {\n" +
"        \n" +
"        String link_value = driver.findElement(By.xpath(\"" + this.Variable1+"\")).getAttribute(\"href\");\n" +
"  \n" +
"     String varname = this.Variable2;\n" +
"     \n" +
" SetStoredLinkHashValue (\"" + this.Variable2 + "\", link_value);" +
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
        
        String link_value = driver.findElement(By.xpath(this.Variable1)).getAttribute("href");
           if (link_value==null){link_value = "";}
  if (link_value=="")
  {
      link_value = driver.findElement(By.xpath(this.Variable1)).getAttribute("src");
        if (link_value==null){link_value = "";}
  }
     if (!"".equals(link_value))
     {
 SetStoredLinkHashValue (this.Variable2, link_value);
    
        this.Pass = true;
     }
     System.out.println ("storelink_value" + link_value);
 }
 catch (NoSuchElementException e)
 {
  this.Pass = false;
  
 }
    
    }

    public void SetStoredLinkHashValue (String varname, String varvalue)
    {
        this.tostore_varname = varname;
        this.tostore_varvalue = varvalue;
    }
}
