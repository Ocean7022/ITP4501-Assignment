package com.example.itp4501_assignment;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {
    ListView lvRanking;
    Background bg;
    DownloadTask task = null;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        lvRanking = findViewById(R.id.lvRankingLocal);
        lvRanking.setElevation(2f);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        if (task == null || task.getStatus().equals(AsyncTask.Status.FINISHED)) {
            progressDialog.show();

            task = new DownloadTask();
            task.execute("https://ranking-mobileasignment-wlicpnigvf.cn-hongkong.fcapp.run");
        }

        bg = new Background(this, findViewById(R.id.linearRanking), getResources().getDisplayMetrics());
        bg.setBackground(getIntent().getStringExtra("style"));
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... values) {
            InputStream inputStream = null;
            String result = "";
            URL url = null;

            try {
                url = new URL(values[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                inputStream = con.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while ((line = bufferedReader.readLine()) != null)
                    result += line;

                inputStream.close();
            }
            catch (Exception e) {
                errorMsgBox();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONArray ranks = new JSONArray(result);
                String[] name = new String[ranks.length()];
                int[] moves = new int[ranks.length()];

                for (int i = 0; i < ranks.length(); i++) {
                    JSONObject jObj = ranks.getJSONObject(i);
                    name[i] = jObj.getString("Name");
                    moves[i] = jObj.getInt("Moves");
                }
                progressDialog.dismiss();
                fillListView(name, moves);
            }
            catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public void fillListView(String[] name, int[] moves) {
        int tmpMove = 0;
        String tmpName = "";

        //sort the data
        for (int i = 0; i < moves.length - 1; i++) {
            for (int x = 0; x < moves.length - (i + 1); x++) {
                if (moves[x] > moves[x + 1]) {
                    tmpMove = moves[x + 1];
                    moves[x + 1] = moves[x];
                    moves[x] = tmpMove;
                    tmpName = name[x + 1];
                    name[x + 1] = name[x];
                    name[x] = tmpName;
                }
            }
        }

        ArrayList<Ranking> rankings = new ArrayList<>();
        for (int i = 0; i < name.length; i++) //put object in ArrayList
            rankings.add(new Ranking(name[i], moves[i], i));

        //fill ListView with custom Adapter
        lvRanking.setAdapter(new RankingAdapter(this, rankings));
    }

    public void errorMsgBox() {
        progressDialog.dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Error getting ranking data!");
        builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (task == null || task.getStatus().equals(AsyncTask.Status.FINISHED)) {
                    progressDialog.show();

                    task = new DownloadTask();
                    task.execute("https://ranking-mobileasignment-wlicpnigvf.cn-hongkong.fcapp.run");
                }
            }
        });
        builder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (task == null || task.getStatus().equals(AsyncTask.Status.FINISHED)) {
                    onBackPressed();
                }
            }
        });
        runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}