package browsermator.com;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;




public class SiteTestView extends JInternalFrame {
String RecentFile1;
String RecentFile2;
String RecentFile3;
String RecentFile4;
String RecentFile5;
String RecentFile6;
String RecentFile7;
String RecentFile8;
String RecentFile9;
String RecentFile10;

String CloudFile1;
String CloudFile2;
String CloudFile3;
String CloudFile4;
String CloudFile5;

 
    private JButton jButtonNewFile;
    private JButton jButtonOpenFile;
    private JButton jButtonSaveEmailConfig;
    private JLabel jLabelRecentFile1;
    private JLabel jLabelRecentFile2;
    private JLabel jLabelRecentFile3;
    private JLabel jLabelRecentFile4;
    private JLabel jLabelRecentFile5;
    private JLabel jLabelRecentFile6;
    private JLabel jLabelRecentFile7;
    private JLabel jLabelRecentFile8;
    private JLabel jLabelRecentFile9;
    private JLabel jLabelRecentFile10;
    private JLabel jLabelRecentFiles;
    private JLabel jLabelVersion;
    private JPanel jPanelVersion;
    private JTextField jTextFieldEmailFrom;
    private JLabel jLabelEmailFrom;
    private JTextField jTextFieldEmailLoginName;
    private JLabel jLabelEmailLoginName;
    private JPasswordField jTextFieldEmailPassword;
    private JLabel jLabelEmailPassword;
    private JTextField jTextFieldEmailTo;
    private JLabel jLabelEmailTo;
    private JTextField jTextFieldSMTPHostName;
    private JLabel jLabelSMTPHostName;
    private JTextField jTextFieldSubject;
    private JLabel jLabelSubject;
    private JPanel jPanelRecentFiles;
    private JPanel jPanelNewOpen;
    private JPanel jPanelDefaultEmailOptions; 
    private JLabel jLabelEmailFormTitle;
    private JButton jButtonOpenFileCloud;
  
    
    
    GridBagLayout GridLayout = new GridBagLayout();
    GridBagConstraints Constraints = new GridBagConstraints();
   
 
   private JButton jButtonBrowseForDownloadDir;
   private JButton jButtonClearDownloadDir;
   private JLabel jLabelDownloadDir;
   private JLabel jTextFieldDownloadDir;
     private JPanel jPanelDownloadDir;
     
      private JButton jButtonBrowseForFireFox;
      private JButton jButtonClearFirefox;
   private JLabel jLabelFirefoxDir;
   private JLabel jTextFieldFirefoxDir;
     private JPanel jPanelFirefoxDir;
     
      private JButton jButtonBrowseForChrome;
      private JButton jButtonClearChrome;
   private JLabel jLabelChromeDir;
   private JLabel jTextFieldChromeDir;
     private JPanel jPanelChromeDir;
     private JPanel jPanelBrowse;
    private JPanel jPanelCenter;
    BrowserMatorConfig appConfig;
    public String chromeversion;
    
   SiteTestView(BrowserMatorConfig _appConfig) {
   RecentFile1 = "";
   RecentFile2 = "";
   RecentFile3 = "";
   RecentFile4 = "";
   RecentFile5 = "";
   RecentFile6 = "";
   RecentFile7 = "";
   RecentFile8 = "";
   RecentFile9 = "";
   RecentFile10 = "";
   CloudFile1 = "";
   CloudFile2 = "";
   CloudFile3 = "";
   CloudFile4 = "";
   CloudFile5 = "";

   
   
    appConfig = _appConfig;
    chromeversion = "(v81)";
  


        
    }
 
  public void RemoveRecentFile(int index)
  {
    
   
String recentfiles = appConfig.getKeyValue("recentfiles");

String outarray[];

outarray = recentfiles.split(",");
index--;
//legacy files have 5 values... decided not to hack it to 10.  Config needs dynamic way to store recents with "more" option,scrollable, and clear.
String[] inarray = outarray.clone();
outarray[index]="";

   for (int x=0; x<outarray.length; x++)
 {
    if (x<index)
    {
      outarray[x] = inarray[x];
    }
    if (x>=index  && x<inarray.length-1)
    {
       outarray[x] = inarray[x+1];
    }
   
    if (x==inarray.length-1)
    {
        outarray[x]=" ";
    }
 }
StoreRecentFiles(outarray);
setRecentFiles(outarray);
  }
  public String getRecentFile1()
  {
      return RecentFile1;
  }
   public String getRecentFile2()
  {
      return RecentFile2;
  }
    public String getRecentFile3()
  {
      return RecentFile3;
  }
     public String getRecentFile4()
  {
      return RecentFile4;
  }
   public String getRecentFile5()
  {
      return RecentFile5;
  }  
     public String getRecentFile6()
  {
      return RecentFile6;
  } 
     public String getRecentFile7()
  {
      return RecentFile7;
  } 
   public String getRecentFile8()
  {
      return RecentFile8;
  }   
   public String getRecentFile9()
  {
      return RecentFile9;
  } 
   public String getRecentFile10()
  {
      return RecentFile10;
  }   
  public void addjButtonNewWebsiteTestActionListener(ActionListener listener)
    {
    jButtonNewFile.addActionListener(listener);
    } 
  public void addjButtonOpenFileCloudActionListener(ActionListener listener)
    {
    jButtonOpenFileCloud.addActionListener(listener);
    } 
    public void addjButtonBrowseForDownloadDirActionListener(ActionListener listener)
    {
    jButtonBrowseForDownloadDir.addActionListener(listener);
  
    } 
      public void addjButtonClearDownloadDirActionListener(ActionListener listener)
    {
    jButtonClearDownloadDir.addActionListener(listener);
  
    } 
      public void addjButtonBrowseForChromeActionListener(ActionListener listener)
    {
    jButtonBrowseForChrome.addActionListener(listener);
  
    } 
         public void addjButtonClearChromeActionListener(ActionListener listener)
    {
    jButtonClearChrome.addActionListener(listener);
  
    } 
        public void addjButtonBrowseForFirefoxActionListener(ActionListener listener)
    {
    jButtonBrowseForFireFox.addActionListener(listener);
  
    } 
        public void addjButtonClearFirefoxActionListener(ActionListener listener)
    {
    jButtonClearFirefox.addActionListener(listener);
  
    } 
  public void addJButtonOpenWebSiteTestActionListener(ActionListener listener)
  {
      jButtonOpenFile.addActionListener(listener);
  }
    public void addJButtonSaveEmailConfigActionListener(ActionListener listener)
  {
      jButtonSaveEmailConfig.addActionListener(listener);
  }
   public void setjTextFieldDownloadDir(String _downloaddir)
   {
       jTextFieldDownloadDir.setText(_downloaddir);
   }
      public void setjTextFieldFirefox(String _path)
   {
     this.jTextFieldFirefoxDir.setText(_path);
   }
         public void setjTextFieldChrome(String _path)
   {
       jTextFieldChromeDir.setText(_path);
   }
    public String getSMTPHostname ()
    {
        String SMTPHostname = jTextFieldSMTPHostName.getText();
        return SMTPHostname;
       
    }
    public void addRecentFile (String filename)
    {
 
String recentfiles = appConfig.getKeyValue("recentfiles");

String outarray[];

outarray = recentfiles.split(",");

String[] inarray = outarray.clone();
// if added filename already exists, sort name to top only
Boolean SortIt = false;
int dupcheckindex = 0;
int dupindex = -1;
for (String savedname: inarray)
{
    if (savedname.equals(filename))
    {
        SortIt = true;
        dupindex = dupcheckindex;
    }
    dupcheckindex++;
}
if (SortIt == false)
{
    
  
    System.arraycopy(inarray, 0, outarray, 1, 9);
 outarray[0] = filename;

}
else
{
//outarray[dupcheckindex] goes to outarray[0]...anything under dupcheckindex, adds 1, anything else stays same
 
    for (int x=0; x<outarray.length; x++)
 {
    if (x<dupindex)
    {
      outarray[x+1] = inarray[x];
    }
    else if (x==dupindex)
    {
        //skip
    }
    else
    {
      outarray[x] = inarray[x];  
    }
 }
    outarray[0] = filename;
    setRecentFiles(outarray);
}
setRecentFiles(outarray);


}
  public void addRecentFile1MouseListener(MouseListener listener)
  {
      jLabelRecentFile1.addMouseListener(listener);
  }

    public void addRecentFile2MouseListener(MouseListener listener)
  {
      jLabelRecentFile2.addMouseListener(listener);
  }
   public void addRecentFile3MouseListener(MouseListener listener)
  {
      jLabelRecentFile3.addMouseListener(listener);
  }
    public void addRecentFile4MouseListener(MouseListener listener)
  {
      jLabelRecentFile4.addMouseListener(listener);
  }
    public void addRecentFile5MouseListener(MouseListener listener)
  {
      jLabelRecentFile5.addMouseListener(listener);
  }
     public void addRecentFile6MouseListener(MouseListener listener)
  {
      jLabelRecentFile6.addMouseListener(listener);
  }
    public void addRecentFile7MouseListener(MouseListener listener)
  {
      jLabelRecentFile7.addMouseListener(listener);
  }
     public void addRecentFile8MouseListener(MouseListener listener)
  {
      jLabelRecentFile8.addMouseListener(listener);
  }
     public void addRecentFile9MouseListener(MouseListener listener)
  {
      jLabelRecentFile9.addMouseListener(listener);
  }
    public void addRecentFile10MouseListener(MouseListener listener)
  {
      jLabelRecentFile10.addMouseListener(listener);
  }
    
  
    public void setMouseOutColor(int labelnumber)
    {
        switch(labelnumber)
        {
            case (1):
            jLabelRecentFile1.setForeground(Color.blue);
            Font thisfont = jLabelRecentFile1.getFont();
            Map attributes = thisfont.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile1.setFont(thisfont.deriveFont(attributes));
            attributes.clear();
            break;
            case (2):
            jLabelRecentFile2.setForeground(Color.blue);
            Font thisfont2 = jLabelRecentFile2.getFont();
            Map attributes2 = thisfont2.getAttributes();
            attributes2.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile2.setFont(thisfont2.deriveFont(attributes2));
            attributes2.clear();
            break;
            case (3):
            jLabelRecentFile3.setForeground(Color.blue);
            Font thisfont3 = jLabelRecentFile3.getFont();
            Map attributes3 = thisfont3.getAttributes();
            attributes3.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile3.setFont(thisfont3.deriveFont(attributes3));
            attributes3.clear();
            break;
            case (4):
            jLabelRecentFile4.setForeground(Color.blue);
            Font thisfont4 = jLabelRecentFile4.getFont();
            Map attributes4 = thisfont4.getAttributes();
            attributes4.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile4.setFont(thisfont4.deriveFont(attributes4));
            attributes4.clear();
            break;
            case (5):
            jLabelRecentFile5.setForeground(Color.blue);
            Font thisfont5 = jLabelRecentFile5.getFont();
            Map attributes5 = thisfont5.getAttributes();
            attributes5.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile5.setFont(thisfont5.deriveFont(attributes5));
            attributes5.clear();
            break;
            case (6):
            jLabelRecentFile6.setForeground(Color.blue);
            Font thisfont6 = jLabelRecentFile6.getFont();
            Map attributes6 = thisfont6.getAttributes();
            attributes6.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile6.setFont(thisfont6.deriveFont(attributes6));
            attributes6.clear();
            break;
            case (7):
            jLabelRecentFile7.setForeground(Color.blue);
            Font thisfont7 = jLabelRecentFile7.getFont();
            Map attributes7 = thisfont7.getAttributes();
            attributes7.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile7.setFont(thisfont7.deriveFont(attributes7));
            attributes7.clear();
            break;
            case (8):
            jLabelRecentFile8.setForeground(Color.blue);
            Font thisfont8 = jLabelRecentFile8.getFont();
            Map attributes8 = thisfont8.getAttributes();
            attributes8.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile8.setFont(thisfont8.deriveFont(attributes8));
            attributes8.clear();
            break;
             case (9):
            jLabelRecentFile9.setForeground(Color.blue);
            Font thisfont9 = jLabelRecentFile9.getFont();
            Map attributes9 = thisfont9.getAttributes();
            attributes9.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile9.setFont(thisfont9.deriveFont(attributes9));
            attributes9.clear();
            break;
              case (10):
            jLabelRecentFile10.setForeground(Color.blue);
            Font thisfont10 = jLabelRecentFile10.getFont();
            Map attributes10 = thisfont10.getAttributes();
            attributes10.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile10.setFont(thisfont10.deriveFont(attributes10));
            attributes10.clear();
            break;
                    
        }
    }
     public void setMouseOverColor(int labelnumber)
    {
        switch(labelnumber)
        {
           
            case (1):
                
            Font thisfont = jLabelRecentFile1.getFont();
            Map attributes = thisfont.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            jLabelRecentFile1.setFont(thisfont.deriveFont(attributes));
            attributes.clear();
            break;
            case (2):
            Font thisfont2 = jLabelRecentFile2.getFont();
            Map attributes2 = thisfont2.getAttributes();
            attributes2.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            jLabelRecentFile2.setFont(thisfont2.deriveFont(attributes2));
            attributes2.clear();
            break;
            case (3):
            Font thisfont3 = jLabelRecentFile3.getFont();
            Map attributes3 = thisfont3.getAttributes();
            attributes3.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            jLabelRecentFile3.setFont(thisfont3.deriveFont(attributes3));
            attributes3.clear();
            break;
            case (4):
            Font thisfont4 = jLabelRecentFile4.getFont();
            Map attributes4 = thisfont4.getAttributes();
            attributes4.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            jLabelRecentFile4.setFont(thisfont4.deriveFont(attributes4));
            attributes4.clear();
        
            break;
            case (5):
            Font thisfont5 = jLabelRecentFile5.getFont();
            Map attributes5 = thisfont5.getAttributes();
            attributes5.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            jLabelRecentFile5.setFont(thisfont5.deriveFont(attributes5));
            attributes5.clear();
            break;
             case (6):
            Font thisfont6 = jLabelRecentFile6.getFont();
            Map attributes6 = thisfont6.getAttributes();
            attributes6.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            jLabelRecentFile6.setFont(thisfont6.deriveFont(attributes6));
            attributes6.clear();
            break;
             case (7):
            Font thisfont7 = jLabelRecentFile7.getFont();
            Map attributes7 = thisfont7.getAttributes();
            attributes7.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            jLabelRecentFile7.setFont(thisfont7.deriveFont(attributes7));
            attributes7.clear();
            break;
             case (8):
            Font thisfont8 = jLabelRecentFile8.getFont();
            Map attributes8 = thisfont8.getAttributes();
            attributes8.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            jLabelRecentFile8.setFont(thisfont8.deriveFont(attributes8));
            attributes8.clear();
            break;
             case (9):
            Font thisfont9 = jLabelRecentFile9.getFont();
            Map attributes9 = thisfont9.getAttributes();
            attributes9.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            jLabelRecentFile9.setFont(thisfont9.deriveFont(attributes9));
            attributes9.clear();
            break;
             case (10):
            Font thisfont10 = jLabelRecentFile10.getFont();
            Map attributes10 = thisfont10.getAttributes();
            attributes10.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            jLabelRecentFile10.setFont(thisfont10.deriveFont(attributes10));
            attributes10.clear();
            break;
           
                    
        }
    }
     public void setMouseClickColor(int labelnumber)
    {
        switch(labelnumber)
        {
            case (1):
            jLabelRecentFile1.setForeground(Color.red);
            break;
            case (2):
            jLabelRecentFile2.setForeground(Color.red);
            break;
            case (3):
            jLabelRecentFile3.setForeground(Color.red);
            break;
            case (4):
            jLabelRecentFile4.setForeground(Color.red);
            break;
            case (5):
            jLabelRecentFile5.setForeground(Color.red);
            break;
            case (6):
            jLabelRecentFile6.setForeground(Color.red);
            break;
            case (7):
            jLabelRecentFile7.setForeground(Color.red);
            break;
            case (8):
            jLabelRecentFile8.setForeground(Color.red);
            break;
            case (9):
            jLabelRecentFile9.setForeground(Color.red);
            break;
            case (10):
            jLabelRecentFile10.setForeground(Color.red);
            break;
                    
        }
    }
    private void setRecentFiles(String[] filenames)
    {
        int count = 1;
     for (String filename : filenames)
     {
      if (count == 1)
      {
         jLabelRecentFile1.setText(filename);
         jLabelRecentFile1.setForeground(Color.blue);
         RecentFile1 = filename;
      }     
      if (count==2)
      {
         jLabelRecentFile2.setText(filename);
          jLabelRecentFile2.setForeground(Color.blue);
         RecentFile2 = filename;
      }  
       if (count==3)
       {
         jLabelRecentFile3.setText(filename);
          jLabelRecentFile3.setForeground(Color.blue);
         RecentFile3 = filename;
       }
       if (count==4)
       {
         jLabelRecentFile4.setText(filename);
          jLabelRecentFile4.setForeground(Color.blue);
         RecentFile4 = filename;
       }
       if (count==5)
       {
         jLabelRecentFile5.setText(filename);
          jLabelRecentFile5.setForeground(Color.blue);
         RecentFile5 = filename;
       }
   
          if (count==6)
       {
         jLabelRecentFile6.setText(filename);
          jLabelRecentFile6.setForeground(Color.blue);
         RecentFile6 = filename;
       }
                   if (count==7)
       {
         jLabelRecentFile7.setText(filename);
          jLabelRecentFile7.setForeground(Color.blue);
         RecentFile7 = filename;
       }
                       if (count==8)
       {
         jLabelRecentFile8.setText(filename);
          jLabelRecentFile8.setForeground(Color.blue);
         RecentFile8 = filename;
       }
                   if (count==9)
       {
         jLabelRecentFile9.setText(filename);
          jLabelRecentFile9.setForeground(Color.blue);
         RecentFile9 = filename;
       }
                  if (count==10)
       {
         jLabelRecentFile10.setText(filename);
          jLabelRecentFile10.setForeground(Color.blue);
         RecentFile10 = filename;
       }
         count++;
     }
     StoreRecentFiles(filenames);
    }
    public void StoreRecentFiles (String[] filenames)
    {
        if (filenames.length>0)
        {
       

    
 String commastringfilenames = filenames[0];
 int ccount = 0;
 for (String filename: filenames)
 {
     if (ccount!=0)
     {
     commastringfilenames = commastringfilenames + "," + filename;
     }
     ccount++;
 }

  
    appConfig.setKeyValue("recentfiles", commastringfilenames);
    
      
}
    }
    public String getEmailLoginName ()
    {
        String EmailLoginName = jTextFieldEmailLoginName.getText();
        return EmailLoginName;
    }
    public String getEmailPassword ()
    {
        String EmailPassword = "";
    
        char[] chars;
        chars = jTextFieldEmailPassword.getPassword();
           for (int x = 0; x<chars.length; x++)
     {
         EmailPassword = EmailPassword + chars[x];
     } 
       
        return EmailPassword;
    }
    public String getEmailTo ()
    {
        String EmailTo = jTextFieldEmailTo.getText();
        return EmailTo;
    }
    public String getEmailFrom()
    {
        String EmailFrom = jTextFieldEmailFrom.getText();
        return EmailFrom;        
    }
    
    public String getSubject()
    {
        String Subject = jTextFieldSubject.getText();
        return Subject;
    }
    public void setVersion(String version)
    {
        String currentVersion = "0.0";
        try
        {
            currentVersion = getCurrentVersion();
        }
        catch (Exception ex)
        {
            System.out.println("Exception getting current version: " + ex.toString());
        }
        if (currentVersion.equals(version))
        {
        jLabelVersion.setText("Version " +version);
        }
        else
        {
         jLabelVersion.setText("Version " + version + " A New Version ("+currentVersion +") is Available, Visit Browsermator.com to download.");   
        }
        
    }
   public void setSMTPHostname (String SMTPHostName)
    {
        jTextFieldSMTPHostName.setText(SMTPHostName);
    }
    public void setEmailLoginName (String EmailLoginName)
    {
      jTextFieldEmailLoginName.setText(EmailLoginName);
    }
    public void setEmailPassword (String EmailPassword)
    {
      jTextFieldEmailPassword.setText(EmailPassword); 
    }
    public void setEmailTo (String EmailPassword)
    {
     jTextFieldEmailTo.setText(EmailPassword);
    
    }
    public void setEmailFrom(String EmailFrom)
    {
      jTextFieldEmailFrom.setText(EmailFrom);      
    }
   
    public void setSubject(String Subject)
    {
      jTextFieldSubject.setText(Subject);
    } 
              public String getCurrentVersion() throws MalformedURLException, UnsupportedEncodingException, IOException
      {
          String ret_val = "0.0";
          String rootURL = "https://www.thebrowserbots.com";
          String APPID = "3";
       String charset = "UTF-8";
String param = "15000";

String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
String CRLF = "\r\n"; // Line separator required by multipart/form-data.
URLConnection connection = new URL(rootURL + "/get_current_version_noheader.php?app_id=" + APPID).openConnection();
connection.setDoOutput(true);


    BufferedReader in = new BufferedReader(new InputStreamReader(
                                   ((HttpURLConnection) connection).getInputStream()));
        String inputLine;
        String return_text="";
        while ((inputLine = in.readLine()) != null)
         return_text += inputLine;
        in.close();    
          ret_val = return_text;
          return ret_val;
          
      }
  public void initializeComponents()
  {
  
String recentfiles = appConfig.getKeyValue("recentfiles");
String downloadPath = appConfig.getKeyValue("downloaddir");
String chromePath = appConfig.getKeyValue("Chrome");
String firefoxPath = appConfig.getKeyValue("Firefox");



     
        jTextFieldSMTPHostName = new JTextField(20);
        jTextFieldEmailLoginName = new JTextField(20);  
        jTextFieldEmailTo = new JTextField(20);
        jTextFieldEmailFrom = new JTextField(20);
        jButtonNewFile = new JButton();
        jButtonOpenFile = new JButton();
        jLabelSMTPHostName = new JLabel();
        jLabelEmailFormTitle = new JLabel();
        jLabelEmailLoginName = new JLabel();
        jLabelEmailPassword = new JLabel();
        jLabelEmailTo = new JLabel();
        jLabelEmailFrom = new JLabel();
        jLabelSubject = new JLabel();
        jButtonSaveEmailConfig = new JButton();
        jTextFieldSubject = new JTextField(20);
        jTextFieldEmailPassword = new JPasswordField(20);
        jLabelVersion = new JLabel();
        jPanelVersion = new JPanel();
        jPanelVersion.add(jLabelVersion);
        
        jLabelRecentFiles = new JLabel();
        jLabelRecentFile1 = new JLabel();
        jLabelRecentFile2 = new JLabel();
        jLabelRecentFile3 = new JLabel();
        jLabelRecentFile4 = new JLabel();
        jLabelRecentFile5 = new JLabel();
        jLabelRecentFile6 = new JLabel();
        jLabelRecentFile7 = new JLabel();
        jLabelRecentFile8 = new JLabel();
        jLabelRecentFile9 = new JLabel();
        jLabelRecentFile10 = new JLabel();
         jTextFieldSMTPHostName.setText("");
    jTextFieldEmailPassword.setText("");
  
        jLabelEmailFormTitle.setText("Email Configuration:");
        
        jLabelSMTPHostName.setText("SMTPHostname:");
       
        jLabelEmailLoginName.setText("Email Login Name:");
        jLabelEmailPassword.setText("Email Login Password:");
        jLabelEmailTo.setText("E-mail To:");
        jLabelEmailFrom.setText("E-mail From:");
        jButtonNewFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/browsermator/com/Resources/newFile.png"))); // NOI18N
        jButtonNewFile.setText("New File ");
        jButtonOpenFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/browsermator/com/Resources/openFile.png"))); // NOI18N
        jButtonOpenFile.setText("Open File ");
        jButtonSaveEmailConfig.setText("Save Default Settings");
        jLabelSubject.setText("Title/Subject:");
        jLabelVersion.setText("Config file browsermator_config.properties is missing....");     
        jLabelRecentFiles.setText("Recent Files:");
       
       
        
        jPanelNewOpen = new JPanel();
        jPanelNewOpen.setLayout(new BoxLayout( jPanelNewOpen , BoxLayout.X_AXIS));
        jPanelNewOpen.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        jPanelNewOpen.add(Box.createRigidArea(new Dimension(10, 0)));
        jPanelNewOpen.add(jButtonNewFile);
        jPanelNewOpen.add(Box.createRigidArea(new Dimension(5, 0)));
        jPanelNewOpen.add(jButtonOpenFile);
   
        jButtonOpenFileCloud = new JButton("Open Browsermator File Cloud");
        jPanelRecentFiles = new JPanel();
       
        jPanelRecentFiles.setLayout(new BoxLayout( jPanelRecentFiles , BoxLayout.Y_AXIS));
   //     jPanelRecentFiles.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
          jPanelDownloadDir = new JPanel();
        jLabelDownloadDir = new JLabel("Download Directory for Chrome/Firefox:");
        jTextFieldDownloadDir = new JLabel("Default");
      
        jButtonBrowseForDownloadDir = new JButton("Browse");
        jButtonClearDownloadDir = new JButton("Default");
        jPanelDownloadDir.add(jLabelDownloadDir);
        jPanelDownloadDir.add(jTextFieldDownloadDir);
        jPanelDownloadDir.add(jButtonBrowseForDownloadDir);
        jPanelDownloadDir.add(jButtonClearDownloadDir);
       
         jPanelFirefoxDir = new JPanel();
        jLabelFirefoxDir = new JLabel("Firefox Executable Location (May be needed for custom install folder):");
        jTextFieldFirefoxDir = new JLabel("Default");
        jButtonBrowseForFireFox = new JButton("Browse");
        jButtonClearFirefox = new JButton("Default");
        jPanelFirefoxDir.add(jLabelFirefoxDir);
        jPanelFirefoxDir.add(jTextFieldFirefoxDir);
        jPanelFirefoxDir.add(jButtonBrowseForFireFox);
        jPanelFirefoxDir.add(jButtonClearFirefox);
        
          jPanelChromeDir = new JPanel();
        jLabelChromeDir = new JLabel("Chrome " + chromeversion + " Executable Location (May be needed for custom install folder):");
        jButtonClearChrome = new JButton("Default");
        jTextFieldChromeDir = new JLabel("Default");
        jButtonBrowseForChrome = new JButton("Browse");
        jPanelChromeDir.add(jLabelChromeDir);
        jPanelChromeDir.add(jTextFieldChromeDir);
        jPanelChromeDir.add(jButtonBrowseForChrome);
        jPanelChromeDir.add(jButtonClearChrome);
        
        jPanelRecentFiles.add(jLabelRecentFiles);
        jPanelRecentFiles.add(jLabelRecentFile1);
        jPanelRecentFiles.add(jLabelRecentFile2);
        jPanelRecentFiles.add(jLabelRecentFile3);
        jPanelRecentFiles.add(jLabelRecentFile4);
        jPanelRecentFiles.add(jLabelRecentFile5);
        jPanelRecentFiles.add(jLabelRecentFile6);
        jPanelRecentFiles.add(jLabelRecentFile7);
        jPanelRecentFiles.add(jLabelRecentFile8);
        jPanelRecentFiles.add(jLabelRecentFile9);
        jPanelRecentFiles.add(jLabelRecentFile10);
        jPanelRecentFiles.add(Box.createRigidArea(new Dimension(10, 10)));
            
        jPanelRecentFiles.add(jButtonOpenFileCloud);
      
       
       
   
        
            jPanelDefaultEmailOptions = new JPanel(GridLayout);
        JPanel spacer = new JPanel();
          Constraints.insets = new Insets(2,2,2,2); //top, left, bottom, right
          AddToGrid(jLabelEmailFormTitle, 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST); 
          AddToGrid(jLabelSMTPHostName, 1, 0, 1, 1, 0.1, 0.1, GridBagConstraints.EAST); 
          AddToGrid(jTextFieldSMTPHostName, 1, 1, 1, 1, 0.1, 0.1, GridBagConstraints.WEST); 
          AddToGrid(jLabelEmailLoginName, 2, 0, 1, 1, 0.1, 0.1, GridBagConstraints.EAST); 
          AddToGrid(jTextFieldEmailLoginName, 2, 1, 1, 1, 0.1, 0.1, GridBagConstraints.WEST); 
          AddToGrid(jLabelEmailPassword, 3, 0, 1, 1, 0.1, 0.1, GridBagConstraints.EAST); 
          AddToGrid(jTextFieldEmailPassword, 3, 1, 1, 1, 0.1, 0.1, GridBagConstraints.WEST); 
          AddToGrid(jLabelEmailTo, 4, 0, 1, 1, 0.1, 0.1, GridBagConstraints.EAST); 
          AddToGrid(jTextFieldEmailTo, 4, 1, 1, 1, 0.1, 0.1, GridBagConstraints.WEST);
          AddToGrid(jLabelEmailFrom, 5, 0, 1, 1, 0.1, 0.1, GridBagConstraints.EAST); 
          AddToGrid(jTextFieldEmailFrom, 5, 1, 1, 1, 0.1, 0.1, GridBagConstraints.WEST);
          AddToGrid(jLabelSubject, 6, 0, 1, 1, 0.1, 0.1, GridBagConstraints.EAST); 
          AddToGrid(jTextFieldSubject, 6, 1, 1, 1, 0.1, 0.1, GridBagConstraints.WEST);
          AddToGrid(jButtonSaveEmailConfig, 7, 1, 1, 1, 0.1, 0.1, GridBagConstraints.WEST);
          AddToGrid(jLabelVersion, 8, 1, 1, 1, 0.1, 0.1, GridBagConstraints.WEST);
       //   AddToGrid(jPanelChromeDir, 1, 2, 1,1, 0.1, 0.1, GridBagConstraints.WEST);
       //   AddToGrid(jPanelFirefoxDir, 2, 2, 1,1, 0.1, 0.1, GridBagConstraints.WEST);
       //   AddToGrid(jPanelDownloadDir, 3, 2, 1,1, 0.1, 0.1, GridBagConstraints.WEST);
          AddToGrid(spacer, 9, 2, 1, 1, 0.99, 0.99, GridBagConstraints.EAST);
          
          jPanelCenter = new JPanel(GridLayout);
            jPanelBrowse = new JPanel();
       
         jPanelBrowse.setLayout(new BoxLayout(  jPanelBrowse  , BoxLayout.Y_AXIS));
         jPanelBrowse.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
          jPanelBrowse.add(jPanelChromeDir);
          jPanelBrowse.add(jPanelFirefoxDir);
          jPanelBrowse.add(jPanelDownloadDir);
           AddToCenterGrid(jPanelRecentFiles, 0, 1, 1,10, 0.1, 0.1, GridBagConstraints.WEST);
          AddToCenterGrid(jPanelBrowse, 11, 1, 1, 3, 0.1, 0.1, GridBagConstraints.WEST);
//   AddToCenterGrid(jPanelChromeDir, 11, 1, 1,1, 0.1, 0.1, GridBagConstraints.WEST);
          //     AddToCenterGrid(jPanelFirefoxDir, 12, 1, 1,1, 0.1, 0.1, GridBagConstraints.WEST);
          //       AddToCenterGrid(jPanelDownloadDir, 13, 1, 1,1, 0.1, 0.1, GridBagConstraints.WEST);
 
     
    //        jPanelCenter.setLayout(new BoxLayout( jPanelCenter , BoxLayout.Y_AXIS));
   //    jPanelCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    //     jPanelCenter.add(jPanelRecentFiles);
   //      jPanelCenter.add(jPanelChromeDir);
   //     jPanelCenter.add(jPanelFirefoxDir);
   //     jPanelCenter.add(jPanelDownloadDir);
      
      
         add(jPanelNewOpen, BorderLayout.NORTH);
         add(jPanelCenter, BorderLayout.CENTER);
         add(jPanelDefaultEmailOptions, BorderLayout.SOUTH);
              String[] RFilesArray = recentfiles.split(",");


setRecentFiles(RFilesArray);
  if(downloadPath!=null)
   {
   setjTextFieldDownloadDir(downloadPath);
   }
   if(firefoxPath!=null)
   {
    setjTextFieldFirefox(firefoxPath);
   }
     if(chromePath!=null)
   {
   setjTextFieldChrome(chromePath);
   }
         pack();
        
        
  } 
  public final void AddToCenterGrid( Component component, int row, int column, int width, int height, double weightx, double weighty, int anchor_value)
     {
         Constraints.gridx = column;
         Constraints.gridy = row;
         Constraints.gridwidth = width;
         Constraints.gridheight = height;
         Constraints.weightx = weightx;
         Constraints.weighty = weighty;
         Constraints.anchor = anchor_value;
         jPanelCenter.add(component, Constraints);
       }  
    public final void AddToGrid( Component component, int row, int column, int width, int height, double weightx, double weighty, int anchor_value)
     {
         Constraints.gridx = column;
         Constraints.gridy = row;
         Constraints.gridwidth = width;
         Constraints.gridheight = height;
         Constraints.weightx = weightx;
         Constraints.weighty = weighty;
         Constraints.anchor = anchor_value;
         jPanelDefaultEmailOptions.add(component, Constraints);
       }

    }