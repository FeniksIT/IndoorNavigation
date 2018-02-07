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
            if (beacon.getY() !=null) {
                canvas.drawCircle(x * beacon.getX(), y * beacon.getY(), x / 100, paint);
                canvas.drawText(beacon.getName(), x * beacon.getX()+x / 100, y * beacon.getY()+y / 100, paint);
            }
        }
        paint.setColor(Color.GREEN);
        canvas.drawCircle(x*user.x, y*user.y, x/70, paint);
    }

    public void setPointFS(List<Beacon> beacons) {
        this.beacons = beacons;
        invalidate();
    }

    public void setCordinateUser(PointF user){
        this.user = user;
        invalidate();
    }

}
