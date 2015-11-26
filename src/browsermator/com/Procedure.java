package browsermator.com;


import java.io.Serializable;
import java.util.ArrayList;


public class Procedure implements Serializable{
  
  

Boolean Pass;
String BugURL;
String BugTitle;
int index;
String TargetBrowser;

   ArrayList<Action> ActionsList = new ArrayList();    
  
   Procedure ()
   {
this.Pass = false;
this.BugURL = "http://www.browsermator.com";
this.BugTitle = "";
this.index = 0;
this.TargetBrowser = "Firefox";

   }
  
   
 
   public void setProcedureTitle(String title)
   {
       this.BugTitle = title;
   }
   
}