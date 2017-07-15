/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import javax.swing.DefaultDesktopManager;
import javax.swing.JInternalFrame;
import javax.swing.UIManager;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.util.HashMap;
/**
 *
 * @author pcalkins
 */
public class browsermatorDesktopManager extends DefaultDesktopManager
{
 
 HashMap<JInternalFrame, String> FrameHashMap;

@Override
  public void deiconifyFrame(JInternalFrame f) {
    super.deiconifyFrame(f);
    super.activateFrame(f);
    try
    {
    f.setSelected(true);
    }
    catch (Exception e)
    {
        System.out.println ("Exception when unminizing/selecting frame: " + e.toString());
    }
   
  }  
 
    public void iconifyFrame(JInternalFrame f) {
 //  f.addInternalFrameListener(ifl);
 
  int minWidth = computeMinFrameWidth();
  int minHeight = computeMinFrameHeight();

  int desiredWidth = computeDesktopIconWidth(minWidth, f.getTitle());
  // UIManager.put("DesktopIcon.width", desiredWidth);
  int desiredHeight = computeDesktopIconHeight(minHeight, f.getTitle());
    super.iconifyFrame(f);   
      JInternalFrame.JDesktopIcon icon = f.getDesktopIcon();
    icon.setSize(desiredWidth, desiredHeight);
   super.activateFrame(f);
   
 // int desktopIconWidth = UIManager.getInt("DesktopIcon.width");
  //    System.out.println("desktopIconWidth = " + desiredWidth + " Title : " + f.getTitle());
 
   try
   {
   f.setSelected(false);
   } 
   catch (Exception e)
    {
        System.out.println ("Exception when minizing/unselecting frame: " + e.toString());
    }
   super.deactivateFrame(f);
  }
      private int computeMinFrameWidth()
    {
        JInternalFrame f = new JInternalFrame("", true, true, true, true);
        f.pack();
        Rectangle r = f.getBounds();
        f.dispose();
        return r.width;
    }
            private int computeMinFrameHeight()
    {
        JInternalFrame f = new JInternalFrame("", true, true, true, true);
        f.pack();
        Rectangle r = f.getBounds();
        f.dispose();
        return r.height;
    }
        private int computeDesktopIconHeight(int minHeight, String title)
    {
        FontRenderContext frc = new FontRenderContext(null,false, false);
        Font font = UIManager.getFont("InternalFrame.titleFont");
                    // default font = ("dialog", Font.BOLD, 12)
        int maxTitleHeight = 0;
   
            Rectangle2D r = new TextLayout(title, font, frc).getBounds();
            int textHeight = (int)r.getHeight();
            if(textHeight > maxTitleHeight)
                maxTitleHeight = textHeight;
      
        int totalHeight = minHeight + maxTitleHeight;
        return totalHeight;
    }
      private int computeDesktopIconWidth(int minWidth, String title)
    {
        FontRenderContext frc = new FontRenderContext(null,false, false);
        Font font = UIManager.getFont("InternalFrame.titleFont");
                    // default font = ("dialog", Font.BOLD, 12)
        int maxTitleWidth = 0;
   
            Rectangle2D r = new TextLayout(title, font, frc).getBounds();
            int textWidth = (int)r.getWidth();
            if(textWidth > maxTitleWidth)
                maxTitleWidth = textWidth;
      
        int totalWidth = minWidth + maxTitleWidth;
        return totalWidth;
    }
        InternalFrameAdapter ifl = new InternalFrameAdapter()
    {
        public void internalFrameIconified(InternalFrameEvent e)
        {
            JInternalFrame iframe = e.getInternalFrame();
            JInternalFrame.JDesktopIcon icon = iframe.getDesktopIcon();
          
           
        }
    };
}
