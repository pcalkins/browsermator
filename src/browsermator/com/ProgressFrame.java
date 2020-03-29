/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Pete
 */
public class ProgressFrame {
   JButton jButtonPause;
    JButton jButtonContinue;
    JButton jButtonCancel;

  JLabel jLabelTasks;
  JTextField JTextFieldProgress;
   JFrame mainFrame;
  String title;
  ProgressFrame(String in_title)
  {
    title = in_title;
  }
  public void initNoButtons(String _text)
  {
     mainFrame = new JFrame(title); 
      JTextFieldProgress = new javax.swing.JTextField("Launching Browser");
       JTextFieldProgress.setPreferredSize(new Dimension(300, 35));
       JTextFieldProgress.setText(_text);
         JPanel jPanelSouth2 = new JPanel();
        jPanelSouth2.add(JTextFieldProgress);
         JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout( contentPanel , BoxLayout.Y_AXIS));
        contentPanel.add(jPanelSouth2);
          mainFrame.add(contentPanel);
       mainFrame.pack();
                 GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
       GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
     
        int x = (int) rect.getMaxX() - mainFrame.getWidth();
        int y = (int) rect.getMaxY() - mainFrame.getHeight();
    //   setResizable(false);
     y = y-38;
        mainFrame.setLocation(x, y); 
                mainFrame.setVisible(true);
             mainFrame.pack();
  }
  public void initFrame()
  {
      mainFrame = new JFrame(title);
      jLabelTasks = new JLabel();
       JTextFieldProgress = new javax.swing.JTextField("Launching Browser");
            JPanel jPanelSouth2 = new JPanel();
        jLabelTasks.setText("Running Tasks:");   
        jPanelSouth2.add(jLabelTasks);
      jLabelTasks = new javax.swing.JLabel();
  jButtonPause = new JButton("Pause");
        jButtonContinue = new JButton("Continue");
        jButtonContinue.setEnabled(false);
        jButtonCancel = new JButton("Cancel");
    jButtonCancel.setEnabled(false);
         JPanel   SouthButtonPanel = new JPanel();
        SouthButtonPanel.add(jButtonContinue);
        SouthButtonPanel.add(jButtonPause);
        SouthButtonPanel.add(jButtonCancel);
       JTextFieldProgress.setPreferredSize(new Dimension(300, 30));
       JTextFieldProgress.setText("Launching Browser");
        jPanelSouth2.add(JTextFieldProgress);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout( contentPanel , BoxLayout.Y_AXIS));
        contentPanel.add(jPanelSouth2);
        contentPanel.add(SouthButtonPanel);
      
        
        mainFrame.add(contentPanel);
  //      mainFrame.add(jPanelSouth2);
        
             
               mainFrame.pack();
                   GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
       GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
     
        int x = (int) rect.getMaxX() - mainFrame.getWidth();
        int y = (int) rect.getMaxY() - mainFrame.getHeight();
    //   setResizable(false);
     y = y-38;
        mainFrame.setLocation(x, y); 
                mainFrame.setVisible(true);
               
              
             mainFrame.pack();
  }
  
    public void addJButtonCancelActionListener(ActionListener listener)
  {
    jButtonCancel.addActionListener(listener);
  }
   public void addJButtonPauseActionListener(ActionListener listener)
  {
      jButtonPause.addActionListener(listener);
  }
    public void addJButtonContinueActionListener(ActionListener listener)
  {
      jButtonContinue.addActionListener(listener);
  }
       public void setJTextFieldProgress(String value)
{
  jButtonCancel.setEnabled(true);
    JTextFieldProgress.setText(value);
}
  public void Pause()
  {
      jButtonCancel.setEnabled(false);
   jButtonPause.setEnabled(false);
       jButtonContinue.setEnabled(true);
    
  }
  public void Continue()
  {
      jButtonCancel.setEnabled(true);
       jButtonPause.setEnabled(true);
       jButtonContinue.setEnabled(false);
    
  }
}
