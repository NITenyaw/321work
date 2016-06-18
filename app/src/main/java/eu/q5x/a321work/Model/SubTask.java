package eu.q5x.a321work.Model;


import java.util.Set;


/**
 * Class description
 */
public class SubTask {
    public String id;
    public String order;
    public String title;
    public String description;
    public String category;
    public int contribution;
    public Set<String> dependencies;
    public Set<String> satisfies;
}
