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
List<String[]> RunTimeFileSet;
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
//  String[] URLListRunTimeEntries;
  
 
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
this.DataSet = new ArrayList(); 
this.RunTimeFileSet = new ArrayList();
this.DataFile="";
this.Type = "";
this.DataLoopSource = "urllist";
this.URLListName = "";
this.URLListData = new String[0];


   }
   public void RefreshFileData()
   {
   RunTimeFileSet.clear();
  setDataFile(DataFile);
   }
   public void RefreshURLListData()
   {
    URLListData = new String[0];
      setURLListData(URLListData, URLListName);
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
         System.out.println("Exception reading csv file: 102 procedure" + ex.toString());
     }
       return return_array;
    }
   public void setRunTimeFileSet(List<String[]> in_set)
   {
       this.RunTimeFileSet = in_set;
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
   public String getPassText()
   {
       String passtext = "has Failed";
       if (this.Pass)
       {
           passtext = "has Passed";
       }
       return passtext;
   }
   public void setRandom(boolean in_random)
   {
       this.random = in_random;
   }
   public void setLimit(int in_limit)
   {
       this.limit = in_limit;
   }
   public void setDataLoopSource(String in_looptype)
     {
         this.DataLoopSource = in_looptype;
     }
  public void setBugTitle(String in_title)
  {
      BugTitle = in_title;
  }  
  public void setBugURL(String in_URL)
  {
      BugURL = in_URL;
  }
  public String getBugTitle()
  {
      return BugTitle;
  }
  public void setType(String type)
  {
      this.Type = type;
  }
  public int getLimit()
  {
      return limit;
  }
  public boolean getRandom()
  {
      return random;
  }
   
}