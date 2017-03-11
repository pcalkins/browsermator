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
      BrowserMatorConfig()
      {
       
          this.userdir = System.getProperty("user.home");
      this.applicationProps = new Properties();    
        try (FileInputStream input = new FileInputStream(userdir + File.separator + "browsermator_config.properties")) {
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
       writer = new FileWriter(userdir + File.separator + "browsermator_config.properties");
    applicationProps.store(writer, "browsermator_settings");
    writer.close();
    } 

    catch (Exception e) {
			System.out.println("Exception writing key: " + key + "value: " +value + "ex: " + e);
		}      
      }
      
     
}
