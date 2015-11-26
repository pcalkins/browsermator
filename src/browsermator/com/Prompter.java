package browsermator.com;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Prompter extends JFrame implements ActionListener 
{
    JButton YesButton;
    JButton NoButton;
    JLabel PromptText;
    
     Prompter (String messagetodisplay)
            {
                setLookAndFeel();
                setSize(348,128);
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                FlowLayout flo = new FlowLayout();
                setLayout(flo);
                YesButton = new JButton("Yes");
                NoButton = new JButton("No");
                PromptText = new JLabel();
                add(YesButton);
                add(NoButton);
                PromptText.setText(messagetodisplay);
                
                add(PromptText);
                
                setVisible(true);
                YesButton.addActionListener(this);
                NoButton.addActionListener(this);

            }
     private void setLookAndFeel()
     {
         try 
         {
             UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
         }
         catch (Exception exc)
                             {
                             }
                             
                            
         
     }
     public void actionPerformed(ActionEvent event)
     {
         Object source = event.getSource();
         if (source == YesButton)
         {
          this.setVisible(false);
          this.dispose();
      
         
     }
         if (source==NoButton)
         {
         //   this.setVisible(false);
        //  this.dispose();
        
         }
}
}
