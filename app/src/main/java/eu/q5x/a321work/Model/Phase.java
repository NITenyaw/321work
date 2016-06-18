package eu.q5x.a321work.Model;


import android.support.annotation.NonNull;

import java.util.ArrayList;


/**
 * Class description
 */
public class Phase implements Comparable<Phase> {
    public String id;
    public String iconId;
    public String title;
    public int order;
    public ArrayList<Task> tasks;

    @Override
    public int compareTo(@NonNull Phase o) {
        if (order > o.order) return 1;
        else if (order < o.order) return -1;
        return 0;
    }

    public int getProgress() {
        int percentDone = 0;
        for(Task task : tasks) {
            percentDone += Math.round((float) task.getProgress() / (float) tasks.size());
        }
        return percentDone;
    }
}
