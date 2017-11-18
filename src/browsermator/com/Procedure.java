package browsermator.com;


import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class Procedure implements Serializable {
  
  

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
transient CSVReader CSVFileReader;
int number_of_columns;
int number_of_records;
Boolean random;
String DataLoopSource;
   ArrayList<Action> ActionsList = new ArrayList();    
  String URLListName;
  String[] URLListData;
 // String[] URLListRunTimeEntries;

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
   public void setIndex(int newindex)
   {
       this.index = newindex;
   }
   public void RefreshFileData()
   {
   RunTimeFileSet.clear();
 // DataSet =  setDataFile(DataFile);
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
       {  
// legacy web stuff...   
//          if (dataFile.contains("http")&& dataFile.contains("//"))
   //    {
   //      DataLoopSource = "file";
   //      ReturnSet = CreateArrayListFromURL(DataFile);
   //    }
             
          DataLoopSource = "file";
    
   }
   }
    public void setDataSet(List<String[]> in_dataset)
    {
        this.DataSet = in_dataset;
    }
    public List<String[]> CreateArrayListFromURL(String fileURL)
    {
     List<String[]> return_array = new ArrayList();
     
      String[] filename_parse = fileURL.split("=");
      String filename = filename_parse[filename_parse.length-1];
      
  File thisFile = new File(System.getProperty("user.home")+"\\BrowserMatorCloudFiles\\"+filename);
  
    //         if (thisFile.exists())
    //         {
    //         Boolean checkdate = CheckIfFileIsNew(file_date, file_id);
    //         if (checkdate)
    //         {
               try {
          
         
    org.apache.commons.io.FileUtils.copyURLToFile(new URL(fileURL), new File(System.getProperty("user.home")+"\\BrowserMatorCloudFiles\\"+filename));
   DataFile = System.getProperty("user.home")+"\\BrowserMatorCloudFiles\\"+filename;
  
} catch (Exception x) { System.out.println ("Exception downloading CSV file" + x.toString()); }
       try
     {
      CSVFileReader = new CSVReader(new FileReader(System.getProperty("user.home")+"\\BrowserMatorCloudFiles\\"+filename), ',', '"', '\0');
             return_array = CSVFileReader.readAll();   
     }
     catch(Exception ex)
     {
         System.out.println("Exception reading csv file: 122 procedure" + ex.toString());
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