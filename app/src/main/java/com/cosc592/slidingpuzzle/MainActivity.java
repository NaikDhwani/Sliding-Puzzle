package com.cosc592.slidingpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppInterface interfaceClass;
    public static Slide slide = new Slide();
    private GestureDetector gd;
    private int gameOver = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        interfaceClass = new AppInterface(this);
        setContentView(interfaceClass);

        TouchHandler handler = new TouchHandler();
        gd = new GestureDetector(this,handler);
    }

    public char[][] getInitialBoardValues() {
        return slide.generateInitialBoard();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(gameOver == 0)
            gd.onTouchEvent(event);
        else
            return false;
        return true;
    }

    private class TouchHandler extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                int startX = (int) e1.getX();
                int startY = (int) e1.getY();
                int endX = (int) e2.getX();
                int endY = (int) e2.getY();

                int blank[] = interfaceClass.getBlankValues();
                int bStartX = blank[0];
                int bStartY = blank[1];
                int bEndX = blank[2];
                int bEndY = blank[3];
                int bX = blank[4];
                int bY = blank[5];

                int grid[] = interfaceClass.getGridValues();
                int gStartX = grid[0];
                int gStartY = grid[1];
                int gEndX = grid[2];
                int gEndY = grid[3];

                if (startX >= bStartX && startX <= bEndX && startY >= bStartY && startY <= bEndY) {

                    if (endX > gEndX || endX < gStartX || endY > gEndY || endY < gStartY) {
                        Log.d("NO", "NO");
                    } else {
                        slide.setOriginalLocation(bX, bY);
                        if (endX > bEndX) {
                            //move right
                            slide.setUpdatedLocation(bX, bY + 1);
                        } else if (endX < bStartX) {
                            //move left
                            slide.setUpdatedLocation(bX, bY - 1);
                        } else if (endY > bEndY) {
                            //move Down
                            slide.setUpdatedLocation(bX + 1, bY);
                        } else if (endY < startY) {
                            //move Up
                            slide.setUpdatedLocation(bX - 1, bY);
                        }
                        char[][] updatedBoard = slide.getUpdate();
                        interfaceClass.updateBoard(updatedBoard);
                    }
                }

                if(interfaceClass.checkFinalBoard()) {
                    Toast.makeText(getApplicationContext(), "Congratulations!", Toast.LENGTH_SHORT).show();
                    gameOver =1;
                }

            }catch (Exception e){
                Log.d("Error: ",e+"");
            }
            return true;
        }
    }
}
