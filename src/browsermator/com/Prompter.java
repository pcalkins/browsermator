package browsermator.com;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Prompter extends JFrame implements ActionListener 
{
    JButton ContinueButton;
    JButton CancelButton;
    Boolean cancelled;
    Boolean hasCancelButton;
     Prompter (String messagetodisplay, Boolean in_hascancel)
            {
              
           this.cancelled = false;    
           this.hasCancelButton = in_hascancel;
                ContinueButton = new JButton("Continue");
                CancelButton = new JButton("Cancel"); 
             
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //        FlowLayout flo = new FlowLayout();
        //        setLayout(flo);
                JLabel messageText = new JLabel(messagetodisplay);
                JPanel messagepanel = new JPanel();
                
                messagepanel.add(messageText);
             
                JPanel buttonpanel = new JPanel();
           //  buttonpanel.setLayout(new FlowLayout());
                buttonpanel.add(ContinueButton);
                if (this.hasCancelButton)
                {
               buttonpanel.add(CancelButton);
                }
                
                JPanel WholePrompt = new JPanel();
                WholePrompt.setLayout(new BorderLayout());
                WholePrompt.add(messagepanel, BorderLayout.CENTER);
                WholePrompt.add(buttonpanel, BorderLayout.SOUTH);
                
               
                setSize(250, 100);
                 GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - this.getWidth();
      
        this.setLocation(x, 0);
                this.setTitle("Actions Paused");
                add(WholePrompt);
             
                
                setVisible(true);
                ContinueButton.addActionListener(this);
              
             //  pack();
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
       public void addCancelButtonActionListener(ActionListener listener) {
       CancelButton.addActionListener(listener);
   }
}
