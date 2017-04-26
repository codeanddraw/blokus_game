package splashdemo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
* <h1>BlokusPiece methods</h1>
* @author  Nisha Chaube
*/
public class BlokusPiece implements Serializable {
    public static final int PIECESIZE = 7;
    public static final int PIECESQUARE = 3;
    public static final int ADJACENTSQUARE = 2;
    public static final int CORNERSQUARE = 1;
    public static final int BLANKSQUARE = 0;
    private int[][]grid;
    private final int color;
    
    //to adjust size of side panel which contains piece information 
    public static final int DEFAULTRESOLUTION = 120;

   /**
   * Default constructor
     * @param shape
     * @param color
   */
    public BlokusPiece(int[][] shape, int color) {
        grid = (int[][]) shape.clone();
        this.color = color;
    }

   /**
   * This is the rotateClockwise method which rotates the piece clockwise.
   */
    public void rotateClockwise() {
        int[][]temp = new int[PIECESIZE][PIECESIZE];
        for (int x = 0; x < PIECESIZE; x++) {
            for (int y = 0; y < PIECESIZE; y++) {
                temp[PIECESIZE - y - 1][x] = grid[x][y];
            }
        }
        grid = temp;
    }

   /**
   * This is the rotateCounterClockwise method which rotates the piece clockwise.
   */
    public void rotateCounterClockwise() {
        int[][] temp = new int[PIECESIZE][PIECESIZE];
        for (int x = 0; x < PIECESIZE; x++) {
            for (int y = 0; y < PIECESIZE; y++) {
                temp[y][PIECESIZE - x - 1] = grid[x][y];
            }
        }
        grid = temp;
    }
    
   /**
   * This is the flipOver method which flips the piece.
   */
    public void flipOver() {
        int[][] temp = new int[PIECESIZE][PIECESIZE];
        for (int x = 0; x < PIECESIZE; x++) {
            for (int y = 0; y < PIECESIZE; y++) {
                temp[PIECESIZE - x - 1][y] = grid[x][y];
            }
        }
        grid = temp;
    }

   /**
   * This is the getValue method which returns grid coordinates.
     * @return grid[x][y]
   */
    public int getValue(int x, int y) {
        return grid[x][y];
    }
    
   /**
   * This is the getColor method which returns color.
     * @return color
   */
    public int getColor() {
        return color;
    }
 
   /**
   * This is the getScores method which returns scores of player.
     * @return scores
   */
    public int getScores() {
      int scores = 0;
      for (int y = 0; y < PIECESIZE; y++)
         for (int x = 0; x < PIECESIZE; x++)
            if (grid[x][y]==PIECESQUARE) 
                scores++;
      return scores;
   }
   
   /**
   * This is the gridLay method which places pieces in rectangular boxes in side panel.
     * @param size
     * @return BufferedImage
   */
    public BufferedImage gridLay(int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        int cellSize = size / (PIECESIZE);
        Graphics2D img = (Graphics2D) image.getGraphics();
        img.setColor(Color.white);
        img.fillRect(0, 0, size, size);
        for (int x = 0; x < PIECESIZE; x++) {
            for (int y = 0; y < PIECESIZE; y++) {
                if (grid[x][y] == PIECESQUARE) {
                    img.setColor(BlokusBoard.getColor(color));
                    img.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                    //border colour of pieces
                    img.setColor(Color.white);
                    img.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }
        return image;
    }

   /**
   * This is the getAllPieceShapes method which returns 21 pieces of different types.
     * @return shapes
   */
    public static int[][][] getAllPieceShapes() {
        int[][][] shapes = new int[21][PIECESIZE][PIECESIZE];
        int i = 0;

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {1, 2, 2, 2, 2, 2, 1},
            {2, 3, 3, 3, 3, 3, 2},
            {1, 2, 2, 2, 2, 2, 1},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}

        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 2, 1, 0, 0, 0},
            {0, 2, 3, 2, 2, 2, 1},
            {0, 2, 3, 3, 3, 3, 2},
            {0, 1, 2, 2, 2, 2, 1},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 1, 2, 1, 0, 0},
            {0, 0, 2, 3, 2, 0, 0},
            {0, 0, 2, 3, 2, 1, 0},
            {0, 0, 2, 3, 3, 2, 0},
            {0, 0, 1, 2, 3, 2, 0},
            {0, 0, 0, 1, 2, 1, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 2, 1, 0, 0},
            {0, 1, 2, 3, 2, 2, 1},
            {0, 2, 3, 3, 3, 3, 2},
            {0, 1, 2, 2, 2, 2, 1},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 2, 1, 0, 0},
            {0, 1, 2, 3, 2, 1, 0},
            {0, 2, 3, 3, 3, 2, 0},
            {0, 1, 2, 2, 3, 2, 0},
            {0, 0, 0, 1, 2, 1, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 2, 1, 0, 0},
            {0, 1, 2, 3, 2, 1, 0},
            {0, 2, 3, 3, 3, 2, 0},
            {0, 1, 2, 3, 2, 1, 0},
            {0, 0, 1, 2, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 2, 2, 2, 1, 0},
            {0, 2, 3, 3, 3, 2, 0},
            {0, 2, 3, 2, 3, 2, 0},
            {0, 1, 2, 1, 2, 1, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 2, 2, 2, 1, 0},
            {0, 2, 3, 3, 3, 2, 0},
            {0, 1, 2, 3, 3, 2, 0},
            {0, 0, 1, 2, 2, 1, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 1, 2, 1, 0},
            {0, 0, 1, 2, 3, 2, 0},
            {0, 1, 2, 3, 3, 2, 0},
            {0, 2, 3, 3, 2, 1, 0},
            {0, 1, 2, 2, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 2, 1, 0, 0},
            {0, 0, 2, 3, 2, 0, 0},
            {0, 1, 2, 3, 2, 1, 0},
            {0, 2, 3, 3, 3, 2, 0},
            {0, 1, 2, 2, 2, 1, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 1, 2, 1, 0, 0},
            {0, 0, 2, 3, 2, 0, 0},
            {0, 0, 2, 3, 2, 2, 1},
            {0, 0, 2, 3, 3, 3, 2},
            {0, 0, 1, 2, 2, 2, 1},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 2, 2, 1, 0},
            {0, 0, 2, 3, 3, 2, 0},
            {0, 1, 2, 3, 2, 1, 0},
            {0, 2, 3, 3, 2, 0, 0},
            {0, 1, 2, 2, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 1, 2, 1, 0, 0},
            {0, 0, 2, 3, 2, 0, 0},
            {0, 0, 2, 3, 2, 0, 0},
            {0, 0, 2, 3, 2, 0, 0},
            {0, 0, 2, 3, 2, 0, 0},
            {0, 0, 1, 2, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 2, 2, 1, 0},
            {0, 1, 2, 3, 3, 2, 0},
            {0, 2, 3, 3, 2, 1, 0},
            {0, 1, 2, 2, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 2, 2, 1, 0, 0},
            {0, 2, 3, 3, 2, 0, 0},
            {0, 2, 3, 3, 2, 0, 0},
            {0, 1, 2, 2, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 2, 1, 0, 0},
            {0, 1, 2, 3, 2, 1, 0},
            {0, 2, 3, 3, 3, 2, 0},
            {0, 1, 2, 2, 2, 1, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 2, 2, 2, 2, 0},
            {0, 2, 3, 3, 3, 2, 0},
            {0, 1, 2, 2, 3, 2, 0},
            {0, 0, 0, 1, 2, 1, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 1, 2, 2, 2, 1, 0},
            {0, 2, 3, 3, 3, 2, 0},
            {0, 1, 2, 2, 2, 1, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 2, 1, 0, 0},
            {0, 0, 2, 3, 2, 1, 0},
            {0, 0, 2, 3, 3, 2, 0},
            {0, 0, 1, 2, 2, 1, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 2, 1, 0, 0},
            {0, 0, 2, 3, 2, 0, 0},
            {0, 0, 2, 3, 2, 0, 0},
            {0, 0, 1, 2, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        shapes[i++] = new int[][]{
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 2, 1, 0, 0},
            {0, 0, 2, 3, 2, 0, 0},
            {0, 0, 1, 2, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0}
        };

        return shapes;
    }
}
