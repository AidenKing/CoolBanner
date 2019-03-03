package com.king.lib.banner.transformer;

import android.annotation.SuppressLint;
import android.view.View;

/**
 * Created by lm on 2016/7/8.
 */
public class DepthPageTransformer extends LMPageTransformer {
   private static float MIN_SCALE = 0.75f;
           @SuppressLint("NewApi")
           @Override
    public void transformPage(View view, float position) {
               int pageWidth = view.getWidth();
               if (position < -1) { // [-Infinity,-1)
                   // This page is way off-screen to the left.
                   view.setAlpha(0);
               } else if (position <= 0) { // [-1,0]
                   // Use the default slide transition when
                   // moving to the left page
                   view.setAlpha(1);
                   view.setTranslationX(0);
                   view.setScaleX(1);
                   view.setScaleY(1);
               } else if (position <= 1) { // (0,1]
                   // Fade the page out.
                   view.setAlpha(1 - position);
                   // Counteract the default slide transition
                   view.setTranslationX(pageWidth * -position);
                   // Scale the page down (between MIN_SCALE and 1)
                   float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
                           * (1 - Math.abs(position));
                   view.setScaleX(scaleFactor);
                   view.setScaleY(scaleFactor);
               } else { // (1,+Infinity]
                   // This page is way off-screen to the right.
                   view.setAlpha(0);
               }
           }

    @Override
    public void scrollInvisible(View view, float position) {

    }

    @Override
    public void scrollLeft(View view, float position) {

    }

    @Override
    public void scrollRight(View view, float position) {

    }


}