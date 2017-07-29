/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.Component;

/**
 *
 * @author Pete
 */
public class ActionSettings {
    Component comp;
    double weightX;
    int width;
    int gridx;
    int GridBagAnchor;
    ActionSettings(Component comp, int gridx, int width, double weightx, int in_anchor)
    {
        this.gridx = gridx;
        this.comp = comp;
        this.width = width;
        this.weightX= weightx;
        this.GridBagAnchor = in_anchor;
    }
}
