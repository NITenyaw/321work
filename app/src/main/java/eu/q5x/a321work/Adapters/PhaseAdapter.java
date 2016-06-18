package eu.q5x.a321work.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eu.q5x.a321work.Model.Phase;
import eu.q5x.a321work.R;

/**
 * Class description
 */
public class PhaseAdapter extends RecyclerView.Adapter<PhaseAdapter.ViewHolder> {
    private ArrayList<Phase> phases;
    private OnItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;

        public ViewHolder(CardView v) {
            super(v);

            title = (TextView) v.findViewById(R.id.title);

            // icon
            /*
            Context context = imageView.getContext();
            int id = context.getResources().getIdentifier("picture0001", "drawable", context.getPackageName());
            imageView.setImageResource(id);
            */
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PhaseAdapter(ArrayList<Phase> myPhases,
                        OnItemClickListener myListener) {
        phases = myPhases;
        listener = myListener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PhaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_phase, parent, false);


        return new ViewHolder(cardView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        final Phase phase = phases.get(position);

        // - replace the contents of the view with that element
        holder.title.setText(phase.title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(phase, holder);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return phases.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Phase phase, ViewHolder viewHolder);
    }
}
