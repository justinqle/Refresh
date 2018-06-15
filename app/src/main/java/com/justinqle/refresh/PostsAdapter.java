package com.justinqle.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.justinqle.refresh.models.Post;
import com.justinqle.refresh.models.Preview;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private static final String TAG = "PostsAdapter";

    private List<Post> posts;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subreddit;
        private TextView user;
        private TextView created_utc;
        private TextView num_comments;
        private TextView points;

        private ImageView thumbnail;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            subreddit = (TextView) v.findViewById(R.id.subreddit);
            user = (TextView) v.findViewById(R.id.user);
            created_utc = (TextView) v.findViewById(R.id.created_utc);
            num_comments = (TextView) v.findViewById(R.id.num_comments);
            points = (TextView) v.findViewById(R.id.points);

            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
        }
    }

    public PostsAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public PostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.title.setText(post.getTitle());
        holder.subreddit.setText(post.getSubreddit());
        holder.user.setText(post.getAuthor());
        holder.created_utc.setText(unixTimeToElapsed(post.getCreatedUtc()));
        holder.num_comments.setText(post.getNumComments() + " comments");
        holder.points.setText(toConciseThousands(post.getUps()));

        // TODO: Show thumbnail for gifs and videos(?)
        Preview preview = post.getPreview();
        if (preview != null) {
            String url = preview.getImages().get(0).getSource().getUrl();
            Picasso.get().load(url).resize(holder.thumbnail.getMaxWidth(), holder.thumbnail.getMaxHeight()).centerCrop().into(holder.thumbnail);
        }
    }

    // TODO Could modfiy a bit for numbers >= 100,0000
    private String toConciseThousands(int number) {
        if (number >= 10000) {
            number /= 100;
            BigDecimal bigDecimal = new BigDecimal(number);
            return bigDecimal.movePointLeft(1).toString() + "k";
        } else {
            return String.valueOf(number);
        }
    }

    // TODO Could use some optimization
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
        return posts.size();
    }
}
