/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author pcalkins
 */
public class ExecuteJavascriptActionView extends ActionView implements Loopable {
   ExecuteJavascriptActionView()
   {

       this.JLabelVariable1 = new JLabel("Execute Javascript:");
       this.JButtonBrowseForFile = new JButton("Browse for .js file");
 this.JPanelAction.add(this.JLabelVariable1);
      this.JPanelAction.add(this.JTextFieldVariable1); 
      this.JPanelAction.add(this.JButtonBrowseForFile);
      this.JPanelAction.add(this.JButtonOK);
        this.JPanelAction.add(this.JButtonDelete);
        theseActionSettings.add(new ActionSettings(JLabelVariable1, 2, 1, 0.0, GridBagConstraints.WEST));
         theseActionSettings.add(new ActionSettings(JTextFieldVariable1, 3, 1, 1.0, GridBagConstraints.WEST));
          theseActionSettings.add(new ActionSettings(JButtonBrowseForFile, 4, 4, 0.0, GridBagConstraints.WEST));
  theseActionSettings.add(new ActionSettings(JButtonOK, 8, 1, 0.0, GridBagConstraints.WEST));
   theseActionSettings.add(new ActionSettings(JButtonDelete, 9, 1, 0.0, GridBagConstraints.WEST));
   
   }
   @Override  
 public void AddListeners(Action action, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, Procedure newbug, ProcedureView newbugview)
   {
   addJButtonBrowseForFile((ActionEvent evt) -> {
     File jsFile = STAppFrame.BrowseForJSFileAction();
      if (jsFile!=null)
      {
         STAppFrame.saveState();
          action.setVariable1(jsFile.getAbsolutePath());
       this.JTextFieldVariable1.setText(jsFile.getAbsolutePath());
      }
   });
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
