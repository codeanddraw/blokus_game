package splashdemo;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

/**
* <h1>LoadGame methods: the initial loading screen</h1>
* @author  Nisha Chaube
*/
public class LoadGame {
    public static SplashScreen loadingScreen;
    public static Double loadingTextArea;
    public static Double loadingProgressArea;
    public static Graphics2D loadingGraphics;
    public static Font myfont;

    public static void loadingMethod() {
        loadingScreen = SplashScreen.getSplashScreen();
        if (loadingScreen != null) {
            Dimension dim = loadingScreen.getSize();
            int ht = dim.height;
            int wd = dim.width;
            //Placing textArea and progressArea
            loadingTextArea = new Rectangle2D.Double(15, ht * 0.7, wd * 0.4, 30);
            loadingProgressArea = new Rectangle2D.Double(15, ht * 0.85, wd * 0.4, 25);
            // create the Graphics environment for drawing status info
            loadingGraphics = loadingScreen.createGraphics();
            //Setting font for text in status info
            myfont = new Font("TimesRoman", Font.BOLD, 14);
            loadingGraphics.setFont(myfont);
        }
    }

    public static void loadingText(String string) {
        if (loadingScreen != null) {
            
            //Color for the background of textArea
            loadingGraphics.setPaint(Color.red);
            loadingGraphics.fill(loadingTextArea);
            
            //Color of the text
            loadingGraphics.setPaint(Color.WHITE);
            loadingGraphics.drawString(string, (int) loadingTextArea.getX() + 10, (int) loadingTextArea.getY() + 20);
            loadingScreen.update();
        }
    }

    public static void loadingProgress(int prog) {
        if (loadingScreen != null) {
            
            //Color for the background of progress Area
            loadingGraphics.setPaint(Color.LIGHT_GRAY);
            loadingGraphics.fill(loadingProgressArea);
            
            //Color for the border of progress Area
            loadingGraphics.setPaint(Color.BLUE);
            loadingGraphics.draw(loadingProgressArea);
            int x = (int) loadingProgressArea.getMinX();
            int y = (int) loadingProgressArea.getMinY();
            int width = (int) loadingProgressArea.getWidth();
            int height = (int) loadingProgressArea.getHeight();
            int doneProg = prog * width / 50;

            //Color for the total progress done
            loadingGraphics.setPaint(Color.BLUE);
            loadingGraphics.fillRect(x, y + 1, doneProg, height);
            loadingScreen.update();
        }
    }

    //driver method
    public static void main(String[] args) {
        loadingMethod();
        mainMethod();
        if (loadingScreen != null) {
            loadingScreen.close();
        }
    }
    
    //called by main method
    public static void mainMethod() {
        for (int i = 1; i <= 5; i++) {
            loadingText("Loading Game resources " + i);
            loadingProgress(i * 10);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Welcome().setVisible(true);
            }
        });
    }
}
