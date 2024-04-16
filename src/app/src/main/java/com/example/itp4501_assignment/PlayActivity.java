package com.example.itp4501_assignment;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends AppCompatActivity {
    Card[] cards;
    Anima anima;
    DatabaseFunction db;
    Background bg;
    Handler process = new Handler();
    String style, playerName;
    TextView tvMove;
    ImageView[] imCardBack , imCardFront;
    LinearLayout linear;
    Space sp1;
    Button buRestart;
    int clickedCard = 0, firstCard = 0, secondCard = 0, moves = 0, matchedCards = 0, sumHeight = 0;
    boolean animationIsPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        anima = new Anima();
        sp1 = findViewById(R.id.sp1);
        buRestart = findViewById(R.id.buRestart);
        tvMove = findViewById(R.id.tvMove);
        linear = findViewById(R.id.linear);
        db = new DatabaseFunction();

        Intent intent = getIntent();
        playerName = intent.getStringExtra("playerName");
        style = intent.getStringExtra("style");

        tvMove.setElevation(2f);
        linear.setElevation(2f);

        imCardBack = new ImageView[]{
                findViewById(R.id.cardBack0),
                findViewById(R.id.cardBack1),
                findViewById(R.id.cardBack2),
                findViewById(R.id.cardBack3),
                findViewById(R.id.cardBack4),
                findViewById(R.id.cardBack5),
                findViewById(R.id.cardBack6),
                findViewById(R.id.cardBack7)
        };

        imCardFront = new ImageView[]{
                findViewById(R.id.cardFront0),
                findViewById(R.id.cardFront1),
                findViewById(R.id.cardFront2),
                findViewById(R.id.cardFront3),
                findViewById(R.id.cardFront4),
                findViewById(R.id.cardFront5),
                findViewById(R.id.cardFront6),
                findViewById(R.id.cardFront7)
        };

        for (int i = 0; i < imCardBack.length; i++) {
            final int index = i;
            imCardBack[i].setElevation(5f);
            imCardFront[i].setElevation(4f);

            imCardBack[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //get user click index
                    clickCard(index);
                }
            });
        }

        //background object
        bg = new Background(this, findViewById(R.id.linearPlay), getResources().getDisplayMetrics());
        //set background by style
        bg.setBackground(style);

        setGameAreaSize();
        newGame(style);
    }

    //main game logic
    public void clickCard(int index) {
        //can not flip card when animation playing
        if (animationIsPlaying)
            return;

        //can not flip more than two card
        if (clickedCard > 1)
            return;

        //can not flip opened card
        if(cards[index].getOpen())
            return;

        //save first card
        if (clickedCard == 0) {
            firstCard = index;
            cards[firstCard].setOpen(true);
            anima.openCard(imCardBack[firstCard]);
            clickedCard++;
            return;
        }

        //save second card
        if (clickedCard == 1) {
            secondCard = index;
            cards[secondCard].setOpen(true);
            anima.openCard(imCardBack[secondCard]);
            clickedCard++;
            moves++;
        }

        //tow cards not match
        if (cards[firstCard].getCardNum() != cards[secondCard].getCardNum()) {
            tvMove.setText("Moves : " + String.valueOf(moves));

            Runnable cardNotMatch = new Runnable() {
                @Override
                public void run() {
                    cards[firstCard].setOpen(false);
                    cards[secondCard].setOpen(false);
                    anima.closeCard(imCardBack[firstCard], imCardBack[secondCard]);
                    stopClickCard(900);
                    clickedCard = 0;
                }
            };
            process.postDelayed(cardNotMatch, 900);
        }
        //two cards match
        else {
            tvMove.setText("Moves : " + String.valueOf(moves));
            matchedCards++;

            Runnable cardNotMatch = new Runnable() {
                @Override
                public void run() {
                    ImageView[] images = {imCardBack[firstCard], imCardBack[secondCard], imCardFront[firstCard], imCardFront[secondCard]};
                    anima.hideCard(images);
                    clickedCard = 0;
                    stopClickCard(200);
                }
            };
            process.postDelayed(cardNotMatch, 1800);
        }

        //end game
        if (matchedCards == 4) {
            //display continue button
            Runnable displayContinueButton = new Runnable() {
                @Override
                public void run() {
                    buRestart.setVisibility(View.VISIBLE);
                }
            };
            process.postDelayed(displayContinueButton, 2500);

            //show game result
            tvMove.setText("Finish! Your Moves : " + moves);

            //set Anonymous if the player does not enter name
            if (playerName.length() == 0)
                playerName = "Anonymous";

            //add record to database
            db.addGameRecord(playerName, moves, style);
        }
    }

    public void stopClickCard(int millis) {
        animationIsPlaying = true;
        Runnable stopClickCard = new Runnable() {
            @Override
            public void run() {
                animationIsPlaying = false;
            }
        };
        process.postDelayed(stopClickCard, millis);
    }

    public void newGame(String style) {
        buRestart.setVisibility(View.INVISIBLE);
        CardPhotoSet photoSet = new CardPhotoSet(style);
        int[] photos = photoSet.getPhotoSet(); //get 4 photo
        cards = new Card[8];
        moves = 0;
        int number = 0;

        //random card number
        for (int numOfCard = 0; numOfCard < cards.length;) {
            number = (int) (Math.random() * 4); //random a new number 0 - 3
            int count = 0;

            //check duplicate
            for (int i = 0; i < numOfCard; i++){
                if (cards[i].getCardNum() == number)
                    count++;
                if (count == 2)
                    break;
            }

            //random again if the number duplicate
            if (count == 2)
                continue;

            cards[numOfCard] = new Card(number, photos[number]);
            imCardFront[numOfCard].setImageResource(photos[number]); //set imageView photo
            numOfCard++;
        }

        //use at random display cards
        int[] randomList = new int[8];
        for (int i = 0; i < randomList.length;) {
            number = (int) (Math.random() * 8); //random a new number 0 - 3
            boolean isDuplicate = false;

            //check duplicate
            for (int x = 0; x < i; x++) {
                if (randomList[x] == number) {
                    isDuplicate = true;
                    break;
                }
            }

            //random again if the number duplicate
            if (isDuplicate)
                continue;

            randomList[i] = number;
            i++;
        }

        //display all cards
        for (int numOfCard = 0; numOfCard < cards.length; numOfCard++)
            anima.popUpCard(imCardBack[randomList[numOfCard]], imCardFront[randomList[numOfCard]], numOfCard);
    }

    public void continueGame(View v) {
        clickedCard = 0;
        firstCard = 0;
        secondCard = 0;
        moves = 0;
        matchedCards = 0;
        tvMove.setText("Moves : 0");
        newGame(style);
    }

    public void setGameAreaSize() {

        //get sp1 height
        ViewTreeObserver observerSp1 = sp1.getViewTreeObserver();
        observerSp1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sumHeight += sp1.getHeight();
                sp1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        //get tvMoves height
        ViewTreeObserver observerTv = tvMove.getViewTreeObserver();
        observerTv.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sumHeight += tvMove.getHeight();
                tvMove.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        //get buRestart height
        ViewTreeObserver observerBu = buRestart.getViewTreeObserver();
        observerBu.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sumHeight += buRestart.getHeight();
                buRestart.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int height = metrics.heightPixels - sumHeight - (int)(20 * metrics.density);

                LinearLayout linear = findViewById(R.id.linear);
                ViewGroup.LayoutParams params = linear.getLayoutParams();
                params.height = height;
                linear.setLayoutParams(params);
            }
        });
    }
}

