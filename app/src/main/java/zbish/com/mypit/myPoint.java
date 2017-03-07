package zbish.com.mypit;

/**
 * Created by Omri on 06/03/2017.
 */

public class myPoint implements Comparable<myPoint>{

    private Float x;
    private Float y;


    public myPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
// sort point by x from small to big
    @Override
    public int compareTo(myPoint o) {

        if(x.floatValue() > o.x.floatValue())
        {
            return 1;
        }
        else if (x.floatValue() < o.x.floatValue())
        {
            return -1;
        }
        else {
            return 0;
        }
    }
    @Override
    public String toString() {
        return "myPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
