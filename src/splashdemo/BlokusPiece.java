
package splashdemo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.LinkedList;

class BlokusPlayer implements Serializable
{
   public LinkedList<BlokusPiece> pieces;
   public boolean firstMove = true;
   public boolean canPlay = true;
   Integer score;
   
   public BlokusPlayer(int color)
   {
      
      this.score = 0;
      int[][][] shapes = BlokusPiece.getAllShapes();
      
      pieces = new LinkedList<>();
       for (int[][] shape : shapes) {
           pieces.add(new BlokusPiece(shape, color));
       }
     
   }
   
   public int getScore()
   {
     return score;
   }
   
   
   
}


public class BlokusPiece implements Serializable
{
   public static final int SHAPE_SIZE = 7;
   public static final int PIECE =3;
   public static final int ADJACENT = 2;
   public static final int CORNER = 1;
   public static final int BLANK = 0;
   
   //to adjust size of side panel which contains piece information 
   public static final int DEFAULT_RESOLUTION = 120;
   
   private int[][] grid;
   private final int color;
   
   //constructor
   public BlokusPiece(int[][] shape, int color)
   {    
      grid = (int[][]) shape.clone();
      this.color = color;
   }
   
   public void rotateClockwise()
   {
      int[][] temp = new int[SHAPE_SIZE][SHAPE_SIZE];
      
      for (int x = 0; x < SHAPE_SIZE; x++)
         for (int y = 0; y < SHAPE_SIZE; y++)
            temp[SHAPE_SIZE - y - 1][x] = grid[x][y];
            
      grid = temp;
   }
   
   public void rotateCounterClockwise()
   {
      int[][] temp = new int[SHAPE_SIZE][SHAPE_SIZE];
      
      for (int x = 0; x < SHAPE_SIZE; x++)
         for (int y = 0; y < SHAPE_SIZE; y++)
            temp[y][SHAPE_SIZE - x - 1] = grid[x][y];
            
      grid = temp;
   }
   
   public void flipOver()
   {
      int[][] temp = new int[SHAPE_SIZE][SHAPE_SIZE];
      
      for (int x = 0; x < SHAPE_SIZE; x++)
         for (int y = 0; y < SHAPE_SIZE; y++)
            temp[SHAPE_SIZE - x - 1][y] = grid[x][y];
            
      grid = temp;
   }
   
   public int getValue(int x, int y)
   {
      return grid[x][y];
   }
   
   public int getColor()
   {
      return color;
   }
   
   public int getPoints()
   {
     return 0;
   }
   // image on side panel
   public BufferedImage gridLay(int size)
   {
      BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
      int cellSize = size/(SHAPE_SIZE);
      Graphics2D g = (Graphics2D) image.getGraphics();
         
      for (int x = 0; x < SHAPE_SIZE; x++)
      {
         for (int y = 0; y < SHAPE_SIZE; y++)
         {
            if (grid[x][y] == PIECE)
            {
               g.setColor(BlokusBoard.getColor(color));
               g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
               //border colour of pieces
               g.setColor(Color.white);
               g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
         }
      }
      return image;
   }
   
  
   
   public static int[][][] getAllShapes()
   {
      int[][][] shapes = new int[21][SHAPE_SIZE][SHAPE_SIZE];
      int i = 0;
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {1, 2, 2, 2, 2, 2, 1},
         {2, 3, 3, 3, 3, 3, 2},
         {1, 2, 2, 2, 2, 2, 1},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}

      };

      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0}, 
         {0, 1, 2, 1, 0, 0, 0},
         {0, 2, 3, 2, 2, 2, 1},
         {0, 2, 3, 3, 3, 3, 2},
         {0, 1, 2, 2, 2, 2, 1},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 1, 2, 1, 0, 0},   
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 1, 0},
         {0, 0, 2, 3, 3, 2, 0},
         {0, 0, 1, 2, 3, 2, 0},
         {0, 0, 0, 1, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0}, 
         {0, 0, 1, 2, 1, 0, 0},
         {0, 1, 2, 3, 2, 2, 1},
         {0, 2, 3, 3, 3, 3, 2},
         {0, 1, 2, 2, 2, 2, 1},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0}, 
         {0, 0, 1, 2, 1, 0, 0}, 
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 3, 2, 0},
         {0, 0, 0, 1, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0}, 
         {0, 0, 1, 2, 1, 0, 0}, 
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 3, 2, 1, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0},   
         {0, 0, 0, 0, 0, 0, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 2, 3, 2, 3, 2, 0},
         {0, 1, 2, 1, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0}, 
         {0, 0, 0, 0, 0, 0, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 3, 3, 2, 0},
         {0, 0, 1, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0}, 
         {0, 0, 0, 1, 2, 1, 0}, 
         {0, 0, 1, 2, 3, 2, 0},
         {0, 1, 2, 3, 3, 2, 0},
         {0, 2, 3, 3, 2, 1, 0},
         {0, 1, 2, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0},   
         {0, 0, 1, 2, 1, 0, 0},   
         {0, 0, 2, 3, 2, 0, 0},
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 1, 2, 1, 0, 0},   
         {0, 0, 2, 3, 2, 0, 0},   
         {0, 0, 2, 3, 2, 2, 1},
         {0, 0, 2, 3, 3, 3, 2},
         {0, 0, 1, 2, 2, 2, 1},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0},   
         {0, 0, 1, 2, 2, 1, 0},   
         {0, 0, 2, 3, 3, 2, 0},
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 2, 0, 0},
         {0, 1, 2, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      
      shapes[i++] = new int[][] {
         {0, 0, 1, 2, 1, 0, 0},    
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0},   
         {0, 0, 1, 2, 2, 1, 0},
         {0, 1, 2, 3, 3, 2, 0},
         {0, 2, 3, 3, 2, 1, 0},
         {0, 1, 2, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0}, 
         {0, 1, 2, 2, 1, 0, 0},
         {0, 2, 3, 3, 2, 0, 0},
         {0, 2, 3, 3, 2, 0, 0},
         {0, 1, 2, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0}, 
         {0, 0, 1, 2, 1, 0, 0},
         {0, 1, 2, 3, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0}, 
         {0, 0, 0, 0, 0, 0, 0},
         {0, 1, 2, 2, 2, 2, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 3, 2, 0},
         {0, 0, 0, 1, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0},   
         {0, 0, 0, 0, 0, 0, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 2, 3, 3, 3, 2, 0},
         {0, 1, 2, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0},   
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 2, 3, 2, 1, 0},
         {0, 0, 2, 3, 3, 2, 0},
         {0, 0, 1, 2, 2, 1, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
         {0, 0, 0, 0, 0, 0, 0},   
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 2, 3, 2, 0, 0},
         {0, 0, 1, 2, 1, 0, 0},
         {0, 0, 0, 0, 0, 0, 0},
         {0, 0, 0, 0, 0, 0, 0}
      };
      
      shapes[i++] = new int[][] { 
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
