/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splashdemo;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author David Marks
 */
class BlokusPlayer implements Serializable {

    public LinkedList<BlokusPiece> pieces;
    public boolean firstMove = true;
    public boolean canPlay = true;

    public BlokusPlayer(int color) {

        int[][][] shapes = BlokusPiece.getAllShapes();

        pieces = new LinkedList<>();
        for (int[][] shape : shapes) {
            pieces.add(new BlokusPiece(shape, color));
        }

    }

    public int getScore() {
        return 0;
    }
}
