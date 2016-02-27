package browsermator.com;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Prompter extends JFrame implements ActionListener 
{
    JButton ContinueButton;

    
     Prompter (String messagetodisplay)
            {
              
                
                ContinueButton = new JButton("Continue");
          
             
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                GridLayout flo = new GridLayout();
                setLayout(flo);
             
                add(ContinueButton);
                setSize(400, 200);
                this.setTitle(messagetodisplay);
                
             
                
                setVisible(true);
                ContinueButton.addActionListener(this);
              

            }
 @Override
     public void actionPerformed(ActionEvent event)
     {
         Object source = event.getSource();
         if (source == ContinueButton)
         {
          this.setVisible(false);
          this.dispose();
     
     }
        
}
}
