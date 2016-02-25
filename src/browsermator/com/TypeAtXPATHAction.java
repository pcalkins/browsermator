package browsermator.com;

import javax.swing.JTable;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TypeAtXPATHAction extends Action
{

  
    
  TypeAtXPATHAction (String TargetXPATH, String ToType, Boolean BoolVal1)
    {
        this.Type = "Type at XPATH";
        
        this.Variable1 = TargetXPATH;
        this.Variable2 = ToType;
        this.BoolVal1 = BoolVal1;
        this.Loopable = true;
    }
    @Override
    public void RunAction(WebDriver driver)
    {
      
 try
 {
        
        WebElement element = driver.findElement(By.xpath(this.Variable1));

 //    if (this.Variable2.contains("\\n")) { this.Variable2 = this.Variable2.replace("\\n", ""); SendEnter = true; }
  
        element.sendKeys(this.Variable2);
if (this.BoolVal1.equals(true))
{
 element.sendKeys(Keys.RETURN);
 
}
        this.Pass = true;
 }
 catch (NoSuchElementException e)
 {
  this.Pass = false;
  
 }
    }  
    @Override
    public void RunDataLoopAction (WebDriver driver, JTable dataLoopTable)
    {
         
      int number_of_rows = dataLoopTable.getRowCount();
      for (int y=0; y<number_of_rows; y++)
      {
             DataLoopVarParser var1Parser = new DataLoopVarParser(this.Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(this.Variable2);
    if (var1Parser.hasDataLoopVar==false && var2Parser.hasDataLoopVar==false)
    {
        RunAction(driver);
    }
          int number_of_fields = var2Parser.DataLoopVars.size();
            String concat_variable1="";
      for (int x=0; x<number_of_fields; x++)
      {
       DataLoopVarHelper theseDataLoopVars = var2Parser.DataLoopVars.get(x);
       concat_variable1+= theseDataLoopVars.beforevar;
       if (theseDataLoopVars.field_column_index!=-1)
       {
           concat_variable1+=dataLoopTable.getValueAt(y, theseDataLoopVars.field_column_index);
         
       }
       concat_variable1+=theseDataLoopVars.aftervar;
      }
    try
 {
        
        WebElement element = driver.findElement(By.xpath(this.Variable1));

 //    if (this.Variable2.contains("\\n")) { this.Variable2 = this.Variable2.replace("\\n", ""); SendEnter = true; }
  
        element.sendKeys(concat_variable1);
if (this.BoolVal1.equals(true))
{
 element.sendKeys(Keys.RETURN);
 
}
        this.Pass = true;
 }
 catch (NoSuchElementException e)
 {
  this.Pass = false;
  
 }
      }
 
    }  
        
   
    }