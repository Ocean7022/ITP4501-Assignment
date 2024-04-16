package com.example.itp4501_assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RankingAdapter extends ArrayAdapter<Ranking> {
    private Context context;
    private List<Ranking> data;

    public RankingAdapter(Context context, List<Ranking> data) {
        super(context, R.layout.items_view, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.items_view, parent, false);

        TextView tvName = rowView.findViewById(R.id.tvName);
        TextView tvInfo = rowView.findViewById(R.id.tvInfo);
        ImageView imageView = rowView.findViewById(R.id.imageView);

        Ranking ranking = data.get(position);
        tvName.setText(ranking.getName());
        tvInfo.setText(ranking.getInfo());
        if (ranking.getNumOfRanking() == 0)
            imageView.setImageResource(R.drawable.no1); //set No.1 image
        else if (ranking.getNumOfRanking() == 1)
            imageView.setImageResource(R.drawable.no2); //set No.2 image
        else if (ranking.getNumOfRanking() == 2)
            imageView.setImageResource(R.drawable.no3); //set No.3 image

        return rowView;
    }
}
