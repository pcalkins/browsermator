/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Cursor;

import java.util.List;

import javax.swing.SwingWorker;


/**
 *
 * @author pcalkins
 */
public class SaveFileThread extends SwingWorker<String, Integer>{
 STAppController mainApp;
 SeleniumTestTool STAppFrame;
 boolean isSaveAs;
 boolean isFlatten;
 int calling_MDI_Index;
    public SaveFileThread(STAppController mainApp, SeleniumTestTool STAppFrame, boolean isSaveAs, boolean isFlatten, int calling_MDI_Index)
 {
   this.mainApp = mainApp;  
   this.STAppFrame = STAppFrame;
   this.isSaveAs = isSaveAs;
   this.isFlatten = isFlatten;
   this.calling_MDI_Index = calling_MDI_Index;
 }
    @Override 
public String doInBackground()
 {
   if (calling_MDI_Index == -1)
   {
       mainApp.Navigator.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
   }
   else
   {
     STAppFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
   }
     if (isFlatten)
     {
         STAppFrame.setFlattenFileButtonName("Flattenning...");
     }
     try
                      {
                     mainApp.SaveFile(STAppFrame, isSaveAs, isFlatten, calling_MDI_Index);
            
                      }
                      catch (Exception e)
                      {
                        System.out.println("Save Changes exception:" + e.toString());  
                      
                      } 
     return "done";
 }
@Override
 protected void done()
 {
     if (calling_MDI_Index == -1)
   { 
  mainApp.Navigator.setCursor(Cursor.getDefaultCursor());   
   }
      else
   {
     STAppFrame.setCursor(Cursor.getDefaultCursor());
   }
     if (isFlatten)
     {
         STAppFrame.setFlattenFileButtonName("Flattenning...");
         
     }
     else
     {
         mainApp.RefreshCleanState(STAppFrame);
     }
     
 }
 @Override
 protected void process ( List <Integer> bugindex)
 {
     
 }


}
