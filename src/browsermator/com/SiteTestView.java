package browsermator.com;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Map;
import java.util.Properties;
import javax.swing.JInternalFrame;



public class SiteTestView extends JInternalFrame {
String RecentFile1;
String RecentFile2;
String RecentFile3;
String RecentFile4;
String RecentFile5;

           

   SiteTestView() {
   RecentFile1 = "";
   RecentFile2 = "";
   RecentFile3 = "";
   RecentFile4 = "";
   RecentFile5 = "";
   
       initComponents();
             Properties applicationProps = new Properties();
             Boolean file_exists = false;
try
{
    File f = new File("browsermator_config.properties");
if(f.exists() && !f.isDirectory()) { 
   file_exists = true;
}
if (file_exists == false)
{
    CreateConfigFile();
}
     FileInputStream input = new FileInputStream("browsermator_config.properties");
applicationProps.load(input);
input.close();
}
catch (Exception e) {
			System.out.println("Exception loading config settings: " + e);
		} 
String recentfiles = applicationProps.getProperty("recentfiles");
String[] RFilesArray = recentfiles.split(",");
   setRecentFiles(RFilesArray);
        
    }
  public final void CreateConfigFile()
  {
      File newconfig = new File("browsermator_config.properties");
      Properties newProps = new Properties();
      newProps.setProperty("email_subect", "");
      newProps.setProperty("email_to", "");
      newProps.setProperty("email_login_password", "");
      newProps.setProperty("email_from", "");
      newProps.setProperty("email_login_name", "");
      newProps.setProperty("smtp_hostname", "");
     newProps.setProperty("recentfiles", " , , , , , ");
              try {
  
    
    
    FileWriter writer = new FileWriter(newconfig);
    newProps.store(writer, "browsermator_settings");
    writer.close();
} 

    catch (Exception e) {
			System.out.println("Exception writing config: " + e);
		}    
      
  }
  public void RemoveRecentFile(int index)
  {
    
            Properties applicationProps = new Properties();
try
{
     FileInputStream input = new FileInputStream("browsermator_config.properties");
applicationProps.load(input);
input.close();
}
catch (Exception e) {
			System.out.println("Exception loading config settings: " + e);
		} 
String recentfiles = applicationProps.getProperty("recentfiles");

String outarray[];

outarray = recentfiles.split(",");
index--;

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
   
  public void addjButtonNewWebsiteTestActionListener(ActionListener listener)
    {
    jButtonNewFile.addActionListener(listener);
    } 

  public void addJButtonOpenWebSiteTestActionListener(ActionListener listener)
  {
      jButtonOpenFile.addActionListener(listener);
  }
    public void addJButtonSaveEmailConfigActionListener(ActionListener listener)
  {
      jButtonSaveEmailConfig.addActionListener(listener);
  }
  
    public String getSMTPHostname ()
    {
        String SMTPHostname = jTextFieldSMTPHostName.getText();
        return SMTPHostname;
       
    }
    public void addRecentFile (String filename)
    {
            Properties applicationProps = new Properties();
try
{
     FileInputStream input = new FileInputStream("browsermator_config.properties");
applicationProps.load(input);
input.close();
String recentfiles = applicationProps.getProperty("recentfiles");

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
    
  
    System.arraycopy(inarray, 0, outarray, 1, 4);
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
catch (Exception e) {
			System.out.println("Exception loading config settings: " + e);
		} 


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
    
    
    public String[] getRecentFiles()
    {
        String[] RecentFiles = null;
        
        RecentFiles[0] = jLabelRecentFile1.getText();
        RecentFiles[1] = jLabelRecentFile2.getText();
        RecentFiles[2] = jLabelRecentFile3.getText();
        RecentFiles[3] = jLabelRecentFile4.getText();
        RecentFiles[4] = jLabelRecentFile5.getText();
        
        return RecentFiles;
     
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
            jLabelRecentFile2.setForeground(Color.blue);
            Font thisfont2 = jLabelRecentFile2.getFont();
            Map attributes2 = thisfont2.getAttributes();
            attributes2.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile2.setFont(thisfont2.deriveFont(attributes2));
            attributes2.clear();
            break;
            case (3):
            jLabelRecentFile3.setForeground(Color.blue);
            jLabelRecentFile3.setForeground(Color.blue);
            Font thisfont3 = jLabelRecentFile3.getFont();
            Map attributes3 = thisfont3.getAttributes();
            attributes3.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile3.setFont(thisfont3.deriveFont(attributes3));
            attributes3.clear();
            break;
            case (4):
            jLabelRecentFile4.setForeground(Color.blue);
            jLabelRecentFile4.setForeground(Color.blue);
            Font thisfont4 = jLabelRecentFile4.getFont();
            Map attributes4 = thisfont4.getAttributes();
            attributes4.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile4.setFont(thisfont4.deriveFont(attributes4));
            attributes4.clear();
            break;
            case (5):
            jLabelRecentFile5.setForeground(Color.blue);
            jLabelRecentFile5.setForeground(Color.blue);
            Font thisfont5 = jLabelRecentFile5.getFont();
            Map attributes5 = thisfont5.getAttributes();
            attributes5.put(TextAttribute.UNDERLINE, -1);
            jLabelRecentFile5.setFont(thisfont5.deriveFont(attributes5));
            attributes5.clear();
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
         RecentFile2 = filename;
      }  
       if (count==3)
       {
         jLabelRecentFile3.setText(filename);
         RecentFile3 = filename;
       }
       if (count==4)
       {
         jLabelRecentFile4.setText(filename);
         RecentFile4 = filename;
       }
       if (count==5)
       {
         jLabelRecentFile5.setText(filename);
         RecentFile5 = filename;
       }

         count++;
     }
     StoreRecentFiles(filenames);
    }
    public void StoreRecentFiles (String[] filenames)
    {
           Properties applicationProps = new Properties();
try
{
     FileInputStream input = new FileInputStream("browsermator_config.properties");
applicationProps.load(input);
input.close();
}
catch (Exception e) {
			System.out.println("Exception loading email settings: " + e);
		} 

        File configFile = new File("browsermator_config.properties");
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
try {
  
    applicationProps.setProperty("recentfiles", commastringfilenames);
    
    FileWriter writer = new FileWriter(configFile);
    applicationProps.store(writer, "recentfiles");
    writer.close();
} 

    catch (Exception e) {
			System.out.println("Exception: " + e);
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
        jLabelVersion.setText(version);
        
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldSMTPHostName = new javax.swing.JTextField();
        jTextFieldEmailLoginName = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldEmailTo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldEmailFrom = new javax.swing.JTextField();
        jButtonNewFile = new javax.swing.JButton();
        jButtonOpenFile = new javax.swing.JButton();
        jButtonSaveEmailConfig = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldSubject = new javax.swing.JTextField();
        jLabelVersion = new javax.swing.JLabel();
        jTextFieldEmailPassword = new javax.swing.JPasswordField();
        jLabelRecentFiles = new javax.swing.JLabel();
        jLabelRecentFile1 = new javax.swing.JLabel();
        jLabelRecentFile2 = new javax.swing.JLabel();
        jLabelRecentFile3 = new javax.swing.JLabel();
        jLabelRecentFile4 = new javax.swing.JLabel();
        jLabelRecentFile5 = new javax.swing.JLabel();

        jLabel1.setText("Email Configuration:");

        jLabel2.setText("SMTPHostname:");

        jLabel4.setText("Email Login Name:");

        jLabel5.setText("Email Login Password:");

        jTextFieldSMTPHostName.setText("smtp.gmail.com");

        jLabel6.setText("E-mail To:");

        jLabel7.setText("E-mail From:");

        jButtonNewFile.setText("New File");

        jButtonOpenFile.setText("Open File");

        jButtonSaveEmailConfig.setText("Save Default Settings");

        jLabel3.setText("Title/Subject:");

        jLabelVersion.setText("Config file browsermator_config.properties is missing....");

        jTextFieldEmailPassword.setText("jPasswordField1");

        jLabelRecentFiles.setText("Recent Files:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextFieldSMTPHostName)
                                    .addComponent(jTextFieldEmailLoginName)
                                    .addComponent(jTextFieldEmailTo)
                                    .addComponent(jTextFieldEmailFrom, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                                    .addComponent(jTextFieldSubject)
                                    .addComponent(jTextFieldEmailPassword))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonSaveEmailConfig))
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonNewFile, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(87, 87, 87)
                                .addComponent(jLabelRecentFiles))
                            .addComponent(jButtonOpenFile, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelRecentFile5)
                            .addComponent(jLabelRecentFile2)
                            .addComponent(jLabelRecentFile1)
                            .addComponent(jLabelRecentFile3)
                            .addComponent(jLabelRecentFile4)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelVersion)))
                .addContainerGap(149, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonNewFile, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelRecentFiles)
                            .addComponent(jLabelRecentFile1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelRecentFile2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelRecentFile3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelRecentFile4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonOpenFile, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelRecentFile5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldSMTPHostName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldEmailLoginName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldEmailPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldEmailTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldEmailFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSaveEmailConfig))
                .addGap(31, 31, 31)
                .addComponent(jLabelVersion)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonNewFile;
    private javax.swing.JButton jButtonOpenFile;
    private javax.swing.JButton jButtonSaveEmailConfig;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelRecentFile1;
    private javax.swing.JLabel jLabelRecentFile2;
    private javax.swing.JLabel jLabelRecentFile3;
    private javax.swing.JLabel jLabelRecentFile4;
    private javax.swing.JLabel jLabelRecentFile5;
    private javax.swing.JLabel jLabelRecentFiles;
    private javax.swing.JLabel jLabelVersion;
    private javax.swing.JTextField jTextFieldEmailFrom;
    private javax.swing.JTextField jTextFieldEmailLoginName;
    private javax.swing.JPasswordField jTextFieldEmailPassword;
    private javax.swing.JTextField jTextFieldEmailTo;
    private javax.swing.JTextField jTextFieldSMTPHostName;
    private javax.swing.JTextField jTextFieldSubject;
    // End of variables declaration//GEN-END:variables

}
