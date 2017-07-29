package browsermator.com;



import java.awt.GridBagConstraints;
import javax.swing.JLabel;


public class ForwardActionView extends ActionView
{
  
   ForwardActionView()
   {
   
   this.JLabelVariable1 = new JLabel("Click Forward Button");
       
theseActionSettings.add(new ActionSettings(JLabelVariable1, 2, 6, 1.0, GridBagConstraints.WEST));
  theseActionSettings.add(new ActionSettings(JButtonOK, 8, 1, 0.0, GridBagConstraints.WEST));
   theseActionSettings.add(new ActionSettings(JButtonDelete, 9, 1, 0.0, GridBagConstraints.WEST));
   
  
   }
 
   
}