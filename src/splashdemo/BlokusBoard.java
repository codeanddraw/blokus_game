package splashdemo;

import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
* <h1>BlokusBoard methods</h1>
* @author  Nisha Chaube
*/
public class BlokusBoard implements Serializable {
    //variables that assign integer values to colours
    public static final int NOCOLOR = 0;
    public static final int BLUECOLOR = 1;
    public static final int GREENCOLOR = 2;
    public static final int REDCOLOR = 3;
    public static final int YELLOWCOLOR = 4;
    
    //variables that assign size of grid and console resoluton
    public static final int GRIDSIZE = 20;
    public static final int CONSOLERESOLUTION = 600;   
    private int[][]grid;
    private final int[][]overlay;

    //variables to set board background and grid lines color   
    public static final Color BOARDCOLOR = Color.GRAY;
    public static final Color GRIDLINECOLOR = Color.WHITE;

    //variables to set game rules
    public static final String OFFBOARDRULE = "Oops pieces cannot go out of board. Try again!";
    public static final String ADJACENCYRULE = "No no no! Same coloured pieces can't share edges.";
    public static final String OVERLAPRULE = "Watch out! Pieces cannot overlap.";
    public static final String STARTRULE = "First piece? Can only begin at the player's corner.";
    public static final String CORNERRULE = "Attention! Pieces must be connected to at least one other piece of the the same color by the corner.";

   /**
   * Default constructor
   */
    public BlokusBoard() {
        grid = new int[GRIDSIZE][GRIDSIZE];
        overlay = new int[GRIDSIZE][GRIDSIZE];
        reset(grid);
        reset(overlay);
    }

   
    /**
   * This is the isValidMove method which checks if the piece is attempted to be placed at a valid position in the first move.
     * @param bp
     * @param xOffset
     * @param yOffset
     * @param firstMove
     * @return True/False
     * @throws splashdemo.IllegalMoveException
   */
    public boolean isValidMove(BlokusPiece bp, int xOffset, int yOffset, boolean firstMove) throws IllegalMoveException {
        boolean corner = false;
        for (int x = 0; x < BlokusPiece.PIECESIZE; x++) {
            for (int y = 0; y < BlokusPiece.PIECESIZE; y++) {
                int value = bp.getValue(x, y);
                boolean inGridBounds = isInGrid(x + xOffset, y + yOffset);
                if (inGridBounds) {
                    int gridValue = grid[x + xOffset][y + yOffset];
                    if (gridValue != NOCOLOR) {
                        if (value == BlokusPiece.PIECESQUARE) {
                            throw new IllegalMoveException(OVERLAPRULE);
                        }
                        if (gridValue == bp.getColor()) {
                            if (value == BlokusPiece.ADJACENTSQUARE) {
                                throw new IllegalMoveException(ADJACENCYRULE);
                            }
                            if (value == BlokusPiece.CORNERSQUARE) {
                                corner = true;
                            }
                        }
                    } else {
                        if (firstMove && value == BlokusPiece.PIECESQUARE && new Point(x + xOffset, y + yOffset).equals(getCornerPoint(bp.getColor()))) {
                            corner = true;
                        }
                    }
                } else {
                    if (value == BlokusPiece.PIECESQUARE) {
                        throw new IllegalMoveException(OFFBOARDRULE);
                    }
                }
            }
        }
        if (!corner) {
            throw new IllegalMoveException(firstMove ? STARTRULE : CORNERRULE);
        }
        return true;
    }

   /**
   * This is the isValidMove method which checks if the piece is attempted to be placed at a valid position.
     * @param bp
     * @param xOffset
     * @param yOffset
     * @return True/False
     * @throws splashdemo.IllegalMoveException
   */
    public boolean isValidMove(BlokusPiece bp, int xOffset, int yOffset) throws IllegalMoveException {
        return isValidMove(bp, xOffset, yOffset, false);
    }

   /**
   * This is the placePiece method which places the piece after checking if it's a valid move(first move) .
     * @param bp
     * @param xOffset
     * @param yOffset
     * @param firstMove
     * @throws splashdemo.IllegalMoveException
   */
    public void placePiece(BlokusPiece bp, int xOffset, int yOffset, boolean firstMove) throws IllegalMoveException {
        isValidMove(bp, xOffset, yOffset, firstMove);
        for (int x = 0; x < BlokusPiece.PIECESIZE; x++) {
            for (int y = 0; y < BlokusPiece.PIECESIZE; y++) {
                if (bp.getValue(x, y) == BlokusPiece.PIECESQUARE) {
                    grid[x + xOffset][y + yOffset] = bp.getColor();
                }
            }
        }
    }

    /**
   * This is the placePiece method which places the piece after checking if it's a valid move.
     * @param bp
     * @param xOffset
     * @param yOffset
     * @throws splashdemo.IllegalMoveException
   */
    public void placePiece(BlokusPiece bp, int xOffset, int yOffset) throws IllegalMoveException {
        placePiece(bp, xOffset, yOffset, false);
    }

    /**
   * This is the overlay method.
     * @param bp
     * @param xOffset
     * @param yOffset
   */
    public void overlay(BlokusPiece bp, int xOffset, int yOffset) {
        reset(overlay);
        for (int x = 0; x < BlokusPiece.PIECESIZE; x++) {
            for (int y = 0; y < BlokusPiece.PIECESIZE; y++) {
                if (isInGrid(x + xOffset - BlokusPiece.PIECESIZE / 2, y + yOffset - BlokusPiece.PIECESIZE/ 2) && bp.getValue(x, y) == BlokusPiece.PIECESQUARE) {
                    overlay[x + xOffset - BlokusPiece.PIECESIZE / 2][y + yOffset - BlokusPiece.PIECESIZE/ 2] = bp.getColor();
                }
            }
        }
    }

    /**
   * This is the gridLay method which renders image on side panel.
     * @param size
     * @return BufferedImage
   */
    public BufferedImage gridLay(int size) {
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        int cellSize = size / (GRIDSIZE);
        Graphics2D g = (Graphics2D) image.getGraphics();

        for (int x = 0; x < GRIDSIZE; x++) {
            for (int y = 0; y < GRIDSIZE; y++) {
                g.setColor(getColor(grid[x][y]));
                if (overlay[x][y] != NOCOLOR) {
                    g.setColor(blendColor(g.getColor(), getColor(overlay[x][y]), 0.1f));
                }
                g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                g.setColor(GRIDLINECOLOR);
                g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);

                if (grid[x][y] == NOCOLOR) {
                    boolean corner = false;
                    Point p = new Point(x, y);
                    if (getCornerPoint(BLUECOLOR).equals(p)) {
                        g.setColor(getColor(BLUECOLOR));
                        corner = true;
                    } else if (getCornerPoint(GREENCOLOR).equals(p)) {
                        g.setColor(getColor(GREENCOLOR));
                        corner = true;
                    } else if (getCornerPoint(REDCOLOR).equals(p)) {
                        g.setColor(getColor(REDCOLOR));
                        corner = true;
                    } else if (getCornerPoint(YELLOWCOLOR).equals(p)) {
                        g.setColor(getColor(YELLOWCOLOR));
                        corner = true;
                    }
                    if (corner) {
                        g.fillOval(x * cellSize + cellSize / 2 - cellSize / 6, y * cellSize + cellSize / 2 - cellSize / 6, cellSize / 3, cellSize / 3);
                    }
                }
            }
        }
        return image;
    }
    
   /**
   * This is the gridLay method.
     * @return Buffered Image
   */
    public BufferedImage gridLay() {
        return gridLay(CONSOLERESOLUTION);
    }
    
   /**
   * This is the blendColor method which blends the piece color with board color after it is selected by mouse click.
     * @param c1
     * @param c2
     * @param balance
     * @return Color
     * @throws splashdemo.IllegalMoveException
   */
    private Color blendColor(Color c1, Color c2, float balance) {
        int r = (int) (c1.getRed() * balance + c2.getRed() * (1 - balance));
        int g = (int) (c1.getGreen() * balance + c2.getGreen() * (1 - balance));
        int b = (int) (c1.getBlue() * balance + c2.getBlue() * (1 - balance));
        return new Color(r, g, b);
    }

   /**
   * This is the resetOverlayGrid method which resets the grid space.
   */
    public void resetOverlayGrid() {
        reset(overlay);
    }

   /**
   * This is a private reset method called by resetOverlayGrid to reset the grid to default grid color.
     * @param array
   */
    private void reset(int[][] array) {
        for (int x = 0; x < GRIDSIZE; x++) {
            for (int y = 0; y < GRIDSIZE; y++) {
                array[x][y] = NOCOLOR;
            }
        }
    }

   /**
   * This is the private isInbounds method which checks if the point is within the grid.
     * @param x1
     * @param x2
     * @param balance
     * @return Color
     * @throws splashdemo.IllegalMoveException
   */
    private boolean isInGrid(int x, int y) {
        return (x >= 0 && y >= 0 && x <GRIDSIZE && y < GRIDSIZE);
    }

     /**
   * This is the getCorner method which places points on four corners of grid.
   * @param color
   * @return Point
   * @exception IllegalArgumentException.
   */
    private Point getCornerPoint(int color) {
        switch (color) {
            case BLUECOLOR:
                return new Point(0, 0);
            case YELLOWCOLOR:
                return new Point(0, GRIDSIZE - 1);
            case REDCOLOR:
                return new Point(GRIDSIZE - 1, GRIDSIZE - 1);
            case GREENCOLOR:
                return new Point(GRIDSIZE - 1, 0);
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
   * This is the getColor method which returns color.
   * @param color
   * @return Color
   */
    public static Color getColor(int color) {
        switch (color) {
            case BLUECOLOR:
                return Color.BLUE;
            case REDCOLOR:
                return Color.RED;
            case YELLOWCOLOR:
                return Color.YELLOW;
            case GREENCOLOR:
                return Color.GREEN;
                //for dark green use RGB< new Color(0, 128, 0)>
            default:
                return BOARDCOLOR;
        }
    }

    /**
   * This is the getCorner method which gets points on four corners of grid.
   * @param pixel
   * @param consoleResolution
   * @return Point
   */
    public Point getCoordinates(Point pixel, int consoleResolution) {
        return new Point(pixel.x /(consoleResolution / GRIDSIZE), pixel.y/(consoleResolution / GRIDSIZE));
    }
    
   /**
   * This is the saveGrid method which saves the current game grid.
     * @param outstream
     * @throws splashdemo.IOException
   */
    void saveGrid(ObjectOutputStream outStream) throws IOException {
        outStream.writeObject(grid);
    }

   /**
   * This is the loadGrid method which loads the previously saved game grid.
     * @param inStream
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     * 
   */
    public void loadGrid(ObjectInputStream inStream) throws IOException, ClassNotFoundException {
        grid = (int[][]) inStream.readObject();
    }

}
