package com.justinqle.refresh.architecture;

import android.app.Activity;
import android.arch.paging.PagedListAdapter;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.justinqle.refresh.GlideApp;
import com.justinqle.refresh.R;
import com.justinqle.refresh.activities.AccountLogin;
import com.justinqle.refresh.models.listing.Post;
import com.justinqle.refresh.models.listing.Preview;

import java.math.BigDecimal;

public class PostAdapter extends PagedListAdapter<Post, PostAdapter.ViewHolder> {

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

    public PostAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_row_card, parent, false);
        ViewHolder vh = new ViewHolder(view);
        // OnClickListeners
        vh.upvote.setOnClickListener(v -> {
            if (PreferenceManager.getDefaultSharedPreferences(vh.upvote.getContext()).getBoolean("logged_in", false)) {
                if (vh.downvote.isActivated()) {
                    vh.downvote.setActivated(false);
                    // Resets color of upvote button
                    vh.downvote.clearColorFilter();
                }
                if (!vh.upvote.isActivated()) {
                    vh.upvote.setActivated(true);
                    int highlight = ContextCompat.getColor(v.getContext(), R.color.upvote);
                    // Sets color of upvote button
                    vh.upvote.setColorFilter(highlight, PorterDuff.Mode.SRC_ATOP);
                    // Sets color of points
                    vh.points.setTextColor(highlight);
                    // Animate points
                    Animation expand = AnimationUtils.loadAnimation(v.getContext(), R.anim.expand);
                    vh.points.startAnimation(expand);
                } else {
                    vh.upvote.setActivated(false);
                    // Resets color of upvote button
                    vh.upvote.clearColorFilter();
                    // Resets color of points
                    vh.points.setTextColor(ContextCompat.getColor(v.getContext(), R.color.secondary_text));
                }
            } else {
                Snackbar.make(parent, R.string.logged_out, Snackbar.LENGTH_LONG).setAction(R.string.log_in,
                        v1 -> ((Activity) parent.getContext()).startActivityForResult(new Intent(parent.getContext(), AccountLogin.class), 1))
                        .show();
            }
        });
        vh.downvote.setOnClickListener(v -> {
            if (PreferenceManager.getDefaultSharedPreferences(vh.downvote.getContext()).getBoolean("logged_in", false)) {
                if (vh.upvote.isActivated()) {
                    vh.upvote.setActivated(false);
                    // Resets color of upvote button
                    vh.upvote.clearColorFilter();
                }
                if (!vh.downvote.isActivated()) {
                    vh.downvote.setActivated(true);
                    int highlight = ContextCompat.getColor(v.getContext(), R.color.downvote);
                    // Sets color of upvote button
                    vh.downvote.setColorFilter(highlight, PorterDuff.Mode.SRC_ATOP);
                    // Sets color of points
                    vh.points.setTextColor(highlight);
                    // Animate points
                    Animation conract = AnimationUtils.loadAnimation(v.getContext(), R.anim.contract);
                    vh.points.startAnimation(conract);
                } else {
                    vh.downvote.setActivated(false);
                    // Resets color of upvote button
                    vh.downvote.clearColorFilter();
                    // Resets color of points
                    vh.points.setTextColor(ContextCompat.getColor(v.getContext(), R.color.secondary_text));
                }
            } else {
                Snackbar.make(parent, R.string.logged_out, Snackbar.LENGTH_LONG).setAction(R.string.log_in,
                        v1 -> ((Activity) parent.getContext()).startActivityForResult(new Intent(parent.getContext(), AccountLogin.class), 1))
                        .show();
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = getItem(position);
        if (post != null) {
            holder.title.setText(post.getTitle());
            holder.subreddit.setText(post.getSubreddit());
            holder.user.setText(post.getAuthor());
            holder.created_utc.setText(unixTimeToElapsed(post.getCreatedUtc()));
            holder.num_comments.setText(holder.num_comments.getContext().getString(R.string.comments, post.getNumComments()));
            holder.points.setText(toConciseThousands(post.getUps()));
            // TODO: Show thumbnail for gifs and videos(?)
            Preview preview = post.getPreview();
            if (preview == null) {
                holder.thumbnail.setVisibility(View.GONE);
            } else {
                String url = preview.getImages().get(0).getSource().getUrl();
                GlideApp.with(holder.thumbnail.getContext()).load(url).centerCrop().transition(DrawableTransitionOptions.withCrossFade()).into(holder.thumbnail);
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView subreddit;
        private TextView user;
        private TextView created_utc;
        private TextView num_comments;
        private TextView points;
        private ImageView thumbnail;
        private ImageButton upvote;
        private ImageButton downvote;

        ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            subreddit = v.findViewById(R.id.subreddit);
            user = v.findViewById(R.id.user);
            created_utc = v.findViewById(R.id.created_utc);
            num_comments = v.findViewById(R.id.num_comments);
            points = v.findViewById(R.id.points);
            thumbnail = v.findViewById(R.id.thumbnail);
            upvote = v.findViewById(R.id.upvote);
            downvote = v.findViewById(R.id.downvote);
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
}
