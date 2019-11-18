/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;

/**
 *
 * @author pcalkins
 */
public class BrowserMatorConfig {

      Properties applicationProps;
      FileInputStream input;
        String userdir;
     FileWriter writer;
      String BrowsermatorAppFolder;

      BrowserMatorConfig()
      {
    this.BrowsermatorAppFolder =   System.getProperty("user.home")+File.separator+"BrowsermatorAppFolder"+File.separator;
 Boolean file_exists = false;
    File f = new File(BrowsermatorAppFolder + "browsermator_config.properties");
if(f.exists() && !f.isDirectory()) { 
   file_exists = true;
}
if (file_exists == false)
{
    CreateConfigFile();
}
          this.userdir = System.getProperty("user.home");
      this.applicationProps = new Properties();    
        try (FileInputStream input = new FileInputStream(BrowsermatorAppFolder + "browsermator_config.properties")) {
             applicationProps.load(input);
             
            
         }
         catch (Exception e)
         {
             System.out.println("error loading config:" + e.toString());
           
             
         }
      }
      public String getKeyValue (String key)
      {
          String value = "";
          value = applicationProps.getProperty(key);
          return value;
          
      }
      public void setKeyValue (String key, String value)
      {
           applicationProps.setProperty(key, value);
     try {
       writer = new FileWriter(BrowsermatorAppFolder + "browsermator_config.properties");
    applicationProps.store(writer, "browsermator_settings");
    writer.close();
    } 

    catch (Exception e) {
			System.out.println("Exception writing key: " + key + "value: " +value + "ex: " + e);
		}      
      }
        public final void CreateConfigFile()
  {
  
      File newconfig = new File(BrowsermatorAppFolder + "browsermator_config.properties");
      Properties newProps = new Properties();

       newProps.setProperty("main_window_locationY", "0");
      newProps.setProperty("main_window_locationX", "0");
      newProps.setProperty("main_window_sizeWidth", "1060");
      newProps.setProperty("main_window_sizeHeight", "950");   
   
      newProps.setProperty("email_subject", "");
      newProps.setProperty("email_to", "");
      newProps.setProperty("email_login_password", "");
      newProps.setProperty("email_from", "");
      newProps.setProperty("email_login_name", "");
      newProps.setProperty("smtp_hostname", "");
     newProps.setProperty("recentfiles", " , , , , , , , , , , ");
  
              try {
  

    
    FileWriter writer = new FileWriter(newconfig);
    newProps.store(writer, "browsermator_settings");
    writer.close();
} 

    catch (Exception e) {
			System.out.println("Exception writing config: " + e);
		}    
      
  }
     
}
