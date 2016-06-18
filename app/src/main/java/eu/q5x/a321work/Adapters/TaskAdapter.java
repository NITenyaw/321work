package eu.q5x.a321work.Adapters;

import android.content.Context;
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
import android.widget.TextView;

import java.util.ArrayList;

import eu.q5x.a321work.Model.Phase;
import eu.q5x.a321work.Model.Task;
import eu.q5x.a321work.R;

/**
 * Class description
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Task> tasks;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout header;
        public ImageView icon;
        public TextView title;
        public ProgressBar progressBar;
        public RecyclerView recyclerView;

        public ViewHolder(CardView v) {
            super(v);

            header = (LinearLayout) v.findViewById(R.id.header);
            icon = (ImageView) v.findViewById(R.id.task_icon);
            title = (TextView) v.findViewById(R.id.title);
            progressBar = (ProgressBar) v.findViewById(R.id.progress);
            recyclerView = (RecyclerView) v.findViewById(R.id.subtask_recycler_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public TaskAdapter(Context parentContext, ArrayList<Task> myTasks) {
        context = parentContext;
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

        // - replace the contents of the view with that element
        holder.title.setText(task.title);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter = new SubTaskAdapter(context, task.subTasks);
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setVisibility(task.isExpanded ? View.VISIBLE : View.GONE);

        holder.header.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    task.isExpanded = !task.isExpanded;
                    notifyDataSetChanged();
                }
            });
        /*
        Context context = imageView.getContext();
        int id = context.getResources().getIdentifier("picture0001", "drawable", context.getPackageName());
        holder.icon.setImageResource(id);
        */

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (tasks == null) return 0;
        return tasks.size();
    }
}
