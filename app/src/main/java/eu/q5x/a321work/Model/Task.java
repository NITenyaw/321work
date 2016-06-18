package eu.q5x.a321work.Model;


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
    public Set<String> dependencies;
    public ArrayList<SubTask> subTasks;


}
