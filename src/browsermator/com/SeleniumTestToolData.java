/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import com.opencsv.CSVReader;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Pete
 */
public class SeleniumTestToolData {
    transient CSVReader CSVFileReader;
ArrayList<Procedure> BugArray = new ArrayList<>();
    ArrayList<String> AllFieldValues;
    ArrayList<String> Visitted_URL_List;
      Thread ActionThread;
  Boolean AllTestsPassed;
  Boolean ShowReport;
  Boolean EmailReport;
  Boolean EmailReportFail;
  Boolean ExitAfter;
  Boolean PromptToClose;
  Boolean changes;
  String TargetBrowser;
  Boolean MultiSession;
  Boolean TemplateOrNew;

  String OSType;
  int WaitTime;
  int Sessions;
  boolean hasStoredVar;
  boolean hasStoredArray;
  boolean IncludeScreenshots;
  Map<String, String> VarHashMap = new HashMap();
  Map<String, List<String>> VarLists = new HashMap();
  
  boolean cancelled;
  boolean testRunning;
  String short_filename;
  int Timeout;
  LocalDateTime TimeOfRun;
  boolean UniqueList;
  String UniqueOption;
  String URL;
String filename;
String EmailPassword;
String EmailTo;
String EmailFrom;
String EmailSubject;
String SMTPHostName;
String EmailLoginName;
Boolean silentMode;

public final String UNIQUE_LOG_DIR;
Map<String, List<String[]>> DataFileHashMap = new HashMap();
String BrowsermatorAppFolder;
     boolean hasSentStoredVars;
     int  EcTimeout;
     
public SeleniumTestToolData (ArrayList<Procedure> BugArray)
        {
     this.hasSentStoredVars = false;
  
         BrowsermatorAppFolder =   System.getProperty("user.home")+File.separator+"BrowsermatorAppFolder"+File.separator;
  
UNIQUE_LOG_DIR = BrowsermatorAppFolder + "BrowsermatorUniqueLogFolder" + File.separator;
           this.TemplateOrNew = false;
            this.BugArray = BugArray;
                this.UniqueOption = "file";
      this.UniqueList = false;
      this.MultiSession = false;
      this.cancelled = false;
    this.hasStoredVar = false;
    this.hasStoredArray = false;
   this.VarHashMap = new HashMap();
  this.TargetBrowser = "Chrome";
  this.OSType = "Windows32";
  this.Sessions = 1;
  this.changes = false;
  this.PromptToClose = false;
  this.ShowReport = false;
  this.filename = "";
  this.short_filename=filename;
  this.URL = "";
  this.IncludeScreenshots = false;
  this.testRunning = false;
  this.AllFieldValues = new ArrayList<>();
  this.Visitted_URL_List = new ArrayList<>();
   this.EmailReport = false;
  this.EmailReportFail = false;
  this.ExitAfter = false;
  this.AllTestsPassed = false;
this.EmailPassword = "";
this.EmailTo = "";
this.EmailFrom = "";
this.EmailSubject = "";
this.SMTPHostName = "";
this.EmailLoginName = "";
this.silentMode = false;
this.EcTimeout = 10;

    try{
      loadGlobalEmailSettings();
  }
      catch (Exception e) {
	System.out.println("Exception on loading email settings: " + e);
    }

        }
public void setEcTimeout (int _timeout)
{
   EcTimeout = _timeout;
}
public int getEcTimeout ()
{
   
    return EcTimeout;
}
public void setSilentMode(Boolean modeset)
{
 
        silentMode = modeset;
}
public void setHasStoredVar(boolean hasit)
{
    this.hasStoredVar=hasit;
}
public void setHasStoredArray(boolean hasit)
{
    this.hasStoredArray=hasit;
}

public void initVarHashMap()
{
    this.VarHashMap = new HashMap();
}
public void setIsTemplateOrNew(boolean is_template_or_new)
{
  this.TemplateOrNew = is_template_or_new;
}
public boolean getIsTemplateOrNew()
{
    return TemplateOrNew;
}
public void initVarLists()
{
 this.VarLists = new HashMap(); 
 VarLists.put("MASTER", new ArrayList<>());
}
public Map<String, String> getVarHashMap()
{
    return VarHashMap;
}
public Map<String, List<String>> getVarLists()
{
   return VarLists;    
}
 public void DeleteBug (int BugIndex)
   {
   for (BMAction A: BugArray.get(BugIndex-1).ActionsList)
   {
       if ("Store Link as Variable by XPATH".equals(A.Type))
       {
          VarHashMap.remove(A.Variable2);
       }
       if ("Store Links as URL List by XPATH".equals(A.Type))
       {
           VarLists.remove(A.Variable2);
       }
   }
 
  BugArray.remove(BugIndex-1);
  ResetBugIndexes();
  
changes=true;
   }
   public void ResetBugIndexes()
   {
       int newindex = 1;
       for (Procedure proc: BugArray)
       {
           proc.setIndex(newindex);
           newindex++;
       }
   }
   public void DeleteAction (Procedure thisBug, int atIndex)
   {
    
    thisBug.ActionsList.remove(atIndex);
   

  for(int BugIndex=0; BugIndex<thisBug.ActionsList.size(); BugIndex++)
     {
     if (BugIndex>=atIndex)
     {
     thisBug.ActionsList.get(BugIndex).index--;
   
     }
     }
 

 changes=true;
   }
 public void clearEmailSettings ()
 {
     setSMTPHostname("");
     setEmailLoginName("");
     setEmailPassword("");
     setEmailTo("");
     setEmailFrom("");
     setSubject("");
     
 }
   public void MoveAction (Procedure thisBug, int toMoveIndex, int Direction)
   {

    int SwapIndex = toMoveIndex + Direction;
    if (Direction == 1)
       {
                if (toMoveIndex<thisBug.ActionsList.size()-1)
     {


  Collections.swap(thisBug.ActionsList, toMoveIndex, SwapIndex);
   thisBug.ActionsList.get(toMoveIndex).setActionIndex(toMoveIndex);
  thisBug.ActionsList.get(SwapIndex).setActionIndex(SwapIndex);
    


      
  
    }

       }
       if (Direction == -1)
       {
     if (toMoveIndex!=0)
    {
    Collections.swap(thisBug.ActionsList, toMoveIndex, SwapIndex);
  thisBug.ActionsList.get(toMoveIndex).setActionIndex(toMoveIndex);
  thisBug.ActionsList.get(SwapIndex).setActionIndex(SwapIndex);


       }
   }

       this.changes=true;
   }
 
         
    public List<String[]> CreateArrayListFromFile(String DataFile)
    {
      List<String[]> return_array = new ArrayList<>();
      
    
             File checkPath = new File(DataFile);
      if (checkPath.exists())
      {
  
     
      try
     {
      CSVFileReader = new CSVReader(new FileReader(DataFile), ',', '"', '\0');
             return_array = CSVFileReader.readAll();   
     }
     catch(Exception ex)
     {
         System.out.println("Exception reading csv file: 122 procedure" + ex.toString());
     }
      }
       return return_array;
    }   
  public void addDataFileToDataFileHashMap(String path_to_file)
{
    List<String[]> DataSet = new ArrayList<>();
    DataSet = CreateArrayListFromFile(path_to_file);
   DataFileHashMap.put(path_to_file, DataSet);
}
  public List<String[]> getDataSetByFileName(String filename)
  {
      List<String[]> DataOut = new ArrayList<String[]>();
      if (DataFileHashMap.containsKey(filename))
      {
          DataOut = DataFileHashMap.get(filename);
      }
      else
      {
          File checkfile = new File(filename);
          if (checkfile.exists())
          {
              addDataFileToDataFileHashMap(filename);
              DataOut = DataFileHashMap.get(filename);
          }
      }
 
      return DataOut;
  }
  
          public void addSelectedVariableName(String varname)
        {
       
        VarHashMap.put(varname, "");
        
       }
    
    public void updateSelectedVariableName(String oldname, String newname)
      {
      
          
        if (VarHashMap.containsKey(newname))
        {       
 
            VarHashMap.put(newname, "");
            VarHashMap.put(oldname, "");
        }
        else
        {
            VarHashMap.remove(oldname);
            VarHashMap.put(newname, "");
    
        }
 
       
      }
    public String GetStoredVariableValue(String fieldname)
{
    String ret_val = "";
if (VarHashMap.containsKey(fieldname))
{
    ret_val = VarHashMap.get(fieldname);
}

return ret_val;
}
public void SetSentStoredVariableName(String varname)
{
   VarHashMap.put(varname, "");
   hasSentStoredVars = true;
}
public void SetStoredVariableName(String varname)
{
    VarHashMap.put(varname, "");  
 
    
}
public void SetStoredVariableValue (String varname, String varval)
{

    if (VarHashMap.containsKey(varname))
    {
        VarHashMap.put(varname, varval);
    }

}

public void setAllFieldValues(ArrayList<String> allfieldvalues)
{
    this.AllFieldValues = allfieldvalues;
}
      public final void loadGlobalEmailSettings() throws IOException 
 {
     Properties applicationProps = new Properties();
   
try
{
         try (FileInputStream input = new FileInputStream(BrowsermatorAppFolder + "browsermator_config.properties")) {
             applicationProps.load(input);
         }
         catch (Exception e)
         {
             System.out.println(e);
         }
}
catch (Exception e) {
			System.out.println("Exception loading email70: " + e);
		} 

   String smtp_hostname = applicationProps.getProperty("smtp_hostname");
   String login_name = applicationProps.getProperty("email_login_name");
   String password = applicationProps.getProperty("email_login_password");
   String to = applicationProps.getProperty("email_to");
   String from = applicationProps.getProperty("email_from");
   String subject = applicationProps.getProperty("email_subject");
 
   setSMTPHostname(smtp_hostname);
   setEmailLoginName(login_name);
   try
   {
   password = Protector.decrypt(password);
   }
   catch (Exception ex)
   {
       System.out.println("Exception getting email password: " + ex.toString());
   }
   setEmailPassword(password);
   setEmailTo(to);
   setEmailFrom(from);
   setSubject(subject);
   
        
	}
       public void ClearEmailSettings ()
 {
     setSMTPHostname("");
     setEmailLoginName("");
     setEmailPassword("");
     setEmailTo("");
     setEmailFrom("");
     setSubject("");
     
 }
      
    public void setEmailPassword(String password)
    {
        this.EmailPassword = password;
    }
    public void setEmailTo(String to)
    {
        this.EmailTo = to;
    }
     public void RefreshData()
 {
     for (Procedure proc: this.BugArray)
     {
         if ("Dataloop".equals(proc.Type))
         {
             if ("file".equals(proc.DataLoopSource))
             {
         proc.RefreshFileData();
             }
             else
             {
           proc.RefreshURLListData();
             }
         }
     }
 }
    public String getTargetBrowser()
    {
        return TargetBrowser;
    }
    public void setEmailFrom(String from)
    {
        this.EmailFrom = from;
    }
    public void setSubject(String subject)
    {
        this.EmailSubject = subject;
    }
    
    public void setSMTPHostname(String smtp_hostname)
    {
        this.SMTPHostName = smtp_hostname;
    }
    public void setEmailLoginName(String login_name)
    {
        this.EmailLoginName = login_name;
    }
    
    public int getWaitTime()
    {
        return WaitTime;
    }
    
    public int getTimeout()
    {
        return Timeout;
    }
    public int getSessions()
    {
        return Sessions;
    }
    public String getSMTPHostname()
    {
        return SMTPHostName;
    }
    public String getEmailFrom()
    {
        return EmailFrom;
    }
    public String getEmailLoginName()
    {
        return EmailLoginName;
    }
    public String getEmailPassword()
    {
        return EmailPassword;
    }
    public String getEmailTo()
    {
        return EmailTo;
    }
    public String getEmailSubject()
    {
        return EmailSubject;
    }
    public String getFilename()
    {
        return filename;
    }
    public String getShortFilename()
    {
        return short_filename;
    }
      public void setFilenames (String filename)
    {
    Path thisP = Paths.get(filename);
    
    
String shortname = thisP.getFileName().toString();
if (shortname.length()>1)
{
  int end_of_name = shortname.indexOf(".browsermation");
  
if (end_of_name<1)
{
    end_of_name=shortname.length();

}

   this.short_filename = shortname.substring(0, end_of_name );
}
else
{
    this.short_filename = shortname;
}


  
    this.filename = filename;
    
    }
  public boolean getEmailReportFail()
  {
      return EmailReportFail;
  }
  public boolean getExitAfter()
  {
      return ExitAfter;
  }
  public void setPromptToClose(boolean closeit)      
  {
     PromptToClose = closeit; 
  }
  public boolean getPromptToClose()
  {
      return PromptToClose;
  }
  public boolean getShowReport()
  {
      return ShowReport;
  }
  public boolean getIncludeScreenshots()
  {
  return IncludeScreenshots;
  }
  public boolean getUniqueList()
  {
      return UniqueList;
  }
  public boolean getEmailReport()
  {
      return EmailReport;
  }
  
           public void setShowReport (Boolean showreport)
    {
        this.ShowReport = showreport;
      
        if (this.ShowReport==false)
        {
    
    
      this.IncludeScreenshots=false;   
        }
        else
        {
   
        this.EmailReportFail = false;
        
     
        this.EmailReport = false;
      
      
        this.ExitAfter = false;
        }
    }
           
             public void setExitAfter (Boolean exitafter)
    {
        this.ExitAfter = exitafter;
   
        if (this.ExitAfter)
        {
      
      this.ShowReport = false;
        }
    }
         public String getUniqueFileOption()
  {
      return UniqueOption;
  }
         public void setUniqueFileOption(String option)
  {
      if (option.equals("file"))
      {
     
       UniqueOption = option; 
         
      }
      if (option.equals("global"))
      {
       
         UniqueOption = option;
         
      }
      
      
  }
           public void setIncludeScreenshots(Boolean includescreenshots)
    {
        this.IncludeScreenshots = includescreenshots;
      
    }
 
    public void setEmailReport (Boolean emailreport)
    {
     
        this.EmailReport = emailreport;
        if (emailreport==true)
        {
     
      this.IncludeScreenshots=false;   
       
            this.ShowReport = false;
               
      this.IncludeScreenshots=false;   
       
        }
     
    }
    
        public void setEmailReportFail (Boolean emailreportfail)
    {
        this.EmailReportFail = emailreportfail;
   
         if (emailreportfail==true)
        {
       
      this.IncludeScreenshots=false;   
   
            this.ShowReport = false;
       
            this.EmailReport = false;
        }
         else
         {
      
         this.EmailReportFail = false;
         
         }
    }
        public void setTargetBrowser (String targetbrowser)
        {   
            if (!silentMode)
            {
            //legacy stuff
            if ("Firefox-Marionette".equals(targetbrowser))
            {
                targetbrowser = "Firefox";
            }
            
        
            this.TargetBrowser = targetbrowser;
            
            }
            else
            {
             for (Procedure thisProc: BugArray)
             {
                for (BMAction thisAct: thisProc.ActionsList) 
                {
                    if (thisAct.Type.equals("Switch Driver"))
                    {
                        thisAct.Variable1 = targetbrowser;
                    }
                }
                    }
            }
         
        }
          public void AddDataLoopProc(Procedure newdataloop)
        {
              BugArray.add(newdataloop);   
       int thissize = BugArray.size();
       BugArray.get(thissize-1).setIndex(thissize);
      
      
        } 
       public void AddDataLoopProc(Procedure newdataloop, int atindex)
        {
            atindex--;
              BugArray.add(atindex, newdataloop);   
       ResetBugIndexes();

        } 
       
         public void AddNewBug()
        {
         Procedure newbug = new Procedure();
         newbug.setType("Procedure");
   
         BugArray.add(newbug);
         int thissize = BugArray.size();
        BugArray.get(thissize-1).setIndex(thissize);

        }
        public void AddNewBug(int atindex)
        {
            atindex--;
             Procedure newbug = new Procedure();
         newbug.setType("Procedure");
          BugArray.add(atindex, newbug);
        ResetBugIndexes();
        }
              public void AddNewDataLoopURLList(String in_listname)
        {
            Procedure newdataloop = new Procedure();
      
         newdataloop.setType("Dataloop");
         
     
      
        newdataloop.setDataLoopSource("urllist");
  
        List<String> blanklist = new ArrayList<>();
  
  
       
        newdataloop.setURLListData(blanklist, in_listname);
        if (!"".equals(in_listname))
        {
        VarLists.put(in_listname, blanklist);
        }
     
   AddDataLoopProc(newdataloop);
        }    
                      public void AddNewDataLoopURLList(String in_listname, int at_index)
        {
            Procedure newdataloop = new Procedure();
      
         newdataloop.setType("Dataloop");
         
     
      
        newdataloop.setDataLoopSource("urllist");
  
        List<String> blanklist = new ArrayList<>();
  
  
       
        newdataloop.setURLListData(blanklist, in_listname);
         if (!"".equals(in_listname))
        {
        VarLists.put(in_listname, blanklist);
        }
     
   AddDataLoopProc(newdataloop, at_index);
        }    
           public void AddNewDataLoopFile(File CSVFile)
        {
        Procedure newdataloop = new Procedure();
      
         newdataloop.setType("Dataloop");
         newdataloop.setDataLoopSource("file");
     
        if (CSVFile.exists())
         {
         
            try {
                newdataloop.setDataFile(CSVFile.getCanonicalPath());
             
            } catch (IOException ex) {
               System.out.println(ex.toString());
            }
       
         
         }
         else
         {
       
         newdataloop.setDataFile("placeholder");  
        
         
         }
   AddDataLoopProc(newdataloop);
        }
                   public void AddNewDataLoopFile(File CSVFile, int at_index)
        {
        Procedure newdataloop = new Procedure();
      
         newdataloop.setType("Dataloop");
         newdataloop.setDataLoopSource("file");
     
        if (CSVFile.exists())
         {
           try {
                newdataloop.setDataFile(CSVFile.getCanonicalPath());
           
            } catch (IOException ex) {
               System.out.println(ex.toString());
            }
        
         
         }
         else
         {
       
         newdataloop.setDataFile("");  
        
         
         }
   AddDataLoopProc(newdataloop, at_index);
        }
        public String getOSType()
        {
            return OSType;
        }
        public void AddNewDataLoop()
        {
       Procedure newdataloop = new Procedure();
        newdataloop.setType("Dataloop");
        newdataloop.setDataLoopSource("none");
           
            AddDataLoopProc(newdataloop); 

        }
                public void AddNewDataLoop(int atindex)
        {
            
       Procedure newdataloop = new Procedure();
        newdataloop.setType("Dataloop");
        newdataloop.setDataLoopSource("none");
           
            AddDataLoopProc(newdataloop, atindex); 

        }
                public void RefreshActionIndexes (ArrayList<BMAction> actionlist)
                {
                  for (int x=0; x<actionlist.size(); x++)
                  {
                      actionlist.get(x).index = x;
                  }
                }
           public void AddActionToArray(BMAction action, Procedure newbug, ProcedureView newbugview)
           {
                        int at_index = newbugview.getJComboBoxAddAtPosition();
             if (at_index>0) {at_index--;}
           newbug.ActionsList.add(at_index, action);
            action.index = at_index;
            RefreshActionIndexes(newbug.ActionsList);
               if ("Store Links as URL List by XPATH".equals(action.Type))
           {
                    String stringactionindex = Integer.toString(action.index+1);
        String stringbugindex = Integer.toString(newbug.index);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
              addSelectedArrayName(bugdashactionindex);
           
           }
                        if ("Store Link as Variable by XPATH".equals(action.Type))
           {
                    String stringactionindex = Integer.toString(action.index+1);
        String stringbugindex = Integer.toString(newbug.index);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
             addSelectedVariableName(bugdashactionindex);
            
           
           }   
            
           }
             public void AddActionToArray (BMAction action, Procedure newbug)
{
   
            newbug.ActionsList.add(action);
           
            action.index = newbug.ActionsList.size()-1;
               if ("Store Links as URL List by XPATH".equals(action.Type))
           {
                    String stringactionindex = Integer.toString(action.index+1);
        String stringbugindex = Integer.toString(newbug.index);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
              addSelectedArrayName(bugdashactionindex);
           
           }
                        if ("Store Link as Variable by XPATH".equals(action.Type))
           {
                    String stringactionindex = Integer.toString(action.index+1);
        String stringbugindex = Integer.toString(newbug.index);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
             addSelectedVariableName(bugdashactionindex);
           
           }
        
}
         public void setSessions (int number_of_sessions)
   {
       this.Sessions = number_of_sessions;
    
      
      
     
   }
        public void setWaitTime (int timeout_seconds)
    {   
    
        this.WaitTime = timeout_seconds;

      
    }
   public void addSelectedArrayName(String varname)
      {
 
 VarLists.put(varname, new ArrayList<>());
 
      }
 
 
         public void setOSType(String in_OSType)
        {
        
           switch (in_OSType)
            {
                case "Windows":
                  OSType = "Windows32";
                    break;
                    
                case "Windows32":
              OSType = "Windows32";
                break;
                case "Windows64":
              OSType = "Windows64";
                break;
                case "Mac":
               OSType = "Mac";
                break;
                case "Linux-32":
               OSType = "Linux-32";
                break;
                case "Linux-64":
              OSType = "Linux-64";
                break;
           }   
            this.OSType = OSType;
          
                
        }
           public List<String[]> RandomizeAndLimitFileList(List<String[]> data_REF, int limit, Boolean randval)
  {
    // first row is column names, remove it
      List<String[]> ret_val = new ArrayList<>();
      List<String[]> data_in = new ArrayList<>();
      
      for (String[] thisrow: data_REF)
      {
          data_in.add(thisrow);
      }
      if (data_in.size()>0)
      {
  data_in.remove(0);
  
             if (randval)
                {
       
Collections.shuffle(data_in);
                }
                if (limit>0)
                {
                    
                    int sizeofvarlist = data_in.size();

 
   data_in.subList(limit, sizeofvarlist).clear();
                }
            
     ret_val = data_in;
    
      }
       return ret_val;
  }
           public void setUniqueList(boolean unique)
 {
     this.UniqueList = unique;
  
     setUniqueFileOption(UniqueOption);
 }
             public List<String> RandomizeAndLimitURLList(String URLListName, int limit, Boolean randval)
  {
    
      List<String> currentlist = new ArrayList<>();
   if (VarLists.containsKey(URLListName))
            {
                 currentlist = new ArrayList<>(VarLists.get(URLListName));
       
          if (currentlist.size()>0)
          {
          if (this.UniqueList)
{
   
   String file_name_to_check = "global";
       if (UniqueOption.equals("file"))
       {
           file_name_to_check = this.short_filename;
       }
       
        String visited_list_file_path = UNIQUE_LOG_DIR + "browsermator_" + file_name_to_check + "_visited_url_log.xml";
        
         File file = new File(visited_list_file_path);
         if (file.exists())
         {
                   Document doc=null;
try
{

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
String file_path = file.getAbsolutePath();

doc = builder.parse(file_path);
 


List<String> removeList = new ArrayList<>();
  
for (String grabbedURL: currentlist)
{   
     
    
    
    NodeList theseElements = doc.getElementsByTagName("URLsVisited");
   NodeList SettingsNodes = theseElements.item(0).getChildNodes();
String URL_FROM_FILE="";
Boolean hasValue = false;

    for (int k = 0; k<SettingsNodes.getLength(); k++)
    {
          URL_FROM_FILE = SettingsNodes.item(k).getTextContent();
  // NamedNodeMap Times = doc.getElementsByTagName("URL").item(0).getAttributes(); 
     if (URL_FROM_FILE.equals(grabbedURL))
     {
      removeList.add(grabbedURL);
     }
     else
     {
     
     }
    }

  Map<String, List<String>> newMap = VarLists;
   newMap.remove(URLListName);
   
   for (List<String> thisList: newMap.values())
              {
                
                 for(String thisString: thisList)
                 {
                     
                    if (thisString.equals(grabbedURL))
                            {
                                if (!removeList.contains(grabbedURL))
                                {
                                    removeList.add(grabbedURL);
                                }     
                            }
                 }
              }
   }

    currentlist.removeAll(removeList);

// VarLists.get(URLListName).clear();
// VarLists.replace(URLListName, currentlist);

}
catch (Exception ex)
{
  System.out.println("Exception with unique log: " + ex.toString());  
}
         }
}
       
                if (randval)
                {
         

Collections.shuffle(currentlist);


 
                }
                if (limit>0)
                {
                    int sizeofvarlist = currentlist.size();

 if (limit<sizeofvarlist)
 {
 //    convert_list = currentlist;
   if (currentlist.size()<limit)
   {
    
   }
   else
   {
     currentlist.subList(limit, sizeofvarlist).clear();
   }
 // currentlist = convert_list;
   return currentlist;
 }
else
 {
  
   return currentlist;
 }

 }
   return currentlist;        
            }   
          return currentlist;
            }
   return currentlist;
  }
 public void AddURLToUniqueFileList(String thisURL)
 {
     Visitted_URL_List.add(thisURL);
   
 }
 public void ClearVisittedURLList()
 {
     Visitted_URL_List.clear();
 }
 public void AddURLListToUniqueFile(String fileOption)
 {
      
     String file_name_to_write = "global";
      
        if (fileOption.equals("file"))
        {
        file_name_to_write = this.short_filename;
        }
        String visited_list_file_path = UNIQUE_LOG_DIR + "browsermator_" + file_name_to_write + "_visited_url_log.xml";
         File file = new File(visited_list_file_path);
         if (file.exists())
         {
                   Document doc=null;
try
{

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
String file_path = file.getAbsolutePath();

doc = builder.parse(file_path);
for (int x=0; x<Visitted_URL_List.size(); x++)
 {
     String URL_To_Store = Visitted_URL_List.get(x);
     
NodeList theseElements = doc.getElementsByTagName("URLsVisited");
   NodeList SettingsNodes = theseElements.item(0).getChildNodes();

String URL_FROM_FILE="";
Boolean hasValue = false;
int node_match_index = 0;
    for (int k = 0; k<SettingsNodes.getLength(); k++)
    {
  
   URL_FROM_FILE = SettingsNodes.item(k).getTextContent();
 
    
   
   if (URL_To_Store.equals(URL_FROM_FILE))
   {
    hasValue = true; 
    node_match_index = k;
   }
}
 if (!hasValue)
 {
      if (!"".equals(URL_To_Store))
      {
  Node root = doc.getFirstChild();

Element new_url = doc.createElement("URL");
new_url.setTextContent(URL_To_Store);
root.appendChild(new_url);

      }
   
 }
 }
            try
             {
   XMLStreamWriter xmlfile = XMLOutputFactory.newInstance().createXMLStreamWriter( new BufferedOutputStream(
                        new FileOutputStream(file)));
     
             try {
xmlfile.writeStartElement("URLsVisited");
NodeList urls_to_write = doc.getElementsByTagName("URL");

    for (int k = 0; k<urls_to_write.getLength(); k++)
    {

      
   String thisSettingsNodeValue = urls_to_write.item(k).getTextContent();

          xmlfile.writeStartElement("URL");
      
    xmlfile.writeCharacters(thisSettingsNodeValue);
  
    xmlfile.writeEndElement();
 }
    xmlfile.writeEndElement();    
     } catch (Exception e) {
           System.out.println("Write error:" + e.toString());
 
        } finally {
            xmlfile.flush();
            xmlfile.close();
} 
             }
             catch (Exception e) {
           System.out.println("Write error:" + e.toString());
 
        }

}
catch (Exception e)
{
    System.out.println("DocumentBuilder error(seleniumtesttoolline1039):" + e.toString());
    e.printStackTrace();
   
}
         }
         else
         {
             try
             {
   XMLStreamWriter xmlfile = XMLOutputFactory.newInstance().createXMLStreamWriter( new BufferedOutputStream(
                        new FileOutputStream(file)));
     
             try {
xmlfile.writeStartElement("URLsVisited");

 for (String URL_To_Store: Visitted_URL_List)
 {
        xmlfile.writeStartElement("URL");
 
    xmlfile.writeCharacters(URL_To_Store);
  
    xmlfile.writeEndElement();
 }
    xmlfile.writeEndElement();    
     } catch (Exception e) {
           System.out.println("Write error:" + e.toString());
 
        } finally {
            xmlfile.flush();
            xmlfile.close();
} 
             }
             catch (Exception e) {
           System.out.println("Write error:" + e.toString());
 
        }
         }

         
 }
 
 public void PromptForUserVarValues()
 {
         int counter = 0;
        
         UserSingleInputPanel[] UserSingleInputPanels;
         
    UserSingleInputPanels = new UserSingleInputPanel[VarHashMap.size()];
    for (Map.Entry<String, String> entry : VarHashMap.entrySet()) {
             String key = entry.getKey();
             
           
       
              UserSingleInputPanel thisUserSingleInputPanel = new UserSingleInputPanel(key);
               thisUserSingleInputPanel.CreatePanel();
             UserSingleInputPanels[counter] = thisUserSingleInputPanel;
            
          
           
            counter++;
          }
    
     UserSingleInputFrame newInputFrame = new UserSingleInputFrame(UserSingleInputPanels, VarHashMap);
      
       newInputFrame.InitializeFrame();
       
        newInputFrame.addLastPanelActionListener();
       while(newInputFrame.isVisible() == true){
        try
        {
Thread.sleep(200);

        }
        catch (InterruptedException e)
                {
                    System.out.println("pause exception: " + e.toString());
                    
                }
       }
 }
}
