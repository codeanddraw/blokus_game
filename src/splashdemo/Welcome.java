package splashdemo;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * <h1>Welcome Screen</h1>
 *
 * @author Nisha Chaube
 */
public class Welcome extends javax.swing.JFrame {

    public Welcome() {
        initComponents();
    }
    static JFrame jf = new JFrame("Game");
    int x = 0, y = 510;

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Font font = new Font("Courier", Font.BOLD + Font.PLAIN, 14);
        g2.setFont(font);
        g2.setColor(Color.white);
        g2.drawString("Copyright @cs205.Ltd: Nisha, David, Bill", x, y);

        try {
            Thread.sleep(500); // Probably causing lag
        } catch (Exception ex) {
        }
        x += 10;
        if (x > this.getWidth()) {
            x = 0;
        }
        repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startGameButton = new javax.swing.JButton();
        howToPlayButton = new javax.swing.JButton();
        mastermindsButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        loadMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        startGameButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/splashdemo/b1.png"))); // NOI18N
        startGameButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        startGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startGameButtonActionPerformed(evt);
            }
        });
        getContentPane().add(startGameButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 130, 120));

        howToPlayButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/splashdemo/b3.png"))); // NOI18N
        howToPlayButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        howToPlayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                howToPlayButtonActionPerformed(evt);
            }
        });
        getContentPane().add(howToPlayButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 90, 90));

        mastermindsButton.setForeground(new java.awt.Color(0, 0, 51));
        mastermindsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/splashdemo/b4.png"))); // NOI18N
        mastermindsButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        mastermindsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mastermindsButtonActionPerformed(evt);
            }
        });
        getContentPane().add(mastermindsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 140, 40));

        jLabel2.setBackground(new java.awt.Color(0, 0, 102));
        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tired? Go Home");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 370, 120, 30));

        jLabel3.setBackground(new java.awt.Color(0, 0, 102));
        jLabel3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Masterminds");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 370, 130, 30));

        jLabel4.setBackground(new java.awt.Color(0, 0, 102));
        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Start Game");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 110, 20));

        jLabel5.setBackground(new java.awt.Color(0, 0, 102));
        jLabel5.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Confused? Read the rules");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, -1, 40));

        exitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/splashdemo/b2.png"))); // NOI18N
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        getContentPane().add(exitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 80, 90));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/splashdemo/pic1.jpeg"))); // NOI18N
        jLabel1.setText("\\");
            getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 800, 520));

            jMenu1.setText("File");

            loadMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, 0));
            loadMenuItem.setText("Load Previous Game");
            loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    loadMenuItemActionPerformed(evt);
                }
            });
            jMenu1.add(loadMenuItem);

            jMenuBar1.add(jMenu1);

            setJMenuBar(jMenuBar1);

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void startGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startGameButtonActionPerformed
        BlokusWindow run = new BlokusWindow();
        this.setVisible(false);
        //jf.setVisible(true);
    }//GEN-LAST:event_startGameButtonActionPerformed

    private void howToPlayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_howToPlayButtonActionPerformed

        try {
            //to open a pdf of rules
            File pdfFile = new File("C:/Users/Ayushi_chaubey/Desktop/soft final project/selected imgs/Blokus.pdf");
            if (pdfFile.exists()) {

                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(pdfFile);
                } else {
                    System.out.println("Awt Desktop is not supported!");
                }

            } else {
                System.out.println("File doesn't exist!");
            }

            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }//GEN-LAST:event_howToPlayButtonActionPerformed


    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void mastermindsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mastermindsButtonActionPerformed
        new Masterminds().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_mastermindsButtonActionPerformed

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadMenuItemActionPerformed
        JFileChooser openDialogue = new JFileChooser();
        int fileChooserResult = openDialogue.showOpenDialog(this);
        if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
            String openFile = openDialogue.getSelectedFile().getPath();// + ".ser";
            BlokusWindow run = new BlokusWindow(openFile);
            this.setVisible(false);

        } else if (fileChooserResult == JFileChooser.CANCEL_OPTION) {
        }
    }//GEN-LAST:event_loadMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Welcome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Welcome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Welcome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Welcome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jf.setSize(700, 200);
        jf.add(new Welcome());
        jf.setVisible(true);

        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Welcome().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitButton;
    private javax.swing.JButton howToPlayButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JButton mastermindsButton;
    private javax.swing.JButton startGameButton;
    // End of variables declaration//GEN-END:variables
}
