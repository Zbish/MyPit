package zbish.com.mypit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static android.R.id.list;

/**
 * Created by Omri on 06/03/2017.
 * the view of the app
 * the class method ondraw, draw all to view
 * use asynctask to draw new points
 */

public class myDraw extends View{
    List<myPoint> points = new ArrayList<>();
    private int screenWidth;
    private int screenHight;
    float moveX,moveY;
    boolean drawLine = true;
    boolean movePoint = false;
    static boolean addNewPoint = false;
    Paint linePaint = new Paint();
    Paint axisPaint = new Paint();
    Paint dotPaint = new Paint();
    public myDraw(Context context) {
        super(context);
        //        pick painter attribute
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(5);
        axisPaint.setColor(Color.BLUE);
        axisPaint.setStrokeWidth(7);
        dotPaint.setColor(Color.RED);
        dotPaint.setStrokeWidth(15);
        dotPaint.setStrokeCap(Paint.Cap.ROUND);
//        finish painter attribute
        newPointTask m1 = new newPointTask();
        m1.execute();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //       get screen width and hight
        this.screenWidth = w;
        this.screenHight = h;
        //       create five random points
        if(points.size()<5)
        {
            for(int i = 0 ; i<5;i++)
            {
                Random r = new Random();
                float x = r.nextInt(screenWidth - 400) + 200;
                float y = r.nextInt(screenHight - 400) + 200;
                myPoint p1 = new myPoint(x,y);
                points.add(p1);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //start draw axis
        canvas.drawLine(screenWidth/12, screenHight/2, screenWidth-screenWidth/12, screenHight/2, axisPaint); //draw width axis
        int axisLineLength = (screenWidth-screenWidth/12)-screenWidth/12;
        canvas.drawLine(screenWidth/2,screenHight/2, screenWidth/2, (screenHight/2)-(axisLineLength/2), axisPaint); //draw hight axis
        canvas.drawLine(screenWidth/2,screenHight/2, screenWidth/2, (screenHight/2)+(axisLineLength/2), axisPaint); //draw hight axis
        // finish draw axis
        //        start draw points
        float x,y,z,e;
        Collections.sort(points); // sort the points
        for (myPoint point : points)
        {
            x = point.getX();
            y = point.getY();
            canvas.drawPoint(x,y,dotPaint);
        }
//        finish draw points
        //        start when point move
        if(movePoint)
        {
            canvas.drawPoint(moveX,moveY,dotPaint);
        }
//  finish when point move
//        start draw line between points
        if(drawLine)
        {
            for (int i = 0 ; i<points.size()-1;i++)
            {
                myPoint pointA = points.get(i);
                int j = i+1;
                myPoint pointB = points.get(j);
                x = pointA.getX();
                y = pointA.getY();
                z = pointB.getX();
                e = pointB.getY();
                canvas.drawLine(x,y,z,e,linePaint);
            }
        }
//      finish draw line between points
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                on action down get x,y acordinate, check if is a point, yes, drop line, remove point from list,draw move dot
                float x,y,x2,y2;
                x = event.getX();
                y = event.getY();
                for (int i = 0; i < points.size(); i++) {
                        x2 = points.get(i).getX();
                        y2 = points.get(i).getY();
                    if (x <= (x2+10) && x + 10 > x2|| y <= (y2+10) && y + 10 > y2) {
                        moveX = x;
                        moveY = y;
                        movePoint = true;
                        drawLine = false;
                        points.remove(i);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
//                when move get x,y cordinate and move dot
                moveX = event.getX();
                moveY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
//              when finger up get x,y cordinate and add new pos for point, draw line connected again,stop move point
                float xx,yy;
                if (!drawLine) {
                    xx = event.getX();
                    yy = event.getY();
                        myPoint savePoint = new myPoint(xx, yy);
                    points.add(savePoint);
                    drawLine = true;
                    movePoint = false;
                    invalidate();
                }
                break;
        }
        return true;
    }
    class newPointTask extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            while (true)
            {
                if(addNewPoint)
                {
                    myPoint newpoint = new myPoint((screenWidth/2),screenHight/2);
                    points.add(newpoint);
                    publishProgress();
                    addNewPoint=false;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            invalidate();
        }


    }
}
