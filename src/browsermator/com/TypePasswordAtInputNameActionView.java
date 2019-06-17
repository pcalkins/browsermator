
package browsermator.com;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TypePasswordAtInputNameActionView extends ActionView 
{
   TypePasswordAtInputNameActionView()
   {
   
       this.JLabelVariable1 = new JLabel("At Input Name:");
    this.JLabelVariable2 = new JLabel("Type password:");
    this.JTextFieldPassword = new JPasswordField("", 15);

        this.JCheckBoxBoolVal1 = new JCheckBox("Press Enter Key");
  theseActionSettings.add(new ActionSettings(JLabelVariable1, 2, 1, 0.0, GridBagConstraints.WEST));
      theseActionSettings.add(new ActionSettings(JTextFieldVariable1, 3, 1, 0.5, GridBagConstraints.WEST));
       theseActionSettings.add(new ActionSettings(JLabelVariable2, 4, 1, 0.0, GridBagConstraints.WEST));
        theseActionSettings.add(new ActionSettings(JTextFieldPassword, 5, 1, 0.5, GridBagConstraints.WEST));
         theseActionSettings.add(new ActionSettings(JCheckBoxBoolVal1, 6, 2, 0.0, GridBagConstraints.WEST));
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
   
     addJTextFieldPasswordDocListener(
             new DocumentListener()
           {

       public void changedUpdate(DocumentEvent documentEvent) {
  STAppFrame.saveState();
           String Password="";
     char[] temp;
     temp = JTextFieldPassword.getPassword();
     for (int x = 0; x<temp.length; x++)
     {
         Password = Password + temp[x];
     }
   //   try
   //   {
   //      Password = Protector.encrypt(Password);
          action.setVariable2(Password);
    
   //   }
   //   catch (Exception e)
   //   {
   //    System.out.println("Encrypt error: " + e.toString());
    //      action.setVariable2(Password);
    //  }
      }

      public void insertUpdate(DocumentEvent documentEvent) {
      String Password="";
     char[] temp;
     temp = JTextFieldPassword.getPassword();
    for (int x = 0; x<temp.length; x++)
     {
         Password = Password + temp[x];
     }
  //    try
  //    {
  //       Password = Protector.encrypt(Password);
          action.setVariable2(Password);
  //    }
    //  catch (Exception e)
    //  {
    //   System.out.println("Encrypt error: " + e.toString());
    //      action.setVariable2(Password);
    //  }
      }
      public void removeUpdate(DocumentEvent documentEvent) {
  STAppFrame.saveState();
          String Password="";
     char[] temp;
     temp = JTextFieldPassword.getPassword();
       for (int x = 0; x<temp.length; x++)
     {
         Password = Password + temp[x];
     }
    //  try
    //  {
    //     Password = Protector.encrypt(Password);
    //      action.setVariable2(Password);
    //  }
    //  catch (Exception e)
    //  {
    //   System.out.println("Encrypt error: " + e.toString());
          action.setVariable2(Password);
    //  }
    //  }
      }
           }
                 );
   }    
  
    
}
