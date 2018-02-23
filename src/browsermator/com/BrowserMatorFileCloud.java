/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Properties;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import netscape.javascript.JSObject;

/**
 *
 * @author pcalkins
 */
public class BrowserMatorFileCloud {
 WebView browser2;
 WebEngine webEngine;
 final JFXPanel fxPanel = new JFXPanel();
Stage webStage;
 JPanel mainPanel;
 JFrame cloudFrame;
 String HTML_TO_SEND="";
 STAppController mainAppController;
 MainAppFrame mainAppFrame;
JLabel loggedinLabelText; 
JLabel loginLabelText;
//JLabel loginLabelName;
//JLabel loginLabelPassword;
//JTextField loginFieldName;
//JPasswordField loginFieldPassword;
// String rootURL = "http://localhost";
 String rootURL = "https://www.browsermator.com";
    JPanel bottomPanel;
JLabel LoadingLabel;
String CLOUDFOLDER = System.getProperty("user.home") + File.separator+"BrowserMatorCloudFiles"+File.separator;
JButton loginButton = new JButton("Login"); 
      public BrowserMatorFileCloud(MainAppFrame in_mainAppFrame, STAppController in_mainAppController)
 {
    bottomPanel = new JPanel();
 LoadingLabel = new JLabel("Loading.  Please wait...");
bottomPanel.add(LoadingLabel);
     Platform.setImplicitExit(false);
     in_mainAppController.LoadNameAndPassword();
     this.mainAppController = in_mainAppController;
     this.mainAppFrame = in_mainAppFrame;

  
 }
  public void ShowFileCloudWindow()
  {
         if ("".equals(mainAppController.loginName))
     {
 ShowHTMLWindow(rootURL + "/browse_files.php");
     }
     else
     {
         String URLwithLogin = rootURL + "/browse_files.php?loginName=" + mainAppController.loginName + "&loginPassword=" + mainAppController.loginPassword;
         
         ShowHTMLWindow(URLwithLogin);
          
     }
  }
  public void UpdateWebView (String url)
  {
           try
      {
        
      URLConnection connection = new URL(url).openConnection();
connection.setDoOutput(true);
  BufferedReader in = new BufferedReader(new InputStreamReader(
                                   ((HttpURLConnection) connection).getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
          HTML_TO_SEND += inputLine;
        in.close();
      
      }
      catch(Exception ex)
      {
          HTML_TO_SEND = "Unable to connect to browsermator.com.";
   //        webviewREF.getEngine().loadContent(HTML_TO_SEND);
      webEngine.loadContent(HTML_TO_SEND);
          System.out.println("Exception browsing cloud: " + ex.toString());
            LoadingLabel.setVisible(false);
         
      }
   if (HTML_TO_SEND=="Unable to connect to browsermator.com.")
{
 //   webviewREF.getEngine().loadContent(HTML_TO_SEND);
    webEngine.loadContent(HTML_TO_SEND);
    LoadingLabel.setVisible(false);
}
else
{

      Platform.runLater(new Runnable() {
            @Override
            public void run() {

                webEngine.load(url);

            }
        });
}
  }
  
       public void ShowHTMLWindow(String url)
  {
    
      try
      {
         
      URLConnection connection = new URL(url).openConnection();
connection.setDoOutput(true);
  BufferedReader in = new BufferedReader(new InputStreamReader(
                                   ((HttpURLConnection) connection).getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
          HTML_TO_SEND += inputLine;
        in.close();
      
      }
      catch(Exception ex)
      {
          HTML_TO_SEND = "Unable to connect to browsermator.com.";
          System.out.println("Exception browsing cloud: " + ex.toString());
          LoadingLabel.setVisible(false);
      }
     //   String HTML_TO_SEND = HTML;
    

   Platform.runLater(new Runnable() {
            @Override
            public void run() {
             
browser2 = new WebView();
webEngine = browser2.getEngine();

 webEngine.getLoadWorker().stateProperty().addListener(
            new ChangeListener<State>() {
              @Override public void changed(ObservableValue ov, State oldState, State newState) {

                  if (newState == Worker.State.SUCCEEDED) {
                 JavaApp appREF = new JavaApp(webEngine);
                      JSObject win
                                = (JSObject) webEngine.executeScript("window");
                        win.setMember("app", appREF);  
                }
                  
                }
            });

if (HTML_TO_SEND=="Unable to connect to browsermator.com.")
{
    webEngine.loadContent(HTML_TO_SEND);
    
}
else
{
webEngine.load(url);
}
Scene scene = new Scene(browser2);
        fxPanel.setScene(scene);
   LoadingLabel.setVisible(false);
  
 
     
  webEngine.locationProperty().addListener(new ChangeListener<String>() {
  @Override public void changed(ObservableValue<? extends String> observableValue, String oldLoc, String newLoc) {

      if (newLoc.contains("serve_file"))
      {
  String[] filename_parts = newLoc.split("\\?");
  String[] queryvalues = filename_parts[1].split("&");
  String[] filename_value = queryvalues[0].split("=");
  String[] file_idvalue = queryvalues[1].split("=");
  String[] filedate_value = queryvalues[2].split("=");
  String file_id = file_idvalue[1];
  String filename = filename_value[1];
  
  try
  {
filename = URLDecoder.decode(filename_value[1], "UTF-8");
  }
  catch (Exception ex)
  {
     System.out.println("Exception when decoding HTML: " +ex.toString());
  }
  String file_date = filedate_value[1];
  String extension = ".browsermation";
  File thisFile = new File(CLOUDFOLDER+filename+extension);
             if (thisFile.exists())
             {
               Boolean checkdate = CheckIfFileIsNew(file_date, file_id);
             if (checkdate)
             {
               try {
          
         
    org.apache.commons.io.FileUtils.copyURLToFile(new URL(newLoc), new File(CLOUDFOLDER+filename+extension));
  
    
    mainAppController.OpenFile(thisFile, false, true);
  UpdateCloudConfig(file_id, file_date);
     cloudFrame.dispose();
} catch (Exception x) { System.out.println ("Exception downloading file" + x.toString()); }
  
             }
             else
             {
                   mainAppController.OpenFile(thisFile, false, true);
   //      Platform.exit();
          cloudFrame.dispose();
             }
             }
            else
             {
          try {
          
         
    org.apache.commons.io.FileUtils.copyURLToFile(new URL(newLoc), new File(CLOUDFOLDER+filename+extension));
    UpdateCloudConfig(file_id, file_date);
    
    mainAppController.OpenFile(thisFile,false, true);
 //  Platform.exit();
     cloudFrame.dispose();
} catch (Exception x) { System.out.println ("Exception downloading file" + x.toString()); }
      }
      }
  }
});      

            }
   });   
JPanel topPanel = new JPanel();
FlowLayout newFlow = new FlowLayout();
topPanel.setLayout(newFlow);
// mainApp.LoadNameAndPassword();
mainAppController.LookUpUser(mainAppController.loginName, mainAppController.loginPassword);
 
if (mainAppController.user_id>0)
{
 loginLabelText = new JLabel("Welcome " + mainAppController.loginName + " you are currently logged in. ");   
 loginButton = new JButton("Switch user"); 
addloginButtonActionListener( new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
LoginDialogLauncher();
  
        }
      }
    );
  topPanel.add(loginLabelText);
  topPanel.add(loginButton);
}
else
{
loginLabelText = new JLabel("You are not logged in. Login to view private files: ");
//JLabel loginLabelName = new JLabel ("Name:");
//JLabel loginLabelPassword = new JLabel ("Password:");
//JTextField loginFieldName = new JTextField("", 10);
//JPasswordField loginFieldPassword = new JPasswordField("",10);
 topPanel.add(loginLabelText);
loginButton = new JButton("Login"); 
addloginButtonActionListener( new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
LoginDialogLauncher();
  
        }
      }
    );
        



// topPanel.add(loginLabelName);
// topPanel.add(loginFieldName);
// topPanel.add(loginLabelPassword);
// topPanel.add(loginFieldPassword);
 topPanel.add(loginButton);
}

 
 
            fxPanel.setVisible(true);
fxPanel.setSize(800,800);

mainPanel = new JPanel(new BorderLayout());

    mainPanel.add(topPanel, BorderLayout.NORTH);
    mainPanel.add(fxPanel, BorderLayout.CENTER);
    mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    mainPanel.setVisible(true);

   cloudFrame = new JFrame("BrowserMation File Cloud");


  
    cloudFrame.add(mainPanel);
    cloudFrame.setSize(800, 800);
    cloudFrame.setVisible(true);   
     
    
    }
    public void UpdateCloudConfig(String file_id, String file_date)
    {
           
      Properties applicationProps = new Properties();
      
      try
{
    Boolean file_exists = false;
    
    File f = new File(CLOUDFOLDER + "browsermator_cloud_files.properties");
if(f.exists() && !f.isDirectory()) { 
   file_exists = true;
}
if (file_exists == false)
{
    CreateCloudConfigFile();
}
      FileInputStream input = new FileInputStream(CLOUDFOLDER + "browsermator_cloud_files.properties");
  
applicationProps.load(input);
input.close();
}
      catch (Exception ex)
      {
         System.out.println ("exception loading cloud files config: " + ex.toString()); 
      }
      applicationProps.setProperty(file_id, file_date);     
   
           try {
       FileWriter writer = new FileWriter(CLOUDFOLDER + "browsermator_cloud_files.properties");
    applicationProps.store(writer, "browsermator_settings");
    writer.close();
         
  
   
} 

    catch (Exception e) {
			System.out.println("Exception writing login details: " + e);
		}      
  }  
 
     public final void CreateCloudConfigFile()
  {
    String userdir = System.getProperty("user.home");
      File newconfig = new File(CLOUDFOLDER+ "browsermator_cloud_files.properties");
      Properties newProps = new Properties();

              try {
  
    FileWriter writer = new FileWriter(newconfig);
    newProps.store(writer, "browsermator_cloud_files");
    writer.close();
} 

    catch (Exception e) {
			System.out.println("Exception writing cloud file config: " + e);
		}    
      
  }    
   public Boolean CheckIfFileIsNew( String file_date, String file_id)
   {
       Boolean ret_val = false;
             String userdir = System.getProperty("user.home");
      Properties applicationProps = new Properties();
      String stored_file_date = "";
       try
{
    Boolean file_exists = false;
    
    File f = new File(CLOUDFOLDER+ "browsermator_cloud_files.properties");
if(f.exists() && !f.isDirectory()) { 
   file_exists = true;
}
if (file_exists == false)
{
    CreateCloudConfigFile();
}
      FileInputStream input = new FileInputStream(CLOUDFOLDER + "browsermator_cloud_files.properties");
  
applicationProps.load(input);
input.close();
}
      catch (Exception ex)
      {
         System.out.println ("exception loading cloud files config: " + ex.toString()); 
      }  
       
       if (applicationProps.containsKey(file_id))
       {
           stored_file_date = applicationProps.getProperty(file_id);
       }
       if (!file_date.equals(stored_file_date))
       {
           ret_val = true;
       }
       return ret_val;
       
   }
    public class JavaApp {
    WebEngine CloudEngine;
        JavaApp(WebEngine in_cloudREF)
        {
        this.CloudEngine = in_cloudREF;
        
        }
        public String upload(String file_id) {
 
  File[] newfiles = mainAppController.BrowseForFile();
    
 if (newfiles!=null)
 {
 
 File newfile = newfiles[0];

SendFileThread UpdateSendFileREF = new SendFileThread(CloudEngine, newfile, mainAppController.loginName, mainAppController.loginPassword, file_id);
 UpdateSendFileREF.execute();

 
// String URLwithLogin = rootURL + "/browse_files.php?edit=" + file_id + "&loginName=" + mainApp.loginName + "&loginPassword=" + mainApp.loginPassword;
  
//  ShowHTMLWindow(URLwithLogin);
 // UpdateWebView (URLwithLogin);
 return "filechosen";
 }
 else
 {
 // if cancel is hit... do ?
     return "cancelled";
 }
  
        }
    }
           public void addloginButtonActionListener(ActionListener listener)
{
    loginButton.addActionListener(listener);
}
    public void LoginDialogLauncher()
       {
           cloudFrame.dispose();
            Login_Register_Dialog loginDialog = new Login_Register_Dialog();
     loginDialog.setLoginName(mainAppController.loginName);
  loginDialog.setPassword(mainAppController.loginPassword);
     loginDialog.addjTextFieldConfirmPasswordDocListener(
             new DocumentListener()
           {
@Override
       public void changedUpdate(DocumentEvent documentEvent) {
       loginDialog.ComparePasswordFields();
       loginDialog.AllRequiredCheck();
      }
@Override
      public void insertUpdate(DocumentEvent documentEvent) {
      loginDialog.ComparePasswordFields();
      loginDialog.AllRequiredCheck();
      }
@Override
      public void removeUpdate(DocumentEvent documentEvent) {
     loginDialog.ComparePasswordFields();
     loginDialog.AllRequiredCheck();
      }
      }
                 );
 loginDialog.addjTextFieldPasswordDocListener(
             new DocumentListener()
           {
@Override
       public void changedUpdate(DocumentEvent documentEvent) {
       loginDialog.ComparePasswordFields();
       loginDialog.AllRequiredCheck();

      }
@Override
      public void insertUpdate(DocumentEvent documentEvent) {
      loginDialog.ComparePasswordFields();
      loginDialog.AllRequiredCheck();
      }
@Override
      public void removeUpdate(DocumentEvent documentEvent) {
     loginDialog.ComparePasswordFields();
     loginDialog.AllRequiredCheck();
      }
      }
                 );
    
     loginDialog.addjTextFieldLoginNameDocListener(
             new DocumentListener()
           {
@Override
       public void changedUpdate(DocumentEvent documentEvent) {
      
       loginDialog.AllRequiredCheck();

      }
@Override
      public void insertUpdate(DocumentEvent documentEvent) {
    
      loginDialog.AllRequiredCheck();
      }
@Override
      public void removeUpdate(DocumentEvent documentEvent) {
    
     loginDialog.AllRequiredCheck();
      }
      }
                 );
     loginDialog.addjTextFieldEmailDocListener(
             new DocumentListener()
           {
@Override
       public void changedUpdate(DocumentEvent documentEvent) {
      
       loginDialog.AllRequiredCheck();
       loginDialog.ValidateEmailAddress();
      }
@Override
      public void insertUpdate(DocumentEvent documentEvent) {
    
      loginDialog.AllRequiredCheck();
      loginDialog.ValidateEmailAddress();
      }
@Override
      public void removeUpdate(DocumentEvent documentEvent) {
    
     loginDialog.AllRequiredCheck();
     loginDialog.ValidateEmailAddress();
      }
      }
                 );
      loginDialog.addjButtonLoginModeActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
        loginDialog.LoginMode();
 
  
        }
      }
    );
   
       loginDialog.addjButtonRegisterModeActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
        loginDialog.RegisterMode();
 
  
        }
      }
    );
        loginDialog.addjButtonRecoverModeActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
        loginDialog.RecoverMode();
 
  
        }
      }
    );
     loginDialog.addjButtonLoginActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 loginDialog.setStatus("");
       mainAppController.LookUpUser(loginDialog.getLoginName(), loginDialog.getPassword());
  if (mainAppController.user_id >0 )
   {
       

                           mainAppController.SaveNameAndPassword(loginDialog.getLoginName(), loginDialog.getPassword());
                  //   UploadFile(STAppFrame);
             
                     loginDialog.dispose();
                     ShowFileCloudWindow();
                
    loginDialog.setStatus("");
   }
  else
  {
      loginDialog.setStatus("Login has failed.");
  }
  
        }
      }
    );
        loginDialog.addjTextFieldPasswordActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
       if ("login".equals(loginDialog.mode) && loginDialog.isActive )
       {
      loginDialog.setStatus("");
      mainAppController.LookUpUser(loginDialog.getLoginName(), loginDialog.getPassword());
       if (mainAppController.user_id >0 )
   {
       
   
     try
                      {
                          mainAppController.SaveNameAndPassword(loginDialog.getLoginName(), loginDialog.getPassword());
             //        UploadFile(STAppFrame);
           
                     loginDialog.dispose();
                     ShowFileCloudWindow();
                      }
                      catch (Exception e)
                      {
                        System.out.println("Upload file exception:" + e.toString());  
                     
                      } 
    loginDialog.setStatus("");
   }
        else
  {
      loginDialog.setStatus("Login has failed.");
  }
       }
  
        }
      }
    );
        loginDialog.addjTextFieldEmailActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
       if ("recover".equals(loginDialog.mode)&& loginDialog.isActive)
       {
           loginDialog.setStatus("");
     String statustext =  mainAppController.RecoverPassword(loginDialog.getEmail());
     loginDialog.setStatus (statustext);
     
       }
       if ("register".equals(loginDialog.mode)&& loginDialog.isActive)
       {
           loginDialog.setStatus("");
      String statustext =  mainAppController.RegisterUser(loginDialog, loginDialog.getLoginName(), loginDialog.getEmail(), loginDialog.getPassword());
      loginDialog.setStatus(statustext);
       }
  
        }
      }
    );
        
     loginDialog.addjButtonRegisterActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 loginDialog.setStatus("");
       String statustext =  mainAppController.RegisterUser(loginDialog, loginDialog.getLoginName(), loginDialog.getEmail(), loginDialog.getPassword());
        loginDialog.setStatus(statustext);
  
        }
      }
    );
     loginDialog.addjButtonRecoverPasswordActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
       String status =   mainAppController.RecoverPassword(loginDialog.getEmail());
         loginDialog.setStatus(status);
  
        }
      }
    );
       }   
 
}
