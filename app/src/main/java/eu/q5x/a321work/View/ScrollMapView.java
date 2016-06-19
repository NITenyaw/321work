package eu.q5x.a321work.View;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.views.MapView;

import eu.q5x.a321work.R;

/**
 * Created by gummibumm on 19.06.16.
 */

public class ScrollMapView extends MapView {

    boolean moving;
    boolean canMove;

    protected ScrollMapView(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs);
    }

    public ScrollMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollMapView(Context context) {
        super(context);
    }

    public ScrollMapView(Context context, MapTileProviderBase aTileProvider) {
        super(context, aTileProvider);
    }

    public ScrollMapView(Context context, MapTileProviderBase aTileProvider, Handler tileRequestCompleteHandler) {
        super(context, aTileProvider, tileRequestCompleteHandler);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getRawX();
        float y = ev.getRawY();
        View view = findViewById(R.id.mapview);
        if (view != null) {
            boolean isInside = isPointInsideView(x, y, view);
            if (isInside) {
                System.out.println("Touch in");
                canMove = false;
            } else {
                int action = ev.getAction();
                System.out.println(action);
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        moving = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!moving) {
                            // CustomDefaultInfoWindow window = (CustomDefaultInfoWindow) view.getTag();
                            // window.close();
                            moving = false;
                        }
                    default:
                        break;
                }
                canMove = true;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public void scrollTo(int arg0, int arg1) {
        if (canMove) {
            super.scrollTo(arg0, arg1);
        }

    }

    ;

    private boolean isPointInsideView(float x, float y, View view) {
        int location[] = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        //point is inside view bounds
        if ((x > viewX && x < (viewX + view.getWidth())) &&
                (y > viewY && y < (viewY + view.getHeight()))) {
            return true;
        } else {
            return false;
        }
    }
}




