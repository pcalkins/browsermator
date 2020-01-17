/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import com.sun.javafx.PlatformUtil;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

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
     JFileChooser BrowserBrowser;
       String OperatingSystem;
     
      BrowserMatorConfig()
      {
              String bits = System.getProperty("sun.arch.data.model");
              OperatingSystem = "Other";
                 if (PlatformUtil.isWindows()){
        OperatingSystem = "Windows64"; 
        boolean is64bit = false;
if (System.getProperty("os.name").contains("Windows")) {
    is64bit = (System.getenv("ProgramFiles(x86)") != null);
} else {
    is64bit = (System.getProperty("os.arch").contains("64"));
}
         if (!is64bit)
           {
            OperatingSystem = "Windows32";   
           }
        }
             if (PlatformUtil.isMac()){
        OperatingSystem = "Mac"; 
        }
              if (PlatformUtil.isLinux()){
        OperatingSystem = "Linux-64"; 
    
           if ("32".equals(bits))
           {
            OperatingSystem = "Linux-32";   
           }
        }     
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
      public void removeKey (String key)
      {
          applicationProps.remove(key);
             try {
       writer = new FileWriter(BrowsermatorAppFolder + "browsermator_config.properties");
    applicationProps.store(writer, "browsermator_settings");
    writer.close();
    }
                catch (Exception e) {
			System.out.println("Exception deleteing key: " + e.toString());
		}  
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
        public void BrowseforBrowserPath(String targetbrowser)
  {
         //   System.out.println("Cannot find binary for Firefox");
  
      switch (targetbrowser)
      {
      
              case "Firefox":
       
 BrowserBrowser = new JFileChooser("Browse for Firefox executable");
 BrowserBrowser.setDialogTitle("Browse for Firefox executable (If Selenium had a problem loading Firefox this may fix it.)");        
        break;
        
              case "Chrome":
                BrowserBrowser= new JFileChooser("Browse for Chrome executable");
 
BrowserBrowser.setDialogTitle("Browse for Chrome executable.");      
      }
                
      
 JPanel newJPanel = new JPanel();
 
 int returnVal = BrowserBrowser.showOpenDialog(newJPanel);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = BrowserBrowser.getSelectedFile();   
   
   setKeyValue(targetbrowser, file.getAbsolutePath());
  
 // Prompter closeDown = new Prompter("Restart to Update Path", "Close and re-open the Browsermator to update executable path.", false,0, 0);
  
  
            }
            else
            {
            
            }
  }
        public final void CreateConfigFile()
  {
  
      File newconfig = new File(BrowsermatorAppFolder + "browsermator_config.properties");
      Properties newProps = new Properties();
             GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
       GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
     
        int x = (int) rect.getMaxX() - 1328;
        if (x<0){x=0;}
        String stringX = Integer.toString(x);
        
       newProps.setProperty("main_window_locationY", "0");
      newProps.setProperty("main_window_locationX", stringX);
      newProps.setProperty("main_window_sizeWidth", "1328");
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
           public String ReturnMachineSerialNumber() throws IOException
    {

        
        Process process = null;
        
         String ret_val = "";
      
       
           String sn = null;

		OutputStream os = null;
		InputStream is = null;

		Runtime runtime = Runtime.getRuntime();
      switch (OperatingSystem)
      {
          
          case "Windows":
              process = Runtime.getRuntime().exec(new String[] { "wmic", "bios", "get", "serialnumber" });
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        String property = sc.next();
        String serial = sc.next();
      ret_val = serial;
      break;
      
          case "Mac":
          
		
		try {
			process = runtime.exec(new String[] { "/usr/sbin/system_profiler", "SPHardwareDataType" });
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os = process.getOutputStream();
		is = process.getInputStream();

		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String marker = "Serial Number";
		try {
			while ((line = br.readLine()) != null) {
				if (line.contains(marker)) {
					sn = line.split(":")[1].trim();
					break;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		if (sn == null) {
			throw new RuntimeException("Cannot find computer SN");
                       
		}

		ret_val = sn;
                break;
          case "Linux":
             line = null;
		marker = "Serial Number:";
		 br = null;

		try {
			br = read("dmidecode -t system");
			while ((line = br.readLine()) != null) {
				if (line.contains(marker)) {
					sn = line.split(marker)[1].trim();
					break;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
                
                if (sn==null)
                {
                 line = null;
		 marker = "system.hardware.serial =";
		 br = null;

		try {
			br = read("lshal");
			while ((line = br.readLine()) != null) {
				if (line.contains(marker)) {
					sn = line.split(marker)[1].replaceAll("\\(string\\)|(\\')", "").trim();
					break;
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
                }
                if (sn==null){sn="";}
                ret_val = sn;
              break;
              
      }
      
      
      
      
      return ret_val;
      
    }
           private static BufferedReader read(String command) {

		OutputStream os = null;
		InputStream is = null;

		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(command.split(" "));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		os = process.getOutputStream();
		is = process.getInputStream();

		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return new BufferedReader(new InputStreamReader(is));
	}    
     
}
