package eu.q5x.a321work;


import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import eu.q5x.a321work.Model.SubTask;
import us.feras.mdv.MarkdownView;


public class DetailActivity extends AppCompatActivity {
    LocationManager mLocationManager;

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    private static final int LOCATION_REFRESH_TIME = 10000;
    private static final int LOCATION_REFRESH_DISTANCE = 100;

    public static final String SUBTASK_ID = "subtaskId";
    public static final String CATEGORY = "category";

    private MapView mapView;
    private MapController mapController;
    private ItemizedOverlay<OverlayItem> locationOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        String id = getIntent().getStringExtra(SUBTASK_ID);
        SubTask subTask = WorkApp.getSubTask(id);
        if (subTask != null) {

            android.support.v7.app.ActionBar ab = getSupportActionBar();
            ab.setTitle("321Work!");
            ab.setSubtitle(subTask.title);

            MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
            if (markdownView != null) {
                markdownView.loadMarkdown(subTask.description);
            }

            checkPermissions();

            String category = subTask.category;
            mapView = (MapView) findViewById(R.id.mapview);
            if (mapView != null) {
                mapView.setTileSource(TileSourceFactory.MAPNIK);
                mapView.setBuiltInZoomControls(true);
                mapView.setMultiTouchControls(true);
                IMapController mapController = mapView.getController();
                mapController.setZoom(9);
                GeoPoint startPoint = new GeoPoint(48.0,7.0);
                mapController.setCenter(startPoint);
            }
            addPoints();
        }
    }

    private void readCSV() {
        List resultList = new ArrayList();
        InputStream contentSteam = getResources().openRawResource(R.raw.poi_fr);
        BufferedReader reader = new BufferedReader(new InputStreamReader(contentSteam));
        try {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";");
                resultList.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addPoints() {

        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        // Put overlay icon a little way from map centre
        GeoPoint point = new GeoPoint(48.0,7.0);
        items.add(new OverlayItem("Here", "SampleDescription", point));

        /* OnTapListener for the Markers, shows a simple Toast. */
        locationOverlay = new ItemizedIconOverlay<OverlayItem>(items,
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
                                "Item '" + item.getTitle() ,Toast.LENGTH_LONG).show();
                        return false;
                    }
                }, this.getApplicationContext());
        mapView.getOverlays().add(locationOverlay);
        mapView.invalidate();
    }

    private void checkPermissions() {
        List<String> permissions = new ArrayList<>();
        String message = "osmdroid permissions:";
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            message += "\nLocation to show user location.";
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            message += "\nStorage access to store map tiles.";
        }
        if (!permissions.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            String[] params = permissions.toArray(new String[permissions.size()]);
            requestPermissions(params, REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        } // else: We already have permissions, so handle as normal
    }
}
