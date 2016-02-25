/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import com.opencsv.CSVReader;
import java.awt.Dimension;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author pcalkins
 */
public class MyTable {
   CSVReader CSVFileReader;
   int number_of_columns;
   int number_of_records;
   String DataFile;
   JTable DataTable;
   Object[] columnnames;
   

 MyTable (String csvFile)
 {
        DataFile = csvFile;
        DataTable = new JTable();
       
               
   try {
       
       CSVFileReader = new CSVReader(new FileReader(csvFile), ',', '"', '\0');
         
           List myEntries = CSVFileReader.readAll();
          columnnames = (String[]) myEntries.get(0);
          DefaultTableModel tableModel = new DefaultTableModel(columnnames, myEntries.size()-1); 
           int rowcount = tableModel.getRowCount();
          for (int x = 0; x<rowcount+1; x++)
           {
              int columnnumber = 0;
             if (x>0)
             {
           for (String thiscellvalue : (String[])myEntries.get(x))
           {
               tableModel.setValueAt(thiscellvalue, x-1, columnnumber);
              columnnumber++;
           }
             }
   
          
           }
        
   
           DataTable = new JTable(tableModel);
     
  //     DataTable.setAutoCreateRowSorter(true);
  //              TableRowSorter sorter = (TableRowSorter) DataTable.getRowSorter();
  //              sorter.setRowFilter(new RowFilter<TableModel, Integer>() {
  //                  @Override
  //                  public boolean include(RowFilter.Entry<? extends TableModel, ? extends Integer> entry) {
  //                      boolean included = true;
  //                      int number_of_columns = DataTable.getRowCount();
  //                      int number_of_empties = 0;
   //                     for (int x=0; x<number_of_columns; x++)
    //                    {
    //                    Object cellValue = entry.getModel().getValueAt(entry.getIdentifier(), x);
    //                    if (cellValue == null || cellValue.toString().trim().isEmpty()) {
    //                    number_of_empties++;
    //                    }
    //                    }
    //                    if (number_of_empties == number_of_columns)
     //                   {
     //                   included = false;
     //                   }
                        
      //                  return included;
      //              }
        //        });      
   
        int number_of_rows = DataTable.getRowCount();
        if (number_of_rows < 20)
        {
        DataTable.setPreferredScrollableViewportSize(new Dimension (1200, number_of_rows * DataTable.getRowHeight()));
        }
        } catch (Exception ex) {
         System.out.println(ex.toString());
         
       }
 }
}
