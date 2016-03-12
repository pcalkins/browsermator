/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

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
   }
   @Override  
 public void AddListeners(Action action, SeleniumTestTool Window, Procedure newbug, ProcedureView newbugview)
   {
   addJButtonBrowseForFile((ActionEvent evt) -> {
     File jsFile = Window.BrowseForJSFileAction();
      if (jsFile!=null)
      {
       action.setVariable1(jsFile.getAbsolutePath());
       this.JTextFieldVariable1.setText(jsFile.getAbsolutePath());
      }
   });
     AddDraggers(action, Window, newbug, newbugview);
     
                        addJButtonDeleteActionActionListener((ActionEvent evt) -> {
                          Window.DeleteAction(newbug, newbugview, action.index);
                           Window.UpdateScrollPane(newbugview);
   });
  


     addJTextFieldVariable1DocListener(
             new DocumentListener()
           {
@Override
       public void changedUpdate(DocumentEvent documentEvent) {
      action.setVariable1(JTextFieldVariable1.getText());

      }
@Override
      public void insertUpdate(DocumentEvent documentEvent) {
      action.setVariable1(JTextFieldVariable1.getText());
      }
@Override
      public void removeUpdate(DocumentEvent documentEvent) {
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
