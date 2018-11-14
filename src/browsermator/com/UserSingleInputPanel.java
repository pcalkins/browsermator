/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Pete
 */
public class UserSingleInputPanel extends JPanel {
    String inputName;
                    
                JLabel jLabelInputValue;
                JTextField jTextFieldInputValue;
              
                JPanel jPanelSetInputValue;
             
       
               
                
               
                String input_value;
   
             
             
    public UserSingleInputPanel(String in_inputname)
    {
        inputName = in_inputname;
        input_value = "";
      

      
    }
  
    public void CreatePanel()
    {
        
             
          jLabelInputValue = new JLabel(inputName + " Value:");
          jTextFieldInputValue = new JTextField(input_value, 20);
          
          jPanelSetInputValue = new JPanel();
            
          jPanelSetInputValue.add(jLabelInputValue);
          jPanelSetInputValue.add(jTextFieldInputValue);
              
         
                add(jPanelSetInputValue);
              
               setSize(600,100);
                setVisible(true);

    }
     public void addjTextFieldInputValueActionListener(ActionListener listener)
    {
        jTextFieldInputValue.addActionListener(listener);
    }

    public String GetInputName()
    {
        String ret_val = "";
        ret_val = inputName;
        return ret_val;
    }
    public String GetInputValue()
    {
        String ret_val = "";
        ret_val = jTextFieldInputValue.getText();
        return ret_val;
    }
   
    
}
