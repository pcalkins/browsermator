package browsermator.com;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
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
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.SAVE_DIALOG;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;



public final class STAppController  {
 public final SiteTestView Navigator;
public JDesktopPane SeleniumToolDesktop;

    private int CurrentMDIWindowIndex;
   public final String ProgramVersion = "1.1.16b";
   public String loginName;
   public String loginPassword;

  Boolean SHOWGUI = true;
  public int user_id;
//  String rootURL = "http://localhost";
 String rootURL = "https://www.browsermator.com";
     ArrayList<SeleniumTestTool> MDIViewClasses = new ArrayList();
       ArrayList<SeleniumTestToolData> MDIDataClasses = new ArrayList();
 MainAppFrame mainAppFrame;

  public STAppController(String[] args) throws PropertyVetoException {
  

     mainAppFrame = new MainAppFrame(); 
    Navigator = new SiteTestView();
    if (args.length>0) { 
   
        CheckArgs(args);}

    if(SHOWGUI)
         {
       
       
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
  mainAppFrame.setWindowProps(input);
 
 
    input.close();

    
} 

    catch (Exception e) {
			System.out.println("Exception: " + e);
		} 
    
   // super.setExtendedState( super.getExtendedState()|JFrame.MAXIMIZED_BOTH );
   //  super.setVisible(true);
 
 
  //  String RecentFiles[] = null;
 //   RecentFiles = Navigator.getRecentFiles();
 //   MouseListener[] RecentFilesListeners = null;
   
  
 mainAppFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
   
     mainAppFrame.addWindowListener(new WindowAdapter() {
    @Override
    public void windowClosing(WindowEvent windowEvent) {
     int closure = 0;
     int number_of_windows_to_close = 0;
   
    
     int last_window_index = MDIViewClasses.size()-1;
     for (int x = last_window_index; x>-1; x--)
     {
        closure  =  CheckToSaveChanges(MDIViewClasses.get(x), MDIDataClasses.get(x), true);
           
      if (closure==1)
      {
      MDIViewClasses.get(x).setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
       MDIViewClasses.get(x).setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      number_of_windows_to_close++;
      }
    
     }
     if (number_of_windows_to_close==MDIViewClasses.size())
     {
     for (int x = 0; x<number_of_windows_to_close; x++)
      { 
       MDIViewClasses.remove(MDIViewClasses.size()-1);
        MDIDataClasses.remove(MDIDataClasses.size()-1);
    }
     }
  
      

        
    if (MDIViewClasses.size()==0)
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
    
     SeleniumToolDesktop.setDesktopManager(new browsermatorDesktopManager());
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
mainAppFrame.initComponents();

 

 mainAppFrame.add(SeleniumToolDesktop);

   SeleniumToolDesktop.add(Navigator);
 
   


  try
        {
       Navigator.setMaximum(true);
        }
        catch (PropertyVetoException e)
        {
            System.out.println("setting maximum error" + e);
        }
         }

 
         if (SHOWGUI)
    { 

       
      
  CurrentMDIWindowIndex = GetCurrentWindow();  
  if (CurrentMDIWindowIndex == -1)
  {
  mainAppFrame.setSaveMenuItemEnabled(false);
  }
  
  mainAppFrame.addFileMenuImportActionListener(
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
  
  mainAppFrame.addFileMenuSaveActionListener(
      new ActionListener() {
           public void actionPerformed (ActionEvent evt) {
                 
         
                 CurrentMDIWindowIndex = GetCurrentWindow();
                 if (CurrentMDIWindowIndex !=-1)
                 {
                     SeleniumTestToolData STAppData = MDIDataClasses.get(CurrentMDIWindowIndex);
                  SeleniumTestTool STAppFrame = MDIViewClasses.get(CurrentMDIWindowIndex);
                    ThreadSaveFile(mainAppFrame,STAppFrame, STAppData, false, false);
                    
                 }
                   else
  {
    JOptionPane.showMessageDialog (null, "No Active Window to save. Click to select a Window.", "No Selected Window", JOptionPane.INFORMATION_MESSAGE);   
  }
                 
            
           } 
       });
  
  mainAppFrame.addFileMenuCloseActionListener( new ActionListener() {
           public void actionPerformed (ActionEvent evt) {
                  
         
                 CurrentMDIWindowIndex = GetCurrentWindow();
                 if (CurrentMDIWindowIndex !=-1)
                 {
                     SeleniumTestTool STAppFrame = MDIViewClasses.get(CurrentMDIWindowIndex);
                 SeleniumTestToolData STAppData = MDIDataClasses.get(CurrentMDIWindowIndex);
                  int closed =  CheckToSaveChanges(STAppFrame, STAppData, false);
        if (closed==1)
      {
      STAppFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
      // RemoveWindow(MDIClasses.size()-1);
         int thisMDIIndex = GetCurrentWindow();
          
      RemoveWindow(thisMDIIndex);
       STAppFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
       STAppFrame.dispose();
      }    
    
 
      }
                    
              
                   else
  {
    JOptionPane.showMessageDialog (null, "No Active Window to close. Click to select a Window.", "No Selected Window", JOptionPane.INFORMATION_MESSAGE);   
  }
                 
              
            
           } 
       });
  mainAppFrame.addFileMenuBrowseCloudMenuItemActionListener(
 new ActionListener() {
           public void actionPerformed (ActionEvent evt) {
                  
         
               OpenBrowserMatorCloud();
                 
              
            
           } 
       });
  mainAppFrame.addFileMenuuploadFileToCloudMenuItemActionListener(
 new ActionListener() {
           public void actionPerformed (ActionEvent evt) {
                  
         
                 CurrentMDIWindowIndex = GetCurrentWindow();
                 if (CurrentMDIWindowIndex !=-1)
                 {
                     SeleniumTestTool STAppFrame = MDIViewClasses.get(CurrentMDIWindowIndex);
                  SeleniumTestToolData STAppData = MDIDataClasses.get(CurrentMDIWindowIndex);
                 
                   UploadFile(STAppFrame, STAppData, true, false);
                    
                 }
                   else
  {
    JOptionPane.showMessageDialog (null, "No Active Window to save. Click to select a Window.", "No Selected Window", JOptionPane.INFORMATION_MESSAGE);   
  }
                 
              
            
           } 
       });
  mainAppFrame.addFileMenuSaveAsActionListener(
      new ActionListener() {
           public void actionPerformed (ActionEvent evt) {
                  
         
                 CurrentMDIWindowIndex = GetCurrentWindow();
                 if (CurrentMDIWindowIndex !=-1)
                 {
                      SeleniumTestToolData STAppData = MDIDataClasses.get(CurrentMDIWindowIndex);
                  SeleniumTestTool STAppFrame = MDIViewClasses.get(CurrentMDIWindowIndex);
                    ThreadSaveFile(mainAppFrame, STAppFrame, STAppData, true, false);
                    
                 }
                   else
  {
    JOptionPane.showMessageDialog (null, "No Active Window to save. Click to select a Window.", "No Selected Window", JOptionPane.INFORMATION_MESSAGE);   
  }
                 
              
            
           } 
       });
  
 mainAppFrame.addFileMenuNewActionListener(
  
         new ActionListener()
  {
  public void actionPerformed (ActionEvent evt)
  {
      String filename = "";
     if (MDIViewClasses.size() == 0) 
           {
          filename = "untitled";
           }
           else
           {
          filename = "untitled" + MDIViewClasses.size();
           }
  
 
  ArrayList<ProcedureView> blankprocviews = new ArrayList<ProcedureView>();
  ArrayList<Procedure> blankprocs = new ArrayList<Procedure>();
 
  SeleniumTestToolData STAppData = new SeleniumTestToolData(blankprocs);
 STAppData.setFilenames(filename);
 
 
 STAppData.setTargetBrowser("Chrome");
  
    STAppData.setTargetBrowser("Chrome");

  STAppData.setOSType("Windows32");
  
 SeleniumTestTool STAppFrame = new SeleniumTestTool(STAppData);   
STAppFrame.setFilename(filename);
STAppFrame.ShowStoredVarControls(false);
  STAppFrame.setClosable(true);
 
  STAppFrame.setMaximizable(true);

  STAppFrame.setResizable(true);

  MDIViewClasses.add(STAppFrame);
  MDIDataClasses.add(STAppData);
    DisplayWindow (MDIViewClasses.size()-1);

  STAppFrame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
     @Override 
     public void internalFrameClosing(InternalFrameEvent e) {
                    int closed =  CheckToSaveChanges(STAppFrame, STAppData, false);
           
      if (closed==1)
      {
      STAppFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
            int thisMDIIndex = GetCurrentWindow();
          
      RemoveWindow(thisMDIIndex);
     //  RemoveWindow(MDIClasses.size()-1); 
       STAppFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      }
        
     
      }
    });
 
 
    STAppFrame.addjButtonDoStuffActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 

 RunActions(STAppFrame, STAppData); 
 
  
        }
      }
    );
      STAppFrame.addjButtonClearEmailSettingsListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
 STAppFrame.ClearEmailSettings(); 
  STAppData.ClearEmailSettings();
  
        }
      }
    );
          STAppFrame.addjButtonLoadEmailSettingsListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 try
 {
  STAppData.loadGlobalEmailSettings();
  STAppFrame.setEmailSettings(STAppData);
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
           STAppFrame.setTargetBrowserView(TargetBrowser);
           STAppData.changes = true;
          
         }
        }
        
        }); 
    STAppFrame.addjButtonGutsViewActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    
        showGuts(STAppFrame, STAppData);
        }
                                          
      }
    );
     STAppFrame.addjButtonNewBugActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    STAppData.AddNewBug();
   STAppFrame.AddNewBugView();  
   int last_added_bug_index = STAppFrame.BugViewArray.size()-1;
   ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
   Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
      AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
  STAppFrame.UpdateDisplay();
        JScrollBar vertical = STAppFrame.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
  }
                                          
      }
    );
      STAppFrame.addjButtonNewDataLoopActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 

     
   STAppFrame.AddNewDataLoopView();
   STAppData.AddNewDataLoop();
    int last_added_bug_index = STAppFrame.BugViewArray.size()-1;
   ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
   Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
      AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
      AddLoopHandlers(STAppFrame, STAppData, newbugview, newbug);
  STAppFrame.UpdateDisplay();
        JScrollBar vertical = STAppFrame.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
  }
                                          
      }
    );
  

  }
  }
    ); 
   
  mainAppFrame.addFileMenuOpenActionListener(
  new ActionListener()
          {
          public void actionPerformed (ActionEvent evt)
          {
          
 
   File[] newfiles = BrowseForFile();
 if (newfiles !=null)
 {
     for (int fileindex = 0; fileindex<newfiles.length; fileindex++)
     {
    OpenFile(newfiles[fileindex], false);
     
    
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
     
     

      OpenFile(RecentFile, false);
       
          
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
     
     

       OpenFile(RecentFile, false);
 
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
     
     

    OpenFile(RecentFile, false);
     
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
     
     

     OpenFile(RecentFile, false);
  
       
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
     
     

     OpenFile(RecentFile, false);
    
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
  OpenFile(newfiles[fileindex], false);
    
    
   }    
 }     

          
          }  });
        
      

     

  Navigator.addjButtonNewWebsiteTestActionListener(
  new ActionListener()
  {
  public void actionPerformed (ActionEvent evt)
  {
      String filename = "";
      if (MDIViewClasses.size() == 0) 
           {
          filename = "untitled";
           }
           else
           {
          filename = "untitled" + MDIViewClasses.size();
           }
  

  ArrayList<Procedure> BugArray = new ArrayList<Procedure>();
  
  
  SeleniumTestToolData STAppData = new SeleniumTestToolData(BugArray);
 STAppData.setFilenames(filename);
 STAppData.setTargetBrowser("Chrome");
  STAppData.setOSType("Windows32");
  
  SeleniumTestTool STAppFrame = new SeleniumTestTool(STAppData);
     STAppFrame.setClosable(true);
 
  STAppFrame.setMaximizable(true);

  STAppFrame.setResizable(true);

  



// SeleniumToolDesktop.repaint();
  MDIViewClasses.add(STAppFrame);
  MDIDataClasses.add(STAppData);
    DisplayWindow (MDIViewClasses.size()-1);

  STAppFrame.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
     @Override 
     public void internalFrameClosing(InternalFrameEvent e) {
                    int closed =  CheckToSaveChanges(STAppFrame, STAppData, false);
           
      if (closed==1)
      {
      STAppFrame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
            int thisMDIIndex = GetCurrentWindow();
          
      RemoveWindow(thisMDIIndex);
     //  RemoveWindow(MDIClasses.size()-1); 
       STAppFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      }
        
     
      }
    });
 
 
    STAppFrame.addjButtonDoStuffActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
 RunActions(STAppFrame, STAppData); 
 
  
        }
      }
    );
      STAppFrame.addjButtonClearEmailSettingsListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 
 STAppFrame.clearEmailSettings();
 STAppData.clearEmailSettings();
  
        }
      }
    );
          STAppFrame.addjButtonLoadEmailSettingsListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
 try
 {

 STAppData.loadGlobalEmailSettings();
  STAppFrame.setEmailSettings(STAppData);
  
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
           STAppFrame.setTargetBrowserView(TargetBrowser);
           STAppData.setTargetBrowser(TargetBrowser);
           STAppData.changes = true;
          
         }
        }
        
        }); 
    STAppFrame.addjButtonGutsViewActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
            
    showGuts(STAppFrame, STAppData);

        }
                                          
      }
    );
    STAppFrame.addjButtonNewBugActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 
    STAppData.AddNewBug();
   STAppFrame.AddNewBugView();  
   int last_added_bug_index = STAppFrame.BugViewArray.size()-1;
   ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
   Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
      AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
  STAppFrame.UpdateDisplay();
        JScrollBar vertical = STAppFrame.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
  }
                                          
      }
    );
      STAppFrame.addjButtonNewDataLoopActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent evt)
        { 

     
   STAppFrame.AddNewDataLoopView();
   STAppData.AddNewDataLoop();
    int last_added_bug_index = STAppFrame.BugViewArray.size()-1;
   ProcedureView newbugview = STAppFrame.BugViewArray.get(last_added_bug_index);
   Procedure newbug = STAppData.BugArray.get(last_added_bug_index);
      AddNewHandlers(STAppFrame, STAppData, newbugview, newbug);
  STAppFrame.UpdateDisplay();
        JScrollBar vertical = STAppFrame.MainScrollPane.getVerticalScrollBar();
 vertical.setValue( vertical.getMaximum() );
  }
                                          
      }
    );
  
  
  

  }
  }
    ); 
         }
  
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
  public int CheckToSaveChanges(SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, Boolean savenow) 
  {
  if (SHOWGUI)
  {
if (STAppData.testRunning)
{
    Prompter cantClose = new Prompter("Procedures Running!" , "You can't close the window while procedures are running.", false, 0,0);
   
    return 1;
   
}
ArrayList<String> AllFieldValuesCheck = new ArrayList<>();
AllFieldValuesCheck.add(STAppData.OSType);
AllFieldValuesCheck.add(STAppData.TargetBrowser);
String stringWaitTime = String.valueOf(STAppData.WaitTime);
AllFieldValuesCheck.add(stringWaitTime);
String stringTimeout = String.valueOf(STAppData.Timeout);
AllFieldValuesCheck.add(stringTimeout);
String stringSessions = String.valueOf(STAppData.Sessions);
AllFieldValuesCheck.add(stringSessions);
AllFieldValuesCheck.add(STAppData.SMTPHostName);
AllFieldValuesCheck.add(STAppData.EmailFrom);
AllFieldValuesCheck.add(STAppData.EmailLoginName);
AllFieldValuesCheck.add(STAppData.EmailPassword);
AllFieldValuesCheck.add(STAppData.EmailTo);
AllFieldValuesCheck.add(STAppData.EmailSubject);
String sthisbool = "false";
if (STAppData.EmailReport)
{
    sthisbool = "true";
}
AllFieldValuesCheck.add(sthisbool);
sthisbool = "false";
if (STAppData.EmailReportFail)
{
    sthisbool = "true";
}

AllFieldValuesCheck.add(sthisbool);


sthisbool = "false";
if (STAppData.ExitAfter)
{
    sthisbool = "true";
}
AllFieldValuesCheck.add(sthisbool);
sthisbool = "false";
if (STAppData.PromptToClose)
{
    sthisbool = "true";
}
AllFieldValuesCheck.add(sthisbool);
sthisbool = "false";
if (STAppData.ShowReport)
{
    sthisbool = "true";
}
AllFieldValuesCheck.add(sthisbool);
sthisbool = "false";
if (STAppData.IncludeScreenshots)
{
    sthisbool = "true";
}
AllFieldValuesCheck.add(sthisbool);
sthisbool = "false";
if (STAppData.UniqueList)
{
    sthisbool = "true";
}

AllFieldValuesCheck.add(sthisbool);

AllFieldValuesCheck.add(STAppData.getUniqueFileOption());
for (Procedure thisproc: STAppData.BugArray)
{
    AllFieldValuesCheck.add(thisproc.BugTitle);
    AllFieldValuesCheck.add(thisproc.DataFile);
     String randboolval = "false";
    if (thisproc.random)
    {
        randboolval = "true";
    }
    
    AllFieldValuesCheck.add(randboolval);
 
    String limitstring = Integer.toString(thisproc.limit);
    AllFieldValuesCheck.add(limitstring);
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
        String checkingboolval2 = "false";
           if (thisact.BoolVal2)
        {
            checkingboolval2 = "true";
        }
        AllFieldValuesCheck.add(checkingboolval2);
          String checkingboolval3 = "false";
                if (thisact.Locked)
        {
            checkingboolval3 = "true";
        }
        AllFieldValuesCheck.add(checkingboolval3);
    }
}
if (!STAppData.AllFieldValues.equals(AllFieldValuesCheck))
{
   STAppData.changes = true;
   
}

if (STAppData.changes==false)
{
  return 0;
}
else
{
  int result = JOptionPane.showConfirmDialog(STAppFrame,"Do you wish to save changes to " + STAppData.filename + "?","Browsermator",JOptionPane.YES_NO_CANCEL_OPTION);
            switch(result){
                  case JOptionPane.YES_OPTION:
                 //   SaveFile
                      //checking "untitled" is kluge, fix this later
                     if (STAppData.filename.contains("untitled"))
                     {
                    
                        try
                          {
                        SaveFileNow(STAppFrame, STAppData, true, false);
                          }
                          catch (Exception ex)
                          {
                              
                          }
                      
                    
                      return 0;
                     }
                     else
                     {
                          if (savenow)
                      {
                          try
                          {
                        SaveFileNow(STAppFrame, STAppData, false, false);
                          }
                          catch (Exception ex)
                          {
                              
                          }
                                  
                          }  
                      else
                      {
                          ThreadSaveFile(mainAppFrame, STAppFrame, STAppData, false, false);  
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
  else
  {
      return 1;
  }
  }
      private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                             
     int closure = 0;
     int number_of_windows_to_close = 0;
   
    
     int last_window_index = MDIViewClasses.size()-1;
     for (int x = last_window_index; x>-1; x--)
     {
        closure  =  CheckToSaveChanges(MDIViewClasses.get(x), MDIDataClasses.get(x),  true);
      
      if (closure==1)
      {
      MDIViewClasses.get(x).setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
      }
      else
      {
       MDIViewClasses.get(x).setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      number_of_windows_to_close++;
      }
    
     }
     if (number_of_windows_to_close==MDIViewClasses.size())
     {
     for (int x = 0; x<number_of_windows_to_close; x++)
      { 
       MDIViewClasses.remove(MDIViewClasses.size()-1);
       MDIDataClasses.remove(MDIDataClasses.size()-1);
       
    }
     }
  
      

        
    if (MDIViewClasses.size()==0)
    {
            Properties newProps = new Properties();
         Boolean file_exists = false;
              String userdir = System.getProperty("user.home");
    try
{
    File f = new File(userdir + File.separator + "browsermator_config.properties");

     FileInputStream input = new FileInputStream(userdir + File.separator + "browsermator_config.properties");
   newProps.load(input);
 
   
      
      newProps.setProperty("main_window_locationY", Integer.toString(mainAppFrame.getY()));
      newProps.setProperty("main_window_locationX", Integer.toString(mainAppFrame.getX()));
      newProps.setProperty("main_window_sizeWidth", Integer.toString(mainAppFrame.getWidth()));
      newProps.setProperty("main_window_sizeHeight", Integer.toString(mainAppFrame.getHeight()));
    
    
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



      public void AddMainAppListeners()
      {

        mainAppFrame.addExitMenuActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
           exitMenuItemActionPerformed(evt);
            }
        });

        
    }  
         public void showGuts(SeleniumTestTool in_sitetestview, SeleniumTestToolData in_sitetestdata)
   {
     
        ViewGuts GUTSREF = new ViewGuts(in_sitetestview, in_sitetestdata);
  GUTSREF.execute();
     
       
       
   } 
  
   

 
  public File[] BrowseForFile()
  {
       

        final JFileChooser fc = new JFileChooser();
      
fc.setMultiSelectionEnabled(true);

 FileNameExtensionFilter filefilter = new FileNameExtensionFilter("Browsermator file (*.browsermation)","browsermation");
 BrowserMatorConfig theseProps = new BrowserMatorConfig();

      String lastused_open_dir = theseProps.getKeyValue("lastused_open_dir");
      if (lastused_open_dir!=null)
      {
      fc.setCurrentDirectory(new File(lastused_open_dir));
      }
    fc.setFileFilter(filefilter);
fc.setPreferredSize(new Dimension(800,600));
int returnVal = fc.showOpenDialog(mainAppFrame);
       File chosenDir = fc.getCurrentDirectory();
 theseProps.setKeyValue ("lastused_open_dir", chosenDir.getAbsolutePath());
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
    BrowserMatorFileCloud thisCloud = new BrowserMatorFileCloud(mainAppFrame, this);
    thisCloud.ShowFileCloudWindow();
}
 public void UploadFile(SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, boolean isSaveAs, boolean isFlatten)
  {
      int current_MDI_Index = GetCurrentWindow();
      
     UploadFileThread UPLOADREF = new UploadFileThread(this, mainAppFrame, STAppFrame, STAppData, current_MDI_Index);
  UPLOADREF.execute();  
   
  }
   public void SaveFile(SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, boolean isSaveAs, boolean isFlatten, int calling_MDI_Index) throws IOException, XMLStreamException
    {
    String old_filename = STAppData.filename;
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
        // do I need to refresh clean state here?
    STAppData.changes = false;
    }
};
File file=null;
  BrowserMatorConfig theseProps = new BrowserMatorConfig();

      String lastused_save_dir = theseProps.getKeyValue("lastused_save_dir");
      if (lastused_save_dir!=null)
      {
      fc.setCurrentDirectory(new File(lastused_save_dir));
      }  
      //"untitled" kluge again... fix this later
    if (isSaveAs==true || STAppData.filename.contains("untitled") == true)
    {
     FileNameExtensionFilter filefilter = new FileNameExtensionFilter("Browsermator file (*.browsermation)","browsermation");

    fc.setFileFilter(filefilter);
    fc.setPreferredSize(new Dimension(800,600));
    // and again "untitled" kluge
    if (STAppData.filename.contains("untitled") == false  && isSaveAs==true)
    {
          String[] left_side_of_dot = STAppData.filename.split("\\.");
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
      File chosenDir = fc.getCurrentDirectory();
 theseProps.setKeyValue ("lastused_save_dir", chosenDir.getAbsolutePath());
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
    
         String filestring = STAppData.filename;
                
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

    String IncludeScreenshots = Boolean.toString(STAppFrame.getIncludeScreenshots());
xmlfile.writeStartElement("IncludeScreenshots");
    xmlfile.writeCharacters(IncludeScreenshots);
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
    String UniqueList = Boolean.toString(STAppFrame.getUniqueList());
xmlfile.writeStartElement("UniqueList");
    xmlfile.writeCharacters(UniqueList);
    xmlfile.writeEndElement();     

     String UniqueFileOption = STAppData.getUniqueFileOption();
    xmlfile.writeStartElement("UniqueFileOption");
    xmlfile.writeCharacters(UniqueFileOption);
    xmlfile.writeEndElement();   
    
String TargetBrowser = STAppData.TargetBrowser;
xmlfile.writeStartElement("TargetBrowser");
    xmlfile.writeCharacters(TargetBrowser);
    xmlfile.writeEndElement();   
// xmlfile.writeAttribute("TargetBrowser", TargetBrowser);
    
Integer WaitTime = STAppFrame.GetWaitTime();
String WaitTimeString = WaitTime.toString();
xmlfile.writeStartElement("WaitTime");
    xmlfile.writeCharacters(WaitTimeString);
    xmlfile.writeEndElement();  
    
 Integer Timeout = STAppData.Timeout;
String TimeoutString = Timeout.toString();
xmlfile.writeStartElement("Timeout");
    xmlfile.writeCharacters(TimeoutString);
    xmlfile.writeEndElement();  
    
Integer NumberOfSessions = STAppFrame.getSessions();
String NumberOfSessionsString = NumberOfSessions.toString();
xmlfile.writeStartElement("Sessions");
    xmlfile.writeCharacters(NumberOfSessionsString);
    xmlfile.writeEndElement();   
// xmlfile.writeAttribute("WaitTime", WaitTimeString);

String OSType = STAppFrame.getOSType();
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

for (Procedure thisbug: STAppData.BugArray)
{

xmlfile.writeStartElement("Procedure");
xmlfile.writeAttribute("Title", thisbug.BugTitle);
xmlfile.writeAttribute("URL", thisbug.BugURL);
xmlfile.writeAttribute("Pass", Boolean.toString(thisbug.Pass));
String index = String.valueOf(thisbug.index);
xmlfile.writeAttribute("index", index);
if (isFlatten==true)
{
 xmlfile.writeAttribute("Type", "Procedure");    
}
else
{
xmlfile.writeAttribute("Type", thisbug.Type);   
}
if ("Dataloop".equals(thisbug.Type))
{
if ("file".equals(thisbug.DataLoopSource))
{
xmlfile.writeAttribute("DataLoopFile", thisbug.DataFile);
}
else
{
    xmlfile.writeAttribute("DataLoopFile", thisbug.URLListName);
}
xmlfile.writeAttribute("DataLoopSource", thisbug.DataLoopSource);
String limit = String.valueOf(thisbug.limit);
xmlfile.writeAttribute("Limit", limit);
Boolean randbool = thisbug.random;
String randstring = "false";
if (randbool)
{
randstring = "true";    
}

xmlfile.writeAttribute("Random", randstring);

}
  if (isFlatten==true && "Dataloop".equals(thisbug.Type))
  {
    
        int indexer = thisbug.index;
           ProcedureView thisbugview = STAppFrame.BugViewArray.get(indexer-1);
       int number_of_rows = 0;
         if ("urllist".equals(thisbug.DataLoopSource))
    {
       //cannot flatten urllist (this would require a run) 
   //   if (thisbugview.getLimit()>0 || thisbugview.getRandom())
   //   {
   //   STAppFrame.RandomizeAndLimitURLList(thisbug.URLListName,thisbugview.getLimit(), thisbugview.getRandom());
   //   }
   //   thisbug.setURLListData(STAppFrame.VarLists.get(thisbug.URLListName), thisbug.URLListName);
   //   thisbugview.setJTableSourceToURLList(thisbug.URLListData, thisbug.URLListName);
   //   number_of_rows = STAppFrame.VarLists.get(thisbug.URLListName).length;
    }
    else
    {
   if ("file".equals(thisbug.DataLoopSource))
    {
       if (thisbugview.getLimit()>0 || thisbugview.getRandom())
       {
         thisbug.setRunTimeFileSet(STAppData.RandomizeAndLimitFileList(thisbug.DataSet, thisbugview.getLimit(), thisbugview.getRandom())); 
        }
         number_of_rows = thisbug.RunTimeFileSet.size();
    }  
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
    
     xmlfile.writeStartElement("BoolVal2");
    String tempstringbool2 = "false";
    if (thisaction.BoolVal2)
    {
        tempstringbool2 = "true";
    }
    
    xmlfile.writeCharacters(tempstringbool2);
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
    
    xmlfile.writeStartElement("BoolVal2");
    String tempstringbool2 = "false";
    if (thisaction.BoolVal2)
    {
        tempstringbool2 = "true";
    }
    xmlfile.writeCharacters(tempstringbool2);
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
    
     xmlfile.writeStartElement("BoolVal2");
    String tempstringbool2 = "false";
    if (thisaction.BoolVal2)
    {
        tempstringbool2 = "true";
    }
    xmlfile.writeCharacters(tempstringbool2);
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

  
 
        } catch (Exception e) {
           System.out.println("Write error:" + e.toString());
 
        } finally {
            xmlfile.flush();
            xmlfile.close();
            if (isFlatten)
            {

     OpenFileThread OPENREF = new OpenFileThread(this, mainAppFrame, file, MDIViewClasses, MDIDataClasses, calling_MDI_Index, true, false);
  OPENREF.execute();
  
            }

        }
if (isFlatten==false)
{
 
STAppData.setFilenames(file.getAbsolutePath());
STAppFrame.setFilenames();
if (isSaveAs)
{
if (STAppFrame.filename.equals(old_filename))
{
 Navigator.addRecentFile(file.getAbsolutePath());
}
else
{
Navigator.addRecentFile(file.getAbsolutePath());
this.UpdateWindowName(calling_MDI_Index, old_filename);

}
}
else
{
Navigator.addRecentFile(STAppData.filename);
}
}

     
        }
 public void RefreshCleanState(SeleniumTestToolData STAppData)
 {
       
 
   
            STAppData.AllFieldValues.clear();
       
STAppData.AllFieldValues.add(STAppData.OSType);
STAppData.AllFieldValues.add(STAppData.TargetBrowser);
String stringWaitTime = String.valueOf(STAppData.getWaitTime());
STAppData.AllFieldValues.add(stringWaitTime);
String stringTimeout = String.valueOf(STAppData.getTimeout());
STAppData.AllFieldValues.add(stringTimeout);
String stringSessions = String.valueOf(STAppData.getSessions());
STAppData.AllFieldValues.add(stringSessions);
STAppData.AllFieldValues.add(STAppData.getSMTPHostname());
STAppData.AllFieldValues.add(STAppData.getEmailFrom());
STAppData.AllFieldValues.add(STAppData.getEmailLoginName());
STAppData.AllFieldValues.add(STAppData.getEmailPassword());
STAppData.AllFieldValues.add(STAppData.getEmailTo());
STAppData.AllFieldValues.add(STAppData.getEmailSubject());

String thisbool = "false";
if (STAppData.getEmailReport())
{
    thisbool = "true";
}
STAppData.AllFieldValues.add(thisbool);

thisbool = "false";
if (STAppData.getEmailReportFail())
{
    thisbool = "true";
}
STAppData.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppData.getExitAfter())
{
    thisbool = "true";
}
STAppData.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppData.getPromptToClose())
{
    thisbool = "true";
}
STAppData.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppData.getShowReport())
{
    thisbool = "true";
}
STAppData.AllFieldValues.add(thisbool);
thisbool = "false";
if (STAppData.getIncludeScreenshots())
{
    thisbool = "true";
}
STAppData.AllFieldValues.add(thisbool);

thisbool = "false";
if (STAppData.getUniqueList())
{
    thisbool = "true";
}

STAppData.AllFieldValues.add(thisbool);

STAppData.AllFieldValues.add(STAppData.getUniqueFileOption());

for (Procedure thisproc: STAppData.BugArray)
{
    
   STAppData.AllFieldValues.add(thisproc.BugTitle);
   STAppData.AllFieldValues.add(thisproc.DataFile);
    String randboolval = "false";
    if (thisproc.random)
    {
        randboolval = "true";
    }
    
    STAppData.AllFieldValues.add(randboolval);
 
    String limitstring = Integer.toString(thisproc.limit);
    STAppData.AllFieldValues.add(limitstring);
    
    for (Action thisact: thisproc.ActionsList)
    {
        String checkingboolval1 = "false";
        String checkingboolval2 = "false";
        String checkingboolval3 = "false";
        
       STAppData.AllFieldValues.add(thisact.Variable1);
       
        STAppData.AllFieldValues.add(thisact.Variable2);
      
        if (thisact.BoolVal1)
        {
            checkingboolval1 = "true";
        }
        STAppData.AllFieldValues.add(checkingboolval1);
          if (thisact.BoolVal2)
        {
            checkingboolval2 = "true";
        }
        STAppData.AllFieldValues.add(checkingboolval2);
            if (thisact.Locked)
        {
            checkingboolval3 = "true";
        }
       STAppData.AllFieldValues.add(checkingboolval3);
    }
}
STAppData.changes = false;
            
 }
  public void SaveFileNow (SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, boolean isSaveAs, boolean isFlatten) throws IOException, XMLStreamException
  {
       int current_MDI_Index = GetCurrentWindow();
  SaveFile (STAppFrame, STAppData, isSaveAs, isFlatten, current_MDI_Index);
  
  }
  public void ThreadSaveFile(MainAppFrame mainAppFrame, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, boolean isSaveAs, boolean isFlatten)
  {
      int current_MDI_Index = GetCurrentWindow();
      
     SaveFileThread SAVEREF = new SaveFileThread(this, mainAppFrame, STAppFrame, STAppData, isSaveAs, isFlatten, current_MDI_Index);
  SAVEREF.execute();  
  }
  
     public void OpenFile (File file, boolean RunIt) 
    {
        
   if (!SHOWGUI)
   {
      OpenFileThread OPENREF = new OpenFileThread(this, file, MDIDataClasses);
  OPENREF.execute();       
   }
   else
   {
    if (RunIt)
    {
         int current_MDI_Index = GetCurrentWindow();
      OpenFileThread OPENREF = new OpenFileThread(this, mainAppFrame, file, MDIViewClasses, MDIDataClasses, current_MDI_Index, false, RunIt);
  OPENREF.execute();    
    }
    else
    {
          int current_MDI_Index = GetCurrentWindow();
  OpenFileThread OPENREF = new OpenFileThread(this, mainAppFrame, file, MDIViewClasses, MDIDataClasses, current_MDI_Index, false, RunIt);
  OPENREF.execute();
    }
 
   }
    }
      public void OpenFile (File file, boolean RunIt, boolean fromCloud) 
    {
        
    int current_MDI_Index = GetCurrentWindow();
  OpenFileThread OPENREF = new OpenFileThread(this, mainAppFrame, file, MDIViewClasses, MDIDataClasses, current_MDI_Index, false, RunIt, fromCloud);
  OPENREF.execute();
 

    }
     
     public void ImportFileFunct (File[] files, int CurrentMDIWindowIndex)
     {
            ImportFileThread IMPORTREF = new ImportFileThread(mainAppFrame, this, files, CurrentMDIWindowIndex);
   IMPORTREF.execute();
   
     }
  
 
  public int GetCurrentWindow()
  {
     
     int frameindex = -1;
     int allframeindex = 0;
    for (SeleniumTestTool thisFrame : MDIViewClasses) {
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
   
     OpenFile(file_to_open, false);
   
     }
  
    if (args[0].equals("runsilent"))
    {
       SHOWGUI=false;
 
   File file_to_open = new File(args[1]);
   
    OpenFile(file_to_open, true);
  
   
    }
      if (args[0].equals("run"))
    {
      
   File file_to_open = new File(args[1]);
    OpenFile(file_to_open, true);
  
   
    }
       
  }
   public void UpdateWindowName (int MDI_CLASS_INDEX, String old_name)
   {
      String update_name = MDIDataClasses.get(MDI_CLASS_INDEX).filename;
   
        for (int jm = 0; jm<mainAppFrame.getjMenuViewItemCount(); jm++)
       {
        String thisFileItemString = mainAppFrame.getjMenuViewItem(jm);  
        
                       if (thisFileItemString.equals(old_name))
                       {
                       mainAppFrame.setjMenuViewItemText(jm, update_name);
                       }
                   
                }  
   }
   public void RemoveWindow (int MDI_CLASS_INDEX)
   {
       if (MDI_CLASS_INDEX>=0)
       {
       String removedWindow = MDIDataClasses.get(MDI_CLASS_INDEX).filename;
   
        for (int jm = 0; jm<mainAppFrame.getjMenuViewItemCount(); jm++)
       {
        String thisFileItemString = mainAppFrame.getjMenuViewItem(jm);
        
                       if (thisFileItemString.equals(removedWindow))
                       {
                       mainAppFrame.removejMenuViewItem(jm);
                       }
                   
                }
            MDIViewClasses.remove(MDI_CLASS_INDEX);
            MDIDataClasses.remove(MDI_CLASS_INDEX);
       }
    }
   
   public void DisplayWindow (int MDI_CLASS_INDEX)
   {
       if (MDI_CLASS_INDEX>=0)
       {
  
  MDIDataClasses.get(MDI_CLASS_INDEX).setFilenames(MDIViewClasses.get(MDI_CLASS_INDEX).filename);
  MDIViewClasses.get(MDI_CLASS_INDEX).setFilenames();
// MDIClasses.get(MDI_CLASS_INDEX).setVisible(true);
 // MDIClasses.get(MDI_CLASS_INDEX).setVisible(true);
//  MDIClasses.get(MDI_CLASS_INDEX).setSize(1400,900);
  mainAppFrame.setSaveMenuItemEnabled(true);
  SeleniumToolDesktop.add(MDIViewClasses.get(MDI_CLASS_INDEX));

  MDIViewClasses.get(MDI_CLASS_INDEX).moveToFront();
        try
  {
  MDIViewClasses.get(MDI_CLASS_INDEX).setMaximum(true);
  }
  catch(Exception ex)
  {
      System.out.println("Veto maximum: " + ex.toString());
       MDIViewClasses.get(MDI_CLASS_INDEX).setSize(880, 600);
  } 
        MDIViewClasses.get(MDI_CLASS_INDEX).setVisible(true); 
       try
       {
        MDIViewClasses.get(MDI_CLASS_INDEX).setSelected(true);
       }
       catch (PropertyVetoException e)
       {
           System.out.println("Veto: " + e.toString());
       }
       String thisopenfile_raw =  MDIDataClasses.get(MDI_CLASS_INDEX).filename;
       
         String thisopenfile="";
                     String twoslashes = "\\" + "\\";
                     thisopenfile = thisopenfile_raw.replace(twoslashes, "\\");
       JMenuItem newfileitem = new JMenuItem(thisopenfile);
      Boolean hasitem = false;
       for (int jm = 0; jm<mainAppFrame.getjMenuViewItemCount(); jm++)
       {
       if (mainAppFrame.getjMenuViewItem(jm).equals(thisopenfile))
       {
           hasitem = true;
       }
       }
       if (!hasitem)
       {       
       mainAppFrame.addjMenuViewItem(newfileitem);
       newfileitem.addActionListener(new ActionListener() {
        
       @Override
        public void actionPerformed (ActionEvent e )
        {
            int current_index = 0;
        for (SeleniumTestTool iframe : MDIViewClasses)
        {
                       String thisFrameName = iframe.getFilename();
         
        
        
                  String thisFileItem = newfileitem.getText();
       
             
                       if (thisFileItem.equals(thisFrameName))
                       {
                            iframe.toFront();
                                  if (iframe.isIcon())
                                  {
                                      try
                                      {
                                      iframe.setIcon(false);
                                      iframe.setMaximum(true);
                                      }
                                      catch (Exception ex)
                                      {
                                          System.out.println ("Exception when maximizing window: " + ex.toString());
                                      }
                                  }
                         
                          
                          
                          try
                          {
                              iframe.setSelected(true);
     
        MDIViewClasses.get(current_index).setSelected(true);
      
                          }
                          catch (PropertyVetoException ec)
                          {
                              System.out.println("Error setting iframe: " + ec.toString());
                          }
                       }    
                       current_index++;
        }
        
             
         
        }
    });
       }
       
 
       
       }
   }

  public int getThisFrameIndex(String frameName)
  {
         int mdi_index = 0;
         int loopcount = 0;
                     for (SeleniumTestTool thisMDIClass : MDIViewClasses)
                {
                    if (thisMDIClass.filename.equals(frameName))
                    {
                      mdi_index = loopcount;
                    }
                    loopcount++;
                }  
                     return mdi_index;
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
   Navigator.setVersion("Version: " + this.ProgramVersion);
  
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
 
app.mainAppFrame.setVisible(true); 
if (args.length>0)
{
    if (args[0].equals("runsilent"))
    {
    app.mainAppFrame.setVisible(false); 
    }
}
   
  
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
    mainAppFrame.setSaveMenuItemEnabled(enabled);
}
public int getJMenuViewItemCount()
{
    return mainAppFrame.getjMenuViewItemCount();
}
public String getJMenuViewItem(int index)
{
   return  mainAppFrame.getjMenuViewItem(index);
}
public void addJMenuViewItem (JMenuItem item_to_add)
{
    mainAppFrame.addjMenuViewItem(item_to_add);
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
 
 // to update below::::
 
   public File BrowseForJSFileAction ()
   {
           final JFileChooser CSVFileChooser = new JFileChooser();
   BrowserMatorConfig theseProps = new BrowserMatorConfig();

      String lastJSOpenDir = theseProps.getKeyValue("lastused_js_open_dir");
       if (lastJSOpenDir!=null)
        {
        CSVFileChooser.setCurrentDirectory(new File(lastJSOpenDir));
        } 
 FileNameExtensionFilter filefilter = new FileNameExtensionFilter("Javascript file (*.js)","js");

    CSVFileChooser.setFileFilter(filefilter);
CSVFileChooser.setPreferredSize(new Dimension(800,600));
int returnVal = CSVFileChooser.showOpenDialog(mainAppFrame);
        File chosenDir = CSVFileChooser.getCurrentDirectory();
 theseProps.setKeyValue ("lastused_js_open_dir", chosenDir.getAbsolutePath());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = CSVFileChooser.getSelectedFile();   

    if (file.getAbsoluteFile().getName().contains(".js"))
{
 
}
else
{
   String path = file.getAbsolutePath();
    
File newfile = new File(path + ".js");
 file = newfile;
 
}  
    return file;
            }
            else
            {
            return null;
            }   
   }
   public void MoveProcedure (SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, int toMoveIndex, int Direction)
   {
     int SwapIndex = toMoveIndex + Direction;
    if (Direction == 1)
       {
      if (SwapIndex<STAppData.BugArray.size())
      {
    Collections.swap(STAppData.BugArray, toMoveIndex, SwapIndex);
  Collections.swap(STAppFrame.BugViewArray, toMoveIndex, SwapIndex);
  STAppData.BugArray.get(toMoveIndex).index = toMoveIndex;
  STAppFrame.BugViewArray.get(SwapIndex).index = SwapIndex;
    
      }
       }  
    if (Direction == -1)
    {
        if (SwapIndex >= 0)
        {
    Collections.swap(STAppData.BugArray, toMoveIndex, SwapIndex);
  Collections.swap(STAppFrame.BugViewArray, toMoveIndex, SwapIndex);
  STAppData.BugArray.get(toMoveIndex).index = toMoveIndex;
  STAppFrame.BugViewArray.get(SwapIndex).index = SwapIndex;
 
        }
    }
      if ("urllist".equals(STAppFrame.BugViewArray.get(toMoveIndex).DataLoopSource))
      {
         STAppData.updateStoredURLListIndexes(STAppFrame, STAppFrame.BugViewArray.get(toMoveIndex)); 
      }
     if ("urllist".equals(STAppFrame.BugViewArray.get(SwapIndex).DataLoopSource))
     {
      STAppData.updateStoredURLListIndexes(STAppFrame, STAppFrame.BugViewArray.get(SwapIndex));
     }
      STAppFrame.UpdateDisplay();
         JComponent component = (JComponent) STAppFrame.MainScrollPane.getViewport().getView();

        Rectangle bounds =  STAppFrame.BugViewArray.get(toMoveIndex).JPanelBug.getBounds();
     bounds.height = 50;
      component.scrollRectToVisible(bounds);
     
    
   }
 
  

   
   public void UpdateStoredVarPulldowns (SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, int BugIndex)
   {
   for (Action A: STAppData.BugArray.get(BugIndex).ActionsList)
   {
       if ("Store Link as Variable by XPATH".equals(A.Type))
       {
           STAppData.VarHashMap.remove(A.Variable2);
       }
       if ("Store Links as URL List by XPATH".equals(A.Type))
       {
           STAppData.VarLists.remove(A.Variable2);
       }
   }
   STAppFrame.updateStoredVarPulldownView();
   }
       public void AddLoopHandlers (SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, ProcedureView newbugview, Procedure newbug) 
      {
          String[] blanklist = new String[0];
           newbugview.addJComboBoxStoredArrayListsItemListener((ItemEvent e) -> {
        if ((e.getStateChange() == ItemEvent.SELECTED)) {
         if (newbugview.JComboBoxStoredArrayLists.getSelectedIndex()>0)
               {
             newbugview.setDataLoopSource("urllist");
          
          String selectedarraylist = newbugview.JComboBoxStoredArrayLists.getSelectedItem().toString(); 
      newbugview.setJTableSourceToURLList(blanklist, selectedarraylist);
      newbug.setURLListName(selectedarraylist);
       newbug.setDataLoopSource("urllist");

               }
            
        }
         
            
         
        });
       
            newbugview.addJButtonBrowseForDataFileActionListener((ActionEvent evt) -> {
             File chosenCSVFile = STAppFrame.ChangeCSVFile();
   if (chosenCSVFile!=null)
   {

   newbugview.setJComboBoxStoredArraylists("Select a stored URL List");
   newbugview.setJTableSourceToFile(chosenCSVFile.getAbsolutePath());
   newbugview.setDataLoopSource("file");
   newbug.setDataFile(chosenCSVFile.getAbsolutePath());
   newbug.setDataLoopSource("file");
  
   STAppFrame.UpdateDisplay();
   }
          });
      
      }
       
       public void AddNewHandlers (SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, ProcedureView newbugview, Procedure newbug)
      {
         newbugview.addJSpinnerLimitListener(new ChangeListener() {

        @Override
        public void stateChanged(ChangeEvent e) {
        newbug.limit = (Integer) newbugview.JSpinnerLimit.getValue();
        }
    });
         newbugview.addJCheckBoxRandomActionListener((ActionEvent evt) -> {
          newbug.random = newbugview.JCheckBoxRandom.isSelected();
         });
         
             
         
          newbugview.addJButtonOKActionListener((ActionEvent evt) -> {
           
               String ACommand = evt.getActionCommand();
               if (ACommand.equals("Update"))
         {
             
          newbugview.Disable();
          newbug.Disable();
            newbugview.Locked= true;
            newbug.Locked = true;
         }
         if (ACommand.equals("Edit"))
         {
             newbugview.Enable();
             newbug.Enable();
             newbugview.Locked = false;
             newbug.Locked = false;
         } 
          
           });
         newbugview.addRightClickPanelListener(this, newbug, newbugview, STAppFrame, STAppData);
         newbugview.addJButtonMoveProcedureUpActionListener((ActionEvent evt) -> {
               MoveProcedure(STAppFrame, STAppData, newbugview.index, -1);
           });
         newbugview.addJButtonMoveProcedureDownActionListener((ActionEvent evt) -> {
               MoveProcedure(STAppFrame, STAppData, newbugview.index, 1);
           });  
           newbugview.addJButtonRunTestActionListener((ActionEvent evt) -> {
               
            RunSingleTest(newbug, newbugview, STAppFrame, STAppData);
           });
           
           newbugview.addJTextFieldBugTitleDocListener(

           new DocumentListener()
           {
       @Override
       public void changedUpdate(DocumentEvent documentEvent) {
       newbug.setProcedureTitle(newbugview.JTextFieldBugTitle.getText());
 //      Window.changes = true;
      }
       @Override
      public void insertUpdate(DocumentEvent documentEvent) {
      newbug.setProcedureTitle(newbugview.JTextFieldBugTitle.getText());
//    Window.changes = true;
      }
      @Override
      public void removeUpdate(DocumentEvent documentEvent) {
      newbug.setProcedureTitle(newbugview.JTextFieldBugTitle.getText());
//      Window.changes = true;
      }
      }
           );
           
 newbugview.addJButtonDeleteBugActionListener((ActionEvent evt) -> {
      
    
               STAppData.DeleteBug(newbugview.index);
                STAppFrame.DeleteBugView(newbugview.index);
                    STAppFrame.UpdateDisplay();

           });

 
 
           newbugview.addJButtonGoActionActionListener((ActionEvent evt) -> {
              GoAction thisActionToAdd = new GoAction("");
               GoActionView thisActionViewToAdd = new GoActionView();
               thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
               STAppFrame.AddActionViewToArray(thisActionViewToAdd, newbugview);         
               STAppData.AddActionToArray(thisActionToAdd, newbug);
               STAppFrame.UpdateDisplay();
          STAppFrame.ScrollActionPaneDown(newbugview);
           
            STAppData.changes=true;  
           });
            newbugview.addJButtonClickAtXPATHActionListener((ActionEvent evt) -> {
              ClickXPATHAction thisActionToAdd = new ClickXPATHAction("", false, false);
              ClickXPATHActionView thisActionViewToAdd = new ClickXPATHActionView();
               thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
             
            thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
               STAppFrame.AddActionViewToArray(thisActionViewToAdd, newbugview);         
               STAppData.AddActionToArray(thisActionToAdd, newbug);
            STAppFrame.UpdateDisplay();
       STAppFrame.ScrollActionPaneDown(newbugview);
           STAppData.changes=true;  
           });
           newbugview.addJButtonTypeAtXPATHActionListener((ActionEvent evt) -> {
              TypeAtXPATHAction thisActionToAdd = new TypeAtXPATHAction("","", false);
              TypeAtXPATHActionView thisActionViewToAdd = new TypeAtXPATHActionView();
             thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
               STAppFrame.AddActionViewToArray(thisActionViewToAdd, newbugview);         
               STAppData.AddActionToArray(thisActionToAdd, newbug);
            STAppFrame.UpdateDisplay();
       STAppFrame.ScrollActionPaneDown(newbugview);
           STAppData.changes=true;  
           });
           newbugview.addJButtonFindXPATHPassFailListener((ActionEvent evt) -> {
             FindXPATHPassFailAction thisActionToAdd = new FindXPATHPassFailAction("", false);
             FindXPATHPassFailActionView thisActionViewToAdd = new FindXPATHPassFailActionView();
           thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
               thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
               STAppFrame.AddActionViewToArray(thisActionViewToAdd, newbugview);         
               STAppData.AddActionToArray(thisActionToAdd, newbug);
            STAppFrame.UpdateDisplay();
       STAppFrame.ScrollActionPaneDown(newbugview);
           STAppData.changes=true;  
           });
           newbugview.addJButtonDoNotFindXPATHPassFailListener((ActionEvent evt) -> {
             FindXPATHPassFailAction thisActionToAdd = new FindXPATHPassFailAction("", true);
             NOTFindXPATHPassFailActionView thisActionViewToAdd = new NOTFindXPATHPassFailActionView();
             thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
           thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
               STAppFrame.AddActionViewToArray(thisActionViewToAdd, newbugview);         
               STAppData.AddActionToArray(thisActionToAdd, newbug);
            STAppFrame.UpdateDisplay();
       STAppFrame.ScrollActionPaneDown(newbugview);
           STAppData.changes=true;  
           });
               newbugview.addJButtonYesNoPromptPassFailListener((ActionEvent evt) -> {
             YesNoPromptPassFailAction thisActionToAdd = new YesNoPromptPassFailAction("");
             YesNoPromptPassFailActionView thisActionViewToAdd = new YesNoPromptPassFailActionView();
             thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
           thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
               STAppFrame.AddActionViewToArray(thisActionViewToAdd, newbugview);         
               STAppData.AddActionToArray(thisActionToAdd, newbug);
            STAppFrame.UpdateDisplay();
       STAppFrame.ScrollActionPaneDown(newbugview);
           STAppData.changes=true;  
           });
           
    newbugview.addDoActionItemListener((ItemEvent e) -> {
        if ((e.getStateChange() == ItemEvent.SELECTED)) {
            Object ActionType = e.getItem();
            String ActionToAdd = ActionType.toString();
            ActionsMaster newActionsMaster = new ActionsMaster();
            HashMap<String, Action> ActionHashMap = newActionsMaster.ActionHashMap;
            HashMap<String, ActionView> ActionViewHashMap = newActionsMaster.ActionViewHashMap;
            
            newbugview.JComboBoxDoActions.setSelectedIndex(0);
           if (ActionHashMap.containsKey(ActionToAdd))
           {
               Action thisActionToAdd = ActionHashMap.get(ActionToAdd);
               ActionView thisActionViewToAdd = ActionViewHashMap.get(ActionToAdd);
              thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
           thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
                 
               STAppData.AddActionToArray(thisActionToAdd, newbug);
               STAppFrame.AddActionViewToArray(thisActionViewToAdd, newbugview);     
            STAppFrame.UpdateDisplay();
       STAppFrame.ScrollActionPaneDown(newbugview);
           STAppData.changes=true;  
           }      
         
            
       
        }
           });
     newbugview.addPassFailActionsItemListener((ItemEvent e) -> {
         if ((e.getStateChange() == ItemEvent.SELECTED)) {
             Object PassFailActionType = e.getItem();
             String PassFailActionToAdd = PassFailActionType.toString();
             ActionsMaster newActionsMaster = new ActionsMaster();
             HashMap<String, Action> PassFailActionHashMap = newActionsMaster.PassFailActionHashMap;
             HashMap<String, ActionView> PassFailActionViewHashMap = newActionsMaster.PassFailActionViewHashMap;
             
             newbugview.JComboBoxPassFailActions.setSelectedIndex(0);
             if (PassFailActionHashMap.containsKey(PassFailActionToAdd))
             {
                 Action thisActionToAdd = PassFailActionHashMap.get(PassFailActionToAdd);
               ActionView thisActionViewToAdd = PassFailActionViewHashMap.get(PassFailActionToAdd);
               thisActionViewToAdd.AddListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
           thisActionViewToAdd.AddLoopListeners(thisActionToAdd, STAppFrame, STAppData, newbug, newbugview);
            STAppData.AddActionToArray(thisActionToAdd, newbug); 
           STAppFrame.AddActionViewToArray(thisActionViewToAdd, newbugview);         
              
            STAppFrame.UpdateDisplay();
       STAppFrame.ScrollActionPaneDown(newbugview);
           STAppData.changes=true;  
             }
          
             
        
         }
           });
     AddLoopHandlers(STAppFrame, STAppData, newbugview, newbug);
       STAppFrame.addjCheckBoxShowReportActionListener((ActionEvent e) -> {
      STAppData.ShowReport = STAppFrame.getjCheckBoxShowReport();
      
  if (STAppData.ShowReport==false)
  {
       STAppFrame.setShowReportView(false);
      STAppFrame.setjCheckBoxIncludeScreenshotsEnabled(false);
    
  }
  else
  {
  STAppFrame.setShowReportView(true);
     STAppData.EmailReportFail = false;
  
      STAppData.EmailReport = false;
      STAppData.ExitAfter = false;
  }
          
       });
 STAppFrame.addjCheckBoxIncludeScreenshotsActionListener((ActionEvent e) -> {
         STAppData.IncludeScreenshots = STAppFrame.getIncludeScreenshots();
          
       });
       STAppFrame.addjCheckBoxPromptToCloseActionListener((ActionEvent e) -> {
         STAppData.PromptToClose = STAppFrame.getjCheckBoxPromptToClose();
          
       });
         STAppFrame.addjCheckBoxExitAfterActionListener((ActionEvent e) -> {
     STAppData.ExitAfter = STAppFrame.getjCheckBoxExitAfter();
  if (STAppData.ExitAfter)
  {
      STAppFrame.setjCheckBoxShowReportSelected(false);
      STAppData.ShowReport = false;
      
      
  }
          
       });
         STAppFrame.addjRadioButtonUniquePerFileActionListener((ActionEvent e) -> {
        if (STAppFrame.getjRadioButtonUniquePerFile())
      {
          STAppFrame.setjRadioButtonUniqueGlobalSelected(false);
           STAppData.UniqueOption = "file";
      }
      else
      {
         STAppFrame.setjRadioButtonUniqueGlobalSelected(true);
          STAppData.UniqueOption = "global";
      }        
       });   
         STAppFrame.addjRadioButtonUniqueGlobalActionListener((ActionEvent e) -> {
          if (STAppFrame.getjRadioButtonUniqueGlobalSelected())
      {
          STAppFrame.setjRadioButtonUniquePerFileSelected(false);
          STAppData.UniqueOption = "global";
      }
      else
      {
          STAppFrame.setjRadioButtonUniquePerFileSelected(true);
          STAppData.UniqueOption = "file";
      }  
             
       }); 
        STAppFrame.addjCheckBoxUniqueURLsActionListener((ActionEvent e) -> {
       STAppData.UniqueList = STAppFrame.getjCheckBoxUniqueURLsSelected();
    
        STAppFrame.setjRadioButtonUniquePerFileEnabled(STAppData.UniqueList);
        STAppFrame.setjRadioButtonUniqueGlobalEnabled(STAppData.UniqueList);
        if (!STAppFrame.getjRadioButtonUniquePerFileSelected() && !STAppFrame.getjRadioButtonUniqueGlobalSelected() )
        {
           STAppData.setUniqueFileOption("file");
        }
    
       });     
         STAppFrame.addjCheckBoxEmailReportActionListener((ActionEvent e) -> {
      STAppData.EmailReport = STAppFrame.getjCheckBoxEmailReportSelected();
  if (STAppData.EmailReport==true)
  {
       STAppFrame.setjCheckBoxIncludeScreenshotsEnabled(false);
      STAppData.IncludeScreenshots=false;   
      STAppFrame.setjCheckBoxShowReportSelected(false);
      STAppData.ShowReport = false;
      STAppFrame.setjCheckBoxEmailReportFailSelected(false);

      STAppData.EmailReportFail = false;
  }
    
       });     
        
          STAppFrame.addjCheckBoxEnableMultiSessionActionListener((ActionEvent e) -> {
    if (STAppFrame.getjCheckBoxEnableMultiSessionSelected())
    {
     STAppFrame.setjSpinnerSessionsEnabled(true);
     STAppData.MultiSession = true;
     
    } 
    else
    {
       STAppFrame.setjSpinnerSessionsEnabled(false);
        STAppData.MultiSession = false;
    }
    
       });      
              STAppFrame.addjCheckBoxEmailReportFailActionListener((ActionEvent e) -> {
    STAppData.EmailReportFail = STAppFrame.getjCheckBoxEmailReportFailSelected();
  if (STAppData.EmailReportFail==true)
  {
       STAppFrame.setjCheckBoxIncludeScreenshotsEnabled(false);
      STAppData.IncludeScreenshots=false;   
  STAppFrame.setjCheckBoxShowReportSelected(false);
            STAppData.ShowReport = false;
     STAppFrame.setjCheckBoxEmailReportSelected(false);
     STAppData.EmailReport = false;
  }

    
       });  
                  STAppFrame.addjCheckBoxOSTypeWindows32ActionListener((ActionEvent e) -> {

     if (STAppFrame.getjCheckBoxOSTypeWindows32Selected())
     {
         STAppData.OSType = "Windows32";
         STAppFrame.setjCheckBoxOSTypeWindows64Selected(false);
     STAppFrame.setjCheckBoxOSTypeMacSelected(false);
     STAppFrame.setjCheckBoxOSTypeLinux32Selected(false);
     STAppFrame.setjCheckBoxOSTypeLinux64Selected(false);
     }
       });     
   
      STAppFrame.addjCheckBoxOSTypeWindows64ActionListener((ActionEvent e) -> {
  if (STAppFrame.getjCheckBoxOSTypeWindows64Selected())
     {
     STAppData.OSType = "Windows64";
     STAppFrame.setjCheckBoxOSTypeWindows32Selected(false);
     STAppFrame.setjCheckBoxOSTypeMacSelected(false);
     STAppFrame.setjCheckBoxOSTypeLinux32Selected(false);
     STAppFrame.setjCheckBoxOSTypeLinux64Selected(false);
     }
   
       });     
                  
         STAppFrame.addjCheckBoxOSTypeMacActionListener((ActionEvent e) -> {
     if (STAppFrame.getjCheckBoxOSTypeMacSelected())
     {
        STAppData.OSType = "Mac";
     STAppFrame.setjCheckBoxOSTypeWindows32Selected(false);
     STAppFrame.setjCheckBoxOSTypeLinux32Selected(false);
     STAppFrame.setjCheckBoxOSTypeLinux64Selected(false);
     STAppFrame.setjCheckBoxOSTypeWindows64Selected(false);
     }
   
       });             
    
                       
         STAppFrame.addjCheckBoxOSTypeLinux32ActionListener((ActionEvent e) -> {
      if (STAppFrame.getjCheckBoxOSTypeLinux32Selected())
     {
         STAppData.OSType = "Linux32";
     STAppFrame.setjCheckBoxOSTypeMacSelected(false);
     STAppFrame.setjCheckBoxOSTypeWindows32Selected(false);
     STAppFrame.setjCheckBoxOSTypeLinux64Selected(false);
     STAppFrame.setjCheckBoxOSTypeWindows64Selected(false);
     }
   
       });   
    
             STAppFrame.addjCheckBoxOSTypeLinux64ActionListener((ActionEvent e) -> {
       if (STAppFrame.getjCheckBoxOSTypeLinux64Selected())
     {
         STAppData.OSType = "Linux64";
     STAppFrame.setjCheckBoxOSTypeMacSelected(false);
     STAppFrame.setjCheckBoxOSTypeLinux32Selected(false);
     STAppFrame.setjCheckBoxOSTypeWindows32Selected(false);
     STAppFrame.setjCheckBoxOSTypeWindows64Selected(false);
     }

   
       });  
         
         
         
      }
       
         
   
  public void RunActions (SeleniumTestToolData STAppData)
  {
           int sessions = 1;
         if (STAppData.MultiSession)
         {
          sessions = STAppData.getSessions();
         }
         
          String tbrowser = STAppData.getTargetBrowser();
      if ("Firefox/IE/Chrome".equals(tbrowser))
      {
 for (int x=0; x<sessions; x++)
 {
    
     STAppData.setTargetBrowser("Firefox");
       RunAllTests REFSYNCH = new RunAllTests(STAppData);
    REFSYNCH.execute();   
    STAppData.setTargetBrowser("Chrome");
       RunAllTests REFSYNCH2 = new RunAllTests(STAppData);
    REFSYNCH2.execute();  
    STAppData.setTargetBrowser("Internet Explorer-32");
      RunAllTests REFSYNCH3 = new RunAllTests(STAppData);
    REFSYNCH3.execute();  
    STAppData.setTargetBrowser("Firefox/IE/Chrome");
 }
      }
      else
      {
     for (int x=0; x<sessions; x++)
 {
    RunAllTests REFSYNCH = new RunAllTests(STAppData);
    REFSYNCH.execute();      
 }       
  }
  }
    public void RunActions(SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData)
 {
   if ("Run All Procedures".equals(STAppFrame.getRunActionsButtonName()))
     {
         
          int sessions = 1;
         if (STAppData.MultiSession)
         {
          sessions = STAppData.getSessions();
         }
         
          String tbrowser = STAppData.getTargetBrowser();
      if ("Firefox/IE/Chrome".equals(tbrowser))
      {
 for (int x=0; x<sessions; x++)
 {
    
     STAppData.setTargetBrowser("Firefox");
       RunAllTests REFSYNCH = new RunAllTests(STAppFrame, STAppData);
    REFSYNCH.execute();   
    STAppData.setTargetBrowser("Chrome");
       RunAllTests REFSYNCH2 = new RunAllTests(STAppFrame, STAppData);
    REFSYNCH2.execute();  
    STAppData.setTargetBrowser("Internet Explorer-32");
      RunAllTests REFSYNCH3 = new RunAllTests(STAppFrame, STAppData);
    REFSYNCH3.execute();  
    STAppData.setTargetBrowser("Firefox/IE/Chrome");
 }
      }
      else
      {
     for (int x=0; x<sessions; x++)
 {
    RunAllTests REFSYNCH = new RunAllTests(STAppFrame, STAppData);
    REFSYNCH.execute();      
 }     
      }
     }
  //   else
  //   {
 //        this.cancelled = true;
 //    }
 
 }

 public void RunSingleTest(Procedure bugtorun, ProcedureView thisbugview, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData)
 {
      RunASingleTest REFSYNCH = new RunASingleTest(STAppFrame, STAppData, bugtorun, thisbugview, STAppData.getTargetBrowser(), STAppData.getOSType());
    REFSYNCH.execute();
 }



      
}
