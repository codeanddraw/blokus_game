package splashdemo;

//import libraries
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class BlokusWindow extends JFrame
   {
      private final BlokusBoard board;
      private final BlokusPlayer[] players;
      private int turn = -1;
      private int pieceIndex;
      private Point selected;
      
      //different panels 
      private JPanel mainPanel;
      private JPanel sidePanel;
      private JPanel piecesPanel;
      private JPanel boardPanel;
      private JLabel grid;
      private ImageIcon boardImage;
      private JButton exit;
      
      //constructor
      public BlokusWindow()
      {
         super("Blokus");
         //player 0 and player 1 is human player
         //player 2 and player 4 is computer player
         board = new BlokusBoard();
         players = new BlokusPlayer[4];
         players[0] = new BlokusPlayer(BlokusBoard.BLUE);
         players[1] = new BlokusPlayer(BlokusBoard.GREEN);
         players[2] = new BlokusPlayer(BlokusBoard.RED);
         players[3] = new BlokusPlayer(BlokusBoard.YELLOW);
         
         //to exit the game window 
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         initializeGUI();
         startNewTurn();
      }
      
      //initialize GUI
      private void initializeGUI()
      {
         class BoardClickListener implements MouseListener, MouseMotionListener, MouseWheelListener
         {
                        
            //abstract methods
            public void mousePressed(MouseEvent e)
            {
               
            }
            
            public void mouseReleased(MouseEvent e)
            {
               
            }
            
            public void mouseEntered(MouseEvent e)
            {
               
            }
             
            public void mouseDragged(MouseEvent e)
            {
            
            }
            
            public void mouseClicked(MouseEvent e)
            {
               if (e.getButton() == MouseEvent.BUTTON3)
               {
                  flipPiece();
               }
               else
               {
                  try
                  {
                     board.placePiece(players[turn].pieces.get(pieceIndex), selected.x - BlokusPiece.SHAPE_SIZE / 2, 
                     selected.y - BlokusPiece.SHAPE_SIZE / 2, players[turn].firstMove);
                     drawBoard();
                     players[turn].pieces.remove(pieceIndex);
                     players[turn].firstMove = false;
                     players[turn].canPlay = !players[turn].pieces.isEmpty();
                     startNewTurn();
                  }
                  catch (IllegalMoveException ex)
                  {
                     displayMessage(ex.getMessage(), "Wrong Move!");
                  }
               }
            }
            
            public void mouseExited(MouseEvent e)
            {
               selected = null;
               board.resetOverlay();
               drawBoard();
            }
            
            public void mouseMoved(MouseEvent e)
            {
               Point p = board.getCoordinates(e.getPoint(), BlokusBoard.CONSOLE_RESOLUTION);
               if (!p.equals(selected))
               {
                  selected = p;
                  board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
                  drawBoard();
               }
            }
            
            public void mouseWheelMoved(MouseWheelEvent e)
            {
               if (e.getWheelRotation() > 0)
               {
                  rotateClockwise();
               }
               else
               {
                  rotateCounterClockwise();
               }
            }
         }
         
         class exitListener implements ActionListener
         {
            public void actionPerformed(ActionEvent event)
            {
               players[turn].canPlay = false;
               startNewTurn();
            }
         }
         
         mainPanel = new JPanel();
         piecesPanel = new JPanel();
          //piece panel
         piecesPanel.setLayout(new BoxLayout(piecesPanel, BoxLayout.PAGE_AXIS));
         
         //side piece panel
         JScrollPane piecePanel = new JScrollPane(piecesPanel);
         piecePanel.getVerticalScrollBar().setUnitIncrement(BlokusPiece.DEFAULT_RESOLUTION);
         piecePanel.setPreferredSize(new Dimension(BlokusPiece.DEFAULT_RESOLUTION -80, BlokusBoard.CONSOLE_RESOLUTION - 27));

         //quit button
         exit = new JButton("I QUIT :<");
         exit.setPreferredSize(new Dimension(BlokusPiece.DEFAULT_RESOLUTION, 20));
         exit.addActionListener(new exitListener());
         
         sidePanel = new JPanel();
         sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
         
         
         
         
         boardPanel = new JPanel();
         boardImage = new ImageIcon(board.gridLay());
         
         grid = new JLabel(boardImage);
         BoardClickListener bcl = new BoardClickListener();
         grid.addMouseListener(bcl);
         grid.addMouseMotionListener(bcl);
         grid.addMouseWheelListener(bcl);
         
         //add grid
         boardPanel.add(grid);
         //add side peice panel
         sidePanel.add(piecePanel);
         //add exit button
         sidePanel.add(exit);
         mainPanel.add(sidePanel);
         mainPanel.add(boardPanel);
         getContentPane().add(mainPanel);
         setVisible(true);
      }
      
      private void rotateClockwise()
      {
         players[turn].pieces.get(pieceIndex).rotateClockwise();
         board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
         drawBoard();
      }
      
      private void rotateCounterClockwise()
      {
         players[turn].pieces.get(pieceIndex).rotateCounterClockwise();
         board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
         drawBoard();
      }
      
      private void flipPiece()
      {
         players[turn].pieces.get(pieceIndex).flipOver();
         board.overlay(players[turn].pieces.get(pieceIndex), selected.x, selected.y);
         drawBoard();
      }
      
      //draw border
      private void drawBoard()
      {
         boardImage.setImage(board.gridLay());
         grid.repaint();
      }
      
      //highlight the border of selected peice
      private void drawBorder()
      {
         JComponent piece = (JComponent) piecesPanel.getComponent(pieceIndex);
         piece.setBorder(BorderFactory.createLineBorder(Color.RED));
      }
      
      //removes border highlight on unselected piece
      private void clearBorder()
      {
         JComponent piece = (JComponent) piecesPanel.getComponent(pieceIndex);
         piece.setBorder(BorderFactory.createLineBorder(Color.white));
      }
      
      //display dialogue boxes 
      private void displayMessage(String message, String title)
      {
         JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
      }
      
      //to select peice type
      private class PieceLabelClickListener implements MouseListener
      {
         public void mouseClicked(MouseEvent e)
         {
            
            BlokusPiecePanel bp = (BlokusPiecePanel) e.getComponent();
            clearBorder();
            pieceIndex = bp.pieceIndex;
            drawBorder();
         }
         
         //need to override abstract methods
         public void mousePressed(MouseEvent e)
         {
            
         }
         
         public void mouseReleased(MouseEvent e)
         {
            
         }
         
         public void mouseEntered(MouseEvent e)
         {
            
         }
         
         public void mouseExited(MouseEvent e)
         {
            
         }
      }
      
      //to flip turns
      private void startNewTurn()
      {
         turn++;
         turn %= 4;
         
         if (isGameOver())
         {
            gameOver();
             
         }
         
         if (!players[turn].canPlay)
         {
            startNewTurn();
            return;
         }
         piecesPanel.removeAll(); 
         for (int i = 0; i < players[turn].pieces.size(); i++)
         {
            BlokusPiecePanel pieceLabel = 
               new BlokusPiecePanel(i, players[turn].pieces.get(i), BlokusPiece.DEFAULT_RESOLUTION);
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
      private boolean isGameOver()
      {
         for (int i = 0; i < 4; i++)
         {
            if (players[i].canPlay) return false;
         }
         return true;
      }
      
      //print score sheet of both players 
      private void gameOver()
      {
        StringBuilder stringContent = new StringBuilder();
        stringContent.append("Human Player's Score: ");
        stringContent.append(players[0].getScore()+players[2].getScore());
        stringContent.append("\n");
        stringContent.append("Computer's Score: ");
        stringContent.append(players[0].getScore()+players[2].getScore());
        stringContent.append("\n");
        JOptionPane.showMessageDialog(this, stringContent.toString(), "Game Over Now", JOptionPane.INFORMATION_MESSAGE );
        System.exit(0);
       
      }

   }
   
 

