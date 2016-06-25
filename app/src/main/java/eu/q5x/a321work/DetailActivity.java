package eu.q5x.a321work;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Collection;
import java.util.List;

import eu.q5x.a321work.Model.PointOfInterest;
import eu.q5x.a321work.Model.SubTask;
import eu.q5x.a321work.View.CustomMapView;
import us.feras.mdv.MarkdownView;


public class DetailActivity extends AppCompatActivity {
    public static final String SUBTASK_ID = "subtaskId";

    private static final String TAG = "Detail";
    private static final int REQUEST_CODE_ASK_PERM = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String id = getIntent().getStringExtra(SUBTASK_ID);
        SubTask subTask = WorkApp.getSubTask(id);
        if (subTask != null) {

            // init action bar
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle(subTask.getTitle());
                ab.setDisplayHomeAsUpEnabled(true);
            }

            // init markdown view
            MarkdownView markdownView = (MarkdownView) findViewById(R.id.markdownView);
            if (markdownView != null) {
                markdownView.loadMarkdown(subTask.getDescription());
            }

            // init map view
            initMapView(subTask);
        }
    }

    private void initMapView(SubTask subTask) {
        CustomMapView mapView = (CustomMapView) findViewById(R.id.mapview);
        if (mapView != null) {
            if (subTask.getCategories() != null && !subTask.getCategories().isEmpty()) {
                // check if the app has the necessary permissions
                checkPermissions();

                // get the points of interests for the given category from csv
                Collection<PointOfInterest> pois = readPoisFromCsv(subTask.getCategories());

                // configure map view
                mapView.setTileSource(TileSourceFactory.MAPNIK);
                mapView.setBuiltInZoomControls(true);
                mapView.setMultiTouchControls(true);
                IMapController mapController = mapView.getController();
                mapController.setZoom(18);
                GeoPoint startPoint = new GeoPoint(48, 7);
                if (!pois.isEmpty()) {
                    PointOfInterest firstPoi = pois.iterator().next();
                    startPoint = new GeoPoint(firstPoi.getLatitude(), firstPoi.getLongitude());
                }
                mapController.setCenter(startPoint);

                // place pois on the map
                addPointsToMap(mapView, pois);

                // create a list of all the pois
                createPoiList(pois);
            } else {
                mapView.setVisibility(View.GONE);
            }
        }
    }

    private Collection<PointOfInterest> readPoisFromCsv(Collection<String> categories) {
        Collection<PointOfInterest> pois = new ArrayList<>();

        // read csv file
        InputStream contentSteam = getResources().openRawResource(R.raw.poi_fr);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(contentSteam))) {
            reader.readLine();
            String line;
            // iterate over lines of the csv-file
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";");
                if (row.length >= 8 && !row[7].isEmpty() && categories.contains(row[7])) {
                    PointOfInterest poi = new PointOfInterest();
                    poi.setLatitude(Double.valueOf(row[1]));
                    poi.setLongitude(Double.valueOf(row[0]));
                    poi.setPoi(row[6]);
                    poi.setHouseNumber(row[2]);
                    poi.setStreet(row[4]);
                    poi.setOpening(row[5]);
                    pois.add(poi);
                }
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        return pois;
    }

    private void addPointsToMap(CustomMapView mapView, Collection<PointOfInterest> pois) {
        ArrayList<OverlayItem> items = new ArrayList<>();

        for (PointOfInterest poi : pois) {
            GeoPoint point = new GeoPoint(poi.getLatitude(), poi.getLongitude());
            items.add(new OverlayItem(Html.fromHtml(poi.toString()).toString(), "SampleDescription", point));
        }

        /* OnTapListener for the Markers, shows a simple Toast. */
        ItemizedOverlay<OverlayItem> locationOverlay = new ItemizedIconOverlay<>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        Toast.makeText(
                                DetailActivity.this,
                                item.getTitle(),
                                Toast.LENGTH_LONG)
                                .show();
                        return true; // We 'handled' this event.
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        Toast.makeText(
                                DetailActivity.this,
                                item.getTitle(),
                                Toast.LENGTH_LONG)
                                .show();
                        return true;
                    }
                }, this.getApplicationContext());
        mapView.getOverlays().add(locationOverlay);
        mapView.invalidate();
    }

    private void createPoiList(Collection<PointOfInterest> pois) {
        LinearLayout list = (LinearLayout) findViewById(R.id.list);
        for (PointOfInterest poi : pois) {
            String poiText = poi.toString() + "\n";
            TextView poiView = new TextView(this);
            poiView.setText(Html.fromHtml(poiText));
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
