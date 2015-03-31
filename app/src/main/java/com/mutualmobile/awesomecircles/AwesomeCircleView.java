package com.mutualmobile.awesomecircles;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by ronald.lovejoy on 3/26/15.
 */
public class AwesomeCircleView extends View {
    private final static int ANIMATION_DURATION = 400;

    private final Paint _brush = new Paint();
    private final Path _path = new Path();
    private final AccelerateDecelerateInterpolator _interpolator =
            new AccelerateDecelerateInterpolator();

    private float _cx;
    private float _cy;
    private float _radius;

    public AwesomeCircleView(Context context) {
        super(context);
        init();
    }

    public AwesomeCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AwesomeCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setColor(int color) {
        _brush.setColor(color);
    }

    private void init() {
        _brush.setAntiAlias(true);
        _brush.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void setRadius(float radius) {
        _radius = radius;
        invalidate();
    }

    public void reset(int color) {
        setColor(color);
        setRadius(0);
    }

    public Animator exposeAnimator(float x, float y) {
        _cx = x;
        _cy = y;

        float width = getWidth();
        float height = getHeight();

        float finalRadius = (float) Math.max(
                Math.max(Math.hypot(x, y), Math.hypot(width - x, y)),
                Math.max(Math.hypot(x, height - y), Math.hypot(width - x, height - y)));

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "radius", 0, finalRadius);
        animator.setDuration(ANIMATION_DURATION);
        animator.setInterpolator(_interpolator);
        return animator;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        _path.reset();

        _path.addRect(0, 0, (float) getWidth(), (float) getHeight(), Path.Direction.CW);
        _path.addCircle(_cx, _cy, _radius, Path.Direction.CCW);

        canvas.drawPath(_path, _brush);
    }
}
