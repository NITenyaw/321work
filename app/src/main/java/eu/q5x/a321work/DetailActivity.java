package eu.q5x.a321work;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import eu.q5x.a321work.Model.SubTask;
import eu.q5x.a321work.View.CustomMapView;
import us.feras.mdv.MarkdownView;


public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "Detail";
    public static final String SUBTASK_ID = "subtaskId";

    private CustomMapView mapView;

    private ArrayList<Double> lats;
    private ArrayList<Double> longs;
    private ArrayList<String> poi;
    private ArrayList<String> house_number;
    private ArrayList<String> streets;
    private ArrayList<String> opening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String id = getIntent().getStringExtra(SUBTASK_ID);
        SubTask subTask = WorkApp.getSubTask(id);
        if (subTask != null) {

            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle(subTask.title);
                ab.setDisplayHomeAsUpEnabled(true);
            }

            MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
            if (markdownView != null) {
                markdownView.loadMarkdown(subTask.description);
            }

            mapView = (CustomMapView) findViewById(R.id.mapview);
            if (mapView != null) {
                if (subTask.category != null) {
                    readCSV(subTask.category);
                    checkPermissions();

                        mapView.setTileSource(TileSourceFactory.MAPNIK);
                        mapView.setBuiltInZoomControls(true);
                        mapView.setMultiTouchControls(true);
                        IMapController mapController = mapView.getController();
                        mapController.setZoom(18);
                        GeoPoint startPoint = new GeoPoint(48, 7);
                        if (lats.size() > 0) {
                            startPoint = new GeoPoint(lats.get(0), longs.get(0));
                        }
                        mapController.setCenter(startPoint);

                    addPoints();
                    makeList();
                } else {
                    mapView.setVisibility(View.GONE);
                }
            }
        }
    }

    private void readCSV(HashSet<String> category) {
        InputStream contentSteam = getResources().openRawResource(R.raw.poi_fr);
        BufferedReader reader = new BufferedReader(new InputStreamReader(contentSteam));
        lats = new ArrayList<>();
        longs = new ArrayList<>();
        poi = new ArrayList<>();
        streets = new ArrayList<>();
        opening = new ArrayList<>();
        house_number = new ArrayList<>();
        // ArrayList<String> plz = new ArrayList<>();
        try {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";");
                if (row.length >= 8 && !row[7].isEmpty() && category.contains(row[7])) {
                    lats.add(Double.valueOf(row[1]));
                    longs.add(Double.valueOf(row[0]));
                    poi.add(row[6]);
                    house_number.add(row[2]);
                    streets.add(row[4]);
                    opening.add(row[5]);
                    // plz.add(row[3]);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
    }

    private String buildPoiString(int i) {
        return "<b>" + poi.get(i) + "</b>" + "\n" +
                streets.get(i) + " " + house_number.get(i) + "\n" +
                opening.get(i);
    }

    private void addPoints() {
        ArrayList<OverlayItem> items = new ArrayList<>();

        for (int i = 0; i < lats.size(); i++) {
            GeoPoint point = new GeoPoint(lats.get(i), longs.get(i));
            // Put overlay icon a little way from map centre
            items.add(new OverlayItem(Html.fromHtml(buildPoiString(i)).toString(), "SampleDescription", point));
        }


        /* OnTapListener for the Markers, shows a simple Toast. */
        ItemizedOverlay<OverlayItem> locationOverlay = new ItemizedIconOverlay<>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index,
                                                     final OverlayItem item) {
                        Toast.makeText(
                                DetailActivity.this,
                                "Beratungsstelle: '" + item.getTitle(), Toast.LENGTH_LONG).show();
                        return true; // We 'handled' this event.
                    }

                    @Override
                    public boolean onItemLongPress(final int index,
                                                   final OverlayItem item) {
                        Toast.makeText(
                                DetailActivity.this,
                                "Item '" + item.getTitle(), Toast.LENGTH_LONG).show();
                        return false;
                    }
                }, this.getApplicationContext());
        mapView.getOverlays().add(locationOverlay);
        mapView.invalidate();
    }

    private void makeList() {
        LinearLayout list = (LinearLayout) findViewById(R.id.list);
        for (int i = 0; i < lats.size(); i++) {
            String poi = buildPoiString(i) + "\n";
            TextView poiView = new TextView(this);
            poiView.setText(Html.fromHtml(poi));
            if (list != null) {
                list.addView(poiView);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissions.isEmpty()) {
            String[] params = permissions.toArray(new String[permissions.size()]);
            int REQUEST_CODE_ASK_PERM = 124;
            requestPermissions(params, REQUEST_CODE_ASK_PERM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
