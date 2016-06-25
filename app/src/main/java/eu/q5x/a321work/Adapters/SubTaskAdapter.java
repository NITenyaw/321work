package eu.q5x.a321work.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import eu.q5x.a321work.DetailActivity;
import eu.q5x.a321work.Model.SubTask;
import eu.q5x.a321work.Model.Task;
import eu.q5x.a321work.R;
import eu.q5x.a321work.WorkApp;

/**
 * Class description
 */
public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.ViewHolder> {
    private Context context;
    private Task task;
    private TaskAdapter.ViewHolder taskVh;
    private List<SubTask> subTasks;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public AppCompatCheckBox checkBox;
        public ImageView details;
        public TextView subtitle;

        public ViewHolder(RelativeLayout v) {
            super(v);

            checkBox = (AppCompatCheckBox) v.findViewById(R.id.checkbox);
            details = (ImageView) v.findViewById(R.id.details);
            subtitle = (TextView) v.findViewById(R.id.subtitle);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SubTaskAdapter(Context parentContext,
                          Task parentTask,
                          TaskAdapter.ViewHolder parentVh) {
        context = parentContext;
        task = parentTask;
        taskVh = parentVh;
        subTasks = task.getSubTasks();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SubTaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_subtask, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        final SubTask subTask = subTasks.get(position);

        // - replace the contents of the view with that element
        // holder.title.setText(subTask.title);
        if (subTask.getTitle() != null && !subTask.getTitle().isEmpty()) {
            String[] title = subTask.getTitle().split("\r\n|\r|\n", 2);
            holder.checkBox.setText(title[0]);
            if (title.length > 1) {
                holder.subtitle.setVisibility(View.VISIBLE);
                holder.subtitle.setText(title[1]);
            } else {
                holder.subtitle.setVisibility(View.GONE);
            }
        }

        holder.checkBox.setChecked(WorkApp.getPref().contains(subTask.getId()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    WorkApp.getPref().edit().putBoolean(subTask.getId(), true).apply();
                } else {
                    WorkApp.getPref().edit().remove(subTask.getId()).apply();
                }
                taskVh.setProgress(task.getProgress(), true);
            }
        });
        holder.details.setVisibility(View.GONE);
        if (subTask.getDescription() != null && !subTask.getDescription().isEmpty()) {
            holder.details.setVisibility(View.VISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(subTask);
                }
            });
        }

        if (subTask.getDependencies() != null && !subTask.getDependencies().isEmpty()) {
            SharedPreferences prefs = WorkApp.getPref();
            applyDeps(holder.checkBox, prefs, subTask.getDependencies());

            prefs.registerOnSharedPreferenceChangeListener(
                    new SharedPreferences.OnSharedPreferenceChangeListener() {
                        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                            if (subTask.getDependencies().contains(key)) {
                                applyDeps(holder.checkBox, prefs, subTask.getDependencies());
                            }
                        }
                    });
        }
    }

    public void applyDeps(AppCompatCheckBox cb, SharedPreferences pref, Collection<String> deps) {
        boolean enabled = true;
        for (String dep : deps) {
            if (!pref.contains(dep)) {
                enabled = false;
                break;
            }
        }
        cb.setEnabled(enabled);
        if (!enabled) cb.setChecked(false);
    }

    public void onItemClick(SubTask subTask) {
        Intent openPhase = new Intent(context, DetailActivity.class);
        openPhase.putExtra(DetailActivity.SUBTASK_ID, subTask.getId());
        context.startActivity(openPhase);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (subTasks == null) return 0;
        return subTasks.size();
    }
}
