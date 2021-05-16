package com.cosc592.slidingpuzzle;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AppInterface extends RelativeLayout {

    final int DP;
    GridLayout boardGrid;
    TextView[][] boardText = new TextView[3][3];
    TextView text;
    MainActivity main = new MainActivity();
    char[][] initialBoardValues;
    int[] location;

    public AppInterface(Context context) {
        super(context);

        DP = (int)(getResources().getDisplayMetrics().density);

        text = new TextView(context);
        text.setText("Place Numbers in Ascending Order and the Empty Slot at the End");
        text.setTextColor(getResources().getColor(R.color.colorPrimary));
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        text.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(10*DP, 40*DP,10*DP,0*DP);
        text.setLayoutParams(params);
        addView(text);


        boardGrid =new GridLayout(context);
        boardGrid.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        boardGrid.setPadding(10*DP,10*DP,10*DP,10*DP);
        boardGrid.setRowCount(3);
        boardGrid.setColumnCount(3);
        params = new LayoutParams(318*DP, 318*DP);
        params.addRule(CENTER_IN_PARENT, TRUE);
        boardGrid.setLayoutParams(params);
        addView(boardGrid);

        initialBoardValues = main.getInitialBoardValues();
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                boardText[i][j] = new TextView(context);
                boardText[i][j].setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                boardText[i][j].setTextColor(getResources().getColor(R.color.colorPrimary));
                boardText[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                boardText[i][j].setGravity(Gravity.CENTER);
                boardText[i][j].setText(getValues(initialBoardValues, i, j));
                GridLayout.LayoutParams gridParams = new GridLayout.LayoutParams();
                gridParams.width = 97*DP;
                gridParams.height = 97*DP;
                gridParams.topMargin = 1*DP;
                gridParams.rightMargin = 1*DP;
                gridParams.leftMargin = 1*DP;
                gridParams.bottomMargin = 1*DP;
                boardText[i][j].setLayoutParams(gridParams);
                boardGrid.addView(boardText[i][j]);
            }
        }
    }

    //Display updated board
    public void updateBoard(char[][] ch){
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                boardText[i][j].setText(getValues(ch, i, j));
            }
        }
    }

    private String getValues(char[][] board, int i, int j) {
        char value = board[i][j];
        return String.valueOf(value);
    }

    //get Grid View Coordinates
    public int[] getGridValues(){
        int[] array = new int[4];
        location = new int[2];
        boardGrid.getLocationOnScreen(location);
        array[0] = location[0] - 10*DP;
        array[1] = location[1] - 10*DP;
        array[2] = (location[0] + 318*DP) - 10*DP;
        array[3] = (location[1] + 318*DP) - 10*DP;
        return array;
    }

    //get coordinate of blank space
    public int[] getBlankValues(){
        int[] array = new int[6];
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(boardText[i][j].getText().toString().equals(" ")) {
                    location = new int[2];
                    boardText[i][j].getLocationOnScreen(location);
                    array[0] = location[0];
                    array[1] = location[1];
                    array[2] = location[0] + boardText[i][j].getWidth();
                    array[3] = location[1] + boardText[i][j].getHeight();
                    array[4] = i;
                    array[5] = j;
                    return array;
                }
            }
        }
        return array;
    }

    //Check for Final Board
    public boolean checkFinalBoard(){
        int k = 0;
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                k++;
                if(!boardText[i][j].getText().toString().equals(String.valueOf(k)))
                    return false;
            }
        }

        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                boardText[i][j].setBackgroundColor(getResources().getColor(R.color.textBackColor));
                boardText[i][j].setTextColor(getResources().getColor(R.color.boardBackColor));
            }
        }
        boardGrid.setBackgroundColor(getResources().getColor(R.color.boardBackColor));
        text.setText("Congratulations!");
        text.setTextColor(getResources().getColor(R.color.textBackColor));
        return true;
    }
}
