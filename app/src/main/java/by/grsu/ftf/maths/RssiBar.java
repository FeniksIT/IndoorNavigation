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

public class RssiBar extends View {
    private float value;
    private int colorBar;

    private Paint paint = new Paint();

    public RssiBar (Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RssiBar, 0, 0);
        value = typedArray.getFloat(R.styleable.RssiBar_value,0);
        colorBar = typedArray.getColor(R.styleable.RssiBar_color, Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float x = this.getMeasuredWidth();
        float y = this.getMeasuredHeight();
        paint.setColor(Color.GRAY);
        canvas.drawRect(0,0,x,y,paint);
        paint.setColor(colorBar);
        canvas.drawRect(0,0,value*x,y,paint);
    }

    public void setValue(float value) {
        this.value = value;
        invalidate();
    }
}
