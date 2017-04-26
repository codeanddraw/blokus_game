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
import java.util.logging.Level;
import java.util.logging.Logger;
import splashdemo.HighScores.SortableEntry;

/**
 * <h1>BlokusWindow methods</h1>
 *
 * @author Nisha Chaube
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

    private JPanel rotateButtons;
    private JPanel flipAndGiveUpButtons;

    private JButton rotateLeft;
    private JButton rotateRight;
    private JButton flip;
    //variables for the menu bar
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem save;
    private JMenuItem load;
    private JMenuItem quit;

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

    public BlokusWindow(String fileName) {

        super("Blokus");
        board = new BlokusBoard();
        players = new BlokusPlayer[4];
        loadGame(fileName);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeGUI();
        startNewTurn();
    }

    /**
     * This is the saveGame method which saves the current game.
     *
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
        outStream.writeInt(turn);

        board.saveGrid(outStream);
    }

    private void saveHighScores(String fileName) {
        try {
            FileOutputStream outFile = new FileOutputStream(fileName);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(BlokusWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This is the loadGame method which loads the previously saved game.
     *
     * @param fileName
     * @throws IOException
     */
    private void loadGame(String fileName) {
        try {
            initializeGUI();
            FileInputStream inFile = new FileInputStream(fileName);
            ObjectInputStream inStream = new ObjectInputStream(inFile);
            players[0] = (BlokusPlayer) inStream.readObject();
            players[1] = (BlokusPlayer) inStream.readObject();
            players[2] = (BlokusPlayer) inStream.readObject();
            players[3] = (BlokusPlayer) inStream.readObject();
            this.turn = inStream.readInt();

            board.loadGrid(inStream);
            piecesPanel.repaint();

        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFound exception thrown: " + ex.getMessage());
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFound exception thrown: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException exception thrown: " + ex.getMessage());
        }

    }

    private boolean isPlayerTurn() {
        return (turn == 0 || turn == 2);
    }

    private boolean isCPUTurn() {
        return (turn == 1 || turn == 3);
    }

    /**
     * This is the initializeGUI method which initializes the GUI by
     * implementing various listeners.
     */
    private void initializeGUI() {

        class BoardKeyListener implements KeyListener {

            @Override
            public void keyTyped(KeyEvent e) {
                // Unneeded
            }

            /**
             * If a key is pressed while the window is in focus, rotate or flip
             * piece.
             *
             * @param e key event representing key pressed
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (isPlayerTurn()) {
                    int keyCode = e.getKeyCode();
                    if (keyCode == KeyEvent.VK_LEFT) {
                        rotateCounterClockwise();
                    } else if (keyCode == KeyEvent.VK_RIGHT) {
                        rotateClockwise();
                    } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP) {
                        flipPiece();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Nothing happens
            }
        }

        class BoardClickListener implements MouseListener, MouseMotionListener {

            //overridden abstract methods
            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseDragged(MouseEvent e) {

            }

            public void mouseWheelMoved(MouseEvent e) {
            }

            //flipping turns on mouse clicks
            public void mouseClicked(MouseEvent e) {

                try {

                    //if player turn
                    if (isPlayerTurn()) {
                        //place block
                        /*
                        NOTE: NullPointerExceptions seem to originate inconsistently in this block.
                         */
                        if (board == null) {
                            System.out.println("board is null");
                        }
                        BlokusPiece piece = (players[turn].pieces.get(pieceIndex));
                        if (piece == null) {
                            System.out.println("piece is null");

                        }

                        int selx = selected.x - BlokusPiece.PIECESIZE / 2; // THIS IS CAUSING EXCEPTION
                        int sely = selected.y - BlokusPiece.PIECESIZE / 2;
                        board.placePiece(players[turn].pieces.get(pieceIndex), selected.x - BlokusPiece.PIECESIZE / 2,
                                selected.y - BlokusPiece.PIECESIZE / 2, players[turn].firstMove);
                        drawBoard();

                        //remove piece
                        players[turn].pieces.remove(pieceIndex);
                        players[turn].firstMove = false;
                        //if you use all your blocks quit
                        players[turn].canPlay = !players[turn].pieces.isEmpty();
                        startNewTurn();
                        //if computer turn
                    } else if (isCPUTurn()) {

                        outerloop:
                        //try 21 different random blocks
                        for (int j = 0; j <= 21; j++) {
                            maxBlocks = players[turn].pieces.size();
                            int randomNum = ThreadLocalRandom.current().nextInt(0, maxBlocks);
                            for (int x = 0; x <= 19; x++) {
                                for (int y = 0; y <= 19; y++) {
                                    try {
                                        // try and place block on every square
                                        board.placePiece(players[turn].pieces.get(randomNum), x - BlokusPiece.PIECESIZE / 2, y - BlokusPiece.PIECESIZE / 2,
                                                players[turn].firstMove);
                                        drawBoard();
                                        players[turn].pieces.remove(randomNum);
                                        players[turn].firstMove = false;
                                        players[turn].canPlay = !players[turn].pieces.isEmpty();

                                        break outerloop;

                                    } catch (IllegalMoveException ex) {
                                    }
                                }
                            }
                            // if computer has no move then quit
                            if (j == 20) {
                                players[turn].canPlay = false;
                            }
                        }

                        startNewTurn();
                    }
                } catch (IllegalMoveException ex) {
                    displayMessage(ex.getMessage(), "Wrong Move!");
                }

            }

            public void mouseExited(MouseEvent e) {
                selected = null;
                board.resetOverlayGrid();
                drawBoard();
            }

            public void mouseMoved(MouseEvent e) {
                if (isPlayerTurn()) {
                    Point p = board.getCoordinates(e.getPoint(), BlokusBoard.CONSOLERESOLUTION);
                    if (!p.equals(selected)) {
                        selected = p;
                        board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
                        drawBoard();
                    }
                }
            }
        }

        class exitListener implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                if (isPlayerTurn()) {
                    players[turn].canPlay = false;
                    startNewTurn();
                    
                }
            }
        }

        class rotateLeftListener implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                if (isPlayerTurn()) {
                    try {
                        rotateCounterClockwise();
                    } catch (NullPointerException npe) {
                        // It's fine if findUser throws a NPE
                    }

                }
            }

        }

        class rotateRightListener implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                if (turn == 0 || turn == 2) {
                    try {
                        rotateClockwise();
                    } catch (NullPointerException npe) {
                        // It's fine if findUser throws a NPE
                    }

                }
            }

        }

        class flipListener implements ActionListener {

            public void actionPerformed(ActionEvent event) {
                if (turn == 0 || turn == 2) {
                    try {
                        flipPiece();
                    } catch (NullPointerException npe) {
                        System.out.println("FlipListener caught: " + npe);
                        // It's fine if findUser throws a NPE
                    }

                }
            }

        }

        mainPanel = new JPanel();
        piecesPanel = new JPanel();
        sidePanel = new JPanel();
        JScrollPane piecePanel = new JScrollPane(piecesPanel);

        rotateButtons = new JPanel(new GridLayout(1, 2));
        flipAndGiveUpButtons = new JPanel(new GridLayout(1, 2));
        rotateLeft = new JButton("⟲");
        rotateRight = new JButton("⟳");

        flip = new JButton("Flip");
        exit = new JButton("Can't Play");

        exit.setPreferredSize(
                new Dimension(BlokusPiece.DEFAULTRESOLUTION, 20));
        exit.addActionListener(
                new exitListener());

        rotateLeft.setPreferredSize(
                new Dimension(BlokusPiece.DEFAULTRESOLUTION, 200));
        rotateLeft.addActionListener(
                new rotateLeftListener());

        rotateRight.setPreferredSize(
                new Dimension(BlokusPiece.DEFAULTRESOLUTION, 20));
        rotateRight.addActionListener(
                new rotateRightListener());

        rotateButtons.setPreferredSize(
                new Dimension(BlokusPiece.DEFAULTRESOLUTION, 20));
        flipAndGiveUpButtons.setPreferredSize(
                new Dimension(BlokusPiece.DEFAULTRESOLUTION, 20));

        piecesPanel.setLayout(
                new BoxLayout(piecesPanel, BoxLayout.PAGE_AXIS));
        piecePanel.getVerticalScrollBar()
                .setUnitIncrement(BlokusPiece.DEFAULTRESOLUTION);
        piecePanel.setPreferredSize(
                new Dimension(BlokusPiece.DEFAULTRESOLUTION - 80, BlokusBoard.CONSOLERESOLUTION - 27));

        flip.setPreferredSize(
                new Dimension(BlokusPiece.DEFAULTRESOLUTION, 20));
        flip.addActionListener(
                new flipListener());

        sidePanel.setLayout(
                new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
        sidePanel.setPreferredSize(
                new Dimension(BlokusPiece.DEFAULTRESOLUTION + 21, BlokusBoard.CONSOLERESOLUTION));
        //menus
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        save = new javax.swing.JMenuItem();

        save.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        }
        );
        load = new javax.swing.JMenuItem();
        load.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuItemActionPerformed(evt);
            }
        });

        quit = new javax.swing.JMenuItem();
        quit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitMenuItemActionPerformed(evt);
            }
        });

        fileMenu.setText("File");
        save.setText("Save Current Game");
        load.setText("Load Previous Game");
        quit.setText("Quit");

        save.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, 0));
        load.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, 0));
        quit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, 0));

        fileMenu.add(save);
        fileMenu.add(load);
        fileMenu.add(quit);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        boardPanel = new JPanel();
        boardImage = new ImageIcon(board.gridLay());

        grid = new JLabel(boardImage);
        BoardClickListener bcl = new BoardClickListener();
        BoardKeyListener bkl = new BoardKeyListener();

        grid.addMouseListener(bcl);
        grid.addMouseMotionListener(bcl);
        this.addKeyListener(bkl);
        this.setFocusable(true);

        //add grid 
        boardPanel.add(grid);
        //add side piece panel
        sidePanel.add(piecePanel);
        //add exit button

        flipAndGiveUpButtons.add(flip);
        flipAndGiveUpButtons.add(exit);
        rotateButtons.add(rotateLeft);
        rotateButtons.add(rotateRight);

        sidePanel.add(rotateButtons);
        sidePanel.add(flipAndGiveUpButtons);
        mainPanel.add(sidePanel);
        mainPanel.add(boardPanel);
        getContentPane().add(mainPanel);
        setVisible(true);
        setFocusable(true);
    }

    /**
     * User picks where to save game, saves if possible.
     *
     * @param evt menu action event
     */
    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser saveDialogue = new JFileChooser();
        int fileChooserResult = saveDialogue.showSaveDialog(this);
        if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
            //System.out.println("Saving file");
            String saveFile = saveDialogue.getSelectedFile().getPath() + ".ser";
            ///System.out.println("saveFile is " + saveFile);
            try {
                this.saveGame(saveFile);
            } catch (IOException ex) {
                System.out.print("Unable to save to file: ");
                System.out.print(ex.getMessage());
            } finally {
                this.gameIsSaved = true;
                //System.out.println("File hopefully saved!");
            }

        } else if (fileChooserResult == JFileChooser.CANCEL_OPTION) {
            System.out.println("Operation cancelled");
        }
    }

    /**
     * User picks game to load, load if possible
     *
     * @param evt menu action event
     */
    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser openDialogue = new JFileChooser();
        int fileChooserResult = openDialogue.showOpenDialog(this);
        if (fileChooserResult == JFileChooser.APPROVE_OPTION) {
            //System.out.println("Opening file");
            String openFile = openDialogue.getSelectedFile().getPath();// + ".ser";
            //System.out.println("openFile is " + openFile);
            this.loadGame(openFile);
        } else if (fileChooserResult == JFileChooser.CANCEL_OPTION) {
            //System.out.println("Load operation cancelled");
        }
    }

    private void quitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        SaveBeforeQuitConfirmationDialogue quitConfirmation = new SaveBeforeQuitConfirmationDialogue(this, true);
        int result = quitConfirmation.showDialog();
        switch (result) {
            case 2:
                saveMenuItemActionPerformed(evt);
                System.exit(0);
                break;
            case 1:
                System.exit(0);
                break;
            case 0:
                System.out.println("lol");
        }
    }

    /**
     * Rotate piece clockwise.
     */
    private void rotateClockwise() {
        players[turn].pieces.get(pieceIndex).rotateClockwise();
        board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
        drawBoard();
    }

    /**
     * Rotate piece counterclockwise.
     */
    private void rotateCounterClockwise() {

        players[turn].pieces.get(pieceIndex).rotateCounterClockwise();
        board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
        drawBoard();
    }

    /**
     * Flip piece upside down.
     */
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
            if (turn == 0 || turn == 2) {
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

        public void mouseWheelMoved(MouseEvent e) {
        }

    }

//to flip turns
    private void startNewTurn() {
        turn++;
        turn %= 4;
        if (isGameOver()) {
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
        if (isPlayerTurn()) {
            drawBorder();
            exit.setEnabled(true);
            flip.setEnabled(true);
            rotateLeft.setEnabled(true);
            rotateRight.setEnabled(true);

        } else {
            clearBorder();
            exit.setEnabled(false);
            flip.setEnabled(false);
            rotateLeft.setEnabled(false);
            rotateRight.setEnabled(false);

        }
        piecesPanel.repaint();
        pack();
    }

    //check for all player's canPlay values
    private boolean isGameOver() {
        // Comment out the three lines below to restore original functionality, this is to help the game end faster for testing
        if (players[0].canPlay == false && players[1].canPlay == false && players[2].canPlay == false &&players[3].canPlay == false) {
            return true;
        }
        for (int i = 0; i < 4; i++) {

            if (players[i].canPlay) {
                return false;
            }
        }
        return true;
    }
//    
//    private SortableEntry getHighScore() {
//        String maxPlayer = "";
//        Integer maxScore = 0;
//        for (int i = 0; i < this.players.length; i++) {
//            if (players[i].calculateScore() > maxScore) {
//                
//            }
//        }
//    }

    //print score sheet of both players 
    private void gameFinish() {
        StringBuilder stringContent = new StringBuilder();
        stringContent.append("***************Score Sheet*************\n");
        stringContent.append("Blue: ");
        stringContent.append(players[0].calculateScore());
        stringContent.append("\n");

        stringContent.append("Green: ");
        stringContent.append(players[1].calculateScore());
        stringContent.append("\n");

        stringContent.append("Red: ");
        stringContent.append(players[2].calculateScore());
        stringContent.append("\n");

        stringContent.append("Yellow: ");
        stringContent.append(players[3].calculateScore());
        stringContent.append("\n");
        ImageIcon icon = new ImageIcon(BlokusWindow.class
                .getResource("d2.gif"));
        JOptionPane.showMessageDialog(this, stringContent.toString(), "Game Over Now", JOptionPane.INFORMATION_MESSAGE, icon);
        System.exit(0);
    }

}
