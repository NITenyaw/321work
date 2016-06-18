package eu.q5x.a321work.Model;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Set;


/**
 * Class description
 */
public class Task {
    public String id;
    public String title;
    public String subtitle;
    public String iconId;
    public int order;
    public boolean isExpanded = false;
    public Set<String> dependencies;
    public ArrayList<SubTask> subTasks;

    public SubTask getSubTask(@NonNull String id) {
        for(SubTask subtask : subTasks) {
            if (id.equals(subtask.id)) return subtask;
        }
        return null;
    }
}
