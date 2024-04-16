package com.example.itp4501_assignment;

import static android.content.ContentValues.TAG;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Background {
    Context context;
    LinearLayout linearLayout;
    DisplayMetrics metrics;
    Handler process = new Handler();
    CardPhotoSet photoSet;
    BgImgQueue bgImgQueue;
    Runnable run;
    int numOfBgImg = 0;

    public Background(Context context, LinearLayout linearLayout, DisplayMetrics metrics) {
        this.context = context;
        this.linearLayout = linearLayout;
        this.metrics = metrics;
        bgImgQueue = new BgImgQueue();
    }

    public void setBackground(String style) {
        int screenHeight = metrics.heightPixels;
        int screenWidth = metrics.widthPixels;

        //get background by style
        this.photoSet = new CardPhotoSet(style);

        LinearLayout layout = linearLayout;

        run = new Runnable() {
            @Override
            public void run() {
                //random image size
                int randomSize = 0;
                while (true) {
                    randomSize = (int) (Math.random() * 86);
                    if (randomSize >= 40)
                        break;
                }
                int imgSize = (int)(screenHeight * (randomSize * 0.001));
                //random start angle
                int angle = (int) (Math.random() * 361);
                //random appear location
                int randomTop = (int) (Math.random() * (screenHeight + 600));
                //play time 10 - 20
                int randomTime = 0;
                while (true) {
                    randomTime = (int) (Math.random() * 21);
                    if (randomTime >= 10)
                        break;
                }

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(imgSize, imgSize);

                //create a new ImageView
                ImageView bgImg = new ImageView(context);
                bgImg.setImageResource(photoSet.getBgImg()); //get image from photoSet
                bgImg.setLayoutParams(params);
                layout.addView(bgImg);
                bgImg.setElevation(1f);

                bgImgQueue.enQueue(bgImg);

                ObjectAnimator animatorRotation = ObjectAnimator.ofFloat(null, "rotation", angle, angle + 360);
                ObjectAnimator animatorX = ObjectAnimator.ofFloat(null, "x", -imgSize, (float) (screenWidth + screenWidth * 0.2));
                ObjectAnimator animatorY = ObjectAnimator.ofFloat(null, "y", randomTop, randomTop - 700);

                //new animation composed of three ObjectAnimators
                AnimatorSet animator = new AnimatorSet();
                animator.playTogether(animatorRotation, animatorX, animatorY);
                animator.setTarget(bgImg);
                animator.setDuration(randomTime * 1000);
                animator.start();

                //remove completed animation
                //max play time = 20s
                //20 / 0.5 = 40
                if (bgImgQueue.size() == 41) {
                    ImageView tmp = bgImgQueue.deQueue();
                    tmp.setImageDrawable(null);
                }
                process.postDelayed(this, 500); //callback //generate a background image every 0.5 seconds
            }
        };
        process.postDelayed(run, 500); //start generate background image
    }

    public void stopLoop() {
        process.removeCallbacks(run);
    }
}
