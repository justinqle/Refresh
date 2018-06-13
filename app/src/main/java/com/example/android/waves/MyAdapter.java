package com.example.android.waves;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.waves.models.Child;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Child> children;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subreddit;
        private TextView user;
        private TextView created_utc;
        private TextView num_comments;

        private ImageView thumbnail;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            subreddit = (TextView) v.findViewById(R.id.subreddit);
            user = (TextView) v.findViewById(R.id.user);
            created_utc = (TextView) v.findViewById(R.id.created_utc);
            num_comments = (TextView) v.findViewById(R.id.num_comments);
        }
    }

    public MyAdapter(List<Child> children) {
        this.children = children;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(children.get(position).getData().getTitle());
        holder.subreddit.setText(children.get(position).getData().getSubreddit());
        holder.user.setText(children.get(position).getData().getAuthor());
        holder.created_utc.setText(unixTimeToElapsed(children.get(position).getData().getCreatedUtc()));
        holder.num_comments.setText(children.get(position).getData().getNumComments() + " comments");
    }

    // Could use some optimization
    private String unixTimeToElapsed(long unixTime) {
        long currentUnixTime = System.currentTimeMillis() / 1000;
        long elapsedTime = currentUnixTime - unixTime;
        if (elapsedTime < 60) {
            return elapsedTime + "s";
        } else if (elapsedTime < 3600) {
            return (elapsedTime / 60) + "m";
        } else if (elapsedTime < 86400) {
            return (elapsedTime / 3600) + "h";
        } else if (elapsedTime < 604800) {
            return (elapsedTime / 86400) + "d";
        } else if (elapsedTime < 2628000) {
            return (elapsedTime / 604800) + "w";
        } else if (elapsedTime < 31540000) {
            return (elapsedTime / 2628000) + "m";
        } else {
            return (elapsedTime / 31540000) + "y";
        }
    }

    @Override
    public int getItemCount() {
        return children.size();
    }
}
