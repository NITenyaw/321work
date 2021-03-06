package eu.q5x.a321work.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.q5x.a321work.Model.Phase;
import eu.q5x.a321work.Model.Task;
import eu.q5x.a321work.R;
import eu.q5x.a321work.View.SmoothProgressBar;

/**
 * Class description
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private List<Task> tasks;
    private TaskAdapter taskAdapter = this;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout header;
        public ImageView icon;
        public ImageView done;
        public TextView title;
        public SmoothProgressBar progressBar;
        public RecyclerView recyclerView;

        public ViewHolder(CardView v) {
            super(v);

            header = (RelativeLayout) v.findViewById(R.id.header);
            icon = (ImageView) v.findViewById(R.id.task_icon);
            done = (ImageView) v.findViewById(R.id.done_icon);
            title = (TextView) v.findViewById(R.id.title);
            progressBar = (SmoothProgressBar) v.findViewById(R.id.progress);
            recyclerView = (RecyclerView) v.findViewById(R.id.subtask_recycler_view);
        }

        public void setProgress(int progress, boolean animate) {
            if (progress >= 99) {
                done.setVisibility(View.VISIBLE);
                header.setBackgroundColor(Color.parseColor("#CEDD95"));
            } else {
                done.setVisibility(View.GONE);
                header.setBackgroundColor(0);
            }
            progressBar.setAnimate(animate);
            progressBar.setProgress(progress);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskAdapter(List<Task> myTasks) {
        tasks = myTasks;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_task, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(cardView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        final Task task = tasks.get(position);

        holder.title.setText(task.getTitle());

        Context context = holder.icon.getContext();
        String iconId = task.getId().replaceAll("-", "").toLowerCase();
        if (task.getId().startsWith("work")) {
            iconId = iconId.replace("work", "appr");
        }

        int id = context.getResources().getIdentifier(iconId, "mipmap", context.getPackageName());

        final Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), id, null);
        if (drawable != null) {
            final Drawable wrapped = DrawableCompat.wrap(drawable);
            drawable.mutate();
            DrawableCompat.setTint(wrapped, Color.parseColor("#907A00"));
            holder.icon.setImageDrawable(wrapped);
        }

        holder.header.setBackgroundColor(0);

        holder.setProgress(task.getProgress(), false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new SubTaskAdapter(context, task, holder);
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setVisibility(task.isExpanded() ? View.VISIBLE : View.GONE);

        holder.header.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        task.setExpanded(!task.isExpanded());
                        notifyDataSetChanged();
                    }
                });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (tasks == null) return 0;
        return tasks.size();
    }
}
