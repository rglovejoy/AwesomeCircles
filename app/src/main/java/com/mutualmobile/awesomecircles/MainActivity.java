package com.mutualmobile.awesomecircles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    private final ArrayList<Integer> _colorList = new ArrayList<>();
    private int _foregroundColorIndex;
    private int _backgroundColorIndex;

    private Resources _res;

    private AwesomeCircleView _awesomeCircleView;
    private View _mainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _res = getResources();
        _colorList.add(_res.getColor(R.color.red));
        _colorList.add(_res.getColor(R.color.green));
        _colorList.add(_res.getColor(R.color.blue));
        _colorList.add(_res.getColor(R.color.yellow));
        _colorList.add(_res.getColor(R.color.magenta));
        _colorList.add(_res.getColor(R.color.cyan));
        _colorList.add(_res.getColor(R.color.orange));
        _colorList.add(_res.getColor(R.color.white));

        _awesomeCircleView = (AwesomeCircleView) findViewById(R.id.awesomeCircleView);
        _mainView = findViewById(R.id.main);

        _backgroundColorIndex = 1;
        _foregroundColorIndex = 0;

        _mainView.setBackgroundColor(_colorList.get(_backgroundColorIndex));
        _awesomeCircleView.reset(_colorList.get(_foregroundColorIndex));
        _awesomeCircleView.setOnTouchListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Animator animator = _awesomeCircleView.exposeAnimator(event.getX(), event.getY());
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    _mainView.setBackgroundColor(getNextBackgroundColor());
                    _awesomeCircleView.reset(getNextForegroundColor());
                }
            });
            animator.start();
        }
        return false;
    }

    private int getNextBackgroundColor() {
        _backgroundColorIndex++;
        if (_backgroundColorIndex >= _colorList.size()) {
            _backgroundColorIndex = 0;
        }
        return _colorList.get(_backgroundColorIndex);
    }

    private int getNextForegroundColor() {
        _foregroundColorIndex++;
        if (_foregroundColorIndex >= _colorList.size()) {
            _foregroundColorIndex = 0;
        }
        return _colorList.get(_foregroundColorIndex);
    }

}
