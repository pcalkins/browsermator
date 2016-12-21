package browsermator.com;


import com.opencsv.CSVReader;
import java.util.ArrayList;


public class Procedure {
  
  

Boolean Pass;
String BugURL;
String BugTitle;
int index;
String TargetBrowser;
MyTable DataSet;
String DataFile;
String Type;


CSVReader CSVFileReader;
int number_of_columns;
int number_of_records;

   ArrayList<Action> ActionsList = new ArrayList();    
  
   Procedure ()
   {
this.Pass = false;
this.BugURL = "https://www.browsermator.com";
this.BugTitle = "";
this.index = 0;
this.TargetBrowser = "Firefox";
this.DataSet=null;
this.DataFile="";
this.Type = "";

   }
  
  
   public void setProcedureTitle(String title)
   {
       this.BugTitle = title;
   }
    public void setDataFile(String dataFile)
   {
       DataFile = dataFile;
       if (!"placeholder".equals(DataFile))
       {  DataSet = new MyTable(DataFile); }
   }
  public void setType(String type)
  {
      this.Type = type;
  }
   
}