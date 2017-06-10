package browsermator.com;


import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Procedure {
  
  

Boolean Pass;
String BugURL;
String BugTitle;
int index;
String TargetBrowser;
List<String[]> DataSet;
List<String[]> RunTimeDataSet;
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
  String[] URLListData;
 
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
this.URLListData = new String[0];

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
           DataSet = CreateArrayListFromFile(DataFile);
          }
           
   }
    public List<String[]> CreateArrayListFromFile(String in_file)
    {
      List<String[]> return_array = new ArrayList();
         try
     {
      CSVFileReader = new CSVReader(new FileReader(DataFile), ',', '"', '\0');
             return_array = CSVFileReader.readAll();   
     }
     catch(Exception ex)
     {
         System.out.println("Exception reading csv file: " + ex.toString());
     }
       return return_array;
    }
   public void setRunTimeDataSet(List<String[]> in_set)
   {
       this.RunTimeDataSet = in_set;
   }
   public void setURLListName(String in_name)
   {
       URLListName = in_name;
   }
   public void setURLListData(String[] in_list, String list_name)
   {
       URLListData = in_list;
       URLListName = list_name;
   }
   public void setDataLoopSource(String in_looptype)
     {
         this.DataLoopSource = in_looptype;
     }
   
  public void setType(String type)
  {
      this.Type = type;
  }
   
}