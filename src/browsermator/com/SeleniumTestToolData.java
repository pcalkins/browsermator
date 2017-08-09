/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;
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
ArrayList<Procedure> BugArray = new ArrayList<Procedure>();
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
  HashMap<String, String> VarHashMap = new HashMap();
  HashMap<String, String[]> VarLists = new HashMap();
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

    
public SeleniumTestToolData (ArrayList<Procedure> BugArray)
        {
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
    try{
      loadGlobalEmailSettings();
  }
      catch (Exception e) {
	System.out.println("Exception on loading email settings: " + e);
    }

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
}
public HashMap<String, String> getVarHashMap()
{
    return VarHashMap;
}
public HashMap<String, String[]> getVarLists()
{
   return VarLists;    
}
 public void DeleteBug (int BugIndex)
   {
   for (Action A: BugArray.get(BugIndex-1).ActionsList)
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
 public void UpdateDataLoopURLListTable(String ListName, String[] storedURLlist, Procedure thisproc)
  {

  
    thisproc.setURLListData(storedURLlist, ListName);
   
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
    String userdir = System.getProperty("user.home");
try
{
         try (FileInputStream input = new FileInputStream(userdir + File.separator + "browsermator_config.properties")) {
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
            //legacy stuff
            if ("Firefox-Marionette".equals(targetbrowser))
            {
                targetbrowser = "Firefox";
            }
            
        
            this.TargetBrowser = targetbrowser;
            
          
         
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
  
        String[] blanklist = new String[0];
  
  
       
        newdataloop.setURLListData(blanklist, in_listname);
        VarLists.put(in_listname, blanklist);
   
     
   AddDataLoopProc(newdataloop);
        }    
                      public void AddNewDataLoopURLList(String in_listname, int at_index)
        {
            Procedure newdataloop = new Procedure();
      
         newdataloop.setType("Dataloop");
         
     
      
        newdataloop.setDataLoopSource("urllist");
  
        String[] blanklist = new String[0];
  
  
       
        newdataloop.setURLListData(blanklist, in_listname);
        VarLists.put(in_listname, blanklist);
   
     
   AddDataLoopProc(newdataloop, at_index);
        }    
           public void AddNewDataLoopFile(File CSVFile)
        {
        Procedure newdataloop = new Procedure();
      
         newdataloop.setType("Dataloop");
         newdataloop.setDataLoopSource("file");
     
        if (CSVFile.exists())
         {
       
         newdataloop.setDataFile(CSVFile.getAbsolutePath());
         
         }
         else
         {
       
         newdataloop.setDataFile("");  
        
         
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
       
         newdataloop.setDataFile(CSVFile.getAbsolutePath());
         
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
           public void AddActionToArray(Action action, Procedure newbug, ProcedureView newbugview)
           {
                        int at_index = newbugview.getJComboBoxAddAtPosition();
             if (at_index>0) {at_index--;}
           newbug.ActionsList.add(at_index, action);
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
             public void AddActionToArray (Action action, Procedure newbug)
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
 
 VarLists.put(varname, new String[0]);
 
      }
 
 
         public void setOSType(String OSType)
        {
            if ("Windows".equals(OSType))
            {
                OSType = "Windows32";
            }
            this.OSType = OSType;
          
                
        }
           public java.util.List<String[]> RandomizeAndLimitFileList(java.util.List<String[]> data_in, int limit, Boolean randval)
  {
    // first row is column names, remove it
  data_in.remove(0);
   java.util.List<String[]> ret_val = null;
             if (randval)
                {
             long seed = System.nanoTime();
Collections.shuffle(data_in, new Random(seed));
                }
                if (limit>0)
                {
                    
                    int sizeofvarlist = data_in.size();

 
   data_in.subList(limit, sizeofvarlist).clear();
                }
            
     ret_val = data_in;
     return ret_val;
             
  }
           public void setUniqueList(boolean unique)
 {
     this.UniqueList = unique;
  
     setUniqueFileOption(UniqueOption);
 }
             public String[] RandomizeAndLimitURLList(String URLListName, int limit, Boolean randval)
  {
       ArrayList<String> convert_list = new ArrayList<String>();
      String[] currentlist = new String[0];
   if (VarLists.containsKey(URLListName))
            {
          currentlist = VarLists.get(URLListName);
                      if (this.UniqueList)
{
   String userdir = System.getProperty("user.home");
   String file_name_to_check = "global";
       if (UniqueOption.equals("file"))
       {
           file_name_to_check = this.short_filename;
       }
        String visited_list_file_path = userdir + File.separator + "browsermator_" + file_name_to_check + "_visited_url_log.xml";
        
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
 
   

 ArrayList<String> removeList = new ArrayList<String>();
  
for (String grabbedURL: currentlist)
{   
     
    
    
    NodeList theseElements = doc.getElementsByTagName("URLsVisited");
   NodeList SettingsNodes = theseElements.item(0).getChildNodes();
String URL_FROM_FILE="";
Boolean hasValue = false;
int node_match_index = 0;
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

    }
 
  

  convert_list = new ArrayList(Arrays.asList(currentlist));

    convert_list.removeAll(removeList);
   currentlist =convert_list.stream().toArray(String[]::new);
// VarLists.get(URLListName).clear();
// VarLists.replace(URLListName, currentlist);

}
catch (Exception ex)
{
    
}
         }
}
       
                if (randval)
                {
             long seed = System.nanoTime();
             
 
        convert_list = new ArrayList(Arrays.asList(currentlist));      
Collections.shuffle(convert_list, new Random(seed));
   currentlist = convert_list.toArray(new String[convert_list.size()]);

 
                }
                if (limit>0)
                {
                    int sizeofvarlist = currentlist.length;

 if (limit<sizeofvarlist)
 {
     convert_list = new ArrayList(Arrays.asList(currentlist));
   if (convert_list.size()<limit)
   {
    
   }
   else
   {
     convert_list.subList(limit, sizeofvarlist).clear();
   }
  currentlist = convert_list.toArray(new String[convert_list.size()]);
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
        String userdir = System.getProperty("user.home");
        if (fileOption.equals("file"))
        {
        file_name_to_write = this.short_filename;
        }
        String visited_list_file_path = userdir + File.separator + "browsermator_" + file_name_to_write + "_visited_url_log.xml";
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
 public int FillTables(Procedure thisproc)
  {
      int number_of_rows = 0;
     for (Action ThisAction: thisproc.ActionsList)
     {
      String concat_variable;
 
              DataLoopVarParser var1Parser = new DataLoopVarParser(ThisAction.Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(ThisAction.Variable2);   
    if (var1Parser.hasDataLoopVar)
    {
 concat_variable = ThisAction.Variable1;
            String middle_part = concat_variable.substring(21, concat_variable.length()-20 );
            String[] parts = middle_part.split(",");
            if (parts[2].contains(":"))
            {   
            String[] parts2 = parts[2].split(":");
            String URLListName = parts2[1];
               if (this.VarLists.containsKey(URLListName))
            {
            this.UpdateDataLoopURLListTable(URLListName, this.VarLists.get(URLListName), thisproc);
            number_of_rows = this.VarLists.get(URLListName).length;
            }
            }
        } 
    if (var2Parser.hasDataLoopVar)
    {
 concat_variable = ThisAction.Variable2;
            String middle_part = concat_variable.substring(21, concat_variable.length()-20 );
            String[] parts = middle_part.split(",");
             if (parts[2].contains(":"))
            {  
            String[] parts2 = parts[2].split(":");
            String URLListName = parts2[1];
            if (this.VarLists.containsKey(URLListName))
            {
              
            this.UpdateDataLoopURLListTable(URLListName, this.VarLists.get(URLListName), thisproc);
            number_of_rows = this.VarLists.get(URLListName).length;
            }
            }
        } 
    
    }
     return number_of_rows;
     }

  
}
