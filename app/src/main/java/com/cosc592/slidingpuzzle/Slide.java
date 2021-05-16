/*Application-1 Sliding Puzzle
  Model Class*/
package com.cosc592.slidingpuzzle;

import java.util.Random;

public class Slide {
    private Random random;
    private final char BLANK = ' ';
    static char[][] board = new char[3][3];
    private int originalX,originalY, updatedX, updatedY;

    public Slide()
    { }

    //Generate Initial Board
    public char[][] generateInitialBoard()
    {
        int[] list = createList();

        int k = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
            {
                if (list[k] == 0)
                    board[i][j] = BLANK;
                else
                    board[i][j] = (char)('0' + list[k]);
                k++;
            }
        return board;
    }

    private int[] createList()
    {
        random = new Random();
        int[] list = new int[9];

        for (int i = 0; i < 9; i++)
            list[i] = i;

        int k = 8;
        for (int i = 0; i < 9; i++)
        {
            int j = random.nextInt(k+1);

            int temp = list[j];
            list[j] = list[k];
            list[k] = temp;

            k = k - 1;
        }
        return list;
    }

    public void setOriginalLocation(int originalX, int originalY){
        this.originalX = originalX;
        this.originalY = originalY;
    }

    public void setUpdatedLocation(int updatedX, int updatedY){
        this.updatedX = updatedX;
        this.updatedY = updatedY;
    }

    //get Updated Board
    public char[][] getUpdate(){
        char value2;
        value2 = board[updatedX][updatedY];
        board[updatedX][updatedY] = BLANK;
        board[originalX][originalY] = value2;
        return board;
    }
}
