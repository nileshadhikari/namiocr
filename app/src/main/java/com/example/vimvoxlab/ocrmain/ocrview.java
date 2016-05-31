package com.example.vimvoxlab.ocrmain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by nilesh on 5/9/16.
 */
public class ocrview extends View {
    private Rect rectangle1;
    private Rect rectangle2;
    private Paint paint;

    public ocrview(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    private void init(){
        int rect1_x = 120;
        int rect1_y = 100;
        int rect1_sideLength =515;
        int rect1_height = 660;

        int rect2_x = 60;
        int rect2_y = 750;
        int rect2_sideLength =1480;
        int rect2_height = 970;


        // create a rectangle that we'll draw later
        rectangle1 = new Rect(rect1_x, rect1_y, rect1_sideLength, rect1_height);
        rectangle2 = new Rect(rect2_x, rect2_y, rect2_sideLength, rect2_height);


        // create the Paint and set its color
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(rectangle1, paint);
        canvas.drawRect(rectangle2, paint);
    }

}


