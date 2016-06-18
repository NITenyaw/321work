package eu.q5x.a321work.Model;


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
    public int compareTo(Phase o) {
        if (order > o.order) return 1;
        else if (order < o.order) return -1;
        return 0;
    }
}
