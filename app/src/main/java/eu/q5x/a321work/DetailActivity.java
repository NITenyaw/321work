package eu.q5x.a321work;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import eu.q5x.a321work.Model.SubTask;
import us.feras.mdv.MarkdownView;


public class DetailActivity extends AppCompatActivity {
    public static final String SUBTASK_ID = "subtaskId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String id = getIntent().getStringExtra(SUBTASK_ID);
        SubTask subTask = WorkApp.getSubTask(id);
        if (subTask != null) {

            MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
            if (markdownView != null) {
                markdownView.loadMarkdown(subTask.description);
            }
        }
    }
}
