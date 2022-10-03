package com.canhmai.ailatrieuphu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.canhmai.ailatrieuphu.R;
import com.canhmai.ailatrieuphu.db.entities.HighScore;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private final Context context;
    private final List<HighScore> storyList;


    public RecycleViewAdapter(Context context, List<HighScore> storyList) {
        this.context = context;
        this.storyList = storyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //anh xa layout item va tra ve mot viewholder chua item do
        View view = LayoutInflater.from(context).inflate(R.layout.item_highscore, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (storyList.size() > 1) {
            Collections.sort(storyList, new Comparator<HighScore>() {
                @Override
                public int compare(HighScore o1, HighScore o2) {
                    return o2.scoreUser - o1.scoreUser;
                }
            });
        }
        HighScore highScore = storyList.get(position);

        holder.textView.setText(highScore.getName());
        String text = formatCurrency(highScore.scoreUser);
        holder.textViewmoney.setText(text);
        holder.vnd.setText(R.string.vnd);

    }


    private String formatCurrency(int scoreUser) {
        StringBuilder text = new StringBuilder();

        while (scoreUser >= 1000) {
            int div = scoreUser % 1000;
            if (div < 10) {
                text.insert(0, ",00" + div);
            } else if (div > 10 & div < 100) {
                text.insert(0, ",0" + div);
            } else if (div > 100) {
                text.insert(0, "," + div);
            }
            scoreUser /= 1000;
        }
        text.insert(0, scoreUser);
        return text.toString();
    }

    @Override
    public int getItemCount() {
        return storyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView textViewmoney;
        TextView vnd;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tv_rank_name);
            textViewmoney = itemView.findViewById(R.id.tv_rank_money);
            vnd = itemView.findViewById(R.id.vnd);

        }
    }
}
