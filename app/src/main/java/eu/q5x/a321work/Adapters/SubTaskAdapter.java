package eu.q5x.a321work.Adapters;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import eu.q5x.a321work.Model.SubTask;
import eu.q5x.a321work.Model.Task;
import eu.q5x.a321work.R;

/**
 * Class description
 */
public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.ViewHolder> {
    private ArrayList<SubTask> subTasks;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public AppCompatCheckBox checkBox;
        public TextView title;
        public ImageView details;

        public ViewHolder(RelativeLayout v) {
            super(v);

            checkBox = (AppCompatCheckBox) v.findViewById(R.id.checkbox);
            title = (TextView) v.findViewById(R.id.title);
            details = (ImageView) v.findViewById(R.id.details);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SubTaskAdapter(ArrayList<SubTask> mySubTasks) {
        subTasks = mySubTasks;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        SubTask task = subTasks.get(position);

        // - replace the contents of the view with that element
        holder.title.setText(task.title);

        /*
        Context context = imageView.getContext();
        int id = context.getResources().getIdentifier("picture0001", "drawable", context.getPackageName());
        holder.icon.setImageResource(id);
        */

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (subTasks == null) return 0;
        return subTasks.size();
    }
}
