package eu.q5x.a321work;


import java.util.ArrayList;

import android.app.Application;

import eu.q5x.a321work.Model.Phase;


/**
 * Class description
 */
public class WorkApp extends Application {
    private static ArrayList<Phase> phases;

    public void onCreate() {
        super.onCreate();
    }

    public static ArrayList<Phase> getPhases() {
        return phases;
    }
}
