/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Toolkit;
import java.util.Timer;
import p2p.DomeinController;

/**
 *
 * @author Jimmy
 */
public class StartupGui {
    
    private static HoofdFrame hoofdFrame;
    private static LoadingScreen loadScreen;
    
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HoofdFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoofdFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoofdFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoofdFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
      
        
        
//        loadScreen = new LoadingScreen();
//        loadScreen.setVisible(true);
//        loadScreen.setLocationRelativeTo(null);
//        loadScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("network-share-icon.png"));
//        loadScreen.removeNotify();
//        loadScreen.setUndecorated(true);
//        loadScreen.addNotify();
        
        
        
        
        
        
        hoofdFrame = new HoofdFrame(new DomeinController());
        hoofdFrame.setVisible(true);
        hoofdFrame.setLocationRelativeTo(null);
        hoofdFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("network-share-icon.png"));
        
    }   
}
