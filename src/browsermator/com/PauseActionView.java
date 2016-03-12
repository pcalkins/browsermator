/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author pcalkins
 */
public class PauseActionView extends ActionView {
  PauseActionView()
   {
 
    
    this.JLabelVariable1 = new JLabel("Minutes to Pause:");
    this.JLabelVariable2 = new JLabel("Seconds to Pause:");
  
    this.JPanelAction.add(this.JLabelVariable1);
      this.JPanelAction.add(this.JTextFieldVariable1); 
      this.JPanelAction.add(this.JLabelVariable2);
      this.JPanelAction.add(this.JTextFieldVariable2);
       this.JPanelAction.add(this.JButtonOK);
  this.JPanelAction.add(this.JButtonDelete);
  
   }
 @Override  
 public void AddListeners(Action action, SeleniumTestTool Window, Procedure newbug, ProcedureView newbugview)
   {
   
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
   addJTextFieldVariable2DocListener(
             new DocumentListener()
           {

       public void changedUpdate(DocumentEvent documentEvent) {
      action.setVariable2(JTextFieldVariable2.getText());
      }

      public void insertUpdate(DocumentEvent documentEvent) {
      action.setVariable2(JTextFieldVariable2.getText());
      }
      public void removeUpdate(DocumentEvent documentEvent) {
     action.setVariable2(JTextFieldVariable2.getText());
      }
      }
                 );
   } 
 
        
}
