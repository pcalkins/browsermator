/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.util.ArrayList;

/**
 *
 * @author Pete
 */
public class DataLoopVarParser {
 
 Boolean hasDataLoopVar = false;   
 ArrayList<DataLoopVarHelper> DataLoopVars;
 
 DataLoopVarParser(String textfieldvalue)
 {
     DataLoopVars = new ArrayList();
     
   if (!textfieldvalue.contains("[dataloop-field-start]"))
   {
       hasDataLoopVar = false;
   }
   else
   {
   hasDataLoopVar = true;  
  
   int end_fieldcount = 0;

    if (textfieldvalue.contains("[dataloop-field-start]") && textfieldvalue.contains("[dataloop-field-end]"))   
    {
        String[] split_testfield_end = textfieldvalue.split("\\[dataloop\\-field\\-end\\]");
    
      
        end_fieldcount = split_testfield_end.length;
   
      //  DataLoopVars = new DataLoopVarHelper[end_fieldcount];
       
        for (int x=0; x<end_fieldcount; x++)
        {
         DataLoopVarHelper FieldData = new DataLoopVarHelper();
            String thesevalues;
            if (split_testfield_end[x].contains("[dataloop-field-start]"))
            {
           
       int startfield_index = split_testfield_end[x].indexOf("[dataloop-field-start]");
       
            thesevalues = split_testfield_end[x].substring(startfield_index+22);
            if (startfield_index==0)
            {
            String[] data_values = thesevalues.split(",");
            FieldData.varnumber = Integer.parseInt(data_values[0]);
            FieldData.field_column_index = Integer.parseInt(data_values[1]);
            if (data_values.length>2)
            {
            FieldData.field_column_name = data_values[2];
            }
            else
            {
             FieldData.field_column_name = "";   
            }
           
            }
            else
            {
             FieldData.beforevar = split_testfield_end[x].substring(x, startfield_index); 
             thesevalues = split_testfield_end[x].substring(startfield_index+22);
             String[] data_values = thesevalues.split(",");
            FieldData.varnumber = Integer.parseInt(data_values[0]);
            FieldData.field_column_index = Integer.parseInt(data_values[1]);
            if (data_values.length>2)
            {
            FieldData.field_column_name = data_values[2];
            }
            else
            {
             FieldData.field_column_name = "";   
            }
          
            }
            
            }
          else
            {
        
            FieldData.aftervar = split_testfield_end[x];
            
            }
         DataLoopVars.add(FieldData);
        }
    }
    
    else
    {
        System.out.println("not finding field");
    }
   }
 }
 public String GetFullValue(int row_number, MyTable my_Table)
 {
     String concat_variable = "";
       for (int y=0; y<DataLoopVars.size(); y++)
      {
       
       DataLoopVarHelper theseDataLoopVars = this.DataLoopVars.get(y);
       concat_variable+= theseDataLoopVars.beforevar;
       if (theseDataLoopVars.field_column_index!=-1)
       {
           concat_variable+= my_Table.DataTable.getValueAt(row_number, theseDataLoopVars.field_column_index);
         
       }
       concat_variable+=theseDataLoopVars.aftervar;
      }
       return concat_variable;
 }
}
