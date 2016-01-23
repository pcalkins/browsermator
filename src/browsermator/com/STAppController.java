package browsermator.com;


import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.ButtonGroup;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.SAVE_DIALOG;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public final class STAppController extends JFrame {
 
private final SiteTestView Navigator;
public JDesktopPane SeleniumToolDesktop;
private UIManager.LookAndFeelInfo LAFOptions[];
private JRadioButtonMenuItem LookAndFeelOptions[];
private ButtonGroup LookAndFeelGroup;
     private JMenuBar menuBar;
     private JMenu fileMenu;
     private JMenuItem openMenuItem;
     private JMenuItem saveMenuItem;
     private JMenuItem exitMenuItem;
     private JMenu jMenuView;
     private JMenu jMenuThemes;
     private JMenu helpMenu;
     private JMenuItem contentsMenuItem;
     private JMenuItem aboutMenuItem;
     private JMenuItem newFileItem;
     private JMenuItem importFileItem;
     private JMenuItem saveAsMenuItem;
      String filename;
      private JMenuItem importMenuItem;
private final String version = "b3";
    private int CurrentMDIWindowIndex;
   private final String ProgramVersion = "b3";
 
     ArrayList<SeleniumTestTool> MDIClasses = new ArrayList();


  public STAppController(String[] args) throws PropertyVetoException {
   
     super ("Browermator");
    super.setExtendedState( super.getExtendedState()|JFrame.MAXIMIZED_BOTH );
 //   this.setSize(1024, 768);
     super.setVisible(true);
   
    Navigator = new SiteTestView();
  //  String RecentFiles[] = null;
 //   RecentFiles = Navigator.getRecentFiles();
 //   MouseListener[] RecentFilesListeners = null;
   
   
   
    super.addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent windowEvent) {
     int closure = 0;
        for (SeleniumTestTool openwindow : MDIClasses)
      { 
        closure  =  CheckToSaveChanges(openwindow);
           
      if (closure==1)
      {
      openwindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
      
       openwindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      }
    }
    
     
        }
    });
          

   
    
     Navigator.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
     Navigator.addInternalFrameListener(new InternalFrameAdapter(){
    @Override
    public void internalFrameDeactivated(    InternalFrameEvent event){
      JInternalFrame[] iframes = SeleniumToolDesktop.getAllFrames();
                for (JInternalFrame iframe : iframes)
                {
                  
                      
                       String thisFrameName = iframe.getTitle();
                       if ("".equals(thisFrameName))
                       {
                           SeleniumToolDesktop.setComponentZOrder(iframe, iframes.length-1);
                       }
                  
                }   
    
    }
        @Override
    public void internalFrameActivated(    InternalFrameEvent event){
      JInternalFrame[] iframes = SeleniumToolDesktop.getAllFrames();

      for (JInternalFrame iframe : iframes)
                {
                  
                      
                       String thisFrameName = iframe.getTitle();
                       if ("".equals(thisFrameName))
                       {
                           SeleniumToolDesktop.setComponentZOrder(iframe, iframes.length-1);
                        
                       }
                
                 
                 
                }   
    
    }
  }
); 
 
               
     SeleniumToolDesktop = new JDesktopPane();
     SeleniumToolDesktop.setSize(1200,800);
     SeleniumToolDesktop.setVisible(true);
     
    
    
  try
  {
      LoadGlobalEmailSettings();
  }
    catch (Exception e) {
	System.out.println("Exception on email settings: " + e);
    } 
   
    
    CurrentMDIWindowIndex = -1;
     
Navigator.setVisible(true);
Navigator.setSize(1000,750);
//Navigator.pack();
 initComponents();

 add(SeleniumToolDesktop);

SeleniumToolDesktop.add(Navigator);
      try
        {
        Navigator.setMaximum(true);
        }
        catch (PropertyVetoException e)
        {
            System.out.println("setting maximum error" + e);
        }
    
  if (args.length>0) { CheckArgs(args);}
  
  CurrentMDIWindowIndex = GetCurrentWindow();  
  if (CurrentMDIWindowIndex == -1)
  {
  saveMenuItem.setEnabled(false);
  }
  
  addFileMenuImportActionListener(
      new ActionListener() {
           public void actionPerformed (ActionEvent evt) {
            
       
         
                 CurrentMDIWindowIndex = GetCurrentWindow();
                 if (CurrentMDIWindowIndex !=-1)
                 {
              File[] newfiles = BrowseForFile();
 if (newfiles !=null)
 {
     for (int fileindex = 0; fileindex<newfiles.length; fileindex++)
     {
 int MDI_CLASS_INDEX;
   
      ImportFile(newfiles[fileindex], CurrentMDIWindowIndex);
    
   }    
 }     
 } 
                 else
                  
  {
    JOptionPane.showMessageDialog (null, "No Active Window to import into. Click to select a Window.", "No Selected Window", JOptionPane.INFORMATION_MESSAGE);   
  }
      }           
           });
  
  addFileMenuSaveActionListener(
      new ActionListener() {
           public void actionPerformed (ActionEvent evt) {
                  try {
       
         
                 CurrentMDIWindowIndex = GetCurrentWindow();
                 if (CurrentMDIWindowIndex !=-1)
                 {
                     SeleniumTestTool STAppFrame = MDIClasses.get(CurrentMDIWindowIndex);
                 
                     try {   
                       int saved =   SaveFile(STAppFrame, false);
                     } catch (XMLStreamException ex) {
                         Logger.getLogger(STAppController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }
                   else
  {
    JOptionPane.showMessageDialog (null, "No Active Window to save. Click to select a Window.", "No Selected Window", JOptionPane.INFORMATION_MESSAGE);   
  }
                  }
               catch (IOException ex)
               {
                 
        System.out.println ("Save IO error" + ex.toString());
      
   
               }
            
           } 
       });
  addFileMenuSaveAsActionListener(
      new ActionListener() {
           public void actionPerformed (ActionEvent evt) {
                  try {
       
         
                 CurrentMDIWindowIndex = GetCurrentWindow();
                 if (CurrentMDIWindowIndex !=-1)
                 {
                     SeleniumTestTool STAppFrame = MDIClasses.get(CurrentMDIWindowIndex);
                 
                     try {   
                       int saved =   SaveFile(STAppFrame, true);
                     } catch (XMLStreamException ex) {
                         Logger.getLogger(STAppController.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }
                   else
  {
    JOptionPane.showMessageDialog (null, "No Active Window to save. Click to select a Window.", "No Selected Window", JOptionPane.INFORMATION_MESSAGE);   
  }
                  }
               catch (IOException ex)
               {
                 
        System.out.println ("Save IO error" + ex.toString());
      
   
               }
            
           } 
       });
  
 addFileMenuNewActionListener(
  new ActionListener()
  {
  public void actionPerformed (ActionEvent evt)
  {
     if (MDIClasses.size() == 0) 
           {
          filename = "untitled";
           }
           else
           {
          filename = "untitled" + MDIClasses.size();
           }
  
 
      SeleniumTestTool STAppFrame = new SeleniumTestTool(filename);

  SeleniumToolDesktop.add(STAppFrame);
  STAppFrame.moveToFront();
  try
  {
  STAppFrame.setSelected(true);
  }
  catch (PropertyVetoException e)
  {
      System.out.println("Veto :" + e.toString());
  }
          
  // STAppFrame.REFTHEMDIFRAME.moveToFront();
  saveMenuItem.setEnabled(true);
  SeleniumToolDesktop.repaint();
  MDIClasses.add(STAppFrame);
 STAppFrame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
     @Override 
     public void internalFrameClosing(InternalFrameEvent e) {
                    int closed =  CheckToSaveChanges(STAppFrame);
           
      if (closed==1)
      {
      STAppFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
       MDIClasses.remove(MDIClasses.size()-1); 
       STAppFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      }
  
     
     
     
      }
    });
 
    STAppFrame.addjButtonDoStuffActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
 STAppFrame.RunActions(); 
 
  
        }
      }
    );
    STAppFrame.addjButtonClearEmailSettingsListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
 STAppFrame.ClearEmailSettings(); 
 
  
        }
      }
    );
   
   STAppFrame.addTargetBrowserItemListener( new ItemListener() {
    
        public void itemStateChanged (ItemEvent e )
        {
         if ((e.getStateChange() == ItemEvent.SELECTED)) {
            Object ActionType = e.getItem();
            String TargetBrowser = ActionType.toString();
           STAppFrame.TargetBrowser = TargetBrowser;
           STAppFrame.changes = true;
           if (STAppFrame.TargetBrowser=="Chrome")
           {
              STAppFrame.setOSTypeActive(true);
           }
           else
           {
            STAppFrame.setOSTypeActive(false);   
           }
         }
        }
        
        });
   
    STAppFrame.addjButtonNewBugActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
   STAppFrame.AddNewBug();  

        }
                                          
      }
    );
  
  
  

  }
  }
    ); 
   
  addFileMenuOpenActionListener(
  new ActionListener()
          {
          public void actionPerformed (ActionEvent evt)
          {
          
 
   File[] newfiles = BrowseForFile();
 if (newfiles !=null)
 {
     for (int fileindex = 0; fileindex<newfiles.length; fileindex++)
     {
   try {
        int MDI_CLASS_INDEX;
           MDI_CLASS_INDEX = OpenFile(newfiles[fileindex], MDIClasses);
         if (MDI_CLASS_INDEX>=0)
     {
           DisplayWindow(MDI_CLASS_INDEX);
     }
       }
       catch (IOException | ClassNotFoundException ex) {
          System.out.println(ex.toString());
       } 
    
   }    
 }     

  
          
          }  });
            
  Navigator.addRecentFile1MouseListener(
  new MouseListener()
          {
              @Override
            public void mouseReleased(MouseEvent e) {
            
            }
            @Override
            public void mousePressed(MouseEvent e) {
           Navigator.setMouseClickColor(1);
            }
            @Override
            public void mouseExited(MouseEvent e) {
          Navigator.setMouseOutColor(1);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
          Navigator.setMouseOverColor(1);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
          
         String RecentFileName = Navigator.getRecentFile1();
         File RecentFile = new File(RecentFileName);
        if (!"".equals(RecentFileName))
 {
     
     
 int MDI_CLASS_INDEX;
       try {
           MDI_CLASS_INDEX = OpenFile(RecentFile, MDIClasses);
        if (MDI_CLASS_INDEX>=0)
             {

        DisplayWindow(MDI_CLASS_INDEX);
               }
               else
               {
               JOptionPane.showMessageDialog (null, "The file " + RecentFile, "does not exist or is corrupt", JOptionPane.INFORMATION_MESSAGE);    
               }
          
       }
       catch (IOException | ClassNotFoundException | HeadlessException ex) {
          System.out.println(ex.toString());
       }
            }
     

          
          }  });
  Navigator.addRecentFile2MouseListener(
  new MouseListener()
          {
               @Override
            public void mouseReleased(MouseEvent e) {
            
            }
            @Override
            public void mousePressed(MouseEvent e) {
           Navigator.setMouseClickColor(2);
            }
            @Override
            public void mouseExited(MouseEvent e) {
          Navigator.setMouseOutColor(2);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
          Navigator.setMouseOverColor(2);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
          
         String RecentFileName = Navigator.getRecentFile2();
         File RecentFile = new File(RecentFileName);
        if (!"".equals(RecentFileName))
 {
     
     
 int MDI_CLASS_INDEX;
       try {
           MDI_CLASS_INDEX = OpenFile(RecentFile, MDIClasses);
 if (MDI_CLASS_INDEX>=0)
     {
        DisplayWindow(MDI_CLASS_INDEX);
           }
       }
       catch (IOException | ClassNotFoundException | HeadlessException ex) {
          System.out.println(ex.toString());
       }
            }
     

          
          }  });
  Navigator.addRecentFile3MouseListener(
  new MouseListener()
          {
               @Override
            public void mouseReleased(MouseEvent e) {
            
            }
            @Override
            public void mousePressed(MouseEvent e) {
           Navigator.setMouseClickColor(3);
            }
            @Override
            public void mouseExited(MouseEvent e) {
          Navigator.setMouseOutColor(3);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
          Navigator.setMouseOverColor(3);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
          
         String RecentFileName = Navigator.getRecentFile3();
         File RecentFile = new File(RecentFileName);
        if (!"".equals(RecentFileName))
 {
     
     
 int MDI_CLASS_INDEX;
       try {
           MDI_CLASS_INDEX = OpenFile(RecentFile, MDIClasses);
        if (MDI_CLASS_INDEX>=0)
     {
        DisplayWindow(MDI_CLASS_INDEX);
           }
       }
       catch (Exception ex) {
          System.out.println(ex.toString());
       }
            }
     

          
          }  });
  
  Navigator.addRecentFile4MouseListener(
  new MouseListener()
          {
               @Override
            public void mouseReleased(MouseEvent e) {
            
            }
            @Override
            public void mousePressed(MouseEvent e) {
           Navigator.setMouseClickColor(4);
            }
            @Override
            public void mouseExited(MouseEvent e) {
          Navigator.setMouseOutColor(4);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
          Navigator.setMouseOverColor(4);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
          
         String RecentFileName = Navigator.getRecentFile4();
         File RecentFile = new File(RecentFileName);
        if (!"".equals(RecentFileName))
 {
     
     
 int MDI_CLASS_INDEX;
       try {
           MDI_CLASS_INDEX = OpenFile(RecentFile, MDIClasses);
  if (MDI_CLASS_INDEX>=0)
     {
        DisplayWindow(MDI_CLASS_INDEX);
           }
       }
       catch (IOException | ClassNotFoundException ex) {
          System.out.println(ex.toString());
       }
       
            }
     

          
          }  });
  
  Navigator.addRecentFile5MouseListener(
  new MouseListener()
          {
               @Override
            public void mouseReleased(MouseEvent e) {
            
            }
            @Override
            public void mousePressed(MouseEvent e) {
           Navigator.setMouseClickColor(5);
            }
            @Override
            public void mouseExited(MouseEvent e) {
          Navigator.setMouseOutColor(5);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
          Navigator.setMouseOverColor(5);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
          
         String RecentFileName = Navigator.getRecentFile5();
         File RecentFile = new File(RecentFileName);
        if (!"".equals(RecentFileName))
 {
     
     
 int MDI_CLASS_INDEX;
       try {
           MDI_CLASS_INDEX = OpenFile(RecentFile, MDIClasses);
       if (MDI_CLASS_INDEX>=0)
     {
        DisplayWindow(MDI_CLASS_INDEX);
           }
       }
       catch (IOException | ClassNotFoundException | HeadlessException ex) {
          System.out.println(ex.toString());
       }
            }
     

          
          }  });
  
  Navigator.addJButtonSaveEmailConfigActionListener(
          new ActionListener()
          {
              public void actionPerformed (ActionEvent evt)
              {
                  try
                  {
              SaveGlobalEmailSettings();
                  }
               catch (Exception e) {
			System.out.println("Exception: " + e);
		} 
              }
          });
  
  
  
  Navigator.addJButtonOpenWebSiteTestActionListener(
  new ActionListener()
          {
          public void actionPerformed (ActionEvent evt)
          {
        File[] newfiles = BrowseForFile();
 if (newfiles !=null)
 {
     for (int fileindex = 0; fileindex<newfiles.length; fileindex++)
     {
   try {
        int MDI_CLASS_INDEX;
           MDI_CLASS_INDEX = OpenFile(newfiles[fileindex], MDIClasses);
         if (MDI_CLASS_INDEX>=0)
     {
           DisplayWindow(MDI_CLASS_INDEX);
     }
       }
       catch (IOException | ClassNotFoundException ex) {
          System.out.println(ex.toString());
       } 
    
   }    
 }     

          
          }  });
        
      

     

  Navigator.addjButtonNewWebsiteTestActionListener(
  new ActionListener()
  {
  public void actionPerformed (ActionEvent evt)
  {
      if (MDIClasses.size() == 0) 
           {
          filename = "untitled";
           }
           else
           {
          filename = "untitled" + MDIClasses.size();
           }
  
 
 
  SeleniumTestTool STAppFrame = new SeleniumTestTool(filename);
   SeleniumToolDesktop.add(STAppFrame);
  STAppFrame.moveToFront();
  try
  {
  STAppFrame.setSelected(true);
  }
  catch (PropertyVetoException e)
  {
      System.out.println("Veto: " + e.toString());
  }
  saveMenuItem.setEnabled(true);
  SeleniumToolDesktop.repaint();
  MDIClasses.add(STAppFrame);
  
  STAppFrame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
     @Override 
     public void internalFrameClosing(InternalFrameEvent e) {
                    int closed =  CheckToSaveChanges(STAppFrame);
           
      if (closed==1)
      {
      STAppFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
       MDIClasses.remove(MDIClasses.size()-1); 
       STAppFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      }
        
     
      }
    });
 
    STAppFrame.addjButtonDoStuffActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
 STAppFrame.RunActions(); 
 
  
        }
      }
    );
      STAppFrame.addjButtonClearEmailSettingsListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
 STAppFrame.ClearEmailSettings(); 
 
  
        }
      }
    );
  STAppFrame.addTargetBrowserItemListener( new ItemListener() {
    
        public void itemStateChanged (ItemEvent e )
        {
         if ((e.getStateChange() == ItemEvent.SELECTED)) {
            Object ActionType = e.getItem();
            String TargetBrowser = ActionType.toString();
           STAppFrame.TargetBrowser = TargetBrowser;
           STAppFrame.changes = true;
           if (STAppFrame.TargetBrowser=="Chrome")
           {
              STAppFrame.setOSTypeActive(true);
           }
           else
           {
            STAppFrame.setOSTypeActive(false);   
           }
         }
        }
        
        }); 
      
    STAppFrame.addjButtonNewBugActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
   STAppFrame.AddNewBug();  
 
 
  }
                                          
      }
    );
  
  
  

  }
  }
    ); 
  
  
  }
  private int CheckToSaveChanges(SeleniumTestTool STAppFrame) 
  {

ArrayList<String> AllFieldValuesCheck = new ArrayList<>();
AllFieldValuesCheck.add(STAppFrame.OSType);
AllFieldValuesCheck.add(STAppFrame.TargetBrowser);
String stringWaitTime = String.valueOf(STAppFrame.GetWaitTime());
AllFieldValuesCheck.add(stringWaitTime);
AllFieldValuesCheck.add(STAppFrame.getSMTPHostname());
AllFieldValuesCheck.add(STAppFrame.getEmailFrom());
AllFieldValuesCheck.add(STAppFrame.getEmailLoginName());
AllFieldValuesCheck.add(STAppFrame.getEmailPassword());
AllFieldValuesCheck.add(STAppFrame.getEmailTo());
AllFieldValuesCheck.add(STAppFrame.getSubject());
String sthisbool = "false";
if (STAppFrame.getEmailReport())
{
    sthisbool = "true";
}
AllFieldValuesCheck.add(sthisbool);
sthisbool = "false";
if (STAppFrame.getEmailReportFail())
{
    sthisbool = "true";
}
AllFieldValuesCheck.add(sthisbool);
sthisbool = "false";
if (STAppFrame.getExitAfter())
{
    sthisbool = "true";
}
AllFieldValuesCheck.add(sthisbool);
sthisbool = "false";
if (STAppFrame.getPromptToClose())
{
    sthisbool = "true";
}
AllFieldValuesCheck.add(sthisbool);
sthisbool = "false";
if (STAppFrame.getShowReport())
{
    sthisbool = "true";
}
AllFieldValuesCheck.add(sthisbool);
for (Procedure thisproc: STAppFrame.BugArray)
{
    AllFieldValuesCheck.add(thisproc.BugTitle);
 
    for (Action thisact: thisproc.ActionsList)
    {
        AllFieldValuesCheck.add(thisact.Variable1);
       
        AllFieldValuesCheck.add(thisact.Variable2);
        String checkingboolval1 = "false";
        if (thisact.BoolVal1)
        {
            checkingboolval1 = "true";
        }
        
        AllFieldValuesCheck.add(checkingboolval1);
    }
}
if (!STAppFrame.AllFieldValues.equals(AllFieldValuesCheck))
{
   STAppFrame.changes =  true;
   
}

if (STAppFrame.changes==false)
{
  return 0;
}
else
{
  int result = JOptionPane.showConfirmDialog(STAppFrame,"Do you wish to save changes to " + filename + "?","Browsermator",JOptionPane.YES_NO_CANCEL_OPTION);
            switch(result){
                  case JOptionPane.YES_OPTION:
                 //   SaveFile
                      try
                      {
                          int saved = 0;
                      saved = SaveFile(STAppFrame, false);
                      
                      return saved;
                      }
                      catch (Exception e)
                      {
                        System.out.println("Save Changes exception:" + e.toString());  
                        return 0;
                      }
                     
                case JOptionPane.NO_OPTION:
                // close window  
                //    System.out.println("close");
                    return 0;
                case JOptionPane.CLOSED_OPTION:
           
                    return 1;
                case JOptionPane.CANCEL_OPTION:
             
                    return 1;
                   
            }
}
return 1;
  }
   private void fileMenuActionPerformed(java.awt.event.ActionEvent evt) {                                         
    }
   
      private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
        System.exit(0);
    }
  
      
        @SuppressWarnings("unchecked")
    private void initComponents() {
   
      menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        newFileItem = new javax.swing.JMenuItem();
        jMenuView = new javax.swing.JMenu();
        jMenuThemes = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        importMenuItem = new javax.swing.JMenuItem();
        newFileItem.setMnemonic('n');
        newFileItem.setText("New");
        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        importMenuItem.setMnemonic('i');
        importMenuItem.setText("Import");
        fileMenu.add(newFileItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(importMenuItem);
        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        saveAsMenuItem.setText("Save As");
        saveAsMenuItem.setMnemonic('a');
             
                  LAFOptions = UIManager.getInstalledLookAndFeels();
   LookAndFeelOptions = new JRadioButtonMenuItem[LAFOptions.length];
   LookAndFeelGroup = new ButtonGroup();
 
  for (int count = 0; count<LAFOptions.length; count++)
  {    
     LookAndFeelOptions[count] = new JRadioButtonMenuItem(LAFOptions[count].getName());
     jMenuThemes.add(LookAndFeelOptions[count]);
     LookAndFeelGroup.add(LookAndFeelOptions[count]);
     LookAndFeelOptions[count].addItemListener( new ItemListener() {
    
        public void itemStateChanged (ItemEvent e )
        {
            for (int count = 0; count < LAFOptions.length; count++ )
            {
                if (LookAndFeelOptions[count].isSelected())
                try
                {
                    UIManager.setLookAndFeel(LAFOptions[count].getClassName());
                    SwingUtilities.updateComponentTreeUI( getContentPane()  );
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }
    });
     
   
  
  }
  
  LookAndFeelOptions[0].setSelected( true );
        fileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuActionPerformed(evt);
            }
        });

      

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
      
        fileMenu.add(exitMenuItem);
        
        

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);
 fileMenu.add(saveAsMenuItem);
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);
        
            fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

 

      
        jMenuThemes.setText("Themes");
        
        
        jMenuView.setText("View");
       
        menuBar.add(jMenuView);
        jMenuView.add(jMenuThemes);
        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentsMenuItem.setMnemonic('c');
        contentsMenuItem.setText("Contents");
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);
   
  


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
    }  
    
   public void addFileMenuNewActionListener (ActionListener listener)
   {
       newFileItem.addActionListener(listener);
   }
    public void addFileMenuSaveActionListener (ActionListener listener) {
       saveMenuItem.addActionListener(listener);
   }
   public void addFileMenuSaveAsActionListener (ActionListener listener) {
    saveAsMenuItem.addActionListener(listener);
   }
   public void addFileMenuImportActionListener (ActionListener listener)
   {
       importMenuItem.addActionListener(listener);
   }
   public void addFileMenuOpenActionListener (ActionListener listener) {
       openMenuItem.addActionListener(listener);
      
   }
   

  public int SaveFile(SeleniumTestTool STAppFrame, boolean isSaveAs) throws IOException, XMLStreamException
    {

      final JFileChooser fc = new JFileChooser(){
    @Override
    public void approveSelection(){
        
        File f = getSelectedFile();
                String filestring = f.toString();
                
                String[] left_side_of_dot = filestring.split("\\.");
                
                 f = new File(left_side_of_dot[0] + ".browsermation");
       
       
        if(f.exists() && getDialogType() == SAVE_DIALOG){
            int result = JOptionPane.showConfirmDialog(STAppFrame,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
            switch(result){
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                 
                    return;
                case JOptionPane.NO_OPTION:
                    return;
                case JOptionPane.CLOSED_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }
        super.approveSelection();
    STAppFrame.changes = false;
    }
};
File file;
   
    if (isSaveAs==true || STAppFrame.filename.contains("untitled") == true)
    {
     FileNameExtensionFilter filefilter = new FileNameExtensionFilter("Browsermator file (*.browsermation)","browsermation");

    fc.setFileFilter(filefilter);
    if (STAppFrame.filename.contains("untitled") == false  && isSaveAs==true)
    {
          String[] left_side_of_dot = STAppFrame.filename.split("\\.");
                
                 file = new File(left_side_of_dot[0] + ".browsermation");
        fc.setSelectedFile(file);
        
    }
int returnVal = fc.showSaveDialog(STAppFrame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                String filestring = file.toString();
                
                String[] left_side_of_dot = filestring.split("\\.");
                
                 file = new File(left_side_of_dot[0] + ".browsermation");
            
            }
            else
            {
               return 1;
            }
    }
    else
    {
    
         String filestring = STAppFrame.filename;
                
                String[] left_side_of_dot = filestring.split("\\.");
                
                 file = new File(left_side_of_dot[0] + ".browsermation");
    }
  

               XMLStreamWriter xmlfile = XMLOutputFactory.newInstance().createXMLStreamWriter( new BufferedOutputStream(
                              new FileOutputStream(file)));
     
             try {
xmlfile.writeStartElement("BrowserMatorWindow");
xmlfile.writeAttribute("Filename",file.getAbsolutePath());
xmlfile.writeAttribute("ProgramVersion", this.ProgramVersion);
String ShowReport = Boolean.toString(STAppFrame.getShowReport());

xmlfile.writeStartElement("FileSettings");

xmlfile.writeStartElement("ShowReport");
    xmlfile.writeCharacters(ShowReport);
    xmlfile.writeEndElement();
// xmlfile.writeAttribute("ShowReport", ShowReport);
String EmailReport = Boolean.toString(STAppFrame.getEmailReport());
  xmlfile.writeStartElement("EmailReport");
    xmlfile.writeCharacters(EmailReport);
    xmlfile.writeEndElement();
// xmlfile.writeAttribute("EmailReport", EmailReport);
String EmailReportFail = Boolean.toString(STAppFrame.getEmailReportFail());
xmlfile.writeStartElement("EmailReportFail");
    xmlfile.writeCharacters(EmailReportFail);
    xmlfile.writeEndElement();
    
// xmlfile.writeAttribute("EmailReportFail", EmailReportFail);
String ExitAfter = Boolean.toString(STAppFrame.getExitAfter());
xmlfile.writeStartElement("ExitAfter");
    xmlfile.writeCharacters(ExitAfter);
    xmlfile.writeEndElement();    
// xmlfile.writeAttribute("ExitAfter", ExitAfter);
   String SMTPHostname = STAppFrame.getSMTPHostname();
xmlfile.writeStartElement("SMTPHostname");
    xmlfile.writeCharacters(SMTPHostname);
    xmlfile.writeEndElement(); 
// xmlfile.writeAttribute("SMTPHostname", STAppFrame.getSMTPHostname());
    
  String EmailLoginName = STAppFrame.getEmailLoginName();
xmlfile.writeStartElement("EmailLoginName");
    xmlfile.writeCharacters(EmailLoginName);
    xmlfile.writeEndElement();     
// xmlfile.writeAttribute("EmailLoginName", STAppFrame.getEmailLoginName());
String PromptToClose = Boolean.toString(STAppFrame.getPromptToClose());
xmlfile.writeStartElement("PromptToClose");
    xmlfile.writeCharacters(PromptToClose);
    xmlfile.writeEndElement();     
// String PromptToClose = Boolean.toString(STAppFrame.getPromptToClose());
//    xmlfile.writeAttribute("PromptToClose", PromptToClose);
String TargetBrowser = STAppFrame.TargetBrowser;
xmlfile.writeStartElement("TargetBrowser");
    xmlfile.writeCharacters(TargetBrowser);
    xmlfile.writeEndElement();   
// xmlfile.writeAttribute("TargetBrowser", TargetBrowser);
    
Integer WaitTime = STAppFrame.GetWaitTime();
String WaitTimeString = WaitTime.toString();
xmlfile.writeStartElement("WaitTime");
    xmlfile.writeCharacters(WaitTimeString);
    xmlfile.writeEndElement();   
// xmlfile.writeAttribute("WaitTime", WaitTimeString);

String OSType = STAppFrame.OSType;
xmlfile.writeStartElement("OSType");
    xmlfile.writeCharacters(OSType);
    xmlfile.writeEndElement();  
// xmlfile.writeAttribute("OSType", OSType);
String EmailPassword = "";
EmailPassword = STAppFrame.getEmailPassword();

String une = "";
      try
      {
     une = Protector.encrypt(EmailPassword);
      }
      catch (Exception e)
      {
      System.out.println("Error encrypting emailpassword: " + e.toString());
      }
      EmailPassword = une;
xmlfile.writeStartElement("EmailPassword");
    xmlfile.writeCharacters(EmailPassword);
    xmlfile.writeEndElement();    
// xmlfile.writeAttribute("EmailPassword", EmailPassword);
String EmailTo = STAppFrame.getEmailTo();
xmlfile.writeStartElement("EmailTo");
    xmlfile.writeCharacters(EmailTo);
    xmlfile.writeEndElement();  
// xmlfile.writeAttribute("EmailTo", STAppFrame.getEmailTo());

String EmailFrom = STAppFrame.getEmailFrom();
xmlfile.writeStartElement("EmailFrom");
    xmlfile.writeCharacters(EmailFrom);
    xmlfile.writeEndElement(); 
// xmlfile.writeAttribute("EmailFrom", STAppFrame.getEmailFrom());
String EmailSubject = STAppFrame.getSubject();
xmlfile.writeStartElement("EmailSubject");
    xmlfile.writeCharacters(EmailSubject);
    xmlfile.writeEndElement();     
// xmlfile.writeAttribute("EmailSubject", STAppFrame.getSubject());
xmlfile.writeEndElement();

for (Procedure thisbug: STAppFrame.BugArray)
{
xmlfile.writeStartElement("Procedure");
xmlfile.writeAttribute("Title", thisbug.BugTitle);
xmlfile.writeAttribute("URL", thisbug.BugURL);
xmlfile.writeAttribute("Pass", Boolean.toString(thisbug.Pass));
String index = String.valueOf(thisbug.index);
xmlfile.writeAttribute("index", index);

    for (Action thisaction: thisbug.ActionsList)
    {
    xmlfile.writeStartElement("Action");

    String LOCKED = Boolean.toString(thisaction.Locked);
   
    xmlfile.writeStartElement("LOCKED");
    xmlfile.writeCharacters(LOCKED);
    xmlfile.writeEndElement();
    
   
        
    String Pass = Boolean.toString(thisaction.Pass);
    xmlfile.writeStartElement("Pass");
    xmlfile.writeCharacters(Pass);
    xmlfile.writeEndElement();
    
    xmlfile.writeStartElement("TimeOfTest");
    xmlfile.writeCharacters(thisaction.TimeOfTest.format(DateTimeFormatter.ISO_DATE_TIME));
    xmlfile.writeEndElement();
    
    xmlfile.writeStartElement("Type");
    xmlfile.writeCharacters(thisaction.Type);
    xmlfile.writeEndElement();
    
    xmlfile.writeStartElement("Variable1");
    xmlfile.writeCharacters(thisaction.Variable1);
    xmlfile.writeEndElement();
    if (thisaction.Type.contains("Password"))
    {
    xmlfile.writeStartElement("Variable2");
    try
    {
    thisaction.Variable2 = Protector.encrypt(thisaction.Variable2);
    }
    catch (Exception e)
            {
            System.out.println("encrypt error.. passvar2: " + e.toString());
            }
    xmlfile.writeCharacters(thisaction.Variable2);
    xmlfile.writeEndElement(); 
    }
    else
    {
    xmlfile.writeStartElement("Variable2");
    xmlfile.writeCharacters(thisaction.Variable2);
    xmlfile.writeEndElement();
    }
    
    xmlfile.writeStartElement("BoolVal1");
    String tempstringbool = "false";
    if (thisaction.BoolVal1)
    {
        tempstringbool = "true";
    }
    xmlfile.writeCharacters(tempstringbool);
    xmlfile.writeEndElement();
    String ActionIndex = Integer.toString(thisaction.index);   
    xmlfile.writeStartElement("ActionIndex");
    xmlfile.writeCharacters(ActionIndex);
    xmlfile.writeEndElement();
    
    xmlfile.writeEndElement();  
    }
    
    xmlfile.writeEndElement();
  
}
xmlfile.writeEndElement();
// STAppFrame.AllFieldValues.clear();


// System.out.println("Successfully Created XML...");
  
 
        } catch (Exception e) {
           System.out.println("Write error:" + e.toString());
 
        } finally {
            xmlfile.flush();
            xmlfile.close();
            STAppFrame.AllFieldValues.clear();
           
STAppFrame.AllFieldValues.add(STAppFrame.OSType);
STAppFrame.AllFieldValues.add(STAppFrame.TargetBrowser);
String stringWaitTime = String.valueOf(STAppFrame.GetWaitTime());
STAppFrame.AllFieldValues.add(stringWaitTime);
STAppFrame.AllFieldValues.add(STAppFrame.getSMTPHostname());
STAppFrame.AllFieldValues.add(STAppFrame.getEmailFrom());
STAppFrame.AllFieldValues.add(STAppFrame.getEmailLoginName());
STAppFrame.AllFieldValues.add(STAppFrame.getEmailPassword());
STAppFrame.AllFieldValues.add(STAppFrame.getEmailTo());
STAppFrame.AllFieldValues.add(STAppFrame.getSubject());


for (Procedure thisproc: STAppFrame.BugArray)
{
    
    STAppFrame.AllFieldValues.add(thisproc.BugTitle);
 
    for (Action thisact: thisproc.ActionsList)
    {
        String checkingboolval1 = "false";
        STAppFrame.AllFieldValues.add(thisact.Variable1);
       
        STAppFrame.AllFieldValues.add(thisact.Variable2);
      
        if (thisact.BoolVal1)
        {
            checkingboolval1 = "true";
        }
        STAppFrame.AllFieldValues.add(checkingboolval1);
    }
}
STAppFrame.changes = false;
        }

this.filename = file.getAbsolutePath();
STAppFrame.setProperties(this.filename);
Navigator.addRecentFile(file.getAbsolutePath());

         return 0;  

        }
  public File[] BrowseForFile()
  {
       
   //    boolean fileexists;
        final JFileChooser fc = new JFileChooser();
fc.setMultiSelectionEnabled(true);

 FileNameExtensionFilter filefilter = new FileNameExtensionFilter("Browsermator file (*.browsermation)","browsermation");

    fc.setFileFilter(filefilter);

int returnVal = fc.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();   
  
 for (int fileindex = 0; fileindex<files.length; fileindex++)
        {
    if (files[fileindex].getAbsoluteFile().getName().contains(".browsermation"))
{
 
}
else
{
   String path = files[fileindex].getAbsolutePath();
    
File newfile = new File(path + ".browsermation");
 files[fileindex] = newfile;
 
}  
  
     
    }
 return files;
            }
            else
            {
               
                return null;
            }
        
            
  }
  public void ImportFile (File file, int MDI_INDEX)
  {
   
        
if(!file.exists()) { 
 
 
}
else
{
        
String full_filename = file.getAbsoluteFile().toString();


   
Document doc=null;
try
{

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();

doc = builder.parse(file.getAbsolutePath());

 
}
catch (ParserConfigurationException | SAXException | IOException e)
{
    System.out.println("DocumentBuilder error:" + e.toString());
   
}
    
finally 
{
 ImportNewWindow(doc, MDI_INDEX);
 MDIClasses.get(MDI_INDEX).changes = true;
}
    
  }

  }
     public int OpenFile (File file, ArrayList<SeleniumTestTool> MDIClasses) throws FileNotFoundException, IOException, ClassNotFoundException
    {
   
  
        
if(!file.exists()) { 
 return -10;
 
}
else
{
        filename = file.getName();
String full_filename = file.getAbsoluteFile().toString();


int MDI_Index = -1;


              Boolean PromptForSameFileName = false;
     String filealreadyopen ="";
     for (SeleniumTestTool thisfile: MDIClasses)
     {
        String twoslashes = "\\" + "\\";
        
         String thisfilename = thisfile.filename.replace(twoslashes, "\\");
        String browsedfile = file.getAbsolutePath();
        
                
         if (browsedfile.equals(thisfilename))
         {   
          filealreadyopen = browsedfile;
             PromptForSameFileName = true; }
     }
     if (PromptForSameFileName==false)
    {

SeleniumTestTool STAppFrame;
Document doc=null;
try
{

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();

doc = builder.parse(file.getAbsolutePath());

 
}
catch (ParserConfigurationException | SAXException | IOException e)
{
    System.out.println("DocumentBuilder error:" + e.toString());
   
}
    
finally 
{
   STAppFrame = BuildNewWindow(doc);
    STAppFrame.UpdateDisplay();
   STAppFrame.setVisible(true);
  STAppFrame.setProperties(full_filename);
   Navigator.addRecentFile(full_filename);
 
MDIClasses.add(STAppFrame);
MDI_Index =  MDIClasses.size()-1;
STAppFrame.AllFieldValues.clear();
STAppFrame.AllFieldValues.add(STAppFrame.OSType);
STAppFrame.AllFieldValues.add(STAppFrame.TargetBrowser);
String stringWaitTime = String.valueOf(STAppFrame.GetWaitTime());
STAppFrame.AllFieldValues.add(stringWaitTime);
STAppFrame.AllFieldValues.add(STAppFrame.getSMTPHostname());
STAppFrame.AllFieldValues.add(STAppFrame.getEmailFrom());
STAppFrame.AllFieldValues.add(STAppFrame.getEmailLoginName());
STAppFrame.AllFieldValues.add(STAppFrame.getEmailPassword());
STAppFrame.AllFieldValues.add(STAppFrame.getEmailTo());
STAppFrame.AllFieldValues.add(STAppFrame.getSubject());
String thisbool = "false";
if (STAppFrame.getEmailReport())
{
    thisbool = "true";
}
STAppFrame.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppFrame.getEmailReportFail())
{
    thisbool = "true";
}
STAppFrame.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppFrame.getExitAfter())
{
    thisbool = "true";
}
STAppFrame.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppFrame.getPromptToClose())
{
    thisbool = "true";
}
STAppFrame.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppFrame.getShowReport())
{
    thisbool = "true";
}
STAppFrame.AllFieldValues.add(thisbool);
for (Procedure thisproc: STAppFrame.BugArray)
{
    STAppFrame.AllFieldValues.add(thisproc.BugTitle);

    for (Action thisact: thisproc.ActionsList)
    {
        String checkingboolval1 = "false";
        STAppFrame.AllFieldValues.add(thisact.Variable1);

        STAppFrame.AllFieldValues.add(thisact.Variable2);
        if (thisact.BoolVal1)
        {
            checkingboolval1 = "true";
        }
        STAppFrame.AllFieldValues.add(checkingboolval1);
        
    }
}


}

 return MDI_Index;
    }
   else
     {
  
    JOptionPane.showMessageDialog (null, filealreadyopen + " is already open", "File is open", JOptionPane.INFORMATION_MESSAGE);
                               
 
 return -1;
     }
}

    }
  public void ImportNewWindow (Document doc, int MDI_INDEX)
  {
   NodeList ProcedureList = doc.getElementsByTagName("Procedure");
   
for (int i = 0; i < ProcedureList.getLength(); ++i)
{
    int newbug_index = MDIClasses.get(MDI_INDEX).BugArray.size();
    MDIClasses.get(MDI_INDEX).AddNewBug(); 
    
    Element Procedure = (Element) ProcedureList.item(i);
     MDIClasses.get(MDI_INDEX).BugArray.get(newbug_index).BugTitle = Procedure.getAttribute("Title");
     MDIClasses.get(MDI_INDEX).BugViewArray.get(newbug_index).JTextFieldBugTitle.setText(Procedure.getAttribute("Title"));
     MDIClasses.get(MDI_INDEX).BugArray.get(newbug_index).BugURL = Procedure.getAttribute("URL");
    
    String stPass = Procedure.getAttribute("Pass");
    Boolean Pass = false;
    if (stPass.equals("true"))
    {
        Pass = true;
    }
     MDIClasses.get(MDI_INDEX).BugArray.get(newbug_index).Pass = Pass;

    
    
 
    
    NodeList ActionsList = Procedure.getElementsByTagName("Action");
   int thislength = ActionsList.getLength();

    for (int j = 0; j <ActionsList.getLength(); j++)
    {
   Element Action = (Element) ActionsList.item(j);
   NodeList ActionNodes = Action.getChildNodes();
   String thisActionNodeName = "none";
   String thisActionNodeValue = "none";
   
   String Variable1 = "";
   String Variable2 = "";
   String LOCKED = "false";
   String BoolVal1 = "false";
    String TimeOfTest;
    String ActionType = "none";
    String ActionIndex;
    String ActionPass;
   Boolean RealBoolVal1 = false;
    for (int k = 0; k<ActionNodes.getLength(); k++)
    {
   thisActionNodeName = ActionNodes.item(k).getNodeName();
   thisActionNodeValue = ActionNodes.item(k).getTextContent();
   
    switch(thisActionNodeName)
    {
        case "Pass":
            ActionPass = thisActionNodeValue;
            break;
        case "ActionIndex":
            ActionIndex = thisActionNodeValue;
            break;
        case "Type":
            ActionType = thisActionNodeValue;
            break;
        case "Variable1":
            Variable1 = thisActionNodeValue;
            break;
        case "Variable2":
            Variable2 = thisActionNodeValue;
            break;
        case "BoolVal1":
            BoolVal1 = thisActionNodeValue;
            if (BoolVal1.equals("true"))
                    {
                    RealBoolVal1 = true;
                    }
            break;
        case "LOCKED":
            LOCKED = thisActionNodeValue;
            break;
       

        case "TimeOfTest":
            TimeOfTest = thisActionNodeValue;
            break;
           
    }  
                
    } 
    
   Procedure NewProcedure =  MDIClasses.get(MDI_INDEX).BugArray.get(newbug_index);
   ProcedureView NewProcedureView =  MDIClasses.get(MDI_INDEX).BugViewArray.get(newbug_index);
   if (ActionType.contains("Password"))
   {
       try
       {
       Variable2 = Protector.decrypt(Variable2);
     
       }
       catch (Exception e)
       {
        //   System.out.println("Load/decrypt error: " + e.toString());
       }
   }
  
 
   switch(ActionType)
           {
                case "Forward Action":
                    
                    ForwardActionView NewForwardActionView = new ForwardActionView();
                    ForwardAction NewForwardAction = new ForwardAction();
                    NewForwardActionView.AddListeners(NewForwardAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                    
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewForwardAction, NewForwardActionView, NewProcedure, NewProcedureView);
                   
                    
                    break;
                case "Back Action":
                    
                    BackActionView NewBackActionView = new BackActionView();
                    BackAction NewBackAction = new BackAction();
                    NewBackActionView.AddListeners(NewBackAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewBackAction, NewBackActionView,NewProcedure, NewProcedureView);
                    break;
         
                case "Click at HREF":
                    ClickAtHREFActionView NewClickAtHREFActionView = new ClickAtHREFActionView();
                    ClickAtHREFAction NewClickAtHREFAction = new ClickAtHREFAction(Variable1);
                    NewClickAtHREFActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewClickAtHREFActionView.UpdateActionView();
                    }
                    NewClickAtHREFActionView.AddListeners(NewClickAtHREFAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewClickAtHREFAction, NewClickAtHREFActionView,NewProcedure, NewProcedureView);
                    break;
                    
                 case "Click at Image SRC":
                    ClickAtImageSRCActionView NewClickAtImageSRCActionView = new ClickAtImageSRCActionView();
                    ClickAtImageSRCAction NewClickAtImageSRCAction = new ClickAtImageSRCAction(Variable1);
                    NewClickAtImageSRCActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewClickAtImageSRCActionView.UpdateActionView();
                    }
                    NewClickAtImageSRCActionView.AddListeners(NewClickAtImageSRCAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewClickAtImageSRCAction, NewClickAtImageSRCActionView,NewProcedure, NewProcedureView);
                    break;  
                     
                case "Click at Link Text":
                    ClickAtLinkTextActionView NewClickAtLinkTextActionView = new ClickAtLinkTextActionView();
                    ClickAtLinkTextAction NewClickAtLinkTextAction = new ClickAtLinkTextAction(Variable1);
                    NewClickAtLinkTextActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewClickAtLinkTextActionView.UpdateActionView();
                    }
                    NewClickAtLinkTextActionView.AddListeners(NewClickAtLinkTextAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewClickAtLinkTextAction, NewClickAtLinkTextActionView,NewProcedure, NewProcedureView);
                    break;
                case "Click at ID":
                    ClickAtIDActionView NewClickAtIDActionView = new ClickAtIDActionView();
                    ClickAtIDAction NewClickAtIDAction = new ClickAtIDAction(Variable1);
                    NewClickAtIDActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewClickAtIDActionView.UpdateActionView();
                    }
                    NewClickAtIDActionView.AddListeners(NewClickAtIDAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewClickAtIDAction, NewClickAtIDActionView, NewProcedure, NewProcedureView);
                    break;
                    
                case "Close Current Tab or Window":
                    CloseCurrentTabOrWindowActionView NewCloseCurrentTabOrWindowActionView = new CloseCurrentTabOrWindowActionView();
                    CloseCurrentTabOrWindowAction NewCloseCurrentTabOrWindowAction = new CloseCurrentTabOrWindowAction();
                    NewCloseCurrentTabOrWindowActionView.AddListeners(NewCloseCurrentTabOrWindowAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                    MDIClasses.get(MDI_INDEX).AddActionToArray (NewCloseCurrentTabOrWindowAction, NewCloseCurrentTabOrWindowActionView,NewProcedure, NewProcedureView);
                    break;
                     
                case "Drag From XPATH to XPATH":
                    DragAndDropActionView NewDragAndDropActionView = new DragAndDropActionView();
                    DragAndDropAction NewDragAndDropAction = new DragAndDropAction(Variable1, Variable2);
                    NewDragAndDropActionView.JTextFieldVariable1.setText(Variable1);
                    NewDragAndDropActionView.JTextFieldVariable2.setText(Variable2);
                    if (LOCKED.equals("true"))
                    {
                    NewDragAndDropActionView.UpdateActionView();
                    }
                    NewDragAndDropActionView.AddListeners(NewDragAndDropAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewDragAndDropAction, NewDragAndDropActionView,NewProcedure, NewProcedureView);
                    break;
                
                case "Enter Key":
                    
                    EnterKeyActionView NewEnterKeyActionView = new EnterKeyActionView();
                    EnterKeyAction NewEnterKeyAction = new EnterKeyAction();
                    NewEnterKeyActionView.AddListeners(NewEnterKeyAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewEnterKeyAction, NewEnterKeyActionView,NewProcedure, NewProcedureView);
                    break;
                    
                case "Execute Javascript":
                    ExecuteJavascriptActionView NewExecuteJavascriptActionView = new ExecuteJavascriptActionView();
                    ExecuteJavascriptAction NewExecuteJavascriptAction = new ExecuteJavascriptAction(Variable1);
                    NewExecuteJavascriptActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewExecuteJavascriptActionView.UpdateActionView();
                    }
                    NewExecuteJavascriptActionView.AddListeners(NewExecuteJavascriptAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewExecuteJavascriptAction, NewExecuteJavascriptActionView, NewProcedure, NewProcedureView);
                    break;   
                    
                case "Go to URL":
                    GoActionView NewGoActionView = new GoActionView();
                    GoAction NewGoAction = new GoAction(Variable1);
                    NewGoActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewGoActionView.UpdateActionView();
                    }
                    NewGoAction.setVariable1(Variable1);
                
                    NewGoActionView.AddListeners(NewGoAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewGoAction, NewGoActionView, NewProcedure, NewProcedureView);
                    break;  
                  
                case "Open New Tab":
                    
                    OpenNewTabActionView NewOpenNewTabActionView = new OpenNewTabActionView();
                    OpenNewTabAction NewOpenNewTabAction = new OpenNewTabAction();
                    NewOpenNewTabActionView.AddListeners(NewOpenNewTabAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewOpenNewTabAction, NewOpenNewTabActionView,NewProcedure, NewProcedureView);
                    break;  
                case "Next Tab":
                    
                    NextTabActionView NewNextTabActionView = new NextTabActionView();
                    NextTabAction NewNextTabAction = new NextTabAction();
                    NewNextTabActionView.AddListeners(NewNextTabAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewNextTabAction, NewNextTabActionView,NewProcedure, NewProcedureView);
                    break; 
                    
                case "Pause":
                    PauseActionView NewPauseActionView = new PauseActionView();
                    PauseAction NewPauseAction = new PauseAction(Variable1, Variable2);
                    NewPauseActionView.JTextFieldVariable1.setText(Variable1);
                    NewPauseActionView.JTextFieldVariable2.setText(Variable2);
                    if (LOCKED.equals("true"))
                    {
                    NewPauseActionView.UpdateActionView();
                    }
                    NewPauseActionView.AddListeners(NewPauseAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewPauseAction, NewPauseActionView,NewProcedure, NewProcedureView);
                    break;
                    
                case "Set Cookie":
                    SetCookieActionView NewSetCookieActionView = new SetCookieActionView();
                    SetCookieAction NewSetCookieAction = new SetCookieAction(Variable1, Variable2);
                    NewSetCookieActionView.JTextFieldVariable1.setText(Variable1);
                    NewSetCookieActionView.JTextFieldVariable2.setText(Variable2);
                    if (LOCKED.equals("true"))
                    {
                    NewSetCookieActionView.UpdateActionView();
                    }
                    NewSetCookieActionView.AddListeners(NewSetCookieAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewSetCookieAction, NewSetCookieActionView,NewProcedure, NewProcedureView);
                    break;
                case "Switch To Frame":
                    SwitchToFrameActionView NewSwitchToFrameActionView = new SwitchToFrameActionView();
                    SwitchToFrameAction NewSwitchToFrameAction = new SwitchToFrameAction(Variable1);
                    NewSwitchToFrameActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewSwitchToFrameActionView.UpdateActionView();
                    }
                    NewSwitchToFrameAction.setVariable1(Variable1);
                
                    NewSwitchToFrameActionView.AddListeners(NewSwitchToFrameAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewSwitchToFrameAction, NewSwitchToFrameActionView, NewProcedure, NewProcedureView);
                    break;  
                    
                case "Switch To Tab or Window":
                    SwitchToTabOrWindowActionView NewSwitchToTabOrWindowActionView = new SwitchToTabOrWindowActionView();
                    SwitchToTabOrWindowAction NewSwitchToTabOrWindowAction = new SwitchToTabOrWindowAction(Variable1);
                    NewSwitchToTabOrWindowActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewSwitchToTabOrWindowActionView.UpdateActionView();
                    }
                    NewSwitchToTabOrWindowAction.setVariable1(Variable1);
                
                    NewSwitchToTabOrWindowActionView.AddListeners(NewSwitchToTabOrWindowAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewSwitchToTabOrWindowAction, NewSwitchToTabOrWindowActionView, NewProcedure, NewProcedureView);
                    break;  
                    
                case "Type at ID":
                    TypeAtIDActionView NewTypeAtIDActionView = new TypeAtIDActionView();
                    TypeAtIDAction NewTypeAtIDAction = new TypeAtIDAction(Variable1, Variable2, RealBoolVal1);
                    NewTypeAtIDActionView.JTextFieldVariable1.setText(Variable1);
                    NewTypeAtIDActionView.JTextFieldVariable2.setText(Variable2);
                    NewTypeAtIDActionView.JCheckBoxBoolVal1.setSelected(RealBoolVal1);
                    if (LOCKED.equals("true"))
                    {
                    NewTypeAtIDActionView.UpdateActionView();
                    }
                    NewTypeAtIDActionView.AddListeners(NewTypeAtIDAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewTypeAtIDAction, NewTypeAtIDActionView,NewProcedure, NewProcedureView);
                    break;
                  case "Type at Input Name":
                    TypeAtInputNameActionView NewTypeAtInputNameActionView = new TypeAtInputNameActionView();
                    TypeAtInputNameAction NewTypeAtInputNameAction = new TypeAtInputNameAction(Variable1, Variable2, RealBoolVal1);
                    NewTypeAtInputNameActionView.JTextFieldVariable1.setText(Variable1);
                    NewTypeAtInputNameActionView.JTextFieldVariable2.setText(Variable2);
                    NewTypeAtInputNameActionView.JCheckBoxBoolVal1.setSelected(RealBoolVal1);
                    if (LOCKED.equals("true"))
                    {
                    NewTypeAtInputNameActionView.UpdateActionView();
                    }
                    NewTypeAtInputNameActionView.AddListeners(NewTypeAtInputNameAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewTypeAtInputNameAction, NewTypeAtInputNameActionView,NewProcedure, NewProcedureView);
                    break;
                      
                case "Type at XPATH":
                    TypeAtXPATHActionView NewTypeAtXPATHActionView = new TypeAtXPATHActionView();
                    TypeAtXPATHAction NewTypeAtXPATHAction = new TypeAtXPATHAction(Variable1, Variable2, RealBoolVal1);
                    NewTypeAtXPATHActionView.JTextFieldVariable1.setText(Variable1);
                    NewTypeAtXPATHActionView.JTextFieldVariable2.setText(Variable2);
                    NewTypeAtXPATHActionView.JCheckBoxBoolVal1.setSelected(RealBoolVal1);
                    if (LOCKED.equals("true"))
                    {
                    NewTypeAtXPATHActionView.UpdateActionView();
                    }
                    NewTypeAtXPATHActionView.AddListeners(NewTypeAtXPATHAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewTypeAtXPATHAction, NewTypeAtXPATHActionView,NewProcedure, NewProcedureView);
                    break;
                 
                    
                case "Click at XPATH":
                    ClickXPATHActionView NewClickXPATHActionView = new ClickXPATHActionView();
                    ClickXPATHAction NewClickXPATHAction = new ClickXPATHAction(Variable1);
                    NewClickXPATHActionView.JTextFieldVariable1.setText(Variable1);
                    NewClickXPATHActionView.AddListeners(NewClickXPATHAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                    if (LOCKED.equals("true"))
                    {
                    NewClickXPATHActionView.UpdateActionView();
                    }
                     MDIClasses.get(MDI_INDEX).AddActionToArray (NewClickXPATHAction, NewClickXPATHActionView,NewProcedure, NewProcedureView);
                    break;
                    
                  case "Yes/No Prompt":
                     YesNoPromptPassFailActionView NewYesNoPromptPassFailActionView = new YesNoPromptPassFailActionView();
                     YesNoPromptPassFailAction NewYesNoPromptPassFailAction = new YesNoPromptPassFailAction(Variable1);
                     NewYesNoPromptPassFailActionView.JTextFieldVariable1.setText(Variable1);
                     if (LOCKED.equals("true"))
                    {
                    NewYesNoPromptPassFailActionView.UpdateActionView();
                    }
                     NewYesNoPromptPassFailActionView.AddListeners(NewYesNoPromptPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewYesNoPromptPassFailAction, NewYesNoPromptPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                     
                 case "Find HREF":
                     FindHREFPassFailActionView NewFindHREFPassFailActionView = new FindHREFPassFailActionView();
                     FindHREFPassFailAction NewFindHREFPassFailAction = new FindHREFPassFailAction(Variable1, false);
                     NewFindHREFPassFailActionView.JTextFieldVariable1.setText(Variable1);
                      if (LOCKED.equals("true"))
                    {
                    NewFindHREFPassFailActionView.UpdateActionView();
                    }
                     NewFindHREFPassFailActionView.AddListeners(NewFindHREFPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewFindHREFPassFailAction, NewFindHREFPassFailActionView,NewProcedure, NewProcedureView);
                     break;
                     
                 case "Do NOT Find HREF":
                     NOTFindHREFPassFailActionView NewNOTFindHREFPassFailActionView = new NOTFindHREFPassFailActionView();
                     FindHREFPassFailAction NewNOTFindHREFPassFailAction = new FindHREFPassFailAction(Variable1, true);
                     NewNOTFindHREFPassFailActionView.JTextFieldVariable1.setText(Variable1);
                      if (LOCKED.equals("true"))
                    {
                    NewNOTFindHREFPassFailActionView.UpdateActionView();
                    }
                     NewNOTFindHREFPassFailActionView.AddListeners(NewNOTFindHREFPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewNOTFindHREFPassFailAction, NewNOTFindHREFPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                 case "Find IFrame SRC":
                     FindIFrameSRCPassFailActionView NewFindIFrameSRCPassFailActionView = new FindIFrameSRCPassFailActionView();
                     FindIFrameSRCPassFailAction NewFindIFrameSRCPassFailAction = new FindIFrameSRCPassFailAction(Variable1, false);
                     NewFindIFrameSRCPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewFindIFrameSRCPassFailActionView.UpdateActionView();
                    }
                     NewFindIFrameSRCPassFailActionView.AddListeners(NewFindIFrameSRCPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewFindIFrameSRCPassFailAction, NewFindIFrameSRCPassFailActionView,NewProcedure, NewProcedureView);
                     break;
                 case "Do NOT Find IFrame SRC":
                     NOTFindIFrameSRCPassFailActionView NewNOTFindIFrameSRCPassFailActionView = new NOTFindIFrameSRCPassFailActionView();
                     FindIFrameSRCPassFailAction NewNOTFindIFrameSRCPassFailAction = new FindIFrameSRCPassFailAction(Variable1, true);
                     NewNOTFindIFrameSRCPassFailActionView.JTextFieldVariable1.setText(Variable1);
                        if (LOCKED.equals("true"))
                    {
                    NewNOTFindIFrameSRCPassFailActionView.UpdateActionView();
                    }
                     NewNOTFindIFrameSRCPassFailActionView.AddListeners(NewNOTFindIFrameSRCPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewNOTFindIFrameSRCPassFailAction, NewNOTFindIFrameSRCPassFailActionView,NewProcedure, NewProcedureView);
                     break;
                     
                 case "Find Image SRC":
                     FindImageSRCPassFailActionView NewFindImageSRCPassFailActionView = new FindImageSRCPassFailActionView();
                     FindImageSRCPassFailAction NewFindImageSRCPassFailAction = new FindImageSRCPassFailAction(Variable1, false);
                     NewFindImageSRCPassFailActionView.JTextFieldVariable1.setText(Variable1);
                        if (LOCKED.equals("true"))
                    {
                    NewFindImageSRCPassFailActionView.UpdateActionView();
                    }
                     NewFindImageSRCPassFailActionView.AddListeners(NewFindImageSRCPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewFindImageSRCPassFailAction, NewFindImageSRCPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                     
                 case "Do NOT Find Image SRC":
                     NOTFindImageSRCPassFailActionView NewNOTFindImageSRCPassFailActionView = new NOTFindImageSRCPassFailActionView();
                     FindImageSRCPassFailAction NewNOTFindImageSRCPassFailAction = new FindImageSRCPassFailAction(Variable1, true);
                     NewNOTFindImageSRCPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewNOTFindImageSRCPassFailActionView.UpdateActionView();
                    }
                     NewNOTFindImageSRCPassFailActionView.AddListeners(NewNOTFindImageSRCPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewNOTFindImageSRCPassFailAction, NewNOTFindImageSRCPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                     
                 case "Find Page Title":
                     FindPAGETITLEPassFailActionView NewFindPAGETITLEPassFailActionView = new FindPAGETITLEPassFailActionView();
                     FindPAGETITLEPassFailAction NewFindPAGETITLEPassFailAction = new FindPAGETITLEPassFailAction(Variable1, false);
                     NewFindPAGETITLEPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewFindPAGETITLEPassFailActionView.UpdateActionView();
                    }
                     NewFindPAGETITLEPassFailActionView.AddListeners(NewFindPAGETITLEPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewFindPAGETITLEPassFailAction, NewFindPAGETITLEPassFailActionView,NewProcedure, NewProcedureView);
                     break;
                     
                 case "Do NOT Find Page Title":
                     NOTFindPAGETITLEPassFailActionView NewNOTFindPAGETITLEPassFailActionView = new NOTFindPAGETITLEPassFailActionView();
                     FindPAGETITLEPassFailAction NewNOTFindPAGETITLEPassFailAction = new FindPAGETITLEPassFailAction(Variable1, true);
                     NewNOTFindPAGETITLEPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewNOTFindPAGETITLEPassFailActionView.UpdateActionView();
                    }
                     NewNOTFindPAGETITLEPassFailActionView.AddListeners(NewNOTFindPAGETITLEPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewNOTFindPAGETITLEPassFailAction, NewNOTFindPAGETITLEPassFailActionView,NewProcedure, NewProcedureView);
                     break;
                     
                 case "Find Text":
                     FindTextPassFailActionView NewFindTextPassFailActionView = new FindTextPassFailActionView();
                     FindTextPassFailAction NewFindTextPassFailAction = new FindTextPassFailAction(Variable1, false);
                     NewFindTextPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewFindTextPassFailActionView.UpdateActionView();
                    }
                     NewFindTextPassFailActionView.AddListeners(NewFindTextPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewFindTextPassFailAction, NewFindTextPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                     
                 case "Do NOT Find Text":
                     NOTFindTextPassFailActionView NewNOTFindTextPassFailActionView = new NOTFindTextPassFailActionView();
                     FindTextPassFailAction NewNOTFindTextPassFailAction = new FindTextPassFailAction(Variable1, true);
                     NewNOTFindTextPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewNOTFindTextPassFailActionView.UpdateActionView();
                    }
                     NewNOTFindTextPassFailActionView.AddListeners(NewNOTFindTextPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray(NewNOTFindTextPassFailAction, NewNOTFindTextPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                     
                 case "Find XPATH":
                     FindXPATHPassFailActionView NewFindXPATHPassFailActionView = new FindXPATHPassFailActionView();
                     FindXPATHPassFailAction NewFindXPATHPassFailAction =  new FindXPATHPassFailAction(Variable1, false);
                     NewFindXPATHPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewFindXPATHPassFailActionView.UpdateActionView();
                    }
                     NewFindXPATHPassFailActionView.AddListeners(NewFindXPATHPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray(NewFindXPATHPassFailAction, NewFindXPATHPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                     
                 case "Do NOT Find XPATH":
                     NOTFindXPATHPassFailActionView NewNOTFindXPATHPassFailActionView = new NOTFindXPATHPassFailActionView();
                     FindXPATHPassFailAction NewNOTFindXPATHPassFailAction =  new FindXPATHPassFailAction(Variable1, true);
                     NewNOTFindXPATHPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewNOTFindXPATHPassFailActionView.UpdateActionView();
                    }
                     NewNOTFindXPATHPassFailActionView.AddListeners(NewNOTFindXPATHPassFailAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewNOTFindXPATHPassFailAction, NewNOTFindXPATHPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                 
                 case "Type Password at XPATH":
                     TypePasswordAtXPATHActionView NewTypePasswordAtXPATHActionView = new TypePasswordAtXPATHActionView();
                     TypePasswordAtXPATHAction NewTypePasswordAtXPATHAction = new TypePasswordAtXPATHAction(Variable1, Variable2, RealBoolVal1);
                     NewTypePasswordAtXPATHActionView.JTextFieldVariable1.setText(Variable1);
                     NewTypePasswordAtXPATHActionView.JTextFieldPassword.setText(Variable2);
                     NewTypePasswordAtXPATHActionView.JCheckBoxBoolVal1.setSelected(RealBoolVal1);
                       if (LOCKED.equals("true"))
                    {
                    NewTypePasswordAtXPATHActionView.UpdateActionView();
                    }
                     NewTypePasswordAtXPATHActionView.AddListeners(NewTypePasswordAtXPATHAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewTypePasswordAtXPATHAction, NewTypePasswordAtXPATHActionView,NewProcedure, NewProcedureView);
        
                     break;  
                     
                 case "Type Password at Input Name":
                     TypePasswordAtInputNameActionView NewTypePasswordAtInputNameActionView = new TypePasswordAtInputNameActionView();
                     TypePasswordAtInputNameAction NewTypePasswordAtInputNameAction = new TypePasswordAtInputNameAction(Variable1, Variable2, RealBoolVal1);
                     NewTypePasswordAtInputNameActionView.JTextFieldVariable1.setText(Variable1);
                     NewTypePasswordAtInputNameActionView.JTextFieldPassword.setText(Variable2);
                     NewTypePasswordAtInputNameActionView.JCheckBoxBoolVal1.setSelected(RealBoolVal1);
                       if (LOCKED.equals("true"))
                    {
                    NewTypePasswordAtInputNameActionView.UpdateActionView();
                    }
                     NewTypePasswordAtInputNameActionView.AddListeners(NewTypePasswordAtInputNameAction,  MDIClasses.get(MDI_INDEX), NewProcedure, NewProcedureView);
                      MDIClasses.get(MDI_INDEX).AddActionToArray (NewTypePasswordAtInputNameAction, NewTypePasswordAtInputNameActionView,NewProcedure, NewProcedureView);
        
                     break;   
                     
           }    
  
 MDIClasses.get(MDI_INDEX).UpdateDisplay();
        }   
     
    }     
  }
  public SeleniumTestTool BuildNewWindow(Document doc)
  {
   
    
   NamedNodeMap NewAttributes = doc.getElementsByTagName("BrowserMatorWindow").item(0).getAttributes(); 
   
   String filename_read = NewAttributes.getNamedItem("Filename").getNodeValue();
   SeleniumTestTool STAppFrame = new SeleniumTestTool(filename_read);
   STAppFrame.filename = filename_read;
    STAppFrame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
     @Override 
     public void internalFrameClosing(InternalFrameEvent e) {
    
    
      int closed =  CheckToSaveChanges(STAppFrame);
           
      if (closed==1)
      {
      STAppFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
       MDIClasses.remove(MDIClasses.size()-1); 
       STAppFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      }
     
      }
    });
  NodeList FileSettingsNode = doc.getElementsByTagName("FileSettings");
 
  String thisSettingsNodeName;
  String thisSettingsNodeValue;
  String stShowReport="false";
  String stEmailReport = "false";
  String stEmailReportFail = "false";
  String stExitAfter = "false";
  String SMTPHostname = "";
  String EmailLoginName = "";
  String stPromptToClose = "false";
  String TargetBrowser = "Firefox";
  String WaitTime = "3";
  String OSType = "Windows";
  String EmailPassword = "";
  String unepassword = "";
  String EmailTo = "";
  String EmailFrom = "";
  String EmailSubject = "";
  
  for (int ke = 0; ke<FileSettingsNode.getLength(); ke++)
    {
   thisSettingsNodeName = FileSettingsNode.item(ke).getNodeName();
   thisSettingsNodeValue = FileSettingsNode.item(ke).getTextContent();
   
    switch(thisSettingsNodeName)
    {
        case "ShowReport":
            stShowReport = thisSettingsNodeValue;
           Boolean ShowReport = false;
   if (stShowReport.equals("true"))
   {
       ShowReport = true;
   }
   STAppFrame.setShowReport(ShowReport);
            break;
        
        case "EmailReport":
   stEmailReport = thisSettingsNodeValue;
   Boolean EmailReport = false;
    if (stEmailReport.equals("true"))
   {
       EmailReport = true;
   }
       STAppFrame.setEmailReport(EmailReport);
            break;
      
        case "EmailReportFail":
   stEmailReportFail = thisSettingsNodeValue;
   Boolean EmailReportFail = false;
    if (stEmailReportFail.equals("true"))
   {
       EmailReportFail = true;
   }
       STAppFrame.setEmailReport(EmailReportFail);
            break;
         
        case "ExitAfter":
   stExitAfter = thisSettingsNodeValue;
   Boolean ExitAfter = false;
    if (stExitAfter.equals("true"))
   {
       ExitAfter = true;
   }
       STAppFrame.setEmailReport(ExitAfter);
            break;        
       
        case "SMTPHostname":
 SMTPHostname = thisSettingsNodeValue;
      STAppFrame.setSMTPHostname(SMTPHostname);
            break;  

        case "EmailLoginName":
 EmailLoginName = thisSettingsNodeValue;
      STAppFrame.setEmailLoginName(EmailLoginName);
            break;  
        
        case "PromptToClose":
 stPromptToClose = thisSettingsNodeValue;
   Boolean PromptToClose = false;
    if (stPromptToClose.equals("true"))
   {
       PromptToClose = true;
   }
       STAppFrame.setPromptToClose(PromptToClose);
            break; 
          
        case "TargetBrowser":
 TargetBrowser = thisSettingsNodeValue;
      STAppFrame.setTargetBrowser(TargetBrowser);
            break;   
            
       case "WaitTime":
 WaitTime = thisSettingsNodeValue;
 int intWaitTime = Integer.parseInt(WaitTime);
      STAppFrame.setWaitTime(intWaitTime);
            break;   
              
       case "OSType":
 OSType = thisSettingsNodeValue;
      STAppFrame.setOSType(OSType);
            break;   
     
       case "EmailPassword":
 EmailPassword = thisSettingsNodeValue;
  try
   {
   unepassword = Protector.decrypt(EmailPassword);
   }
   catch (GeneralSecurityException | IOException e)
           {
               System.out.println("decrypt error" + e.toString());
           }
      STAppFrame.setEmailPassword(unepassword);
            break;  
      
       case "EmailTo":
 EmailTo = thisSettingsNodeValue;
      STAppFrame.setEmailTo(EmailTo);
            break;    
      
       case "EmailFrom":
 EmailFrom = thisSettingsNodeValue;
      STAppFrame.setEmailFrom(EmailFrom);
            break;   
       
       case "EmailSubject":
 EmailSubject = thisSettingsNodeValue;
      STAppFrame.setSubject(EmailSubject);
            break; 
    }

    }
   NodeList ProcedureList = doc.getElementsByTagName("Procedure");
   
for (int i = 0; i < ProcedureList.getLength(); ++i)
{
    
    STAppFrame.AddNewBug(); 
   
    Element Procedure = (Element) ProcedureList.item(i);
    STAppFrame.BugArray.get(i).BugTitle = Procedure.getAttribute("Title");
    STAppFrame.BugViewArray.get(i).JTextFieldBugTitle.setText(Procedure.getAttribute("Title"));
    STAppFrame.BugArray.get(i).BugURL = Procedure.getAttribute("URL");
    
    String stPass = Procedure.getAttribute("Pass");
    Boolean Pass = false;
    if (stPass.equals("true"))
    {
        Pass = true;
    }
    STAppFrame.BugArray.get(i).Pass = Pass;

    
    
 
    
    NodeList ActionsList = Procedure.getElementsByTagName("Action");
  
    for (int j = 0; j <ActionsList.getLength(); j++)
    {
   Element Action = (Element) ActionsList.item(j);
   NodeList ActionNodes = Action.getChildNodes();
   String thisActionNodeName = "none";
   String thisActionNodeValue = "none";
   
   String Variable1 = "";
   String Variable2 = "";
   String LOCKED = "false";
   String BoolVal1 = "false";
    String TimeOfTest;
    String ActionType = "none";
    String ActionIndex;
    String ActionPass;
   Boolean RealBoolVal1 = false;
    for (int k = 0; k<ActionNodes.getLength(); k++)
    {
   thisActionNodeName = ActionNodes.item(k).getNodeName();
   thisActionNodeValue = ActionNodes.item(k).getTextContent();
   
    switch(thisActionNodeName)
    {
        case "Pass":
            ActionPass = thisActionNodeValue;
            break;
        case "ActionIndex":
            ActionIndex = thisActionNodeValue;
            break;
        case "Type":
            ActionType = thisActionNodeValue;
            break;
        case "Variable1":
            Variable1 = thisActionNodeValue;
            break;
        case "Variable2":
            Variable2 = thisActionNodeValue;
            break;
        case "BoolVal1":
            BoolVal1 = thisActionNodeValue;
            if (BoolVal1.equals("true"))
                    {
                    RealBoolVal1 = true;
                    }
           break;
        case "LOCKED":
            LOCKED = thisActionNodeValue;
            break;
       

        case "TimeOfTest":
            TimeOfTest = thisActionNodeValue;
            break;
           
    }  
                
    } 
    
   Procedure NewProcedure = STAppFrame.BugArray.get(i);
   ProcedureView NewProcedureView = STAppFrame.BugViewArray.get(i);
   if (ActionType.contains("Password"))
   {
       try
       {
       Variable2 = Protector.decrypt(Variable2);
    
       }
       catch (Exception e)
       {
        //   System.out.println("Load/decrypt error: " + e.toString());
       }
   }
  
 
   switch(ActionType)
           {
                case "Forward Action":
                    
                    ForwardActionView NewForwardActionView = new ForwardActionView();
                    ForwardAction NewForwardAction = new ForwardAction();
                    NewForwardActionView.AddListeners(NewForwardAction, STAppFrame, NewProcedure, NewProcedureView);
                    
                    STAppFrame.AddActionToArray (NewForwardAction, NewForwardActionView, NewProcedure, NewProcedureView);
                   
                    
                    break;
                case "Back Action":
                    
                    BackActionView NewBackActionView = new BackActionView();
                    BackAction NewBackAction = new BackAction();
                    NewBackActionView.AddListeners(NewBackAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewBackAction, NewBackActionView,NewProcedure, NewProcedureView);
                    break;
                    
                    
                case "Go to URL":
                    GoActionView NewGoActionView = new GoActionView();
                    GoAction NewGoAction = new GoAction(Variable1);
                    NewGoActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewGoActionView.UpdateActionView();
                    }
                    NewGoAction.setVariable1(Variable1);
                
                    NewGoActionView.AddListeners(NewGoAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewGoAction, NewGoActionView, NewProcedure, NewProcedureView);
                    break;
                    
                case "Click at HREF":
                    ClickAtHREFActionView NewClickAtHREFActionView = new ClickAtHREFActionView();
                    ClickAtHREFAction NewClickAtHREFAction = new ClickAtHREFAction(Variable1);
                    NewClickAtHREFActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewClickAtHREFActionView.UpdateActionView();
                    }
                    NewClickAtHREFActionView.AddListeners(NewClickAtHREFAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewClickAtHREFAction, NewClickAtHREFActionView,NewProcedure, NewProcedureView);
                    break;
                    
                 case "Click at Image SRC":
                    ClickAtImageSRCActionView NewClickAtImageSRCActionView = new ClickAtImageSRCActionView();
                    ClickAtImageSRCAction NewClickAtImageSRCAction = new ClickAtImageSRCAction(Variable1);
                    NewClickAtImageSRCActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewClickAtImageSRCActionView.UpdateActionView();
                    }
                    NewClickAtImageSRCActionView.AddListeners(NewClickAtImageSRCAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewClickAtImageSRCAction, NewClickAtImageSRCActionView,NewProcedure, NewProcedureView);
                    break;  
                     
                case "Click at Link Text":
                    ClickAtLinkTextActionView NewClickAtLinkTextActionView = new ClickAtLinkTextActionView();
                    ClickAtLinkTextAction NewClickAtLinkTextAction = new ClickAtLinkTextAction(Variable1);
                    NewClickAtLinkTextActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewClickAtLinkTextActionView.UpdateActionView();
                    }
                    NewClickAtLinkTextActionView.AddListeners(NewClickAtLinkTextAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewClickAtLinkTextAction, NewClickAtLinkTextActionView,NewProcedure, NewProcedureView);
                    break;
                case "Click at ID":
                    ClickAtIDActionView NewClickAtIDActionView = new ClickAtIDActionView();
                    ClickAtIDAction NewClickAtIDAction = new ClickAtIDAction(Variable1);
                    NewClickAtIDActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewClickAtIDActionView.UpdateActionView();
                    }
                    NewClickAtIDActionView.AddListeners(NewClickAtIDAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewClickAtIDAction, NewClickAtIDActionView, NewProcedure, NewProcedureView);
                    break;
                    
                case "Close Current Tab or Window":
                    CloseCurrentTabOrWindowActionView NewCloseCurrentTabOrWindowActionView = new CloseCurrentTabOrWindowActionView();
                    CloseCurrentTabOrWindowAction NewCloseCurrentTabOrWindowAction = new CloseCurrentTabOrWindowAction();
                    NewCloseCurrentTabOrWindowActionView.AddListeners(NewCloseCurrentTabOrWindowAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewCloseCurrentTabOrWindowAction, NewCloseCurrentTabOrWindowActionView,NewProcedure, NewProcedureView);
                    break;
                case "Enter Key":
                    
                    EnterKeyActionView NewEnterKeyActionView = new EnterKeyActionView();
                    EnterKeyAction NewEnterKeyAction = new EnterKeyAction();
                    NewEnterKeyActionView.AddListeners(NewEnterKeyAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewEnterKeyAction, NewEnterKeyActionView,NewProcedure, NewProcedureView);
                    break;
                    
                case "Execute Javascript":
                    ExecuteJavascriptActionView NewExecuteJavascriptActionView = new ExecuteJavascriptActionView();
                    ExecuteJavascriptAction NewExecuteJavascriptAction = new ExecuteJavascriptAction(Variable1);
                    NewExecuteJavascriptActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewExecuteJavascriptActionView.UpdateActionView();
                    }
                    NewExecuteJavascriptActionView.AddListeners(NewExecuteJavascriptAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewExecuteJavascriptAction, NewExecuteJavascriptActionView, NewProcedure, NewProcedureView);
                    break; 
                    
                 case "Drag From XPATH to XPATH":
                    DragAndDropActionView NewDragAndDropActionView = new DragAndDropActionView();
                    DragAndDropAction NewDragAndDropAction = new DragAndDropAction(Variable1, Variable2);
                    NewDragAndDropActionView.JTextFieldVariable1.setText(Variable1);
                    NewDragAndDropActionView.JTextFieldVariable2.setText(Variable2);
                    if (LOCKED.equals("true"))
                    {
                    NewDragAndDropActionView.UpdateActionView();
                    }
                    NewDragAndDropActionView.AddListeners(NewDragAndDropAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewDragAndDropAction, NewDragAndDropActionView,NewProcedure, NewProcedureView);
                    break;
                 
                 case "Open New Tab":
                    
                    OpenNewTabActionView NewOpenNewTabActionView = new OpenNewTabActionView();
                    OpenNewTabAction NewOpenNewTabAction = new OpenNewTabAction();
                    NewOpenNewTabActionView.AddListeners(NewOpenNewTabAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewOpenNewTabAction, NewOpenNewTabActionView,NewProcedure, NewProcedureView);
                    break;   
                 case "Next Tab":
                    
                    NextTabActionView NewNextTabActionView = new NextTabActionView();
                    NextTabAction NewNextTabAction = new NextTabAction();
                    NewNextTabActionView.AddListeners(NewNextTabAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewNextTabAction, NewNextTabActionView,NewProcedure, NewProcedureView);
                    break;
                     
                 case "Pause":
                    PauseActionView NewPauseActionView = new PauseActionView();
                    PauseAction NewPauseAction = new PauseAction(Variable1, Variable2);
                    NewPauseActionView.JTextFieldVariable1.setText(Variable1);
                    NewPauseActionView.JTextFieldVariable2.setText(Variable2);
                    if (LOCKED.equals("true"))
                    {
                    NewPauseActionView.UpdateActionView();
                    }
                    NewPauseActionView.AddListeners(NewPauseAction,  STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewPauseAction, NewPauseActionView,NewProcedure, NewProcedureView);
                    break;
                
                 case "Set Cookie":
                    SetCookieActionView NewSetCookieActionView = new SetCookieActionView();
                    SetCookieAction NewSetCookieAction = new SetCookieAction(Variable1, Variable2);
                    NewSetCookieActionView.JTextFieldVariable1.setText(Variable1);
                    NewSetCookieActionView.JTextFieldVariable2.setText(Variable2);
                    if (LOCKED.equals("true"))
                    {
                    NewSetCookieActionView.UpdateActionView();
                    }
                    NewSetCookieActionView.AddListeners(NewSetCookieAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewSetCookieAction, NewSetCookieActionView,NewProcedure, NewProcedureView);
                    break;
                case "Switch To Frame":
                    SwitchToFrameActionView NewSwitchToFrameActionView = new SwitchToFrameActionView();
                    SwitchToFrameAction NewSwitchToFrameAction = new SwitchToFrameAction(Variable1);
                    NewSwitchToFrameActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewSwitchToFrameActionView.UpdateActionView();
                    }
                    NewSwitchToFrameActionView.AddListeners(NewSwitchToFrameAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewSwitchToFrameAction, NewSwitchToFrameActionView, NewProcedure, NewProcedureView);
                    break; 
                    
                 case "Switch To Tab or Window":
                    SwitchToTabOrWindowActionView NewSwitchToTabOrWindowActionView = new SwitchToTabOrWindowActionView();
                    SwitchToTabOrWindowAction NewSwitchToTabOrWindowAction = new SwitchToTabOrWindowAction(Variable1);
                    NewSwitchToTabOrWindowActionView.JTextFieldVariable1.setText(Variable1);
                    if (LOCKED.equals("true"))
                    {
                    NewSwitchToTabOrWindowActionView.UpdateActionView();
                    }
                    NewSwitchToTabOrWindowActionView.AddListeners(NewSwitchToTabOrWindowAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewSwitchToTabOrWindowAction, NewSwitchToTabOrWindowActionView, NewProcedure, NewProcedureView);
                    break; 
                    
                case "Type at ID":
                    TypeAtIDActionView NewTypeAtIDActionView = new TypeAtIDActionView();
                    TypeAtIDAction NewTypeAtIDAction = new TypeAtIDAction(Variable1, Variable2, RealBoolVal1);
                    NewTypeAtIDActionView.JTextFieldVariable1.setText(Variable1);
                    NewTypeAtIDActionView.JTextFieldVariable2.setText(Variable2);
                    NewTypeAtIDActionView.JCheckBoxBoolVal1.setSelected(RealBoolVal1);
                    if (LOCKED.equals("true"))
                    {
                    NewTypeAtIDActionView.UpdateActionView();
                    }
                
                    NewTypeAtIDActionView.AddListeners(NewTypeAtIDAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewTypeAtIDAction, NewTypeAtIDActionView,NewProcedure, NewProcedureView);
                    break;
                    
                  case "Type at Input Name":
                    TypeAtInputNameActionView NewTypeAtInputNameActionView = new TypeAtInputNameActionView();
                    TypeAtInputNameAction NewTypeAtInputNameAction = new TypeAtInputNameAction(Variable1, Variable2, RealBoolVal1);
                    NewTypeAtInputNameActionView.JTextFieldVariable1.setText(Variable1);
                    NewTypeAtInputNameActionView.JTextFieldVariable2.setText(Variable2);
                    NewTypeAtInputNameActionView.JCheckBoxBoolVal1.setSelected(RealBoolVal1);
                    if (LOCKED.equals("true"))
                    {
                    NewTypeAtInputNameActionView.UpdateActionView();
                    }
                    NewTypeAtInputNameActionView.AddListeners(NewTypeAtInputNameAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewTypeAtInputNameAction, NewTypeAtInputNameActionView,NewProcedure, NewProcedureView);
                    break;
                      
                case "Type at XPATH":
                    TypeAtXPATHActionView NewTypeAtXPATHActionView = new TypeAtXPATHActionView();
                    TypeAtXPATHAction NewTypeAtXPATHAction = new TypeAtXPATHAction(Variable1, Variable2, RealBoolVal1);
                    NewTypeAtXPATHActionView.JTextFieldVariable1.setText(Variable1);
                    NewTypeAtXPATHActionView.JTextFieldVariable2.setText(Variable2);
                    NewTypeAtXPATHActionView.JCheckBoxBoolVal1.setSelected(RealBoolVal1);
                    if (LOCKED.equals("true"))
                    {
                    NewTypeAtXPATHActionView.UpdateActionView();
                    }
                    NewTypeAtXPATHActionView.AddListeners(NewTypeAtXPATHAction, STAppFrame, NewProcedure, NewProcedureView);
                    STAppFrame.AddActionToArray (NewTypeAtXPATHAction, NewTypeAtXPATHActionView,NewProcedure, NewProcedureView);
                    break;
                    
                case "Click at XPATH":
                    ClickXPATHActionView NewClickXPATHActionView = new ClickXPATHActionView();
                    ClickXPATHAction NewClickXPATHAction = new ClickXPATHAction(Variable1);
                    NewClickXPATHActionView.JTextFieldVariable1.setText(Variable1);
                    NewClickXPATHActionView.AddListeners(NewClickXPATHAction, STAppFrame, NewProcedure, NewProcedureView);
                    if (LOCKED.equals("true"))
                    {
                    NewClickXPATHActionView.UpdateActionView();
                    }
                    STAppFrame.AddActionToArray (NewClickXPATHAction, NewClickXPATHActionView,NewProcedure, NewProcedureView);
                    break;
                    
                  case "Yes/No Prompt":
                     YesNoPromptPassFailActionView NewYesNoPromptPassFailActionView = new YesNoPromptPassFailActionView();
                     YesNoPromptPassFailAction NewYesNoPromptPassFailAction = new YesNoPromptPassFailAction(Variable1);
                     NewYesNoPromptPassFailActionView.JTextFieldVariable1.setText(Variable1);
                     if (LOCKED.equals("true"))
                    {
                    NewYesNoPromptPassFailActionView.UpdateActionView();
                    }
                     NewYesNoPromptPassFailActionView.AddListeners(NewYesNoPromptPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewYesNoPromptPassFailAction, NewYesNoPromptPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                     
                 case "Find HREF":
                     FindHREFPassFailActionView NewFindHREFPassFailActionView = new FindHREFPassFailActionView();
                     FindHREFPassFailAction NewFindHREFPassFailAction = new FindHREFPassFailAction(Variable1, false);
                     NewFindHREFPassFailActionView.JTextFieldVariable1.setText(Variable1);
                      if (LOCKED.equals("true"))
                    {
                    NewFindHREFPassFailActionView.UpdateActionView();
                    }
                     NewFindHREFPassFailActionView.AddListeners(NewFindHREFPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewFindHREFPassFailAction, NewFindHREFPassFailActionView,NewProcedure, NewProcedureView);
                     break;
                     
                 case "Do NOT Find HREF":
                     NOTFindHREFPassFailActionView NewNOTFindHREFPassFailActionView = new NOTFindHREFPassFailActionView();
                     FindHREFPassFailAction NewNOTFindHREFPassFailAction = new FindHREFPassFailAction(Variable1, true);
                     NewNOTFindHREFPassFailActionView.JTextFieldVariable1.setText(Variable1);
                      if (LOCKED.equals("true"))
                    {
                    NewNOTFindHREFPassFailActionView.UpdateActionView();
                    }
                     NewNOTFindHREFPassFailActionView.AddListeners(NewNOTFindHREFPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewNOTFindHREFPassFailAction, NewNOTFindHREFPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                 case "Find IFrame SRC":
                     FindIFrameSRCPassFailActionView NewFindIFrameSRCPassFailActionView = new FindIFrameSRCPassFailActionView();
                     FindIFrameSRCPassFailAction NewFindIFrameSRCPassFailAction = new FindIFrameSRCPassFailAction(Variable1, false);
                     NewFindIFrameSRCPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewFindIFrameSRCPassFailActionView.UpdateActionView();
                    }
                     NewFindIFrameSRCPassFailActionView.AddListeners(NewFindIFrameSRCPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewFindIFrameSRCPassFailAction, NewFindIFrameSRCPassFailActionView,NewProcedure, NewProcedureView);
                     break;
                 case "Do NOT Find IFrame SRC":
                     NOTFindIFrameSRCPassFailActionView NewNOTFindIFrameSRCPassFailActionView = new NOTFindIFrameSRCPassFailActionView();
                     FindIFrameSRCPassFailAction NewNOTFindIFrameSRCPassFailAction = new FindIFrameSRCPassFailAction(Variable1, true);
                     NewNOTFindIFrameSRCPassFailActionView.JTextFieldVariable1.setText(Variable1);
                        if (LOCKED.equals("true"))
                    {
                    NewNOTFindIFrameSRCPassFailActionView.UpdateActionView();
                    }
                     NewNOTFindIFrameSRCPassFailActionView.AddListeners(NewNOTFindIFrameSRCPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewNOTFindIFrameSRCPassFailAction, NewNOTFindIFrameSRCPassFailActionView,NewProcedure, NewProcedureView);
                     break;
                     
                 case "Find Image SRC":
                     FindImageSRCPassFailActionView NewFindImageSRCPassFailActionView = new FindImageSRCPassFailActionView();
                     FindImageSRCPassFailAction NewFindImageSRCPassFailAction = new FindImageSRCPassFailAction(Variable1, false);
                     NewFindImageSRCPassFailActionView.JTextFieldVariable1.setText(Variable1);
                        if (LOCKED.equals("true"))
                    {
                    NewFindImageSRCPassFailActionView.UpdateActionView();
                    }
                     NewFindImageSRCPassFailActionView.AddListeners(NewFindImageSRCPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewFindImageSRCPassFailAction, NewFindImageSRCPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                     
                 case "Do NOT Find Image SRC":
                     NOTFindImageSRCPassFailActionView NewNOTFindImageSRCPassFailActionView = new NOTFindImageSRCPassFailActionView();
                     FindImageSRCPassFailAction NewNOTFindImageSRCPassFailAction = new FindImageSRCPassFailAction(Variable1, true);
                     NewNOTFindImageSRCPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewNOTFindImageSRCPassFailActionView.UpdateActionView();
                    }
                     NewNOTFindImageSRCPassFailActionView.AddListeners(NewNOTFindImageSRCPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewNOTFindImageSRCPassFailAction, NewNOTFindImageSRCPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                     
                 case "Find Page Title":
                     FindPAGETITLEPassFailActionView NewFindPAGETITLEPassFailActionView = new FindPAGETITLEPassFailActionView();
                     FindPAGETITLEPassFailAction NewFindPAGETITLEPassFailAction = new FindPAGETITLEPassFailAction(Variable1, false);
                     NewFindPAGETITLEPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewFindPAGETITLEPassFailActionView.UpdateActionView();
                    }
                     NewFindPAGETITLEPassFailActionView.AddListeners(NewFindPAGETITLEPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewFindPAGETITLEPassFailAction, NewFindPAGETITLEPassFailActionView,NewProcedure, NewProcedureView);
                     break;
                     
                 case "Do NOT Find Page Title":
                     NOTFindPAGETITLEPassFailActionView NewNOTFindPAGETITLEPassFailActionView = new NOTFindPAGETITLEPassFailActionView();
                     FindPAGETITLEPassFailAction NewNOTFindPAGETITLEPassFailAction = new FindPAGETITLEPassFailAction(Variable1, true);
                     NewNOTFindPAGETITLEPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewNOTFindPAGETITLEPassFailActionView.UpdateActionView();
                    }
                     NewNOTFindPAGETITLEPassFailActionView.AddListeners(NewNOTFindPAGETITLEPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewNOTFindPAGETITLEPassFailAction, NewNOTFindPAGETITLEPassFailActionView,NewProcedure, NewProcedureView);
                     break;
                     
                 case "Find Text":
                     FindTextPassFailActionView NewFindTextPassFailActionView = new FindTextPassFailActionView();
                     FindTextPassFailAction NewFindTextPassFailAction = new FindTextPassFailAction(Variable1, false);
                     NewFindTextPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewFindTextPassFailActionView.UpdateActionView();
                    }
                     NewFindTextPassFailActionView.AddListeners(NewFindTextPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewFindTextPassFailAction, NewFindTextPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                     
                 case "Do NOT Find Text":
                     NOTFindTextPassFailActionView NewNOTFindTextPassFailActionView = new NOTFindTextPassFailActionView();
                     FindTextPassFailAction NewNOTFindTextPassFailAction = new FindTextPassFailAction(Variable1, true);
                     NewNOTFindTextPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewNOTFindTextPassFailActionView.UpdateActionView();
                    }
                     NewNOTFindTextPassFailActionView.AddListeners(NewNOTFindTextPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray(NewNOTFindTextPassFailAction, NewNOTFindTextPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                     
                 case "Find XPATH":
                     FindXPATHPassFailActionView NewFindXPATHPassFailActionView = new FindXPATHPassFailActionView();
                     FindXPATHPassFailAction NewFindXPATHPassFailAction =  new FindXPATHPassFailAction(Variable1, false);
                     NewFindXPATHPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewFindXPATHPassFailActionView.UpdateActionView();
                    }
                     NewFindXPATHPassFailActionView.AddListeners(NewFindXPATHPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray(NewFindXPATHPassFailAction, NewFindXPATHPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                     
                 case "Do NOT Find XPATH":
                     NOTFindXPATHPassFailActionView NewNOTFindXPATHPassFailActionView = new NOTFindXPATHPassFailActionView();
                     FindXPATHPassFailAction NewNOTFindXPATHPassFailAction =  new FindXPATHPassFailAction(Variable1, true);
                     NewNOTFindXPATHPassFailActionView.JTextFieldVariable1.setText(Variable1);
                       if (LOCKED.equals("true"))
                    {
                    NewNOTFindXPATHPassFailActionView.UpdateActionView();
                    }
                     NewNOTFindXPATHPassFailActionView.AddListeners(NewNOTFindXPATHPassFailAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewNOTFindXPATHPassFailAction, NewNOTFindXPATHPassFailActionView, NewProcedure, NewProcedureView);
                     break;
                 
                 case "Type Password at XPATH":
                     TypePasswordAtXPATHActionView NewTypePasswordAtXPATHActionView = new TypePasswordAtXPATHActionView();
                     TypePasswordAtXPATHAction NewTypePasswordAtXPATHAction = new TypePasswordAtXPATHAction(Variable1, Variable2, RealBoolVal1);
                     NewTypePasswordAtXPATHActionView.JTextFieldVariable1.setText(Variable1);
                     NewTypePasswordAtXPATHActionView.JTextFieldPassword.setText(Variable2);
                     NewTypePasswordAtXPATHActionView.JCheckBoxBoolVal1.setSelected(RealBoolVal1);
                       if (LOCKED.equals("true"))
                    {
                    NewTypePasswordAtXPATHActionView.UpdateActionView();
                    }
                     NewTypePasswordAtXPATHActionView.AddListeners(NewTypePasswordAtXPATHAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewTypePasswordAtXPATHAction, NewTypePasswordAtXPATHActionView,NewProcedure, NewProcedureView);
        
                     break;   
                 case "Type Password at Input Name":
                     TypePasswordAtInputNameActionView NewTypePasswordAtInputNameActionView = new TypePasswordAtInputNameActionView();
                     TypePasswordAtInputNameAction NewTypePasswordAtInputNameAction = new TypePasswordAtInputNameAction(Variable1, Variable2, RealBoolVal1);
                     NewTypePasswordAtInputNameActionView.JTextFieldVariable1.setText(Variable1);
                     NewTypePasswordAtInputNameActionView.JTextFieldPassword.setText(Variable2);
                     NewTypePasswordAtInputNameActionView.JCheckBoxBoolVal1.setSelected(RealBoolVal1);
                       if (LOCKED.equals("true"))
                    {
                    NewTypePasswordAtInputNameActionView.UpdateActionView();
                    }
                     NewTypePasswordAtInputNameActionView.AddListeners(NewTypePasswordAtInputNameAction, STAppFrame, NewProcedure, NewProcedureView);
                     STAppFrame.AddActionToArray (NewTypePasswordAtInputNameAction, NewTypePasswordAtInputNameActionView,NewProcedure, NewProcedureView);
        
                     break;   
                     
           }    
  
STAppFrame.UpdateDisplay();
        }   
     
    }
STAppFrame.addTargetBrowserItemListener( new ItemListener() {
    
        public void itemStateChanged (ItemEvent e )
        {
         if ((e.getStateChange() == ItemEvent.SELECTED)) {
            Object ActionType = e.getItem();
            String TargetBrowser = ActionType.toString();
           STAppFrame.TargetBrowser = TargetBrowser;
          STAppFrame.changes = true;
           if (STAppFrame.TargetBrowser=="Chrome")
           {
              STAppFrame.setOSTypeActive(true);
           }
           else
           {
            STAppFrame.setOSTypeActive(false);   
           }
         }
        }
        
        });

STAppFrame.addjButtonDoStuffActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
 STAppFrame.RunActions(); 
 
  
        }
      }
    );
   
      STAppFrame.addjButtonClearEmailSettingsListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
 STAppFrame.ClearEmailSettings(); 
 
  
        }
      }
    );
      
    STAppFrame.addjButtonNewBugActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
   STAppFrame.AddNewBug();  
 
 
  }
                                          
      }
    );
  
 
  
 return STAppFrame;

  }
  public int GetCurrentWindow()
  {
     
     int frameindex = -1;
     int allframeindex = 0;
    for (SeleniumTestTool thisFrame : MDIClasses) {
			if (thisFrame.isSelected()==true)
                        {
                         frameindex = allframeindex;
                         
                        }
                    allframeindex++;
		}
     

      return frameindex;
  }
  public void CheckArgs(String[] args)
  {

      if (args[0].equals("open"))
     {
     File file_to_open = new File(args[1]);
     try {
     int MDI_CLASS_INDEX = OpenFile(file_to_open, MDIClasses);
     if (MDI_CLASS_INDEX>=0)
     {
     DisplayWindow(MDI_CLASS_INDEX);
     }
     }
       catch (IOException | ClassNotFoundException ex) {
          System.out.println(ex.toString());
       }
    
     
     }
     if (args[0].equals("run"))
     {
    File file_to_open = new File(args[1]);
     try {
     int MDI_CLASS_INDEX = OpenFile(file_to_open, MDIClasses);
      if (MDI_CLASS_INDEX>=0)
     { 
     DisplayWindow(MDI_CLASS_INDEX);
          MDIClasses.get(MDI_CLASS_INDEX).RunActions(); 
     }
     }
       catch (IOException | ClassNotFoundException ex) {
         System.out.println(ex.toString());
       }
     
    
     }   
  }
  
   public void DisplayWindow (int MDI_CLASS_INDEX)
   {
       if (MDI_CLASS_INDEX>=0)
       {
  MDIClasses.get(MDI_CLASS_INDEX).setTitle("Browsermator - " + MDIClasses.get(MDI_CLASS_INDEX).filename);
  MDIClasses.get(MDI_CLASS_INDEX).setVisible(true);
  MDIClasses.get(MDI_CLASS_INDEX).setSize(1200,900);
  saveMenuItem.setEnabled(true);
  SeleniumToolDesktop.add(MDIClasses.get(MDI_CLASS_INDEX));
  
  MDIClasses.get(MDI_CLASS_INDEX).moveToFront();

       MDIClasses.get(MDI_CLASS_INDEX).setVisible(true); 
       try
       {
        MDIClasses.get(MDI_CLASS_INDEX).setSelected(true);
       }
       catch (PropertyVetoException e)
       {
           System.out.println("Veto: " + e.toString());
       }
       String thisopenfile_raw = MDIClasses.get(MDI_CLASS_INDEX).filename;
       
         String thisopenfile="";
                     String twoslashes = "\\" + "\\";
                     thisopenfile = thisopenfile_raw.replace(twoslashes, "\\");
       JMenuItem newfileitem = new JMenuItem(thisopenfile);
      Boolean hasitem = false;
       for (int jm = 0; jm<jMenuView.getItemCount(); jm++)
       {
       if (jMenuView.getItem(jm).getText()==thisopenfile)
       {
           hasitem = true;
       }
       }
       if (!hasitem)
       {       
       jMenuView.add(newfileitem);
       newfileitem.addActionListener(new ActionListener() {
        
       @Override
        public void actionPerformed (ActionEvent e )
        {

       JInternalFrame[] iframes = SeleniumToolDesktop.getAllFrames();
                for (JInternalFrame iframe : iframes)
                {
                  
                   String thisFrameName = iframe.getTitle();
                     String twoslashes = "\\" + "\\";
        
        
                  
                    
                       if (newfileitem.getText().equals(thisFrameName))
                       {
                         
                           iframe.toFront();
                           // iframe.requestFocus(true);
                          try
                          {
                              iframe.setSelected(true);
                          }
                          catch (PropertyVetoException ec)
                          {
                              System.out.println(ec.toString());
                          }
                       }
                  
                }   
        
             
         
        }
    });
       }
       
       
       
       }
   }

  
    public final void LoadGlobalEmailSettings() throws IOException 
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



   String smtp_hostname = applicationProps.getProperty("smtp_hostname");
   String login_name = applicationProps.getProperty("email_login_name");
   String password = applicationProps.getProperty("email_login_password");
   if (!"".equals(password))
   {
   try
   {
   password = Protector.decrypt(password);
   }
   catch (GeneralSecurityException | IOException e)
           {
               System.out.println("decrypt error" + e.toString());
           }
   }
   String to = applicationProps.getProperty("email_to");
   String from = applicationProps.getProperty("email_from");
   String subject = applicationProps.getProperty("email_subject");

   Navigator.setSMTPHostname(smtp_hostname);
   Navigator.setEmailLoginName(login_name);
   Navigator.setEmailPassword(password);
   Navigator.setEmailTo(to);
   Navigator.setEmailFrom(from);
   Navigator.setSubject(subject);
   Navigator.setVersion("Version: " + version);
  
	}
 public void SaveGlobalEmailSettings() throws IOException
 {

    File configFile = new File("browsermator_config.properties");
     FileInputStream input = new FileInputStream("browsermator_config.properties");

try {
    Properties props = new Properties();
   props.load(input);
    props.setProperty("smtp_hostname", Navigator.getSMTPHostname() );
    props.setProperty("email_login_name", Navigator.getEmailLoginName() );
    String password = Navigator.getEmailPassword();
    if (!"".equals(password))
    {
    password = Protector.encrypt(password);
    }
    props.setProperty("email_login_password", password );
    props.setProperty("email_to", Navigator.getEmailTo() );
    props.setProperty("email_from", Navigator.getEmailFrom() );
    props.setProperty("email_subject", Navigator.getSubject() );
    
    FileWriter writer = new FileWriter(configFile);
    props.store(writer, "browsermator_settings");
    writer.close();
     JOptionPane.showMessageDialog (null, "Email Settings Saved", "Save Complete", JOptionPane.INFORMATION_MESSAGE);
    
} 

    catch (Exception e) {
			System.out.println("Exception: " + e);
		} 
 }
  public static void main(String[] args) { 
  try
  {
   
      STAppController app = new STAppController(args); 
   app.setVisible(true); 
 
  }
  catch (PropertyVetoException e)
          {
           System.out.println("Exception: " + e);
          }
  
  }
  
  
    
                         
}
