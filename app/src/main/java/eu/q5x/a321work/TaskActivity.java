package eu.q5x.a321work;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import eu.q5x.a321work.Adapters.TaskAdapter;
import eu.q5x.a321work.Model.Phase;
import eu.q5x.a321work.Model.Task;


public class TaskActivity extends AppCompatActivity {
    public static final String PHASE_ID = "phaseId";
    public SharedPreferences.OnSharedPreferenceChangeListener prefListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        String id = getIntent().getStringExtra(PHASE_ID);
        final Phase phase = WorkApp.getPhase(id);
        if (phase != null) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("  " + phase.title);

                int iconId = getResources().getIdentifier(phase.id, "mipmap", getPackageName());
                final Drawable drawable = ResourcesCompat.getDrawable(getResources(), iconId, null);
                if (drawable != null) {
                    final Drawable wrapped = DrawableCompat.wrap(drawable);
                    drawable.mutate();
                    DrawableCompat.setTint(wrapped, Color.parseColor("#FFFFFF"));
                    actionBar.setLogo(wrapped);
                    actionBar.setDisplayUseLogoEnabled(true);
                    actionBar.setDisplayShowHomeEnabled(true);
                }
            }
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            if (recyclerView != null) {
                // use this setting to improve performance if you know that changes
                // in content_en do not change the layout size of the RecyclerView
                // recyclerView.setHasFixedSize(true);

                // use a linear layout manager
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(layoutManager);

                // specify an adapter (see also next example)
                RecyclerView.Adapter adapter = new TaskAdapter(phase.tasks);
                recyclerView.setAdapter(adapter);
            }
        }
    }


}
