package eu.q5x.a321work;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Application;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.q5x.a321work.Model.Phase;
import eu.q5x.a321work.Model.SubTask;
import eu.q5x.a321work.Model.Task;


/**
 * Class description
 */
public class WorkApp extends Application {
    private static final String TAG = "321work";
    private static ArrayList<Phase> phases;

    public void onCreate() {
        super.onCreate();

        ObjectMapper mapper = new ObjectMapper();
        InputStream contentSteam = getResources().openRawResource(R.raw.content);

        // JSON from file to Object
        try {
            TypeReference<List<Phase>> typeReference = new TypeReference<List<Phase>>() {};
            phases = mapper.readValue(contentSteam, typeReference);

            Collections.sort(phases);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public static ArrayList<Phase> getPhases() {
        return phases;
    }

    public static Phase getPhase(String id) {
        for(Phase phase : phases) {
            if (id.equals(phase.id)) return phase;
        }
        return null;
    }

    public static SubTask getSubTask(String id) {
        for(Phase phase : phases) {
            for (Task task : phase.tasks) {
                SubTask subTask = task.getSubTask(id);
                if (subTask != null) return subTask;
            }
        }
        return null;
    }
}
