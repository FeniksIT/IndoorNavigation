package by.grsu.ftf.maths;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import by.grsu.ftf.activity.R;

public class barRssi extends View {

    private String maxRssi;
    private String minRssi;
    private String valueRssi;

    Paint paint = new Paint();

    public barRssi(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.barRssi, 0, 0);
        maxRssi = typedArray.getString(R.styleable.barRssi_max);
        valueRssi = typedArray.getString(R.styleable.barRssi_value);
        minRssi = typedArray.getString(R.styleable.barRssi_min);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float x = this.getMeasuredWidth();
        int y = this.getMeasuredHeight();
        paint.setColor(Color.GRAY);
        canvas.drawRect(0,0,x,y,paint);
        x = Math.abs((Integer.valueOf(minRssi) - Integer.valueOf(valueRssi))*x)/(Integer.valueOf(maxRssi) - Integer.valueOf(minRssi));
        paint.setColor(Color.RED);
        canvas.drawRect(0,0,x,y,paint);
    }
    public void setMaxRssi(String maxRssi) {
        this.maxRssi = maxRssi;
        invalidate();
        requestLayout();
    }

    public void setValueRssi(String valueRssi) {
        this.valueRssi = valueRssi;
        invalidate();
        requestLayout();
    }
}
