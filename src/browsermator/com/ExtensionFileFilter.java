/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.io.File;

/**
 *
 * @author pcalkins
 */
/**
 * Inherited FileFilter class to facilitate reuse when
 * multiple file filter selections are required. For example
 * purposes, I used a static nested class, which is defined
 * as below as a member of our original FileChooserExample
 * class.
 */
public class ExtensionFileFilter extends javax.swing.filechooser.FileFilter {

    private java.util.List<String> extensions;
    private String description;

    public ExtensionFileFilter(String[] exts, String desc) {
        if (exts != null) {
            extensions = new java.util.ArrayList<String>();

            for (String ext : exts) {

                // Clean array of extensions to remove "."
                // and transform to lowercase.
                extensions.add(
                    ext.replace(".", "").trim().toLowerCase()
                );
            }
        } // No else need; null extensions handled below.

        // Using inline if syntax, use input from desc or use
        // a default value.
        // Wrap with an if statement to default as well as
        // avoid NullPointerException when using trim().
        description = (desc != null) ? desc.trim() : "Custom File List";
    }

    // Handles which files are allowed by filter.
    @Override
    public boolean accept(File f) {
    
        // Allow directories to be seen.
        if (f.isDirectory()) return true;

        // exit if no extensions exist.
        if (extensions == null) return false;
		
        // Allows files with extensions specified to be seen.
        for (String ext : extensions) {
            if (f.getName().toLowerCase().endsWith("." + ext))
                return true;
        }

        // Otherwise file is not shown.
        return false;
    }

    // 'Files of Type' description
    @Override
    public String getDescription() {
        return description;
    }
}
