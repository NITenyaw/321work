package eu.q5x.a321work.Model;


import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Collection;

import eu.q5x.a321work.WorkApp;


/**
 * Class description
 */
public class SubTask implements Comparable<SubTask> {
    private String id;
    private String title;
    private String description;
    private Collection<String> categories;
    private int order;
    private int contribution = 0;
    private Collection<String> dependencies;
    private Collection<String> autofulfills;


    public int getContribution() {
        return contribution;
    }

    public void setContribution(int contribution) {
        this.contribution = contribution;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<String> getCategories() {
        return categories;
    }

    public void setCategories(Collection<String> categories) {
        this.categories = categories;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Collection<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Collection<String> dependencies) {
        this.dependencies = dependencies;
    }

    public Collection<String> getAutofulfills() {
        return autofulfills;
    }

    public void setAutofulfills(Collection<String> autofulfills) {
        this.autofulfills = autofulfills;
    }

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
