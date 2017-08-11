/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.io.FileInputStream;
import java.util.Properties;
import javax.swing.ButtonGroup;
// import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Pete
 */
public class MainAppFrame extends JFrame {
      Properties newProps = new Properties();
      String filename;
      String short_filename;
      private JRadioButtonMenuItem LookAndFeelOptions[];
 //     public final SiteTestView Navigator;
// public JDesktopPane SeleniumToolDesktop;
private ButtonGroup LookAndFeelGroup;
     private JMenuBar menuBar;
     private JMenu fileMenu;
     private JMenu editMenu;
     private JMenuItem undoMenuItem;
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
          private JMenuItem importMenuItem;
          private UIManager.LookAndFeelInfo LAFOptions[];
  public MainAppFrame()
  {
          super ("Browsermator");
   
      
      this.filename = "";
      this.short_filename = "";
     //  Navigator = new SiteTestView();
   
     
  }
 public void addjMenuViewItem(JMenuItem newfileitem)
 {
     jMenuView.add(newfileitem);
 }
 public void removejMenuViewItem(int index)
 {
     jMenuView.remove(index);
 }
 public int getjMenuViewItemCount()
 {
     int ret_val = 0;
     ret_val = jMenuView.getItemCount();
     return ret_val;
 }
 public void setjMenuViewItemText(int index, String update_name)
 {
     jMenuView.getItem(index).setText(update_name);
 }
     
 public String getjMenuViewItem(int index)
 {
     String ret_val;
     ret_val = jMenuView.getItem(index).getText();
     return ret_val;
 }

 public void addExitMenuActionListener(ActionListener listener)
 {
    exitMenuItem.addActionListener(listener);
 }
 public void addAboutMenuItemActionListener(ActionListener listener)
 {
     aboutMenuItem.addActionListener(listener);
 }
  public void setWindowProps(FileInputStream input)
          {
              try
              {
            newProps.load(input);
              }
              catch (Exception ex)
              {
                  System.out.println ("Exception loading config : " + ex.toString());
              }
                int winLocY =  Integer.parseInt(newProps.getProperty("main_window_locationY", "0"));
   int winLocX =   Integer.parseInt(newProps.getProperty("main_window_locationX", "0"));
   int winWidth =  Integer.parseInt(newProps.getProperty("main_window_sizeWidth", "1200"));
   int winHeight = Integer.parseInt(newProps.getProperty("main_window_sizeHeight", "802"));
      java.awt.Point startPosition = new java.awt.Point(winLocX, winLocY);
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
          @SuppressWarnings("unchecked")
    public void initComponents() {
   
      menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        editMenu = new javax.swing.JMenu();
        undoMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        newFileItem = new javax.swing.JMenuItem();
        jMenuView = new javax.swing.JMenu();
        jMenuThemes = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        browseCloudMenuItem = new javax.swing.JMenuItem();
        uploadFileToCloudMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        closeMenuItem = new javax.swing.JMenuItem();
        importMenuItem = new javax.swing.JMenuItem();
        newFileItem.setMnemonic('n');
        newFileItem.setText("New");
        undoMenuItem.setText("Undo");
        openMenuItem.setMnemonic('o');
        undoMenuItem.setMnemonic('Z');
        openMenuItem.setText("Open");
        closeMenuItem.setText("Close");
        importMenuItem.setMnemonic('i');
        importMenuItem.setText("Import");
        editMenu.add(undoMenuItem);
        fileMenu.add(newFileItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(importMenuItem);
        fileMenu.setMnemonic('f');
        fileMenu.setText("File");
        editMenu.setText("Edit");
        saveAsMenuItem.setText("Save As");
        saveAsMenuItem.setMnemonic('a');
        browseCloudMenuItem.setText("BrowserMator File Cloud");
        uploadFileToCloudMenuItem.setText("Upload to BrowserMator Cloud");
           undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));  
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
            fileMenu.add(exitMenuItem);

       
        
            fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        menuBar.add(editMenu);

      
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
   
    }
    public void setSaveMenuItemEnabled(Boolean enable_it)
    {
      saveMenuItem.setEnabled(enable_it);
    }
     public void addFileMenuCloseActionListener (ActionListener listener)
   {
       closeMenuItem.addActionListener(listener);
   }
   public void addFileMenuNewActionListener (ActionListener listener)
   {
       newFileItem.addActionListener(listener);
   }
    public void addEditMenuUndoActionListener (ActionListener listener) {
       undoMenuItem.addActionListener(listener);
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
   public void showMainWindow (boolean visible)
   {
       this.setVisible(visible);
   }
}
