package zbish.com.mypit;
import android.os.AsyncTask;

/**
 * Created by Omri on 09/03/2017.
 * a class that make the job in the background
 */

class MyAsyncTask extends AsyncTask<myPoint,Void,Boolean>
{
    @Override
    protected Boolean doInBackground(myPoint... params) {
//        check if touch is a point
        if(myDraw.synk == 1)
        {
            float x2,y2,x,y;
            x= params[0].getX();
            y= params[0].getY();
            for (int i = 0; i < myDraw.points.size(); i++) {
                x2 = myDraw.points.get(i).getX();
                y2 = myDraw.points.get(i).getY();
                if (x <= (x2+10) && x + 10 > x2|| y <= (y2+10) && y + 10 > y2) {
                    myDraw.points.remove(i);
                    myDraw.movePoint = true;
                    myDraw.drawLine = false;
                    myDraw.synk = 2;
                }
            }
        }
//        move the point
        if(myDraw.synk == 2)
        {
            myDraw.moveX = params[0].getX();
            myDraw.moveY = params[0].getY();
        }
        return true;
    }


}