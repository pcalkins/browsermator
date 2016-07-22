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
                FlowLayout flo = new FlowLayout();
                setLayout(flo);
                JLabel messageText = new JLabel(messagetodisplay);
                JPanel flowpanel = new JPanel();
                
                flowpanel.add(messageText);
                JPanel boxpanel = new JPanel();
                boxpanel.setLayout(new BoxLayout(boxpanel, BoxLayout.Y_AXIS));
                boxpanel.add(flowpanel);
                boxpanel.add(ContinueButton);
                setSize(400, 200);
                 GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - this.getWidth();
      
        this.setLocation(x, 0);
                this.setTitle(messagetodisplay);
                add(boxpanel);
             
                
                setVisible(true);
                ContinueButton.addActionListener(this);
              
               pack();
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
