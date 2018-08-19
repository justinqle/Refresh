package com.justinqle.refresh.architecture;

import android.arch.paging.PagedListAdapter;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
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

import net.dean.jraw.models.Submission;
import net.dean.jraw.models.SubmissionPreview;

public class SubmissionsAdapter extends PagedListAdapter<Submission, SubmissionsAdapter.ViewHolder> {

    private static final char[] SUFFIXES = {'k', 'm', 'g', 't', 'p', 'e'};

    private static final DiffUtil.ItemCallback<Submission> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Submission>() {
                @Override
                public boolean areItemsTheSame(Submission oldItem, Submission newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(Submission oldItem, Submission newItem) {
                    return oldItem.getTitle().equals(newItem.getTitle()) &&
                            oldItem.getSubreddit().equals(newItem.getSubreddit()) &&
                            oldItem.getAuthor().equals(newItem.getAuthor()) &&
                            oldItem.getCreated().equals(newItem.getCreated()) &&
                            oldItem.getCommentCount().equals(newItem.getCommentCount()) &&
                            oldItem.getScore() == newItem.getScore() &&
                            oldItem.getPreview() == newItem.getPreview();
                }
            };

    public SubmissionsAdapter() {
        super(DIFF_CALLBACK);
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_container, parent, false);
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
//                Snackbar.make(parent, R.string.logged_out, Snackbar.LENGTH_LONG).setAction(R.string.log_in,
//                        v1 -> ((Activity) parent.getContext()).startActivityForResult(new Intent(parent.getContext(), AccountLogin.class), 1))
//                        .show();
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
//                Snackbar.make(parent, R.string.logged_out, Snackbar.LENGTH_LONG).setAction(R.string.log_in,
//                        v1 -> ((Activity) parent.getContext()).startActivityForResult(new Intent(parent.getContext(), AccountLogin.class), 1))
//                        .show();
            }
        });
        return vh;
    }

    private static String format(long number) {
        if (number < 10000) {
            // No need to format this
            return String.valueOf(number);
        }
        // Convert to a string
        final String string = String.valueOf(number);
        // The suffix we're using, 1-based
        final int magnitude = (string.length() - 1) / 3;
        // The number of digits we must show before the prefix
        final int digits = (string.length() - 1) % 3 + 1;

        // Build the string
        char[] value = new char[4];
        for (int i = 0; i < digits; i++) {
            value[i] = string.charAt(i);
        }
        int valueLength = digits;
        // Can and should we add a decimal point and an additional number?
        if (digits == 1 && string.charAt(1) != '0') {
            value[valueLength++] = '.';
            value[valueLength++] = string.charAt(1);
        }
        value[valueLength++] = SUFFIXES[magnitude - 1];
        return new String(value, 0, valueLength);
    }

    private static String getAbbreviatedTimeSpan(long timeMillis) {
        long span = System.currentTimeMillis() - timeMillis;
        if (span < DateUtils.MINUTE_IN_MILLIS) {
            return "Just Now";
        } else if (span < DateUtils.HOUR_IN_MILLIS) {
            return (span / DateUtils.MINUTE_IN_MILLIS) + "m";
        } else if (span < DateUtils.DAY_IN_MILLIS) {
            return (span / DateUtils.HOUR_IN_MILLIS) + "h";
        } else if (span < DateUtils.WEEK_IN_MILLIS) {
            return (span / DateUtils.DAY_IN_MILLIS) + "d";
        } else if (span < (DateUtils.YEAR_IN_MILLIS / 12L)) {
            return (span / DateUtils.WEEK_IN_MILLIS) + "w";
        } else if (span < DateUtils.YEAR_IN_MILLIS) {
            return (span / (DateUtils.YEAR_IN_MILLIS / 12L)) + "mo";
        } else {
            return (span / DateUtils.YEAR_IN_MILLIS) + "y";
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Submission submission = getItem(position);
        if (submission != null) {
            holder.title.setText(submission.getTitle());

            // Adding various description text info
            String description = submission.getSubreddit() + "  " +
                    submission.getAuthor() + "  " +
                    getAbbreviatedTimeSpan(submission.getCreated().getTime());
            Spannable spannable = new SpannableString(description);
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(holder.description.getContext(), R.color.primary_dark)), 0, submission.getSubreddit().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.description.setText(spannable, TextView.BufferType.SPANNABLE);

            holder.num_comments.setText(holder.num_comments.getContext().getString(R.string.comments, submission.getCommentCount()));
            holder.points.setText(format(submission.getScore()));
            // TODO: Placeholder image depending on type; hide when selftext
            SubmissionPreview preview = submission.getPreview();
            if (preview == null) {
                //holder.thumbnail.setVisibility(View.GONE);
            } else {
                String url = preview.getImages().get(0).getSource().getUrl();
                GlideApp.with(holder.thumbnail.getContext()).load(url).centerCrop().transition(DrawableTransitionOptions.withCrossFade()).into(holder.thumbnail);
            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private TextView num_comments;
        private TextView points;
        private ImageView thumbnail;
        private ImageButton upvote;
        private ImageButton downvote;

        ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            description = v.findViewById(R.id.description);
            num_comments = v.findViewById(R.id.num_comments);
            points = v.findViewById(R.id.points);
            thumbnail = v.findViewById(R.id.thumbnail);
            upvote = v.findViewById(R.id.upvote);
            downvote = v.findViewById(R.id.downvote);
        }
    }

}
