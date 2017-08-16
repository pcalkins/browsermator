/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author pcalkins
 */
public class ProgressWindow extends JFrame {

    JPanel progressPanel;
    JLabel jLabelTasks;
    JTextField jTextFieldProgress;
    JButton jButtonPause;
    JButton jButtonContinue;
    JButton jButtonCancel;
    GridBagLayout GridBagLayout;
    GridBagConstraints BugConstraints;
    String filename;
    JPanel buttonPanel;
    ProgressWindow(String in_filename)
    {
   
        buttonPanel = new JPanel();
        filename = in_filename;
        GridBagLayout = new GridBagLayout();
        BugConstraints = new GridBagConstraints();
        jButtonPause = new JButton("Pause");
        jButtonContinue = new JButton("Continue");
        jButtonContinue.setEnabled(false);
        jButtonCancel = new JButton("Cancel");
        
          progressPanel = new JPanel();
        jTextFieldProgress = new JTextField(25);
        jLabelTasks = new JLabel("Running Tasks: ");
        
          progressPanel.setLayout(GridBagLayout);
       buttonPanel.add(jButtonContinue);
       buttonPanel.add(jButtonPause);
       buttonPanel.add(jButtonCancel);
       
       AddToGrid(jLabelTasks, 0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE);
       AddToGrid(jTextFieldProgress, 0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL);
       AddToGrid(buttonPanel, 1,1,1,1,0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE);
     

    

                this.setTitle(filename);
      this.add(progressPanel);  
      
                
                setVisible(true);
 this.pack();
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - this.getWidth();
     
        this.setLocation(x, 0);  

    }
     public void setjTextFieldProgress(String in_text)
     {

         jTextFieldProgress.setText(in_text);
     
     }
     public final void AddToGrid( Component component, int row, int column, int width, int height, double weightx, double weighty, int anchor_value, int fill)
     {
         BugConstraints.fill = fill;
         BugConstraints.gridx = column;
         BugConstraints.gridy = row;
         BugConstraints.gridwidth = width;
         BugConstraints.gridheight = height;
         BugConstraints.weightx = weightx;
         BugConstraints.weighty = weighty;
         BugConstraints.anchor = anchor_value;
      
         progressPanel.add(component, BugConstraints);
        progressPanel.validate();
     }
     public void Pause()
     {
       jButtonPause.setEnabled(false);
       jButtonContinue.setEnabled(true);
      
     
 
     }
     public void Continue()
     {
       jButtonPause.setEnabled(true);
       jButtonContinue.setEnabled(false);
         
     }
  public void addJButtonContinueActionListener(ActionListener listener) {
      jButtonContinue.addActionListener(listener);
   }
   public void addJButtonPauseActionListener(ActionListener listener) {
      jButtonPause.addActionListener(listener);
          }
    public void addJButtonCancelActionListener(ActionListener listener) {
       jButtonCancel.addActionListener(listener);
   }
}
