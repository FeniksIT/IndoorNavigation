package by.grsu.ftf.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
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
    private PointF user = new PointF(0,0);
    private Drawable drawable;
    private List<Beacon> beacons = new ArrayList<>();
    private List<Float> setings = new ArrayList<>();

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
            if (beacon.getY() !=null && beacon.getX()!=null && setings.size()>1) {
                canvas.drawCircle(x*Math.abs((beacon.getX()*(-1))/setings.get(0)), y*Math.abs((beacon.getY()*(-1))/setings.get(1)), x / 100, paint);
                canvas.drawText(beacon.getName() + "   " + beacon.getDistance(), x*Math.abs((beacon.getX()*(-1))/setings.get(0))+x / 100, y*Math.abs((beacon.getY()*(-1))/setings.get(1))+y / 100, paint);
            }
        }
        paint.setColor(Color.GREEN);
        if (setings.size()>1) {
            canvas.drawCircle(x * Math.abs((user.x * (-1)) / setings.get(0)), y * Math.abs((user.y * (-1)) / setings.get(1)), x / 70, paint);
        }
    }

    public void setPointFS(List<Beacon> beacons, List<Float> setings) {
        this.beacons = beacons;
        this.setings = setings;
        invalidate();
    }

    public void setCordinateUser(PointF user){
        this.user = user;
        invalidate();
    }

}
