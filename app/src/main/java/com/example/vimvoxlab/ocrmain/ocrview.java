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
    private Rect rectangle;
    private Paint paint;

    public ocrview(Context context, AttributeSet attrs) {
        super(context,attrs);
        init();
    }

    private void init(){
        int x = 60;
        int y = 750;
        int sideLength =1550;
        int height = 970;

        // create a rectangle that we'll draw later
        rectangle = new Rect(x, y, sideLength, height);


        // create the Paint and set its color
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(rectangle, paint);
    }

}


