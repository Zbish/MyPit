package zbish.com.mypit;

import android.support.annotation.NonNull;

/**
 * Created by Omri on 06/03/2017.
 * class my point
 * point on the grahf
 * every point have x,y cordinate
 */

class myPoint implements Comparable<myPoint>{

    private Float x;
    private Float y;


   myPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

// sort point by x from small to big
    @Override
    public int compareTo(@NonNull myPoint myp) {

        if(x > myp.x)
        {
            return 1;
        }
        else if (x < myp.x)
        {
            return -1;
        }
        else {
            return 0;
        }
    }
}
