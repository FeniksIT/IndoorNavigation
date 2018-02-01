package by.grsu.ftf.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import by.grsu.ftf.activity.R;
import by.grsu.ftf.beacon.Beacon;

public class MapView extends View{

    private Paint paint = new Paint();
    private Drawable drawable;
    private List<Beacon> beacons = new ArrayList<>();

    public MapView(Context context) {
        this(context,null);
    }

    public MapView(Context context,@Nullable AttributeSet attrs) {
        super(context,attrs);
        drawable = getResources().getDrawable(R.drawable.ic_map);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = this.getMeasuredWidth();
        int y = this.getMeasuredHeight();
        drawable.setBounds(0,0,x,y);
        drawable.draw(canvas);
        paint.setColor(Color.RED);
        for (Beacon beacon:beacons){
            canvas.drawCircle(x*beacon.getCoordinates().x,y*beacon.getCoordinates().y,x/100,paint);
        }
    }

    public void setPointFS(List<Beacon> beacons) {
        this.beacons = beacons;
        invalidate();
    }
}
