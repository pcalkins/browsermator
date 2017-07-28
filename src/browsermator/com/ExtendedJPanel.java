/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 *
 * @author pcalkins
 */
public class ExtendedJPanel extends JPanel implements Scrollable{
 GridBagLayout ExtLayout;
 GridBagConstraints ExtLayoutConstraints;
    ExtendedJPanel()
    {

   ExtLayout = new GridBagLayout();
   ExtLayoutConstraints = new GridBagConstraints();
   this.setLayout(ExtLayout);
//    ActionConstraints.gridx = 1;
//         ActionConstraints.gridy = actionindex;
//         ActionConstraints.gridwidth = 1;
//         ActionConstraints.gridheight = 1;
 
 //  ExtLayoutConstraints.anchor = GridBagConstraints.WEST;
 
 
   
    }
 
    public final void AddToGrid( Component component, int row, int column, int width, int height, double weightx, double weighty, int anchor_value)
     {
         ExtLayoutConstraints.gridx = column;
         ExtLayoutConstraints.gridy = row;
         ExtLayoutConstraints.gridwidth = width;
         ExtLayoutConstraints.gridheight = height;
         ExtLayoutConstraints.weightx = weightx;
         ExtLayoutConstraints.weighty = weighty;
         ExtLayoutConstraints.anchor = anchor_value;
         ExtLayout.setConstraints(component, ExtLayoutConstraints);
         this.add(component);
         this.revalidate();
     }


  @Override
    public Dimension getPreferredScrollableViewportSize() {
        Dimension preferredSize = this.getPreferredSize();
        if (getParent() instanceof JViewport) {
            preferredSize.width += ((JScrollPane) getParent().getParent()).getVerticalScrollBar()
                    .getPreferredSize().width;
        }
        return preferredSize;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return orientation == SwingConstants.HORIZONTAL ? Math.max(visibleRect.width * 9 / 10, 1)
                : Math.max(visibleRect.height * 9 / 10, 1);
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        if (getParent() instanceof JViewport) {
            JViewport viewport = (JViewport) getParent();
            return getPreferredSize().height < viewport.getHeight();
        }
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return true;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return orientation == SwingConstants.HORIZONTAL ? Math.max(visibleRect.width / 10, 1)
                : Math.max(visibleRect.height / 10, 1);
    }
}
