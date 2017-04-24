package splashdemo;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
* <h1>BlokusPiecePanel methods</h1>
* @author  Nisha Chaube
*/
class BlokusPiecePanel extends JLabel {
    public int pieceIndex;
    
   /**
   *Parameterized Constructor
     * @param pieceIndex
     * @param blokuspiece
     * @param size
   */
    public BlokusPiecePanel(int pieceIndex, BlokusPiece blokuspiece, int size) {
        super(new ImageIcon(blokuspiece.gridLay(size)));
        this.pieceIndex = pieceIndex;
    }
}
