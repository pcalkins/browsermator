package browsermator.com;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;



public final class STAppController extends JFrame {
 
public final SiteTestView Navigator;
public JDesktopPane SeleniumToolDesktop;
private UIManager.LookAndFeelInfo LAFOptions[];
private JRadioButtonMenuItem LookAndFeelOptions[];
private ButtonGroup LookAndFeelGroup;
     private JMenuBar menuBar;
     private JMenu fileMenu;
     private JMenuItem openMenuItem;
     private JMenuItem saveMenuItem;
     private JMenuItem exitMenuItem;
     private JMenuItem closeMenuItem;
     private JMenu jMenuView;
     private JMenu jMenuThemes;
     private JMenu helpMenu;
     private JMenuItem contentsMenuItem;
     private JMenuItem aboutMenuItem;
     private JMenuItem newFileItem;
     private JMenuItem saveAsMenuItem;
     private JMenuItem uploadFileToCloudMenuItem;
      private JMenuItem browseCloudMenuItem;
      String filename;
      private JMenuItem importMenuItem;
private final String version = "0.1.35";
    private int CurrentMDIWindowIndex;
   public final String ProgramVersion = "0.1.35";
   public String loginName;
   public String loginPassword;
   
  public int user_id;
//  String rootURL = "http://localhost";
 String rootURL = "https://www.browsermator.com";
     ArrayList<SeleniumTestTool> MDIClasses = new ArrayList();


  public STAppController(String[] args) throws PropertyVetoException {
  
     super ("Browsermator");
                 Properties newProps = new Properties();
                  this.loginName = "";
    this.loginPassword = "";
         Boolean file_exists = false;
              String userdir = System.getProperty("user.home");
    try
{
    File f = new File(userdir + File.separator + "browsermator_config.properties");
if(f.exists() && !f.isDirectory()) { 
   file_exists = true;
}
if (file_exists == false)
{
    CreateConfigFile();
}
     FileInputStream input = new FileInputStream(userdir + File.separator + "browsermator_config.properties");
   newProps.load(input);
 
   int winLocY =  Integer.parseInt(newProps.getProperty("main_window_locationY", "0"));
   int winLocX =   Integer.parseInt(newProps.getProperty("main_window_locationX", "0"));
   int winWidth =  Integer.parseInt(newProps.getProperty("main_window_sizeWidth", "1025"));
   int winHeight = Integer.parseInt(newProps.getProperty("main_window_sizeHeight", "802"));
      Point startPosition = new Point(winLocX, winLocY);
            if (isLocationInScreenBounds(startPosition) )
        {

        super.setLocation(startPosition);
        super.setSize(winWidth, winHeight);
        }
       
      else
      {
  int Width = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getWidth();
int Height = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
super.setSize(Width-300,Height-300);
      super.setLocation(0,0);
    super.setExtendedState( super.getExtendedState()|JFrame.MAXIMIZED_BOTH );
      }
    input.close();

    
} 

    catch (Exception e) {
			System.out.println("Exception: " + e);
		} 
   // super.setExtendedState( super.getExtendedState()|JFrame.MAXIMIZED_BOTH );
   //  super.setVisible(true);
 
    Navigator = new SiteTestView();
  //  String RecentFiles[] = null;
 //   RecentFiles = Navigator.getRecentFiles();
 //   MouseListener[] RecentFilesListeners = null;
   
  
   super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
   
    super.addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent windowEvent) {
     int closure = 0;
     int number_of_windows_to_close = 0;
   
    
     int last_window_index = MDIClasses.size()-1;
     for (int x = last_window_index; x>-1; x--)
     {
        closure  =  CheckToSaveChanges(MDIClasses.get(x), true);
           
      if (closure==1)
      {
      MDIClasses.get(x).setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
       MDIClasses.get(x).setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      number_of_windows_to_close++;
      }
    
     }
     if (number_of_windows_to_close==MDIClasses.size())
     {
     for (int x = 0; x<number_of_windows_to_close; x++)
      { 
       MDIClasses.remove(MDIClasses.size()-1);
    }
     }
  
      

        
    if (MDIClasses.size()==0)
    {
            Properties newProps = new Properties();
         Boolean file_exists = false;
              String userdir = System.getProperty("user.home");
    try
{
    File f = new File(userdir + File.separator + "browsermator_config.properties");

     FileInputStream input = new FileInputStream(userdir + File.separator + "browsermator_config.properties");
   newProps.load(input);
 
   
      
      newProps.setProperty("main_window_locationY", Integer.toString(windowEvent.getWindow().getY()));
      newProps.setProperty("main_window_locationX", Integer.toString(windowEvent.getWindow().getX()));
      newProps.setProperty("main_window_sizeWidth", Integer.toString(windowEvent.getWindow().getWidth()));
      newProps.setProperty("main_window_sizeHeight", Integer.toString(windowEvent.getWindow().getHeight()));
    
    
    FileWriter writer = new FileWriter(userdir + File.separator + "browsermator_config.properties");
    newProps.store(writer, "browsermator_settings");
    writer.close();
   
    
} 

    catch (Exception e) {
			System.out.println("Exception: " + e);
		} 
      System.exit(0);
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
Navigator.setSize(1000,800);
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
 
    ImportFileFunct(newfiles, CurrentMDIWindowIndex);

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
                 
         
                 CurrentMDIWindowIndex = GetCurrentWindow();
                 if (CurrentMDIWindowIndex !=-1)
                 {
                     SeleniumTestTool STAppFrame = MDIClasses.get(CurrentMDIWindowIndex);
                 
                    ThreadSaveFile(STAppFrame, false, false);
                    
                 }
                   else
  {
    JOptionPane.showMessageDialog (null, "No Active Window to save. Click to select a Window.", "No Selected Window", JOptionPane.INFORMATION_MESSAGE);   
  }
                 
            
           } 
       });
  
  addFileMenuCloseActionListener( new ActionListener() {
           public void actionPerformed (ActionEvent evt) {
                  
         
                 CurrentMDIWindowIndex = GetCurrentWindow();
                 if (CurrentMDIWindowIndex !=-1)
                 {
                     SeleniumTestTool STAppFrame = MDIClasses.get(CurrentMDIWindowIndex);
                 
                  int closed =  CheckToSaveChanges(STAppFrame, false);
           
    
       MDIClasses.remove(MDIClasses.size()-1); 
       STAppFrame.dispose();
      }
                    
              
                   else
  {
    JOptionPane.showMessageDialog (null, "No Active Window to close. Click to select a Window.", "No Selected Window", JOptionPane.INFORMATION_MESSAGE);   
  }
                 
              
            
           } 
       });
  addFileMenuBrowseCloudMenuItemActionListener(
 new ActionListener() {
           public void actionPerformed (ActionEvent evt) {
                  
         
               OpenBrowserMatorCloud();
                 
              
            
           } 
       });
  addFileMenuuploadFileToCloudMenuItemActionListener(
 new ActionListener() {
           public void actionPerformed (ActionEvent evt) {
                  
         
                 CurrentMDIWindowIndex = GetCurrentWindow();
                 if (CurrentMDIWindowIndex !=-1)
                 {
                     SeleniumTestTool STAppFrame = MDIClasses.get(CurrentMDIWindowIndex);
                 
                   UploadFile(STAppFrame, true, false);
                    
                 }
                   else
  {
    JOptionPane.showMessageDialog (null, "No Active Window to save. Click to select a Window.", "No Selected Window", JOptionPane.INFORMATION_MESSAGE);   
  }
                 
              
            
           } 
       });
  addFileMenuSaveAsActionListener(
      new ActionListener() {
           public void actionPerformed (ActionEvent evt) {
                  
         
                 CurrentMDIWindowIndex = GetCurrentWindow();
                 if (CurrentMDIWindowIndex !=-1)
                 {
                     SeleniumTestTool STAppFrame = MDIClasses.get(CurrentMDIWindowIndex);
                 
                    ThreadSaveFile(STAppFrame, true, false);
                    
                 }
                   else
  {
    JOptionPane.showMessageDialog (null, "No Active Window to save. Click to select a Window.", "No Selected Window", JOptionPane.INFORMATION_MESSAGE);   
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
     STAppFrame.ShowStoredVarControls(false);

      STAppFrame.setTargetBrowser("Chrome");
      STAppFrame.setOSType("Windows32");
   STAppFrame.setClosable(true);
  STAppFrame.setMaximizable(true);
  STAppFrame.setTitle("Browsermator - " + STAppFrame.filename);
  STAppFrame.setResizable(true);
 
 
  SeleniumToolDesktop.add(STAppFrame);
  STAppFrame.moveToFront();


  try
  {
       STAppFrame.setMaximum(true);
    

  STAppFrame.setVisible(true);
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
                    int closed =  CheckToSaveChanges(STAppFrame, false);
           
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
       STAppFrame.addjButtonLoadEmailSettingsListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 try
 {
 STAppFrame.LoadGlobalEmailSettings();
 }
 catch (Exception ex)
 {
     System.out.println ("Exception loading global email settings: " + ex.toString());
 }
  
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
           if (STAppFrame.TargetBrowser=="Chrome" || "Firefox".equals(STAppFrame.TargetBrowser))
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
    STAppFrame.addjButtonGutsViewActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
   STAppFrame.ShowGuts();

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
     STAppFrame.addjButtonNewDataLoopActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
//   File chosenCSVFile = BrowseForCSVFile();
//   if (chosenCSVFile!=null)
//   {
//   STAppFrame.AddNewDataLoop(chosenCSVFile);  
//   }
              File blankfile = null;
   STAppFrame.AddNewDataLoop(blankfile);  
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
    OpenFile(newfiles[fileindex], MDIClasses, false);
     
    
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
     
     

      OpenFile(RecentFile, MDIClasses, false);
       
          
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
     
     

       OpenFile(RecentFile, MDIClasses, false);
 
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
     
     

    OpenFile(RecentFile, MDIClasses, false);
     
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
     
     

     OpenFile(RecentFile, MDIClasses, false);
  
       
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
     
     

     OpenFile(RecentFile, MDIClasses, false);
    
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
  OpenFile(newfiles[fileindex], MDIClasses, false);
    
    
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
 STAppFrame.ShowStoredVarControls(false);
  STAppFrame.setTargetBrowser("Chrome");
  STAppFrame.setOSType("Windows32");
     STAppFrame.setClosable(true);
 
  STAppFrame.setMaximizable(true);
  STAppFrame.setTitle("Browsermator - " + STAppFrame.filename);
  STAppFrame.setResizable(true);
  STAppFrame.setSize(1024, 800);
   SeleniumToolDesktop.add(STAppFrame);
  STAppFrame.moveToFront();
  

  try
  {
  
   STAppFrame.setMaximum(true);
   STAppFrame.setVisible(true);
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
                    int closed =  CheckToSaveChanges(STAppFrame, false);
           
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
          STAppFrame.addjButtonLoadEmailSettingsListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 try
 {
 STAppFrame.LoadGlobalEmailSettings();
 }
 catch (Exception ex)
 {
     System.out.println ("Exception loading global email settings: " + ex.toString());
 }
  
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
           if ("Chrome".equals(STAppFrame.TargetBrowser)|| "Firefox".equals(STAppFrame.TargetBrowser))
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
    STAppFrame.addjButtonGutsViewActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
   STAppFrame.ShowGuts();

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
      STAppFrame.addjButtonNewDataLoopActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
//   File chosenCSVFile = BrowseForCSVFile();
//   if (chosenCSVFile!=null)
//   {
            File blankfile = new File("placeholder");
   STAppFrame.AddNewDataLoop(blankfile);  
//   }
 
  }
                                          
      }
    );
  
  
  

  }
  }
    ); 
  
  
  }
   public static boolean isLocationInScreenBounds(Point location) 
    {
      
      // Check if the location is in the bounds of one of the graphics devices.
    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice[] graphicsDevices = graphicsEnvironment.getScreenDevices();
    Rectangle graphicsConfigurationBounds = new Rectangle();
    
    // Iterate over the graphics devices.
    for (int j = 0; j < graphicsDevices.length; j++) {
      
      // Get the bounds of the device.
      GraphicsDevice graphicsDevice = graphicsDevices[j];
      graphicsConfigurationBounds.setRect(graphicsDevice.getDefaultConfiguration().getBounds());
      
        // Is the location in this bounds?
      graphicsConfigurationBounds.setRect(graphicsConfigurationBounds.x, graphicsConfigurationBounds.y,
          graphicsConfigurationBounds.width, graphicsConfigurationBounds.height);
      if (graphicsConfigurationBounds.contains(location.x, location.y)) {
        
        // The location is in this screengraphics.
        return true;
        
      }
      
    }
    
    // We could not find a device that contains the given point.
    return false;
    
    }
   public final void CreateConfigFile()
  {
    String userdir = System.getProperty("user.home");
      File newconfig = new File(userdir + File.separator + "browsermator_config.properties");
      Properties newProps = new Properties();

       newProps.setProperty("main_window_locationY", "0");
      newProps.setProperty("main_window_locationX", "0");
      newProps.setProperty("main_window_sizeWidth", "1000");
      newProps.setProperty("main_window_sizeHeight", "800");   
   
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
  public int CheckToSaveChanges(SeleniumTestTool STAppFrame, Boolean savenow) 
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
    AllFieldValuesCheck.add(thisproc.DataFile);
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
   STAppFrame.changes = true;
   
}

if (STAppFrame.changes==false)
{
  return 0;
}
else
{
  int result = JOptionPane.showConfirmDialog(STAppFrame,"Do you wish to save changes to " + STAppFrame.filename + "?","Browsermator",JOptionPane.YES_NO_CANCEL_OPTION);
            switch(result){
                  case JOptionPane.YES_OPTION:
                 //   SaveFile
                     if (STAppFrame.filename.contains("untitled"))
                     {
                      if (savenow)
                      {
                        try
                          {
                        SaveFileNow(STAppFrame, true, false);
                          }
                          catch (Exception ex)
                          {
                              
                          }
                      }  
                      else
                      {
                          ThreadSaveFile(STAppFrame, true, false);  
                      }
                     }
                     else
                     {
                          if (savenow)
                      {
                          try
                          {
                        SaveFileNow(STAppFrame, false, false);
                          }
                          catch (Exception ex)
                          {
                              
                          }
                                  
                          }  
                      else
                      {
                          ThreadSaveFile(STAppFrame, false, false);  
                      }
                       
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
     int closure = 0;
     int number_of_windows_to_close = 0;
   
    
     int last_window_index = MDIClasses.size()-1;
     for (int x = last_window_index; x>-1; x--)
     {
        closure  =  CheckToSaveChanges(MDIClasses.get(x), true);
           
      if (closure==1)
      {
      MDIClasses.get(x).setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
       MDIClasses.get(x).setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      number_of_windows_to_close++;
      }
    
     }
     if (number_of_windows_to_close==MDIClasses.size())
     {
     for (int x = 0; x<number_of_windows_to_close; x++)
      { 
       MDIClasses.remove(MDIClasses.size()-1);
    }
     }
  
      

        
    if (MDIClasses.size()==0)
    {
            Properties newProps = new Properties();
         Boolean file_exists = false;
              String userdir = System.getProperty("user.home");
    try
{
    File f = new File(userdir + File.separator + "browsermator_config.properties");

     FileInputStream input = new FileInputStream(userdir + File.separator + "browsermator_config.properties");
   newProps.load(input);
 
   
      
      newProps.setProperty("main_window_locationY", Integer.toString(getY()));
      newProps.setProperty("main_window_locationX", Integer.toString(getX()));
      newProps.setProperty("main_window_sizeWidth", Integer.toString(getWidth()));
      newProps.setProperty("main_window_sizeHeight", Integer.toString(getHeight()));
    
    
    FileWriter writer = new FileWriter(userdir + File.separator + "browsermator_config.properties");
    newProps.store(writer, "browsermator_settings");
    writer.close();
   
    
} 

    catch (Exception e) {
			System.out.println("Exception: " + e);
		} 
      System.exit(0);
    } 
   
    
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
        browseCloudMenuItem = new javax.swing.JMenuItem();
        uploadFileToCloudMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        closeMenuItem = new javax.swing.JMenuItem();
        importMenuItem = new javax.swing.JMenuItem();
        newFileItem.setMnemonic('n');
        newFileItem.setText("New");
        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        closeMenuItem.setText("Close");
        importMenuItem.setMnemonic('i');
        importMenuItem.setText("Import");
        fileMenu.add(newFileItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(importMenuItem);
        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        saveAsMenuItem.setText("Save As");
        saveAsMenuItem.setMnemonic('a');
        browseCloudMenuItem.setText("BrowserMator File Cloud");
        uploadFileToCloudMenuItem.setText("Upload to BrowserMator Cloud");
           saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));  
           openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK)); 
           closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));  
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
        fileMenu.add(closeMenuItem);
        
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
      
        fileMenu.add(exitMenuItem);
        
        

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);
 fileMenu.add(saveAsMenuItem);
 fileMenu.add(browseCloudMenuItem);
 fileMenu.add(uploadFileToCloudMenuItem);
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
   
  


    //    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
    }  
   public void addFileMenuCloseActionListener (ActionListener listener)
   {
       closeMenuItem.addActionListener(listener);
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
   public void addFileMenuBrowseCloudMenuItemActionListener(ActionListener listener)
   {
       browseCloudMenuItem.addActionListener(listener);
   }
   public void addFileMenuuploadFileToCloudMenuItemActionListener (ActionListener listener)
   {
       uploadFileToCloudMenuItem.addActionListener(listener);
   }
   public void addFileMenuImportActionListener (ActionListener listener)
   {
       importMenuItem.addActionListener(listener);
   }
   public void addFileMenuOpenActionListener (ActionListener listener) {
       openMenuItem.addActionListener(listener);
      
   }
   

  public File BrowseForCSVFile()
    {
 final JFileChooser CSVFileChooser = new JFileChooser();

   CSVFileChooser.addChoosableFileFilter(new ExtensionFileFilter(
                    new String[] { ".CSV", ".XLSX", ".XLSXM", ".XLS", ".XLSM" },
                    "Data File (*.CSV|XLSX|XLSXM|XLS|XLSM)"));

    CSVFileChooser.addChoosableFileFilter(new ExtensionFileFilter(
                    new String[] { ".CSV" },
                    "Comma Delimited File (*.CSV)"));
    CSVFileChooser.addChoosableFileFilter(new ExtensionFileFilter(
                    new String[] { ".XLSX", ".XLSXM", ".XLS", ".XLSM" },
                    "Excel File (*.XLSX|XLSXM|XLS|XLSM)"));


    // Turn off 'All Files' capability of file chooser,
    // so only our custom filter is used.
    CSVFileChooser.setAcceptAllFileFilterUsed(false);


int returnVal = CSVFileChooser.showOpenDialog(this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = CSVFileChooser.getSelectedFile();   

    return file;
            }
            else
            {
            return null;
            }
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
public void OpenBrowserMatorCloud()
{
    BrowserMatorFileCloud thisCloud = new BrowserMatorFileCloud(this);
    thisCloud.ShowFileCloudWindow();
}
 public void UploadFile(SeleniumTestTool STAppFrame, boolean isSaveAs, boolean isFlatten)
  {
      int current_MDI_Index = GetCurrentWindow();
      
     UploadFileThread UPLOADREF = new UploadFileThread(this, STAppFrame, current_MDI_Index);
  UPLOADREF.execute();  
   
  }
   public void SaveFile(SeleniumTestTool STAppFrame, boolean isSaveAs, boolean isFlatten, int calling_MDI_Index) throws IOException, XMLStreamException
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
File file=null;
   
    if (isSaveAs==true || STAppFrame.filename.contains("untitled") == true)
    {
     FileNameExtensionFilter filefilter = new FileNameExtensionFilter("Browsermator file (*.browsermation)","browsermation");

    fc.setFileFilter(filefilter);
    if (STAppFrame.filename.contains("untitled") == false  && isSaveAs==true)
    {
          String[] left_side_of_dot = STAppFrame.filename.split("\\.");
                if (isFlatten)
                {
                 file = new File(left_side_of_dot[0] + "-flat.browsermation");   
                }
                else
                {
                 file = new File(left_side_of_dot[0] + ".browsermation");
                }
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
xmlfile.writeAttribute("Filename",file.getName());
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
xmlfile.writeAttribute("Type", thisbug.Type);   

if ("Dataloop".equals(thisbug.Type))
{

xmlfile.writeAttribute("DataLoopFile", thisbug.DataFile);

}
  if (isFlatten==true && "Dataloop".equals(thisbug.Type))
  {
        int indexer = thisbug.index;
           ProcedureView thisbugview = STAppFrame.BugViewArray.get(indexer-1);
       int number_of_rows = thisbugview.myTable.DataTable.getRowCount();
       if (number_of_rows == 0)
       {
         
           STAppFrame.FillTables(thisbug, thisbugview);
       }
  for( Action ThisAction : thisbug.ActionsList ) { 
 ThisAction.InitializeLoopTestVars(number_of_rows);
  } 
int action_index_for_flatten = 0;
 for (int x = 0; x<number_of_rows; x++)
    {
     for (Action thisaction: thisbug.ActionsList)
    {
         String original_value1 = thisaction.Variable1;
           String original_value2 = thisaction.Variable2;
               DataLoopVarParser var1Parser = new DataLoopVarParser(thisaction.Variable1);
    DataLoopVarParser var2Parser = new DataLoopVarParser(thisaction.Variable2);
    if (var1Parser.hasDataLoopVar==false && var2Parser.hasDataLoopVar==false)
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
   
    try
    {
         xmlfile.writeStartElement("Variable2");
    String encpass = Protector.encrypt(thisaction.Variable2);
    xmlfile.writeCharacters(encpass);
    xmlfile.writeEndElement(); 
    }
    catch (Exception e)
            {
            System.out.println("encrypt error.. passvar2: " + e.toString());
                   System.out.println("encrypt error.. passvar2: " + e.toString());
             xmlfile.writeStartElement("Variable2");
    String encpass = "";
    xmlfile.writeCharacters(thisaction.Variable2);
    xmlfile.writeEndElement();   
            }
    
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
    String ActionIndex = Integer.toString(action_index_for_flatten);   
    
    xmlfile.writeStartElement("ActionIndex");
    xmlfile.writeCharacters(ActionIndex);
    action_index_for_flatten++;
    xmlfile.writeEndElement();
    
    xmlfile.writeEndElement();  
    }
    else
    {
               String concat_variable;
            String concat_variable2;
 concat_variable = var1Parser.GetFullValue(x, thisbug.DataSet);
 if (!"".equals(concat_variable))
 {
      thisaction.Variable1 = concat_variable;
 }
      concat_variable2 = var2Parser.GetFullValue(x, thisbug.DataSet);
     if (!"".equals(concat_variable2))
     {
      thisaction.Variable2 = concat_variable2;  
     }   
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
   
    try
    {
         xmlfile.writeStartElement("Variable2");
    String encpass = Protector.encrypt(thisaction.Variable2);
    xmlfile.writeCharacters(thisaction.Variable2);
    xmlfile.writeEndElement(); 
    }
    catch (Exception e)
            {
            System.out.println("encrypt error.. passvar2: " + e.toString());
             xmlfile.writeStartElement("Variable2");
    String encpass = "";
    xmlfile.writeCharacters(thisaction.Variable2);
    xmlfile.writeEndElement();   
            }
   
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
    String ActionIndex = Integer.toString(action_index_for_flatten);   
    xmlfile.writeStartElement("ActionIndex");
    xmlfile.writeCharacters(ActionIndex);
    action_index_for_flatten++;
    xmlfile.writeEndElement();
    
    xmlfile.writeEndElement();    
        thisaction.Variable1 = original_value1;
   thisaction.Variable2 = original_value2;
    }
  
    }
    }
  }
  else
  {
 
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
       try
    {
         xmlfile.writeStartElement("Variable2");
    String encpass = Protector.encrypt(thisaction.Variable2);
    xmlfile.writeCharacters(encpass);
    xmlfile.writeEndElement(); 
    }
    catch (Exception e)
            {
            System.out.println("encrypt error.. passvar2: " + e.toString());
                   System.out.println("encrypt error.. passvar2: " + e.toString());
             xmlfile.writeStartElement("Variable2");
    String encpass = "";
    xmlfile.writeCharacters(thisaction.Variable2);
    xmlfile.writeEndElement();   
            }
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
            if (isFlatten)
            {

     OpenFileThread OPENREF = new OpenFileThread(this, file, this.MDIClasses, calling_MDI_Index, true, false);
  OPENREF.execute();
            }
            else
            {
 
    STAppFrame.setProperties(file.getAbsolutePath());
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
    STAppFrame.AllFieldValues.add(thisproc.DataFile);
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
        }
if (isFlatten==false)
{
this.filename = file.getAbsolutePath();
STAppFrame.setProperties(this.filename);
this.Navigator.addRecentFile(file.getAbsolutePath());

}
else
{
   
}
     
        }

  public void SaveFileNow (SeleniumTestTool STAppFrame, boolean isSaveAs, boolean isFlatten) throws IOException, XMLStreamException
  {
       int current_MDI_Index = GetCurrentWindow();
  SaveFile (STAppFrame, isSaveAs, isFlatten, current_MDI_Index);
  }
  public void ThreadSaveFile(SeleniumTestTool STAppFrame, boolean isSaveAs, boolean isFlatten)
  {
      int current_MDI_Index = GetCurrentWindow();
      
     SaveFileThread SAVEREF = new SaveFileThread(this, STAppFrame, isSaveAs, isFlatten, current_MDI_Index);
  SAVEREF.execute();  
  }
  
     public void OpenFile (File file, ArrayList<SeleniumTestTool> MDIClasses, boolean RunIt) 
    {
        
    int current_MDI_Index = GetCurrentWindow();
  OpenFileThread OPENREF = new OpenFileThread(this, file, MDIClasses, current_MDI_Index, false, RunIt);
  OPENREF.execute();
  
 

    }
      public void OpenFile (File file, ArrayList<SeleniumTestTool> MDIClasses, boolean RunIt, boolean fromCloud) 
    {
        
    int current_MDI_Index = GetCurrentWindow();
  OpenFileThread OPENREF = new OpenFileThread(this, file, MDIClasses, current_MDI_Index, false, RunIt, fromCloud);
  OPENREF.execute();
 

    }
     
     public void ImportFileFunct (File[] files, int CurrentMDIWindowIndex)
     {
            ImportFileThread IMPORTREF = new ImportFileThread(this, files, CurrentMDIWindowIndex);
   IMPORTREF.execute();
     }
  
  public SeleniumTestTool BuildNewWindow(Document doc)
  {
   
    
   NamedNodeMap NewAttributes = doc.getElementsByTagName("BrowserMatorWindow").item(0).getAttributes(); 
   
   String filename_read = NewAttributes.getNamedItem("Filename").getNodeValue();
   SeleniumTestTool STAppFrame = new SeleniumTestTool(filename_read);
 // STAppFrame.ShowStoredVarControls(false);
   STAppFrame.filename = filename_read;

      STAppFrame.setClosable(true);
  STAppFrame.setMaximizable(true);
  STAppFrame.setTitle("Browsermator - " + STAppFrame.filename);
  STAppFrame.setResizable(true);
  STAppFrame.setSize(1024, 800);
   STAppFrame.setClosable(true);
  STAppFrame.setMaximizable(true);

    STAppFrame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
     @Override 
     public void internalFrameClosing(InternalFrameEvent e) {
    
    
      int closed =  CheckToSaveChanges(STAppFrame, false);
           
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
  String OSType = "Windows32";
  String EmailPassword = "";
  String unepassword = "";
  String EmailTo = "";
  String EmailFrom = "";
  String EmailSubject = "";
  
try
{
    NodeList SettingsNodes = FileSettingsNode.item(0).getChildNodes();


    for (int k = 0; k<SettingsNodes.getLength(); k++)
    {
   thisSettingsNodeName = SettingsNodes.item(k).getNodeName();
   thisSettingsNodeValue = SettingsNodes.item(k).getTextContent();
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
   //            System.out.println("decrypt error" + e.toString());
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
    }
catch (Exception e)
        {
            System.out.println("Error loading filesettings: " + e.toString());
          
        }
try
{
   NodeList ProcedureList = doc.getElementsByTagName("Procedure");
   
for (int i = 0; i < ProcedureList.getLength(); ++i)
{
    
    
    
   
    Element Procedure = (Element) ProcedureList.item(i);
   
    String DataFile = Procedure.getAttribute("DataLoopFile");
    if (!"".equals(DataFile))
    {
        
        File DataFile_file = new File(DataFile);
         if (DataFile_file.exists())
        {
            STAppFrame.AddNewDataLoop(DataFile_file);
        }
        else
        {
           STAppFrame.AddNewBug();
        }
       
    }
    else
    {
     STAppFrame.AddNewBug();    
    }
    
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
    String Password = "";
    
    
   Boolean RealBoolVal1 = false;
   Boolean boolLOCKED = false;
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
             if (LOCKED.equals("true"))
                    {
                   boolLOCKED = true;
                    }
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
       Password = Protector.decrypt(Variable2);
       Variable2 = Password;
       }
       catch (Exception e)
       {
     //   System.out.println("Load/decrypt error: " + e.toString());
       }
   }
  ActionsMaster NewActionsMaster = new ActionsMaster();
   
   HashMap<String, Action> thisActionHashMap = NewActionsMaster.ActionHashMap;
   HashMap<String, ActionView> thisActionViewHashMap = NewActionsMaster.ActionViewHashMap;
   HashMap<String, Action> thisPassFailActionHashMap = NewActionsMaster.PassFailActionHashMap;
   HashMap<String, ActionView> thisPassFailActionViewHashMap = NewActionsMaster.PassFailActionViewHashMap;
    if (thisActionHashMap.containsKey(ActionType))
           {
               Action thisActionToAdd = (Action) thisActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, boolLOCKED);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, boolLOCKED);
               thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, NewProcedure, NewProcedureView);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, NewProcedure, NewProcedureView);
               STAppFrame.AddActionToArray (thisActionToAdd, thisActionViewToAdd, NewProcedure, NewProcedureView);
               
           }      
 
     if (thisPassFailActionHashMap.containsKey(ActionType))
             {
               Action thisActionToAdd = (Action) thisPassFailActionHashMap.get(ActionType);
               ActionView thisActionViewToAdd = (ActionView) thisPassFailActionViewHashMap.get(ActionType);
               thisActionToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, boolLOCKED);
               thisActionViewToAdd.SetVars(Variable1, Variable2, Password, RealBoolVal1, boolLOCKED);
               thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, NewProcedure, NewProcedureView);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, NewProcedure, NewProcedureView);
              STAppFrame.AddActionToArray (thisActionToAdd, thisActionViewToAdd, NewProcedure, NewProcedureView);
             }
 
 
  
STAppFrame.UpdateDisplay();
        }   
     
    }
    }
catch (Exception e)
        {
            System.out.println("Error loading procedure: " + e.toString());
          
        }
STAppFrame.addTargetBrowserItemListener( new ItemListener() {
    
        public void itemStateChanged (ItemEvent e )
        {
         if ((e.getStateChange() == ItemEvent.SELECTED)) {
            Object ActionType = e.getItem();
            String TargetBrowser = ActionType.toString();
           STAppFrame.TargetBrowser = TargetBrowser;
          STAppFrame.changes = true;
           if (STAppFrame.TargetBrowser=="Chrome" || "Firefox".equals(STAppFrame.TargetBrowser))
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
STAppFrame.addjButtonBrowseForFireFoxExeActionListener(
new ActionListener() {
    public void actionPerformed (ActionEvent evt)
    {
    FireFoxProperties FFProperties = new FireFoxProperties();
    FFProperties.BrowseforFFPath();
 
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
   STAppFrame.addjButtonFlattenFileActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
   
                   ThreadSaveFile(STAppFrame, true, true);
                    
 
  
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
         STAppFrame.addjButtonLoadEmailSettingsListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 try
 {
 STAppFrame.LoadGlobalEmailSettings();
 }
 catch (Exception ex)
 {
     System.out.println ("Exception loading global email settings: " + ex.toString());
 }
  
        }
      }
    );
            STAppFrame.addjButtonGutsViewActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
   STAppFrame.ShowGuts();

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
     STAppFrame.addjButtonNewDataLoopActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
//   File chosenCSVFile = BrowseForCSVFile();
//   if (chosenCSVFile!=null)
//   {
//   STAppFrame.AddNewDataLoop(chosenCSVFile);  
//   }
             File blankfile = null;
   STAppFrame.AddNewDataLoop(blankfile);  
  }
                                          
      }
    );
  
 
  
 return STAppFrame;

  }
   public void FlattenFile(SeleniumTestTool windowToFlatten)
 {
  //   FlattenLoop FlatREF = new FlattenLoop(DataLoop );
  //  FlatREF.execute();
    
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
   
     OpenFile(file_to_open, MDIClasses, false);
   
     }
  
    if (args[0].equals("run"))
    {
   File file_to_open = new File(args[1]);
    OpenFile(file_to_open, MDIClasses, true);
  
   
    }
       
  }
  
   public void DisplayWindow (int MDI_CLASS_INDEX)
   {
       if (MDI_CLASS_INDEX>=0)
       {
  MDIClasses.get(MDI_CLASS_INDEX).setTitle("Browsermator - " + MDIClasses.get(MDI_CLASS_INDEX).filename);
  MDIClasses.get(MDI_CLASS_INDEX).setVisible(true);
  MDIClasses.get(MDI_CLASS_INDEX).setSize(1400,900);
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
                              System.out.println("Error setting iframe: " + ec.toString());
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
     String userdir = System.getProperty("user.home");
     
try
{
     FileInputStream input = new FileInputStream(userdir + File.separator + "browsermator_config.properties");
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


 String userdir = System.getProperty("user.home");


    File configFile = new File(userdir + File.separator + "browsermator_config.properties");
     FileInputStream input = new FileInputStream(userdir + File.separator + "browsermator_config.properties");

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
  
  
  public void LoadFirefoxPath()
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
             System.out.println("Error config file: " + e);
           
             
         }
}
catch (Exception e) {
			System.out.println("Exception loading firefox path: " + e);
                        
		} 

   
 
 
   
        
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
      applicationProps.setProperty("firefox_exe", pathtofirefox);
           try {
       FileWriter writer = new FileWriter(userdir + File.separator + "browsermator_config.properties");
    applicationProps.store(writer, "browsermator_settings");
    writer.close();
         
  
   
} 

    catch (Exception e) {
			System.out.println("Exception writing firefox path: " + e);
		}      
  }    
public void setSaveMenuState(boolean enabled)
{
    saveMenuItem.setEnabled(enabled);
}
public int getJMenuViewItemCount()
{
    return jMenuView.getItemCount();
}
public String getJMenuViewItem(int index)
{
   return  jMenuView.getItem(index).getText();
}
public void addJMenuViewItem (JMenuItem item_to_add)
{
    jMenuView.add(item_to_add);
}
   public void LoadNameAndPassword()
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
             System.out.println("error name and pw config:" + e.toString());
           
             
         }
}
catch (Exception e) {
			System.out.println("Exception loading name and pw config: " + e);
                        
		} 

    this.loginName = applicationProps.getProperty("loginName");
    String temppassword = applicationProps.getProperty("loginPassword");
   if (temppassword==null || temppassword=="")
   {
    this.loginName = "";
    this.loginPassword = "";
   }
   else
       
   {
       try
    {
     this.loginPassword = Protector.decrypt(temppassword);
    }
    catch (Exception ex)
    {
      System.out.println("error decrypting login pw: " + ex.toString());
       
    }

   }
   
        
  }
   public void SaveNameAndPassword(String in_loginName, String in_Password)
  {
      String userdir = System.getProperty("user.home");
      Properties applicationProps = new Properties();
      this.loginName = in_loginName;
      this.loginPassword = in_Password;
      try
{
    Boolean file_exists = false;
    
    File f = new File(userdir + File.separator + "browsermator_config.properties");
if(f.exists() && !f.isDirectory()) { 
   file_exists = true;
}
if (file_exists == false)
{
    CreateConfigFile();
}
      FileInputStream input = new FileInputStream(userdir + File.separator + "browsermator_config.properties");
  
applicationProps.load(input);
input.close();
}
      catch (Exception ex)
      {
         System.out.println ("exception loading config: " + ex.toString()); 
      }
      applicationProps.setProperty("loginName", in_loginName);
      String encPassword = "";
      try
      {
       encPassword = Protector.encrypt(in_Password);
      }
      catch (Exception ex)
      {
          System.out.println("error encrypting login pw: " + ex.toString());
          
      }
      applicationProps.setProperty("loginPassword", encPassword);
      
           try {
       FileWriter writer = new FileWriter(userdir + File.separator + "browsermator_config.properties");
    applicationProps.store(writer, "browsermator_settings");
    writer.close();
         
  
   
} 

    catch (Exception e) {
			System.out.println("Exception writing login details: " + e);
		}      
  }  
   
    public void LookUpUser(String name, String password)
 {
   String outHTML = "";
     UserParamHash userData = new UserParamHash(name, "", password);
     SendReceiveData thisSession = new SendReceiveData(rootURL + "/get_user_id.php", userData);
  Boolean errorcheck = false;
          try
           {
      outHTML = thisSession.SendParams();           
           }
   catch (Exception ex)
   {
       errorcheck = true;
       System.out.println ("Exception getting user_id : " + ex.toString());
       
   }
   if (outHTML.isEmpty() || "failed".equals(outHTML))
   {
       if (errorcheck)
       {
           this.user_id = -1;
       }
       else
       {
        this.user_id = 0;
       }
       
 }
   else
   {
       try
       {
      this.user_id = Integer.parseInt(outHTML);
       }
       catch (Exception ex)
       {
           System.out.println("Exception parsing user_id int:" + ex.toString());
           this.user_id = -1;
       }
               
   }
 }
     public String RecoverPassword (String email)
 {
     
     UserParamHash userData = new UserParamHash("", email, "");
      SendReceiveData thisSession = new SendReceiveData(rootURL + "/recover.php", userData);
      try
     {
     String outHTML = thisSession.SendParams();
      if ("Success".equals(outHTML))
      {
        return "An email with your password has been sent.";
       
      }
      else
      {
          if (outHTML.contains("failed to send"))
          {
  
          return "Connection to mail server has failed.  Please try again later.";    
          }
          
          else
          {
        return "There is no account registered to " + email;
          }
      }
     
      
     }
     catch (Exception ex)
     {
         
         System.out.println("Exception recovering password: " + ex.toString());
         if (ex.toString().contains("Connection refused"))
         {
             return "Unable to connect to browsermator.com";
         }
         else
         {
         return "Unable to recover password.";
         }
     }
   
 }
 public String RegisterUser (Login_Register_Dialog loginDialog, String loginName, String Email, String Password)
 {
    
     
     UserParamHash userData = new UserParamHash(loginName, Email, Password);
     SendReceiveData thisSession = new SendReceiveData(rootURL + "/register.php", userData);
  try
  {
     String outHTML = thisSession.SendParams();
      if ("Success".equals(outHTML))
      {
        
       return "Success";
       
      }
      else
      {
        return "The username and/or email address you have chosen already exists.";
      }
  }
  catch (Exception ex)
  {
      System.out.println ("Exception sending params register: " + ex.toString());
  }
     
     
return "Unable to connect browsermator.com.";
  
 }
}
