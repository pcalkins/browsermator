package browsermator.com;


import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class FindXPATHPassFailActionView extends ActionView 
{
  
   FindXPATHPassFailActionView()
   {
  
       this.JLabelVariable1 = new JLabel("Find XPATH:");
       
  theseActionSettings.add(new ActionSettings(JLabelVariable1, 2, 1, 0.0, GridBagConstraints.WEST));
        theseActionSettings.add(new ActionSettings(JTextFieldVariable1, 3, 5, 1.0, GridBagConstraints.WEST));
        theseActionSettings.add(new ActionSettings(JButtonOK, 8, 1, 0.0, GridBagConstraints.WEST));
        theseActionSettings.add(new ActionSettings(JButtonDelete, 9, 1, 0.0, GridBagConstraints.WEST));
         
   }
 @Override  
public void AddListeners(BMAction action, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, Procedure newbug, ProcedureView newbugview)
   {
 AddDraggers(action, STAppFrame, STAppData, newbug, newbugview);

                        addJButtonDeleteActionActionListener((ActionEvent evt) -> {
                            STAppFrame.saveState();
                            STAppFrame.DeleteActionView(newbugview, action.index);
                          STAppData.DeleteAction(newbug, action.index);
                      
   });
   



     addJTextFieldVariable1DocListener(
             new DocumentListener()
           {
@Override
       public void changedUpdate(DocumentEvent documentEvent) {
 STAppFrame.saveState();
           action.setVariable1(JTextFieldVariable1.getText());

      }
@Override
      public void insertUpdate(DocumentEvent documentEvent) {
      action.setVariable1(JTextFieldVariable1.getText());
      }
@Override
      public void removeUpdate(DocumentEvent documentEvent) {
  STAppFrame.saveState();
          action.setVariable1(JTextFieldVariable1.getText());
      }
      }
                 );

     addJButtonOKActionActionListener((ActionEvent evt) -> {
             STAppFrame.saveState();
         String ACommand = evt.getActionCommand();
         
         if (ACommand.equals("Update"))
         {
             
             UpdateActionView();
             action.Locked= true;
             
         }
         if (ACommand.equals("Edit"))
         {
             EditActionView();
             action.Locked= false;
             
         } });        

   } 
 
}