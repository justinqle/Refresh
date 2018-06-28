package com.justinqle.refresh.architecture;

import android.arch.paging.PagedListAdapter;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.justinqle.refresh.R;
import com.justinqle.refresh.models.listing.Post;
import com.justinqle.refresh.models.listing.Preview;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;

public class PostAdapter extends PagedListAdapter<Post, PostAdapter.ViewHolder> {

    private static final String TAG = "PostAdapter";

    private static final DiffUtil.ItemCallback<Post> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Post>() {
                @Override
                public boolean areItemsTheSame(Post oldItem, Post newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(Post oldItem, Post newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle());
                }
            };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subreddit;
        private TextView user;
        private TextView created_utc;
        private TextView num_comments;
        private TextView points;
        private ImageView thumbnail;
        private ImageButton upvote;
        private ImageButton downvote;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            subreddit = (TextView) v.findViewById(R.id.subreddit);
            user = (TextView) v.findViewById(R.id.user);
            created_utc = (TextView) v.findViewById(R.id.created_utc);
            num_comments = (TextView) v.findViewById(R.id.num_comments);
            points = (TextView) v.findViewById(R.id.points);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);
            upvote = v.findViewById(R.id.upvote);
            downvote = v.findViewById(R.id.downvote);
        }
    }

    public PostAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row_card, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = getItem(position);

        holder.title.setText(post.getTitle());
        holder.subreddit.setText(post.getSubreddit());
        holder.user.setText(post.getAuthor());
        holder.created_utc.setText(unixTimeToElapsed(post.getCreatedUtc()));
        holder.num_comments.setText(post.getNumComments() + " comments");
        holder.points.setText(toConciseThousands(post.getUps()));
        // TODO: Show thumbnail for gifs and videos(?)
        Preview preview = post.getPreview();
        if (preview == null) {
            holder.thumbnail.setVisibility(View.GONE);
        } else {
            String url = preview.getImages().get(0).getSource().getUrl();
            Picasso.get().load(url).fit().centerCrop().into(holder.thumbnail);
        }

        // OnClickListeners
        holder.upvote.setOnClickListener(v -> {
            if (holder.downvote.isActivated()) {
                holder.downvote.setActivated(false);
                // Resets color of upvote button
                holder.downvote.clearColorFilter();
            }
            if (!holder.upvote.isActivated()) {
                holder.upvote.setActivated(true);
                int highlight = ContextCompat.getColor(v.getContext(), R.color.upvote);
                // Sets color of upvote button
                holder.upvote.setColorFilter(highlight, PorterDuff.Mode.SRC_ATOP);
                // Sets color of points
                holder.points.setTextColor(highlight);
                // Animate points
                Animation expand = AnimationUtils.loadAnimation(v.getContext(), R.anim.expand);
                holder.points.startAnimation(expand);
            } else {
                holder.upvote.setActivated(false);
                // Resets color of upvote button
                holder.upvote.clearColorFilter();
                // Resets color of points
                holder.points.setTextColor(ContextCompat.getColor(v.getContext(), R.color.secondary_text));
            }
        });
        holder.downvote.setOnClickListener(v -> {
            if (holder.upvote.isActivated()) {
                holder.upvote.setActivated(false);
                // Resets color of upvote button
                holder.upvote.clearColorFilter();
            }
            if (!holder.downvote.isActivated()) {
                holder.downvote.setActivated(true);
                int highlight = ContextCompat.getColor(v.getContext(), R.color.downvote);
                // Sets color of upvote button
                holder.downvote.setColorFilter(highlight, PorterDuff.Mode.SRC_ATOP);
                // Sets color of points
                holder.points.setTextColor(highlight);
                // Animate points
                Animation conract = AnimationUtils.loadAnimation(v.getContext(), R.anim.contract);
                holder.points.startAnimation(conract);
            } else {
                holder.downvote.setActivated(false);
                // Resets color of upvote button
                holder.downvote.clearColorFilter();
                // Resets color of points
                holder.points.setTextColor(ContextCompat.getColor(v.getContext(), R.color.secondary_text));
            }
        });
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
}
