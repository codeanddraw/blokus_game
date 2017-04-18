package splashdemo;

//side panel of Blokus pieces
import javax.swing.ImageIcon;
import javax.swing.JLabel;

class BlokusPiecePanel extends JLabel
{  
      public int pieceIndex;
      //constructor
      public BlokusPiecePanel(int pieceIndex, BlokusPiece blokuspiece, int size)
      {
         super(new ImageIcon(blokuspiece.gridLay(size)));
         this.pieceIndex = pieceIndex;
      }
}