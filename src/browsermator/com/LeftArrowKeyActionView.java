/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;

/**
 *
 * @author pcalkins
 */
public class LeftArrowKeyActionView extends ActionView {
       LeftArrowKeyActionView()
   {
    
       
        
       this.JLabelVariable1 = new JLabel("Left Arrow Key");
theseActionSettings.add(new ActionSettings(JLabelVariable1, 2, 6, 1.0, GridBagConstraints.WEST));
  theseActionSettings.add(new ActionSettings(JButtonOK, 8, 1, 0.0, GridBagConstraints.WEST));
   theseActionSettings.add(new ActionSettings(JButtonDelete, 9, 1, 0.0, GridBagConstraints.WEST));
 
     
  
   }
}
