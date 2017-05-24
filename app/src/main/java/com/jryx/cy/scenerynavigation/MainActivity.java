package com.jryx.cy.scenerynavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.jryx.cy.citypicker.CityPickerActivity;

public class MainActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private TextView cityID = null;
    private String cityName = null;
    private String cityCode = null;
    private static final int REQUEST_CODE_PICK_CITY = 233;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityID = (TextView)findViewById(R.id.cityID);
        cityID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CityPickerActivity.class);
                startActivityForResult(intent,REQUEST_CODE_PICK_CITY);
            }
        });
 //       cityName = cityID.getText().toString();

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setInAnimation(this,R.anim.in_rightleft);
        viewFlipper.setOutAnimation(this,R.anim.out_rightleft);
        viewFlipper.setAutoStart(true);

       viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            private float startX;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        viewFlipper.stopFlipping();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if(startX-event.getX()>50)//向左滑动
                        {
                            viewFlipper.setInAnimation(getApplicationContext(),R.anim.in_rightleft);
                            viewFlipper.setOutAnimation(getApplicationContext(),R.anim.out_rightleft);
                            viewFlipper.showNext();
                        }
                        else if(event.getX()-startX>50) { //向右滑动
                            viewFlipper.setInAnimation(getApplicationContext(), R.anim.in_leftright);
                            viewFlipper.setOutAnimation(getApplicationContext(), R.anim.out_leftright);
                            viewFlipper.showPrevious();
                        }
                        else //点击事件
                        {
                            Intent intent = null;
                            Bundle bundle = null;
                            intent = new Intent(getApplicationContext(),SceneryShowActivity.class);
                            bundle = new Bundle();
                            bundle.putString("url", String.valueOf(viewFlipper.getDisplayedChild()));
                            intent.putExtras(bundle);
                            getApplicationContext().startActivity(intent);
                        }
                        viewFlipper.startFlipping();
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        if(cityName!=null)
            cityID.setText(cityName+"@"+cityCode);
        else
            cityID.setText("冷水江");
        super.onResume();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
                cityName = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                cityCode = data.getStringExtra(CityPickerActivity.KEY_PICKED_ID);
                //cityID.setText(cityName);
                /*
                修改viewFlipper的显示内容
                 */
            }
        }
    }


/*    private float startX;
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                viewFlipper.stopFlipping();
                break;
            case MotionEvent.ACTION_UP:
                if (event.getX() - startX>50) {// flip to right
                    viewFlipper.setInAnimation(this, R.anim.in_leftright);
                    viewFlipper.setOutAnimation(this, R.anim.out_leftright);
                    viewFlipper.showPrevious();
                } else if(startX-event.getX() >50){// flip to left
                    viewFlipper.setInAnimation(this, R.anim.in_rightleft);
                    viewFlipper.setOutAnimation(this, R.anim.out_rightleft);
                    viewFlipper.showNext();
                }
                //viewFlipper.startFlipping();
                break;

        }
        return super.onTouchEvent(event);
    }*/
}
