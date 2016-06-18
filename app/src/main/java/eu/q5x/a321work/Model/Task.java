package eu.q5x.a321work.Model;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Set;


/**
 * Class description
 */
public class Task implements Comparable<Task> {
    public String id;
    public String title;
    public String iconId;
    public String subtitle;
    public int order;
    public int maxContribution = 0;
    public boolean isExpanded = false;
    public Set<String> dependencies;
    public ArrayList<SubTask> subTasks;

    @Override
    public int compareTo(@NonNull Task o) {
        if (order > o.order) return 1;
        else if (order < o.order) return -1;
        return 0;
    }

    public SubTask getSubTask(@NonNull String id) {
        for(SubTask subtask : subTasks) {
            if (id.equals(subtask.id)) return subtask;
        }
        return null;
    }

    /**
     * Calculates the task progress depending on the state of subtasks.
     * @return Progress of task in percent.
     */
    public int getProgress() {
        if (maxContribution > 0) {
            int sumContribution = 0;
            for(SubTask subtask : subTasks) {
                if (subtask.isDone()) sumContribution += subtask.contribution;
            }
            return Math.round((float) maxContribution / (float) sumContribution);
        }

        int percentDone = 0;
        for(SubTask subtask : subTasks) {
            if (subtask.isDone()) percentDone += Math.round(100f / subTasks.size());
        }
        return percentDone;
    }
}
