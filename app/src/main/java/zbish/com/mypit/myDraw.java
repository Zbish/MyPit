package zbish.com.mypit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    static List<myPoint> points = new ArrayList<>();
    static boolean drawLine = true;
    static boolean movePoint = false;
    static boolean newPoint = false;
    static int synk = 1;
    static float moveX,moveY;
    Paint linePaint = new Paint();
    Paint axisPaint = new Paint();
    Paint dotPaint = new Paint();

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
        Collections.sort(points); // sort the points
        for (myPoint point : points)
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
        for (int i = 0,j = 1 ; i<points.size()-1;i++,j++)
        {
            myPoint pointA = points.get(i);
            myPoint pointB = points.get(j);
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
        myPoint newpoint = new myPoint((screenWidth/2),screenHight/2);
        myDraw.points.add(newpoint);
        newPoint = false;
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
        MyAsyncTask a = new MyAsyncTask();
        if (MotionEvent.ACTION_DOWN == event.getAction())
        {
            myPoint m1 = new myPoint(event.getX(),event.getY());
            a.executeOnExecutor(SERIAL_EXECUTOR,m1);
        }
        else if(MotionEvent.ACTION_MOVE == event.getAction()&&movePoint)
        {

            try {
                Thread.sleep(10);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MyAsyncTask b = new MyAsyncTask();
            myPoint m2 = new myPoint(event.getX(),event.getY());
            b.executeOnExecutor(SERIAL_EXECUTOR,m2);
            invalidate();
        }
       else if(MotionEvent.ACTION_UP == event.getAction()&&movePoint)
        {
            myPoint savePoint = new myPoint(event.getX(),event.getY());
            myDraw.points.add(savePoint);
            myDraw.movePoint = false;
            myDraw.drawLine = true;
            invalidate();
            myDraw.synk = 1;
        }
        return true;
        }


}
