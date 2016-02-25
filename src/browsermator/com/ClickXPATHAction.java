package browsermator.com;

import javax.swing.JTable;
import org.openqa.selenium.WebDriver;


public class ClickXPATHAction extends Action 
{

    
  ClickXPATHAction (String ToClick, boolean BoolVal1)
    {
        this.Variable1 = ToClick;
        this.Type = "Click at XPATH";
        this.BoolVal1 = BoolVal1;
        this.Loopable = true;
    }
  @Override
    public void RunAction(WebDriver driver)
    {
        
 if (this.BoolVal1.equals(true))
{
     RightClickCatchAction(driver, this.Variable1);
 }
 else
 {
 ClickCatchAction(driver, this.Variable1);
 }
    }
    @Override
    public void RunDataLoopAction (WebDriver driver, JTable dataLoopTable)
    {
    DataLoopVarParser var1Parser = new DataLoopVarParser(this.Variable1);
    if (!var1Parser.hasDataLoopVar)
    {
        RunAction(driver);
    }
    else
    {
      int number_of_fields = var1Parser.DataLoopVars.size();
      String concat_variable1="";
      int number_of_rows = dataLoopTable.getRowCount();
      for (int y=0; y<number_of_rows; y++)
      {
      for (int x=0; x<number_of_fields; x++)
      {
       DataLoopVarHelper theseDataLoopVars = var1Parser.DataLoopVars.get(x);
       concat_variable1+= theseDataLoopVars.beforevar;
       if (theseDataLoopVars.field_column_index!=-1)
       {
           concat_variable1+=dataLoopTable.getValueAt(y, theseDataLoopVars.field_column_index);
         
       }
       concat_variable1+=theseDataLoopVars.aftervar;
      }
    
      }
      if (this.BoolVal1.equals(true))
{
     RightClickCatchAction(driver, concat_variable1);
 }
 else
 {
 ClickCatchAction(driver, concat_variable1);
 }
    }
    }
}