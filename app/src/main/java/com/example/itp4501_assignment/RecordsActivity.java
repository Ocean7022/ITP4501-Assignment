package com.example.itp4501_assignment;


import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;

public class RecordsActivity extends AppCompatActivity {
    DatabaseFunction db;
    Background bg;
    Space spR1;
    TextView tvR;
    Button buSort;
    ListView lvRankingLocal;
    int sumHeight = 0;
    boolean isSort = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        db = new DatabaseFunction();
        spR1 = findViewById(R.id.spR1);
        tvR = findViewById(R.id.tvR);
        buSort = findViewById(R.id.buSort);
        lvRankingLocal = findViewById(R.id.lvRankingLocal);
        lvRankingLocal.setElevation(2f);
        lvRankingLocal.setAdapter(new RankingAdapter(this, db.getAllGameLog(false)));
        setListViewAreaSize();

        bg = new Background(this, findViewById(R.id.linearRecords), getResources().getDisplayMetrics());
        bg.setBackground(getIntent().getStringExtra("style"));
    }

    public void fillListView(View v) {
        if (!isSort) { //get records sort by moves
            lvRankingLocal.setAdapter(new RankingAdapter(this, db.getAllGameLog(true)));
            buSort.setText("Default");
            isSort = true;
        } else { //get records sort by play time
            lvRankingLocal.setAdapter(new RankingAdapter(this, db.getAllGameLog(false)));
            buSort.setText("Sort By Moves");
            isSort = false;
        }
    }

    public void setListViewAreaSize() {
        //get spR1 height
        ViewTreeObserver observerSpR1 = spR1.getViewTreeObserver();
        observerSpR1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sumHeight += spR1.getHeight();
                spR1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        //get tvR height
        ViewTreeObserver observerTvR = tvR.getViewTreeObserver();
        observerTvR.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sumHeight += tvR.getHeight();
                tvR.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        //get buSort height
        ViewTreeObserver observerBuSort = buSort.getViewTreeObserver();
        observerBuSort.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                sumHeight += buSort.getHeight();
                buSort.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int height = metrics.heightPixels - sumHeight - (int)(20 * metrics.density);

                ViewGroup.LayoutParams params = lvRankingLocal.getLayoutParams();
                params.height = height;
                lvRankingLocal.setLayoutParams(params);
            }
        });
    }
}