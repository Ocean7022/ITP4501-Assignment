package com.example.itp4501_assignment;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class Anima {
    Handler process;

    public Anima() {
        process = new Handler();
    }

    public void openCard(ImageView image) {
        AnimatorSet anima = (AnimatorSet) AnimatorInflater.loadAnimator(image.getContext(), R.animator.flip_card);

        anima.setTarget(image);
        anima.start();

        Runnable showCard = new Runnable() {
            @Override
            public void run() {
                image.setElevation(3f);
            }
        };
        process.postDelayed(showCard, 400);
    }

    public void closeCard(ImageView imageFirst, ImageView imageSecond) {
        AnimatorSet animaFirst = (AnimatorSet) AnimatorInflater.loadAnimator(imageFirst.getContext(), R.animator.flip_card);
        AnimatorSet animaSecond = (AnimatorSet) AnimatorInflater.loadAnimator(imageSecond.getContext(), R.animator.flip_card);

        animaFirst.setTarget(imageFirst);
        animaSecond.setTarget(imageSecond);
        animaFirst.reverse();
        animaSecond.reverse();

        Runnable hideCard = new Runnable() {
            @Override
            public void run() {
                imageFirst.setElevation(5f);
                imageSecond.setElevation(5f);
            }
        };
        process.postDelayed(hideCard, 500);
    }

    public void popUpCard(ImageView imageBack, ImageView imageFront, int numOfCard) {
        AnimatorSet animaPopup = (AnimatorSet) AnimatorInflater.loadAnimator(imageBack.getContext(), R.animator.pop_up_card);
        AnimatorSet animaShow = (AnimatorSet) AnimatorInflater.loadAnimator(imageBack.getContext(), R.animator.show);
        AnimatorSet animaShow2 = (AnimatorSet) AnimatorInflater.loadAnimator(imageFront.getContext(), R.animator.show);
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.playTogether(animaPopup, animaShow);

        animatorSet.setStartDelay(numOfCard * 200);
        animatorSet.setTarget(imageBack);
        animatorSet.start();

        animaShow2.setStartDelay((numOfCard * 200) + 1000);
        animaShow2.setTarget(imageFront);
        animaShow2.start();
    }

    public void hideCard(ImageView[] images) {
        AnimatorSet[] animaHide = new AnimatorSet[images.length];

        for (int i = 0; i < images.length; i++) {
            animaHide[i] = (AnimatorSet) AnimatorInflater.loadAnimator(images[i].getContext(), R.animator.hide);
            animaHide[i].setTarget(images[i]);
            animaHide[i].start();
        }

        Runnable hideCard = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 2; i++) {
                    images[i].setElevation(5f);
                }
            }
        };
        process.postDelayed(hideCard, 500);
    }
}
