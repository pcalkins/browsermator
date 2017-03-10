/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 *
 * @author pcalkins
 */
public class FireFoxProperties {
    String firefox_path;
    String targetbrowser;
    JFileChooser FindFireFoxExe;
  public FireFoxProperties(String in_tb)
  {
  this.targetbrowser = in_tb;
  this.FindFireFoxExe = new JFileChooser("Browse for executable");
   this.FindFireFoxExe.setPreferredSize(new Dimension(800,600));
  }
  public void BrowseforFFPath()
  {
         //   System.out.println("Cannot find binary for Firefox");
  
  
        if ("Chrome 49".equals(this.targetbrowser))
      {
  FindFireFoxExe = new JFileChooser("Browse for Chrome executable");
 
 FindFireFoxExe.setDialogTitle("Browse for Chrome executable (for manual installs on XP.)");
      } 
        else
        {
 FindFireFoxExe = new JFileChooser("Browse for Firefox executable");
 FindFireFoxExe.setDialogTitle("Browse for Firefox executable (If Selenium had a problem loading Firefox this may fix it.)");        
        }
      
 JPanel newJPanel = new JPanel();
 
 int returnVal = FindFireFoxExe.showOpenDialog(newJPanel);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = FindFireFoxExe.getSelectedFile();   
   
   WriteFireFoxPathToProperties(file.getAbsolutePath());
  
 // Prompter closeDown = new Prompter("Restart to Update Path", "Close and re-open the Browsermator to update executable path.", false,0, 0);
  
  
            }
            else
            {
            
            }
  }
  
    public String LoadFirefoxPath()
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
             System.out.println("error loading firefox path:" + e.toString());
           
             
         }
}
catch (Exception e) {
			System.out.println("Exception loading firefox path: " + e);
                        
		} 
      return applicationProps.getProperty("firefox_exe");
   
 
   
        
  }
        public String LoadChromePath()
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
             System.out.println("error loading chrome path:" + e.toString());
           
             
         }
}
catch (Exception e) {
			System.out.println("Exception loading firefox path: " + e);
                        
		} 
      return applicationProps.getProperty("chrome_exe");
   
 
   
        
  }
  public void WriteFireFoxPathToProperties(String pathtofirefox)
  {
      String userdir = System.getProperty("user.home");
      Properties applicationProps = new Properties();
      try
{

      FileInputStream input = new FileInputStream(userdir + File.separator + "browsermator_config.properties");
applicationProps.load(input);
input.close();
}
      catch (Exception ex)
      {
          
      }
      String prop_type = "chrome_exe";
      if ("Firefox".equals(targetbrowser))
      {
         prop_type = "firefox_exe";
      }
    
      applicationProps.setProperty(prop_type, pathtofirefox);
           try {
       FileWriter writer = new FileWriter(userdir + File.separator + "browsermator_config.properties");
    applicationProps.store(writer, "browsermator_settings");
    writer.close();
         
  
   
} 

    catch (Exception e) {
			System.out.println("Exception writing firefox path: " + e);
		}      
  }   
}
