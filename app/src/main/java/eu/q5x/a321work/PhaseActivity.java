package eu.q5x.a321work;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import eu.q5x.a321work.Adapters.PhaseAdapter;
import eu.q5x.a321work.Model.Phase;


public class PhaseActivity extends AppCompatActivity {
    private PhaseActivity activity = this;
    RecyclerView.Adapter adapter;

    private PhaseAdapter.OnItemClickListener listener =
            new PhaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Phase phase, final PhaseAdapter.ViewHolder viewHolder) {
                    Intent openPhase = new Intent(activity, TaskActivity.class);
                    openPhase.putExtra(TaskActivity.PHASE_ID, phase.id);
                    startActivity(openPhase);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phase);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        if (recyclerView != null) {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            // recyclerView.setHasFixedSize(true);

            // use a linear layout manager
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            // specify an adapter (see also next example)
            adapter = new PhaseAdapter(WorkApp.getPhases(), listener);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.impressum:
                Intent intent = new Intent(this, Impressum.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
