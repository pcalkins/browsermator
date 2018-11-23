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
 MainAppFrame mainAppFrame;
 STAppController mainAppController;
 SeleniumTestTool STAppFrame;
 SeleniumTestToolData STAppData;
 boolean isSaveAs;
 boolean isFlatten;
 int calling_MDI_Index;
    public SaveFileThread(STAppController in_mainAppData, MainAppFrame in_mainAppFrame, SeleniumTestTool STAppFrame, SeleniumTestToolData STAppData, boolean isSaveAs, boolean isFlatten, int calling_MDI_Index)
 {
   this.mainAppFrame = in_mainAppFrame;
   this.mainAppController = in_mainAppData;
   this.STAppFrame = STAppFrame;
   this.STAppData = STAppData;
   this.isSaveAs = isSaveAs;
   this.isFlatten = isFlatten;
   this.calling_MDI_Index = calling_MDI_Index;
 }
    @Override 
public String doInBackground()
 {
   if (calling_MDI_Index == -1)
   {
       mainAppController.Navigator.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
                     mainAppController.SaveFile(STAppFrame, STAppData, isSaveAs, isFlatten, calling_MDI_Index);
            
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
  mainAppController.Navigator.setCursor(Cursor.getDefaultCursor());   
   }
      else
   {
     STAppFrame.setCursor(Cursor.getDefaultCursor());
   }
     if (isFlatten)
     {
         STAppFrame.setFlattenFileButtonName ("Flatten to New File");
         
     }
     else
     {
         mainAppController.RefreshCleanState(STAppData);
     }
     
 }
 @Override
 protected void process ( List <Integer> bugindex)
 {
     
 }


}
