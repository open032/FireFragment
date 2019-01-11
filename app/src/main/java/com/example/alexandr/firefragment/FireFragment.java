package com.example.alexandr.firefragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class FireFragment extends Fragment implements View.OnTouchListener{
    private float x;
    private float bAnimation;
    private float y;
    private float hAnimation;
    private float l;
    private float radian;
    private float screenHeight = 0f;
    private float xPosition;
    private float yPosition;
    private float centerX;
    private float centerY;
    private FrameLayout frameLayout;
    private static final long DEFAULT_ANIMATION_DURATION = 2500L;
    private ImageView sausage;
    private static final String TAG = "myLogs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fire_fragment, container, false);

        sausage = rootView.findViewById(R.id.cat_image_view);
        frameLayout = rootView.findViewById(R.id.frame_layout);
        frameLayout.setOnTouchListener(this);
        frameLayout.setOnClickListener(onClickListener);

        return rootView;
    }

    @Override
    public boolean onTouch (View v, MotionEvent event){
        xPosition = event.getX();
        yPosition = event.getY();
        centerY = screenHeight - 225;

        x = centerX - xPosition;
        y = centerY - yPosition;
        l = (float) Math.sqrt((y * y) + (x * x));
        radian = (float) Math.asin(y/l);
        bAnimation = (float) Math.cos(radian) * 3500;
        hAnimation = (float) Math.sin(radian) * 3500;

        if(xPosition >= centerX){
            x = xPosition - centerX;
            y = centerY - yPosition;
            l = (float) Math.sqrt((y * y) + (x * x));

            radian = (float) Math.asin(y/l);
            bAnimation = -(float) Math.cos(radian) * 3500;
            Log.d(TAG, " bAnimation = " + bAnimation);
            hAnimation = (float) Math.sin(radian) * 3500;
        }


//            Log.d(TAG, " b = " + bLeft);
//            Log.d(TAG, " h = " + h);
//            Log.d(TAG, " l = " + l);
//            Log.d(TAG, " radian = " + radian);


        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = Float.valueOf(displayMetrics.heightPixels);
        centerX = Float.valueOf(displayMetrics.widthPixels) / 2;

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            ValueAnimator valueAnimatorX = ValueAnimator.ofFloat(0f, -bAnimation);
            Log.d(TAG, " bAnimation = " + bAnimation);
            ValueAnimator valueAnimatorY = ValueAnimator.ofFloat(0f, -hAnimation);

            valueAnimatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float)valueAnimator.getAnimatedValue();

                    //Log.d(TAG, "myLogs" + " y = " + yPosition);
                    //Log.d(TAG, "myLogs" + " center = " + center);
                    sausage.setTranslationY(value);

                }
            });
            valueAnimatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float)valueAnimator.getAnimatedValue();

                    //Log.d(TAG, "myLogs" + " x = " + xPosition);
                    sausage.setTranslationX(value);
                }
            });

            valueAnimatorY.setInterpolator(new LinearInterpolator());
            valueAnimatorY.setDuration(DEFAULT_ANIMATION_DURATION);

            valueAnimatorY.start();

            valueAnimatorX.setInterpolator(new LinearInterpolator());
            valueAnimatorX.setDuration(DEFAULT_ANIMATION_DURATION);

            valueAnimatorX.start();

        }
    };


}