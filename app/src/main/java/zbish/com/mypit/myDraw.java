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
 */

public class myDraw extends View{
    List<myPoint> points = new ArrayList<myPoint>();
    private int screenWidth;
    private int screenHight;
    float moveX,moveY;
    boolean drawLine = true;
    boolean movePoint = false;
    static boolean addNewPoint = false;

    public myDraw(Context context) {
        super(context);
        newPointTask m1 = new newPointTask();
        m1.execute();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //       get screen width and hight
        this.screenWidth = w;
        this.screenHight = h;
        //       create five random dot
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
        //        pick painter attribute
        Paint linePaint = new Paint();
        linePaint.setColor(Color.GREEN);
        linePaint.setStrokeWidth(3);
        Paint axisPaint = new Paint();
        axisPaint.setColor(Color.BLUE);
        axisPaint.setStrokeWidth(5);
        Paint dotPaint = new Paint();
        dotPaint.setColor(Color.RED);
        dotPaint.setStrokeWidth(15);
//        finish painter attribute
        //start draw axis
        canvas.drawLine(150, screenHight/2, screenWidth-150, screenHight/2, axisPaint); //draw width axis
        int t = (screenWidth-150)-150;
        canvas.drawLine(screenWidth/2,screenHight/2, screenWidth/2, (screenHight/2)-(t/2), axisPaint); //draw hight axis
        canvas.drawLine(screenWidth/2,screenHight/2, screenWidth/2, (screenHight/2)+(t/2), axisPaint); //draw hight axis
        // finish draw axis
        //        start draw points
        float x,y,z,e;
        Collections.sort(points); // sort the points
        for (myPoint point : points)
        {
            myPoint drawPoint = point;
            x = drawPoint.getX();
            y = drawPoint.getY();
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
                myPoint myp = points.get(i);
                int j = i+1;
                myPoint myp2 = points.get(j);
                x = myp.getX();
                y = myp.getY();
                z = myp2.getX();
                e = myp2.getY();
                canvas.drawLine(x,y,z,e,linePaint);
            }
        }
//      finish draw line between points
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                on action down take x,y acordinate, check if is a dot, yes drop line, remove point from list,draw move dot
                float x,y,x2,y2;
                x = event.getX();
                y = event.getY();
                for (int i = 0; i < points.size(); i++) {
                        x2 = points.get(i).getX();
                        y2 = points.get(i).getY();
                    if (x <= (x2+10) && x + 10 > x2|| y <= (y2+10) && y + 10 > y2) {
                        drawLine = false;
                        moveX = x;
                        moveY = y;
                        movePoint = true;
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
//              when finger get x,y cordinate and add new pos for point, draw line connected again,stop move point
                if (!drawLine) {
                    x = event.getX();
                    y = event.getY();
                        myPoint savePoint = new myPoint(x, y);
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
