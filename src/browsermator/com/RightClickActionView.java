/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import javax.swing.JLabel;

/**
 *
 * @author pcalkins
 */
public class RightClickActionView extends ActionView {
         RightClickActionView()
   {
    
       this.JLabelVariable1 = new JLabel("Right-Click");
 this.JPanelAction.add(this.JLabelVariable1);
 this.JPanelAction.add(this.JButtonOK);
   this.JPanelAction.add(this.JButtonDelete);
     
  
   }
}
