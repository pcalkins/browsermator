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
Boolean Locked;
int limit;
CSVReader CSVFileReader;
int number_of_columns;
int number_of_records;
Boolean random;
String DataLoopSource;
   ArrayList<Action> ActionsList = new ArrayList();    
  String URLListName;
   Procedure ()
   {
       this.limit = 0;
       this.Locked = false;
       this.random = false;
this.Pass = false;
this.BugURL = "https://www.browsermator.com";
this.BugTitle = "";
this.index = 0;
this.TargetBrowser = "Firefox";
this.DataSet=null;
this.DataFile="";
this.Type = "";
this.DataLoopSource = "urllist";
this.URLListName = "placeholder";

   }
          public void Disable()
       {

    for (Action ACT: ActionsList)
    {
        ACT.Locked = true;
       
    }
       }
       public void Enable()
       {
  for (Action ACT: ActionsList)
    {
        ACT.Locked = false;
       
    }
       }
   public void setLocked(Boolean lockedstate)
   {
     Locked = lockedstate;
   }
   public Boolean getLocked()
   {
       return Locked;
   }
   public void setProcedureTitle(String title)
   {
       this.BugTitle = title;
   }
    public void setDataFile(String dataFile)
   {
       DataFile = dataFile;
       if (!"placeholder".equals(DataFile))
       {   DataLoopSource = "file";
           DataSet = new MyTable(DataFile);
       URLListName = dataFile;
       }
   }
  public void setType(String type)
  {
      this.Type = type;
  }
   
}