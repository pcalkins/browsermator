/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browsermator.com;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.SAVE_DIALOG;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author pcalkins
 */
public class BrowserMatorReport {
JFrame ReportFrame;
JEditorPane HTMLPane;
JButton jButtonSaveFile;
String report;
BorderLayout thisLayout;
JPanel mainPanel;
    public BrowserMatorReport (String report)
 {
   jButtonSaveFile = new JButton("Save as HTML file");
   this.report = report;
   HTMLPane = new JEditorPane("text/html",this.report);
   mainPanel = new JPanel(new BorderLayout());
   ReportFrame = new JFrame("Browsermator Report");
    
    jButtonSaveFile.addActionListener(new ActionListener() {
        
       @Override
        public void actionPerformed (ActionEvent e )
        {
         SaveAsHTMLFile();   
        }
        });

    mainPanel.add(jButtonSaveFile, BorderLayout.NORTH);
    mainPanel.add(HTMLPane, BorderLayout.CENTER);
    ReportFrame.add(mainPanel);
    ReportFrame.setSize(800, 800);
    ReportFrame.setVisible(true);   
 }
    public void SaveAsHTMLFile()
    {
         final JFileChooser fc = new JFileChooser(){
    @Override
    public void approveSelection(){
        
        File f = getSelectedFile();
                String filestring = f.toString();
                
                String[] left_side_of_dot = filestring.split("\\.");
                
                 f = new File(left_side_of_dot[0] + ".html");
       
       
        if(f.exists() && getDialogType() == SAVE_DIALOG){
            int result = JOptionPane.showConfirmDialog(ReportFrame,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
            switch(result){
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                 
                    return;
                case JOptionPane.NO_OPTION:
                    return;
                case JOptionPane.CLOSED_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }
        super.approveSelection();
    
    }
};
File file=null;
   
   
     FileNameExtensionFilter filefilter = new FileNameExtensionFilter("HTML file (*.html)","html");

    fc.setFileFilter(filefilter);
   
        
        fc.setSelectedFile(file);
        
    
int returnVal = fc.showSaveDialog(this.ReportFrame);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                String filestring = file.toString();
                
                String[] left_side_of_dot = filestring.split("\\.");
                
                 file = new File(left_side_of_dot[0] + ".html");
           FileWriter fWriter = null;
BufferedWriter writer = null;
try {
    fWriter = new FileWriter(file);
    writer = new BufferedWriter(fWriter);
    writer.write(report);
    writer.newLine();
     writer.close();
} catch (Exception ex) {
  System.out.println ("Exception saving HTML file: " + ex.toString());
}
            
   
            }
            else
            {
                return;
            }
    }
}
