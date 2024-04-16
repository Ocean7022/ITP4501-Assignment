package com.example.itp4501_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvTitle;
    ImageView imgButPlay, imgButRank, imgButRecord, imgButClose;
    EditText edPlayerName;
    Spinner spStyle;
    String style = "";
    Background bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        imgButPlay = findViewById(R.id.imgButPlay);
        imgButRank = findViewById(R.id.imgButRank);
        imgButRecord = findViewById(R.id.imgButRecord);
        imgButClose = findViewById(R.id.imgButClose);
        edPlayerName = findViewById(R.id.edPlayerName);
        spStyle = findViewById(R.id.spStyle);

        tvTitle.setElevation(2f);
        imgButPlay.setElevation(2f);
        imgButRank.setElevation(2f);
        imgButRecord.setElevation(2f);
        imgButClose.setElevation(2f);
        edPlayerName.setElevation(2f);
        spStyle.setElevation(2f);

        //background object
        bg = new Background(this, findViewById(R.id.linearMain), getResources().getDisplayMetrics());

        String[] items = {"Number", "艦これ", "ウマ娘"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spStyle.setAdapter(adapter);

        //get user selected style
        spStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                style = parent.getItemAtPosition(position).toString();
                bg.stopLoop(); //stop previous background
                bg.setBackground(style); //start new background by style
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //click to play game page
        imgButPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                intent.putExtra("playerName", edPlayerName.getText().toString());
                intent.putExtra("style", style);
                startActivity(intent);
            }
        });

        //click to ranking page
        imgButRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RankingActivity.class);
                intent.putExtra("style", style);
                startActivity(intent);
            }
        });

        //click to records page
        imgButRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordsActivity.class);
                intent.putExtra("style", style);
                startActivity(intent);
            }
        });

        //click to close app
        imgButClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }
}