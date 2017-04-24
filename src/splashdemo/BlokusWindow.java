package splashdemo;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ThreadLocalRandom;

/**
* <h1>BlokusWindow methods</h1>
* @author  Nisha Chaube
*/
class BlokusWindow extends JFrame {
    private final BlokusBoard board;
    private final BlokusPlayer[] players;
    private int turn = -1;
    private int maxBlocks;
    private int pieceIndex;
    private Point selected;

    //variables for different panels in the the game board 
    private JPanel mainPanel;
    private JPanel sidePanel;
    private JPanel piecesPanel;
    private JPanel boardPanel;
    private JLabel grid;
    private ImageIcon boardImage;
    private JButton exit;

    //variables for the menu bar
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem save;
    private JMenuItem load;

    private boolean gameIsSaved;
   
   /**
   * Default constructor that creates players and initializes the GUI.
   */
    public BlokusWindow() {
        super("Blokus");
        //player 0 and player 1 = human player
        //player 2 and player 4 = computer player
        board = new BlokusBoard();
        players = new BlokusPlayer[4];
        players[0] = new BlokusPlayer(BlokusBoard.BLUECOLOR);
        players[1] = new BlokusPlayer(BlokusBoard.GREENCOLOR);
        players[2] = new BlokusPlayer(BlokusBoard.REDCOLOR);
        players[3] = new BlokusPlayer(BlokusBoard.YELLOWCOLOR);
        //to exit the game window 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeGUI();
        startNewTurn();
    }

   /**
   * This is the saveGame method which saves the current game.
     * @param fileName
     * @throws FileNotFoundException
     * @throws IOException
   */
    private void saveGame(String fileName) throws FileNotFoundException, IOException {
        FileOutputStream outFile = new FileOutputStream(fileName);
        ObjectOutputStream outStream = new ObjectOutputStream(outFile);
        for (BlokusPlayer player : players) {
            outStream.writeObject(player);
        }
        board.saveGrid(outStream);
        outStream.writeInt(turn);
    }

   /**
   * This is the loadGame method which loads the previously saved game.
     * @param fileName
     * @throws IOException
   */
    private void loadGame(String fileName) throws IOException {
        try {
            initializeGUI();
            FileInputStream inFile = new FileInputStream(fileName);
            ObjectInputStream inStream = new ObjectInputStream(inFile);
            players[0] = (BlokusPlayer) inStream.readObject();
            players[1] = (BlokusPlayer) inStream.readObject();
            players[2] = (BlokusPlayer) inStream.readObject();
            players[3] = (BlokusPlayer) inStream.readObject();
            board.loadGrid(inStream);
            this.turn = inStream.readInt();
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFound exception thrown: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFound exception thrown: " + ex.getMessage());
        }

    }
    
   /**
   * This is the initializeGUI method which initializes the GUI by implementing various listeners.
   */
    private void initializeGUI() {
        class BoardClickListener implements MouseListener, MouseMotionListener, MouseWheelListener {
            //overridden abstract methods
            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseDragged(MouseEvent e) {

            }

            //flipping turns on mouse clicks
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    flipPiece();
                } else {
                    try {
                        
                        
                        if (turn == 0 || turn == 2) {
                            board.placePiece(players[turn].pieces.get(pieceIndex), selected.x - BlokusPiece.PIECESIZE / 2,
                                    selected.y - BlokusPiece.PIECESIZE / 2, players[turn].firstMove);
                            drawBoard();
                            players[turn].pieces.remove(pieceIndex);
                            players[turn].firstMove = false;
                            players[turn].canPlay = !players[turn].pieces.isEmpty();
                            startNewTurn();
                        } else if (turn == 1 || turn == 3) {
                            
                            outerloop:
                            for (int j=0; j <= 21; j++){
                                 maxBlocks=players[turn].pieces.size();
                                 int randomNum = ThreadLocalRandom.current().nextInt(0, maxBlocks);   
                                for (int x = 0; x <= 19; x++) {
                                    for (int y = 0; y <= 19; y++) {
                                        try {
                                            board.placePiece(players[turn].pieces.get(randomNum), x - BlokusPiece.PIECESIZE/ 2, y - BlokusPiece.PIECESIZE/ 2,
                                                players[turn].firstMove);
                                            drawBoard();
                                            players[turn].pieces.remove(randomNum);
                                            players[turn].firstMove = false;
                                            players[turn].canPlay = !players[turn].pieces.isEmpty();
                                            
                                            
                                      
                                            break outerloop;
                                            
                                    }   catch (IllegalMoveException ex) {
                                    }
                                }
                            }
                                if(j== 20)
                                            {
                                                players[turn].canPlay = false;
                                            }
                            }
                            
                
                            startNewTurn();
                        }
                    } catch (IllegalMoveException ex) {
                        displayMessage(ex.getMessage(), "Wrong Move!");
                    }
                  
                }
            }

            public void mouseExited(MouseEvent e) {
                selected = null;
                board.resetOverlayGrid();
                drawBoard();
            }

            public void mouseMoved(MouseEvent e) {
                if (turn == 0 || turn == 2) {
                    Point p = board.getCoordinates(e.getPoint(), BlokusBoard.CONSOLERESOLUTION);
                    if (!p.equals(selected)) {
                        selected = p;
                        board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
                        drawBoard();
                    }
                }
            }

            /**
            * This is the mouseWheelMoved method which rotates the piece clockwise or counterclockwise.
              * @param e
            */
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() > 0) {
                    rotateClockwise();
                } else {
                    rotateCounterClockwise();
                }
            }
        }

        class exitListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                players[turn].canPlay = false;
                startNewTurn();
            }
        }

        mainPanel = new JPanel();
        piecesPanel = new JPanel();
        sidePanel = new JPanel();
        JScrollPane piecePanel = new JScrollPane(piecesPanel);
        //quit button for player
        exit = new JButton("I QUIT :<");
  
        piecesPanel.setLayout(new BoxLayout(piecesPanel, BoxLayout.PAGE_AXIS));        
        piecePanel.getVerticalScrollBar().setUnitIncrement(BlokusPiece.DEFAULTRESOLUTION);
        piecePanel.setPreferredSize(new Dimension(BlokusPiece.DEFAULTRESOLUTION - 80, BlokusBoard.CONSOLERESOLUTION - 27));

        exit.setPreferredSize(new Dimension(BlokusPiece.DEFAULTRESOLUTION, 20));
        exit.addActionListener(new exitListener());

        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
        //menus
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        save = new javax.swing.JMenuItem();
        save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        load = new javax.swing.JMenuItem();
        load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuItemActionPerformed(evt);
            }
        });

        fileMenu.setText("File");
        save.setText("Save Current Game");
        load.setText("Load Previous Game");

        fileMenu.add(save);
        fileMenu.add(load);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        boardPanel = new JPanel();
        boardImage = new ImageIcon(board.gridLay());

        grid = new JLabel(boardImage);
        BoardClickListener bcl = new BoardClickListener();
        grid.addMouseListener(bcl);
        grid.addMouseMotionListener(bcl);
        grid.addMouseWheelListener(bcl);

        //add grid
        boardPanel.add(grid);
        //add side piece panel
        sidePanel.add(piecePanel);
        //add exit button
        sidePanel.add(exit);
        mainPanel.add(sidePanel);
        mainPanel.add(boardPanel);
        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser saveDialogue = new JFileChooser();
        int fileChooserResult = saveDialogue.showSaveDialog(this);
        if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
            System.out.println("Saving file");
            String saveFile = saveDialogue.getSelectedFile().getPath() + ".ser";
            System.out.println("saveFile is " + saveFile);
            try {
                this.saveGame(saveFile);
            } catch (IOException ex) {
                System.out.print("Unable to save to file: ");
                System.out.print(ex.getMessage());
            } finally {
                this.gameIsSaved = true;
                System.out.println("File hopefully saved!");
            }

        } else if (fileChooserResult == JFileChooser.CANCEL_OPTION) {
            System.out.println("Operation cancelled");
        }
    }

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser openDialogue = new JFileChooser();
        int fileChooserResult = openDialogue.showOpenDialog(this);
        if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
            System.out.println("Opening file");
            String openFile = openDialogue.getSelectedFile().getPath();// + ".ser";
            System.out.println("openFile is " + openFile);
            try {
                this.loadGame(openFile);
            } catch (IOException ex) {
                System.out.print("Unable to open file: ");
                System.out.print(ex.getCause());
            }
        } else if (fileChooserResult == JFileChooser.CANCEL_OPTION) {
            System.out.println("Load operation cancelled");
        }
    }

    private void rotateClockwise() {
        players[turn].pieces.get(pieceIndex).rotateClockwise();
        board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
        drawBoard();
    }

    private void rotateCounterClockwise() {
        players[turn].pieces.get(pieceIndex).rotateCounterClockwise();
        board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
        drawBoard();
    }

    private void flipPiece() {
        players[turn].pieces.get(pieceIndex).flipOver();
        board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
        drawBoard();
    }

    //draw border
    private void drawBoard() {
        boardImage.setImage(board.gridLay());
        grid.repaint();
    }

    //highlight the border of selected peice
    private void drawBorder() {
        JComponent piece = (JComponent) piecesPanel.getComponent(pieceIndex);
        piece.setBorder(BorderFactory.createLineBorder(Color.red));
    }

    //removes border highlight on unselected piece
    private void clearBorder() {
        JComponent piece = (JComponent) piecesPanel.getComponent(pieceIndex);
        piece.setBorder(BorderFactory.createLineBorder(Color.white));
    }

    //display dialogue boxes 
    private void displayMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    //to select peice type
    private class PieceLabelClickListener implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            if (turn == 0 || turn == 2){
            BlokusPiecePanel bp = (BlokusPiecePanel) e.getComponent();
            clearBorder();
            pieceIndex = bp.pieceIndex;
            drawBorder();
            }
        }

        //need to override abstract methods
        public void mousePressed(MouseEvent e) {

        }

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {

        }
    }

    //to flip turns
    private void startNewTurn() {
        turn++;
        turn %= 4;
        if (hasPlayerQuit()) {
            gameFinish();
        }
        if (!players[turn].canPlay) {
            startNewTurn();
            return;
        }
        piecesPanel.removeAll();
        for (int i = 0; i < players[turn].pieces.size(); i++) {
            BlokusPiecePanel pieceLabel
                    = new BlokusPiecePanel(i, players[turn].pieces.get(i), BlokusPiece.DEFAULTRESOLUTION);
            pieceLabel.addMouseListener(new PieceLabelClickListener());
            pieceLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            piecesPanel.add(pieceLabel);
        }
        pieceIndex = 0;
        drawBorder();
        piecesPanel.repaint();
        pack();
    }

    //check for all player's canPlay values
    private boolean hasPlayerQuit() {
        for (int i = 0; i < 4; i++) {
            if (players[i].canPlay) {
                return false;
            }
        }
        return true;
    }

    //print score sheet of both players 
    private void gameFinish() {
        StringBuilder stringContent = new StringBuilder();
        stringContent.append("***************Score Sheet*************\n");
        stringContent.append("Computer Player 1 :");
        stringContent.append(players[0].calculateScore());
        stringContent.append("\n");

        stringContent.append("Human Player 2   :");
        stringContent.append(players[1].calculateScore());
        stringContent.append("\n");

        stringContent.append("Computer Player 3 :");
        stringContent.append(players[2].calculateScore());
        stringContent.append("\n");

        stringContent.append("Human Player 4    :");
        stringContent.append(players[3].calculateScore());
        stringContent.append("\n");
        ImageIcon icon = new ImageIcon(BlokusWindow.class.getResource("d2.gif"));
        JOptionPane.showMessageDialog(this, stringContent.toString(), "Game Over Now", JOptionPane.INFORMATION_MESSAGE, icon);
        System.exit(0);
    }

}
