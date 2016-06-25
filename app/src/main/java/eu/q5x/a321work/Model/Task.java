package eu.q5x.a321work.Model;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Class description
 */
public class Task implements Comparable<Task> {
    private String id;
    private String title;
    private String iconId; // was never used
    private String subtitle;
    private int order;
    private int maxContribution = 0;
    private boolean isExpanded = false;
    private Collection<String> dependencies;
    private List<SubTask> subTasks;

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getMaxContribution() {
        return maxContribution;
    }

    public void setMaxContribution(int maxContribution) {
        this.maxContribution = maxContribution;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public Collection<String> getDependencies() {
        if (dependencies == null) {
            dependencies = new HashSet<>();
        }
        return dependencies;
    }

    public void setDependencies(Collection<String> dependencies) {
        this.dependencies = dependencies;
    }

    public List<SubTask> getSubTasks() {
        if (subTasks == null) {
            subTasks = new ArrayList<>();
        }
        return subTasks;
    }

    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public int compareTo(@NonNull Task o) {
        if (order > o.order) return 1;
        else if (order < o.order) return -1;
        return 0;
    }

    public SubTask getSubTask(@NonNull String id) {
        for (SubTask subtask : subTasks) {
            if (id.equals(subtask.getId())) return subtask;
        }
        return null;
    }

    /**
     * Calculates the task progress depending on the state of subtasks.
     *
     * @return Progress of task in percent.
     */
    public int getProgress() {
        if (maxContribution > 0) {
            int sumContribution = 0;
            for (SubTask subtask : subTasks) {
                if (subtask.isDone()) sumContribution += subtask.getContribution();
            }
            if (sumContribution < 1) return 0;
            return Math.round(100 * (maxContribution / (float) sumContribution));
        }

        int percentDone = 0;
        for (SubTask subtask : subTasks) {
            if (subtask.isDone()) percentDone += Math.round(100f / subTasks.size()) + 1;
        }
        return percentDone;
    }
}
