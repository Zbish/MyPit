package zbish.com.mypit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
public class MainActivity extends AppCompatActivity{
    RelativeLayout canvas;
    Button btAddD;
    myDraw mg1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        canvas = (RelativeLayout) findViewById(R.id.myPitView);
        btAddD = (Button)findViewById(R.id.btnAddDot);
        myClick onClick = new myClick();
        btAddD.setOnClickListener(onClick);
        mg1 = new myDraw(this);
        canvas.addView(mg1);
    }
    class myClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            myDraw.newPoint = true;
            mg1.invalidate();
            }

        }
    }
