package browsermator.com;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Prompter extends JFrame implements ActionListener 
{
    JButton ContinueButton;
    JButton CancelButton;
    JLabel JLabelJumpTo;
    JComboBox JComboBoxJumpToValue;
    Boolean cancelled;
    Boolean hasCancelButton;
  int JumpToRecord = -1;
    int number_of_records = 0;
      JTextArea jTextAreaMessage;
      JLabel jLabelMessage;
     Prompter ( String filename, String messagetodisplay, Boolean in_hascancel, int currentrecord, int number_of_records)
            {
           this.JLabelJumpTo = new JLabel("Skip to #: ");
           this.setResizable(true);
          
           if (number_of_records>0)
           {
               this.JComboBoxJumpToValue = new JComboBox();
           for (int x = 0; x<number_of_records; x++)
           {
            JComboBoxJumpToValue.addItem(x+1);
           }
           JComboBoxJumpToValue.setSelectedItem(currentrecord+1);
           }
           this.number_of_records = number_of_records;
           this.cancelled = false;    
           this.hasCancelButton = in_hascancel;
                ContinueButton = new JButton("Continue");
                CancelButton = new JButton("Cancel"); 
             
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //        FlowLayout flo = new FlowLayout();
        //        setLayout(flo);
            
             
                JPanel buttonpanel = new JPanel();
           //  buttonpanel.setLayout(new FlowLayout());
                buttonpanel.add(ContinueButton);
                   JPanel messagepanel = new JPanel();
                if (this.hasCancelButton)
                {
                    this.setResizable(false);
               buttonpanel.add(CancelButton);
               jLabelMessage = new JLabel(messagetodisplay);                       
                messagepanel.add(jLabelMessage);
                }
                else
                {
           //     jTextAreaMessage = new JTextArea(5, 40); 
                    jTextAreaMessage = new JTextArea(messagetodisplay); 
                jTextAreaMessage.setLineWrap(true);
            //    jTextAreaMessage.setText(messagetodisplay);
                jTextAreaMessage.setColumns(40);
                messagepanel.add(jTextAreaMessage);
                }
                        
                if (this.number_of_records>0)
                {
                    buttonpanel.add(JLabelJumpTo);
                    buttonpanel.add(JComboBoxJumpToValue);
                }
                JPanel WholePrompt = new JPanel();
                WholePrompt.setLayout(new BorderLayout());
                WholePrompt.add(messagepanel, BorderLayout.CENTER);
                WholePrompt.add(buttonpanel, BorderLayout.SOUTH);
                
               
         // setSize(400, 200);
       
    
    //   setResizable(false);
    
                this.setTitle(filename);
                add(WholePrompt);
               pack();
                   GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
       GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - this.getWidth();
    //   setResizable(false);
        this.setLocation(x, 0); 
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
          ClickedContinue();
         }
   
     }
     public void ClickedContinue()
     {
          if (this.number_of_records>0)
           {
           this.JumpToRecord = JComboBoxJumpToValue.getSelectedIndex();
           }
          this.setVisible(false);
          this.dispose();
     }
      public void addJumpToItemListener(ItemListener listener) {
       JComboBoxJumpToValue.addItemListener(listener);
     
   } 
       public void addCancelButtonActionListener(ActionListener listener) {
       CancelButton.addActionListener(listener);
   }
}
