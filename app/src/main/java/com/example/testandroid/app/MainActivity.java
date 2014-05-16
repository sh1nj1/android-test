package com.example.testandroid.app;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text1:
                mAnimator.setIntValues(100, 200);
                mAnimator.start();
                break;
            case R.id.text2:
                mAnimator2.setIntValues(100, 200);
                mAnimator2.start();
                break;
            case R.id.text3:
                int duration = 5000;
                try {
                    duration = Integer.parseInt(String.valueOf(mTextDuration.getText()));
                } catch (Exception e) {e.printStackTrace();}
                mAnimator.setDuration(duration);
                mAnimator2.setDuration(duration);
                break;
        }
    }

    static class AnimatedPointView {
        private final TextView tv;
        private int point;

        public AnimatedPointView(TextView tv) {
            this.tv = tv;
        }

        public void setPoint(int point) {
            this.point = point;
            tv.setText(String.valueOf(point));
            if (BuildConfig.DEBUG) Log.d(TAG, "point="+point);
        }

        public int getPoint() {
            return Integer.parseInt(tv.getText().toString());
        }
    }

    ObjectAnimator mAnimator;

    com.nineoldandroids.animation.ObjectAnimator mAnimator2;

    EditText mTextDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button text = (Button) findViewById(R.id.text1);
        text.setOnClickListener(this);

        final Button text2 = (Button) findViewById(R.id.text2);
        text2.setOnClickListener(this);

        final Button text3 = (Button) findViewById(R.id.text3);
        text3.setOnClickListener(this);

        mTextDuration = (EditText) findViewById(R.id.text_duration);

        mAnimator = createAnimation(text);
        mAnimator.start();

        mAnimator2 = createNineOldAnimation(text2);
        mAnimator2.start();
    }

    private ObjectAnimator createAnimation(TextView text) {
        ObjectAnimator anim = ObjectAnimator.ofInt(new AnimatedPointView(text), "point", 0, 100);
        anim.setEvaluator(new TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "start=" + startValue + ", end=" + endValue + ", fraction=" + fraction);
                return startValue + Math.round(fraction * (endValue - startValue));
            }
        });
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(5000);
        return anim;
    }

    private com.nineoldandroids.animation.ObjectAnimator createNineOldAnimation(TextView text) {
        com.nineoldandroids.animation.ObjectAnimator anim = com.nineoldandroids.animation.ObjectAnimator.ofInt(new AnimatedPointView(text), "point", 0, 100);
        anim.setEvaluator(new com.nineoldandroids.animation.TypeEvaluator<Integer>() {
            @Override
            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "start=" + startValue + ", end=" + endValue + ", fraction=" + fraction);
                return startValue + Math.round(fraction * (endValue - startValue));
            }
        });
        anim.setInterpolator(new LinearInterpolator());
        anim.setDuration(5000);
        return anim;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
