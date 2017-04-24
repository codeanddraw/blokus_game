package splashdemo;

import java.io.Serializable;
import java.util.LinkedList;
/**
* <h1>BlokusPlayer methods</h1>
* @author  Nisha Chaube
*/
class BlokusPlayer implements Serializable {
    public LinkedList<BlokusPiece> pieces;
    public boolean firstMove = true;
    public boolean canPlay = true;

    /**
   * This is BlokusPlayer constructor which loads pieces of different players as their turn comes.
     * @param color
   */
    public BlokusPlayer(int color) {
        int[][][] pieceShapes = BlokusPiece.getAllPieceShapes();
        pieces = new LinkedList<>();
        for (int[][]shape : pieceShapes) {
            pieces.add(new BlokusPiece(shape, color));
        }
    }

    /**
   * This is calculateScore method which calculates scores of both players.
     * @return totalScores
   */
    public int calculateScore() {
      int totalScores = 0;
      for (BlokusPiece blokuspiece : pieces)
      {
         totalScores = totalScores + blokuspiece.getScores();
      }
      return totalScores;
    }
}
