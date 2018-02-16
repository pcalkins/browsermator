/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;


import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class PromptToClose extends JFrame  {
    
    Boolean runAgain = false;
      JButton jButtonRunAgain;
    JButton jButtonClose;
     JLabel messageText;
     JPanel messagePanel;
    JPanel buttonPanel;
         
    PromptToClose ( String filename, String messagetodisplay)
            {
           jButtonRunAgain = new JButton("Run Again");
           jButtonClose = new JButton("Close");
           
           messageText = new JLabel(messagetodisplay);
           messagePanel = new JPanel();  
           messagePanel.add(messageText);
           buttonPanel = new JPanel();
           buttonPanel.add(jButtonClose);
           buttonPanel.add(jButtonRunAgain);
            JPanel WholePrompt = new JPanel();
                WholePrompt.setLayout(new BorderLayout());
                WholePrompt.add(messagePanel, BorderLayout.CENTER);
                WholePrompt.add(buttonPanel, BorderLayout.SOUTH);
                
               
         setSize(400, 200);
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - this.getWidth();
       setResizable(false);
        this.setLocation(x, 0);
                this.setTitle(filename);
                add(WholePrompt);
             
                
                setVisible(true);
                pack();
           }
    public void addjButtonCloseActionListener(ActionListener listener)
    {
        jButtonClose.addActionListener(listener);
    }
    public void addjButtonRunAgainActionListener(ActionListener listener)
    {
        jButtonRunAgain.addActionListener(listener);
    }
}