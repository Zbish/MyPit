package zbish.com.mypit;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Omri on 14/03/2017.
 * class points make work on a list of points
 * the only class thet make new points
 */

class points {
  private List<myPoint> points = new ArrayList<>();

    points(List<myPoint> points1) {
        this.points = points1;
    }

    void createRandomPoint(int screenwidth, int screenhight){
            Random r = new Random();
        Log.d("a12",screenwidth +"ff"+screenhight);
            float x = r.nextInt(screenwidth - 400) + 200;
            float y = r.nextInt(screenhight - 400) + 200;
            myPoint p1 = new myPoint(x,y);
            points.add(p1);
    }

     List<myPoint> getPointsList()
    {
        Collections.sort(points);
        return points;
    }
    void addPoint(float x, float y)
    {
        myPoint p1 = new myPoint(x,y);
        points.add(p1);
    }
    void removePoint(int index)
    {
        points.remove(index);
    }
    myPoint getPoint(int index)
    {
        myPoint mp1;
        mp1 = points.get(index);
        return mp1;
    }
}
