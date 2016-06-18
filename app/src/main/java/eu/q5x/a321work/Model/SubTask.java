package eu.q5x.a321work.Model;


import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Set;

import eu.q5x.a321work.WorkApp;


/**
 * Class description
 */
public class SubTask implements Comparable<SubTask> {
    public String id;
    public String title;
    public String description;
    public String category;
    public int order;
    public int contribution;
    public Set<String> dependencies;
    public Set<String> autofulfills;

    @Override
    public int compareTo(@NonNull SubTask o) {
        if (order > o.order) return 1;
        else if (order < o.order) return -1;
        return 0;
    }

    public boolean isDone() {
        if (id == null || id.isEmpty()) {
            Log.e("SubTask", "SubTask without id.");
            return true;
        }
        return WorkApp.getPref().contains(id);
    }
}
