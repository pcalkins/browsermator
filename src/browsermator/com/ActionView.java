package browsermator.com;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

public abstract class ActionView implements Listenable, Initializable{

     int index;
     int bugindex;
     JPanel JPanelAction;
          String ActionType;
   JPasswordField JTextFieldPassword;
   JTextField JTextFieldVariable1;
   JTextField JTextFieldVariable2;
   JLabel JLabelVariable1;
   JLabel JLabelVariable2;

   JLabel JLabelPassFail;

   JButton JButtonOK;
   JButton JButtonDelete;
   JLabel JLabelIndex;
   JButton JButtonMoveUp;
   JButton JButtonMoveDown;
   JCheckBox JCheckBoxBoolVal1;
   JButton JButtonBrowseForFile;
   ActionView()
   {

     
      this.JPanelAction = new JPanel();
      this.JLabelPassFail = new JLabel("");
      this.JTextFieldVariable1 =  new JTextField("", 15);
      this.JTextFieldVariable2 = new JTextField("", 15);
      this.JTextFieldPassword = new JPasswordField("", 15);
      this.JLabelVariable1 = new JLabel("Generic");
      this.JLabelVariable2 = new JLabel("Generic");
      this.JCheckBoxBoolVal1 = new JCheckBox("Press Enter Key");
    this.JButtonOK = new JButton("Disable");
  this.JButtonOK.setActionCommand("Update");
    this.JButtonDelete = new JButton("Remove");
       this.JButtonMoveDown = new JButton("\\/");
       this.JButtonMoveUp = new JButton("/\\");
String stringactionindex = Integer.toString(this.index+1);
        String stringbugindex = Integer.toString(this.bugindex+1);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
      this.JLabelIndex = new JLabel(bugdashactionindex);
    
      this.JPanelAction.add(this.JLabelIndex);
             this.JPanelAction.add(this.JButtonMoveUp);
             this.JPanelAction.add(this.JButtonMoveDown);
 
         
   }
     public void setActionFieldToDataColumn (int field_number, int columnindex, String selected_name)
     {
         
         String data_field_string = "[dataloop-field-start]"+field_number+","+columnindex+"," + selected_name+"[dataloop-field-end]";
         switch (field_number)
         {
             
             case 1:
                 JTextFieldVariable1.setText(data_field_string);
                 break;
             case 2:
                 JTextFieldVariable2.setText(data_field_string);
                 break;
             case 3:
                 JTextFieldPassword.setText(data_field_string);
                 break;
                 
         }
              
     }
     public void setActionViewIndex (int newindex)
     {
         this.index = newindex;
     }

     public void addJTextFieldFocusListener(FocusListener focuslistener)
     {
        JTextFieldVariable1.addFocusListener(focuslistener);
     }
     public void addJTextField2FocusListener(FocusListener focuslistener)
     {
         JTextFieldVariable2.addFocusListener(focuslistener);
     }
      public void addJCheckBoxBoolVal1ActionListener(ActionListener actlistener)
     {
         JCheckBoxBoolVal1.addActionListener(actlistener);
     }
      public void addJTextFieldPasswordActionListener(ActionListener actlistener)
     {
         JTextFieldPassword.addActionListener(actlistener);
     }
      public void addJTextFieldVariable1ActionListener(ActionListener actlistener)
     {
         JTextFieldVariable1.addActionListener(actlistener);
     }
         public void addJTextFieldVariable1DocListener(DocumentListener doclistener)
     {
         JTextFieldVariable1.getDocument().addDocumentListener(doclistener);
     }
         public void addJTextFieldVariable2DocListener(DocumentListener doclistener)
         {
             JTextFieldVariable2.getDocument().addDocumentListener(doclistener);
         }
           public void addJTextFieldPasswordDocListener(DocumentListener doclistener)
         {
             JTextFieldPassword.getDocument().addDocumentListener(doclistener);
         }
       public void addJButtonOKActionActionListener(ActionListener listener)
       {
       JButtonOK.addActionListener(listener);
   
       }
       
        public void addJButtonBrowseForFile(ActionListener listener)
       {
           JButtonBrowseForFile.addActionListener(listener);
       }
       public void addJButtonMoveUpActionListener(ActionListener listener)
       {
           JButtonMoveUp.addActionListener(listener);
       }
       public void addJButtonMoveDownActionListener(ActionListener listener)
       {
           JButtonMoveDown.addActionListener(listener);
       }
          public void addJButtonDeleteActionActionListener(ActionListener listener)
       {
       JButtonDelete.addActionListener(listener);
   
       } 
 
       public void UpdateActionView()
       {
      
   this.JTextFieldVariable1.setEnabled(false);
   this.JTextFieldVariable2.setEnabled(false);
   this.JTextFieldVariable1.setEditable(false);
   this.JTextFieldVariable2.setEditable(false);
   this.JTextFieldPassword.setEnabled(false);
   this.JTextFieldPassword.setEditable(false);
   this.JButtonOK.setText("Enable");
   this.JButtonOK.setActionCommand("Edit");
       }
       public void EditActionView()
       {
 
    this.JTextFieldVariable1.setEnabled(true);
   this.JTextFieldVariable2.setEnabled(true);
   this.JTextFieldVariable1.setEditable(true);
   this.JTextFieldVariable2.setEditable(true);
   this.JTextFieldPassword.setEnabled(true);
   this.JTextFieldPassword.setEditable(true);
   this.JButtonOK.setText("Disable");
   this.JButtonOK.setActionCommand("Update");
  
       }
       @Override
       public void SetVars(String Variable1, String Variable2, String Password, Boolean BoolVal1)
       {
         this.JTextFieldVariable1.setText(Variable1);
         this.JTextFieldVariable2.setText(Variable2);
         this.JTextFieldPassword.setText(Password);
         
         if (BoolVal1)
         {
             this.JCheckBoxBoolVal1.setSelected(true);
         }
       }
       public void SetIndexes(int bugindex, int actionindex)
       {
  
   
           this.index = actionindex;
           this.bugindex = bugindex;
            String stringactionindex = Integer.toString(this.index+1);
        String stringbugindex = Integer.toString(this.bugindex+1);
        String bugdashactionindex = stringbugindex + "-" + stringactionindex;
           this.JLabelIndex.setText(bugdashactionindex);
           
       }
   @Override
   public void AddListeners(Action action, SeleniumTestTool Window, Procedure newbug, ProcedureView newbugview)
   {
   
   this.addJButtonMoveDownActionListener((ActionEvent evt) -> {
       Window.MoveAction(Window, newbug, newbugview, action.index, 1);
      
   });
     this.addJButtonMoveUpActionListener((ActionEvent evt) -> {
         Window.MoveAction(Window, newbug, newbugview, action.index, -1);
   });

                        this.addJButtonDeleteActionActionListener((ActionEvent evt) -> {
                          Window.DeleteAction(newbug, newbugview, action.index);
                            Window.UpdateDisplay();
   });
           

   } 


  public void AddLoopListeners(Action action, SeleniumTestTool Window, Procedure newbug, ProcedureView newbugview)
   {

if (newbugview.myTable!=null)
{
  

     addJTextFieldFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            
            
                newbugview.ShowFieldInstructions(true, 1, newbugview.index, action.index);
                newbugview.setLastSelectedField (1, newbugview.index, action.index);
            }

            @Override
            public void focusLost(FocusEvent e) {
            newbugview.ShowFieldInstructions(false, 1, newbugview.index, action.index);
               newbugview.clearLastSelectedValues();
            }
        });
      addJTextField2FocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            
            
                newbugview.ShowFieldInstructions(true, 2, newbugview.index, action.index);
                newbugview.setLastSelectedField (2, newbugview.index, action.index);
            }

            @Override
            public void focusLost(FocusEvent e) {
            newbugview.ShowFieldInstructions(false, 2, newbugview.index, action.index);
               newbugview.clearLastSelectedValues();
            }
        });
   }
}
}