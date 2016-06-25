package eu.q5x.a321work.Model;


import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Class description
 */
public class Phase implements Comparable<Phase> {
    private String id;
    private String iconId;
    private String title;
    private int order;
    private List<Task> tasks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<Task> getTasks() {
        if(tasks == null){
            tasks = new ArrayList<>();
        }
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public int compareTo(@NonNull Phase o) {
        if (order > o.order) return 1;
        else if (order < o.order) return -1;
        return 0;
    }

    public int getProgress() {
        int percentDone = 0;
        for (Task task : tasks) {
            percentDone += Math.round((float) task.getProgress() / (float) tasks.size());
        }
        return percentDone;
    }
}
