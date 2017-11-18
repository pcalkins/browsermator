/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author pcalkins
 */
public class StoredArrayLoopVarParser {
   Boolean hasDataLoopVar = false;   
 ArrayList<DataLoopVarHelper> StoredLoopVars;
 
 StoredArrayLoopVarParser(String textfieldvalue)
 {
     StoredLoopVars = new ArrayList();
     
   if (!textfieldvalue.contains("[storedarrayloop-field-start]"))
   {
       hasDataLoopVar = false;
   }
   else
   {
   hasDataLoopVar = true;  
  
   int end_fieldcount = 0;
   int start_fieldcount = 0;
    if (textfieldvalue.contains("[storedarrayloop-field-start]") && textfieldvalue.contains("[storedarrayloop-field-end]"))   
    {
        String[] split_testfield_end = textfieldvalue.split("\\[storedarrayloop\\-field\\-end\\]");
        String[] split_testfield_start = textfieldvalue.split("\\[storedarrayloop\\-field\\-start\\]");
      
        end_fieldcount = split_testfield_end.length;
        start_fieldcount = split_testfield_start.length;
      //  DataLoopVars = new DataLoopVarHelper[end_fieldcount];
       
        for (int x=0; x<end_fieldcount; x++)
        {
         DataLoopVarHelper FieldData = new DataLoopVarHelper();
            String thesevalues;
            if (split_testfield_end[x].contains("[storedarrayloop-field-start]"))
            {
           
       int startfield_index = split_testfield_end[x].indexOf("[storedarrayloop-field-start]");
       
            thesevalues = split_testfield_end[x].substring(startfield_index+22);
            if (startfield_index==0)
            {
          
          
            
             FieldData.field_column_name = thesevalues;
    
           
            }
            else
            {
             FieldData.beforevar = split_testfield_end[x].substring(x, startfield_index); 
             thesevalues = split_testfield_end[x].substring(startfield_index+22);
            
            FieldData.field_column_name = thesevalues;
            }
          
          
            }
            
         
          else
            {
        
            FieldData.aftervar = split_testfield_end[x];
            
            }
         StoredLoopVars.add(FieldData);
        }
    }
    
    else
    {
        System.out.println("not finding field");
    }
   }
 }
 public String GetFullValue(int row_number, JTable in_Table)
 {
     String concat_variable = "";
       for (int y=0; y<StoredLoopVars.size(); y++)
      {
       
       DataLoopVarHelper theseDataLoopVars = this.StoredLoopVars.get(y);
       concat_variable+= theseDataLoopVars.beforevar;
       if (theseDataLoopVars.field_column_index!=-1)
       {
           concat_variable+= in_Table.getValueAt(row_number, theseDataLoopVars.field_column_index);
         
       }
       concat_variable+=theseDataLoopVars.aftervar;
      }
       return concat_variable;
 }
}  
