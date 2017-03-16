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
import java.util.List;
import java.util.Random;
import static android.os.AsyncTask.SERIAL_EXECUTOR;

/**
 * Created by Omri on 06/03/2017.
 * the view of the app
 * the class method ondraw, draw all to view
 */

public class myDraw extends View{
    int screenWidth;
    int screenHight;
    boolean drawLine = true;
    boolean movePoint = false;
    static boolean newPoint = false;
    float moveX,moveY;
    private points ListOfPOints;
    Paint linePaint = new Paint();
    Paint axisPaint = new Paint();
    Paint dotPaint = new Paint();

    public myDraw(Context context,List<myPoint> poi) {
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
        //       create five random points
        this.ListOfPOints = new points(poi);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.screenWidth = getMeasuredWidth();
        this.screenHight = getMeasuredHeight();
        if(ListOfPOints.getPointsList().size()<5)
        {
            for(int i = 0 ; i<5;i++)
            {
                ListOfPOints.createRandomPoint(screenWidth,screenHight);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        drawAxis(canvas);
//        add new point
        if(newPoint)
        {
            addNewPOint();
        }
        if(movePoint)
        {
            movePOint(canvas);
        }
        super.draw(canvas);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPoints(canvas);
        if(drawLine)
        {
           drawLineBetweenPoints(canvas);
        }
    }
//    handeling on tuch event
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MyAsyncTask b = new MyAsyncTask();
        if (MotionEvent.ACTION_DOWN == event.getAction())
        {
            myPoint m1 = new myPoint(event.getX(),event.getY());
            float x2,y2,x,y;
            x= m1.getX();
            y= m1.getY();
            for (int i = 0; i < ListOfPOints.getPointsList().size(); i++) {
                x2 = ListOfPOints.getPoint(i).getX();
                y2 = ListOfPOints.getPoint(i).getY();
                if (x <= (x2+10) && x + 10 > x2|| y <= (y2+10) && y + 10 > y2) {
                    ListOfPOints.removePoint(i);
                    movePoint = true;
                    drawLine = false;

                }
            }
        }

        else if(MotionEvent.ACTION_MOVE == event.getAction()&&movePoint)
        {
            myPoint m2 = new myPoint(event.getX(),event.getY());
            b.execute(m2);
        }
       else if(MotionEvent.ACTION_UP == event.getAction()&&movePoint)
        {
            ListOfPOints.addPoint(event.getX(),event.getY());
            movePoint = false;
            drawLine = true;
            invalidate();
        }
        return true;
        }
    class MyAsyncTask extends AsyncTask<myPoint,Void,Void>
    {
        @Override
        protected Void doInBackground(myPoint... params) {
                moveX = params[0].getX();
                moveY = params[0].getY();
                publishProgress();

            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            invalidate();


        }
    }
    //draw axis
    private void drawAxis(Canvas canvas)
    {
        canvas.drawLine(screenWidth/12, screenHight/2, screenWidth-screenWidth/12, screenHight/2, axisPaint); //draw width axis
        int axisLineLength = (screenWidth-screenWidth/12)-screenWidth/12;
        canvas.drawLine(screenWidth/2,screenHight/2, screenWidth/2, (screenHight/2)-(axisLineLength/2), axisPaint); //draw hight axis
        canvas.drawLine(screenWidth/2,screenHight/2, screenWidth/2, (screenHight/2)+(axisLineLength/2), axisPaint); //draw hight axis
    }
    //    draw points
    private void drawPoints(Canvas canvas)
    {
        float x,y;
        for (myPoint point : ListOfPOints.getPointsList())
        {
            x = point.getX();
            y = point.getY();
            canvas.drawPoint(x,y,dotPaint);
        }
    }
    //        draw line between points
    private void drawLineBetweenPoints(Canvas canvas)
    {
        float x,y,z,e;
        for (int i = 0,j = 1 ; i<ListOfPOints.getPointsList().size()-1;i++,j++)
        {
            myPoint pointA = ListOfPOints.getPoint(i);
            myPoint pointB = ListOfPOints.getPoint(j);
            x = pointA.getX();
            y = pointA.getY();
            z = pointB.getX();
            e = pointB.getY();
            canvas.drawLine(x,y,z,e,linePaint);
        }
    }
    //    move the point draw
    private void movePOint(Canvas canvas)
    {
        canvas.drawPoint(moveX,moveY,dotPaint);
    }
    //    add new point
    public void addNewPOint()
    {
        ListOfPOints.addPoint(screenWidth/2,screenHight/2);
        newPoint = false;
    }
}
